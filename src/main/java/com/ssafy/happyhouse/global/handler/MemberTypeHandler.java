package com.ssafy.happyhouse.global.handler;

import com.ssafy.happyhouse.entity.member.constant.MemberType;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.type.JdbcType;
import org.apache.ibatis.type.MappedTypes;
import org.apache.ibatis.type.TypeHandler;

import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

@MappedTypes(MemberType.class)
public class MemberTypeHandler implements TypeHandler<MemberType> {

    @Override
    public void setParameter(PreparedStatement ps, int i, MemberType parameter, JdbcType jdbcType) throws SQLException {

        ps.setString(i, parameter.getMemberType());
    }

    @Override
    public MemberType getResult(ResultSet rs, String columnName) throws SQLException {

        return MemberType.from(rs.getString(columnName));
    }

    @Override
    public MemberType getResult(ResultSet rs, int columnIndex) throws SQLException {

        return MemberType.from(rs.getString(columnIndex));
    }

    @Override
    public MemberType getResult(CallableStatement cs, int columnIndex) throws SQLException {

        return MemberType.from(cs.getString(columnIndex));
    }
}
