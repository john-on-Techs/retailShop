package com.jotech.views;

import com.jotech.bean.ReceivingBean;
import com.jotech.bean.RunningBalanceProductBean;
import com.jotech.bean.SaleBean;
import com.jotech.bean.UserBean;
import com.jotech.dao.DBHandler;
import com.jotech.entity.Receiving;
import com.jotech.entity.RunningBalanceProduct;
import com.jotech.entity.Sale;
import com.jotech.entity.User;
import com.jotech.util.AppHelper;
import com.jotech.util.Utils;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

public class SaleUI {

    private SaleBean saleBean = new SaleBean();
    private RunningBalanceProductBean runningBalanceProductBean = new RunningBalanceProductBean();
    private ReceivingBean receivingBean = new ReceivingBean();
    private UserBean userBean = new UserBean();
    private User user;

    public SaleUI(User user) {
        this.user = user;

        int choice;
        do {
            showSaleMenu();
            choice = Integer.parseInt(Utils.readKeyboard("choice"));
            switch (choice) {
                case 1:
                    createSale();
                    break;
                case 2:
                    readSale();
                    break;

                case 3:
                    listSales();
                    break;
                case 4:
                    listSalesPerUser();
                    break;
                case 5:
                    choice = 5;
                    break;
            }
        } while (choice != 5);
    }

    private void showSaleMenu() {
        System.out.println("***************************");
        System.out.println("Sale Management Screen");
        System.out.println("***************************");
        System.out.println("1. Create Sale");
        System.out.println("2. Read Sale");
        System.out.println("3. List Sale");
        System.out.println("4. List Sale Per User");
        System.out.println("5. Back to Home");

    }

    private void createSale() {
        int productId = Integer.parseInt(Utils.readKeyboard("product id"));
        int quantity = Integer.parseInt(Utils.readKeyboard("quantity"));
        //set the sale selling price to the 0
        Sale sale = new Sale(productId, 0.0, quantity, user.getId());
        try {
            if (saleBean.create(sale)) {
                System.out.println("Sale recorded successfully");
            }else{
                System.out.println("Could not make sale");
            }
        } catch (SQLException e) {
            System.out.println("Some error occurred"+e.getLocalizedMessage());
            e.printStackTrace();
        }

    }

    private void readSale() {
        int saleId = Integer.parseInt(Utils.readKeyboard("sale Id"));
        try {
            Sale sale = saleBean.read(saleId);
            if (sale != null) {
                System.out.println(sale);
            } else {
                System.out.println("sale does not exist");
            }
        } catch (SQLException e) {
            System.out.println("Some error occurred");
            e.printStackTrace();
        }
    }

    private void listSales() {
        try {
            ArrayList<Sale> saleList = saleBean.getSales();
            if (saleList.size() > 0) {
                for (Sale sale : saleList) {
                    System.out.println(sale);
                }
            } else {
                System.out.println("No sales yet");
            }
        } catch (SQLException e) {
            System.out.println("Some error occurred");
            e.printStackTrace();
        }
    }

    private void listSalesPerUser() {
        int userId = Integer.parseInt(Utils.readKeyboard("user id"));
        try {
            User user = userBean.read(userId);
            if (user != null) {
                ArrayList<Sale> saleList = saleBean.getSales();
                if (saleList.size() > 0) {
                    for (Sale sale : saleList) {
                        if (sale.getUserId() == userId) {
                            System.out.println(sale);
                        }
                    }
                } else {
                    System.out.println("No sales for this user");
                }
            } else {
                System.out.println("User with the supplied id does not exist");
            }
        } catch (SQLException e) {
            System.out.println("Some error occurred");
            e.printStackTrace();
        }
    }
}
