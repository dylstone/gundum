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
 * トランザクション情報（サービス情報）ＤＢ部品
 * <p>
 * トランザクション情報（サービス情報）のＯＲマッパー
 * </p>
 * @author 那須智貴
 * @version 0.1　2014/05/12　初版
 * @version 0.2  2014/05/21  トランザクション情報の親子関係追加
 */
@Entity
public class TransServiceInfo extends BaseEntity {

	//	/** トランザクションサービスID */
	//	@Id
	//	@Column(nullable = false, columnDefinition = "char(10)")
	//	public String transServiceId;

	/** トランザクションID */
	@Id
	@Column(nullable = false, columnDefinition = "char(10)")
	public String transactionId;

	/** 顧客ＩＤ */
	@Column(nullable = false, columnDefinition = "char(10)")
	public String kokyakuId;

	/** 契約ＩＤ */
	@Column(nullable = false, columnDefinition = "char(10)")
	public String keiyakuId;

	/** サービスＩＤ */
	@Column(nullable = false, columnDefinition = "char(10)")
	public String serviceId;

	//	/** トランザクション契約ＩＤに紐づくトランザクション契約情報 */
	//	@ManyToOne
	//	@JoinColumn(name = "trans_keiyaku_id")
	//	public TransKeiyakuInfo transKeiyakuInfo;

	/** ステータス */
	@Column(nullable = false, columnDefinition = "char(4)")
	public String status;

	/** サービスコード */
	@ManyToOne
	@JoinColumn(name = "service_cd")
	public NewServiceMaster serviceMaster;

	/** 利用開始日 */
	@Column(nullable = true, columnDefinition = "char(8)")
	public String riyouKaishiNengetsubi;

	/** 利用コース */
	@Column(nullable = true, columnDefinition = "varchar2(100)")
	public String riyouCourse;

	/** 利用プラン */
	@Column(nullable = true, columnDefinition = "varchar2(100)")
	public String riyouPlan;

	/** ＩＰｖ６アダプタ */
	@Column(nullable = true, columnDefinition = "varchar2(50)")
	public String ipv6Adapter;

	/** メールアドレス */
	@Column(nullable = true, columnDefinition = "varchar2(30)")
	public String mailaddress;

	/** ＴＴ－ｐｈｏｎｅ状態 */
	@Column(nullable = true, columnDefinition = "varchar2(50)")
	public String ttPhoneStatus;

	/** ソク割１５ */
	@Column(nullable = true, columnDefinition = "varchar2(50)")
	public String sokuwari15;

	/** 開通工事日 */
	@Column(nullable = true, columnDefinition = "char(8)")
	public String kaitsuuKoujibi;

	/** 工事予定日日 */
	@Column(nullable = true, columnDefinition = "char(8)")
	public String koujiYoteibi;

	/** 従属サービスコード１ */
	@ManyToOne
	@JoinColumn(name = "depend_service_cd1")
	public NewServiceMaster dependService1;

	/** 従属サービスコード２ */
	@ManyToOne
	@JoinColumn(name = "depend_service_cd2")
	public NewServiceMaster dependService2;

	/** 都道府県 */
	@Column(nullable = true, columnDefinition = "varchar2(8)")
	public String todouhuken;

	/** 市区郡 */
	@Column(nullable = true, columnDefinition = "varchar2(100)")
	public String siKuGun;

	/** 町村／大字 */
	@Column(nullable = true, columnDefinition = "varchar2(100)")
	public String tyousonOaza;

	/** 字／番地／号 */
	@Column(nullable = true, columnDefinition = "varchar2(100)")
	public String azaBanchiGou;

	/** アパートマンション */
	@Column(nullable = true, columnDefinition = "varchar2(50)")
	public String apartment;

	/** 部屋番号 */
	@Column(nullable = true, columnDefinition = "varchar2(30)")
	public String apartmentRoomNo;

	/** 業務詳細 */
	@Column(nullable = false, columnDefinition = "char(1)")
	public String businessDetail;

	/** 申込内容詳細 */
	@Column(nullable = false, columnDefinition = "char(1)")
	public String applyDetail;

	/** 手続き種別 */
	@Column(nullable = false, columnDefinition = "varchar2(3)")
	public String procedureType;

	/** 手続き進捗キュー登録状況 */
	@Column(nullable = false, columnDefinition = "char(1)")
	public String tourokuFlg;

	/** SOステータス */
	@Column(nullable = true, columnDefinition = "varchar2(4)")
	public String soStatus;

	/** 起動間隔 */
	@Column(nullable = false, columnDefinition = "number(4) default 0")
	public String intervalTime;

	/** 最終実行時刻 */
	@Column(nullable = false, columnDefinition = "timestamp default sysdate")
	public Timestamp lastRunDate;

	/** サービスワークフロー定義ファイル情報 */
	@Column(nullable = true, columnDefinition = "varchar2(100)")
	public String swfFileInfo;

	/** 論理削除年月日 */
	@Column(nullable = true, columnDefinition = "char(8)")
	public String deleteDate;

	/** 作成者 */
	@Column(nullable = false, columnDefinition = "varchar2(32)", updatable = false)
	public String createUser;

	/** 最終更新者 */
	@Column(nullable = false, columnDefinition = "varchar2(32)")
	public String lastUpdateUser;

	/** 作成日時 */
	@CreatedTimestamp
	@Column(nullable = false, columnDefinition = "timestamp default sysdate", updatable = false)
	public Timestamp createDT;

	/** 最終更新日時 */
	@Version
	@UpdatedTimestamp
	@Column(nullable = false, columnDefinition = "timestamp default sysdate")
	public Timestamp lastUpdateDT;

	/** トランザクション情報（サービス情報）のFinderクラス */
	public static Finder<String, TransServiceInfo> find =
			BaseEntity.getFinder(String.class, TransServiceInfo.class);

}
