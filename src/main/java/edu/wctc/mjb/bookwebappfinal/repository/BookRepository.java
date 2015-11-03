package edu.wctc.mjb.bookwebappfinal.repository;

import edu.wctc.mjb.bookwebappfinal.entity.Book;
import java.io.Serializable;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 *
 * @author jlombardo
 */
public interface BookRepository extends JpaRepository<Book, Integer>, Serializable {
    
}
