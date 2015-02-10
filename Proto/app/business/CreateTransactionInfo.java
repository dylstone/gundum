package business;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import models.entities.ApplySvcKaisen;
import models.entities.ApplySvcMail;
import models.entities.ApplySvcOption;
import models.entities.ApplySvcSecurity;
import models.entities.ApplySvcSettingOption;
import models.entities.ApplyUketsukeKey;
import models.entities.NewApplyServiceInfo;
import models.entities.NewServiceMaster;
import models.entities.ProcedureServiceMaster;
import models.entities.ProcedureTypeMaster;
import models.entities.SwfTeigiMaster;
import models.entities.TransServiceInfo;

import common.BusinessResult;
import common.BusinessResult.ResultCode;
import common.business.IdKanri;

import constants.DbDataValue.IdCd;
import constants.DbDataValue.Status;

/**
 * トランザクション情報登録業務部品.
 * <p>
 * トランザクション情報のＤＢ部品を呼び出し、<br>
 * 登録を実行する業務部品
 * </p>
 * @author 甲斐正之
 * @version 0.1　2014/05/15　新規作成
 */
public final class CreateTransactionInfo {

	private CreateTransactionInfo() {
	}

	/*/**
	 * トランザクション情報登録.
	 * <p>
	 * トランザクション情報のＤＢ部品を呼び出し、<br>
	 * 登録を実行する
	 * </p>
	 * @param applyInfo 申込情報
	 * @param kokyakuInfo 顧客情報
	 * @return 処理結果
	 * @throws Exception ○○例外
	 * @author 甲斐正之
	 * @version 0.1　2014/05/15　新規作成
	 */
	/*public static BusinessResult<String> createTransacitonInfo(ApplyInfo applyInfo,
	        KokyakuInfo kokyakuInfo) throws Exception {

	    BusinessResult<String> result = new BusinessResult<String>();

	    ArrayList<ApplyServiceInfo> makelist = new ArrayList<ApplyServiceInfo>();

	    for (ApplyServiceInfo asinfo : applyInfo.applyServiceInfo) {

	        String serviceCd = asinfo.serviceMaster.serviceCd;
	        boolean makeFlg = true;

	        if (kokyakuInfo != null)
	        {
	            for (ServiceInfo sinfo : kokyakuInfo.keiyakuInfo.get(0).serviceInfo) {
	                if (serviceCd.equals(sinfo.serviceMaster.serviceCd))
	                {
	                    makeFlg = false;
	                    break;
	                }

	            }
	        }

	        if (makeFlg) {
	            makelist.add(asinfo);
	        }
	    }

	    try {

	        if (makelist.size() > 0) {

	            //TransKokyakuInfo transKokyakuInfo = setTransKokyakuInfo(applyInfo.applyKokyakuInfo);

	            //transKokyakuInfo.transKeiyakuInfo = setTransKeiyakuInfo(applyInfo.applyKeiyakuInfo,
	            //kokyakuInfo.kokyakuId, transKokyakuInfo);

	            for (ApplyServiceInfo asInfo : makelist)
	            {
	                TransServiceInfo tsInfo = setTransServiceInfo(asInfo,
	                        applyInfo.applyKokyakuInfo.kokyakuInfo.kokyakuId,
	                        applyInfo.applyKeiyakuInfo.keiyakuInfo.keiyakuId);

	                tsInfo.save();

	                //transKokyakuInfo.transKeiyakuInfo.transServiceInfo.add(tsInfo);
	            }

	            //transKokyakuInfo.save();

	            result.setResultCode(ResultCode.Success);

	        } else {
	            result.setResultCode(ResultCode.BusinessError);
	        }

	    } catch (Exception e) {
	        e.printStackTrace();
	        throw e;
	    }

	    return result;

	}*/

	/*/**
	 * トランザクション情報登録.
	 * <p>
	 * トランザクション情報のＤＢ部品を呼び出し、<br>
	 * 登録を実行する
	 * </p>
	 * @param asInfo 申込サービス情報
	 * @param auKey 申込受付キー
	 * @param stMaster システムワークフローマスタ
	 * @return 処理結果
	 * @throws Exception ○○例外
	 * @author 甲斐正之
	 * @version 0.1　2014/05/15　新規作成
	 */
	/*public static BusinessResult<String> createTransacitonInfo(ApplyServiceInfo asInfo, ApplyUketsukeKey auKey,
	        SwfTeigiMaster stMaster) throws Exception {

	    BusinessResult<String> result = new BusinessResult<String>();

	    try {

	        TransServiceInfo tsInfo = setTransServiceInfo(asInfo, auKey.kokyakuId, auKey.keiyakuId, stMaster);

	        tsInfo.save();

	        result.setResultCode(ResultCode.Success);

	    } catch (Exception e) {
	        e.printStackTrace();
	        throw e;
	    }

	    return result;

	}*/

