package com.yh.demo.ureport.provider;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.bstek.ureport.provider.report.ReportFile;
import com.bstek.ureport.provider.report.ReportProvider;
import com.yh.demo.ureport.entity.ReportFileEntity;
import com.yh.demo.ureport.mapper.ReportFileEntityMapper;
import lombok.Setter;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;


/**
 * 报表数据源适配类
 * @author yanghan
 * @date 2020/11/19
 */
@Component
@EnableConfigurationProperties(PostgresqlProperties.class)
public class PostgresqlProvider implements ReportProvider {

    @Resource
    private PostgresqlProperties postgresqlProperties;
    @Resource
    private ReportFileEntityMapper reportFileEntityMapper;

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
        ReportFileEntity reportFileEntity = reportFileEntityMapper.selectOne(
                Wrappers.<ReportFileEntity>lambdaQuery()
                        .eq(ReportFileEntity::getName, this.getCorrectName(fileName))
        );
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
        reportFileEntityMapper.delete(
                Wrappers.<ReportFileEntity>lambdaQuery()
                        .eq(ReportFileEntity::getName, this.getCorrectName(fileName))
        );
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
        QueryWrapper<ReportFileEntity> qw = new QueryWrapper<>();
        List<ReportFileEntity> list = reportFileEntityMapper.selectList(qw);
        List<ReportFile> reportList = new ArrayList<>();
        for (ReportFileEntity reportFileEntity : list) {
            // 封装成接口要求对象返回
            reportList.add(new ReportFile(reportFileEntity.getName(), reportFileEntity.getUpdateTime()));
        }
        return reportList;
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
        ReportFileEntity reportFileEntity = reportFileEntityMapper.selectOne(
                Wrappers.<ReportFileEntity>lambdaQuery()
                        .eq(ReportFileEntity::getName, fileName)
        );
        Date currentDate = new Date();
        if (reportFileEntity == null) {
            reportFileEntity = new ReportFileEntity();
            reportFileEntity.setName(fileName);
            reportFileEntity.setContent(content);
            reportFileEntity.setCreateTime(currentDate);
            reportFileEntity.setUpdateTime(currentDate);
            reportFileEntityMapper.insert(reportFileEntity);
        } else {
            reportFileEntity.setContent(content);
            reportFileEntity.setUpdateTime(currentDate);
            reportFileEntityMapper.updateById(reportFileEntity);
        }
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
        if (fileName.startsWith(postgresqlProperties.getPrefix())) {
            fileName = fileName.substring(postgresqlProperties.getPrefix().length());
        }
        return fileName;
    }

    @Override
    public String getName() {
        return postgresqlProperties.getName();
    }

    @Override
    public boolean disabled() {
        return postgresqlProperties.isDisabled();
    }

    @Override
    public String getPrefix() {
        return postgresqlProperties.getPrefix();
    }


}
