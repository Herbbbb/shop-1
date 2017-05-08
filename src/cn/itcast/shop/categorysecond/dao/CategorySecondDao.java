package cn.itcast.shop.categorysecond.dao;

import java.util.List;

import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

import cn.itcast.shop.category.domain.Category;
import cn.itcast.shop.categorysecond.domian.CategorySecond;
import cn.itcast.shop.utils.PageHibernateCallback;

public class CategorySecondDao extends HibernateDaoSupport{
	//����csid����CategorySecond
	public CategorySecond findCSByCsid(Integer csid){
		String hsql = "from CategorySecond where csid = ?";
		List<CategorySecond> cs = this.getHibernateTemplate().find(hsql,csid);
		if(cs!=null && cs.size()>0){
			return cs.get(0);
		}else{
			return null;
		}
	}
	//��ѯ������������
	public int findCount() {
		String hql = "select count(*) from CategorySecond";
		List<Long> list = this.getHibernateTemplate().find(hql);
		if(list != null && list.size() > 0){
			return list.get(0).intValue();
		}
		return 0;
	}
	public List<CategorySecond> findByPage(int begin, int limit) {
		String hql = "from CategorySecond";
		List<CategorySecond> list = this.getHibernateTemplate().execute(new PageHibernateCallback<CategorySecond>(hql,null,begin,limit));
		return list;
	}
	public void update(CategorySecond categorySecond) {
		this.getHibernateTemplate().update(categorySecond);
		
	}
	public List<CategorySecond> findAll() {
		String hql = "from CategorySecond";
		List<CategorySecond> list = this.getHibernateTemplate().find(hql);
		if(list!=null && list.size() > 0){
			return list;
		}
		return null;
	}
	//����
	public void save(CategorySecond categorySecond) {
		this.getHibernateTemplate().save(categorySecond);
		
	}
	//���Ҷ����˵�
	public List<CategorySecond> findByCid(Integer cid) {
		String hql = "from CategorySecond where cid = ?";
		List<CategorySecond> list = this.getHibernateTemplate().find(hql,cid);
		if(list!=null && list.size() > 0){
			return list;
		}
		return null;
	}
}
