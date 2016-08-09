package es.arcasi.oss.boot.filestorage.model.dto;

import es.arcasi.oss.filestorage.model.FileMetadata;

/**
 * Response returned by upload service with the unique fileID identifying the file uploaded 
 * @author plopezmesa
 *
 */
public class UploadResponse {
  /*
   * Unique file ID identifying the uploaded resource
   */
  private String fileId;
  /**
   * File metatada of the uploaded resource
   */
  private FileMetadata fileMetadata;

  public UploadResponse(String fileId, FileMetadata fileMetadata) {
    this.fileId = fileId;
    this.fileMetadata = fileMetadata;
  }

  public UploadResponse(String fileId) {
    this.fileId = fileId;
  }

  public String getFileId() {
    return fileId;
  }

  public void setFileId(String fileId) {
    this.fileId = fileId;
  }

  public FileMetadata getFileMetadata() {
    return fileMetadata;
  }

  public void setFileMetadata(FileMetadata fileMetadata) {
    this.fileMetadata = fileMetadata;
  }
}