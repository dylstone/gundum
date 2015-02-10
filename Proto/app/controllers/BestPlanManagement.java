package controllers;

import play.api.templates.Html;
import play.mvc.Controller;
import play.mvc.Result;
import scala.collection.mutable.StringBuilder;

/**
 *  ベストプラン情報管理
 *  <p>
 *  ベストプラン関係を管理する
 *  </p>
 * @author 那須　智貴
 * @version 0.1 2014/08/13 新規作成
 */
public class BestPlanManagement extends Controller {

	/**
	 * 最適商品提案検索画面の初期表示処理
	 * <p>
	 * 最適商品提案検索画面の初期表示を行う
	 * </p>
	 * @return Formインスタンス
	 * @author 那須　智貴
	 * @version 0.1 2014/08/13 新規作成
	 * @throws  
	 */
	public static Result initSearch() {
	    
	    // 変数定義
        StringBuilder sbResult = new StringBuilder();
		
		// 返却するサブフレームを作成
		sbResult.append(views.html.subframes.sf_search_best_plan.render(""));
		
		// Viewに戻す
		return ok(new Html(sbResult));
	}
	
	/**
     * 最適商品提案一覧画面の初期表示処理
     * <p>
     * 最適商品提案一覧画面の初期表示を行う
     * </p>
     * @return Formインスタンス
     * @author 那須　智貴
     * @version 0.1 2014/08/13 新規作成
     * @throws  
     */
    public static Result searchResult() {
        
        // 変数定義
        StringBuilder sbResult = new StringBuilder();
        
        // 返却するサブフレームを作成
        sbResult.append(views.html.subframes.sf_result_best_plan.render(""));
        
        // Viewに戻す
        return ok(new Html(sbResult));
    }
    
    
    /**
     * 料金シミュレーション画面処理
     * <p>
     * 料金シミュレーション画面の表示を行う
     * </p>
     * @return Formインスタンス
     * @author 那須　智貴
     * @version 0.1 2014/08/13 新規作成
     * @throws  
     */
    public static Result ryokinSimulation() {
        
        // 変数定義
        StringBuilder sbResult = new StringBuilder();
        
        // 返却するサブフレームを作成
        sbResult.append(views.html.subframes.sf_ryokin_simulation.render(""));
        
        // Viewに戻す
        return ok(new Html(sbResult));
    }
	
}
