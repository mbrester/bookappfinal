package edu.wctc.mjb.bookwebappfinal.controller;


import edu.wctc.mjb.bookwebappfinal.model.Author;
import edu.wctc.mjb.bookwebappfinal.model.AuthorDaoStrategy;
import edu.wctc.mjb.bookwebappfinal.model.AuthorService;
import edu.wctc.mjb.bookwebappfinal.model.DbStrategy;
import edu.wctc.mjb.bookwebappfinal.model.MySqlDbStrategy;
import java.io.IOException;
import java.lang.reflect.Constructor;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.sql.DataSource;

/**
 * The main controller for author-related activities
 *
 * @author jlombardo/Mbrester
 */
@WebServlet(name = "AuthorController", urlPatterns = {"/AuthorController"})
public class AuthorController extends HttpServlet {

    

    private static final String LIST_PAGE = "/listAuthors.jsp";
    private static final String EDIT_PAGE = "/edit.jsp";
    private static final String LIST_ACTION = "list";
    private static final String SUBMIT_ACTION = "submit";
    private static final String ACTION_PARAM = "action";
    private static final String CANCEL_ACTION = "cancel";
    private static final String EDIT_ACTION = "edit";
    private static final String ADD_ACTION = "add";
    private static final String ADD_PAGE = "/add.jsp";
    private static final String UPDATE_ACTION = "update";
    private static final String DELETE_ACTION = "delete";
    private static final String NEW_ACTION = "new";

    private String driverClass;
    private String url;
    private String userName;
    private String password;
    private String dbStrategyClassName;
    private String daoClassName;
    private DbStrategy db;
    private AuthorDaoStrategy authorDao;
    
    
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

        try {
            
            AuthorService authService = injectDependenciesAndGetAuthorService();

            //In this version of the project we only have the findAuthors working, but I will mirge other parts later
            switch (action) {
                case LIST_ACTION:
                    this.refreshList(request, authService);
                    destination = LIST_PAGE;
                    break;
                case UPDATE_ACTION:
                       
                        
                       authService.updateRecord(request.getParameter("authorName"), Integer.parseInt(request.getParameter("authorId")));
                    this.refreshList(request, authService);
                    destination = LIST_PAGE;
                    break;
                case EDIT_ACTION:
                    String authID = request.getParameter("authorID");
                   Author a = authService.getAuthorById(authID);
                    request.setAttribute("author", a);
                    destination = EDIT_PAGE;
                    break;
                   
                case ADD_ACTION:
                    destination = ADD_PAGE;
                    break;
                case NEW_ACTION:
                    authService.createNewAuthoer(request.getParameter("authorName"));
                    
                    this.refreshList(request, authService);
                    destination = LIST_PAGE;
                     break;
                    
                case DELETE_ACTION:
                    authService.deleteRecordByID(Integer.parseInt(request.getParameter("authorID")));
                    this.refreshList(request, authService);
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
  
  //I used the Same name as Jim, could not think of a better name.
  private AuthorService injectDependenciesAndGetAuthorService() throws Exception {

        Class dbClass = Class.forName(dbStrategyClassName);
        DbStrategy db = (DbStrategy) dbClass.newInstance();

        AuthorDaoStrategy authorDao = null;
        Class daoClass = Class.forName(daoClassName);
        Constructor constructor = null;
        try{
        constructor = daoClass.getConstructor(new Class[]{
            DbStrategy.class, String.class, String.class, String.class, String.class
        });
        }catch(NoSuchMethodException e){
            
        }
        if (constructor != null) {
            Object[] constructorArgs = new Object[]{
                db, driverClass, url, userName, password
            };
            authorDao = (AuthorDaoStrategy) constructor
                    .newInstance(constructorArgs);

        } else{
            Context ctx = new InitialContext();
        
            DataSource ds = (DataSource) ctx.lookup("jdbc/book");
            constructor = daoClass.getConstructor(new Class[]{
                DataSource.class, DbStrategy.class
            });
            Object[] constructorArgs = new Object[]{
                ds, db
            };

            authorDao = (AuthorDaoStrategy) constructor
                    .newInstance(constructorArgs);
        }
        return new AuthorService(authorDao);
    }

    
    private void refreshList(HttpServletRequest request, AuthorService authService) throws Exception {
        List<Author> authors = authService.getAllAuthors();
        request.setAttribute("authors", authors);
    }
     @Override
    public void init() throws ServletException {
        driverClass = getServletConfig().getInitParameter("driverClass");
        url = getServletConfig().getInitParameter("url");
        userName = getServletConfig().getInitParameter("userName");
        password = getServletConfig().getInitParameter("password");
        dbStrategyClassName = this.getServletConfig().getInitParameter("dbStrategy");
        daoClassName = this.getServletConfig().getInitParameter("authorDao");
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
