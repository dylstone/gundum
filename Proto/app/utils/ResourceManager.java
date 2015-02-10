package utils;

/**
 * リソース管理ユーティリティ
 */
public final class ResourceManager {
    private ResourceManager() {
    }

    /**
     * ユニークＩＤ取得処理
     * @return ユニークＩＤ
     */
    public static String getUUID() {
        String uuid = "";

        Integer i = 0;
        // キャッシュ上にユニークなUUIDを作成する。
        do {
            GLog.write("UUID発行:施行" + (++i));
            uuid = java.util.UUID.randomUUID().toString();
        } while (CacheManager.existUser(uuid) || !CacheManager.initUser(uuid));

        GLog.write("UUID発行成功");
        return uuid;
    }

}
