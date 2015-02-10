package models.entities;

import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Version;

import com.avaje.ebean.annotation.CreatedTimestamp;
import com.avaje.ebean.annotation.UpdatedTimestamp;

/**
 * ＩＤ情報ＤＢ部品
 * <p>
 * ＩＤ情報のＯＲマッパー
 * </p>
 * @author 大平　司
 * @version 0.4　2014/04/09　プロト開発STEP4版
 */
@Entity
public class IdInfo extends BaseEntity {

	/** ＩＤコード */
	@Id
    @Column(nullable = true, columnDefinition ="char(4)")
	public String idCd;

	/** ＩＤ識別文字列 */
    @Column(nullable = false, columnDefinition ="varchar2(5)")
	public String idTypeString;

    /** ＩＤ番号桁数 */
    @Column(nullable = false, columnDefinition ="number(2) default 0")
	public Byte idNoDigit;
    
	/** ＩＤ番号 */
    @Column(nullable = false, columnDefinition ="number(10) default 0")
	public Integer idNo;

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
	

	/** ＩＤ情報のFinderクラス */
    public static Finder<String, IdInfo> find = 
    		BaseEntity.getFinder(String.class, IdInfo.class);   


}
