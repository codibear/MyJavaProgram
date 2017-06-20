package BookClient.view;


import kotlin.reflect.jvm.internal.impl.serialization.deserialization.NotFoundClasses;
import po.Book;
import po.FlagAndObject;
import po.User;

import java.io.*;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 * Created by 29185 on 2017/6/16.
 */
public class ViewClient {
    private static Socket s;
    private static OutputStream os;
    private static InputStream is;
    private static Scanner in = new Scanner(System.in);
    //private static FlagAndObject fo = new FlagAndObject();-----------  不在这里用的原因是因为，我每次都需要一个空的FlagAndObject类型的对象
    private static ObjectOutputStream oss;
    private static ObjectInputStream ois;
    public static void main(String[] args) {


            try {
                while (true){
                //创建连接，用于登录连接
                s = new Socket("127.0.0.1",9999);

                //最好别在这写，因为容易出现当前状态为等待状态，为下面的操作制作障碍
                //应该什么时候用什么时候调用

                os = s.getOutputStream();

                //登录，成功后跳转到主页面
                login();
            }
            } catch (IOException e) {
                e.printStackTrace();
            }

    }

    public static void login(){
        FlagAndObject fo = new FlagAndObject();
        //传递数据的准备
        int flag=-1;
        List<User> userList = new ArrayList<>();

        User user = new User();
        System.out.println("*********************欢迎来到登录界面******************\n");
        System.out.println("请输入账号：");
        user.setUserName(in.next());
        System.out.println("请输入密码：");
        user.setPwd(in.next());
        flag=0;
        userList.add(user);
        fo.setFlag(flag);
        fo.setArrayList(userList);
        //正式传递数据
        try {
            //传出
            oss = new ObjectOutputStream(os);
            oss.writeObject(fo);

            //oss.close();  写在这就会关闭socket

            //接收返回信息
            is = s.getInputStream();
            ois = new ObjectInputStream(is);
            FlagAndObject userListBack =(FlagAndObject) ois.readObject();
            flag = userListBack.getFlag();
            ois.close();
            is.close();
            if(flag==0){
                //进入操作界面
                System.out.println("登录成功！");
                s.close(); //如果在这里关闭socke，后面没法操作，程序关闭
                while (true){
                    show();
                }
            }else {
                System.out.println("登录失败，请重新登录！");
                s.close();
            }



        } catch (IOException e) {
            e.printStackTrace();
        }catch (ClassNotFoundException e){
            e.printStackTrace();
        }


        //管家操作
        System.out.println("请选择相应的操作：\n1.登录\n2.查看账户\n删除帐户");

    }
    public static void show(){
        System.out.println("======================成功进入电子图书馆======================");
        System.out.println("请选择操作代号：\n1.录入图书\n2.查询图书\n3.修改图书\n4.删除图书");
        int check = in.nextInt();
        switch (check){
            case 1:
                addBook();
                break;
            case 2:
                System.out.println("成功进入查询界面请选择您要做的操作\n1.通过书号查询\n2.通过书名查询\n3.通过作者查询");
                int checkSelect = in.nextInt();
                switch (checkSelect){
                    case 1:
                        selectBookByBookNum();
                        break;
                    case 2:
                        selectBookByBookName();
                        break;
                    case 3:
                        selectBookByBookAuthor();
                        break;
                    default:
                        close();
                        break;
                }

                break;
            case 3:
                updateBook();
                break;
            case 4:
                deleteBook();
                break;
            default:
                //退出
                close();
                break;
        }

    }
    //bookNum,bookName,bookType,bookAuthor,bookFactory
    //想服务器传递flag=1 证明在执行添加图书操作
    public static void addBook(){
        FlagAndObject fo = new FlagAndObject();
        List <Book> books= new ArrayList<>();
        try {
            //准备发送信息
            s = new Socket("127.0.0.1",9999);
            os = s.getOutputStream();
            oss = new ObjectOutputStream(os);

            System.out.println("××××××××××   录入图书   ××××××××××");
            boolean b = true;
            while (b) {
                Book book = new Book();
                System.out.println("请输入图书的书号：");
                book.setBookNum(in.next());
                System.out.println("请输入图书的书名：");
                book.setBookName(in.next());
                System.out.println("请输入图书的类型：");
                book.setBookType(in.next());
                System.out.println("请输入图书的作者：");
                book.setBookAuthor(in.next());
                System.out.println("请输入图书的出版社：");
                book.setBookFactory(in.next());
                books.add(book);
                System.out.println("是否继续录入？  （输入N退出录入，任意键继续）");
                if (in.next().equalsIgnoreCase("n")){
                    b=false;
                }
            }
            fo.setFlag(1);
            fo.setArrayList(books);
            oss.writeObject(fo);

            //接收信息
            is = s.getInputStream();
            ois = new ObjectInputStream(is);

            FlagAndObject fo2= (FlagAndObject)ois.readObject();
            int flag = fo2.getFlag();
            if(flag==1){
                System.out.println("录入成功！");
            }else {
                System.out.println("录入失败@请重新录入！");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }catch (ClassNotFoundException e){
            e.printStackTrace();
        }finally {
            close();
        }
    }


    //通过书号查询--21
    public static void selectBookByBookNum(){
        FlagAndObject fo = new FlagAndObject();
        List <Book> books= new ArrayList<>();
        try {
            //准备发送信息
            s = new Socket("127.0.0.1",9999);
            os = s.getOutputStream();
            oss = new ObjectOutputStream(os);

            System.out.println("××××××××××   通过书号查询图书   ××××××××××");
            Book book = new Book();
            System.out.println("请输入图书的书号：");
            book.setBookNum(in.next());
            books.add(book);

            fo.setFlag(21);
            fo.setArrayList(books);
            oss.writeObject(fo);

            //接收信息
            is = s.getInputStream();
            ois = new ObjectInputStream(is);

            FlagAndObject fo2= (FlagAndObject)ois.readObject();
            int flag = fo2.getFlag();
            List <Book> bookListBack = fo2.getArrayList();
            System.out.println("书号\t\t书名\t\t类型\t\t作者\t\t出版社");
            if(flag==21){
                Book bookBack = bookListBack.get(0);
                //输出图书的详细内容
                System.out.println(bookBack.getBookNum()+"\t\t"+bookBack.getBookName()+"\t\t"+bookBack.getBookType()
                        +"\t\t"+bookBack.getBookAuthor()+"\t\t"+bookBack.getBookFactory());
                System.out.println("查询成功！");
            }else {
                System.out.println("书库中没有此书@请重新输入！");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }catch (ClassNotFoundException e){
            e.printStackTrace();
        }finally {
            close();
        }
    }

    //通过书名查询--22
    public static void selectBookByBookName(){
        FlagAndObject fo = new FlagAndObject();
        List <Book> books= new ArrayList<>();
        try {
            //准备发送信息
            s = new Socket("127.0.0.1",9999);
            os = s.getOutputStream();
            oss = new ObjectOutputStream(os);

            System.out.println("××××××××××   通过书名查询图书   ××××××××××");
            Book book = new Book();
            System.out.println("请输入图书的书名：");
            book.setBookName(in.next());
            books.add(book);

            fo.setFlag(22);
            fo.setArrayList(books);
            oss.writeObject(fo);

            //接收信息
            is = s.getInputStream();
            ois = new ObjectInputStream(is);

            FlagAndObject fo2= (FlagAndObject)ois.readObject();
            int flag = fo2.getFlag();
            List <Book> bookListBack = fo2.getArrayList();
            System.out.println("书号\t\t书名\t\t类型\t\t作者\t\t出版社");
            if(flag==22){
                for(int i = 0;i<bookListBack.size();i++){
                Book bookBack = bookListBack.get(i);
                //输出图书的详细内容
                System.out.println(bookBack.getBookNum()+"\t\t"+bookBack.getBookName()+"\t\t"+bookBack.getBookType()
                        +"\t\t"+bookBack.getBookAuthor()+"\t\t"+bookBack.getBookFactory());
                }
               System.out.println("查询成功！");
            }else {
                System.out.println("书库中没有此书@请重新输入！");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }catch (ClassNotFoundException e){
            e.printStackTrace();
        }finally {
            close();
        }
    }
    //通过作者名查询--22
    public static void selectBookByBookAuthor(){
        FlagAndObject fo = new FlagAndObject();
        List <Book> books= new ArrayList<>();
        try {
            //准备发送信息
            s = new Socket("127.0.0.1",9999);
            os = s.getOutputStream();
            oss = new ObjectOutputStream(os);

            System.out.println("××××××××××   通过作者查询图书   ××××××××××");
            Book book = new Book();
            System.out.println("请输入图书的作者名：");
            book.setBookAuthor(in.next());
            books.add(book);

            fo.setFlag(23);
            fo.setArrayList(books);
            oss.writeObject(fo);

            //接收信息
            is = s.getInputStream();
            ois = new ObjectInputStream(is);

            FlagAndObject fo2= (FlagAndObject)ois.readObject();
            int flag = fo2.getFlag();
            List <Book> bookListBack = fo2.getArrayList();
            System.out.println("书号\t\t书名\t\t类型\t\t作者\t\t出版社");
            if(flag==23){
                for(int i = 0;i<bookListBack.size();i++){
                    Book bookBack = bookListBack.get(i);
                    //输出图书的详细内容
                    System.out.println(bookBack.getBookNum()+"\t\t"+bookBack.getBookName()+"\t\t"+bookBack.getBookType()
                            +"\t\t"+bookBack.getBookAuthor()+"\t\t"+bookBack.getBookFactory());
                }
                System.out.println("查询成功！");
            }else {
                System.out.println("书库中没有此书@请重新输入！");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }catch (ClassNotFoundException e){
            e.printStackTrace();
        }finally {
            close();
        }
    }

    //通过书号查询--3
    public static void updateBook(){
        FlagAndObject fo = new FlagAndObject();
        List <Book> books= new ArrayList<>();
        try {
            //准备发送信息
            s = new Socket("127.0.0.1",9999);
            os = s.getOutputStream();
            oss = new ObjectOutputStream(os);

            System.out.println("××××××××××   修改图书   ××××××××××");
            Book book = new Book();
            System.out.println("请输入要修改的图书书号：");
            book.setBookNum(in.next());
            System.out.println("请输入修改后的图书书名：");
            book.setBookName(in.next());
            System.out.println("请输入修改后的图书类型：");
            book.setBookType(in.next());
            System.out.println("请输入修改后的图书作者：");
            book.setBookAuthor(in.next());
            System.out.println("请输入修改后的图书出版社：");
            book.setBookFactory(in.next());
            books.add(book);
            fo.setFlag(3);
            fo.setArrayList(books);
            oss.writeObject(fo);

            //接收信息
            is = s.getInputStream();
            ois = new ObjectInputStream(is);

            FlagAndObject fo2= (FlagAndObject)ois.readObject();
            int flag = fo2.getFlag();
            if(flag==3){
                System.out.println("修改成功！");
            }else {
                System.out.println("书库中没有此书@请重新输入！");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }catch (ClassNotFoundException e){
            e.printStackTrace();
        }finally {
            close();
        }
    }

    //通过书号删除--4
    public static void deleteBook(){
        FlagAndObject fo = new FlagAndObject();
        List <Book> books= new ArrayList<>();
        try {
            //准备发送信息
            s = new Socket("127.0.0.1",9999);
            os = s.getOutputStream();
            oss = new ObjectOutputStream(os);

            System.out.println("××××××××××   修改图书   ××××××××××");
            Book book = new Book();
            System.out.println("请输入要删除的图书书号：");
            book.setBookNum(in.next());
            books.add(book);
            fo.setFlag(4);
            fo.setArrayList(books);
            oss.writeObject(fo);

            //接收信息
            is = s.getInputStream();
            ois = new ObjectInputStream(is);

            FlagAndObject fo2= (FlagAndObject)ois.readObject();
            int flag = fo2.getFlag();
            if(flag==4){
                System.out.println("删除成功！");
            }else {
                System.out.println("书库中没有此书@请重新输入！");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }catch (ClassNotFoundException e){
            e.printStackTrace();
        }finally {
            close();
        }
    }

    public static void close(){

            try {
                if(s!=null){
                    s.close();
                }if(oss!=null){
                    oss.close();
                }if(os!=null){
                    os.close();
                }if(ois!=null){
                    s.close();
                }if(is!=null){
                    is.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }

    }
}