	/**
	 * トランザクション情報登録(改訂版).
	 * <p>
	 * トランザクション情報のＤＢ部品を呼び出し、<br>
	 * 登録を実行する
	 * </p>
	 * @param asInfo 申込サービス情報
	 * @param auKey 申込受付キー
	 * @return 処理結果
	 * @throws Exception ○○例外
	 * @author 甲斐正之
	 * @version 0.1　2014/07/24　新規作成
	 */
	public static BusinessResult<String> createTransacitonInfo(NewApplyServiceInfo asInfo, ApplyUketsukeKey auKey)
			throws Exception {

		BusinessResult<String> result = new BusinessResult<String>();
		BusinessResult<ProcedureTypeMaster> resultPTM = new BusinessResult<ProcedureTypeMaster>();
		BusinessResult<SwfTeigiMaster> resultSTM = new BusinessResult<SwfTeigiMaster>();

		try {

			// 回線サービスの登録
			if (asInfo.applySvcKaisen != null && asInfo.applySvcKaisen.size() > 0) {
				for (ApplySvcKaisen ask : asInfo.applySvcKaisen) {
					resultPTM = SelectProcedureTypeMaster.selectProcedureTypeMaster(ask.serviceMaster.serviceCd);
					if (resultPTM.getResultCode() != ResultCode.Success) {
						result.setResultCode(ResultCode.BusinessError);
						return result;
					}

					resultSTM = SelectSwfTeigiMaster.selectSwfTeigiMaster(resultPTM.getValue());
					if (resultSTM.getResultCode() != ResultCode.Success) {
						result.setResultCode(ResultCode.BusinessError);
						return result;
					}

					TransServiceInfo tsInfo = setTransServiceInfoKaisen(asInfo, auKey.kokyakuId, auKey.keiyakuId,
							resultSTM.getValue(), ask);

					tsInfo.save();
				}
			}

			// メールサービスの登録
			if (asInfo.applySvcMail != null && asInfo.applySvcMail.size() > 0) {
				for (ApplySvcMail asm : asInfo.applySvcMail) {
					resultPTM = SelectProcedureTypeMaster.selectProcedureTypeMaster(asm.serviceMaster.serviceCd);
					if (resultPTM.getResultCode() != ResultCode.Success) {
						result.setResultCode(ResultCode.BusinessError);
						return result;
					}

					resultSTM = SelectSwfTeigiMaster.selectSwfTeigiMaster(resultPTM.getValue());
					if (resultSTM.getResultCode() != ResultCode.Success) {
						result.setResultCode(ResultCode.BusinessError);
						return result;
					}

					TransServiceInfo tsInfo = setTransServiceInfoMail(asInfo, auKey.kokyakuId, auKey.keiyakuId,
							resultSTM.getValue(), asm);

					tsInfo.save();
				}
			}

			// セキュリティ関連サービスの登録
			if (asInfo.applySvcSecurity != null && asInfo.applySvcSecurity.size() > 0) {
				for (ApplySvcSecurity ass : asInfo.applySvcSecurity) {
					resultPTM = SelectProcedureTypeMaster.selectProcedureTypeMaster(ass.serviceMaster.serviceCd);
					if (resultPTM.getResultCode() != ResultCode.Success) {
						result.setResultCode(ResultCode.BusinessError);
						return result;
					}

					resultSTM = SelectSwfTeigiMaster.selectSwfTeigiMaster(resultPTM.getValue());
					if (resultSTM.getResultCode() != ResultCode.Success) {
						result.setResultCode(ResultCode.BusinessError);
						return result;
					}

					TransServiceInfo tsInfo = setTransServiceInfoSecurity(asInfo, auKey.kokyakuId, auKey.keiyakuId,
							resultSTM.getValue(), ass);

					tsInfo.save();
				}
			}

			// オプションサービスの登録
			if (asInfo.applySvcOption != null && asInfo.applySvcOption.size() > 0) {
				for (ApplySvcOption aso : asInfo.applySvcOption) {
					resultPTM = SelectProcedureTypeMaster.selectProcedureTypeMaster(aso.serviceMaster.serviceCd);
					if (resultPTM.getResultCode() != ResultCode.Success) {
						result.setResultCode(ResultCode.BusinessError);
						return result;
					}

					resultSTM = SelectSwfTeigiMaster.selectSwfTeigiMaster(resultPTM.getValue());
					if (resultSTM.getResultCode() != ResultCode.Success) {
						result.setResultCode(ResultCode.BusinessError);
						return result;
					}

					TransServiceInfo tsInfo = setTransServiceInfoOption(asInfo, auKey.kokyakuId, auKey.keiyakuId,
							resultSTM.getValue(), aso);

					tsInfo.save();
				}
			}

			// 設置場所サービスの登録
			if (asInfo.applySvcSettingOption != null && asInfo.applySvcSettingOption.size() > 0) {
				for (ApplySvcSettingOption aso : asInfo.applySvcSettingOption) {
					resultPTM = SelectProcedureTypeMaster.selectProcedureTypeMaster(aso.serviceMaster.serviceCd);
					if (resultPTM.getResultCode() != ResultCode.Success) {
						result.setResultCode(ResultCode.BusinessError);
						return result;
					}

					resultSTM = SelectSwfTeigiMaster.selectSwfTeigiMaster(resultPTM.getValue());
					if (resultSTM.getResultCode() != ResultCode.Success) {
						result.setResultCode(ResultCode.BusinessError);
						return result;
					}

					TransServiceInfo tsInfo = setTransServiceInfoSettingOption(asInfo, auKey.kokyakuId,
							auKey.keiyakuId,
							resultSTM.getValue(), aso);

					tsInfo.save();
				}
			}

			result.setResultCode(ResultCode.Success);

		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}

		return result;

	}

