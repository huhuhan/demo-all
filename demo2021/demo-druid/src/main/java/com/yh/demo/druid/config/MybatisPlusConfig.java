package com.yh.demo.druid.config;

import com.baomidou.mybatisplus.autoconfigure.SpringBootVFS;
import com.baomidou.mybatisplus.extension.spring.MybatisSqlSessionFactoryBean;
import com.yh.demo.db.dynamic.MyDynamicRoutingDataSource;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.core.io.support.ResourcePatternResolver;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@EnableTransactionManagement
@Configuration
@MapperScan("com.yh.demo.druid.**.mapper")
public class MybatisPlusConfig {

    /**
     * Mybatis-Plus是用默认的dataSource这个Bean，重新指定为自定义的数据库源yhDataSource
     * @param dataSource
     * @return
     * @throws Exception
     */
    @Bean
    @ConditionalOnMissingBean
    public SqlSessionFactory sqlSessionFactory(MyDynamicRoutingDataSource dataSource) throws Exception {
        MybatisSqlSessionFactoryBean factory = new MybatisSqlSessionFactoryBean();
        factory.setDataSource(dataSource);
        factory.setVfs(SpringBootVFS.class);

        ResourcePatternResolver resourceResolver = new PathMatchingResourcePatternResolver();
        Resource[] mappers = resourceResolver.getResources("classpath:/mapper/*Mapper.xml");

        factory.setMapperLocations(mappers);
        return factory.getObject();
    }
}

