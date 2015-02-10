package controllers;

import java.util.List;
import java.util.Map;

import models.entities.ApplyInfo;
import models.entities.ApplyXml;
import models.entities.GamenItemControlInfo;
import models.entities.KokyakuInfo;
import models.entities.MypageControlInfo;
import models.entities.NewApplyServiceInfo;
import models.entities.NewServiceInfo;
import models.entities.NewServiceMaster;
import models.entities.OperatorMaster;
import models.input.ServiceInputInfo;
import models.input.WizardInputInfo;
import models.sqlrows.KokyakuTaiouRirekiInfo;
import play.api.templates.Html;
import play.cache.Cache;
import play.data.Form;
import play.mvc.Controller;
import play.mvc.Http.Context;
import play.mvc.Http.Session;
import play.mvc.Result;
import scala.collection.mutable.StringBuilder;
import utils.ResourceManager;
import utils.ServiceUtil;
import utils.parser.RequireForm;
import business.InsertApplyKokyakuInfo;
import business.InsertApplyUketsuke;
import business.InsertTaiouRireki;
import business.MakeDiff;
import business.MakeServiceInfo;
import business.SelectApplyUketsuke;
import business.SelectApplyXML;
import business.SelectGamenKomoku;
import business.SelectKokyakuInfo;
import business.SelectMypageControlInfo;
import business.SelectProgressQueue;
import business.SelectServiceMaster;
import business.SelectTaiouRireki;

import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.xml.DomDriver;
import common.ApplyInfoXml;
import common.BusinessResult;
import common.BusinessResult.ResultCode;

import constants.DbDataValue;

/**
 *  顧客情報取得
 *  <p>
 *  顧客情報照会画面表示情報を取得する
 *  </p>
 * @author 江川
 * @version 0.1 2014/02/13 新規作成
 *          0.2 2014/03/05 Step2取込
 */
public class Application extends Controller {

    /**
     * ログイン画面表示情報の取得処理
     * <p>
     * Formインスタンスを返却する
     * </p>
     * @return Formインスタンス
     * @author 那須
     * @version 0.1 2014/02/13 新規作成
     */
    public static Result index() {

        // Formインスタンス生成
        Form<models.entities.OperatorMaster> formOperatorMaster =
                new Form<models.entities.OperatorMaster>(models.entities.OperatorMaster.class);

        // Viewに戻す
        return ok(views.html.login.render("", formOperatorMaster));
    }

