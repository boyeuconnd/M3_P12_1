package controller;

import model.User;
import service.UserDAO;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

@WebServlet(name = "ControllerServlet",urlPatterns = "/user")
public class ControllerServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private UserDAO userDAO;
    public void init() {
        userDAO = new UserDAO();
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        response.setCharacterEncoding("utf-8");
        String action =request.getParameter("action");
        if(action==null)action="";
        switch (action){
            case "create":
                insertUser(request,response);
                break;
            case "edit":
                updateUser(request,response);
                break;
            case "delete":
                deleteConfirm(request,response);
                break;
            default:
                break;
        }
    }

    private void updateUser(HttpServletRequest request, HttpServletResponse response) {
        int id = Integer.parseInt(request.getParameter("id"));
        String name = request.getParameter("name");
        String email = request.getParameter("email");
        String country = request.getParameter("country");
        User updateUser = new User(id,name,email,country);
        RequestDispatcher dispatcher;
        try {
            if(userDAO.updateUser(updateUser)){
                request.setAttribute("messenger","User Updated");
                dispatcher=request.getRequestDispatcher("user/editConfirm.jsp");
                dispatcher.forward(request,response);
            }else {
                dispatcher = request.getRequestDispatcher("404.jsp");
                dispatcher.forward(request,response);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ServletException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    private void deleteConfirm(HttpServletRequest request, HttpServletResponse response) {
        int id = Integer.parseInt(request.getParameter("id"));
        RequestDispatcher dispatcher;
        try {
            if(userDAO.deleteUser(id)){
                request.setAttribute("messenger","User Deleted");
                dispatcher=request.getRequestDispatcher("user/deleteConfirm.jsp");
                dispatcher.forward(request,response);
            }else {
                dispatcher = request.getRequestDispatcher("404.jsp");
                dispatcher.forward(request,response);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ServletException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void insertUser(HttpServletRequest request, HttpServletResponse response) {
        User newUser;
        String name = request.getParameter("name");
        String email = request.getParameter("email");
        String country = request.getParameter("country");
        newUser = new User(name,email,country);
        try {
            userDAO.insertUser(newUser);
            request.setAttribute("messenger","User was Added");
            RequestDispatcher dispatcher = request.getRequestDispatcher("/user/create.jsp");
            dispatcher.forward(request,response);

        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ServletException ex){
            ex.printStackTrace();
        } catch (IOException ioe){
            ioe.printStackTrace();
        }

    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        response.setContentType("text/html;charset=UTF-8");
        response.setCharacterEncoding("utf-8");
        String action = request.getParameter("action");
        if(action==null)action="";
        try{
            switch (action){
                case "create":
                    showNewForm(request, response);
                    break;
                case "edit":
                    showEditForm(request, response);
                    break;
                case "delete":
                    showDeleteConfirm(request, response);
                    break;
                case "show":
                    listUser(request,response);
                    break;
                case "sort":
                    softList(request,response);
                    break;
                default:
                    listUser(request, response);
                    break;
            }
        }catch (Exception ex){
            throw new ServletException(ex);
        }

    }

    private void softList(HttpServletRequest request, HttpServletResponse response) {
        List<User> sortList = null;
        String type = request.getParameter("type");
        if(type.equals("name")){
            sortList = userDAO.sortByName();
        }else if(type.equals("country")){
            sortList= userDAO.sortByCountry();
        }
        request.setAttribute("sortList",sortList);
        RequestDispatcher dispatcher = request.getRequestDispatcher("user/sort.jsp");
        try {
            dispatcher.forward(request,response);
        } catch (ServletException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void showEditForm(HttpServletRequest request, HttpServletResponse response) {
        int id = Integer.parseInt(request.getParameter("id"));
        User editUser = userDAO.selectUser(id);
        request.setAttribute("editUser",editUser);
        RequestDispatcher dispatcher = request.getRequestDispatcher("user/edit.jsp");
        try {
            dispatcher.forward(request,response);
        } catch (ServletException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void showDeleteConfirm(HttpServletRequest request, HttpServletResponse response) {
        RequestDispatcher dispatcher = request.getRequestDispatcher("user/delete.jsp");
        try {
            dispatcher.forward(request,response);
        } catch (ServletException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void showNewForm(HttpServletRequest request, HttpServletResponse response) {
        RequestDispatcher dispatcher = request.getRequestDispatcher("user/create.jsp");
        try {
            dispatcher.forward(request,response);
        } catch (ServletException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void listUser(HttpServletRequest request, HttpServletResponse response) {
        List<User> listUser = userDAO.selectAllUsers();
        request.setAttribute("listUser", listUser);
        RequestDispatcher dispatcher = request.getRequestDispatcher("user/list.jsp");
        try {
            dispatcher.forward(request, response);
        } catch (ServletException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
