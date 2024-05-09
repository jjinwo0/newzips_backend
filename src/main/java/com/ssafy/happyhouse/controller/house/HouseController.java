package com.ssafy.happyhouse.controller.house;

import com.ssafy.happyhouse.entity.house.HouseDeal;
import com.ssafy.happyhouse.service.HouseService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/house")
@RequiredArgsConstructor
public class HouseController {

    private final HouseService houseService;

    @GetMapping("/list/code/{code}")
    public ResponseEntity<?> findApartListByDongCode(@PathVariable("code") String dongCode){

        List<HouseDeal> findList = houseService.findHouseDealByDongCode(dongCode);

        return ResponseEntity.ok(findList);
    }

    @GetMapping("/detail/{code}")
    public ResponseEntity<HouseDeal> findDetail(@PathVariable("code") Long aptCode){

        HouseDeal findDetail = houseService.findHouseDealByAptCode(aptCode);

        return ResponseEntity.ok(findDetail);
    }

    @GetMapping("/list/name/{name}")
    public ResponseEntity<?> findApartListByName(@PathVariable("name") String apartName) {

        List<HouseDeal> findList = houseService.findHouseDealByName(apartName);

        return ResponseEntity.ok(findList);
    }
}
