package constants;

/**
 * ＤＢデータ値定数定義クラス
 * <p>
 * ＤＢで使用するデータ値の定数を定義するクラス
 * </p>
 * @author 大平　司
 * @version 0.4　2014/04/11　初版
 */
public class DbDataValue {

	/**
	 * 支払方法種別コード
	 *
	 * @author 丁玉磊
	 *
	 */
	public class PayMethodTypesCd {
		/** 口座自振 */
		public static final int ITEM_0 = 0;
		/** 郵便振替 */
		public static final int ITEM_1 = 1;
		/** クレジット(カード引落) */
		public static final int ITEM_2 = 2;
		/** TOKAI(自振) */
		public static final int ITEM_3 = 3;
		/** CVS入金(月次CVS) */
		public static final int ITEM_4 = 4;
		/** 振込 */
		public static final int ITEM_5 = 5;
		/** 法人請求／A4請求(振込) */
		public static final int ITEM_6 = 6;
		/** 現金 */
		public static final int ITEM_7 = 7;
		/** ペイジー(Pay-easy) */
		public static final int ITEM_8 = 8;
		/** 郵便局払込 */
		public static final int ITEM_9 = 9;
		/** KDDI請求 */
		public static final int ITEM_10 = 10;
		/** 未請求 */
		public static final int ITEM_11 = 11;
	}

	/**
	 * KDDI請求ステータスコード
	 *
	 * @author 丁玉磊
	 *
	 */
	public class KDDIApplyStatusCd {
		/** 切替待ち */
		public static final String ITEM_0 = "00";
		/**KDDI請求ステータスコードに「手続中」*/
		public static final String ITEM_1 = "01";
	}

	/**
	 * ＩＤコード
	 */
	public final class IdCd {
		private IdCd() {
		}

		/** 申込ＩＤ */
		public static final String APPLY = "0001";
		/** 受付ＩＤ */
		public static final String UKETSUKE = "0002";
		/** 申込顧客ＩＤ */
		public static final String APPLY_KOKYAKU = "0003";
		/** 申込契約ＩＤ */
		public static final String APPLY_KEIYAKU = "0004";
		/** 申込サービスＩＤ */
		public static final String APPLY_SERVICE = "0005";
		/** 顧客ＩＤ */
		public static final String KOKYAKU = "0006";
		/** 契約ＩＤ */
		public static final String KEIYAKU = "0007";
		/** サービスＩＤ */
		public static final String SERVICE = "0008";
		/** 課金ＩＤ */
		public static final String KAKIN = "0009";
		/** 対応ＩＤ */
		public static final String TAIOU = "0010";
		/** トランザクションＩＤ */
		public static final String TRANSACTION = "0011";
		/** キューＩＤ */
		public static final String QUEUE = "0012";
		/** ＳＯＩＤ */
		public static final String SO = "0013";
		/** 回線サービスＩＤ */
		public static final String KAISEN = "0014";
		/** メールサービスＩＤ */
		public static final String MAIL = "0015";
		/** セキュリティサービスＩＤ */
		public static final String SECURITY = "0016";
		/** オプションサービスＩＤ */
		public static final String OPTION = "0017";

	}

	/**
	 * 申込状態
	 */
	public final class ApplyStatus {
		private ApplyStatus() {
		}

		/** 申請中 */
		public static final String SHINSEICHUU = "0001";
		/** 申請完了 */
		public static final String SHINSEIKANRYOU = "0002";
		/** 申請中 */
		public static final String CHUUSHICHUU = "0003";
		/** 申請完了 */
		public static final String CHUUSHIKANRYOU = "0004";

	}

	/**
	 * 変更種別
	 */
	public final class HenkouType {
		private HenkouType() {
		}

		/** 追加 */
		public static final String INSERT = "0001";
		/** 変更 */
		public static final String UPDATE = "0002";
		/** 削除 */
		public static final String DELETE = "0003";

	}

	/**
	 * 承認ステータス
	 */
	public final class SyouninStatus {
		private SyouninStatus() {
		}

		/** 承認待ち */
		public static final String SYOUNINMACHI = "0001";
		/** 承認 */
		public static final String SYOUNIN = "0002";
		/** 却下 */
		public static final String KYAKKA = "0003";
		/** 差し戻し */
		public static final String SASHIMODOSHI = "0004";

	}

	/**
	 * ステータス
	 */
	public final class Status {
		private Status() {
		}

		/** 手続き状態 */
		public static final String TETSUDUKICHUU = "0001";
		/** 手続き完了 */
		public static final String TETSUDUKIKANRYOU = "0002";

	}

	/**
	 * 職業コード
	 */
	public final class JobCd {
		private JobCd() {
		}

		/** 職業１ */
		public static final String JOB1 = "0001";
		/** 職業２ */
		public static final String JOB2 = "0002";
		/** 職業３ */
		public static final String JOB3 = "0003";

	}

