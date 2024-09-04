package com.itheima.service;

import com.itheima.entity.PageResult;
import com.itheima.entity.QueryPageBean;
import com.itheima.pojo.CheckGroup;

import java.util.List;

/**
 * @Author：蔡洋
 * @Date：2024/9/3 13:58
 */
public interface CheckGroupService {
    PageResult pageQuery(QueryPageBean queryPageBean);

    void add(CheckGroup checkGroup, Integer[] checkItemIds);

    CheckGroup findById(Integer id);

    List<Integer> findCheckItemIdsByCheckGroupId(Integer id);

    void edit(CheckGroup checkGroup, Integer[] checkItemIds);
}
