/**
 * Copyright &copy; 2015-2020 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.jeeplus.modules.resourcedownload.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jeeplus.common.persistence.Page;
import com.jeeplus.common.service.CrudService;
import com.jeeplus.modules.resourcedownload.dao.TResourceDownloadDao;
import com.jeeplus.modules.resourcedownload.entity.TResourceDownload;

/**
 * 资源下载Service
 * @author pxw
 * @version 2017-12-31
 */
@Service
@Transactional(readOnly = false)
public class TResourceDownloadService extends CrudService<TResourceDownloadDao, TResourceDownload> {

	public TResourceDownload get(String id) {
		return super.get(id);
	}
	
	public List<TResourceDownload> findList(TResourceDownload tResourceDownload) {
		return super.findList(tResourceDownload);
	}
	
	public Page<TResourceDownload> findPage(Page<TResourceDownload> page, TResourceDownload tResourceDownload) {
		return super.findPage(page, tResourceDownload);
	}
	
	@Transactional(readOnly = false)
	public void save(TResourceDownload tResourceDownload) {
		super.save(tResourceDownload);
	}
	
	@Transactional(readOnly = false)
	public void delete(TResourceDownload tResourceDownload) {
		super.delete(tResourceDownload);
	}
	
	
	
	
}