    /**
     * 顧客情報照会画面表示情報の取得処理
     * <p>
     * 顧客情報照会画面の表示に必要な情報を取得し返却する
     * </p>
     * @return 顧客情報、申込情報、オペレータ情報、画面項目情報
     *         対応履歴情報、マイページ制御情報
     *          サービス情報、申込サービス情報
     * @author 江川
     * @version 0.1 2014/02/13 新規作成
     * @version 0.2 2014/03/05 Step2取込
     * @version 0.3 2014/04/08 Step4取込(契約一覧作成)
     * @version 0.4 2014/04/14 Step4取込(キャッシュ対応)
     */
    public static Result getKokyakuInfo() {

        String method = request().method();

        // HTTPメソッドかどうか確認
        if ("GET".equals(method)) {
            return redirect("/");
        }
        
        // 変数定義
        StringBuilder sbResult = new StringBuilder();
        
        // ViewからのForm情報を取得
        Map<String, String[]> formSearchCond = request().body().asFormUrlEncoded();
        String strKokyakuId = formSearchCond.get("postKokyakuId")[0];
        String strKeiyakuId = formSearchCond.get("postKeiyakuId")[0];

        // 変数定義(INパラメータ)
        String strSubFrameId = "win1    ";
        String strfunctionCd = "mv000001";

        // 変数定義(businessからの返却値)
        ResultCode success = ResultCode.Success;
        BusinessResult<Form<KokyakuInfo>> resultKokyakuInfo = new BusinessResult<Form<KokyakuInfo>>();;
        BusinessResult<Form<ApplyInfo>> resultApplyInfo;
        BusinessResult<List<GamenItemControlInfo>> resultGamenKomokuInfo;
        BusinessResult<List<KokyakuTaiouRirekiInfo>> resultTaiouRirekiInfo;
        BusinessResult<List<MypageControlInfo>> resultMypageControlInfo;
        BusinessResult<List<NewServiceMaster>> resultServiceMaster = new BusinessResult<List<NewServiceMaster>>();;
        Form<OperatorMaster> formOperatorMaster = new Form<OperatorMaster>(OperatorMaster.class);
        Form<WizardInputInfo> formWizardInputInfo = new Form<WizardInputInfo>(WizardInputInfo.class);

        // UUIDの発行
        Context ctx = Context.current();
//      String keyOpId = ResourceManager.getUUID();
        String keyKokyakuId = ResourceManager.getUUID();
        String keyKeiyakuId = ResourceManager.getUUID();
        String keyKokyakuInfo = ResourceManager.getUUID();
        String keyServiceInfo = ResourceManager.getUUID();
        String keyApplyInfo = ResourceManager.getUUID();
        String keyApplyXML = ResourceManager.getUUID();

        // セッションにUUIDを格納する
        Session session = ctx.session();
        session.put("keyKokyakuId", keyKokyakuId);
        session.put("keyKeiyakuId", keyKeiyakuId);
        session.put("keyKokyakuInfo", keyKokyakuInfo);
        session.put("keyServiceInfo", keyServiceInfo);
        session.put("keyApplyInfo", keyApplyInfo);
        session.put("keyApplyXML", keyApplyXML);

        // キャッシュのKey定義
        //      String keyOpId = "opeid";
        //      String keyKokyakuId = "kokyakuid1";
        //      String keyKeiyakuId = "keiyakuid1";

        // キャッシュに取得した情報を格納する
        Cache.set(keyKokyakuId, strKokyakuId);
        Cache.set(keyKeiyakuId, strKeiyakuId);

        //////////////////////////////////
        /* オペレータログイン取得処理   */
        //////////////////////////////////
        String strOpCacheKey = ctx().session().get("keyOpId").toString();
        Object objOperation = Cache.get(strOpCacheKey);
        if(objOperation == null){
            // エラーページへ遷移（ここでは省略。）
        }else{
            formOperatorMaster = formOperatorMaster.fill((OperatorMaster) objOperation);
        }

        //////////////////////////////////
        /* 顧客情報取得処理             */
        //////////////////////////////////
        resultKokyakuInfo = getKokyakuInfoExtract(strKokyakuId);

        if (resultKokyakuInfo.getResultCode() != success)
        {
            return ok(views.html.login.render(resultKokyakuInfo.getMessage(), formOperatorMaster));
        }

        // サービス情報の編集を行う業務部品を呼び出す
//        BusinessResult<Map<String, List<ServiceInfo>>> mapServiceInfo =
//                MakeServiceInfo.makeServiceInfo(formKokyakuInfo);
        BusinessResult<Map<String, List<NewServiceInfo>>> mapServiceInfo = 
                new BusinessResult<Map<String,List<NewServiceInfo>>>();

        // キャッシュのKey定義
        //      String keyKokyakuInfo = "kokyakuinfo1";
        //      String keyServiceInfo = "serviceinfo1";

        // キャッシュに取得した情報を格納する
        Cache.set(keyKokyakuInfo, resultKokyakuInfo.getValue());
        Cache.set(keyServiceInfo, mapServiceInfo.getValue());

        //////////////////////////////////
        /* 申込顧客情報取得処理         */
        //////////////////////////////////
        resultApplyInfo = getApplyInfoExtract(strKokyakuId, strKeiyakuId, keyApplyXML);

        // キャッシュのKey定義
        //      String keyApplyInfo = "ApplyInfo1";

        // キャッシュに取得した情報を格納する
        Cache.set(keyApplyInfo, resultApplyInfo.getValue());
        
        //////////////////////////////////
        /* サービスマスタ取得処理         */
        //////////////////////////////////
        resultServiceMaster = getServiceMasterExtract();
        // サービスマスタをキャッシュにいれておく。
        String keyServiceMaster = ResourceManager.getUUID();
        session.put("keyServiceMaster", keyServiceMaster);
        Cache.set(keyServiceMaster, resultServiceMaster.getValue());
        
        //////////////////////////////////
        /* 画面項目情報取得処理         */
        //////////////////////////////////
        resultGamenKomokuInfo = getGamenKomokuInfoExtract(strSubFrameId);

        //////////////////////////////////
        /* 対応履歴情報取得処理         */
        //////////////////////////////////
        resultTaiouRirekiInfo = getTaiouRirekiInfoExtract(strKokyakuId);

        //////////////////////////////////
        /* マイページ制御情報取得処理   */
        //////////////////////////////////
        resultMypageControlInfo = getMypageControlInfoExtract(formOperatorMaster.value().get().operatorId, strfunctionCd);
        
        // viewへの引数
        BusinessResult<Map<String, List<NewApplyServiceInfo>>> mapApplyServiceInfo =
                new BusinessResult<Map<String, List<NewApplyServiceInfo>>>();
        
        // 申込受付キー、手続き進捗キーを見て申込履歴、パーマネントなどを見るべき
        // ここでは、申込履歴を検索して、結果値によって比較表示を行う。
        if(resultApplyInfo.getValue() == null){
            sbResult.append(views.html.subframes.sf_kokyaku_info.render(resultKokyakuInfo.getValue(), mapServiceInfo.getValue(),resultServiceMaster.getValue()));
        }else{
            // 仕掛中がある場合、マージ処理を行う。
            WizardInputInfo wizardInputInfo = MakeDiff.makeDiff(resultApplyInfo.getValue().get(), resultServiceMaster.getValue());
            formWizardInputInfo = formWizardInputInfo.fill(wizardInputInfo);
            // キャッシュにいれておく
            String keywizardInfo = ResourceManager.getUUID();
            session.put("keywizardInfo", keywizardInfo);
            Cache.set(keywizardInfo, formWizardInputInfo);
            // 申込サービス情報の編集を行う業務部品を呼び出す
            //Form<ApplyInfo> formApplyInfo = new Form<ApplyInfo>(ApplyInfo.class);
            //mapApplyServiceInfo = MakeServiceInfo.makeApplyServiceInfo(formApplyInfo.fill(resultApplyInfo.getValue().get()));
            sbResult.append(
                    views.html.subframes.sf_kokyaku_info_diff.render(
                            resultKokyakuInfo.getValue(),
                            resultApplyInfo.getValue(),
                            formWizardInputInfo,
                            mapServiceInfo.getValue(),
                            mapApplyServiceInfo.getValue(),
                            resultServiceMaster.getValue()
                            ));
        }
        sbResult.append(
                views.html.subframes.sf_taiou_rireki.render(resultTaiouRirekiInfo.getValue(), resultGamenKomokuInfo.getValue()));
        sbResult.append(views.html.subframes.sf_kokyaku_kakushu_rireki.render());
        sbResult.append(views.html.subframes.sf_urikakekin_info.render());

        // Viewに返す
        //return ok(views.html.base.render(resultOperation.getValue()));
        return ok(new Html(sbResult));
    }

    /**
     * 開局チェック情報取得処理
     * <p>
     * 開局チェック画面の表示に必要な情報を取得し返却する
     * </p>
     * @return サービスマスタ情報、顧客情報、ウィザード情報
     *         サービス情報
     * @author 江川
     * @version 0.3 2014/03/17 Step3取込
     * @version 0.4 2014/04/14 Step4取込（キャッシュ対応）
     */

