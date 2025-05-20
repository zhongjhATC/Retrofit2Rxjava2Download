package com.zhongjh.retrofitdownloadlib.http;

import java.io.File;

/**
 * 下载文件信息
 *
 * @author zhongjh
 * @date 2025/5/20
 */
public class DownloadInfo {
    /**
     * file
     */
    private File file;
    /**
     * 文件名称
     */
    private String fileName;
    /**
     * 文件大小 单位 byte
     */
    private long fileSize;
    /**
     * 当前已下载大小 单位 byte
     */
    private long currentSize;
    /**
     * 当前下载进度
     */
    private int progress;
    /**
     * 下载速率
     */
    private long speed;
    /**
     * 下载用时
     */
    private long usedTime;
    /**
     * 下载异常信息
     */
    private Throwable errorMsg;

    public long getUsedTime() {
        return usedTime;
    }

    public void setUsedTime(long usedTime) {
        this.usedTime = usedTime;
    }


    public File getFile() {
        return file;
    }

    public void setFile(File file) {
        this.file = file;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public long getFileSize() {
        return fileSize;
    }

    public void setFileSize(long fileSize) {
        this.fileSize = fileSize;
    }

    public long getCurrentSize() {
        return currentSize;
    }

    public void setCurrentSize(long currentSize) {
        this.currentSize = currentSize;
    }

    public int getProgress() {
        return progress;
    }

    public void setProgress(int progress) {
        this.progress = progress;
    }

    public long getSpeed() {
        return speed;
    }

    public void setSpeed(long speed) {
        this.speed = speed;
    }

    public Throwable getErrorMsg() {
        return errorMsg;
    }

    public void setErrorMsg(Throwable errorMsg) {
        this.errorMsg = errorMsg;
    }
}
