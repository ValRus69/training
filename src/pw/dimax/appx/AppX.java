package pw.dimax.appx;

import pw.dimax.util.Log;

import javax.sql.rowset.CachedRowSet;
import java.sql.SQLException;

public class AppX {

    public static void main(String args[]){

        DataBase db = new DataBase();
        CachedRowSet crs = db.executeQuery("select * from PERSON");

        try {
            while (crs.next()) {
                for (int i = 0; i < crs.getMetaData().getColumnCount(); i++) {
                    Log.print(crs.getMetaData().getColumnName(i+1) +
                            ": " + crs.getString(i+1));
                }
                Log.print("===============");
            }
        } catch (SQLException e){
            Log.exception(e);
        }
    }
}
