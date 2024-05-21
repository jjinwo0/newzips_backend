package com.ssafy.happyhouse.entity.auction;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Auction {

    int id;
    String court;
    String usage;
    String location;
    String appraisalValue;
    String minimumSalePrice;
    String saleDate;
    String status;

}
