package business;

import java.util.ArrayList;
import java.util.List;

import models.entities.ApplyXml;

import common.BusinessResult;
import common.BusinessResult.ResultCode;

/**
 * 申込XML情報検索業務部品.
 * <p>
 * 申込XM情報のＤＢ部品を呼び出し、<br>
 * 検索結果を返却する業務部品
 * </p>
 * @author 那須　智貴
 * @version 0.1　2014/07/28　新規作成
 */
public final class SelectApplyXML {
    private SelectApplyXML() {
    }

    /**
     * 申込XML情報検索
     * <p>
     * 申込XMLのＤＢ部品を呼び出し、<br>
     * 検索結果を返却する
     * </p>
     * @param kokyakuId 顧客ID
     * @param keiyakuId 契約ID
     * @return 申込XML
     * @throws Exception ○○例外
     * @author 那須　智貴
     * @version 0.1　2014/07/28　新規作成
     */
    public static BusinessResult<List<ApplyXml>> selectApplyXML(String kokyakuId, String keiyakuId)
            throws Exception {
        
        // インスタンス生成
        BusinessResult<List<ApplyXml>> result = new BusinessResult<List<ApplyXml>>();
        List<ApplyXml> listApplyXml = new ArrayList<ApplyXml>();

        try {
            
            // 申込XML取得
            listApplyXml = ApplyXml.find.where().eq("kokyakuId", kokyakuId).eq("keiyakuId", keiyakuId).findList();
            
            // 戻り値セット
            result.setResultCode(ResultCode.Success);
            result.setValue(listApplyXml);

        } catch (Exception e) {
            e.printStackTrace();
            result.setResultCode(ResultCode.BusinessError);
            result.setValue(listApplyXml);
            throw e;
        }

        return result;

    }
    
    /**
     * 申込XML情報検索
     * <p>
     * 申込XMLのＤＢ部品を呼び出し、<br>
     * 検索結果を返却する
     * </p>
     * @param queueId 申込受付キー
     * @return 申込XML
     * @throws Exception ○○例外
     * @author 那須　智貴
     * @version 0.1　2014/08/12　新規作成
     */
    public static BusinessResult<List<ApplyXml>> selectApplyXMLByApplyId(String strApplyId)
            throws Exception {
        
        // インスタンス生成
        BusinessResult<List<ApplyXml>> result = new BusinessResult<List<ApplyXml>>();
        List<ApplyXml> listApplyXml = new ArrayList<ApplyXml>();

        try {
            
            // 申込XML取得
            listApplyXml = ApplyXml.find.where().eq("applyId", strApplyId).findList();
            
            // 戻り値セット
            result.setResultCode(ResultCode.Success);
            result.setValue(listApplyXml);

        } catch (Exception e) {
            e.printStackTrace();
            result.setResultCode(ResultCode.BusinessError);
            result.setValue(listApplyXml);
            throw e;
        }

        return result;

    }
}