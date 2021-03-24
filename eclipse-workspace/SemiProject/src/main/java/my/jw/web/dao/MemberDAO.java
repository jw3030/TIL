package my.jw.web.dao;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import my.jw.web.vo.MemberVO;

@Repository
public class MemberDAO {
	@Autowired
	SqlSession sqlSession;

	public void memberInsert(MemberVO m) {
		sqlSession.insert("mapper.member.memberInsert",m);
	}

	public String login(MemberVO m) {
		// TODO Auto-generated method stub
		System.out.println("==="+m.getPw()+"===");
		String name=sqlSession.selectOne("mapper.member.login",m);
		System.out.println(name);
		return name;
	}
	
}

	
	

