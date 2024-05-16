package com.ssafy.happyhouse.controller.store;

import com.ssafy.happyhouse.redis.entity.Store;
import com.ssafy.happyhouse.request.AddressName;
import com.ssafy.happyhouse.request.StoreCondition;
import com.ssafy.happyhouse.service.StoreService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/store")
@RequiredArgsConstructor
@Slf4j
public class StoreController {

    private final StoreService storeService;

    @GetMapping("/list")
    public ResponseEntity<?> findStoreListByAddressName(@ModelAttribute StoreCondition storeCondition) {

        log.info(storeCondition.toString());
        //List<Store> findList = storeService.getStoresByDong(addressName);

        return ResponseEntity.ok(null);
    }

}
