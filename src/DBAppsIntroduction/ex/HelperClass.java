package DBAppsIntroduction.ex;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public enum HelperClass {

    static Connection getMySqlConnection() throws SQLException {
        final Properties props = new Properties();
        props.setProperty(Constants.USER_KEY, Constants.USER_VALUE);
        props.setProperty(Constants.PASSWORD_KEY, Constants.PASSWORD_VALUE);

        return DriverManager.getConnection(Constants.JDBC_MYSQL_URL, props);
    }
}
