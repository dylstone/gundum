package business;

import java.util.List;

import models.entities.NewServiceMaster;

import common.BusinessResult;
import common.BusinessResult.ResultCode;

/**
 * サービスマスタ取得処理
 * <p>
 * サービスマスタのＤＢ部品を呼び出し、<br>
 * 検索結果を返却する業務部品
 * </p>
 * @author 那須 智貴
 * @version 0.1　2014/07/18　新規作成
 */
public final class SelectServiceMaster {
    private SelectServiceMaster() {
    }

    /**
     * サービスマスタ取得処理（全取得）
     * <p>
     * サービスマスタのＤＢ部品を呼び出し、<br>
     * 検索結果を返却する。
     * </p>
     * @return サービスマスタ抽出結果
     * @throws Exception ○○例外
     * @author 那須 智貴
     * @version 0.1　2014/07/18　新規作成
     */
    public static BusinessResult<List<NewServiceMaster>> all() throws Exception {

        BusinessResult<List<NewServiceMaster>> result = new BusinessResult<List<NewServiceMaster>>();

        try {
            // サービスマスタ検索を実行する
            List<NewServiceMaster> lstServMaster = NewServiceMaster.find.findList();

            //if (ki == null) {
            if (lstServMaster == null || lstServMaster.size() == 0) {
                // サービスマスタが取得できなかった場合
                result.setResultCode(ResultCode.BusinessError);
            } else {
                // サービスマスタが取得できた場合
                result.setValue(lstServMaster);
                result.setResultCode(ResultCode.Success);
            }
        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        }

        return result;
    }

}
