package business;

import java.util.List;

import models.entities.KokyakuInfo;
import common.BusinessResult;
import common.BusinessResult.ResultCode;

/**
 * パーマネント情報抽出クラス.
 * <p>
 * パーマネント情報の抽出を行う業務部品
 * </p>
 * @author 甲斐正之
 * @version 0.1　2014/05/15　新規作成
 */
public final class SelectPermanentInfo {
    private SelectPermanentInfo() {
    }

    /**
     * 顧客情報抽出.
     * <p>
     * 顧客情報のＤＢ部品を呼び出し、<br>
     * 検索結果を返却する。
     * </p>
     * @param kokyakuId 顧客ＩＤ
     * @param keiyakuId 契約ＩＤ
     * @return 顧客情報抽出結果
     * @throws Exception ○○例外
     * @author 甲斐正之
     * @version 0.1　2014/05/15　新規作成
     */
    public static BusinessResult<KokyakuInfo> selectKokyakuInfo(String kokyakuId, String keiyakuId) throws Exception {

        BusinessResult<KokyakuInfo> result = new BusinessResult<KokyakuInfo>();

        try {
            // 顧客情報の検索を実行する
            List<KokyakuInfo> listKi = KokyakuInfo.find
                    .fetch("keiyakuInfo")
                    .fetch("keiyakuInfo.serviceInfo")
                    .fetch("keiyakuInfo.serviceInfo.serviceMaster")
                    .fetch("keiyakuInfo.serviceInfo.dependService1")
                    .fetch("keiyakuInfo.serviceInfo.dependService2")
                    .where().eq("kokyakuId", kokyakuId).eq("keiyakuInfo.keiyakuId", keiyakuId)
                    .findList();

            if (listKi != null && listKi.size() > 0) {
                result.setValue(listKi.get(0));
            }
            result.setResultCode(ResultCode.Success);
        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        }

        return result;
    }

}
