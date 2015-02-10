package business;

import java.util.List;

import models.entities.OperatorMaster;
import play.data.Form;
import common.BusinessResult;
import common.BusinessResult.ResultCode;
import constants.Message;

/**
 * ユーザ権限抽出.
 * <p>
 * オペレータマスタのＤＢ部品を呼び出し、<br>
 * 検索結果を返却する業務部品
 * </p>
 * @author 甲斐正之
 * @version 0.1　2014/02/25　新規作成
 * @version 0.4　2014/04/16　STEP4対応
 */
public final class SelectUserKengen {
    private SelectUserKengen() {
    }

    /**
     * ユーザ権限抽出.
     * <p>
     * オペレータマスタのＤＢ部品を呼び出し、<br>
     * 検索結果を返却する。
     * </p>
     * @param operatorId オペレータＩＤ
     * @param operatorPwd パスワード
     * @return オペレータマスタ検索結果
     * @throws Exception ○○例外
     * @author 甲斐正之
     * @version 0.1　2014/02/25　新規作成
     * @version 0.4　2014/04/16　STEP4対応
     */
    public static BusinessResult<Form<OperatorMaster>> selectOperatorMaster(String operatorId, String operatorPwd)
            throws Exception {

        BusinessResult<Form<OperatorMaster>> result = new BusinessResult<Form<OperatorMaster>>();

        try
        {
            // オペレータマスタの検索を実行する
            List<OperatorMaster> omList = OperatorMaster.find.where().eq("operatorId", operatorId)
                    .eq("operatorPwd", operatorPwd).findList();

            if (omList.size() == 1) {
                // 取得件数が1件の場合
                Form<OperatorMaster> form = new Form<OperatorMaster>(OperatorMaster.class);
                OperatorMaster om = omList.get(0);
                form = form.fill(om);

                result.setResultCode(ResultCode.Success);
                result.setValue(form);

            } else {
                // 取得件数が1件以外の場合
                result.setResultCode(ResultCode.BusinessError);
                result.setMessage(Message.MSGID_MUI0001);
            }
        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        }

        return result;
    }

}
