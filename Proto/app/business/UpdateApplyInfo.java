package business;

import javax.persistence.OptimisticLockException;

import models.entities.ApplyInfo;
import models.entities.ApplyUketsukeKey;

import common.BusinessResult;
import common.BusinessResult.ResultCode;

import constants.DbDataValue.ApplyStatus;

/**
 * 申込情報更新業務部品.
 * <p>
 * 申込顧客情報のＤＢ部品を呼び出し、<br>
 * 更新を実行する業務部品
 * </p>
 * @author 甲斐正之
 * @version 0.1　2014/05/15　新規作成
 */
public final class UpdateApplyInfo {
	private UpdateApplyInfo() {
	}

	/**
	 * 申込顧客情報更新.
	 * <p>
	 * 申込顧客情報のＤＢ部品を呼び出し、<br>
	 * 更新を実行する
	 * </p>
	 * @param applyInfo 申込情報
	 * @return 処理結果
	 * @throws Exception ○○例外
	 * @author 甲斐正之
	 * @version 0.1　2014/05/15　新規作成
	 */
	public static BusinessResult<String> updateApplyInfo(ApplyInfo applyInfo) throws Exception {

		BusinessResult<String> result = new BusinessResult<String>();

		try {

			applyInfo = updateApplyStatus(applyInfo);

			applyInfo.update();

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
	 * 申込受付キー更新
	 * @param auKey 申込受付キー
	 * @return 処理結果
	 * @throws Exception ○○例外
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
	 * 申込情報更新.
	 * <p>
	 * 申込情報の更新対象項目に値を設定する
	 * </p>
	 * @param info 申込情報
	 * @return 申込情報
	 * @author 甲斐正之
	 * @version 0.1　2014/05/15　新規作成
	 */
	private static ApplyInfo updateApplyStatus(ApplyInfo info) {

		info.applyStatus = ApplyStatus.SHINSEIKANRYOU;
		info.lastUpdateUser = "BatchUser";
		return info;

	}

	private static ApplyUketsukeKey updateApplyUketsukeStatus(ApplyUketsukeKey auKey, String newActivity) {

		auKey.activityId = newActivity;
		auKey.lastUpdateUser = "BatchUser";
		return auKey;

	}

}