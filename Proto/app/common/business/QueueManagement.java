package common.business;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import models.entities.ApplyUketsukeKey;
import models.entities.ProgressQueue;
import models.entities.TransServiceInfo;

import com.avaje.ebean.Ebean;
import com.avaje.ebean.SqlRow;
import common.BusinessResult;
import common.BusinessResult.ResultCode;

import constants.DbDataValue;

/**
 * ワークフロー起動キュー業務部品
 * <p>
 * 起動キューのＤＢ部品を呼び出し、<br>
 * 検索/更新を行う。
 * </p>
 * @author 那須智貴
 * @version 0.1　2014/05/13　新規作成
 */
public final class QueueManagement {
	private QueueManagement() {
	}

	/**
	 * キュー作成(サービス情報)処理
	 * <p>
	 * キュー作成を行うModelを作成
	 * </p>
	 * @param lstTrans トランザクションリスト
	 * @return 進捗キーリスト
	 * @throws Exception ○○例外
	 * @author 那須智貴
	 * @version 0.1　2014/05/13　新規作成
	 */
	public static BusinessResult<List<ProgressQueue>> makeQueueService(List<TransServiceInfo> lstTrans)
			throws Exception {

		// インスタンス生成
		BusinessResult<List<ProgressQueue>> result = new BusinessResult<List<ProgressQueue>>();
		List<ProgressQueue> listQueue = new ArrayList<ProgressQueue>();

		try {
			// キューを作成するためのトランザクションがある場合
			if (lstTrans.size() > 0) {

				// 日付取得
				Date nowDate = Calendar.getInstance().getTime();
				SimpleDateFormat sdf1 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
				String strDate = sdf1.format(nowDate);
				Timestamp time = Timestamp.valueOf(strDate);

				for (TransServiceInfo info : lstTrans) {
					// キューを作成
					ProgressQueue progressQueue = new ProgressQueue();
					progressQueue.queueId = IdKanri.getId(DbDataValue.IdCd.QUEUE);
					progressQueue.kokyakuId = "";
					progressQueue.keiyakuId = "";
					progressQueue.serviceId = info.serviceId;
					progressQueue.transactionId = info.transactionId;
					progressQueue.mainTantouKinou = DbDataValue.MainTantouKinou.SWF;
					progressQueue.currentTantouKinou = DbDataValue.CurrentTantouKinou.SWF;
					//progressQueue.status = DbDataValue.ShoriStatus.MISHORI;
					progressQueue.activityId = DbDataValue.Activity.SWF;
					//progressQueue.ifKey = DbDataValue.IFKey.SHOKI;
					progressQueue.ifKey = "";
					progressQueue.otherKeyColumn = "";
					progressQueue.otherKeyData = "";
					progressQueue.deleteDate = "";
					progressQueue.createUser = "BATCH";
					progressQueue.lastUpdateUser = "BATCH";
					progressQueue.createDT = time;
					progressQueue.lastUpdateDT = time;
					listQueue.add(progressQueue);
				}
			} else {
				// ない場合は無視
			}

			result.setValue(listQueue);
			result.setResultCode(ResultCode.Success);

		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}

		return result;
	}

