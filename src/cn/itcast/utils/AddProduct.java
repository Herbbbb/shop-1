package cn.itcast.utils;

import java.io.File;
import java.util.Date;

import org.junit.Test;
import org.apache.commons.codec.digest.DigestUtils;

import cn.itcast.shop.categorysecond.domian.CategorySecond;
import cn.itcast.shop.product.dao.ProductDao;
import cn.itcast.shop.product.domain.Product;

public class AddProduct {
	
	ProductDao pr = new ProductDao();
	@Test
	public void test(){
//		String ss = this.getClass().getClassLoader().getResource("").getPath();
		File srcFile = new File("E:\\Workspaces\\MyPro\\ssh\\WebRoot\\products\\2\\��Ь");
//		System.out.println(ss);
		this.getAllJavaFilePaths(srcFile);
	}
	
	
	public  void getAllJavaFilePaths(File srcFolder) {
		// ��ȡ��Ŀ¼�����е��ļ������ļ��е�File����
		File[] fileArray = srcFolder.listFiles();
		// ������File���飬�õ�ÿһ��File����
		
		int i = 0;
		for (File file : fileArray) {
			// �жϸ�File�����Ƿ����ļ���
			if (file.isDirectory()) {
				getAllJavaFilePaths(file);
			} else {
				// �����ж��Ƿ���.java��β
				if (file.getName().endsWith(".jpg")) {
					//File oldfile=new File(file.getPath()); 
		           // File newfile=new File(file.getParent()+"/"+"cs"+(i++)+".jpg"); 
					// ��������ļ��ľ���·��
					String s = file.getPath().substring(file.getPath().indexOf("products"));
					Product product = new Product();
					product.setPname("��Ь"+i++);
					product.setIs_hot(0);
					product.setMarket_price(153.0+i++);
					product.setShop_price(120.3+(i++)*0.8);
					product.setImage(s);
					product.setPdate(new Date());
					product.setPdesc("���µ���ĺ�����Ь��Ь��ɽЬ��װЬ");
					CategorySecond cs = new CategorySecond();
					cs.setCsid(11);
//					product.setCategorySecond(cs);
					
					System.out.println(s);
					System.out.println(product.toString()+cs.getCsid());
		           // oldfile.renameTo(newfile);
		            
					
				}
			}
		}
	}

	//���Լ���
	@Test
	public void md5Test() {
		String ss = "123456";
		System.out.println(DigestUtils.md5Hex(ss.getBytes()));
		System.out.println(	DigestUtils.md5(ss.getBytes()).toString());
		System.out.println(	DigestUtils.shaHex(ss));
		System.out.println(	DigestUtils.md5(ss));
		
		
		
	}

}
