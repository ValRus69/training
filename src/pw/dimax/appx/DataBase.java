package pw.dimax.appx;

import javax.sql.rowset.CachedRowSet;
import javax.sql.rowset.RowSetFactory;
import javax.sql.rowset.RowSetProvider;
import java.sql.*;
import java.util.Properties;

public class DataBase {

    private String driver;
    private String url;
    private Properties properties;
    private Connection connection;



    public DataBase() {}

    public DataBase(String driver, String url, String login, String password, String charset) {
        this.driver = driver;
        this.url = url;

        this.properties = new Properties();
        this.properties.setProperty("password", password);
        this.properties.setProperty("user", login);
        this.properties.setProperty("lc_ctype", charset);
    }



    public DataBase Connect() {
        try {
            Class.forName( getDriver() );
        } catch (ClassNotFoundException e) {
            Log.exception(e);;
        }

        try {
            connection = DriverManager.getConnection( getUrl(), getProperties());
        } catch (SQLException e) {
            Log.exception(e);
            connection = null;
        }

        return this;
    }



    public void Disconnect(Connection connection) {
        try {
            connection.close();
        } catch (SQLException e) {
            Log.exception(e);
        }
        this.connection = null;
    }



    public CachedRowSet executeQuery(String query) {
        CachedRowSet crs = null;
        try {
            this.Connect();
            Statement stmt = connection.createStatement();
            ResultSet rs = stmt.executeQuery(query);

            RowSetFactory factory = RowSetProvider.newFactory();
            crs = factory.createCachedRowSet();

            crs.populate(rs);

            rs.close();
            stmt.close();
        } catch (SQLException e) {
            Log.exception(e);
        } finally {
            this.Disconnect(connection);
        }

        return crs;
    }



    public int executeUpdate(String query) {
        int numrows = 0;
        try {
            this.Connect();
            Statement stmt = connection.createStatement();
            numrows = stmt.executeUpdate(query);
            stmt.close();
        } catch (SQLException e) {
            Log.exception(e);
        } finally {
            this.Disconnect(connection);
        }

        return numrows;
    }



    public boolean execute(String query) {
        boolean res = false;
        try {
            this.Connect();
            Statement stmt = connection.createStatement();
            res = stmt.execute(query);
            stmt.close();
        } catch (SQLException e) {
            Log.exception(e);
        } finally {
            this.Disconnect(connection);
        }

        return res;
    }



    public Connection getConnection() {
        return connection;
    }

    public String getDriver() {
        if(driver == null) {
            throw new NullPointerException("String 'driver' not initialized");
        }
        return driver;
    }

    public String getUrl() {
        if(url == null) {
            throw new NullPointerException("String 'url' not initialized");
        }
        return url;
    }

    public Properties getProperties() {
        if(properties == null) {
            throw new NullPointerException("Properties 'properties' not initialized");
        }
        return properties;
    }



    public void setDriver(String driver) {
        this.driver = driver;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public void setProperties(String login, String password) {
        Properties properties = new Properties();
        properties.setProperty("password", password);
        properties.setProperty("user", login);
        this.properties = properties;
    }

    public void setProperties(String login, String password, String charset) {
        Properties properties = new Properties();
        properties.setProperty("password", password);
        properties.setProperty("user", login);
        properties.setProperty("lc_ctype", charset);
        this.properties = properties;
    }
}
