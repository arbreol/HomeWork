package practice.model.service;

import practice.model.vo.Member;

import java.util.*;

public class LoginService {

    private Scanner sc = new Scanner(System.in);

    // 업다운 게임 시작
    // 1 ~ 100 사이 숫자 중 랜덤하게 한 숫자를 지정하고 업/다운 게임을 진행
    // 맞춘 횟수가 현재 로그인한 회원의 최초 또는 최고 기록인 경우 회원의 highScore 필드 값을 변경
    public void startGame(Member loginMember) {
        System.out.println("[Game Start...]");

        Random random = new Random();
        int correct = random.nextInt(100) + 1;

        Set<Integer> choiceSet = new HashSet<>();
        boolean attemp = true;

        while (attemp) {
            System.out.println("[Choice Number 1 ~ 100]");
            String choiceString = sc.nextLine();

            int choice = 0;
            try {
                choice = Integer.parseInt(choiceString);
            } catch (NumberFormatException e) {
                System.out.println("Input the Number 1 ~ 100");
                continue;
            }

            choiceSet.add(choice);

            if (correct == choice) {
                attemp = false;
                System.out.println("[Correct]");
                if (loginMember.getHighScore() == 0 || loginMember.getHighScore() > choiceSet.size()) {
                    loginMember.setHighScore(choiceSet.size());

                }
            } else if (correct < choice) {
                System.out.println("[DOWN]");
            } else if (correct > choice) {
                System.out.println("[UP]");
            }
        }
    }

    // 내 정보 조회
    // 로그인한 멤버의 정보 중 비밀번호를 제외한 나머지 정보만 화면에 출력
    public void selectMyInfo(Member loginMember) {
        System.out.println("[내 정보 조회]");
        System.out.println(loginMember.getMemberId());
        System.out.println(loginMember.getMemberName());
        System.out.println(loginMember.getHighScore());
    }

    // 전체 회원 조회
    // 전체 회원의 아이디, 이름, 최고점수를 출럭
    public void selectAllMember(List<Member> members) {
        System.out.println("[전체 회원 조회]");
        Iterator<Member> iterator = members.iterator();
        while (iterator.hasNext()) {
            Member member = iterator.next();
            System.out.println(member.getMemberId());
            System.out.println(member.getMemberName());
            System.out.println(member.getHighScore());
        }
        System.out.println();
    }

    // 비밀번호 변경
    // 현재 비밀번호를 입력 받아
    // 같은 경우에만 새 비밀번호를 입력 받아 비밀번호 변경
    public void updatePassword(Member loginMember) {
        System.out.println("[비밀번호 변경]");
        String password = sc.nextLine();
        loginMember.setMemberPw(password);
        System.out.println("[Password Updated]");
    }


}
