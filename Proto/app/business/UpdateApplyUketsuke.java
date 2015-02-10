package business;

import javax.persistence.OptimisticLockException;

import models.entities.ApplyUketsukeKey;

import common.BusinessResult;
import common.BusinessResult.ResultCode;

import constants.DbDataValue.Activity;

/**
 * 申込受付キー更新業務部品.
 * <p>
 * 申込受付キーのＤＢ部品を呼び出し、<br>
 * 更新を実行する業務部品
 * </p>
 * @author 甲斐正之
 * @version 0.1　2014/05/15　新規作成
 */
public final class UpdateApplyUketsuke {
	private UpdateApplyUketsuke() {
	}

	/**
	 * 申込受付キー更新.
	 * <p>
	 * 申込受付キーのＤＢ部品を呼び出し、<br>
	 * 更新を実行する
	 * </p>
	 * @param auKey 申込受付キー
	 * @return 処理結果
	 * @throws Exception ○○例外
	 * @author 甲斐正之
	 * @version 0.1　2014/05/15　新規作成
	 */
	public static BusinessResult<String> updateApplyUketsukeKey(ApplyUketsukeKey auKey, String newActivity)
			throws Exception {

		BusinessResult<String> result = new BusinessResult<String>();

		try {

			auKey = updateApplyUketsukeStatus(auKey, newActivity);

			auKey.update();

			result.setResultCode(ResultCode.Success);

		} catch (OptimisticLockException e) {
			e.printStackTrace();
			result.setResultCode(ResultCode.BusinessError);
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}

		return result;

	}

	/**
	 * 申込受付キー更新.
	 * <p>
	 * 申込受付キーのＤＢ部品を呼び出し、<br>
	 * 更新を実行する
	 * </p>
	 * @param id 申込受付キーID
	 * @return 処理結果
	 * @throws Exception ○○例外
	 * @author 甲斐正之
	 * @version 0.1　2014/05/15　新規作成
	 */
	public static BusinessResult<String> updateApplyUketsukeKey(String id) throws Exception {

		BusinessResult<String> result = new BusinessResult<String>();

		try {

			ApplyUketsukeKey key = ApplyUketsukeKey.find.byId(id);

			key = updateApplyUketsukeStatus(key, Activity.TRANSACTION);

			key.update();

			result.setResultCode(ResultCode.Success);

		} catch (OptimisticLockException e) {
			e.printStackTrace();
			result.setResultCode(ResultCode.BusinessError);
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}

		return result;

	}

	/**
	 * 申込受付キー更新.
	 * <p>
	 * 申込受付キーの更新対象項目に値を設定する
	 * </p>
	 * @param auKey 申込受付キー
	 * @return 申込受付キー
	 * @author 甲斐正之
	 * @version 0.1　2014/05/15　新規作成
	 */
	private static ApplyUketsukeKey updateApplyUketsukeStatus(ApplyUketsukeKey auKey, String newActivity) {

		auKey.activityId = newActivity;
		auKey.lastUpdateUser = "BatchUser";
		return auKey;

	}

}