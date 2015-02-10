package common.business;

import java.lang.reflect.Field;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import models.entities.SOOutput;
import models.entities.TransServiceInfo;

import common.BusinessResult;
import common.BusinessResult.ResultCode;

/**
 * トランザクション業務部品
 * <p>
 * トランザクションのＤＢ部品を呼び出し、<br>
 * 検索/更新を行う。
 * </p>
 * @author 那須智貴
 * @version 0.1　2014/05/13　新規作成
 */
public final class TransactionManagement {
    private TransactionManagement() {
    }

    /**
     * トランザクション(顧客情報)抽出処理
     * <p>
     * トランザクション(顧客情報)の抽出を行う。
     * </p>
     * @return トランザクションの更新対象件数
     * @author 那須智貴
     * @version 0.1　2014/05/13　新規作成
     */
    /*public static BusinessResult<List<TransKokyakuInfo>> selectTransKokyaku(String strStatus)
    		throws Exception {

    	// インスタンス生成
    	BusinessResult<List<TransKokyakuInfo>> result = new BusinessResult<List<TransKokyakuInfo>>();
    	List<TransKokyakuInfo> resultList = new ArrayList<TransKokyakuInfo>();

    	try {

    		// トランザクション(顧客情報)のステータスが未処理のものを検索する。
    		resultList = TransKokyakuInfo.find.where().eq("status", strStatus).findList();
    		// 戻り値格納
    		result.setValue(resultList);
    		result.setResultCode(ResultCode.Success);

    	} catch (Exception e) {
    		e.printStackTrace();
    		throw e;
    	}

    	return result;
    }*/

    /**
     * トランザクション(契約情報)抽出処理
     * <p>
     * トランザクション(契約情報)の抽出を行う。
     * </p>
     * @return トランザクションの更新対象件数
     * @author 那須智貴
     * @version 0.1　2014/05/13　新規作成
     */
    /*public static BusinessResult<List<TransKeiyakuInfo>> selectTransKeiyaku(String strStatus)
    		throws Exception {

    	// インスタンス生成
    	BusinessResult<List<TransKeiyakuInfo>> result = new BusinessResult<List<TransKeiyakuInfo>>();
    	List<TransKeiyakuInfo> resultList = new ArrayList<TransKeiyakuInfo>();

    	try {

    		// トランザクション(契約情報)のステータスが未処理のものを検索する。
    		resultList = TransKeiyakuInfo.find.where().eq("soStatus", strStatus).findList();
    		// 戻り値格納
    		result.setValue(resultList);
    		result.setResultCode(ResultCode.Success);

    	} catch (Exception e) {
    		e.printStackTrace();
    		throw e;
    	}

    	return result;
    }*/

    /**
     * トランザクション(サービス情報)抽出処理（SOステイタスを条件にした抽出）
     * <p>
     * トランザクション(サービス情報)の抽出を行う。
     * </p>
     * @param strSOStatus SOステータス
     * @return トランザクションの更新対象件数
     * @throws Exception ○○例外
     * @author 那須智貴
     * @version 0.1　2014/05/13　新規作成
     */
    public static BusinessResult<List<TransServiceInfo>> selectTransServiceSOStatus(String strSOStatus)
            throws Exception {

        // インスタンス生成
        BusinessResult<List<TransServiceInfo>> result = new BusinessResult<List<TransServiceInfo>>();
        List<TransServiceInfo> resultList = new ArrayList<TransServiceInfo>();

        try {

            // トランザクション(サービス情報)のステータスが未処理のものを検索する。
            resultList = TransServiceInfo.find.where().eq("soStatus", strSOStatus).findList();
            // 戻り値格納
            result.setValue(resultList);
            result.setResultCode(ResultCode.Success);

        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        }

        return result;
    }

