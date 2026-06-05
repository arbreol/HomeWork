package edu.employee.dao;

import edu.common.JDBCUtil;
import edu.employee.vo.EmployeeVO;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class EmployeeDaoImpl implements EmployeeDao {

    @Override
    public List<EmployeeVO> getDepartmentEmployees(String deptTitle) throws SQLException {
        List<EmployeeVO> list = new ArrayList<>();

        String sql = "SELECT E.EMP_NAME AS 사원명, D.DEPT_TITLE AS 부서명, J.JOB_NAME AS 직급명, " +
                "CASE WHEN E.BONUS IS NULL THEN '보너스 없음' ELSE E.BONUS END AS 보너스율, " +
                "CASE WHEN E.ENT_YN = 'Y' THEN '재직' " +
                "WHEN E.ENT_YN = 'N' THEN '퇴사' ELSE '미정' END AS 퇴직여부 " +
                "FROM EMPLOYEE E " +
                "INNER JOIN DEPARTMENT D ON E.DEPT_CODE = D.DEPT_ID " +
                "INNER JOIN JOB J USING (JOB_CODE) " +
                "WHERE D.DEPT_TITLE = ? " +
                "ORDER BY E.BONUS DESC";

        try (
                Connection connection = JDBCUtil.getConnection();
                PreparedStatement preparedStatement = connection.prepareStatement(sql);
        ) {
            preparedStatement.setString(1, deptTitle);

            try (
                    ResultSet resultSet = preparedStatement.executeQuery();
            ) {
                while (resultSet.next()) {
                    EmployeeVO e = new EmployeeVO();
                    e.setEmpName(resultSet.getString(1));
                    e.setDeptTitle(resultSet.getString(2));
                    e.setJobName(resultSet.getString(3));
                    e.setBonus(resultSet.getString(4));
                    e.setEntYn(resultSet.getString(5));

                    list.add(e);
                }
            }
        }
        return list;
    }

    @Override
    public List<EmployeeVO> getDepartmentAvgSalary() throws SQLException {
        List<EmployeeVO> list = new ArrayList<>();

        String sql = "SELECT D.DEPT_TITLE AS \"부서명\", J.JOB_NAME AS \"직급명\", round(avg(E.SALARY), 0) AS \"평균 급여\" " +
                "FROM EMPLOYEE E INNER JOIN DEPARTMENT D ON (E.DEPT_CODE = D.DEPT_ID) INNER JOIN JOB J USING (JOB_CODE) " +
                "WHERE E.ENT_YN = 'N' " +
                "GROUP BY D.DEPT_TITLE, J.JOB_NAME " +
                "HAVING round(avg(E.SALARY), 0) >= 3000000 " +
                "ORDER BY round(avg(E.SALARY), 0) DESC; ";

        try (Connection connection = JDBCUtil.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql);
             ResultSet resultSet = preparedStatement.executeQuery();
        ) {
            while (resultSet.next()) {
                EmployeeVO e = new EmployeeVO();

                e.setDeptTitle(resultSet.getString(1));
                e.setJobName(resultSet.getString(2));
                e.setAvgSalary(resultSet.getInt(3));

                list.add(e);
            }
        }
        return list;
    }

    @Override
    public List<EmployeeVO> getWorkingEmployees() throws SQLException {

        List<EmployeeVO> list = new ArrayList<>();

        String sql = "SELECT D.DEPT_TITLE AS \"부서명\", J.JOB_NAME AS \"직급명\", E.EMP_NAME AS \"사원명\", E.SALARY AS \"급여\" " +
                "FROM EMPLOYEE E LEFT OUTER JOIN DEPARTMENT D ON (E.DEPT_CODE = D.DEPT_ID) LEFT OUTER JOIN JOB J USING (JOB_CODE)" +
                "WHERE E.ENT_YN = 'N' " +
                "ORDER BY J.JOB_NAME ASC " +
                "LIMIT 10; ";

        try (Connection connection = JDBCUtil.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql);
             ResultSet resultSet = preparedStatement.executeQuery();
        ) {
            while (resultSet.next()) {
                EmployeeVO e = new EmployeeVO();

                e.setDeptTitle(resultSet.getString(1));
                e.setJobName(resultSet.getString(2));
                e.setEmpName(resultSet.getString(3));
                e.setSalary(resultSet.getInt(4));

                list.add(e);
            }
        }
        return list;
    }

    @Override
    public int increaseSalary(String deptCode) throws SQLException {
        String sql = "UPDATE EMPLOYEE " +
                "SET SALARY = SALARY * 1.1 " +
                "WHERE DEPT_CODE = ?; ";

        try (
                Connection connection = JDBCUtil.getConnection();
                PreparedStatement preparedStatement = connection.prepareStatement(sql)
        ) {
            connection.setAutoCommit(false);

            preparedStatement.setString(1, deptCode);
            int result = preparedStatement.executeUpdate();

            if (result == 0) {
                connection.rollback();
                return 0;
            }

            connection.commit();
            return result;
        }
    }

    @Override
    public List<EmployeeVO> getEmployeesWithoutPhone() throws SQLException {
        List<EmployeeVO> list = new ArrayList<>();

        String sql =
                "SELECT E.EMP_NAME AS \"사원명\", IFNULL(E.PHONE, '없음') AS \"휴대폰 번호\", D.DEPT_TITLE AS \"부서명\" " +
                        "FROM EMPLOYEE E INNER JOIN DEPARTMENT D ON(E.DEPT_CODE = D.DEPT_ID) " +
                        "WHERE E.PHONE IS NULL " +
                        "ORDER BY E.EMP_NAME DESC; ";

        try (Connection connection = JDBCUtil.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql);
             ResultSet resultSet = preparedStatement.executeQuery();
        ) {
            while (resultSet.next()) {
                EmployeeVO e = new EmployeeVO();

                e.setEmpName(resultSet.getString(1));
                e.setEmpNo(resultSet.getString(2));
                e.setDeptTitle(resultSet.getString(3));

                list.add(e);
            }
        }
        return list;
    }
}
