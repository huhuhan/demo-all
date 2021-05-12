package com.yh.demo.druid.sys.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.yh.demo.druid.sys.model.domain.Dict;

import java.util.List;

/**
 * <p>
 * 系统-字典表 服务类
 * </p>
 *
 * @author yanghan
 * @since 2021-05-06
 */
public interface IDictService extends IService<Dict> {

    List<Dict> getList();

    List<Dict> getListByOneDB();

    List<Dict> getListByTwoDB();
}
