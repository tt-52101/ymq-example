package io.ymq.example;

import java.io.FileInputStream;
import java.io.FileOutputStream;  
import java.io.IOException;  
import java.util.ArrayList;  
import java.util.HashMap;  
import java.util.List;  
import java.util.Map;

import net.sf.jxls.exception.ParsePropertyException;  
import net.sf.jxls.transformer.XLSTransformer;  
  
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;  
import org.apache.poi.ss.usermodel.Workbook;  
  
/**   
* @author 鲁炬 
* 
*/  
public class XlsExportUtil {  
  
  public static void createExcel() throws ParsePropertyException, InvalidFormatException, IOException {}  
  
  @SuppressWarnings("rawtypes")  
  public static void main(String[] args) throws ParsePropertyException, InvalidFormatException, IOException {  
    //获取Excel模板文件  
    String fileDir = XlsExportUtil.class.getResource("").getFile();

    String filePath = "d:\\XlsExportUtilTemplate.xls";
    System.out.println("excel template file:" + filePath);  
    FileInputStream is = new FileInputStream(filePath);  
  
    //创建测试数据  
//    Map<String, Object> map = new HashMap<String, Object>();
    List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();  
    Map<String, Object> map1 = new HashMap<String, Object>();  
    map1.put("name", "电视");  
    map1.put("price", "3000");  
    map1.put("desc", "3D电视机");  
    map1.put("a", "中文测试");
    Map<String, Object> map2 = new HashMap<String, Object>();  
    map2.put("name", "空调");  
    map2.put("price", "2000");  
    map2.put("desc", "变频空调");
    map2.put("a", "测试中文");
    list.add(map1);  
    list.add(map2);  
//    map.put("list", list);
  
    ArrayList<List> objects = new ArrayList<List>();
    objects.add(list);
    objects.add(list);
    objects.add(list);
    objects.add(list);

    //sheet的名称  
    List<String> listSheetNames = new ArrayList<String>();  
    listSheetNames.add("1");  
    listSheetNames.add("2");  
    listSheetNames.add("3");  
    listSheetNames.add("4");
    System.out.println(objects);
    //调用引擎生成excel报表  
    XLSTransformer transformer = new XLSTransformer();
    Workbook workbook = transformer.transformMultipleSheetsList(is, objects, listSheetNames, "list", new HashMap(), 0);
    workbook.write(new FileOutputStream("d:\\1.xls"));
  
  }  
}  