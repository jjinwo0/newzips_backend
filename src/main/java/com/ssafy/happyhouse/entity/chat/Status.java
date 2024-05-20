package com.ssafy.happyhouse.entity.chat;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Getter
@RequiredArgsConstructor
public enum Status {

    LIVE("LIVE"), EXPIRED("EXPIRED");

    private final String status;

    public static Status from(String status) {

        return Status.valueOf(status.toUpperCase());
    }

    public static boolean isStatus(String status) {

        List<Status> list = Arrays.stream(Status.values())
                .filter(st -> st.name().equals(status))
                .collect(Collectors.toList());

        return list.size() != 0;
    }
}
