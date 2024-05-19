package DBAppsIntroduction.ex;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class PrintAllMinionNamesLectorSolution {
    private static final String GET_ALL_MINIONS = "select name from minions_db.minions";

    public static void main(String[] args) throws SQLException {
        //алгоритъмът му НЕ е правилен, не връща правилния резултат, но запазих решението заради preparestatement-a и
        //методът beforeFirst().
        final Connection sqlConnection = HelperClass.getMySqlConnection();

        final PreparedStatement statement = sqlConnection.prepareStatement(GET_ALL_MINIONS,
                ResultSet.TYPE_SCROLL_INSENSITIVE,
                ResultSet.CONCUR_READ_ONLY);

        final ResultSet minionsResultSet = statement.executeQuery();

        int minionsCount = 0;

        while (minionsResultSet.next()) minionsCount++;

        minionsResultSet.beforeFirst();

        int firstIndex = 1;
        int lastIndex = minionsCount;

        for (int i = 1; i < minionsCount + 1; i++) {
            if (i % 2 == 0) {
                minionsResultSet.absolute(firstIndex);
                firstIndex++;
            } else {
                minionsResultSet.absolute(lastIndex);
                lastIndex--;
            }

            System.out.println(minionsResultSet.getString("name"));

            minionsResultSet.next();
        }
    }
}
