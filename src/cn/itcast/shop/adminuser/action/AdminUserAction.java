package cn.itcast.shop.adminuser.action;

import org.apache.commons.codec.digest.DigestUtils;
import org.apache.struts2.ServletActionContext;

import cn.itcast.shop.adminuser.domain.AdminUser;
import cn.itcast.shop.adminuser.service.AdminUserService;
import cn.itcast.shop.utils.PageBean;

import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.ModelDriven;
/**
 * ��̨����Ա��½ģ��
 * @author Administrator
 *
 */
public class AdminUserAction extends ActionSupport implements ModelDriven<AdminUser> {
	private AdminUser adminUser = new AdminUser();
	public AdminUser getModel() {
		// TODO Auto-generated method stub
		return adminUser;
	}
	private String root;

	public void setRoot(String root) {
		this.root = root;
	}
	private Integer page;
	
	public void setPage(Integer page) {
		this.page = page;
	}
	private AdminUserService adminUserService;
	public void setAdminUserService(AdminUserService adminUserService) {
		this.adminUserService = adminUserService;
	}
	//��ҳ
	public String index(){
		return "index";
	}
	//��½ҳ
	public String loginPage() {
		return "loginPage";
	}
	//��½
	public String login(){
		AdminUser existAdminUser = adminUserService.login(adminUser);
		
		if(existAdminUser == null){
			this.addActionMessage("�û������ڣ�");
			return "loginFail";
		}else{
			ServletActionContext.getRequest().getSession().setAttribute("existAdminUser", existAdminUser);
			return "loginSuccess";
		}
	}
	

	//�˳���½
	public String quit(){
		ServletActionContext.getRequest().getSession().invalidate();
		return "quit";
	}
	
	//�첽���
	public String add() {
		if(adminUser.getUsername() != null && !adminUser.getUsername().equals("")) {
			if(root.equals("sorry")) {
				//0������ͨ����Ա
				adminUser.setPrivilege(0);
				String password = DigestUtils.md5Hex(adminUser.getPassword());
				adminUser.setPassword(password);
				adminUserService.save(adminUser);
			}
		}
		return NONE;
	}
	
	//���й���Ա
	public String findAdmins() {
		if(page == null) {
			page = 1;
		}
		//��ѯ���й���Ա
		PageBean<AdminUser> pageBean = adminUserService.findByPage(page);
		ActionContext.getContext().getValueStack().set("pageBean", pageBean);
		return "findAdmins";
	}
	//��ת�����ҳ��
	public String toAdd() {
		return "toAdd";
	}
	//��ӹ���Ա
	public String addAdmin() {
		AdminUser existAdminUser = (AdminUser) ServletActionContext.getRequest().getSession().getAttribute("existAdminUser");
		if(existAdminUser != null && existAdminUser.getPrivilege().equals(1)) {
			if(adminUser.getUsername() != null &&
					!adminUser.getUsername().equals("")&& adminUser.getPassword() != null 
					&& !adminUser.getPassword().equals("")) {
				//0������ͨ����Ա
				adminUser.setPrivilege(0);
				String password = DigestUtils.md5Hex(adminUser.getPassword());
				adminUser.setPassword(password);
				adminUserService.save(adminUser);
				return "success";
			}else{
				this.addActionMessage("�û��������벻����Ϊ�գ�");
				return "toAdd";
			}
		}else{
			this.addActionMessage("Ȩ�޲���");
			return "toAdd";
		}

		
	}
	
	//ɾ������Ա
	public String delete() {
		AdminUser existAdminUser = (AdminUser) ServletActionContext.getRequest().getSession().getAttribute("existAdminUser");
		if(existAdminUser != null && existAdminUser.getPrivilege() == 1) {
			
			AdminUser deleteAdminUser = adminUserService.findByaid(adminUser.getAuid());
			if(deleteAdminUser != null) {
				adminUserService.delete(deleteAdminUser);
			}
		}else {
			this.addActionMessage("Ȩ�޲���");
		}
		return "success";
	}
	
	//��ת�޸�
	public String edit() {
		AdminUser existAdminUser = (AdminUser) ServletActionContext.getRequest().getSession().getAttribute("existAdminUser");
		if(existAdminUser != null && existAdminUser.getPrivilege() == 1) {
			
			AdminUser updateAdminUser = adminUserService.findByaid(adminUser.getAuid());
			if(updateAdminUser != null) {
				ServletActionContext.getContext().getValueStack().set("editUser", updateAdminUser);
			}
		}else {
			this.addActionMessage("Ȩ�޲���");
			return "success";
		}
		return "edit";
	}
	
	//�޸�
	public String editAdmin(){
		AdminUser existAdminUser = (AdminUser) ServletActionContext.getRequest().getSession().getAttribute("existAdminUser");
		if(existAdminUser != null && existAdminUser.getPrivilege() == 1) {
			
			AdminUser updateAdminUser = adminUserService.findByaid(adminUser.getAuid());
			if(updateAdminUser != null) {
				if(adminUser.getUsername() != null) {
					updateAdminUser.setUsername(adminUser.getUsername());
				}
				if(adminUser.getPassword() != null) {
					String password  = DigestUtils.md5Hex(adminUser.getPassword());
					updateAdminUser.setPassword(password);
				}
				adminUserService.update(updateAdminUser);
			}
		}else {
			this.addActionMessage("Ȩ�޲���");
		}
		return "success";
	}
	
}
