package io.ymq.example;

import org.apache.poi.ss.usermodel.Workbook;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.*;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

/**
 * <p>Excel导出工具类[Multi Thread]</p>
 * <p>
 * 使用步骤:
 * 	--1. 创建ExcelExporter对象 [x]后即会自动组合生成Excel
 * 	--2. 可以从queue中获取以生成的Excel[如需打包，可以先调用x.writeToTempFile生成临时文件,后x.deleteTempFile删除文件]
 * </p>
 * @author 【Liuxi】
 * @date 2015-8-28
 * @version 1.0.1
 * @since JDK 1.6
 */
public class ExcelExporter {
	/**
	 * 当前类文件的上层目录
	 */
	public static final String ROOT_DIR = new File(ExcelExporter.class.getResource("/").getPath()).getParent()+File.separator;;
	/**
	 * 临时文件保存位置
	 */
	public static final String TEMP_DIR = "/tmp/";
	
	/**
	 * Excel文件格式
	 */
	public static final String XLSX_EXT = ".xlsx";
	
	/**
	 * 生成Excel的最大线程数
	 */
	public static int MAX_THREAD_SIZE = 10;

	/**
	 * 线程休眠时间【毫秒】
	 */
	public static long THREAD_SLEEP_SSS = 10;
	
	/**
	 * 生成Excel是否结束
	 */
	private boolean finished = false;
	
	/**
	 * Excel线程队列
	 */
	private BlockingQueue<Excel> queue = new LinkedBlockingQueue<Excel>();
	
