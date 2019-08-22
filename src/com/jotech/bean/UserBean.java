package com.jotech.bean;

import com.jotech.dao.DBHandler;
import com.jotech.entity.User;
import com.jotech.enums.DataTypes;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class UserBean implements BeanInterface<User> {
    @Override
    public boolean create(User user) throws SQLException {
        String query = "INSERT INTO user(id,name) values(?,?)";
        DataTypes[] dataTypes = new DataTypes[2];
        Object[] params = new Object[2];
        dataTypes[0] = DataTypes.INTEGER;
        dataTypes[1] = DataTypes.STRING;
        params[0] = user.getId();
        params[1] = user.getName();
        if (DBHandler.getInstance().executeAction(query, params, dataTypes)) {
            DBHandler.getInstance().commitChanges();
            return true;
        } else {
            DBHandler.getInstance().rollbackChanges();
            return false;
        }
    }

    @Override
    public User read(int id) throws SQLException {
        String query = "SELECT * FROM user WHERE id=?";
        Object[] params = new Object[1];
        DataTypes[] dataTypes = new DataTypes[1];
        params[0] = id;
        dataTypes[0] = DataTypes.INTEGER;
        ResultSet rs = DBHandler.getInstance().executeQuery(query, params, dataTypes);
        User user = null;

        if (rs.next()) {
            user = new User();
            user.setId(rs.getInt("id"));
            user.setName(rs.getString("name"));
        }
        return user;
    }

    public ArrayList<User> getUsers() throws SQLException {
        String query = "SELECT * FROM user ";
        ArrayList<User> users = new ArrayList<>();
        ResultSet rs = DBHandler.getInstance().executeQuery(query, null, null);
        while (rs.next()) {
            User user = new User(rs.getInt("id"), rs.getString("name"));
            users.add(user);
        }
        return users;
    }

    @Override
    public boolean update(User user) throws SQLException {
        String query = "UPDATE user SET name=? WHERE id=?";

        DataTypes[] dataTypes = new DataTypes[2];
        Object[] params = new Object[2];

        dataTypes[0] = DataTypes.STRING;
        dataTypes[1] = DataTypes.INTEGER;

        params[0] = user.getName();
        params[1] = user.getId();
        if (DBHandler.getInstance().executeAction(query, params, dataTypes)) {
            DBHandler.getInstance().commitChanges();
            return true;
        } else {
            DBHandler.getInstance().rollbackChanges();
            return false;
        }

    }

    @Override
    public boolean delete(User user) throws SQLException {
        String query = "DELETE FROM user  WHERE id=?";
        DataTypes[] dataTypes = new DataTypes[1];
        Object[] params = new Object[1];
        dataTypes[0] = DataTypes.STRING;
        params[0] = user.getName();

        if(DBHandler.getInstance().executeAction(query, params, dataTypes)){
            DBHandler.getInstance().commitChanges();
            return true;
        }else{
            return false;
        }
    }
}
