package com.jotech.bean;

import com.jotech.dao.DBHandler;
import com.jotech.entity.Product;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class ProductBean implements BeanInterface<Product>{
    @Override
    public boolean create(Product product) throws SQLException {
        Connection conn = DBHandler.getInstance().getConnection();
        String query = "INSERT INTO product(id,name,description) VALUES(?,?,?); ";
        PreparedStatement ps = conn.prepareStatement(query);
        ps.setInt(1, product.getId());
        ps.setString(2, product.getName());
        ps.setString(3, product.getDescription());
        return ps.executeUpdate() > 0;
    }
    @Override
    public Product read(int id) throws SQLException {
        String query = "SELECT * FROM product  WHERE id=" + id;
        ResultSet rs = DBHandler.getInstance().executeQuery(query);
        Product product = new Product();
        if (rs.next()) {
            product.setId(rs.getInt("id"));
            product.setName(rs.getString("name"));
            product.setDescription(rs.getString("description"));
        }
        return product;
    }

    public ArrayList<Product> getProducts() throws SQLException {
        String query = "SELECT * FROM product";
        ResultSet rs = DBHandler.getInstance().executeQuery(query);
        ArrayList<Product> productList = new ArrayList<>();
        while (rs.next()) {
            Product product = new Product(rs.getInt("id"), rs.getString("name"), rs.getString("description"));
            productList.add(product);

        }
        return productList;
    }
    @Override
    public boolean update(Product product) throws SQLException {
        Connection conn = DBHandler.getInstance().getConnection();

        String query = "UPDATE product SET name=?,description=? where id=?";
        PreparedStatement ps = conn.prepareStatement(query);

        ps.setString(1, product.getName());
        ps.setString(2, product.getDescription());
        ps.setInt(3, product.getId());
        return ps.executeUpdate() > 0;
    }
    @Override
    public boolean delete(Product product) throws SQLException {

        Connection conn = DBHandler.getInstance().getConnection();
        String query = "DELETE  FROM product where id=?";
        PreparedStatement ps = conn.prepareStatement(query);
        ps.setInt(1, product.getId());
        return ps.executeUpdate() > 0;
    }

}
