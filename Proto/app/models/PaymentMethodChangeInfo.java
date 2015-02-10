package models;

public class PaymentMethodChangeInfo {
	// 要求区分
	public int requestType;
	// 事業区分コード
	public String businessType;

	// ブランドコード
	public String brandType;

	// 契約ID
	public String contractID;

	// 請求ID
	public String requestID;

	// 支払方法種別コード
	public String payMethodType;

	// 申込日
	public String applyDate;

	// 支払方法適用開始月
	public String validStartMonth;

	// 顧客ID
	public String customerID;

	// ユーザＩＤ
	public String userID;

}