    public static Result getKaikyokuCheckInfo() {

        // キャッシュのKey定義
        //      String keyServiceMaster = "ServiceMaster1";
        //      String keyKokyakuInfo = "kokyakuinfo1";
        //      String keyServiceInfo = "serviceinfo1";
        
        // 変数定義
        String strServMasterCacheKey = null;
        
        // UUIDの発行
//        Context ctx = Context.current();
//        String keyServiceMaster = ResourceManager.getUUID();
//
//        // セッションにUUIDを格納する
//        Session session = ctx.session();
//        session.put("keyServiceMaster", keyServiceMaster);

        // セッションからUUIDを取得する
        //      String keyServiceMaster = ctx().session().get("keyServiceMaster").toString();
        String keyKokyakuInfo = ctx().session().get("keyKokyakuInfo").toString();
        String keyServiceInfo = ctx().session().get("keyServiceInfo").toString();

        try {

//            //////////////////////////////////
//            /* サービスマスタ情報を取得する */
//            //////////////////////////////////
//            resultServiceMaster = SelectKaikyokuCheckInfo.selectKaikyokuCheckInfo();
//
//            listServiceMaster = resultServiceMaster.getValue();
//
//            // サービスマスタ情報をキャッシュに格納する
//            Cache.set(keyServiceMaster, listServiceMaster);
            strServMasterCacheKey = ctx().session().get("keyServiceMaster").toString();

        } catch (Exception e) {
            // TODO 自動生成された catch ブロック
            e.printStackTrace();
        }

        // ウィザード情報のインスタンス作成
        Form<WizardInputInfo> formWizardInputInfo = new Form<WizardInputInfo>(WizardInputInfo.class);

        // キャッシュに格納している情報を取得する
        @SuppressWarnings("unchecked")
        List<NewServiceMaster> calistServiceMaster = (List<NewServiceMaster>) Cache.get(strServMasterCacheKey);
        @SuppressWarnings("unchecked")
        Form<KokyakuInfo> caFormKokyakuInfo = (Form<KokyakuInfo>) Cache.get(keyKokyakuInfo);
        @SuppressWarnings("unchecked")
        Map<String, List<NewServiceInfo>> camapServiceInfo =
                (Map<String, List<NewServiceInfo>>) Cache.get(keyServiceInfo);
        
        // 利用コースプルダウンを生成するために、サービスマスタを作成し直す
        List<NewServiceMaster> lstKaisen = ServiceUtil.fillterByServiceBunruiCd(DbDataValue.ServiceBunrui.KAISEN, calistServiceMaster);
        
        return ok(views.html.subframes.sf_iten_tetsuzuki.render(
                lstKaisen,
                caFormKokyakuInfo,
                formWizardInputInfo,
                camapServiceInfo));

    }

    /**
     * 開局チェック画面戻る処理
     * <p>
     * 開局チェック画面を隠す処理
     * </p>
     * @return サービスマスタ情報、顧客情報、ウィザード情報
     *         サービス情報
     * @author 江川
     * @version 0.3 2014/03/17 Step3取込
     * @version 0.4 2014/04/14 Step4取込（キャッシュ対応）
     */
    public static Result kaikyokuCheckBack() {

        // キャッシュのKey定義
        //      String keyServiceMaster = "ServiceMaster1";
        //      String keyKokyakuInfo = "kokyakuinfo1";
        //      String keyServiceInfo = "serviceinfo1";

        // セッションからUUIDを取得する
        String keyServiceMaster = ctx().session().get("keyServiceMaster").toString();
        String keyKokyakuInfo = ctx().session().get("keyKokyakuInfo").toString();
        String keyServiceInfo = ctx().session().get("keyServiceInfo").toString();

        // キャッシュに格納している情報を取得する
        @SuppressWarnings("unchecked")
        Form<KokyakuInfo> caFormKokyakuInfo = (Form<KokyakuInfo>) Cache.get(keyKokyakuInfo);
        @SuppressWarnings("unchecked")
        List<NewServiceMaster> caListServiceMaster = (List<NewServiceMaster>) Cache.get(keyServiceMaster);
        @SuppressWarnings("unchecked")
        Map<String, List<NewServiceInfo>> camapServiceInfo = (Map<String, List<NewServiceInfo>>) Cache.get(keyServiceInfo);

        // ウィザード情報のインスタンス作成
        Form<WizardInputInfo> formWizardInputInfo = new Form<WizardInputInfo>(WizardInputInfo.class);

        // キャッシュのクリア
        //Cache.remove(keyServiceMaster);

        return ok(views.html.subframes.sf_iten_tetsuzuki.render(
                caListServiceMaster,
                caFormKokyakuInfo,
                formWizardInputInfo,
                camapServiceInfo));
    }

    /**
     * 移転先申込処理
     * <p>
     * 移転先コース申込画面を表示する
     * </p>
     * @return サービスマスタ情報、顧客情報、ウィザード情報
     *         サービス情報
     * @author 江川
     * @version 0.3 2014/03/17 Step3取込
     * @version 0.4 2014/04/14 Step4取込（キャッシュ対応）
     */
    @RequireForm({ "SSCR0004.wizardContents" })
    public static Result itensakiMoushikomi() {

        // Viewのウィザード情報を取得する
        @SuppressWarnings("unchecked")
        Form<WizardInputInfo> formWizardInputInfo =
                (Form<WizardInputInfo>) ctx().args.get("SSCR0004.wizardContents");

        // キャッシュのKey定義
        //      String keyServiceMaster = "ServiceMaster1";
        //      String keyKokyakuInfo = "kokyakuinfo1";
        //      String keywizardInfo = "wizardInfo1";
        //      String keyServiceInfo = "serviceinfo1";

        // UUIDの発行
        Context ctx = Context.current();
        String keywizardInfo = ResourceManager.getUUID();

        // セッションにUUIDを格納する
        Session session = ctx.session();
        session.put("keywizardInfo", keywizardInfo);

        // ウィザード情報をキャッシュに格納する
        Cache.set(keywizardInfo, formWizardInputInfo);

        // セッションからUUIDを取得する
        String keyServiceMaster = ctx().session().get("keyServiceMaster").toString();
        String keyKokyakuInfo = ctx().session().get("keyKokyakuInfo").toString();
        String keyServiceInfo = ctx().session().get("keyServiceInfo").toString();

        // キャッシュに格納している情報を取得する
        @SuppressWarnings("unchecked")
        Form<KokyakuInfo> caFormKokyakuInfo = (Form<KokyakuInfo>) Cache.get(keyKokyakuInfo);
        @SuppressWarnings("unchecked")
        List<NewServiceMaster> caListServiceMaster = (List<NewServiceMaster>) Cache.get(keyServiceMaster);
        @SuppressWarnings("unchecked")
        Form<WizardInputInfo> caFormWizardInputInfo = (Form<WizardInputInfo>) Cache.get(keywizardInfo);
        @SuppressWarnings("unchecked")
        Map<String, List<NewServiceInfo>> camapServiceInfo = (Map<String, List<NewServiceInfo>>) Cache.get(keyServiceInfo);

        return ok(views.html.subframes.sf_iten_tetsuzuki.render(
                caListServiceMaster,
                caFormKokyakuInfo,
                caFormWizardInputInfo,
                camapServiceInfo));
    }

