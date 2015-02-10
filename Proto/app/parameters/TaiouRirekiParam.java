package parameters;

/**
 * 対応履歴パラメータクラス.
 * <p>
 * DB部品から取得した対応履歴データを格納する。
 * </p>
 * @author 甲斐正之
 * @version 0.1　2014/02/28　新規作成
 */
public class TaiouRirekiParam {

	/** 顧客対応ＩＤ */
	public String taiouId;

	/** 顧客ＩＤ */
	public String kokyakuId;

	/** 連絡者氏名 */
	public String renrakushaName;

	/** 電話番号 */
	public String telNo;

	/** 対応日時 */
	public String taiouNichiji;

	/** 対応オペレータＩＤ */
	public String taiouOperatorId;

	/** 対応オペレータ氏名 */
	public String operatorName;

	/** 対応オペレータ所属 */
	public String operatorDepartment;

	/** 関連顧客対応ＩＤ */
	public String kanrenClientTaioId;

	/** 問い合わせ内容 */
	public String toiawaseNaiyou;

	/** 問い合わせ種別 */
	public String toiawaseSyubetsu;

	/** 対応内容 */
	public String taiouNaiyou;

	/** 対応結果 */
	public String taiouKekka;

	/** 通知内容 */
	public String tsuuchiNaiyou;

	/** 履歴登録年月日 */
	public String rirekiTourokuNengetsubi;

	/** 履歴登録オペレータＩＤ */
	public String rirekiTourokuOperatorId;

}