	/**
	 * 生成Excel【自动添加序号】
	 * @param title
	 * 		表头
	 * @param columns
	 * 		列标识
	 * @param dataList
	 * 		表格中要存放的数据
	 */
	public ExcelExporter(String title,Map<String,String> columns,List<Map<String,Object>> dataList){
		if(dataList != null){
			int workbook_num = dataList.size()%Excel.MAX_ROWS==0 ? (dataList.size()/Excel.MAX_ROWS) : (dataList.size()/Excel.MAX_ROWS+1);
			for(int i = 0;i < workbook_num;i++) {
				int fromIndex = i*Excel.MAX_ROWS;
				int toIndex = (i+1)*Excel.MAX_ROWS;
				if(toIndex > dataList.size()){
					toIndex = dataList.size();
				}
				Excel excel= new Excel(title, columns, dataList.subList(fromIndex, toIndex));
				queue.add(excel);
				try {
					Thread.sleep(THREAD_SLEEP_SSS);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
			start();
		}
	}
	
	/**
	 * 生成Excel
	 * @param title
	 * 		表头
	 * @param columns
	 * 		列标识
	 * @param dataList
	 * 		表格中要存放的数据
	 * @param autoRowNum
	 * 		是否自动添加序号
	 */
	public ExcelExporter(String title,Map<String,String> columns,List<Map<String,Object>> dataList,boolean autoRowNum){
		if(dataList != null){
			int workbook_num = dataList.size()%Excel.MAX_ROWS==0 ? (dataList.size()/Excel.MAX_ROWS) : (dataList.size()/Excel.MAX_ROWS+1);
			for(int i = 0;i < workbook_num;i++) {
				int fromIndex = i*Excel.MAX_ROWS;
				int toIndex = (i+1)*Excel.MAX_ROWS;
				if(toIndex > dataList.size()){
					toIndex = dataList.size();
				}
				Excel excel= new Excel(title, columns, dataList.subList(fromIndex, toIndex),autoRowNum);
				this.queue.add(excel);
				try {
					Thread.sleep(THREAD_SLEEP_SSS);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
			start();
		}
	}
	
	public void start(){
		if( queue != null && queue.size() > 0){
			BlockingQueue<Excel> runningQueue = new LinkedBlockingQueue<Excel>();		
			BlockingQueue<Excel> waittingQueue = new LinkedBlockingQueue<Excel>();		
			BlockingQueue<Excel> terminatedQueue = new LinkedBlockingQueue<Excel>();

			if( MAX_THREAD_SIZE > 0 && queue.size() > MAX_THREAD_SIZE){
				for(int i = 0;i<MAX_THREAD_SIZE;i++){
					runningQueue.add((Excel) queue.toArray()[i]);
				}
				for(int i = MAX_THREAD_SIZE;i<queue.size();i++){
					waittingQueue.add((Excel) queue.toArray()[i]);
				}
			}else{
				runningQueue.addAll(queue);
				waittingQueue = null;
			}
						
			for(Excel e : runningQueue) {
				new Thread(e).start();
			}
			while(terminatedQueue.size() < queue.size()){
				try {
					Thread.sleep(THREAD_SLEEP_SSS);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				for(Iterator<Excel> it = runningQueue.iterator();it.hasNext();){
					Excel excel = it.next();
					if(excel.isTerminated()){
						terminatedQueue.add(excel);
						runningQueue.remove(excel);
						
						if(waittingQueue != null && waittingQueue.size() > 0){
							runningQueue.add(waittingQueue.poll());
						}
					}else{
						if(!excel.isStarted()){
							new Thread(excel).start();
						}
					}
					if(runningQueue == null || runningQueue.size() <= 0){
						break;
					}
				}
			}

			queue.clear();
			queue.addAll(terminatedQueue);

			this.setFinished(true);
		}
	}
	
	
	/**
	 * 将生成的文件写入临时文件夹
	 * @param excelFileName
	 * 		文件名
	 * @return
	 * 		文件的所在目录
	 */
	public String writeToTempFile(String excelFileName){
		//excelFileName = excelFileName.split(".").length > 1 ? excelFileName.split(".")[0] : excelFileName;
		
		while(!finished){
			try {
				Thread.sleep(THREAD_SLEEP_SSS);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		if(queue == null || queue.size()==0) {
			System.out.println("The excel queue is null!");
			return null;
		}
		//System.out.println("queue.size:" + queue.size());
		if(excelFileName == null || "".equals(excelFileName)){
			excelFileName = System.currentTimeMillis()+"";
		}
		//List<String> xlsxFilePathList = new ArrayList<String>();
		String dir = TEMP_DIR + SysUtil.current("yyyy"+File.separator+"MM"+File.separator+"dd") + 
				File.separator + uuid()+
				File.separator+ excelFileName ;
		
        FileOutputStream fos = null;
        Workbook wb = null;
		if(queue.size() == 1){
			String path = dir + File.separator + excelFileName +XLSX_EXT;
			
			File xlsxFile = new File(path);
	        //xlsxFilePathList.add(path);
	        if(!xlsxFile.exists()){
	        	if(!xlsxFile.getParentFile().exists()){
	        		xlsxFile.getParentFile().mkdirs(); 
	        	}
				try {
					xlsxFile.createNewFile();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			try {
				fos = new FileOutputStream(xlsxFile);
				wb = queue.peek().getWorkbook();
				if(wb != null){
					wb.write(fos);
		            fos.flush();
		            wb = null;
		            fos.close();
				}
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}else{
			for(int i = 0;i < queue.size();i++){
				if(queue.toArray()[i] != null && ((Excel)queue.toArray()[i]).getWorkbook() != null){
					String path = dir+ File.separator + excelFileName +"_"+ (i+1) +XLSX_EXT;
		            //xlsxFilePathList.add(path);
					File xlsxFile = new File(path);
					if(!xlsxFile.exists()){
			        	if(!xlsxFile.getParentFile().exists()){
			        		xlsxFile.getParentFile().mkdirs(); 
			        	}
						try {
							xlsxFile.createNewFile();
						} catch (IOException e) {
							e.printStackTrace();
						}
					}
					try {
						wb = ((Excel)queue.toArray()[i]).getWorkbook();
						fos = new FileOutputStream(xlsxFile);
						if(wb != null){
							wb.write(fos);
				            fos.flush();
				            wb = null;
				            fos.close();
						}
					} catch (FileNotFoundException e) {
						e.printStackTrace();
					} catch (IOException e) {
						e.printStackTrace();
					}
				}
			}
		}
		if(fos != null ){
			try {
				fos.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return dir;
	}
	
	public void deleteTempFile(File dirFile){
		if(dirFile.exists()){
			if (dirFile.isDirectory()) {
		        File[] files = dirFile.listFiles();
		        for (File file : files) {
		        	deleteTempFile(file);
		        }
		    }else{
		    	dirFile.delete();
		    }
		}
	}
    
	public boolean isFinished() {
		return finished;
	}
	public void setFinished(boolean finished) {
		this.finished = finished;
	}

	public BlockingQueue<Excel> getQueue() {
		return queue;
	}

	public void setQueue(BlockingQueue<Excel> queue) {
		this.queue = queue;
	}

	public static String uuid() {
		return UUID.randomUUID().toString().replaceAll("-", "").toUpperCase();
	}
}
