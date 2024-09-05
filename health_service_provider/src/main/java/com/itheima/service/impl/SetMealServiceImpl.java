package com.itheima.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.itheima.constant.RedisConstant;
import com.itheima.dao.SetMealDao;
import com.itheima.entity.PageResult;
import com.itheima.entity.QueryPageBean;
import com.itheima.pojo.SetMeal;
import com.itheima.service.SetMealService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import redis.clients.jedis.JedisPool;

import java.util.HashMap;
import java.util.List;
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

    @Autowired
    private JedisPool jedisPool;

    @Override
    public void add(SetMeal setmeal, Integer[] checkGroupIds) {
        setMealDao.add(setmeal);
        if (checkGroupIds != null && checkGroupIds.length > 0) {
            setSetMealAndCheckGroup(setmeal.getId(), checkGroupIds);
        }
        //将图片名称保存到Redis
        savePic2Redis(setmeal.getImg());
    }

    @Override
    public PageResult pageQuery(QueryPageBean queryPageBean) {
        Integer currentPage = queryPageBean.getCurrentPage();
        Integer pageSize = queryPageBean.getPageSize();
        String queryString = queryPageBean.getQueryString();
        PageHelper.startPage(currentPage, pageSize);
        Page<SetMeal> page = setMealDao.selectByCondition(queryString);
        long total = page.getTotal();
        List<SetMeal> rows = page.getResult();
        return new PageResult(total, rows);
    }

    @Override
    public void delete(Integer id) {
        Long count = setMealDao.selectCountBySetMealId(id);
        if (count > 0) {
            throw new RuntimeException("当前套餐被引用，不能删除");
        } else {
            setMealDao.deleteAssociation(id);
            setMealDao.delete(id);
        }
    }

    @Override
    public SetMeal findById(Integer id) {
        SetMeal setMeal = setMealDao.findById(id);
        return setMeal;
    }

    @Override
    public List<Integer> findCheckGroupIdsBySetMealId(Integer id) {
        List<Integer> checkGroupIds = setMealDao.findCheckGroupIdsBySetMealId(id);
        return checkGroupIds;
    }

    @Override
    public void edit(SetMeal setMeal, Integer[] checkGroupIds) {
        setMealDao.edit(setMeal);
        setMealDao.deleteAssociation(setMeal.getId());
        setSetMealAndCheckGroup(setMeal.getId(),checkGroupIds);
    }

    @Override
    public String findImage(Integer id) {
        String image = setMealDao.findImage(id);
        return image;
    }

    private void savePic2Redis(String pic) {
        jedisPool.getResource().sadd(RedisConstant.SET_MEAL_PIC_DB_RESOURCES, pic);
    }

    public void setSetMealAndCheckGroup(Integer id, Integer[] checkGroupIds) {
        for (Integer checkGroupId : checkGroupIds) {
            Map<String, Integer> map = new HashMap<>();
            map.put("setmeal_id", id);
            map.put("checkgroup_id", checkGroupId);
            setMealDao.setSetMealAndCheckGroup(map);
        }
    }
}
