package controllers;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import models.entities.NewServiceInfo;
import models.entities.ProgressQueue;
import models.entities.SOInput;
import models.entities.SOOutput;
import models.entities.TransServiceInfo;
import play.mvc.Controller;
import play.mvc.Result;
import business.InsertSOInput;
import business.PermanentManagement;
import business.ReceiveSOOutput;
import business.ReflectSOOutput;
import business.SelectProgressQueue;
import business.SelectTransactionInfo;
import business.SendSOInput;

import com.avaje.ebean.Ebean;
import common.BusinessResult;
import common.BusinessResult.ResultCode;
import common.StartServiceWorkFlow;
import common.business.QueueManagement;
import common.business.TransactionManagement;

import constants.DbDataValue;
import constants.DbDataValue.Activity;
import constants.DbDataValue.ShoriStatus;

/**
 *  手続き部品呼び出しコントローラ
 *  <p>
 *  ・Pearlより呼び出されるコントローラ。
 *  ・手続きを行う業務部品をコールする。
 *  </p>
 * @author 那須智貴
 * @version 0.1 2014/05/13 新規作成
 */
public class BatchManager extends Controller {

	private static final String CST_SUCCESS = "0"; // 正常
	private static final String CST_ERROR = "1"; // 異常

	/**
	 * 手続部品起動コントローラ動作確認
	 * @return 動作中結果表示
	 */
	public static Result index() {
		return ok("SWF");
	}

	/**
	 * トランザクション生成処理
	 * <p>
	 * 
	 * </p>
	 * @param strApplyId ID連結文字列(顧客ID+契約ID+サービスID)
	 * @return 成功/失敗フラグ
	 * @author 那須智貴
	 * @version 0.1 2014/05/13 新規作成
	 */
	public static Result createTransactionService(final String strApplyId) {

		// インスタンス生成
		BusinessResult<String> result = new BusinessResult<String>();

		if (strApplyId.isEmpty()) {
			return ok(CST_ERROR);
		} else {
			// 申込IDをもとに、トランザクション登録の対象となるレコードを抽出
			// result = HOGEHOGE

			// 抽出したレコードの申込IDとサービスIDを条件に申込サービス情報より抽出。
			// result = HOGEHOGE

			// トランザクションTBLにINSERTする準備を行う。
			// importTransaction();

			// トランザクションTBLに登録。(ステイタスを「手続き状態」にした上で)
			// result = insertTransaction();

			// 申込情報を更新。（手続き中のステイタスにでも変える？）
			// result = updateApplyStatus();

			if (result.getResultCode() != ResultCode.Success) {
				return ok(CST_ERROR);
			}
		}

		return ok(CST_SUCCESS);

	}

	/**
	 * ワークフロー起動キュー生成処理（サービス用）
	 * <p>
	 * 
	 * </p>
	 * @param なし
	 * @return 成功/失敗フラグ
	 * @author 那須智貴
	 * @version 0.1 2014/05/13 新規作成
	 */
	public static Result createQueueService() {

		// インスタンス生成
		BusinessResult<List<TransServiceInfo>> result = new BusinessResult<List<TransServiceInfo>>();
		BusinessResult<List<ProgressQueue>> resultQueue = new BusinessResult<List<ProgressQueue>>();
		BusinessResult<String> resultUpd = new BusinessResult<String>();

		try {
			// トランザクションスタート
			Ebean.beginTransaction();
			// キューをスタックする対象となるトランザクションを取得。
			result = TransactionManagement.selectTransServiceSOStatus(DbDataValue.IFKey.SHOKI);
			// 戻り値確認
			if (result.getResultCode() != ResultCode.Success) {
				return ok(CST_ERROR);
			} else {
				// 件数分キューを作成する。
				resultQueue = QueueManagement.makeQueueService(result.getValue());

				// キュー情報確認
				if (resultQueue.getResultCode() == ResultCode.Success) {
					// キューに何もスタックしない場合はスルー
					if (resultQueue.getValue().size() == 0) {
						return ok(CST_SUCCESS);
					} else {
						resultUpd = QueueManagement.updateQueue(resultQueue.getValue());
						if (resultUpd.getResultCode() != ResultCode.Success) {
							return ok(CST_ERROR);
						}
					}
				} else {
					return ok(CST_ERROR);
				}
			}

			// トランザクションコミット
			Ebean.commitTransaction();

		} catch (Exception e) {
			// TODO 自動生成された catch ブロック
			e.printStackTrace();
		} finally {

			Ebean.endTransaction();
		}

		// 戻り値確認
		return ok(CST_SUCCESS);

	}

