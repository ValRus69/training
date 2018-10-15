package pw.dimax.appx.service.dao;

import pw.dimax.appx.service.Department;

import java.sql.SQLException;
import java.util.List;

public interface DepartmentDao {

    public Department create();

    public Department read(int key);

    public void update(Department department);

    public void delete(Department department);

    public List<Department> getAll() throws SQLException;
}
