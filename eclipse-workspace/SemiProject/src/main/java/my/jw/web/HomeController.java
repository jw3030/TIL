package my.jw.web;

import java.io.BufferedReader;
import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import my.jw.web.service.MemberService;
import my.jw.web.service.OrderService;
import my.jw.web.vo.MemberVO;
import my.jw.web.vo.OrderVO;

@Controller
public class HomeController {
	@Autowired
	MemberService memberService;
	
	@RequestMapping(value = "login.jw", 
			method= {RequestMethod.POST},
			produces = "application/text; charset=utf8")			
	@ResponseBody
	public String login(@ModelAttribute("info") MemberVO m, HttpServletRequest request,
			HttpServletResponse response){
			
		JSONObject json=new JSONObject();

		try {
			
			String name=memberService.login(m);
			System.out.println(name);
			
			if(name!=null) {
				
				
				System.out.println(name);
				HttpSession session=request.getSession();
				session.setAttribute("member", m);
				json.put("name", name+"님 접속중");

			}else {
				json.put("msg", "로그인 실패");

			}
		}catch(Exception e) {
			e.printStackTrace();
			json.put("msg", e.getMessage());
		}
		return json.toJSONString();

	}
	
	@RequestMapping(value = "logout.jw", 
			method= {RequestMethod.POST},
			produces = "application/text; charset=utf8")			
	@ResponseBody
	public String logout(HttpServletRequest request,
			HttpServletResponse response){
		
			HttpSession session=request.getSession(false);
			session.invalidate();
			return "";
		
	}


	@RequestMapping(value = "memberInsert.jw",
			method= {RequestMethod.POST},
			produces = "application/text; charset=utf8")
	
	@ResponseBody
	public String memberInsert(@RequestParam("id") String id, 
			@RequestParam("pw")String pw,@RequestParam("name")String name,
			HttpServletRequest request,
			HttpServletResponse response) {
		
			
			
			try {
				MemberVO m=new MemberVO(id,pw,name); 
				memberService.memberInsert(m);
				return name+"님 회원가입 되셨습니다";
			}catch(Exception e) {
				e.printStackTrace();
				return e.getMessage();
		}		
	}
    @Autowired OrderService orderService;
	
	
	///////////// 주문 처리 //////////////////
	@RequestMapping(value = "order.jw", 
			method= {RequestMethod.POST},
			produces = "application/text; charset=utf8")			
	@ResponseBody
	public String order(HttpServletRequest request,
			HttpServletResponse response){
		JSONObject json=null;
		try {
			BufferedReader br=request.getReader();
			JSONParser parser=new JSONParser();
			json=(JSONObject) parser.parse(br);
			JSONArray array=(JSONArray) json.get("product");
			
			Object []array2=array.toArray();
			
			ArrayList<OrderVO> list=new ArrayList<OrderVO>();
			for(Object o : array2) {
				
				JSONObject j=(JSONObject) o;
				String prodname=(String) j.get("name");
				long quantity=(Long) j.get("quantity");
				OrderVO orderVO=new OrderVO("web",prodname,quantity);
				HttpSession session=request.getSession();
				MemberVO memberVO=(MemberVO) session.getAttribute("member");
				if(memberVO!=null) {//로그인 된 사용자라면 id를 추가해준다
					orderVO.setMemberid(memberVO.getId());
				}else {
					orderVO.setMemberid(" ");
				}
				
				list.add(orderVO);
			}
			
			System.out.println("총 "+list.size()+"개 품목을 주문합니다");
			long order_group_no=orderService.ordersInsert(list);
			
			json=new JSONObject();			
			
			if(true) {		
				
				json.put("order_group_no", order_group_no);
				
			}else {
				json.put("msg", "주문 실패");
			}
		}catch(Exception e) {
			e.printStackTrace();
			json.put("msg", e.getMessage());
		}	
		return json.toJSONString();
		
	}

}
