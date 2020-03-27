package com.jeeplus.modules.train.service;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.jeeplus.modules.train.dao.SearchTempDao;
@Service
@Transactional(readOnly = true)
public class SearchTempService {
	@Autowired
	private SearchTempDao searchTempDao;

	public SearchTempDao getSearchTempDao() {
		return searchTempDao;
	}

	public void setSearchTempDao(SearchTempDao searchTempDao) {
		this.searchTempDao = searchTempDao;
	}
	@Transactional(readOnly = false)
	public void save(List<Map> maps) {
		searchTempDao.insert(maps);
	}
	@Transactional(readOnly = false)
	public void delete() {
		searchTempDao.delete();
	}
}
