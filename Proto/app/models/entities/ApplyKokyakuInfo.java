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
 * 申込顧客情報ＤＢ部品
 * <p>
 * 申込顧客情報のＯＲマッパー
 * </p>
 * @author 大平　司
 * @version 0.4　2014/04/09　プロト開発STEP4版
 */
@Entity
public class ApplyKokyakuInfo extends BaseEntity {

	/** 申込顧客ＩＤ */
	@Id
    @Column(nullable = true, columnDefinition ="char(10)")
	public String applyKokyakuId;
	
	/** 申込ＩＤに紐づく申込情報 */
	@OneToOne
	@JoinColumn(name = "apply_id")
	public ApplyInfo applyInfo;

	/** 顧客ＩＤに紐づく顧客情報 */
	@OneToOne
	@JoinColumn(name = "kokyaku_id")
	public KokyakuInfo kokyakuInfo;
	
	/** 変更種別 */
    @Column(nullable = false, columnDefinition ="char(4)")
	public String henkouType;
	
	/** 承認ステータス */
    @Column(nullable = true, columnDefinition ="char(4)")
	public String syouninStatus;
	
	/** ユーザＩＤ */
    @Column(nullable = true, columnDefinition ="varchar2(20)")
    public String kokyakuUserId;

	/** ユーザパスワード */
    @Column(nullable = true, columnDefinition ="varchar2(20)")
    public String kokyakuUserPwd;

	/** 姓 */
    @Column(nullable = true, columnDefinition ="varchar2(16)")
    public String lastName;

	/** 名 */
    @Column(nullable = true, columnDefinition ="varchar2(16)")
    public String firstName;

	/** 姓（ふりがな） */
    @Column(nullable = true, columnDefinition ="varchar2(32)")
    public String lastNameFurigana;

	/** 名（ふりがな） */
    @Column(nullable = true, columnDefinition ="varchar2(32)")
    public String firstNameFurigana;

    /** 部署名 */
    @Column(nullable = true, columnDefinition ="varchar2(32)")
    public String department;
    
    /** 法人担当者 */
    @Column(nullable = true, columnDefinition ="varchar2(32)")
    public String corporativeUser;
    
	/** 性別コード */
    @Column(nullable = true, columnDefinition ="char(1)")
    public String seibetsuCd;

	/** 生年月日 */
    @Column(nullable = true, columnDefinition ="char(8)")
    public String birthNengetsubi;

	/** 職業コード */
    @Column(nullable = true, columnDefinition ="char(4)")
    public String jobCd;

	/** 見込顧客フラグ */
    @Column(nullable = false, columnDefinition ="char(1) default 0")
    public String mikomiKokyakuFlg;

	/** 連絡先メールアドレス */
    @Column(nullable = true, columnDefinition ="varchar2(30)")
    public String renrakusakiMailaddress;

	/** 電話種別 */
    @Column(nullable = true, columnDefinition ="char(4)")
    public String telType;

	/** 電話番号１ */
    @Column(nullable = true, columnDefinition ="varchar2(16)")
    public String telNo1;

	/** 電話番号２ */
    @Column(nullable = true, columnDefinition ="varchar2(16)")
    public String telNo2;

	/** ＦＡＸ番号 */
    @Column(nullable = true, columnDefinition ="varchar2(16)")
    public String faxNo;

	/** 郵便番号 */
    @Column(nullable = true, columnDefinition ="varchar2(8)")
    public String zipNo;

	/** 都道府県 */
    @Column(nullable = true, columnDefinition ="varchar2(8)")
    public String todouhuken;

	/** 市区郡 */
    @Column(nullable = true, columnDefinition ="varchar2(100)")
    public String siKuGun;

	/** 町村／大字 */
    @Column(nullable = true, columnDefinition ="varchar2(100)")
    public String tyousonOaza;

	/** 字／番地／号 */
    @Column(nullable = true, columnDefinition ="varchar2(100)")
    public String azaBanchiGou;

	/** アパートマンション */
    @Column(nullable = true, columnDefinition ="varchar2(50)")
    public String apartment;

	/** 部屋番号 */
    @Column(nullable = true, columnDefinition ="varchar2(30)")
    public String apartmentRoomNo;

	/** 気付・様 */
    @Column(nullable = true, columnDefinition ="varchar2(20)")
    public String kidukeSama;

	/** ユーザパスワード最終更新日 */
    @Column(nullable = true, columnDefinition ="char(8)")
    public String kokyakuUserPwdLastUpdated;

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
	

	/** 申込顧客ＩＤに紐づく申込契約情報 */
	@OneToOne(mappedBy = "applyKokyakuInfo")
	public ApplyKeiyakuInfo applyKeiyakuInfo;


	/** 申込顧客情報のFinderクラス */
    public static Finder<String, ApplyKokyakuInfo> find = 
    		BaseEntity.getFinder(String.class, ApplyKokyakuInfo.class);   

}
