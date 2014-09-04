package db;

import general.ConsoleLogger;
import general.Logger;

import java.sql.Connection;
import java.sql.SQLException;

public abstract class ConnectionManager {

    protected static Logger logger = new ConsoleLogger(
            ConnectionManager.class, 100);

    protected String host = null, user = null, password = null;
    private final int maxParallelConnection;
    protected Connection[] connectionPool = null;
    private int connectionRoundRobinIndex = 0;

    public ConnectionManager(String host, String user, String password,
            int poolSize, int maxParallelConnection) {
        this.host = host;
        this.user = user;
        this.password = password;
        this.maxParallelConnection = maxParallelConnection;

        connectionPool = new Connection[maxParallelConnection];
        
        if (poolSize <= maxParallelConnection) maxParallelConnection = poolSize;
        else throw new RuntimeException("At max " + maxParallelConnection
                + " connections are allowed in pool.");

        for (int i = 0; i < maxParallelConnection; i++) {
            connectionPool[i] = null;
        }
    }
    
    public ConnectionManager(String host, String user, String password,
            int poolSize) {
        this(host, user, password, poolSize, 1);
    }

    protected int getConnectionIndex() {
        int current = connectionRoundRobinIndex;
        connectionRoundRobinIndex = (connectionRoundRobinIndex + 1)
                % maxParallelConnection;
        return current;
    }

    public abstract Connection getConnection();

    public void closeConnection(boolean commit) {
        for (Connection connection : connectionPool) {
            try {
                if (connection != null && connection.isClosed() == false) {
                    if (commit == true) {
                        if (connection.getAutoCommit() == false) connection.commit();
                    }
                    connection.close();
                }
            } catch (SQLException se) {
                logger.error("Failed to close connection: " + se.getMessage());
            } finally {
                connection = null;
            }
        }
    }

    public void closeConnection() {
        closeConnection(true);
    }

}
