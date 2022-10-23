package ink.fengshuai.minio.autoconfigure;

import ink.fengshuai.minio.autoconfigure.objectargs.FileObject;
import ink.fengshuai.minio.autoconfigure.objectargs.InputStreamObject;
import io.minio.ObjectWriteResponse;
import io.minio.StatObjectResponse;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.File;
import java.io.FileInputStream;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest()
@Slf4j
class MinioFileStorageTest {

    @Autowired
    private MinioFileStorage fileStorage;

    private static final String DEFAULT_BUCKET = "pictures-bucket";

    private static final String remoteImgUrl = "https://cn.bing.com/images/search?view=detailV2&ccid=8%2Ftj6wLt&id=C53158597C98AC52343244131D5DD081DC10D92D&thid=OIP-C.8_tj6wLtybfLjREgvB-1CwHaMW&mediaurl=https%3A%2F%2Fwww.mms591.com%2Fwww.mms591.com-photo%2F20200705%2F1-200F5163049-50_480x800.jpg&exph=800&expw=480&q=%e5%a3%81%e7%ba%b8%e9%ab%98%e6%b8%85%e5%85%a8%e5%b1%8f&simid=608051521475647567&form=IRPRST&ck=3BBA53468D181D8E6BDD07325D906C3A&selectedindex=210&ajaxhist=0&ajaxserp=0&vt=0&sim=11";
    private static final String localFile = "C:\\Users\\26964\\Pictures\\Saved Pictures\\武动乾坤-绫清竹.jpg";
    private static final String localFileName = "武动乾坤-绫清竹.jpg";


    @Test
    void isExistBucket() throws Exception {

        assertTrue(fileStorage.isExistBucket(DEFAULT_BUCKET));

    }

    @Test
    void createBucket() throws Exception {

        fileStorage.createBucket(DEFAULT_BUCKET);
    }

    @Test
    void putFile() throws Exception {
        File file = new File(localFile);
        String objName = "20221022-1/" + file.getName();
        FileInputStream fileInputStream = new FileInputStream(file);
        InputStreamObject fileObject = new InputStreamObject(objName, fileInputStream);
        fileObject.setSize(fileObject.getSize());
        ObjectWriteResponse objectWriteResponse = fileStorage.putStream(DEFAULT_BUCKET, fileObject);
        assertEquals(objectWriteResponse.object(), objName);
    }

    @Test
    void uploadFile() throws Exception {

        String objName = "20221022-2/" + localFileName;

        FileObject fileObject = new FileObject(objName, localFile);

        ObjectWriteResponse objectWriteResponse = fileStorage.uploadFile(DEFAULT_BUCKET, fileObject);
        assertEquals(objectWriteResponse.object(), objName);
    }

    @Test
    void getFileStat() throws Exception {
        String objName = "20221022-2/" + localFileName;
        StatObjectResponse fileStat = fileStorage.getFileStat(DEFAULT_BUCKET, objName);
        assertEquals(objName, fileStat.object());
    }

    @Test
    void getViewUrl() throws Exception {
        String objName = "20221022-2/" + localFileName;
        String viewUrl = fileStorage.getViewUrl(DEFAULT_BUCKET, objName);
        assertNotNull(viewUrl);
        log.info("url:" + viewUrl);
    }


    @Test
    void downloadFileTo() throws Exception {
        String objName = "20221022-2/" + localFileName;
        fileStorage.downloadFileTo(DEFAULT_BUCKET, objName, "./" + localFileName);
    }
}