package ink.fengshuai.minio.autoconfigure;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(
        prefix = "minio"
)
@Data
public class MinoClientProperties {

    /**
     * api address
     */
    private String endpoint;
    private String accessKey;
    private String secretKey;

    private String region;

    int port = -1;
    /**
     * 默认http
     */
    boolean secure;

}
