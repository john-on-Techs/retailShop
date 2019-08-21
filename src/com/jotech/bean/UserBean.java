package com.jotech.bean;

import com.jotech.dao.DBHandler;
import com.jotech.entity.User;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class UserBean implements BeanInterface<User>{
    @Override
    public boolean create(User user) throws SQLException {
        String query = "INSERT INTO user(id,name) values(?,?)";
        Connection conn = DBHandler.getInstance().getConnection();
        PreparedStatement ps = conn.prepareStatement(query);
        ps.setInt(1,user.getId());
        ps.setString(2,user.getName());
        return ps.executeUpdate()>0;
    }
    @Override
    public User read(int id) throws SQLException {
        String query = "SELECT * FROM user WHERE id="+id;
        ResultSet rs = DBHandler.getInstance().executeQuery(query);
        User user  = new User();

        if(rs.next()){
            user.setId(rs.getInt("id"));
            user.setName(rs.getString("name"));
        }
        return user;
    }

    public ArrayList<User> getUsers() throws SQLException {
        String query = "SELECT * FROM user ";
        ArrayList<User> users = new ArrayList<>();
        ResultSet rs = DBHandler.getInstance().executeQuery(query);
        while (rs.next()){
            User user = new User(rs.getInt("id"),rs.getString("name"));
            users.add(user);
        }
        return users;
    }
    @Override
    public boolean update(User user) throws SQLException {
        String query = "UPDATE user SET id=?, name=? WHERE id=?";
        Connection conn =  DBHandler.getInstance().getConnection();
        PreparedStatement ps  = conn.prepareStatement(query);
        ps.setInt(1,user.getId());
        ps.setString(2,user.getName());
        ps.setInt(3,user.getId());
        return ps.executeUpdate()>0;
    }
    @Override
    public boolean delete(User user) throws SQLException {
        String query = "DELETE FROM user  WHERE id=?";
        Connection conn =  DBHandler.getInstance().getConnection();
        PreparedStatement ps  = conn.prepareStatement(query);
        ps.setInt(1,user.getId());
        return ps.executeUpdate()>0;
    }
}
