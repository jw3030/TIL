package my.jw.web.service;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import my.jw.web.dao.MemberDAO;
import my.jw.web.dao.OrderDAO;
import my.jw.web.vo.MemberVO;
import my.jw.web.vo.OrderVO;

@Service
public class OrderService {
	@Autowired
	OrderDAO orderDAO;
	
	public long ordersInsert(ArrayList<OrderVO> list) throws Exception{
		return orderDAO.ordersInsert(list);
	}
}


