package cn.itcast.shop.user.domain;

import java.io.Serializable;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import cn.itcast.shop.order.domain.Order;
/**
 * �û���
 * @author Administrator
 *
 */
public class User implements Serializable {
	private static final long serialVersionUID = -4279398672409985483L;
	private Integer uid;
	//�ǳ�
	private String nickname;
	private String email;
	private String password;
	private String name;
	private String gender;
	private Date birthday;
	//��ס��
	private String residence;
	//ͷ��
	private String portrait;
	private Integer state;
	private String code;

	// һ���û���Ӧ�������:
	private Set<Order> orders = new HashSet<Order>();
	//һ���û������ж���ջ���ַ
	private Set<Address> address = new HashSet<Address>();
	
	//һ���û������ж���ղ�
	private Set<Favorites> favorites = new HashSet<Favorites>();
	
	
	public Set<Favorites> getFavorites() {
		return favorites;
	}
	public void setFavorites(Set<Favorites> favorites) {
		this.favorites = favorites;
	}
	public Set<Address> getAddress() {
		return address;
	}
	public void setAddress(Set<Address> address) {
		this.address = address;
	}
	public Set<Order> getOrders() {
		return orders;
	}
	public void setOrders(Set<Order> orders) {
		this.orders = orders;
	}
	public Integer getUid() {
		return uid;
	}
	public void setUid(Integer uid) {
		this.uid = uid;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getNickname() {
		return nickname;
	}
	public void setNickname(String nickname) {
		this.nickname = nickname;
	}
	public String getGender() {
		return gender;
	}
	public void setGender(String gender) {
		this.gender = gender;
	}
	public Date getBirthday() {
		return birthday;
	}
	public void setBirthday(Date birthday) {
		this.birthday = birthday;
	}
	public String getResidence() {
		return residence;
	}
	public void setResidence(String residence) {
		this.residence = residence;
	}
	public String getPortrait() {
		return portrait;
	}
	public void setPortrait(String portrait) {
		this.portrait = portrait;
	}
	public Integer getState() {
		return state;
	}
	public void setState(Integer state) {
		this.state = state;
	}
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
}
