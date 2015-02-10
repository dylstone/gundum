package business;

import java.util.List;

import models.entities.ProcedureServiceMaster;

import common.BusinessResult;
import common.BusinessResult.ResultCode;

/**
 * 手続き実行サービスマスタ抽出業務部品.
 * <p>
 * 手続き実行サービスマスタのＤＢ部品を呼び出し、<br>
 * 手続き実行サービスマスタを抽出する業務部品
 * </p>
 * @author 甲斐正之
 * @version 0.1　2014/07/07　新規作成
 */
public class SelectProcedureServiceMaster {

	/**
	 * 手続き実行サービスマスタ抽出.
	 * <p>
	 * 手続き実行サービスマスタのＤＢ部品を呼び出し、<br>
	 * 手続き実行サービスマスタを抽出する
	 * </p>
	 * @param 申込情報
	 * @return 処理結果
	 * @author 甲斐正之
	 * @version 0.1　2014/07/07　新規作成
	 */
	public static BusinessResult<List<ProcedureServiceMaster>> selectProcedureServiceMaster()
			throws Exception {

		// インスタンス生成
		BusinessResult<List<ProcedureServiceMaster>> result = new BusinessResult<List<ProcedureServiceMaster>>();

		try {

			// 手続き実行サービスマスタを検索する。
			List<ProcedureServiceMaster> master = ProcedureServiceMaster.find.where()
					.eq("primaryKey.shohinCd", "0001")
					.eq("primaryKey.businessDetail", "3")
					.findList();

			result.setValue(master);
			result.setResultCode(ResultCode.Success);

		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}

		return result;
	}

}