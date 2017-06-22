package BookServer.server.impl;

import BookServer.dao.impl.BookDaoImpl;
import po.Book;
import BookServer.server.IBookServer;

import java.sql.ResultSet;

/**
 * Created by 29185 on 2017/6/15.
 */
public class BookServerImpl implements IBookServer {
    BookDaoImpl bookDao = new BookDaoImpl();

    @Override
    public int insertBookS(Book book) {
        String sql = "insert into book(bookNum,bookName,bookType,bookAuthor,bookFactory,bookCount)values(?,?,?,?,?,?)";
        int num = bookDao.insertBook(sql,book);
        return num;
    }

    @Override
    public int deleteBookS(Book book) {
        String sql = "delete from book where bookNum = ?";
        int num = bookDao.deleteBook(sql,book);
        return num;
    }

    //应该有好多种更改方式
    //1.通过书号，更改书名
    @Override
    public int updateBookS(Book book) {
        String sql = "update book set bookName=?,bookType=?,bookAuthor=?,bookFactory=?,bookCount=? where bookNum=?";
        int num = bookDao.updateBook(sql,book);
        return num;
    }
    //1.通过书号查询
    @Override
    public ResultSet selectBookByBookNumS(Book book) {
        String sql = "select * from book where bookNum = ?";
        ResultSet rs = null;
        rs = bookDao.selectBookByBookNum(sql,book);
        return rs;
    }
    //2.通过作者查询
    @Override
    public ResultSet selectBookByAuthorS(Book book) {
        String sql = "select * from book where bookAuthor = ?";
        ResultSet rs = null;
        rs = bookDao.selectBookByAuthor(sql,book);
        return rs;
    }
    //3.通过书名查询
    @Override
    public ResultSet selectBookByBookNameS(Book book) {
        String sql = "select * from book where bookName = ?";
        ResultSet rs = null;
        rs = bookDao.selectBookByBookName(sql,book);
        return rs;
    }
}
