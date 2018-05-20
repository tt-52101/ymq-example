package io.ymq.example;

import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.usermodel.HSSFHeader;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xssf.streaming.SXSSFWorkbook;
import org.apache.poi.xssf.usermodel.XSSFRichTextString;

import java.util.LinkedList;
import java.util.List;
import java.util.Map;

/**
 * <p>Excel</p>
 * <p>根据数据创建Excel Workbook.</p>
 * @author 刘习 【Liuxi】
 * @date 2015-9-25
 * @version 1.0.1
 * @since JDK 1.6
 */
public class Excel implements Runnable{
	/**
	 * 线程休眠时间【毫秒】
	 */
	public static long THREAD_SLEEP_SSS = 20;
	
	public static final int MAX_COL_NUM = 255;		//XLSX每一sheet中列数最大值
	public static final int MAX_ROW_NUM = 1000;		//XLSX每一sheet中行数最大值
	public static final int MAX_SHEET_NUM = 10;		//XLSX中sheet数最大值
	
	public static final int MAX_ROWS = 10000;			//XLSX中数据记录条数最大值=MAX_ROW_NUM*MAX_SHEET_NUM
	
	/**
	 * 表头,不为空时放在表中sheet的第一行，描述页面数据
	 */
	private String title; 
	
	/**
	 * 列标识，表头不为空时放在表中sheet的第二行，否则第一行
	 */
	private Map<String,String> columns; //LinkedHashMap
	
	/**
	 * 表格中要存放的数据，表头不为空时放在表中sheet的第二行，否则第一行
	 * 注意：数据map中的,key为ROW_NUM或者带_FILTER结尾的，导出EXCEL时会被过滤，即不会导出到EXCEL中
	 */
	private List<Map<String,Object>> dataList = new LinkedList<Map<String,Object>>();
	
	/**
	 * 是否自动添加序号
	 */
	private boolean autoRowNum = false;
	
	
	/**
	 * Excel workbook
	 */
	private Workbook workbook = null;
	
	/**
	 * 标识
	 */
	private String sign = "";

	/**
	 * run是否执行
	 */
	private boolean started = false;
	
	/**
	 * run是否结束
	 */
	private boolean terminated = false;
	
	public Excel(String title,Map<String,String> columns,List<Map<String,Object>> dataList){
		this.title = title;
		this.columns = columns;
		this.dataList = dataList;
		this.autoRowNum = true;
	}
	
	public Excel(String title,Map<String,String> columns,List<Map<String,Object>> dataList,boolean autoRowNum){
		this.title = title;
		this.columns = columns;
		this.dataList = dataList;
		this.autoRowNum = autoRowNum;
	}
	
	@Override
	public void run() {
		create();
	}
	
