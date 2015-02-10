package business;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.persistence.OptimisticLockException;

import models.entities.ApplyInfo;
import models.entities.ApplyKeiyakuInfo;
import models.entities.ApplyKokyakuInfo;
import models.entities.ApplySvcKaisen;
import models.entities.ApplySvcOption;
import models.entities.ApplySvcSecurity;
import models.entities.ApplySvcSettingOption;
import models.entities.ApplyXml;
import models.entities.KokyakuInfo;
import models.entities.NewApplyServiceInfo;
import models.entities.NewServiceMaster;
import models.input.ServiceInputInfo;
import models.input.WizardInputInfo;
import play.data.Form;

import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.xml.DomDriver;
import common.ApplyContents;
import common.ApplyInfoXml;
import common.BusinessResult;
import common.BusinessResult.ResultCode;
import common.business.IdKanri;

import constants.DbDataValue.ApplyStatus;
import constants.DbDataValue.HenkouType;
import constants.DbDataValue.IdCd;
import constants.DbDataValue.SyouninStatus;
import constants.DbDataValue.TelType;
import constants.Message;

/**
 * 申込顧客情報登録業務部品.
 * <p>
 * 申込顧客情報のＤＢ部品を呼び出し、<br>
 * 登録または更新を実行する業務部品
 * </p>
 * @author 甲斐正之
 * @version 0.1　2014/04/11　新規作成
 */
public final class InsertApplyKokyakuInfo {
    private InsertApplyKokyakuInfo() {
    }

    /**
     * 申込顧客情報登録.
     * <p>
     * 申込顧客情報のＤＢ部品を呼び出し、<br>
     * 登録または更新を実行する
     * </p>
     * @param inputInfo 画面入力情報
     * @param kokyakuInfo 顧客情報
     * @param operatorId オペレータID
     * @param applyInfo 申込情報
     * @param mapOption オプション情報
     * @return 申込情報
     * @throws Exception ○○例外
     * @author 甲斐正之
     * @version 0.1　2014/04/11　新規作成
     */
    public static BusinessResult<Form<ApplyInfo>> insertApplyKokyakuInfo(WizardInputInfo inputInfo,
            KokyakuInfo kokyakuInfo, String operatorId, Form<ApplyInfo> applyInfo, Map<String, String[]> mapOption) throws Exception {

        BusinessResult<Form<ApplyInfo>> selectResult = new BusinessResult<Form<ApplyInfo>>();
        selectResult = SelectApplyKokyakuInfo.selectApplyKokyakuInfo(kokyakuInfo.kokyakuId,
                kokyakuInfo.keiyakuInfo.get(0).keiyakuId);

        BusinessResult<Form<ApplyInfo>> result = new BusinessResult<Form<ApplyInfo>>();
        ApplyInfo info = new ApplyInfo();
        //BottomFields field = new BottomFields();

        try {
            
            // 初期処理時点の申込履歴があり、再取得でも申込履歴がある場合
            if (applyInfo != null && selectResult.getResultCode() == ResultCode.Success) {
                //info = selectResult.getValue().get();
                info = applyInfo.get();
                info = updateApplyInfo(inputInfo, info, operatorId);
                //field = setBottomFields(operatorId);
                
                if (info.applyKokyakuInfo != null) {
                    info = updateApplyKokyakuInfo(inputInfo, info, operatorId);
                } else {
                    info.applyKokyakuInfo = setApplyKokyakuInfo(inputInfo, kokyakuInfo, info, operatorId);
                }

                if (info.applyKeiyakuInfo != null) {
                    info = updateApplyKeiyakuInfo(inputInfo, info, operatorId);
                } else {
                    info.applyKeiyakuInfo = setApplyKeiyakuInfo(inputInfo, kokyakuInfo, info, info.applyKokyakuInfo,
                            operatorId);
                }
                
                // サービス共通の修正
                // サービス共通がサービス申込履歴にある場合
                if (info.newApplyServiceInfo != null && info.newApplyServiceInfo.size() > 0) {
                    // サービス申込履歴にウィザード入力情報を詰め替える。
                    info = updateApplyServiceInfo(inputInfo, info, operatorId);
                // サービス共通がサービス申込履歴にない場合
                } else {
                    info.newApplyServiceInfo = setApplyServiceInfo(inputInfo, kokyakuInfo, info, info.applyKeiyakuInfo, operatorId);
                }
                
                // サービス属性の修正
                // セキュリティ情報を申込履歴に登録するために、申込履歴（セキュリティ・オプション）にマッピング。
                for(int cnt = 0; cnt<info.newApplyServiceInfo.size(); cnt++){
                    if (info.newApplyServiceInfo.get(cnt).applySvcSecurity != null && info.newApplyServiceInfo.get(cnt).applySvcSecurity.size() > 0) {
                        List<ApplySvcSecurity> lstApplySvcSecurity = info.newApplyServiceInfo.get(cnt).applySvcSecurity;
                        info.newApplyServiceInfo.get(cnt).applySvcSecurity = 
                                updateApplySecurity(operatorId, mapOption, info.newApplyServiceInfo.get(cnt), lstApplySvcSecurity);
                    }else{
                        info.newApplyServiceInfo.get(cnt).applySvcSecurity = 
                                makeApplySecurityList(operatorId, mapOption, info.newApplyServiceInfo.get(cnt));
                    }
                }
                for(int cnt = 0; cnt<info.newApplyServiceInfo.size(); cnt++){
                    if (info.newApplyServiceInfo.get(cnt).applySvcOption != null && info.newApplyServiceInfo.get(cnt).applySvcOption.size() > 0) {
                        info.newApplyServiceInfo.get(cnt).applySvcOption = 
                                makeApplyOptionList(operatorId, mapOption, info.newApplyServiceInfo.get(cnt));
                    }else{
                        info.newApplyServiceInfo.get(cnt).applySvcOption = 
                                makeApplyOptionList(operatorId, mapOption, info.newApplyServiceInfo.get(cnt));
                    }
                }

                info.update();

             // 初期処理時点の申込履歴がなく、再取得でも申込履歴がない場合（まったくの新規申込）
            } else if (applyInfo == null && selectResult.getResultCode() == ResultCode.BusinessError) {

                //field = setBottomFields(operatorId);
                info = setApplyInfo(inputInfo, kokyakuInfo, operatorId);
                info.applyKokyakuInfo = setApplyKokyakuInfo(inputInfo, kokyakuInfo, info, operatorId);
                info.applyKeiyakuInfo = setApplyKeiyakuInfo(inputInfo, kokyakuInfo, info, info.applyKokyakuInfo, operatorId);
                info.newApplyServiceInfo = setApplyServiceInfo(inputInfo, kokyakuInfo, info, info.applyKeiyakuInfo, operatorId);
                
                // セキュリティ情報を申込履歴に登録するために、申込履歴（セキュリティ・オプション）にマッピング。
                for(int cnt = 0; cnt<info.newApplyServiceInfo.size(); cnt++){
                    info.newApplyServiceInfo.get(cnt).applySvcKaisen = 
                            makeApplyKaisenList(operatorId, inputInfo, info.newApplyServiceInfo.get(cnt));
                    info.newApplyServiceInfo.get(cnt).applySvcSecurity = 
                            makeApplySecurityList(operatorId, mapOption, info.newApplyServiceInfo.get(cnt));
                    info.newApplyServiceInfo.get(cnt).applySvcOption = 
                            makeApplyOptionList(operatorId, mapOption, info.newApplyServiceInfo.get(cnt));
                }
                
                info.save();

            } else {
                result.setResultCode(ResultCode.BusinessError);
                result.setMessage(Message.MSGID_MKK0004);
            }

            Form<ApplyInfo> form = new Form<ApplyInfo>(ApplyInfo.class);
            form = form.fill(info);
            result.setValue(form);
            result.setResultCode(ResultCode.Success);

        } catch (OptimisticLockException e) {
            e.printStackTrace();
            result.setResultCode(ResultCode.BusinessError);
            result.setMessage(Message.MSGID_MKK0004);
        } catch (Exception e) {
            e.printStackTrace();
            result.setResultCode(ResultCode.BusinessError);
            result.setMessage(Message.MSGID_MKK0002);
        }

        return result;

    }
    
