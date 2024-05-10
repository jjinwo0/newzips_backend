package com.ssafy.happyhouse.controller.house;

import com.ssafy.happyhouse.entity.house.HouseDeal;
import com.ssafy.happyhouse.service.HouseService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collections;
import java.util.List;

@RestController
@RequestMapping("/house")
@RequiredArgsConstructor
public class HouseController {

    private final HouseService houseService;

    @GetMapping("/list/code/{code}")
    public ResponseEntity<?> findApartListByDongCode(@PathVariable("code") String dongCode){

        List<HouseDeal> findList = houseService.findHouseDealByDongCode(dongCode);

        if (findList.isEmpty())
            return ResponseEntity.ok(Collections.emptyList());

        return ResponseEntity.ok(findList);
    }

    @GetMapping("/detail/{code}")
    public ResponseEntity<?> findDetail(@PathVariable("code") Long aptCode){

        List<HouseDeal> findDetail = houseService.findHouseDealByAptCode(aptCode);

        if (findDetail.isEmpty())
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);

        return ResponseEntity.ok(findDetail);
    }

    @GetMapping("/list/name/{name}")
    public ResponseEntity<?> findApartListByName(@PathVariable("name") String apartName) {

        List<HouseDeal> findList = houseService.findHouseDealByName(apartName);

        if (findList.isEmpty())
            return ResponseEntity.ok(Collections.emptyList());

        return ResponseEntity.ok(findList);
    }
}
