package BookServer.view;


import BookServer.server.impl.BookServerImpl;
import po.Book;
import BookServer.server.impl.UserSeverImpl;
import po.FlagAndObject;
import po.User;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Scanner;

/**
 * Created by 29185 on 2017/6/16.
 */
public class ViewServer implements Runnable{



    //用户传送数据
    private static UserSeverImpl userSever = new UserSeverImpl();
    private static BookServerImpl bookServer = new BookServerImpl();

    private static FlagAndObject fo;
    private static OutputStream os;
    private static InputStream is;
    private static Scanner in = new Scanner(System.in);
    private static ObjectOutputStream oos;
    private static ObjectInputStream ois;
    private static Socket s;
    private static ServerSocket ss;
    ViewServer(Socket s){
        this.s = s;
        try {
            ois=new ObjectInputStream(s.getInputStream());
            os=s.getOutputStream();
            oos = new ObjectOutputStream(os);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    @Override
    public void run() {
        int flag = -1;

        try {
            while (true){
                //FlagAndObject fo;
                //接收来自客户端的数据
                is = s.getInputStream();

                fo = (FlagAndObject)ois.readObject();
                flag = fo.getFlag();
                System.out.println("接收数据成功");


                //匹配--0---登录操作
                if(flag==0){
                    User userBack = new User();
                    //数据库验证步骤
                    System.out.println("进行账户验证！");
                    List userList = fo.getArrayList();
                    User user  = (User)userList.get(0);
                    FlagAndObject foBackUser = new FlagAndObject();
                    HashMap hashMap = userSever.selectUserByUsernameAndPwdS(user);
                    if(hashMap.size()>0){
                        int b = (int)hashMap.get("authority");
                        String userName  = (String)hashMap.get("userName");
                        String pwd = (String)hashMap.get("pwd");


                        userBack.setPwd(pwd);
                        userBack.setUserName(userName);

                        List arrayList = new ArrayList();
                        arrayList.add(userBack);

                        //读取传来的数据
                        if(b==2){//图书管理员
                            flag=200;
                            foBackUser.setFlag(flag);
                            foBackUser.setArrayList(arrayList);
                            OutputStream os = s.getOutputStream();

                            oos.writeObject(foBackUser);
                            System.out.println("验证成功！");
                        }else if(b==1){//帐户管理员
                            flag=100;
                            foBackUser.setFlag(flag);
                            foBackUser.setArrayList(arrayList);
                            OutputStream os = s.getOutputStream();

                            oos.writeObject(foBackUser);
                            System.out.println("验证成功！");
                        }else if(b==3){//学生
                            flag=300;
                            foBackUser.setFlag(flag);
                            foBackUser.setArrayList(arrayList);
                            OutputStream os = s.getOutputStream();

                            oos.writeObject(foBackUser);
                            System.out.println("验证成功！");
                        }
                        else {
                            flag=-1;
                            foBackUser.setFlag(flag);
                            OutputStream os = s.getOutputStream();

                            oos.writeObject(foBackUser);
                            System.out.println("验证失败@");
                        }
                    }else {
                        flag=-1;
                        foBackUser.setFlag(flag);
                        userList.add(user);
                        foBackUser.setArrayList(userList);
                        OutputStream os = s.getOutputStream();

                        oos.writeObject(foBackUser);
                        System.out.println("验证失败@");
                    }
                }

                //匹配--10---录入帐户功能
                if (flag==10){
                    System.out.println("进行账号录入");
                    List<User> userList = fo.getArrayList();
                    int num=0;
                    FlagAndObject userInsertBack = new FlagAndObject();
                    for(int i = 0;i<userList.size();i++){
                        User user = userList.get(i);
                        num = userSever.insertUserS(user);
                    }

                    if(num>0){
                        userInsertBack.setFlag(100);
                        OutputStream os = s.getOutputStream();

                        oos.writeObject(userInsertBack);
                        System.out.println("录入成功！ ");
                    }else {
                        userInsertBack.setFlag(-1);
                        OutputStream os = s.getOutputStream();

                        oos.writeObject(userInsertBack);
                        System.out.println("录入失败！ ");
                    }
                }

                //匹配--1---录入图书功能
                if (flag==1){
                    System.out.println("进行图书录入");
                    List<Book> bookList = fo.getArrayList();
                    int num=0;
                    FlagAndObject bookInsertBack = new FlagAndObject();
                    for(int i = 0;i<bookList.size();i++){
                        Book book;
                        book = bookList.get(i);
                        num = bookServer.insertBookS(book);
                    }

                    if(num>0){
                        bookInsertBack.setFlag(1);
                        OutputStream os = s.getOutputStream();

                        oos.writeObject(bookInsertBack);
                        System.out.println("录入成功！ ");
                    }else {
                        bookInsertBack.setFlag(-1);
                        OutputStream os = s.getOutputStream();

                        oos.writeObject(bookInsertBack);
                        System.out.println("录入失败！ ");
                    }
                }

                //通过书号查询图书
                if (flag==21){
                    Book bookBack;
                    System.out.println("进行通过书号查询图书");
                    List<Book> bookList = fo.getArrayList();

                    Book book;
                    book = bookList.get(0);
                    List<Book> bookListBack = new ArrayList<>();
                    ResultSet rs = bookServer.selectBookByBookNumS(book);
                    try {
                        while (rs.next()){
                            bookBack= new Book();
                            bookBack.setBookNum(rs.getString("bookNum"));
                            bookBack.setBookName( rs.getString("bookName"));
                            bookBack.setBookType( rs.getString("bookAuthor"));
                            bookBack.setBookAuthor(rs.getString("bookType"));
                            bookBack.setBookFactory( rs.getString("bookFactory"));
                            bookBack.setBookCount(rs.getInt("bookCount"));
                            bookListBack.add(bookBack);
                        }

                    } catch (SQLException e) {
                        e.printStackTrace();
                    }

                    //传回的数据
                    FlagAndObject bookSelectByBookNumBack = new FlagAndObject();

                    if(bookListBack.get(0).getBookNum()!=null){
                        bookSelectByBookNumBack.setFlag(21);
                        bookSelectByBookNumBack.setArrayList(bookListBack);
                        OutputStream os = s.getOutputStream();

                        oos.writeObject(bookSelectByBookNumBack);
                        System.out.println("录入成功！ ");
                    }else {
                        bookSelectByBookNumBack.setFlag(-1);
                        OutputStream os = s.getOutputStream();

                        oos.writeObject(bookSelectByBookNumBack);
                        System.out.println("录入失败！ ");
                    }
                }


                //通过书名查询
                if(flag==22){
                    Book bookBack;
                    System.out.println("进行通过书名查询图书");
                    List<Book> bookList = fo.getArrayList();

                    Book book;
                    book = bookList.get(0);
                    List<Book> bookListBack = new ArrayList<>();
                    ResultSet rs = bookServer.selectBookByBookNameS(book);
                    try {
                        while (rs.next()){
                            bookBack= new Book();
                            bookBack.setBookNum(rs.getString("bookNum"));
                            bookBack.setBookName( rs.getString("bookName"));
                            bookBack.setBookType( rs.getString("bookAuthor"));
                            bookBack.setBookAuthor(rs.getString("bookType"));
                            bookBack.setBookFactory( rs.getString("bookFactory"));
                            bookBack.setBookCount(rs.getInt("bookCount"));
                            bookListBack.add(bookBack);
                        }

                    } catch (SQLException e) {
                        e.printStackTrace();
                    }

                    //传回的数据
                    FlagAndObject bookSelectByBookNumBack = new FlagAndObject();

                    if(bookListBack.get(0).getBookNum()!=null){
                        bookSelectByBookNumBack.setFlag(22);
                        bookSelectByBookNumBack.setArrayList(bookListBack);
                        OutputStream os = s.getOutputStream();

                        oos.writeObject(bookSelectByBookNumBack);
                        System.out.println("查询成功！ ");
                    }else {
                        bookSelectByBookNumBack.setFlag(-1);
                        OutputStream os = s.getOutputStream();

                        oos.writeObject(bookSelectByBookNumBack);
                        System.out.println("查询失败！ ");
                    }
                }

                //通过作者名查询
                if(flag==23){
                    Book bookBack;
                    System.out.println("进行通过作者名查询图书");
                    List<Book> bookList = fo.getArrayList();

                    Book book;
                    book = bookList.get(0);
                    List<Book> bookListBack = new ArrayList<>();
                    ResultSet rs = bookServer.selectBookByAuthorS(book);
                    try {
                        while (rs.next()){
                            bookBack= new Book();
                            bookBack.setBookNum(rs.getString("bookNum"));
                            bookBack.setBookName( rs.getString("bookName"));
                            bookBack.setBookType( rs.getString("bookAuthor"));
                            bookBack.setBookAuthor(rs.getString("bookType"));
                            bookBack.setBookFactory( rs.getString("bookFactory"));
                            bookBack.setBookCount(rs.getInt("bookCount"));
                            bookListBack.add(bookBack);
                        }

                    } catch (SQLException e) {
                        e.printStackTrace();
                    }

                    //传回的数据
                    FlagAndObject bookSelectByBookNumBack = new FlagAndObject();

                    if(bookListBack.get(0).getBookNum()!=null){
                        bookSelectByBookNumBack.setFlag(23);
                        bookSelectByBookNumBack.setArrayList(bookListBack);
                        OutputStream os = s.getOutputStream();

                        oos.writeObject(bookSelectByBookNumBack);
                        System.out.println("查询成功！ ");
                    }else {
                        bookSelectByBookNumBack.setFlag(-1);
                        OutputStream os = s.getOutputStream();

                        oos.writeObject(bookSelectByBookNumBack);
                        System.out.println("查询失败！ ");
                    }
                }

                //通过书号修改图书
                if (flag==3){
                    Book bookBack;
                    System.out.println("进行通过书号修改图书");
                    List<Book> bookList = fo.getArrayList();

                    Book book;
                    book = bookList.get(0);
                    int num = bookServer.updateBookS(book);
                    FlagAndObject bookInsertBack = new FlagAndObject();
                    if(num>0){
                        bookInsertBack.setFlag(3);
                        OutputStream os = s.getOutputStream();

                        oos.writeObject(bookInsertBack);
                        System.out.println("修改成功！ ");
                    }else {
                        bookInsertBack.setFlag(-1);
                        OutputStream os = s.getOutputStream();

                        oos.writeObject(bookInsertBack);
                        System.out.println("修改失败！ ");
                    }
                }

                //通过书号删除图书
                if (flag==4){
                    Book bookBack;
                    System.out.println("进行通过书号删除图书");
                    List<Book> bookList = fo.getArrayList();

                    Book book;
                    book = bookList.get(0);
                    int num = bookServer.deleteBookS(book);
                    FlagAndObject bookInsertBack = new FlagAndObject();
                    if(num>0){
                        bookInsertBack.setFlag(4);
                        OutputStream os = s.getOutputStream();

                        oos.writeObject(bookInsertBack);
                        System.out.println("删除成功！ ");
                    }else {
                        bookInsertBack.setFlag(-1);
                        OutputStream os = s.getOutputStream();

                        oos.writeObject(bookInsertBack);
                        System.out.println("删除失败！ ");
                    }
                }
                //通过书号删除图书
                if (flag==40){
                    User userBack;
                    System.out.println("进行通过书号删除图书");
                    List<User> userList = fo.getArrayList();

                    User user;
                    user = userList.get(0);
                    int num = userSever.deleteUserS(user);
                    FlagAndObject bookInsertBack = new FlagAndObject();
                    if(num>0){
                        bookInsertBack.setFlag(40);
                        OutputStream os = s.getOutputStream();

                        oos.writeObject(bookInsertBack);
                        System.out.println("删除成功！ ");
                    }else {
                        bookInsertBack.setFlag(-1);
                        OutputStream os = s.getOutputStream();

                        oos.writeObject(bookInsertBack);
                        System.out.println("删除失败！ ");
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }catch (ClassNotFoundException e){
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        //运行一次就行
        ServerSocket ss=null;
        try {
            ss= new ServerSocket(9999);
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println("服务器运行成功！");
        while(true){
            try {
                s = ss.accept();
            } catch (IOException e) {
                e.printStackTrace();
            }
            Runnable r = new ViewServer(s);
            Thread t = new Thread(r);
            System.out.println(t.getName());
            t.start();
        }

    }
}