	/**
	 * 次ステータス更新処理
	 * <p>
	 * トランザクション情報、手続き進捗キューを更新する
	 * </p>
	 * @return 処理結果
	 * @author 甲斐
	 * @version 0.1 2014/06/24 新規作成
	 */
	public static Result nextStatus() {

		String strContent = request().body().asText();
		//Map<String, String[]> reqMap = request().body().asFormUrlEncoded();
		Map<String, String[]> reqMap = checkContent(strContent);

		// 引数として渡される項目と値
		String strIntervalTime = reqMap.get("interval")[0];
		String strQueueId = reqMap.get("queueid")[0];
		String strStatus = reqMap.get("status")[0];
		String strActivityId = reqMap.get("activityid")[0];

		// 変数定義(businessからの返却値)
		ResultCode success = ResultCode.Success;

		BusinessResult<ProgressQueue> result = new BusinessResult<ProgressQueue>();
		BusinessResult<String> resultTrans = new BusinessResult<String>();
		BusinessResult<String> resultQueue = new BusinessResult<String>();

		try {

			Ebean.beginTransaction();

			result = SelectProgressQueue.selectProgressQueue(strQueueId);

			if (result.getResultCode() == success) {

				ProgressQueue queue = result.getValue();

				// トランザクション情報更新
				resultTrans = TransactionManagement.updateTransaction(
						queue.transactionId, strStatus, strIntervalTime, null);

				// 戻り値結果を参照
				if (resultTrans.getResultCode() != success) {
					return internalServerError("BusinessError");
				}

				// 手続き進捗キュー更新
				resultQueue = QueueManagement.updateProgressQueue(queue, strActivityId);

				// 戻り値結果を参照
				if (resultQueue.getResultCode() != success) {
					return internalServerError("BusinessError");
				}

				Ebean.commitTransaction();
			}

		} catch (Exception e) {
			// TODO 自動生成された catch ブロック
			e.printStackTrace();
			return internalServerError("SystemError");
		} finally {
			Ebean.endTransaction();
		}

		return ok("Success");

	}

	/**
	 * 外部連携用アクティビティＩＤ更新処理
	 * <p>
	 * 外部データ連携でSO指示が送信された場合、アクティビティＩＤを更新する。
	 * </p>
	 * @return 処理結果
	 * @author 甲斐
	 * @version 0.1 2014/07/31 新規作成
	 */
	public static Result sendSOInput() {

		// 変数定義(businessからの返却値)
		ResultCode Success = ResultCode.Success;

		String soId = "";
		String strOtherKey = "soId";

		BusinessResult<List<SOInput>> result = new BusinessResult<List<SOInput>>();
		BusinessResult<String> resultFlow = new BusinessResult<String>();
		BusinessResult<String> resultUpd = new BusinessResult<String>();
		BusinessResult<ProgressQueue> resultQueue = new BusinessResult<ProgressQueue>();

		try {

			Ebean.beginTransaction();

			result = SendSOInput.selectSOInput(ShoriStatus.MISHORI);

			if (result.getResultCode() == Success) {

				for (SOInput soInput : result.getValue()) {

					soId = soInput.soId;

					// 手続き進捗キュー検索
					resultQueue = SelectProgressQueue.selectProgressForSOInput(strOtherKey, soId);
					if (resultQueue.getResultCode() != Success) {
						return internalServerError("BusinessError");
					}

					// 手続き進捗キュー更新
					resultFlow = QueueManagement.updateProgressQueue(resultQueue.getValue(), Activity.SOKEKKA);
					if (resultFlow.getResultCode() != Success) {
						return internalServerError("BusinessError");
					}

					// SO指示更新
					resultUpd = SendSOInput.updateSOInput(soInput, ShoriStatus.SHORIZUMI);
					if (resultUpd.getResultCode() != Success) {
						return internalServerError("BusinessError");
					}
				}

				Ebean.commitTransaction();
			}

		} catch (Exception e) {
			// TODO 自動生成された catch ブロック
			e.printStackTrace();
			return internalServerError("SystemError");
		} finally {
			Ebean.endTransaction();
		}

		return ok("Success");

	}

