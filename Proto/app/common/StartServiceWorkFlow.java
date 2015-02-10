package common;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

import models.entities.ProgressQueue;
import models.entities.TransServiceInfo;
import common.BusinessResult.ResultCode;

/**
 * サービスワークフロー起動クラス.
 * <p>
 * サービスワークフロー定義ファイルを実行し、
 * 手続き処理を開始する。
 * </p>
 * @author 甲斐正之
 * @version 0.1　2014/05/15　新規作成
 */
public final class StartServiceWorkFlow {
    private StartServiceWorkFlow() {
    }

    /**
     * サービスワークフロー起動処理.
     * <p>
     * サービスワークフロー定義呼出を行う。
     * </p>
     * @param queue 手続き進捗キュー
     * @param tsInfo トランザクション情報(サービス情報)
     * @return 処理結果
     * @throws Exception ○○例外
     * @author 甲斐正之
     * @version 0.1　2014/05/15　新規作成
     */
    public static BusinessResult<String> startWorkFlow(ProgressQueue queue, TransServiceInfo tsInfo) throws Exception {

        BusinessResult<String> result = new BusinessResult<String>();

        // 定義ファイルの指定
        String command = "perl";
        //String PATH = "c:\\script\\service_work_flow.pl";
        String path = tsInfo.swfFileInfo;

        // 前回の起動時刻より起動間隔以上経過しているか判定する
        Calendar nowDate = Calendar.getInstance();
        Calendar lastRunDate = Calendar.getInstance();
        lastRunDate.setTime(tsInfo.lastRunDate);
        lastRunDate.add(Calendar.MINUTE, Integer.parseInt(tsInfo.intervalTime));

        if (nowDate.compareTo(lastRunDate) > 0)
        {
            try {
                // サービスワークフロー定義を実行する
                ProcessBuilder pb = new ProcessBuilder(command, path, tsInfo.soStatus, queue.queueId);
                Process p = pb.start();
                //if (p.waitFor() == 200) {
                if (p.waitFor() == 0) {
                    InputStream is = p.getInputStream();
                    BufferedReader reader = new BufferedReader(new InputStreamReader(is));
                    String s = reader.readLine();
                    int code = startController(s);

                    result.setResultCode(ResultCode.Success);
                    result.setValue(String.valueOf(code));

                } else {
                    result.setResultCode(ResultCode.BusinessError);
                }

                //result.setValue(String.valueOf(p.exitValue()));

            } catch (Exception e) {
                e.printStackTrace();
                throw e;
            }
        } else {
            result.setResultCode(ResultCode.Success);
        }

        return result;

    }

    private static int startController(String strContent) {

        Map<String, String[]> params = checkContent(strContent);
        String path = params.get("path")[0];

        String urlString = "http://localhost:9000/" + path;
        int code = 0;

        try {

            URL url = new URL(urlString);

            HttpURLConnection uc = (HttpURLConnection) url.openConnection();
            uc.setDoOutput(true);//POST可能にする
            uc.setRequestMethod("POST");
            uc.setRequestProperty("Host", "localhost");// ヘッダを設定
            uc.setRequestProperty("Content-Type", "text/plain");
            uc.setRequestProperty("Content-Length", String.valueOf(strContent.length()));
            uc.setRequestProperty("Accept-Charset", "utf-8");

            OutputStream os = uc.getOutputStream();//POST用のOutputStreamを取得

            PrintStream ps = new PrintStream(os);
            ps.print(strContent);//データをPOSTする
            ps.close();

            code = uc.getResponseCode();

        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return code;
    }

    private static Map<String, String[]> checkContent(String strContent) {

        Map<String, String[]> map = new HashMap<String, String[]>();
        String[] params = strContent.split("&");

        for (String param : params) {
            String[] key = param.split("=");
            if (key.length == 2) {
                String[] value = key[1].split(",");
                map.put(key[0], value);
            }
        }

        return map;
    }

}