	/**
	 * 電話種別
	 */
	public final class TelType {
		private TelType() {
		}

		/** 自宅 */
		public static final String HOME = "0001";
		/** 会社 */
		public static final String COMPANY = "0002";
		/** 携帯 */
		public static final String MOBILE = "0003";

	}

	/**
	 * ブランドコード
	 */
	public final class BrandCd {
		private BrandCd() {
		}

		/** ブランド１ */
		public static final String BRAND1 = "brd00001";
		/** ブランド２ */
		public static final String BRAND2 = "brd00002";
		/** ブランド３ */
		public static final String BRAND3 = "brd00003";

	}

	/**
	 * 販売形態
	 */
	public final class HanbaiKeitai {
		private HanbaiKeitai() {
		}

		/** 直販 */
		public static final String DIRECT = "01";
		/** ホールセール */
		public static final String WHOLESALE = "02";
		/** 情報店ホールセール */
		public static final String INFOWHOLESALE = "03";

	}

	/**
	 * 支払方法
	 */
	public final class ShiharaiHouhou {
		private ShiharaiHouhou() {
		}

		/** コンビニ払い */
		public static final String CVS = "コンビニ払い";
		/** クレジット払い */
		public static final String CREDIT = "クレジット払い";

	}

	/**
	 * 請求区分
	 */
	public final class SeikyuuKubun {
		private SeikyuuKubun() {
		}

		/** ＩＳＰ請求 */
		public static final String ISP = "ＩＳＰ請求";
		/** ＫＤＤＩ請求 */
		public static final String KDDI = "ＫＤＤＩ請求";

	}

	/**
	 * サービス分類
	 */
	public final class ServiceBunrui {
		private ServiceBunrui() {
		}

		/** 回線 */
		public static final String KAISEN = "0001";
		/** オプション */
		public static final String OPTION = "0002";
		/** 機器 */
		public static final String KIKI = "0003";
		/** 配送 */
		public static final String HAISOU = "0004";
		/** 通知 */
		public static final String TSUUCHI = "0005";

	}

	/**
	 * 問い合わせ種別
	 */
	public final class ToiawaseSyubetsu {
		private ToiawaseSyubetsu() {
		}

		/** 種別１ */
		public static final String SYUBETSU1 = "0001";
		/** 種別２ */
		public static final String SYUBETSU2 = "0002";
		/** 種別３ */
		public static final String SYUBETSU3 = "0003";

	}

	/**
	 * 処理ステータス
	 */
	public final class ShoriStatus {
		private ShoriStatus() {
		}

		/** 未処理 */
		public static final String MISHORI = "01";
		/** 処理済（手続き進捗キュー：処理済み、ＳＯ指示：処理済み、ＳＯ結果：結果反映済み） */
		public static final String SHORIZUMI = "02";
		/** 受信済み（ＳＯ結果：受信済み） */
		public static final String JUSHINZUMI = "03";

	}

	/**
	 * I/Fキー
	 */
	public final class IFKey {
		private IFKey() {
		}

		/** 初期 */
		public static final String SHOKI = "S1";
		/** ＳＯ指示 */
		public static final String SO_SHIJI = "S2";
		/** ＳＯ受信 */
		public static final String SO_JUSHIN = "S3";
		/** ＳＯ結果反映 */
		public static final String SO_KEKKA_HANEI = "S4";
		/** パーマネント反映 */
		public static final String PARMANENT_HANEI = "S5";

	}

	/**
	 * 主担当機能
	 */
	public final class MainTantouKinou {
		private MainTantouKinou() {
		}

		/** SWF */
		public static final String SWF = "01";
		/** バッチ */
		public static final String BATCH = "02";

	}

	/**
	 * 現在担当機能
	 */
	public final class CurrentTantouKinou {
		private CurrentTantouKinou() {
		}

		/** SWF */
		public static final String SWF = "01";
		/** バッチ */
		public static final String BATCH = "02";
		/** 外部連携 */
		public static final String GAIBU_RENKEI = "03";
	}

	/**
	 * アクティビティＩＤ
	 */
	public final class Activity {
		private Activity() {
		}

		/** 未処理 */
		public static final String MISHORI = "010";
		/** 承認待ち */
		public static final String SHONINMACHI = "020";
		/** トランザクション作成 */
		public static final String TRANSACTION = "030";
		/** サービスワークフロー実行 */
		public static final String SWF = "100";
		/** ＳＯ指示 */
		public static final String SOSHIJI = "500";
		/** ＳＯ結果反映 */
		public static final String SOKEKKA = "600";
		/** パーマネント反映 */
		public static final String PERMANENT = "200";
		/** パーマネント反映済み */
		public static final String END = "999";

	}

}
