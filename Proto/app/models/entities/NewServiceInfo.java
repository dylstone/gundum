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
 * 新サービス情報ＤＢ部品
 * <p>
 * 新サービス情報のＯＲマッパー
 * </p>
 * @author 大平　司
 * @version 0.5　2014/07/11　プロト開発STEP5版
 */
@Entity
public class NewServiceInfo extends BaseEntity {

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
	@OneToMany(mappedBy = "service", cascade = CascadeType.ALL)
	public List<SvcKaisen> svcKaisen = new ArrayList<SvcKaisen>();
	
	/** メールサービス */
	@OneToMany(mappedBy = "service", cascade = CascadeType.ALL)
	public List<SvcMail> svcMail = new ArrayList<SvcMail>();
	
	/** セキュリティ関連サービス */
	@OneToMany(mappedBy = "service", cascade = CascadeType.ALL)
	public List<SvcSecurity> svcSecurity = new ArrayList<SvcSecurity>();
	
	/** オプションサービス */
	@OneToMany(mappedBy = "service", cascade = CascadeType.ALL)
	public List<SvcOption> svcOption = new ArrayList<SvcOption>();
	
	/** 設置場所サービス */
    @OneToMany(mappedBy = "service", cascade = CascadeType.ALL)
    public List<SvcSettingOption> svcSettingOption = new ArrayList<SvcSettingOption>();
	

	/** 新サービス情報のFinderクラス */
    public static Finder<String, NewServiceInfo> find = 
    		BaseEntity.getFinder(String.class, NewServiceInfo.class);   

}
