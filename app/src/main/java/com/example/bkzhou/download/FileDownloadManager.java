package com.example.bkzhou.download;

/**
 * Created by bkzhou on 16-5-7.
 */
public class FileDownloadManager {
  /** 该文件下载的url */
  private String url;
  /** 该文件下载路径，默认为SD卡file目录 */
  private String path = FileUtils.getExternalStorageFilePath();
  public FileDownloadManager(String url,String fileName){
    this(url, fileName, null);
  }
  public FileDownloadManager(String url, String fileName, String path){
    this.url = url;
    if (path != null)
      this.path = path;
    if (!this.path.substring(this.path.length()-1).equals("/")){
      this.path += "/";
    }
  }
}
