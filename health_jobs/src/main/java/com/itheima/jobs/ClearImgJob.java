package com.itheima.jobs;

import com.itheima.constant.RedisConstant;
import com.itheima.entity.Result;
import com.itheima.utils.QiniuUtils;
import org.springframework.beans.factory.annotation.Autowired;
import redis.clients.jedis.JedisPool;

import java.util.Set;

/**
 * @Author：SatanCY
 * @Date：2024/9/5 21:52
 * 自定义Job，实现定时清理垃圾图片
 */
public class ClearImgJob {

    @Autowired
    private JedisPool jedisPool;

    //根据Redis中保存的两个set集合进行差值计算，获得垃圾图片名称集合
    public void clearImg() {
        System.out.println("已保存的："+jedisPool.getResource().smembers(RedisConstant.SET_MEAL_PIC_DB_RESOURCES));
        System.out.println("已上传的："+jedisPool.getResource().smembers(RedisConstant.SET_MEAL_PIC_RESOURCES));
        Set<String> set = jedisPool.getResource().sdiff(RedisConstant.SET_MEAL_PIC_RESOURCES, RedisConstant.SET_MEAL_PIC_DB_RESOURCES);
        System.out.println("未保存的："+set);
        if (set != null) {
            for (String picName : set) {
                System.out.println(picName);
                //删除七牛云服务器上的图片
                QiniuUtils.deleteFileFromQiniu(picName);
                //从Redis集合中删除图片名称
                jedisPool.getResource().srem(RedisConstant.SET_MEAL_PIC_RESOURCES, picName);
            }
        }
    }
}
