package com.jeeplus.modules.resource.entity;

import com.jeeplus.common.persistence.DataEntity;
import com.jeeplus.modules.sys.entity.User;

/**
 * 
 * <dl>
 * <dt><span class="strong">类说明:</span></dt>
 * <dd>资源评分实体类</dd>
 * </dl>
 * <dl>
 * <dt><span class="strong">创建时间:</span></dt>
 * <dd>2017年6月22日 上午10:45:53</dd>
 * </dl>
 * 
 * @author duan_lizhi
 * @since 1.0
 *
 */
public class MesanEvaluate extends DataEntity<MesanEvaluate>{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String text;//评论
	private String userId;//用户id
	private String mesanId;//资料id
	private User user;

	public User getUser() {
		return user;
	}
	public void setUser(User user) {
		this.user = user;
	}
	public String getText() {
		return text;
	}
	public void setText(String text) {
		this.text = text;
	}
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	public String getMesanId() {
		return mesanId;
	}
	public void setMesanId(String mesanId) {
		this.mesanId = mesanId;
	}
}