    /**
     * 移転先申込画面戻る処理
     * <p>
     * 移転先申込画面を隠し、開局チェック画面を表示する
     * </p>
     * @return サービスマスタ情報、顧客情報、ウィザード情報
     *         サービス情報
     * @author 江川
     * @version 0.3 2014/03/17 Step3取込
     * @version 0.4 2014/04/14 Step4取込（キャッシュ対応）
     */
    public static Result itensakiMoushikomiBack() {

        // キャッシュのKey定義
        //      String keyServiceMaster = "ServiceMaster1";
        //      String keyKokyakuInfo = "kokyakuinfo1";
        //      String keywizardInfo = "wizardInfo1";
        //      String keyServiceInfo = "serviceinfo1";

        // セッションからUUIDを取得する
        String keyServiceMaster = ctx().session().get("keyServiceMaster").toString();
        String keyKokyakuInfo = ctx().session().get("keyKokyakuInfo").toString();
        String keywizardInfo = ctx().session().get("keywizardInfo").toString();
        String keyServiceInfo = ctx().session().get("keyServiceInfo").toString();

        // キャッシュに格納している情報を取得する
        @SuppressWarnings("unchecked")
        Form<KokyakuInfo> caFormKokyakuInfo = (Form<KokyakuInfo>) Cache.get(keyKokyakuInfo);
        @SuppressWarnings("unchecked")
        List<NewServiceMaster> caListServiceMaster = (List<NewServiceMaster>) Cache.get(keyServiceMaster);
        @SuppressWarnings("unchecked")
        Form<WizardInputInfo> caFormWizardInputInfo = (Form<WizardInputInfo>) Cache.get(keywizardInfo);
        @SuppressWarnings("unchecked")
        Map<String, List<NewServiceInfo>> camapServiceInfo = (Map<String, List<NewServiceInfo>>) Cache.get(keyServiceInfo);

        return ok(views.html.subframes.sf_iten_tetsuzuki.render(
                caListServiceMaster,
                caFormKokyakuInfo,
                caFormWizardInputInfo,
                camapServiceInfo));

    }

    /**
     * 申込確認情報表示処理
     * <p>
     * 契約者情報申込確認画面を表示する
     * </p>
     * @return 顧客情報、申込情報、ウィザード情報
     *         サービス情報、申込サービス情報
     * @author 江川
     * @version 0.3 2014/03/17 Step3取込
     * @version 0.4 2014/04/14 Step4取込（キャッシュ対応）
     */
    @RequireForm({ "SSCR0004.wizardContents" })
    public static Result moushikomiKakuninInfo() {

        // Viewのウィザード情報を取得する
        @SuppressWarnings("unchecked")
        Form<WizardInputInfo> formWizardInputInfo =
                (Form<WizardInputInfo>) ctx().args.get("SSCR0004.wizardContents");

        // キャッシュのKey定義
        //      String keyServiceMaster = "ServiceMaster1";
        //      String keyKokyakuInfo = "kokyakuinfo1";
        //      String keywizardInfo = "wizardInfo1";
        //      String keyServiceInfo = "serviceinfo1";

        // セッションからUUIDを取得する
        String keyServiceMaster = ctx().session().get("keyServiceMaster").toString();
        String keyKokyakuInfo = ctx().session().get("keyKokyakuInfo").toString();
        String keywizardInfo = ctx().session().get("keywizardInfo").toString();
        String keyServiceInfo = ctx().session().get("keyServiceInfo").toString();
        String keyApplyInfo = ctx().session().get("keyApplyInfo").toString();

        // ウィザード情報をキャッシュに格納する
        Cache.set(keywizardInfo, formWizardInputInfo);

        // キャッシュに格納している情報を取得する
        @SuppressWarnings("unchecked")
        Form<KokyakuInfo> caFormKokyakuInfo = (Form<KokyakuInfo>) Cache.get(keyKokyakuInfo);
        @SuppressWarnings("unchecked")
        Form<WizardInputInfo> caFormWizardInputInfo = (Form<WizardInputInfo>) Cache.get(keywizardInfo);
        @SuppressWarnings("unchecked")
        Map<String, List<NewServiceInfo>> camapServiceInfo = (Map<String, List<NewServiceInfo>>) Cache.get(keyServiceInfo);
        @SuppressWarnings("unchecked")
        List<NewServiceMaster> caListServiceMaster = (List<NewServiceMaster>) Cache.get(keyServiceMaster);
        @SuppressWarnings("unchecked")
        Form<ApplyInfo> caFormApplyInfo = (Form<ApplyInfo>) Cache.get(keyApplyInfo);
        // キャッシュのクリア
        //Cache.remove(keyServiceMaster);

        // viewへの引数
        BusinessResult<Map<String, List<NewApplyServiceInfo>>> mapApplyServiceInfo =
                new BusinessResult<Map<String, List<NewApplyServiceInfo>>>();

        return ok(views.html.subframes.sf_kokyaku_info_diff.render(
                caFormKokyakuInfo,
                caFormApplyInfo,
                caFormWizardInputInfo,
                camapServiceInfo,
                mapApplyServiceInfo.getValue(),
                caListServiceMaster
                ));
    }

