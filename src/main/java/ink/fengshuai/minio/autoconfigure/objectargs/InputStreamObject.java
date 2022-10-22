package ink.fengshuai.minio.autoconfigure.objectargs;

import lombok.Data;

import java.io.InputStream;

@Data
public class InputStreamObject {
    private String objectName;
    private InputStream inputStream;
    private String contentType;

    private InputStreamObject(String objectName, InputStream inputStream, String contentType) {
        this.objectName = objectName;
        this.inputStream = inputStream;
        this.contentType = contentType;
    }

    private InputStreamObject(String objectName, InputStream inputStream) {
        this.objectName = objectName;
        this.inputStream = inputStream;
    }

    public static InputStreamObject create(String objectName, InputStream inputStream, String contentType) {
        return new InputStreamObject(objectName, inputStream, contentType);
    }

    public static InputStreamObject create(String objectName, InputStream inputStream) {
        return new InputStreamObject(objectName, inputStream);
    }

    private long size = -1;
    /**
     * 当size未知时，须设置partSize，控制内存使用。默认10m
     */
    private long partSize = 10485760;

}
