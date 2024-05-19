package DBAppsIntroduction.ex;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public enum HelperClass {
    //класът трябва да е от тип SINGLETON, тъй като util-ите му ще бъдат използвани през цялото съществуване на
    //програмата и ще връщат едни и същи резултати. Искаме класът да има само една истанция и да не се вдига конструктор
    //за да се инициализира. Най-простият начин за създаване на клас в Java, който да е тип SINGLETON, да има само
    //една истанция в нашето приложение и да не се изисква конструктор.

    ;   // след поставянето на точка и запетая в създадения сингълтън клас да създаваме методи и променливи.

    // Това означава че този enum клас може да държи в себе си константите, методи които да връщат някакъв обект.

    static Connection getMySqlConnection() throws SQLException {
        final Properties props = new Properties();
        props.setProperty(Constants.USER_KEY, Constants.USER_VALUE);
        props.setProperty(Constants.PASSWORD_KEY, Constants.PASSWORD_VALUE);

        return DriverManager.getConnection(Constants.JDBC_MYSQL_URL, props);
    }

    }
