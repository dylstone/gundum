package business;

import java.util.List;

import models.sqlrows.KokyakuTaiouRirekiInfo;
import common.BusinessResult;
import common.BusinessResult.ResultCode;

/**
 * 対応履歴抽出.
 * <p>
 * 対応履歴のＤＢ部品を呼び出し、<br>
 * 検索結果を返却する業務部品
 * </p>
 * @author 甲斐正之
 * @version 0.1　2014/02/28　新規作成
 * @version 0.2　2014/03/17　STEP3対応
 * @version 0.3　2014/04/23　STEP4対応
 */
public final class SelectTaiouRireki {
    private SelectTaiouRireki() {
    }

    /**
     * 顧客対応履歴抽出.
     * <p>
     * 顧客対応履歴取得のＤＢ部品を呼び出し、<br>
     * 検索結果を返却する。
     * </p>
     * @param kokyakuId 顧客ＩＤ
     * @return 対応履歴検索結果
     * @throws Exception ○○例外
     * @author 甲斐正之
     * @version 0.1　2014/02/28　新規作成
     * @version 0.2　2014/03/17　STEP3対応
     * @version 0.3　2014/04/23　STEP4対応
     */
    public static BusinessResult<List<KokyakuTaiouRirekiInfo>> selectKokyakuTaiouRireki(String kokyakuId)
            throws Exception {

        //BusinessResult<List<TaiouRirekiParam>> result = new BusinessResult<List<TaiouRirekiParam>>();
        BusinessResult<List<KokyakuTaiouRirekiInfo>> result = new BusinessResult<List<KokyakuTaiouRirekiInfo>>();

        try {

            // 対応履歴の検索を実行する
            List<KokyakuTaiouRirekiInfo> resultlist = KokyakuTaiouRirekiInfo.find().where().eq("kokyakuId", kokyakuId)
                    .order("lastUpdateDT desc").findList();

            /*List<SqlRow> rowlist = KokyakuTaiouRirekiInfo.getQueryByKokyakuId(kokyakuId).findList();
            Iterator<SqlRow> it = rowlist.iterator();
            List<TaiouRirekiParam> resultlist = new ArrayList<TaiouRirekiParam>();

            while (it.hasNext())
            {
            	// 対応履歴の検索結果を１件ずづパラメータクラスに格納
            	SqlRow row = it.next();
            	TaiouRirekiParam param = new TaiouRirekiParam();
            	param.taiouId = row.getString("taiou_id");
            	param.kokyakuId = row.getString("kokyaku_id");
            	param.renrakushaName = row.getString("renrakusha_name");
            	param.telNo = row.getString("tel_no");
            	param.taiouNichiji = row.getString("taiou_nichiji");
            	param.taiouOperatorId = row.getString("taiou_operator_id");
            	param.operatorName = row.getString("operator_name");
            	param.operatorDepartment = row.getString("operator_department");
            	param.kanrenClientTaioId = row.getString("kanren_client_taiou_id");
            	param.toiawaseNaiyou = row.getString("toiawase_naiyou");
            	param.toiawaseSyubetsu = row.getString("toiawase_syubetsu");
            	param.taiouNaiyou = row.getString("taiou_naiyou");
            	param.taiouKekka = row.getString("taiou_kekka");
            	param.tsuuchiNaiyou = row.getString("tsuuchi_naiyou");
            	param.rirekiTourokuNengetsubi = row.getString("rireki_touroku_nengetsubi");
            	param.rirekiTourokuOperatorId = row.getString("rireki_touroku_operator_id");

            	resultlist.add(param);
            }*/

            result.setValue(resultlist);
            result.setResultCode(ResultCode.Success);

        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        }

        return result;
    }
}
