package com.itheima.dao;

import com.itheima.pojo.SetMeal;

import java.util.Map;

/**
 * @Author：SatanCY
 * @Date：2024/9/4 22:20
 */
public interface SetMealDao {
    void add(SetMeal setmeal);

    void setSetMealAndCheckGroup(Map<String, Integer> map);
}
