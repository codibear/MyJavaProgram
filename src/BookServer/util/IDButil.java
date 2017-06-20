package BookServer.util;

import java.sql.ResultSet;

/**
 * Created by 29185 on 2017/6/15.
 */
public interface IDButil {
    void getConnection();
    void closeConnection();
    int insert(String string,Object [] objects);
    int delete(String string,Object [] objects);
    int update(String string,Object [] objects);
    ResultSet select(String string, Object [] objects);
}