	/**
	 * ＳＯ指示登録処理
	 * <p>
	 * ＳＯ指示を登録する
	 * 関連ステータスを更新する
	 * （トランザクション情報、手続き進捗キュー）
	 * </p>
	 * @param 顧客ID
	 * @param 契約ID
	 * @param サービスID
	 * @return 処理結果
	 * @author 江川
	 * @version 0.1 2014/05/14 新規作成
	 */

	public static Result soInput() {

		String strContent = request().body().asText();
		//Map<String, String[]> reqMap = request().body().asFormUrlEncoded();
		Map<String, String[]> reqMap = checkContent(strContent);

		// 引数として渡される項目と値
		//String kokyakuId = reqMap.get("kokyakuid")[0];
		//String keiyakuId = reqMap.get("keiyakuid")[0];
		//String serviceId = reqMap.get("serviceid")[0];
		//String strTransactionId = reqMap.get("transactionid")[0];
		String strIntervalTime = reqMap.get("interval")[0];
		String strQueueId = reqMap.get("queueid")[0];
		String strStatus = reqMap.get("status")[0];
		String strActivityId = reqMap.get("activityid")[0];

		// 変数定義(businessからの返却値)
		ResultCode Success = ResultCode.Success;

		String strOtherKey = "soId";

		BusinessResult<String> resultSO = new BusinessResult<String>();
		BusinessResult<String> resultTrans = new BusinessResult<String>();
		BusinessResult<String> resultQueue = new BusinessResult<String>();
		//BusinessResult<List<ProgressQueue>> resultList = new BusinessResult<List<ProgressQueue>>();
		BusinessResult<ProgressQueue> result = new BusinessResult<ProgressQueue>();

		try {

			Ebean.beginTransaction();

			//resultList = SelectProgressQueue.selectActiveQueue(Activity.SOSHIJI);
			result = SelectProgressQueue.selectProgressQueue(strQueueId);
			ProgressQueue queue = result.getValue();

			//if (resultList.getResultCode() == success && resultList.getValue().size() > 0) {
			if (result.getResultCode() == Success && queue != null) {

				//for (ProgressQueue queue : resultList.getValue()) {

				// SO指示登録業務部品
				resultSO = InsertSOInput.insertSOInput(queue.kokyakuId, queue.keiyakuId, queue.serviceId,
						queue.transactionId);

				// 戻り値結果を参照
				if (resultSO.getResultCode() != Success) {
					return internalServerError("BusinessError");
				}

				// トランザクション情報更新
				resultTrans = TransactionManagement.updateTransaction(
						queue.transactionId, strStatus, strIntervalTime, null);

				// 戻り値結果を参照
				if (resultTrans.getResultCode() != Success) {
					return internalServerError("BusinessError");
				}

				// 手続き進捗キュー更新
				resultQueue = QueueManagement.updateProgressQueue(queue, strActivityId, strOtherKey,
						resultSO.getValue());
				//resultQueue = QueueManagement.updateQueueIFKey(strQueueId, DbDataValue.IFKey.SHOKI,
				//DbDataValue.IFKey.SO_SHIJI, strOtherKey, result.getValue());

				// 戻り値結果を参照
				if (resultQueue.getResultCode() != Success) {
					return internalServerError("BusinessError");
				}
				//}

				Ebean.commitTransaction();
			}

		} catch (Exception e) {
			// TODO 自動生成された catch ブロック
			e.printStackTrace();
			return internalServerError("SystemError");
		} finally {
			Ebean.endTransaction();
		}

		return ok("Success");

	}

	/**
	 * ＳＯ受信結果更新処理
	 * <p>
	 * ＳＯ受信結果により関連ステータスを更新する
	 * （ＳＯ結果、トランザクション情報、手続き進捗キュー）
	 * </p>
	 * @param SOID
	 * @return 処理結果
	 * @author 江川
	 * @version 0.1 2014/05/14 新規作成
	 */

