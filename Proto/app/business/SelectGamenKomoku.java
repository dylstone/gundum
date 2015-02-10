package business;

import java.util.List;

import models.entities.GamenItemControlInfo;
import common.BusinessResult;
import common.BusinessResult.ResultCode;

/**
 * 画面項目制御情報抽出.
 * <p>
 * 画面項目制御情報のＤＢ部品を呼び出し、<br>
 * 検索結果を返却する業務部品
 * </p>
 * @author 甲斐正之
 * @version 0.1　2014/02/25　新規作成
 */
public final class SelectGamenKomoku {
    private SelectGamenKomoku() {
    }

    /**
     * 画面項目制御情報抽出.
     * <p>
     * 画面項目制御情報のＤＢ部品を呼び出し、<br>
     * 検索結果を返却する。
     * </p>
     * @param subFrameId サブフレームＩＤ
     * @return 画面項目制御情報抽出結果
     * @throws Exception ○○例外
     * @author 甲斐正之
     * @version 0.1　2014/02/25　新規作成
     */
    public static BusinessResult<List<GamenItemControlInfo>> selectGamenControlInfo(String subFrameId) throws Exception {

        BusinessResult<List<GamenItemControlInfo>> result = new BusinessResult<List<GamenItemControlInfo>>();

        try {
            // 画面項目制御情報の検索を実行する
            List<GamenItemControlInfo> gicList = GamenItemControlInfo.find.where()
                    .eq("primaryKey.subFrameId", subFrameId).findList();

            result.setValue(gicList);
            result.setResultCode(ResultCode.Success);

        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        }

        return result;
    }

}
