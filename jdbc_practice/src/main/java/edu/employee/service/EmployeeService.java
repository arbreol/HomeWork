package edu.employee.service;

import edu.employee.dao.EmployeeDao;
import edu.employee.dao.EmployeeDaoImpl;
import edu.employee.vo.EmployeeVO;

import java.sql.SQLException;
import java.util.List;
import java.util.Scanner;

public class EmployeeService {

    private Scanner sc = new Scanner(System.in);

    private EmployeeDao dao = new EmployeeDaoImpl();

    public void displayMenu() {

        int menu = 0; // 메뉴 선택용 변수

        do {
            try {
                System.out.println("[직원 관리 시스템]");
                System.out.println("1. 마케팅부 직원 정보 조회");
                System.out.println("2. 부서·직급별 평균 급여 조회");
                System.out.println("3. 재직 중인 직원 목록 조회");
                System.out.println("4. 부서 급여 10% 인상");
                System.out.println("5. 휴대폰 번호 없는 직원 조회");
                System.out.println("0. 종료");
                System.out.print("메뉴 선택 >> ");

                menu = sc.nextInt();
                sc.nextLine(); // 입력 버퍼 개행문자 제거
                System.out.println(); // 줄바꿈

                switch (menu) {
                    case 1:
                        getDepartmentEmployees();
                        break;

                    case 2:
                        getDepartmentAvgSalary();
                        break;

                    case 3:
                        getWorkingEmployees();
                        break;

                    case 4:
                        increaseSalary();
                        break;

                    case 5:
                        getEmployeesWithoutPhone();
                        break;

                    case 0:
                        System.out.println("[프로그램 종료]");
                        break;

                    default:
                        System.out.println("잘못 입력하셨습니다. 메뉴를 다시 선택해주세요.");
                }

            } catch (Exception e) {
                sc.nextLine(); // 잘못된 입력 제거
                e.printStackTrace();
            }
        } while (menu != 0);
    }

    private void getDepartmentEmployees() {
        System.out.print("부서명 입력 : ");
        String deptTitle = sc.nextLine();

        try {
            List<EmployeeVO> list = dao.getDepartmentEmployees(deptTitle);

            if (list.isEmpty()) {
                System.out.println("조회 결과 없음");
                return;
            }

            for (EmployeeVO e : list) {
                System.out.println(e);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return;
    }

    private void getDepartmentAvgSalary() {
        try {
            List<EmployeeVO> list = dao.getDepartmentAvgSalary();

            if (list.isEmpty()) {
                System.out.println("조회 결과 없음");
                return;
            }

            for (EmployeeVO e : list) {
                System.out.println(e);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return;
    }

    private void getWorkingEmployees() {
        try {
            List<EmployeeVO> list = dao.getWorkingEmployees();

            if (list.isEmpty()) {
                System.out.println("조회 결과 없음");
                return;
            }

            for (EmployeeVO e : list) {
                System.out.println(e);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return;
    }

    private void increaseSalary() {
        System.out.print("부서코드 입력 : ");
        String deptCode = sc.nextLine();
        try {
            int result = dao.increaseSalary(deptCode);

            if (result > 0) {
                System.out.println(result + "명의 급여가 10% 인상되었습니다.");
            } else {
                System.out.println("해당하는 직원이 없습니다.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void getEmployeesWithoutPhone() {
        try {
            List<EmployeeVO> list = dao.getEmployeesWithoutPhone();

            if (list.isEmpty()) {
                System.out.println("조회 결과 없음");
                return;
            }

            for (EmployeeVO e : list) {
                System.out.println(e);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return;
    }
}
