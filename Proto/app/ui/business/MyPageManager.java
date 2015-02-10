package ui.business;

import java.util.List;

import ui.models.MyPageSetting;

/**
 * マイページ設定制御処理
 *
 */
public final class MyPageManager {
    private MyPageManager() {
    }

    /**
     * 
     * @param userId ユーザID
     * @param settingId 設定ID
     * @param kinouId 機能ID
     * @return List<SubframeSetting> サブフレーム設定情報のリスト
     */
    public static List<MyPageSetting> load(String userId, String settingId,
            String kinouId) {
        if (kinouId == null) {
            return MyPageManager.load(userId, settingId);
        }
        return MyPageSetting.find.where().eq("userId", userId)
                .eq("settingId", settingId).eq("kinouId", kinouId).findList();
    }

    /**
     * マイページ設定取得処理
     * @param userId ユーザID
     * @param settingId 設定ID
     * @return マイページ設定リスト
     */
    public static List<MyPageSetting> load(String userId, String settingId) {
        if (settingId == null) {
            return MyPageManager.load(userId);
        }
        return MyPageSetting.find.where().eq("userId", userId)
                .eq("settingId", settingId).findList();
    }

    /**
     * マイページ設定取得処理
     * @param userId ユーザID
     * @return マイページ設定リスト
     */
    public static List<MyPageSetting> load(String userId) {
        return MyPageSetting.find.where().eq("userId", userId).findList();
    }

    /**
     * マイページ設定登録処理
     * @param settings マイページ設定リスト
     * @return 登録結果（true:登録成功)
     */
    public static boolean save(List<MyPageSetting> settings) {
        for (MyPageSetting setting : settings) {
            if (setting.id == null
                    || MyPageSetting.find.byId(setting.id) == null) {
                setting.save();
            } else {
                setting.update();
            }
        }
        return true;
    }
}
