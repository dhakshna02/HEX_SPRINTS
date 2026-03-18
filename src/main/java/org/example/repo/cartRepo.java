package org.example.repo;


import org.example.enums.UserMembership;
import org.example.model.CartItem;
import org.example.model.user;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

@Repository
public class cartRepo {

    private final JdbcTemplate jdbcTemplate;

    public cartRepo(JdbcTemplate jdbcTemplate){
        this.jdbcTemplate = jdbcTemplate;
    }

    public void getHelo() {
        System.out.println("hi");
    }


    public void insertUserAndCart(user user, CartItem cart) {

        String sql = " insert into `user` (name,membership) values ( ?,? )";

        KeyHolder keyHolder = new GeneratedKeyHolder();

        jdbcTemplate.update(connection -> {
            PreparedStatement ps =
                    connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);

            ps.setString(1, user.getName());
            ps.setString(2, user.getMembership().toString());

            return ps;
        }, keyHolder);

        int id = keyHolder.getKey().intValue();

        System.out.println("Generated user id = " + id);
        System.out.println("Inserted in to user table ");
        String sql1 = "INSERT INTO cart_item (name,price,quantity,user_id) VALUES (?,?,?,?)";

            jdbcTemplate.update(sql1,
                    cart.getName(),
                    cart.getPrice(),
                    cart.getQuantity(),
                    id);

            System.out.println("insertion completed ");

    }

    public List<CartItem> getAllDetails() {
        String sql = "select c.id As cid," +
                    " c.name as cname , c.price as cprice, c.quantity as cquantity , " +
                        " u.id as uid, u.name as uname , u.membership as umembership  from cart_item c join user u where c.user_id = u.id ";
        return jdbcTemplate.query(sql, new RowMapper<CartItem>() {
            @Override
            public CartItem mapRow(ResultSet rst, int rowNum) throws SQLException {
                return new CartItem(
                        rst.getInt("cid"),
                        rst.getString("cname"),
                        rst.getBigDecimal("cprice"),
                        rst.getInt("cquantity"),
                        new user( rst.getInt("uid"),
                                rst.getString("uname"),
                                UserMembership.valueOf(rst.getString("umembership")))

                );
            }
        });
    }

    public List<user> getAllUserName() {
        String sql = "select id , name from user";
        return jdbcTemplate.query(sql, new RowMapper<user>() {
            @Override
            public user mapRow(ResultSet rs, int rowNum) throws SQLException {
                return new user(
                        rs.getInt("id"),
                       rs.getString("name")
                );
            }
        });
    }

    public List<CartItem> getAllDetailss() {
        String sql = "select id , name , price , quantity , user_id from cart_item";
        return jdbcTemplate.query(sql, new RowMapper<CartItem>() {
            @Override
            public CartItem mapRow(ResultSet rs, int rowNum) throws SQLException {
                return new CartItem (
                        rs.getInt("id"),
                        rs.getString("name"),
                        rs.getBigDecimal("price"),
                        rs.getInt("quantity"),
                        new user(rs.getInt("user_id"))
                );
            }
        });
        }

    public List<CartItem> getProductsWithId() {
        String sql = "select id , name from cart_item";
        return jdbcTemplate.query(sql, new RowMapper<CartItem>() {
            @Override
            public CartItem mapRow(ResultSet rs, int rowNum) throws SQLException {
                return new CartItem(
                        rs.getInt("id"),
                        rs.getString("name")
                );
            }
        });
    }

    public int deleteProduct(int id) {
        String sql = "delete from cart_item where id = ?";
        try {
            return jdbcTemplate.update(sql, id);
        }catch (RuntimeException e){
            System.out.println(e.getMessage());
            e.printStackTrace();
        }
        return id;
    }
}

