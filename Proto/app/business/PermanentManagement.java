package business;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import models.entities.KeiyakuInfo;
import models.entities.NewServiceInfo;
import models.entities.ServiceInfo;
import models.entities.ServiceMaster;
import models.entities.SvcKaisen;
import models.entities.SvcMail;
import models.entities.SvcOption;
import models.entities.SvcSecurity;
import models.entities.SvcSettingOption;
import models.entities.TransServiceInfo;

import common.BusinessResult;
import common.BusinessResult.ResultCode;
import common.business.IdKanri;

import constants.DbDataValue.IdCd;
import constants.DbDataValue.Status;

/**
 * パーマネント業務部品
 * <p>
 * パーマネントのＤＢ部品を呼び出し、<br>
 * 検索/更新を行う。
 * </p>
 * @author 那須智貴
 * @version 0.1　2014/05/13　新規作成
 */
public final class PermanentManagement {
	private PermanentManagement() {
	}

	/**
	 * パーマネント(サービス情報)抽出処理
	 * <p>
	 * パーマネント(サービス情報)抽出を行う
	 * </p>
	 * @param strKeiyakuId 契約ID
	 * @return サービス情報のリスト
	 * @author 那須智貴
	 * @throws Exception ○○例外
	 * @version 0.1　2014/05/13　新規作成
	 */
	public static BusinessResult<List<NewServiceInfo>> selectPermanentService(String strKeiyakuId)
			throws Exception {

		// インスタンス生成
		BusinessResult<List<NewServiceInfo>> result = new BusinessResult<List<NewServiceInfo>>();
		List<NewServiceInfo> listParmanent = new ArrayList<NewServiceInfo>();

		try {
			listParmanent = NewServiceInfo.find
					.fetch("svcKaisen")
					.fetch("svcMail")
					.fetch("svcSecurity")
					.fetch("svcOption")
					.fetch("svcSettingOption")
					.where().eq("keiyakuInfo.keiyakuId", strKeiyakuId).findList();

			result.setValue(listParmanent);
			result.setResultCode(ResultCode.Success);

		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}

		return result;
	}

