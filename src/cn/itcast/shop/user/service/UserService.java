package cn.itcast.shop.user.service;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cn.itcast.shop.user.dao.UserDao;
import cn.itcast.shop.user.domain.User;
import cn.itcast.shop.utils.PageBean;
@Transactional
@Service
public class UserService {
	private UserDao userDao;

	public void setUserDao(UserDao userDao) {
		this.userDao = userDao;
	}
	
	
	// ���û�����ѯ�û��ķ���:
	public User findByNickname(String nickname){
		return userDao.findByNickname(nickname);
	}

	// ҵ�������û�ע�����:
	
	public void save(User user) {
		// �����ݴ��뵽���ݿ�
		user.setState(1); // 0:�����û�δ����.  1:�����û��Ѿ�����.
		user.setCode(null);
		//md5����
		String password = DigestUtils.md5Hex(user.getPassword());
		user.setPassword(password);
		userDao.save(user);
		
	}

	// ҵ�����ݼ������ѯ�û�
	public User findByCode(String code) {
		return userDao.findByCode(code);
	}

	// �޸��û���״̬�ķ���
	public void update(User existUser) {
		//md5����
		String password = DigestUtils.md5Hex(existUser.getPassword());
		existUser.setPassword(password);
		userDao.update(existUser);
	}

	// �û���¼�ķ���
	public User loginByEmail(User user) {
		return userDao.loginByEmail(user);
	}
	// �û���¼�ķ���
	public User loginByNickname(User user) {
		return userDao.loginByNickname(user);
	}


	public PageBean<User> findByPage(Integer page) {
		PageBean<User> pageBean = new PageBean<User>();
		pageBean.setPage(page);
		int limit = 10;
		pageBean.setLimit(limit);
		
		int totalCount = 0;
		totalCount = userDao.findCount();
		pageBean.setTotalCount(totalCount);
		
		int totalPage = 0;
		if(totalCount % limit == 0){
			totalPage = totalCount / limit;
		}else{
			totalPage = totalCount / limit+1;
		}
		pageBean.setTotalPage(totalPage);
		int begin = (page - 1)*limit;
		List<User> list = userDao.findByPage(begin,limit);
		pageBean.setList(list);
		
		return pageBean;
	}
	//����������
	public PageBean<User> finddefriend(Integer page, Integer state) {
		PageBean<User> pageBean = new PageBean<User>();
		pageBean.setPage(page);
		int limit = 10;
		pageBean.setLimit(limit);
		
		int totalCount = 0;
		totalCount = userDao.findCount(state);
		pageBean.setTotalCount(totalCount);
		
		int totalPage = 0;
		if(totalCount % limit == 0){
			totalPage = totalCount / limit;
		}else{
			totalPage = totalCount / limit+1;
		}
		pageBean.setTotalPage(totalPage);
		int begin = (page - 1)*limit;
		List<User> list = userDao.findByPage(begin,limit,state);
		pageBean.setList(list);
		return pageBean;
	}

	public User findByUid(Integer uid) {
		
		return userDao.findByUid(uid);
	}


	public void delete(User existUser) {
		userDao.delete(existUser);
		
	}


	public User findByEmail(String email) {
		// TODO Auto-generated method stub
		return userDao.findByEmail(email);
	}
}
