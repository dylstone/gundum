package business;

import models.entities.ApplyXml;

import common.ApplyInfoXml;
import common.BusinessResult;
import common.BusinessResult.ResultCode;
import common.ChangeApplyInfoFromXml;

/**
 * 申込XML検索業務部品.
 * <p>
 * 申込XMLのＤＢ部品を呼び出し、<br>
 * 検索結果を返却する業務部品
 * </p>
 * @author 甲斐正之
 * @version 0.1　2014/05/15　新規作成
 */
public final class MakeApplyInfo {
	private MakeApplyInfo() {
	}

	/**
	 * 申込XML検索.
	 * <p>
	 * 申込XMLのＤＢ部品を呼び出し、<br>
	 * 検索結果を返却する
	 * </p>
	 * @param key 申込ID
	 * @return 申込XML情報
	 * @throws Exception ○○例外
	 * @author 甲斐正之
	 * @version 0.1　2014/05/15　新規作成
	 */
	public static BusinessResult<ApplyInfoXml> makeApplyInfo(String key) throws Exception {

		String strXml = "";
		BusinessResult<ApplyInfoXml> result = new BusinessResult<ApplyInfoXml>();

		try {

			// 申込XMLの検索を実行する
			ApplyXml xml = ApplyXml.find.byId(key);

			if (xml == null) {
				// 申込XMLが取得できなかった場合
				result.setResultCode(ResultCode.BusinessError);
			} else {
				// 申込XMLが取得できた場合
				strXml = xml.applyXml;
				ApplyInfoXml info = ChangeApplyInfoFromXml.changeFromXml(strXml);
				result.setValue(info);
				result.setResultCode(ResultCode.Success);
			}
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}

		return result;

	}
}