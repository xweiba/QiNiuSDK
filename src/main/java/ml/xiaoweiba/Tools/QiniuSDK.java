package ml.xiaoweiba.Tools;

import com.google.gson.Gson;
import com.qiniu.common.QiniuException;
import com.qiniu.common.Zone;
import com.qiniu.http.Response;
import com.qiniu.storage.BucketManager;
import com.qiniu.storage.Configuration;
import com.qiniu.storage.UploadManager;
import com.qiniu.storage.model.DefaultPutRet;
import com.qiniu.util.Auth;
import org.apache.commons.fileupload.disk.DiskFileItem;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.UUID;

/**
 * @program: QINIUSDKImage
 * @description: 七牛云工具类
 * @author: Mr.xweiba
 * @create: 2018-06-01 00:18
 **/

public class QiniuSDK {
    private String ak;
    private String sk;
    private String bucketname;    //空间名
    private Auth auth;
    private String upToken;
    private String key = null;    //默认不指定key的情况下，以文件内容的hash值作为文件名
    private Configuration cfg = new Configuration(Zone.zone0());
    private UploadManager uploadManager = new UploadManager(cfg);
    private BucketManager bucketManager;

    QiniuSDK(String ak, String sk, String bucketname){
        this.ak = ak;
        this.sk = sk;
        this.bucketname = bucketname;
        this.auth = Auth.create(ak, sk);
        this.upToken = auth.uploadToken(bucketname);
        bucketManager = new BucketManager(auth, cfg);
    }

    public boolean delete(String keyFile){
        try {
            bucketManager.delete(bucketname, keyFile);
            return true;
        } catch (QiniuException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean updateFile(MultipartFile multipartFile){
        DiskFileItem fi = MultFileToIoFile.multipartFile(multipartFile);
        Response qresponse;
        try {
            qresponse = uploadManager.put(fi.getInputStream(),key,upToken,null,null);
            System.out.println(qresponse.bodyString());
            DefaultPutRet putRet = new Gson().fromJson(qresponse.bodyString(), DefaultPutRet.class);
            System.out.println(putRet.key);
            return true;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return false;
    }
}
