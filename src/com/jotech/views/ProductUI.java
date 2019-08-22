package com.jotech.views;

import com.jotech.bean.ProductBean;
import com.jotech.entity.Product;
import com.jotech.util.AppHelper;
import com.jotech.util.Utils;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ProductUI {
    private ProductBean productBean = new ProductBean();

    public ProductUI() {

        int choice;
        do {
            showProductMenu();
            choice = Integer.parseInt(Utils.readKeyboard("choice"));
            switch (choice) {
                case 1:
                    createProduct();
                    break;
                case 2:
                    readProduct();
                    break;
                case 3:
                    updateProduct();
                    break;
                case 4:
                    deleteProduct();
                    break;
                case 5:
                    listProducts();
                    break;
                case 6:
                    choice =6;
                    break;
            }
        }while(choice !=6);
    }

    private void showProductMenu() {
        System.out.println("***************************");
        System.out.println("Product Management Screen");
        System.out.println("***************************");
        System.out.println("1. Create Product");
        System.out.println("2. Read Product");
        System.out.println("3. Update Product");
        System.out.println("4. Delete Product");
        System.out.println("5. List Products");
        System.out.println("6. Back to Home");

    }

    private void createProduct() {
        int id = Integer.parseInt(Utils.readKeyboard("product id"));
        String name = Utils.readKeyboard("product name");
        String description = Utils.readKeyboard("product description");
        Product product = new Product(id, name, description);
        try {
            if (productBean.create(product)) {
                System.out.println("Product added successfully");
            }else {
                System.out.println("product could not be added");
            }
        } catch (SQLException e) {
            System.out.println("Some error occurred");
            Logger.getLogger(AppHelper.class.getName()).log(Level.SEVERE, null, e);
        }
    }

    private void readProduct() {
        int productId = Integer.parseInt(Utils.readKeyboard("product Id"));
        try {
          Product product = productBean.read(productId);
          if(product !=null){
              System.out.println(product);
          }else{
              System.out.println("The product with the supplied id:" +productId+" Does not exist");
          }
        } catch (SQLException e) {
            System.out.println("some error occurred: "+e.getLocalizedMessage());
            e.printStackTrace();
        }
    }

    private void updateProduct() {
        int productId = Integer.parseInt(Utils.readKeyboard("product Id"));
        Product product = null;
        try {
             product = productBean.read(productId);
            System.out.println(product.getName() + " "+product.getDescription());
        } catch (SQLException e) {
            e.printStackTrace();
        }
        if(product != null){
            String name = Utils.readKeyboard(" new name");
            String description = Utils.readKeyboard("new description");
            if(name != null && !name.isEmpty()){
                product.setName(name);
            }
            if(description != null && !description.isEmpty()){
                product.setDescription(description);
            }
            try {
                if(productBean.update(product)){
                    System.out.println("Product updated successfully");
                }else{
                    System.out.println("Could not update the product");
                }
            } catch (SQLException e) {

                e.printStackTrace();
            }
        }
        else{
            System.out.println("The product does not exist");
        }

    }

    private void deleteProduct() {
        int productId = Integer.parseInt(Utils.readKeyboard("product Id"));
        Product product = null;
        try {
            product = productBean.read(productId);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        if(product != null){
            try {
                if(productBean.delete(product)){
                    System.out.println("Product deleted successfully");
                }else {
                    System.out.println("Could not delete the product");
                }
            } catch (SQLException e) {
                System.out.println("Some error occurred"+e.getLocalizedMessage());
                e.printStackTrace();
            }
        }
        else{
            System.out.println("The product does not exist");
        }
    }

    private void listProducts() {
        try {
            ArrayList<Product> productList = productBean.getProducts();
            if(productList.size()>0){
                for(Product product: productList){
                    System.out.println(product.getName() + " "+product.getDescription());
                }
            }else{
                System.out.println("There are no products registered yet");
            }
        } catch (SQLException e) {
            System.out.println("some error occurred"+e.getLocalizedMessage());
            e.printStackTrace();
        }
    }

}
