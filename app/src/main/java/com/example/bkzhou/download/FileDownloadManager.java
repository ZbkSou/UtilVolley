package com.example.bkzhou.download;

import com.example.bkzhou.utils.FileUtils;
import com.example.bkzhou.utils.Md5Util;

import java.util.ArrayList;

/**
 * Created by bkzhou on 16-5-7.
 */
public class FileDownloadManager {
  /**
   * 该文件下载的url
   */
  private String url;
  /**
   * 该文件下载名
   */
  private String fileName;
  /** 数据库操作对象 */
  private DownloadDBHelper helper;
  /** 下载一个文件的所有线程信息 */
  private ArrayList<DownloadInfo> infos;
  /**
   * 该文件下载路径，默认为SD卡file目录
   */
  private String path = FileUtils.getExternalStorageFilePath();

  public FileDownloadManager(String url, String fileName) {
    this(url, fileName, null);
  }

  public FileDownloadManager(String url, String fileName, String path) {
    this.url = url;
    if (path != null)
      this.path = path;
    if (!this.path.substring(this.path.length() - 1).equals("/")) {
      this.path += "/";
    }
    //保存该文件原路径
    this.fileName = this.path + fileName;
    //将文件名字先进行md5，最后等文件下载完成之后再更改文件名字
    String md5 = Md5Util.getMD5Str(fileName);
    this.path += md5;
    helper = new DownloadDBHelper();
    infos = new ArrayList<>();
    threads = new ArrayList<>();
  }
}
