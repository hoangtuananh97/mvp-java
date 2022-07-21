package com.example.demo.controller;

import com.example.demo.Utils;
import com.example.demo.exception.MemberExistsException;
import com.example.demo.exception.MemberNotFoundException;
import com.example.demo.exception.SalaryInvalidateException;
import com.example.demo.model.MemberTypes;
import com.example.demo.model.Members;
import com.example.demo.repository.MemberRepository;
import com.example.demo.repository.MemberTypeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/api/v1/members")
public class MemberController {

    @Autowired
    private MemberRepository memberRepository;
    @Autowired
    private MemberTypeRepository memberTypeRepository;

    @GetMapping
    public List<Members> getAllMember(){
        return memberRepository.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Members> getMemberById(@PathVariable long id){
        Members member = memberRepository.findById(id).
                orElseThrow(() -> new MemberNotFoundException("Member not found by id:" + id));
        return ResponseEntity.ok(member);
    }

    @PostMapping
    public ResponseEntity<Members> createMember(@RequestBody Members params){
        String username = params.getUsername();
        int salary = params.getSalary();
        try{
            MemberTypes memberType = memberTypeRepository.getMemberTypesBySalary(Utils.toLong(salary));
            if (memberType == null){
                throw new SalaryInvalidateException("Minimum salary for new member is 15000");
            }
            Members member = memberRepository.findByUsername(username);
            if (member != null){
                throw new MemberExistsException("Username exists");
            }
            params.setMemberType(memberType);
            member = memberRepository.save(params);
            return ResponseEntity.ok(member);
        }
        catch (Exception e){
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }

    @PutMapping("/{id}")
    public ResponseEntity<Members> updateMember(@PathVariable long id, @RequestBody Members params){
        Members updateMember = memberRepository.findById(id)
                .orElseThrow(() -> new MemberNotFoundException("Member not found by id:" + id));
        updateMember.setUsername(params.getUsername());
        updateMember.setPassword(params.getPassword()); // encode
        updateMember.setSalary(params.getSalary());
        memberRepository.save(updateMember);

        return ResponseEntity.ok(updateMember);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<HttpStatus> deleteMember(@PathVariable long id){
        Members member = memberRepository.findById(id).
                orElseThrow(() -> new MemberNotFoundException("Member not found by id: " + id));
        memberRepository.delete(member);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
