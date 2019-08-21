package com.jotech.bean;

import com.jotech.dao.DBHandler;
import com.jotech.entity.Receiving;

import java.sql.*;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

public class ReceivingBean implements BeanInterface<Receiving>{
    @Override
    public boolean create(Receiving receiving) throws SQLException {
        String query = "INSERT INTO receiving(batchNo,date,productId,quantity,buyingPrice,sellingPrice,userId) values(?,?,?,?,?,?,?)";
        Connection conn = DBHandler.getInstance().getConnection();
        PreparedStatement ps = conn.prepareStatement(query);
        ps.setInt(1, receiving.getBatchNo());
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        String dateString = simpleDateFormat.format(receiving.getDate());
        ps.setDate(2, Date.valueOf(dateString));
        ps.setInt(3, receiving.getProductId());
        ps.setInt(4, receiving.getQuantity());
        ps.setDouble(5, receiving.getBuyingPrice());
        ps.setDouble(6, receiving.getSellingPrice());
        ps.setInt(7,receiving.getUserId());

        return ps.executeUpdate() > 0;
    }

    @Override
    public Receiving read(int id) throws SQLException {
        String query = "SELECT * FROM receiving WHERE productId=" + id+" LIMIT 1";
        ResultSet rs = DBHandler.getInstance().executeQuery(query);
        Receiving receiving = new Receiving();
        if (rs.next()) {
            setReceivingObject(rs, receiving);
        }
        return receiving;
    }


    public ArrayList<Receiving> getReceivings() throws SQLException {
        String query = "SELECT * FROM receiving";
        ResultSet rs = DBHandler.getInstance().executeQuery(query);
        ArrayList<Receiving> receivingList = new ArrayList<>();
        while (rs.next()) {
            Receiving receiving = new Receiving();
            setReceivingObject(rs, receiving);
            receivingList.add(receiving);

        }
        return receivingList;

    }

    private void setReceivingObject(ResultSet rs, Receiving receiving) throws SQLException {
        receiving.setBatchNo(rs.getInt("batchNo"));
        receiving.setDate(rs.getDate("date"));
        receiving.setProductId(rs.getInt("productId"));
        receiving.setQuantity(rs.getInt("quantity"));
        receiving.setBuyingPrice(rs.getDouble("buyingPrice"));
        receiving.setSellingPrice(rs.getDouble("sellingPrice"));
    }
    @Override
    public boolean update(Receiving receiving) throws SQLException {
        Connection conn = DBHandler.getInstance().getConnection();

        String query = "UPDATE receiving SET date=?,productId=?,quantity=?,buyingPrice=?,sellingPrice=? where batchNo=?";
        PreparedStatement ps = conn.prepareStatement(query);

        ps.setDate(1, (Date) receiving.getDate());
        ps.setInt(2, receiving.getProductId());
        ps.setInt(3, receiving.getQuantity());
        ps.setDouble(4, receiving.getBuyingPrice());
        ps.setDouble(5, receiving.getSellingPrice());
        ps.setDouble(6, receiving.getBatchNo());
        return ps.executeUpdate() > 0;
    }
    @Override
    public boolean delete(Receiving receiving) throws SQLException {

        Connection conn = DBHandler.getInstance().getConnection();
        String query = "DELETE  FROM receiving where batchNo=?";
        PreparedStatement ps = conn.prepareStatement(query);
        ps.setInt(1, receiving.getBatchNo());
        return ps.executeUpdate() > 0;
    }

}
