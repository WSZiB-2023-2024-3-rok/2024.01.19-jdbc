package pl.edu.eszib.jdbc;

import java.sql.*;

public class App {
    public static Connection connection;

    public static void main(String[] args) {
        connect();
        User user = new User();
        user.setLogin("admin");
        user.setPassword("admin123");
        user.setRole(User.Role.ADMIN);
        saveUser2(user);
        System.out.println(user.getId());
        disconnect();
    }

    private static void connect() {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            App.connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/test5",
                    "root", "");
        } catch (ClassNotFoundException | SQLException e) {
            System.out.println("cos nie pyklo !!");
            e.printStackTrace();
        }
    }

    private static void disconnect() {
        try {
            App.connection.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private static void saveUser(User user) {
        try {
            String sql =
                    new StringBuilder("INSERT INTO tuser (login, password, role) VALUES (")
                            .append("'").append(user.getLogin()).append("',")
                            .append("'").append(user.getPassword()).append("',")
                            .append("'").append(user.getRole().toString()).append("'")
                            .append(");").toString();

            Statement statement = connection.createStatement();
            statement.execute(sql);
        } catch(SQLException e) {
            e.printStackTrace();
        }
    }

    private static void saveUser2(User user) {
        try {
            String sql = "INSERT INTO tuser (login, password, role) VALUES (?,?,?);";
            PreparedStatement preparedStatement = connection.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS);

            preparedStatement.setString(1, user.getLogin());
            preparedStatement.setString(2, user.getPassword());
            preparedStatement.setString(3, user.getRole().toString());

            preparedStatement.executeUpdate();
            //preparedStatement.clearParameters();

            ResultSet rs = preparedStatement.getGeneratedKeys();
            rs.next();
            user.setId(rs.getInt(1));
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
