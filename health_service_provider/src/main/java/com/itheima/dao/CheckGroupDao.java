package com.itheima.dao;

import com.github.pagehelper.Page;
import com.itheima.pojo.CheckGroup;

import java.util.List;
import java.util.Map;

/**
 * @Author：SatanCY
 * @Date：2024/9/3 13:58
 */
public interface CheckGroupDao {
    Page<CheckGroup> selectByCondition(String queryString);

    void add(CheckGroup checkGroup);

    void setCheckGroupAndCheckItem(Map<String, Integer> map);

    List<Integer> findCheckItemIdsByCheckGroupId(Integer id);

    CheckGroup findById(Integer id);

    void edit(CheckGroup checkGroup);

    void deleteAssociation(Integer id);
}
