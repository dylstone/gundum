package models.entities;

import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Version;

import com.avaje.ebean.annotation.CreatedTimestamp;
import com.avaje.ebean.annotation.UpdatedTimestamp;

/**
 * オペレータマスタＤＢ部品
 * <p>
 * オペレータマスタのＯＲマッパー
 * </p>
 * @author 大平　司
 * @version 0.4　2014/04/09　プロト開発STEP4版
 */
@Entity
public class OperatorMaster extends BaseEntity {

	/** オペレータＩＤ */
	@Id
    public String operatorId;

	/** 氏名 */
    public String operatorName;

    /** ユーザパスワード */
    public String operatorPwd;

    /** 所属 */
    @Column(nullable = true, length = 30)
    public String operatorDepartment;

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
	

    /** オペレータマスタのFinderクラス */
    public static Finder<String, OperatorMaster> find = 
    		BaseEntity.getFinder(String.class, OperatorMaster.class);


	@Override
	public String toString() {
		return "OperatorMaster [operatorId=" + operatorId + ", operatorName="
				+ operatorName + ", operatorPwd=" + operatorPwd
				+ ", operatorDepartment=" + operatorDepartment
				+ ", deleteDate=" + deleteDate + ", createUser=" + createUser
				+ ", lastUpdateUser=" + lastUpdateUser + ", createDT="
				+ createDT + ", lastUpdateDT=" + lastUpdateDT + "]";
	}

}
