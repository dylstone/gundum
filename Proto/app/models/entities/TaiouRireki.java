package models.entities;

import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Version;

import com.avaje.ebean.annotation.CreatedTimestamp;
import com.avaje.ebean.annotation.UpdatedTimestamp;

/**
 * 対応履歴ＤＢ部品
 * <p>
 * 対応履歴のＯＲマッパー
 * </p>
 * @author 大平　司
 * @version 0.4　2014/04/09　プロト開発STEP4版
 */
@Entity
public class TaiouRireki extends BaseEntity {

	/** 顧客対応ＩＤ */
	@Id
    @Column(nullable = true, columnDefinition ="char(10)")
	public String taiouId;

	/** 顧客ＩＤ */
    @Column(nullable = false, columnDefinition ="char(10)")
	public String kokyakuId;

	/** 連絡者氏名 */
    @Column(nullable = true, columnDefinition ="varchar2(32)")
	public String renrakushaName;

	/** 電話番号 */
    @Column(nullable = true, columnDefinition ="varchar2(16)")
	public String telNo;

	/** 対応日時 */
    @Column(nullable = true, columnDefinition ="timestamp")
	public String taiouNichiji;

	/** 対応オペレータＩＤ */
    @Column(nullable = true, columnDefinition ="varchar2(10)")
	public String taiouOperatorId;

	/** 関連顧客対応ＩＤ */
    @Column(nullable = true, columnDefinition ="char(10)")
	public String kanrenClientTaiouId;

	/** 問い合わせ内容 */
    @Column(nullable = true, columnDefinition ="varchar2(400)")
	public String toiawaseNaiyou;

	/** 問い合わせ種別 */
    @Column(nullable = true, columnDefinition ="char(4)")
	public String toiawaseSyubetsu;

	/** 対応内容 */
    @Column(nullable = true, columnDefinition ="varchar2(400)")
	public String taiouNaiyou;

	/** 対応結果 */
    @Column(nullable = true, columnDefinition ="varchar2(400)")
	public String taiouKekka;

	/** 通知内容 */
    @Column(nullable = true, columnDefinition ="varchar2(400)")
	public String tsuuchiNaiyou;

	/** 履歴登録年月日 */
    @Column(nullable = true, columnDefinition ="char(8)")
	public String rirekiTourokuNengetsubi;

	/** 履歴登録オペレータＩＤ */
    @Column(nullable = true, columnDefinition ="varchar2(10)")
	public String rirekiTourokuOperatorId;

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
	

	/** 対応履歴のFinderクラス */
    public static Finder<String, TaiouRireki> find = 
    		BaseEntity.getFinder(String.class, TaiouRireki.class);   

}
