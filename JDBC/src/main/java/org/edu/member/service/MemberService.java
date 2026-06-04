package org.edu.member.service;

import org.edu.member.dao.MemberDao;
import org.edu.member.dao.KtsMemberDaoImpl;
import org.edu.member.vo.Member;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class MemberService {
    private Scanner sc = new Scanner(System.in);

    private MemberDao dao = new KtsMemberDaoImpl();

    public void displayMenu() {

        int menu = 0;

        do {
            try {
                System.out.println("[메인 메뉴]");
                System.out.println("1. 회원 등록");
                System.out.println("2. 회원 목록 조회");
                System.out.println("3. 회원 정보 조회");
                System.out.println("4. 회원 수정");
                System.out.println("5. 회원 삭제");
                System.out.println("0. 종료");
                System.out.print("메뉴 선택 >> ");

                menu = sc.nextInt();
                sc.nextLine();
                System.out.println();

                switch (menu) {
                    case 1:
                        create();
                        break;
                    case 2:
                        getList();
                        break;
                    case 3:
                        get();
                        break;
                    case 4:
                        update();
                        break;
                    case 5:
                        delete();
                        break;

                    case 0:
                        System.out.println("[프로그램 종료]");
                        break;
                    default:
                        System.out.println("잘못 입력하셨습니다. 메뉴를 다시 선택해주세요.");
                }

            } catch (SQLException e) {
                System.out.println("DB 작업중 에러 발생");
                e.printStackTrace();

            } catch (Exception e) {
                sc.nextLine();
                e.printStackTrace();
            }
        } while (menu != 0);
    }

    private void create() throws SQLException {
        System.out.println("=== 회원 등록 ===");

        String id, pw, name, role;
        System.out.print("아이디 : ");
        id = sc.nextLine();

        System.out.print("비밀번호 : ");
        pw = sc.nextLine();

        System.out.print("이름 : ");
        name = sc.nextLine();

        System.out.print("권한 : ");
        role = sc.nextLine();

        Member m = new Member();
        m.setId(id);
        m.setPw(pw);
        m.setName(name);
        m.setRole(role);

        int result = dao.create(m);

        if(result > 0) {
            System.out.println(name + "님의 가입을 환영합니다.");
        } else {
            System.out.println("회원 등록 실패");
        }
    }

    private void update() throws SQLException {
        System.out.println("=== 회원 정보 수정 ===");

        System.out.print("회원 번호 입력 : ");
        int no = sc.nextInt();
        sc.nextLine();

        Member member = dao.get(no);
        if (member == null) {
            System.out.println("회원이 없습니다.");
            return;
        }

        System.out.print("수정할 이름 : ");
        String name = sc.nextLine();

        System.out.print("수정할 권한 : ");
        String role = sc.nextLine();

        Member m = new Member();
        m.setNo(no);
        m.setName(name);
        m.setRole(role);

        int result = dao.update(m);
        if (result > 0) {
            System.out.println("회원 정보 수정 성공");
        } else {
            System.out.println("회원 정보 수정 실패");
        }
    }

    private void delete() throws SQLException {
        System.out.print("삭제할 번호 입력 : ");
        int no = sc.nextInt();
        sc.nextLine();

        int result = dao.delete(no);
        if (result > 0) {
            System.out.println("회원 삭제 성공");
        } else {
            System.out.println("회원 삭제 실패");
        }
    }

    private void get() throws SQLException {
        System.out.print("조회할 번호 입력 : ");
        int no = sc.nextInt();
        sc.nextLine();

        Member m = dao.get(no);
        if (m != null) {
            System.out.println(m);
        } else {
            System.out.println("조회한 번호의 회원이 없습니다.");
        }
    }

    private void getList() throws SQLException {
        List<Member> list = dao.getList();
        if (list == null) {
            System.out.println("목록 조회 실패");
        } else {
            for (Member m : list) {
                System.out.println(m);
            }
        }
    }
}
