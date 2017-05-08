package cn.itcast.shop.user.dao;

import java.util.List;

import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

import cn.itcast.shop.user.domain.User;
import cn.itcast.shop.utils.PageHibernateCallback;

public class UserDao extends HibernateDaoSupport{
	
	// �����ֲ�ѯ�Ƿ��и��û�:
		public User findByNickname(String nickname){
			String hql = "from User where nickname = ?";
			List<User> list = this.getHibernateTemplate().find(hql, nickname);
			if(list != null && list.size() > 0){
				return list.get(0);
			}
			return null;
		}
		
		// ע���û��������ݿ����ʵ��
		public void save(User user) {
			this.getHibernateTemplate().save(user);
		}

		// ���ݼ������ѯ�û�
		public User findByCode(String code) {
			String hql = "from User where code = ?";
			List<User> list = this.getHibernateTemplate().find(hql,code);
			if(list != null && list.size() > 0){
				return list.get(0);
			}
			return null;
		}
		
		// �޸��û�״̬�ķ���
		public void update(User existUser) {
			this.getHibernateTemplate().update(existUser);
		}

		// �û���¼�ķ���
		public User loginByEmail(User user) {
			String hql = "from User where email = ? and password = ? and state = ?";
			//��ͬmd5���ܲ鿴
			String password = DigestUtils.md5Hex(user.getPassword());
			List<User> list = this.getHibernateTemplate().find(hql, user.getEmail(),password,1);
			if(list != null && list.size() > 0){
				return list.get(0);
			}
			return null;
		}
		// �û���¼�ķ���
		public User loginByNickname(User user) {
			String hql = "from User where nickname = ? and password = ? and state = ?";
			//��ͬmd5���ܲ鿴
			String password = DigestUtils.md5Hex(user.getPassword());
			List<User> list = this.getHibernateTemplate().find(hql, user.getNickname(),password,1);
			if(list != null && list.size() > 0){
				return list.get(0);
			}
			return null;
		}

		public int findCount() {
			String hql = "select count(*) from User";
			List<Long> list = this.getHibernateTemplate().find(hql);
			if(list != null && list.size() > 0){
				return list.get(0).intValue();
			}
			return 0;
		}

		public List<User> findByPage(int begin, int limit) {
			String hql = "from User";
			List<User> list = this.getHibernateTemplate().execute(new PageHibernateCallback<User>(hql,null,begin,limit));
			return list;
		}

		public User findByUid(Integer uid) {
			String hql = "from User where uid=?";
			return this.getHibernateTemplate().get(User.class, uid);
		}

		public void delete(User existUser) {
			this.getHibernateTemplate().delete(existUser);
			
		}

		public User findByEmail(String email) {
			String hql = "from User where email = ?";
			List<User> list = this.getHibernateTemplate().find(hql, email);
			if(list != null && list.size() > 0){
				return list.get(0);
			}
			return null;
		}

		public int findCount(Integer state) {
			String hql = "select count(*) from User where state=?";
			List<Long> list = this.getHibernateTemplate().find(hql,state);
			if(list != null && list.size() > 0){
				return list.get(0).intValue();
			}
			return 0;
		}

		public List<User> findByPage(int begin, int limit, Integer state) {
			String hql = "from User where state=?";
			List<User> list = this.getHibernateTemplate().execute(new PageHibernateCallback<User>(hql,new Integer[]{state} ,begin,limit));
			return list;
		}
		
		
}
