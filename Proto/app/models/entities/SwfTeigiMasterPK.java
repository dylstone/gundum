package models.entities;

import javax.persistence.Column;
import javax.persistence.Embeddable;

/**
 * サービスワークフロー定義マスタ複合主キークラス
 * <p>
 * サービスワークフロー定義マスタの複合主キーを形成する。
 * </p>
 * @author 甲斐
 * @version 0.1　2014/07/07　新規作成
 */
@Embeddable
public class SwfTeigiMasterPK {

	/** サービスコード */
	@Column(nullable = false, columnDefinition = "char(4)")
	public String serviceCd;

	/** 業務詳細 */
	@Column(nullable = false, columnDefinition = "char(1)")
	public String businessDetail;

	/** 申込内容詳細 */
	@Column(nullable = false, columnDefinition = "char(1)")
	public String applyDetail;

	/** 手続き種別 */
	@Column(nullable = false, columnDefinition = "varchar2(3)")
	public String procedureType;

	/** 適用開始日 */
	@Column(nullable = true, columnDefinition = "char(8)")
	public String tekiyoStartDate;

	/**
	 * コンストラクタ
	 * <p>
	 * コンストラクタ
	 * </p>
	 * @author 甲斐
	 */
	public SwfTeigiMasterPK() {
	}

	/**
	 * コンストラクタ（複合主キー格納）
	 * <p>
	 * クラスが保持する複合主キーに値を格納するコンストラクタ。
	 * </p>
	 * @param serviceCd サービスコード
	 * @param businessDetail 業務詳細
	 * @param applyDetail 申込内容詳細
	 * @param procedureType 手続き種別
	 * @param tekiyoStartDate 適用開始日
	 * @author 甲斐
	 */
	public SwfTeigiMasterPK(String serviceCd, String businessDetail, String applyDetail,
			String procedureType, String tekiyoStartDate) {
		this.serviceCd = serviceCd;
		this.businessDetail = businessDetail;
		this.applyDetail = applyDetail;
		this.procedureType = procedureType;
		this.tekiyoStartDate = tekiyoStartDate;
	}

	/**
	 * 複合主キー一致確認処理
	 * <p>
	 * 引数の複合主キーとクラスが保持する複合主キーが一致するか確認する。
	 * </p>
	 * @param other 複合主キーのオブジェクト
	 * @return 確認結果（true:一致、false：不一致）
	 * @author 甲斐
	 */
	public boolean equals(Object other) {
		if (other instanceof SwfTeigiMasterPK) {
			final SwfTeigiMasterPK otherSwfTeigiMasterPK = (SwfTeigiMasterPK) other;
			final boolean areEqual = (otherSwfTeigiMasterPK.serviceCd.equals(this.serviceCd) &&
					otherSwfTeigiMasterPK.businessDetail.equals(this.businessDetail) &&
					otherSwfTeigiMasterPK.applyDetail.equals(this.applyDetail) &&
					otherSwfTeigiMasterPK.procedureType.equals(this.procedureType) &&
					otherSwfTeigiMasterPK.tekiyoStartDate.equals(this.tekiyoStartDate));

			return areEqual;
		}

		return false;
	}

	/**
	 * ハッシュコード取得処理
	 * <p>
	 * クラスが保持する複合主キーで生成したハッシュコードを取得する。
	 * </p>
	 * @return ハッシュコード
	 * @author 甲斐
	 */
	public int hashCode() {
		return (this.serviceCd.hashCode() + this.businessDetail.hashCode() + this.applyDetail.hashCode()
				+ this.procedureType.hashCode() + this.tekiyoStartDate.hashCode());
	}

}
