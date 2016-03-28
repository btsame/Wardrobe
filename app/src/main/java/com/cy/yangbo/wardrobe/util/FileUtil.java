package com.cy.yangbo.wardrobe.util;

import android.util.Log;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

/**
 * Created by Administrator on 2016/2/24.
 */
public class FileUtil {

    public static final String TAG = FileUtil.class.getSimpleName();

    /**
     * 移动文件
     * @param srcFileName 	源文件完整路径
     * @param destDirName 	目的目录完整路径
     * @return 文件移动成功返回true，否则返回false
     */
    public static boolean moveFile(String srcFileName, String destDirName) {

        File srcFile = new File(srcFileName);
        if(!srcFile.exists() || !srcFile.isFile())
            return false;

        File destDir = new File(destDirName);
        if (!destDir.exists())
            destDir.mkdirs();

        return srcFile.renameTo(new File(destDirName + File.separator + srcFile.getName()));
    }

    /**
     * 移动目录
     * @param srcDirName 	源目录完整路径
     * @param destDirName 	目的目录完整路径
     * @return 目录移动成功返回true，否则返回false
     */
    public static boolean moveDirectory(String srcDirName, String destDirName) {

        File srcDir = new File(srcDirName);
        if(!srcDir.exists() || !srcDir.isDirectory())
            return false;

        File destDir = new File(destDirName);
        if(!destDir.exists())
            destDir.mkdirs();

        /**
         * 如果是文件则移动，否则递归移动文件夹。删除最终的空源文件夹
         * 注意移动文件夹时保持文件夹的树状结构
         */
        File[] sourceFiles = srcDir.listFiles();
        for (File sourceFile : sourceFiles) {
            if (sourceFile.isFile())
                moveFile(sourceFile.getAbsolutePath(), destDir.getAbsolutePath());
            else if (sourceFile.isDirectory())
                moveDirectory(sourceFile.getAbsolutePath(),
                        destDir.getAbsolutePath() + File.separator + sourceFile.getName());
            else
                ;
        }
        return srcDir.delete();
    }

    /**
     * 复制单个文件
     * @param oldPath String 原文件路径 如：c:/fqf.txt
     * @param newPath String 复制后路径 如：f:/fqf.txt
     * @return boolean
     */
    public static boolean copyFile(String oldPath, String newPath) {
        InputStream in = null;
        OutputStream out = null;
        try {
            File oldfile = new File(oldPath);
            if(!oldfile.exists() || !oldfile.isFile()){
                return false;
            }
            float bytesum = 0;
            int byteread = 0;
            in = new FileInputStream(oldPath); //读入原文件
            out = new FileOutputStream(newPath);
            byte[] buffer = new byte[1024];
            while ( (byteread = in.read(buffer)) != -1) {
                bytesum += byteread; //字节数 文件大小
                out.write(buffer, 0, byteread);
            }
            Log.v(TAG, "复制文件(" + oldPath + "--->" + newPath + "),文件大小(" + (bytesum / 1024) + "KB)");
            return true;
        }
        catch (Exception e) {
            Log.e(TAG, "复制文件(" + oldPath + "--->" + newPath + ")出现异常！");
            e.printStackTrace();
            return false;
        }finally {
            try{
                if(in != null){
                    in.close();
                }
                if(out != null){
                    out.close();
                }
            }catch(IOException e1){

            }
        }
    }

    /**
     * 复制整个文件夹内容
     * @param oldPath String 原文件路径 如：c:/fqf
     * @param newPath String 复制后路径 如：f:/fqf/ff
     * @return boolean
     */
    public static void copyFolder(String oldPath, String newPath) {

        try {
            (new File(newPath)).mkdirs(); //如果文件夹不存在 则建立新文件夹
            File a=new File(oldPath);
            String[] file=a.list();
            File temp=null;
            for (int i = 0; i < file.length; i++) {
                if(oldPath.endsWith(File.separator)){
                    temp=new File(oldPath+file[i]);
                }
                else{
                    temp=new File(oldPath+File.separator+file[i]);
                }

                if(temp.isFile()){
                    FileInputStream input = new FileInputStream(temp);
                    FileOutputStream output = new FileOutputStream(newPath + "/" +
                            (temp.getName()).toString());
                    byte[] b = new byte[1024 * 5];
                    int len;
                    while ( (len = input.read(b)) != -1) {
                        output.write(b, 0, len);
                    }
                    output.flush();
                    output.close();
                    input.close();
                }
                if(temp.isDirectory()){//如果是子文件夹
                    copyFolder(oldPath+"/"+file[i],newPath+"/"+file[i]);
                }
            }
        }
        catch (Exception e) {
            System.out.println("复制整个文件夹内容操作出错");
            e.printStackTrace();

        }

    }
}
