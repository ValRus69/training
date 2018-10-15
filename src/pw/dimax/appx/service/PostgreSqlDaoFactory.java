package pw.dimax.appx.service;

import pw.dimax.appx.service.dao.DaoFactory;
import pw.dimax.appx.service.dao.DepartmentDao;
import pw.dimax.appx.service.dao.PersonDao;
import pw.dimax.util.Log;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class PostgreSqlDaoFactory implements DaoFactory {

    private String driver = "org.postgresql.Driver";
    private String url = "jdbc:postgresql://localhost:5432/dmx";
    private String user = "postgres";
    private String password = "2737306";

    private Connection connection;

    @Override
    public Connection getConnection() {
        if(connection != null){
            return connection;
        }else {
            try {
                Class.forName( driver );
            } catch (ClassNotFoundException e) {
                Log.exception(e);
            }

            try {
                connection = DriverManager.getConnection( url, user, password );
            } catch (SQLException e) {
                Log.exception(e);
                connection = null;
            }
        }

        return connection;
    }

    @Override
    public PersonDao getPersonDao(Connection connection) {
        return null;
    }

    @Override
    public DepartmentDao getDepartmentDao(Connection connection) {
        return null;
    }
}