	public static Result receiveSOOutput() {

		//String strContent = request().body().asText();
		//Map<String, String[]> reqMap = request().body().asFormUrlEncoded();
		//Map<String, String[]> reqMap = checkContent(strContent);

		// 引数として渡される項目と値
		//String soId = "soi0000001";
		String soId = "";
		//String strIntervalTime = reqMap.get("interval")[0];
		//String strQueueId = reqMap.get("queueid")[0];
		//String strStatus = reqMap.get("status")[0];
		//String strActivityId = reqMap.get("activityid")[0];

		// 変数定義(businessからの返却値)
		ResultCode success = ResultCode.Success;

		//String strOtherKey = "soId";

		BusinessResult<SOOutput> result = new BusinessResult<SOOutput>();
		//BusinessResult<String> resultTrans = new BusinessResult<String>();
		//BusinessResult<String> resultQueue = new BusinessResult<String>();
		BusinessResult<TransServiceInfo> resultTrans = new BusinessResult<TransServiceInfo>();
		//BusinessResult<String> resultUpdate = new BusinessResult<String>();
		BusinessResult<List<ProgressQueue>> resultList = new BusinessResult<List<ProgressQueue>>();
		BusinessResult<String> resultFlow = new BusinessResult<String>();

		try {

			//Ebean.beginTransaction();

			resultList = SelectProgressQueue.selectActiveQueue(Activity.SOKEKKA);

			if (resultList.getResultCode() == success && resultList.getValue().size() > 0) {

				for (ProgressQueue queue : resultList.getValue()) {

					soId = queue.otherKeyData;
					// SO結果受信業務部品
					result = ReceiveSOOutput.receiveSOOutput(soId);

					if (result.getResultCode() == success && result.getValue() != null) {

						// トランザクション情報抽出
						resultTrans = SelectTransactionInfo.selectTransactionInfo(queue.transactionId);
						/*resultTrans = TransactionManagement.updateTransaction(
								//result.getValue().serviceId,
								result.getValue().transactionId,
								strStatus,
								strIntervalTime,
								null);*/

						// 戻り値結果を参照
						if (resultTrans.getResultCode() != success) {
							return internalServerError("BusinessError");
						}

						// 手続き進捗キュー更新
						//resultQueue = QueueManagement.updateProgressQueue(queue, Activity.SWF, strOtherKey, soId);
						//resultQueue = QueueManagement.updateQueueIFKey(strQueueId, DbDataValue.IFKey.SO_SHIJI,
						//DbDataValue.IFKey.SO_JUSHIN, strOtherKey, soId);

						// 戻り値結果を参照
						/*if (resultQueue.getResultCode() != Success) {
							return internalServerError("BusinessError");
						}*/

						// サービスワークフロー起動
						resultFlow = StartServiceWorkFlow.startWorkFlow(queue, resultTrans.getValue());
						if (resultFlow.getResultCode() != ResultCode.Success) {
							return internalServerError("BusinessError");
						}

						/*} else {

							// トランザクション情報更新
							resultUpdate = TransactionManagement.updateTransaction(
									//resultSelect.getValue().serviceId,
									resultSelect.getValue().transactionId,
									null,
									null,
									null);

							// 戻り値結果を参照
							if (resultUpdate.getResultCode() != Success) {
								return internalServerError("Error");
							}*/
					}
				}
			}

			//Ebean.commitTransaction();

		} catch (Exception e) {
			// TODO 自動生成された catch ブロック
			e.printStackTrace();
			return internalServerError("SystemError");
		} finally {
			//Ebean.endTransaction();
		}

		return ok("Success");

	}

	/**
	 * ＳＯ結果反映処理
	 * <p>
	 * ＳＯ結果のステータスが「受信済み」の場合、
	 * ＳＯ結果情報によりトランザクション情報を更新する
	 * 関連ステータスを更新する
	 * （ＳＯ結果、トランザクション情報、手続き進捗キュー）
	 * </p>
	 * @param SOID
	 * @return 処理結果
	 * @author 江川
	 * @version 0.1 2014/05/14 新規作成
	 */

