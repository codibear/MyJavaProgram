package BookServer;

import BookServer.server.impl.GetConnection;

/**
 * Created by 29185 on 2017/6/15.
 */
public class test {
    public static void main(String[] args) {
        String  name = new GetConnection().getValue("driver");
        System.out.println(name);
    }
}
