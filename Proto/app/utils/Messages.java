package utils;

import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import play.Play;

/**
 * ブランド別文言出力ユーティリティ.
 */
public final class Messages {
    private static Messages instance = new Messages();
    private static Properties myConf;
    private static Map<String, Properties> brandMap;

    /**
     * インスタンス取得処理
     * @return ブランド別文言出力ユーティリティインスタンス
     */
    public static Messages getInstance() {
        return instance;
    }

    private Messages() {

        // ブランド文言マップの初期化
        brandMap = new HashMap<String, Properties>();

        // デフォルトメッセージファイル定義の読み込み。
        Properties sysDefaults = new Properties();
        try {
            sysDefaults.load(new InputStreamReader(Play.application()
                    .resourceAsStream("messages"), "UTF-8"));
            brandMap.put("default", sysDefaults);
        } catch (Exception e) {
            System.out
                    .println("System Defaults File Load Error !! Use the Program Defaults.");
        }

        // 読み込みメッセージリスト
        // 最終的に存在するメッセージファイル全部を読み込む処理になる、はず。
        List<String> lstMessages = new ArrayList<String>();
        lstMessages.add("tcom");
        lstMessages.add("tnc");

        // ブランド別メッセージファイル定義の読み込み。
        for (String brand : lstMessages) {
            try {
                myConf = new Properties(sysDefaults);
                myConf.load(new InputStreamReader(Play.application()
                        .resourceAsStream("messages." + brand), "UTF-8"));
                brandMap.put(brand, myConf);
            } catch (Exception e) {
                System.out
                        .println("User Configuration File Load Error !! Ignore the User Customize."
                                + "[" + brand + "]");
            }
        }

    }

    /**
     * メッセージ出力処理.
     * 
     * @param key メッセージID
     * @param args 置換用オブジェクト
     * @return メッセージ文字列
     */
    public static String get(String key, Object... args) {
        if (key == null) {
            return "";
        }
        String brand = play.mvc.Http.Context.current().session().get("brand");
        Properties p = brandMap.get("default");
        if (brandMap.containsKey(brand)) {
            p = brandMap.get(brand);
        }

        if (!p.containsKey(key)) {
            return "メッセージＩＤ : 「" + key + "」は定義されていません。";
        }

        // 置換用オブジェクトがない場合、文字列をそのまま返却する。
        if (args.length == 0) {
            return p.getProperty(key);
        }
        return String.format(p.getProperty(key), args);
    }
}
