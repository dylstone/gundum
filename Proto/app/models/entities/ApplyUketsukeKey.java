package models.entities;

import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Version;

import com.avaje.ebean.annotation.CreatedTimestamp;
import com.avaje.ebean.annotation.UpdatedTimestamp;

/**
 * 申込受付キーＤＢ部品
 * <p>
 * 申込受付キーのＯＲマッパー
 * </p>
 * @author 甲斐
 * @version 0.1　2014/07/09　新規作成
 */
@Entity
public class ApplyUketsukeKey extends BaseEntity {

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

	/** 受付ＩＤ */
	@Column(nullable = true, columnDefinition = "char(10)")
	public String applyId;

	/** アクティビティＩＤ */
	@Column(nullable = false, columnDefinition = "char(3)")
	public String activityId;

	/** ブランドコード */
	@Column(nullable = true, columnDefinition = "char(4)")
	public String brandCd;

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

	/** 申込受付キーのFinderクラス */
	public static Finder<String, ApplyUketsukeKey> find =
			BaseEntity.getFinder(String.class, ApplyUketsukeKey.class);

}
