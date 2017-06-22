package po;

import java.io.Serializable;

/**
 * Created by 29185 on 2017/6/15.
 */
public class Book implements Serializable {
    private int bid;
    private String bookNum;
    private String bookName;
    private String bookType;
    private String bookAuthor;
    private String bookFactory;
    private int bookCount;

    public int getBookCount() {
        return bookCount;
    }

    public void setBookCount(int bookCount) {
        this.bookCount = bookCount;
    }

    public int getBid() {
        return bid;
    }

    public void setBid(int bid) {
        this.bid = bid;
    }

    public String getBookNum() {
        return bookNum;
    }

    public void setBookNum(String bookNum) {
        this.bookNum = bookNum;
    }

    public String getBookName() {
        return bookName;
    }

    public void setBookName(String bookName) {
        this.bookName = bookName;
    }

    public String getBookType() {
        return bookType;
    }

    public void setBookType(String bookType) {
        this.bookType = bookType;
    }

    public String getBookAuthor() {
        return bookAuthor;
    }

    public void setBookAuthor(String bookAuthor) {
        this.bookAuthor = bookAuthor;
    }

    public String getBookFactory() {
        return bookFactory;
    }

    public void setBookFactory(String bookFactory) {
        this.bookFactory = bookFactory;
    }
}
