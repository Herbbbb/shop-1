package cn.itcast.shop.order.adminOrderAction;

import java.util.List;

import cn.itcast.shop.categorysecond.domian.CategorySecond;
import cn.itcast.shop.order.domain.Order;
import cn.itcast.shop.order.domain.OrderItem;
import cn.itcast.shop.order.service.OrderService;
import cn.itcast.shop.utils.PageBean;

import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.ModelDriven;

public class AdminOrderAction extends ActionSupport implements
		ModelDriven<Order> {
	Order order = new Order();
	public Order getModel() {
		// TODO Auto-generated method stub
		return order;
	}
	private OrderService orderService;
	public void setOrderService(OrderService orderService) {
		this.orderService = orderService;
	}
	private Integer page;
	public void setPage(Integer page) {
		this.page = page;
	}
	//��ѯ���ж���
	public String findAll(){
		if(page == null){
			page = 1;
		}
		PageBean<Order> pageBean = orderService.findByPage(page);
		ActionContext.getContext().getValueStack().set("pageBean", pageBean);
		return "findAll";
	}
	//ɾ������
/*	public String cancel(){
		List<OrderItem> orderItems = orderService.findOrderItems(order.getOid());
		if(orderItems!=null && orderItems.size()>0){
			for( OrderItem orderItem : orderItems){
				orderService.delete(orderItem);
			}
			Order order2 = orderService.findByOid(order.getOid());
			orderService.delete(order2);
		}
		return "delete";
	}*/
	
	//����
	public String send(){
		Order order2 = orderService.findByOid(order.getOid());
		order2.setState(3);
		orderService.update(order2);
		return "send";
	}
	
	//��ѯ����״̬�Ķ���
	public String findByState(){
		if(order.getState()==1){
			if(page == null){
				page = 1;
			}
			PageBean<Order> pageBean = orderService.findByState(page,order.getState());
			ActionContext.getContext().getValueStack().set("pageBean", pageBean);
			return "list1";
		}else if(order.getState()==2){
			if(page == null){
				page = 1;
			}
			PageBean<Order> pageBean = orderService.findByState(page,order.getState());
			ActionContext.getContext().getValueStack().set("pageBean", pageBean);
			return "list2";
		}else if(order.getState()==3){
			if(page == null){
				page = 1;
			}
			PageBean<Order> pageBean = orderService.findByState(page,order.getState());
			ActionContext.getContext().getValueStack().set("pageBean", pageBean);
			return "list3";
		}else if(order.getState()==4){
			if(page == null){
				page = 1;
			}
			PageBean<Order> pageBean = orderService.findByState(page,order.getState());
			ActionContext.getContext().getValueStack().set("pageBean", pageBean);
			return "list4";
		}
		return "send";
	}
	
	//�¶����۱�
	public String findmonth() {
		
		return "month";
	}
	
	//���ȱ���
	public String findquater() {
		
		return "quater";
	}
	
	//��ȱ���
	public String findyear() {
		
		return "year";
	}
}
