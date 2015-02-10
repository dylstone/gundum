package business;

import models.entities.ApplyInfo;
import models.entities.ApplyUketsukeKey;

import common.ApplyInfoXml;
import common.BusinessResult;
import common.BusinessResult.ResultCode;

public class CreateApplyRireki {
	private CreateApplyRireki() {
	};

	/**
	 * 申込履歴登録.
	 * <p>
	 * 申込顧客情報のＤＢ部品を呼び出し、<br>
	 * 登録を実行する
	 * </p>
	 * @param aiXml 申込情報XML
	 * @param auKey 申込受付キー
	 * @return 実行結果
	 * @throws Exception ○○例外
	 * @author 甲斐正之
	 * @version 0.1　2014/07/28　新規作成
	 */
	public static BusinessResult<String> createApplyRireki(ApplyInfoXml aiXml, ApplyUketsukeKey auKey) throws Exception {

		BusinessResult<String> result = new BusinessResult<String>();
		ApplyInfo info = new ApplyInfo();

		try {

			//info = setApplyInfo(aiXml, auKey);
			//info.applyKokyakuInfo = setApplyKokyakuInfo(info, aiXml, auKey);
			//info.applyKeiyakuInfo = setApplyKeiyakuInfo(info, aiXml, auKey);
			//info.newApplyServiceInfo = setApplyServiceInfo(info, aiXml, auKey);
			info = aiXml.applyContents.applyInfo;

			info.save();

			result.setResultCode(ResultCode.Success);

		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}

		return result;

	}

	/**
	 * 申込情報設定.
	 * <p>
	 * 申込情報に値を設定する
	 * </p>
	 * @param inputInfo 画面入力情報
	 * @param kokyakuInfo 顧客情報
	 * @param operator オペレータID
	 * @return 申込情報クラス
	 * @author 甲斐正之
	 * @version 0.1　2014/07/28　新規作成
	 */
	/*private static ApplyInfo setApplyInfo(ApplyInfoXml aiXml, ApplyUketsukeKey auKey) {

		ApplyInfo info = new ApplyInfo();
		info.applyId = aiXml.applyContents.applyId;
		info.uketsukeId = IdKanri.getId(IdCd.UKETSUKE);
		info.kokyakuInfo = new KokyakuInfo();
		info.kokyakuInfo.kokyakuId = auKey.kokyakuId;
		info.keiyakuInfo = new KeiyakuInfo();
		info.keiyakuInfo.keiyakuId = auKey.keiyakuId;
		info.applyStatus = ApplyStatus.SHINSEICHUU;
		info.tourokuMissFlg = Boolean.FALSE;
		info.deleteDate = null;
		info.createUser = "BatchUser";
		info.lastUpdateUser = "BatchUser";

		return info;

	}*/

