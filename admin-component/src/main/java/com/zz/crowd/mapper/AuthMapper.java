package com.zz.crowd.mapper;

import com.zz.crowd.entity.Auth;
import com.zz.crowd.entity.AuthExample;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface AuthMapper {
    int countByExample(AuthExample example);

    int deleteByExample(AuthExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(Auth record);

    int insertSelective(Auth record);

    List<Auth> selectByExample(AuthExample example);

    Auth selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") Auth record, @Param("example") AuthExample example);

    int updateByExample(@Param("record") Auth record, @Param("example") AuthExample example);

    int updateByPrimaryKeySelective(Auth record);

    int updateByPrimaryKey(Auth record);

    List<Integer> selectAuthByRoleId(Integer roleId);

    void deleteByRoleId(Integer roleId);

    void insertRoleIdAndAuthId(@Param("roleId") Integer roleId, @Param("authIdArray") List<Integer> authIdArray);

    List<String> selectAuthByAdminId(Integer adminId);
}