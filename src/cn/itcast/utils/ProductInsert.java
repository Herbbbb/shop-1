package cn.itcast.utils;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ProductInsert {
	// ����
	private static final String ECODING = "UTF-8";
	// ��ȡimg��ǩ����
	private static final String IMGURL_REG = "<img.*err-product.*src=[^>]*?>";
	// ��ȡsrc·��������
	private static final String IMGSRC_REG = "img[0-9].+360buyimg.com.+jpg";
	//��ȡ��title��img����
	private static final String IMGTITLE_REG = "(<a.+title=[^>]*?>)([^(<a.title=[^>]*?>)])";
	//��ȡtitle��ǩ������
	private static final String TITLE_REG = "blank.*title=(.*).*href";
	private static final String TITLE_REG1 = "=.+(\"){1}";
	//��Ҫ��ͼ����
	private static final String TU2 = "img30.360buyimg.com/da/jfs/t1915/215/1329999964/2996/d7ff13f0/5698dc03N23f2e3b8.jpg";
	private static final String TU3 = "img14.360buyimg.com/da/jfs/t2026/221/2097811452/2816/8eb35b4b/5698dc16Nb2ab99df.jpg";
	private static final String TU4 = "img12.360buyimg.com/cms/jfs/t2050/256/1470027660/4336/2a2c74bd/56a89b8fNfbaade9a.jpg";
	
	/**
	 * 
	 * ���ݹؼ�����������ͼƬ�����ػ�����title������ͼƬ���Ϻ�title���ϵ�map
	 * @param keyword �ؼ���
	 * @param pathHeader ·����ͷ���������̷�����E:
	 * @param category ·��һ����
	 * @param categorySecond ·��һ����
	 * @param categoryThird ·��һ����
	 * @return
	 */
	public Map<String, List<String>> getPictureAndTitle(String keyword,String pathHeader, String category, String categorySecond, String categoryThird) {
		Map<String, List<String>> map = new HashMap<String,List<String>>();
		String HTML = searchHTML(keyword);
		List<String> pictures = searchpicture(HTML, pathHeader, category, categorySecond, categoryThird);
		List<String> titles = getProductTitle(HTML);
		map.put("pictures", pictures);
		map.put("titles", titles);
		return map;
		
	}
	/**
	 * ���ݹؼ�����������ͼƬ�����أ�·�����  :�̷�/һ��·��/����·��/����·��/
	 * @param keyword �ؼ���
	 * @param pathHeader ·����ͷ���������̷�����E:
	 * @param category ·��һ����
	 * @param categorySecond ·��һ����
	 * @param categoryThird ·��һ����
	 * 
	 */
	public List<String> searchpicture(String HTML, String pathHeader, String category, String categorySecond, String categoryThird) {
		List<String> imgPath = null;
		try {
			//��ȡͼƬ��ǩ
			List<String> imgUrl = this.getImageUrl(HTML);
			//��ȡͼƬsrc��ַ
			List<String> imgSrc = this.getImageSrc(imgUrl);
			//����
			imgPath = this.Download(imgSrc, pathHeader, category, categorySecond, categoryThird);
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return imgPath;
	}
	/**
	 * ��ȡͼƬ����
	 * @param keyword �����Ĺؼ��ֺ����ص�һ��
	 * @return
	 */
	public List<String> getProductTitle (String HTML) {
		List<String> title1 = null;
		try {
			//��ȡtitleURL
			List<String> titleUrl = this.getTitleUrl(HTML);
			//��ȡtitle����
			List<String> title = this.getTitle(titleUrl);
			title1 = this.getTitle1(title);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return title1;
	}
	/**
	 * HTML����
	 * @param keyword
	 * @return
	 */
	private String searchHTML(String keyword) {
		String HTML = null;
		try {
			keyword = urlChange(keyword);
			String url = "https://search.jd.com/Search?keyword=" +
					keyword +
					"&enc=utf-8&pvid=b3e95ae00061403a87ea06ebe8b90c99";
			HTML = this.getHTML(url);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return HTML;
	}
	
	/***
	 * ��ȡHTML����
	 * 
	 * @param url
	 * @return
	 * @throws Exception
	 */
	private String getHTML(String url) throws Exception {
		URL uri = new URL(url);
		URLConnection connection = uri.openConnection();
		InputStream in = connection.getInputStream();
		byte[] buf = new byte[1024];
		int length = 0;
		StringBuffer sb = new StringBuffer();
		while ((length = in.read(buf, 0, buf.length)) > 0) {
			sb.append(new String(buf, ECODING));
		}
		in.close();
		return sb.toString();
	}

	/***
	 * ��ȡImageUrl��ַ
	 * 
	 * @param HTML
	 * @return
	 */
	private List<String> getImageUrl(String HTML) {
		Matcher matcher = Pattern.compile(IMGURL_REG).matcher(HTML);
		List<String> listImgUrl = new ArrayList<String>();
		while (matcher.find()) {
			listImgUrl.add(matcher.group());
		}
		return listImgUrl;
	}

	/***
	 * ��ȡImageSrc��ַ
	 * 
	 * @param listImageUrl
	 * @return
	 */
	private List<String> getImageSrc(List<String> listImageUrl) {
		List<String> listImgSrc = new ArrayList<String>();
		for (String image : listImageUrl) {
			Matcher matcher = Pattern.compile(IMGSRC_REG).matcher(image);
			while (matcher.find()) {
				listImgSrc.add(matcher.group());
			}
		}
		return listImgSrc;
	}
	
	
	/***
	 * ��ȡtitleUrl��ַ
	 * 
	 * @param HTML
	 * @return TitleUrl
	 */
	private List<String> getTitleUrl(String HTML) {
		Matcher matcher = Pattern.compile(IMGTITLE_REG).matcher(HTML);
		List<String> TitleUrl = new ArrayList<String>();
		while (matcher.find()) {
			TitleUrl.add(matcher.group());
		}
		return TitleUrl;
	}
	
	//��ȡ��ҳtitle
	private List<String> getTitle(List<String> imgUrl) {
		List<String> listTitle = new ArrayList<String>();
		for (String title : imgUrl) {
			Matcher matcher = Pattern.compile(TITLE_REG).matcher(title);
			while (matcher.find()) {
				listTitle.add(matcher.group());
			}
		}
		return listTitle;
	}
	//��ȡ��ҳtitle
	private List<String> getTitle1(List<String> imgUrl) {
		List<String> listTitle = new ArrayList<String>();
		for (String title : imgUrl) {
			Matcher matcher = Pattern.compile(TITLE_REG1).matcher(title);
			while (matcher.find()) {
				listTitle.add(matcher.group().substring(2,matcher.group().length()-1).replace("����", "Q��"));
			}
		}
		return listTitle;
	}

	/***
	 * ����ͼƬ
	 * 
	 * @param listImgSrc
	 */
	private List<String> Download(List<String> listImgSrc,String pathHeader, String category, String categorySecond, String categoryThird) {
		ArrayList<String> imgPathList = new ArrayList<String>();
		try {
			int i = 0;
			for (String url : listImgSrc) {
				if(url.equals(TU2) || url.equals(TU3) || url.equals(TU4)){
					continue;
				}
				String u = "http://"+url;
				String imageName = pathHeader+"/"+category+"/"+categorySecond+"/"+categoryThird;  
				URL uri = new URL(u);
				InputStream in = uri.openStream();
				BufferedInputStream bs = new BufferedInputStream(in);
				File file = new File(imageName);
				if(!file.exists()) {
					file.mkdirs();
				}
				String filename = imageName+"/"+i+".jpg";
				File file1 = new File(filename);
				FileOutputStream fo = new FileOutputStream(file1);
				BufferedOutputStream bo = new BufferedOutputStream(fo);
				
				byte[] buf = new byte[1024];
				int length = 0;
				System.out.println("��ʼ����:" + u);
				while ((length = bs.read(buf, 0, buf.length)) != -1) {
					bo.write(buf, 0, length);
				}
				
				bs.close();
				bo.close();
				System.out.println(filename + "�������");
				imgPathList.add(filename.substring(filename.indexOf("/")+1,filename.length()));
				i++;
			}
			
		} catch (Exception e) {
			System.out.println("����ʧ��");
		}
		return imgPathList;
	}
	
	/**
	 * URL����
	 * @param key��Ҫ���������
	 * @return
	 */
	private String urlChange(String key) {
		String keyword = null;
		try {
			keyword = URLEncoder.encode(key, "UTF-8");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		return keyword;
	}
	
	/**
	 * ����һ��ͼƬ
	 * @param imgaddr ͼƬ��ַ
	 * @param path ·��
	 * @return
	 */
	public String DownloadImg(String imgaddr ,String path, Integer i) {
		String imgpath = null;
		try {
				String u = "http://"+imgaddr;
				String imageName = "E:/products/"+path;  
				URL uri = new URL(u);
				InputStream in = uri.openStream();;
				BufferedInputStream bs = new BufferedInputStream(in);
				File file = new File(imageName);
				if(!file.exists()) {
					file.mkdirs();
				}
				String img = imageName+i+".jpg";
				File file1 = new File(img);
				FileOutputStream fo = new FileOutputStream(file1);
				BufferedOutputStream bo = new BufferedOutputStream(fo);
				
				byte[] buf = new byte[1024];
				int length = 0;
				System.out.println("��ʼ����:" + u);
				while ((length = bs.read(buf, 0, buf.length)) != -1) {
					bo.write(buf, 0, length);
				}
				
				bs.close();
				bo.close();
				System.out.println(img + "�������");
				imgpath = img.substring(img.indexOf("/")+1,img.length());
			
		} catch (Exception e) {
			System.out.println("����ʧ��");
		}
		return imgpath;
	}
}
