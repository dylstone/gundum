package controllers;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import models.entities.ApplyUketsukeKey;
import models.entities.NewApplyServiceInfo;
import models.entities.ProcedureServiceMaster;
import models.entities.ProgressQueue;
import models.entities.TransServiceInfo;
import play.mvc.Controller;
import play.mvc.Result;
import business.CreateProgressQueue;
import business.CreateTransactionInfo;
import business.SelectApplyInfo;
import business.SelectProcedureServiceMaster;
import business.SelectProgressQueue;
import business.SelectTransactionInfo;
import business.UpdateApplyInfo;
import business.UpdateTransactionInfo;

import com.avaje.ebean.Ebean;
import common.BusinessResult;
import common.BusinessResult.ResultCode;
import common.StartServiceWorkFlow;
import common.business.QueueManagement;

import constants.DbDataValue.Activity;

/**
 * バッチコントローラ
 *
 */
public class BatchController extends Controller {
	/**
	 * トランザクション生成
	 * @return トランザクション生成結果
	 */
	public static Result makeTransaction() {

		// postで渡されるパラメータの取得
		//String strContent = request().body().asText();
		//Map<String, String[]> form = request().body().asFormUrlEncoded();
		//Map<String, String[]> form = checkContent(strContent);
		//String applyId = form.get("key")[0];

		//BusinessResult<ApplyInfo> resultApply = new BusinessResult<ApplyInfo>();
		//BusinessResult<KokyakuInfo> resultKokyaku = new BusinessResult<KokyakuInfo>();
		BusinessResult<String> resultTrans = new BusinessResult<String>();
		BusinessResult<String> resultUpdApply = new BusinessResult<String>();
		//BusinessResult<List<ApplyInfo>> resultList = new BusinessResult<List<ApplyInfo>>();
		//BusinessResult<ProcedureTypeMaster> resultPTM = new BusinessResult<ProcedureTypeMaster>();
		//BusinessResult<SwfTeigiMaster> resultSTM = new BusinessResult<SwfTeigiMaster>();
		BusinessResult<List<ApplyUketsukeKey>> resultListKey = new BusinessResult<List<ApplyUketsukeKey>>();
		//BusinessResult<ApplyServiceInfo> resultASI = new BusinessResult<ApplyServiceInfo>();
		BusinessResult<NewApplyServiceInfo> resultASI = new BusinessResult<NewApplyServiceInfo>();
		BusinessResult<List<ProcedureServiceMaster>> resultListPSM = new BusinessResult<List<ProcedureServiceMaster>>();

		try {

			// 申込情報検索
			//resultApply = SelectApplyInfo.selectApplyInfo(applyId);
			//resultList = SelectApplyInfo.selectApplyInfo();
			resultListKey = QueueManagement.checkUketsukeKey(Activity.TRANSACTION);

			//if (resultApply.getResultCode() == ResultCode.Success) {
			if (resultListKey.getResultCode() == ResultCode.Success) {

				//for (ApplyInfo applyInfo : resultList.getValue()) {
				for (ApplyUketsukeKey auKey : resultListKey.getValue()) {

					//ApplyInfo applyInfo = resultApply.getValue();

					// パーマネント情報検索
					//resultKokyaku = SelectPermanentInfo.selectKokyakuInfo(applyInfo.kokyakuInfo.kokyakuId,
					//applyInfo.keiyakuInfo.keiyakuId);
					Ebean.beginTransaction();

					//KokyakuInfo kokyakuInfo = resultKokyaku.getValue();
					resultASI = SelectApplyInfo.selectNewApplyServiceInfo(auKey.applyId, auKey.serviceId);
					if (resultASI.getResultCode() != ResultCode.Success) {
						Ebean.rollbackTransaction();
						return internalServerError("BusinessError");
					}

					NewApplyServiceInfo asInfo = resultASI.getValue();

					//for (ApplyServiceInfo asInfo : resultApply.getValue().applyServiceInfo) {
					//for (ApplyServiceInfo asInfo : applyInfo.applyServiceInfo) {
					// 手続き種別マスタ取得
					/*resultPTM = SelectProcedureTypeMaster.selectProcedureTypeMaster(asInfo);
					if (resultPTM.getResultCode() != ResultCode.Success) {
					    Ebean.rollbackTransaction();
					    return internalServerError("BusinessError");
					}

					ProcedureTypeMaster ptMaster = resultPTM.getValue();*/

					// サービスワークフロー定義マスタ取得
					/*resultSTM = SelectSwfTeigiMaster.selectSwfTeigiMaster(ptMaster);
					if (resultSTM.getResultCode() != ResultCode.Success) {
					    Ebean.rollbackTransaction();
					    return internalServerError("BusinessError");
					}

					SwfTeigiMaster stMaster = resultSTM.getValue();*/

					// トランザクション情報作成
					//resultTrans = CreateTransactionInfo.createTransacitonInfo(applyInfo, kokyakuInfo);
					resultTrans = CreateTransactionInfo.createTransacitonInfo(asInfo, auKey);
					if (resultTrans.getResultCode() != ResultCode.Success) {
						Ebean.rollbackTransaction();
						return internalServerError("BusinessError");
					}

					//}

					// 手続き実行サービスマスタから契約に必要なサービスの取得
					resultListPSM = SelectProcedureServiceMaster.selectProcedureServiceMaster();

					for (ProcedureServiceMaster psMaster : resultListPSM.getValue()) {

						// トランザクション情報作成
						resultTrans = CreateTransactionInfo.createTransacitonInfo(psMaster, auKey);
						if (resultTrans.getResultCode() != ResultCode.Success) {
							Ebean.rollbackTransaction();
							return internalServerError("BusinessError");
						}
					}

					// 申込情報更新
					//resultUpdApply = UpdateApplyInfo.updateApplyInfo(applyInfo);
					resultUpdApply = UpdateApplyInfo.updateApplyUketsukeKey(auKey, Activity.END);

					if (resultUpdApply.getResultCode() == ResultCode.Success) {
						Ebean.commitTransaction();
					}
					else {
						Ebean.rollbackTransaction();
						return internalServerError("BusinessError");
					}
				}
			}

		} catch (Exception e) {
			e.printStackTrace();
			return internalServerError("SystemError");
		} finally {
			Ebean.endTransaction();
		}

		return ok("Success");

	}

