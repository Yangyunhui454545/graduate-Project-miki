package com.dayang.miki.repository;

import com.dayang.miki.domain.Cart;
import com.dayang.miki.domain.Item;
import com.dayang.miki.domain.Item_option;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;


import java.util.List;

public interface CartRepo extends JpaRepository<Cart, Long> {
    @Modifying
    @Query(
            value = "truncate table cart",
            nativeQuery = true
    )
    void truncateCart();

    @Modifying
    @Query("UPDATE Cart c SET c.count =:cnt WHERE c.item_option =:item_option")
    int updateCnt(@Param("cnt") int cnt, @Param("item_option") Item_option item_option);

    @Query("select c.item from Cart c")
    List<Item> getItem(Pageable pageable);

    @Query("select distinct c.item from Cart c")
    List<Item> getItemNoPage();


    @Query("select c.item_option from Cart c")
    List<Item_option> getItemOption(Pageable pageable);

    Page<Cart> findAll(Pageable pageable);

}