	public void create(){
		started = true;
		//The max row limit
		if(dataList == null || dataList.size() > MAX_ROWS){
			return;
		}
		//The max column limit
		if(columns == null || columns.size() > MAX_COL_NUM){
			return;
		}
		workbook = new SXSSFWorkbook(100);
		sign = System.currentTimeMillis()+"";
				
		//exist_row：当前已存在行数，
		//	2表示表格描述信息与表头存在，
		//	1表格描述信息不存在，但表头存在，
		//	0表示表格描述信息与表头都不存在
		int exist_row = 2; 
		if(title == null || "".equals(title)){
			exist_row --; 
		}
		if(columns == null || columns.size() < 1){
			exist_row --; 
		}
		int page = dataList.size()%MAX_ROW_NUM==0 ? dataList.size()/MAX_ROW_NUM : dataList.size()/MAX_ROW_NUM+1;
		for(int i = 0;i < page;i++) {
			Sheet sheet = createSheet(title, columns, i+1);
			int fromIndex = i*MAX_ROW_NUM;
			int toIndex = (i+1)*MAX_ROW_NUM;
			if(toIndex > dataList.size()){
				toIndex = dataList.size();
			}

			//单元格数据填充
			int now_row = exist_row;
			//保证序号列存在【默认第一列，即columns[0]代表序号】
			Row row = null;
			//单元格默认居中左对齐
			CellStyle cellStyle = createColumnStyle();
			for(Map<String,Object> data : dataList.subList(fromIndex, toIndex)){
				row = sheet.createRow(now_row++);
				int now_column = 0;
				if(autoRowNum){	//自动序号=当前行数 
					Cell cell = row.createCell(now_column++);
					cellStyle.setAlignment(HSSFCellStyle.ALIGN_RIGHT);//<序号列右对齐>
					cell.setCellStyle(cellStyle);
					cell.setCellValue(new XSSFRichTextString((now_row-exist_row)+""));
				}
				cellStyle.setAlignment(HSSFCellStyle.ALIGN_LEFT);//<内容列左对齐>
				for(String key : data.keySet()){
					if(!("ROW_NUM".equals(key) || key.endsWith("_FILTER"))){ //过滤
						Cell cell = row.createCell(now_column++);
						cell.setCellStyle(cellStyle);
						cell.setCellValue(new XSSFRichTextString(data.get(key).toString()));
					}
				}
			}
			try {
				Thread.sleep(THREAD_SLEEP_SSS);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		terminated = true;
	}

	/**
	 * 创建sheet（带表头）
	 * @param columns
	 * 			表头
	 * @param page
	 * 			页数
	 * @return
	 */
	public Sheet createSheet(String title,Map<String,String> columns,int page){
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
        int now_row = 0;
		if(!(title == null || "".equals(title))){
			//表格描述信息存在
			sheet.addMergedRegion(new CellRangeAddress(0,0,0,columns.size()-1));//对象的构造方法需要传入合并单元格的首行、最后一行、首列、最后一列
			Cell cell = row.createCell(now_row);
			cell.setCellValue(new XSSFRichTextString(title));
			cell.setCellStyle(createTitleStyle());
			now_row++;
		}
		row = sheet.createRow(now_row++);
		//设置列标
		cellStyle = createTitleStyle();
		int now_col = 0;
		for(String key : columns.keySet()) {
			Cell cell = row.createCell(now_col);
			cell.setCellValue(new XSSFRichTextString(columns.get(key)));
			cell.setCellStyle(cellStyle);
			now_col++;
		}
		
		/* 自动调整宽度 */  
//		for (int i = 0; i < sheet.getRow(0).getPhysicalNumberOfCells() ; i++) {  
//			sheet.autoSizeColumn(i,true);  
//		}  
		
		// 设置冻结
		//sheet.createFreezePane(0, 1, 1, 1);
		sheet.createFreezePane(1,now_row,1,now_row);
		return sheet;
	}
	
	/**
	 * 设置表头描述样式
	 * @return
	 */
	public CellStyle createTitleStyle(){
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
	 * 设置表体样式
	 * @return
	 */
	public CellStyle createColumnStyle(){

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

	
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}

	public Map<String, String> getColumns() {
		return columns;
	}
	public void setColumns(Map<String, String> columns) {
		this.columns = columns;
	}

	public List<Map<String, Object>> getDataList() {
		return dataList;
	}
	public void setDataList(List<Map<String, Object>> dataList) {
		this.dataList = dataList;
	}

	public boolean isAutoRowNum() {
		return autoRowNum;
	}
	public void setAutoRowNum(boolean autoRowNum) {
		this.autoRowNum = autoRowNum;
	}

	public Workbook getWorkbook() {
		return workbook;
	}
	public void setWorkbook(Workbook workbook) {
		this.workbook = workbook;
	}

	public boolean isTerminated() {
		return terminated;
	}
	public void setTerminated(boolean terminated) {
		this.terminated = terminated;
	}
	
	/**
	 * @param args
	 */
	public static void main(String[] args) {

	}

	public boolean isStarted() {
		return started;
	}

	public void setStarted(boolean started) {
		this.started = started;
	}
	
	public String getSign() {
		return sign;
	}

	public void setSign(String sign) {
		this.sign = sign;
	}
}
