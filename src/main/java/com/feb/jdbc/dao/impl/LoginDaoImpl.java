package com.feb.jdbc.dao.impl;

import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.support.JdbcDaoSupport;

import com.feb.jdbc.dao.LoginDao;
import com.feb.jdbc.entity.Member;
import com.feb.jdbc.entity.MemberRowMapper;

public class LoginDaoImpl extends JdbcDaoSupport implements LoginDao {

	@Override
	public Member login(String memberId) {
		String sql = "select * from lecture.member where member_id = ?";
		Member member = null;
		// 여기서 없는 회원 아이디로 쿼리시 Exception 발생
		try {
			member = getJdbcTemplate().queryForObject(sql, new Object[] {memberId}, new MemberRowMapper());
		} catch(EmptyResultDataAccessException e) {
			throw new EmptyResultDataAccessException(e.getMessage(), 1, e);
		}
		
		return member;
	}
	
	public Member login2(String memberId) throws EmptyResultDataAccessException {
		String sql = "select * from lecture.member where member_id = ?";
		Member member =  getJdbcTemplate().queryForObject(sql, new Object[] {memberId}, new MemberRowMapper());
		return member;
	}
	
}
