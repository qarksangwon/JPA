package com.spb.total.entity;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@Table(name = "board")
@ToString
public class Board {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long boardId;
    private String title;
    @Lob
    @Column(length = 10000)
    private String content;
    private String imgPath;
    private LocalDateTime regDate;

}
