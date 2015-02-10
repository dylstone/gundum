package business;

import models.entities.SOInput;
import common.BusinessResult;
import common.BusinessResult.ResultCode;
import common.business.IdKanri;
import constants.DbDataValue.IdCd;
import constants.DbDataValue.ShoriStatus;

/**
 * ＳＯ指示登録業務部品.
 * <p>
 * ＳＯ指示のＤＢ部品を呼び出し、<br>
 * 登録を実行する業務部品
 * </p>
 * @author 江川
 * @version 0.1　2014/05/13　新規作成
 */
public final class InsertSOInput {
    private InsertSOInput() {
    }

    /**
     * ＳＯ指示情報登録.
     * <p>
     * ＳＯ指示のＤＢ部品を呼び出し、<br>
     * 登録を実行する
     * </p>
     * @param kokyakuId 顧客ID
     * @param keiyakuId 契約ID
     * @param serviceId サービスID
     * @param transactionId トランザクションID
     * @return 結果コード
     * @throws Exception ○○例外
     * @author 江川
     * @version 0.1　2014/05/13　新規作成
     * @version 0.2  2014/05/21  トランザクションIDの引数追加
     */
    public static BusinessResult<String> insertSOInput(
            String kokyakuId, String keiyakuId, String serviceId, String transactionId) throws Exception {

        // 戻り値の生成
        BusinessResult<String> result = new BusinessResult<String>();
        SOInput info = new SOInput();

        try {

            // ＳＯ指示の登録情報を設定する
            info = setSOInput(kokyakuId, keiyakuId, serviceId, transactionId);

            // ＳＯ指示情報を登録する
            info.save();

            // 結果コードを設定する
            result.setResultCode(ResultCode.Success);
            result.setValue(info.soId);

        } catch (Exception e) {
            e.printStackTrace();
            result.setResultCode(ResultCode.BusinessError);

        }

        return result;

    }

    /**
     * ＳＯ指示情報設定.
     * <p>
     * ＳＯ指示に値を設定する
     * </p>
     * @param 顧客ID
     * @param 契約ID
     * @param サービスID
     * @param トランザクションID
     * @return SO指示情報
     * @author 江川
     * @version 0.1　2014/05/13　新規作成
     * @version 0.2  2014/05/21  トランザクションIDの引数追加
     */
    private static SOInput setSOInput(
            String kokyakuId, String keiyakuId, String serviceId, String transactionId) {

        SOInput info = new SOInput();
        info.soId = IdKanri.getId(IdCd.SO);
        info.kokyakuId = kokyakuId;
        info.keiyakuId = keiyakuId;
        info.serviceId = serviceId;
        info.transactionId = transactionId;
        info.status = ShoriStatus.MISHORI;
        info.deleteDate = null;
        info.createUser = "BatchUser";
        info.lastUpdateUser = "BatchUser";

        return info;

    }

}