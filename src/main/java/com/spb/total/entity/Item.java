package com.spb.total.entity;

import com.spb.total.constant.ItemSellStatus;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;
import java.time.LocalDateTime;

@Getter
@Setter
@Entity // JPA 에 Entity 클래스임을 지정. Entity 는 반드시 기본 키를(@Id) 가져야 한다.
@ToString
public class Item {
    @Id
    @Column(name = "item_id") // 컬럼명 지정
    @GeneratedValue(strategy = GenerationType.AUTO) // pk가 자동 생성되도록, 생성 전략중 하나
    private Long id; // 상품 코드
    @Column(nullable = false, length = 50) // null 허용하지 않으며, 길이(크기) 50으로 지정
    private String itemName; // 상품명
    @Column(nullable = false)
    private int price; // 가격
    @Column(nullable = false)
    private int stockNumber; //재고
    @Lob // 내용이 많은 경우(굉장히 긴 문자열) 사용하는 옵션
    @Column(nullable = false)
    private  String itemDetail; //상세 설명
    @Enumerated(EnumType.STRING) // EnumType 의 ORDINARY 는 순서값이 들어감.
    // String 은 해당 문자열이 DB에 들어감
    private ItemSellStatus itemSellStatus; // 제품 판매 상태
    private LocalDateTime regTime; // 등록 시간
    private LocalDateTime updateTime; // 수정 시간
}
