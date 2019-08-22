package com.jotech.bean;

import com.jotech.dao.DBHandler;
import com.jotech.entity.RunningBalanceProduct;
import com.jotech.enums.DataTypes;

import javax.xml.crypto.Data;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class RunningBalanceProductBean {
    public boolean create(RunningBalanceProduct runningBalanceProduct) throws SQLException {
        String query = "INSERT INTO runningBalanceProduct(id,productId,runningBalance) values(?,?,?)";
        DataTypes[]  dataTypes = new DataTypes[3];
        Object[] params= new Object[3];

        dataTypes[0]=DataTypes.INTEGER;
        dataTypes[1]=DataTypes.INTEGER;
        dataTypes[2]=DataTypes.INTEGER;

        params[0]=runningBalanceProduct.getId();
        params[1]=runningBalanceProduct.getProductId();
        params[2] = runningBalanceProduct.getRunningBalance();

        return DBHandler.getInstance().executeAction(query,params,dataTypes);
    }
    public RunningBalanceProduct getRunningBalanceProduct(int productId) throws SQLException {
        String query = "SELECT * FROM runningBalanceProduct WHERE productId=?";
        DataTypes[] dataTypes = new DataTypes[1];
        Object[] params = new Object[1];
        params[0]=productId;
        dataTypes[0]=DataTypes.INTEGER;
        ResultSet rs = DBHandler.getInstance().executeQuery(query,params,dataTypes);
        RunningBalanceProduct runningBalanceProduct  = new RunningBalanceProduct();

        if(rs.next()){
            runningBalanceProduct.setId(rs.getInt("id"));
            runningBalanceProduct.setProductId(rs.getInt("productId"));
            runningBalanceProduct.setRunningBalance(rs.getInt("runningBalance"));

        }
        return runningBalanceProduct;
    }
   public boolean isProductHavingRunningBalance(int productId) throws SQLException {
        String query ="SELECT COUNT(*) as 'count' FROM  runningBalanceProduct WHERE productId=?";
       DataTypes[] dataTypes = new DataTypes[1];
       Object[] params = new Object[1];
       params[0]=productId;
       dataTypes[0]=DataTypes.INTEGER;
        ResultSet rs = DBHandler.getInstance().executeQuery(query,params,dataTypes);
        int count=0;
        if(rs.next()){
            count = rs.getInt("count");
        }
        return count>0;
    }

    public boolean updateRunningBalanceProduct(RunningBalanceProduct runningBalanceProduct) throws SQLException {
        String query = "UPDATE runningBalanceProduct SET runningBalance=? WHERE id=? AND productId=?";
        DataTypes[]  dataTypes = new DataTypes[3];
        Object[] params= new Object[3];

        dataTypes[0]=DataTypes.INTEGER;
        dataTypes[1]=DataTypes.INTEGER;
        dataTypes[2]=DataTypes.INTEGER;

        params[0] = runningBalanceProduct.getRunningBalance();
        params[1]=runningBalanceProduct.getId();
        params[2]=runningBalanceProduct.getProductId();

        return DBHandler.getInstance().executeAction(query,params,dataTypes);
    }


}
