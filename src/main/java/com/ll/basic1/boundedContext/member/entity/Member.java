package com.ll.basic1.boundedContext.member.entity;

import lombok.AllArgsConstructor;
import lombok.Data;

@AllArgsConstructor
@Data
public class Member {

    private static long lastId;
    private final long id; // memberId
    private final String userId;
    private final String password;


    static {
        lastId = 0;
    }

    public Member(String userId, String password) {
        this(++lastId, userId, password);
    }

}
