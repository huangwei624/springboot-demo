package com.middleyun.net;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.arronlong.httpclientutil.HttpClientUtil;
import com.arronlong.httpclientutil.common.HttpConfig;
import com.arronlong.httpclientutil.exception.HttpProcessException;
import lombok.Data;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class BatchSendCmd {

    private static ExecutorService threadPool = Executors.newFixedThreadPool(20);

    /**
     * 并发动态区指令
     */
    private void sendDynamicAreaCmd() {
        for (int i = 0; i < 9; i++) {
            threadPool.submit(new CmdDynamicAreaRunnable(i+1));
        }
    }

    /**
     * 并发创建节目
     */
    private void sendProgramCmd() {
        for (int i = 0; i < 9; i++) {
            threadPool.submit(new CmdProgramRunnable(i+2, i+1));
        }
    }

    @Data
    static class CmdDynamicAreaRunnable implements Runnable{
        private int cardNo;

        public CmdDynamicAreaRunnable(int cardNo) {
            this.cardNo = cardNo;
        }

        @Override
        public void run() {
            HashMap<String, Object> map = new HashMap<>();
            map.put("cardNo", "0000000" + cardNo);
            map.put("blockNo", 3);
            map.put("content", "空位42");
            try {
                HttpClientUtil.post(HttpConfig.custom().url("http://localhost:86/card/refreshDynamicArea").json(JSON.toJSONString(map)));
                System.out.println("发送请求");
            } catch (HttpProcessException e) {
                e.printStackTrace();
            }
        }
    }


    @Data
    static class CmdProgramRunnable implements Runnable {
        private int cardNo;
        private int id;

        public CmdProgramRunnable(int id, int cardNo) {
            this.id = id;
            this.cardNo = cardNo;
        }

        @Override
        public void run() {
            try {
                // 读取配置
                String res = HttpClientUtil.get(HttpConfig.custom().url("http://localhost:86/card/readScreenConfig/" + this.id));
                Map map = JSONObject.parseObject(res, Map.class);
                String content = (String)map.get("obj");
//                System.out.println(content);

                // 生效配置
                HashMap<String, Object> formData = new HashMap<>();
                formData.put("id", this.id);
                formData.put("configContent", content);
                HttpClientUtil.post(HttpConfig.custom().url("http://localhost:86/card/takeEffectConfig").map(formData));

            } catch (HttpProcessException e) {
                e.printStackTrace();
            }
        }
    }

    public static void main(String[] args) {
        BatchSendCmd batchSendCmd = new BatchSendCmd();
        batchSendCmd.sendDynamicAreaCmd();
        batchSendCmd.sendProgramCmd();
    }
}
