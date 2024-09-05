package com.itheima.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.itheima.constant.MessageConstant;
import com.itheima.constant.RedisConstant;
import com.itheima.entity.PageResult;
import com.itheima.entity.QueryPageBean;
import com.itheima.entity.Result;
import com.itheima.pojo.SetMeal;
import com.itheima.service.SetMealService;
import com.itheima.utils.QiniuUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import redis.clients.jedis.JedisPool;

import java.util.List;
import java.util.UUID;

/**
 * @Author：SatanCY
 * @Date：2024/9/4 21:37
 */
@RestController
@RequestMapping("/setmeal")
public class SetMealController {

    @Reference
    private SetMealService setMealService;

    @Autowired
    private JedisPool jedisPool;

    @RequestMapping("/upload")
    public Result upload(@RequestParam("imgFile") MultipartFile imgFile) {
        try {
            //获取原始文件名
            String originalFilename = imgFile.getOriginalFilename();
            int lastIndexOf = originalFilename.lastIndexOf(".");
            //获取文件后缀
            String suffix = originalFilename.substring(lastIndexOf - 1);
            //使用UUID随机产生文件名称，防止同名文件覆盖
            String fileName = UUID.randomUUID().toString() + suffix;
            QiniuUtils.upload2Qiniu(imgFile.getBytes(), fileName);
            //将上传图片名称存入Redis，基于Redis的Set集合存储
            jedisPool.getResource().sadd(RedisConstant.SET_MEAL_PIC_RESOURCES, fileName);
            //图片上传成功
            return new Result(true, MessageConstant.PIC_UPLOAD_SUCCESS, fileName);
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false, MessageConstant.PIC_UPLOAD_FAIL);
        }
    }

    @RequestMapping("/add")
    public Result add(@RequestBody SetMeal setmeal, @RequestParam("checkgroupIds") Integer[] checkGroupIds) {
        try {
            setMealService.add(setmeal, checkGroupIds);
        } catch (Exception e) {
            return new Result(false, MessageConstant.ADD_SETMEAL_FAIL);
        }
        return new Result(true, MessageConstant.ADD_SETMEAL_SUCCESS);
    }

    @RequestMapping("/findPage")
    public PageResult findPage(@RequestBody QueryPageBean queryPageBean) {
        PageResult pageResult = setMealService.pageQuery(queryPageBean);
        return pageResult;
    }

    @RequestMapping("/findById")
    public Result findById(Integer id) {
        try {
            SetMeal setMeal = setMealService.findById(id);
            return new Result(true, MessageConstant.QUERY_SETMEAL_SUCCESS,setMeal);
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false, MessageConstant.QUERY_SETMEAL_FAIL);
        }
    }

    @RequestMapping("/findCheckGroupIdsBySetMealId")
    public Result findCheckGroupIdsBySetMealId(Integer id) {
        try {
            List<Integer> checkGroupIds = setMealService.findCheckGroupIdsBySetMealId(id);
            return new Result(true, MessageConstant.QUERY_CHECKGROUP_SUCCESS, checkGroupIds);
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false, MessageConstant.QUERY_CHECKGROUP_FAIL);
        }
    }

    @RequestMapping("/edit")
    public Result edit(@RequestBody SetMeal setMeal,@RequestParam("checkGroupIds") Integer[] checkGroupIds) {
        try {
            setMealService.edit(setMeal,checkGroupIds);
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false,MessageConstant.EDIT_SETMEAL_FAIL);
        }
        return new Result(true,MessageConstant.EDIT_SETMEAL_SUCCESS);
    }

    @RequestMapping("/delete")
    public Result delete(@RequestParam("setMealId") Integer id) {
        try {
            setMealService.delete(id);
        } catch (RuntimeException re) {
            System.out.println(re.getMessage());
            return new Result(false, re.getMessage());
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false, MessageConstant.DELETE_SETMEAL_FAIL);
        }
        return new Result(true, MessageConstant.DELETE_SETMEAL_SUCCESS);
    }

    @RequestMapping("/findImage")
    public Result findImage(Integer id) {
        String image = setMealService.findImage(id);
        if (image!=null && image.length()>0) {
            return new Result(true,MessageConstant.QUERY_IMAGE_SUCCESS,image);
        }
        return new Result(false,MessageConstant.QUERY_IMAGE_FAIL);
    }
}
