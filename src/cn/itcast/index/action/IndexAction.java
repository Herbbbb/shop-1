package cn.itcast.index.action;
import java.util.ArrayList;
import java.util.List;

import org.apache.struts2.ServletActionContext;

import cn.itcast.shop.category.domain.Category;
import cn.itcast.shop.product.domain.Product;
import cn.itcast.shop.product.service.ProductService;
import cn.itcast.shop.utils.SaveSessionTool;

import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;
public class IndexAction  extends ActionSupport{
	private static final long serialVersionUID = 1L;
	private SaveSessionTool saveSessionTool;
	public void setSaveSessionTool(SaveSessionTool saveSessionTool) {
		this.saveSessionTool = saveSessionTool;
	}

	private ProductService productService;
	

	public void setProductService(ProductService productService) {
		this.productService = productService;
	}
	
	public String index(){
		//����˵���
		saveSessionTool.saveSession();
		//��ȡ����ͼƬ
		List<Product> slist =  productService.findByHot(3);
		ServletActionContext.getRequest().setAttribute("slist", slist);
		//��ȡ�̱�
		List<Product> logoList = productService.findByHot(4); 
		ActionContext.getContext().getValueStack().set("logoList", logoList);
		//��ȡ������Ʒ
		List<Product> hList1 = productService.findHot(0);
		ActionContext.getContext().getValueStack().set("hList1", hList1);
		List<Product> hList2 = productService.findHot(16);
		ActionContext.getContext().getValueStack().set("hList2", hList2);
		//��ȡ��������
		List<Product> nList = productService.findByCtid(3);
		ActionContext.getContext().getValueStack().set("nList", nList);
		
		//����Ů��
		List<Product> List1 = productService.findByCtid(111);
		List<Product> nvbaolist = new ArrayList<Product>();
		//С��
		List<Product> List2 = productService.findByCtid(583);
		List<Product> yingyinlist = new ArrayList<Product>();
		//������
		List<Product> List3 = productService.findByCtid(10);
		List<Product> shoubiaolist = new ArrayList<Product>();
		
		
		//�淶ÿ������ĳ���
		for (int j = 0; j < 9; j++) {
			nvbaolist.add(List1.get(j));
			yingyinlist.add(List2.get(j));
			shoubiaolist.add(List3.get(j));
		}
		ActionContext.getContext().getValueStack().set("nvbaolist", nvbaolist);
		ActionContext.getContext().getValueStack().set("yingyinlist", yingyinlist);
		ActionContext.getContext().getValueStack().set("shoubiaolist", shoubiaolist);
		return "index";
	}
	//����ָ��
	public String guidance() {
		return "guidance";
	}
	//�˻�
	public String returnGood() {
		return "returnGood";
	}
	//����
	public String advice() {
		return "advice";
	}
	//����
	public String aboutus() {
		return "aboutus";
	}
	//��ʱ404ҳ
	public String fourzfour() {
		
		return "fourzfour";
	}
	public String more() {
		List<Category> clist = saveSessionTool.getCategory();
		ActionContext.getContext().getValueStack().set("clist", clist);
		return "more";
	}
	
	
}
