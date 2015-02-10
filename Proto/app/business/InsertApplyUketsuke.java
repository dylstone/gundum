package business;

import javax.persistence.OptimisticLockException;

import models.entities.ApplyInfo;
import models.entities.ApplyUketsukeKey;

import common.BusinessResult;
import common.BusinessResult.ResultCode;
import common.business.IdKanri;

import constants.DbDataValue.IdCd;

/**
 * 申込受付キー登録業務部品.
 * <p>
 * 申込受付キーのＤＢ部品を呼び出し、<br>
 * INSERTを実行する業務部品
 * </p>
 * @author 那須　智貴
 * @version 0.1　2014/07/25　新規作成
 */
public final class InsertApplyUketsuke {
    private InsertApplyUketsuke() {
    }

    /**
     * 申込受付キー登録.
     * <p>
     * 申込受付キーのＤＢ部品を呼び出し、<br>
     * 登録を実行する
     * </p>
     * @param applyInfo 申込履歴
     * @param strBrandCd ブランドコード
     * @return 処理結果
     * @throws Exception ○○例外
     * @author 那須　智貴
     * @version 0.1　2014/07/25　新規作成
     */
    public static BusinessResult<String> insertApplyUketsukeKey(ApplyInfo applyInfo, String strBrandCd, String strOperatorId, String strKokyakuId, String strKeiyakuId) throws Exception {
        
        // インスタンス生成
        BusinessResult<String> result = new BusinessResult<String>();
        ApplyUketsukeKey applyUketsukeKey = new ApplyUketsukeKey();
        
        try {
            applyUketsukeKey.activityId = "010";
            applyUketsukeKey.applyId = applyInfo.applyId;
            applyUketsukeKey.brandCd = strBrandCd;
            applyUketsukeKey.keiyakuId = strKeiyakuId;
            applyUketsukeKey.kokyakuId = strKokyakuId;
            applyUketsukeKey.queueId = IdKanri.getId(IdCd.QUEUE);
            applyUketsukeKey.serviceId = applyInfo.newApplyServiceInfo.get(0).serviceId;
            applyUketsukeKey.createUser = strOperatorId;
            applyUketsukeKey.lastUpdateUser = strOperatorId;
            applyUketsukeKey.deleteDate = null;
            // 申込受付キー登録
            applyUketsukeKey.save();

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
     * 申込受付キー更新.
     * <p>
     * 申込受付キーのＤＢ部品を呼び出し、<br>
     * 更新を実行する
     * </p>
     * @param id 申込受付キーID
     * @return 処理結果
     * @throws Exception ○○例外
     * @author 甲斐正之
     * @version 0.1　2014/05/15　新規作成
     */
    public static BusinessResult<String> updateApplyUketsukeKey(String id) throws Exception {

        BusinessResult<String> result = new BusinessResult<String>();

        try {

            ApplyUketsukeKey key = ApplyUketsukeKey.find.byId(id);

            key = updateApplyUketsukeStatus(key);

            key.update();

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
     * 申込受付キー更新.
     * <p>
     * 申込受付キーの更新対象項目に値を設定する
     * </p>
     * @param auKey 申込受付キー
     * @return 申込受付キー
     * @author 甲斐正之
     * @version 0.1　2014/05/15　新規作成
     */
    private static ApplyUketsukeKey updateApplyUketsukeStatus(ApplyUketsukeKey auKey) {

        auKey.activityId = "030";
        auKey.lastUpdateUser = "BatchUser";
        return auKey;

    }
    
    /**
     * 申込受付キー作成
     * <p>
     * 申込受付キーのＤＢ部品を呼び出し、<br>
     * INSERTを実行する
     * </p>
     * @param auKey 申込受付キー
     * @return 処理結果
     * @throws Exception ○○例外
     * @author 甲斐正之
     * @version 0.1　2014/05/15　新規作成
     */
    public static BusinessResult<String> updateApplyUketsukeKey(ApplyUketsukeKey auKey) throws Exception {

        BusinessResult<String> result = new BusinessResult<String>();

        try {

            auKey = updateApplyUketsukeStatus(auKey);

            auKey.update();

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

}