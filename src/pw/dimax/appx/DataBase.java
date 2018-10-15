package pw.dimax.appx;

import pw.dimax.util.Log;

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



    public DataBase() {
        this.driver = "org.postgresql.Driver";
        this.url = "jdbc:postgresql://localhost:5432/dmx";

        this.properties = new Properties();
        this.properties.setProperty("password", "2737306");
        this.properties.setProperty("user", "postgres");
        this.properties.setProperty("lc_ctype", "UTF8");
    }



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
            Log.exception(e);
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