    /**
     * 申込顧客情報登録処理
     * <p>
     * 申込顧客情報を登録後、申込情報を再取得し返却する
     * </p>
     * @return 顧客情報、申込情報、ウィザード情報
     *         サービス情報、申込サービス情報
     * @author 江川
     * @version 0.4 2014/04/14 Step4取込
     */
    @SuppressWarnings("unchecked")
    public static Result tourokuApplyKokyakuInfo() {

        // 変数定義
        //      String strSubFrameId = "win1    ";
        String strSettingOptionKey = "";
        StringBuilder sbResult = new StringBuilder();
        String strTaiouNaiyou = "";
        
        // 変数定義(businessからの返却値)
        ResultCode success = ResultCode.Success;
        BusinessResult<Form<ApplyInfo>> resultApplyInfo;
        BusinessResult<Form<KokyakuInfo>> resultKokyakuInfo;
        BusinessResult<List<KokyakuTaiouRirekiInfo>> resultTaiouRirekiInfo = new BusinessResult<List<KokyakuTaiouRirekiInfo>>();
        BusinessResult<List<GamenItemControlInfo>> resultGamenKomokuInfo = new BusinessResult<List<GamenItemControlInfo>>();
        //      BusinessResult<List<GamenItemControlInfo>> ResultGamenKomokuInfo;

        // キャッシュのKey定義
        //      String keyOpId = "opeid1";
        //      String keyKokyakuInfo = "kokyakuinfo1";
        //      String keyApplyInfo = "ApplyInfo1";
        //      String keywizardInfo = "wizardInfo1";
        //      String keyServiceInfo = "serviceinfo1";
        //      String keyKokyakuId = "kokyakuid1";

        // セッションからUUIDを取得する
        String keyOpId = ctx().session().get("keyOpId").toString();
        String keyKokyakuInfo = ctx().session().get("keyKokyakuInfo").toString();
        String keyApplyInfo = ctx().session().get("keyApplyInfo").toString();
        String keywizardInfo = ctx().session().get("keywizardInfo").toString();
        String keyServiceInfo = ctx().session().get("keyServiceInfo").toString();
        String keyKokyakuId = ctx().session().get("keyKokyakuId").toString();
        String keyServiceMaster = ctx().session().get("keyServiceMaster").toString();
        String keyApplyXML = ctx().session().get("keyApplyXML").toString();
        String strKokyakuIdKey = ctx().session().get("keyKokyakuId").toString();
        String strKeiyakuIdKey = ctx().session().get("keyKeiyakuId").toString();
        Map<String, String[]> reqMap = ctx().request().body().asFormUrlEncoded();   // オプション情報
        if(ctx().session().get("settingOptionKey") != null){
            strSettingOptionKey = ctx().session().get("settingOptionKey").toString();
        }
        if(reqMap.get("taiouNaiyou") != null){
            if(reqMap.get("taiouNaiyou").length > 0){
                strTaiouNaiyou = reqMap.get("taiouNaiyou")[0];
            }
        }

        // キャッシュに格納している情報を取得する
//      String caStrOperatorId = (String) Cache.get(keyOpId);
        String caStrKokyakuId = (String) Cache.get(keyKokyakuId);
        Form<KokyakuInfo> caFormKokyakuInfo = (Form<KokyakuInfo>) Cache.get(keyKokyakuInfo);
        //Form<ApplyInfo> caFormApplyInfo = (Form<ApplyInfo>) Cache.get(keyApplyInfo);
        Form<WizardInputInfo> caFormWizardInputInfo = (Form<WizardInputInfo>) Cache.get(keywizardInfo);
        OperatorMaster operatorMaster = (OperatorMaster) Cache.get(keyOpId);
        List<ApplyXml> caLstApplyXML = (List<ApplyXml>) Cache.get(keyApplyXML);
        String strKokyakuId = (String) Cache.get(strKokyakuIdKey);
        String strKeiyakuId = (String) Cache.get(strKeiyakuIdKey);
        Form<ServiceInputInfo> formServiceInputInfo = (Form<ServiceInputInfo>) Cache.get(strSettingOptionKey);
        
        
        //////////////////////////////////
        /* 申込履歴登録処理             */
        //////////////////////////////////
        resultApplyInfo = makeApplyHistory(
                caFormWizardInputInfo, caFormKokyakuInfo, operatorMaster.operatorId,caLstApplyXML, reqMap, strKokyakuId, strKeiyakuId, formServiceInputInfo);
        
        if (resultApplyInfo.getResultCode() != success)
        {
            // エラー画面へ遷移する
            return ok(views.html.subframes.sf_error.render(resultApplyInfo.getMessage()));
        }
        
        //////////////////////////////////
        /* 対応履歴情報登録処理         */
        //////////////////////////////////
        if(!strTaiouNaiyou.isEmpty()){
            resultTaiouRirekiInfo = taiouRirekiInfoReg(caStrKokyakuId, operatorMaster.operatorId, strTaiouNaiyou);
            if (resultTaiouRirekiInfo.getResultCode() != success)
            {
                // エラー画面へ遷移する
                return ok(views.html.subframes.sf_error.render(resultTaiouRirekiInfo.getMessage()));
            }
            sbResult.append(
                    views.html.subframes.sf_taiou_rireki.render(
                            resultTaiouRirekiInfo.getValue(),
                            resultGamenKomokuInfo.getValue()));
        }

        // 申込サービス情報の編集(View表示)
        Form<ApplyInfo> formApplyInfo = resultApplyInfo.getValue();

        // 申込サービス情報の編集を行う業務部品を呼び出す
        BusinessResult<Map<String, List<NewApplyServiceInfo>>> mapApplyServiceInfo =
                MakeServiceInfo.makeApplyServiceInfo(formApplyInfo);

        // キャッシュに取得した情報を格納する
        Cache.set(keyApplyInfo, resultApplyInfo.getValue());

        //////////////////////////////////
        /* 顧客情報取得処理（再取得）   */
        //////////////////////////////////
        resultKokyakuInfo = getKokyakuInfoExtract(caStrKokyakuId);

        if (resultKokyakuInfo.getResultCode() != success)
        {
            // エラー画面へ遷移する
            return ok(views.html.subframes.sf_error.render(resultKokyakuInfo.getMessage()));
        }

        // サービス情報の編集(View表示)
        Form<KokyakuInfo> formKokyakuInfo = resultKokyakuInfo.getValue();

        // サービス情報の編集を行う業務部品を呼び出す
        BusinessResult<Map<String, List<NewServiceInfo>>> mapServiceInfo =
                MakeServiceInfo.makeServiceInfo(formKokyakuInfo);

        // キャッシュに取得した情報を格納する
        Cache.set(keyKokyakuInfo, resultKokyakuInfo.getValue());
        Cache.set(keyServiceInfo, mapServiceInfo.getValue());

        //////////////////////////////////
        /* 画面項目情報取得処理         */
        //////////////////////////////////
        //      ResultGamenKomokuInfo = getGamenKomokuInfoExtract(strSubFrameId);

        // キャッシュに格納している情報を取得する
        caFormKokyakuInfo = (Form<KokyakuInfo>) Cache.get(keyKokyakuInfo);
        Map<String, List<NewServiceInfo>> camapServiceInfo = (Map<String, List<NewServiceInfo>>) Cache.get(keyServiceInfo);
        List<NewServiceMaster> caListServiceMaster = (List<NewServiceMaster>) Cache.get(keyServiceMaster);

        // キャッシュのクリア
        //Cache.remove(keywizardInfo);
        Cache.remove(strSettingOptionKey);

        sbResult.append(
                views.html.subframes.sf_kokyaku_info_diff.render(
                        caFormKokyakuInfo,
                        resultApplyInfo.getValue(),
                        caFormWizardInputInfo,
                        camapServiceInfo,
                        mapApplyServiceInfo.getValue(),
                        caListServiceMaster
                        ));

        // Viewに返す
        return ok(new Html(sbResult));

    }