    /**
     * 申込XML情報登録.
     * <p>
     * 申込情報XMLを作成し、申込XMLに登録する。
     * </p>
     * @param inputInfo 画面入力情報
     * @param kokyakuInfo 顧客情報
     * @param operatorId オペレータID
     * @param applyInfo 申込情報
     * @param mapOption オプション情報
     * @return 申込情報
     * @throws Exception ○○例外
     * @author 那須　智貴
     * @version 0.1　2014/07/28　新規作成
     */
    public static BusinessResult<ApplyInfo> updateApplyInfoXML(
            WizardInputInfo wizardInputInfo,
            KokyakuInfo kokyakuInfo, 
            String operatorId, 
            List<ApplyXml> applyXML, 
            Map<String, String[]> mapOption,
            String strBrandCd,
            String strKokyakuId,
            String strKeiyakuId,
            Form<ServiceInputInfo> formSerInfo) throws Exception {
        
        // インスタンス生成
        BusinessResult<ApplyInfo> result = new BusinessResult<ApplyInfo>();
        BusinessResult<List<ApplyXml>> selectResult = new BusinessResult<List<ApplyXml>>();
        BusinessResult<String> updateApplyXMLResult = new BusinessResult<String>();
        ApplyInfoXml applyInfoXml = new ApplyInfoXml();
        applyInfoXml.applyContents = new ApplyContents();
        ApplyInfo applyInfo = new ApplyInfo();
        ServiceInputInfo serInfo = new ServiceInputInfo();
        
        try {
            // 申込XML取得
            selectResult = SelectApplyXML.selectApplyXML(strKokyakuId, strKeiyakuId);
            
//            if(selectResult.getResultCode() == ResultCode.Success){
//                
//                // 初期処理時点のキャッシュの申込XMLがあり、再取得でも申込XMLがある場合（申込XMLのXML情報を更新）
//                if (applyXML != null && selectResult.getValue().size() > 0) {
//                    
//                    // 申込情報（JavaObj）の申込情報を作成
//                    for(int i=0; i<applyXML.size(); i++){
//                        
//                        // 申込情報XMLをオブジェクトに変換
//                        String strApplyXML = applyXML.get(i).applyXml;
//                        XStream xs = new XStream(new DomDriver());
//                        applyInfoXml = (ApplyInfoXml) xs.fromXML(strApplyXML.toString());
//                        
//                        applyInfo = applyInfoXml.applyContents.applyInfo;
//                        
//                        // 申込情報をセット
//                        applyInfo = setApplyInfoXML(applyInfo, wizardInputInfo, operatorId);
//                        // 申込情報（顧客情報）をセット
//                        if (applyInfo.applyKokyakuInfo != null) {
//                            
//                            applyInfo = setApplyKokaykuInfoXML(applyInfo, wizardInputInfo, operatorId);
//                        } else {
//                            applyInfo.applyKokyakuInfo = makeApplyKokyakuInfoXML(wizardInputInfo, kokyakuInfo, applyInfo, operatorId);
//                        }
//                        // 申込情報（契約情報）をセット
//                        if (applyInfo.applyKeiyakuInfo != null) {
//                            applyInfo = setApplyKeiyakuInfoXML(applyInfo, operatorId);
//                        } else {
//                            applyInfo.applyKeiyakuInfo = 
//                                    makeApplyKeiyakuInfoXML(wizardInputInfo, kokyakuInfo, applyInfo, applyInfo.applyKokyakuInfo, operatorId);
//                        }
//                        // サービス共通がサービス申込履歴にある場合
//                        if (applyInfo.newApplyServiceInfo != null && applyInfo.newApplyServiceInfo.size() > 0) {
//                            // サービス申込履歴にウィザード入力情報を詰め替える。
//                            applyInfo = updateApplyServiceInfo(wizardInputInfo, applyInfo, operatorId);
//                        // サービス共通がサービス申込履歴にない場合
//                        } else {
//                            applyInfo.newApplyServiceInfo = 
//                                    setApplyServiceInfo(wizardInputInfo, kokyakuInfo, applyInfo, applyInfo.applyKeiyakuInfo, operatorId);
//                        }
//                        // サービス属性の修正
//                        // セキュリティ情報を申込履歴に登録するために、申込履歴（セキュリティ・オプション）にマッピング。
//                        for(int cnt = 0; cnt<applyInfo.newApplyServiceInfo.size(); cnt++){
//                            if (applyInfo.newApplyServiceInfo.get(cnt).applySvcSecurity != null && applyInfo.newApplyServiceInfo.get(cnt).applySvcSecurity.size() > 0) {
//                                List<ApplySvcSecurity> lstApplySvcSecurity = applyInfo.newApplyServiceInfo.get(cnt).applySvcSecurity;
//                                applyInfo.newApplyServiceInfo.get(cnt).applySvcSecurity = 
//                                        updateApplySecurity(operatorId, mapOption, applyInfo.newApplyServiceInfo.get(cnt), lstApplySvcSecurity);
//                            }else{
//                                applyInfo.newApplyServiceInfo.get(cnt).applySvcSecurity = 
//                                        makeApplySecurityList(operatorId, mapOption, applyInfo.newApplyServiceInfo.get(cnt));
//                            }
//                        }
//                        for(int cnt = 0; cnt<applyInfo.newApplyServiceInfo.size(); cnt++){
//                            if (applyInfo.newApplyServiceInfo.get(cnt).applySvcOption != null && applyInfo.newApplyServiceInfo.get(cnt).applySvcOption.size() > 0) {
//                                List<ApplySvcOption> lstApplySvcOption = applyInfo.newApplyServiceInfo.get(cnt).applySvcOption;
//                                applyInfo.newApplyServiceInfo.get(cnt).applySvcOption = 
//                                        updateApplyOption(operatorId, mapOption, applyInfo.newApplyServiceInfo.get(cnt), lstApplySvcOption);
//                            }else{
//                                applyInfo.newApplyServiceInfo.get(cnt).applySvcOption = 
//                                        makeApplyOptionList(operatorId, mapOption, applyInfo.newApplyServiceInfo.get(cnt));
//                            }
//                        }
//                        
//                        // 申込XMLを作成
//                        updateApplyXMLResult = updateApplyXML(kokyakuInfo, applyInfo, applyInfoXml, operatorId, applyXML.get(i));
//    
//                    }
//    
//                // 初期処理時点の申込履歴がなく、再取得でも申込履歴がない場合（まったくの新規申込）
//                } else if (applyXML == null && selectResult.getValue().size() == 0) {
//    
//                    applyInfo = setApplyInfo(wizardInputInfo, kokyakuInfo, operatorId);
//                    applyInfo.applyKokyakuInfo = makeApplyKokyakuInfoXML(wizardInputInfo, kokyakuInfo, applyInfo, operatorId);
//                    applyInfo.applyKeiyakuInfo = makeApplyKeiyakuInfoXML(wizardInputInfo, kokyakuInfo, applyInfo, applyInfo.applyKokyakuInfo, operatorId);
//                    applyInfo.newApplyServiceInfo = setApplyServiceInfo(wizardInputInfo, kokyakuInfo, applyInfo, applyInfo.applyKeiyakuInfo, operatorId);
//                    
//                    // セキュリティ情報を申込履歴に登録するために、申込履歴（セキュリティ・オプション）にマッピング。
//                    for(int cnt = 0; cnt<applyInfo.newApplyServiceInfo.size(); cnt++){
//                        applyInfo.newApplyServiceInfo.get(cnt).applySvcKaisen = 
//                                makeApplyKaisenList(operatorId, wizardInputInfo, applyInfo.newApplyServiceInfo.get(cnt));
//                        applyInfo.newApplyServiceInfo.get(cnt).applySvcSecurity = 
//                                makeApplySecurityList(operatorId, mapOption, applyInfo.newApplyServiceInfo.get(cnt));
//                        applyInfo.newApplyServiceInfo.get(cnt).applySvcOption = 
//                                makeApplyOptionList(operatorId, mapOption, applyInfo.newApplyServiceInfo.get(cnt));
//                        applyInfo.newApplyServiceInfo.get(cnt).applySvcSettingOption = 
//                                makeApplySettingOptionList(operatorId, serInfo, applyInfo.newApplyServiceInfo.get(cnt));
//                    }
//                    
//                    // 申込XMLを作成
//                    updateApplyXMLResult = insertApplyXML(kokyakuInfo, applyInfo, applyInfoXml, operatorId, strBrandCd, strKokyakuId, strKeiyakuId);
//
//                } else {
//                    // 排他エラー
//                    result.setResultCode(ResultCode.BusinessError);
//                    result.setMessage(Message.MSGID_MKK0004);
//                    return result;
//                }
            if(selectResult.getResultCode() == ResultCode.Success){
                if(formSerInfo != null){
                    serInfo = formSerInfo.get();
                }else{
                    serInfo=null;
                }
                applyInfo = setApplyInfo(wizardInputInfo, kokyakuInfo, operatorId);
                applyInfo.applyKokyakuInfo = makeApplyKokyakuInfoXML(wizardInputInfo, kokyakuInfo, applyInfo, operatorId);
                applyInfo.applyKeiyakuInfo = makeApplyKeiyakuInfoXML(wizardInputInfo, kokyakuInfo, applyInfo, applyInfo.applyKokyakuInfo, operatorId);
                applyInfo.newApplyServiceInfo = setApplyServiceInfo(wizardInputInfo, kokyakuInfo, applyInfo, applyInfo.applyKeiyakuInfo, operatorId);
                
                // セキュリティ情報を申込履歴に登録するために、申込履歴（セキュリティ・オプション）にマッピング。
                for(int cnt = 0; cnt<applyInfo.newApplyServiceInfo.size(); cnt++){
                    applyInfo.newApplyServiceInfo.get(cnt).applySvcKaisen = 
                            makeApplyKaisenList(operatorId, wizardInputInfo, applyInfo.newApplyServiceInfo.get(cnt));
                    applyInfo.newApplyServiceInfo.get(cnt).applySvcSecurity = 
                            makeApplySecurityList(operatorId, mapOption, applyInfo.newApplyServiceInfo.get(cnt));
                    applyInfo.newApplyServiceInfo.get(cnt).applySvcOption = 
                            makeApplyOptionList(operatorId, mapOption, applyInfo.newApplyServiceInfo.get(cnt));
                    applyInfo.newApplyServiceInfo.get(cnt).applySvcSettingOption = 
                            makeApplySettingOptionList(operatorId, serInfo, applyInfo.newApplyServiceInfo.get(cnt));
                }
                
                // 申込XMLを作成
                updateApplyXMLResult = insertApplyXML(kokyakuInfo, applyInfo, applyInfoXml, operatorId, strBrandCd, strKokyakuId, strKeiyakuId);
            }
            result.setResultCode(updateApplyXMLResult.getResultCode());
            result.setValue(applyInfo);
                
            
        } catch (OptimisticLockException e) {
            e.printStackTrace();
            result.setResultCode(ResultCode.BusinessError);
            result.setMessage(Message.MSGID_MKK0004);
        } catch (Exception e) {
            e.printStackTrace();
            result.setResultCode(ResultCode.BusinessError);
            result.setMessage(Message.MSGID_MKK0002);
        }
        return result;
        
    }

