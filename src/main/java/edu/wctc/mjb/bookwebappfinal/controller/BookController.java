package edu.wctc.mjb.bookwebappfinal.controller;



import edu.wctc.mjb.bookwebappfinal.entity.Author;
import edu.wctc.mjb.bookwebappfinal.entity.Book;
import edu.wctc.mjb.bookwebappfinal.service.AuthorService;
import edu.wctc.mjb.bookwebappfinal.service.BookService;
import java.io.IOException;
import java.lang.reflect.Constructor;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.inject.Inject;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.sql.DataSource;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

/**
 * The main controller for author-related activities
 *
 * @author jlombardo/Mbrester
 */
@WebServlet(name = "BookController", urlPatterns = {"/BookController"})
public class BookController extends HttpServlet {

    

    private static final String LIST_PAGE = "/listBooks.jsp";
    private static final String EDIT_PAGE = "/bookEdit.jsp";
    private static final String LIST_ACTION = "list";
    private static final String SUBMIT_ACTION = "submit";
    private static final String ACTION_PARAM = "action";
    private static final String CANCEL_ACTION = "cancel";
    private static final String EDIT_ACTION = "edit";
    private static final String ADD_ACTION = "add";
    private static final String ADD_PAGE = "/bookAdd.jsp";
    private static final String UPDATE_ACTION = "update";
    private static final String DELETE_ACTION = "delete";
    private static final String NEW_ACTION = "new";
    
   
    
  
    
    
    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
  protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");

        String destination = LIST_PAGE;
        String action = request.getParameter(ACTION_PARAM);
        Book book = null;
        String bookID = null;
        Author author = null;
        
        
        ServletContext sctx = getServletContext();
        WebApplicationContext ctx
                = WebApplicationContextUtils.getWebApplicationContext(sctx);
        BookService bookService = (BookService) ctx.getBean("bookService");
        AuthorService authService = (AuthorService) ctx.getBean("authorService");
       
        try {
             this.getAuthors(request, authService);
           

            //In this version of the project we only have the findAuthors working, but I will mirge other parts later
            switch (action) {
                case LIST_ACTION:
                    this.refreshList(request, bookService);
                    destination = LIST_PAGE;
                    break;
                case UPDATE_ACTION:
                    author = authService.findById(request.getParameter("authorID"));
                        bookID = request.getParameter("bookId");
                       book = bookService.findById(bookID);
                       book.setTittle(request.getParameter("tittle"));
                       book.setIsbn(request.getParameter("isbn"));
                       book.setAuthorId(author);
                        bookService.edit(book);
                       
                    this.refreshList(request, bookService);
                    destination = LIST_PAGE;
                    break;
                case EDIT_ACTION:
                    
                    bookID = request.getParameter("bookID");
                   book = bookService.findById(bookID);
                    request.setAttribute("book", book);
                    destination = EDIT_PAGE;
                    break;
                   
                case ADD_ACTION:
                    destination = ADD_PAGE;
                    break;
                case NEW_ACTION:
                    author = authService.findById(request.getParameter("authorID"));
                    book = new Book(0);
                    book.setTittle(request.getParameter("tittle"));
                    book.setIsbn(request.getParameter("isbn"));
                    book.setAuthorId(author);
                    bookService.edit(book);
                    
                    this.refreshList(request, bookService);
                    destination = LIST_PAGE;
                     break;
                    
                case DELETE_ACTION:
                    book = bookService.findById(request.getParameter("bookID"));
                    bookService.remove(book);
                    this.refreshList(request, bookService);
                    destination = LIST_PAGE;
                    break;
                           
            }
            

        } catch (Exception e) {
            request.setAttribute("errMsg", e.getCause().getMessage());
        }

        // Forward to destination page
        RequestDispatcher dispatcher
                = getServletContext().getRequestDispatcher(destination);
        dispatcher.forward(request, response);
    }
  
 
  private void getAuthors(HttpServletRequest request, AuthorService authService) throws Exception {
        List<Author> authors = authService.findAll();
        request.setAttribute("authors", authors);
    }
    private void refreshList(HttpServletRequest request, BookService bookService) throws Exception {
        List<Book> books = bookService.findAll();
        request.setAttribute("books", books);
    }
     @Override
    public void init() throws ServletException {
       
    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