	/**
	 * トランザクション情報登録.
	 * <p>
	 * トランザクション情報のＤＢ部品を呼び出し、<br>
	 * 登録を実行する
	 * </p>
	 * @praam applyInfo 申込情報
	 * @param kokyakuInfo 顧客情報
	 * @return 処理結果
	 * @author 甲斐正之
	 * @version 0.1　2014/05/15　新規作成
	 */
	public static BusinessResult<String> createTransacitonInfo(ProcedureServiceMaster psMaster, ApplyUketsukeKey auKey)
			throws Exception {

		BusinessResult<String> result = new BusinessResult<String>();
		BusinessResult<SwfTeigiMaster> resultSTM = new BusinessResult<SwfTeigiMaster>();

		try {

			// サービスワークフロー定義マスタ取得
			resultSTM = SelectSwfTeigiMaster.selectSwfTeigiMaster(psMaster);
			if (resultSTM.getResultCode() != ResultCode.Success) {
				result.setResultCode(ResultCode.BusinessError);
				return result;
			}

			TransServiceInfo tsInfo = setTransServiceInfo(psMaster, auKey.kokyakuId, auKey.keiyakuId,
					resultSTM.getValue());

			tsInfo.save();

			result.setResultCode(ResultCode.Success);

		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}

		return result;

	}

	/*/**
	 * トランザクション情報(顧客情報)設定.
	 * <p>
	 * トランザクション情報(顧客情報)に値を設定する
	 * </p>
	 * @param applyInfo 申込情報
	 * @return トランザクション情報(顧客情報)クラス
	 * @author 甲斐正之
	 * @version 0.1　2014/05/15　新規作成
	 */
	/*private static TransKokyakuInfo setTransKokyakuInfo(ApplyKokyakuInfo applyInfo) {

		TransKokyakuInfo info = new TransKokyakuInfo();
		info.transKokyakuId = IdKanri.getId(IdCd.TRANSACTION);
		info.transactionId = IdKanri.getId(IdCd.TRANSACTION);
		info.kokyakuId = applyInfo.kokyakuInfo.kokyakuId;
		info.kokyakuUserId = applyInfo.kokyakuUserId;
		info.kokyakuUserPwd = applyInfo.kokyakuUserPwd;
		info.status = Status.TETSUDUKICHUU;
		info.lastName = applyInfo.lastName;
		info.firstName = applyInfo.firstName;
		info.lastNameFurigana = applyInfo.lastNameFurigana;
		info.firstNameFurigana = applyInfo.firstNameFurigana;
		info.department = applyInfo.department;
		info.corporativeUser = applyInfo.corporativeUser;
		info.seibetsuCd = applyInfo.seibetsuCd;
		info.birthNengetsubi = applyInfo.birthNengetsubi;
		info.jobCd = applyInfo.jobCd;
		info.mikomiKokyakuFlg = applyInfo.mikomiKokyakuFlg;
		info.renrakusakiMailaddress = applyInfo.renrakusakiMailaddress;
		info.telType = applyInfo.telType;
		info.telNo1 = applyInfo.telNo1;
		info.telNo2 = applyInfo.telNo2;
		info.faxNo = applyInfo.faxNo;
		info.zipNo = applyInfo.zipNo;
		info.todouhuken = applyInfo.todouhuken;
		info.si_ku_gun = applyInfo.si_ku_gun;
		info.tyousonOaza = applyInfo.tyousonOaza;
		info.azaBanchiGou = applyInfo.azaBanchiGou;
		info.apartment = applyInfo.apartment;
		info.apartmentRoomNo = applyInfo.apartmentRoomNo;
		info.kidukeSama = applyInfo.kidukeSama;
		info.kokyakuUserPwdLastUpdated = applyInfo.kokyakuUserPwdLastUpdated;
		info.deleteDate = null;
		info.createUser = "BatchUser";
		info.lastUpdateUser = "BatchUser";

		return info;

	}*/

