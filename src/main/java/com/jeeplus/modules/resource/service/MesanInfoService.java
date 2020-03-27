/**
 * Copyright &copy; 2015-2020 <a href="http://www.nxpt.com/">JeePlus</a> All rights reserved.
 */
package com.jeeplus.modules.resource.service;

import java.io.UnsupportedEncodingException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jeeplus.common.persistence.Page;
import com.jeeplus.common.service.CrudService;
import com.jeeplus.modules.resource.entity.MesanInfo;
import com.jeeplus.modules.resource.dao.MesanInfoDao;
import com.jeeplus.modules.sys.entity.User;
import com.jeeplus.modules.sys.utils.FileUtils;

/**
 * 资料信息Service
 * @author panjp
 * @version 2017-03-20
 */
@Service
@Transactional(readOnly = true)
public class MesanInfoService extends CrudService<MesanInfoDao, MesanInfo> {
	@Autowired
	MesanInfoDao mesanInfoDao;
	public MesanInfo get(String id) {
		return super.get(id);
	}
	
	public List<MesanInfo> findList(MesanInfo mesanInfo) {
		return super.findList(mesanInfo);
	}
	
	public Page<MesanInfo> findPage(Page<MesanInfo> page, MesanInfo mesanInfo) {
		//BaseService.dataScopeFileForOffice(mesanInfo);
		return super.findPage(page, mesanInfo);
	}
	public Page<MesanInfo> statisMesanList(Page<MesanInfo> page, MesanInfo mesanInfo) {
		mesanInfo.setPage(page);
		page.setList(mesanInfoDao.statisMesanList(mesanInfo));
		return page;
	}
	
	public Page<MesanInfo> findSelMesanInfoListPage(Page<MesanInfo> page, MesanInfo mesanInfo) {
		//BaseService.dataScopeFileForOffice(mesanInfo);
		mesanInfo.setPage(page);
		page.setList(mesanInfoDao.findSelMesanInfoListPage(mesanInfo));
		return page;
	}
	public Page<MesanInfo> mesanStudyTime(Page<MesanInfo> page, MesanInfo mesanInfo) {
		//BaseService.dataScopeFileForOffice(mesanInfo);
		mesanInfo.setPage(page);
		page.setList(mesanInfoDao.mesanStudyTime(mesanInfo));
		return page;
	}
	
	public Page<MesanInfo> findMyMesanInfoPage(Page<MesanInfo> page, MesanInfo mesanInfo) {
		mesanInfo.setPage(page);
		page.setList(mesanInfoDao.findMyMesanInfoPage(mesanInfo));
		return page;
	}
	public Page<User> findChanYuQingKuangUser(Page<User> page, User user) {
		user.setPage(page);
		page.setList(mesanInfoDao.findChanYuQingKuangUser(user));
		return page;
	}
	
	@Transactional(readOnly = false)
	public void save(MesanInfo mesanInfo) {
		String url =  mesanInfo.getMesanUrl();
		if(null!=url && !"".equals(url.trim())){
			String fileName = url.substring(url.lastIndexOf(".")+1,url.length()).toUpperCase();
			if("PDF".equals(fileName) || "PPT".equals(fileName) || "DOC".equals(fileName) ||"DOCX".equals(fileName) ||"PPTX".equals(fileName)){//如果是可以在线预览文件则生成预览文件
				String swfStr = mesanInfo.getSwfUrl();
				if(null==swfStr || "".equals(swfStr.trim())){//如果未生成预览文件则生成
					try {
						swfStr = FileUtils.fileToSwf(mesanInfo.getMesanUrl());
						//生成成功后更新文件
						mesanInfo.setSwfUrl(swfStr);
					} catch (UnsupportedEncodingException e) {
						e.printStackTrace();
					}
				}
			}
		}
		super.save(mesanInfo);
	}
	@Transactional(readOnly = false)
	public void setViewTop(MesanInfo mesanInfo) {
		mesanInfoDao.setViewTop(mesanInfo);
	}
	@Transactional(readOnly = false)
	public void delete(MesanInfo mesanInfo) {
		super.delete(mesanInfo);
	}

	public MesanInfo getByFileName(MesanInfo mesanInfo) {
		return mesanInfoDao.getByFileName(mesanInfo);
	}
	
	
}