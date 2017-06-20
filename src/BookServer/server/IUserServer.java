package BookServer.server;

import po.User;

/**
 * Created by 29185 on 2017/6/15.
 */
public interface IUserServer {
    int insertUserS( User user);
    int deleteUserS( User user);
    int updateUserS( User user);
    boolean selectUserByUsernameAndPwdS(User user);
}
