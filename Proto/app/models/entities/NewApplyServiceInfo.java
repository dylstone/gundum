package models.entities;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
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
public class NewApplyServiceInfo extends BaseEntity {

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

	/** 利用開始日 */
    @Column(nullable = true, columnDefinition ="char(8)")
	public String riyouKaishiNengetsubi;

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
	

	/** 回線サービス */
	@OneToMany(mappedBy = "applyService", cascade = CascadeType.ALL)
	public List<ApplySvcKaisen> applySvcKaisen = new ArrayList<ApplySvcKaisen>();
	
	/** メールサービス */
	@OneToMany(mappedBy = "applyService", cascade = CascadeType.ALL)
	public List<ApplySvcMail> applySvcMail = new ArrayList<ApplySvcMail>();
	
	/** セキュリティ関連サービス */
	@OneToMany(mappedBy = "applyService", cascade = CascadeType.ALL)
	public List<ApplySvcSecurity> applySvcSecurity = new ArrayList<ApplySvcSecurity>();
	
	/** オプションサービス */
	@OneToMany(mappedBy = "applyService", cascade = CascadeType.ALL)
	public List<ApplySvcOption> applySvcOption = new ArrayList<ApplySvcOption>();
	
	/** 設置場所サービス */
    @OneToMany(mappedBy = "applyService", cascade = CascadeType.ALL)
    public List<ApplySvcSettingOption> applySvcSettingOption = new ArrayList<ApplySvcSettingOption>();
	
    
	/** 申込サービス情報のFinderクラス */
    public static Finder<String, NewApplyServiceInfo> find = 
    		BaseEntity.getFinder(String.class, NewApplyServiceInfo.class);   

}
