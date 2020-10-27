package com.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.model.User;
import com.utils.DataBaseUtil;

public class UserDao {
    //查询数据库信息

    /**
     * 在用户提交注册信息是，需要判断该用户名是否存在
     *
     * @param username
     * @return
     */
    public boolean userExist(String username) {
        Connection conn = DataBaseUtil.getConn();
        //根据指定的用户名查询信息
        String sql = "select * from tb_user where username = ?";

        try {
            //获取PreparedStatement对象，用于执行数据库查询
            PreparedStatement preparedStatement = conn.prepareStatement(sql);
            preparedStatement.setString(1, username);
            //执行查询获取结果集
            ResultSet resultSet = preparedStatement.executeQuery();
            while (!resultSet.next()) {
                //如果没有此数据，证明该用户名可用
                return true;
            }
            //释放资源,后创建的先销毁
            resultSet.close();
            preparedStatement.close();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            DataBaseUtil.closeConn(conn);
        }

        return true;
    }

    /**
     * 在用户提交注册信息时，如果注册成功需要将，需要将用户注册的信息存入数据库
     */
    public void saveUser(User user) {
        //获取数据库连接
        Connection conn = DataBaseUtil.getConn();
        //插入信息的sql语句
        String sql = "insert into tb_user(username,password,sex,question,answer,email) values(?,?,?,?,?,?)";
        try {
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, user.getUsername());
            ps.setString(2, user.getPassword());
            ps.setString(3, user.getSex());
            ps.setString(4, user.getQuestion());
            ps.setString(5, user.getAnswer());
            ps.setString(6, user.getEmail());
            //执行更新操作
            System.out.println(sql);
            ps.executeUpdate();
            //释放资源
            ps.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * 注册成功后，用户既可通过注册的用户及密码进行登录，对于程序而言，此操作实质是根据
     * 用户所提供的用户名及密码在数据库进行查询，如果查询成功，则登录成功
     */
    public User login(String username, String password) {
        //实例化一个用户对象
        User user =null;
        Connection conn = DataBaseUtil.getConn();
        String sql = "select * from tb_user where username = ? and password = ?";
        try {
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, username);
            ps.setString(2, password);
            //执行查询获取结果集
            ResultSet rs = ps.executeQuery();

            //判断结果集是否有效,如过有效，则对用户进行赋值
            while (rs.next()) {

                user = new User();
                //对用户对象进行复制
                user.setId(rs.getInt("id"));
                user.setUsername(rs.getString("username"));
                user.setPassword(rs.getString("password"));
                user.setSex(rs.getString("sex"));
                user.setQuestion(rs.getString("question"));
                user.setAnswer(rs.getString("answer"));
                user.setEmail(rs.getString("email"));
            }
            //释放资源
            rs.close();
            ps.close();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            DataBaseUtil.closeConn(conn);
        }

        return user;
    }
}