    /**
     * トランザクション(サービス情報)抽出処理
     * <p>
     * トランザクション(サービス情報)の抽出を行う。
     * </p>
     * @param strServiceId サービスID
     * @param strTransactionId トランザクションID
     * @return 更新対象のトランザクション
     * @throws Exception ○○例外
     * @author 那須智貴
     * @version 0.1　2014/05/13　新規作成
     */
    public static BusinessResult<List<TransServiceInfo>> selectTransService(String strServiceId, String strTransactionId)
            throws Exception {

        // インスタンス生成
        BusinessResult<List<TransServiceInfo>> result = new BusinessResult<List<TransServiceInfo>>();
        List<TransServiceInfo> lstTrans = new ArrayList<TransServiceInfo>();

        try {
            lstTrans = TransServiceInfo.find
                    .fetch("serviceMaster")
                    .fetch("dependService1")
                    .fetch("dependService2")
                    .where().eq("transactionId", strTransactionId).eq("serviceId", strServiceId)
                    .findList();

            result.setValue(lstTrans);
            result.setResultCode(ResultCode.Success);

        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        }

        return result;
    }

    /**
     * トランザクションサービス取得
     * @param strKeiyakuId 契約ID
     * @return トランザクションサービス情報
     * @throws Exception ○○例外
     */
    public static BusinessResult<List<TransServiceInfo>> selectTransService(String strKeiyakuId)
            throws Exception {

        // インスタンス生成
        BusinessResult<List<TransServiceInfo>> result = new BusinessResult<List<TransServiceInfo>>();
        List<TransServiceInfo> lstTrans = new ArrayList<TransServiceInfo>();

        try {
            lstTrans = TransServiceInfo.find
                    .fetch("serviceMaster")
                    .fetch("dependService1")
                    .fetch("dependService2")
                    .where().eq("keiyakuId", strKeiyakuId)
                    .order("serviceId")
                    .findList();

            result.setValue(lstTrans);
            result.setResultCode(ResultCode.Success);

        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        }

        return result;
    }

    /**
     * トランザクション(サービス情報)のＳＯステータス更新処理
     * <p>
     * トランザクション(サービス情報)のＳＯステータス更新を行う。
     * </p>
     * @param strTransactionId トランザクションID
     * @param strSOStatus SOステイタス（更新対象となるステイタス）
     * @param strIntervalTime SOステイタス（更新値となるステイタス）
     * @param soOutput SO結果モデル
     * @return 更新結果
     * @throws Exception ○○例外
     * @author 那須智貴
     * @version 0.1　2014/05/13　新規作成
     */
    public static BusinessResult<String> updateTransaction(
            String strTransactionId, String strSOStatus, String strIntervalTime, SOOutput soOutput)
            throws Exception {

        // インスタンス生成
        BusinessResult<String> result = new BusinessResult<String>();
        //List<TransServiceInfo> lstTrans = new ArrayList<TransServiceInfo>();
        Field field;

        try {

            TransServiceInfo tsInfo = TransServiceInfo.find.byId(strTransactionId);

            if (tsInfo != null) {
                Date nowDate = Calendar.getInstance().getTime();
                SimpleDateFormat sdf1 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                String strDate = sdf1.format(nowDate);
                Timestamp time = Timestamp.valueOf(strDate);

                // 引数のステータスがnullの場合はステータスを更新しない
                if (strSOStatus != null) {
                    tsInfo.soStatus = strSOStatus;
                }

                if (strIntervalTime != null) {
                    tsInfo.intervalTime = strIntervalTime;
                }

                tsInfo.lastUpdateUser = "BatchUser";
                // SO結果反映が存在する場合はトランザクションに格納
                if (soOutput != null && soOutput.soResultColumn != null && !soOutput.soResultColumn.isEmpty()) {
                    field = tsInfo.getClass().getField(soOutput.soResultColumn);
                    field.setAccessible(false);
                    field.set(tsInfo, soOutput.soResultData);
                }
                int updResult = models.sqlrows.TransServiceInfo.updateTransactionService(tsInfo, time);

                if (updResult == 0) {
                    result.setValue("");
                    result.setResultCode(ResultCode.BusinessError);
                }

            } else {
                result.setValue("");
                result.setResultCode(ResultCode.BusinessError);
            }
            result.setValue("");
            result.setResultCode(ResultCode.Success);

        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        }

        return result;
    }

