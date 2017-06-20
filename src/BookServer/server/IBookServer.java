package BookServer.server;

import po.Book;
import po.User;

import java.sql.ResultSet;

/**
 * Created by 29185 on 2017/6/15.
 */
public interface IBookServer {
    int insertBookS( Book book);
    int deleteBookS( Book book);
    int updateBookS(Book book);
    ResultSet selectBookByBookNumS(Book book);
    ResultSet selectBookByAuthorS( Book book);
    ResultSet selectBookByBookNameS(Book book);
}
