package es.arcasi.oss.boot.filestorage.autocofigure;

import javax.validation.constraints.NotNull;

import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * Configuration properties for File Storage Service
 * @author plopezmesa
 *
 */

@ConfigurationProperties(prefix = FileStorageProperties.PREFIX)
public class FileStorageProperties {

  public static final String PREFIX = "arcasi.filestorage";

  public static final String ENDPOINT_URL = null;

  /**
   * Type of the ServiceBean using for file storage
   */
  @NotNull
  private FileStorageServiceType serviceType = FileStorageServiceType.DUMMY;

  /**
   * Local path where files will be stored
   */
  private String basePath;

  /**
   * TimeToLive for Memory File Storage
   */
  private Long ttl;

  /**
   * RequestMapping URL for FileUpload Controller
   */
  private String endpointUrl = ENDPOINT_URL;

  public FileStorageServiceType getServiceType() {
    return serviceType;
  }

  public void setServiceType(FileStorageServiceType serviceType) {
    this.serviceType = serviceType;
  }

  public String getBasePath() {
    return basePath;
  }

  public void setBasePath(String basePath) {
    this.basePath = basePath;
  }

  public String getEndpointUrl() {
    return endpointUrl;
  }

  public void setEndpointUrl(String endpointUrl) {
    this.endpointUrl = endpointUrl;
  }

  public Long getTtl() {
    return ttl;
  }

  public void setTtl(Long ttl) {
    this.ttl = ttl;
  }
}
