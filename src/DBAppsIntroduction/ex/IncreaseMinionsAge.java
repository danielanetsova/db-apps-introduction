package DBAppsIntroduction.ex;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;

public class IncreaseMinionsAge {
    public static void main(String[] args)  {
        try (Scanner scanner = new Scanner(System.in);
        Connection connection = HelperClass.getMySqlConnection()){

            String inputIds = scanner.nextLine().replace(" ", ", ");
            String query = String.format("UPDATE minions " +
                    "SET age = age + 1, name = LOWER(name) WHERE id IN (%s)", inputIds);
            PreparedStatement updatePreparedStatement = connection.prepareStatement(query);
            updatePreparedStatement.execute();
            PreparedStatement selectMinionsPrStm = connection.prepareStatement("SELECT name, age FROM minions");
            ResultSet selectMinionsResultSet = selectMinionsPrStm.executeQuery();

            while (selectMinionsResultSet.next()) {
                System.out.printf("%s %d%n", selectMinionsResultSet.getString("name"),
                        selectMinionsResultSet.getInt("age"));
            }

        } catch (SQLException e) {
            throw new RuntimeException();
        }
    }
}
