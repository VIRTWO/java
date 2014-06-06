package db;

import general.ConsoleLogger;
import general.Logger;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class QueryExecuter {

    private static Logger logger = new ConsoleLogger(
            QueryExecuter.class, 100);

    private ConnectionManager cm = null;

    public QueryExecuter(ConnectionManager cm) {
        this.cm = cm;
    }

    private int executeUpdate(String query) {
        int error = 0;
        Statement s;
        try {
            s = cm.getConnection().createStatement();
            int rowsInserted = s.executeUpdate(query);
            return rowsInserted;
        } catch (SQLException e) {
            logger.error("Failed to execute update: " + e.getMessage());
            error = e.getErrorCode();
        }

        return -1 * error;
    }

    public int executeUpdateQuery(String updateQuery) {
        return executeUpdate(updateQuery);
    }

    public int executeInsertQuery(String insertQuery) {
        return executeUpdate(insertQuery);
    }

    public long executeCountQuery(String countQuery) {
        int error = 0;
        Statement s;
        try {
            s = cm.getConnection().createStatement();
            ResultSet rs = s.executeQuery(countQuery);
            rs.next();
            return rs.getLong(1);
        } catch (SQLException e) {
            logger.error("Failed to execute count: " + e.getMessage());
            error = e.getErrorCode();
        }

        return -1 * error;
    }

}
