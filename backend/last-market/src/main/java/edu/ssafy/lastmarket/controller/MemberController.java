package edu.ssafy.lastmarket.controller;

import edu.ssafy.lastmarket.domain.dto.MemberRegistDto;
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
    public ResponseEntity<?> registMember(Principal principal, MemberRegistDto memberRegistDto){

        memberService.updateMember(memberRegistDto, principal.getName());
        memberCategoryService.save(memberRegistDto.getCategories(), principal.getName());

        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PatchMapping("/user/profile")
    public ResponseEntity<?> registProfile(Principal principal,  @RequestParam("image")MultipartFile multipartFile) throws IOException {

        memberService.updateProfile(imageUploadService.upload(multipartFile),principal.getName());

        return new ResponseEntity<>(HttpStatus.CREATED);
    }

}
