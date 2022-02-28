package com.yh.demo.ureport.provider;

import com.bstek.ureport.provider.report.ReportFile;
import com.bstek.ureport.provider.report.ReportProvider;
import com.yh.demo.ureport.constant.BaseContants;
import com.yh.demo.ureport.entity.ReportFileEntity;
import com.yh.demo.ureport.properties.DbSqlProperties;
import com.yh.demo.ureport.service.IReportFileEntityService;
import lombok.AllArgsConstructor;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.List;


/**
 * 报表数据源适配抽象类
 *
 * @author yanghan
 * @date 2020/11/19
 */
@ConditionalOnProperty(
        prefix = BaseContants.BASE_PROVIDER_PREFIX,
        value = "enabled",
        havingValue = "true"
)
@EnableConfigurationProperties(DbSqlProperties.class)
@AllArgsConstructor
@Component
public class DbSqlProvider implements ReportProvider {

    private final IReportFileEntityService iReportFileEntityService;

    private final DbSqlProperties dbSqlProperties;


    /**
     * 加载报表
     *
     * @param fileName 文件全称
     * @return java.io.InputStream
     * @author yanghan
     * @date 2020/11/19
     */
    @Override
    public InputStream loadReport(String fileName) {
        ReportFileEntity reportFileEntity = iReportFileEntityService.getByName(this.getCorrectName(fileName));
        if (reportFileEntity == null) {
            return null;
        }
        String content = reportFileEntity.getContent();
        return new ByteArrayInputStream(content.getBytes());
    }

    /**
     * 删除报表
     *
     * @param fileName 文件全称
     * @author yanghan
     * @date 2020/11/19
     */
    @Override
    public void deleteReport(String fileName) {
        iReportFileEntityService.deleteByName(this.getCorrectName(fileName));
    }

    /**
     * 打开报表文件，获取列表
     *
     * @return java.util.List<com.bstek.ureport.provider.report.ReportFile>
     * @author yanghan
     * @date 2020/11/19
     */
    @Override
    public List<ReportFile> getReportFiles() {
        return iReportFileEntityService.getReportFiles();
    }

    /**
     * 保存更新报表
     *
     * @param fileName 文件全称
     * @param content  报表内容xml
     * @author yanghan
     * @date 2020/11/19
     */
    @Override
    public void saveReport(String fileName, String content) {
        fileName = this.getCorrectName(fileName);
        iReportFileEntityService.saveOrUpdateByName(fileName, content);
    }

    /**
     * 获取没有前缀的文件名
     *
     * @param fileName 文件全称
     * @return java.lang.String
     * @author yanghan
     * @date 2020/11/19
     */
    private String getCorrectName(String fileName) {
        if (fileName.startsWith(dbSqlProperties.getPrefix())) {
            fileName = fileName.substring(dbSqlProperties.getPrefix().length());
        }
        return fileName;
    }

    @Override
    public String getName() {
        return dbSqlProperties.getName();
    }

    @Override
    public boolean disabled() {
        return !dbSqlProperties.isEnabled();
    }

    @Override
    public String getPrefix() {
        return dbSqlProperties.getPrefix();
    }


}
