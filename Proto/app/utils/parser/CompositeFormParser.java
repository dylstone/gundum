package utils.parser;

import java.util.HashMap;
import java.util.Map;

/**
 * 複合フォームパーサ
 */
public class CompositeFormParser {

    /**
     * リクエストパラメータから該当サブフレーム/画面部品のパラメータ項目を抽出する。
     * @param map リクエストパラメータマップ
     * @param subframeId サブフレームID
     * @param partId 画面部品ID
     * @return 該当サブフレーム/画面部品に該当するリクエストパラメータマップ
     */
    public Map<String, String[]> getForm(Map<String, String[]> map,
            String subframeId, String partId) {
        HashMap<String, String[]> result = new HashMap<String, String[]>();

        // マップが空の場合、空のマップを返す。
        if (map == null) {
            return result;
        }

        // 拡張for文（for-each)でループ
        // サブフレームID.partID.で始まる項目のみマップにつめて返す。
        String prefix = subframeId + "." + partId + ".";
        for (Map.Entry<String, String[]> e : map.entrySet()) {
            if (e.getKey().startsWith(prefix)) {
                String key = e.getKey().substring(prefix.length());
                if (!result.containsKey(key)) {
                    String values = "";
                    for (String s : e.getValue()) {
                        values += s + " ";
                    }
                    System.out.println(key + ":" + values);
                    result.put(key, e.getValue());
                } else {
                    System.out.println("[サブフレームID:" + subframeId + "  /画面部品ID:"
                            + partId + "]に入力要素の重複があります。");
                }
            }
        }

        return result;
    }
}
