package io.ymq.example;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

import org.apache.tools.zip.ZipEntry;
import org.apache.tools.zip.ZipOutputStream;


/**
 * 描述:
 *
 * @author yanpenglei
 * @create 2018-05-19 12:02
 **/
public class ZIPUtil {

    public static final String ZIP_EXT = ".zip";
    /**
     * 压缩文件或者文件夹
     * <p>压缩采用gb2312编码，其它编码方式可能造成文件名与文件夹名使用中文的情况下压缩后为乱码</p>
     * @param source
     * 		要压缩的文件或者文件夹
     * @param zipFileName
     * 		压缩后的zip文件名称 压缩后的目录组织与windows的zip压缩的目录组织相同
     * 		会根据压缩的目录的名称，在压缩文件夹中创建一个改名的根目录， 其它压缩的文件和文件夹都在该目录下依照原来的文件目录组织形式
     * @throws IOException
     */
    public static void ZIP(String source, String zipFileName)throws IOException {
        ZipOutputStream zos = new ZipOutputStream(new File(zipFileName));
        // 设置压缩的时候文件名编码为gb2312
        zos.setEncoding("gb2312");
        // System.out.println(zos.getEncoding());
        File f = new File(source);
        if (f.isDirectory()) {
            // 如果直接压缩文件夹
            ZIPDIR(source, zos, f.getName() + "/");// 此处使用/来表示目录，如果使用\\来表示目录的话，会导致压缩后的文件目录组织形式在解压缩的时候不能正确识别。
        } else {
            // 如果直接压缩文件
            ZIPDIR(f.getPath(), zos, new File(f.getParent()).getName() + "/");
            ZIPFile(f.getPath(), zos, new File(f.getParent()).getName() + "/"+ f.getName());
        }
        zos.closeEntry();
        zos.close();
    }

    /**
     * 压缩目录
     * @param sourceDir
     * 		需要压缩的目录位置
     * @param zos
     * 		压缩到的zip文件
     * @param tager
     * 		压缩到的目标位置
     * @throws IOException
     */
    public static void ZIPDIR(String sourceDir, ZipOutputStream zos,String tager) throws IOException {
        // System.out.println(tager);
        ZipEntry ze = new ZipEntry(tager);
        zos.putNextEntry(ze);
        // 提取要压缩的文件夹中的所有文件
        File f = new File(sourceDir);
        File[] flist = f.listFiles();
        if (flist != null) {
            // 如果该文件夹下有文件则提取所有的文件进行压缩
            for (File fsub : flist) {
                if (fsub.isDirectory()) {
                    // 如果是目录则进行目录压缩
                    ZIPDIR(fsub.getPath(), zos, tager + fsub.getName() + "/");
                } else {
                    // 如果是文件，则进行文件压缩
                    ZIPFile(fsub.getPath(), zos, tager + fsub.getName());
                }
            }
        }
    }

    /**
     * zip 压缩单个文件
     * @param sourceFileName
     * 	要压缩的原文件
     * @param zos
     * 	压缩到的zip文件
     * @param tager
     * 	压缩到的目标位置
     * @throws IOException
     */
    public static void ZIPFile(String sourceFileName, ZipOutputStream zos, String tager) throws IOException {
        // System.out.println(tager);
        ZipEntry ze = new ZipEntry(tager);
        zos.putNextEntry(ze);
        // 读取要压缩文件并将其添加到压缩文件中
        FileInputStream fis = new FileInputStream(new File(sourceFileName));
        byte[] bf = new byte[2048];
        int location = 0;
        while ((location = fis.read(bf)) != -1) {
            zos.write(bf, 0, location);
        }
        fis.close();
    }

    /**
     * @param args
     */
    public static void main(String[] args) {

    }

}
