package com.spb.total.repository;

import com.spb.total.constant.ItemSellStatus;
import com.spb.total.entity.Item;
import com.spb.total.entity.Member;
import com.spb.total.entity.Order;
import com.spb.total.entity.OrderItem;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;

import javax.persistence.EntityManager;
import javax.persistence.EntityNotFoundException;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
@TestPropertySource(locations = "classpath:application-test2.properties")
@Slf4j
class OrderRepositoryTest {
    @Autowired
    OrderRepository orderRepository;
    @Autowired
    ItemRepository itemRepository;
    @Autowired
    MemberRepository memberRepository;
    @PersistenceContext
    EntityManager em;

    public Item createItem(){
        Item item = new Item();
        item.setItemName("테스트 상품");
        item.setPrice(10000);
        item.setItemDetail("상세 설명");
        item.setItemSellStatus(ItemSellStatus.SELL);
        item.setStockNumber(100);
        return item;
    }

    public Order createOrder(){
        Order order = new Order();
        for(int i = 0; i<3; i++){
            Item item = this.createItem();
            itemRepository.save(item);
            OrderItem orderItem = new OrderItem();
            orderItem.setItem(item);
            orderItem.setCount(10);
            orderItem.setOrderPrice(1000);
            orderItem.setOrder(order);
            order.getOrderItemList().add(orderItem);
        }
        Member member = new Member();
        member.setName("박상원");
        member.setEmail("Test@test.com");
        member.setRegDate(LocalDateTime.now());
        memberRepository.save(member);
        order.setMember(member);
        orderRepository.save(order);
        return order;
    }

    @Test
    @DisplayName("영속성 전이 테스트")
    public void cascadeTest(){
        Order order = new Order();
        for(int i =0; i<3; i++){
            Item item = this.createItem();
            itemRepository.save(item);
            OrderItem orderItem = new OrderItem();
            orderItem.setItem(item);
            orderItem.setCount(10);
            orderItem.setOrderPrice(1000);
            orderItem.setOrder(order);
            order.getOrderItemList().add(orderItem);
        }
        orderRepository.saveAndFlush(order);
        em.clear();

        //주문 조회
        Order saveOrder = orderRepository.findById(order.getId()).orElseThrow(EntityNotFoundException::new);
        log.info(String.valueOf(saveOrder.getOrderItemList().size()));
        log.warn(saveOrder.toString());
    }
}