package controllers;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

import models.PaymentMethodChangeInfo;
import models.entities.KddiBillAppHistory;
import models.entities.PayMethod;
import play.data.Form;
import play.mvc.Controller;
import play.mvc.Result;
import common.BusinessResult;
import constants.DbDataValue.KDDIApplyStatusCd;
import constants.DbDataValue.PayMethodTypesCd;

public class PaymentMethodChange extends Controller {

	/**
	 * Display a blank form.
	 */
	public static Result blank() {
		Form<PaymentMethodChangeInfo> paymentMethodChangeForm = new Form<PaymentMethodChangeInfo>(
				PaymentMethodChangeInfo.class);
		return ok(views.html.PaymentMethodChange
				.render(paymentMethodChangeForm));
	}

	/**
	 * Handle the form submission.
	 */
	public static Result submit() {
		Form<PaymentMethodChangeInfo> filledForm = new Form<PaymentMethodChangeInfo>(
				PaymentMethodChangeInfo.class).bindFromRequest();

		// // Check accept conditions
		// if(!"true".equals(filledForm.field("accept").value())) {
		// filledForm.reject("accept",
		// "You must accept the terms and conditions");
		// }
		//
		PaymentMethodChangeInfo created = filledForm.get();
		if (created.requestType == 0) {
			loadNewPayMethod(created);
		} else {
			updatePayMethod(created);
		}
		if (!filledForm.hasErrors()) {
			return ok(views.html.summary.render(created));
		} else {
			return redirect("/");
		}
	}

	/**
	 * 支払方法の登録処理
	 * <p>
	 * 支払方法を登録し返却する
	 * </p>
	 *
	 * @return BusinessResult<PayMethod>
	 * @author 丁玉磊
	 * @version 2014/11/10 新規作成
	 */
	private static BusinessResult<PayMethod> loadNewPayMethod(
			PaymentMethodChangeInfo info) {

		BusinessResult<PayMethod> resultPayMethodInfo = new BusinessResult<PayMethod>();

		PayMethod payMethod = new PayMethod();
		KddiBillAppHistory kddiBillAppHistory =new KddiBillAppHistory();

		Date sysdate = new Date();

	    // 「支払方法」
		//payMethod.payMethodId =  "1" ;                   /** 支払方法ID */
		payMethod.billingId = info.requestID;            /** 請求ID */
//		payMethod.payMethodCode ="1";                  /** 支払方法コード */
		payMethod.applyDate =info.applyDate;          /** 申込日 */
	//	payMethod.payMethodStartMonth = info.validStartMonth;        /** 支払方法適用開始月  */
		payMethod.userId = info.userID;               /** ユーザID */
		payMethod.customerNo = info.customerID;       /** 顧客番号 */
	//	payMethod.applicationStartDate = info.validStartMonth;  /** 適用開始日 */
	//	payMethod.applicationEndDate = "20241111";     /** 適用終了日 */
		payMethod.createUser = info.userID ;
		payMethod.lastUpdateUser = info.userID ;

		// 「KDDI請求申請履歴」

	//	kddiBillAppHistory.kddiBillingApplyHistoryId ="1";
		kddiBillAppHistory.billingId = info.requestID;
	//	kddiBillAppHistory.kddiBillingStatusCd ="01" ;  /** KDDI請求ステータスコード */
	//	kddiBillAppHistory.payMethodCd = "1";       /** 支払方法コード */
		kddiBillAppHistory.applyDate = info.applyDate;  /** 申込日 */
	//	kddiBillAppHistory.payMethodStartMonth = info.validStartMonth;        /** 支払方法適用開始月  */
		kddiBillAppHistory.userId = info.userID ;
	//	kddiBillAppHistory.applicationStartDate = info.validStartMonth;       /** 適用開始日 */

		kddiBillAppHistory.createUser = info.userID ;
		kddiBillAppHistory.lastUpdateUser = info.userID ;


		// TODO:
		//KDDI請求のばあい「支払方法」および「KDDI請求申請履歴」をDB登録する。
		if (info.payMethodType=="KDDI請求" ) {
			payMethod.applicationStartDate = info.validStartMonth + "01"; //適用開始月 +01   ④
			payMethod.payMethodCode = String.valueOf(PayMethodTypesCd.ITEM_4);    //支払方法種別コードに「CVS入金(月次CVS)」を設定

			try {
				payMethod.save();
				resultPayMethodInfo.setValue(payMethod);
			} catch (Exception e) {
				// TODO 自動生成された catch ブロック
				e.printStackTrace();
			}

			kddiBillAppHistory.payMethodCd = String.valueOf(PayMethodTypesCd.ITEM_10);       /** 支払方法コード */
			kddiBillAppHistory.kddiBillingStatusCd = String.valueOf(KDDIApplyStatusCd.ITEM_1); /*　KDDI請求ステータスコードに「手続中」を設定 */

			try {
				kddiBillAppHistory.save();
				resultPayMethodInfo.setValue(payMethod);
			} catch (Exception e) {
				// TODO 自動生成された catch ブロック
				e.printStackTrace();
			}

		} else {    //KDDI請求以外の場合「支払方法」のみ登録

			payMethod.applicationStartDate = info.validStartMonth + "01"; //適用開始月 +01  ③

			try {
				payMethod.save();
				resultPayMethodInfo.setValue(payMethod);
			} catch (Exception e) {
				// TODO 自動生成された catch ブロック
				e.printStackTrace();
			}
		}

		return resultPayMethodInfo;
	}

