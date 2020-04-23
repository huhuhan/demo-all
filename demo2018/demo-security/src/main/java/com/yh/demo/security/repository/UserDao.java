package com.yh.demo.security.repository;

import com.yh.demo.security.bean.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;


public interface UserDao extends JpaRepository<User, String> {

    List<User> findByUserName(String userName);

}