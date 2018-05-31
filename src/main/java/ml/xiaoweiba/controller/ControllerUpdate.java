package ml.xiaoweiba.controller;

import com.google.gson.Gson;
import com.qiniu.common.Zone;
import com.qiniu.http.Response;
import com.qiniu.storage.Configuration;
import com.qiniu.storage.UploadManager;
import com.qiniu.storage.model.DefaultPutRet;
import com.qiniu.util.Auth;

import org.apache.commons.fileupload.disk.DiskFileItem;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

import javax.servlet.http.HttpServletRequest;

import java.io.File;
import java.io.IOException;
import java.util.UUID;

/**
 * @program: QINIUSDKImage
 * @description: Controller 上传图片测试
 * @author: Mr.xweiba
 * @create: 2018-05-31 23:05
 **/
@Controller
public class ControllerUpdate {
    // @RequestMapping("/")
    // public String index(){
    //     return "index";
    // }
    @RequestMapping(value = "/editItemsSubmit", produces = "application/json; charset=utf-8")
    @ResponseBody
    public String editItemsSubmit(Model model, HttpServletRequest request,
                                  //接收图片地址
                                  MultipartFile item_pic) throws Exception {


        //上传的图片的原始名称
        String originalFilename = item_pic.getOriginalFilename();

        //上传图片
        if(item_pic!=null&&originalFilename!=null&&originalFilename.length()>0) {
            //存储的物理路径
            String pic_path = "D:\\tomcat\\upload\\pic\\";

            //新的图片名称 生成随机名
            // UUID.randomUUID() 生成随机数
            // originalFilename.lastIndexOf(".") 返回originalFilename字符串出现点的字符串的下标
            // originalFilename.substring(int i) 返回originalFilename下标 i 前的字符串
            // originalFilename.substring(originalFilename.lastIndexOf(".")) 截取图片不包含后缀的名字
            String newFileName = UUID.randomUUID() + originalFilename.substring(originalFilename.lastIndexOf("."));

            // 创建文件
            File newFile = new File(pic_path + newFileName);



            //将内存中的图片数据写入硬盘
            item_pic.transferTo(newFile);

            //将新的图片名称写入到itemsCustom
            // itemsCustom.setPic(newFileName);
        }
        //加否则判断如果用户未改动图片会报错
        // }else {
        //     throw new CustomException("图片上传错误");
        // }

        //调用service更新商品信息,页面需要将商品信息传到此方法
        // itemsCustom.getCreatetime();
        //更新数据, 图片路径提交已在sql中屏蔽,待处理
        // itemsService.updateItems(id, itemsCustom);
        //重定向到显示列表 重定向会在本根目录重定向,不需要加前缀,且需要加如后缀
        return "上传成功";
    }

    @RequestMapping("/updaImage")
    @ResponseBody
    public boolean updaImage(Model model, HttpServletRequest request,
                             //接收图片地址
                             MultipartFile item_pic){
        CommonsMultipartFile cf = (CommonsMultipartFile)item_pic;
        DiskFileItem fi = (DiskFileItem) cf.getFileItem();

        Configuration cfg = new Configuration(Zone.zone0());
        UploadManager uploadManager = new UploadManager(cfg);

        String ak = "";
        String sk = "";    // 密钥配置
        Auth auth = Auth.create(ak, sk);    // TODO Auto-generated constructor stub
        String bucketname = "image";    //空间名
        String key = null;    //需要替换的图片名

        String upToken = auth.uploadToken(bucketname);
        Response qresponse;

        try {
            qresponse = uploadManager.put(fi.getInputStream(),key,upToken,null,null);

            DefaultPutRet putRet = new Gson().fromJson(qresponse.bodyString(), DefaultPutRet.class);

            System.out.println(putRet.key);
            System.out.println(putRet.hash);
            return true;

        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }
}
