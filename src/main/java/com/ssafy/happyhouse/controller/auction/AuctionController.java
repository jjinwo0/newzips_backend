package com.ssafy.happyhouse.controller.auction;

import com.ssafy.happyhouse.entity.auction.Auction;
import com.ssafy.happyhouse.request.AddressName;
import com.ssafy.happyhouse.service.AuctionService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;

@RestController
@RequestMapping("auction")
@RequiredArgsConstructor
public class AuctionController {

    private final AuctionService auctionService;

    @GetMapping("/list")
    public ResponseEntity<?> findAuctionsByDongCode(@ModelAttribute AddressName addressName) {

        List<Auction> auctions = auctionService.getAuctionsByDongCode(addressName);
        return ResponseEntity.ok(auctions);

    }

}
