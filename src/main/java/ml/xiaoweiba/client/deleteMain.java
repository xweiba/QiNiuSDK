package ml.xiaoweiba.client;

import com.qiniu.common.QiniuException;
import com.qiniu.common.Zone;
import com.qiniu.storage.BucketManager;
import com.qiniu.storage.Configuration;
import com.qiniu.util.Auth;

/**
 * @program: QINIUSDKImage
 * @description: 删除类测试
 * @author: Mr.xweiba
 * @create: 2018-05-31 17:38
 **/

public class deleteMain {
    String ak = "";
    String sk = "";    // 密钥配置
    Auth auth = Auth.create(ak, sk);

    String bucketName = "image";
    String keyFile = "1.jpg";
    // 构造一个带指定Zone对象的配置类 Zone为服务器地区
    Configuration cfg = new Configuration(Zone.zone0());
    BucketManager bucketManager = new BucketManager(auth, cfg);

    public boolean delete(){
        try {
            bucketManager.delete(bucketName, keyFile);
            return true;
        } catch (QiniuException e) {
            e.printStackTrace();
            return false;
        }
    }

    public static void main(String[] args) {
        boolean be = new deleteMain().delete();
        System.out.println(be);
    }
}
