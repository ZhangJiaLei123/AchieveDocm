package com.jeeplus.modules.tcourse.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.jeeplus.common.persistence.annotation.MyBatisDao;
import com.jeeplus.modules.sys.entity.CarouselImg;
import com.yfhl.commons.domain.QueryScope;
import com.yfhl.commons.persistence.BaseDao;

@MyBatisDao
public interface IStudyActivityDao extends BaseDao<CarouselImg, QueryScope<CarouselImg>> {
	public List<Map> findStudyActivit(QueryScope<Map> qs);
	
	public Map findTrainProgramById(String id);
	
	public List<Map> findProgramActivityByProgramId(@Param("trainProgramId")String trainProgramId);
}
