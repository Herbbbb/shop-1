package cn.itcast.utils;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.hibernate.CacheMode;
import org.hibernate.FlushMode;
import org.hibernate.ScrollMode;
import org.hibernate.ScrollableResults;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.search.FullTextSession;
import org.hibernate.search.Search;
import org.junit.Test;

import cn.itcast.shop.category.domain.Category;
import cn.itcast.shop.category.service.CategoryService;
import cn.itcast.shop.categorysecond.domian.CategorySecond;
import cn.itcast.shop.categorysecond.service.CategorySecondService;
import cn.itcast.shop.categorythird.domain.CategoryThird;
import cn.itcast.shop.categorythird.service.CategoryThirdService;
import cn.itcast.shop.product.domain.Product;
import cn.itcast.shop.product.service.ProductService;
import cn.itcast.shop.user.domain.User;

import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.ModelDriven;

public class ActionTest extends ActionSupport implements ModelDriven<User> {
	private CategoryService categoryService;
	private CategorySecondService categorySecondService;
	private CategoryThirdService categoryThirdService;
	private ProductService productService;
	private User user = new User();
	public void setCategoryService(CategoryService categoryService) {
		this.categoryService = categoryService;
	}
	public void setCategorySecondService(CategorySecondService categorySecondService) {
		this.categorySecondService = categorySecondService;
	}
	public void setCategoryThirdService(CategoryThirdService categoryThirdService) {
		this.categoryThirdService = categoryThirdService;
	}
	public void setProductService(ProductService productService) {
		this.productService = productService;
	}
	private ProductInsert pi;
	//������ҳ
	public String pageTest() {
		return "pageTest";
	}
	public String login() {
		return "login";
	}
	public String regist() {
		return "regist";
	}
	//������ʾҳ��
	public String msg() {
		this.clearErrorsAndMessages();
		this.addActionMessage("��½����");
//		this.addActionError("����ʧ��");
		return "msg";
	}
	//���Ա��ύ
	public String Testfrom() {
		String email = user.getEmail();
		String password = user.getPassword();
		String nickname= user.getNickname();
		
		return NONE;
	}
	public String Test() {
		String pathHeader = "E:/products";
		String CategoryName = null;
		String CategorySecondName = null;
		String CategoryThirdName = null;

		Integer cid = 17;
		Integer csid = 189;
		Integer ctid= 926;
		boolean b = true;
		String bb = "categoryThird.getCtid().equals(ctid)";
		
		List<CategorySecond> s = categorySecondService.findByCid(cid);
		CategoryName = categoryService.findByCid(cid).getCname();
//			CategorySecond cs = s.get(csnum);
		for(CategorySecond cs : s) {
			if(cs.getCsid().equals(csid)) {
				CategorySecondName = cs.getCsname();
				List<CategoryThird> categoryThirdList = categoryThirdService.findByCsid(cs.getCsid());
				for(CategoryThird categoryThird : categoryThirdList) {
					if(b) {
						pi = new ProductInsert();
						//��ȡ�����˵�����
						CategoryThirdName = categoryThird.getCtname();
						//��ȡͼƬ��title�ļ���
						Map<String, List<String>> map = pi.getPictureAndTitle(CategoryThirdName,
								pathHeader, CategoryName, CategorySecondName, CategoryThirdName);
						
						//����������
						List<String> imageList = map.get("pictures");
						if(imageList.size()<1) {
							continue;
						}
						//ͼƬ������
						List<String> descList = map.get("titles");
						if(descList.size()<imageList.size()) {
							for(String s1 : imageList) {
								descList.add("Q���̳�     ��Ʒ����     ʵ���л�");
							}
						}
						if(imageList.size() < descList.size()) {
							for(int i = 0; i < imageList.size(); i++) {
								//�����Ʒ
								Product product = new Product();
								product.setCategoryThird(categoryThird);
								product.setImage(imageList.get(i));
								if(i%2==0) {
									product.setIs_hot(1);
									product.setMarket_price(250.0+8*i);
									product.setShop_price(200.0+7*i);
								}else{
									product.setIs_hot(0);
									product.setMarket_price(320.0+8*i);
									product.setShop_price(300.0+7*i);
								}
								
								product.setPdate(new Date());
								product.setPdesc(descList.get(i));
								product.setPname(descList.get(i));
//								productService.saveProduct(product);
							}
						}else{
							//imageList.size() > descList.size()
							int j = 0;
							for(int i = 0; i < imageList.size(); i++) {
								//�����Ʒ
								Product product = new Product();
								product.setCategoryThird(categoryThird);
								product.setImage(imageList.get(i));
								if(i%2==0) {
									product.setIs_hot(1);
									product.setMarket_price(250.0+8*i);
									product.setShop_price(200.0+7*i);
								}else{
									product.setIs_hot(0);
									product.setMarket_price(320.0+8*i);
									product.setShop_price(300.0+7*i);
								}
								
								product.setPdate(new Date());
								if(i>descList.size()-1) {
									if(j<descList.size()-2) {
										product.setPdesc(descList.get(j));
										product.setPname(descList.get(j));
										j++;
									}else{
										j = 0;
									}
								}else{
									product.setPdesc(descList.get(i));
									product.setPname(descList.get(i));
								}
//								productService.saveProduct(product);
							}
						}
					
					}
				}
					
			}
			
		}
		return NONE;
	}
	/**
	 * ������Ʒ
	 * @return
	 */
	public String saveProduct() {
		String pathHeader = "E:/products";
		String CategoryName = null;
		String CategorySecondName = null;
		String CategoryThirdName = null;
		String Category = null;
		String CategorySecond = null;
		String CategoryThird = null;
		// ��������һ���˵�
		List<Category> categoryList = categoryService.findByCids(1,1);
		for (Category category : categoryList) {
			//��ȡһ���˵�����
			CategoryName = category.getCname();
			Category = category.getCid().toString();
			// ����һ���˵�cid���Ҷ����˵�
			List<CategorySecond> categorySecondList = categorySecondService
					.findByCid(category.getCid());
			// ���ݶ����˵�csid���������˵�
			for (CategorySecond categorySecond : categorySecondList) {
				//��ȡ�����˵�����
				CategorySecondName = categorySecond.getCsname();
				CategorySecond = categorySecond.getCsid().toString();
				List<CategoryThird> categoryThirdList = categoryThirdService
						.findByCsid(categorySecond.getCsid());
				// �����Ʒ
				for (CategoryThird categoryThird : categoryThirdList) {
					pi = new ProductInsert();
					//��ȡ�����˵�����
					CategoryThirdName = categoryThird.getCtname();
					CategoryThird = categoryThird.getCtid().toString();
					//��ȡͼƬ��title�ļ���
					Map<String, List<String>> map = pi.getPictureAndTitle(CategorySecondName+" "+CategoryThirdName,
							pathHeader, Category, CategorySecond, CategoryThird);
					
					//����������
					List<String> imageList = map.get("pictures");
					if(imageList.size() < 1) {
						continue;
					}
					//ͼƬ������
					List<String> descList = map.get("titles");
					if(descList.size() < imageList.size()) {
						for(String s : imageList) {
							descList.add("Q���̳�    ��Ʒ����   ��ʱ��ɱ  ʵ���л� ֧�ֻ������POS��ˢ�����ۺ����ŷ���");
						}
					}
					if(imageList.size() < descList.size()) {
						for(int i = 0; i < imageList.size(); i++) {
							//�����Ʒ
							Product product = new Product();
							product.setCategoryThird(categoryThird);
							product.setImage(imageList.get(i));
							if(i%2==0) {
								product.setIs_hot(1);
								product.setMarket_price(250.0+8*i);
								product.setShop_price(200.0+7*i);
							}else{
								product.setIs_hot(0);
								product.setMarket_price(320.0+8*i);
								product.setShop_price(300.0+7*i);
							}
							
							product.setPdate(new Date());
							product.setPdesc(descList.get(i));
							product.setPname(descList.get(i));
//							productService.saveProduct(product);
						}
					}else{
						//imageList.size() > descList.size()
						int j = 0;
						for(int i = 0; i < imageList.size(); i++) {
							//�����Ʒ
							Product product = new Product();
							product.setCategoryThird(categoryThird);
							product.setImage(imageList.get(i));
							if(i%2==0) {
								product.setIs_hot(1);
								product.setMarket_price(250.0+8*i);
								product.setShop_price(200.0+7*i);
							}else{
								product.setIs_hot(0);
								product.setMarket_price(320.0+8*i);
								product.setShop_price(300.0+7*i);
							}
							
							product.setPdate(new Date());
							if(i>descList.size()-1) {
								if(j<descList.size()-2) {
									product.setPdesc(descList.get(j));
									product.setPname(descList.get(j));
									j++;
								}else{
									j = 0;
								}
							}else{
								product.setPdesc(descList.get(i));
								product.setPname(descList.get(i));
							}
							
//							productService.saveProduct(product);
						}
					}
					
				}
			}
		}
		
		return "login";
	}
	@Test
	public void saveTest() {
		List<Category> list = categoryService.findByCids(0, 6);
		System.out.println(list.size()+""+list.get(0).getCname()+list.get(1).getCname()+list.get(5).getCname());
	}
	
