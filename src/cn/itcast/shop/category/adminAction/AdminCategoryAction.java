package cn.itcast.shop.category.adminAction;

import java.util.List;

import org.apache.struts2.ServletActionContext;

import cn.itcast.shop.adminuser.domain.AdminUser;
import cn.itcast.shop.category.domain.Category;
import cn.itcast.shop.category.service.CategoryService;
import cn.itcast.shop.categorysecond.domian.CategorySecond;
import cn.itcast.shop.categorysecond.service.CategorySecondService;
import cn.itcast.shop.utils.PageBean;

import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.ModelDriven;

public class AdminCategoryAction extends ActionSupport implements ModelDriven<Category> {
	private  CategoryService categoryService;

	public void setCategoryService(CategoryService categoryService) {
		this.categoryService = categoryService;
	}

	private Integer page;
	
	public void setPage(Integer page) {
		this.page = page;
	}
	Category category = new Category();
	public Category getModel() {
		return category;
	}
	//��ѯ����һ������
	public String findAll(){
		if(page == null){
			page = 1;
		}
		PageBean<Category> pageBean = categoryService.findByPage(page);
		ActionContext.getContext().getValueStack().set("pageBean", pageBean);
		return "findAll";
	}
	
	//��תһ������༭
	public String edit(){
		AdminUser existAdminUser = (AdminUser) ServletActionContext.getRequest().getSession().getAttribute("existAdminUser");
		if(existAdminUser!=null){
			ServletActionContext.getResponse().setCharacterEncoding("utf-8");
			Category categoryedit = categoryService.findByCid(category.getCid());
			if(categoryedit!=null){
				ActionContext.getContext().getValueStack().set("categoryedit", categoryedit);
			}
			return "edit";
		}else{
			this.addActionMessage("Ȩ�޲��㣡");
			return "edited";
		}
	}
	
	//�޸�һ������
	public String update(){
		Category updateCategory = categoryService.findByCid(category.getCid());
		updateCategory.setCname(category.getCname());
		categoryService.update(updateCategory);
		return "edited";
	}
	//ɾ��һ������
	public String delete() {
		AdminUser existAdminUser = (AdminUser) ServletActionContext.getRequest().getSession().getAttribute("existAdminUser");
		if(existAdminUser != null && existAdminUser.getPrivilege().equals(1)) {
			
			
		}
		return "delete";
	}
	
	//��ת���һ���˵�
	public String toadd() {
		
		return "toadd";
	}
	
	//���һ���˵�
	public String add() {
		AdminUser existAdminUser = (AdminUser) ServletActionContext.getRequest().getSession().getAttribute("existAdminUser");
		if(existAdminUser != null ) {
			categoryService.saveCategory(category);
		}else {
			this.addActionMessage("�ο��޷��޸�����!���¼���޸ģ�");
		}
		return "add";
	}
}
