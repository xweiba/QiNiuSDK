package ml.xiaoweiba.client;



import com.qiniu.util.Auth;
import com.qiniu.util.Base64;
import com.qiniu.util.StringMap;
import com.qiniu.util.UrlSafeBase64;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;

import java.io.File;
import java.io.FileInputStream;

/**
 * @program: QINIUSDKImage
 * @description: 文件上传测试
 * @author: Mr.xweiba
 * @create: 2018-05-31 15:41
 **/

public class UpdateMain {
    String ak = "";
    String sk = "";    // 密钥配置
    Auth auth = Auth.create(ak, sk);    // TODO Auto-generated constructor stub
    String bucketname = "image";    //空间名
    String key = "1.txt";    //需要替换的图片名
    StringMap putPolicy  = new StringMap();



    public String getUpToken() {
        putPolicy.put("returnBody", "{\"key\":\"$(key)\",\"hash\":\"$(etag)\",\"bucket\":\"$(bucket)\",\"fsize\":$(fsize)}");
        // 安全策略 上传的key文件，不允许修改
        // putPolicy.put("insertOnly", 1);
        String upToken = auth.uploadToken(bucketname, key, 3600, putPolicy);
        System.out.println("upToken: " + upToken);
        return upToken;
    }

    public void put64image() throws Exception {
        String file = "D:\\1.txt";//图片路径
        FileInputStream fis = null;
        int l = (int) (new File(file).length());
        byte[] src = new byte[l];
        fis = new FileInputStream(new File(file));
        fis.read(src);
        String file64 = Base64.encodeToString(src, 0);
        String url = "http://upload.qiniup.com/putb64/" + l+"/key/"+ UrlSafeBase64.encodeToString(key);
        //非华东空间需要根据注意事项 1 修改上传域名
        RequestBody rb = RequestBody.create(null, file64);
        Request request = new Request.Builder().
                url(url).
                addHeader("Content-Type", "application/octet-stream")
                .addHeader("Authorization", "UpToken " + getUpToken())
                .post(rb).build();
        System.out.println(request.headers());
        OkHttpClient client = new OkHttpClient();
        okhttp3.Response response = client.newCall(request).execute();
        System.out.println(response);
    }
    public static void main(String[] args) throws Exception {
        new UpdateMain().put64image();
    }

}
