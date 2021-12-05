package com.newsp.dao;

import java.util.List;
import java.util.HashMap;
import java.util.Map;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;

import com.newsp.vo.UsersVO;

public class UsersDaoImpl implements UsersDao {
	
	private static final String STATEMENT = "com.newsp.mapper.UsersMapper.";
	
	@Autowired
	private SqlSession sqlSession;

	@Override
	public Integer signupIdCheck(String id) {
		/* [회원가입] : 아이디 중복 체크
		 * param : user가 입력한 id
		 * return : 중복여부 int형으로 return
		 */
		int count = 0;
		if(sqlSession == null) {
			System.out.println("id check >> session is null");
		}else {
			count = sqlSession.selectOne(STATEMENT + "signUpIdChk", id);	
		}
		
		return count;
	}

	@Override
	public Integer signupNickCheck(String nickname) {
		/* [회원가입] : 닉네임 중복 체크
		 * param : user가 입력한 nickname
		 * return : 중복여부 int형으로 return
		 */
		int count = 0;
		if(sqlSession == null) {
			System.out.println("nick check >> session is null");
		}else {
			count = sqlSession.selectOne(STATEMENT + "signUpNickChk", nickname);
		}
		return count;
	}

	@Override
	public Integer signupEmailCheck(String email) {
		/* [회원가입] : 이메일 중복 체크
		 * param : user가 입력한 email
		 * return : 중복여부 int형으로 return
		 */
		int count = 0;
		if(sqlSession == null) {
			System.out.println("email check >> session is null");
		}else {
			count = sqlSession.selectOne(STATEMENT + "signUpEmailChk", email);	
		}
		return count;
	}

	@Override
	public void insertUserInfo(UsersVO vo) {
		/* [회원가입] : 회원 정보  insert
		 * param : user 정보(id, pw, nickname, email)를 담은 vo
		 * */
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("id", vo.getId());
		map.put("pw", vo.getPassword());
		map.put("nickname", vo.getNickname());
		map.put("email", vo.getEmail());
		
		sqlSession.insert(STATEMENT + "insertUserInfo", map);
		
	}

	@Override
	public void updateAuthKey(UsersVO vo) {
		/* [회원가입] : 이메일 인증 key insert
		 * param : user정보(email, authKey)를 담은 vo
		 * */
		Map<String, String> map = new HashMap<String, String>();
		map.put("email", vo.getEmail());
		map.put("authKey", vo.getAuth_key());
		sqlSession.update(STATEMENT + "updateAuthKey", map);
		
	}

	@Override
	public void updateAuthStatus(Map<String, String> map) {
		/* [회원가입] : 인증 메일에서 승인 버튼을 누르면 auth_status를 1로 update
		 * param : email과 authKey를 담은 map
		 * */
		sqlSession.update(STATEMENT + "updateAuthStatus", map);
		
	}

	@Override
	public List<UsersVO> checkLogin(Map<String, Object> map) {
		/* [로그인] : id와 pw를 비교해서 로그인 가능 여부를 check
		 * param : id와 pw를 담은 map
		 * */
		return sqlSession.selectList(STATEMENT + "checkLogin", map);
	}

	@Override
	public List<UsersVO> getUserInfo(String id) {
		List<UsersVO> list = sqlSession.selectList(STATEMENT + "getUserInfo", id);
		return list;
	}

	@Override
	public void updateNickname(Map<String, Object> map) {
		sqlSession.update(STATEMENT + "updateNickname", map);
	}

	@Override
	public void updatePassword(Map<String, Object> map) {
		sqlSession.update(STATEMENT + "updatePassword", map);
	}
}

