package business;

import java.util.List;

import models.entities.ApplyInfo;
import models.entities.ApplyServiceInfo;
import models.entities.NewApplyServiceInfo;

import common.BusinessResult;
import common.BusinessResult.ResultCode;

import constants.DbDataValue.ApplyStatus;

/**
 * 申込情報検索業務部品.
 * <p>
 * 申込情報のＤＢ部品を呼び出し、<br>
 * 検索結果を返却する業務部品
 * </p>
 * @author 甲斐正之
 * @version 0.1　2014/05/15　新規作成
 */
public final class SelectApplyInfo {
	private SelectApplyInfo() {
	}

	/**
	 * 申込情報検索.
	 * <p>
	 * 申込情報のＤＢ部品を呼び出し、<br>
	 * 検索結果を返却する
	 * </p>
	 * @param applyId 申込ID
	 * @return 申込情報
	 * @throws Exception ○○例外
	 * @author 甲斐正之
	 * @version 0.1　2014/05/15　新規作成
	 */
	public static BusinessResult<ApplyInfo> selectApplyInfo(String applyId)
			throws Exception {

		BusinessResult<ApplyInfo> result = new BusinessResult<ApplyInfo>();

		try {

			// 申込情報の検索を実行する
			List<ApplyInfo> listAp = ApplyInfo.find
					.fetch("applyKokyakuInfo")
					.fetch("applyKeiyakuInfo")
					.fetch("applyServiceInfo")
					.fetch("applyServiceInfo.serviceMaster")
					.fetch("applyServiceInfo.dependService1")
					.fetch("applyServiceInfo.dependService2")
					.where().eq("applyId", applyId).eq("applyStatus", ApplyStatus.SHINSEICHUU)
					.findList();

			if (listAp == null || listAp.size() == 0) {
				// 申込情報が取得できなかった場合
				result.setResultCode(ResultCode.BusinessError);
			} else {
				// 申込情報が取得できた場合
				result.setValue(listAp.get(0));
				result.setResultCode(ResultCode.Success);
			}
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}

		return result;

	}

	/**
	 * 申込サービス情報取得
	 * @param applyId 申込ID
	 * @param serviceId サービスID
	 * @return 申込サービス情報リスト
	 * @throws Exception ○○例外
	 */
	public static BusinessResult<ApplyServiceInfo> selectApplyServiceInfo(String applyId, String serviceId)
			throws Exception {

		BusinessResult<ApplyServiceInfo> result = new BusinessResult<ApplyServiceInfo>();

		try {

			// 申込情報の検索を実行する
			List<ApplyServiceInfo> listAp = ApplyServiceInfo.find
					.fetch("serviceMaster")
					.fetch("dependService1")
					.fetch("dependService2")
					.where().eq("applyInfo.applyId", applyId).eq("serviceId", serviceId)
					.findList();

			if (listAp == null || listAp.size() == 0) {
				// 申込情報が取得できなかった場合
				result.setResultCode(ResultCode.BusinessError);
			} else {
				// 申込情報が取得できた場合
				result.setValue(listAp.get(0));
				result.setResultCode(ResultCode.Success);
			}
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}

		return result;

	}

	/**
	 * 申込サービス情報取得（改訂版）
	 * @param applyId 申込ID
	 * @param serviceId サービスID
	 * @return 申込サービス情報リスト
	 * @throws Exception ○○例外
	 */
	public static BusinessResult<NewApplyServiceInfo> selectNewApplyServiceInfo(String applyId, String serviceId)
			throws Exception {

		BusinessResult<NewApplyServiceInfo> result = new BusinessResult<NewApplyServiceInfo>();

		try {

			// 申込情報の検索を実行する
			List<NewApplyServiceInfo> listAp = NewApplyServiceInfo.find
					.fetch("applySvcMail")
					.fetch("applySvcSecurity")
					.fetch("applySvcKaisen")
					.fetch("applySvcOption")
					.fetch("applySvcSettingOption")
					.where().eq("applyInfo.applyId", applyId).eq("serviceId", serviceId)
					.findList();

			if (listAp == null || listAp.size() == 0) {
				// 申込情報が取得できなかった場合
				result.setResultCode(ResultCode.BusinessError);
			} else {
				// 申込情報が取得できた場合
				result.setValue(listAp.get(0));
				result.setResultCode(ResultCode.Success);
			}
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}

		return result;

	}

	/**
	 * 申込顧客情報検索.
	 * <p>
	 * 申込情報のＤＢ部品を呼び出し、<br>
	 * 処理対象の申込ＩＤのリストを返却する
	 * </p>
	 * @return 申込ＩＤリスト
	 * @throws Exception ○○例外
	 * @author 甲斐正之
	 * @version 0.1　2014/05/15　新規作成
	 */
	//public static BusinessResult<List<String>> selectApplyInfo() throws Exception {
	public static BusinessResult<List<ApplyInfo>> selectApplyInfo() throws Exception {

		//BusinessResult<List<String>> result = new BusinessResult<List<String>>();
		//List<String> idList = new ArrayList<String>();
		BusinessResult<List<ApplyInfo>> result = new BusinessResult<List<ApplyInfo>>();

		try {

			// 申込情報の検索を実行する
			List<ApplyInfo> listAp = ApplyInfo.find
					.fetch("applyKokyakuInfo")
					.fetch("applyKeiyakuInfo")
					.fetch("applyServiceInfo")
					.fetch("applyServiceInfo.serviceMaster")
					.fetch("applyServiceInfo.dependService1")
					.fetch("applyServiceInfo.dependService2")
					.where().eq("applyStatus", ApplyStatus.SHINSEICHUU)
					.findList();

			if (listAp == null || listAp.size() == 0) {
				// 申込情報が取得できなかった場合
				result.setResultCode(ResultCode.BusinessError);
			} else {
				// 申込情報が取得できた場合
				/*for (ApplyInfo info : listAp) {
					idList.add(info.applyId);
				}*/
				result.setValue(listAp);
				result.setResultCode(ResultCode.Success);
			}
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}

		return result;
	}
}