    /**
     * 申込情報更新.
     * <p>
     * 申込情報の更新対象項目に値を設定する
     * </p>
     * @param inputInfo 画面入力情報
     * @param info 申込情報
     * @param operatorId オペレータID
     * @return 申込情報クラス
     * @author 甲斐正之
     * @version 0.1　2014/04/11　新規作成
     */
    private static ApplyInfo updateApplyInfo(WizardInputInfo inputInfo, ApplyInfo info, String operatorId) {

        info.uketsukeId = IdKanri.getId(IdCd.UKETSUKE);
        //info.applyKokyakuInfo.telType = TelType.HOME;
        //info.applyKokyakuInfo.telNo1 = inputInfo.inputTelNo;
        //info.applyKokyakuInfo.telNo2 = null;
        //info.applyKokyakuInfo.zipNo = inputInfo.inputZipNo1 + inputInfo.inputZipNo2;
        //info.applyKokyakuInfo.todouhuken = inputInfo.inputTodouhuken;
        //info.applyKokyakuInfo.si_ku_gun = inputInfo.inputSiKuGun;
        //info.applyKokyakuInfo.tyousonOaza = inputInfo.inputTyousonOaza;
        //info.applyKokyakuInfo.azaBanchiGou = inputInfo.inputAzaBanchiGou;
        //info.applyKokyakuInfo.apartment = inputInfo.inputApartment;
        //info.applyKokyakuInfo.apartmentRoomNo = inputInfo.inputApartmentRoomNo;
        //info.applyKokyakuInfo.kidukeSama = inputInfo.inputKidukeSama;
        //info.applyServiceInfo.get(0).riyouCourse = inputInfo.inputRiyouCourse;
        //info.applyServiceInfo.get(0).kaitsuuKoujibi = inputInfo.inputKaitsuuKoujibi;
        //info.applyServiceInfo.get(0).koujiYoteibi = inputInfo.inputKoujiYoteibi;
        info.lastUpdateUser = operatorId;
        //info.lastUpdateDT = null;
        //info.applyKokyakuInfo.bottomFields.lastUpdateUser = operatorId;
        //info.applyKokyakuInfo.bottomFields.lastUpdateDT = null;
        //info.applyKeiyakuInfo.bottomFields.lastUpdateUser = operatorId;
        //info.applyKeiyakuInfo.bottomFields.lastUpdateDT = null;
        //info.applyServiceInfo.get(0).bottomFields.lastUpdateUser = operatorId;
        //info.applyServiceInfo.get(0).bottomFields.lastUpdateDT = null;
        return info;

    }
    
