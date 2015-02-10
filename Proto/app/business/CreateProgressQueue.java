package business;

import models.entities.ProgressQueue;
import models.entities.TransServiceInfo;

import common.BusinessResult;
import common.BusinessResult.ResultCode;
import common.business.IdKanri;

import constants.DbDataValue.Activity;
import constants.DbDataValue.CurrentTantouKinou;
import constants.DbDataValue.IdCd;
import constants.DbDataValue.MainTantouKinou;

/**
 * 手続き進捗キュー登録業務部品.
 * <p>
 * 手続き進捗キューのＤＢ部品を呼び出し、<br>
 * 登録を実行する業務部品
 * </p>
 * @author 甲斐正之
 * @version 0.1　2014/05/15　新規作成
 */
public final class CreateProgressQueue {

    private CreateProgressQueue() {
    }

    /**
     * 手続き進捗キュー(SWF)登録.
     * <p>
     * 手続き進捗キューのＤＢ部品を呼び出し、<br>
     * 登録を実行する
     * </p>
     * @param transInfo トランザクション情報(サービス情報)
     * @return 処理結果
     * @throws Exception ○○例外
     * @author 甲斐正之
     * @version 0.1　2014/05/15　新規作成
     */
    public static BusinessResult<String> createProgressQueue(TransServiceInfo transInfo) throws Exception {

        BusinessResult<String> result = new BusinessResult<String>();

        try {

            ProgressQueue pq = setProgressQueue(transInfo);
            pq.save();

            result.setResultCode(ResultCode.Success);

        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        }

        return result;

    }

    /*/**
     * 手続き進捗キュー(バッチ)登録.
     * <p>
     * 手続き進捗キューのＤＢ部品を呼び出し、<br>
     * 登録を実行する
     * </p>
     * @praam batchId バッチＩＤ
     * @param key キー情報
     * @return 処理結果
     * @author 甲斐正之
     * @version 0.1　2014/05/15　新規作成
     */
    /*public static BusinessResult<String> createBatchQueue(String batchId, String key)
    		throws Exception {

    	BusinessResult<String> result = new BusinessResult<String>();

    	try {

    		ProgressQueue pq = setBatchQueue(batchId, key);
    		pq.save();

    		result.setResultCode(ResultCode.Success);

    	} catch (Exception e) {
    		e.printStackTrace();
    		throw e;
    	}

    	return result;

    }*/

    /**
     * 手続き進捗キュー設定.
     * <p>
     * 手続き進捗キューに値を設定する
     * </p>
     * @param transInfo トランザクション情報(サービス情報)
     * @return 手続き進捗キュークラス
     * @author 甲斐正之
     * @version 0.1　2014/05/15　新規作成
     */
    private static ProgressQueue setProgressQueue(TransServiceInfo transInfo) {

        ProgressQueue queue = new ProgressQueue();
        queue.queueId = IdKanri.getId(IdCd.QUEUE);
        //queue.kokyakuId = transInfo.transKeiyakuInfo.transKokyakuInfo.kokyakuId;
        queue.kokyakuId = transInfo.kokyakuId;
        //queue.keiyakuId = transInfo.transKeiyakuInfo.keiyakuId;
        queue.keiyakuId = transInfo.keiyakuId;
        queue.serviceId = transInfo.serviceId;
        queue.transactionId = transInfo.transactionId;
        queue.mainTantouKinou = MainTantouKinou.SWF;
        queue.currentTantouKinou = CurrentTantouKinou.SWF;
        queue.activityId = Activity.SWF;
        queue.ifKey = null;
        queue.otherKeyColumn = null;
        queue.otherKeyData = null;
        queue.deleteDate = null;
        queue.createUser = "BatchUser";
        queue.lastUpdateUser = "BatchUser";

        return queue;

    }

    /*/**
     * 手続き進捗キュー(バッチ)設定.
     * <p>
     * 手続き進捗キューに値を設定する
     * </p>
     * @praam batchId バッチＩＤ
     * @param key キー情報
     * @return 手続き進捗キュークラス
     * @author 甲斐正之
     * @version 0.1　2014/05/15　新規作成
     */
    /*private static ProgressQueue setBatchQueue(String batchId, String key) {

    	ProgressQueue queue = new ProgressQueue();
    	queue.queueId = IdKanri.getId(IdCd.QUEUE);
    	queue.kokyakuId = null;
    	queue.keiyakuId = null;
    	queue.serviceId = null;
    	queue.transactionId = null;
    	queue.mainTantouKinou = MainTantouKinou.BATCH;
    	queue.currentTantouKinou = CURRENT_TANTOU_KINOU.BATCH;
    	queue.status = ShoriStatus.MISHORI;
    	queue.ifKey = null;
    	queue.otherKeyColumn = batchId;
    	queue.otherKeyData = key;
    	queue.deleteDate = null;
    	queue.createUser = "BatchUser";
    	queue.lastUpdateUser = "BatchUser";

    	return queue;

    }*/

}