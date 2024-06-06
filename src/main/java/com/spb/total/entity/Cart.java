package com.spb.total.entity;


import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;

@Entity
@Getter @Setter
@ToString
@Table(name = "cart")
public class Cart {
    @Id
    @Column(name = "cart_id")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String cartName;

    @OneToOne
    @JoinColumn(name = "member_id")
    private Member member;
}

