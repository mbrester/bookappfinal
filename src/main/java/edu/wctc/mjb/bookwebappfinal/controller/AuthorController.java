package edu.wctc.mjb.bookwebappfinal.controller;



import edu.wctc.mjb.bookwebappfinal.entity.Author;
import edu.wctc.mjb.bookwebappfinal.service.AuthorService;
import javax.servlet.annotation.WebServlet;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
import javax.json.Json;
import javax.json.JsonArray;
import javax.json.JsonArrayBuilder;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;
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
    private static final String AJAX_LIST_ACTION = "listAjax";
    
   
    
  
    
    
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
        Author author = null;
        String authID = null;
        
        
        
         ServletContext sctx = getServletContext();
        WebApplicationContext ctx
                = WebApplicationContextUtils.getWebApplicationContext(sctx);
        AuthorService authService = (AuthorService) ctx.getBean("authorService");
        PrintWriter out = response.getWriter();
        
        try {
            
           

            //In this version of the project we only have the findAuthors working, but I will mirge other parts later
            switch (action) {
                case LIST_ACTION:
                    this.refreshList(request, authService);
                    destination = LIST_PAGE;
                    break;
                case UPDATE_ACTION:
                        authID = request.getParameter("authorId");
                       author = authService.findById(authID);
                       author.setAuthorName(request.getParameter("authorName"));
                        authService.edit(author);
                       
                    this.refreshList(request, authService);
                    destination = LIST_PAGE;
                    break;
                case AJAX_LIST_ACTION:
                    List<Author> authors = authService.findAll();
                    JsonArrayBuilder jsonArrayBuilder = Json.createArrayBuilder();

                    authors.forEach((authorObj) -> {
                        jsonArrayBuilder.add(
                                Json.createObjectBuilder()
                                .add("authorId", authorObj.getAuthorId())
                                .add("authorName", authorObj.getAuthorName())
                                .add("dateAdded", authorObj.getDateAdded().toString())
                        );
                    });

                    JsonArray authorsJson = jsonArrayBuilder.build();
                    response.setContentType("application/json");
                    out.write(authorsJson.toString());
                    out.flush();
                    return;
                case EDIT_ACTION:
                    authID = request.getParameter("authorID");
                   author = authService.findById(authID);
                    request.setAttribute("author", author);
                    destination = EDIT_PAGE;
                    break;
                   
                case ADD_ACTION:
                    destination = ADD_PAGE;
                    break;
                case NEW_ACTION:
                    author = new Author(0);
                    author.setAuthorName(request.getParameter("authorName"));
                    authService.edit(author);
                    
                    this.refreshList(request, authService);
                    destination = LIST_PAGE;
                     break;
                    
                case DELETE_ACTION:
                    author = authService.findById(request.getParameter("authorID"));
                    authService.remove(author);
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
  
 
    private void refreshList(HttpServletRequest request,  AuthorService authService) throws Exception {
        List<Author> authors = authService.findAll();
        request.setAttribute("authors", authors);
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
