package models.sqlrows;

import java.sql.Timestamp;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;

import play.db.ebean.Model;

import com.avaje.ebean.Ebean;
import com.avaje.ebean.SqlUpdate;
import com.avaje.ebean.annotation.Sql;

/**
 * トランザクション（サービス情報）ＤＢ部品
 * <p>
 * トランザクション（サービス情報）にアクセスするクラス
 * </p>
 * @author 那須智貴
 * @version 0.1　2014/05/15　初版
 * @version 0.2　2014/05/21　親子レコード関係追加
 */
@Entity
@Sql
public class TransServiceInfo extends Model {

	//	/** トランザクションサービスID */
	//	public String transServiceId;

	/** トランザクションID */
	public String transactionId;

	/** 顧客ＩＤ */
	public String kokyakuId;

	/** 契約ＩＤ */
	public String keiyakuId;

	/** サービスＩＤ */
	public String serviceId;

	//	/** トランザクション契約ＩＤ */
	//	public String transKeiyakuId;

	/** ステータス */
	public String status;

	/** サービスコード */
	public String serviceCd;

	/** 利用開始日 */
	public String riyouKaishiNengetsubi;

	/** 利用コース */
	public String riyouCourse;

	/** 利用プラン */
	public String riyouPlan;

	/** ＩＰｖ６アダプタ */
	public String ipv6Adapter;

	/** メールアドレス１ */
	public String mailaddress;

	/** ＴＴ－ｐｈｏｎｅ状態 */
	public String ttPhoneStatus;

	/** ソク割１５ */
	public String sokuwari15;

	/** 開通工事日 */
	public String kaitsuuKoujibi;

	/** 工事予定日日 */
	public String koujiYoteibi;

	/** 従属サービスコード１ */
	public String dependServiceCd1;

	/** 従属サービスコード２ */
	@ManyToOne
	public String dependServiceCd2;

	/** 業務詳細 */
	public String businessDetail;

	/** 申込内容詳細 */
	public String applyDetail;

	/** 手続き種別 */
	public String procedureType;

	/** 手続き進捗キュー登録状況 */
	public String tourokuFlg;

	/** SOステータス */
	public String soStatus;

	/** 起動間隔 */
	public String intervalTime;

	/** 最終実行時刻 */
	public Timestamp lastRunDate;

	/** サービスワークフロー定義ファイル情報 */
	public String swfFileInfo;

	/** 論理削除年月日 */
	public String deleteDate;

	/** 作成者 */
	public String createUser;

	/** 最終更新者 */
	public String lastUpdateUser;

	/** 作成日時 */
	public Timestamp createDT;

	/** 最終更新日時 */
	public Timestamp lastUpdateDT;

	/**
	 * トランザクション（サービス）情報更新処理
	 * <p>
	 * ＯＲマッパーを使用せず、トランザクション情報に上書きする。<br>
	 * トランザクションＩＤ、サービスＩＤおよび、最終更新日時が、更新前の最終更新日時と<br>
	 * 同じレコードが存在しない場合、戻り値として、0 を返す。<br>
	 * </p>
	 * @param info トランザクション情報
	 * @param lastUpdateDT 更新後の最終更新日時
	 * @return 更新件数
	 * @author 那須智貴
	 */
	public static int updateTransactionService(models.entities.TransServiceInfo info, Timestamp lastUpdateDT) {

		// トランザクション（サービス）更新クエリを実行する。
		int modifiedCount = getUpdateTransactionService(info, lastUpdateDT).execute();

		return modifiedCount;
	}

