/**
 * Copyright &copy; 2015-2020 <a href="http://www.nxpt.com/">JeePlus</a> All rights reserved.
 */
package com.jeeplus.modules.resource.service;
import java.util.List;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.jeeplus.common.persistence.Page;
import com.jeeplus.common.service.CrudService;
import com.jeeplus.modules.resource.dao.MesanDirDao;
import com.jeeplus.modules.resource.entity.MesanDir;

/**
 * 资源目录Service
 * @author panjp
 * @version 2017-03-19
 */
@Service
@Transactional(readOnly = true)
public class MesanDirService extends CrudService<MesanDirDao, MesanDir> {

	public MesanDir get(String id) {
		return super.get(id);
	}
	
	public List<MesanDir> findList(MesanDir mesanDir) {
		return super.findList(mesanDir);
	}
	
	public Page<MesanDir> findPage(Page<MesanDir> page, MesanDir mesanDir) {
		return super.findPage(page, mesanDir);
	}
	
	@Transactional(readOnly = false)
	public void save(MesanDir mesanDir) {
		super.save(mesanDir);
	}
	
	@Transactional(readOnly = false)
	public void delete(MesanDir mesanDir) {
		super.delete(mesanDir);
	}
	
	
	
	
}