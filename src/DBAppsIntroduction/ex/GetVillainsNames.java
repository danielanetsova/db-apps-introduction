package DBAppsIntroduction.ex;

import java.sql.*;
import java.util.Properties;

public class GetVillainsNames {
    private static final String GET_VILLAINS_NAMES_QUERY = "SELECT v.name, " +
            "count(distinct mv.minion_id) AS \"minions_count\"\n" +
            "FROM villains AS v\n" +
            "JOIN minions_villains AS mv ON v.id = mv.villain_id\n" +
            "GROUP BY mv.villain_id\n" +
            "HAVING minions_count > 15\n" +
            "ORDER BY minions_count DESC";
    private static final String COLUMN_LABEL_FOR_VILLAIN_NAME = "name";
    private static final String COLUMN_LABEL_FOR_VILLAIN_MINIONS_COUNT = "minions_count";

    public static void main(String[] args) throws SQLException {

        final Connection connection = HelperClass.getMySqlConnection();

        PreparedStatement stmt = connection.prepareStatement(GET_VILLAINS_NAMES_QUERY);

        ResultSet resultSet = stmt.executeQuery();

        while (resultSet.next()) {
            print(resultSet);
        }

        connection.close();
    }
    private static void  print(ResultSet resultSet) throws SQLException {
        System.out.printf("%s %d%n", resultSet.getString(COLUMN_LABEL_FOR_VILLAIN_NAME),
                resultSet.getInt(COLUMN_LABEL_FOR_VILLAIN_MINIONS_COUNT));
    }

}