    /**
     * トランザクション(サービス情報)のＳＯステータス更新処理
     * <p>
     * トランザクション(サービス情報)のＳＯステータス更新を行う。
     * </p>
     * @param strServiceId サービスID
     * @param strTransactionId トランザクションID
     * @param strSOStatus SOステイタス
     * @param strIntervalTime 起動間隔
     * @param soOutput SO結果モデル
     * @return 更新結果
     * @throws Exception ○○例外
     * @author 那須智貴
     * @version 0.1　2014/05/13　新規作成
     */
    public static BusinessResult<String> updateTransaction(
            String strServiceId, String strTransactionId, String strSOStatus, String strIntervalTime, SOOutput soOutput)
            throws Exception {

        // インスタンス生成
        BusinessResult<String> result = new BusinessResult<String>();
        List<TransServiceInfo> lstTrans = new ArrayList<TransServiceInfo>();
        Field field;

        try {

            lstTrans = TransServiceInfo.find.where()
                    .eq("transactionId", strTransactionId)
                    .eq("serviceId", strServiceId)
                    .findList();

            if (lstTrans.size() == 1) {

                Date nowDate = Calendar.getInstance().getTime();
                SimpleDateFormat sdf1 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                String strDate = sdf1.format(nowDate);
                Timestamp time = Timestamp.valueOf(strDate);

                for (TransServiceInfo info : lstTrans) {
                    // 引数のステータスがnullの場合はステータスを更新しない
                    if (strSOStatus != null) {
                        info.soStatus = strSOStatus;
                    }
                    info.intervalTime = strIntervalTime;
                    info.lastUpdateUser = "BatchUser";
                    // SO結果反映が存在する場合はトランザクションに格納
                    if (soOutput != null) {
                        field = info.getClass().getField(soOutput.soResultColumn);
                        field.setAccessible(false);
                        field.set(info, soOutput.soResultData);
                    }
                    int updResult = models.sqlrows.TransServiceInfo.updateTransactionService(info, time);
                    if (updResult == 0) {
                        result.setValue("");
                        result.setResultCode(ResultCode.BusinessError);
                    }
                }
            } else if (lstTrans.size() > 1) {
                result.setValue("");
                result.setResultCode(ResultCode.BusinessError);
            }
            result.setValue("");
            result.setResultCode(ResultCode.Success);

        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        }

        return result;
    }

    /**
     * トランザクション(サービス情報)削除処理
     * <p>
     * トランザクション(サービス情報)の削除を行う。
     * </p>
     * @param strServiceId サービスID
     * @param strTransactionId トランザクションID
     * @param strSOStatus SOステイタス（更新対象となるステイタス）
     * @return 削除結果
     * @throws Exception ○○例外
     * @author 那須智貴
     * @version 0.1　2014/05/13　新規作成
     */
    public static BusinessResult<String> deleteTransactionInfo(String strServiceId, String strTransactionId,
            String strSOStatus)
            throws Exception {

        // インスタンス生成
        BusinessResult<String> result = new BusinessResult<String>();
        List<TransServiceInfo> lstTrans = new ArrayList<TransServiceInfo>();

        try {
            lstTrans = TransServiceInfo.find.where()
                    .eq("transactionId", strTransactionId)
                    .eq("serviceId", strServiceId)
                    .eq("soStatus", strSOStatus)
                    .findList();

            if (lstTrans.size() == 1) {
                for (TransServiceInfo info : lstTrans) {
                    info.delete();
                }
            } else if (lstTrans.size() > 1) {
                result.setValue("");
                result.setResultCode(ResultCode.BusinessError);
            }
            result.setValue("");
            result.setResultCode(ResultCode.Success);

        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        }

        return result;
    }