    /**
     * 申込XMLの申込情報を設定
     * <p>
     * 申込XMLの申込情報部分に値を設定
     * </p>
     * @param applyInfo 申込情報
     * @param wizardInputInfo 入力情報
     * @param operatorId オペレータID
     * @return 申込情報クラス
     * @author 那須　智貴
     * @version 0.1　2014/07/29　新規作成
     */
    private static ApplyInfo setApplyInfoXML(ApplyInfo applyInfo, WizardInputInfo wizardInputInfo, String strOperatorId) {

        applyInfo.uketsukeId = IdKanri.getId(IdCd.UKETSUKE);
        applyInfo.lastUpdateUser = strOperatorId;
        return applyInfo;

    }

    /**
     * 申込顧客情報更新.
     * <p>
     * 申込顧客情報の更新対象項目に値を設定する
     * </p>
     * @param inputInfo 画面入力情報
     * @param info 申込情報
     * @param operatorId オペレータID
     * @return 申込情報クラス
     * @author 甲斐正之
     * @version 0.1　2014/04/11　新規作成
     */
    private static ApplyInfo updateApplyKokyakuInfo(WizardInputInfo inputInfo, ApplyInfo info, String operatorId) {

        info.applyKokyakuInfo.telType = TelType.HOME;
        info.applyKokyakuInfo.telNo1 = inputInfo.inputTelNo;
        info.applyKokyakuInfo.telNo2 = null;
        info.applyKokyakuInfo.zipNo = inputInfo.inputZipNo1 + inputInfo.inputZipNo2;
        info.applyKokyakuInfo.todouhuken = inputInfo.inputTodouhuken;
        info.applyKokyakuInfo.siKuGun = inputInfo.inputSiKuGun;
        info.applyKokyakuInfo.tyousonOaza = inputInfo.inputTyousonOaza;
        info.applyKokyakuInfo.azaBanchiGou = inputInfo.inputAzaBanchiGou;
        info.applyKokyakuInfo.apartment = inputInfo.inputApartment;
        info.applyKokyakuInfo.apartmentRoomNo = inputInfo.inputApartmentRoomNo;
        info.applyKokyakuInfo.kidukeSama = inputInfo.inputKidukeSama;
        info.applyKokyakuInfo.lastUpdateUser = operatorId;

        return info;

    }
    
    /**
     * 申込XMLの申込顧客情報を設定
     * <p>
     * 申込XMLの申込顧客情報部分に値を設定
     * </p>
     * @param inputInfo 画面入力情報
     * @param info 申込情報
     * @param operatorId オペレータID
     * @return 申込情報クラス
     * @author 甲斐正之
     * @version 0.1　2014/04/11　新規作成
     */
    private static ApplyInfo setApplyKokaykuInfoXML(ApplyInfo info, WizardInputInfo inputInfo, String operatorId) {

        info.applyKokyakuInfo.telType = TelType.HOME;
        info.applyKokyakuInfo.telNo1 = inputInfo.inputTelNo;
        info.applyKokyakuInfo.telNo2 = null;
        info.applyKokyakuInfo.zipNo = inputInfo.inputZipNo1 + inputInfo.inputZipNo2;
        info.applyKokyakuInfo.todouhuken = inputInfo.inputTodouhuken;
        info.applyKokyakuInfo.siKuGun = inputInfo.inputSiKuGun;
        info.applyKokyakuInfo.tyousonOaza = inputInfo.inputTyousonOaza;
        info.applyKokyakuInfo.azaBanchiGou = inputInfo.inputAzaBanchiGou;
        info.applyKokyakuInfo.apartment = inputInfo.inputApartment;
        info.applyKokyakuInfo.apartmentRoomNo = inputInfo.inputApartmentRoomNo;
        info.applyKokyakuInfo.kidukeSama = inputInfo.inputKidukeSama;
        info.applyKokyakuInfo.lastUpdateUser = operatorId;

        return info;

    }

    /**
     * 申込契約情報更新.
     * <p>
     * 申込契約情報の更新対象項目に値を設定する
     * </p>
     * @param inputInfo 画面入力情報
     * @param info 申込情報
     * @param operatorId オペレータID
     * @return 申込情報クラス
     * @author 甲斐正之
     * @version 0.1　2014/04/11　新規作成
     */
    private static ApplyInfo updateApplyKeiyakuInfo(WizardInputInfo inputInfo, ApplyInfo info, String operatorId) {

        info.applyKeiyakuInfo.lastUpdateUser = operatorId;

        return info;

    }
    
    /**
     * 申込XMLの申込契約情報を設定
     * <p>
     * 申込XMLの申込契約情報部分に値を設定
     * </p>
     * @param inputInfo 画面入力情報
     * @param info 申込情報
     * @param operatorId オペレータID
     * @return 申込情報クラス
     * @author 甲斐正之
     * @version 0.1　2014/04/11　新規作成
     */
    private static ApplyInfo setApplyKeiyakuInfoXML(ApplyInfo info, String operatorId) {

        info.applyKeiyakuInfo.lastUpdateUser = operatorId;

        return info;

    }

    /**
     * 申込サービス情報更新.
     * <p>
     * 申込サービス情報の更新対象項目に値を設定する
     * </p>
     * @param inputInfo 画面入力情報
     * @param info 申込情報
     * @param operatorId オペレータID
     * @return 申込情報クラス
     * @author 甲斐正之
     * @version 0.1　2014/04/11　新規作成
     */
    private static ApplyInfo updateApplyServiceInfo(WizardInputInfo inputInfo, ApplyInfo info, String operatorId) {

//        info.applyServiceInfo.get(0).applySvcKaisen.get(0).riyouCourse = inputInfo.inputRiyouCourseName;
//        info.applyServiceInfo.get(0).kaitsuuKoujibi = inputInfo.inputKaitsuuKoujibi;
//        info.applyServiceInfo.get(0).koujiYoteibi = inputInfo.inputKoujiYoteibi;
        info.newApplyServiceInfo.get(0).lastUpdateUser = operatorId;

        return info;

    }

