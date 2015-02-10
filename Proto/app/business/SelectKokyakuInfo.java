package business;

import java.util.List;

import models.entities.KokyakuInfo;
import play.data.Form;

import common.BusinessResult;
import common.BusinessResult.ResultCode;

import constants.Message;

/**
 * 顧客情報抽出.
 * <p>
 * 顧客情報のＤＢ部品を呼び出し、<br>
 * 検索結果を返却する業務部品
 * </p>
 * @author 甲斐正之
 * @version 0.1　2014/02/25　新規作成
 * @version 0.3　2014/03/26　STEP3対応
 * @version 0.4　2014/04/16　STEP4対応
 */
public final class SelectKokyakuInfo {
    private SelectKokyakuInfo() {
    }

    /**
     * 顧客情報抽出.
     * <p>
     * 顧客情報のＤＢ部品を呼び出し、<br>
     * 検索結果を返却する。
     * </p>
     * @param kokyakuId 顧客ＩＤ
     * @return 顧客情報抽出結果
     * @throws Exception ○○例外
     * @author 甲斐正之
     * @version 0.1　2014/02/25　新規作成
     * @version 0.3　2014/03/26　STEP3対応
     * @version 0.4　2014/04/16　STEP4対応
     */
    public static BusinessResult<Form<KokyakuInfo>> selectKokyakuInfo(String kokyakuId) throws Exception {

        BusinessResult<Form<KokyakuInfo>> result = new BusinessResult<Form<KokyakuInfo>>();

        try {
            // 顧客情報の検索を実行する
            //KokyakuInfo ki = KokyakuInfo.find.byId(kokyakuId);
            // 2014/07/23 サービスの持ち方を変更 START
//            List<KokyakuInfo> listKi = KokyakuInfo.find
//                    .fetch("keiyakuInfo")
//                    .fetch("keiyakuInfo.serviceInfo")
//                    .fetch("keiyakuInfo.serviceInfo.serviceMaster")
//                    .fetch("keiyakuInfo.serviceInfo.dependService1")
//                    .fetch("keiyakuInfo.serviceInfo.dependService2")
//                    .where().eq("kokyakuId", kokyakuId)
//                    .findList();
            List<KokyakuInfo> listKi = KokyakuInfo.find
                  .fetch("keiyakuInfo")
                  .fetch("keiyakuInfo.serviceInfo")
                  .fetch("keiyakuInfo.serviceInfo.svcKaisen")
                  .fetch("keiyakuInfo.serviceInfo.svcKaisen.serviceMaster")
                  .fetch("keiyakuInfo.serviceInfo.svcKaisen.serviceMaster.serviceRyoukin")
                  .fetch("keiyakuInfo.serviceInfo.svcMail")
                  .fetch("keiyakuInfo.serviceInfo.svcMail.serviceMaster")
                  .fetch("keiyakuInfo.serviceInfo.svcMail.serviceMaster.serviceRyoukin")
                  .fetch("keiyakuInfo.serviceInfo.svcSecurity")
                  .fetch("keiyakuInfo.serviceInfo.svcSecurity.serviceMaster")
                  .fetch("keiyakuInfo.serviceInfo.svcSecurity.serviceMaster.serviceRyoukin")
                  .fetch("keiyakuInfo.serviceInfo.svcOption")
                  .fetch("keiyakuInfo.serviceInfo.svcOption.serviceMaster")
                  .fetch("keiyakuInfo.serviceInfo.svcOption.serviceMaster.serviceRyoukin")
                  .fetch("keiyakuInfo.serviceInfo.svcSettingOption")
                  .fetch("keiyakuInfo.serviceInfo.svcSettingOption.serviceMaster")
                  .where().eq("kokyakuId", kokyakuId)
                  .findList();
            // 2014/07/23 サービスの持ち方を変更 END
            //if (ki == null) {
            if (listKi == null || listKi.size() == 0) {
                // 顧客情報が取得できなかった場合
                result.setResultCode(ResultCode.BusinessError);
                result.setMessage(Message.MSGID_MKK0001);
            } else {
                // 顧客情報が取得できた場合
                Form<KokyakuInfo> form = new Form<KokyakuInfo>(KokyakuInfo.class);
                form = form.fill(listKi.get(0));
                result.setValue(form);
                result.setResultCode(ResultCode.Success);
            }
        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        }

        return result;
    }

}