    /**
     * トランザクション(サービス情報)抽出処理
     * <p>
     * パーマネント未反映のトランザクション(サービス情報)の抽出を行う。
     * </p>
     * @param transactionId トランザクションID
     * @param serviceId サービスID
     * @param strStatus ステータス
     * @return 未反映件数
     * @throws Exception ○○例外
     * @author 甲斐正之
     * @version 0.1　2014/05/21　新規作成
     */
    public static BusinessResult<Integer> selectTransService(String transactionId, String serviceId, String strStatus)
            throws Exception {

        // インスタンス生成
        BusinessResult<Integer> result = new BusinessResult<Integer>();
        List<TransServiceInfo> resultList = new ArrayList<TransServiceInfo>();

        try {

            // トランザクション(サービス情報)のステータスが未処理のものを検索する。
            resultList = TransServiceInfo.find.where().eq("transactionId", transactionId).ne("serviceId", serviceId)
                    .ne("soStatus", strStatus).findList();
            // 戻り値格納
            result.setValue(Integer.valueOf(resultList.size()));
            result.setResultCode(ResultCode.Success);

        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        }

        return result;
    }

    /**
     * トランザクションサービス件数取得
     * @param keiyakuId 契約ID
     * @return トランザクションサービス件数
     * @throws Exception ○○例外
     */
    public static BusinessResult<Integer> countTransService(String keiyakuId) throws Exception {

        // インスタンス生成
        BusinessResult<Integer> result = new BusinessResult<Integer>();
        List<TransServiceInfo> resultList = new ArrayList<TransServiceInfo>();

        try {

            // トランザクション(サービス情報)のステータスが未処理のものを検索する。
            resultList = TransServiceInfo.find.where().eq("keiyakuId", keiyakuId).findList();
            // 戻り値格納
            result.setValue(Integer.valueOf(resultList.size()));
            result.setResultCode(ResultCode.Success);

        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        }

        return result;
    }

    /**
     * トランザクション情削除処理
     * <p>
     * トランザクション情報の削除を行う。
     * </p>
     * @param サービスID
     * @param トランザクションID
     * @param SOステイタス（更新対象となるステイタス）
     * @return 削除結果
     * @author 甲斐正之
     * @version 0.1　2014/05/21　新規作成
     */
    /*public static BusinessResult<String> deleteTransactionAll(String strTransactionId) throws Exception {

    	// インスタンス生成
    	BusinessResult<String> result = new BusinessResult<String>();
    	List<TransKokyakuInfo> lstTrans = new ArrayList<TransKokyakuInfo>();

    	try {
    		lstTrans = TransKokyakuInfo.find
    				.fetch("transKeiyakuInfo")
    				.fetch("transKeiyakuInfo.transServiceInfo")
    				.where().eq("transactionId", strTransactionId)
    				.findList();

    		if (lstTrans.size() == 1) {
    			for (TransKokyakuInfo info : lstTrans) {
    				info.delete();
    			}
    		} else if (lstTrans.size() > 1) {
    			result.setValue("");
    			result.setResultCode(ResultCode.BusinessError);
    		}
    		result.setValue("");
    		result.setResultCode(ResultCode.Success);

    	} catch (Exception e) {
    		e.printStackTrace();
    		throw e;
    	}

    	return result;
    }*/

    /**
     * トランザクション削除処理
     * @param tsInfoList トランザクション情報リスト
     * @return 処理結果
     * @throws Exception ○○例外
     */
    public static BusinessResult<String> deleteTransaction(List<TransServiceInfo> tsInfoList) throws Exception {

        // インスタンス生成
        BusinessResult<String> result = new BusinessResult<String>();

        try {
            for (TransServiceInfo tsInfo : tsInfoList) {

                tsInfo.delete();
            }
            result.setValue("");
            result.setResultCode(ResultCode.Success);

        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        }

        return result;
    }

    /**
     * トランザクション情報削除
     * @param tsInfo トランザクション情報
     * @return 処理結果
     * @throws Exception ○○例外
     */
    public static BusinessResult<String> deleteTransaction(TransServiceInfo tsInfo) throws Exception {

        // インスタンス生成
        BusinessResult<String> result = new BusinessResult<String>();

        try {
            tsInfo.delete();
            result.setValue("");
            result.setResultCode(ResultCode.Success);

        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        }

        return result;
    }
}
