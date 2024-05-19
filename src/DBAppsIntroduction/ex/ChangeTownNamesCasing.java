package DBAppsIntroduction.ex;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

public class ChangeTownNamesCasing {
    public static void main(String[] args) {

        try (Scanner scanner = new Scanner(System.in); Connection connection = HelperClass.getMySqlConnection()) {

           String countryName = scanner.nextLine();
           PreparedStatement prdStatement = connection.prepareStatement("SELECT name FROM towns WHERE country = ?");
           prdStatement.setString(1, countryName);
           ResultSet resultSet = prdStatement.executeQuery();

            if (!resultSet.next()) {
                System.out.println("No town names were affected.");
            } else {
                List<String> townsList = new ArrayList<>();
                do {
                    townsList.add(resultSet.getString("name").toUpperCase());
                } while (resultSet.next());

                System.out.printf("%d town names were affected.\n" +
                        "%s%n", townsList.size(), Arrays.toString(townsList.toArray()));
            }

        } catch (SQLException e) {
            throw new RuntimeException();
        }
    }
}
