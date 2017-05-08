package cn.itcast.shop.product.service;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.SessionFactory;
import org.hibernate.search.FullTextQuery;
import org.hibernate.search.FullTextSession;
import org.hibernate.search.Search;
import org.hibernate.search.query.dsl.QueryBuilder;

import cn.itcast.shop.product.dao.ProductDao;
import cn.itcast.shop.product.domain.Product;
import cn.itcast.shop.utils.PageBean;

public class ProductService {

	private ProductDao productDao;

	public void setProductDao(ProductDao productDao) {
		this.productDao = productDao;
	}

	public List<Product> findHot(int begin) {
		
		return productDao.findHot(begin);
	}

	public List<Product> findNew(int begin, int limit) {
		
		return productDao.findNew(begin, limit);
	}

	public Product findByPid(Integer pid) {
		// TODO Auto-generated method stub
		return productDao.findByPid(pid);
	}
	// ���ݶ��������ѯ��Ʒ��Ϣ
	public PageBean<Product> findByPageCsid(Integer csid, int page) {
		PageBean<Product> pageBean = new PageBean<Product>();
		//���õ�ǰҳ��
		pageBean.setPage(page);
		//����ÿҳ��ʾ��¼��
		int limit = 36;
		pageBean.setLimit(limit);
		//�����ܼ�¼��
		int totalCount = 0;
		totalCount = productDao.findCountCsid(csid);
		pageBean.setTotalCount(totalCount);
		//������ҳ��
		int totalPage=0;
		if(totalCount%limit==0){
			totalPage = totalCount/limit;
		}else{
			totalPage = totalCount/limit+1;
		}
		
		pageBean.setTotalPage(totalPage);
		
		//ÿҳ��ʾ�����ݼ���
		int begin = (page - 1)*limit;
		
		List<Product> list = productDao.findByPageCsid(csid,begin,limit);
		pageBean.setList(list);
		return pageBean;
	}
	//����һ�������cid�ͷ�ҳ��ѯ��Ʒ
	public PageBean<Product> findByPageCid(Integer cid, int page) {
		PageBean<Product> pageBean = new PageBean<Product>();
		//���õ�ǰҳ��
		pageBean.setPage(page);
		//����ÿҳ��ʾ��¼��
		int limit = 36;
		pageBean.setLimit(limit);
		//�����ܼ�¼��
		int totalCount = 0;
		totalCount = productDao.findCountCid(cid);
		pageBean.setTotalCount(totalCount);
		//������ҳ��
		int totalPage = 0;
		if(totalCount % limit==0){
			totalPage = totalCount / limit;
		}else{
			totalPage = totalCount / limit +1;
		}
		
		pageBean.setTotalPage(totalPage);
		//ÿҳ��ʾ�����ݼ���
		int begin = (page - 1) * limit;
		
		List<Product> list = productDao.findByPageCid(cid,begin,limit);
		pageBean.setList(list);
		
		return pageBean;
	}
	public void saveProduct(Product product){
		productDao.saveProduct(product);
	}

	public PageBean<Product> findByPage(Integer page) {
		PageBean<Product> pageBean = new PageBean<Product>();
		pageBean.setPage(page);
		int limit = 30;
		pageBean.setLimit(limit);
		int totalCount = 0;
		totalCount = productDao.findCount();
		pageBean.setTotalCount(totalCount);
		
		int totalPage = 0;
		if(totalCount % limit ==0){
			totalPage = totalCount / limit;
		}else{
			totalPage = totalCount /limit+1;
		}
		
		pageBean.setTotalPage(totalPage);
		int begin = (page-1)*limit;
		List<Product> list = productDao.findByPage(begin,limit);
		pageBean.setList(list);
		return pageBean;
	}

	public void update(Product product) {
		productDao.update(product);
		
	}
	//��������ֵ����������Ʒ
	public List<Product> findByHot(int hot) {
		
		return productDao.findByHot(hot);
	}
	//���������˵�ctid����������Ʒ
	public List<Product> findByCtid(int ctid) {
		return  productDao.findByCtid(ctid);
	}
	
	//����ctid�������в�Ʒ
	public PageBean<Product> findByPageCtid(int ctid, int page) {
		PageBean<Product> pageBean = new PageBean<Product>();
		//���õ�ǰҳ��
		pageBean.setPage(page);
		//����ÿҳ��ʾ��¼��
		int limit = 36;
		pageBean.setLimit(limit);
		//�����ܼ�¼��
		int totalCount = 0;
		totalCount = productDao.findCountCtid(ctid);
		pageBean.setTotalCount(totalCount);
		//������ҳ��
		int totalPage = 0;
		if(totalCount % limit==0){
			totalPage = totalCount / limit;
		}else{
			totalPage = totalCount / limit +1;
		}
		
		pageBean.setTotalPage(totalPage);
		//ÿҳ��ʾ�����ݼ���
		int begin = (page - 1) * limit;
		
		List<Product> list = productDao.findByPageCtid(ctid,begin,limit);
		pageBean.setList(list);
		
		return pageBean;
	}
	private SessionFactory sessionFactory;
	public void setSessionFactory(SessionFactory sessionFactory) {
	this.sessionFactory = sessionFactory;
}
	//���ݹؼ��ֺ͵�ǰҳ���ѯƥ����Ʒ
	public PageBean<Product> searchByKeyword(String keyword, Integer pageNo) {
		//ͨ����ǰ�����Session��ȡFullTextSession����
		FullTextSession fts = Search.getFullTextSession(sessionFactory.openSession());
		//��ȡ�ض�����ض�QueryBuilder����
		QueryBuilder qb = fts.getSearchFactory().buildQueryBuilder().forEntity(Product.class).get();
		//�õ�search query
		org.apache.lucene.search.Query luceneQuery = qb.keyword().onFields("pname").matching(keyword).createQuery();
		FullTextQuery query = fts.createFullTextQuery(luceneQuery, Product.class);
		//��ҳ��
		PageBean<Product> pageBean = new PageBean<Product>();
		//��ҳ��Ϣ
		int limit = 36;
		pageBean.setLimit(limit);
		pageBean.setPage(pageNo);
		
		int totalCount = 0;
		totalCount = query.getResultSize();
		pageBean.setTotalCount(totalCount);
		//������ҳ��
		int totalPage = 0;
		if(totalCount % limit==0){
			totalPage = totalCount / limit;
		}else{
			totalPage = totalCount / limit +1;
		}
		
		pageBean.setTotalPage(totalPage);
		
		int begin = (pageNo - 1) * limit;
	
		//��ҳ��ѯ
		query.setFirstResult(begin);
		query.setMaxResults(limit);
		List<Product> list = query.list();
		List<Product> list1 = new ArrayList<Product>();
		for(Product product : list) {
			if(product.getIs_hot() < 2) {
				list1.add(product);
			}
		}
		//��װ��ҳ����
		pageBean.setList(list1);
		    
		return pageBean;
	}
	
}
