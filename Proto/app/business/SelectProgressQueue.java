package business;

import java.util.ArrayList;
import java.util.List;

import models.entities.ProgressQueue;

import common.BusinessResult;
import common.BusinessResult.ResultCode;

import constants.DbDataValue;
import constants.DbDataValue.Activity;
import constants.DbDataValue.CurrentTantouKinou;

/**
 * 手続き進捗キュー抽出業務部品.
 * <p>
 * 手続き進捗キューのＤＢ部品を呼び出し、<br>
 * 手続き進捗キューを抽出する業務部品
 * </p>
 * @author 甲斐正之
 * @version 0.1　2014/05/15　新規作成
 */
public final class SelectProgressQueue {
	private SelectProgressQueue() {
	}

	/**
	 * 手続き進捗キュー抽出.
	 * <p>
	 * 手続き進捗キューのＤＢ部品を呼び出し、<br>
	 * 手続き進捗キューを抽出する
	 * </p>
	 * @return 処理結果
	 * @throws Exception ○○例外
	 * @author 甲斐正之
	 * @version 0.1　2014/05/15　新規作成
	 */
	public static BusinessResult<List<String>> selectProgressQueue() throws Exception {

		// インスタンス生成
		BusinessResult<List<String>> result = new BusinessResult<List<String>>();
		List<String> idList = new ArrayList<String>();

		try {

			// 手続き進捗キューから担当機能がSWF、アクティビティＩＤが"100"のものを検索する。
			List<ProgressQueue> listQueue = ProgressQueue.find.where()
					.eq("currentTantouKinou", CurrentTantouKinou.SWF)
					.eq("activityId", Activity.SWF).findList();

			// 戻り値格納
			if (listQueue == null || listQueue.size() == 0) {
				// 申込情報が取得できなかった場合
				result.setResultCode(ResultCode.BusinessError);
			} else {
				// 申込情報が取得できた場合
				for (ProgressQueue queue : listQueue) {
					idList.add(queue.queueId);
				}
				result.setValue(idList);
				result.setResultCode(ResultCode.Success);
			}

		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}

		return result;
	}

	/**
	 * アクティビティキュー取得
	 * @param activityId アクティビティID
	 * @return アクティビティキューリスト
	 * @throws Exception ○○例外
	 */
	public static BusinessResult<List<ProgressQueue>> selectActiveQueue(String activityId) throws Exception {

		// インスタンス生成
		BusinessResult<List<ProgressQueue>> result = new BusinessResult<List<ProgressQueue>>();
		//List<String> idList = new ArrayList<String>();

		try {

			// 手続き進捗キューから担当機能がSWF、アクティビティＩＤが引数の値を使用して検索する。
			List<ProgressQueue> listQueue = ProgressQueue.find.where()
					.eq("currentTantouKinou", CurrentTantouKinou.SWF)
					.eq("activityId", activityId).findList();

			// 戻り値格納
			if (listQueue == null || listQueue.size() == 0) {
				// キュー情報が取得できなかった場合
				result.setResultCode(ResultCode.BusinessError);
			} else {
				// キュー情報が取得できた場合
				/*for (ProgressQueue queue : listQueue) {
					idList.add(queue.queueId);
				}*/
				result.setValue(listQueue);
				result.setResultCode(ResultCode.Success);
			}

		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}

		return result;
	}

	/**
	 * 手続き進捗キュー抽出.
	 * <p>
	 * 手続き進捗キューのＤＢ部品を呼び出し、<br>
	 * 手続き進捗キューを抽出する
	 * </p>
	 * @return 処理結果
	 * @author 甲斐正之
	 * @version 0.1　2014/07/31　新規作成
	 */
	public static BusinessResult<ProgressQueue> selectProgressForSOInput(String strOtherKeyColumn,
			String strOtherKeyData) throws Exception {

		// インスタンス生成
		BusinessResult<ProgressQueue> result = new BusinessResult<ProgressQueue>();
		List<ProgressQueue> listQueue = new ArrayList<ProgressQueue>();

		try {

			// キューIDをキーとして手続き進捗キューを検索する。
			listQueue = ProgressQueue.find.where()
					.eq("otherKeyColumn", strOtherKeyColumn)
					.eq("otherKeyData", strOtherKeyData).findList();

			// 戻り値格納
			if (listQueue == null || listQueue.size() == 0) {
				// キュー情報が取得できなかった場合
				result.setResultCode(ResultCode.BusinessError);
			} else {
				// キュー情報が取得できた場合
				result.setValue(listQueue.get(0));
				result.setResultCode(ResultCode.Success);
			}

		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}

		return result;
	}

	/**
	 * 手続き進捗キュー抽出.
	 * <p>
	 * 手続き進捗キューのＤＢ部品を呼び出し、<br>
	 * 手続き進捗キューを抽出する
	 * </p>
	 * @param queueId キューID
	 * @return 処理結果
	 * @throws Exception ○○例外
	 * @author 甲斐正之
	 * @version 0.1　2014/05/15　新規作成
	 */
	public static BusinessResult<ProgressQueue> selectProgressQueue(String queueId) throws Exception {

		// インスタンス生成
		BusinessResult<ProgressQueue> result = new BusinessResult<ProgressQueue>();
		ProgressQueue pq = new ProgressQueue();

		try {

			// キューIDをキーとして手続き進捗キューを検索する。
			pq = ProgressQueue.find.byId(queueId);
			// 戻り値格納
			result.setValue(pq);
			result.setResultCode(ResultCode.Success);

		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}

		return result;
	}
	
	/**
     * 手続き進捗キュー抽出.
     * <p>
     * 手続き進捗キューのＤＢ部品を呼び出し、<br>
     * 顧客ID、契約IDをキーに抽出する
     * </p>
     * @param queueId キューID
     * @return 処理結果
     * @throws Exception ○○例外
     * @author 甲斐正之
     * @version 0.1　2014/05/15　新規作成
     */
    public static BusinessResult<Integer> selectProgressQueueCountByKokyakuInfo(String strKokyakuId, String strKeiyakuId) throws Exception {

        // インスタンス生成
        BusinessResult<Integer> result = new BusinessResult<Integer>();

        try {

            // キューIDをキーとして手続き進捗キューを検索する。
            int i = ProgressQueue.find.where()
                    .eq("kokyakuId", strKokyakuId)
                    .eq("keiyakuId", strKeiyakuId)
                    .ne("activityId", DbDataValue.Activity.END)
                    .findRowCount();
            
            // 戻り値格納
            result.setValue(i);
            result.setResultCode(ResultCode.Success);

        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        }

        return result;
    }

}