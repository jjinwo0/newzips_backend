package com.ssafy.happyhouse.entity.auction;

import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Auction {

    private int id;
    private String court;
    private String productUsage;
    private String location;
    private String appraisalValue;
    private String minimumSalePrice;
    private String saleDate;
    private String status;
    private String sido;
    private String gugun;
    private String dong;
    private String dongCode;
    private String lng;
    private String lat;

}
