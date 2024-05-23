package com.ssafy.happyhouse.mapper;

import com.ssafy.happyhouse.entity.auction.Auction;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface AuctionMapper {

    List<Auction> getAuctionsByGuGunCode(String gugunCode);
}
