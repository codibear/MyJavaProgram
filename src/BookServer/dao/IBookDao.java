package BookServer.dao;

import po.Book;
import po.User;
import BookServer.util.DButil;

import java.sql.ResultSet;

/**
 * Created by 29185 on 2017/6/15.
 */
public interface IBookDao{
    int insertBook(String string, Book book);
    int deleteBook(String string, Book book);
    int updateBook(String string, Book book);
    ResultSet selectBookByBookNum(String string, Book book);
    ResultSet selectBookByAuthor(String string, Book book);
    ResultSet selectBookByBookName(String string, Book book);
}
