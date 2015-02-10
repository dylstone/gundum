package utils.parser;

import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import play.Play;
import play.data.Form;
import play.data.validation.ValidationError;
import play.libs.F;
import play.libs.F.Promise;
import play.mvc.Action;
import play.mvc.Http;
import play.mvc.SimpleResult;
import scala.collection.mutable.StringBuilder;

/**
 *  必須フォームアノテーション処理
 */
public class RequireFormAction extends Action<RequireForm> {
    /**
     * 必須フォームチェックアクション
     * @param ctx コンテキスト
     * @throws Throwable アックションの例外
     * @return アクション結果
     */
    public Promise<SimpleResult> call(Http.Context ctx) throws Throwable {
        // HashMap<String, String> className = new HashMap<String, String>();
        // className.put("service1", "models.Model1");
        // className.put("service2", "models.ValidationCheck");
        Properties classMap = new Properties();
        classMap.load(new InputStreamReader(Play.application()
                .resourceAsStream("classmap"), "UTF-8"));

        // 共通的な情報取得処理
        // デフォルトのパーサですべてのパラメータをマップに格納
        Map<String, String[]> form = ctx.request().body().asFormUrlEncoded();
        // 自前パーサ初期化（staticにすれば不要になるかも）
        CompositeFormParser p = new CompositeFormParser();

        boolean hasError = false;
        // エラーメッセージの準備
        // inputErrors：項目エラーマップ
        // key:対象項目ID・・・formId(=subframeId.partId).komokuId
        // value:表示エラーメッセージ
        Map<String, String> inputErrors = new HashMap<String, String>();
        // globalError：相関エラーメッセージ
        // :表示メッセージ
        // 追加で、タイトルとか警告レベルとか足す。かも？
        String globalError = "";
        List<String> lstError = new ArrayList<String>();

        // 自前パーサでサブフレームID,画面部品ID毎にマップを取り出す。
        for (String formId : configuration.value()) {
            String[] ids = formId.split("\\.");
            String subframeId = ids[0];
            String partId = ids[1];
            // ちゃんとチェックしないとだめだけどな！

            Map<String, String[]> singleForm = p.getForm(form, subframeId,
                    partId);
            // Class<?> clazz = Class.forName(className.get(partId));
            Class<?> clazz = Class.forName(classMap.getProperty(partId));
            Form<?> form1 = Form.form(clazz).bindFromRequest(singleForm);

            if (form1.hasErrors()) {
                for (Map.Entry<String, List<ValidationError>> e : form1
                        .errors().entrySet()) {
                    String key = e.getKey();
                    String v = "";
                    for (ValidationError ve : e.getValue()) {
                        v += utils.Messages.get(key, ve.arguments());
                        v += "：";
                        v += utils.Messages.get(ve.message(), ve.arguments()
                                .toArray());
                        lstError.add(v);
                    }

                    if (key.isEmpty()) {
                        globalError += v;
                        globalError += "\n";
                        System.out.println("相関エラー>");
                        System.out.println("[" + formId + "." + key + "]" + v);
                        hasError = true;
                    } else {
                        String komokuId = formId + "." + key;
                        inputErrors.put(komokuId, v);
                        globalError += v;
                        System.out.println("項目エラー>");
                        System.out.println("[" + formId + "." + key + "]" + v);
                        hasError = true;
                    }
                }

            }
            if (!hasError) {
                ctx.args.put(formId, form1);
            }
        }

        if (hasError) {
            // 返却用処理
            StringBuilder buffer = new StringBuilder();

            // ダイアログ表示
            if (!globalError.isEmpty()) {
                buffer.append(views.html.subframes.sf_error_list.render(lstError));
            }
            // 項目エラー表示
            //buffer.append(views.html.subframes.gse_error.render(inputErrors));
            //return F.Promise.pure((SimpleResult) ok(new Html(buffer)));
            return F.Promise.pure((SimpleResult) ok(views.html.subframes.sf_error_list.render(lstError)));
        }

        return delegate.call(ctx);
    }
}