	//һ�β�������Ʒ
	public String addMoreProduct() {
		//�ҵ�ͼƬ·��
		String imagePath = "E:\\Workspaces\\MyProject\\ssh\\WebRoot\\img\\brand";
		File file = new File(imagePath);
		File[] filelist = file.listFiles();
		for(File image : filelist) {
			if (image.getName().endsWith(".jpg")) {
				String s = image.getPath().substring(image.getPath().indexOf("img"));
				Product product = new Product();
				product.setPname("");
				product.setIs_hot(4);
//				product.setMarket_price(2250.0);
//				product.setShop_price(2010.0);
				product.setImage(s.replace("\\", "/"));
//				product.setPdesc("");
//				System.out.println(product.toString());
//				productService.saveProduct(product);
			}
		}
		return NONE;
	}
	public User getModel() {
		// TODO Auto-generated method stub
		return user;
	}
	//дһ���ļ���lua��������
	public String createPath() throws IOException {
		int i = 1;
		String keyword = null;
		String imgpath = null;
		StringBuffer keywordsb = new StringBuffer();
		StringBuffer imgpathsb = new StringBuffer();
		List<Category> categorys = categoryService.findAll();
		keywordsb.append("data=").append("{");
		for(Category category : categorys) {
			//����һ���˵�id���Ҷ����˵�
			List<CategorySecond> categoryseconds = categorySecondService.findByCid(category.getCid());
			for(CategorySecond categorysecond : categoryseconds) {
				//���ݶ����˵�id���������˵�
				List<CategoryThird> categorythirds = categoryThirdService.findByCsid(categorysecond.getCsid());
			
				for(CategoryThird categorythird : categorythirds) {
					keyword = "["+"\""+category.getCid().toString()+"/"+categorysecond.getCsid().toString()+"/"+categorythird.getCtid().toString()+"/"
							+"\""+"]"+"="+"\""+categorysecond.getCsname()+" "+categorythird.getCtname()+"\"";
					
					i++;
					
					keywordsb.append(keyword).append(",");
				}
				
				
			}
		}
		
		OutputStream os = null ;
		keywordsb.append("}").append(";return data");
		
		//д��TXT��
		try {
			File file = new File("E:\\Workspaces\\MyProject\\ssh\\src\\text.txt");
			os = new FileOutputStream(file);
			
			byte[] b = keywordsb.toString().getBytes();
			os.write(b);
			
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally{
			if(os != null) {
				os.close();
				os=null;
			}
			
		}
		return "login";
	}
	
	//����txt�ļ��е�ֵ����������
	public String findPicture() {
		//�ȶ��ļ�
		BufferedReader br = null;
		FileInputStream in = null;
		try{
			File infile = new File("E:\\Workspaces\\dashi.txt");
			in = new FileInputStream(infile);
			br = new BufferedReader(new InputStreamReader(in,"utf-8"));
			String line = null;
			int i = 0;
			int j = 0;
			List<String> list = new ArrayList<String>();
			while((line=br.readLine())!=null) {
				if(line.length()>0) {
					i++;
					
					list.add(line);
					if(i==3) {
						
						//����
						i=0;
						//·��
						String path = list.get(0);
						//ͼƬ��ַ
						String imgaddr = list.get(1);
						//ͼƬ����
						String desc = list.get(2);
						ProductInsert pinsert = new ProductInsert();
						//����
//						String img = pinsert.DownloadImg(imgaddr, path, j);
						
						//�����Ʒ
						Product product = new Product();
						if(j%2==0) {
							product.setIs_hot(1);
							product.setMarket_price(285.0);
							product.setShop_price(212.0);
						}else{
							product.setIs_hot(0);
							product.setMarket_price(396.0);
							product.setShop_price(303.0);
						}
						product.setPdate(new Date());
						product.setPname(desc);
						product.setPdesc(desc);
						CategoryThird categoryThird = categoryThirdService.findByCtid(Integer.parseInt(list.get(0).split("/")[2]));
						product.setImage("");
						product.setCategoryThird(categoryThird);
//						productService.saveProduct(product);
						list = new ArrayList<String>();
						j++;
					}
				}
			}
		}catch(Exception e){
			e.printStackTrace();
		}finally{
			try{
				if(br!=null) {
					br.close();
					br = null;
				}
			}catch(Exception e){
				e.printStackTrace();
			}
		}
		
		return "login";
	}
	
	private SessionFactory sessionFactory;
	
	
	
		public void setSessionFactory(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}
		//Ϊ���ݿ����Ѵ��ڵ����ݴ�������
		public String createIndex() {
			FullTextSession fts = Search.getFullTextSession(sessionFactory.openSession());
				
			fts.setFlushMode(FlushMode.MANUAL);
			fts.setCacheMode(CacheMode.IGNORE);
				
			int BATCH_SIZE = 1000;
			long startTime = new Date().getTime();
			//Scrollable results will avoid loading too many objects in memory
			ScrollableResults results = fts.createCriteria(Product.class).setFetchSize(BATCH_SIZE).scroll(ScrollMode.FORWARD_ONLY );
			int index = 0;
			while( results.next() ) {
				index++;
				fts.index( results.get(0) ); //index each element
				if (index % BATCH_SIZE == 0) {
					fts.flushToIndexes(); //apply changes to indexes
				   	fts.clear(); //free memory since the queue is processed
				}
			}
				
			long endTime = new Date().getTime();
			System.out.println("���ν�����������ʱ" + (endTime - startTime));
				return "login";
		}
	
	
}
