package com.ssafy.happyhouse.mapper;

import com.ssafy.happyhouse.entity.house.HouseDeal;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface HouseMapper {

    public List<HouseDeal> getHouseList(String dongCode);

    public List<HouseDeal> getHouseListByName(String houseName);

    public HouseDeal getHouseDetailByAptCode(Long aptCode);
}
