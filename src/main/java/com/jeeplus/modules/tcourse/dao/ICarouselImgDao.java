package com.jeeplus.modules.tcourse.dao;

import com.jeeplus.common.persistence.annotation.MyBatisDao;
import com.jeeplus.modules.sys.entity.CarouselImg;
import com.yfhl.commons.domain.QueryScope;
import com.yfhl.commons.persistence.BaseDao;

/**
 * 轮播图
 * @author Panjp
 *
 */
@MyBatisDao
public interface ICarouselImgDao extends BaseDao<CarouselImg, QueryScope<CarouselImg>>{

	
}
