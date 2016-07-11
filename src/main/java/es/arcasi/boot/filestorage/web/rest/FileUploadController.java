package es.arcasi.boot.filestorage.web.rest;

import java.io.IOException;

import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.multipart.MultipartFile;

import es.arcasi.filestorage.model.FileStorageItem;
import es.arcasi.filestorage.model.UploadResponse;
import es.arcasi.filestorage.services.FileStorageService;

@Controller
@RequestMapping(value = "${arcasi.filestorage.endpointUrl:/filestorage}")
public class FileUploadController {

  @Autowired
  FileStorageService fileStorageService;

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