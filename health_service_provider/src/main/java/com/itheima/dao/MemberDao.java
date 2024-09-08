package com.itheima.dao;

import com.itheima.pojo.Member;

/**
 * @Author：SatanCY
 * @Date：2024/9/8 17:55
 */
public interface MemberDao {
    Member findByTelephone(String telephone);

    void add(Member member);
}
