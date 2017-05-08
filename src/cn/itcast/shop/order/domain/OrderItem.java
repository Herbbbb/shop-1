package cn.itcast.shop.order.domain;

import java.io.Serializable;

import cn.itcast.shop.product.domain.Product;

public class OrderItem implements Serializable{
	private static final long serialVersionUID = -8530150120657563450L;
	private Integer itemid;
	private Integer count;
	private Double subtotal;
	private Product product;
	private Order order;
	public Integer getItemid() {
		return itemid;
	}
	public void setItemid(Integer itemid) {
		this.itemid = itemid;
	}
	public Integer getCount() {
		return count;
	}
	public void setCount(Integer count) {
		this.count = count;
	}
	public Double getSubtotal() {
		return subtotal;
	}
	public void setSubtotal(Double subtotal) {
		this.subtotal = subtotal;
	}
	public Product getProduct() {
		return product;
	}
	public void setProduct(Product product) {
		this.product = product;
	}
	public Order getOrder() {
		return order;
	}
	public void setOrder(Order order) {
		this.order = order;
	}
	
	
	
}
