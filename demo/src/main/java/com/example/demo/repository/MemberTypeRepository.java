package com.example.demo.repository;

import com.example.demo.model.MemberTypes;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface MemberTypeRepository extends JpaRepository<MemberTypes, Integer> {
    @Query(
        value = "SELECT * FROM MEMBER_TYPE WHERE (IS_LIMIT_SALARY = true AND FROM_SALARY < :salary AND :salary < TO_SALARY) OR (IS_LIMIT_SALARY = false AND FROM_SALARY < :salary)",
        nativeQuery = true
    )
    MemberTypes getMemberTypesBySalary(@Param("salary") Long salary);
}
