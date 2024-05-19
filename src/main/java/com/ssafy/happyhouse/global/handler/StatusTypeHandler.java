package com.ssafy.happyhouse.global.handler;

import com.ssafy.happyhouse.entity.chat.Status;
import org.apache.ibatis.type.JdbcType;
import org.apache.ibatis.type.MappedTypes;
import org.apache.ibatis.type.TypeHandler;

import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

@MappedTypes(Status.class)
public class StatusTypeHandler implements TypeHandler<Status> {

    @Override
    public void setParameter(PreparedStatement ps, int i, Status parameter, JdbcType jdbcType) throws SQLException {

        ps.setString(i, parameter.getStatus());
    }

    @Override
    public Status getResult(ResultSet rs, String columnName) throws SQLException {

        return Status.from(rs.getString(columnName));
    }

    @Override
    public Status getResult(ResultSet rs, int columnIndex) throws SQLException {

        return Status.from(rs.getString(columnIndex));
    }

    @Override
    public Status getResult(CallableStatement cs, int columnIndex) throws SQLException {

        return Status.from(cs.getString(columnIndex));
    }
}
