package ink.fengshuai.minio.autoconfigure;

import io.minio.MinioClient;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.util.StringUtils;

@EnableConfigurationProperties(MinoClientProperties.class)
public class MinioClientAutoConfiguration {

    @Bean
    public MinioClient minioClient(MinoClientProperties minoClientProperties) {

        MinioClient.Builder builder = MinioClient.builder()
                .credentials(minoClientProperties.getAccessKey(), minoClientProperties.getSecretKey());
        // endpoint
        int port = minoClientProperties.getPort();
        if (port > 0) {
            builder.endpoint(minoClientProperties.getEndpoint(), minoClientProperties.getPort(), minoClientProperties.isSecure());
        } else {
            builder.endpoint(minoClientProperties.getEndpoint());
        }

        // region
        String region = minoClientProperties.getRegion();
        if (StringUtils.hasText(region)) {
            builder.region(region);
        }

        return builder.build();
    }

    @Bean
    public MinioFileStorage minioFileStorage(MinioClient minioClient) {
        return new MinioFileStorageImpl(minioClient);
    }
}
