package DBAppsIntroduction.ex;

import java.sql.*;
import java.util.Scanner;

public class IncreaseAgeStoredProcedure {
    public static void main(String[] args) {
        //DELIMITER $$
        //CREATE PROCEDURE usp_get_older(minion_id int)
        //BEGIN
        //
        //UPDATE minions
        //SET age = age + 1
        //where id = minion_id;
        //
        //END $$
        //DELIMITER ;

        try (Scanner scanner = new Scanner(System.in);
             Connection connection = HelperClass.getMySqlConnection()) {

            int inputId = Integer.parseInt(scanner.nextLine());
            CallableStatement callableStatement = connection.prepareCall("CALL usp_get_older(?)");
            callableStatement.setInt(1, inputId);
            callableStatement.execute();

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
