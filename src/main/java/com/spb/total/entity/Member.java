package com.spb.total.entity;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;
import java.time.LocalDateTime;

@Getter
@Setter
@Entity
@Table(name="member")
@ToString
public class Member {
    @Id
    @Column(name = "member_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String pwd;
    @Column(unique = true)
    private String email;
    private String image;
    private LocalDateTime regDate;

    @PrePersist // DB 에 Insert 되기 전에 실행되는 메소드
    public void prePersist(){
        regDate = LocalDateTime.now();
    }

}
