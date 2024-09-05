package com.itheima.dao;

import com.github.pagehelper.Page;
import com.itheima.pojo.SetMeal;

import java.util.List;
import java.util.Map;

/**
 * @Author：SatanCY
 * @Date：2024/9/4 22:20
 */
public interface SetMealDao {
    void add(SetMeal setmeal);

    void setSetMealAndCheckGroup(Map<String, Integer> map);

    Page<SetMeal> selectByCondition(String queryString);

    void deleteAssociation(Integer id);

    void delete(Integer id);

    Long selectCountBySetMealId(Integer id);

    SetMeal findById(Integer id);

    List<Integer> findCheckGroupIdsBySetMealId(Integer id);

    void edit(SetMeal setMeal);

    String findImage(Integer id);
}