	/**
	 * 支払方法の更新処理
	 * <p>
	 * 支払方法を更新し返却する
	 * </p>
	 *
	 * @return BusinessPesult<PayMethod>
	 * @author 丁玉磊
	 * @version 2014/11/10 新規作成
	 */
	private static BusinessResult<PayMethod> updatePayMethod(
			PaymentMethodChangeInfo info) {

		BusinessResult<PayMethod> resultPayMethodInfo = new BusinessResult<PayMethod>();

		PayMethod payMethod = new PayMethod();

		if (info.payMethodType != "KDDI請求"){
			List<PayMethod> payMethodList = PayMethod.find.where().eq("payMethodId", "").findList();
			if (payMethodList != null){
				payMethod = payMethodList.get(0);
			} else {
				// 作成者ID
				payMethod.createUser = info.userID ;
			}
		}

		KddiBillAppHistory kddiBillAppHistory = new KddiBillAppHistory();

		// TODO:
		// 「支払方法」
		payMethod.billingId = info.requestID;		/** 請求ID */
		// 支払方法コード
		if (info.payMethodType != "KDDI請求"){
			payMethod.payMethodCode = String.valueOf(PayMethodTypesCd.ITEM_4);
		} else {
			payMethod.payMethodCode = String.valueOf(PayMethodTypesCd.ITEM_10);
		}
		payMethod.applyDate = info.applyDate;		/** 申込日 */
		payMethod.payMethodStartMonth = info.validStartMonth;		/** 支払方法適用開始月 */
		payMethod.userId = info.userID;		/** ユーザID */
		payMethod.lastUpdateUser = info.userID ;		/** 更新者ID */

		// 「KDDI請求申請履歴」
		kddiBillAppHistory.billingId = info.requestID;		/** 請求ID */
		// 支払方法コード
		if (info.payMethodType != "KDDI請求"){
			kddiBillAppHistory.payMethodCd = String.valueOf(PayMethodTypesCd.ITEM_4);
		} else {
			kddiBillAppHistory.payMethodCd = String.valueOf(PayMethodTypesCd.ITEM_10);
		}
		kddiBillAppHistory.applyDate = info.applyDate;		/** 申込日 */
		kddiBillAppHistory.payMethodStartMonth = info.validStartMonth;		/** 支払方法適用開始月 */
		kddiBillAppHistory.userId = info.userID;		/** ユーザID */
		kddiBillAppHistory.createUser = info.userID ;		/** 作成者ID */
		kddiBillAppHistory.lastUpdateUser = info.userID ;		/** 更新者ID */

		if (info.payMethodType != "KDDI請求"){
			if (payMethod.payMethodCode != "KDDI請求"){
				// 2-2-1. 支払方法種別コードが「KDDI請求」以外かつ、
				// 変更前の「支払方法」の支払方法種別コードが「KDDI請求」以外の場合、
				// 適用開始日に表4-1-2 入力項目の支払方法適用開始月+「01」(日)を設定
				payMethod.applicationStartDate = info.validStartMonth + "01";		/** 適用開始日 */
				// 適用終了日
				int yyyy = Integer.parseInt(info.validStartMonth.substring(0,4));
			    int MM = Integer.parseInt(info.validStartMonth.substring(5,7));
			    int dd = 1;
			    Calendar cal = Calendar.getInstance();
			    cal.set(yyyy,MM-1,dd);
			    int last = cal.getActualMaximum(Calendar.DATE);
			    payMethod.applicationEndDate = info.validStartMonth + String.valueOf(last);

			    try {
					payMethod.save();
					resultPayMethodInfo.setValue(payMethod);
				} catch (Exception e) {
					// TODO 自動生成された catch ブロック
					e.printStackTrace();
				}
			} else {
				// 2-2-2. 支払方法種別コードが「KDDI請求」以外かつ、
				// 変更前の「支払方法」の支払方法種別コードが「KDDI請求」の場合 、
				// 支払方法種別コードに「CVS入金(月次CVS)」を設定
				kddiBillAppHistory.payMethodCd = String.valueOf(PayMethodTypesCd.ITEM_4);		/** 支払方法コード */
				// KDDI請求ステータスコードに「切替待ち」を設定
				kddiBillAppHistory.kddiBillingStatusCd = KDDIApplyStatusCd.ITEM_0;		/** KDDI請求ステータスコード */

				try {
					kddiBillAppHistory.save();
					resultPayMethodInfo.setValue(payMethod);
				} catch (Exception e) {
					// TODO 自動生成された catch ブロック
					e.printStackTrace();
				}
			}
		} else {
			// 2-2-3. 支払方法種別コードが「KDDI請求」の場合
			// 適用開始日に表4-1-2 入力項目の支払方法適用開始月+「01」(日)を設定
			payMethod.applicationStartDate = info.validStartMonth + "01";		/** 適用開始日 */
			// 支払方法コード 支払方法種別コードに「CVS入金(月次CVS)」を設定
			payMethod.payMethodCode = String.valueOf(PayMethodTypesCd.ITEM_4);		/** 支払方法コード */

			try {
				payMethod.save();
				resultPayMethodInfo.setValue(payMethod);
			} catch (Exception e) {
				// TODO 自動生成された catch ブロック
				e.printStackTrace();
			}

			// 支払方法コード 支払方法種別コードに「KDDI請求」を設定
			kddiBillAppHistory.payMethodCd = String.valueOf(PayMethodTypesCd.ITEM_10);		/** 支払方法コード */
			// KDDI請求ステータスコードに「手続中」を設定
			kddiBillAppHistory.kddiBillingStatusCd = KDDIApplyStatusCd.ITEM_1;		/** KDDI請求ステータスコード */

			try {
				kddiBillAppHistory.save();
				resultPayMethodInfo.setValue(payMethod);
			} catch (Exception e) {
				// TODO 自動生成された catch ブロック
				e.printStackTrace();
			}
		}

		return resultPayMethodInfo;
	}

}
