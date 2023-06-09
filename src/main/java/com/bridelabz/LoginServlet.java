package com.bridelabz;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.regex.Pattern;

@WebServlet(//name = "LoginServlet", value = "/LoginServlet",
        description = "login Servlet Testing",
        urlPatterns = {"/LoginServlet"},
        initParams = {
                @WebInitParam(name = "user", value = "Sourav"),
                @WebInitParam(name = "password", value = "So@123456")
        }
)
public class LoginServlet extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        System.out.println("called do-post method of login servlet");
        String user = request.getParameter("user");
        String pwd = request.getParameter("pwd");
        String userID = getServletConfig().getInitParameter("user");
        String password = getServletConfig().getInitParameter("password");
        boolean isValidPassword = Pattern.compile("(?=.*[A-Z])(?=.*[0-9])(?=.*[!@#$%^&*])[a-zA-Z0-9!@#$%^&*]{8,}").matcher(pwd).matches();
        boolean isValidUserName = Pattern.compile("^[A-Z][a-zA-Z]{2,}").matcher(user).matches();
        if (!isValidUserName) {
            RequestDispatcher requestDispatcher = getServletContext().getRequestDispatcher("/login.html");
            PrintWriter out = response.getWriter();
            out.println("<font color=red>UserName doesn't match regex</font>");
            requestDispatcher.include(request, response);
        }
        else if (!isValidPassword) {
            RequestDispatcher requestDispatcher = getServletContext().getRequestDispatcher("/login.html");
            PrintWriter out = response.getWriter();
            out.println("<font color=red>Password doesn't match regex</font>");
            requestDispatcher.include(request, response);
        }
        else if (user.equals(userID) && pwd.equals(password)) {
            request.setAttribute("user", user);
            request.getRequestDispatcher("/LoginSuccess.jsp").forward(request, response);
        } else {
            RequestDispatcher requestDispatcher = getServletContext().getRequestDispatcher("/login.html");
            PrintWriter out = response.getWriter();
            out.println("<font color=red>Invalid UserName and Password</font>");
            requestDispatcher.include(request, response);
        }
    }
}