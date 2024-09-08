package com.itheima.service;

import com.itheima.entity.PageResult;
import com.itheima.entity.QueryPageBean;
import com.itheima.pojo.SetMeal;

import java.util.List;

/**
 * @Author：SatanCY
 * @Date：2024/9/4 21:39
 */
public interface SetMealService {
    void add(SetMeal setmeal, Integer[] checkGroupIds);

    PageResult pageQuery(QueryPageBean queryPageBean);

    void delete(Integer setMealId);

    SetMeal findById(Integer id);

    List<Integer> findCheckGroupIdsBySetMealId(Integer id);

    void edit(SetMeal setMeal, Integer[] checkGroupIds);

    String findImage(Integer id);

    List<SetMeal> findAll();

}
