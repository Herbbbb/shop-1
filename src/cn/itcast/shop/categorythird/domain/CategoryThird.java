package cn.itcast.shop.categorythird.domain;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

import cn.itcast.shop.categorysecond.domian.CategorySecond;
import cn.itcast.shop.product.domain.Product;
/**
 * �����˵�
 * @author Administrator
 *
 */
public class CategoryThird implements Serializable {
	private static final long serialVersionUID = 3849318295586083969L;
	private Integer ctid;
	private String ctname;
	
	//��������˵���Ӧһ�������˵�
	private CategorySecond categorySecond;
	//һ�������˵����ж����Ʒ
	private Set<Product> products = new HashSet<Product>();
	
	public Set<Product> getProducts() {
		return products;
	}

	public void setProducts(Set<Product> products) {
		this.products = products;
	}

	public Integer getCtid() {
		return ctid;
	}

	public void setCtid(Integer ctid) {
		this.ctid = ctid;
	}

	public String getCtname() {
		return ctname;
	}

	public void setCtname(String ctname) {
		this.ctname = ctname;
	}

	public CategorySecond getCategorySecond() {
		return categorySecond;
	}

	public void setCategorySecond(CategorySecond categorySecond) {
		this.categorySecond = categorySecond;
	}
}
