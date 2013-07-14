package com.spxc.bayfiles.adapter;

public class fileObject {

    private String fileName;
    private String size;
    private String infoToken;
    private String deleteToken;
    private String sha1;
    private String fileId;

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public String getSize() {
        return size;
    }

    public void setSize(String size) {
        this.size = size;
    }

    public String getInfoToken() {
        return infoToken;
    }

    public void setInfoToken(String infoToken) {
        this.infoToken = infoToken;
    }

    public String getDeleteToken() {
        return deleteToken;
    }

    public void setDeleteToken(String deleteToken) {
        this.deleteToken = deleteToken;
    }

    public String getSha1() {
        return sha1;
    }

    public void setSha1(String sha1) {
        this.sha1 = sha1;
    }
    
    public String getFileId() {
        return fileId;
    }

    public void setFileId(String fileId) {
        this.fileId = fileId;
    }
}