	/**
	 * 進捗キー生成
	 * 
	 * @return 生成結果
	 */
	public static Result makeProgressQueue() {

		// postで渡されるパラメータの取得
		//String strContent = request().body().asText();
		//Map<String, String[]> form = request().body().asFormUrlEncoded();
		//Map<String, String[]> form = checkContent(strContent);
		//String transactionId = form.get("key")[0];

		//BusinessResult<TransKokyakuInfo> resultTrans = new BusinessResult<TransKokyakuInfo>();
		BusinessResult<String> resultQueue = new BusinessResult<String>();
		BusinessResult<String> resultUpdTrans = new BusinessResult<String>();
		BusinessResult<List<TransServiceInfo>> resultList = new BusinessResult<List<TransServiceInfo>>();

		try {

			// トランザクション情報検索
			//resultTrans = SelectTransactionInfo.selectTransactionInfo(transactionId);
			resultList = SelectTransactionInfo.selectTransactionInfo();

			//if (resultTrans.getResultCode() == ResultCode.Success) {
			if (resultList.getResultCode() == ResultCode.Success) {

				Ebean.beginTransaction();

				//List<TransServiceInfo> tsilist = resultTrans.getValue().transKeiyakuInfo.transServiceInfo;

				//for (TransServiceInfo tsInfo : tsilist) {
				for (TransServiceInfo tsInfo : resultList.getValue()) {

					// 手続き進捗キュー作成
					resultQueue = CreateProgressQueue.createProgressQueue(tsInfo);
					if (resultQueue.getResultCode() != ResultCode.Success) {
						Ebean.rollbackTransaction();
						return internalServerError("BusinessError");
					}

					// トランザクション情報更新
					resultUpdTrans = UpdateTransactionInfo.updateTransactionInfo(tsInfo);

					if (resultUpdTrans.getResultCode() != ResultCode.Success) {
						Ebean.rollbackTransaction();
						return internalServerError("BusinessError");
					}

				}

				Ebean.commitTransaction();
			}
		} catch (Exception e) {
			e.printStackTrace();
			return internalServerError("SystemError");
		} finally {
			Ebean.endTransaction();
		}

		return ok("Success");

	}

	/**
	 * ワークフロー起動処理
	 * @return 処理結果
	 */
	public static Result startWorkFlow() {

		// postで渡されるパラメータの取得
		String strContent = request().body().asText();
		//Map<String, String[]> form = request().body().asFormUrlEncoded();
		Map<String, String[]> form = checkContent(strContent);
		String queueId = form.get("key")[0];

		BusinessResult<ProgressQueue> resultQueue = new BusinessResult<ProgressQueue>();
		BusinessResult<TransServiceInfo> resultTrans = new BusinessResult<TransServiceInfo>();
		BusinessResult<String> resultFlow = new BusinessResult<String>();

		try {
			// 手続き進捗キュー抽出
			resultQueue = SelectProgressQueue.selectProgressQueue(queueId);
			ProgressQueue queue = resultQueue.getValue();

			// トランザクション情報抽出
			resultTrans = SelectTransactionInfo.selectTransactionInfo(queue.transactionId);
			TransServiceInfo tsInfo = resultTrans.getValue();

			// サービスワークフロー起動
			resultFlow = StartServiceWorkFlow.startWorkFlow(queue, tsInfo);
			if (resultFlow.getResultCode() != ResultCode.Success) {
				//return internalServerError(resultFlow.getValue());
				return internalServerError("BusinessError");
			}

		} catch (Exception e) {
			e.printStackTrace();
			return internalServerError("SystemError");
		}

		return ok("Success");

	}

	/*public static Result getShoriTaisho() {

		// postで渡されるパラメータの取得
		String strContent = request().body().asText();
		//Map<String, String[]> form = request().body().asFormUrlEncoded();
		Map<String, String[]> form = checkContent(strContent);
		String batchId = form.get("key")[0];

		BusinessResult<List<String>> resultList = new BusinessResult<List<String>>();
		BusinessResult<String> resultQueue = new BusinessResult<String>();

		try {

			if (batchId.equals("makeTransaction")) {
				resultList = SelectApplyInfo.selectApplyInfo();
			} else if (batchId.equals("makeProgressQueue")) {
				resultList = SelectTransactionInfo.selectTransactionInfo();
			} else if (batchId.equals("startWorkFlow")) {
				resultList = SelectProgressQueue.selectProgressQueue();
			} else {
				return internalServerError("BusinessError");
			}

			if (resultList.getResultCode() == ResultCode.Success) {

				Ebean.beginTransaction();

				for (String id : resultList.getValue()) {

					// 手続き進捗キュー作成
					resultQueue = CreateProgressQueue.createBatchQueue(batchId, id);
					if (resultQueue.getResultCode() != ResultCode.Success) {
						Ebean.rollbackTransaction();
						return internalServerError("BusinessError");
					}
				}

				Ebean.commitTransaction();
			}
		} catch (Exception e) {
			e.printStackTrace();
			return internalServerError("SystemError");
		}

		return ok("Success");

	}*/

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
