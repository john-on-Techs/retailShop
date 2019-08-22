package com.jotech.bean;

import com.jotech.dao.DBHandler;
import com.jotech.entity.Receiving;
import com.jotech.entity.RunningBalanceProduct;
import com.jotech.entity.Sale;
import com.jotech.enums.DataTypes;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class SaleBean implements BeanInterface<Sale> {
    ReceivingBean receivingBean = new ReceivingBean();
    private RunningBalanceProductBean runningBalanceProductBean = new RunningBalanceProductBean();

    @Override
    public boolean create(Sale sale) throws SQLException {
        String query = "INSERT INTO sale(date,productId,sellingPrice,quantity,userId) VALUES(NOW(),?,?,?,?); ";
        DataTypes[] dataTypes = new DataTypes[4];
        Object[] params = new Object[4];


        dataTypes[0] = DataTypes.INTEGER;
        dataTypes[1] = DataTypes.DOUBLE;
        dataTypes[2] = DataTypes.INTEGER;
        dataTypes[3] = DataTypes.INTEGER;

        params[0] = sale.getProductId();
        //param[1] updated within the receiving table
        params[2] = sale.getQuantity();
        params[3] = sale.getUserId();


        Receiving receiving = receivingBean.read(sale.getProductId());
        if (receiving != null) {
            RunningBalanceProduct runningBalanceProduct = runningBalanceProductBean.getRunningBalanceProduct(sale.getProductId());
            if (runningBalanceProduct != null) {
                if (runningBalanceProduct.getRunningBalance() > sale.getQuantity()) {
                    runningBalanceProduct.setRunningBalance(runningBalanceProduct.getRunningBalance() - sale.getQuantity());
                    // update the selling price from 0 to the selling price within the receiving table
                    sale.setSellingPrice(receiving.getSellingPrice());
                    //add the parameter for selling price to sale object
                    params[1] = sale.getSellingPrice();
                    if (DBHandler.getInstance().executeAction(query, params, dataTypes) && runningBalanceProductBean.updateRunningBalanceProduct(runningBalanceProduct)) {
                        DBHandler.getInstance().commitChanges();
                        return true;

                    } else {
                        DBHandler.getInstance().rollbackChanges();
                    }
                }
            }
        }

        return false;

    }

    @Override
    public Sale read(int id) throws SQLException {
        String query = "SELECT * FROM sale WHERE id=?";
        DataTypes[] dataTypes = new DataTypes[1];
        Object[] params = new Object[1];
        dataTypes[0] = DataTypes.INTEGER;
        params[0] = id;
        ResultSet rs = DBHandler.getInstance().executeQuery(query, params, dataTypes);
        Sale sale = null;
        if (rs.next()) {
            sale = new Sale();
            setSaleDetails(rs, sale);
        }
        return sale;
    }

    public ArrayList<Sale> getSales() throws SQLException {
        String query = "SELECT * FROM sale";
        ResultSet rs = DBHandler.getInstance().executeQuery(query, null, null);
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

        return false;
    }

    @Override
    public boolean delete(Sale sale) throws SQLException {
        return false;
    }

}
