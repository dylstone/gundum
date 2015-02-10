package common;

/**
 * 業務部品実行結果クラス.
 * <p>
 * コントローラへ返却する業務部品の実行結果を格納する。
 * </p>
 * @param <T> 対象クラス型
 * @author 甲斐正之
 * @version 0.1　2014/02/28　新規作成
 */
public class BusinessResult<T> {

    /** 結果コード */
    private ResultCode resultCode;

    /** メッセージ */
    private String message;

    /** 取得データ */
    private T value;

    /**
     * 結果コードに格納するコードの列挙型
     */
    public enum ResultCode {
        /** 成功 */
        Success,
        /** 業務エラー */
        BusinessError,
        /** システムエラー */
        SystemError
    }

    /**
     * @return 結果コード
     */
    public ResultCode getResultCode() {
        return resultCode;
    }

    /**
     * @param resultCode 結果コード
     */
    public void setResultCode(ResultCode resultCode) {
        this.resultCode = resultCode;
    }

    /**
     * @return メッセージ
     */
    public String getMessage() {
        return message;
    }

    /**
     * @param message メッセージ
     */
    public void setMessage(String message) {
        this.message = message;
    }

    /**
     * @return 取得データ
     */
    public T getValue() {
        return value;
    }

    /**
     * @param value 取得データ
     */
    public void setValue(T value) {
        this.value = value;
    }
}
