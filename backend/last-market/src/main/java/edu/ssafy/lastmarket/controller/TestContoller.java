package edu.ssafy.lastmarket.controller;

import edu.ssafy.lastmarket.argumentresolver.Login;
import edu.ssafy.lastmarket.domain.entity.Member;
import edu.ssafy.lastmarket.repository.MemberRepository;
import edu.ssafy.lastmarket.service.CloudImageUploadService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.Optional;

@Controller
@RequestMapping("/api")
@RequiredArgsConstructor
public class TestContoller {

    private final CloudImageUploadService cloudImageUploadService;
    private final MemberRepository memberRepository;
    @GetMapping("/test")
    public ResponseEntity<?> test(@Login Member member){

        System.out.println("=================");
        System.out.println(member.getLocation());
        System.out.println(member.getUsername());
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping("/img/{id}")
    public ResponseEntity<?> deleteImg(@PathVariable("id") String fileName){
        fileName = "files/" + fileName;
        cloudImageUploadService.delete(fileName);
        return new ResponseEntity<>(HttpStatus.OK);
    }
    @GetMapping("/getmember")
    public ResponseEntity<?> test(){
        Optional<Member> memberOptional = memberRepository.findMemberFetchJoinByUsername("kakao_2638166690");
        System.out.println("=================");
        System.out.println(memberOptional.get().getLocation());
        System.out.println(memberOptional.get().getUsername());
        return new ResponseEntity<>(HttpStatus.OK);
    }

}
