package controllers;

import java.util.HashMap;
import java.util.Map;

import models.entities.ApplyUketsukeKey;
import play.mvc.Controller;
import play.mvc.Result;
import business.CreateApplyRireki;
import business.MakeApplyInfo;
import business.SelectApplyUketsuke;
import business.UpdateApplyInfo;

import com.avaje.ebean.Ebean;
import common.ApplyInfoXml;
import common.BusinessResult;
import common.BusinessResult.ResultCode;

import constants.DbDataValue.Activity;

/**
 *  申込情報管理
 *  <p>
 *  申込情報を画面管理する
 *  </p>
 * @author 甲斐
 * @version 0.1 2014/07/10 新規作成
 */
public class ApplyRirekiManagement extends Controller {

	/**
	 * 申込受付キー承認画面初期表示処理
	 * <p>
	 * 申込受付キー承認画面の初期表示を行う
	 * </p>
	 * @return Formインスタンス
	 * @author 甲斐
	 * @version 0.1 2014/07/10 新規作成
	 * @throws  
	 */
	public static Result makeApplyRireki() {

		// postで渡されるパラメータの取得
		String strContent = request().body().asText();
		Map<String, String[]> form = checkContent(strContent);
		String queueId = form.get("key")[0];

		// 結果インスタンス生成
		BusinessResult<ApplyInfoXml> resultAIX = new BusinessResult<ApplyInfoXml>();
		BusinessResult<ApplyUketsukeKey> resultAUK = new BusinessResult<ApplyUketsukeKey>();
		BusinessResult<String> resultCrt = new BusinessResult<String>();
		BusinessResult<String> resultUpd = new BusinessResult<String>();

		try {

			Ebean.beginTransaction();

			// 申込受付情報取得
			resultAUK = SelectApplyUketsuke.selectApplyUketsukeKey(queueId);

			if (resultAUK.getResultCode() != ResultCode.Success) {
				return internalServerError("BusinessError");
			}

			// 申込XML取得
			resultAIX = MakeApplyInfo.makeApplyInfo(resultAUK.getValue().applyId);

			if (resultAIX.getResultCode() != ResultCode.Success) {
				return internalServerError("BusinessError");
			}

			// 申込履歴登録
			resultCrt = CreateApplyRireki.createApplyRireki(resultAIX.getValue(), resultAUK.getValue());

			if (resultCrt.getResultCode() != ResultCode.Success) {
				Ebean.rollbackTransaction();
				return internalServerError("BusinessError");
			}

			// 申込受付情報更新
			resultUpd = UpdateApplyInfo.updateApplyUketsukeKey(resultAUK.getValue(), Activity.SHONINMACHI);

			if (resultUpd.getResultCode() != ResultCode.Success) {
				Ebean.rollbackTransaction();
				return internalServerError("BusinessError");
			}

			Ebean.commitTransaction();

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			Ebean.endTransaction();
		}

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
