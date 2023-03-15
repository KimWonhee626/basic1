package com.ll.basic1.base.rsData;

import lombok.AllArgsConstructor;
import lombok.Getter;

// ** RsData => boolean + 알파

@AllArgsConstructor
@Getter
public class RsData {
    private final String resultCode;
    private final String msg;
    private final Object data; // data == memberId

    public static RsData of(String resultCode, String msg) {
        return of(resultCode, msg, null);
    }

    public static RsData of(String resultCode, String msg, Object data) {
        return new RsData(resultCode, msg, data); // data == memberId
    }

    public boolean isSuccess() {
        return resultCode.startsWith("S-");
    }
}