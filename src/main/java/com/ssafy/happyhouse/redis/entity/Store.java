package com.ssafy.happyhouse.redis.entity;

import lombok.Data;
import org.springframework.data.annotation.Id;

@Data
public class Store {
    @Id
    private String storeCode;

    private String storeName;

    private String mainCategoryCode;

    private String mainCategoryName;

    private String subCategoryCode;

    private String subCategoryName;

    private String dongCode;

    private String dongName;

    private String doro;

    private String lng;

    private String lat;
}
