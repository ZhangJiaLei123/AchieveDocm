package com.jeeplus.modules.tcourse.service;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jeeplus.modules.course.entity.CourseInfo;
import com.jeeplus.modules.sys.entity.CarouselImg;
import com.jeeplus.modules.tcourse.dao.ICarouselImgDao;
import com.yfhl.commons.domain.QueryScope;

/***
 * 首页Service
 * @author Panjp
 *
 */
@Service
@Transactional(readOnly=true)
public class IndexService{
	private final Logger logger = LoggerFactory.getLogger(this.getClass());
	
	@Autowired
	private ICarouselImgDao iCarouselImgDao;
	
	public List<CarouselImg> findCaruseImg(CarouselImg carouselImg){
		QueryScope<CarouselImg> qs = new QueryScope<CarouselImg>();
		qs.setBizEntity(carouselImg);
		logger.debug("==================");
		return iCarouselImgDao.findList(qs);
	}
	public List<CourseInfo> findNewCourseInfo(CourseInfo courseInfo) {
		// TODO Auto-generated method stub
		return null;
	}
	
}
