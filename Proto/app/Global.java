import java.lang.reflect.Method;
import java.util.Map;

import play.GlobalSettings;
import play.libs.F;
import play.libs.F.Promise;
import play.mvc.Action;
import play.mvc.Http;
import play.mvc.Http.Request;
import play.mvc.SimpleResult;

/**
 * グローバル処理クラス
 *
 */
public class Global extends GlobalSettings {

//	// リクエストすべてに対するフィルタ処理
//	@Override
//	public Action<?> onRequest(Request request, Method actionMethod) {
//		System.out.println("before each request..." + request.toString());
//		// 無名のアクションを必ず合成する。
//		return new Action.Simple() {
//			public Promise<SimpleResult> call(Http.Context ctx)
//					throws Throwable {
//
//				System.out.println("引数：" + ctx.args);
//				System.out.println("アクション："
//						+ ctx.args.get("ROUTE_ACTION_METHOD"));
//				System.out.println("ユニークＩＤ：" + ctx.session().get("uid"));
//
//				if (ctx.args.get("ROUTE_ACTION_METHOD").equals("makeTransaction")
//						|| ctx.args.get("ROUTE_ACTION_METHOD").equals("makeProgressQueue")
//						|| ctx.args.get("ROUTE_ACTION_METHOD").equals("startWorkFlow")
//						|| ctx.args.get("ROUTE_ACTION_METHOD").equals("soInput")
//						|| ctx.args.get("ROUTE_ACTION_METHOD").equals("sendSOInput")
//						|| ctx.args.get("ROUTE_ACTION_METHOD").equals("receiveSOOutput")
//						|| ctx.args.get("ROUTE_ACTION_METHOD").equals("reflectSOOutput")
//						|| ctx.args.get("ROUTE_ACTION_METHOD").equals("registParmanent")
//						|| ctx.args.get("ROUTE_ACTION_METHOD").equals("nextStatus")
//						|| ctx.args.get("ROUTE_ACTION_METHOD").equals("makeApplyRireki")) {
//					return delegate.call(ctx);
//				}
//
//				if (!"index".equals(ctx.args.get("ROUTE_ACTION_METHOD"))
//						&& ctx.session().get("uid") == null) {
//					return F.Promise
//							.pure((SimpleResult) unauthorized("unauthorized"));
//				}
//				// 仮実装です。いずれ消します。START>>>
//				Map<String, String[]> reqMap = ctx.request().body().asFormUrlEncoded();
//				if (reqMap != null) {
//					if (reqMap.get("operatorId") != null) {
//						ctx.session().put("operatorId", reqMap.get("operatorId")[0]);
//					}
//				}
//
//				if (ctx.session().get("operatorId") == null) {
//					ctx.session().put("brand", "tcom");
//				} else {
//					if ("opr0000001".equals(ctx.session().get("operatorId"))) {
//						ctx.session().put("brand", "tcom");
//					} else if ("opr0000002".equals(ctx.session().get("operatorId"))) {
//						ctx.session().put("brand", "tnc");
//					} else {
//						ctx.session().put("brand", "tcom");
//					}
//				}
//				// <<< END
//				//ctx.session().put("brand", "tcom");
//				ctx.session().put("uid", "010");
//				return delegate.call(ctx);
//			}
//		};
//	}
}
