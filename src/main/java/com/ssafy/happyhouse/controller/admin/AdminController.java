package com.ssafy.happyhouse.controller.admin;

import com.ssafy.happyhouse.entity.member.Member;
import com.ssafy.happyhouse.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/admin")
@RequiredArgsConstructor
public class AdminController {

    private final MemberService memberService;

    @GetMapping("/member/list")
    public ResponseEntity<?> findAll(){

        List<Member> memberList = memberService.findAll();

        return ResponseEntity.ok(memberList);
    }

    @DeleteMapping("/member/delete/{id}")
    public ResponseEntity<?> deleteMember(@PathVariable("id") Long id){

        memberService.deleteMember(id);

        return new ResponseEntity<>(HttpStatus.OK);
    }
}