	public static Result reflectSOOutput() {

		String strContent = request().body().asText();
		//Map<String, String[]> reqMap = request().body().asFormUrlEncoded();
		Map<String, String[]> reqMap = checkContent(strContent);

		// 引数として渡される項目と値
		//String soId = "soi0000001";
		String soId = "";
		String strIntervalTime = reqMap.get("interval")[0];
		String strQueueId = reqMap.get("queueid")[0];
		String strStatus = reqMap.get("status")[0];
		String strActivityId = reqMap.get("activityid")[0];

		// 変数定義(businessからの返却値)
		ResultCode success = ResultCode.Success;

		BusinessResult<SOOutput> result = new BusinessResult<SOOutput>();
		BusinessResult<String> resultTrans = new BusinessResult<String>();
		BusinessResult<String> resultQueue = new BusinessResult<String>();
		BusinessResult<ProgressQueue> resultSelect = new BusinessResult<ProgressQueue>();

		try {

			Ebean.beginTransaction();

			resultSelect = SelectProgressQueue.selectProgressQueue(strQueueId);

			ProgressQueue queue = resultSelect.getValue();
			soId = queue.otherKeyData;

			// SO結果受信業務部品
			result = ReflectSOOutput.reflectSOOutput(soId);

			if (result.getResultCode() == success && result.getValue() != null) {

				// トランザクション情報更新
				resultTrans = TransactionManagement.updateTransaction(
						//result.getValue().serviceId,
						queue.transactionId,
						strStatus,
						strIntervalTime,
						result.getValue());

				// 戻り値結果を参照
				if (resultTrans.getResultCode() != success) {
					return internalServerError("BusinessError");
				}

				// 手続き進捗キュー更新
				//resultQueue = QueueManagement.updateQueueIFKey(strQueueId, DbDataValue.IFKey.SO_JUSHIN,
				//DbDataValue.IFKey.SO_KEKKA_HANEI, null, null);

				// 手続き進捗キュー更新
				resultQueue = QueueManagement.updateProgressQueue(queue, strActivityId, null, null);

				// 戻り値結果を参照
				if (resultQueue.getResultCode() != success) {
					return internalServerError("BusinessError");
				}

				/*} else {

					// トランザクション情報更新
					resultTrans = TransactionManagement.updateTransaction(
							resultSelect.getValue().serviceId,
							resultSelect.getValue().transactionId,
							null,
							strIntervalTime,
							null);

					// 戻り値結果を参照
					if (resultTrans.getResultCode() != Success) {
						return internalServerError("Error");
					}*/
			}

			Ebean.commitTransaction();

		} catch (Exception e) {
			// TODO 自動生成された catch ブロック
			e.printStackTrace();
			return internalServerError("SystemError");
		} finally {
			Ebean.endTransaction();
		}
		return ok("Success");

	}

