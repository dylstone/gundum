package business;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import models.entities.KokyakuInfo;
import models.sqlrows.KokyakuTaiouRirekiInfo;
import play.data.Form;
import common.BusinessResult;
import common.BusinessResult.ResultCode;
import common.business.IdKanri;
import constants.DbDataValue.IdCd;
import constants.Message;

/**
 * 対応履歴登録業務部品.
 * <p>
 * 対応履歴登録ＤＢ部品を呼び出し、<br>
 * 対応履歴を登録する業務部品
 * </p>
 * @author 甲斐正之
 * @version 0.1　2014/04/17　新規作成
 */
public final class InsertTaiouRireki {
    private InsertTaiouRireki() {
    }

    /**
     * 対応履歴登録.
     * <p>
     * 対応履歴登録ＤＢ部品を呼び出し、<br>
     * 対応履歴を登録する業務部品
     * </p>
     * @param kokyakuId 顧客ID
     * @param operatorId オペレータID
     * @param taiouNaiyo 対応内容
     * @return 登録後の対応履歴情報
     * @throws Exception ○○例外
     * @author 甲斐正之
     * @version 0.1　2014/04/17　新規作成
     */
    public static BusinessResult<List<KokyakuTaiouRirekiInfo>> insertKokyakuTaiouRireki(
            String kokyakuId, String operatorId, String taiouNaiyo) throws Exception {

        BusinessResult<List<KokyakuTaiouRirekiInfo>> result = new BusinessResult<List<KokyakuTaiouRirekiInfo>>();

        try {
            KokyakuTaiouRirekiInfo info = setRirekiInfo(kokyakuId, operatorId, taiouNaiyo);
            int cnt = KokyakuTaiouRirekiInfo.insertTaiouRireki(info);

            if (cnt == 1) {
                result.setResultCode(ResultCode.Success);
            } else {
                result.setResultCode(ResultCode.BusinessError);
                result.setMessage(Message.MSGID_MKK0002);
            }

        } catch (Exception e) {
            e.printStackTrace();
            result.setResultCode(ResultCode.BusinessError);
            result.setMessage(Message.MSGID_MKK0002);
        }

        if (result.getResultCode() == ResultCode.Success)
        {
            List<KokyakuTaiouRirekiInfo> taioulist = SelectTaiouRireki.selectKokyakuTaiouRireki(kokyakuId).getValue();

            result.setValue(taioulist);
        }

        return result;

    }

    /**
     * 対応履歴設定.
     * <p>
     * 対応履歴登録用パラメータを設定する
     * </p>
     * @param kokyakuId 顧客ID
     * @param operatorId オペレータID
     * @param taiouNaiyo 対応内容
     * @return 登録対象対応履歴情報
     * @author 甲斐正之
     * @version 0.1　2014/04/17　新規作成
     */
    private static KokyakuTaiouRirekiInfo setRirekiInfo(String kokyakuId, String operatorId, String taiouNaiyo)
            throws Exception {

        KokyakuTaiouRirekiInfo info = new KokyakuTaiouRirekiInfo();
        Date nowDate = Calendar.getInstance().getTime();
        SimpleDateFormat sdf1 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        SimpleDateFormat sdf2 = new SimpleDateFormat("yyyyMMdd");
        String strDate = sdf1.format(nowDate);
        String strDate2 = sdf2.format(nowDate);
        Timestamp time = Timestamp.valueOf(strDate);
        BusinessResult<Form<KokyakuInfo>> result = SelectKokyakuInfo.selectKokyakuInfo(kokyakuId);
        KokyakuInfo kokyakuInfo = null;
        if (result.getResultCode() == ResultCode.Success)
        {
            kokyakuInfo = result.getValue().get();
        }

        info.taiouId = IdKanri.getId(IdCd.TAIOU);
        info.kokyakuId = kokyakuId;
        info.renrakushaName = kokyakuInfo != null ? kokyakuInfo.firstName + kokyakuInfo.lastName : null;
        info.telNo = kokyakuInfo != null ? kokyakuInfo.telNo1 : null;
        info.taiouNichiji = strDate;
        info.taiouOperatorId = operatorId;
        info.kanrenClientTaioId = null;
        info.toiawaseNaiyou = null;
        info.toiawaseSyubetsu = null;
        info.taiouNaiyou = taiouNaiyo;
        info.taiouKekka = null;
        info.tsuuchiNaiyou = null;
        info.rirekiTourokuNengetsubi = strDate2;
        info.rirekiTourokuOperatorId = operatorId;
        info.deleteDate = null;
        info.createUser = operatorId;
        info.lastUpdateUser = operatorId;
        info.createDT = time;
        info.lastUpdateDT = time;

        return info;
    }
}