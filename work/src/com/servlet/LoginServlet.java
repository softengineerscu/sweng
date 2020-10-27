package com.servlet;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.dao.UserDao;
import com.model.User;

/**
 * Created by pc on 17-5-11.
 */

public class LoginServlet extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("text/html;charset=utf-8");
        req.setCharacterEncoding("utf-8");

        String username = req.getParameter("username");
        String password = req.getParameter("password");
        UserDao userDao = new UserDao();
        //根据密码查询用户
        User user  = userDao.login(username, password);
        //判断user是否为空
        if (user != null) {
            //将用户的对象放到session中
            req.getSession().setAttribute("user", user);
            //转发到result.jsp页面
            req.getRequestDispatcher("message.jsp").forward(req, resp);
            /**
             response.sendRedirect(url)跳转到指定的URL地址，产生一个新的request，所以要传递参数只有在url后加参
             数，如：
             url?id=1.
             request.getRequestDispatcher(url).forward(request,response)是直接将请求转发到指定URL，所以该请求
             能够直接获得上一个请求的数据，也就是说采用请求转发，request对象始终存在，不会重新创建。而
             sendRedirect()会新建request对象，所以上一个request中的数据会丢失。
             */
        }else {
            //登录失败
            req.setAttribute("info","用户名或密码错误！");
            req.getRequestDispatcher("message.jsp").forward(req, resp);
        }
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doPost(req, resp);
    }
}
