package com.yh.demo.ureport.service.impl;


import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.bstek.ureport.provider.report.ReportFile;
import com.yh.demo.ureport.entity.ReportFileEntity;
import com.yh.demo.ureport.mapper.ReportFileEntityMapper;
import com.yh.demo.ureport.service.IReportFileEntityService;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class ReportFileEntityServiceImpl extends ServiceImpl<ReportFileEntityMapper, ReportFileEntity> implements IReportFileEntityService {


    @Override
    public void saveOrUpdateByName(String name, String content) {
        ReportFileEntity reportFileEntity = this.getOne(
                Wrappers.<ReportFileEntity>lambdaQuery()
                        .eq(ReportFileEntity::getName, name)
        );
        Date currentDate = new Date();
        if (reportFileEntity == null) {
            reportFileEntity = new ReportFileEntity();
            reportFileEntity.setName(name);
            reportFileEntity.setContent(content);
            reportFileEntity.setCreateTime(currentDate);
            reportFileEntity.setUpdateTime(currentDate);
            this.save(reportFileEntity);
        } else {
            reportFileEntity.setContent(content);
            reportFileEntity.setUpdateTime(currentDate);
            this.updateById(reportFileEntity);
        }
    }

    @Override
    public List<ReportFile> getReportFiles() {
        List<ReportFileEntity> list = this.list();
        List<ReportFile> reportList = new ArrayList<>();
        for (ReportFileEntity reportFileEntity : list) {
            reportList.add(new ReportFile(reportFileEntity.getName(), reportFileEntity.getUpdateTime()));
        }
        return reportList;
    }

    @Override
    public void deleteByName(String name) {
        this.remove(Wrappers.<ReportFileEntity>lambdaQuery()
                .eq(ReportFileEntity::getName, name)
        );
    }

    @Override
    public ReportFileEntity getByName(String name) {
        return this.getOne(Wrappers.<ReportFileEntity>lambdaQuery()
                .eq(ReportFileEntity::getName, name)
        );
    }
}
