package pw.dimax.appx.service.dao;

import pw.dimax.appx.service.Person;

import java.sql.SQLException;
import java.util.List;

public interface PersonDao {

    public Person create();

    public Person read(int key);

    public void update(Person person);

    public void delete(Person person);

    public List<Person> getAll() throws SQLException;
}