	/**
	 * キュー作成登録処理（LISTより取り出す）
	 * <p>
	 * キュー作成を行うDB部品を呼び出す。
	 * </p>
	 * @param lstQueue キューリスト
	 * @return 登録処理結果
	 * @throws Exception ○○例外
	 * @author 那須智貴
	 * @version 0.1　2014/05/13　新規作成
	 */
	public static BusinessResult<String> updateQueue(List<ProgressQueue> lstQueue)
			throws Exception {

		// インスタンス生成
		BusinessResult<String> result = new BusinessResult<String>();

		try {
			// キューを作成するためのトランザクションがある場合
			if (lstQueue.size() == 1) {
				for (ProgressQueue info : lstQueue) {
					info.save();
				}
			} else {
				// ない場合は無視
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
	 * キューのアクティビティ更新処理
	 * <p>
	 * キューのアクティビティ更新を行う。
	 * </p>
	 * @param queue 進捗キュー
	 * @param strActivityId アクティビティID
	 * @param strOtherKeyColumn その他のキーカラム
	 * @param strOtherKeyData その他のキーデータ
	 * @return 更新結果
	 * @throws Exception ○○例外
	 * @author 甲斐
	 * @version 0.1　2014/06/23　新規作成
	 */
	public static BusinessResult<String> updateProgressQueue(ProgressQueue queue, String strActivityId,
			String strOtherKeyColumn, String strOtherKeyData)
			throws Exception {

		// インスタンス生成
		BusinessResult<String> result = new BusinessResult<String>();

		try {
			if (queue != null) {
				queue.activityId = strActivityId;
				queue.otherKeyColumn = strOtherKeyColumn;
				queue.otherKeyData = strOtherKeyData;
				queue.lastUpdateUser = "BatchUser";
				queue.update();

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
	 * 手続き進捗キューのアクティビティ更新処理
	 * <p>
	 * 手続き進捗キューのアクティビティ更新を行う。
	 * </p>
	 * @author 甲斐
	 * @version 0.1　2014/06/23　新規作成
	 */
	public static BusinessResult<String> updateProgressQueue(ProgressQueue queue, String strActivityId)
			throws Exception {

		// インスタンス生成
		BusinessResult<String> result = new BusinessResult<String>();

		try {
			if (queue != null) {
				queue.activityId = strActivityId;
				queue.lastUpdateUser = "BatchUser";
				queue.update();

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
	 * 進捗キュー更新処理
	 * @param keiyakuId 契約ID
	 * @param strActivityId アクティビティID
	 * @param nextActivityId 次アクティビティID
	 * @return 処理結果
	 * @throws Exception ○○例外
	 */
	public static BusinessResult<String> updateProgressQueue(String keiyakuId, String strActivityId,
			String nextActivityId)
			throws Exception {

		// インスタンス生成
		BusinessResult<String> result = new BusinessResult<String>();

		try {
			List<ProgressQueue> lstQueue = ProgressQueue.find.where().eq("keiyakuId", keiyakuId)
					.eq("activityId", strActivityId).findList();

			if (lstQueue.size() > 0) {
				for (ProgressQueue queue : lstQueue) {
					queue.activityId = nextActivityId;
					queue.lastUpdateUser = "BatchUser";
					queue.update();
				}
				result.setValue("");
				result.setResultCode(ResultCode.Success);
			} else {
				result.setValue("");
				result.setResultCode(ResultCode.BusinessError);
			}

		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}

		return result;
	}

	/**
	 * 進捗キーチェック処理
	 * @param strActivityId アクティビティID
	 * @return 進捗キーリスト
	 * @throws Exception ○○例外
	 */
	public static BusinessResult<List<String>> checkProgressQueue(String strActivityId) throws Exception {

		// インスタンス生成
		BusinessResult<List<String>> result = new BusinessResult<List<String>>();
		BusinessResult<Integer> resultCnt = new BusinessResult<Integer>();
		List<String> keiyakuList = new ArrayList<String>();

		try {

			StringBuilder sql = new StringBuilder();
			sql.append("SELECT keiyaku_id, activity_id, COUNT(queue_id) as cnt ");
			sql.append("FROM progress_queue ");
			sql.append("WHERE activity_id = :id ");
			sql.append("GROUP BY keiyaku_id, activity_id");
			List<SqlRow> sqlRows = Ebean.createSqlQuery(sql.toString())
					.setParameter("id", strActivityId)
					.findList();

			for (SqlRow row : sqlRows) {
				String keiyakuId = row.getString("keiyaku_id");
				resultCnt = TransactionManagement.countTransService(keiyakuId);
				if (resultCnt.getValue().equals(row.getInteger("cnt"))) {
					keiyakuList.add(keiyakuId);
				}
			}

			result.setValue(keiyakuList);
			result.setResultCode(ResultCode.Success);

		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}

		return result;
	}

	/**
	 * 申込受付キーチェック処理
	 * @param strActivityId アクティビティID
	 * @return 申込受付キーリスト
	 * @throws Exception ○○例外
	 */
	public static BusinessResult<List<ApplyUketsukeKey>> checkUketsukeKey(String strActivityId) throws Exception {

		// インスタンス生成
		BusinessResult<List<ApplyUketsukeKey>> result = new BusinessResult<List<ApplyUketsukeKey>>();

		try {

			List<ApplyUketsukeKey> lstQueue = ApplyUketsukeKey.find.where()
					.eq("activityId", strActivityId).order("keiyakuId, queueId").findList();

			if (lstQueue.size() > 0) {
				result.setResultCode(ResultCode.Success);
				result.setValue(lstQueue);

			} else {
				result.setResultCode(ResultCode.BusinessError);
			}

		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}

		return result;
	}

	/**
	 * キューのIFKey更新処理
	 * <p>
	 * キューのIFKey更新を行う。
	 * </p>
	 * @param strQueueId キューID
	 * @param strIFKeyCond IFキー条件
	 * @param strActivityId アクティビティID
	 * @param strOtherKeyColumn その他キーカラム
	 * @param strOtherKeyData その他キーデータ
	 * @return トランザクションの更新対象件数
	 * @throws Exception ○○例外
	 * @author 那須智貴
	 * @version 0.1　2014/05/13　新規作成
	 */
	public static BusinessResult<String> updateQueueIFKey(String strQueueId, String strIFKeyCond,
			String strActivityId, String strOtherKeyColumn, String strOtherKeyData)
			throws Exception {

		// インスタンス生成
		BusinessResult<String> result = new BusinessResult<String>();
		List<ProgressQueue> lstQueue = new ArrayList<ProgressQueue>();

		try {
			lstQueue = ProgressQueue.find.where()
					.eq("queueId", strQueueId)
					//.eq("ifKey", strIFKeyCond)
					.findList();

			if (lstQueue.size() == 1) {
				for (ProgressQueue info : lstQueue) {
					info.activityId = strActivityId;
					info.otherKeyColumn = strOtherKeyColumn;
					info.otherKeyData = strOtherKeyData;
					info.lastUpdateUser = "BatchUser";
					info.update();
				}
			} else if (lstQueue.size() > 1) {
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
	 * キュー削除処理
	 * <p>
	 * キューの削除を行う。
	 * </p>
	 * @param strQueueId キューID
	 * @return トランザクションの更新対象件数
	 * @throws Exception ○○例外
	 * @author 那須智貴
	 * @version 0.1　2014/05/13　新規作成
	 */
	public static BusinessResult<String> deleteProgressQueue(String strQueueId)
			throws Exception {

		// インスタンス生成
		BusinessResult<String> result = new BusinessResult<String>();
		List<ProgressQueue> lstQueue = new ArrayList<ProgressQueue>();

		try {
			lstQueue = ProgressQueue.find.where().eq("queueId", strQueueId).findList();

			if (lstQueue.size() == 1) {
				for (ProgressQueue info : lstQueue) {
					info.delete();
				}
			} else if (lstQueue.size() > 1) {
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

}
