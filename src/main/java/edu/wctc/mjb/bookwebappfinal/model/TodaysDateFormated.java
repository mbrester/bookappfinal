/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.wctc.mjb.bookwebappfinal.model;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 *
 * @author Brester
 */
public class TodaysDateFormated {

   public Object getDate() {

        Date date = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        
        return sdf.format(date);
    }  
}
