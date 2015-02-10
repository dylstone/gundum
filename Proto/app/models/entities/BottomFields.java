package models.entities;

import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.Version;

import com.avaje.ebean.annotation.CreatedTimestamp;
import com.avaje.ebean.annotation.UpdatedTimestamp;

/**
 * 共通下部項目クラス
 * <p>
 * テーブル共通で下部に定義する項目
 * </p>
 * @author 大平　司
 * @version 0.4　2014/04/09　プロト開発STEP4版
 */
@Embeddable
public class BottomFields {

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
	
}
