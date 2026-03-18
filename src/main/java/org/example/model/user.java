package org.example.model;

import org.example.enums.UserMembership;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.stereotype.Component;

import java.util.Objects;

@Component
public class user {

    private int id ;
    private String name ;
    private UserMembership membership;

    public user() {
    }

    public user(int id, String name, UserMembership membership) {
        this.id = id;
        this.name = name;
        this.membership = membership;
    }


    public user(int id, String name) {
        this.id = id ;
        this.name = name;
    }

    public user(int userId) {
        this.id = userId;
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

    public UserMembership getMembership() {
        return membership;
    }

    public void setMembership(UserMembership membership) {
        this.membership = membership;
    }

    @Override
    public String toString() {
        return "user{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", membership=" + membership +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        user user = (user) o;
        return Objects.equals(name, user.name);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(name);
    }
}
