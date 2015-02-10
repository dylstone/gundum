package business;

import java.util.ArrayList;
import java.util.List;

import models.entities.SOInput;

import common.BusinessResult;
import common.BusinessResult.ResultCode;

/**
 * ＳＯ指示検索業務部品.
 * <p>
 * ＳＯ指示のＤＢ部品を呼び出し、<br>
 * 検索を実行する業務部品
 * </p>
 * @author 甲斐
 * @version 0.1　2014/07/31　新規作成
 */
public class SendSOInput {

	/**
	 * ＳＯ指示情報検索.
	 * <p>
	 * ＳＯ指示のＤＢ部品を呼び出し、<br>
	 * 検索を実行する
	 * </p>
	 * @param status ステータス
	 * @return 結果コード
	 * @author 甲斐
	 * @version 0.1　2014/07/31　新規作成
	 */
	public static BusinessResult<List<SOInput>> selectSOInput(String status) throws Exception {

		// 戻り値の生成
		BusinessResult<List<SOInput>> result = new BusinessResult<List<SOInput>>();
		List<SOInput> listSO = new ArrayList<SOInput>();

		try {

			// ＳＯ指示の検索
			listSO = SOInput.find.where().eq("status", status).findList();

			// 結果コードを設定する
			result.setResultCode(ResultCode.Success);
			result.setValue(listSO);

		} catch (Exception e) {
			e.printStackTrace();
			result.setResultCode(ResultCode.BusinessError);

		}

		return result;

	}

	/**
	 * ＳＯ指示情報更新.
	 * <p>
	 * ＳＯ指示のＤＢ部品を呼び出し、<br>
	 * 更新を実行する
	 * </p>
	 * @param soInput SO指示情報
	 * @param status ステータス
	 * @return 結果コード
	 * @author 甲斐
	 * @version 0.1　2014/07/31　新規作成
	 */
	public static BusinessResult<String> updateSOInput(SOInput soInput, String status) throws Exception {

		// 戻り値の生成
		BusinessResult<String> result = new BusinessResult<String>();

		try {

			// ＳＯ指示の更新

			soInput.status = status;
			soInput.lastUpdateUser = "BatchUser";

			soInput.update();

			// 結果コードを設定する
			result.setResultCode(ResultCode.Success);
			result.setValue("");

		} catch (Exception e) {
			e.printStackTrace();
			result.setResultCode(ResultCode.BusinessError);

		}

		return result;

	}

}