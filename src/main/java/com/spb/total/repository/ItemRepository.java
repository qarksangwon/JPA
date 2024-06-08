package com.spb.total.repository;

import com.spb.total.entity.Item;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ItemRepository extends JpaRepository<Item, Long> {
    List<Item> findByItemName(String itemName);
    List<Item> findByItemNameOrItemDetail(String itemName, String itemDetail);
    List<Item> findByPriceLessThanOrderByPriceDesc(int price);
    List<Item> findByPriceBetween(int minPrice, int maxPrice);
    List<Item> findByItemNameContaining(String keyword);

    @Query("select i from Item i where i.itemDetail like %:itemDetail% order by i.price desc")
    List<Item> findByItemDetail(@Param("itemDetail") String detail);

    @Query(value= "select * from item i where i.item_detail like %:itemDetail% order by i.price desc", nativeQuery = true)
    List<Item> findByItemDetailByNative(@Param("itemDetail")String detail);
}
