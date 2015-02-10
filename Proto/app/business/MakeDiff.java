package business;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import models.entities.ApplyInfo;
import models.entities.NewApplyServiceInfo;
import models.entities.NewServiceMaster;
import models.input.WizardInputInfo;
import play.data.Form;

import common.BusinessResult;
import common.BusinessResult.ResultCode;

import constants.DbDataValue.ServiceBunrui;

/**
 * 申込履歴、トランザクション、パーマネント差分表示作成
 * <p>
 * 申込履歴、トランザクション、パーマネントの差分表示を行うための
 * 情報を提供する業務部品。
 * </p>
 * @author 那須 智貴
 * @version 0.1　2014/07/18　新規作成
 */
public final class MakeDiff {
    private MakeDiff() {
    }

    /**
     * 申込履歴⇒共通クラスパラメータへ格納
     * <p>
     * 申込履歴⇒共通クラスパラメータへ格納
     * </p>
     * @param ApplyInfo 申込履歴
     * @return 比較表示用クラスパラメータ
     * @author 那須 智貴
     * @version 0.1　2014/07/18　新規作成
     */
    public static WizardInputInfo makeDiff(ApplyInfo applyInfo, List<NewServiceMaster> lstSerMaster) {
        
        // 戻り値インスタンス生成
        WizardInputInfo wizardInputInfo = new WizardInputInfo();
        
        // 顧客申込履歴⇒共通クラスパラメータ
        if(applyInfo.applyKokyakuInfo != null){
            wizardInputInfo.inputApartment = applyInfo.applyKokyakuInfo.apartment;
            wizardInputInfo.inputApartmentRoomNo = applyInfo.applyKokyakuInfo.apartmentRoomNo;
            wizardInputInfo.inputAzaBanchiGou = applyInfo.applyKokyakuInfo.azaBanchiGou;
            wizardInputInfo.inputKidukeSama = applyInfo.applyKokyakuInfo.kidukeSama;
            wizardInputInfo.inputSiKuGun = applyInfo.applyKokyakuInfo.siKuGun;
            wizardInputInfo.inputTelNo = applyInfo.applyKokyakuInfo.telNo1;
            wizardInputInfo.inputTodouhuken = applyInfo.applyKokyakuInfo.todouhuken;
            wizardInputInfo.inputTyousonOaza = applyInfo.applyKokyakuInfo.tyousonOaza;
            if(!applyInfo.applyKokyakuInfo.zipNo.isEmpty()){
                wizardInputInfo.inputZipNo1 = applyInfo.applyKokyakuInfo.zipNo.substring(0,3);
                wizardInputInfo.inputZipNo2 = applyInfo.applyKokyakuInfo.zipNo.substring(4,7);
            }
        }
        
        // 契約申込履歴⇒共通クラスパラメータ
        if(applyInfo.applyKeiyakuInfo != null){
            // 今のとこはなし
        }
        
        // サービス申込履歴⇒共通クラスパラメータ
        if(applyInfo.newApplyServiceInfo != null){
            if(applyInfo.newApplyServiceInfo.size() > 0){
                for(int cntServ = 0; cntServ < applyInfo.newApplyServiceInfo.size(); cntServ++){
                    if(applyInfo.newApplyServiceInfo.get(cntServ).applySvcKaisen != null){
                        for(int cntKai = 0; cntKai < applyInfo.newApplyServiceInfo.get(cntServ).applySvcKaisen.size(); cntKai++){
                            wizardInputInfo.inputItPhoneStatus = 
                                    applyInfo.newApplyServiceInfo.get(cntServ).applySvcKaisen.get(cntKai).ttPhoneStatus;
                            wizardInputInfo.inputKaitsuuKoujibi = 
                                    applyInfo.newApplyServiceInfo.get(cntServ).applySvcKaisen.get(cntKai).kaitsuuKoujibi;
                            wizardInputInfo.inputKoujiYoteibi = 
                                    applyInfo.newApplyServiceInfo.get(cntServ).applySvcKaisen.get(cntKai).koujiYoteibi;
                            wizardInputInfo.inputRiyouCourse = 
                                    applyInfo.newApplyServiceInfo.get(cntServ).applySvcKaisen.get(cntKai).riyouCourse;
                            wizardInputInfo.inputRiyouCourseName = 
                                    utils.ServiceUtil.convCodeToName(applyInfo.newApplyServiceInfo.get(cntServ).applySvcKaisen.get(cntKai).riyouCourse, lstSerMaster);
                        }
                    }
                }
            }
        }

        return wizardInputInfo;

    }

    /**
     * 申込サービスリスト作成.
     * <p>
     * 申込情報からサービス分類毎の申込サービス情報を抽出し、<br>
     * 結果を返却する
     * </p>
     * @param applyInfo 申込情報
     * @return サービス分類毎の申込サービス情報リスト
     * @author 甲斐正之
     * @version 0.1　2014/04/11　新規作成
     */
    public static BusinessResult<Map<String, List<NewApplyServiceInfo>>> makeApplyServiceInfo(Form<ApplyInfo> applyInfo) {

        BusinessResult<Map<String, List<NewApplyServiceInfo>>> result = new BusinessResult<Map<String, List<NewApplyServiceInfo>>>();
        Map<String, List<NewApplyServiceInfo>> map = new HashMap<String, List<NewApplyServiceInfo>>();
        List<NewApplyServiceInfo> mainlist = new ArrayList<NewApplyServiceInfo>();
        List<NewApplyServiceInfo> sublist1 = new ArrayList<NewApplyServiceInfo>();
        List<NewApplyServiceInfo> sublist2 = new ArrayList<NewApplyServiceInfo>();
        String key1 = ServiceBunrui.KAISEN;
        String key2 = ServiceBunrui.TSUUCHI;
        mainlist = applyInfo.get().newApplyServiceInfo;

        for (NewApplyServiceInfo info : mainlist)
        {
//            if (key1.equals(info.serviceMaster.serviceBunrui)) {
//                sublist1.add(info);
//            } else if (key2.equals(info.serviceMaster.serviceBunrui)) {
//                sublist2.add(info);
//            }
        }

        map.put(key1, sublist1);
        map.put(key2, sublist2);

        result.setResultCode(ResultCode.Success);
        result.setValue(map);

        return result;

    }
}