	/**
	 * パーマネントサービス生成
	 * @param info トランザクションサービス情報
	 * @param lstParmanent パーマネントリスト
	 * @return 処理結果
	 * @throws Exception ○○例外
	 */
	public static BusinessResult<String> makePermanentService(TransServiceInfo info,
			List<ServiceInfo> lstParmanent)
			throws Exception {

		// インスタンス生成
		BusinessResult<String> result = new BusinessResult<String>();

		try {

			if (lstParmanent.size() == 0) {

				// 日付取得
				Date nowDate = Calendar.getInstance().getTime();
				SimpleDateFormat sdf1 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
				String strDate = sdf1.format(nowDate);
				Timestamp time = Timestamp.valueOf(strDate);

				ServiceInfo serviceInfo = new ServiceInfo();
				serviceInfo.serviceId = info.serviceId;
				serviceInfo.keiyakuInfo = new KeiyakuInfo();
				//serviceInfo.keiyakuInfo.keiyakuId = info.transKeiyakuInfo.keiyakuId;
				serviceInfo.keiyakuInfo.keiyakuId = info.keiyakuId;
				serviceInfo.status = info.status;
				serviceInfo.serviceMaster = new ServiceMaster();
				serviceInfo.serviceMaster.serviceCd = info.serviceMaster.serviceCd;
				serviceInfo.riyouKaishiNengetsubi = info.riyouKaishiNengetsubi;
				serviceInfo.riyouCourse = info.riyouCourse;
				serviceInfo.riyouPlan = info.riyouPlan;
				serviceInfo.ipv6Adapter = info.ipv6Adapter;
				serviceInfo.mailaddress1 = info.mailaddress;
				serviceInfo.mailaddress2 = null;
				serviceInfo.ttPhoneStatus = info.ttPhoneStatus;
				serviceInfo.sokuwari15 = info.sokuwari15;
				serviceInfo.kaitsuuKoujibi = info.kaitsuuKoujibi;
				serviceInfo.koujiYoteibi = info.koujiYoteibi;
				serviceInfo.dependService1 = new ServiceMaster();
				if (info.dependService1 != null) {
					serviceInfo.dependService1.serviceCd = info.dependService1.serviceCd;
				}
				serviceInfo.dependService2 = new ServiceMaster();
				if (info.dependService2 != null) {
					serviceInfo.dependService2.serviceCd = info.dependService2.serviceCd;
				}
				serviceInfo.deleteDate = "";
				serviceInfo.createUser = "BatchUser";
				serviceInfo.lastUpdateUser = "BatchUser";
				serviceInfo.createDT = time;
				serviceInfo.lastUpdateDT = time;

				serviceInfo.save();

			} else {
				// ここはパーマネントの更新なので、割愛します。
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
	 * パーマネント(サービス情報)作成処理
	 * <p>
	 * パーマネント(サービス情報)登録を行うModelを作成
	 * </p>
	 * @param lstTrans トランザクション情報リスト
	 * @param lstParmanent パーマネント情報リスト
	 * @return 結果文字列
	 * @throws Exception ○○例外
	 * @author 那須智貴
	 * @version 0.1　2014/05/13　新規作成
	 */
	public static BusinessResult<String> makePermanentService(List<TransServiceInfo> lstTrans,
			List<NewServiceInfo> lstParmanent) throws Exception {

		// インスタンス生成
		BusinessResult<String> result = new BusinessResult<String>();

		try {

			if (lstParmanent.size() == 0) {
				// パーマネントを作成するためのトランザクションがある場合
				if (lstTrans.size() > 0) {

					String tmpServiceId = lstTrans.get(0).serviceId;
					//サービス情報親テーブルの設定
					NewServiceInfo serviceInfo = makeServiceInfo(lstTrans.get(0));
					for (TransServiceInfo info : lstTrans) {

						if (!info.serviceId.equals(tmpServiceId)) {
							// サービスIDが異なる場合、別レコードを作成
							lstParmanent.add(serviceInfo);
							tmpServiceId = info.serviceId;
							serviceInfo = makeServiceInfo(info);
						}

						String strBunrui = info.serviceMaster.serviceBunrui.serviceBunruiCd;

						if ("0001".equals(strBunrui)) {
							// 回線
							serviceInfo.svcKaisen.add(makeServiceKaisen(info));

						} else if ("0005".equals(strBunrui)) {
							// 通知
							serviceInfo.svcMail.add(makeServiceMail(info));

						} else if ("0006".equals(strBunrui)) {
							// セキュリティ
							serviceInfo.svcSecurity.add(makeServiceSecurity(info));

						} else if ("0002".equals(strBunrui)) {
							// オプション
							serviceInfo.svcOption.add(makeServiceOption(info));

						} else if ("0007".equals(strBunrui)) {
							// 設置場所
							serviceInfo.svcSettingOption.add(makeServiceSettingOption(info));

						} else {
							continue;
						}

					}

					lstParmanent.add(serviceInfo);
					result = insertPermanentService(lstParmanent);

				} else {
					// ない場合は無視
				}
			} else {

				for (NewServiceInfo serviceInfo : lstParmanent) {
					String tmpServiceId = "";
					for (TransServiceInfo info : lstTrans) {

						if (!serviceInfo.serviceId.equals(info.serviceId)) {
							continue;
						}

						if (!tmpServiceId.equals(info.serviceId)) {
							serviceInfo = updateServiceInfo(serviceInfo, info);
							tmpServiceId = info.serviceId;
						}

						String strBunrui = info.serviceMaster.serviceBunrui.serviceBunruiCd;

						if ("0001".equals(strBunrui)) {
							// 回線
							serviceInfo.svcKaisen.add(makeServiceKaisen(info));

						} else if ("0005".equals(strBunrui)) {
							// 通知
							serviceInfo.svcMail.add(makeServiceMail(info));

						} else if ("0006".equals(strBunrui)) {
							// セキュリティ
							serviceInfo.svcSecurity.add(makeServiceSecurity(info));

						} else if ("0002".equals(strBunrui)) {
							// オプション
							serviceInfo.svcOption.add(makeServiceOption(info));

						} else if ("0007".equals(strBunrui)) {
							// 設置場所
							serviceInfo.svcSettingOption.add(makeServiceSettingOption(info));

						} else {
							continue;
						}
					}
				}

				result = updatePermanentService(lstParmanent);
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
	 * パーマネント(サービス情報)登録処理
	 * <p>
	 * パーマネント(サービス情報)登録を行う
	 * </p>
	 * @param tsInfo トランザクション情報
	 * @return サービス情報
	 * @author 甲斐
	 * @version 0.1　2014/07/24　新規作成
	 */
	private static NewServiceInfo makeServiceInfo(TransServiceInfo tsInfo) {

		// インスタンス生成
		NewServiceInfo serviceInfo = new NewServiceInfo();

		serviceInfo.serviceId = tsInfo.serviceId;
		serviceInfo.status = Status.TETSUDUKIKANRYOU;
		serviceInfo.keiyakuInfo = new KeiyakuInfo();
		serviceInfo.keiyakuInfo.keiyakuId = tsInfo.keiyakuId;
		serviceInfo.riyouKaishiNengetsubi = tsInfo.riyouKaishiNengetsubi;
		serviceInfo.deleteDate = "";
		serviceInfo.createUser = "BatchUser";
		serviceInfo.lastUpdateUser = "BatchUser";

		return serviceInfo;
	}

	/**
	 * パーマネント(サービス情報)登録処理
	 * <p>
	 * パーマネント(サービス情報)登録を行う
	 * </p>
	 * @param tsInfo トランザクション情報
	 * @return サービス情報
	 * @author 甲斐
	 * @version 0.1　2014/07/24　新規作成
	 */
	private static NewServiceInfo updateServiceInfo(NewServiceInfo serviceInfo, TransServiceInfo tsInfo) {

		// インスタンス生成
		serviceInfo.status = Status.TETSUDUKIKANRYOU;
		serviceInfo.riyouKaishiNengetsubi = tsInfo.riyouKaishiNengetsubi;
		serviceInfo.createUser = "BatchUser";
		serviceInfo.lastUpdateUser = "BatchUser";

		return serviceInfo;
	}

	/**
	 * パーマネント(サービス情報(回線))登録処理
	 * <p>
	 * パーマネント(サービス情報(回線))登録を行う
	 * </p>
	 * @param tsInfo トランザクション情報
	 * @return サービス情報(回線)
	 * @author 那須智貴
	 * @version 0.1　2014/07/24　新規作成
	 */
	private static SvcKaisen makeServiceKaisen(TransServiceInfo tsInfo) {

		// インスタンス生成
		SvcKaisen svcKaisen = new SvcKaisen();

		svcKaisen.svcKaisenId = IdKanri.getId(IdCd.KAISEN);
		svcKaisen.service = new NewServiceInfo();
		svcKaisen.service.serviceId = tsInfo.serviceId;
		svcKaisen.serviceMaster = tsInfo.serviceMaster;
		svcKaisen.riyouCourse = tsInfo.riyouCourse;
		svcKaisen.riyouPlan = tsInfo.riyouPlan;
		svcKaisen.ipv6Adapter = tsInfo.ipv6Adapter;
		svcKaisen.ttPhoneStatus = tsInfo.ttPhoneStatus;
		svcKaisen.sokuwari15 = tsInfo.sokuwari15;
		svcKaisen.kaitsuuKoujibi = tsInfo.kaitsuuKoujibi;
		svcKaisen.koujiYoteibi = tsInfo.koujiYoteibi;
		svcKaisen.deleteDate = "";
		svcKaisen.createUser = "BatchUser";
		svcKaisen.lastUpdateUser = "BatchUser";

		return svcKaisen;
	}

	/**
	 * パーマネント(サービス情報(メール))登録処理
	 * <p>
	 * パーマネント(サービス情報(メール))登録を行う
	 * </p>
	 * @param tsInfo トランザクション情報
	 * @return サービス情報(メール)
	 * @author 甲斐
	 * @version 0.1　2014/07/24　新規作成
	 */
	private static SvcMail makeServiceMail(TransServiceInfo tsInfo) {

		// インスタンス生成
		SvcMail svcMail = new SvcMail();

		svcMail.svcMailId = IdKanri.getId(IdCd.MAIL);
		svcMail.service = new NewServiceInfo();
		svcMail.service.serviceId = tsInfo.serviceId;
		svcMail.serviceMaster = tsInfo.serviceMaster;
		svcMail.mailaddress = tsInfo.mailaddress;
		svcMail.dependService1 = tsInfo.dependService1;
		svcMail.dependService2 = tsInfo.dependService2;
		svcMail.deleteDate = "";
		svcMail.createUser = "BatchUser";
		svcMail.lastUpdateUser = "BatchUser";

		return svcMail;
	}

	/**
	 * パーマネント(サービス情報(セキュリティ))登録処理
	 * <p>
	 * パーマネント(サービス情報(セキュリティ))登録を行う
	 * </p>
	 * @param tsInfo トランザクション情報
	 * @return サービス情報(セキュリティ)
	 * @author 甲斐
	 * @version 0.1　2014/07/24　新規作成
	 */
	private static SvcSecurity makeServiceSecurity(TransServiceInfo tsInfo) {

		// インスタンス生成
		SvcSecurity svcSecurity = new SvcSecurity();

		svcSecurity.svcSecurityId = IdKanri.getId(IdCd.SECURITY);
		svcSecurity.service = new NewServiceInfo();
		svcSecurity.service.serviceId = tsInfo.serviceId;
		svcSecurity.serviceMaster = tsInfo.serviceMaster;
		svcSecurity.deleteDate = "";
		svcSecurity.createUser = "BatchUser";
		svcSecurity.lastUpdateUser = "BatchUser";

		return svcSecurity;
	}

	/**
	 * パーマネント(サービス情報(オプション))登録処理
	 * <p>
	 * パーマネント(サービス情報(オプション))登録を行う
	 * </p>
	 * @param tsInfo トランザクション情報
	 * @return サービス情報(オプション)
	 * @author 甲斐
	 * @version 0.1　2014/07/24　新規作成
	 */
	private static SvcOption makeServiceOption(TransServiceInfo tsInfo) {

		// インスタンス生成
		SvcOption svcOption = new SvcOption();

		svcOption.svcOptionId = IdKanri.getId(IdCd.OPTION);
		svcOption.service = new NewServiceInfo();
		svcOption.service.serviceId = tsInfo.serviceId;
		svcOption.serviceMaster = tsInfo.serviceMaster;
		svcOption.deleteDate = "";
		svcOption.createUser = "BatchUser";
		svcOption.lastUpdateUser = "BatchUser";

		return svcOption;
	}

	private static SvcSettingOption makeServiceSettingOption(TransServiceInfo tsInfo) {

		// インスタンス生成
		SvcSettingOption svcSettingOption = new SvcSettingOption();

		svcSettingOption.svcSettingOptionId = IdKanri.getId(IdCd.OPTION);
		svcSettingOption.service = new NewServiceInfo();
		svcSettingOption.service.serviceId = tsInfo.serviceId;
		svcSettingOption.serviceMaster = tsInfo.serviceMaster;
		svcSettingOption.deleteDate = "";
		svcSettingOption.createUser = "BatchUser";
		svcSettingOption.lastUpdateUser = "BatchUser";

		return svcSettingOption;
	}

	/**
	 * パーマネント(サービス情報)登録処理
	 * <p>
	 * パーマネント(サービス情報)登録を行う
	 * </p>
	 * @param lstParmanent パーマネント情報リスト
	 * @return 登録結果
	 * @throws Exception ○○例外
	 * @author 那須智貴
	 * @version 0.1　2014/05/13　新規作成
	 */
	public static BusinessResult<String> insertPermanentService(List<NewServiceInfo> lstParmanent)
			throws Exception {

		// インスタンス生成
		BusinessResult<String> result = new BusinessResult<String>();

		try {
			// キューを作成するためのトランザクションがある場合
			if (lstParmanent.size() > 0) {
				for (NewServiceInfo info : lstParmanent) {
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
	 * パーマネント(サービス情報)登録処理
	 * <p>
	 * パーマネント(サービス情報)登録を行う
	 * </p>
	 * @param lstParmanent パーマネント情報リスト
	 * @return 登録結果
	 * @throws Exception ○○例外
	 * @author 那須智貴
	 * @version 0.1　2014/05/13　新規作成
	 */
	public static BusinessResult<String> updatePermanentService(List<NewServiceInfo> lstParmanent)
			throws Exception {

		// インスタンス生成
		BusinessResult<String> result = new BusinessResult<String>();

		try {
			// キューを作成するためのトランザクションがある場合
			if (lstParmanent.size() > 0) {
				for (NewServiceInfo info : lstParmanent) {
					info.update();
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

}
