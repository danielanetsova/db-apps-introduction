package DBAppsIntroduction.lab;

import java.sql.*;
import java.util.Properties;
import java.util.Scanner;

public class DataRetrievalApplication {
    public static void main(String[] args) throws SQLException {
        Scanner scanner = new Scanner(System.in);

        System.out.print("Username: ");
        String user = scanner.nextLine();
        user = user.isEmpty() ? "root" : user;

        System.out.print("Password: ");
        String pass = scanner.nextLine();
        System.out.println();

        Properties properties = new Properties();
        properties.setProperty("user", user);
        properties.setProperty("password", pass);

        Connection connection = DriverManager.
                getConnection("jdbc:mysql://localhost:3306/diablo", properties);

        PreparedStatement prStmt = connection.prepareStatement("SELECT u.user_name, u.first_name, u.last_name,\n" +
                "COUNT(*) as 'played_games'\n" +
                "FROM users AS u\n" +
                "JOIN users_games AS ug ON u.id = ug.user_id\n" +
                "WHERE user_name = ?\n" +
                "GROUP BY u.user_name, u.first_name, u.last_name");

        System.out.print("Searched username: ");
        String userInDB = scanner.nextLine();
        prStmt.setString(1, userInDB);

        ResultSet resultSet = prStmt.executeQuery();

        if (!resultSet.next()) {
            System.out.println("No such user exists");
        } else {
            do  {
                System.out.printf("User: %s%n%s %s has played %d games%n",
                        resultSet.getString("user_name"),
                        resultSet.getString("first_name"),
                        resultSet.getString("last_name"),
                        resultSet.getInt("played_games"));
            } while (resultSet.next());
        }
        connection.close();
    }
}
