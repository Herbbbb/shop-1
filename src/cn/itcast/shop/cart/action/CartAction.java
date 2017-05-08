package cn.itcast.shop.cart.action;

import org.apache.struts2.ServletActionContext;

import cn.itcast.shop.cart.domain.Cart;
import cn.itcast.shop.cart.domain.CartItem;
import cn.itcast.shop.product.domain.Product;
import cn.itcast.shop.product.service.ProductService;
import cn.itcast.shop.utils.UUIDUtils;

import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;

public class CartAction extends ActionSupport {
	
	private ProductService productService;
	private Integer pid;
	private Integer count;


	public Integer getPid() {
		return pid;
	}

	public void setPid(Integer pid) {
		this.pid = pid;
	}

	public Integer getCount() {
		return count;
	}

	public void setCount(Integer count) {
		this.count = count;
	}

	public void setProductService(ProductService productService) {
		this.productService = productService;
	}

	//��ӹ��ﳵ
	public String addCart(){
		CartItem cartItem = new CartItem();
		cartItem.setCount(count);
		Product product = productService.findByPid(pid);
		cartItem.setProduct(product);
		Integer subtotal = (int) (cartItem.getCount() * product.getShop_price());
		cartItem.setSubtotal(subtotal);
		Cart cart = getCart();
		cart.addCart(cartItem);
		return "addcart";
	}
	//��ӹ��ﳵ
	public String indexAddCart(){
		CartItem cartItem = new CartItem();
		cartItem.setCount(count);
		Product product = productService.findByPid(pid);
		cartItem.setProduct(product);
		Integer subtotal = (int) (cartItem.getCount() * product.getShop_price());
		cartItem.setSubtotal(subtotal);
		Cart cart = getCart();
		cart.addCart(cartItem);
		return "indexAddCart";
	}
	
	
	private Cart getCart(){
		Cart cart =(Cart) ServletActionContext.getRequest().getSession().getAttribute("cart");
		if(cart == null){
			cart = new Cart();
			ServletActionContext.getRequest().getSession().setAttribute("cart", cart);
			
		}
		return cart;
		
	}
	
	//�ҵĹ��ﳵ
	public String myCart(){
		return "mycart";
	}
	
	//ɾ�����ﳵ��
	public String deleteCartItem(){
		Cart cart = this.getCart();
		cart.removeCart(pid);
		
		return "delete";
	}
	//��չ��ﳵ
	public String clearCart(){
		Cart cart = this.getCart();
		cart.clearCart();
		return "clearcart";
	}
}
