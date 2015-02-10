/**
 * 
 */
package constants;

/**
 *  メッセージIDConst
 *  <p>
 *  メッセージIDのConst値を定義する。
 *  </p>
 * @author 那須
 * @version 0.1 2014/04/11 新規作成
 */
public final class Message {
    private Message() {
    }

    /** システムに異常が発生しました。 */
    public static final String MSGID_MCM0001 = "MCM0001";
    /** IDの採番ができませんでした。 */
    public static final String MSGID_MCM0002 = "MCM0002";
    /** ログインID、またはパスワードが間違っています。 */
    public static final String MSGID_MUI0001 = "MUI0001";
    /** 顧客情報が取得できません。 */
    public static final String MSGID_MKK0001 = "MKK0001";
    /** 申込情報の登録に失敗しました。 */
    public static final String MSGID_MKK0002 = "MKK0002";
    /** 表示するデータはありません。 */
    public static final String MSGID_MKK0003 = "MKK0003";
    /** すでに更新されていますので、更新できません。再度検索し直してください。 */
    public static final String MSGID_MKK0004 = "MKK0004";
}
