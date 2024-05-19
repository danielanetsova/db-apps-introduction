package DBAppsIntroduction.ex;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;

public class GetMinionNames {

    public static void main(String[] args) throws SQLException {
        String query = "SELECT v.name AS 'villain_name', m.name AS 'minion_name', m.age\n" +
                "FROM villains AS v\n" +
                "JOIN minions_villains AS mv ON v.id = mv.villain_id\n" +
                "JOIN minions AS m ON m.id = mv.minion_id\n" +
                "WHERE mv.villain_id = ?";

        try (Connection connection = HelperClass.getMySqlConnection()) {
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            int villain_id;
            try (Scanner scanner = new Scanner(System.in)) {
                villain_id =  Integer.parseInt(scanner.nextLine());
            }
            preparedStatement.setInt(1, villain_id);
            ResultSet resultSet = preparedStatement.executeQuery();
            print(resultSet, villain_id);
        }
    }

    private static void print(ResultSet resultSet, int villain_id) throws SQLException {
        String ifNoVillainIdExists = String.format("No villain with ID %d exists in the database.", villain_id);
        String villain = String.format("Villain: %s", resultSet.getString("villain_name"));


        if (!resultSet.next()) {
            System.out.println(ifNoVillainIdExists);
        } else {
            System.out.println(villain);
            int counter = 0;
            do {
                counter++;
                String minionNameAndAge = String.format("%d. %s %d",
                        counter,
                        resultSet.getString("minion_name"),
                        resultSet.getInt("age")
                );
                System.out.println(minionNameAndAge);
            } while (resultSet.next());
        }
    }
}
