package es.arcasi.oss.boot.filestorage.web.rest;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;

import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import es.arcasi.oss.boot.filestorage.model.dto.UploadResponse;
import es.arcasi.oss.filestorage.model.FileMetadata;
import es.arcasi.oss.filestorage.model.FileStorageItem;
import es.arcasi.oss.filestorage.services.FileStorageService;

@RestController
@RequestMapping(value = "${arcasi.filestorage.endpoint-url:/filestorage}")
public class FileStorageController {

  @Value("${arcasi.filestorage.endpoint-url:/filestorage}")
  private String baseURL;
  private FileStorageService fileStorageService;

  public FileStorageController(FileStorageService fileStorageService) {
    this.fileStorageService = fileStorageService;
  }

  @ResponseStatus(HttpStatus.CREATED)
  @RequestMapping(value = "/", method = RequestMethod.POST)
  public ResponseEntity<UploadResponse> upload(@RequestParam("file") MultipartFile file) throws IOException, URISyntaxException {
    if (file.isEmpty()) {
      return ResponseEntity.badRequest().header("Failure", "An error has occurred uploading file").body(null);
    }

    FileMetadata fileMetadata = new FileMetadata();
    fileMetadata.setName(file.getOriginalFilename());
    fileMetadata.setMimeType(file.getContentType());
    FileStorageItem fileStorageItem = new FileStorageItem(file.getBytes(), fileMetadata);

    String fileId = fileStorageService.save(fileStorageItem);
    return ResponseEntity.created(new URI(baseURL + "/" + fileId)).body(new UploadResponse(fileId, fileMetadata));
  }

  @ResponseStatus(HttpStatus.OK)
  @RequestMapping(value = "/{fileId}", method = RequestMethod.GET)
  public ResponseEntity<UploadResponse> get(@PathVariable("fileId") String fileId) throws IOException {
    FileStorageItem fileStorageItem = fileStorageService.get(fileId);
    return ResponseEntity.ok(new UploadResponse(fileId, fileStorageItem.getFileMetadata()));
  }

  @ResponseStatus(HttpStatus.OK)
  @RequestMapping(value = "/{fileId}/stream", method = RequestMethod.GET) 
  public void download(@PathVariable("fileId") String fileId, HttpServletResponse response) throws IOException {
    FileStorageItem fileStorageItem = fileStorageService.get(fileId);

    String contentType = MediaType.APPLICATION_OCTET_STREAM_VALUE;
    if (fileStorageItem.getFileMetadata() != null && fileStorageItem.getFileMetadata().getMimeType() != null) {
      contentType = fileStorageItem.getFileMetadata().getMimeType();
    }
    
    // Copy the stream to the response's output stream.
    response.setContentType(contentType);
    response.setContentLength(fileStorageItem.getFile().length);
    IOUtils.write(fileStorageItem.getFile(), response.getOutputStream());
    response.flushBuffer();
  }
  
  @ResponseStatus(HttpStatus.NO_CONTENT)
  @RequestMapping(value = "/{fileId}", method = RequestMethod.DELETE)
  public void delete(@PathVariable("fileId") String fileId) throws IOException {
    fileStorageService.delete(fileId);
  }

}