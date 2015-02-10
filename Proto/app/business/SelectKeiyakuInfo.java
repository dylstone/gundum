package business;

import java.util.List;

import models.sqlrows.KokyakuKeiyakuInfo;
import common.BusinessResult;
import common.BusinessResult.ResultCode;

/**
 * 契約情報抽出.
 * <p>
 * 契約情報一覧に表示するデータ抽出のＤＢ部品を呼び出し、<br>
 * 検索結果を返却する業務部品
 * </p>
 * @author 那須智貴
 * @version 0.1　2014/04/07　新規作成
 */
public final class SelectKeiyakuInfo {
    private SelectKeiyakuInfo() {
    }

    /**
     * 顧客契約情報抽出.
     * <p>
     * 契約情報一覧に表示するデータ抽出のＤＢ部品を呼び出し、<br>
     * 検索結果を返却する。
     * </p>
     * @param strKeiyakuId 顧客ＩＤ
     * @return 契約情報抽出結果
     * @throws Exception ○○例外
     * @author 那須智貴
     * @version 0.1　2014/02/25　新規作成
     * @version 0.3　2014/03/26　STEP3対応
     */
    public static BusinessResult<List<KokyakuKeiyakuInfo>> selectKeiyakuResultList(String strKeiyakuId)
            throws Exception {

        BusinessResult<List<KokyakuKeiyakuInfo>> result = new BusinessResult<List<KokyakuKeiyakuInfo>>();

        try {
            // 顧客契約情報の検索を実行する
            List<KokyakuKeiyakuInfo> resultlist;

            if (!strKeiyakuId.isEmpty()) {
                resultlist = KokyakuKeiyakuInfo.find()
                        .where().eq("keiyakuId", strKeiyakuId)
                        .findList();
            } else {
                resultlist = KokyakuKeiyakuInfo.find()
                        .findList();
            }

            result.setValue(resultlist);
            result.setResultCode(ResultCode.Success);

        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        }

        return result;
    }

}
