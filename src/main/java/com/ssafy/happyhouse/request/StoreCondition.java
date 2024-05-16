package com.ssafy.happyhouse.request;

import lombok.Data;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Data
public class StoreCondition extends AddressName {
    private String selectedOption;

    public List<String> selectedOptions() {
        return Arrays.stream(this.selectedOption.split(",")).toList();
    }
}
