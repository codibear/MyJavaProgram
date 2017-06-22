package BookServer.util;

import BookServer.server.impl.GetConnection;

import java.sql.*;

/**
 * Created by 29185 on 2017/6/15.
 */
public class DButil implements IDButil{
    private Connection conn = null;
    private ResultSet rs = null;
    private PreparedStatement pst = null;
    private GetConnection getConnection = new GetConnection();
    @Override
    public void getConnection() {
        try {
            Class.forName(getConnection.getValue("driver"));
            conn = DriverManager.getConnection(getConnection.getValue("conn"),getConnection.getValue
                    ("username"),getConnection.getValue("password"));
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }catch(SQLException e){
            e.printStackTrace();
        }
    }

    @Override
    public void closeConnection() {

            try {

                if(conn!=null){
                    conn.close();
                } if(pst!=null){
                    pst.close();
                } if(rs!=null){
                    rs.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
    }

    @Override
    public int insert(String string, Object[] objects) {
        int num = 0;
        getConnection();
        try {
            pst = conn.prepareStatement(string);
            for(int i = 0;i<objects.length;i++){
                pst.setObject(i+1,objects[i]);
            }
            num = pst.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return num;
    }

    @Override
    public int delete(String string, Object[] objects) {
        int num = 0;
        getConnection();
        try {
            pst = conn.prepareStatement(string);
            for(int i = 0;i<objects.length;i++){
                pst.setObject(i+1,objects[i]);
            }
            num = pst.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return num;
    }

    @Override
    public int update(String string, Object[] objects) {
        int num = 0;
        getConnection();
        try {
            pst = conn.prepareStatement(string);
            for(int i = 0;i<objects.length;i++){
                pst.setObject(i+1,objects[i]);
            }
            num = pst.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return num;
    }

    @Override
    public ResultSet select(String string, Object[] objects) {
        getConnection();
        if(objects==null){
            try {
                pst = conn.prepareStatement(string);
                pst.executeQuery();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }else {
            try {
                ;pst = conn.prepareStatement(string);
                for(int i = 0;i<objects.length;i++){
                    pst.setObject(i+1,objects[i]);
                }
                rs = pst.executeQuery();
            } catch (SQLException e) {
                e.printStackTrace();
            }

        }
        return rs;
    }
}
