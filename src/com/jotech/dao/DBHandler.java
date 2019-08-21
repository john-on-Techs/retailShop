package com.jotech.dao;

import java.sql.*;
import java.util.logging.Level;
import java.util.logging.Logger;

public final class DBHandler {
    private static Connection connection;
    private static Statement statement;
    private static DBHandler dbHandler = null;

    static {
        createConnection();
        setupUserTable();
        setupProductTable();
        setupReceivingTable();
        setupSaleTable();
        setupRunningBalanceProductTable();
    }

    private DBHandler() {
    }

    public static DBHandler getInstance() {
        if (dbHandler == null) {
            synchronized (DBHandler.class) {
                dbHandler = new DBHandler();
            }
        }
        return dbHandler;
    }

    private static void createConnection() {
        try {
            String DB_DRIVER = "com.mysql.cj.jdbc.Driver";
            Class.forName(DB_DRIVER).newInstance();
            String DB_URL = "jdbc:mysql://localhost/retail";
            String DB_USER = "root";
            String DB_PASSWORD = "Franjescal 1997";
            connection = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
            connection.setAutoCommit(false);

        } catch (IllegalAccessException | InstantiationException | ClassNotFoundException | SQLException e) {
            Logger.getLogger(DBHandler.class.getName()).log(Level.SEVERE, null, e);
            e.printStackTrace();
        }
    }

    public boolean execAction(String query) throws SQLException {
        statement = connection.createStatement();
        statement.execute(query);
        return true;
    }

    public ResultSet executeQuery(String query) throws SQLException {
        statement = connection.createStatement();
        return statement.executeQuery(query);
    }

    public Connection getConnection() {
        return connection;
    }
    public void commitChanges(){
        try {
            connection.commit();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    public void rollbackChanges(){
        try {
            connection.rollback();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    private static void setupUserTable(){
        String query1 ="CREATE TABLE IF NOT EXISTS `user` (\n" +
                "  `id` INT(11) NOT NULL,\n" +
                "  `name` VARCHAR(100) NOT NULL,\n" +
                "  PRIMARY KEY (`id`)\n" +
                ") ENGINE=InnoDB DEFAULT CHARSET=latin1";
       // String query2 ="INSERT  INTO user(id, name) values (1,'jotech')";

        try {
            statement = connection.createStatement();
            statement.addBatch(query1);
           // statement.addBatch(query2);
            statement.executeBatch();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    private static void setupProductTable(){
        String query ="CREATE TABLE IF NOT EXISTS `product` (\n" +
                "  `id` INT(11) NOT NULL,\n" +
                "  `name` VARCHAR(100) NOT NULL,\n" +
                "  `description` VARCHAR(255) NOT NULL,\n" +
                "  PRIMARY KEY (`id`)\n" +
                ") ENGINE=InnoDB DEFAULT CHARSET=latin1";
        try {
            statement = connection.createStatement();
            statement.execute(query);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    private static void setupReceivingTable(){
        String query ="CREATE TABLE IF NOT EXISTS `receiving` (\n" +
                "  `batchNo` INT(11) NOT NULL,\n" +
                "  `date` DATE NOT NULL,\n" +
                "  `productId` INT NOT NULL,\n" +
                "  `quantity` INT NOT NULL,\n" +
                "  `buyingPrice` DECIMAL(10,2) NOT NULL,\n" +
                "  `sellingPrice` DECIMAL(10,2) NOT NULL,\n" +
                "  `userId` INT NOT NULL,\n" +
                "  PRIMARY KEY (`batchNo`),\n" +
                " FOREIGN KEY (`productId`) REFERENCES product(id),\n" +
                " FOREIGN KEY (`userId`) REFERENCES user(id) \n" +
                ") ENGINE=InnoDB DEFAULT CHARSET=latin1";
        try {
            statement = connection.createStatement();
            statement.execute(query);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    private static void setupSaleTable(){
        String query ="CREATE TABLE IF NOT EXISTS `sale` (\n" +
                "  `id` INT(11) NOT NULL AUTO_INCREMENT,\n" +
                "  `date` DATE NOT NULL,\n" +
                "  `productId` INT NOT NULL,\n" +
                "  `sellingPrice` DECIMAL(10,2) NOT NULL,\n" +
                "  `quantity` INT NOT NULL,\n" +
                "  `userId` INT NOT NULL,\n" +
                "  PRIMARY KEY (`id`),\n" +
                " FOREIGN KEY (`productId`) REFERENCES product(id) ,\n" +
                " FOREIGN KEY (`userId`) REFERENCES user(id)\n" +
                ") ENGINE=InnoDB DEFAULT CHARSET=latin1";
        try {
            statement = connection.createStatement();
            statement.execute(query);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    private static void setupRunningBalanceProductTable(){
        String query ="CREATE TABLE IF NOT EXISTS `runningBalanceProduct` (\n" +
                "  `id` INT(11) NOT NULL AUTO_INCREMENT,\n" +
                "  `productId` INT NOT NULL,\n" +
                "  `runningBalance` INT NOT NULL,\n" +
                "  PRIMARY KEY (`id`,`productId`),\n" +
                " FOREIGN KEY (`productId`) REFERENCES product(id)\n" +
                ") ENGINE=InnoDB DEFAULT CHARSET=latin1";
        try {
            statement = connection.createStatement();
            statement.execute(query);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

}
