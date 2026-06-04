package org.edu.member.dao;

import org.edu.member.vo.Member;

import java.sql.SQLException;
import java.util.List;

public interface MemberDao {

    int create(Member m) throws SQLException;

    int update(Member m) throws SQLException;

    Member get(int no) throws SQLException;

    int delete(int no) throws SQLException;

    List<Member> getList() throws SQLException;
}

