package edu.wctc.mjb.bookwebappfinal.service;

import edu.wctc.mjb.bookwebappfinal.entity.Book;
import edu.wctc.mjb.bookwebappfinal.repository.AuthorRepository;
import edu.wctc.mjb.bookwebappfinal.repository.BookRepository;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

/**
 * This is a special Spring-enabled service class that delegates work to 
 * various Spring managed repository objects using special spring annotations
 * to perform dependency injection, and special annotations for transactions.
 * It also uses SLF4j to provide logging features.
 * 
 * @author jlombardo
 */
@Repository("bookService")
@Transactional(readOnly = true)
public class BookService {
    private transient final Logger LOG = LoggerFactory.getLogger(BookService.class);
    
    @Autowired
    private BookRepository bookRepo;
    
    @Autowired
    private AuthorRepository authorRepo;

    public BookService() {
    }
    
    public List<Book> findAll() {
        return bookRepo.findAll();
    }
    
    public Book findById(String id) {
        return bookRepo.findOne(new Integer(id));
    }
    
    @Transactional(readOnly = false, propagation = Propagation.REQUIRED)
    public void remove(Book book) {
        LOG.debug("Deleting book: " + book.getTittle());
        bookRepo.delete(book);
    }

    @Transactional(readOnly = false, propagation = Propagation.REQUIRED)
    public Book edit(Book book) {
        return bookRepo.saveAndFlush(book);
    }
    
}
