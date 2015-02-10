package models.entities;

import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Version;

import com.avaje.ebean.annotation.CreatedTimestamp;
import com.avaje.ebean.annotation.UpdatedTimestamp;

/**
 * サービスマスタＤＢ部品
 * <p>
 * サービスマスタのＯＲマッパー
 * </p>
 * @author 大平　司
 * @version 0.5　2014/07/11　プロト開発STEP5版
 */
@Entity
public class NewServiceMaster extends BaseEntity {

	/** サービスコード */
	@Id
    @Column(nullable = true, columnDefinition ="char(4)")
	public String serviceCd;

	/** サービス名称 */
    @Column(nullable = true, columnDefinition ="varchar2(100)")
	public String serviceMeishou;

	/** サービス分類コード */
	@ManyToOne
	@JoinColumn(name = "service_bunrui_cd")
	public ServiceBunruiMaster serviceBunrui;

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
	
	
	/** サービスコードに紐づくサービス料金マスタ */
	@OneToOne(mappedBy = "serviceMaster")
	public NewServiceRyoukinMaster serviceRyoukin;

	/** サービスマスタのFinderクラス */
    public static Finder<String, NewServiceMaster> find = 
    		BaseEntity.getFinder(String.class, NewServiceMaster.class);   

}
