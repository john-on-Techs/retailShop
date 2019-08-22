package com.jotech.bean;

import com.jotech.dao.DBHandler;
import com.jotech.entity.Product;
import com.jotech.enums.DataTypes;

import javax.xml.crypto.Data;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class ProductBean implements BeanInterface<Product>{
    @Override
    public boolean create(Product product) throws SQLException {
        String query = "INSERT INTO product(id,name,description) VALUES(?,?,?); ";

        DataTypes[] dataTypes = new DataTypes[3];
        dataTypes[0]=DataTypes.INTEGER;
        dataTypes[1]=DataTypes.STRING;
        dataTypes[2]=DataTypes.STRING;

        Object[] params = new Object[3];
        params[0] = product.getId();
        params[1]=product.getName();
        params[2]=product.getDescription();
        if(DBHandler.getInstance().executeAction(query,params,dataTypes)){
            DBHandler.getInstance().commitChanges();
            return true;
        }else{
            DBHandler.getInstance().rollbackChanges();
            return false;
        }

    }
    @Override
    public Product read(int id) throws SQLException {

        DataTypes[] dataTypes = new DataTypes[1];
        Object[] params = new Object[1];
        String query = "SELECT * FROM product  WHERE id=?";
        dataTypes[0]=DataTypes.INTEGER;
        params[0]= id;

        ResultSet rs = DBHandler.getInstance().executeQuery(query,params,dataTypes);
        Product product = null;
        if (rs.next()) {
            product = new Product();
            product.setId(rs.getInt("id"));
            product.setName(rs.getString("name"));
            product.setDescription(rs.getString("description"));
        }
        return product;
    }

    public ArrayList<Product> getProducts() throws SQLException {
        String query = "SELECT * FROM product";
        ResultSet rs = DBHandler.getInstance().executeQuery(query,null,null);
        ArrayList<Product> productList = new ArrayList<>();
        while (rs.next()) {
            Product product = new Product(rs.getInt("id"), rs.getString("name"), rs.getString("description"));
            productList.add(product);

        }
        return productList;
    }
    @Override
    public boolean update(Product product) throws SQLException {
        String query = "UPDATE product SET name=?,description=? where id=?";
        DataTypes[] dataTypes = new DataTypes[3];
        Object[] params = new Object[3];
        params[0]=product.getName();
        params[1]=product.getDescription();
        params[2]=product.getId();
        dataTypes[0]=DataTypes.STRING;
        dataTypes[1]=DataTypes.STRING;
        dataTypes[2]=DataTypes.INTEGER;
        if(DBHandler.getInstance().executeAction(query,params,dataTypes)){
            DBHandler.getInstance().commitChanges();
            return true;
        }else{
            DBHandler.getInstance().rollbackChanges();
            return false;
        }

    }
    @Override
    public boolean delete(Product product) throws SQLException {


        String query = "DELETE  FROM product where id=?";
        DataTypes[] dataTypes = new DataTypes[1];
        Object[] params = new Object[1];
        dataTypes[0]=DataTypes.INTEGER;
        params[0]= product.getId();
        if(DBHandler.getInstance().executeAction(query,params,dataTypes)){
            DBHandler.getInstance().commitChanges();
            return true;
        }else{
            DBHandler.getInstance().rollbackChanges();
            return false;
        }


    }

}
