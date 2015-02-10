package models.entities;

import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Version;

import com.avaje.ebean.annotation.CreatedTimestamp;
import com.avaje.ebean.annotation.UpdatedTimestamp;

/**
 * 手続き実行サービスマスタＤＢ部品
 * <p>
 * 手続き種別マスタのＯＲマッパー
 * </p>
 * @author 甲斐
 * @version 0.1　2014/07/07　新規作成
 */
@Entity
public class ProcedureServiceMaster extends BaseEntity {

	/** 手続き実行サービスマスタの複合主キー */
	@EmbeddedId
	public ProcedureServiceMasterPK primaryKey;

	/** 手続き種別 */
	@Column(nullable = false, columnDefinition = "varchar2(3)")
	public String procedureType;
	
	/** 適用終了日 */
	@Column(nullable = true, columnDefinition = "char(8)")
	public String tekiyoEndDate;

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

	/** 手続き種別マスタのFinderクラス */
	public static Finder<ProcedureServiceMasterPK, ProcedureServiceMaster> find =
			BaseEntity.getFinder(ProcedureServiceMasterPK.class, ProcedureServiceMaster.class);

}
