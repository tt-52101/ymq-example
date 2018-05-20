package io.ymq.example;

import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.usermodel.HSSFHeader;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xssf.streaming.SXSSFWorkbook;
import org.apache.poi.xssf.usermodel.XSSFRichTextString;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * <p>Excel操作</p>
 * <p></p>
 * @author 刘习 【Liuxi】
 * @date 2015-8-19
 * @version 1.0.1
 * @since JDK 1.6
 */
public class ExcelUtil {
	public static final int XLS_MAX_COL_NUM = 255;		//XLS工作薄单表格列数最大值
	public static final int XLS_MAX_ROW_NUM = 60000;	//XLS工作薄单表格行数最大值
	public static final int XLS_MAX_PAGE_NUM = 255;		//XLS工作薄表格数最大值
	/**
	 * 创建XLS类型Workbook
	 * @param describe
	 * 		表格描述信息，存在则置于表头上方，即第一行，表头置于第二行
	 * @param columns
	 * 		表头[列标]数组
	 * @param dataMapList
	 * 		Map<key,value>类型待写入workbook的数据List
	 * @param keys
	 * 		Map<key,value>数据与columns一一对应 的key数组
	 * @param autoRownum
	 * 		0:不自动添加序号，1：自动添加序号，默认：0
	 * @return
	 */
	public static Workbook createXLSWorkbook(String describe,String[] columns,List<Map<String,Object>> dataMapList,String[] keys,int autoRownum){
		//最大列数限制
		if(columns != null && columns.length > XLS_MAX_COL_NUM)	return null;	
		//最大行数限制
		if(dataMapList != null && dataMapList.size() > XLS_MAX_ROW_NUM* XLS_MAX_PAGE_NUM)return null;
		
		Workbook workbook = new SXSSFWorkbook(100);
		int page = 1;	//首个页面
		Sheet sheet = ExcelUtil.createSheet(workbook,describe, columns, page);
		
		//保证序号列存在【默认第一列，即columns[0]代表序号】
		boolean auto = autoRownum==1 && columns.length - keys.length == 1;
		Row row = null;
		//单元格数据填充
		if(dataMapList != null){
			//exist_row：当前已存在行数，
			//	2表示表格描述信息与表头存在，
			//	1表格描述信息不存在，但表头存在，
			//	0表示表格描述信息与表头都不存在
			int exist_row = 2; 
			if(describe == null || "".equals(describe)){
				exist_row --; 
			}
			if(columns == null || columns.length < 1){
				exist_row --; 
			}
			int now_row = exist_row;
			
			//单元格默认居中左对齐
			CellStyle cellStyle = ExcelUtil.createBodyStyle(workbook, sheet);
			for(Map<String,Object> data : dataMapList){
				row = sheet.createRow(now_row++);
				if(auto){	//自动序号=当前行数 
					Cell cell = row.createCell(0);
					cellStyle.setAlignment(HSSFCellStyle.ALIGN_RIGHT);//<序号列右对齐>
					cell.setCellStyle(cellStyle);
					cell.setCellValue(new XSSFRichTextString((now_row-exist_row)+""));
				}else{
					autoRownum = 0;
				}
				cellStyle.setAlignment(HSSFCellStyle.ALIGN_LEFT);//<内容列左对齐>
				for(int i = 0; i < keys.length; i++){
					Cell cell = row.createCell(i+autoRownum);
					cell.setCellStyle(cellStyle);
					cell.setCellValue(new XSSFRichTextString(data.get(keys[i]).toString()));
				}
				if (now_row % XLS_MAX_ROW_NUM == 0) {
					page++;
					sheet = createSheet(workbook,describe, columns,page);
					now_row = 0;
				}
			}
		}
		return workbook;
	}
	
