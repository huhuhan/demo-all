package com.yh.demo.druid.sys.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.yh.demo.db.annotation.DS;
import com.yh.demo.druid.sys.mapper.DictMapper;
import com.yh.demo.druid.sys.model.domain.Dict;
import com.yh.demo.druid.sys.service.IDictService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 * 系统-字典表 服务实现类
 * </p>
 *
 * @author yanghan
 * @since 2021-05-06
 */
@Service
public class DictServiceImpl extends ServiceImpl<DictMapper, Dict> implements IDictService {

    /** 注入jdbcTemplateOne */
    private final JdbcTemplate jdbcTemplateOne;
    /** 注入默认的JdbcTemplate */
    private final JdbcTemplate jdbcTemplate;

    public DictServiceImpl(@Qualifier("jdbcTemplateOne") JdbcTemplate jdbcTemplateOne, JdbcTemplate jdbcTemplate) {
        this.jdbcTemplateOne = jdbcTemplateOne;
        this.jdbcTemplate = jdbcTemplate;
    }
    
    private static final RowMapper<Dict> ROW_MAPPER = (rs, rowNum) -> {
        Dict dict = new Dict();
        dict.setId(rs.getInt("id_"));
        dict.setKey(rs.getString("key_"));
        dict.setCode(rs.getString("code_"));
        dict.setName(rs.getString("name_"));
        return dict;
    };


    @DS("dataSourceOne")
//    @DS("dataSourceTwo")
    @Override
    public List<Dict> getList() {
        return super.list();
    }

    @Override
    public List<Dict> getListByOneDB() {
        return jdbcTemplateOne.query("SELECT * FROM sys_dict", ROW_MAPPER);
    }


    @DS("dataSourceTwo")
    @Override
    public List<Dict> getListByTwoDB() {
        return jdbcTemplate.query("SELECT * FROM sys_dict", ROW_MAPPER);
    }
}
