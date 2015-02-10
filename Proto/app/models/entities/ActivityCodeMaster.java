package models.entities;

import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Version;

import com.avaje.ebean.annotation.CreatedTimestamp;
import com.avaje.ebean.annotation.UpdatedTimestamp;

/**
 * アクティビティコードマスタＤＢ部品
 * <p>
 * アクティビティコードマスタのＯＲマッパー
 * </p>
 * @author 甲斐
 * @version 0.5　2014/07/17　プロト開発STEP5版
 */
@Entity
public class ActivityCodeMaster extends BaseEntity {

	/** アクティビティコード */
	@Id
    @Column(nullable = false, columnDefinition ="char(3)")
	public String activityId;

	/**アクティビティ名称 */
    @Column(nullable = true, columnDefinition ="varchar2(30)")
	public String activityName;

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
	
	/** サービスマスタのFinderクラス */
    public static Finder<String, ActivityCodeMaster> find = 
    		BaseEntity.getFinder(String.class, ActivityCodeMaster.class);   

}
