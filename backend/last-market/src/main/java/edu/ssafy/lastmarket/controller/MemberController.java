package edu.ssafy.lastmarket.controller;

import edu.ssafy.lastmarket.argumentresolver.Login;
import edu.ssafy.lastmarket.domain.dto.MemberRegistDto;
import edu.ssafy.lastmarket.domain.entity.Member;
import edu.ssafy.lastmarket.service.ImageUploadService;
import edu.ssafy.lastmarket.service.MemberCategoryService;
import edu.ssafy.lastmarket.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@Controller
@RequiredArgsConstructor
@RequestMapping("/api")
public class MemberController {

    private final MemberService memberService;
    private final MemberCategoryService memberCategoryService;

    private final ImageUploadService imageUploadService;
    @PostMapping("/user")
    public ResponseEntity<?> registMember(@Login Member member, @RequestBody MemberRegistDto memberRegistDto, Authentication authentication){
        System.out.println(authentication.isAuthenticated()+" "+ authentication.getPrincipal());
        System.out.println(memberRegistDto.getCategories());
        memberService.updateMember(memberRegistDto, member);
        memberCategoryService.save(memberRegistDto.getCategories(), member);

        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PatchMapping("/user/profile")
    public ResponseEntity<?> registProfile(@Login Member member,  @RequestParam("image")MultipartFile multipartFile) throws IOException {

        memberService.updateProfile(imageUploadService.upload(multipartFile),member);

        return new ResponseEntity<>(HttpStatus.CREATED);
    }

}