	/**
	 * 申込顧客情報設定.
	 * <p>
	 * 申込顧客情報に値を設定する
	 * </p>
	 * @param inputInfo 画面入力情報
	 * @param kokyakuInfo 顧客情報
	 * @param apInfo 申込情報
	 * @param operatorId オペレータID
	 * @return 申込顧客情報クラス
	 * @author 甲斐正之
	 * @version 0.1　2014/07/28　新規作成
	 */
	/*private static ApplyKokyakuInfo setApplyKokyakuInfo(ApplyInfo apInfo, ApplyInfoXml aiXml, ApplyUketsukeKey auKey) {

		ApplyKokyakuInfo info = new ApplyKokyakuInfo();
		info.applyKokyakuId = IdKanri.getId(IdCd.APPLY_KOKYAKU);
		info.applyInfo = apInfo;
		info.kokyakuInfo = new KokyakuInfo();
		info.kokyakuInfo.kokyakuId = auKey.kokyakuId;
		info.henkouType = HenkouType.UPDATE;
		info.syouninStatus = SyouninStatus.SYOUNINMACHI;
		info.kokyakuUserId = aiXml.kokyakuInfo.kokyakuUserId;
		info.kokyakuUserPwd = aiXml.kokyakuInfo.kokyakuUserPwd;
		info.lastName = aiXml.applyContents.lastName;
		info.firstName = aiXml.applyContents.firstName;
		info.lastNameFurigana = aiXml.applyContents.lastNameFurigana;
		info.firstNameFurigana = aiXml.applyContents.firstNameFurigana;
		info.department = aiXml.applyContents.department;
		info.corporativeUser = aiXml.applyContents.corporativeUser;
		info.seibetsuCd = aiXml.applyContents.seibetsuCd;
		info.birthNengetsubi = aiXml.applyContents.birthNengetsubi;
		info.jobCd = aiXml.applyContents.jobCd;
		info.mikomiKokyakuFlg = aiXml.applyContents.mikomiKokyakuFlg;
		info.renrakusakiMailaddress = aiXml.applyContents.renrakusakiMailaddress;
		info.telType = aiXml.applyContents.telType;
		info.telNo1 = aiXml.applyContents.telNo1;
		info.telNo2 = aiXml.applyContents.telNo2;
		info.faxNo = aiXml.applyContents.faxNo;
		info.zipNo = aiXml.applyContents.zipNo;
		info.todouhuken = aiXml.applyContents.todouhuken;
		info.siKuGun = aiXml.applyContents.siKuGun;
		info.tyousonOaza = aiXml.applyContents.tyousonOaza;
		info.azaBanchiGou = aiXml.applyContents.azaBanchiGou;
		info.apartment = aiXml.applyContents.apartment;
		info.apartmentRoomNo = aiXml.applyContents.apartmentRoomNo;
		info.kidukeSama = aiXml.applyContents.kidukeSama;
		info.kokyakuUserPwdLastUpdated = aiXml.kokyakuInfo.kokyakuUserPwdLastUpdated;
		info.createUser = "BatchUser";
		info.lastUpdateUser = "BatchUser";

		return info;

	}*/

	/**
	 * 申込契約情報設定.
	 * <p>
	 * 申込契約情報に値を設定する
	 * </p>
	 * @param inputInfo 画面入力情報
	 * @param kokyakuInfo 顧客情報
	 * @param apInfo 申込情報
	 * @param apKeiyakuInfo 申込顧客情報
	 * @param operatorId オペレータID
	 * @return 申込契約情報クラス
	 * @author 甲斐正之
	 * @version 0.1　2014/07/28　新規作成
	 */
	/*private static ApplyKeiyakuInfo setApplyKeiyakuInfo(ApplyInfo apInfo, ApplyInfoXml aiXml, ApplyUketsukeKey auKey) {

		ApplyKeiyakuInfo info = new ApplyKeiyakuInfo();
		info.applyKeiyakuId = IdKanri.getId(IdCd.APPLY_KEIYAKU);
		info.applyInfo = apInfo;
		info.applyKokyakuInfo = apInfo.applyKokyakuInfo;
		info.keiyakuInfo = new KeiyakuInfo();
		info.keiyakuInfo.keiyakuId = auKey.keiyakuId;
		info.henkouType = HenkouType.UPDATE;
		info.syouninStatus = SyouninStatus.SYOUNINMACHI;
		info.keiyakuBrandCd = aiXml.applyContents.keiyakuBrandCd;
		info.hanbaiKeitai = aiXml.applyContents.hanbaiKeitai;
		info.moushikomiNengetsubi = aiXml.applyContents.moushikomiNengetsubi;
		info.keiyakuKoushinNengetsu = aiXml.applyContents.keiyakuKoushinNengetsu;
		info.riyouKaishiNengetsubi = aiXml.applyContents.riyouKaishiNengetsubi;
		info.shiharaiHouhou = aiXml.applyContents.shiharaiHouhou;
		info.seikyuuKubun = aiXml.applyContents.seikyuuKubun;
		info.deleteDate = null;
		info.createUser = "BatchUser";
		info.lastUpdateUser = "BatchUser";

		return info;

	}*/

