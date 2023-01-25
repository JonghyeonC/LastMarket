package edu.ssafy.lastmarket.controller;

import edu.ssafy.lastmarket.argumentresolver.Login;
import edu.ssafy.lastmarket.domain.dto.MemberRegistDto;
import edu.ssafy.lastmarket.domain.eneity.Member;
import edu.ssafy.lastmarket.service.ImageUploadService;
import edu.ssafy.lastmarket.service.MemberCategoryService;
import edu.ssafy.lastmarket.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.security.Principal;
@Controller
@RequiredArgsConstructor
public class MemberController {

    private final MemberService memberService;
    private final MemberCategoryService memberCategoryService;

    private final ImageUploadService imageUploadService;
    @PostMapping("/user")
    public ResponseEntity<?> registMember(@Login Member member, MemberRegistDto memberRegistDto){

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
