package com.ssafy.happyhouse.mapper;

import com.ssafy.happyhouse.redis.entity.Store;
import com.ssafy.happyhouse.request.AddressName;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface StoreMapper {

    List<Store> findALl();

    String findDongCodeByDongName(AddressName addressName);
}
