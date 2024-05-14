package com.ssafy.happyhouse.service;

import com.ssafy.happyhouse.entity.house.HouseDeal;
import com.ssafy.happyhouse.global.error.ErrorCode;
import com.ssafy.happyhouse.global.error.exception.EntityNotFoundException;
import com.ssafy.happyhouse.mapper.HouseMapper;
import com.ssafy.happyhouse.request.AddressName;
import com.ssafy.happyhouse.response.MapMarkerInfo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class HouseService {

    private final HouseMapper houseMapper;

    /**
     * 동 번호로 아파트 거래 내역을 조회
     * @param dongCode
     * @return
     */
    public List<HouseDeal> findHouseDealByDongCode(String dongCode){

        List<HouseDeal> findHouseList = houseMapper.getHouseList(dongCode);

        return findHouseList;
    }

    /**
     * 아파트 코드를 통해 아파트 상세 정보 조회
     * @param aptCode
     * @return
     */
    public List<HouseDeal> findHouseDealByAptCode(Long aptCode){

        List<HouseDeal> findHouseDeal = houseMapper.getHouseDetailByAptCode(aptCode);

        return findHouseDeal;
    }

    /**
     * 아파트명으로 아파트 정보를 검색
     * @param apartName
     * @return
     */
    public List<HouseDeal> findHouseDealByName(String apartName) {

        List<HouseDeal> findHouseList = houseMapper.getHouseListByName(apartName);

        return findHouseList;
    }

    /**
     * 주소를 기반으로 모든 아파트 정보를 조회
     * @param addressName
     * @return
     */
    public List<MapMarkerInfo> findApartListByAddressName(AddressName addressName) {
        List<MapMarkerInfo> apartListByAddressName = houseMapper.getApartListByAddressName(addressName);

        return apartListByAddressName;
    }
}
