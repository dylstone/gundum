package business;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import models.entities.ProcedureServiceMaster;
import models.entities.ProcedureTypeMaster;
import models.entities.SwfTeigiMaster;

import common.BusinessResult;
import common.BusinessResult.ResultCode;

/**
 * サービスワークフロー定義マスタ抽出業務部品.
 * <p>
 * サービスワークフロー定義マスタのＤＢ部品を呼び出し、<br>
 * サービスワークフロー定義マスタを抽出する業務部品
 * </p>
 * @author 甲斐正之
 * @version 0.1　2014/07/07　新規作成
 */
public final class SelectSwfTeigiMaster {
    private SelectSwfTeigiMaster() {
    }

    /**
     * サービスワークフロー定義マスタ抽出.
     * <p>
     * サービスワークフロー定義マスタのＤＢ部品を呼び出し、<br>
     * サービスワークフロー定義マスタを抽出する
     * </p>
     * @param ptMaster 手続き種別マスタ
     * @return 処理結果
     * @throws Exception ○○例外
     * @author 甲斐正之
     * @version 0.1　2014/07/07　新規作成
     */
    public static BusinessResult<SwfTeigiMaster> selectSwfTeigiMaster(ProcedureTypeMaster ptMaster)
            throws Exception {

        // インスタンス生成
        BusinessResult<SwfTeigiMaster> result = new BusinessResult<SwfTeigiMaster>();

        try {

            Date nowDate = Calendar.getInstance().getTime();
            SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
            String strDate = sdf.format(nowDate);

            // 申込情報のサービスコードをキーとしてサービスワークフロー定義マスタを検索する
            List<SwfTeigiMaster> master = SwfTeigiMaster.find.where()
                    .eq("primaryKey.serviceCd", ptMaster.primaryKey.serviceCd)
                    .eq("primaryKey.businessDetail", ptMaster.primaryKey.businessDetail)
                    .eq("primaryKey.applyDetail", ptMaster.primaryKey.applyDetail)
                    .eq("primaryKey.procedureType", ptMaster.procedureType)
                    .le("primaryKey.tekiyoStartDate", strDate)
                    .ge("tekiyoEndDate", strDate).findList();

            if (master == null || master.size() != 1) {
                // 手続き種別マスタが取得できなかった場合
                result.setResultCode(ResultCode.BusinessError);
            } else {
                result.setValue(master.get(0));
                result.setResultCode(ResultCode.Success);
            }

        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        }

        return result;
    }
    
    /**
	 * サービスワークフロー定義マスタ抽出.
	 * <p>
	 * サービスワークフロー定義マスタのＤＢ部品を呼び出し、<br>
	 * サービスワークフロー定義マスタを抽出する
	 * </p>
	 * @param 手続き実行サービスマスタ
	 * @return 処理結果
	 * @author 甲斐正之
	 * @version 0.1　2014/07/07　新規作成
	 */
	public static BusinessResult<SwfTeigiMaster> selectSwfTeigiMaster(ProcedureServiceMaster psMaster)
			throws Exception {

		// インスタンス生成
		BusinessResult<SwfTeigiMaster> result = new BusinessResult<SwfTeigiMaster>();

		try {

			Date nowDate = Calendar.getInstance().getTime();
			SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
			String strDate = sdf.format(nowDate);

			// 申込情報のサービスコードをキーとしてサービスワークフロー定義マスタを検索する
			List<SwfTeigiMaster> master = SwfTeigiMaster.find.where()
					.eq("primaryKey.serviceCd", psMaster.primaryKey.serviceCd)
					.eq("primaryKey.businessDetail", psMaster.primaryKey.businessDetail)
					.eq("primaryKey.applyDetail", "1")
					.eq("primaryKey.procedureType", psMaster.procedureType)
					.le("primaryKey.tekiyoStartDate", strDate)
					.ge("tekiyoEndDate", strDate).findList();

			if (master == null || master.size() != 1) {
				// 手続き種別マスタが取得できなかった場合
				result.setResultCode(ResultCode.BusinessError);
			} else {
				result.setValue(master.get(0));
				result.setResultCode(ResultCode.Success);
			}

		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}

		return result;
	}

}