    /**
     * 申込情報設定.
     * <p>
     * 申込情報に値を設定する
     * </p>
     * @param inputInfo 画面入力情報
     * @param kokyakuInfo 顧客情報
     * @param operator オペレータID
     * @return 申込情報クラス
     * @author 甲斐正之
     * @version 0.1　2014/04/11　新規作成
     */
    private static ApplyInfo setApplyInfo(WizardInputInfo inputInfo, KokyakuInfo kokyakuInfo, String operatorId) {

        ApplyInfo info = new ApplyInfo();
        info.applyId = IdKanri.getId(IdCd.APPLY);
        info.uketsukeId = IdKanri.getId(IdCd.UKETSUKE);
        info.kokyakuInfo = kokyakuInfo;
        info.keiyakuInfo = kokyakuInfo.keiyakuInfo.get(0);
        info.applyStatus = ApplyStatus.SHINSEICHUU;
        info.tourokuMissFlg = Boolean.FALSE;
        info.deleteDate = null;
        info.createUser = operatorId;
        info.lastUpdateUser = operatorId;

        return info;

    }

    /**
     * 申込顧客情報設定.
     * <p>
     * 申込顧客情報に値を設定する
     * </p>
     * @param inputInfo 画面入力情報
     * @param kokyakuInfo 顧客情報
     * @param apInfo 申込情報
     * @param operatorId オペレータID
     * @return 申込顧客情報クラス
     * @author 甲斐正之
     * @version 0.1　2014/04/11　新規作成
     */
    private static ApplyKokyakuInfo setApplyKokyakuInfo(WizardInputInfo inputInfo, KokyakuInfo kokyakuInfo,
            ApplyInfo apInfo, String operatorId) {

        ApplyKokyakuInfo info = new ApplyKokyakuInfo();
        info.applyKokyakuId = IdKanri.getId(IdCd.APPLY_KOKYAKU);
        info.applyInfo = apInfo;
        info.kokyakuInfo = kokyakuInfo;
        info.henkouType = HenkouType.UPDATE;
        info.syouninStatus = SyouninStatus.SYOUNINMACHI;
        info.kokyakuUserId = kokyakuInfo.kokyakuUserId;
        info.kokyakuUserPwd = kokyakuInfo.kokyakuUserPwd;
        info.lastName = kokyakuInfo.lastName;
        info.firstName = kokyakuInfo.firstName;
        info.lastNameFurigana = kokyakuInfo.lastNameFurigana;
        info.firstNameFurigana = kokyakuInfo.firstNameFurigana;
        info.department = kokyakuInfo.department;
        info.corporativeUser = kokyakuInfo.corporativeUser;
        info.seibetsuCd = kokyakuInfo.seibetsuCd;
        info.birthNengetsubi = kokyakuInfo.birthNengetsubi;
        info.jobCd = kokyakuInfo.jobCd;
        info.mikomiKokyakuFlg = kokyakuInfo.mikomiKokyakuFlg;
        info.renrakusakiMailaddress = kokyakuInfo.renrakusakiMailaddress;
        info.telType = TelType.HOME;
        info.telNo1 = inputInfo.inputTelNo;
        info.telNo2 = null;
        info.faxNo = kokyakuInfo.faxNo;
        info.zipNo = inputInfo.inputZipNo1 + inputInfo.inputZipNo2;
        info.todouhuken = inputInfo.inputTodouhuken;
        info.siKuGun = inputInfo.inputSiKuGun;
        info.tyousonOaza = inputInfo.inputTyousonOaza;
        info.azaBanchiGou = inputInfo.inputAzaBanchiGou;
        info.apartment = inputInfo.inputApartment;
        info.apartmentRoomNo = inputInfo.inputApartmentRoomNo;
        info.kidukeSama = inputInfo.inputKidukeSama;
        info.kokyakuUserPwdLastUpdated = kokyakuInfo.kokyakuUserPwdLastUpdated;
        info.deleteDate = null;
        info.createUser = operatorId;
        info.lastUpdateUser = operatorId;

        return info;

    }
    
    /**
     * 申込顧客情報XMLの新規設定.
     * <p>
     * 申込顧客情報に値を設定する
     * </p>
     * @param inputInfo 画面入力情報
     * @param kokyakuInfo 顧客情報
     * @param apInfo 申込情報
     * @param operatorId オペレータID
     * @return 申込顧客情報クラス
     * @author 甲斐正之
     * @version 0.1　2014/04/11　新規作成
     */
    private static ApplyKokyakuInfo makeApplyKokyakuInfoXML(WizardInputInfo inputInfo, KokyakuInfo kokyakuInfo,
            ApplyInfo apInfo, String operatorId) {

        ApplyKokyakuInfo info = new ApplyKokyakuInfo();
        info.applyKokyakuId = IdKanri.getId(IdCd.APPLY_KOKYAKU);
        info.applyInfo = apInfo;
        info.kokyakuInfo = kokyakuInfo;
        info.henkouType = HenkouType.UPDATE;
        info.syouninStatus = SyouninStatus.SYOUNINMACHI;
        info.kokyakuUserId = kokyakuInfo.kokyakuUserId;
        info.kokyakuUserPwd = kokyakuInfo.kokyakuUserPwd;
        info.lastName = kokyakuInfo.lastName;
        info.firstName = kokyakuInfo.firstName;
        info.lastNameFurigana = kokyakuInfo.lastNameFurigana;
        info.firstNameFurigana = kokyakuInfo.firstNameFurigana;
        info.department = kokyakuInfo.department;
        info.corporativeUser = kokyakuInfo.corporativeUser;
        info.seibetsuCd = kokyakuInfo.seibetsuCd;
        info.birthNengetsubi = kokyakuInfo.birthNengetsubi;
        info.jobCd = kokyakuInfo.jobCd;
        info.mikomiKokyakuFlg = kokyakuInfo.mikomiKokyakuFlg;
        info.renrakusakiMailaddress = kokyakuInfo.renrakusakiMailaddress;
        info.telType = TelType.HOME;
        info.telNo1 = inputInfo.inputTelNo;
        info.telNo2 = null;
        info.faxNo = kokyakuInfo.faxNo;
        info.zipNo = inputInfo.inputZipNo1 + inputInfo.inputZipNo2;
        info.todouhuken = inputInfo.inputTodouhuken;
        info.siKuGun = inputInfo.inputSiKuGun;
        info.tyousonOaza = inputInfo.inputTyousonOaza;
        info.azaBanchiGou = inputInfo.inputAzaBanchiGou;
        info.apartment = inputInfo.inputApartment;
        info.apartmentRoomNo = inputInfo.inputApartmentRoomNo;
        info.kidukeSama = inputInfo.inputKidukeSama;
        info.kokyakuUserPwdLastUpdated = kokyakuInfo.kokyakuUserPwdLastUpdated;
        info.deleteDate = null;
        info.createUser = operatorId;
        info.lastUpdateUser = operatorId;

        return info;

    }

