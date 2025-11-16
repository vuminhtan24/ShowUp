/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dal;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ResourceBundle;

/**
 *
 * @author VU MINH TAN
 */
public class DBContext {

    protected Connection connection;

    public DBContext() {

        try {
            String url = "jdbc:mysql://localhost:3306/showup_db?useSSL=false&characterEncoding=UTF-8";
            String user = "root";

            String pass = "1234";

            Class.forName("com.mysql.cj.jdbc.Driver"); // MySQL driver mới

            connection = DriverManager.getConnection(url, user, pass);

            System.out.println(" Connected to database!");
        } catch (ClassNotFoundException | SQLException ex) {
            ex.printStackTrace(); // để thấy lỗi rõ hơn khi debug
        }
    }

    public static void main(String[] args) {
        DBContext db = new DBContext();
        if (db.connection != null) {
            System.out.println("✅ Kết nối thành công");
        } else {
            System.out.println("❌ Không kết nối được");
        }
    }
}
