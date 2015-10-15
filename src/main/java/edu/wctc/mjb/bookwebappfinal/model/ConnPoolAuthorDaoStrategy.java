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
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

/**
 *
 * @author Brester
 */
public class ConnPoolAuthorDaoStrategy implements DaoStrategy {
   private DbStrategy db;
   private DataSource ds;

    public ConnPoolAuthorDaoStrategy(DataSource ds, DbStrategy db) {
        this.db = db;
        this.ds = ds;
    }

     @Override
    public final Author getAuthorById(int authorId) throws Exception {
        db.openConnection(ds);
        
        Map<String,Object> rawRec = db.findById("author", "author_id", authorId);
        Author author = new Author();
        author.setAuthorID((int)rawRec.get("author_id"));
        author.setAuthorName(rawRec.get("author_name").toString());
        author.setDateAdded((Date)rawRec.get("date_added"));
        db.closeConnection();
        return author;
    }
    
    @Override
    public List<Author> getAllRecords() throws Exception{
        db.openConnection(ds);
        
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
         db.openConnection(ds);
         db.deleteRecord("author","author_id",pkValue);
         db.closeConnection();
        
    }
   @Override
    public void createNewRecord(Map<String,Object> record) throws Exception{
        db.openConnection(ds);
        db.createNewRecord(record, "author");
        db.closeConnection();
    }
   @Override
    public void updateRecord(Map<String,Object> newData, Object pkValue) throws Exception
    {
        db.openConnection(ds);
        db.updateRecord(newData, "author","author_id",pkValue);
        db.closeConnection();
    }

    public DbStrategy getDb() {
        return db;
    }

    public void setDb(DbStrategy db) {
        this.db = db;
    }

    public static void main(String[] args) throws NamingException, Exception {
        Context ctx = new InitialContext();
        DataSource ds = (DataSource) ctx.lookup("jdbc/book");
      ConnPoolAuthorDaoStrategy dao = new ConnPoolAuthorDaoStrategy(ds,new MySqlDbStrategy());
        System.out.println( dao.getAuthorById(13));
        
    }
    
}