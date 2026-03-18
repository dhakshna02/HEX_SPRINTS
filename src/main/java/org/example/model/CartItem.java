package org.example.model;

import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.Objects;

@Component
public class CartItem {
    private int id ;
    private String name ;
    private BigDecimal price;
    private int quantity;
    private user user;

    public CartItem() {
    }

    public CartItem(int id, String name, BigDecimal price, int quantity,user user) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.quantity = quantity;
        this.user = user;
    }

    public CartItem(int id, String name, BigDecimal price, int quantity) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.quantity = quantity;
    }

    public CartItem(int id, String name) {
        this.id = id;
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public user getUser() {
        return user;
    }

    public void setUser(user user) {
        this.user = user;
    }

    @Override
    public String toString() {
        return "CartItem{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", price=" + price +
                ", quantity=" + quantity +
                ", user=" + user +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        CartItem cartItem = (CartItem) o;
        return id == cartItem.id && quantity == cartItem.quantity && Objects.equals(name, cartItem.name) && Objects.equals(price, cartItem.price) && Objects.equals(user, cartItem.user);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, price, quantity, user);
    }
}