package business;

import java.util.List;

import models.entities.ApplyInfo;
import play.data.Form;

import common.BusinessResult;
import common.BusinessResult.ResultCode;

import constants.DbDataValue.ApplyStatus;

/**
 * 申込顧客情報検索業務部品.
 * <p>
 * 申込情報のＤＢ部品を呼び出し、<br>
 * 検索結果を返却する業務部品
 * </p>
 * @author 甲斐正之
 * @version 0.1　2014/04/11　新規作成
 */
public final class SelectApplyKokyakuInfo {
    private SelectApplyKokyakuInfo() {
    }

    /**
     * 申込顧客情報検索.
     * <p>
     * 申込情報のＤＢ部品を呼び出し、<br>
     * 検索結果を返却する
     * </p>
     * @param kokyakuId 顧客ID
     * @param keiyakuId 契約ID
     * @return 申込情報
     * @throws Exception ○○例外
     * @author 甲斐正之
     * @version 0.1　2014/04/11　新規作成
     */
    public static BusinessResult<Form<ApplyInfo>> selectApplyKokyakuInfo(String kokyakuId, String keiyakuId)
            throws Exception {

        BusinessResult<Form<ApplyInfo>> result = new BusinessResult<Form<ApplyInfo>>();

        try {

            // 申込情報の検索を実行する
//            List<ApplyInfo> listAp = ApplyInfo.find
//                    .fetch("applyKokyakuInfo")
//                    .fetch("applyKeiyakuInfo")
//                    .fetch("applyServiceInfo")
//                    .fetch("applyServiceInfo.serviceMaster")
//                    .fetch("applyServiceInfo.dependService1")
//                    .fetch("applyServiceInfo.dependService2")
//                    .where().eq("kokyakuInfo.kokyakuId", kokyakuId).eq("keiyakuInfo.keiyakuId", keiyakuId)
//                    .eq("applyStatus", ApplyStatus.SHINSEICHUU)
//                    .findList();
            List<ApplyInfo> listAp = ApplyInfo.find
                    .fetch("applyKokyakuInfo")
                    .fetch("applyKeiyakuInfo")
                    .fetch("newApplyServiceInfo")
                    .fetch("newApplyServiceInfo.applySvcKaisen")
                    .fetch("newApplyServiceInfo.applySvcKaisen.serviceMaster")
                    .fetch("newApplyServiceInfo.applySvcMail")
                    .fetch("newApplyServiceInfo.applySvcMail.serviceMaster")
                    .fetch("newApplyServiceInfo.applySvcSecurity")
                    .fetch("newApplyServiceInfo.applySvcSecurity.serviceMaster")
                    .fetch("newApplyServiceInfo.applySvcOption")
                    .fetch("newApplyServiceInfo.applySvcOption.serviceMaster")
                    .where().eq("kokyakuInfo.kokyakuId", kokyakuId).eq("keiyakuInfo.keiyakuId", keiyakuId)
                    .eq("applyStatus", ApplyStatus.SHINSEICHUU)
                    .findList();

            if (listAp == null || listAp.size() == 0) {
                // 申込情報が取得できなかった場合
                result.setResultCode(ResultCode.BusinessError);
            } else {
                // 申込情報が取得できた場合
                Form<ApplyInfo> form = new Form<ApplyInfo>(ApplyInfo.class);
                form = form.fill(listAp.get(0));
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