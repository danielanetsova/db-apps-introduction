package DBAppsIntroduction.ex;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class PrintAllMinionNames {
    public static void main(String[] args) {

        try (Connection connection = HelperClass.getMySqlConnection()){
            PreparedStatement preparedStatement = connection.prepareStatement("SELECT name FROM minions");
            ResultSet resultSet = preparedStatement.executeQuery();
            List<String> minionNames = new ArrayList<>();
            while (resultSet.next()) {
                minionNames.add(resultSet.getString("name"));
            }
            String output = "";
            while (!minionNames.isEmpty()) {
               output += String.format("%s%n", minionNames.get(0));
               output += String.format("%s%n", minionNames.get(minionNames.size()-1));
                minionNames.remove(minionNames.size()-1);
                minionNames.remove(0);
            }
            System.out.print(output);
        } catch (SQLException e) {
            throw new RuntimeException();
        }
    }
}
