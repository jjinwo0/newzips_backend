package com.ssafy.happyhouse.controller.member;

import com.ssafy.happyhouse.dto.member.MemberDto;
import com.ssafy.happyhouse.entity.member.Member;
import com.ssafy.happyhouse.global.token.JwtTokenDto;
import com.ssafy.happyhouse.global.token.TokenManager;
import com.ssafy.happyhouse.service.MemberService;
import io.jsonwebtoken.Claims;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;

@Slf4j
@RestController
@RequestMapping("/member")
@RequiredArgsConstructor
public class MemberController {

    private final MemberService memberService;

    private final TokenManager tokenManager;

    @PostMapping("/login")
    public ResponseEntity<JwtTokenDto> loginMember(@RequestBody MemberDto.Request dto,
                                         HttpServletRequest request,
                                         HttpServletResponse response) {

        Member findMember = memberService.findByUsernameAndPassword(dto.getUsername(), dto.getPassword());

        log.info("debug info :: " + findMember.getEmail());

        System.out.println("Username :: " + dto.getUsername());
        System.out.println("Password :: " + dto.getPassword());

        JwtTokenDto token = tokenManager.createJwtTokenDto(findMember.getId(), findMember.getUsername(), findMember.getRole());
        memberService.updateToken(findMember.getId(), token);

        Cookie cookie = new Cookie("accessToken", token.getRefreshToken());
        cookie.setPath("/");
        cookie.setMaxAge(60 * 60 * 24 * 7); // 1주일
        response.addCookie(cookie);

        log.info("response :: " + token);

        return ResponseEntity.ok(token);
    }

    @PostMapping("/logout")
    public ResponseEntity<?> logout(HttpServletRequest request,
                                    HttpServletResponse response) {

        try {
            String authorization = request.getHeader("Authorization");
            String accessToken = authorization.split(" ")[1];

            Claims claims = tokenManager.getTokenClaims(accessToken);

            Member findMember = memberService.findById(Long.valueOf((Integer) claims.get("id")));
            memberService.expireToken(findMember.getId(), LocalDateTime.now());

            Cookie cookie = new Cookie("loginMember", null);
            cookie.setPath("/");
            cookie.setMaxAge(0); // 쿠키의 만료 시간을 0으로 설정하여 삭제한다.
            response.addCookie(cookie);
        } catch (Exception e){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        return ResponseEntity.ok("Logout Success");
    }

    @PostMapping("/join")
    public ResponseEntity<?> join(@RequestBody MemberDto.Join dto){

        memberService.join(dto);

        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PatchMapping("/update/{id}")
    public ResponseEntity<?> update(@RequestBody MemberDto.Update dto){

        memberService.updateMember(dto);

        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping("/valid/{username}")
    public ResponseEntity<?> validUsername(@PathVariable String username){

        memberService.validUsername(username);

        return new ResponseEntity<>(HttpStatus.OK);
    }
}
