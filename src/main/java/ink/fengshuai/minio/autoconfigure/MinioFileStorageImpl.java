package ink.fengshuai.minio.autoconfigure;

import ink.fengshuai.minio.autoconfigure.objectargs.Duration;
import ink.fengshuai.minio.autoconfigure.objectargs.FileObject;
import ink.fengshuai.minio.autoconfigure.objectargs.InputStreamObject;
import io.minio.*;
import io.minio.http.Method;
import org.springframework.util.StringUtils;

import java.io.InputStream;
import java.util.function.Consumer;

public class MinioFileStorageImpl implements MinioFileStorage {

    private final MinioClient minioClient;

    public MinioFileStorageImpl(MinioClient minioClient) {
        this.minioClient = minioClient;
    }

    @Override
    public boolean isExistBucket(String bucket) throws Exception {
        return minioClient.bucketExists(BucketExistsArgs.builder().bucket(bucket).build());
    }

    @Override
    public void createBucket(String bucket) throws Exception {
        minioClient.makeBucket(MakeBucketArgs.builder().bucket(bucket).build());
    }

    @Override
    public ObjectWriteResponse putStream(String bucket, InputStreamObject fileObject) throws Exception {

        PutObjectArgs.Builder builder = PutObjectArgs.builder()
                .bucket(bucket)
                .object(fileObject.getObjectName());
        // content type
        String contentType = fileObject.getContentType();
        if (StringUtils.hasText(contentType)) {
            builder.contentType(fileObject.getContentType());
        }

        InputStream inputStream = fileObject.getInputStream();
        long size = fileObject.getSize();
        if (size > 0) {
            builder.stream(inputStream, size, -1);
        } else {
            builder.stream(inputStream, -1, fileObject.getPartSize());
        }

        return minioClient.putObject(builder.build());
    }

    @Override
    public void getStreamToRead(String bucket, String objectName, Consumer<InputStream> readStream) throws Exception {
        try (InputStream stream = minioClient.getObject(
                GetObjectArgs.builder()
                        .bucket(bucket)
                        .object(objectName)
                        .build())) {
            // Read data from stream
            readStream.accept(stream);
        }
    }

    @Override
    public ObjectWriteResponse uploadFile(String bucket, FileObject fileObject) throws Exception {
        UploadObjectArgs.Builder builder = UploadObjectArgs.builder()
                .bucket(bucket)
                .object(fileObject.objectName())
                .filename(fileObject.file());

        return minioClient.uploadObject(builder.build());
    }

    @Override
    public StatObjectResponse getFileStat(String bucket, String fileObjName) throws Exception {
        return minioClient.statObject(
                StatObjectArgs.builder().bucket(bucket).object(fileObjName).build()
        );
    }


    @Override
    public String getViewUrl(String bucket, String fileObjName, Duration duration) throws Exception {
        GetPresignedObjectUrlArgs presignedObjectUrlArgs = GetPresignedObjectUrlArgs.builder()
                .method(Method.GET)
                .bucket(bucket)
                .object(fileObjName)
                .expiry(duration.value(), duration.timeUnit())
                .build();

        return minioClient.getPresignedObjectUrl(presignedObjectUrlArgs);
    }

    @Override
    public void downloadFileTo(String bucket, String fileObjName, String outPutFile) throws Exception {
        minioClient.downloadObject(
                DownloadObjectArgs.builder()
                        .bucket(bucket)
                        .object(fileObjName)
                        .filename(outPutFile)
                        .build());
    }

    @Override
    public void removeFile(String bucket, String fileObjName) throws Exception {
        minioClient.removeObject(
                RemoveObjectArgs.builder().bucket(bucket).object(fileObjName).build()
        );
    }

}
