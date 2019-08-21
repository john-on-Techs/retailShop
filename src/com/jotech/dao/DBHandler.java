package com.jotech.dao;

import java.io.FileReader;
import java.io.IOException;
import java.sql.*;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;

public final class DBHandler {
    private static Connection connection;
    private static Statement statement;
    private static DBHandler dbHandler = null;
    private static String DB_DRIVER = null;
    private static String DB_URL = null;
    private static String DB_USER = null;
    private static String DB_PASSWORD = null;
    private static String defaultUsername=null;
    private static int defaultUserId=0;

    static {
        readPropertiesFile();
        createConnection();
        setupUserTable();
        setupProductTable();
        setupReceivingTable();
        setupSaleTable();
        setupRunningBalanceProductTable();
    }

    private static void readPropertiesFile() {

        try (FileReader reader = new FileReader("db.properties")) {
            Properties p = new Properties();
            p.load(reader);
            DB_DRIVER = p.getProperty("DB_DRIVER");
            DB_URL = p.getProperty("DB_URL");
            DB_USER = p.getProperty("DB_USER");
            DB_PASSWORD = p.getProperty("DB_PASSWORD");
            defaultUserId = Integer.parseInt(p.getProperty("default_id"));
            defaultUsername = p.getProperty("default_name");
        } catch (IOException e) {
            e.printStackTrace();
        }
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
            Class.forName(DB_DRIVER).newInstance();
            connection = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
            connection.setAutoCommit(false);

        } catch (IllegalAccessException | InstantiationException | ClassNotFoundException | SQLException e) {
            Logger.getLogger(DBHandler.class.getName()).log(Level.SEVERE, null, e);
            e.printStackTrace();
        }
    }

    // or
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

    public void commitChanges() {
        try {
            connection.commit();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void rollbackChanges() {
        try {
            connection.rollback();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private static void setupUserTable() {
        String query1 = "CREATE TABLE IF NOT EXISTS `user` (\n" +
                "  `id` INT(11) NOT NULL,\n" +
                "  `name` VARCHAR(100) NOT NULL,\n" +
                "  PRIMARY KEY (`id`)\n" +
                ") ENGINE=InnoDB DEFAULT CHARSET=latin1";


        String query2 = "SELECT COUNT(*) AS 'count' FROM user";
        String query3 = "INSERT INTO user(id,name) values ( "+defaultUserId+", '" +defaultUsername+"')";

        try {
            statement = connection.createStatement();
            statement.execute(query1);

            ResultSet rs = statement.executeQuery(query2);

            if (rs.next()) {
                int count = rs.getInt("count");
                //if no any user record insert the first record
                if (!(count > 0)) {
                    statement.executeUpdate(query3);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private static void setupProductTable() {
        String query = "CREATE TABLE IF NOT EXISTS `product` (\n" +
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

    private static void setupReceivingTable() {
        String query = "CREATE TABLE IF NOT EXISTS `receiving`(\n" +
                "    `receiveId`    INT   NOT NULL AUTO_INCREMENT,\n" +
                "    `batchNo`      int(11)        NOT NULL,\n" +
                "    `date`         DATE          NOT NULL,\n" +
                "    `productId`    INT(11)        NOT NULL,\n" +
                "    `quantity`     INT(11)        NOT NULL,\n" +
                "    `buyingPrice`  DECIMAL(10, 2) NOT NULL,\n" +
                "    `sellingPrice` DECIMAL(10, 2) NOT NULL,\n" +
                "    `userId`       INT(11)    NOT NULL,\n" +
                "    PRIMARY KEY (`receiveId`),\n" +
                "    FOREIGN KEY (`productId`) REFERENCES `product` (`id`),\n" +
                "    FOREIGN KEY (`userId`) REFERENCES `user` (`id`)\n" +
                ") ENGINE = InnoDB\n" +
                "  DEFAULT CHARSET = latin1";
        try {
            statement = connection.createStatement();
            statement.execute(query);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private static void setupSaleTable() {
        String query = "CREATE TABLE IF NOT EXISTS `sale` (\n" +
                "  `id` INT(11) NOT NULL AUTO_INCREMENT,\n" +
                "  `date` DATE NOT NULL,\n" +
                "  `productId` INT NOT NULL,\n" +
                "  `sellingPrice` DECIMAL(10,2) NOT NULL,\n" +
                "  `quantity` INT NOT NULL,\n" +
                "  `userId` INT NOT NULL,\n" +
                "  PRIMARY KEY (`id`),\n" +
                " FOREIGN KEY (`productId`) REFERENCES product(id) ON DELETE CASCADE ON UPDATE  CASCADE ,\n" +
                " FOREIGN KEY (`userId`) REFERENCES user(id) ON DELETE CASCADE ON UPDATE  CASCADE \n" +
                ") ENGINE=InnoDB DEFAULT CHARSET=latin1";
        try {
            statement = connection.createStatement();
            statement.execute(query);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private static void setupRunningBalanceProductTable() {
        String query = "CREATE TABLE IF NOT EXISTS `runningBalanceProduct` (\n" +
                "  `id` INT(11) NOT NULL AUTO_INCREMENT,\n" +
                "  `productId` INT NOT NULL,\n" +
                "  `runningBalance` INT NOT NULL,\n" +
                "  PRIMARY KEY (`id`,`productId`),\n" +
                " FOREIGN KEY (`productId`) REFERENCES product(id) ON DELETE CASCADE ON UPDATE  CASCADE\n" +
                ") ENGINE=InnoDB DEFAULT CHARSET=latin1";
        try {
            statement = connection.createStatement();
            statement.execute(query);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

}
