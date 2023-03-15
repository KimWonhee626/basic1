package com.ll.basic1.base.rsData;

import lombok.AllArgsConstructor;
import lombok.Getter;

// ** RsData => boolean + 알파

@AllArgsConstructor
@Getter
public class RsData {
    private final String resultCode;
    private final String msg;

    public static RsData of(String resultCode, String msg) {
        return new RsData(resultCode, msg);
    }
}