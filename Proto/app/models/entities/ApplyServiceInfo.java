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
 * 申込サービス情報ＤＢ部品
 * <p>
 * 申込サービス情報のＯＲマッパー
 * </p>
 * @author 大平　司
 * @version 0.5　2014/07/11　プロト開発STEP5版
 */
@Entity
public class ApplyServiceInfo extends BaseEntity {

	/** 申込サービスＩＤ */
	@Id
    @Column(nullable = true, columnDefinition ="char(10)")
	public String applyServiceId;
	
	/** 申込ＩＤに紐づく申込情報 */
	@ManyToOne
	@JoinColumn(name = "apply_id")
	public ApplyInfo applyInfo;

	/** 申込契約ＩＤに紐づく申込契約情報 */
	@ManyToOne
	@JoinColumn(name = "apply_keiyaku_id")
	public ApplyKeiyakuInfo applyKeiyakuInfo;
	
	/** サービスＩＤ */
	@Column(nullable = false, columnDefinition = "char(10)")
	public String serviceId;
	
	/** 変更種別 */
    @Column(nullable = false, columnDefinition ="char(4)")
	public String henkouType;
	
	/** 承認ステータス */
    @Column(nullable = true, columnDefinition ="char(4)")
	public String syouninStatus;

	/** サービスコード */
    @ManyToOne
	@JoinColumn(name = "service_cd")
    public ServiceMaster serviceMaster;

	/** 利用開始日 */
    @Column(nullable = true, columnDefinition ="char(8)")
	public String riyouKaishiNengetsubi;

	/** 利用コース */
    @Column(nullable = true, columnDefinition ="varchar2(100)")
	public String riyouCourse;

	/** 利用プラン */
    @Column(nullable = true, columnDefinition ="varchar2(100)")
	public String riyouPlan;

	/** ＩＰｖ６アダプタ */
    @Column(nullable = true, columnDefinition ="varchar2(50)")
    public String ipv6Adapter;

	/** メールアドレス１ */
    @Column(nullable = true, columnDefinition ="varchar2(30)")
	public String mailaddress1;

	/** メールアドレス２ */
    @Column(nullable = true, columnDefinition ="varchar2(30)")
	public String mailaddress2;

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

	/** 従属サービスコード１ */
    @ManyToOne
	@JoinColumn(name = "depend_service_cd1")
    public ServiceMaster dependService1;

	/** 従属サービスコード２ */
    @ManyToOne
	@JoinColumn(name = "depend_service_cd2")
    public ServiceMaster dependService2;

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
	
	
	/** 申込サービス情報のFinderクラス */
    public static Finder<String, ApplyServiceInfo> find = 
    		BaseEntity.getFinder(String.class, ApplyServiceInfo.class);   

}
