package edu.employee.dao;

import java.util.List;

import edu.employee.vo.EmployeeVO;

import java.sql.SQLException;

public interface EmployeeDao {
    List<EmployeeVO> getDepartmentEmployees(String deptTitle) throws SQLException;

    List<EmployeeVO> getDepartmentAvgSalary() throws SQLException;

    List<EmployeeVO> getWorkingEmployees() throws SQLException;

    int increaseSalary(String deptCode) throws SQLException;

    List<EmployeeVO> getEmployeesWithoutPhone() throws SQLException;
}
