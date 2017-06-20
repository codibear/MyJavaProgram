package BookServer.dao.impl;

import BookServer.dao.IUserDao;
import po.User;
import BookServer.util.DButil;
import po.User;

import java.sql.ResultSet;

/**
 * Created by 29185 on 2017/6/15.
 */
public class UserDaoImpl implements IUserDao{
    DButil dButil = new DButil();

    @Override
    public int insertUser(String string, User user) {
        int num = 0;
        Object [] objects = {user.getUserName(),user.getPwd()};
        num = dButil.insert(string,objects);
        dButil.closeConnection();
        return num;
    }

    @Override
    public int deleteUser(String string, User user) {
        int num = 0;
        Object [] objects = {user.getUserName()};
        num = dButil.delete(string,objects);
        dButil.closeConnection();
        return num;
    }

    @Override
    public int updateUser(String string, User user) {
        int num = 0;
        Object [] objects = {user.getUid(),user.getUserName()};
        num = dButil.update(string,objects);
        dButil.closeConnection();
        return num;
    }

    @Override
    public ResultSet selectUserByUsernameAndPwd(String string, User user) {
        ResultSet rs;
        Object [] objects = {user.getUserName(),user.getPwd()};
        rs = dButil.select(string,objects);
        //dButil.closeConnection(); 关闭后rs就是空？
        return rs;
    }
}
