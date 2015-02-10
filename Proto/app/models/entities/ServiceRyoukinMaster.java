package models.entities;

import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Version;

import com.avaje.ebean.annotation.CreatedTimestamp;
import com.avaje.ebean.annotation.UpdatedTimestamp;

/**
 * サービス料金マスタＤＢ部品
 * <p>
 * サービス料金マスタのＯＲマッパー
 * </p>
 * @author 大平　司
 * @version 0.4　2014/04/09　プロト開発STEP4版
 */
@Entity
public class ServiceRyoukinMaster extends BaseEntity {

	/** サービス料金コード */
	@Id
    @Column(nullable = true, columnDefinition ="char(4)")
	public String serviceRyoukinCd;

	/** サービスコード */
	@OneToOne
	@JoinColumn(name = "service_cd")
	public ServiceMaster serviceMaster;

	/** 月額料金 */
    @Column(nullable = true, columnDefinition ="number(10) default 0")
	public Integer getsugakuRyoukin;

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
	
	
	/** サービス料金マスタのFinderクラス */
    public static Finder<String, ServiceRyoukinMaster> find = 
    		BaseEntity.getFinder(String.class, ServiceRyoukinMaster.class);   

}