    /**
     * 対応履歴情報登録処理
     * <p>
     * 対応内容を登録後、再取得した情報を返却する
     * </p>
     * @return 対応履歴情報
     *         画面項目情報
     * @author 江川
     * @version 0.4 2014/04/15 Step4取込
     */
    public static Result tourokuTaiouRirekiInfo() {

        // ViewからのForm情報を取得
        Map<String, String[]> formTaiouNaiyou = request().body().asFormUrlEncoded();
        String strTaiouNaiyou = formTaiouNaiyou.get("taiouNaiyou")[0];

        // 変数定義
        String strSubFrameId = "win1    ";

        // 変数定義(businessからの返却値)
        ResultCode success = ResultCode.Success;
        BusinessResult<List<KokyakuTaiouRirekiInfo>> resultTaiouRirekiInfo;
        BusinessResult<List<GamenItemControlInfo>> resultGamenKomokuInfo;

        // キャッシュのKey定義
        //      String keyOpId = "opeid1";
        //      String keyKokyakuId = "kokyakuid1";

        // セッションからUUIDを取得する
        String keyOpId = ctx().session().get("keyOpId").toString();
        String keyKokyakuId = ctx().session().get("keyKokyakuId").toString();

        // キャッシュに格納している情報を取得する
//        String caStrOperatorId = (String) Cache.get(keyOpId);
        OperatorMaster caOperatorMaster = (OperatorMaster) Cache.get(keyOpId);
        String caStrKokyakuId = (String) Cache.get(keyKokyakuId);

        //////////////////////////////////
        /* 対応履歴情報登録処理         */
        //////////////////////////////////
        resultTaiouRirekiInfo = taiouRirekiInfoReg(caStrKokyakuId, caOperatorMaster.operatorId, strTaiouNaiyou);
        if (resultTaiouRirekiInfo.getResultCode() != success)
        {
            // エラー画面へ遷移する
            return ok(views.html.subframes.sf_error.render(resultTaiouRirekiInfo.getMessage()));
        }

        //////////////////////////////////
        /* 画面項目情報取得処理         */
        //////////////////////////////////
        resultGamenKomokuInfo = getGamenKomokuInfoExtract(strSubFrameId);

        // Viewに返す
        return ok(views.html.subframes.sf_taiou_rireki.render(
                resultTaiouRirekiInfo.getValue(),
                resultGamenKomokuInfo.getValue()));
    }
    
    /**
     * オプション登録処理（登録フレーム表示）
     * <p>
     * オプション登録フレームの初期表示を行う
     * </p>
     * @return なし
     * @author 那須　智貴
     * @version 0.1 2014/07/28 新規作成
     */

    public static Result tourokuOption() {
        
        // 変数定義
        StringBuilder sbResult = new StringBuilder();
        Form<ServiceInputInfo> formServiceInputInfo = new Form<ServiceInputInfo>(ServiceInputInfo.class);
        // 返却するHTML作成
        sbResult.append(views.html.subframes.sf_option_tetsuzuki.render(formServiceInputInfo));
        // Viewに返す
        return ok(new Html(sbResult));

    }
    
    /**
     * オプション確認処理
     * <p>
     * オプション完了フレームを閉じる
     * </p>
     * @return なし
     * @author 那須　智貴
     * @version 0.1 2014/07/28 新規作成
     */
    @RequireForm({ "SSCR0004.serviceInputContents" })
    public static Result refOption() {
        
        // 変数定義
        StringBuilder sbResult = new StringBuilder();
        //Form<ServiceInputInfo> formServiceInputInfo = new Form<ServiceInputInfo>(ServiceInputInfo.class);
        
        @SuppressWarnings("unchecked")
        Form<ServiceInputInfo> formServiceInputInfo =
                (Form<ServiceInputInfo>) ctx().args.get("SSCR0004.serviceInputContents");
        
        // UUIDの発行
        Context ctx = Context.current();
        String strSettingOptionKey = ResourceManager.getUUID();

        // セッションにUUIDを格納する
        Session session = ctx.session();
        session.put("settingOptionKey", strSettingOptionKey);

        // ウィザード情報をキャッシュに格納する
        Cache.set(strSettingOptionKey, formServiceInputInfo);
        
        // Formに変換
        //formServiceInputInfo = formServiceInputInfo.fill(formServiceInputInfo);
        
        // 返却するHTML作成
        sbResult.append(views.html.subframes.sf_option_tetsuzuki.render(formServiceInputInfo));
        
        // Viewに返す
        return ok(new Html(sbResult));

    }
    
    /**
     * オプション完了処理（完了フレームを閉じる）
     * <p>
     * オプション完了フレームを閉じる
     * </p>
     * @return なし
     * @author 那須　智貴
     * @version 0.1 2014/07/28 新規作成
     */

