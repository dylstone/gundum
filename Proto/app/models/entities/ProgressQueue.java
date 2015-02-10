package models.entities;

import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Version;

import com.avaje.ebean.annotation.CreatedTimestamp;
import com.avaje.ebean.annotation.UpdatedTimestamp;

/**
 * 手続き進捗キューＤＢ部品
 * <p>
 * 手続き進捗キューのＯＲマッパー
 * </p>
 * @author 那須智貴
 * @version 0.4　2014/05/12　初版
 */
@Entity
public class ProgressQueue extends BaseEntity {

	/** キューＩＤ */
	@Id
	@Column(nullable = false, columnDefinition = "char(10)")
	public String queueId;

	/** 顧客ＩＤ */
	@Column(nullable = true, columnDefinition = "char(10)")
	public String kokyakuId;

	/** 契約ＩＤ */
	@Column(nullable = true, columnDefinition = "char(10)")
	public String keiyakuId;

	/** サービスＩＤ */
	@Column(nullable = true, columnDefinition = "char(10)")
	public String serviceId;

	/** トランザクションID */
	@Column(nullable = true, columnDefinition = "char(10)")
	public String transactionId;

	/** 主担当機能 */
	@Column(nullable = false, columnDefinition = "char(2)")
	public String mainTantouKinou;

	/** 現在担当機能 */
	@Column(nullable = false, columnDefinition = "char(2)")
	public String currentTantouKinou;

	//	/** ステータス */
	//    @Column(nullable = false, columnDefinition ="char(2)")
	//	public String status;

	/** アクティビティID */
	@Column(nullable = false, columnDefinition = "char(3)")
	public String activityId;

	/** Ｉ／Ｆキー */
	@Column(nullable = true, columnDefinition = "char(10)")
	public String ifKey;

	/** その他キー情報（カラム） */
	@Column(nullable = true, columnDefinition = "varchar2(32)")
	public String otherKeyColumn;

	/** その他キー情報（値） */
	@Column(nullable = true, columnDefinition = "varchar2(100)")
	public String otherKeyData;

	/** 論理削除年月日 */
	@Column(nullable = true, columnDefinition = "char(8)")
	public String deleteDate;

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

	/** 手続き進捗キューのFinderクラス */
	public static Finder<String, ProgressQueue> find =
			BaseEntity.getFinder(String.class, ProgressQueue.class);

}
