package validate.views.helper;

import java.util.List;

import play.data.Form.Field;
import play.libs.F.Tuple;

/**
 * バリデーションヘルパ
 */
public final class ValidateHelper {
    private static ValidateHelper instance = new ValidateHelper();

    private ValidateHelper() {
    }

    /**
     * インスタンス取得
     * @return インスタンス
     */
    public static ValidateHelper getInstance() {
        return instance;
    }

    /**
     * jQuery Validation Engine用判定クラスのデフォルト出力
     * 
     * @param field メッセージID
     * @param defClass 置換用オブジェクト
     * @return メッセージ文字列
     */
    public static String getValidateClass(Field field, String... defClass) {
        String res = "";

        List<Tuple<String, List<Object>>> as = field.constraints();
        StringBuilder sb = new StringBuilder();

        // Pattern（正規表現）バリデーションの対応
        String reg = "";// 対象バリデーション文字列

        for (Tuple<String, List<Object>> tuple : as) {
            // 対応する判定はすべてこのように読み込みを行うこと。
            if ("constraint.required".equals(tuple._1)) {
                sb.append("required").append(",");
            }

            if ("constraint.min".equals(tuple._1)) {
                sb.append("min").append(tuple._2).append(",");
            }
            if ("constraint.max".equals(tuple._1)) {
                sb.append("max").append(tuple._2).append(",");
            }
            if ("constraint.minLength".equals(tuple._1)) {
                sb.append("minSize").append(tuple._2).append(",");
            }
            if ("constraint.maxLength".equals(tuple._1)) {
                sb.append("maxSize").append(tuple._2).append(",");
            }
            if ("constraint.email".equals(tuple._1)) {
                sb.append("custom[email]").append(",");
            }
            if ("constraint.pattern".equals(tuple._1)) {
                sb.append("funcCall[myReg]").append(",");
                reg = (tuple._2).get(0).toString();
            }
            if (tuple._1 instanceof String
                    && tuple._1.startsWith("constraint.custom.")) {

                sb.append("custom[")
                        .append(tuple._1.substring("constraint.custom."
                                .length())).append("]").append(",");
            }
        }

        String strClass = "";
        if (defClass.length > 0 && !defClass[0].isEmpty()) {
            strClass = defClass[0];
        }

        if (sb.length() > 0) {
            String validateClass = String.format("validate[%s]", sb
                    .deleteCharAt(sb.length() - 1).toString());
            if (strClass.isEmpty()) {
                strClass = validateClass;
            } else {
                strClass = "\"" + strClass + " " + validateClass + "\"";
            }
        }

        if (!strClass.isEmpty()) {
            res = "class=" + strClass + "";
        }

        if (!reg.isEmpty()) {
            res = res + " data-reg=" + reg + "";
        }

        return res;

    }
}
