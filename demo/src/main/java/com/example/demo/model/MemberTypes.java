package com.example.demo.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Set;

@Setter
@Getter
@NoArgsConstructor
@Entity
@Table(name="member_type")
public class MemberTypes implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    @Column(name = "type_classify")
    private String typeClassify;

    @Column(name = "from_salary")
    private long fromSalary;

    @Column(name = "to_salary")
    private long toSalary;

    @Column(name = "is_limit_salary")
    private boolean isLimitSalary = true;

    @OneToMany(mappedBy = "memberType", targetEntity = Members.class, orphanRemoval = true, fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JsonIgnore
    private Set<Members> members;

    public MemberTypes(String typeClassify, long fromSalary, long toSalary) {
        this.typeClassify = typeClassify;
        this.fromSalary = fromSalary;
        this.toSalary = toSalary;
    }

}
