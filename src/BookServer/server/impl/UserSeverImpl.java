package BookServer.server.impl;

import BookServer.dao.impl.UserDaoImpl;
import BookServer.server.IUserServer;
import po.User;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Created by 29185 on 2017/6/15.
 */
public class UserSeverImpl implements IUserServer{
    UserDaoImpl userDao = new UserDaoImpl();
    @Override
    public int insertUserS(User user) {
        String sql = "insert into user(userName,pwd)values(?,?)";
        int num = userDao.insertUser(sql,user);
        return num;
    }

    @Override
    public int deleteUserS(User user) {
        String sql = "delete from user where username = ?";
        int num = userDao.deleteUser(sql,user);
        return num;
    }

    @Override
    public int updateUserS(User user) {
        String sql = "update user set userName = ? where uid=?";
        int num = userDao.updateUser(sql,user);
        return num;
    }

    @Override
    public boolean selectUserByUsernameAndPwdS(User user) {
        User user1 = new User();
        String sql = "select * from user where userName=? and pwd=?";
        ResultSet rs = userDao.selectUserByUsernameAndPwd(sql,user);
        try {
            while (rs.next()){
                user1.setUserName(rs.getString("userName"));
                user1.setPwd(rs.getString("pwd"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        if(user1.getPwd()==null||user1.getUserName()==null){
            return false;
        }else {
            return true;
        }
    }
}
