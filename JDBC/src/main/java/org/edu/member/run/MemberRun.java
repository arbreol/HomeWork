package org.edu.member.run;

import org.edu.member.common.JDBCUtil;
import org.edu.member.service.MemberService;

public class MemberRun {
    public static void main(String[] args) {

        MemberService service = new MemberService();
        service.displayMenu();
        JDBCUtil.close();
    }
}
