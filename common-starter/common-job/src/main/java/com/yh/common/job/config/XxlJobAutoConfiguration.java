package com.yh.common.job.config;


import com.xxl.job.core.executor.impl.XxlJobSpringExecutor;
import com.yh.common.job.handler.XxjJobDemoHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author yanghan
 * @date 2022/2/22
 */
@ConditionalOnProperty(value = XxlJobProperties.PREFIX + ".enabled", havingValue = "true")
@Configuration
@EnableConfigurationProperties(XxlJobProperties.class)
public class XxlJobAutoConfiguration {

    private static final Logger logger = LoggerFactory.getLogger(XxlJobAutoConfiguration.class);

    private final XxlJobProperties xxlJobProperties;

    public XxlJobAutoConfiguration(XxlJobProperties xxlJobProperties) {
        this.xxlJobProperties = xxlJobProperties;
    }

    @Bean//(initMethod = "start", destroyMethod = "destroy")
    public XxlJobSpringExecutor xxlJobExecutor() {
        logger.info(">>>>>>>>>>> xxl-job config init.");
        XxlJobSpringExecutor xxlJobSpringExecutor = new XxlJobSpringExecutor();
        xxlJobSpringExecutor.setAdminAddresses(xxlJobProperties.getAdmin().getAddresses());
        xxlJobSpringExecutor.setAppname(xxlJobProperties.getExecutor().getAppName());
        xxlJobSpringExecutor.setIp(xxlJobProperties.getExecutor().getIp());
        xxlJobSpringExecutor.setPort(xxlJobProperties.getExecutor().getPort());
        xxlJobSpringExecutor.setAccessToken(xxlJobProperties.getAccessToken());
        xxlJobSpringExecutor.setLogPath(xxlJobProperties.getExecutor().getLogPath());
        xxlJobSpringExecutor.setLogRetentionDays(xxlJobProperties.getExecutor().getLogRetentionDays());
        return xxlJobSpringExecutor;
    }

    @Bean
    public XxjJobDemoHandler demoHandler(){
        return new XxjJobDemoHandler();
    }
}
