package BookServer.server.impl;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

import java.io.File;
import java.util.HashMap;
import java.util.Iterator;

/**
 * Created by 29185 on 2017/6/22.
 */
public class GetConnection {
    public Iterator<Element> getConnection(){
        Iterator<Element> field=null;
        File file = new File("E:\\javaApp\\BookManager\\xml\\Connection.xml");
        SAXReader reader = new SAXReader();
        try {
            Document d =reader.read(file);
            Element root = d.getRootElement();
            field = root.elementIterator();
        } catch (DocumentException e) {
            e.printStackTrace();
        }
        return field;
    }
    public String getValue(String key){
        Iterator<Element> field=getConnection();
        HashMap<String,String> container=new HashMap<>();
        //可以简单又key值索引就能得到，但是这里训练的是HashMap的应用
        while (field.hasNext()){
            Element e = field.next();
            String name = e.getName();
            String value = e.getText();
            if(name.equals("driver")){
                container.put("driver",value);
            }else if(name.equals("conn")){
                container.put("conn",value);
            }else if(name.equals("username")){
                container.put("username",value);
            }else if(name.equals("password")){
                container.put("password",value);
            }else {
                System.out.println("出错啦@");
            }
        }
        return container.get(key);

        /*String gain = container.get(key);
        switch (key){
            case "dirver":
                gain = container.get(key);
                break;
            case "connection":
                break;
            case "username":
                break;
            case "password":
                break;
            default:
                System.out.println("输入无效字符串");
                break;
        }*/
    }
}
