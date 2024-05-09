package com.ssafy.happyhouse.global.handler;

import com.ssafy.happyhouse.entity.member.constant.Role;
import org.apache.ibatis.type.JdbcType;
import org.apache.ibatis.type.MappedTypes;
import org.apache.ibatis.type.TypeHandler;

import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

@MappedTypes(Role.class)
public class RoleTypeHandler implements TypeHandler<Role>{
    @Override
    public void setParameter(PreparedStatement ps, int i, Role parameter, JdbcType jdbcType) throws SQLException {

        ps.setString(i, parameter.getRoleType());
    }

    @Override
    public Role getResult(ResultSet rs, String columnName) throws SQLException {

        return Role.from(rs.getString(columnName));
    }

    @Override
    public Role getResult(ResultSet rs, int columnIndex) throws SQLException {

        return Role.from(rs.getString(columnIndex));
    }

    @Override
    public Role getResult(CallableStatement cs, int columnIndex) throws SQLException {

        return Role.from(cs.getString(columnIndex));
    }
}
