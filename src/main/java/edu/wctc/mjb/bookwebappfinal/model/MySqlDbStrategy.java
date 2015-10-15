/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.wctc.mjb.bookwebappfinal.model;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import javax.sql.DataSource;

/**
 *
 * @author Brester
 */
public class MySqlDbStrategy implements DbStrategy {

    private Connection conn;

    @Override
    public void openConnection(String driverClass, String url, String userName, String password) throws Exception {

        Class.forName(driverClass);
        conn = DriverManager.getConnection(url, userName, password);

    }

    @Override
    public void closeConnection() throws SQLException {
        conn.close();
    }
    @Override
    public final Map<String, Object> findById(String tableName, String pkName,Object pkValue) throws SQLException {

        String sql = "SELECT * FROM " + tableName + " WHERE " + pkName + " = ?";
        PreparedStatement stmt = null;
        final Map<String, Object> record = new HashMap();

        try {
            stmt = conn.prepareStatement(sql);
            stmt.setObject(1, pkValue);
            ResultSet rs = stmt.executeQuery();
            final ResultSetMetaData metaData = rs.getMetaData();
            final int fields = metaData.getColumnCount();

            // Retrieve the raw data from the ResultSet and copy the values into a Map
            // with the keys being the column names of the table.
            if (rs.next()) {
                for (int i = 1; i <= fields; i++) {
                    record.put(metaData.getColumnName(i), rs.getObject(i));
                }
            }
            
        } catch (Exception e) {
            throw e;
        } finally {
            try {
                conn.close();
            } catch (SQLException e) {
                throw e;
            } 
        } 

        return record;
    }

    @Override
    public List<Map<String, Object>> findAllRecords(String tableName) throws SQLException {
        Statement stmt = null;
        ResultSet rs = null;

        String sql = "SELECT * FROM " + tableName;
        stmt = conn.createStatement();
        rs = stmt.executeQuery(sql);
        ResultSetMetaData metaData = rs.getMetaData();
        final int columnCount = metaData.getColumnCount();

        List<Map<String, Object>> recordList = new ArrayList<>();

        while (rs.next()) {
            Map<String, Object> record = new HashMap<>();
            for (int i = 1; i <= columnCount; i++) {
                record.put(metaData.getColumnName(i), rs.getObject(i));
            }
            recordList.add(record);
        }
        return recordList;
    }

    @Override
    public void deleteRecord(String tableName, String pkName, Object pkValue) throws SQLException {
        PreparedStatement stmt = null;

        String sql = "DELETE FROM " + tableName + " WHERE " + pkName + " =  ?";

        
        stmt = conn.prepareStatement(sql);
        stmt.setObject(1, pkValue);
        System.out.println(stmt);
        stmt.executeUpdate();
    }

    @Override
    public void createNewRecord(Map<String, Object> record, String tableName) throws Exception {
        PreparedStatement stmt = null;

        String sql = "INSERT INTO " + tableName + " (";
        Set temp = record.keySet();
        List<String> colmnNames = new ArrayList<>(temp);
        for (int count = 0; count < colmnNames.size(); count++) {

            try {
                colmnNames.get(count + 1);
                sql += colmnNames.get(count) + ",";
            } catch (IndexOutOfBoundsException e) {
                sql += colmnNames.get(count);

            }
        }
        sql += ") VALUES (";
        for (int count = 0; count < colmnNames.size(); count++) {
            try {
                colmnNames.get(count + 1);
                sql += "?,";
            } catch (IndexOutOfBoundsException e) {
                sql += "?";
            }
        }
        sql += ")";
        
        
        int count = 1;
        stmt = conn.prepareStatement(sql);
        for(;count <= colmnNames.size();count++){
         stmt.setObject(count, record.get(colmnNames.get(count-1)));
        }
        System.out.println(sql);
        stmt.executeUpdate();
    }

    @Override
    public void updateRecord(Map<String, Object> record, String tableName, String pkName, Object pkValue) throws SQLException {
        
        PreparedStatement stmt = null;

        String sql = "UPDATE " + tableName + " SET ";
        Set temp = record.keySet();
        List<String> colmnNames = new ArrayList<>(temp);
        for (int count = 0; count < colmnNames.size(); count++) {
            try {
                colmnNames.get(count + 1);
                sql += colmnNames.get(count) + " = ?, ";
            } catch (IndexOutOfBoundsException e) {
                sql += colmnNames.get(count) + " = ?";

            }

        }
         sql += " WHERE " + pkName + " =  ?";
         
         stmt = conn.prepareStatement(sql);
         int count = 1;
        for(;count <= colmnNames.size();count++){
         stmt.setObject(count, record.get(colmnNames.get(count-1)));
        }
        stmt.setObject(count, pkValue);
        System.out.println(sql);
        
        stmt.executeUpdate();
    }

    public static void main(String[] args) throws Exception {
        MySqlDbStrategy db = new MySqlDbStrategy();
        db.openConnection("com.mysql.jdbc.Driver", "jdbc:mysql://localhost:3306/book", "root", "password");
        Date date = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Map<String, Object> record = new HashMap<>();
        record.put("author_name", "AND HIS NAME IS");
        record.put("date_added", sdf.format(date));

        db.deleteRecord("author", "author_id", 6);
        db.closeConnection();
    }

    @Override
    public void openConnection(DataSource ds) throws Exception {
      conn =  ds.getConnection();
    }

    

    

}