	/*/**
	 * トランザクション情報(契約情報)設定.
	 * <p>
	 * トランザクション情報(契約情報)に値を設定する
	 * </p>
	 * @param applyInfo 申込情報
	 * @param kokyakuId 顧客ＩＤ
	 * @param transactionId トランザクションＩＤ
	 * @return トランザクション情報(契約情報)クラス
	 * @author 甲斐正之
	 * @version 0.1　2014/05/15　新規作成
	 */
	/*private static TransKeiyakuInfo setTransKeiyakuInfo(ApplyKeiyakuInfo applyInfo, String kokyakuId,
			TransKokyakuInfo tranKokyakuInfo) {

		TransKeiyakuInfo info = new TransKeiyakuInfo();
		info.transKeiyakuId = IdKanri.getId(IdCd.TRANSACTION);
		info.transactionId = tranKokyakuInfo.transactionId;
		info.keiyakuId = applyInfo.keiyakuInfo.keiyakuId;
		info.transKokyakuInfo = tranKokyakuInfo;
		info.keiyakuBrandCd = applyInfo.keiyakuBrandCd;
		info.status = Status.TETSUDUKICHUU;
		info.hanbaiKeitai = applyInfo.hanbaiKeitai;
		info.moushikomiNengetsubi = applyInfo.moushikomiNengetsubi;
		info.keiyakuKoushinNengetsu = applyInfo.keiyakuKoushinNengetsu;
		info.riyouKaishiNengetsubi = applyInfo.riyouKaishiNengetsubi;
		info.shiharaiHouhou = applyInfo.shiharaiHouhou;
		info.deleteDate = null;
		info.createUser = "BatchUser";
		info.lastUpdateUser = "BatchUser";

		return info;

	}*/

	/*/**
	 * トランザクション情報(サービス情報)設定.
	 * <p>
	 * トランザクション情報(サービス情報)に値を設定する
	 * </p>
	 * @param applyInfo 申込情報
	 * @param keiyakuId 契約ＩＤ
	 * @param transactionId トランザクションＩＤ
	 * @return 申込サービス情報クラス
	 * @author 甲斐正之
	 * @version 0.1　2014/04/11　新規作成
	 */
	/*private static TransServiceInfo setTransServiceInfo(ApplyServiceInfo applyInfo, String kokyakuId, String keiyakuId) {

	    Date nowDate = Calendar.getInstance().getTime();
	    SimpleDateFormat sdf1 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	    String strDate = sdf1.format(nowDate);
	    Timestamp time = Timestamp.valueOf(strDate);
	    TransServiceInfo info = new TransServiceInfo();
	    //info.transServiceId = IdKanri.getId(IdCd.TRANSACTION);
	    //info.transactionId = transKeiyakuInfo.transactionId;
	    info.transactionId = IdKanri.getId(IdCd.TRANSACTION);
	    info.kokyakuId = kokyakuId;
	    info.keiyakuId = keiyakuId;
	    info.serviceId = applyInfo.serviceId;
	    //info.transKeiyakuInfo = transKeiyakuInfo;
	    info.status = Status.TETSUDUKICHUU;
	    info.serviceMaster = applyInfo.serviceMaster;
	    info.riyouKaishiNengetsubi = applyInfo.riyouKaishiNengetsubi;
	    info.riyouCourse = applyInfo.riyouCourse;
	    info.riyouPlan = applyInfo.riyouPlan;
	    info.ipv6Adapter = applyInfo.ipv6Adapter;
	    info.mailaddress1 = applyInfo.mailaddress1;
	    info.mailaddress2 = applyInfo.mailaddress2;
	    info.ttPhoneStatus = applyInfo.ttPhoneStatus;
	    info.sokuwari15 = applyInfo.sokuwari15;
	    info.kaitsuuKoujibi = applyInfo.kaitsuuKoujibi;
	    info.koujiYoteibi = applyInfo.koujiYoteibi;
	    info.dependService1 = applyInfo.dependService1;
	    info.dependService2 = applyInfo.dependService2;
	    info.tourokuFlg = "0";
	    info.soStatus = null;
	    info.intervalTime = "0";
	    info.lastRunDate = time;
	    info.deleteDate = null;
	    info.createUser = "BatchUser";
	    info.lastUpdateUser = "BatchUser";

	    return info;

	}*/

