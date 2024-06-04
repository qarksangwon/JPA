package com.spb.total.repository;

import com.spb.total.constant.ItemSellStatus;
import com.spb.total.entity.Item;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;

import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@TestPropertySource(locations = "classpath:application-test.properties")
class ItemRepositoryTest {
    @Autowired
    ItemRepository itemRepository;

    @Test
    @DisplayName("상품 저장 테스트")
    public void createItemTest() {
        for(int i = 1; i <= 10; i++) {
            Item item = new Item();
            if(i < 5) item.setItemName("테스트 상품" + i);
            else item.setItemName("실제 상품" + i);
            item.setPrice(10000*i);
            item.setItemDetail("테스트 상품 상세 설명"+ i);
            item.setItemSellStatus(ItemSellStatus.SELL);
            item.setStockNumber(100);
            item.setRegTime(LocalDateTime.now());
            item.setUpdateTime(LocalDateTime.now());
            itemRepository.save(item);
        }
    }

    @Test
    @DisplayName("상품명으로 조회 테스트")
    public void findByItemNameTest(){
        this.createItemTest();
        List<Item> itemList = itemRepository.findByItemName("테스트 상품3");
        for(Item item : itemList){
            System.out.println("상품 가격 : " + item.getPrice());
        }
    }

    @Test
    @DisplayName("상품명, 상품상세설명")
    public void findByItemNameOrItemDetailTest() {
        this.createItemTest();
        List<Item> itemList = itemRepository.findByItemNameOrItemDetail("테스트 상품1", "테스트 상품 상세 설명5");
        for(Item item : itemList) {
            System.out.println("상품 명 : " + item.getItemName());
            System.out.println("상품 설명 : " + item.getItemDetail());
        }
    }

    @Test
    @DisplayName("LessThan 테스트")
    public void findBytPriceLessThanTest(){
        this.createItemTest();
        List<Item> itemList = itemRepository.findByPriceLessThanOrderByPriceDesc(70001);
        for (Item o : itemList){
            System.out.println(o.toString());
        }
    }

    @Test
    @DisplayName("Between 테스트")
    public void findByPriceBetweenTest(){
        this.createItemTest();
        List<Item> itemList = itemRepository.findByPriceBetween(20000,40000);
        for(Item o : itemList){
            System.out.println(o.toString());
        }
    }

    @Test
    @DisplayName("키워드 포함 테스트")
    public void findByItemNameContainingTest(){
        this.createItemTest();
        List<Item> itemList = itemRepository.findByItemNameContaining("실제");
        for(Item o : itemList){
            System.out.println(o.toString());
        }
    }

}