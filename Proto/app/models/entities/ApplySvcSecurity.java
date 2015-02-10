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
 * 申込セキュリティ関連サービスＤＢ部品
 * <p>
 * 申込セキュリティ関連サービスのＯＲマッパー
 * </p>
 * @author 大平　司
 * @version 0.5　2014/07/11　プロト開発STEP5版
 */
@Entity
public class ApplySvcSecurity extends BaseEntity {

	/** 申込セキュリティ関連サービスＩＤ */
	@Id
	@Column(nullable = true, columnDefinition ="char(10)")
	public String applySvcSecurityId;

	/** 申込サービスＩＤ */
	@ManyToOne
	@JoinColumn(name = "service_id")
	public NewApplyServiceInfo applyService;

	/** サービスコード */
    @ManyToOne
	@JoinColumn(name = "service_cd")
    public NewServiceMaster serviceMaster;

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
	

	/** 申込セキュリティ関連サービスのFinderクラス */
    public static Finder<String, ApplySvcSecurity> find = 
    		BaseEntity.getFinder(String.class, ApplySvcSecurity.class);   

}
