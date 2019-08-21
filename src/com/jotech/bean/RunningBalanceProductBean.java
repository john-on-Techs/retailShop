package com.jotech.bean;

import com.jotech.dao.DBHandler;
import com.jotech.entity.RunningBalanceProduct;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class RunningBalanceProductBean {
    public boolean create(RunningBalanceProduct runningBalanceProduct) throws SQLException {
        String query = "INSERT INTO runningBalanceProduct(id,productId,runningBalance) values(?,?,?)";
        Connection conn = DBHandler.getInstance().getConnection();
        PreparedStatement ps = conn.prepareStatement(query);
        ps.setInt(1,runningBalanceProduct.getId());
        ps.setInt(2,runningBalanceProduct.getProductId());
        ps.setInt(3,runningBalanceProduct.getRunningBalance());

        return ps.executeUpdate()>0;
    }
    public RunningBalanceProduct getRunningBalanceProduct(int productId) throws SQLException {
        String query = "SELECT * FROM runningBalanceProduct WHERE productId="+productId;
        ResultSet rs = DBHandler.getInstance().executeQuery(query);
        RunningBalanceProduct runningBalanceProduct  = new RunningBalanceProduct();

        if(rs.next()){
            runningBalanceProduct.setId(rs.getInt("id"));
            runningBalanceProduct.setProductId(rs.getInt("productId"));
            runningBalanceProduct.setRunningBalance(rs.getInt("runningBalance"));

        }
        return runningBalanceProduct;
    }
   public boolean isProductHavingRunningBalance(int productId) throws SQLException {
        String query ="SELECT COUNT(*) as 'count' FROM  runningBalanceProduct WHERE productId="+productId;
        ResultSet rs = DBHandler.getInstance().executeQuery(query);
        int count=0;
        if(rs.next()){
            count = rs.getInt("count");
        }
        return count>0;
    }

    public boolean updateRunningBalanceProduct(RunningBalanceProduct runningBalanceProduct) throws SQLException {
        String query = "UPDATE runningBalanceProduct SET runningBalance=? WHERE id=? AND productId=?";
        Connection conn =  DBHandler.getInstance().getConnection();
        PreparedStatement ps  = conn.prepareStatement(query);
        ps.setInt(1,runningBalanceProduct.getRunningBalance());
        ps.setInt(2,runningBalanceProduct.getId());
        ps.setInt(3,runningBalanceProduct.getProductId());

        return ps.executeUpdate()>0;
    }


}
