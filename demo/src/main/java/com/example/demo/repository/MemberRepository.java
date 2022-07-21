package com.example.demo.repository;

import com.example.demo.model.Members;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MemberRepository extends JpaRepository<Members, Long> {
    Members findByUsername(String username);
}
