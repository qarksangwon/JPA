package com.spb.total.repository;

import com.spb.total.entity.Item;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ItemRepository extends JpaRepository<Item, Long> {
    List<Item> findByItemName(String itemName);
    List<Item> findByItemNameOrItemDetail(String itemName, String itemDetail);
    List<Item> findByPriceLessThanOrderByPriceDesc(int price);
    List<Item> findByPriceBetween(int minPrice, int maxPrice);
}
