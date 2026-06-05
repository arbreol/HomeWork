package org.edu.member.dao;

import org.edu.member.common.JDBCUtil;
import org.edu.member.vo.Member;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class KtsMemberDaoImpl implements MemberDao{
    Connection con = JDBCUtil.getConnection();

    @Override
    public int create(Member m) throws SQLException {

        String sql = "insert into members values(null, ?, ?, ?, ?, default)";
        try (PreparedStatement pstm = con.prepareStatement(sql)){
            pstm.setString(1, m.getId());
            pstm.setString(2, m.getPw());
            pstm.setString(3, m.getName());
            pstm.setString(4, m.getRole());

            int result = pstm.executeUpdate();
            if (result > 0) {
                con.commit();
            }
            return result;
        }
    }

    @Override
    public int update(Member m) throws SQLException {
        String sql = "update members set name = ?, role = ? where no = ?";
        try (PreparedStatement pstm = con.prepareStatement(sql)){
            pstm.setString(1, m.getName());
            pstm.setString(2, m.getRole());
            pstm.setInt(3, m.getNo());
            int result = pstm.executeUpdate();
            if (result > 0) {
                con.commit();
            } else {
                con.rollback();
            }
            return result;
        }
    }

    @Override
    public Member get(int no) throws SQLException {
        String sql = "select * from members where no = ?";
        try (PreparedStatement pstm = con.prepareStatement(sql)){
            pstm.setInt(1, no);
            ResultSet rs = pstm.executeQuery();
            if (!rs.next()) {
                return null;
            } else {
                Member m = new Member();
                m.setNo(rs.getInt("no"));
                m.setId(rs.getString("id"));
                m.setPw(rs.getString("password"));
                m.setName(rs.getString("name"));
                m.setRole(rs.getString("role"));
                m.setDeletedYn(rs.getString("deleted_yn").charAt(0));
                return m;
            }
        }
    }

    @Override
    public int delete(int no) throws SQLException {
        String sql = "delete from members where no = ?";
        try (PreparedStatement pstm = con.prepareStatement(sql)){
            pstm.setInt(1, no);
            int result = pstm.executeUpdate();
            if (result > 0) {
                con.commit();
            }
            return result;
        }
    }

    @Override
    public List<Member> getList() throws SQLException {
        List<Member> list = new ArrayList<>();
        String sql = "select * from members";
        try (PreparedStatement pstm = con.prepareStatement(sql)){
            ResultSet rs = pstm.executeQuery();

            while (rs.next()) {
                Member m = new Member();
                m.setNo(rs.getInt(1));
                m.setId(rs.getString(2));
                m.setPw(rs.getString(3));
                m.setName(rs.getString(4));
                m.setRole(rs.getString(5));
                m.setDeletedYn(rs.getString(6).charAt(0));
                list.add(m);
            }
            return list;
        }
    }
}
