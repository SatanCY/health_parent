package com.itheima.service;

import com.itheima.entity.Result;

import java.util.Map;

/**
 * @Author：SatanCY
 * @Date：2024/9/8 17:24
 */
public interface OrderService {
    Result order(Map map) throws Exception;

    Map findById(Integer id) throws Exception;
}
