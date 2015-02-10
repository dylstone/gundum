package business;

import java.util.List;

import models.entities.ApplyUketsukeKey;
import models.sqlrows.ApplyUketsukeInfo;

import common.BusinessResult;
import common.BusinessResult.ResultCode;

import constants.DbDataValue;

/**
 * 申込受付キー検索業務部品.
 * <p>
 * 申込受付キーのＤＢ部品を呼び出し、<br>
 * 検索結果を返却する業務部品
 * </p>
 * @author 甲斐正之
 * @version 0.1　2014/04/11　新規作成
 */
public final class SelectApplyUketsuke {
	private SelectApplyUketsuke() {
	}

	/**
	 * 申込受付キー検索.
	 * <p>
	 * 申込受付キーのＤＢ部品を呼び出し、<br>
	 * 検索結果を返却する
	 * </p>
	 * @return 申込受付キー
	 * @author 甲斐正之
	 * @version 0.1　2014/04/11　新規作成
	 */
	public static BusinessResult<List<ApplyUketsukeKey>> selectApplyUketsuke() {

		BusinessResult<List<ApplyUketsukeKey>> result = new BusinessResult<List<ApplyUketsukeKey>>();

		try {

			// 申込情報の検索を実行する
			List<ApplyUketsukeKey> lstKey = ApplyUketsukeKey.find.where()
					.order("keiyakuId, queueId").findList();

			result.setResultCode(ResultCode.Success);
			result.setValue(lstKey);

		} catch (Exception e) {
			e.printStackTrace();
		}

		return result;

	}

	/**
	 * 申込受付情報.
	 * <p>
	 * 申込受付情報のＤＢ部品を呼び出し、<br>
	 * 検索結果を返却する
	 * </p>
	 * @return 申込受付情報
	 * @author 甲斐正之
	 * @version 0.1　2014/07/21　新規作成
	 */
	public static BusinessResult<List<ApplyUketsukeInfo>> selectApplyUketsukeInfo() {

		BusinessResult<List<ApplyUketsukeInfo>> result = new BusinessResult<List<ApplyUketsukeInfo>>();

		try {

			// 申込情報の検索を実行する
			List<ApplyUketsukeInfo> lstInfo = ApplyUketsukeInfo.find().where().eq("activityId", "020").findList();

			result.setResultCode(ResultCode.Success);
			result.setValue(lstInfo);

		} catch (Exception e) {
			e.printStackTrace();
		}

		return result;

	}

	/**
	 * 申込受付キー検索.
	 * <p>
	 * 申込受付キーのＤＢ部品を呼び出し、<br>
	 * 検索結果を返却する
	 * </p>
	 * @param queueId キーID
	 * @return 申込受付キー
	 * @author 甲斐正之
	 * @version 0.1　2014/07/21　新規作成
	 */
	public static BusinessResult<ApplyUketsukeKey> selectApplyUketsukeKey(String queueId) {

		BusinessResult<ApplyUketsukeKey> result = new BusinessResult<ApplyUketsukeKey>();

		try {

			// 申込情報の検索を実行する
			ApplyUketsukeKey auInfo = ApplyUketsukeKey.find.byId(queueId);

			if (auInfo != null) {
				result.setResultCode(ResultCode.Success);
				result.setValue(auInfo);

			} else {
				result.setResultCode(ResultCode.BusinessError);
			}

		} catch (Exception e) {
			e.printStackTrace();
		}

		return result;

	}

	/**
	 * 申込受付キー承認対象件数取得.
	 * <p>
	 * 申込受付キーのＤＢ部品を呼び出し、<br>
	 * 承認対象件数を返却する
	 * </p>
	 * @return 申込情報
	 * @author 甲斐正之
	 * @version 0.1　2014/04/11　新規作成
	 */
	public static BusinessResult<Integer> countApplyUketsuke() {

		BusinessResult<Integer> result = new BusinessResult<Integer>();

		try {

			// 申込情報の検索を実行する
			int cntKey = ApplyUketsukeKey.find.where()
					.eq("activityId", "020").findRowCount();

			if (cntKey > 0) {
				result.setResultCode(ResultCode.Success);
				result.setValue(Integer.valueOf(cntKey));

			} else {
				result.setResultCode(ResultCode.BusinessError);
				result.setValue(Integer.valueOf(0));
			}

		} catch (Exception e) {
			e.printStackTrace();
		}

		return result;

	}
	
	/**
     * 申込受付キー検索.
     * <p>
     * 申込受付キーのＤＢ部品を呼び出し、<br>
     * 顧客ID、契約IDをキーに検索結果を返却する
     * </p>
     * @param queueId キーID
     * @return 申込受付キー
     * @author 甲斐正之
     * @version 0.1　2014/07/21　新規作成
     */
    public static BusinessResult<Integer> selectApplyUketsukeKeyCountByKokyakuInfo(String strKokyakuId, String strKeiyakuId) {

        // インスタンス生成
        BusinessResult<Integer> result = new BusinessResult<Integer>();

        try {

            // キューIDをキーとして手続き進捗キューを検索する。
            int i = ApplyUketsukeKey.find.where()
                    .eq("kokyakuId", strKokyakuId)
                    .eq("keiyakuId", strKeiyakuId)
                    .ne("activityId", DbDataValue.Activity.END)
                    .findRowCount();
            
            // 戻り値格納
            result.setValue(i);
            result.setResultCode(ResultCode.Success);

        } catch (Exception e) {
            e.printStackTrace();
        }

        return result;

    }
}