package es.arcasi.oss.boot.filestorage.autocofigure;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import es.arcasi.oss.filestorage.services.FileStorageService;
import es.arcasi.oss.filestorage.services.impl.DiskFileStorageServiceImpl;
import es.arcasi.oss.filestorage.services.impl.MemoryFileStorageServiceImpl;


@Configuration
//@ConditionalOnMissingBean(FileStorageService.class)
@EnableConfigurationProperties(FileStorageProperties.class)
public class FileStorageAutoConfiguration {

  @Autowired
  private FileStorageProperties properties = new FileStorageProperties();

  @Bean
  @Qualifier("fileStorageAutoconfigured")
  public FileStorageService fileStorageConfigurer() throws IOException {
    switch (properties.getServiceType()) {
    case LOCAL:
      return new DiskFileStorageServiceImpl(this.properties.getBasePath());
    case MEMORY:
      if (this.properties.getTtl() != null) {
        return new MemoryFileStorageServiceImpl(this.properties.getTtl());
      }
      return new MemoryFileStorageServiceImpl();
    default:
      throw new RuntimeException("No FileStorageService implementationfound");
    }
  }

}
