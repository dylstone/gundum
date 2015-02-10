package business;

import java.util.List;

import models.entities.TransServiceInfo;
import common.BusinessResult;
import common.BusinessResult.ResultCode;

/**
 * トランザクション情報抽出業務部品.
 * <p>
 * トランザクション情報のＤＢ部品を呼び出し、<br>
 * トランザクション情報を抽出する業務部品
 * </p>
 * @author 甲斐正之
 * @version 0.1　2014/05/15　新規作成
 */
public final class SelectTransactionInfo {
    private SelectTransactionInfo() {
    }

    /**
     * トランザクション情報抽出.
     * <p>
     * トランザクション情報のＤＢ部品を呼び出し、<br>
     * トランザクション情報を抽出する
     * </p>
     * @praam transactionId トランザクション情報
     * @param kokyakuInfo 顧客情報
     * @return 処理結果
     * @author 甲斐正之
     * @version 0.1　2014/05/15　新規作成
     */
    /*public static BusinessResult<TransKokyakuInfo> selectTransactionInfo(String transactionId)
    		throws Exception {

    	// インスタンス生成
    	BusinessResult<TransKokyakuInfo> result = new BusinessResult<TransKokyakuInfo>();
    	List<TransKokyakuInfo> resultList = new ArrayList<TransKokyakuInfo>();

    	try {

    		// トランザクション(サービス情報)のステータスが未処理のものを検索する。
    		resultList = TransKokyakuInfo.find
    				.fetch("transKeiyakuInfo")
    				.fetch("transKeiyakuInfo.transServiceInfo")
    				.fetch("transKeiyakuInfo.transServiceInfo.serviceMaster")
    				.fetch("transKeiyakuInfo.transServiceInfo.dependService1")
    				.fetch("transKeiyakuInfo.transServiceInfo.dependService2")
    				.where().eq("transactionId", transactionId)
    				.eq("transKeiyakuInfo.transServiceInfo.soStatus", IFKey.MISHORI)
    				.findList();
    		// 戻り値格納
    		result.setValue(resultList.get(0));
    		result.setResultCode(ResultCode.Success);

    	} catch (Exception e) {
    		e.printStackTrace();
    		throw e;
    	}

    	return result;
    }*/

    /**
     * トランザクション情報抽出.
     * <p>
     * トランザクション情報のＤＢ部品を呼び出し、<br>
     * トランザクション情報を抽出する
     * </p>
     * @param transactionId トランザクション情報
     * @return 処理結果
     * @throws Exception ○○例外
     * @author 甲斐正之
     * @version 0.1　2014/05/15　新規作成
     */
    public static BusinessResult<TransServiceInfo> selectTransactionInfo(String transactionId)
            throws Exception {

        // インスタンス生成
        BusinessResult<TransServiceInfo> result = new BusinessResult<TransServiceInfo>();
        //List<TransServiceInfo> resultList = new ArrayList<TransServiceInfo>();

        try {

            // トランザクション(サービス情報)を検索する。
            /*resultList = TransServiceInfo.find
            		.where().eq("transactionId", transactionId).eq("serviceId", serviceId)
            		.findList();*/
            TransServiceInfo tsInfo = TransServiceInfo.find.byId(transactionId);
            // 戻り値格納
            result.setValue(tsInfo);
            result.setResultCode(ResultCode.Success);

        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        }

        return result;
    }

    /**
     * トランザクション情報抽出.
     * <p>
     * トランザクション情報のＤＢ部品を呼び出し、<br>
     * トランザクション情報を抽出する
     * </p>
     * @return 処理結果
     * @throws Exception ○○例外
     * @author 甲斐正之
     * @version 0.1　2014/05/15　新規作成
     */
    //public static BusinessResult<List<String>> selectTransactionInfo() throws Exception {
    public static BusinessResult<List<TransServiceInfo>> selectTransactionInfo() throws Exception {

        // インスタンス生成
        //BusinessResult<List<String>> result = new BusinessResult<List<String>>();
        BusinessResult<List<TransServiceInfo>> result = new BusinessResult<List<TransServiceInfo>>();
        //List<String> idList = new ArrayList<String>();

        try {

            // トランザクション(サービス情報)から手続き進捗キュー未登録のデータを検索する。
            /*List<TransKokyakuInfo> listTrans = TransKokyakuInfo.find
            		.fetch("transKeiyakuInfo")
            		.fetch("transKeiyakuInfo.transServiceInfo")
            		.fetch("transKeiyakuInfo.transServiceInfo.serviceMaster")
            		.fetch("transKeiyakuInfo.transServiceInfo.dependService1")
            		.fetch("transKeiyakuInfo.transServiceInfo.dependService2")
            		.where().eq("transKeiyakuInfo.transServiceInfo.soStatus", IFKey.MISHORI)
            		.findList();*/
            List<TransServiceInfo> listTrans = TransServiceInfo.find
                    .fetch("serviceMaster")
                    .fetch("dependService1")
                    .fetch("dependService2")
                    .where().eq("tourokuFlg", "0").findList();
            // 戻り値格納
            if (listTrans == null || listTrans.size() == 0) {
                // トランザクション情報が取得できなかった場合
                result.setResultCode(ResultCode.BusinessError);
            } else {
                // トランザクション情報が取得できた場合
                /*for (TransKokyakuInfo info : listTrans) {
                	idList.add(info.transactionId);
                }*/
                result.setValue(listTrans);
                result.setResultCode(ResultCode.Success);
            }

        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        }

        return result;
    }

}