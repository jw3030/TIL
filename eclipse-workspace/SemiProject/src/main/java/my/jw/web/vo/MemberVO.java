package my.jw.web.vo;

import java.util.Date;

import my.jw.web.util.MyException;



public class MemberVO {

	private String id,pw,name;
	private Date date;
	

	public MemberVO(String id, String pw, String name)throws MyException {

		this(id,pw);
		setName(name);
	}
	

	public MemberVO(String id, String pw)throws MyException {
		super();
		setId(id);
		setPw(pw);
	}


	public MemberVO() {
		super();
		// TODO Auto-generated constructor stub
	}


	public String getId() {
		return id;
	}

	public void setId(String id)throws MyException {
		if(id!=null) {
		this.id = id;
		}else {
			throw new MyException("id�� �Էµ��� �ʾҽ��ϴ�");
		}
	}

	public String getPw() {
		return pw;
	}

	public void setPw(String pw)throws MyException {
		if(pw!=null) {
			this.pw = pw;
		}else {
			throw new MyException("pw�� �Էµ��� �ʾҽ��ϴ�");
		}
		
	}

	public String getName() {
		return name;
	}

	public void setName(String name) throws MyException {
		if(name!=null) {
			this.name = name;
		}else {
			throw new MyException("name�� �Էµ��� �ʾҽ��ϴ�");
		}
		
	}
	
	

	public MemberVO(Date date) {
		super();
		this.date = date;
	}


	@Override
	public String toString() {
		return "MemberVO [id=" + id + ", pw=" + pw + ", name=" + name + "]";
	}
	
	
}
