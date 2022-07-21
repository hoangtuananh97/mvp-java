package com.example.demo.model;

import com.example.demo.exception.UserNameInvalidateException;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

import javax.persistence.*;
import java.io.Serializable;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Setter
@Getter
@NoArgsConstructor
@Entity
@Table(name="member")
public class Members implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    @Setter(AccessLevel.NONE)
    @Column(name = "username", unique = true)
    private String username;

    @Column(name = "password")
    private String password;

    @Column(name = "salary")
    private int salary;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "member_type_id", nullable = false)
    @JsonIgnore
    private MemberTypes memberType;


    public Members(String username, String password, int salary, MemberTypes memberType) {
        this.username = username;
        this.password = password;
        this.salary = salary;
        this.memberType = memberType;
    }

    public void setUsername(String username) {
        String regexEmail = "^(.+)@(.+)$";
        Pattern pattern = Pattern.compile(regexEmail);
        Matcher matcher = pattern.matcher(username);
        if (!matcher.matches()){
            throw new UserNameInvalidateException(String.format("%s is not email",username));
        }
        this.username = username;
    }

}