	/**
	 * 申込サービス情報設定.
	 * <p>
	 * 申込サービス情報に値を設定する
	 * </p>
	 * @param inputInfo 画面入力情報
	 * @param kokyakuInfo 顧客情報
	 * @param apInfo 申込情報
	 * @param apKeiyakuInfo 申込契約情報
	 * @param operatorId オペレータID
	 * @return 申込サービス情報クラス
	 * @author 甲斐正之
	 * @version 0.1　2014/07/28　新規作成
	 */
	/*private static List<NewApplyServiceInfo> setApplyServiceInfo(ApplyInfo apInfo, ApplyInfoXml aiXml,
			ApplyUketsukeKey auKey) {

		List<NewApplyServiceInfo> infoList = new ArrayList<NewApplyServiceInfo>();
		NewApplyServiceInfo info = new NewApplyServiceInfo();
		ServiceBunruiMaster sbMaster = ServiceBunruiMaster.find.byId(aiXml.applyContents.serviceCd);

		info.applyServiceId = IdKanri.getId(IdCd.APPLY_SERVICE);
		info.applyInfo = apInfo;
		info.applyKeiyakuInfo = apInfo.applyKeiyakuInfo;
		info.serviceId = auKey.serviceId;
		info.henkouType = HenkouType.UPDATE;
		info.syouninStatus = SyouninStatus.SYOUNINMACHI;

		if ("0001".equals(sbMaster.serviceBunruiCd)) {
			// 回線
			info.applySvcKaisen.add(makeServiceKaisen(aiXml, auKey.serviceId));

		} else if ("0005".equals(sbMaster.serviceBunruiCd)) {
			// 通知
			info.applySvcMail.add(makeServiceMail(aiXml, auKey.serviceId));

		} else if ("0006".equals(sbMaster.serviceBunruiCd)) {
			// セキュリティ
			info.applySvcSecurity.add(makeServiceSecurity(aiXml, auKey.serviceId));

		} else if ("0002".equals(sbMaster.serviceBunruiCd)) {
			// オプション
			info.applySvcOption.add(makeServiceOption(aiXml, auKey.serviceId));

		}

		info.riyouKaishiNengetsubi = aiXml.applyContents.riyouKaishiNengetsubi;
		info.deleteDate = null;
		info.createUser = "BatchUser";
		info.lastUpdateUser = "BatchUser";

		infoList.add(info);
		return infoList;

	}*/

	/**
	 * 申込サービス情報(回線)登録処理
	 * <p>
	 * 申込サービス情報(回線)登録を行う
	 * </p>
	 * @param aiXml 申込情報XML
	 * @param serviceId サービスID
	 * @return 申込サービス情報(回線)
	 * @author 那須智貴
	 * @version 0.1　2014/07/24　新規作成
	 */
	/*private static ApplySvcKaisen makeServiceKaisen(ApplyInfoXml aiXml, String serviceId) {

		// インスタンス生成
		ApplySvcKaisen svcKaisen = new ApplySvcKaisen();

		svcKaisen.applySvcAuhikariId = IdKanri.getId(IdCd.KAISEN);
		svcKaisen.applyService = new NewApplyServiceInfo();
		svcKaisen.applyService.serviceId = serviceId;
		svcKaisen.serviceMaster = new NewServiceMaster();
		svcKaisen.serviceMaster.serviceCd = aiXml.applyContents.serviceCd;
		svcKaisen.riyouCourse = aiXml.applyContents.riyouCourse;
		svcKaisen.riyouPlan = aiXml.applyContents.riyouPlan;
		svcKaisen.ipv6Adapter = aiXml.applyContents.ipv6Adapter;
		svcKaisen.ttPhoneStatus = aiXml.applyContents.ttPhoneStatus;
		svcKaisen.sokuwari15 = aiXml.applyContents.sokuwari15;
		svcKaisen.kaitsuuKoujibi = aiXml.applyContents.kaitsuuKoujibi;
		svcKaisen.koujiYoteibi = aiXml.applyContents.koujiYoteibi;
		svcKaisen.deleteDate = "";
		svcKaisen.createUser = "BatchUser";
		svcKaisen.lastUpdateUser = "BatchUser";

		return svcKaisen;
	}*/

