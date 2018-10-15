package pw.dimax.appx.service.dao;

import java.sql.Connection;

public interface DaoFactory {

    Connection getConnection();

    PersonDao getPersonDao(Connection connection);

    DepartmentDao getDepartmentDao(Connection connection);

}
