package cn.itcast.shop.user.action;

import java.io.IOException;
import java.text.MessageFormat;
import java.util.List;
import java.util.Properties;
import java.util.Random;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.mail.MessagingException;
import javax.mail.Session;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts2.ServletActionContext;

import cn.itcast.mail.Mail;
import cn.itcast.mail.MailUtils;
import cn.itcast.shop.cart.domain.Cart;
import cn.itcast.shop.user.domain.Address;
import cn.itcast.shop.user.domain.User;
import cn.itcast.shop.user.service.AddressService;
import cn.itcast.shop.user.service.UserService;
import cn.itcast.shop.utils.SaveSessionTool;

import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.ModelDriven;

public class UserAction extends ActionSupport implements ModelDriven<User>{
	private User user = new User();
	private UserService userService;

	private String temp;
	public void setTemp(String temp) {
		this.temp = temp;
	}

	public void setSaveSessionTool(SaveSessionTool saveSessionTool) {
		this.saveSessionTool = saveSessionTool;
	}

	private SaveSessionTool saveSessionTool;

	
	public void setUserService(UserService userService) {
		this.userService = userService;
	}
	
	private String checkcode;
	
	public void setCheckcode(String checkcode) {
		this.checkcode = checkcode;
	}
	/**
	 * �����Ϣ
	 */
	public void clearMessages(){
		this.clearErrorsAndMessages();
		this.clearActionErrors();
	}
	
	public User getModel() {
		saveSessionTool.saveSession();
		this.clearMessages();
		return user;
	}
	/**
	 * ת����¼ҳ��
	 */
	public String loginPage(){
		return "loginPage";
	}
	/**
	 * ��¼
	 */
	public String login(){
	    // ������֤����
	    String regEx = "\\w+(\\.\\w)*@\\w+(\\.\\w{2,3}){1,3}";
	    // ����������ʽ
	    Pattern pattern = Pattern.compile(regEx);
	    // ���Դ�Сд��д��
	    // Pattern pat = Pattern.compile(regEx, Pattern.CASE_INSENSITIVE);
	    Matcher matcher = pattern.matcher(temp);
	    // �ַ����Ƿ���������ʽ��ƥ��
	    boolean rs = matcher.matches();
	    User exitsUser = null;
		if(rs){
			//�����½
			user.setEmail(temp);
			//�����û��Ƿ����
			exitsUser = userService.loginByEmail(user);
		}else{
			//�û�����½
			user.setNickname(temp);
			//�����û��Ƿ����
			exitsUser = userService.loginByNickname(user);
		}
		
		if(exitsUser==null){
			this.addActionMessage("�û��������ڻ����������");
			return "loginfail";
		}
		Cart cart = new Cart();
		ServletActionContext.getRequest().getSession().setAttribute("cart", cart);
		ServletActionContext.getRequest().getSession().setAttribute("exitsUser", exitsUser);
		return "loginsuccess";
	}
	
	
	/**
	 * ��ת��ע��ҳ��
	 */
	public String registPage(){
		return "registPage";
	}
	
	/**
	 * ע��
	 * @throws IOException 
	 */
	public String userRegist() throws IOException{
		String code = (String) ServletActionContext.getRequest().getSession().getAttribute("checkcode");
		if(!checkcode.equalsIgnoreCase(code)){
			this.addActionError("��֤�����");
			return "input";
		}
		//������Ա�
		String activecode = (String) ServletActionContext.getRequest().getSession().getAttribute("activecode");
		if(activecode != null && activecode.equals(user.getCode())) {
			User exitemailUser = userService.findByEmail(user.getEmail());
			//У������Ψһ��
			if(exitemailUser != null) {
				this.addActionError("�������Ѿ�ע���");
				return "input";
			}else{
				userService.save(user);
				this.addActionMessage("ע��ɹ�!");
			} 
		}else{
			this.addActionError("���������");
			return "input";
		}
		return "msg";
	}
	
