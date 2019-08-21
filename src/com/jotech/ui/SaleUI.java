package com.jotech.ui;

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
import java.util.Date;
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
                    updateSale();
                    break;
                case 4:
                    deleteSale();
                    break;
                case 5:
                    listSales();
                    break;
                case 6:
                    listSalesPerUser();
                    break;
                case 7:
                    choice = 7;
                    break;
            }
        } while (choice != 7);
    }

    private void showSaleMenu() {
        System.out.println("***************************");
        System.out.println("Sale Management Screen");
        System.out.println("***************************");
        System.out.println("1. Create Sale");
        System.out.println("2. Read Sale");
        System.out.println("3. Update Sale");
        System.out.println("4. Delete Sale");
        System.out.println("5. List Sale");
        System.out.println("6. List Sale Per User");
        System.out.println("7. Back to Home");

    }

    private void createSale() {
        int productId = Integer.parseInt(Utils.readKeyboard("product id"));
        Receiving receiving = null;
        RunningBalanceProduct runningBalanceProduct = null;
        try {
            receiving = receivingBean.read(productId);

        } catch (SQLException e) {
            Logger.getLogger(AppHelper.class.getName()).log(Level.SEVERE, null, e);
        }


        if (receiving != null) {

            try {
                runningBalanceProduct = runningBalanceProductBean.getRunningBalanceProduct(productId);
            } catch (SQLException e) {
                e.printStackTrace();
            }
            System.out.println(runningBalanceProduct);
            if (runningBalanceProduct != null) {
                int quantity = Integer.parseInt(Utils.readKeyboard("quantity"));
                if (runningBalanceProduct.getRunningBalance() > quantity) {
                    Date date = new Date();
                    Sale sale = new Sale(date, productId, receiving.getSellingPrice(), quantity, user.getId());
                    try {
                        runningBalanceProduct.setRunningBalance(runningBalanceProduct.getRunningBalance() - quantity);
                        if (saleBean.create(sale) && runningBalanceProductBean.updateRunningBalanceProduct(runningBalanceProduct)) {
                            DBHandler.getInstance().commitChanges();
                        }
                    } catch (SQLException e) {
                        DBHandler.getInstance().rollbackChanges();
                        e.printStackTrace();
                    }

                }
                else{
                    System.out.println("Not enough quantity existing! Remaining is "+runningBalanceProduct.getRunningBalance());
                }
            }
        }
    }

    private void readSale() {
        int saleId = Integer.parseInt(Utils.readKeyboard("sale Id"));
        Sale sale = null;
        try {
            sale = saleBean.read(saleId);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        if (sale == null) {
            System.out.println("sale does not exist");
        }
    }

    private void updateSale() {
        int saleId = Integer.parseInt(Utils.readKeyboard("sale id"));
        Sale sale = null;
        try {
            sale = saleBean.read(saleId);

        } catch (SQLException e) {
            e.printStackTrace();
        }
        if (sale != null) {
            //TODO: You are not suppossed to Update sale for now

        } else {
            System.out.println("The sale does not exist");
        }

    }

    private void deleteSale() {
        int saleId = Integer.parseInt(Utils.readKeyboard("sale Id"));
        Sale sale = null;
        try {
            sale = saleBean.read(saleId);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        if (sale != null) {
            try {
                if(saleBean.delete(sale)){
                    System.out.println("Sale deleted successfully");
                    DBHandler.getInstance().commitChanges();
                }
            } catch (SQLException e) {
                DBHandler.getInstance().rollbackChanges();
                e.printStackTrace();
            }
        } else {
            System.out.println("The sale does not exist");
        }
    }

    private void listSales() {
        ArrayList<Sale> saleList = new ArrayList<>();
        try {
            saleList = saleBean.getSales();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        for (Sale sale : saleList) {
            System.out.println(sale);
        }

    }

    private void listSalesPerUser() {
        User user = null;
        int userId = Integer.parseInt(Utils.readKeyboard("user id"));
        try {
            user = userBean.read(userId);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        if (user != null) {
            ArrayList<Sale> saleList = new ArrayList<>();
            try {
                saleList = saleBean.getSales();
            } catch (SQLException e) {
                e.printStackTrace();
            }
            for (Sale sale : saleList) {
                if (sale.getUserId() == userId) {
                    System.out.println(sale);
                }
            }
        }
    }
}
