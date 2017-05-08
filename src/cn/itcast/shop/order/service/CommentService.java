package cn.itcast.shop.order.service;

import cn.itcast.shop.order.dao.CommentDao;
import cn.itcast.shop.order.domain.Comment;

public class CommentService {
	private CommentDao commentDao;

	public void setCommentDao(CommentDao commentDao) {
		this.commentDao = commentDao;
	}
	
	//����oid������
	public Comment findByoid(Integer oid) {
		
		return commentDao.findByoid(oid);
	}
	
}
