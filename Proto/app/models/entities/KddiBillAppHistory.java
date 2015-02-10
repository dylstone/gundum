package models.entities;

import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Version;

import com.avaje.ebean.annotation.CreatedTimestamp;
import com.avaje.ebean.annotation.UpdatedTimestamp;

/**
 * KDDI請求申請履歴ＤＢ部品
 * <p>
 * KDDI請求申請履歴のＯＲマッパー
 * </p>
 *
 * @author
 * @version 0.4　2014/11/10　プロト開発STEP4版
 */
@Entity
public class KddiBillAppHistory extends BaseEntity {

	/** KDDI請求申請履歴ID */
	@Id
	@Column(nullable = false, columnDefinition = "number(10)")
	public String kddiBillingApplyHistoryId;

	/** 請求ID */
	@Column(nullable = false, columnDefinition = "number(10)")
	public String billingId;

	/** KDDI請求ステータスコード */
	@Column(nullable = false, columnDefinition = "char(2)")
	public String kddiBillingStatusCd;

	/** 支払方法コード */
	@Column(nullable = false, columnDefinition = "number(6)")
	public String payMethodCd;

	/** 申込日 */
	@Column(nullable = false, columnDefinition = "char(8)")
	public String applyDate;

	/** 支払方法適用開始月 */
	@Column(nullable = false, columnDefinition = "char(6)")
	public String payMethodStartMonth;

	/** ユーザID */
	@Column(nullable = false, columnDefinition = "varchar2(10)")
	public String userId;

	/** 適用開始日 */
	@Column(nullable = false, columnDefinition = "char(8)")
	public String applicationStartDate;

	/** 適用終了日 */
	@Column(nullable = false, columnDefinition = "char(8)")
	public String applicationEndDate;


	/** 作成者 */
	@Column(nullable = false, columnDefinition = "varchar2(32)", updatable = false)
	public String createUser;

	/** 最終更新者 */
	@Column(nullable = false, columnDefinition = "varchar2(32)")
	public String lastUpdateUser;

	/** 作成日時 */
	@CreatedTimestamp
	@Column(nullable = false, columnDefinition = "timestamp default sysdate", updatable = false)
	public Timestamp createDT;

	/** 最終更新日時 */
	@Version
	@UpdatedTimestamp
	@Column(nullable = false, columnDefinition = "timestamp default sysdate")
	public Timestamp lastUpdateDT;

	/** 論理削除年月日 */
	@Column(nullable = true, columnDefinition = "char(8)")
	public String deleteDate;

	/** オペレータマスタのFinderクラス */
    public static Finder<String, KddiBillAppHistory> find =
    		BaseEntity.getFinder(String.class, KddiBillAppHistory.class);

}
