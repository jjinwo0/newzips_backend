package com.ssafy.happyhouse.controller.member;

import com.ssafy.happyhouse.dto.member.MemberDto;
import com.ssafy.happyhouse.entity.member.Member;
import com.ssafy.happyhouse.service.MemberService;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("/member")
@RequiredArgsConstructor
public class MemberController {

    private final MemberService memberService;

    @PostMapping("/login")
    public ResponseEntity<?> loginMember(@RequestBody MemberDto.Request dto,
                                         HttpServletRequest request,
                                         HttpServletResponse response) {

        Member findMember = memberService.findByUsernameAndPassword(dto.getUsername(), dto.getPassword());

        log.info("debug info :: " + findMember.getEmail());

        System.out.println("Username :: " + dto.getUsername());
        System.out.println("Password :: " + dto.getPassword());

        HttpSession session = request.getSession();

        session.setAttribute("loginMember", findMember);

        Cookie cookie = new Cookie("loginMember", session.getId());
        cookie.setPath("/");
        cookie.setMaxAge(60 * 60 * 24 * 7); // 1주일
        response.addCookie(cookie);

        return ResponseEntity.ok(findMember);
    }

    @PostMapping("/logout")
    public ResponseEntity<?> logout(HttpServletRequest request,
                                    HttpServletResponse response) {

        HttpSession session = request.getSession(false);

        session.invalidate();

        Cookie cookie = new Cookie("loginMember", null);
        cookie.setPath("/");
        cookie.setMaxAge(0); // 쿠키의 만료 시간을 0으로 설정하여 삭제한다.
        response.addCookie(cookie);

        return ResponseEntity.ok("Logout Success");
    }

    @PostMapping("/join")
    public ResponseEntity<?> join(MemberDto.Join dto){

        memberService.join(dto);

        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PatchMapping("/update/{id}")
    public ResponseEntity<?> update(@RequestBody MemberDto.Update dto){

        memberService.updateMember(dto);

        return new ResponseEntity<>(HttpStatus.OK);
    }
}
