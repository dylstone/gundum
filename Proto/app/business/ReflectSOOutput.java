package business;

import java.util.List;

import models.entities.SOOutput;
import common.BusinessResult;
import common.BusinessResult.ResultCode;
import constants.DbDataValue.ShoriStatus;

/**
 * ＳＯ結果反映業務部品.
 * <p>
 * ＳＯ結果のＤＢ部品を呼び出し、<br>
 * 結果を反映を実行する業務部品
 * </p>
 * @author 江川
 * @version 0.1　2014/05/13　新規作成
 */
public final class ReflectSOOutput {
    private ReflectSOOutput() {
    }

    /**
     * ＳＯ結果反映.
     * <p>
     * ＳＯ結果のＤＢ部品を呼び出し、<br>
     * 結果を反映する
     * </p>
     * @param soId SOID
     * @return 結果コード
     * @throws Exception ○○例外
     * @author 江川
     * @version 0.1　2014/05/13　新規作成
     */
    public static BusinessResult<SOOutput> reflectSOOutput(String soId) throws Exception {

        // 戻り値の生成
        BusinessResult<SOOutput> result = new BusinessResult<SOOutput>();
        SOOutput info = new SOOutput();

        try {

            // ＳＯ結果に対しステータスが受信済みのデータを検索する
            List<SOOutput> soResult = SOOutput.find.where().eq("soId", soId)
                    .eq("status", ShoriStatus.MISHORI).findList();

            if (soResult.size() == 1) {

                // データが取得できた場合、ＳＯ結果のステータスを更新する
                info = soResult.get(0);
                info = setSOOutput(info);
                info.update();
                result.setValue(info);

            } else {

                // データが取得できなかった場合、ＳＯ結果のステータスを更新しない

            }

            // 結果コードを設定する
            result.setResultCode(ResultCode.Success);

        } catch (Exception e) {
            e.printStackTrace();
            result.setResultCode(ResultCode.BusinessError);

        }

        return result;

    }

    /**
     * ＳＯ結果更新情報設定.
     * <p>
     * ＳＯ結果の更新項目に値を設定する
     * </p>
     * @return SO結果情報
     * @author 江川
     * @version 0.1　2014/05/13　新規作成
     */
    private static SOOutput setSOOutput(SOOutput info) {

        //		SOOutput info = new SOOutput();
        info.status = ShoriStatus.SHORIZUMI;
        info.lastUpdateUser = "BatchUser";

        return info;

    }

}
