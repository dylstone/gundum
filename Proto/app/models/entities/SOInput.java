package models.entities;

import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Version;

import com.avaje.ebean.annotation.CreatedTimestamp;
import com.avaje.ebean.annotation.UpdatedTimestamp;

/**
 * Ｓ／Ｏ指示ＤＢ部品
 * <p>
 * Ｓ／Ｏ指示のＯＲマッパー
 * </p>
 * @author 那須智貴
 * @version 0.4　2014/05/12　初版
 */
@Entity
public class SOInput extends BaseEntity {

	/** SOID */
	@Id
	@Column(nullable = false, columnDefinition ="char(10)")
	public String soId;
	
	/** 顧客ＩＤ */
    @Column(nullable = true, columnDefinition ="char(10)")
    public String kokyakuId;
    
    /** 契約ＩＤ */
    @Column(nullable = true, columnDefinition ="char(10)")
	public String keiyakuId;
	
    /** サービスＩＤ */
	@Column(nullable = true, columnDefinition ="char(10)")
	public String serviceId;
	
	/** トランザクションID */
    @Column(nullable = true, columnDefinition ="char(10)")
    public String transactionId;
    
	/** ステータス */
    @Column(nullable = false, columnDefinition ="char(2)")
	public String status;

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
	

	/** Ｓ／Ｏ指示のFinderクラス */
    public static Finder<String, SOInput> find = 
    		BaseEntity.getFinder(String.class, SOInput.class);   

}