	/**
	 * トランザクション更新クエリ取得処理
	 * <p>
	 * ＯＲマッパーを使用せず、トランザクションを更新するクエリを取得する。
	 * 更新条件は、トランザクションＩＤ、サービスＩＤと最終更新年月日が合致するレコードとする。
	 * </p>
	 * @param transServiceInfo 更新するトランザクション情報
	 * @param lastUpdateDT 更新後の最終更新日時
	 * @return トランザクションを更新するクエリ
	 * @author 那須智貴
	 */
	private static SqlUpdate getUpdateTransactionService(models.entities.TransServiceInfo transServiceInfo,
			Timestamp lastUpdateDT) {

		StringBuilder sql = new StringBuilder();
		sql.append("UPDATE ");
		sql.append("trans_service_info ");
		sql.append("SET ");
		sql.append("transaction_id = :transactionId, ");
		sql.append("kokyaku_id = :kokyakuId, ");
		sql.append("keiyaku_id = :keiyakuId, ");
		sql.append("service_id = :serviceId, ");
		//sql.append("trans_keiyaku_id = :transKeiyakuId, ");
		sql.append("status = :status, ");
		sql.append("service_cd = :serviceCd, ");
		sql.append("riyou_kaishi_nengetsubi = :riyouKaishiNengetsubi, ");
		sql.append("riyou_course = :riyouCourse, ");
		sql.append("riyou_plan = :riyouPlan, ");
		sql.append("ipv6adapter = :ipv6Adapter, ");
		sql.append("mailaddress = :mailaddress, ");
		sql.append("tt_phone_status = :ttPhoneStatus, ");
		sql.append("sokuwari15 = :sokuwari15, ");
		sql.append("kaitsuu_koujibi = :kaitsuuKoujibi, ");
		sql.append("kouji_yoteibi = :koujiYoteibi, ");
		sql.append("depend_service_cd1 = :dependServiceCd1, ");
		sql.append("depend_service_cd2 = :dependServiceCd2, ");
		sql.append("business_detail = :businessDetail, ");
		sql.append("apply_detail = :applyDetail, ");
		sql.append("procedure_type = :procedureType, ");
		sql.append("touroku_flg = :tourokuFlg, ");
		sql.append("so_status = :soStatus, ");
		sql.append("interval_time = :intervalTime, ");
		sql.append("last_run_date = :lastRunDate, ");
		sql.append("swf_file_info = :swfFileInfo, ");
		sql.append("delete_date = :deleteDate, ");
		sql.append("create_user = :createUser, ");
		sql.append("last_update_user = :lastUpdateUser, ");
		sql.append("create_dt = :createDT, ");
		sql.append("last_update_dt = :lastUpdateDT ");
		sql.append("WHERE ");
		sql.append("(");
		sql.append("transaction_id = :whereTransactionId AND ");
		sql.append("service_id = :whereServiceId AND ");
		sql.append("last_update_dt = :whereLastUpdateDT");
		sql.append(")");

		SqlUpdate update = Ebean.createSqlUpdate(sql.toString());
		//update.setParameter("transServiceId", transServiceInfo.transServiceId);
		update.setParameter("transactionId", transServiceInfo.transactionId);
		update.setParameter("kokyakuId", transServiceInfo.kokyakuId);
		update.setParameter("keiyakuId", transServiceInfo.keiyakuId);
		update.setParameter("serviceId", transServiceInfo.serviceId);
		update.setParameter("keiyakuId", transServiceInfo.keiyakuId);
		//update.setParameter("transKeiyakuId", transServiceInfo.transKeiyakuInfo.transKeiyakuId);
		update.setParameter("status", transServiceInfo.status);
		update.setParameter("serviceCd",
				(transServiceInfo.serviceMaster != null) ? transServiceInfo.serviceMaster.serviceCd : null);
		update.setParameter("riyouKaishiNengetsubi", transServiceInfo.riyouKaishiNengetsubi);
		update.setParameter("riyouCourse", transServiceInfo.riyouCourse);
		update.setParameter("riyouPlan", transServiceInfo.riyouPlan);
		update.setParameter("ipv6Adapter", transServiceInfo.ipv6Adapter);
		update.setParameter("mailaddress", transServiceInfo.mailaddress);
		update.setParameter("ttPhoneStatus", transServiceInfo.ttPhoneStatus);
		update.setParameter("sokuwari15", transServiceInfo.sokuwari15);
		update.setParameter("kaitsuuKoujibi", transServiceInfo.kaitsuuKoujibi);
		update.setParameter("koujiYoteibi", transServiceInfo.koujiYoteibi);
		update.setParameter("dependServiceCd1",
				(transServiceInfo.dependService1 != null) ? transServiceInfo.dependService1.serviceCd : null);
		update.setParameter("dependServiceCd2",
				(transServiceInfo.dependService2 != null) ? transServiceInfo.dependService2.serviceCd : null);
		update.setParameter("businessDetail", transServiceInfo.businessDetail);
		update.setParameter("applyDetail", transServiceInfo.applyDetail);
		update.setParameter("procedureType", transServiceInfo.procedureType);
		update.setParameter("tourokuFlg", transServiceInfo.tourokuFlg);
		update.setParameter("soStatus", transServiceInfo.soStatus);
		update.setParameter("intervalTime", transServiceInfo.intervalTime);
		update.setParameter("lastRunDate", lastUpdateDT);
		update.setParameter("swfFileInfo", transServiceInfo.swfFileInfo);
		update.setParameter("deleteDate", transServiceInfo.deleteDate);
		update.setParameter("createUser", transServiceInfo.createUser);
		update.setParameter("lastUpdateUser", "BatchUser");
		update.setParameter("createDT", transServiceInfo.createDT.toString());
		update.setParameter("lastUpdateDT", lastUpdateDT);
		update.setParameter("whereTransactionId", transServiceInfo.transactionId);
		update.setParameter("whereServiceId", transServiceInfo.serviceId);
		update.setParameter("whereLastUpdateDT", transServiceInfo.lastUpdateDT);

		return update;
	}

}