	/**
	 * 创建XLS类型Workbook
	 * 
	 * @param columns
	 * 		表头[列标]数组
	 * @param dataMapList
	 * 		Map<key,value>类型待写入workbook的数据List
	 * @param keys
	 * 		Map<key,value>数据与columns一一对应 的key数组
	 * @param autoRownum
	 * 		0:不自动添加序号，1：自动添加序号，默认：0
	 * @return
	 */
	public static Workbook createXLSWorkbook(String[] columns,List<Map<String,Object>> dataMapList,String[] keys,int autoRownum){
		return ExcelUtil.createXLSWorkbook(null, columns, dataMapList, keys,1);
	}
	/**
	 * 创建sheet（带表头）
	 * @param workbook
	 * 			Excel workbook
	 * @param columns
	 * 			表头
	 * @param page
	 * 			页数
	 * @return
	 */
	public static  Sheet createSheet(Workbook workbook,String describe,String[] columns,int page){
		Sheet sheet = workbook.createSheet("sheet-"+page);
		// 设置sheet的标题  
        Header header = sheet.getHeader();  
        header.setCenter("Center Header");  
        header.setLeft("Left Header");  
        header.setRight(HSSFHeader.font("Stencil-Normal", "Italic")  
                + HSSFHeader.fontSize((short) 16)  
                + "Right w/ Stencil-Normal Italic font and size 16");  
        
        CellStyle cellStyle = null;
        
        Row row = sheet.createRow(0);
        int now_rom = 0;
		if(!(describe == null || "".equals(describe))){
			//表格描述信息存在
			sheet.addMergedRegion(new CellRangeAddress(0,0,0,columns.length-1));//对象的构造方法需要传入合并单元格的首行、最后一行、首列、最后一列
			Cell cell = row.createCell(now_rom);
			cell.setCellValue(new XSSFRichTextString(describe));
			cell.setCellStyle(createHeadStyle( workbook, sheet));
			now_rom++;
		}
		row = sheet.createRow(now_rom++);
		//设置列标
		cellStyle = createTitleStyle( workbook, sheet);
		for(int i = 0; i < columns.length; i++){
			Cell cell = row.createCell(i);
			cell.setCellValue(new XSSFRichTextString(columns[i]));
			cell.setCellStyle(cellStyle);
		}
		// 设置冻结
		//sheet.createFreezePane(0, 1, 1, 1);
		sheet.createFreezePane(1,now_rom,1,now_rom);
		return sheet;
	}
	
	/**
	 *设置表头描述样式
	 * @param workbook
	 * @param sheet
	 * @return
	 */
	public static CellStyle createHeadStyle(Workbook workbook,Sheet sheet){
        //生成一个字体
	    Font font = workbook.createFont();
	    font.setBoldweight(HSSFFont.BOLDWEIGHT_NORMAL); //正常
	    //font.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);	//加粗
	    font.setFontHeightInPoints((short) 12);			//大小
	    
        CellStyle cellStyle = workbook.createCellStyle();		
        
		cellStyle.setAlignment(HSSFCellStyle.ALIGN_LEFT);				//排列居左
        //cellStyle.setAlignment(HSSFCellStyle.ALIGN_CENTER);				//排列居中
	    cellStyle.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER); 	//垂直居中
	    cellStyle.setFont(font);					// 把字体应用到当前的样式
	    
	    cellStyle.setBorderLeft(HSSFCellStyle.BORDER_THIN);		//左边框
		cellStyle.setBorderRight(HSSFCellStyle.BORDER_THIN);	//右边框
		cellStyle.setBorderTop(HSSFCellStyle.BORDER_THIN);		//上边框
		cellStyle.setBorderBottom(HSSFCellStyle.BORDER_THIN);	//下边框
		
