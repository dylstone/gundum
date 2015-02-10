/**
 * 
 */
package utils;

import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * 変換クラス
 * <p>
 * 変換を取り扱う
 * </p>
 * @author 那須智貴
 * @version 0.1　2014/03/17　新規作成
 */
public final class Formatter {
    private Formatter() {
    }

    private static final String DEFAULT_FORMAT = "yyyy-MM-dd HH:mm:ss";

    /**
     * 日付変換
     * <p>
     * 日付のフォーマット変換を行う。（文字列⇒文字列）
     * </p>
     * @param strDate 日付文字列
     * @param strFormat 日付型
     * @return 変換後の日付文字列
     * @author 那須智貴
     * @version 0.1　2014/03/17　新規作成
     */
    public static String convDateFormat(String strDate, String strFormat) {

        try {
            SimpleDateFormat sdf = new SimpleDateFormat(DEFAULT_FORMAT);
            Date date = sdf.parse(strDate);
            return new SimpleDateFormat(strFormat).format(date);
        } catch (Exception e) {
            return strDate;
        }
    }

    /**
     * 日付変換
     * <p>
     * 日付のフォーマット変換を行う。（文字列⇒文字列）
     * </p>
     * @param strDate 変換前（yyyyMMdd）
     * @return 変換後（yyyy年MM月dd日）
     * @author 那須智貴
     * @version 0.1　2014/03/17　新規作成
     */
    public static String convStrToStrDate(String strDate) {

        try {
            return String.format("%s年%s月%s日", strDate.substring(0, 0 + 4), strDate.substring(4, 4 + 2),
                    strDate.substring(6, 6 + 2));
        } catch (Exception e) {
            return strDate;
        }
    }

    /**
     * 符号負数値変換
     * <p>
     * 3桁ごとにカンマ（数値⇒文字列）
     * </p>
     * @param iValue 変換前（#####）
     * @return 変換後（###,##）
     * @author 那須智貴
     * @version 0.1　2014/03/17　新規作成
     */
    public static String convAddComma(int iValue) {

        try {
            NumberFormat nfNum = NumberFormat.getNumberInstance();
            return nfNum.format(iValue);

        } catch (Exception e) {
            return null;
        }

    }

}
