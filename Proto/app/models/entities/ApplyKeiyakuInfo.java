package models.entities;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Version;

import com.avaje.ebean.annotation.CreatedTimestamp;
import com.avaje.ebean.annotation.UpdatedTimestamp;

/**
 * 申込契約情報ＤＢ部品
 * <p>
 * 申込契約情報のＯＲマッパー
 * </p>
 * @author 大平　司
 * @version 0.4　2014/04/09　プロト開発STEP4版
 */
@Entity
public class ApplyKeiyakuInfo extends BaseEntity {

	/** 申込契約ＩＤ */
	@Id
    @Column(nullable = true, columnDefinition ="char(10)")
	public String applyKeiyakuId;
	
	/** 申込ＩＤに紐づく申込情報 */
	@OneToOne
	@JoinColumn(name = "apply_id")
	public ApplyInfo applyInfo;

	/** 申込顧客ＩＤに紐づく申込顧客情報 */
	@OneToOne
	@JoinColumn(name = "apply_kokyaku_id")
	public ApplyKokyakuInfo applyKokyakuInfo;
	
	/** 契約ＩＤに紐づく契約情報 */
	@OneToOne
	@JoinColumn(name = "keiyaku_id")
	public KeiyakuInfo keiyakuInfo;

	/** 変更種別 */
    @Column(nullable = false, columnDefinition ="char(4)")
	public String henkouType;
	
	/** 承認ステータス */
    @Column(nullable = true, columnDefinition ="char(4)")
	public String syouninStatus;

	/** 契約ブランドコード */
    @Column(nullable = true, columnDefinition ="char(8)")
	public String keiyakuBrandCd;

	/** 販売形態 */
    @Column(nullable = true, columnDefinition ="char(2)")
	public String hanbaiKeitai;

	/** 申込日 */
    @Column(nullable = true, columnDefinition ="char(8)")
	public String moushikomiNengetsubi;

	/** 契約更新年月 */
    @Column(nullable = true, columnDefinition ="char(6)")
	public String keiyakuKoushinNengetsu;

	/** 利用開始日 */
    @Column(nullable = true, columnDefinition ="char(8)")
	public String riyouKaishiNengetsubi;

	/** 支払方法 */
    @Column(nullable = true, columnDefinition ="varchar2(50)")
	public String shiharaiHouhou;

	/** 請求区分 */
    @Column(nullable = true, columnDefinition ="varchar2(50)")
	public String seikyuuKubun;

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

    
	/** 申込契約ＩＤに紐づく申込サービス情報のリスト */
	@OneToMany(mappedBy = "applyKeiyakuInfo")
	public List<ApplyServiceInfo> applyServiceInfoList = new ArrayList<ApplyServiceInfo>();


	/** 申込契約情報のFinderクラス */
    public static Finder<String, ApplyKeiyakuInfo> find = 
    		BaseEntity.getFinder(String.class, ApplyKeiyakuInfo.class);

}