		return cellStyle;
	}
	
	/**
	 *设置表列标题样式
	 * @param workbook
	 * @param sheet
	 * @return
	 */
	public static CellStyle createTitleStyle(Workbook workbook,Sheet sheet){
        //生成一个字体
	    Font font = workbook.createFont();
	    font.setBoldweight(HSSFFont.BOLDWEIGHT_NORMAL); //正常
	    //font.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);	//加粗
	    font.setFontHeightInPoints((short) 12);			//大小
	    
        CellStyle cellStyle = workbook.createCellStyle();		
        
        cellStyle.setAlignment(HSSFCellStyle.ALIGN_CENTER);				//排列居中
	    cellStyle.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER); 	//垂直居中
	    cellStyle.setFont(font);					// 把字体应用到当前的样式
	    
	    cellStyle.setBorderLeft(HSSFCellStyle.BORDER_THIN);		//左边框
		cellStyle.setBorderRight(HSSFCellStyle.BORDER_THIN);	//右边框
		cellStyle.setBorderTop(HSSFCellStyle.BORDER_THIN);		//上边框
		cellStyle.setBorderBottom(HSSFCellStyle.BORDER_THIN);	//下边框
		  
		/* 自动调整宽度 */  
		for (int i = 0; i < 6; i++) {  
			sheet.autoSizeColumn(i,true);  
		}  
		int curColWidth = 0;  //实际宽度
		 
		/* 默认宽度 */  
		int[] defaultColWidth = { 2000, 2000, 3000, 6000, 3000, 5000 };  
		/* 实际宽度 < 默认宽度的时候、设置为默认宽度 */  
		for (int i = 0; i < 6; i++) {  
			curColWidth = sheet.getColumnWidth(i);  
		    if (curColWidth < defaultColWidth[i]) {  
		    	sheet.setColumnWidth(i, defaultColWidth[i]);  
		    }  
		}
		return cellStyle;
	}
	
	
	/**
	 * 设置表体样式
	 * @param workbook
	 * @param sheet
	 * @return
	 */
	public static CellStyle createBodyStyle(Workbook workbook,Sheet sheet){

        CellStyle cellStyle = workbook.createCellStyle();		
        
		cellStyle.setAlignment(HSSFCellStyle.ALIGN_LEFT);				//排列居左
        //cellStyle.setAlignment(HSSFCellStyle.ALIGN_CENTER);			//排列居中
	    cellStyle.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER); 	//垂直居中
	    
	    cellStyle.setBorderLeft(HSSFCellStyle.BORDER_THIN);		//左边框
		cellStyle.setBorderRight(HSSFCellStyle.BORDER_THIN);	//右边框
		cellStyle.setBorderTop(HSSFCellStyle.BORDER_THIN);		//上边框
		cellStyle.setBorderBottom(HSSFCellStyle.BORDER_THIN);	//下边框
		
		return cellStyle;
	}
	
	/**
	 * @param args
	 */
	public static void main(String[] args){
		String discribe = "文件名";
		String[] cols = new String[]{"K","A","B","C"};
		String[] keys = new String[]{"A","B","C"};
		List<Map<String,Object>> dataMapList = new ArrayList<Map<String,Object>>();
		for(int i = 0 ; i < 10; i++){
			HashMap<String, Object> map = new HashMap<String, Object>();
			for(int j = 0; j < keys.length;j++){
				map.put(keys[j], i);
			}
			dataMapList.add(map);
		}
		Workbook workbook = ExcelUtil.createXLSWorkbook(discribe,cols, dataMapList, keys,1);
		if(workbook != null){
	        FileOutputStream out = null;
	        try {
				String fileName =  "[XLSWorkbook]"+discribe+ System.currentTimeMillis() + ".xlsx";
				fileName = new String(fileName.getBytes("UTF8"), "UTF-8");
	        	out = new FileOutputStream(new File("G:/"+fileName));
	        	workbook.write(out);
		    } catch (IOException e) {
		        e.printStackTrace();
		    }finally {
		    	try {
		    		if(out != null){
		    			out.close();
		            }
		    	} catch (IOException e) {
		    		e.printStackTrace();
		    	}
		    }
		}
		System.out.println("sucess");
	}
}