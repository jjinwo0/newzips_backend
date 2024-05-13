package com.ssafy.happyhouse.mapper;

import com.ssafy.happyhouse.entity.house.HouseDeal;
import com.ssafy.happyhouse.request.AddressName;
import com.ssafy.happyhouse.response.MapMarkerInfo;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Map;

@Mapper
public interface HouseMapper {

    // 동 번호로 아파트 거래 내역을 조회
    public List<HouseDeal> getHouseList(String dongCode);

    // 아파트명으로 아파트 정보를 검색
    public List<HouseDeal> getHouseListByName(String apartmentName);

    // 아파트 코드를 통해 아파트 상세 정보 조회
    public List<HouseDeal> getHouseDetailByAptCode(Long aptCode);

    // 위도 경도를 기반으로 아파트 리스트 조회
    public List<HouseDeal> getHouseListByLatLng(Map<String, Object> map);

    // 주소를 기반으로 모든 아파트 정보를 조회
    List<MapMarkerInfo> getApartListByAddressName(AddressName addressName);
}
