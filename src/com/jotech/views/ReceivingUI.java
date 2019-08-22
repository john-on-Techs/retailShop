package com.jotech.views;

import com.jotech.bean.ReceivingBean;
import com.jotech.bean.RunningBalanceProductBean;
import com.jotech.bean.UserBean;
import com.jotech.entity.Receiving;
import com.jotech.entity.User;
import com.jotech.util.Utils;

import java.sql.SQLException;
import java.util.ArrayList;

public class ReceivingUI {
    private RunningBalanceProductBean runningBalanceProductBean = new RunningBalanceProductBean();
    private ReceivingBean receivingBean = new ReceivingBean();
    private UserBean userBean = new UserBean();
    private User user;

    public ReceivingUI(User user) {
        this.user = user;

        int choice;
        do {
            showSaleMenu();
            choice = Integer.parseInt(Utils.readKeyboard("choice"));
            switch (choice) {
                case 1:
                    createReceiving();
                    break;
                case 2:
                    readReceiving();
                    break;

                case 3:
                    listRecievings();
                    break;
                case 4:
                    listReceivingPerUser();
                    break;
                case 5:
                    choice = 5;
                    break;
            }
        } while (choice != 5);
    }

    private void showSaleMenu() {
        System.out.println("***************************");
        System.out.println("Receiving Management Screen");
        System.out.println("***************************");
        System.out.println("1. Create Receiving");
        System.out.println("2. Read Receiving");
        System.out.println("3. List Receiving");
        System.out.println("4. List Receiving per User");
        System.out.println("5. Back to Home");

    }

    private void createReceiving() {
        int batchNumber = Integer.parseInt(Utils.readKeyboard("batch number"));
        int productId = Integer.parseInt(Utils.readKeyboard("product id"));
        int quantity = Integer.parseInt(Utils.readKeyboard("quantity"));
        double buyingPrice = Double.parseDouble(Utils.readKeyboard("buying price"));
        double sellingPrice = Double.parseDouble(Utils.readKeyboard("selling price"));

        Receiving receiving = new Receiving(batchNumber, productId, quantity, buyingPrice, sellingPrice, user.getId());
        try {
            if (receivingBean.create(receiving)) {
                System.out.println("Product received successfully");
            } else {
                System.out.println("Could not register reception for this product");
            }
        } catch (SQLException e) {
            System.out.println("Some error occurred");
            e.printStackTrace();
        }
    }

    private void readReceiving() {
        int productId = Integer.parseInt(Utils.readKeyboard("receiving Id"));
        try {
            Receiving receiving = receivingBean.read(productId);
            if (receiving != null) {
                System.out.println(receiving);
            } else {
                System.out.println("Receiving for this product does not exist");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void listRecievings() {
        try {
            ArrayList<Receiving> receivingList = receivingBean.getReceivings();
            if (receivingList.size() > 0) {
                for (Receiving receiving : receivingList) {
                    System.out.println(receiving);
                }
            } else {
                System.out.println("No receiving yet");
            }

        } catch (SQLException e) {
            System.out.println("some error occurred");
            e.printStackTrace();
        }
    }

    private void listReceivingPerUser() {
        int userId = Integer.parseInt(Utils.readKeyboard("user id"));
        try {
            User user = userBean.read(userId);
            if (user != null) {
                ArrayList<Receiving> receivingList = receivingBean.getReceivings();
                if (receivingList.size() > 0) {
                    for (Receiving receiving : receivingList) {
                        if (receiving.getUserId() == userId) {
                            System.out.println(receiving);
                        }
                    }
                } else {
                    System.out.println("No receiving for this user");
                }
            } else {
                System.out.println("User does not exist");
            }
        } catch (SQLException e) {
            System.out.println("some error occurred");
            e.printStackTrace();
        }

    }
}
