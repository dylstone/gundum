package controllers;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import models.entities.ApplyInfo;
import models.entities.ApplyXml;
import models.entities.KokyakuInfo;
import models.entities.NewApplyServiceInfo;
import models.entities.NewServiceInfo;
import models.entities.NewServiceMaster;
import models.entities.OperatorMaster;
import models.input.WizardInputInfo;
import models.sqlrows.ApplyUketsukeInfo;
import play.api.templates.Html;
import play.data.Form;
import play.mvc.Controller;
import play.mvc.Result;
import scala.collection.mutable.StringBuilder;
import business.MakeDiff;
import business.SelectApplyUketsuke;
import business.SelectApplyXML;
import business.SelectKokyakuInfo;
import business.SelectServiceMaster;
import business.SelectUserKengen;
import business.UpdateApplyUketsuke;

import com.avaje.ebean.Ebean;
import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.xml.DomDriver;
import common.ApplyInfoXml;
import common.BusinessResult;
import common.BusinessResult.ResultCode;

/**
 *  申込情報管理
 *  <p>
 *  申込情報を画面管理する
 *  </p>
 * @author 甲斐
 * @version 0.1 2014/07/10 新規作成
 */
public class ApplyManagement extends Controller {

	/**
	 * 申込受付キー承認画面初期表示処理
	 * <p>
	 * 申込受付キー承認画面の初期表示を行う
	 * </p>
	 * @return Formインスタンス
	 * @author 甲斐
	 * @version 0.1 2014/07/10 新規作成
	 * @throws  
	 */
	public static Result initSearch() {
	    
	    // 変数定義
        StringBuilder sbResult = new StringBuilder();
        
		// Listインスタンス生成
		//BusinessResult<List<ApplyUketsukeKey>> lstApplyUketsuke = new BusinessResult<List<ApplyUketsukeKey>>();
        BusinessResult<List<ApplyUketsukeInfo>> lstApplyUketsuke = new BusinessResult<List<ApplyUketsukeInfo>>();

		// 申込受付キー全件取得
        lstApplyUketsuke = SelectApplyUketsuke.selectApplyUketsukeInfo();
		
		// 返却するサブフレームを作成
		sbResult.append(views.html.subframes.sf_apply_shonin.render(lstApplyUketsuke.getValue()));
		
		// Viewに戻す
		return ok(new Html(sbResult));
	}
	
	/**
     * 承認詳細画面初期表示処理
     * <p>
     * 承認詳細画面の初期表示を行うコントローラメソッド。
     * </p>
     * @return Formインスタンス
     * @author 那須　智貴
     * @version 0.1 2014/07/10 新規作成
     * @throws  
     */
    public static Result getApplyDetail() {
        
        String method = request().method();

        // HTTPメソッドかどうか確認
        if ("GET".equals(method)) {
            return redirect("/");
        }
        
        // 変数定義
        StringBuilder sbResult = new StringBuilder();

        // インスタンス生成
        BusinessResult<Form<KokyakuInfo>> resultKokyakuInfo = new BusinessResult<Form<KokyakuInfo>>();
        Form<ApplyInfo> formApplyInfo = new Form<ApplyInfo>(ApplyInfo.class);
        BusinessResult<List<ApplyXml>> searchResult = new BusinessResult<List<ApplyXml>>();
        Form<WizardInputInfo> formWizardInputInfo = new Form<WizardInputInfo>(WizardInputInfo.class);
        // ダミーインスタンス
        Map<String, List<NewServiceInfo>> mapServiceInfo = new HashMap<String, List<NewServiceInfo>>();
        Map<String, List<NewApplyServiceInfo>> mapApplyServiceInfo = new HashMap<String, List<NewApplyServiceInfo>>();
        BusinessResult<List<NewServiceMaster>> resultLstServiceMaster = new BusinessResult<List<NewServiceMaster>>();

        
        // ViewからのForm情報を取得
        Map<String, String[]> formSearchCond = request().body().asFormUrlEncoded();
        String strApplyId = formSearchCond.get("postApplyId")[0];
        String strQueueId = formSearchCond.get("postQueueId")[0];
        String strKokyakuId = formSearchCond.get("postKokyakuId")[0];
        String strKeiyakuId = formSearchCond.get("postKeiyakuId")[0];
        String strActivityId = formSearchCond.get("postActivityId")[0];
        
        //////////////////////////////////
        /* 顧客情報取得処理             */
        //////////////////////////////////
        try {
            resultKokyakuInfo = SelectKokyakuInfo.selectKokyakuInfo(strKokyakuId);
            if(resultKokyakuInfo.getResultCode() != ResultCode.Success){
                // エラーページとかに飛ばす。今は未実装。
            }
        } catch (Exception e) {
            // TODO 自動生成された catch ブロック
            e.printStackTrace();
        }
        
        //////////////////////////////////
        /* サービスマスタ取得処理         */
        //////////////////////////////////
        try {
            resultLstServiceMaster = SelectServiceMaster.all();
            if(resultLstServiceMaster.getResultCode() != ResultCode.Success ){
                // マスタ取れない場合は、エラーページ遷移予定
            }
            
            }catch (Exception e) {
            // TODO 自動生成され catch ブロック
            e.printStackTrace();
        }

        //////////////////////////////////
        /* 申込XML取得処理         */
        //////////////////////////////////
        ApplyInfoXml applyInfoXml = new ApplyInfoXml();
        
        String strApplyXML = "";
        try {
            // 申込XML取得
            searchResult = SelectApplyXML.selectApplyXMLByApplyId(strApplyId);
            
            // 申込情報XMLをオブジェクトに変換
            if(searchResult.getValue().size() > 0){
                if(searchResult.getValue().get(0).applyXml != null){
                    strApplyXML = searchResult.getValue().get(0).applyXml;
                    XStream xs = new XStream(new DomDriver());
                    applyInfoXml = (ApplyInfoXml) xs.fromXML(strApplyXML);
                    formApplyInfo = formApplyInfo.fill(applyInfoXml.applyContents.applyInfo);
                    // 仕掛中がある場合、マージ処理を行う。
                    WizardInputInfo wizardInputInfo = MakeDiff.makeDiff(formApplyInfo.get(), resultLstServiceMaster.getValue());
                    formWizardInputInfo = formWizardInputInfo.fill(wizardInputInfo);
                }
            }
        } catch (Exception e) {
            // TODO 自動生成され catch ブロック
            e.printStackTrace();
        }
        
        
        
        
        // 返却するサブフレームを作成
        sbResult.append(views.html.subframes.sf_apply_shonin_detail.render(resultKokyakuInfo.getValue(),
                formApplyInfo,
                formWizardInputInfo,
                mapServiceInfo,
                mapApplyServiceInfo,
                resultLstServiceMaster.getValue(),
                strQueueId,
                strActivityId
                ));
        
        // Viewに戻す
        return ok(new Html(sbResult));
    }

