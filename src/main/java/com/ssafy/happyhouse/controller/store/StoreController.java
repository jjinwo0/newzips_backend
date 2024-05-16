package com.ssafy.happyhouse.controller.store;

import com.ssafy.happyhouse.redis.entity.Store;
import com.ssafy.happyhouse.request.AddressName;
import com.ssafy.happyhouse.service.StoreService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/store")
@RequiredArgsConstructor
public class StoreController {

    private final StoreService storeService;

    @GetMapping("/list")
    public ResponseEntity<?> findStoreListByAddressName(@ModelAttribute AddressName addressName) {
        List<Store> findList = storeService.getStoresByDong(addressName.getDongName());
        System.out.println(findList.size() + "       ========================================");
        return ResponseEntity.ok(findList);
    }

}
