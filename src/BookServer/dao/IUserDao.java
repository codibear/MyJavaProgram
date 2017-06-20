package BookServer.dao;


import BookServer.util.DButil;
import po.User;

import java.sql.ResultSet;

/**
 * Created by 29185 on 2017/6/15.
 */
public interface IUserDao{
    int insertUser(String string, User user);
    int deleteUser(String string, User user);
    int updateUser(String string, User user);
    ResultSet selectUserByUsernameAndPwd(String string, User user);
}
