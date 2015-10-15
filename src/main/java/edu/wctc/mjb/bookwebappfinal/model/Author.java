/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.wctc.mjb.bookwebappfinal.model;

import java.util.Date;

/**
 *
 * @author Brester
 */
public class Author {
    private String authorName;
    private Date dateAdded;
    private int authorID;

    public Author() {
    }

    public Author(int authorID) {
        this.authorID = authorID;
    }

    public Author(String authorName, Date dateAdded, int authorID) {
        this.authorName = authorName;
        this.dateAdded = dateAdded;
        this.authorID = authorID;
    }

    public String getAuthorName() {
        return authorName;
    }

    public void setAuthorName(String authorName) {
        this.authorName = authorName;
    }

    public Date getDateAdded() {
        return dateAdded;
    }

    public void setDateAdded(Date dateAdded) {
        this.dateAdded = dateAdded;
    }

    public int getAuthorID() {
        return authorID;
    }

    public void setAuthorID(int authorID) {
        this.authorID = authorID;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 11 * hash + this.authorID;
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Author other = (Author) obj;
        if (this.authorID != other.authorID) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Author{" + "authorName=" + authorName + ", dateAdded=" + dateAdded + ", authorID=" + authorID + '}';
    }
    
    
    
}
