package es.arcasi.oss.boot.filestorage.autocofigure;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.autoconfigure.condition.ConditionalOnWebApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import es.arcasi.oss.boot.filestorage.web.rest.FileStorageController;
import es.arcasi.oss.filestorage.services.FileStorageService;

@Configuration
@AutoConfigureAfter(FileStorageAutoConfiguration.class)
@ConditionalOnWebApplication
@ConditionalOnMissingBean(value = FileStorageController.class)
@ConditionalOnProperty(prefix = "arcasi.filestorage", name = { "endpoint-url" }, havingValue = "")
public class FileStorageEndpointAutoConfiguration {

  @Autowired
  @Qualifier("fileStorageAutoconfigured")
  private FileStorageService fileStorageService;

  @Bean
  public FileStorageController fileStorageControllerConfigurer() {
    return new FileStorageController(fileStorageService);
  }

}
