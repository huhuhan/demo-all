package com.yh.demo.ureport.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.bstek.ureport.provider.report.ReportFile;
import com.yh.demo.ureport.entity.ReportFileEntity;

import java.util.List;

public interface IReportFileEntityService extends IService<ReportFileEntity> {

    /**
     * 保存或更新
     *
     * @param name    文件名
     * @param content 报表内容
     */
    void saveOrUpdateByName(String name, String content);

    /**
     * 报表列表，按接口对象返回
     *
     * @return {@link ReportFile}
     */
    List<ReportFile> getReportFiles();


    /**
     * 删除
     *
     * @param name 文件名
     */
    void deleteByName(String name);

    /**
     * 获取报表详情
     *
     * @param name 文件名
     * @return {@link ReportFileEntity}
     */
    ReportFileEntity getByName(String name);
}
