package BookServer.dao.impl;

import BookServer.dao.IBookDao;
import po.Book;
import BookServer.util.DButil;

import java.sql.ResultSet;

/**
 * Created by 29185 on 2017/6/15.
 */
public class BookDaoImpl implements IBookDao{
    DButil dButil = new DButil();
    ResultSet rs = null;
    @Override
    public int insertBook(String string, Book book) {
        int num = 0;
        Object [] objects = {book.getBookNum(),book.getBookName(),book.getBookType(),book.getBookAuthor(),book.getBookFactory(),book.getBookCount()};
        num = dButil.insert(string,objects);
        dButil.closeConnection();
        return num;
    }

    @Override
    public int deleteBook(String string, Book book) {
        int num = 0;
        Object [] objects = {book.getBookNum()};
        num = dButil.delete(string,objects);
        dButil.closeConnection();
        return num;
    }

    @Override
    public int updateBook(String string, Book book) {
        int num = 0;
        Object [] objects = {book.getBookName(),book.getBookType(),book.getBookAuthor(),book.getBookFactory(),book.getBookCount(),book.getBookNum()};
        num = dButil.update(string,objects);
        dButil.closeConnection();
        return num;
    }

    @Override
    public ResultSet selectBookByBookNum(String string, Book book) {
        Object [] objects = {book.getBookNum()};
        rs = dButil.select(string,objects);
        return rs;
    }
    @Override
    public ResultSet selectBookByAuthor(String string, Book book) {
        Object [] objects = {book.getBookAuthor()};
        rs = dButil.select(string,objects);
        return rs;
    }
    @Override
    public ResultSet selectBookByBookName(String string, Book book) {
        Object [] objects = {book.getBookName()};
        rs = dButil.select(string,objects);
        return rs;
    }
}
