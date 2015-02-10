package models.entities;

import javax.persistence.Column;
import javax.persistence.Embeddable;

/**
 * 手続き種別マスタ複合主キークラス
 * <p>
 * 手続き種別マスタの複合主キーを形成する。
 * </p>
 * @author 甲斐
 * @version 0.1　2014/07/07　新規作成
 */
@Embeddable
public class ProcedureTypeMasterPK {

	/** サービスコード */
	@Column(nullable = false, columnDefinition = "char(4)")
	public String serviceCd;

	/** 業務詳細 */
	@Column(nullable = false, columnDefinition = "char(1)")
	public String businessDetail;

	/** 申込内容詳細 */
	@Column(nullable = false, columnDefinition = "char(1)")
	public String applyDetail;

	/**
	 * コンストラクタ
	 * <p>
	 * コンストラクタ
	 * </p>
	 * @author 甲斐
	 */
	public ProcedureTypeMasterPK() {
	}

	/**
	 * コンストラクタ（複合主キー格納）
	 * <p>
	 * クラスが保持する複合主キーに値を格納するコンストラクタ。
	 * </p>
	 * @param serviceCd サービスコード
	 * @param businessDetail 業務詳細
	 * @param applyDetail 申込内容詳細
	 * @author 甲斐
	 */
	public ProcedureTypeMasterPK(String serviceCd, String businessDetail, String applyDetail) {
		this.serviceCd = serviceCd;
		this.businessDetail = businessDetail;
		this.applyDetail = applyDetail;
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
		if (other instanceof ProcedureTypeMasterPK) {
			final ProcedureTypeMasterPK otherSwfTeigiMasterPK = (ProcedureTypeMasterPK) other;
			final boolean areEqual = (otherSwfTeigiMasterPK.serviceCd.equals(this.serviceCd) &&
					otherSwfTeigiMasterPK.businessDetail.equals(this.businessDetail) &&
					otherSwfTeigiMasterPK.applyDetail.equals(this.applyDetail));

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
		return (this.serviceCd.hashCode() + this.businessDetail.hashCode() + this.applyDetail.hashCode());
	}

}
