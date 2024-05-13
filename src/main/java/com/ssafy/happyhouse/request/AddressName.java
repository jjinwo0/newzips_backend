package com.ssafy.happyhouse.request;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class AddressName {

    @NotBlank(message = "시/도 이름을 입력해주세요.")
    private String sidoName;

    @NotBlank(message = "구/군 이름을 입력해주세요.")
    private String gugunName;

    @NotBlank(message = "동 이름을 입력해주세요.")
    private String dongName;
}
