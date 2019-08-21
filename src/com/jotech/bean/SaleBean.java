package com.jotech.bean;

import com.jotech.dao.DBHandler;
import com.jotech.entity.Sale;

import java.sql.*;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

public class SaleBean implements BeanInterface<Sale>{

@Override
    public boolean create(Sale sale) throws SQLException {
        Connection conn = DBHandler.getInstance().getConnection();
        String query = "INSERT INTO sale(date,productId,sellingPrice,quantity,userId) VALUES(?,?,?,?,?); ";
        PreparedStatement ps = conn.prepareStatement(query);
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        String dateString = simpleDateFormat.format(sale.getDate());
        ps.setDate(1, Date.valueOf(dateString));
        ps.setInt(2, sale.getProductId());
        ps.setDouble(3, sale.getSellingPrice());
        ps.setInt(4, sale.getQuantity());
        ps.setInt(5, sale.getUserId());
        return ps.executeUpdate() > 0;
    }
    @Override
    public Sale read(int id) throws SQLException {
        String query = "SELECT * FROM sale WHERE id=" + id;
        ResultSet rs = DBHandler.getInstance().executeQuery(query);
        Sale sale = new Sale();
        if (rs.next()) {
            setSaleDetails(rs, sale);
        }
        return sale;
    }

    public ArrayList<Sale> getSales() throws SQLException {
        String query = "SELECT * FROM sale";
        ResultSet rs = DBHandler.getInstance().executeQuery(query);
        ArrayList<Sale> saleList = new ArrayList<>();
        while (rs.next()) {
            Sale sale = new Sale();
            setSaleDetails(rs, sale);
            saleList.add(sale);

        }
        return saleList;
    }

    private void setSaleDetails(ResultSet rs, Sale sale) throws SQLException {
        sale.setId(rs.getInt("id"));
        sale.setDate(rs.getDate("date"));
        sale.setProductId(rs.getInt("productId"));
        sale.setSellingPrice(rs.getDouble("sellingPrice"));
        sale.setQuantity(rs.getInt("quantity"));
        sale.setUserId(rs.getInt("userId"));
    }
    @Override
    public boolean update(Sale sale) throws SQLException {
        Connection conn = DBHandler.getInstance().getConnection();

        String query = "UPDATE sale SET date=?,productId=?,sellingPrice=?,quantity=?,userId=? where id=?";
        PreparedStatement ps = conn.prepareStatement(query);

        ps.setDate(1, (Date) sale.getDate());
        ps.setInt(2, sale.getProductId());
        ps.setDouble(3, sale.getSellingPrice());
        ps.setInt(4, sale.getQuantity());
        ps.setInt(5, sale.getId());
        ps.setInt(6, sale.getUserId());
        return ps.executeUpdate() > 0;
    }
    @Override
    public boolean delete(Sale sale) throws SQLException {

        Connection conn = DBHandler.getInstance().getConnection();
        String query = "DELETE  FROM sale where id=?";
        PreparedStatement ps = conn.prepareStatement(query);
        ps.setInt(1, sale.getId());
        return ps.executeUpdate() > 0;
    }

}
