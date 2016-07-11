package es.arcasi.boot.filestorage.autocofigure;

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

  public static final String ENDPOINT_URL = "/filestorage";

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
   * RequestMapping URL for FileUpload Controller
   */
  @NotNull
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
}