    public static Result kanryouOption() {
        
        // 変数定義
        StringBuilder sbResult = new StringBuilder();
        String strSettingOptionKey = ctx().session().get("settingOptionKey").toString();

        @SuppressWarnings("unchecked")
        Form<ServiceInputInfo> formServiceInputInfo = (Form<ServiceInputInfo>) Cache.get(strSettingOptionKey);
        

        // 返却するHTML作成
        sbResult.append(views.html.subframes.sf_option_tetsuzuki.render(formServiceInputInfo));
        // Viewに返す
        return ok(new Html(sbResult));

    }
    

//    /**
//     * オペレータログイン情報の取得処理
//     * <p>
//     * オペレータログイン情報を取得し返却する
//     * </p>
//     * @param オペレータユーザID
//     * @param オペレータパスワード
//     * @return オペレータ情報
//     * @author 江川
//     * @version 0.2 2014/03/05 新規作成
//     */
//    private static BusinessResult<Form<OperatorMaster>> getOperatorInfoExtract(
//            String strOperatorId, String strOperatorPwd) {
//
//        BusinessResult<Form<OperatorMaster>> resultOperation = new BusinessResult<Form<OperatorMaster>>();
//
//        try {
//            resultOperation = SelectUserKengen.selectOperatorMaster(strOperatorId, strOperatorPwd);
//
//        } catch (Exception e) {
//            // TODO 自動生成された catch ブロック
//            e.printStackTrace();
//        }
//
//        return resultOperation;
//    }

    /**
     * 顧客情報の取得処理
     * <p>
     * 顧客情報を取得し返却する
     * （顧客情報、契約者情報、サービス情報、料金情報、サービスマスタ情報）
     * </p>
     * @param 顧客ID
     * @return 顧客情報
     * @author 江川
     * @version 0.2 2014/03/05 新規作成
     */
    private static BusinessResult<Form<KokyakuInfo>> getKokyakuInfoExtract(
            String strKokyakuId) {

        BusinessResult<Form<KokyakuInfo>> resultKokyakuInfo = new BusinessResult<Form<KokyakuInfo>>();

        try {
            resultKokyakuInfo = SelectKokyakuInfo.selectKokyakuInfo(strKokyakuId);

        } catch (Exception e) {
            // TODO 自動生成され catch ブロック
            e.printStackTrace();
        }

        return resultKokyakuInfo;
    }

    /**
     * 申込情報の取得処理
     * <p>
     * 申込情報を取得し返却する
     * （申込情報、申込顧客情報、申込契約者情報、申込サービス情報、申込料金情報）
     * </p>
     * @param 顧客ID
     * @param 契約ID
     * @return 申込情報
     * @author 江川
     * @version 0.4 2014/04/22 新規作成
     */
    private static BusinessResult<Form<ApplyInfo>> getApplyInfoExtract(
            String strKokyakuId, String strKeiyakuId, String strKeyApplyXML) {

        BusinessResult<Form<ApplyInfo>> resultApplyInfo = new BusinessResult<Form<ApplyInfo>>();
        BusinessResult<List<ApplyXml>> searchResult = new BusinessResult<List<ApplyXml>>();
        ApplyInfoXml applyInfoXml = new ApplyInfoXml();
        Form<ApplyInfo> formApplyInfo = new Form<ApplyInfo>(ApplyInfo.class);
        String strApplyXML = "";
        boolean boGetApplyXML = false;
        
        try {
            //resultApplyInfo = SelectApplyKokyakuInfo.selectApplyKokyakuInfo(strKokyakuId, strKeiyakuId);
            
            // 手続き進捗キー情報取得
            BusinessResult<Integer> iProgressResult =
                    SelectProgressQueue.selectProgressQueueCountByKokyakuInfo(strKokyakuId, strKeiyakuId);
            
            if(iProgressResult.getResultCode() == ResultCode.Success){
                
                if(iProgressResult.getValue() > 0){
                    boGetApplyXML = true;
                }else{
                    // 申込受付キー取得
                    BusinessResult<Integer> iApplyUketsukeKeyCountResult =
                            SelectApplyUketsuke.selectApplyUketsukeKeyCountByKokyakuInfo(strKokyakuId, strKeiyakuId);
                    if(iApplyUketsukeKeyCountResult.getResultCode() == ResultCode.Success){
                        if(iApplyUketsukeKeyCountResult.getValue() > 0){
                            boGetApplyXML = true;
                        }
                    }else{
                        throw new Exception();
                    }
                }
                if(boGetApplyXML){
                    // 申込XML取得
                    searchResult = SelectApplyXML.selectApplyXML(strKokyakuId, strKeiyakuId);
                    
                    // 申込情報XMLをオブジェクトに変換
                    if(searchResult.getValue().size() > 0){
                        // 申込XMLをキャッシュに詰めておく
                        Cache.set(strKeyApplyXML, searchResult.getValue());
                        
                        if(searchResult.getValue().get(0).applyXml != null){
                            strApplyXML = searchResult.getValue().get(0).applyXml;
                            XStream xs = new XStream(new DomDriver());
                            applyInfoXml = (ApplyInfoXml) xs.fromXML(strApplyXML);
                            formApplyInfo = formApplyInfo.fill(applyInfoXml.applyContents.applyInfo);
                            resultApplyInfo.setValue(formApplyInfo);
                        }
                    }
                }
            }else{
                throw new Exception();
            }

            resultApplyInfo.setResultCode(ResultCode.Success);

        } catch (Exception e) {
            // TODO 自動生成され catch ブロック
            e.printStackTrace();
        }

        return resultApplyInfo;
    }

    /**
     * 画面項目情報の取得処理
     * <p>
     * 画面項目情報を取得し返却する
     * </p>
     * @param サブフレームID
     * @return 画面項目情報
     * @author 江川
     * @version 0.2 2014/03/05 新規作成
     */
    private static BusinessResult<List<GamenItemControlInfo>> getGamenKomokuInfoExtract(
            String strSubFrameId) {

        BusinessResult<List<GamenItemControlInfo>> resultGamenKomokuInfo =
                new BusinessResult<List<GamenItemControlInfo>>();

        try {
            resultGamenKomokuInfo = SelectGamenKomoku.selectGamenControlInfo(strSubFrameId);

        } catch (Exception e) {
            // TODO 自動生成された catch ブロック
            e.printStackTrace();
        }

        return resultGamenKomokuInfo;
    }

    /**
     * 対応履歴情報の取得処理
     * <p>
     * 対応履歴情報を取得し返却する
     * </p>
     * @param 顧客ID
     * @return 対応履歴情報
     * @author 江川
     * @version 0.2 2014/03/05 新規作成
     */
    private static BusinessResult<List<KokyakuTaiouRirekiInfo>> getTaiouRirekiInfoExtract(
            String strKokyakuId) {

        BusinessResult<List<KokyakuTaiouRirekiInfo>> resultTaiouRirekiInfo =
                new BusinessResult<List<KokyakuTaiouRirekiInfo>>();

        try {
            resultTaiouRirekiInfo = SelectTaiouRireki.selectKokyakuTaiouRireki(strKokyakuId);

        } catch (Exception e) {
            // TODO 自動生成された catch ブロック
            e.printStackTrace();
        }

        return resultTaiouRirekiInfo;
    }

