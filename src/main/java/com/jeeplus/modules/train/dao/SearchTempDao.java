package com.jeeplus.modules.train.dao;

import java.util.List;
import java.util.Map;

import com.jeeplus.common.persistence.annotation.MyBatisDao;
@MyBatisDao
public interface SearchTempDao {
	//插入数据到中间表
	int insert(List<Map> maps);
	//删除临时表的数据
	int delete();
}
