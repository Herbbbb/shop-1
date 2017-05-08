package cn.itcast.shop.user.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cn.itcast.shop.user.dao.FavoritesDao;
import cn.itcast.shop.user.domain.Favorites;

@Transactional
@Service
public class FavoritesService {
	private FavoritesDao favoritesDao;

	public void setFavoritesDao(FavoritesDao favoritesDao) {
		this.favoritesDao = favoritesDao;
	}
	//����ղؼ�
	public void addFavorites(Favorites favorites) {
		favoritesDao.save(favorites);
	}
	public Favorites findFavorites(Integer uid, Integer pid) {
		// TODO Auto-generated method stub
		return favoritesDao.findFavorites(uid, pid);
	}
	public List<Favorites> findByUid(Integer uid) {
		// TODO Auto-generated method stub
		return favoritesDao.findByUid(uid);
	}
	//ɾ��
	public void delete(Favorites favorites) {
		// TODO Auto-generated method stub
		favoritesDao.delete(favorites);
	}
}
