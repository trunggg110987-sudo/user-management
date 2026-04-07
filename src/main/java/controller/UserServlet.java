package controller;

import model.User;
import service.UserService;
import service.IUserService;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

@WebServlet(name = "UserServlet", value = "/users")
public class UserServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private IUserService userService;

    public void init() {
        userService = new UserService();
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String action = req.getParameter("action");
        if (action == null) action = "";
        try {
            switch (action) {
                case "create":
                    req.getRequestDispatcher("create.jsp").forward(req, resp);
                    break;
                case "edit":
                    User user = userService.selectUser(Integer.parseInt(req.getParameter("id")));
                    req.setAttribute("user", user);
                    req.getRequestDispatcher("edit.jsp").forward(req, resp);
                    break;
                case "findByCountry":
                    List<User> byCountry = userService.findByCountry(req.getParameter("country"));
                    if (byCountry.isEmpty()) {
                        req.setAttribute("errorMessage", "No users found for country: " + req.getParameter("country"));
                    }
                    req.setAttribute("listUser", byCountry);
                    req.getRequestDispatcher("list.jsp").forward(req, resp);
                    break;
                case "sortByName":
                    req.setAttribute("listUser", userService.sortByName());
                    req.getRequestDispatcher("list.jsp").forward(req, resp);
                    break;
                default:
                    req.setAttribute("listUser", userService.selectAllUsers());
                    req.getRequestDispatcher("list.jsp").forward(req, resp);
                    break;
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String action = req.getParameter("action");
        if (action == null) action = "";
        try {
            switch (action) {
                case "create":
                    userService.insertUser(new User(
                            req.getParameter("name"),
                            req.getParameter("email"),
                            req.getParameter("country")
                    ));
                    break;
                case "update":
                    userService.updateUser(new User(
                            Integer.parseInt(req.getParameter("id")),
                            req.getParameter("name"),
                            req.getParameter("email"),
                            req.getParameter("country")
                    ));
                    break;
                case "delete":
                    userService.deleteUser(Integer.parseInt(req.getParameter("id")));
                    break;
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        resp.sendRedirect(req.getContextPath() + "/users");
    }
}
