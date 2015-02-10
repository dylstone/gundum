package utils;

/**
 * ログ出力ユーティリティ
 */
public final class GLog {

    private GLog() {
    }

    /**
     * ログ出力処理
     * @param message 出力メッセージ
     */
    public static void write(String message) {
        System.out.println(message);
    }

}
