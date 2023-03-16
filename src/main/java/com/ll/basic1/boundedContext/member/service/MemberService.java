package com.ll.basic1.boundedContext.member.service;

import com.ll.basic1.base.rsData.RsData;
import com.ll.basic1.boundedContext.member.entity.Member;
import com.ll.basic1.boundedContext.member.repository.MemberRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

/*  ** 프로그램 실행 과정
 * HomeController 객체 생성
 *    MemberService 객체 생성
 *       MemberRepository 객체 생성
 * MemberController 객체 생성
 * 고객(브라우저)이 /member/login?userId=user1&password=1234 요청
 *   MemberController::login 실행
 *       MemberService::tryLogin 실행
 *           MemberRepository::findByUserId 실행
 *           MemberRepository::findByUserId 응답
 *       MemberService::tryLogin 응답
 *   MemberController::login 응답
 * 응답이 브라우저로 전송
 * */

@Service // 아래 클래스의 객체는 Ioc 컨테이너에 의해 생사소멸 관리된다.
@AllArgsConstructor
public class MemberService {

    private final MemberRepository memberRepository;

    public RsData tryLogin(String userId, String password) {
        Member member = memberRepository.findByUserId(userId);

        if (member == null) {
            return RsData.of("F-2", "%s(은)는 존재하지 않는 회원입니다.".formatted(userId));
        }

        if (!member.getPassword().equals(password)) {
            return RsData.of("F-1", "비밀번호가 일치하지 않습니다.");
        }

        return RsData.of("S-1", "%s 님 환영합니다.".formatted(userId), member);
    }

    public Member findByUserId(String userId) {
        return memberRepository.findByUserId(userId);
    }

    public Member findById(long id) {
        return memberRepository.findById(id);
    }
}
