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
 * 設置場所サービスＤＢ部品
 * <p>
 * 設置場所サービスのＯＲマッパー
 * </p>
 * @author 那須　智貴
 * @version 0.1　2014/08/05　新規作成
 */
@Entity
public class SvcSettingOption extends BaseEntity {

	/** 設置場所関連サービスＩＤ */
	@Id
	@Column(nullable = true, columnDefinition ="char(10)")
	public String svcSettingOptionId;

	/** サービスＩＤ */
	@ManyToOne
	@JoinColumn(name = "service_id")
	public NewServiceInfo service;

	/** サービスコード */
    @ManyToOne
	@JoinColumn(name = "service_cd")
    public NewServiceMaster serviceMaster;
    
    /** 都道府県 */
    @Column(nullable = true, columnDefinition ="varchar2(8)")
    public String todouhuken;

    /** 市区郡 */
    @Column(nullable = true, columnDefinition ="varchar2(100)")
    public String siKuGun;

    /** 町村／大字 */
    @Column(nullable = true, columnDefinition ="varchar2(100)")
    public String tyousonOaza;

    /** 字／番地／号 */
    @Column(nullable = true, columnDefinition ="varchar2(100)")
    public String azaBanchiGou;

    /** アパートマンション */
    @Column(nullable = true, columnDefinition ="varchar2(50)")
    public String apartment;

    /** 部屋番号 */
    @Column(nullable = true, columnDefinition ="varchar2(30)")
    public String apartmentRoomNo;
    
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
	

	/** セキュリティ関連サービスのFinderクラス */
    public static Finder<String, SvcSettingOption> find = 
    		BaseEntity.getFinder(String.class, SvcSettingOption.class);   

}
