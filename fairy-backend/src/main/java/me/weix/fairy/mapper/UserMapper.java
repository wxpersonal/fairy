package me.weix.fairy.mapper;

import me.weix.fairy.pojo.User;
import org.apache.ibatis.annotations.Param;

public interface UserMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(User record);

    int insertSelective(User record);

    User selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(User record);

    int updateByPrimaryKey(User record);

    /**
     * 在下面添加自定义方法
     */
    User getUserByMobile(@Param("mobile") String mobile);

    User getUserByEmail(@Param("email") String email);

    User getUserByUsername(@Param("username") String username);
}