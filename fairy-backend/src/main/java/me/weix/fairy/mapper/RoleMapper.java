package me.weix.fairy.mapper;

import me.weix.fairy.pojo.Role;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface RoleMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(Role record);

    int insertSelective(Role record);

    Role selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(Role record);

    int updateByPrimaryKey(Role record);

    /**
     * 在下面添加自定义方法
     */

    List<Role> getRolesByUserId(@Param("userId") Integer userId);
}