package com.example.bkzhou.utils;

import android.os.Environment;

import com.example.bkzhou.base.BaseApplication;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

/**
 * 完成文件的相关操作
 * Created by bkzhou on 16-5-7.
 */
public class FileUtils {
  private static final String EXTERNAL_STORAGE_PATH ="/FileUtils";
  /**
   *设置temp 文件目录最大缓存
   */
  private static final long K_BYTES_TO_DELETE = 10 * 1024;
  /**
   * 检测并且创建文件
   * 传入地址如果不存在则创建文件
   */
  public static File checkAndCreateFile(String path){
    File file = new File(path);
    if (!file.exists())
      try {
        file.createNewFile();
      }catch (IOException e){
        e.printStackTrace();
      }
    return file;
  }
  /**
   * 创建主目录下子目录，自动会在末尾添加"/"文件分隔符
   */
  public static String checkAndCreateChildDirectory(String path){
    File file = new File(path);
    if (!file.exists())
      file.mkdirs();
    if (!file.exists())
      return null;
    return path+"/";
  }
  /**
   * 获取文件地址
   * 外部{@link #EXTERNAL_STORAGE_PATH}目录或者/data/data/com.android.framework/files/目录（如果SD卡不可用）,
   * 末尾自带"/"符号
   */
  public static String getExternalStoragePath(){
    String path = null;
    //需要检测外部SD卡的挂载状态
    if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
      path = checkAndCreateChildDirectory(Environment.getExternalStorageDirectory().getPath() + EXTERNAL_STORAGE_PATH);
    }
    if (path == null){
      //如果外部SD卡不可用，使用"/data/data/com.android.framework/files/"目录
      path = BaseApplication.getInstance().getFilesDir().getPath();
    }
    if (!path.subSequence(path.length()-1, path.length()).equals("/"))
      path += "/";
    return path;
  }
  /**
   * 所有在外部存储目录下的子目录都需要在此定义文件夹名
   */
  public enum ExternalStorageType{
    TEMP("temp"),FILE("file"),IMAGE("image"),VOICE("voice"),VIDEO("video"),HTML("html");

    private String typeName;
    ExternalStorageType(String typeName){
      this.typeName = typeName;
    }

    public String getFilePath(String parentPath) {
      String path = parentPath;
      if (!(parentPath.charAt(parentPath.length()-1)=='/')){
        path += "/";
      }
      return path+typeName;
    }
  }
  /**
   * 获取temp文件目录
   *
   */
  public static String getExternalStorageTempPath(){
    ExternalStorageType type = ExternalStorageType.TEMP;
    return checkAndCreateChildDirectory(type.getFilePath(getExternalStoragePath()));
  }
  /**
   * 创建temp文件
   * 在外部{@link #getExternalStorageTempPath()}目录下创建文件，
   */
  public static File createFileInTempDirectory(String filename){
    return checkAndCreateFile(getExternalStorageTempPath() + filename);
  }
  /**
   * 删除外部临时文件目录
   */
  public static void clearExternalStorageTemp(){
    Log.i("FileUtils", "application close clear temp directory");
    if (getFileOrDirectorySize(getExternalStorageTempPath()) >= K_BYTES_TO_DELETE) {
      File file = new File(getExternalStorageTempPath());
      File[] files = file.listFiles();
      for (File temp : files)
        temp.delete();
    }
  }
  /**
   * 获取外部文件目录，过大会自动删除
   */
  public static String getExternalStorageFilePath(){
    ExternalStorageType type = ExternalStorageType.FILE;
    return checkAndCreateChildDirectory(type.getFilePath(getExternalStoragePath()));
  }

  /**
   * 在外部{@link #getExternalStorageFilePath()}目录下创建文件，
   */
  public static File createFileInFileDirectory(String filename){
    return checkAndCreateFile(getExternalStorageTempPath() + filename);
  }

  /**
   * 获取外部图片文件目录，在该目录下会创建.nomedia文件防止系统扫描
   */
  public static String getExternalStorageImagePath(){
    ExternalStorageType type = ExternalStorageType.IMAGE;
    String path = type.getFilePath(getExternalStoragePath());
    String result = checkAndCreateChildDirectory(path);
    checkAndCreateNoMedia(path);
    return result;
  }
  /**
   * 在外部{@link #getExternalStorageImagePath()}目录下创建文件，
   */
  public static File createFileInImageDirectory(String filename){
    return checkAndCreateFile(getExternalStorageImagePath() + filename);
  }
  /**
   * 获取外部声音文件目录，在该目录下会创建.nomedia文件防止系统扫描
   */
  public static String getExternalStorageVoicePath(){
    ExternalStorageType type = ExternalStorageType.VOICE;
    String path = type.getFilePath(getExternalStoragePath());
    String result = checkAndCreateChildDirectory(path);
    checkAndCreateNoMedia(path);
    return result;
  }
  /**
   * 在外部{@link #getExternalStorageVoicePath()}目录下创建文件，
   */
  public static File createFileInVoiceDirectory(String filename){
    return checkAndCreateFile(getExternalStorageVoicePath() + filename);
  }
  /**
   * 获取外部网页文件目录
   */
  public static String getExternalStorageHtmlPath(){
    ExternalStorageType type = ExternalStorageType.HTML;
    return checkAndCreateChildDirectory(type.getFilePath(getExternalStoragePath()));
  }

  /**
   * 在外部{@link #getExternalStorageHtmlPath()}目录下创建文件，
   */
  public static File createFileInHtmlDirectory(String filename){
    return checkAndCreateFile(getExternalStorageHtmlPath() + filename);
  }
  /**
   * 获取文件或者目录大小，单位为取整的KB
   */
  public static long getFileOrDirectorySize(String path){
    long size = 0;
    if (path == null)
      return size;
    File file = new File(path);
    if (!file.exists())
      return size;
    if (file.isDirectory()){
      File[] files = file.listFiles();
      if (files != null) {
        for (File temp : files) {
          size += getFileOrDirectorySize(temp.getAbsolutePath());
        }
      }
    }else{
      size = getFileSize(file);
    }

    return size/1024;
  }
  /**
   * @return 返回文件大小，单位为byte
   */
  private static long getFileSize(File file){
    long size = 0;
    if (file.exists()){
      try {
        FileInputStream fis = new FileInputStream(file);
        size = fis.available();
      }catch (Exception e){
        e.printStackTrace();
      }
    }
    return size;
  }
  /**
   * 检测该目录下是否有nomedia文件，如果没有就创建
   */
  private static void checkAndCreateNoMedia(String path){
    checkAndCreateFile(path + "/.nomedia");
  }
  /**
   * 文件拷贝
   * @param src 源文件
   * @param des 目标文件
   */
  public static void copyFile(File src, File des){
    try {
      int byteRead = 0;
      InputStream inputStream = new FileInputStream(src);
      if (!des.exists())
        des.createNewFile();
      OutputStream outputStream = new FileOutputStream(des);
      byte[] buffer = new byte[1024];
      while ( (byteRead = inputStream.read(buffer)) != -1) {
        outputStream.write(buffer, 0, byteRead);
      }
      inputStream.close();
      outputStream.flush();
      outputStream.close();
    } catch (FileNotFoundException e) {
      e.printStackTrace();
    } catch (IOException e) {
      e.printStackTrace();
    }
  }
}
