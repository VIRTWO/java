package db;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.DriverManager;

public class MysqlConnectionManager extends ConnectionManager {

    // hostURL => hostname.com/PPR
    public MysqlConnectionManager(String hostURL, String user, String passowrd,
            int poolSize) {
        this(hostURL, user, passowrd, poolSize, 1);
    }
    
    public MysqlConnectionManager(String hostURL, String user, String passowrd,
            int poolSize, int maxParallelConnection) {
        super(hostURL, user, passowrd, poolSize, maxParallelConnection);
    }

    public Connection getConnection() {

        int connectionIndex = getConnectionIndex();
        Connection connection = connectionPool[connectionIndex];

        try {
            // return old connection
            if (connection != null && connection.isClosed() == false) return connection;

            Class.forName("com.mysql.jdbc.Driver");
            String url = "jdbc:mysql://" + this.host + "?user=" + this.user
                    + "&password=" + this.password;
            connection = DriverManager.getConnection(url);

        } catch (ClassNotFoundException cnfe) {
            logger.error("MySQL driver not found: " + cnfe.getMessage());
            connection = null;
        } catch (SQLException se) {
            logger.error("Failed to open connection: " + se.getMessage());
            connection = null;
        }

        // we are here means a new connection
        connectionPool[connectionIndex] = connection;

        return connection;
    }

}