    /**
     * 申込契約情報設定.
     * <p>
     * 申込契約情報に値を設定する
     * </p>
     * @param inputInfo 画面入力情報
     * @param kokyakuInfo 顧客情報
     * @param apInfo 申込情報
     * @param apKeiyakuInfo 申込顧客情報
     * @param operatorId オペレータID
     * @return 申込契約情報クラス
     * @author 甲斐正之
     * @version 0.1　2014/04/11　新規作成
     */
    private static ApplyKeiyakuInfo setApplyKeiyakuInfo(WizardInputInfo inputInfo, KokyakuInfo kokyakuInfo,
            ApplyInfo apInfo, ApplyKokyakuInfo apKokyakuInfo, String operatorId) {

        ApplyKeiyakuInfo info = new ApplyKeiyakuInfo();
        info.applyKeiyakuId = IdKanri.getId(IdCd.APPLY_KEIYAKU);
        info.applyInfo = apInfo;
        info.applyKokyakuInfo = apKokyakuInfo;
        info.keiyakuInfo = kokyakuInfo.keiyakuInfo.get(0);
        info.henkouType = HenkouType.UPDATE;
        info.syouninStatus = SyouninStatus.SYOUNINMACHI;
        info.keiyakuBrandCd = kokyakuInfo.keiyakuInfo.get(0).keiyakuBrandCd;
        info.hanbaiKeitai = kokyakuInfo.keiyakuInfo.get(0).hanbaiKeitai;
        info.moushikomiNengetsubi = kokyakuInfo.keiyakuInfo.get(0).moushikomiNengetsubi;
        info.keiyakuKoushinNengetsu = kokyakuInfo.keiyakuInfo.get(0).keiyakuKoushinNengetsu;
        info.riyouKaishiNengetsubi = kokyakuInfo.keiyakuInfo.get(0).riyouKaishiNengetsubi;
        info.shiharaiHouhou = kokyakuInfo.keiyakuInfo.get(0).shiharaiHouhou;
        info.seikyuuKubun = kokyakuInfo.keiyakuInfo.get(0).seikyuuKubun;
        info.deleteDate = null;
        info.createUser = operatorId;
        info.lastUpdateUser = operatorId;

        return info;

    }
    
    /**
     * 申込契約情報XMLの新規設定.
     * <p>
     * 申込契約情報に値を設定する
     * </p>
     * @param inputInfo 画面入力情報
     * @param kokyakuInfo 顧客情報
     * @param apInfo 申込情報
     * @param apKeiyakuInfo 申込顧客情報
     * @param operatorId オペレータID
     * @return 申込契約情報クラス
     * @author 那須　智貴
     * @version 0.1　2014/07/29　新規作成
     */
    private static ApplyKeiyakuInfo makeApplyKeiyakuInfoXML(WizardInputInfo inputInfo, KokyakuInfo kokyakuInfo,
            ApplyInfo apInfo, ApplyKokyakuInfo apKokyakuInfo, String operatorId) {

        ApplyKeiyakuInfo info = new ApplyKeiyakuInfo();
        info.applyKeiyakuId = IdKanri.getId(IdCd.APPLY_KEIYAKU);
        info.applyInfo = apInfo;
        info.applyKokyakuInfo = apKokyakuInfo;
        info.keiyakuInfo = kokyakuInfo.keiyakuInfo.get(0);
        info.henkouType = HenkouType.UPDATE;
        info.syouninStatus = SyouninStatus.SYOUNINMACHI;
        info.keiyakuBrandCd = kokyakuInfo.keiyakuInfo.get(0).keiyakuBrandCd;
        info.hanbaiKeitai = kokyakuInfo.keiyakuInfo.get(0).hanbaiKeitai;
        info.moushikomiNengetsubi = kokyakuInfo.keiyakuInfo.get(0).moushikomiNengetsubi;
        info.keiyakuKoushinNengetsu = kokyakuInfo.keiyakuInfo.get(0).keiyakuKoushinNengetsu;
        info.riyouKaishiNengetsubi = kokyakuInfo.keiyakuInfo.get(0).riyouKaishiNengetsubi;
        info.shiharaiHouhou = kokyakuInfo.keiyakuInfo.get(0).shiharaiHouhou;
        info.seikyuuKubun = kokyakuInfo.keiyakuInfo.get(0).seikyuuKubun;
        info.deleteDate = null;
        info.createUser = operatorId;
        info.lastUpdateUser = operatorId;

        return info;

    }

    /**
     * 申込サービス情報設定.
     * <p>
     * 申込サービス情報に値を設定する
     * </p>
     * @param inputInfo 画面入力情報
     * @param kokyakuInfo 顧客情報
     * @param apInfo 申込情報
     * @param apKeiyakuInfo 申込契約情報
     * @param operatorId オペレータID
     * @return 申込サービス情報クラス
     * @author 甲斐正之
     * @version 0.1　2014/04/11　新規作成
     */
    private static List<NewApplyServiceInfo> setApplyServiceInfo(WizardInputInfo inputInfo, KokyakuInfo kokyakuInfo,
            ApplyInfo apInfo, ApplyKeiyakuInfo apKeiyakuInfo, String operatorId) {

        List<NewApplyServiceInfo> infoList = new ArrayList<NewApplyServiceInfo>();
        NewApplyServiceInfo info = new NewApplyServiceInfo();
        
        info.applyServiceId = IdKanri.getId(IdCd.APPLY_SERVICE);
        info.applyInfo = apInfo;
        info.applyKeiyakuInfo = apKeiyakuInfo;
        //		info.serviceInfo = kokyakuInfo.keiyakuInfo.get(0).serviceInfo.get(0);
        info.serviceId = kokyakuInfo.keiyakuInfo.get(0).serviceInfo.get(0).serviceId;
        info.henkouType = HenkouType.UPDATE;
        info.syouninStatus = SyouninStatus.SYOUNINMACHI;
        info.riyouKaishiNengetsubi = kokyakuInfo.keiyakuInfo.get(0).serviceInfo.get(0).riyouKaishiNengetsubi;
        //info.riyouCourse = inputInfo.inputRiyouCourseName;
        info.deleteDate = null;
        info.createUser = operatorId;
        info.lastUpdateUser = operatorId;

        infoList.add(info);
        return infoList;

    }
    
    /**
     * 申込回線情報設定.
     * <p>
     * 申込回線情報に値を設定する
     * </p>
     * @param operatorId オペレータID
     * @param mapOption オプション情報
     * @param operatorId サービス情報
     * @return 申込回線サービス情報クラス
     * @author 那須　智貴
     * @version 0.1　2014/07/24　新規作成
     */
    private static List<ApplySvcKaisen> makeApplyKaisenList(
            String operatorId, WizardInputInfo inputInfo, NewApplyServiceInfo serviceInfo){
        
        // インスタンス生成
        List<ApplySvcKaisen> result = new ArrayList<ApplySvcKaisen>();
        
        // 回線サービスは、必ず１件作成。
        ApplySvcKaisen applySvcKaisen = new ApplySvcKaisen();
        applySvcKaisen.applySvcAuhikariId = IdKanri.getId(IdCd.TRANSACTION);
        applySvcKaisen.serviceMaster = new NewServiceMaster();
        applySvcKaisen.serviceMaster.serviceCd = inputInfo.inputRiyouCourse;
        applySvcKaisen.applyService = serviceInfo;
        applySvcKaisen.ttPhoneStatus = inputInfo.inputItPhoneStatus;
        applySvcKaisen.kaitsuuKoujibi = inputInfo.inputKaitsuuKoujibi;
        applySvcKaisen.koujiYoteibi = inputInfo.inputKoujiYoteibi;
        applySvcKaisen.riyouCourse = inputInfo.inputRiyouCourse;
        applySvcKaisen.deleteDate = null;
        applySvcKaisen.createUser = operatorId;
        applySvcKaisen.lastUpdateUser = operatorId;
        result.add(applySvcKaisen);
        
        // 戻り値
        return result;
    }
    
