package cn.itcast.shop.user.adminaction;

import org.apache.struts2.ServletActionContext;

import cn.itcast.shop.adminuser.domain.AdminUser;
import cn.itcast.shop.user.domain.User;
import cn.itcast.shop.user.service.UserService;
import cn.itcast.shop.utils.PageBean;

import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.ModelDriven;
/**
 * �û�����ģ��
 * @author Administrator
 *
 */
public class UserAdminAction extends ActionSupport implements ModelDriven<User>{
	private User user = new User();
	public User getModel() {
		// TODO Auto-generated method stub
		return user;
	}

	private UserService userService;
	public void setUserService(UserService userService) {
		this.userService = userService;
	}
	private Integer page;
	
	public void setPage(Integer page) {
		this.page = page;
	}
	//�������е��û�
	public String findAll(){
		if(page == null || page.equals("")) {
			page = 1;
		}
		PageBean<User> pageBean = userService.findByPage(page);
		ActionContext.getContext().getValueStack().set("pageBean", pageBean);
		return "findAll";
	}

	//�޸�ǰ
	public String edit(){
		AdminUser existAdminUser = (AdminUser) ServletActionContext.getRequest().getSession().getAttribute("existAdminUser");
		if(existAdminUser!=null){
			user = userService.findByUid(user.getUid());
			return "editSuccess";
		}else{
			this.addActionMessage("�ο��޷��޸�����!���¼���޸ģ�");
			return "Success";
		}
	}
	//����
	public String update(){
		userService.update(user);
		return "Success";
		
	}
	//ɾ��
	public String delete(){
		AdminUser existAdminUser = (AdminUser) ServletActionContext.getRequest().getSession().getAttribute("existAdminUser");
		if(existAdminUser!=null){
		User existUser = userService.findByUid(user.getUid());
		userService.delete(existUser);
		return "Success";
		}else{
			this.addActionMessage("�ο��޷��޸�����!���¼���޸ģ�");
			return "Success";
		}
		
	}
	//�����û�
	public String defriend() {
		AdminUser existAdminUser = (AdminUser) ServletActionContext.getRequest().getSession().getAttribute("existAdminUser");
		if(existAdminUser!=null){
		User existUser = userService.findByUid(user.getUid());
		existUser.setState(2);
		userService.update(existUser);
		return "Success";
		}else{
			this.addActionMessage("�ο��޷��޸�����!���¼���޸ģ�");
			return "Success";
		}
	}
	//��ת�����������û�
	public String todefriend() {
		if(page == null || page.equals("")) {
			page = 1;
		}
		PageBean<User> pageBean = userService.finddefriend(page, 2);
		ActionContext.getContext().getValueStack().set("pageBean", pageBean);
		
		return "userdefriend";
	}
	//�ָ��û�
	public String redefriend() {
		AdminUser existAdminUser = (AdminUser) ServletActionContext.getRequest().getSession().getAttribute("existAdminUser");
		if(existAdminUser!=null){
		User existUser = userService.findByUid(user.getUid());
		existUser.setState(1);
		userService.update(existUser);
		return "Success";
		}else{
			this.addActionMessage("�ο��޷��޸�����!���¼���޸ģ�");
			return "Success";
		}
	}
	

	
}
