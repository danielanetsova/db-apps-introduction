package DBAppsIntroduction.ex;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;

public class AddMinion {
    public static void main(String[] args) {
        String minionName;
        int minionAge;
        String townName;
        String villainName;

        try (Scanner scanner = new Scanner(System.in)) {
            List<String> minionInfo = Arrays.stream(scanner.nextLine().split(" ")).collect(Collectors.toList());
            minionName = minionInfo.get(1);
            minionAge = Integer.parseInt(minionInfo.get(2));
            townName = minionInfo.get(3);

            String villainInfo = scanner.nextLine().split(" ")[1];
            villainName = villainInfo;
        }

        try (Connection connection = HelperClass.getMySqlConnection()){

            // 1. проверка дали градът съществува, ако не съществува го добавяме
            addTownIfDoesntExist(connection, townName);

            //2. добавяне на миниона в базата
            addMinionToTheDatabase(connection, townName, minionName, minionAge);

            //3. проверка дали лошковецът съществува, ако не съществува го добавяме с "evil"
            addVillainIfExists(connection, villainName);

            //4. правим миниона слуга на лошковеца
            makeNewMinionServantOfTheVillain(connection, minionName, villainName);

        } catch (SQLException e) {
            throw new RuntimeException();
        }


    }

    private static int returnId(ResultSet resultSet) throws SQLException {
        int id = 0;
        if (resultSet.next()) {
            id = resultSet.getInt("id");
        } else {
           throw new SQLException();
        }
        return id;
    }

    private static void addTownIfDoesntExist(Connection connection, String townName)
            throws SQLException {
        String selectQuery = String.format("select name from towns where name = \'%s\'", townName);
        PreparedStatement selectQueryPreparedStatement = connection.prepareStatement(selectQuery);
        ResultSet rltSetIfTownExist =  selectQueryPreparedStatement.executeQuery();
        if (!rltSetIfTownExist.next()) {
            String insertQuery = String.format("INSERT INTO towns(name) VALUES(\'%s\')", townName);
            PreparedStatement insertQueryPreparedStatement = connection.prepareStatement(insertQuery);
            insertQueryPreparedStatement.execute();
            System.out.printf("Town %s was added to the database.%n", townName);
        }
    }

    private static void addMinionToTheDatabase(Connection connection, String townName, String minionName, int minionAge)
                                                throws SQLException {
        String selectQuery = String.format("select id from towns where name = \'%s\'", townName);
        PreparedStatement selectQueryPreparedStatement = connection.prepareStatement(selectQuery);
        ResultSet selectQueryResultSet = selectQueryPreparedStatement.executeQuery();
        int town_id = returnId(selectQueryResultSet);
        String valuesToInsert = String.format("\'%s\', %d, %d", minionName, minionAge, town_id);
        String insertQuery = String.format("INSERT INTO minions(name, age, town_id) VALUES(%s)", valuesToInsert);
        connection.prepareStatement(insertQuery).execute();
    }

    private static void addVillainIfExists(Connection connection, String villainName) throws SQLException {

        String selectQuery = String.format("select name from villains where name = \'%s\'", villainName);
        PreparedStatement selectQueryPreparedStatement = connection.prepareStatement(selectQuery);
        ResultSet resultSet = selectQueryPreparedStatement.executeQuery();
        //добавяне на лошковеца ако не съществува
        if (!resultSet.next()) {
            String valuesToInsert = String.format("\'%s\', \'evil\'", villainName);
            String insertQuery = String.format("INSERT INTO villains(name, evilness_factor) VALUES(%s)", valuesToInsert);
            connection.prepareStatement(insertQuery).execute();
            System.out.printf("Villain %s was added to the database.%n", villainName);
        }
    }

    private static void makeNewMinionServantOfTheVillain(Connection connection, String minionName, String villainName)
            throws SQLException {

        String selectMinionIdQuery = String.format("select id from minions where name = \'%s\'", minionName);
        PreparedStatement selectMinionIdPreparedStatement = connection.prepareStatement(selectMinionIdQuery);
        ResultSet selectMinionIdResultSet = selectMinionIdPreparedStatement.executeQuery();
        int minion_id = returnId(selectMinionIdResultSet);

        String selectVillainIdQuery = String.format("select id from villains where name = \'%s\'", villainName);
        PreparedStatement selectVillainIdPreparedStatement = connection.prepareStatement(selectVillainIdQuery);
        ResultSet selectVillainIdResultSet = selectVillainIdPreparedStatement.executeQuery();
        int villain_id = returnId(selectVillainIdResultSet);

        String insertQuery = String.format("INSERT INTO minions_villains(minion_id, villain_id) VALUES(%d, %d)",
                minion_id, villain_id);
        connection.prepareStatement(insertQuery).execute();

        System.out.printf("Successfully added %s to be minion of %s.%n", minionName, villainName);
    }
}
