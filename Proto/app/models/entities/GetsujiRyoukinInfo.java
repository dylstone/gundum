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
 * 月次料金情報ＤＢ部品
 * <p>
 * 月次料金情報のＯＲマッパー
 * </p>
 * @author 大平　司
 * @version 0.4　2014/04/09　プロト開発STEP4版
 */
@Entity
public class GetsujiRyoukinInfo extends BaseEntity {

	/** 課金ＩＤ */
	@Id
    @Column(nullable = true, columnDefinition ="char(10)")
	public String kakinId;

	/** 請求ＩＤ */
    @Column(nullable = false, columnDefinition ="char(10)")
	public String seikyuuId;

	/** 契約ＩＤに紐づく契約情報 */
	@ManyToOne
	@JoinColumn(name = "keiyaku_id")
	public KeiyakuInfo keiyakuInfo;

	/** 対象月 */
    @Column(nullable = false, precision = 2, scale = 0)
	public Byte taisyouMonth;

	/** 総額 */
    @Column(nullable = true, columnDefinition ="number(20) default 0")
	public Long sougaku;

	/** 内消費税 */
    @Column(nullable = true, columnDefinition ="number(10) default 0")
	public Integer tax;

	/** 月額料金 */
    @Column(nullable = true, columnDefinition ="number(10) default 0")
	public Integer getsugakuRyoukin;

	/** 日割料金 */
    @Column(nullable = true, columnDefinition ="number(10) default 0")
	public Integer hiwariRyoukin;

	/** 一時金 */
    @Column(nullable = true, columnDefinition ="number(10) default 0")
	public Integer ichijikin;

	/** 割引額 */
    @Column(nullable = true, columnDefinition ="number(10) default 0")
	public Integer waribikigaku;

	/** 従量料金 */
    @Column(nullable = true, columnDefinition ="number(10) default 0")
	public Integer zyuuryouRyoukin;

	/** 調整金額 */
    @Column(nullable = true, columnDefinition ="number(10) default 0")
	public Integer tyouseiKingaku;

	/** 預り品代金充当金額 */
    @Column(nullable = true, columnDefinition ="number(10) default 0")
	public Integer azukarihinKingaku;

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
	

	/** 月次料金情報のFinderクラス */
    public static Finder<String, GetsujiRyoukinInfo> find = 
    		BaseEntity.getFinder(String.class, GetsujiRyoukinInfo.class);

}
