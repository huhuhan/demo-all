package com.yh.demo.aop.service.impl;

import com.yh.demo.aop.service.ToDoService;
import org.springframework.stereotype.Service;

/**
 * @author yanghan
 * @date 2021/5/7
 */
@Service
public class ToDoServiceImpl implements ToDoService {
    @Override
    public String toSelect() {
        System.out.println("Enter toSelect");
        return "[select return value]";
    }

    @Override
    public void toInsert() {
        System.out.println("Enter toInsert");
    }

    @Override
    public void toUpdate() {
        System.out.println("Enter toUpdate");
    }

    @Override
    public void toDelete() {
        System.out.println("Enter toDelete");
    }
}