	/**
	 * 申込サービス情報(メール)登録処理
	 * <p>
	 * 申込サービス情報(メール)登録を行う
	 * </p>
	 * @param aiXml 申込情報XML
	 * @param serviceId サービスID
	 * @return 申込サービス情報(メール)
	 * @author 甲斐
	 * @version 0.1　2014/07/24　新規作成
	 */
	/*private static ApplySvcMail makeServiceMail(ApplyInfoXml aiXml, String serviceId) {

		// インスタンス生成
		ApplySvcMail svcMail = new ApplySvcMail();

		svcMail.applySvcMailId = IdKanri.getId(IdCd.MAIL);
		svcMail.applyService = new NewApplyServiceInfo();
		svcMail.applyService.serviceId = serviceId;
		svcMail.serviceMaster = new NewServiceMaster();
		svcMail.serviceMaster.serviceCd = aiXml.applyContents.serviceCd;
		svcMail.mailaddress = aiXml.applyContents.mailaddress;
		svcMail.dependService1 = new NewServiceMaster();
		svcMail.dependService1.serviceCd = aiXml.applyContents.dependServiceCd1;
		svcMail.dependService2 = new NewServiceMaster();
		svcMail.dependService2.serviceCd = aiXml.applyContents.dependServiceCd2;
		svcMail.deleteDate = "";
		svcMail.createUser = "BatchUser";
		svcMail.lastUpdateUser = "BatchUser";

		return svcMail;
	}*/

	/**
	 * 申込サービス情報(セキュリティ)登録処理
	 * <p>
	 * 申込サービス情報(セキュリティ)登録を行う
	 * </p>
	 * @param aiXml 申込情報XML
	 * @param serviceId サービスID
	 * @return 申込サービス情報(セキュリティ)
	 * @author 甲斐
	 * @version 0.1　2014/07/24　新規作成
	 */
	/*private static ApplySvcSecurity makeServiceSecurity(ApplyInfoXml aiXml, String serviceId) {

		// インスタンス生成
		ApplySvcSecurity svcSecurity = new ApplySvcSecurity();

		svcSecurity.applySvcSecurityId = IdKanri.getId(IdCd.SECURITY);
		svcSecurity.applyService = new NewApplyServiceInfo();
		svcSecurity.applyService.serviceId = serviceId;
		svcSecurity.serviceMaster = new NewServiceMaster();
		svcSecurity.serviceMaster.serviceCd = aiXml.applyContents.serviceCd;
		svcSecurity.deleteDate = "";
		svcSecurity.createUser = "BatchUser";
		svcSecurity.lastUpdateUser = "BatchUser";

		return svcSecurity;
	}*/

	/**
	 * 申込サービス情報(オプション)登録処理
	 * <p>
	 * 申込サービス情報(オプション)登録を行う
	 * </p>
	 * @param aiXml 申込情報XML
	 * @param serviceId サービスID
	 * @return 申込サービス情報(オプション)
	 * @author 甲斐
	 * @version 0.1　2014/07/24　新規作成
	 */
	/*private static ApplySvcOption makeServiceOption(ApplyInfoXml aiXml, String serviceId) {

		// インスタンス生成
		ApplySvcOption svcOption = new ApplySvcOption();

		svcOption.applySvcOptionId = IdKanri.getId(IdCd.OPTION);
		svcOption.applyService = new NewApplyServiceInfo();
		svcOption.applyService.serviceId = serviceId;
		svcOption.serviceMaster = new NewServiceMaster();
		svcOption.serviceMaster.serviceCd = aiXml.applyContents.serviceCd;
		svcOption.deleteDate = "";
		svcOption.createUser = "BatchUser";
		svcOption.lastUpdateUser = "BatchUser";

		return svcOption;
	}*/
}