/**
 * Copyright &copy; 2015-2020 <a href="http://www.nxpt.com/">JeePlus</a> All rights reserved.
 */
package com.jeeplus.modules.sys.service;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.alibaba.fastjson.JSONObject;
import com.jeeplus.common.persistence.Page;
import com.jeeplus.common.service.CrudService;
import com.jeeplus.modules.sys.dao.CarouselImgDao;
import com.jeeplus.modules.sys.entity.CarouselImg;

/**
 * 轮播图Service
 * @author panjp
 * @version 2017-03-26
 */
@Service
@Transactional(readOnly = true)
public class CarouselImgService extends CrudService<CarouselImgDao, CarouselImg> {
	
	@Autowired
	private CarouselImgDao carouselImgDao;
	
	public CarouselImg get(String id) {
		return super.get(id);
	}
	
	public List<CarouselImg> findList(CarouselImg carouselImg) {
		return super.findList(carouselImg);
	}
	
	public Page<CarouselImg> findPage(Page<CarouselImg> page, CarouselImg carouselImg) {
		return super.findPage(page, carouselImg);
	}
	
	@Transactional(readOnly = false)
	public void save(CarouselImg carouselImg) {
		super.save(carouselImg);
	}
	
	@Transactional(readOnly = false)
	public void delete(CarouselImg carouselImg) {
		super.delete(carouselImg);
	}
	
	public String checkTitle(String title){
		try {
			JSONObject jsonObject = new JSONObject();
			jsonObject.put("title", title);
			Map<String, Object> checkTitle = carouselImgDao.checkTitle(jsonObject);
			if (checkTitle == null) {
				return "true";
			}
		} catch (Exception e) {
			// TODO: handle exception
		}
		return "false";
	}
	
	
}