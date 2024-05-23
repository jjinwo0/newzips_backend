package com.ssafy.happyhouse.service;

import com.ssafy.happyhouse.entity.auction.Auction;
import com.ssafy.happyhouse.mapper.AuctionMapper;
import com.ssafy.happyhouse.mapper.StoreMapper;
import com.ssafy.happyhouse.request.AddressName;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;

@Service
@RequiredArgsConstructor
public class AuctionService {

    private final StoreMapper storeMapper;

    private final AuctionMapper auctionMapper;

    public List<Auction> getAuctionsByGuGunCode(AddressName addressName) {

        String dongCode = storeMapper.findDongCodeByDongName(addressName);
        String gugunCode = dongCode.substring(0,5) + "00000";

        List<Auction> tmp = auctionMapper.getAuctionsByGuGunCode(gugunCode);
        return tmp;
    }

    public Auction getAuctionById(Long id) {
        return auctionMapper.getAuctionById(id);
    }
}
