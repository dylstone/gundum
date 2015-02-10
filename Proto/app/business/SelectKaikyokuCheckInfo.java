package business;

import java.util.List;

import models.entities.ServiceMaster;
import common.BusinessResult;
import common.BusinessResult.ResultCode;

/**
 * 開局チェック情報取得.
 * <p>
 * サービスマスタのＤＢ部品を呼び出し、<br>
 * 検索結果を返却する業務部品
 * </p>
 * @author 甲斐正之
 * @version 0.1　2014/03/14　新規作成
 */
public final class SelectKaikyokuCheckInfo {
    private SelectKaikyokuCheckInfo() {
    }

    /**
     * 開局チェック情報取得.
     * <p>
     * サービスマスタのＤＢ部品を呼び出し、<br>
     * 検索結果を返却する。
     * </p>
     * @return サービスマスタ検索結果
     * @throws Exception ○○例外
     * @author 甲斐正之
     * @version 0.1　2014/03/14　新規作成
     */
    public static BusinessResult<List<ServiceMaster>> selectKaikyokuCheckInfo()
            throws Exception {

        BusinessResult<List<ServiceMaster>> result = new BusinessResult<List<ServiceMaster>>();
        String bunrui = "0001";

        try
        {
            // サービスマスタの検索を実行する
            List<ServiceMaster> smList = ServiceMaster.find.where().eq("serviceBunrui", bunrui).findList();

            result.setResultCode(ResultCode.Success);
            result.setValue(smList);

        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        }

        return result;
    }

}
