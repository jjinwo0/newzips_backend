package com.ssafy.happyhouse.mapper;

import com.ssafy.happyhouse.entity.house.HouseDeal;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Map;

@Mapper
public interface HouseMapper {

    public List<HouseDeal> getHouseList(String dongCode);

    public List<HouseDeal> getHouseListByName(String apartmentName);

    public List<HouseDeal> getHouseDetailByAptCode(Long aptCode);

    public List<HouseDeal> getHouseListByLatLng(Map<String, Object> map);
}
