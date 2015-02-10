package business;

import javax.persistence.OptimisticLockException;

import models.entities.TransServiceInfo;
import common.BusinessResult;
import common.BusinessResult.ResultCode;
import constants.DbDataValue.IFKey;

/**
 * トランザクション情報更新業務部品.
 * <p>
 * トランザクション情報(サービス情報)のＤＢ部品を呼び出し、<br>
 * 更新を実行する業務部品
 * </p>
 * @author 甲斐正之
 * @version 0.1　2014/05/15　新規作成
 */
public final class UpdateTransactionInfo {
    private UpdateTransactionInfo() {
    }

    /**
     * トランザクション情報更新.
     * <p>
     * トランザクション情報(サービス情報)のＤＢ部品を呼び出し、<br>
     * 更新を実行する
     * </p>
     * @param transInfo トランザクション情報(サービス情報)
     * @return 処理結果
     * @throws Exception ○○例外
     * @author 甲斐正之
     * @version 0.1　2014/05/15　新規作成
     */
    public static BusinessResult<String> updateTransactionInfo(TransServiceInfo transInfo) throws Exception {

        BusinessResult<String> result = new BusinessResult<String>();

        try {

            transInfo = updateTransactionStatus(transInfo);
            transInfo.update();

            result.setResultCode(ResultCode.Success);

        } catch (OptimisticLockException e) {
            e.printStackTrace();
            result.setResultCode(ResultCode.BusinessError);
        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        }

        return result;

    }

    /**
     * トランザクション情報更新.
     * <p>
     * トランザクション情報の更新対象項目に値を設定する
     * </p>
     * @param info トランザクション情報
     * @return トランザクション情報
     * @author 甲斐正之
     * @version 0.1　2014/05/15　新規作成
     */
    private static TransServiceInfo updateTransactionStatus(TransServiceInfo transInfo) {

        transInfo.tourokuFlg = "1";
        transInfo.soStatus = IFKey.SHOKI;
        transInfo.lastUpdateUser = "BatchUser";
        return transInfo;

    }

}