package com.dayang.miki.controller;

import com.dayang.miki.domain.Cart;
import com.dayang.miki.domain.Item;
import com.dayang.miki.domain.Item_img;
import com.dayang.miki.domain.Item_option;
import com.dayang.miki.service.CartService;
import com.dayang.miki.service.ItemService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@Controller
@RequiredArgsConstructor
public class CartController {

    private final CartService cartService;
    private final ItemService itemService;

    @GetMapping("/cartList")
    public String cartList(Model model){
        List<Cart> carts= cartService.findAll();
        List<Item_img> imgs = new ArrayList<>();
        for(Cart cart : carts){
            imgs.add(itemService.itemImg(cart.getItem().getId()));
        }
        model.addAttribute(carts);
        model.addAttribute(imgs);

        return "/cart/cart";
    }

    @PostMapping("/cart")
    public void insertCart(@RequestParam("item_id")Long item_id, @RequestParam("item_option_id")Long item_option_id, @RequestParam("count") int count){

        Item item = itemService.findOne(item_id);
        Item_option item_option = itemService.findItemOptionById(item_option_id);
        Cart cart = new Cart();
        cart.setItem(item);
        cart.setItem_option(item_option);
        cart.setCount(count);
        cartService.save(cart);
    }

    @DeleteMapping("/cart/{cart_id}")
    public String deleteOne(@PathVariable("cart_id") Long id){
        cartService.deleteOne(id);
        return "redirect:/cart/cart";
    }

    @DeleteMapping("/cart")
    public String deleteAll(){
        cartService.truncateCart();
        return "redirect:/cart/cart";
    }

}
