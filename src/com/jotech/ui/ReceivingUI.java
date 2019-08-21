package com.jotech.ui;

import com.jotech.bean.ReceivingBean;
import com.jotech.bean.RunningBalanceProductBean;
import com.jotech.bean.UserBean;
import com.jotech.dao.DBHandler;
import com.jotech.entity.Receiving;
import com.jotech.entity.RunningBalanceProduct;
import com.jotech.entity.User;
import com.jotech.util.Utils;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;

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
                    updateReceiving();
                    break;
                case 4:
                    deleteRecieving();
                    break;
                case 5:
                    listRecievings();
                    break;
                case 6:
                    listReceivingPerUser();
                    break;
                case 7:
                    choice = 7;
                    break;
            }
        } while (choice != 7);
    }

    private void showSaleMenu() {
        System.out.println("***************************");
        System.out.println("Receiving Management Screen");
        System.out.println("***************************");
        System.out.println("1. Create Receiving");
        System.out.println("2. Read Receiving");
        System.out.println("3. Update  Receiving");
        System.out.println("4. Delete Receiving");
        System.out.println("5. List Receiving");
        System.out.println("6. List Receiving per User");
        System.out.println("7. Back to Home");

    }

    private void createReceiving() {
        int batchNumber = Integer.parseInt(Utils.readKeyboard("batch number"));
        Date date = new Date();
        int productId = Integer.parseInt(Utils.readKeyboard("product id"));
        int quantity = Integer.parseInt(Utils.readKeyboard("quantity"));
        double buyingPrice = Double.parseDouble(Utils.readKeyboard("buying price"));
        double sellingPrice = Double.parseDouble(Utils.readKeyboard("selling price"));

        Receiving receiving = new Receiving(batchNumber, date, productId, quantity, buyingPrice, sellingPrice, user.getId());

        RunningBalanceProduct runningBalanceProduct = null;
        try {
            if(runningBalanceProductBean.isProductHavingRunningBalance(productId)){
                runningBalanceProduct = runningBalanceProductBean.getRunningBalanceProduct(productId);
                runningBalanceProduct.setRunningBalance(runningBalanceProduct.getRunningBalance()+quantity);
                if(runningBalanceProductBean.updateRunningBalanceProduct(runningBalanceProduct) && receivingBean.create(receiving)){
                    System.out.println("Product received successfully");
                    DBHandler.getInstance().commitChanges();
                }
            }else{
                runningBalanceProduct = new RunningBalanceProduct(productId,quantity);
                if(runningBalanceProductBean.create(runningBalanceProduct) && receivingBean.create(receiving)){
                    System.out.println("Product received successfully");
                    DBHandler.getInstance().commitChanges();
                }

            }
        } catch (SQLException e) {
            DBHandler.getInstance().rollbackChanges();
            e.printStackTrace();
        }

    }

    private void readReceiving() {
        int productId = Integer.parseInt(Utils.readKeyboard("receiving Id"));
        Receiving receiving = null;
        try {
            receiving = receivingBean.read(productId);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        if (receiving == null) {
            System.out.println("receiving does not exist");
        }
    }

    private void updateReceiving() {
        int productId = Integer.parseInt(Utils.readKeyboard("receiving id"));
        Receiving receiving = null;
        try {
            receiving = receivingBean.read(productId);

        } catch (SQLException e) {
            e.printStackTrace();
        }
        if (receiving != null) {
            //TODO: You are not suppossed to Update receiving for now

        } else {
            System.out.println("The receiving does not exist");
        }

    }

    private void deleteRecieving() {
        int receivingId = Integer.parseInt(Utils.readKeyboard("receiving Id"));
        Receiving receiving = null;
        try {
            receiving = receivingBean.read(receivingId);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        if (receiving != null) {
            try {
                receivingBean.delete(receiving);
                System.out.println("Receiving deleted successfully");
                DBHandler.getInstance().commitChanges();
            } catch (SQLException e) {
                DBHandler.getInstance().rollbackChanges();
                e.printStackTrace();
            }
        } else {
            System.out.println("The receiving does not exist");
        }
    }

    private void listRecievings() {
        ArrayList<Receiving> receivingList = new ArrayList<>();
        try {
            receivingList = receivingBean.getReceivings();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        for (Receiving receiving : receivingList) {
            System.out.println(receiving);
        }

    }

    private void listReceivingPerUser() {
        User user = null;
        int userId = Integer.parseInt(Utils.readKeyboard("user id"));
        try {
            user = userBean.read(userId);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        if (user != null) {
            ArrayList<Receiving> receivingList = new ArrayList<>();
            try {
                receivingList = receivingBean.getReceivings();
            } catch (SQLException e) {
                e.printStackTrace();
            }
            for (Receiving receiving : receivingList) {
                if (receiving.getUserId() == userId) {
                    System.out.println(receiving);
                }
            }
        }
    }
}
