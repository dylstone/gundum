package controllers;

import java.util.ArrayList;
import java.util.List;

import models.entities.ApplyUketsukeKey;
import models.entities.KeiyakuInfo;
import models.entities.OperatorMaster;
import models.sqlrows.KokyakuKeiyakuInfo;
import play.api.templates.Html;
import play.cache.Cache;
import play.data.Form;
import play.mvc.Controller;
import play.mvc.Http.Context;
import play.mvc.Http.Session;
import play.mvc.Result;
import scala.collection.mutable.StringBuilder;
import utils.ResourceManager;
import business.SelectApplyUketsuke;
import business.SelectKeiyakuInfo;
import business.SelectUserKengen;

import common.BusinessResult;
import common.BusinessResult.ResultCode;

/**
 *  顧客情報取得
 *  <p>
 *  顧客情報照会画面表示情報を取得する
 *  </p>
 * @author 那須
 * @version 0.1 2014/04/04 新規作成
 */
public class KokyakuInfoManagement extends Controller {
    
    /**
     * ベースフレーム初期表示
     * <p>
     * ログイン後、ベースフレームを返す処理
     * </p>
     * @return 
     * @author 那須 智貴
     * @version 0.1 2014/07/17 新規作成
     */
    public static Result showBaseFrame() {
        
        // 変数定義
        StringBuilder sbResult = new StringBuilder();
        
        // インスタンス生成。
        BusinessResult<Form<OperatorMaster>> resultOperation = new BusinessResult<Form<OperatorMaster>>();
        BusinessResult<List<ApplyUketsukeKey>> resultlstApplyUketsuke = new BusinessResult<List<ApplyUketsukeKey>>();
        BusinessResult<Integer> resultToDoCnt = new BusinessResult<Integer>();
        
        // ログイン情報（ID・パスワード）リクエストを取得。
        Form<OperatorMaster> formOperatorMaster = new Form<OperatorMaster>(OperatorMaster.class).bindFromRequest();
        
        // リクエストの確認
        if (!formOperatorMaster.hasErrors()) {
            
            // Modelのデータ取得
            OperatorMaster operatorMaster = formOperatorMaster.get();
            
            // 値詰めなおし
            String strOperatorId = operatorMaster.operatorId;
            String strOperatorPwd = operatorMaster.operatorPwd;
            
            strOperatorId = "opid000001";
            strOperatorPwd = "0001";
            
            // オペレータログイン取得処理
            resultOperation = getOperatorInfoExtract(strOperatorId, strOperatorPwd);
            
            // 戻り値確認
            if (resultOperation.getResultCode() != ResultCode.Success)
            {
                return ok(views.html.login.render(resultOperation.getMessage(), resultOperation.getValue()));
            }
            
            // UUIDの発行
            Context ctx = Context.current();
            String keyOpId = ResourceManager.getUUID();
            
            // セッションにUUIDを格納する
            Session session = ctx.session();
            session.put("keyOpId", keyOpId);
            
            // キャッシュにログイン情報（OperatorMaster）を格納する。
            Cache.set(keyOpId, resultOperation.getValue().get());
            
            // ブランドを取得
            String strBrand = ctx().session().get("brand").toString();
            
            // TODO情報の取得（申込受付キー全件取得）
            resultToDoCnt = SelectApplyUketsuke.countApplyUketsuke();
            
            // responseの準備
            sbResult.append(views.html.base.render(resultOperation.getValue(), resultToDoCnt.getValue(), strBrand));
            
        }
        
        // 返却
        return ok(new Html(sbResult));
    }

