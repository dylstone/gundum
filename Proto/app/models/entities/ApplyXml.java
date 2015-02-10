package models.entities;

import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Version;

import com.avaje.ebean.annotation.CreatedTimestamp;
import com.avaje.ebean.annotation.UpdatedTimestamp;


/**
 * 申込XMLＤＢ部品
 * <p>
 * 申込XMLのＯＲマッパー
 * </p>
 * @author 甲斐
 * @version 0.1　2014/07/25　新規作成
 */
@Entity
public class ApplyXml extends BaseEntity {

	/** 申込ＩＤ */
	@Id
	@Column(nullable = false, columnDefinition = "char(10)")
	public String applyId;

	/** 申込情報XML */
//	@Column(nullable = true, columnDefinition = "XMLType")
	@Column(nullable = true, columnDefinition = "CLob")
	public String applyXml;
	
	/** 受付台帳ＩＤ */
	@Column(nullable = true, columnDefinition = "char(10)")
	public String uketsukeId;
	
	/** 業務詳細コード */
	@Column(nullable = false, columnDefinition = "char(1)")
	public String businessDetail;

	/** 顧客ＩＤ */
	@Column(nullable = false, columnDefinition = "char(10)")
	public String kokyakuId;
	
	/** 契約ＩＤ */
	@Column(nullable = false, columnDefinition = "char(10)")
	public String keiyakuId;

	/** 受付日 */
	@Column(nullable = true, columnDefinition = "char(8)")
	public String uketsukeDate;
	
	/** 申込日 */
	@Column(nullable = true, columnDefinition = "char(8)")
	public String applyDate;
	
	/** 申込バージョンコード */
	@Column(nullable = true, columnDefinition = "char(4)")
	public String applyVerCd;
	
	/** 受付ルートコード */
	@Column(nullable = true, columnDefinition = "char(2)")
	public String uketsukeRouteCd;

	/** ブランドコード */
	@Column(nullable = false, columnDefinition = "char(4)")
	public String brandCd;
	
	/** 不備有無 */
	@Column(nullable = false, columnDefinition = "char(1)")
	public String errorCd;

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
	public static Finder<String, ApplyXml> find =
			BaseEntity.getFinder(String.class, ApplyXml.class);

}
