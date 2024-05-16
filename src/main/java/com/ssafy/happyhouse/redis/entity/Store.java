package com.ssafy.happyhouse.redis.entity;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.redis.core.RedisHash;
import org.springframework.security.access.annotation.Secured;

@Data
public class Store {
    @Id
    private String storeCode;

    private String storeName;

    private String storeTypeCode;

    private String storeTypeName;

    private String dongName;

    private String dongCode;

    private String jibun;

    private String doro;

    private String lng;

    private String lat;
}