	/*/**
	 * トランザクション情報(サービス情報)設定.
	 * <p>
	 * トランザクション情報(サービス情報)に値を設定する
	 * </p>
	 * @param applyInfo 申込情報
	 * @param keiyakuId 契約ＩＤ
	 * @param transactionId トランザクションＩＤ
	 * @return 申込サービス情報クラス
	 * @author 甲斐正之
	 * @version 0.1　2014/04/11　新規作成
	 */
	/*private static TransServiceInfo setTransServiceInfo(ApplyServiceInfo applyInfo,
	        String kokyakuId, String keiyakuId, SwfTeigiMaster stMaster) {

	    Date nowDate = Calendar.getInstance().getTime();
	    SimpleDateFormat sdf1 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	    String strDate = sdf1.format(nowDate);
	    Timestamp time = Timestamp.valueOf(strDate);
	    TransServiceInfo info = new TransServiceInfo();
	    info.transactionId = IdKanri.getId(IdCd.TRANSACTION);
	    info.kokyakuId = kokyakuId;
	    info.keiyakuId = keiyakuId;
	    info.serviceId = applyInfo.serviceId;
	    info.status = Status.TETSUDUKICHUU;
	    info.serviceMaster = applyInfo.serviceMaster;
	    info.riyouKaishiNengetsubi = applyInfo.riyouKaishiNengetsubi;
	    info.riyouCourse = applyInfo.riyouCourse;
	    info.riyouPlan = applyInfo.riyouPlan;
	    info.ipv6Adapter = applyInfo.ipv6Adapter;
	    info.mailaddress1 = applyInfo.mailaddress1;
	    info.mailaddress2 = applyInfo.mailaddress2;
	    info.ttPhoneStatus = applyInfo.ttPhoneStatus;
	    info.sokuwari15 = applyInfo.sokuwari15;
	    info.kaitsuuKoujibi = applyInfo.kaitsuuKoujibi;
	    info.koujiYoteibi = applyInfo.koujiYoteibi;
	    info.dependService1 = applyInfo.dependService1;
	    info.dependService2 = applyInfo.dependService2;
	    info.businessDetail = stMaster.primaryKey.businessDetail;
	    info.applyDetail = stMaster.primaryKey.applyDetail;
	    info.procedureType = stMaster.primaryKey.procedureType;
	    info.tourokuFlg = "0";
	    info.soStatus = stMaster.startStatus;
	    info.intervalTime = "0";
	    info.lastRunDate = time;
	    info.swfFileInfo = stMaster.scriptFileInfo;
	    info.deleteDate = null;
	    info.createUser = "BatchUser";
	    info.lastUpdateUser = "BatchUser";

	    return info;

	}*/