    /**
     * マイページ制御情報の取得処理
     * <p>
     * マイページ制御情報を取得し返却する
     * </p>
     * @param オペレータユーザID
     * @param 機能ID
     * @return マイページ制御情報
     * @author 江川
     * @version 0.2 2014/03/05 新規作成
     */
    private static BusinessResult<List<MypageControlInfo>> getMypageControlInfoExtract(
            String strOperatorId, String strfunctionCd) {

        BusinessResult<List<MypageControlInfo>> resultMypageControlInfo =
                new BusinessResult<List<MypageControlInfo>>();

        try {
            resultMypageControlInfo = SelectMypageControlInfo.selectMypageControlInfo(strOperatorId, strfunctionCd);

        } catch (Exception e) {
            // TODO 自動生成された catch ブロック
            e.printStackTrace();
        }

        return resultMypageControlInfo;
    }

    /**
     * 申込顧客情報登録処理
     * <p>
     * 申込顧客情報を登録後、再取得した情報を返却する
     * （申込情報、申込顧客情報、申込契約者情報、申込サービス情報）
     * </p>
     * @param ウィザード情報
     * @param 顧客情報
     * @param オペレータユーザID
     * @param 申込情報
     * @param オプション情報
     * @return 申込情報
     * @author 江川
     * @version 0.4 2014/04/15 新規作成
     */
    private static BusinessResult<Form<ApplyInfo>> makeApplyHistory(
            Form<WizardInputInfo> formWizardInputInfo,
            Form<KokyakuInfo> formKokyakuInfo,
            String strOperatorId,
            List<ApplyXml> lstApplyXml,
            Map<String, String[]> mapOption,
            String strKokyakuId,
            String strKeiyakuId,
            Form<ServiceInputInfo> formServiceInputInfo) {

        BusinessResult<Form<ApplyInfo>> result = new BusinessResult<Form<ApplyInfo>>();
        BusinessResult<List<ApplyXml>> selectResult = new BusinessResult<List<ApplyXml>>();
        ApplyInfoXml applyInfoXml = new ApplyInfoXml();
        Form<ApplyInfo> formApplyInfo = new Form<ApplyInfo>(ApplyInfo.class);
        
        try {
            
            // ブランドコードを取得
            String strBrandCd = ctx().session().get("brand");
            
            // 申込情報を登録する
            BusinessResult<ApplyInfo> resultUpd = InsertApplyKokyakuInfo.updateApplyInfoXML(
                    formWizardInputInfo.get(),
                    formKokyakuInfo.get(),
                    strOperatorId,
                    lstApplyXml,
                    mapOption,
                    strBrandCd,
                    strKokyakuId,
                    strKeiyakuId,
                    formServiceInputInfo);
            
            if(resultUpd.getResultCode() == ResultCode.Success){
                
                // 申込XMLを再取得する
                selectResult = SelectApplyXML.selectApplyXML(
                        strKokyakuId,
                        strKeiyakuId);
                
                // 申込履歴の登録が正常に登録した場合
                if(selectResult.getResultCode() == ResultCode.Success){
                    
                    // 申込情報XMLを取得
                    if(selectResult.getValue().size() > 0){
                        for(int i=0; i<selectResult.getValue().size(); i++){
                            // 申込情報XMLをオブジェクトに変換
                            String strApplyXML = selectResult.getValue().get(i).applyXml;
                            XStream xs = new XStream(new DomDriver());
                            applyInfoXml = (ApplyInfoXml) xs.fromXML(strApplyXML);
                            formApplyInfo = formApplyInfo.fill(applyInfoXml.applyContents.applyInfo);
                        }
                    }
                    
                    // 申込受付キーを登録
                    InsertApplyUketsuke.insertApplyUketsukeKey(applyInfoXml.applyContents.applyInfo, strBrandCd, strOperatorId, strKokyakuId, strKeiyakuId);
                }
                
            }else{
                result.setResultCode(selectResult.getResultCode());
                result.setValue(formApplyInfo);
            }

        } catch (Exception e) {
            // TODO 自動生成され catch ブロック
            e.printStackTrace();
        }
        
        result.setResultCode(selectResult.getResultCode());
        result.setValue(formApplyInfo);
        
        return result;
    }

    /**
     * 対応履歴情報登録処理
     * <p>
     * 対応内容を登録後、再取得した情報を返却する
     * </p>
     * @param オペレータユーザID
     * @param 顧客ID
     * @param 対応履歴内容
     * @return 対応履歴情報
     * @author 江川
     * @version 0.4 2014/04/15 新規作成
     */
    private static BusinessResult<List<KokyakuTaiouRirekiInfo>> taiouRirekiInfoReg(
            String strOperatorId, String strKokyakuId, String strTaiouNaiyou) {

        BusinessResult<List<KokyakuTaiouRirekiInfo>> resultTaiouRirekiInfo =
                new BusinessResult<List<KokyakuTaiouRirekiInfo>>();

        try {
            resultTaiouRirekiInfo = InsertTaiouRireki.insertKokyakuTaiouRireki(
                    strOperatorId, strKokyakuId, strTaiouNaiyou);

        } catch (Exception e) {
            // TODO 自動生成された catch ブロック
            e.printStackTrace();
        }

        return resultTaiouRirekiInfo;
    }
    
    /**
     * サービスマスタ全取得処理
     * <p>
     * サービスマスタを取得する。
     * </p>
     * @param 顧客ID
     * @param 契約ID
     * @return 申込情報
     * @author 江川
     * @version 0.4 2014/04/22 新規作成
     */
    private static BusinessResult<List<NewServiceMaster>> getServiceMasterExtract() {
        
        // インスタンス生成
        BusinessResult<List<NewServiceMaster>> result = new BusinessResult<List<NewServiceMaster>>();

        try {
            result = SelectServiceMaster.all();

        } catch (Exception e) {
            // TODO 自動生成され catch ブロック
            e.printStackTrace();
        }

        return result;
    }

}
