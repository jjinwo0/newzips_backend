package com.ssafy.happyhouse.service;

import com.ssafy.happyhouse.entity.house.HouseDeal;
import com.ssafy.happyhouse.mapper.HouseMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class HouseService {

    private final HouseMapper houseMapper;

    public List<HouseDeal> findHouseDealByName(String apartName) {

        List<HouseDeal> findHouseList = houseMapper.getHouseListByName(apartName);

        if (findHouseList.isEmpty())
            throw new RuntimeException("Not Found List");

        return findHouseList;
    }

    public List<HouseDeal> findHouseDealByDongCode(String dongCode){

        List<HouseDeal> findHouseList = houseMapper.getHouseList(dongCode);

        if (findHouseList.isEmpty())
            throw new RuntimeException("Not Found List");

        return findHouseList;
    }

    public HouseDeal findHouseDealByAptCode(Long aptCode){

        HouseDeal findHouseDeal = houseMapper.getHouseDetailByAptCode(aptCode);

        if (findHouseDeal == null)
            throw new RuntimeException("Not Found List");

        return findHouseDeal;
    }
}