	/**
	 * トランザクション情報(サービス情報(回線))設定.
	 * <p>
	 * トランザクション情報(サービス情報(回線))に値を設定する
	 * </p>
	 * @param applyInfo 申込情報
	 * @param kokyakuId 顧客ＩＤ
	 * @param keiyakuId 契約ＩＤ
	 * @param stMaster SWF定義マスタ
	 * @param ask 申込サービス情報(回線)
	 * @return トランザクション情報
	 * @author 甲斐正之
	 * @version 0.1　2014/07/24　新規作成
	 */
	private static TransServiceInfo setTransServiceInfoKaisen(NewApplyServiceInfo applyInfo,
			String kokyakuId, String keiyakuId, SwfTeigiMaster stMaster, ApplySvcKaisen ask) {

		Date nowDate = Calendar.getInstance().getTime();
		SimpleDateFormat sdf1 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String strDate = sdf1.format(nowDate);
		Timestamp time = Timestamp.valueOf(strDate);
		TransServiceInfo info = new TransServiceInfo();
		info.transactionId = IdKanri.getId(IdCd.TRANSACTION);
		info.kokyakuId = kokyakuId;
		info.keiyakuId = keiyakuId;
		info.serviceId = applyInfo.serviceId;
		info.status = Status.TETSUDUKICHUU;
		info.serviceMaster = ask.serviceMaster;
		info.riyouKaishiNengetsubi = applyInfo.riyouKaishiNengetsubi;
		info.riyouCourse = ask.riyouCourse;
		info.riyouPlan = ask.riyouPlan;
		info.ipv6Adapter = ask.ipv6Adapter;
		info.mailaddress = null;
		info.ttPhoneStatus = ask.ttPhoneStatus;
		info.sokuwari15 = ask.sokuwari15;
		info.kaitsuuKoujibi = ask.kaitsuuKoujibi;
		info.koujiYoteibi = ask.koujiYoteibi;
		info.dependService1 = null;
		info.dependService2 = null;
		info.businessDetail = stMaster.primaryKey.businessDetail;
		info.applyDetail = stMaster.primaryKey.applyDetail;
		info.procedureType = stMaster.primaryKey.procedureType;
		info.tourokuFlg = "0";
		info.soStatus = stMaster.startStatus;
		info.intervalTime = "0";
		info.lastRunDate = time;
		info.swfFileInfo = stMaster.scriptFileInfo;
		info.deleteDate = null;
		info.createUser = "BatchUser";
		info.lastUpdateUser = "BatchUser";

		return info;

	}

	/**
	 * トランザクション情報(サービス情報(メール))設定.
	 * <p>
	 * トランザクション情報(サービス情報(メール))に値を設定する
	 * </p>
	 * @param applyInfo 申込情報
	 * @param kokyakuId 顧客ＩＤ
	 * @param keiyakuId 契約ＩＤ
	 * @param stMaster SWF定義マスタ
	 * @param asm 申込サービス情報(メール)
	 * @return トランザクション情報
	 * @author 甲斐正之
	 * @version 0.1　2014/07/24　新規作成
	 */
	private static TransServiceInfo setTransServiceInfoMail(NewApplyServiceInfo applyInfo,
			String kokyakuId, String keiyakuId, SwfTeigiMaster stMaster, ApplySvcMail asm) {

		Date nowDate = Calendar.getInstance().getTime();
		SimpleDateFormat sdf1 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String strDate = sdf1.format(nowDate);
		Timestamp time = Timestamp.valueOf(strDate);
		TransServiceInfo info = new TransServiceInfo();
		info.transactionId = IdKanri.getId(IdCd.TRANSACTION);
		info.kokyakuId = kokyakuId;
		info.keiyakuId = keiyakuId;
		info.serviceId = applyInfo.serviceId;
		info.status = Status.TETSUDUKICHUU;
		info.serviceMaster = asm.serviceMaster;
		info.riyouKaishiNengetsubi = applyInfo.riyouKaishiNengetsubi;
		info.riyouCourse = null;
		info.riyouPlan = null;
		info.ipv6Adapter = null;
		info.mailaddress = asm.mailaddress;
		info.ttPhoneStatus = null;
		info.sokuwari15 = null;
		info.kaitsuuKoujibi = null;
		info.koujiYoteibi = null;
		info.dependService1 = asm.dependService1;
		info.dependService2 = asm.dependService2;
		info.businessDetail = stMaster.primaryKey.businessDetail;
		info.applyDetail = stMaster.primaryKey.applyDetail;
		info.procedureType = stMaster.primaryKey.procedureType;
		info.tourokuFlg = "0";
		info.soStatus = stMaster.startStatus;
		info.intervalTime = "0";
		info.lastRunDate = time;
		info.swfFileInfo = stMaster.scriptFileInfo;
		info.deleteDate = null;
		info.createUser = "BatchUser";
		info.lastUpdateUser = "BatchUser";

		return info;

	}

