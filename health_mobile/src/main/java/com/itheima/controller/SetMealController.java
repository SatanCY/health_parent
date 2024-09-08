package com.itheima.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.itheima.constant.MessageConstant;
import com.itheima.entity.Result;
import com.itheima.pojo.CheckGroup;
import com.itheima.pojo.CheckItem;
import com.itheima.pojo.SetMeal;
import com.itheima.service.CheckGroupService;
import com.itheima.service.CheckItemService;
import com.itheima.service.SetMealService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

/**
 * @Author：SatanCY
 * @Date：2024/9/8 12:43
 */
@RestController
@RequestMapping("/setmeal")
public class SetMealController {

    @Reference
    private SetMealService setMealService;

    @Reference
    private CheckGroupService checkGroupService;

    @Reference
    private CheckItemService checkItemService;

    @GetMapping("/getAllSetMeal")
    public Result getAllSetMeal() {
        try {
            List<SetMeal> setMealList = setMealService.findAll();
            return new Result(true, MessageConstant.GET_SETMEAL_LIST_SUCCESS, setMealList);
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false, MessageConstant.GET_SETMEAL_LIST_FAIL);
        }
    }

    @PostMapping("/findById")
    public Result findById(Integer id) {
        try {
            SetMeal setMeal = setMealService.findById(id);
            List<CheckGroup> checkGroupList = new ArrayList<>();
            List<Integer> checkGroupIds = setMealService.findCheckGroupIdsBySetMealId(id);
            if (checkGroupIds!=null && checkGroupIds.size()>0) {
                for (Integer checkGroupId : checkGroupIds) {
                    CheckGroup checkGroup = checkGroupService.findById(checkGroupId);
                    List<CheckItem> checkItemList = new ArrayList<>();
                    List<Integer> checkItemIds = checkGroupService.findCheckItemIdsByCheckGroupId(checkGroupId);
                    if (checkItemIds!=null && checkItemIds.size()>0) {
                        for (Integer checkItemId : checkItemIds) {
                            CheckItem checkItem = checkItemService.findById(checkItemId);
                            checkItemList.add(checkItem);
                        }
                        checkGroup.setCheckItems(checkItemList);
                        checkGroupList.add(checkGroup);
                    }
                }
                setMeal.setCheckGroups(checkGroupList);
            }
            return new Result(true,MessageConstant.QUERY_SETMEAL_SUCCESS,setMeal);
        } catch (Exception e) {
            return new Result(false,MessageConstant.QUERY_SETMEAL_FAIL);
        }
    }
}