	/**
	 * AJAX�첽�������ǳ�
	 * @throws IOException 
	 */
	public String findByName() throws IOException{
		User existUser = userService.findByNickname(user.getNickname());
		HttpServletResponse response = ServletActionContext.getResponse();
		response.setContentType("text/html;charset=UTF-8");
		// �ж�
		if (existUser != null) {
			// ��ѯ�����û�:�û����Ѿ�����
			response.getWriter().println("<font color='red'>�û����Ѿ�����</font>");
		} else {
			// û��ѯ�����û�:�û�������ʹ��
			response.getWriter().println("<font color='green'>�û�������ʹ��</font>");
		}
		return NONE;
	}
	//ajax ���ͼ����뵽�û�����
	public String sendActive() {
		String words =  "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz1234567890";
		Random random = new Random();
		StringBuffer sb = new StringBuffer();
		for (int i = 0; i < 6; i++) {
			int index = random.nextInt(words.length()); 
			char c = words.charAt(index);
			sb.append(c);
			}
		user.setCode(sb.toString());
		ActionContext.getContext().getSession().put("activecode", sb.toString().trim());
		try {
			//���ͼ����ʼ�
			Properties props = new Properties();
			props.load(this.getClass().getClassLoader().getResourceAsStream("email_template.properties"));
			String host = props.getProperty("host");
			String uname = props.getProperty("uname");
			String pwd = props.getProperty("pwd");
			String from = props.getProperty("from");
			String to = user.getEmail();
			String subject = props.getProperty("subject");
			String content = props.getProperty("content");
			content = MessageFormat.format(content,user.getCode());
			Session session = MailUtils.createSession(host, uname, pwd);
			Mail mail = new Mail(from,to,subject,content);
			try {
				MailUtils.send(session, mail);
				HttpServletResponse response = ServletActionContext.getResponse();
				response.setContentType("text/html;charset=UTF-8");
				response.getWriter().println("<font color='red'>���ͳɹ�</font>");
			} catch (MessagingException e) {
				e.printStackTrace();
			}
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return NONE;
	}

	//ajax���������Ƿ�ע���
	public String checkEmail() throws IOException {
		User existUser = userService.findByEmail(user.getEmail());
		HttpServletResponse response = ServletActionContext.getResponse();
		response.setContentType("text/html;charset=UTF-8");
		// �ж�
		if (existUser == null) {
			//û�и��û�
				response.getWriter().println("<font color='green'>�������</font>");
			} else {
				response.getWriter().println("<font color='red'>�����Ѵ���</font>");
			}
		return NONE;
	}
	
	/**
	 * �˳�
	 */
	public String quit(){
		// ����session
		ServletActionContext.getRequest().getSession().removeAttribute("exitsUser");
		return "quit";
	}
	

	//��ת���ҵ�Q��
	public String myqtao() {
		
		return "myqtao";
	}
	//�����޸��û�����
	public String updateUser() {
		User exitesUser = (User) ServletActionContext.getRequest().getSession().getAttribute("exitsUser");
		if(exitesUser != null) {
			if(user.getBirthday() != null && !user.getBirthday().equals(exitesUser.getBirthday())) {
				exitesUser.setBirthday(user.getBirthday());
			}
			if(user.getGender() != null && !user.getGender().equals(exitesUser.getGender())) {
				exitesUser.setGender(user.getGender());
			}
			if(user.getName() != null && !user.getName().equals(exitesUser.getName())) {
				exitesUser.setName(user.getName());
			}
			if(user.getNickname() != null && !user.getNickname().equals(exitesUser.getNickname())) {
				exitesUser.setNickname(user.getNickname());
			}
			if(user.getResidence() != null && !user.getResidence().equals(exitesUser.getResidence())) {
				exitesUser.setResidence(user.getResidence());
			}
			
			userService.update(exitesUser);
			this.addActionMessage("�޸��û���Ϣ�ɹ���");
		}
		
		return "updateUser";
	}
	//��ת����д����ҳ
	public String findPassword() {
		
		return "findpassword";
	}
	public String sendEmailfindpassword() {
		//����email�����û��Ƿ����
		User emailUser = userService.findByEmail(user.getEmail());
		if(emailUser == null) {
			this.addActionMessage("email������");
			return "findpassword";
		}else{
			String words =  "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz1234567890";
			Random random = new Random();
			StringBuffer sb = new StringBuffer();
			for (int i = 0; i < 6; i++) {
				int index = random.nextInt(words.length()); 
				char c = words.charAt(index);
				sb.append(c);
				}
			emailUser.setCode(sb.toString());
			userService.update(emailUser);
			ActionContext.getContext().getSession().put("activecode", sb.toString().trim());
			try {
				//���ͼ����ʼ�
				Properties props = new Properties();
				props.load(this.getClass().getClassLoader().getResourceAsStream("password_email.properties"));
				String host = props.getProperty("host");
				String uname = props.getProperty("uname");
				String pwd = props.getProperty("pwd");
				String from = props.getProperty("from");
				String to = user.getEmail();
				String subject = props.getProperty("subject");
				String content = props.getProperty("content");
				content = MessageFormat.format(content,emailUser.getUid(),emailUser.getCode());
				Session session = MailUtils.createSession(host, uname, pwd);
				Mail mail = new Mail(from,to,subject,content);
				try {
					MailUtils.send(session, mail);
				} catch (MessagingException e) {
					e.printStackTrace();
				}
				
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		
		this.addActionMessage("�����ʼ��ɹ����뵽������������!");
		return "msg";
	}

	//��ת��������ҳ
	public String resetpassword() {
		User emailUser = userService.findByUid(user.getUid());
		String code = (String) ServletActionContext.getRequest().getSession().getAttribute("activecode");
		if(code != null && emailUser != null) {
			if(code.equals(emailUser.getCode())) {
				ServletActionContext.getRequest().setAttribute("uid", emailUser.getUid());
				return "resetpassword";
			}
		}
		this.addActionMessage("���ʼ��Ѿ����ù����룬�����·���");
		return "msg";
	}
	//�ύ��������
	public String submitpassword() {
		User resetUser = userService.findByUid(user.getUid());
		if(resetUser != null) {
			resetUser.setPassword(user.getPassword());
			resetUser.setCode(null);
			userService.update(resetUser);
			return "loginPage";
		}
		this.addActionMessage("��ѽ���������ˣ�����һ��");
		return "msg";
	}
}
