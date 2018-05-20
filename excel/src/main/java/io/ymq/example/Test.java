package io.ymq.example;

import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * 描述:
 *
 * @author yanpenglei
 * @create 2018-05-19 12:29
 **/
public class Test {

    /**
     * @param args
     */
    public static void main(String[] args) {
        int size = 1; //sheet number
        int cols = 5; //column number
        int excelMAX_ROWS=10;

        String title = "主标题内容";

        Map<String,String> columns = new LinkedHashMap<String, String>();

        for(int j = 0;j <= cols;j++){
            columns.put("列"+j, "列标-"+j+"");
        }

        List<Map<String,Object>> dataList = new ArrayList<Map<String,Object>>();
        for(int i = 0;i < size;i++){
            for(int j = 0;j < excelMAX_ROWS;j++){
                Map<String, Object> map = new LinkedHashMap<String, Object>();
                for(int k = 0;k < cols;k++){
                    map.put("列"+k, "行"+j+":"+"列"+j);
                }
                dataList.add(map);
            }
        }

        //used
        System.out.println("**************************"+SysUtil.current()+"**************************");
        ExcelExporter exporter = new ExcelExporter(title, columns, dataList);
        String dir = exporter.writeToTempFile("测试吧");
        System.out.println(dir);
        try {

            ZIPUtil.ZIP(dir, dir+"_"+System.currentTimeMillis()+".zip");
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println("**************************"+SysUtil.current()+"**************************");

    }

}
