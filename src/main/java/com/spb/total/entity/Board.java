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
    @PrePersist
    public void prePersist() {
        regDate = LocalDateTime.now();
    }
    @ManyToOne(fetch = FetchType.LAZY) // 지연 전략
    @JoinColumn(name = "member_id")
    private Member member; // 작성자

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "category_id")
    private Category category;

    //Board 와 Location 은 1:1 관계
    @OneToOne(mappedBy = "board", fetch = FetchType.LAZY, cascade = CascadeType.ALL , orphanRemoval = true)
    private Location location;

}