	/**
	 * 申込受付キー承認処理
	 * <p>
	 * 申込受付キーを承認し、承認後の結果表示を行う
	 * </p>
	 * @return Formインスタンス
	 * @author 甲斐
	 * @version 0.1 2014/07/10 新規作成
	 */
	public static Result shoninResult() {
	    
	    // 変数定義
        StringBuilder sbResult = new StringBuilder();
        
        // ViewからのForm情報を取得
        Map<String, String[]> formShoninCond = request().body().asFormUrlEncoded();
        String strQueueId = formShoninCond.get("queueId")[0];
        
        // インスタンス生成
        BusinessResult<String> resultUpd = new BusinessResult<String>();
        BusinessResult<List<ApplyUketsukeInfo>> lstApplyUketsuke = new BusinessResult<List<ApplyUketsukeInfo>>();
        List<ApplyUketsukeInfo> lstKey = new ArrayList<ApplyUketsukeInfo>();

        try {

            Ebean.beginTransaction();

            resultUpd = UpdateApplyUketsuke.updateApplyUketsukeKey(strQueueId);

            if (resultUpd.getResultCode() == ResultCode.Success) {
                Ebean.commitTransaction();

                // リスト再取得
                lstApplyUketsuke = SelectApplyUketsuke.selectApplyUketsukeInfo();
                lstKey = lstApplyUketsuke.getValue();

            } else {
                Ebean.rollbackTransaction();
            }

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            Ebean.endTransaction();
        }

//        Integer cntApplyUketuske = SelectApplyUketsuke.countApplyUketsuke().getValue();
//        BusinessResult<Form<OperatorMaster>> resultOperation;
//
//        //オペレータログイン取得処理
//        resultOperation = getOperatorInfoExtract(id, pwd);

		// 返却するサブフレームを作成
        sbResult.append(views.html.subframes.sf_apply_shonin.render(lstApplyUketsuke.getValue()));
        
        // Viewに戻す
        return ok(new Html(sbResult));

	}

	/**
	 * オペレータログイン情報の取得処理
	 * <p>
	 * オペレータログイン情報を取得し返却する
	 * </p>
	 * @return BusinessPesult<Form<OperatorMaster>>ResulOperation
	 * @author 江川
	 * @version 0.2 2014/03/05 新規作成
	 */
	private static BusinessResult<Form<OperatorMaster>> getOperatorInfoExtract(
			String strOperatorId, String strOperatorPwd) {

		BusinessResult<Form<OperatorMaster>> resultOperation = new BusinessResult<Form<OperatorMaster>>();

		try {
			resultOperation = SelectUserKengen.selectOperatorMaster(strOperatorId, strOperatorPwd);

		} catch (Exception e) {
			// TODO 自動生成された catch ブロック
			e.printStackTrace();
		}

		return resultOperation;
	}
}
