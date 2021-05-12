package com.yh.demo.druid.sys.rest;


import com.yh.demo.druid.sys.model.domain.Dict;
import com.yh.demo.druid.sys.service.IDictService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * <p>
 * 系统-字典表 前端控制器
 * </p>
 *
 * @author yanghan
 * @since 2021-05-06
 */
@RestController
@RequestMapping("/sys/dict")
public class DictController {

    @Autowired
    private IDictService iDictService;

    @GetMapping("/list")
    public List<Dict> getList() {
        return iDictService.getList();
    }

    @GetMapping("/one/list")
    public List<Dict> getListByOneDB() {
        return iDictService.getListByOneDB();
    }

    @GetMapping("/two/list")
    public List<Dict> getListByTwoDB() {
        return iDictService.getListByTwoDB();
    }

}