    /**
     * 申込セキュリティ情報設定.
     * <p>
     * 申込セキュリティ情報に値を設定する
     * </p>
     * @param operatorId オペレータID
     * @param mapOption オプション情報
     * @param operatorId サービス情報
     * @return 申込セキュリティサービス情報クラス
     * @author 那須　智貴
     * @version 0.1　2014/07/24　新規作成
     */
    private static List<ApplySvcSecurity> makeApplySecurityList(
            String operatorId, Map<String, String[]> mapOption, NewApplyServiceInfo serviceInfo){
        
        // インスタンス生成
        List<ApplySvcSecurity> result = new ArrayList<ApplySvcSecurity>();
        
        // オプション情報を追加処理
        // 申込したかどうかのフラグ
        String[] strSecurityOptions = mapOption.get("securityUpdFlg");
        // 申込したサービスコード
        String[] strServiceCds = mapOption.get("securityServiceCd");
        
        if(strSecurityOptions != null){
            for(int i=0; i < strSecurityOptions.length; i++){
                // オプションを変更していた場合は、サービスコードを元に、申込履歴（サービス）に登録。
                if("1".equals(strSecurityOptions[i])){
                    ApplySvcSecurity applySvcSecurity = new ApplySvcSecurity();
                    applySvcSecurity.applySvcSecurityId = IdKanri.getId(IdCd.TRANSACTION);
                    applySvcSecurity.applyService = serviceInfo;
                    applySvcSecurity.serviceMaster = new NewServiceMaster();
                    applySvcSecurity.serviceMaster.serviceCd = strServiceCds[i];
                    applySvcSecurity.deleteDate = null;
                    applySvcSecurity.createUser = operatorId;
                    applySvcSecurity.lastUpdateUser = operatorId;
                    result.add(applySvcSecurity);
                }
            }
        }
        
        // 戻り値
        return result;
    }
    
    /**
     * 申込セキュリティ情報更新.
     * <p>
     * 申込セキュリティ情報に値を設定する
     * </p>
     * @param operatorId オペレータID
     * @param mapOption オプション情報
     * @param operatorId サービス情報
     * @return 申込セキュリティサービス情報クラス
     * @author 那須　智貴
     * @version 0.1　2014/07/24　新規作成
     */
    private static List<ApplySvcSecurity> updateApplySecurity(
            String operatorId, Map<String, 
            String[]> mapOption, 
            NewApplyServiceInfo serviceInfo, 
            List<ApplySvcSecurity> lstApplySvcSecurity){
        
        // インスタンス生成
        List<ApplySvcSecurity> addList = new ArrayList<ApplySvcSecurity>();
        
        // オプション情報を追加処理
        // 申込したかどうかのフラグ
        String[] strSecurityOptions = mapOption.get("securityUpdFlg");
        // 申込したサービスコード
        String[] strServiceCds = mapOption.get("securityServiceCd");
        
        if(strSecurityOptions != null){
            for(int i=0; i < strSecurityOptions.length; i++){
                // オプションを変更していた場合は、サービスコードを元に、申込履歴（サービス）に登録。
                if("1".equals(strSecurityOptions[i])){
                    // 既存のサービスを見る
                    for(ApplySvcSecurity ass : lstApplySvcSecurity){
                        // 既存のサービスコードと新規追加サービスコードを比較して、ある場合は更新
                        if(ass.serviceMaster.serviceCd.equals(strServiceCds[i])){
                            ass.lastUpdateUser = operatorId;
                        // 新規登録    
                        }else{
                            ApplySvcSecurity applySvcSecurity = new ApplySvcSecurity();
                            applySvcSecurity.applySvcSecurityId = IdKanri.getId(IdCd.TRANSACTION);
                            applySvcSecurity.applyService = serviceInfo;
                            applySvcSecurity.serviceMaster = new NewServiceMaster();
                            applySvcSecurity.serviceMaster.serviceCd = strServiceCds[i];
                            applySvcSecurity.deleteDate = null;
                            applySvcSecurity.createUser = operatorId;
                            applySvcSecurity.lastUpdateUser = operatorId;
                            addList.add(applySvcSecurity);
                            break;
                        }
                    }
                }
            }
        }
        for(ApplySvcSecurity ass : addList){
            lstApplySvcSecurity.add(ass);
        }
        
        // 戻り値
        return lstApplySvcSecurity;
    }
    
    /**
     * 申込オプション情報設定.
     * <p>
     * 申込オプション情報に値を設定する
     * </p>
     * @param operatorId オペレータID
     * @param mapOption オプション情報
     * @param operatorId サービス情報
     * @return 申込オプションサービス情報クラス
     * @author 那須　智貴
     * @version 0.1　2014/07/24　新規作成
     */
    private static List<ApplySvcOption> makeApplyOptionList(
            String operatorId, Map<String, String[]> mapOption, NewApplyServiceInfo serviceInfo){
        
        // インスタンス生成
        List<ApplySvcOption> result = new ArrayList<ApplySvcOption>();
        
        // オプション情報を追加処理
        // 申込したかどうかのフラグ
        String[] strOptions = mapOption.get("optionUpdFlg");
        // 申込したサービスコード
        String[] strServiceCds = mapOption.get("optionServiceCd");
        
        if(strOptions != null){
            for(int i=0; i < strOptions.length; i++){
                // オプションを変更していた場合は、サービスコードを元に、申込履歴（サービス）に登録。
                if("1".equals(strOptions[i])){
                    ApplySvcOption applySvcOption = new ApplySvcOption();
                    applySvcOption.applySvcOptionId = IdKanri.getId(IdCd.TRANSACTION);
                    applySvcOption.applyService = serviceInfo;
                    applySvcOption.serviceMaster = new NewServiceMaster();
                    applySvcOption.serviceMaster.serviceCd = strServiceCds[i];
                    applySvcOption.deleteDate = null;
                    applySvcOption.createUser = operatorId;
                    applySvcOption.lastUpdateUser = operatorId;
                    result.add(applySvcOption);
                }
            }
        }
        
        // 戻り値
        return result;
    }
    
