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
 * サービス情報ＤＢ部品
 * <p>
 * サービス情報のＯＲマッパー
 * </p>
 * @author 大平　司
 * @version 0.4　2014/04/09　プロト開発STEP4版
 */
@Entity
public class ServiceInfo extends BaseEntity {

	/** サービスＩＤ */
	@Id
	@Column(nullable = true, columnDefinition ="char(10)")
	public String serviceId;

	/** 契約ＩＤ */
	@ManyToOne
	@JoinColumn(name = "keiyaku_id")
	public KeiyakuInfo keiyakuInfo;

	/** ステータス */
    @Column(nullable = false, columnDefinition ="char(4)")
	public String status;

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
	

	/** サービス情報のFinderクラス */
    public static Finder<String, ServiceInfo> find = 
    		BaseEntity.getFinder(String.class, ServiceInfo.class);   

}
