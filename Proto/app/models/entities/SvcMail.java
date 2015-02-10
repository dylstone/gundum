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
 * メールサービスＤＢ部品
 * <p>
 * メールサービスのＯＲマッパー
 * </p>
 * @author 大平　司
 * @version 0.5　2014/07/11　プロト開発STEP5版
 */
@Entity
public class SvcMail extends BaseEntity {

	/** メールサービスＩＤ */
	@Id
	@Column(nullable = true, columnDefinition ="char(10)")
	public String svcMailId;

	/** サービスＩＤ */
	@ManyToOne
	@JoinColumn(name = "service_id")
	public NewServiceInfo service;

	/** サービスコード */
    @ManyToOne
	@JoinColumn(name = "service_cd")
    public NewServiceMaster serviceMaster;

	/** メールアドレス */
    @Column(nullable = true, columnDefinition ="varchar2(30)")
	public String mailaddress;

	/** 従属サービスコード１ */
    @ManyToOne
	@JoinColumn(name = "depend_service_cd1")
    public NewServiceMaster dependService1;

	/** 従属サービスコード２ */
    @ManyToOne
	@JoinColumn(name = "depend_service_cd2")
    public NewServiceMaster dependService2;

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
	

	/** メールサービスのFinderクラス */
    public static Finder<String, SvcMail> find = 
    		BaseEntity.getFinder(String.class, SvcMail.class);   

}
