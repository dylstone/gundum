package utils;

import play.cache.Cache;

/**
 * キャッシュ処理ラップ
 * 
 * @see 排他処理を組み込む。
 */
public final class CacheManager {

    /**
     * 
     */
    private CacheManager() {
    }

    /**
     * キャッシュ中のユーザ情報存在チェック
     * @param uuid ユーザのユニークID
     * @return チェック結果（true:存在する)
     */
    public static boolean existUser(String uuid) {

        Object o = Cache.get("uuid:" + uuid);
        if (o == null) {
            return false;
        }
        return true;
    }

    /**
     * ユニークIDのキャッシュ処理初期化
     * @param uuid ユーザのユニークID
     * @return 初期化結果（true:セッションの初期化完了）
     */
    public static boolean initUser(String uuid) {
        try {
            Cache.set("uuid:" + uuid, "foo");
        } catch (Exception e) {
            // もし、UUIDが重複したら、例外が発生する。
            return false;
        }
        return true;
    }

}
