package org.example.service;


import org.example.model.CartItem;
import org.example.model.user;
import org.example.repo.cartRepo;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class cartService {

    private final cartRepo cartrepo;

    public cartService(cartRepo cartrepo){
        this.cartrepo = cartrepo;
    }

    public void getHello() {
        cartrepo.getHelo();
    }


    @Transactional
    public void insertUserAndCart(user user, CartItem cart) {
        cartrepo.insertUserAndCart(user,cart);
    }


    public List<CartItem> getAllDetails() {
        return cartrepo.getAllDetails();
    }

    public List<user> getAllUserName() {
        List<user> userLis=  cartrepo.getAllUserName();

        return userLis.stream().distinct().toList();
    }

    public List<CartItem> getAllItemofUser(int  id) {
        List<CartItem> cartlist =  cartrepo.getAllDetailss();
        return cartlist.stream().filter(CartItem -> CartItem.getUser().getId() == id).toList();
    }

    public List<CartItem> getProductsWithId() {
        return cartrepo.getProductsWithId();
    }

    public int deleteProduct(int id) {
        return cartrepo.deleteProduct(id);
    }
}
