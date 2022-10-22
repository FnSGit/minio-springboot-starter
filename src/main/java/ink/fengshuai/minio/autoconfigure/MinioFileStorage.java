package ink.fengshuai.minio.autoconfigure;

import ink.fengshuai.minio.autoconfigure.objectargs.Duration;
import ink.fengshuai.minio.autoconfigure.objectargs.FileObject;
import ink.fengshuai.minio.autoconfigure.objectargs.InputStreamObject;
import io.minio.ObjectWriteResponse;
import io.minio.StatObjectResponse;

import java.io.InputStream;
import java.util.concurrent.TimeUnit;
import java.util.function.Consumer;

public interface MinioFileStorage {

    boolean isExistBucket(String bucket) throws Exception;

    void createBucket(String bucket) throws Exception;

    ObjectWriteResponse putStream(String bucket, InputStreamObject inputStreamObject) throws Exception;

    void getStreamToRead(String bucket, String objectName, Consumer<InputStream> readStream) throws Exception;

    ObjectWriteResponse uploadFile(String bucket, FileObject fileObject) throws Exception;


    StatObjectResponse getFileStat(String bucket, String fileObjName) throws Exception;

    String getViewUrl(String bucket, String fileObjName, Duration duration) throws Exception;

    default String getViewUrl(String bucket, String fileObjName) throws Exception {
        return getViewUrl(bucket, fileObjName, new Duration(TimeUnit.DAYS, 7));
    }

    void downloadFileTo(String bucket, String fileObjName, String outPutFile) throws Exception;

    void removeFile(String bucket, String fileObjName) throws Exception;
}
