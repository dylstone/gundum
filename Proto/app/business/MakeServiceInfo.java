package business;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import models.entities.ApplyInfo;
import models.entities.KokyakuInfo;
import models.entities.NewApplyServiceInfo;
import models.entities.NewServiceInfo;
import play.data.Form;

import common.BusinessResult;
import common.BusinessResult.ResultCode;

import constants.DbDataValue.ServiceBunrui;

/**
 * サービス情報リスト作成業務部品.
 * <p>
 * 顧客情報からサービス分類毎のサービス情報を抽出し、<br>
 * 結果を返却する業務部品
 * </p>
 * @author 甲斐正之
 * @version 0.1　2014/04/11　新規作成
 */
public final class MakeServiceInfo {
    private MakeServiceInfo() {
    }

    /**
     * サービス情報リスト作成.
     * <p>
     * 顧客情報からサービス分類毎のサービス情報を抽出し、<br>
     * 結果を返却する
     * </p>
     * @param kokyakuInfo 顧客情報
     * @return サービス分類毎のサービス情報リスト
     * @author 甲斐正之
     * @version 0.1　2014/04/11　新規作成
     */
    public static BusinessResult<Map<String, List<NewServiceInfo>>> makeServiceInfo(Form<KokyakuInfo> kokyakuInfo) {

        BusinessResult<Map<String, List<NewServiceInfo>>> result = new BusinessResult<Map<String, List<NewServiceInfo>>>();
        Map<String, List<NewServiceInfo>> map = new HashMap<String, List<NewServiceInfo>>();
        List<NewServiceInfo> mainlist = new ArrayList<NewServiceInfo>();
        List<NewServiceInfo> sublist1 = new ArrayList<NewServiceInfo>();
        List<NewServiceInfo> sublist2 = new ArrayList<NewServiceInfo>();
        String key1 = ServiceBunrui.KAISEN;
        String key2 = ServiceBunrui.TSUUCHI;
        //mainlist = kokyakuInfo.get().keiyakuInfo.get(0).serviceInfo;

        for (NewServiceInfo info : mainlist)
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