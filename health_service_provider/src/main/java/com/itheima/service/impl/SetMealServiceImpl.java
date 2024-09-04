package com.itheima.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.itheima.dao.SetMealDao;
import com.itheima.pojo.SetMeal;
import com.itheima.service.SetMealService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.Map;

/**
 * @Author：SatanCY
 * @Date：2024/9/4 21:40
 */
@Service(interfaceClass = SetMealService.class)
@Transactional
public class SetMealServiceImpl implements SetMealService {

    @Autowired
    private SetMealDao setMealDao;

    @Override
    public void add(SetMeal setmeal, Integer[] checkGroupIds) {
        setMealDao.add(setmeal);
        setSetMealAndCheckGroup(setmeal.getId(), checkGroupIds);
    }

    public void setSetMealAndCheckGroup(Integer id, Integer[] checkGroupIds) {
        if (checkGroupIds != null && checkGroupIds.length > 0) {
            for (Integer checkGroupId : checkGroupIds) {
                Map<String, Integer> map = new HashMap<>();
                map.put("setmeal_id", id);
                map.put("checkgroup_id", checkGroupId);
                setMealDao.setSetMealAndCheckGroup(map);
            }
        }
    }
}
