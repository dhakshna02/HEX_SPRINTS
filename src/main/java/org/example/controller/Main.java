package org.example.controller;

import org.example.config.projConfig;
import org.example.enums.UserMembership;
import org.example.model.CartItem;
import org.example.model.user;
import org.example.service.cartService;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import java.math.BigDecimal;
import java.util.List;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {

        System.out.println("Hello, World!");

        var context = new AnnotationConfigApplicationContext(projConfig.class);
        cartService service = context.getBean(cartService.class);
        user user = context.getBean(org.example.model.user.class);



        Scanner sc = new Scanner(System.in);

        while(true) {
            System.out.println("");
            System.out.println("1: Insert into Db");
            System.out.println("2: Fetech all from Db");
            System.out.println("3: Fetch Items by given username");
            System.out.println("4: Delete an item ");
            int choice = sc.nextInt();
            sc.nextLine();

        switch (choice) {

          case 1:
              System.out.println("Enter thr name of the user ");
              String usernae = sc.nextLine();

                    user.setName(usernae);
              System.out.println("Enter the membership(Normal/premium)");
              String membership = sc.nextLine().toUpperCase();
                    user.setMembership(UserMembership.valueOf(membership));

                    CartItem cart = context.getBean(CartItem.class);
                    cart.setName("Mobile");
                    cart.setPrice(BigDecimal.valueOf(200));
                    cart.setQuantity(5);

                    // setting user first
                    try {
                        service.insertUserAndCart(user, cart);


                    } catch (RuntimeException e) {
                        System.out.println(e.getMessage());
                        e.printStackTrace();
                    }
               break;
          case 2:
              try{
                  List<CartItem> cartItems = service.getAllDetails();
                  cartItems.forEach(cartItem -> {
                      System.out.println("Id:" + cartItem.getId());
                      System.out.println("name:" + cartItem.getName());
                      System.out.println("price: "+ cartItem.getPrice());
                      System.out.println("quanity: "+ cartItem.getQuantity());
                      System.out.println("userId : "+cartItem.getUser().getId());
                      System.out.println("username : "+cartItem.getUser().getName());
                      System.out.println("membership : "+cartItem.getUser().getMembership());



                  } );

              } catch (RuntimeException e) {
                  throw new RuntimeException(e);
              }
              break;
            case 3:
                try{
                   List<user> userList = service.getAllUserName();

                   userList.forEach(user1 ->{
                       System.out.print(user1.getId()+"\t");
                           System.out.print(user1.getName());
                       System.out.println("");
                   });

                    System.out.println("Enter the username id to get all products ");
                    int id  = sc.nextInt();
                    sc.nextLine();


                   List<CartItem> cartlist = service.getAllItemofUser(id);
                    System.out.println("Getting all products ");
                   cartlist.forEach(cartItem -> {
                       System.out.println("Id: \t" + cartItem.getId());
                       System.out.println("Name: \t" +cartItem.getName());
                       System.out.println("Price: \t" +cartItem.getPrice());
                       System.out.println("Quanity: \t" +cartItem.getQuantity());
                   } );


                }catch (RuntimeException e){
                    System.out.println(e.getMessage());
                    e.printStackTrace();
                }
            break;
            case 4:

                try{
                    List<CartItem> carlist = service.getProductsWithId();

                    carlist.forEach(carlis ->{
                        System.out.print(carlis.getId()+"\t");
                        System.out.print(carlis.getName());
                        System.out.println("");
                    });

                    System.out.println("Enter the id to delete ");
                    int id = sc.nextInt();
                    sc.nextLine();

                    int updatedrow = service.deleteProduct(id);
                    if(updatedrow > 0)
                        System.out.println("deleted successfully");
                }catch (RuntimeException e){
                    System.out.println(e.getMessage());
                    e.printStackTrace();

                }



        }
        }
    }
}