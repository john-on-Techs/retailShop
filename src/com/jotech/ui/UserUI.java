package com.jotech.ui;

import com.jotech.bean.UserBean;
import com.jotech.dao.DBHandler;
import com.jotech.entity.User;
import com.jotech.util.AppHelper;
import com.jotech.util.Utils;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

public class UserUI {
    private UserBean userBean = new UserBean();

    public UserUI() {

        int choice;
        do {
            showUserMenu();
            choice = Integer.parseInt(Utils.readKeyboard("choice"));
            switch (choice) {
                case 1:
                    createUser();
                    break;
                case 2:
                    readUser();
                    break;
                case 3:
                    updateUser();
                    break;
                case 4:
                    deleteUser();
                    break;
                case 5:
                    listUsers();
                    break;
                case 6:
                    choice =6;
                    break;
            }
        }while(choice !=6);
    }

    private void showUserMenu() {
        System.out.println("***************************");
        System.out.println("User Management Screen");
        System.out.println("***************************");
        System.out.println("1. Create User");
        System.out.println("2. Read User");
        System.out.println("3. Update User");
        System.out.println("4. Delete User");
        System.out.println("5. List User");
        System.out.println("6. Back to Home");

    }

    private void createUser() {
        int userId = Integer.parseInt(Utils.readKeyboard("user id"));
        String name = Utils.readKeyboard("user name");
        User user = new User(userId,name);
        try {
            if (userBean.create(user)) {
                DBHandler.getInstance().commitChanges();
                System.out.println("User added successfully");
            }

        } catch (SQLException e) {
            DBHandler.getInstance().rollbackChanges();
            Logger.getLogger(AppHelper.class.getName()).log(Level.SEVERE, null, e);
            e.printStackTrace();
        }
    }

    private void readUser() {
        int userId = Integer.parseInt(Utils.readKeyboard("user Id"));
        User user = null;
        try {
          user = userBean.read(userId);
            System.out.println(user.getName());
        } catch (SQLException e) {
            e.printStackTrace();
        }
        if(user ==null){
            System.out.println("user does not exist");
        }
    }

    private void updateUser() {
        int userId = Integer.parseInt(Utils.readKeyboard("user Id"));
        User user = null;
        try {
            user = userBean.read(userId);
            System.out.println(user.getName());
        } catch (SQLException e) {
            e.printStackTrace();
        }
        if(user != null){
            String name = Utils.readKeyboard(" new name");

            if(name != null && !name.isEmpty()){
                user.setName(name);
            }

            try {
                if(userBean.update(user)){
                    DBHandler.getInstance().commitChanges();
                    System.out.println("User updated successfully");
                }

            } catch (SQLException e) {
                DBHandler.getInstance().rollbackChanges();
                e.printStackTrace();
            }
        }
        else{
            System.out.println("The user does not exist");
        }

    }

    private void deleteUser() {
        int productId = Integer.parseInt(Utils.readKeyboard("user Id"));
        User user = null;
        try {
            user = userBean.read(productId);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        if(user != null){
            try {
                if(userBean.delete(user)){
                    System.out.println("User deleted successfully");
                    DBHandler.getInstance().commitChanges();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        else{
            System.out.println("The user does not exist");
        }
    }

    private void listUsers() {
        ArrayList<User> userList = new ArrayList<>();
        try {
            userList = userBean.getUsers();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        for(User user: userList){
            System.out.println(user.getName());
        }

    }
}
