package BookServer.server.impl;

import BookServer.dao.impl.UserDaoImpl;
import BookServer.server.IUserServer;
import po.User;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;

/**
 * Created by 29185 on 2017/6/15.
 */
public class UserSeverImpl implements IUserServer{
    UserDaoImpl userDao = new UserDaoImpl();
    @Override
    public int insertUserS(User user) {
        String sql = "insert into user(userName,pwd,authority)values(?,?,?)";
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
    public HashMap selectUserByUsernameAndPwdS(User user) {
        int num = -1;
        //User user1 = new User();
        HashMap hashMap = new HashMap();
        String sql = "select * from user where userName=? and pwd=?";
        ResultSet rs = userDao.selectUserByUsernameAndPwd(sql,user);
        try {
            while (rs.next()){
               // user1.setUserName(rs.getString("userName"));
                //user1.setPwd(rs.getString("pwd"));
                num = rs.getInt("authority");
                hashMap.put("userName",rs.getString("userName"));
                hashMap.put("pwd",rs.getString("pwd"));
                hashMap.put("authority",num);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return hashMap;
    }
}
