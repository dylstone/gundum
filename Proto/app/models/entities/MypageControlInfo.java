package models.entities;

import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Version;

import com.avaje.ebean.annotation.CreatedTimestamp;
import com.avaje.ebean.annotation.UpdatedTimestamp;

/**
 * マイページ制御情報ＤＢ部品
 * <p>
 * マイページ制御情報のＯＲマッパー
 * </p>
 * @author 大平　司
 * @version 0.4　2014/04/09　プロト開発STEP4版
 */
@Entity
public class MypageControlInfo extends BaseEntity {

	/** マイページ制御情報の複合主キー */
	@EmbeddedId
	public MypageControlInfoPK primaryKey;

	/** 左位置 */
    @Column(nullable = true, columnDefinition ="number(11,3) default 0")
	public Float leftPosition;

	/** 上位置 */
    @Column(nullable = true, columnDefinition ="number(11,3) default 0")
	public Float topPosition;

	/** 幅 */
    @Column(nullable = true, columnDefinition ="number(11,3) default 0")
	public Float width;

	/** 高さ */
    @Column(nullable = true, columnDefinition ="number(11,3) default 0")
	public Float height;

	/** 重なり順 */
    @Column(nullable = true, columnDefinition ="number(2) default 0")
	public Byte stackOrder;

	/** 表示 */
    @Column(nullable = true, columnDefinition ="number(1) default 0")
	public Boolean display;

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
	

	/** マイページ制御情報のFinderクラス */
	public static Finder<MypageControlInfoPK, MypageControlInfo> find = 
			BaseEntity.getFinder(MypageControlInfoPK.class, MypageControlInfo.class);

}