    /**
     * 申込オプション情報設定.
     * <p>
     * 申込オプション情報に値を設定する
     * </p>
     * @param operatorId オペレータID
     * @param mapOption オプション情報
     * @param operatorId サービス情報
     * @return 申込オプションサービス情報クラス
     * @author 那須　智貴
     * @version 0.1　2014/07/24　新規作成
     */
    private static List<ApplySvcSettingOption> makeApplySettingOptionList(
            String operatorId, ServiceInputInfo settingInfo, NewApplyServiceInfo serviceInfo){
        
        // インスタンス生成
        List<ApplySvcSettingOption> result = new ArrayList<ApplySvcSettingOption>();
        
        if(settingInfo != null){
            ApplySvcSettingOption applySvcSettingOption = new ApplySvcSettingOption();
            applySvcSettingOption.applySvcSettingOptionId = IdKanri.getId(IdCd.TRANSACTION);
            applySvcSettingOption.applyService = serviceInfo;
            applySvcSettingOption.todouhuken = settingInfo.inputTodouhuken;
            applySvcSettingOption.siKuGun = settingInfo.inputSiKuGun;
            applySvcSettingOption.tyousonOaza = settingInfo.inputTyousonOaza;
            applySvcSettingOption.serviceMaster = new NewServiceMaster();
            applySvcSettingOption.serviceMaster.serviceCd = settingInfo.inputServiceCd;
            applySvcSettingOption.azaBanchiGou = settingInfo.inputAzaBanchiGou;
            applySvcSettingOption.apartment = settingInfo.inputApartment;
            applySvcSettingOption.apartmentRoomNo = settingInfo.inputApartmentRoomNo;
            applySvcSettingOption.deleteDate = null;
            applySvcSettingOption.createUser = operatorId;
            applySvcSettingOption.lastUpdateUser = operatorId;
            result.add(applySvcSettingOption);
        }
        
        // 戻り値
        return result;
    }
    
    /**
     * 申込オプション情報更新.
     * <p>
     * 申込オプション情報に値を設定する
     * </p>
     * @param operatorId オペレータID
     * @param mapOption オプション情報
     * @param operatorId サービス情報
     * @return 申込セキュリティサービス情報クラス
     * @author 那須　智貴
     * @version 0.1　2014/07/24　新規作成
     */
    private static List<ApplySvcOption> updateApplyOption(
            String operatorId, Map<String, 
            String[]> mapOption, 
            NewApplyServiceInfo serviceInfo, 
            List<ApplySvcOption> lstApplySvcOption){
        
        // インスタンス生成
        List<ApplySvcOption> addList = new ArrayList<ApplySvcOption>();
        
        // オプション情報を追加処理
        // 申込したかどうかのフラグ
        String[] strSecurityOptions = mapOption.get("securityUpdFlg");
        // 申込したサービスコード
        String[] strServiceCds = mapOption.get("securityServiceCd");
        
        for(int i=0; i < strSecurityOptions.length; i++){
            // オプションを変更していた場合は、サービスコードを元に、申込履歴（サービス）に登録。
            if("1".equals(strSecurityOptions[i])){
                // 既存のサービスを見る
                for(ApplySvcOption aso : lstApplySvcOption){
                    // 既存のサービスコードと新規追加サービスコードを比較して、ある場合は更新
                    if(aso.serviceMaster.serviceCd.equals(strServiceCds[i])){
                        aso.lastUpdateUser = operatorId;
                    // 新規登録    
                    }else{
                        ApplySvcOption applySvcOption = new ApplySvcOption();
                        applySvcOption.applySvcOptionId = IdKanri.getId(IdCd.TRANSACTION);
                        applySvcOption.applyService = serviceInfo;
                        applySvcOption.serviceMaster = new NewServiceMaster();
                        applySvcOption.serviceMaster.serviceCd = strServiceCds[i];
                        applySvcOption.deleteDate = null;
                        applySvcOption.createUser = operatorId;
                        applySvcOption.lastUpdateUser = operatorId;
                        addList.add(applySvcOption);
                        break;
                    }
                }
            }
        }
        for(ApplySvcOption aso : addList){
            lstApplySvcOption.add(aso);
        }
        
        // 戻り値
        return lstApplySvcOption;
    }
    
    /**
     * 申込XML情報更新
     * <p>
     * 申込XMLに値を設定し、更新処理を行う
     * </p>
     * @param operatorId オペレータID
     * @param mapOption オプション情報
     * @param operatorId サービス情報
     * @return 申込オプションサービス情報クラス
     * @author 那須　智貴
     * @version 0.1　2014/07/24　新規作成
     */
    private static BusinessResult<String> updateApplyXML(
            KokyakuInfo kokyakuInfo, ApplyInfo applyInfo, ApplyInfoXml applyInfoXml, String strOperatorId, ApplyXml applyXml){
        
        // インスタンス生成
        BusinessResult<String> result = new BusinessResult<String>();
        
        applyInfoXml.applyContents.applyInfo = applyInfo;
        applyInfoXml.kokyakuInfo = kokyakuInfo;
        applyInfoXml.applyContents.businessDetail = "3";
        applyInfoXml.applyContents.applyDetail = "1";
        
        // JavaObjからXMLに変換
        XStream xs = new XStream(new DomDriver());
        String strApplyInfoXml = xs.toXML(applyInfoXml); 
        
        // 申込XML情報設定
        // ※※※applyXmlとあるが、実際はCLOBで作成
        applyXml.applyXml = strApplyInfoXml;
        applyXml.lastUpdateUser = strOperatorId;
        
        applyXml.update();
        
        result.setResultCode(ResultCode.Success);
        
        // 戻り値
        return result;
    }
    
    /**
     * 申込XML情報追加
     * <p>
     * 申込XMLに値を設定し、INSERT処理を行う
     * </p>
     * @param operatorId オペレータID
     * @param mapOption オプション情報
     * @param operatorId サービス情報
     * @return 申込オプションサービス情報クラス
     * @author 那須　智貴
     * @version 0.1　2014/07/24　新規作成
     */
    private static BusinessResult<String> insertApplyXML(
            KokyakuInfo kokyakuInfo, ApplyInfo applyInfo, ApplyInfoXml applyInfoXml, String strOperatorId, String strBrandCd, String strKokyakuId, String strKeiyakuId){
        
        // インスタンス生成
        BusinessResult<String> result = new BusinessResult<String>();
        ApplyXml applyXml = new ApplyXml();
        
        applyInfoXml.applyContents.applyInfo = applyInfo;
        applyInfoXml.kokyakuInfo = kokyakuInfo;
        applyInfoXml.applyContents.businessDetail = "3";
        applyInfoXml.applyContents.applyDetail = "1";
        
        // JavaObjからXMLに変換
        XStream xs = new XStream(new DomDriver());
        String strApplyInfoXml = xs.toXML(applyInfoXml); 

        // 申込XML情報設定
        applyXml.applyId = applyInfo.applyId;
        // ※※※applyXmlとあるが、実際はCLOBで作成
        applyXml.applyXml = strApplyInfoXml;
        applyXml.uketsukeId = IdKanri.getId(IdCd.UKETSUKE);
        applyXml.businessDetail = "3";
        applyXml.kokyakuId = strKokyakuId;
        applyXml.keiyakuId = strKeiyakuId;
        applyXml.brandCd = strBrandCd;
        applyXml.errorCd = "0";
        applyXml.createUser = strOperatorId;
        applyXml.lastUpdateUser = strOperatorId;
        
        applyXml.save();
        
        result.setResultCode(ResultCode.Success);
            
        // 戻り値
        return result;
    }


}