    /**
	 * 顧客情報検索画面初期表示処理
	 * <p>
	 * 顧客情報照会画面の初期表示を行う
	 * </p>
	 * @return Formインスタンス
	 * @author 那須
	 * @version 0.1 2014/04/04 新規作成
	 */
	public static Result initSearch() {

	    // 変数定義
        StringBuilder sbResult = new StringBuilder();
	    
        // インスタンス生成
        Form<KeiyakuInfo> formKeiyakuInfo = new Form<KeiyakuInfo>(KeiyakuInfo.class);
        List<KokyakuKeiyakuInfo> lstKeiyaku = new ArrayList<KokyakuKeiyakuInfo>();
        
        // responseの準備
        sbResult.append(views.html.subframes.sf_search_result.render(formKeiyakuInfo,lstKeiyaku));
        
        // 返却
        return ok(new Html(sbResult));
        
//		// Formインスタンス生成
//		Form<KeiyakuInfo> formKeiyakuInfo = new Form<KeiyakuInfo>(KeiyakuInfo.class);
//		Form<OperatorMaster> formOperatorMaster = new Form<OperatorMaster>(OperatorMaster.class).bindFromRequest();
//
//		// Listインスタンス生成
//		ArrayList<KokyakuKeiyakuInfo> lstKokyakuKeiyakuInfo = new ArrayList<KokyakuKeiyakuInfo>();
//
//		// リクエストの確認
//		if (!formOperatorMaster.hasErrors()) {
//			// Modelのデータ取得
//			OperatorMaster operatorMaster = formOperatorMaster.get();
//			// 値詰めなおし
//			String strOperatorId = operatorMaster.operatorId;
//			String strOperatorPwd = operatorMaster.operatorPwd;
//
//			ResultCode success = ResultCode.Success;
//			BusinessResult<Form<OperatorMaster>> resultOperation;
//
//			//オペレータログイン取得処理
//			resultOperation = getOperatorInfoExtract(strOperatorId, strOperatorPwd);
//
//			if (resultOperation.getResultCode() != success)
//			{
//				return ok(views.html.login.render(resultOperation.getMessage(), resultOperation.getValue()));
//			}
//			
//			formOperatorMaster =  resultOperation.getValue();
//
//		}
//
//		Integer cntApplyUketuske = SelectApplyUketsuke.countApplyUketsuke().getValue();
//		// Viewに戻す
//		return ok(views.html.kokyaku_search_result.render(formKeiyakuInfo, lstKokyakuKeiyakuInfo, formOperatorMaster,
//				cntApplyUketuske));
	}

	/**
	 * 契約情報検索画面結果表示処理
	 * <p>
	 * 契約情報照会画面の結果表示を行う
	 * </p>
	 * @return Formインスタンス
	 * @author 那須
	 * @version 0.1 2014/04/04 新規作成
	 */
	public static Result searchResult() {
	    
	    // 変数定義
        StringBuilder sbResult = new StringBuilder();
        
		// ViewからのForm情報を取得
		Form<KeiyakuInfo> formKeiyakuInfo = new Form<KeiyakuInfo>(KeiyakuInfo.class).bindFromRequest();
		Form<OperatorMaster> formOperatorMaster = new Form<OperatorMaster>(OperatorMaster.class);

		// リクエストの確認
		if (!formKeiyakuInfo.hasErrors()) {

			// Modelのデータ取得
			KeiyakuInfo keiyakuInfo = formKeiyakuInfo.get();

			// 契約ID詰めなおし
			String keiyakuId = keiyakuInfo.keiyakuId;

			// 契約情報取得処理
			BusinessResult<List<KokyakuKeiyakuInfo>> resultKeiyakuInfo = getKeiyakuSearchResultExtract(keiyakuId);

//			return ok(views.html.subframes.sf_search_result.render(formKeiyakuInfo, resultKeiyakuInfo.getValue(),
//					formOperatorMaster));
			sbResult.append(views.html.parts.pt_search_result_grid.render(resultKeiyakuInfo.getValue()));
			
			return ok(new Html(sbResult));
			//return ok(views.html.subframes.sf_search_result.render(formKeiyakuInfo, resultKeiyakuInfo.getValue()));

		} else
		{
			return redirect("/");
		}

	}

	/**
	 * 契約情報の取得処理
	 * <p>
	 *契約情報を取得し返却する
	 * </p>
	 * @return BusinessPesult<Form<OperatorMaster>>ResulOperation
	 * @author 那須
	 * @version 0.2 2014/04/07 新規作成
	 */
	private static BusinessResult<List<KokyakuKeiyakuInfo>> getKeiyakuSearchResultExtract(String strKeiyakuId) {

		BusinessResult<List<KokyakuKeiyakuInfo>> resultKeiyakuInfo = new BusinessResult<List<KokyakuKeiyakuInfo>>();

		try {
			resultKeiyakuInfo = SelectKeiyakuInfo.selectKeiyakuResultList(strKeiyakuId);

		} catch (Exception e) {
			// TODO 自動生成された catch ブロック
			e.printStackTrace();
		}

		return resultKeiyakuInfo;
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
