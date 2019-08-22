package com.jotech.util;

import com.jotech.bean.UserBean;
import com.jotech.entity.User;
import com.jotech.views.ProductUI;
import com.jotech.views.ReceivingUI;
import com.jotech.views.SaleUI;
import com.jotech.views.UserUI;

import java.sql.SQLException;

import static com.jotech.util.Utils.readKeyboard;

public class AppHelper {


    public void operate() {

        UserBean userBean = new UserBean();

        int id = Integer.parseInt(readKeyboard("user id"));

        try {
            User user = userBean.read(id);
            if (user != null) {
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

            }else{
                System.out.println("User with this id does not exist Try again....");
                operate();
            }


        } catch (SQLException e) {

            e.printStackTrace();
            System.exit(1);

        }

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
