package models.entities;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Version;

import com.avaje.ebean.annotation.CreatedTimestamp;
import com.avaje.ebean.annotation.UpdatedTimestamp;

/**
 * 申込情報ＤＢ部品
 * <p>
 * 申込情報のＯＲマッパー
 * </p>
 * @author 大平　司
 * @version 0.4　2014/04/09　プロト開発STEP4版
 */
@Entity
public class ApplyInfo extends BaseEntity {

	/** 申込ＩＤ */
	@Id
    @Column(nullable = false, columnDefinition ="char(10)")
	public String applyId;

	/** 受付ＩＤ */
    @Column(nullable = false, columnDefinition ="char(10)")
	public String uketsukeId;

	/** 顧客ＩＤに紐づく顧客情報 */
	@OneToOne
	@JoinColumn(name = "kokyaku_id")
	public KokyakuInfo kokyakuInfo;

	/** 契約ＩＤに紐づく契約情報 */
	@OneToOne
	@JoinColumn(name = "keiyaku_id")
	public KeiyakuInfo keiyakuInfo;

	/** 申込状態 */
    @Column(nullable = false, columnDefinition ="char(4)")
	public String applyStatus;

	/** 登録ミスフラグ */
    @Column(nullable = true, columnDefinition ="number(1) default 0")
	public Boolean tourokuMissFlg;
	
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


	/** 申込ＩＤに紐づく申込顧客情報 */
	@OneToOne(mappedBy = "applyInfo", cascade = CascadeType.ALL)
	public ApplyKokyakuInfo applyKokyakuInfo;
    
	/** 申込ＩＤに紐づく申込契約情報 */
	@OneToOne(mappedBy = "applyInfo", cascade = CascadeType.ALL)
	public ApplyKeiyakuInfo applyKeiyakuInfo;
    
    /** 申込ＩＤに紐づく申込サービス情報のリスト */
	@OneToMany(mappedBy = "applyInfo", cascade = CascadeType.ALL)
	public List<ApplyServiceInfo> applyServiceInfo = new ArrayList<ApplyServiceInfo>();
	
    /** 申込ＩＤに紐づく申込（新）サービス情報のリスト */
	@OneToMany(mappedBy = "applyInfo", cascade = CascadeType.ALL)
	public List<NewApplyServiceInfo> newApplyServiceInfo = new ArrayList<NewApplyServiceInfo>();
	

	/** 申込情報のFinderクラス */
    public static Finder<String, ApplyInfo> find = 
    		BaseEntity.getFinder(String.class, ApplyInfo.class);

}
