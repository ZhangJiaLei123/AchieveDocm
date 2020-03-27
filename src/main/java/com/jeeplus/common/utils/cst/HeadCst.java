package com.jeeplus.common.utils.cst;

import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;

import scala.annotation.bridge;

import com.google.common.collect.Lists;
import com.jeeplus.common.utils.excel.ExportExcel;
import com.jeeplus.common.utils.excel.ImportExcel;
import com.jeeplus.common.utils.excel.annotation.ExcelField;
import com.jeeplus.modules.sys.entity.District;

/***
 * 校验表头用
 * @author Administrator
 *
 */
public class HeadCst {
	public static final String LOGIN_NAME = "登录名";
	public static final String NAME = "姓名";
	/***
	 * 验证表头是否正确(验证的是不通过，所以值为否）
	  * Company:     jcby
	  * @version:    1.0   
	  * @since:  JDK 1.7.0_76
	  * user  ygq
	  * Create at:  2017年5月16日 下午4:38:54
	  * @param ei
	  * @return
	 */
	public static  boolean headValid(ImportExcel ei){
		boolean va = true;
		Row row = ei.getRow(1);
		String loginValid = "";
		Cell cell = row.getCell(0);
		if(null != cell){
			loginValid = cell.getStringCellValue();
		}
		String name = "";
		Cell cell1 = row.getCell(1);
		if(null != cell1){
			name =  cell1.getStringCellValue();
		}
		if(HeadCst.LOGIN_NAME.equals(loginValid)&& HeadCst.NAME.equals(name)){
			va = false;
		}
		return va;
	}
	/***
	 * 验证表头通用方法
	 * @param ei 导入Excel
	 * @param cls 导入的实体Class
	 * @return true 验证通过，false 验证失败
	 */
	public static  boolean headValid(ImportExcel ei, Class<?> cls){
		ExportExcel excle = new ExportExcel("表头", cls, 1);
		List<Object[]> annotationList = excle.annotationList;
		// Initialize
		List<String> headerList = Lists.newArrayList();
		for (Object[] os : annotationList){
			String t = ((ExcelField)os[0]).title();
			// 如果是导出，则去掉注释
				String[] ss = StringUtils.split(t, "**", 2);
				if (ss.length==2){
					t = ss[0];
			}
			headerList.add(t);
		}
		boolean bl = true;
		Row row = ei.getRow(1);
		for (int i = 0; i < headerList.size(); i++) {
			String headCell = headerList.get(i);
			String cell = row.getCell(i).getStringCellValue();
			if(!headCell.equals(cell.trim())){
				bl = false;
				break;
			}
		}
		return bl;
	}
	
}
