package es.arcasi.oss.boot.filestorage.web.rest;

import java.io.IOException;

import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.IOUtils;
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
import es.arcasi.oss.filestorage.model.FileStorageItem;
import es.arcasi.oss.filestorage.services.FileStorageService;


@RestController
@RequestMapping(value = "${arcasi.filestorage.endpoint-url:/filestorage}")
public class FileStorageController {
  

  private FileStorageService fileStorageService;

  public FileStorageController(FileStorageService fileStorageService) {
    this.fileStorageService = fileStorageService;
  }

  @ResponseStatus(HttpStatus.CREATED)
  @RequestMapping(value = "/", method = RequestMethod.POST)
  public ResponseEntity<UploadResponse> upload(@RequestParam("file") MultipartFile file) throws IOException {
    if (file.isEmpty()) {
      return ResponseEntity.badRequest().header("Failure", "An error has occurred uploading file").body(null);
    }
    byte[] bytes = file.getBytes();
    String fileId = fileStorageService.save(new FileStorageItem(bytes));
    return ResponseEntity.ok(new UploadResponse(fileId));
  }

  @ResponseStatus(HttpStatus.OK)
  @RequestMapping(value = "/{fileId}", method = RequestMethod.GET, produces = MediaType.APPLICATION_OCTET_STREAM_VALUE)
  public void download(@PathVariable("fileId") String fileId, HttpServletResponse response) throws IOException {
    FileStorageItem fileStorageItem = fileStorageService.get(fileId);

    // Copy the stream to the response's output stream.
    response.setContentLength(fileStorageItem.getFile().length);
    IOUtils.write(fileStorageItem.getFile(), response.getOutputStream());
    response.flushBuffer();
  }

}