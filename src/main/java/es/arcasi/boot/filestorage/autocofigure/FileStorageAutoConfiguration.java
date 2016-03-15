package es.arcasi.boot.filestorage.autocofigure;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import es.arcasi.filestorage.services.FileStorageService;
import es.arcasi.filestorage.services.impl.DiskFileStorageServiceImpl;
import es.arcasi.filestorage.services.impl.DummyFileStorageServiceImpl;

@Configuration
@ConditionalOnMissingBean(FileStorageService.class)
@EnableConfigurationProperties(FileStorageProperties.class)
public class FileStorageAutoConfiguration {

  @Autowired
  private FileStorageProperties properties = new FileStorageProperties();

  @Bean
  public FileStorageService fileStorageConfigurer() throws IOException {
    switch (properties.getServiceType()) {
    case LOCAL:
      return new DiskFileStorageServiceImpl(this.properties.getBasePath());
    case DUMMY:
      return new DummyFileStorageServiceImpl();
    default:
      return new DummyFileStorageServiceImpl();
    }
  }
  
  
  
}
