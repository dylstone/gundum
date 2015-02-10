package models.entities;

import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Version;

import com.avaje.ebean.annotation.CreatedTimestamp;
import com.avaje.ebean.annotation.UpdatedTimestamp;

/**
 * 申込回線サービスＤＢ部品
 * <p>
 * 申込回線サービスのＯＲマッパー
 * </p>
 * @author 大平　司
 * @version 0.5　2014/07/11　プロト開発STEP5版
 */
@Entity
public class ApplySvcKaisen extends BaseEntity {

	/** 申込回線サービスＩＤ */
	@Id
	@Column(nullable = true, columnDefinition ="char(10)")
	public String applySvcAuhikariId;

	/** 申込サービスＩＤ */
	@ManyToOne
	@JoinColumn(name = "service_id")
	public NewApplyServiceInfo applyService;

	/** サービスコード */
    @ManyToOne
	@JoinColumn(name = "service_cd")
    public NewServiceMaster serviceMaster;

	/** 利用コース */
    @Column(nullable = true, columnDefinition ="varchar2(100)")
	public String riyouCourse;

	/** 利用プラン */
    @Column(nullable = true, columnDefinition ="varchar2(100)")
	public String riyouPlan;

	/** ＩＰｖ６アダプタ */
    @Column(nullable = true, columnDefinition ="varchar2(50)")
    public String ipv6Adapter;

	/** ＴＴ－ｐｈｏｎｅ状態 */
    @Column(nullable = true, columnDefinition ="varchar2(50)")
	public String ttPhoneStatus;

	/** ソク割１５ */
    @Column(nullable = true, columnDefinition ="varchar2(50)")
	public String sokuwari15;

	/** 開通工事日 */
    @Column(nullable = true, columnDefinition ="char(8)")
	public String kaitsuuKoujibi;

	/** 工事予定日日 */
    @Column(nullable = true, columnDefinition ="char(8)")
	public String koujiYoteibi;

	/** 論理削除年月日 */
    @Column(nullable = true, columnDefinition ="char(8)")
    public String deleteDate;

	/** 作成者 */
    @Column(nullable = false, columnDefinition ="varchar2(32)", updatable = false)
	public String createUser;

	/** 最終更新者 */
    @Column(nullable = false, columnDefinition ="varchar2(32)")
	public String lastUpdateUser;

	/** 作成日時 */
    @CreatedTimestamp
    @Column(nullable = false, columnDefinition ="timestamp default sysdate", updatable = false)
	public Timestamp createDT;

	/** 最終更新日時 */
    @Version
    @UpdatedTimestamp
    @Column(nullable = false, columnDefinition ="timestamp default sysdate")
	public Timestamp lastUpdateDT;
	

	/** 申込回線サービスのFinderクラス */
    public static Finder<String, ApplySvcKaisen> find = 
    		BaseEntity.getFinder(String.class, ApplySvcKaisen.class);   

}
