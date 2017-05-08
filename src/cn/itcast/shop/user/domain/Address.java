package cn.itcast.shop.user.domain;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

import cn.itcast.shop.order.domain.Order;
/**
 * �ջ���ַ������
 * @author Administrator
 *
 */
public class Address implements Serializable{
	private static final long serialVersionUID = 3692005159750145703L;
	private Integer aid;
	//�ջ���
	private String consignee;
	//�绰
	private String phone;
	//�ջ���ַ
	private String addr;
	//��ǰ�û�
	private User user;
	//���������ͬһ����ַ
	private Set<Order> orders = new HashSet<Order>();
	public Set<Order> getOrders() {
		return orders;
	}
	public void setOrders(Set<Order> orders) {
		this.orders = orders;
	}
	public Integer getAid() {
		return aid;
	}
	public void setAid(Integer aid) {
		this.aid = aid;
	}
	public String getConsignee() {
		return consignee;
	}
	public void setConsignee(String consignee) {
		this.consignee = consignee;
	}
	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
	public String getAddr() {
		return addr;
	}
	public void setAddr(String addr) {
		this.addr = addr;
	}
	public User getUser() {
		return user;
	}
	public void setUser(User user) {
		this.user = user;
	}
	@Override
	public String toString() {
		return "Address [aid=" + aid + ", consignee=" + consignee + ", phone="
				+ phone + ", addr=" + addr + ", user=" + user + "]";
	}
	
}
