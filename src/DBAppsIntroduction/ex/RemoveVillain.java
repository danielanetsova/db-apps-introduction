package DBAppsIntroduction.ex;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;

public class RemoveVillain {
    //1. взимаме името на злодея, ако resultset-a е празен No such villain was found
    //2. ако не е празен взимаме каунта на минионите които владее и изтриваме записите
    //3. истриваме злодея от базата и изпринтваме Carl was deleted 16 minions released

    public static void main(String[] args) {

        int inputId;
        try (Scanner scanner = new Scanner(System.in)){
            inputId = Integer.parseInt(scanner.nextLine());
        }

        try (Connection connection = HelperClass.getMySqlConnection()){
            PreparedStatement selectVillainNamePrStm = connection.prepareStatement
                    ("SELECT name FROM villains WHERE id = ?");
            selectVillainNamePrStm.setInt(1, inputId);
            ResultSet selectVillainNameResSet = selectVillainNamePrStm.executeQuery();

            if (!selectVillainNameResSet.next()) {
                System.out.println("No such villain was found.");
            } else {
                int countOfServantMinions = 0;

                PreparedStatement selectCountOfServantMinionsPrStm = connection.prepareStatement
                        ("SELECT COUNT(*) FROM minions_villains WHERE villain_id = ?");
                selectCountOfServantMinionsPrStm.setInt(1, inputId);
                ResultSet selectCountOfServantMinionsResSet = selectCountOfServantMinionsPrStm.executeQuery();
                if (selectCountOfServantMinionsResSet.next()) {
                    countOfServantMinions = selectCountOfServantMinionsResSet.getInt("COUNT(*)");
                    PreparedStatement deleteFromMinionsVillains = connection.prepareStatement
                            ("DELETE FROM minions_villains WHERE villain_id = ?");
                    deleteFromMinionsVillains.setInt(1, inputId);
                    deleteFromMinionsVillains.execute();
                } // вместо тази проверка може да се използва connection.setAutoCommit(false);
                PreparedStatement delVillainPrStm = connection.prepareStatement("DELETE FROM villains WHERE id = ?");
                delVillainPrStm.setInt(1, inputId);
                delVillainPrStm.execute();

                String villainName = selectVillainNameResSet.getString("name");
                System.out.printf("%s was deleted\n" +
                        "%d minions released%n", villainName, countOfServantMinions);
            }
        } catch (SQLException e) {
            throw new RuntimeException();
        }
    }
}
