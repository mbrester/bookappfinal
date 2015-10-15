/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.wctc.mjb.bookwebappfinal.model;

import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 *
 * @author Brester
 */
public class AuthorDaoStrategy implements DaoStrategy {
   private DbStrategy db;
   private String driverClass;
   private String url; 
   private String userName;
   private String password;

    public AuthorDaoStrategy(DbStrategy db, String driverClass, String url, String userName, String password) {
        this.db = db;
        this.driverClass = driverClass;
        this.url = url;
        this.userName = userName;
        this.password = password;
    }

     @Override
    public final Author getAuthorById(int authorId) throws Exception {
        db.openConnection(driverClass, url, userName, password);
        
        Map<String,Object> rawRec = db.findById("author", "author_id", authorId);
        Author author = new Author();
        author.setAuthorID((int)rawRec.get("author_id"));
        author.setAuthorName(rawRec.get("author_name").toString());
        author.setDateAdded((Date)rawRec.get("date_added"));
        
        return author;
    }
    
    @Override
    public List<Author> getAllRecords() throws Exception{
        db.openConnection(driverClass, url, userName, password);
        
        List<Map<String,Object>> rawData = db.findAllRecords("author");
        List<Author> authors = new ArrayList<>();
        
        for(Map rawRec : rawData){
            Author author = new Author();
            
            Object obj = rawRec.get("author_id");
            author.setAuthorID(Integer.parseInt( (obj== null) ? "": obj.toString()));
            
            obj = rawRec.get("author_name");
            author.setAuthorName(obj == null? "" : obj.toString());
           
            obj = rawRec.get("date_added");
            
            author.setDateAdded(obj == null ? new Date() : (Date)obj);
            
            authors.add(author);
        }
        db.closeConnection();
        return authors;
    }
   @Override
    public void deleteRecordByID(Object pkValue) throws Exception{
         db.openConnection(driverClass, url, userName, password);
         db.deleteRecord("author","author_id",pkValue);
         db.closeConnection();
        
    }
   @Override
    public void createNewRecord(Map<String,Object> record) throws Exception{
        db.openConnection(driverClass, url, userName, password);
        db.createNewRecord(record, "author");
        db.closeConnection();
    }
   @Override
    public void updateRecord(Map<String,Object> newData, Object pkValue) throws Exception
    {
        db.openConnection(driverClass, url, userName, password);
        db.updateRecord(newData, "author","author_id",pkValue);
        db.closeConnection();
    }

    public DbStrategy getDb() {
        return db;
    }

    public void setDb(DbStrategy db) {
        this.db = db;
    }

    public String getDriverClass() {
        return driverClass;
    }

    public void setDriverClass(String driverClass) {
        this.driverClass = driverClass;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
    
    public static void main(String[] args) throws Exception {
        DaoStrategy dao = new AuthorDaoStrategy(new MySqlDbStrategy(),"com.mysql.jdbc.Driver","jdbc:mysql://localhost:3306/book","root","password");
        Date date = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Map<String, Object> record = new HashMap<>();
        record.put("author_name", "John Cena");
        record.put("date_added", sdf.format(date));
        dao.updateRecord(record, 8);
        
        
    }
    
}