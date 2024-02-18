package com.feb.jdbc.service;

import java.util.HashMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;

import com.feb.jdbc.dao.LoginDao;
import com.feb.jdbc.entity.Member;
import com.feb.jdbc.util.Sha512Encoder;

@Service
public class LoginService {
	
	@Autowired
	private LoginDao loginDao;
	
	
	// 가급적 dao에 있는 메서드 선언부 일치시키기 - 단 여기서는 조금 바꿈
	public boolean login(HashMap<String, String> params) {
		String memberId = params.get("memberId");
		// spring jdbc 일 때
//		Member member = null;
//		// 잘못된 아이디 비번 입력시 예외 처리
//		try {
//			member = loginDao.login(memberId);
//		} catch (EmptyResultDataAccessException e) {
//			return false;
//		}
		Member member = loginDao.login(memberId);
		// mybatis는 exception 발생 안시키고 total 0 나오므로 try-catch문 말고 if문으로 검사해야 함
		if(ObjectUtils.isEmpty(member)) {
			return false;
		}
		String memberPw = member.getPasswd(); // DB에 있는 값
			
		Sha512Encoder encoder = Sha512Encoder.getInstance();
		String passwd = params.get("passwd"); // 사용자가 입력한 값
		// 사용자가 입력한 값을 암호화한 것
		String encodeTxt = encoder.getSecurePassword(passwd);
			
		System.out.println(member);
		// org.springframework.util.StringUtils
		return StringUtils.pathEquals(memberPw, encodeTxt);

	}
}
