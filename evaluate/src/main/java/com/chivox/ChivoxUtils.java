package com.chivox;

import android.app.Application;

import com.dcbfhd.utilcode.utils.LogUtils;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.util.Arrays;

/**
 * kxf -> 2019-09-19
 **/
public class ChivoxUtils {


    public static AIRecorder recorderApp = null;
    public static  long engine;
    public static String serialNumber,deviceId;
    public static String appKey = "1568857255000032", secretKey = "17302830e14c5aa40ea07c4a32ad9bb8";

    private void getEngine(Application application) {
        byte buf[] = new byte[1024];
        AIEngine.aiengine_get_device_id(buf, application);
        String deviceId = new String(buf).trim();
        LogUtils.e("deviceId: " + deviceId);

        String sig = String.format("{\"appKey\":\"%s\",\"secretKey\":\"%s\",\"userId\":\"%s\"}", "1568857255000032", "17302830e14c5aa40ea07c4a32ad9bb8", "testUser");
        JSONObject sig_json = null;
        try {
            sig_json = new JSONObject(sig);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        byte cfg_b[] = Arrays.copyOf(sig_json.toString().getBytes(), 1024);
        int ret = AIEngine.aiengine_opt(0, 6, cfg_b, 1024);
        String serialNumberInfo = "";
        if (ret > 0) {
            serialNumberInfo = new String(cfg_b, 0, ret);
        } else {
            serialNumberInfo = new String(cfg_b);
        }
        String serialNumber = null;
        try {
            serialNumber = (new JSONObject(serialNumberInfo)).getString("serialNumber");
        } catch (JSONException e1) {
            e1.printStackTrace();
        }
        LogUtils.i("serialNumber: " + serialNumber);

        String resourcePath = AIEngineHelper.extractResourceOnce(application, "aiengine.resource.zip", true);

        String provisionPath = AIEngineHelper.extractResourceOnce(application, "aiengine.provision", false);

        String path = AIEngineHelper.getFilesDir(application).getPath();
        String cfg = String.format("{\"prof\":{\"enable\":1, \"output\":\"" + path + "/log.log\"}, \"appKey\": \"%s\", \"secretKey\": \"%s\", \"provision\": \"%s\", \"native\": {\"en.pred.exam\":{\"res\": \"%s\"},\"en.sent.score\":{\"res\": \"%s\"},\"en.word.score\":{\"res\": \"%s\"}}}",
                "1568857255000032", "17302830e14c5aa40ea07c4a32ad9bb8",
                provisionPath,
                new File(resourcePath, "exam/bin/eng.pred.aux.P3.V4.12").getAbsolutePath(),
                new File(resourcePath, "eval/bin/eng.snt.g4.P3.N1.0.2").getAbsolutePath(),
                new File(resourcePath, "eval/bin/eng.wrd.g4.P3.N1.0.2").getAbsolutePath());

        long engine = AIEngine.aiengine_new(cfg, application);
        LogUtils.i("aiengine_new: " + cfg);
        LogUtils.i("aiengine: " + engine);

    }
}
