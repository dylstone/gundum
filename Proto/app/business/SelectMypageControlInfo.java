package business;

import java.util.List;

import models.entities.MypageControlInfo;
import common.BusinessResult;
import common.BusinessResult.ResultCode;

/**
 * マイページ制御情報抽出.
 * <p>
 * マイページ制御情報のＤＢ部品を呼び出し、<br>
 * 検索結果を返却する業務部品
 * </p>
 * @author 甲斐正之
 * @version 0.1　2014/02/28　新規作成
 * @version 0.3　2014/03/18　STEP3対応
 */
public final class SelectMypageControlInfo {
    private SelectMypageControlInfo() {
    }

    /**
     * マイページ制御情報抽出.
     * <p>
     * マイページ制御情報のＤＢ部品を呼び出し、<br>
     * 検索結果を返却する。
     * </p>
     * @param operatorId オペレータＩＤ
     * @param function 機能
     * @return マイページ制御情報抽出結果
     * @throws Exception ○○例外
     * @author 甲斐正之
     * @version 0.1　2014/02/28　新規作成
     * @version 0.3　2014/03/18　STEP3対応
     */
    public static BusinessResult<List<MypageControlInfo>> selectMypageControlInfo(String operatorId, String function)
            throws Exception {

        BusinessResult<List<MypageControlInfo>> result = new BusinessResult<List<MypageControlInfo>>();

        try {
            // マイページ制御情報の検索を実行する
            List<MypageControlInfo> mciList = MypageControlInfo.find.where().eq("primaryKey.operatorId", operatorId)
                    .eq("primaryKey.function", function).findList();

            result.setValue(mciList);
            result.setResultCode(ResultCode.Success);

        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        }

        return result;
    }

}
