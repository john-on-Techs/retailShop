package com.jotech.util;

import com.jotech.bean.*;
import com.jotech.entity.User;
import com.jotech.ui.ProductUI;
import com.jotech.ui.ReceivingUI;
import com.jotech.ui.SaleUI;
import com.jotech.ui.UserUI;

import java.sql.SQLException;

import static com.jotech.util.Utils.readKeyboard;

public class AppHelper {
    private User user = null;

    public void operate() {

        UserBean userBean = new UserBean();

        int id = Integer.parseInt(readKeyboard("user id"));

        try {
            user = userBean.read(id);
        } catch (SQLException e) {

            e.printStackTrace();
            System.exit(1);

        }
        //System.out.println(user.getId());
        int choice = 0;
        do {

            //show Menu
            showMenu();

            //get user input and convert it into integer
            try {
                choice = Integer.parseUnsignedInt(readKeyboard("choice"));
            } catch (NumberFormatException e) {
                System.out.println("***** Invalid choice *****");
                continue;
            }
            //switch choice
            switch (choice) {

                case 1:
                    ProductUI productUI = new ProductUI();
                    break;
                case 2:
                    ReceivingUI receivingUI = new ReceivingUI(user);
                    break;
                case 3:
                    SaleUI saleUI = new SaleUI(user);
                    break;
                case 4:
                    UserUI userUI = new UserUI();
                    break;

                case 5:
                    user = null;
                    operate();
                    break;

            }

        } while (choice != 100 && user != null);

    }

    private static void showMenu() {
        System.out.println("***************************");
        System.out.println("RetailShop Screen");
        System.out.println("***************************");
        System.out.println("Select an a action :");
        System.out.println("1. Products");
        System.out.println("2. Receiving");
        System.out.println("3. Sales");
        System.out.println("4. Manage Users");
        System.out.println("5. Logout");
        System.out.println("100. To quit!!");
        System.out.println("_____________________________________");
        System.out.print("answer: ");
    }
}
