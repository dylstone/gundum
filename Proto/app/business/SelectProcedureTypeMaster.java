package business;

import models.entities.ApplyServiceInfo;
import models.entities.ProcedureTypeMaster;
import models.entities.ProcedureTypeMasterPK;

import common.BusinessResult;
import common.BusinessResult.ResultCode;

/**
 * 手続き種別マスタ抽出業務部品.
 * <p>
 * 手続き種別マスタのＤＢ部品を呼び出し、<br>
 * 手続き種別マスタを抽出する業務部品
 * </p>
 * @author 甲斐正之
 * @version 0.1　2014/07/07　新規作成
 */
public final class SelectProcedureTypeMaster {
    private SelectProcedureTypeMaster() {
    }

    /**
     * 手続き種別マスタ抽出.
     * <p>
     * 手続き種別マスタのＤＢ部品を呼び出し、<br>
     * 手続き種別マスタを抽出する
     * </p>
     * @param asInfo 申込情報
     * @return 処理結果
     * @throws Exception ○○例外
     * @author 甲斐正之
     * @version 0.1　2014/07/07　新規作成
     */
    public static BusinessResult<ProcedureTypeMaster> selectProcedureTypeMaster(ApplyServiceInfo asInfo)
            throws Exception {

        // インスタンス生成
        BusinessResult<ProcedureTypeMaster> result = new BusinessResult<ProcedureTypeMaster>();

        try {

            // 申込情報のサービスコードをキーとして手続き種別マスタを検索する。
            ProcedureTypeMasterPK pk = new ProcedureTypeMasterPK(
                    asInfo.serviceMaster.serviceCd, "3", "1");

            ProcedureTypeMaster master = ProcedureTypeMaster.find.byId(pk);

            if (master == null) {
                // 手続き種別マスタが取得できなかった場合
                result.setResultCode(ResultCode.BusinessError);
            } else {
                result.setValue(master);
                result.setResultCode(ResultCode.Success);
            }

        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        }

        return result;
    }
    
    /**
     * 手続き種別マスタ抽出.
     * <p>
     * 手続き種別マスタのＤＢ部品を呼び出し、<br>
     * 手続き種別マスタを抽出する
     * </p>
     * @param serviceCd サービスコード
     * @return 処理結果
     * @throws Exception ○○例外
     * @author 甲斐正之
     * @version 0.1　2014/07/07　新規作成
     */
    public static BusinessResult<ProcedureTypeMaster> selectProcedureTypeMaster(String serviceCd)
            throws Exception {

        // インスタンス生成
        BusinessResult<ProcedureTypeMaster> result = new BusinessResult<ProcedureTypeMaster>();

        try {

            // 申込情報のサービスコードをキーとして手続き種別マスタを検索する。
            ProcedureTypeMasterPK pk = new ProcedureTypeMasterPK(
            		serviceCd, "3", "1");

            ProcedureTypeMaster master = ProcedureTypeMaster.find.byId(pk);

            if (master == null) {
                // 手続き種別マスタが取得できなかった場合
                result.setResultCode(ResultCode.BusinessError);
            } else {
                result.setValue(master);
                result.setResultCode(ResultCode.Success);
            }

        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        }

        return result;
    }

}