	/**
	 * トランザクション情報(サービス情報(セキュリティ))設定.
	 * <p>
	 * トランザクション情報(サービス情報(セキュリティ))に値を設定する
	 * </p>
	 * @param applyInfo 申込情報
	 * @param kokyakuId 顧客ＩＤ
	 * @param keiyakuId 契約ＩＤ
	 * @param stMaster SWF定義マスタ
	 * @param ass 申込サービス情報(セキュリティ)
	 * @return トランザクション情報
	 * @author 甲斐正之
	 * @version 0.1　2014/07/24　新規作成
	 */
	private static TransServiceInfo setTransServiceInfoSecurity(NewApplyServiceInfo applyInfo,
			String kokyakuId, String keiyakuId, SwfTeigiMaster stMaster, ApplySvcSecurity ass) {

		Date nowDate = Calendar.getInstance().getTime();
		SimpleDateFormat sdf1 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String strDate = sdf1.format(nowDate);
		Timestamp time = Timestamp.valueOf(strDate);
		TransServiceInfo info = new TransServiceInfo();
		info.transactionId = IdKanri.getId(IdCd.TRANSACTION);
		info.kokyakuId = kokyakuId;
		info.keiyakuId = keiyakuId;
		info.serviceId = applyInfo.serviceId;
		info.status = Status.TETSUDUKICHUU;
		info.serviceMaster = ass.serviceMaster;
		info.riyouKaishiNengetsubi = applyInfo.riyouKaishiNengetsubi;
		info.riyouCourse = null;
		info.riyouPlan = null;
		info.ipv6Adapter = null;
		info.mailaddress = null;
		info.ttPhoneStatus = null;
		info.sokuwari15 = null;
		info.kaitsuuKoujibi = null;
		info.koujiYoteibi = null;
		info.dependService1 = null;
		info.dependService2 = null;
		info.businessDetail = stMaster.primaryKey.businessDetail;
		info.applyDetail = stMaster.primaryKey.applyDetail;
		info.procedureType = stMaster.primaryKey.procedureType;
		info.tourokuFlg = "0";
		info.soStatus = stMaster.startStatus;
		info.intervalTime = "0";
		info.lastRunDate = time;
		info.swfFileInfo = stMaster.scriptFileInfo;
		info.deleteDate = null;
		info.createUser = "BatchUser";
		info.lastUpdateUser = "BatchUser";

		return info;

	}

	/**
	 * トランザクション情報(サービス情報(オプション))設定.
	 * <p>
	 * トランザクション情報(サービス情報(オプション))に値を設定する
	 * </p>
	 * @param applyInfo 申込情報
	 * @param kokyakuId 顧客ＩＤ
	 * @param keiyakuId 契約ＩＤ
	 * @param stMaster SWF定義マスタ
	 * @param aso 申込サービス情報(オプション)
	 * @return トランザクション情報
	 * @author 甲斐正之
	 * @version 0.1　2014/07/24　新規作成
	 */
	private static TransServiceInfo setTransServiceInfoOption(NewApplyServiceInfo applyInfo,
			String kokyakuId, String keiyakuId, SwfTeigiMaster stMaster, ApplySvcOption aso) {

		Date nowDate = Calendar.getInstance().getTime();
		SimpleDateFormat sdf1 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String strDate = sdf1.format(nowDate);
		Timestamp time = Timestamp.valueOf(strDate);
		TransServiceInfo info = new TransServiceInfo();
		info.transactionId = IdKanri.getId(IdCd.TRANSACTION);
		info.kokyakuId = kokyakuId;
		info.keiyakuId = keiyakuId;
		info.serviceId = applyInfo.serviceId;
		info.status = Status.TETSUDUKICHUU;
		info.serviceMaster = aso.serviceMaster;
		info.riyouKaishiNengetsubi = applyInfo.riyouKaishiNengetsubi;
		info.riyouCourse = null;
		info.riyouPlan = null;
		info.ipv6Adapter = null;
		info.mailaddress = null;
		info.ttPhoneStatus = null;
		info.sokuwari15 = null;
		info.kaitsuuKoujibi = null;
		info.koujiYoteibi = null;
		info.dependService1 = null;
		info.dependService2 = null;
		info.businessDetail = stMaster.primaryKey.businessDetail;
		info.applyDetail = stMaster.primaryKey.applyDetail;
		info.procedureType = stMaster.primaryKey.procedureType;
		info.tourokuFlg = "0";
		info.soStatus = stMaster.startStatus;
		info.intervalTime = "0";
		info.lastRunDate = time;
		info.swfFileInfo = stMaster.scriptFileInfo;
		info.deleteDate = null;
		info.createUser = "BatchUser";
		info.lastUpdateUser = "BatchUser";

		return info;

	}

