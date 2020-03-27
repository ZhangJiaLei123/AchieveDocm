package com.jeeplus.modules.resource.dao;

import com.jeeplus.common.persistence.CrudDao;
import com.jeeplus.common.persistence.annotation.MyBatisDao;
import com.jeeplus.modules.resource.entity.MesanMark;

@MyBatisDao
public interface MesanMarkDao extends CrudDao<MesanMark> {
	public MesanMark getMesanMarkByMesanIdAndUserId(MesanMark mesanMark);
	
	public Double getAvgMarkByMesanId(String mesanId);
}
