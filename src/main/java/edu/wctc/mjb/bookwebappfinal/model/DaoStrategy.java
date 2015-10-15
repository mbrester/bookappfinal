/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.wctc.mjb.bookwebappfinal.model;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

/**
 *
 * @author Brester
 */
public interface DaoStrategy {

    public List<Author> getAllRecords() throws Exception;
    
    public void deleteRecordByID(Object pkValue) throws Exception;
    
    public void createNewRecord(Map<String,Object> record) throws Exception;
    
    public void updateRecord(Map<String,Object> newData, Object pkValue) throws Exception;
    
    public Author getAuthorById(int authorId) throws Exception;
}