	/**
	 * トランザクション情報(サービス情報(オプション))設定.
	 * <p>
	 * トランザクション情報(サービス情報(オプション))に値を設定する
	 * </p>
	 * @param applyInfo 申込情報
	 * @param kokyakuId 顧客ＩＤ
	 * @param keiyakuId 契約ＩＤ
	 * @param stMaster SWF定義マスタ
	 * @param aso 申込サービス情報(オプション)
	 * @return トランザクション情報
	 * @author 甲斐正之
	 * @version 0.1　2014/07/24　新規作成
	 */
	private static TransServiceInfo setTransServiceInfoSettingOption(NewApplyServiceInfo applyInfo,
			String kokyakuId, String keiyakuId, SwfTeigiMaster stMaster, ApplySvcSettingOption aso) {

		Date nowDate = Calendar.getInstance().getTime();
		SimpleDateFormat sdf1 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String strDate = sdf1.format(nowDate);
		Timestamp time = Timestamp.valueOf(strDate);
		TransServiceInfo info = new TransServiceInfo();
		info.transactionId = IdKanri.getId(IdCd.TRANSACTION);
		info.kokyakuId = kokyakuId;
		info.keiyakuId = keiyakuId;
		info.serviceId = applyInfo.serviceId;
		info.status = Status.TETSUDUKICHUU;
		info.serviceMaster = aso.serviceMaster;
		info.riyouKaishiNengetsubi = applyInfo.riyouKaishiNengetsubi;
		info.riyouCourse = null;
		info.riyouPlan = null;
		info.ipv6Adapter = null;
		info.mailaddress = null;
		info.ttPhoneStatus = null;
		info.sokuwari15 = null;
		info.kaitsuuKoujibi = null;
		info.koujiYoteibi = null;
		info.dependService1 = null;
		info.dependService2 = null;
		info.businessDetail = stMaster.primaryKey.businessDetail;
		info.applyDetail = stMaster.primaryKey.applyDetail;
		info.procedureType = stMaster.primaryKey.procedureType;
		info.tourokuFlg = "0";
		info.soStatus = stMaster.startStatus;
		info.intervalTime = "0";
		info.lastRunDate = time;
		info.swfFileInfo = stMaster.scriptFileInfo;
		info.deleteDate = null;
		info.createUser = "BatchUser";
		info.lastUpdateUser = "BatchUser";

		return info;

	}

	/**
	 * トランザクション情報(サービス情報)設定.
	 * <p>
	 * トランザクション情報(サービス情報)に値を設定する
	 * </p>
	 * @param applyInfo 申込情報
	 * @param kokyakuId 顧客ＩＤ
	 * @param keiyakuId 契約ＩＤ
	 * @param stMaster SWF定義マスタ
	 * @return トランザクション情報
	 * @author 甲斐正之
	 * @version 0.1　2014/07/24　新規作成
	 */
	private static TransServiceInfo setTransServiceInfo(ProcedureServiceMaster psMaster,
			String kokyakuId, String keiyakuId, SwfTeigiMaster stMaster) {

		Date nowDate = Calendar.getInstance().getTime();
		SimpleDateFormat sdf1 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String strDate = sdf1.format(nowDate);
		Timestamp time = Timestamp.valueOf(strDate);
		TransServiceInfo info = new TransServiceInfo();
		info.transactionId = IdKanri.getId(IdCd.TRANSACTION);
		info.kokyakuId = kokyakuId;
		info.keiyakuId = keiyakuId;
		info.serviceId = IdKanri.getId(IdCd.SERVICE);
		info.status = Status.TETSUDUKICHUU;
		info.serviceMaster = new NewServiceMaster();
		info.serviceMaster.serviceCd = psMaster.primaryKey.serviceCd;
		info.riyouKaishiNengetsubi = null;
		info.riyouCourse = null;
		info.riyouPlan = null;
		info.ipv6Adapter = null;
		info.mailaddress = null;
		info.ttPhoneStatus = null;
		info.sokuwari15 = null;
		info.kaitsuuKoujibi = null;
		info.koujiYoteibi = null;
		info.dependService1 = null;
		info.dependService2 = null;
		info.businessDetail = stMaster.primaryKey.businessDetail;
		info.applyDetail = stMaster.primaryKey.applyDetail;
		info.procedureType = stMaster.primaryKey.procedureType;
		info.tourokuFlg = "0";
		info.soStatus = stMaster.startStatus;
		info.intervalTime = "0";
		info.lastRunDate = time;
		info.swfFileInfo = stMaster.scriptFileInfo;
		info.deleteDate = null;
		info.createUser = "BatchUser";
		info.lastUpdateUser = "BatchUser";

		return info;

	}

}