	/**
	 * パーマネント反映（手続き部品）
	 * <p>
	 * 
	 * </p>
	 * @param なし
	 * @return 成功/失敗フラグ
	 * @author 那須智貴
	 * @version 0.1 2014/05/13 新規作成
	 */
	public static Result registParmanent() {

		//String strContent = request().body().asText();
		//Map<String, String[]> reqMap = request().body().asFormUrlEncoded();
		//Map<String, String[]> reqMap = checkContent(strContent);

		//String strServiceId = reqMap.get("serviceid")[0];
		//String strTransactionId = reqMap.get("transactionid")[0];
		//String strQueueId = reqMap.get("queueid")[0];
		//String strIntervalTime = reqMap.get("interval")[0];
		//String strStatus = reqMap.get("status")[0];
		//String strActivityId = reqMap.get("activityid")[0];

		// インスタンス生成
		BusinessResult<List<TransServiceInfo>> resultTrans = new BusinessResult<List<TransServiceInfo>>();
		//BusinessResult<TransServiceInfo> resultTrans = new BusinessResult<TransServiceInfo>();
		BusinessResult<String> resultUpd = new BusinessResult<String>();
		//BusinessResult<String> resultUpdTrans = new BusinessResult<String>();
		BusinessResult<String> resultDelTrans = new BusinessResult<String>();
		BusinessResult<String> resultDelQueue = new BusinessResult<String>();
		//BusinessResult<Integer> resultCnt = new BusinessResult<Integer>();
		BusinessResult<List<NewServiceInfo>> resultParmanent = new BusinessResult<List<NewServiceInfo>>();
		BusinessResult<List<String>> resultList = new BusinessResult<List<String>>();

		try {

			Ebean.beginTransaction();

			// パーマネント反映待ちのキューとトランザクション情報の件数が等しい契約IDのリストを取得
			resultList = QueueManagement.checkProgressQueue(Activity.PERMANENT);
			List<String> keiyakuList = resultList.getValue();

			for (String keiyakuId : keiyakuList) {

				// トランザクション情報検索業務部品コール
				//resultTrans = TransactionManagement.selectTransService(strServiceId, strTransactionId);
				resultTrans = TransactionManagement.selectTransService(keiyakuId);

				// 対象のトランザクションがなければ、スルー
				if (resultTrans.getResultCode() != ResultCode.Success) {
					return ok("BusinessError");
				}/* else {
					if (resultTrans.getValue().size() == 0) {
						return ok(CST_SUCCESS);
					} else if (resultTrans.getValue().size() > 1) {
						return ok(CST_ERROR);
					}
					
					}*/

				// パーマネント検索(現段階では、対象のパーマネントは探しに行かない)
				resultParmanent = PermanentManagement.selectPermanentService(keiyakuId);
				if (resultParmanent.getResultCode() != ResultCode.Success) {
					return internalServerError("BusinessError");
				}

				// パーマネント情報更新業務部品コール
				resultUpd = PermanentManagement.makePermanentService(resultTrans.getValue(),
						resultParmanent.getValue());
				if (resultUpd.getResultCode() != ResultCode.Success) {
					return internalServerError("BusinessError");
				}

				// パーマネント未反映のトランザクションサービス情報検索
				//resultCnt = TransactionManagement.selectTransService(strTransactionId, strServiceId, IFKey.PARMANENT_HANEI);

				// パーマネント未反映のトランザクションサービス情報が存在する場合
				/*if (resultCnt.getValue().intValue() > 0) {

					// トランザクション情報更新（ここでわざわざパーマネント反映に更新するのもおかしい気がするが・・・
					// やり方的には、もう一個S6みたいなステータスにキューを更新して、トランザクション・キューを削除するバッチが流れるんじゃないか？）
					resultUpdTrans = TransactionManagement.updateTransaction(
							strServiceId,
							strTransactionId,
							strStatus,
							strIntervalTime,
							null);

					if (resultUpdTrans.getResultCode() != ResultCode.Success) {
						return internalServerError(CST_ERROR);
					}
				} else {

					// トランザクションを削除
					//resultDelTrans = TransactionManagement.deleteTransactionInfo(strTransactionId, strServiceId, strStatus);
					resultDelTrans = TransactionManagement.deleteTransactionAll(strTransactionId);
					if (resultDelTrans.getResultCode() != ResultCode.Success) {
						return internalServerError(CST_ERROR);
					}
				}*/

				// トランザクションを削除
				resultDelTrans = TransactionManagement.deleteTransaction(resultTrans.getValue());
				if (resultDelTrans.getResultCode() != ResultCode.Success) {
					return internalServerError("BusinessError");
				}

				// キューを削除
				/*resultDelQueue = QueueManagement.deleteProgressQueue(strQueueId);
				if (resultDelQueue.getResultCode() != ResultCode.Success) {
					return internalServerError(CST_ERROR);
				}*/

				// キューを更新
				resultDelQueue = QueueManagement.updateProgressQueue(keiyakuId, Activity.PERMANENT, Activity.END);
				if (resultDelQueue.getResultCode() != ResultCode.Success) {
					return internalServerError("BusinessError");
				}
			}

			Ebean.commitTransaction();

		} catch (Exception e) {
			// TODO 自動生成された catch ブロック
			e.printStackTrace();
			return internalServerError("SystemError");
		} finally {
			Ebean.endTransaction();
		}

		// 戻り値確認
		return ok("Success");

	}

	private static Map<String, String[]> checkContent(String strContent) {

		Map<String, String[]> map = new HashMap<String, String[]>();
		String[] params = strContent.split("&");

		for (String param : params) {
			String[] key = param.split("=");
			if (key.length == 2) {
				String[] value = key[1].split(",");
				map.put(key[0], value);
			}
		}

		return map;
	}
}
