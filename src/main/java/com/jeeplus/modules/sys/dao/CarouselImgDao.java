/**
 * Copyright &copy; 2015-2020 <a href="http://www.nxpt.com/">JeePlus</a> All rights reserved.
 */
package com.jeeplus.modules.sys.dao;

import java.util.Map;

import com.alibaba.fastjson.JSONObject;
import com.jeeplus.common.persistence.CrudDao;
import com.jeeplus.common.persistence.annotation.MyBatisDao;
import com.jeeplus.modules.sys.entity.CarouselImg;

/**
 * 轮播图DAO接口
 * @author panjp
 * @version 2017-03-26
 */
@MyBatisDao
public interface CarouselImgDao extends CrudDao<CarouselImg> {
	
	Map<String, Object> checkTitle(JSONObject object);
	
}