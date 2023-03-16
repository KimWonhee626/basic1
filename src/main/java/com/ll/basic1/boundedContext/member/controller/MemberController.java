package com.ll.basic1.boundedContext.member.controller;

import com.ll.basic1.base.rsData.RsData;
import com.ll.basic1.boundedContext.member.entity.Member;
import com.ll.basic1.boundedContext.member.service.MemberService;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.Map;

@Controller
public class MemberController {
    private final MemberService memberService;

    // 생성자 주입
    @Autowired
    public MemberController(MemberService memberService) {
        this.memberService = memberService;
    }

    @GetMapping("/member/login")
    @ResponseBody
    public RsData login(String userId, String password, HttpServletResponse resp) {
        if (userId == null || userId.trim().length() == 0) {
            return RsData.of("F-3", "userId(을)를 입력해주세요.");
        }

        if (password == null || password.trim().length() == 0) {
            return RsData.of("F-4", "password(을)를 입력해주세요.");
        }

        RsData rsData = memberService.tryLogin(userId, password);

        if (rsData.isSuccess()) {
            Member member = (Member) rsData.getData();
            resp.addCookie(new Cookie("loginMemberId", member.getId() + ""));
        }

        return rsData;
    }

    @GetMapping("/member/logout")
    @ResponseBody
    public RsData logout(HttpServletRequest req, HttpServletResponse resp) {
        if (req.getCookies() != null) { // 쿠키가  있으면
            Arrays.stream(req.getCookies())
                    .filter(cookie -> cookie.getName().equals("loginMemberId")) // 로그인 멤버 아이디가 같은것 걸러냄
                    .forEach(cookie -> {
                        cookie.setMaxAge(0); // 수명을 0으로 설정
                        resp.addCookie(cookie); // 다시 쿠키 주입
                    });
        }

        return RsData.of("S-1", "로그아웃 되었습니다.");
    }



    @GetMapping("/member/me")
    @ResponseBody
    public RsData showMe(HttpServletRequest req) {
        long loginMemberId = 0;

        if (req.getCookies() != null) {
            loginMemberId = Arrays.stream(req.getCookies())
                    .filter(cookie -> cookie.getName().equals("loginMemberId"))
                    .map(Cookie::getValue)
                    .mapToInt(Integer::parseInt)
                    .findFirst()
                    .orElse(0);
        }

        boolean isLogin = loginMemberId > 0;

        if (isLogin == false)
            return RsData.of("F-1", "로그인 후 이용해주세요.");

        Member member = memberService.findById(loginMemberId);

        return RsData.of("S-1", "당신의 userId(은)는 %s 입니다.".formatted(member.getUserId()));
    }
}
