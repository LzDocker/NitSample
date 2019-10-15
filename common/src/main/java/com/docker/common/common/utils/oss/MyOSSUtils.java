package com.docker.common.common.utils.oss;

import android.content.Context;
import android.util.Log;
import com.alibaba.sdk.android.oss.ClientConfiguration;
import com.alibaba.sdk.android.oss.ClientException;
import com.alibaba.sdk.android.oss.OSS;
import com.alibaba.sdk.android.oss.OSSClient;
import com.alibaba.sdk.android.oss.ServiceException;
import com.alibaba.sdk.android.oss.callback.OSSCompletedCallback;
import com.alibaba.sdk.android.oss.callback.OSSProgressCallback;
import com.alibaba.sdk.android.oss.common.OSSLog;
import com.alibaba.sdk.android.oss.common.auth.OSSCredentialProvider;
import com.alibaba.sdk.android.oss.common.auth.OSSPlainTextAKSKCredentialProvider;
import com.alibaba.sdk.android.oss.model.PutObjectRequest;
import com.alibaba.sdk.android.oss.model.PutObjectResult;
import java.text.SimpleDateFormat;
import java.util.Date;
import static com.docker.common.common.config.ThiredPartModel.FLODER;
import static com.docker.common.common.config.ThiredPartModel.FLODERVIDEO;
import static com.docker.common.common.config.ThiredPartModel.P_BUCKETNAME;
import static com.docker.common.common.config.ThiredPartModel.accessKeyId;
import static com.docker.common.common.config.ThiredPartModel.accessKeySecret;
import static com.docker.common.common.config.ThiredPartModel.endpoint;
public class MyOSSUtils {


    private static MyOSSUtils instance;
    private OSS oss;
    private SimpleDateFormat simpleDateFormat;
    public MyOSSUtils() {

    }
    public static MyOSSUtils getInstance() {
        if (instance == null) {
            if (instance == null) {
                return new MyOSSUtils();
            }
        }
        return instance;
    }

    private void getOSs(Context context) {
        OSSCredentialProvider credentialProvider = new OSSPlainTextAKSKCredentialProvider(accessKeyId, accessKeySecret);
        ClientConfiguration conf = new ClientConfiguration();
        conf.setConnectionTimeout(15 * 1000); // 连接超时，默认15秒
        conf.setSocketTimeout(15 * 1000); // socket超时，默认15秒
        conf.setMaxConcurrentRequest(5); // 最大并发请求书，默认5个
        conf.setMaxErrorRetry(2); // 失败后最大重试次数，默认2次
        OSSLog.enableLog();
        oss = new OSSClient(context.getApplicationContext(), endpoint, credentialProvider, conf);
        if (simpleDateFormat == null) {
            simpleDateFormat = new SimpleDateFormat("yyyyMMdd");
        }
    }

    /**
     * 上传图片 上传文件
     *
     * @param context       application上下文对象
     * @param ossUpCallback 成功的回调
     * @param img_name      上传到oss后的文件名称，图片要记得带后缀 如：.jpg
     * @param imgPath       图片的本地路径
     */

    public void upImage(Context context, final String img_name, String imgPath, final MyOSSUtils.OssUpCallback ossUpCallback) {

        getOSs(context);

        final Date data = new Date();

        data.setTime(System.currentTimeMillis());

        PutObjectRequest putObjectRequest = new PutObjectRequest(P_BUCKETNAME, FLODER + simpleDateFormat.format(data) + "/" + img_name, imgPath);
        putObjectRequest.setProgressCallback(new OSSProgressCallback<PutObjectRequest>() {
            @Override
            public void onProgress(PutObjectRequest request, long currentSize, long totalSize) {
                ossUpCallback.inProgress(currentSize, totalSize);
            }
        });

        oss.asyncPutObject(putObjectRequest, new OSSCompletedCallback<PutObjectRequest, PutObjectResult>() {

            @Override
            public void onSuccess(PutObjectRequest request, PutObjectResult result) {

                Log.e("MyOSSUtils", "------getRequestId:" + result.getRequestId());

                ossUpCallback.successImg(oss.presignPublicObjectURL(P_BUCKETNAME, FLODER + simpleDateFormat.format(data) + "/" + img_name));

            }

            @Override
            public void onFailure(PutObjectRequest request, ClientException clientException, ServiceException serviceException) {
                ossUpCallback.successImg(null);
            }
        });

    }

    /**
     * 上传图片 上传流
     *
     * @param context       application上下文对象
     * @param ossUpCallback 成功的回调
     * @param img_name      上传到oss后的文件名称，图片要记得带后缀 如：.jpg
     * @param imgbyte       图片的byte数组
     */

    public void upImage(Context context, final String img_name, byte[] imgbyte, final MyOSSUtils.OssUpCallback ossUpCallback) {

        getOSs(context);

        final Date data = new Date();

        data.setTime(System.currentTimeMillis());

        PutObjectRequest putObjectRequest = new PutObjectRequest(P_BUCKETNAME, FLODER + simpleDateFormat.format(data) + "/" + img_name, imgbyte);

        putObjectRequest.setProgressCallback(new OSSProgressCallback<PutObjectRequest>() {
            @Override
            public void onProgress(PutObjectRequest request, long currentSize, long totalSize) {
                ossUpCallback.inProgress(currentSize, totalSize);
            }
        });

        oss.asyncPutObject(putObjectRequest, new OSSCompletedCallback<PutObjectRequest, PutObjectResult>() {
            @Override
            public void onSuccess(PutObjectRequest request, PutObjectResult result) {
                Log.e("MyOSSUtils", "------getRequestId:" + result.getRequestId());
                ossUpCallback.successImg(oss.presignPublicObjectURL(P_BUCKETNAME, FLODER + simpleDateFormat.format(data) + "/" + img_name));
            }

            @Override
            public void onFailure(PutObjectRequest request, ClientException clientException, ServiceException serviceException) {
                ossUpCallback.successImg(null);
            }
        });

    }

    /**
     * 上传视频
     *
     * @param context       application上下文对象
     * @param ossUpCallback 成功的回调
     * @param video_name    上传到oss后的文件名称，视频要记得带后缀 如：.mp4
     * @param video_path    视频的本地路径
     */

    public void upVideo(Context context, final String video_name, String video_path, final MyOSSUtils.OssUpCallback ossUpCallback) {

        getOSs(context);

        final Date data = new Date();

        data.setTime(System.currentTimeMillis());

        PutObjectRequest putObjectRequest = new PutObjectRequest(P_BUCKETNAME, FLODERVIDEO + simpleDateFormat.format(data) + "/" + video_name, video_path);

        putObjectRequest.setProgressCallback(new OSSProgressCallback<PutObjectRequest>() {
            @Override
            public void onProgress(PutObjectRequest request, long currentSize, long totalSize) {
                ossUpCallback.inProgress(currentSize, totalSize);
            }
        });
        oss.asyncPutObject(putObjectRequest, new OSSCompletedCallback<PutObjectRequest, PutObjectResult>() {
            @Override
            public void onSuccess(PutObjectRequest request, PutObjectResult result) {
                ossUpCallback.successVideo(oss.presignPublicObjectURL(P_BUCKETNAME, FLODERVIDEO + simpleDateFormat.format(data) + "/" + video_name));
            }

            @Override
            public void onFailure(PutObjectRequest request, ClientException clientException, ServiceException serviceException) {
                ossUpCallback.successVideo(null);
            }
        });

    }


    public interface OssUpCallback {

        void successImg(String img_url);

        void successVideo(String video_url);

        void inProgress(long progress, long zong);

    }
}
