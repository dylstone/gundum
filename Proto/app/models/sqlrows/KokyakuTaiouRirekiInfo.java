package models.sqlrows;

import java.sql.Timestamp;

import javax.persistence.Entity;

import play.db.ebean.Model;

import com.avaje.ebean.Ebean;
import com.avaje.ebean.Query;
import com.avaje.ebean.RawSql;
import com.avaje.ebean.RawSqlBuilder;
import com.avaje.ebean.SqlUpdate;
import com.avaje.ebean.annotation.Sql;

/**
 * 顧客情報・対応履歴ＤＢ部品
 * <p>
 * 顧客情報に紐づく対応履歴にアクセスするクラス
 * </p>
 * @author 大平　司
 * @version 0.4　2014/04/14　プロト開発STEP4版
 */
@Entity
@Sql
public class KokyakuTaiouRirekiInfo extends Model {

	/** 顧客対応ＩＤ */
	public String taiouId;

	/** 顧客ＩＤ */
	public String kokyakuId;
	
	/** 顧客姓 */
    public String kokyakuLastName;

	/** 顧客名 */
    public String kokyakuFirstName;

	/** 顧客姓（ふりがな） */
    public String kokyakuLastNameFurigana;

	/** 顧客名（ふりがな） */
    public String kokyakuFirstNameFurigana;

	/** 連絡者氏名 */
	public String renrakushaName;

	/** 電話番号 */
	public String telNo;

	/** 対応日時 */
	public String taiouNichiji;

	/** 対応オペレータＩＤ */
	public String taiouOperatorId;

	/** 対応オペレータ氏名 */
	public String operatorName;

	/** 対応オペレータ所属 */
	public String operatorDepartment;

	/** 関連顧客対応ＩＤ */
	public String kanrenClientTaioId;

	/** 問い合わせ内容 */
	public String toiawaseNaiyou;

	/** 問い合わせ種別 */
	public String toiawaseSyubetsu;

	/** 対応内容 */
	public String taiouNaiyou;

	/** 対応結果 */
	public String taiouKekka;

	/** 通知内容 */
	public String tsuuchiNaiyou;

	/** 履歴登録年月日 */
	public String rirekiTourokuNengetsubi;

	/** 履歴登録オペレータＩＤ */
	public String rirekiTourokuOperatorId;

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
	 * 顧客情報・対応履歴抽出クエリ取得処理
	 * <p>
	 * ＯＲマッパーを使用せず、顧客ＩＤに紐づく顧客情報と対応履歴および、<br>
	 * 対応履歴の対応オペレータＩＤに紐づくオペレータマスタを抽出するクエリを取得する。
	 * </p>
	 * @return 顧客ＩＤに紐づく顧客情報・対応履歴を抽出するクエリ
	 * @author 大平　司
	 */
	public static Query<KokyakuTaiouRirekiInfo> find() {
		
		// SELECT文を生成する。
		StringBuilder sql = new StringBuilder();
		sql.append("SELECT ");
		sql.append("tbl1.last_name, ");
		sql.append("tbl1.first_name, ");
		sql.append("tbl1.last_name_furigana, ");
		sql.append("tbl1.first_name_furigana, ");
		sql.append("tbl2.taiou_id, ");
		sql.append("tbl2.kokyaku_id, ");
		sql.append("tbl2.renrakusha_name, ");
		sql.append("tbl2.tel_no, ");
		sql.append("tbl2.taiou_nichiji, ");
		sql.append("tbl2.taiou_operator_id, ");
		sql.append("tbl3.operator_name, ");
		sql.append("tbl3.operator_department, ");
		sql.append("tbl2.kanren_client_taiou_id, ");
		sql.append("tbl2.toiawase_naiyou, ");
		sql.append("tbl2.toiawase_syubetsu, ");
		sql.append("tbl2.taiou_naiyou, ");
		sql.append("tbl2.taiou_kekka, ");
		sql.append("tbl2.tsuuchi_naiyou, ");
		sql.append("tbl2.rireki_touroku_nengetsubi, ");
		sql.append("tbl2.rireki_touroku_operator_id, ");
 		sql.append("tbl2.delete_date, ");
 		sql.append("tbl2.create_user, ");
		sql.append("tbl2.last_update_user, ");
		sql.append("tbl2.create_dt, ");
		sql.append("tbl2.last_update_dt ");
		sql.append("FROM kokyaku_info tbl1 ");
		sql.append("INNER JOIN taiou_rireki tbl2 ");
		sql.append("ON (tbl1.kokyaku_id = tbl2.kokyaku_id) ");
		sql.append("LEFT OUTER JOIN operator_master tbl3 ");
		sql.append("ON (tbl2.taiou_operator_id = tbl3.operator_id)");

		// フィールドとテーブルのカラムを結び付ける。
		RawSql rawSql = 
				RawSqlBuilder.parse(sql.toString())
				.columnMapping("tbl1.last_name", "kokyakuLastName")
				.columnMapping("tbl1.first_name", "kokyakuFirstName")
				.columnMapping("tbl1.last_name_furigana", "kokyakuLastNameFurigana")
				.columnMapping("tbl1.first_name_furigana", "kokyakuFirstNameFurigana")
				.columnMapping("tbl2.taiou_id", "taiouId")
				.columnMapping("tbl2.kokyaku_id", "kokyakuId")
				.columnMapping("tbl2.renrakusha_name", "renrakushaName")
				.columnMapping("tbl2.tel_no", "telNo")
				.columnMapping("tbl2.taiou_nichiji", "taiouNichiji")
				.columnMapping("tbl2.taiou_operator_id", "taiouOperatorId")
				.columnMapping("tbl3.operator_name", "operatorName")
				.columnMapping("tbl3.operator_department", "operatorDepartment")
				.columnMapping("tbl2.kanren_client_taiou_id", "kanrenClientTaioId")
				.columnMapping("tbl2.toiawase_naiyou", "toiawaseNaiyou")
				.columnMapping("tbl2.toiawase_syubetsu", "toiawaseSyubetsu")
				.columnMapping("tbl2.taiou_naiyou", "taiouNaiyou")
				.columnMapping("tbl2.taiou_kekka", "taiouKekka")
				.columnMapping("tbl2.tsuuchi_naiyou", "tsuuchiNaiyou")
				.columnMapping("tbl2.rireki_touroku_nengetsubi", "rirekiTourokuNengetsubi")
				.columnMapping("tbl2.rireki_touroku_operator_id", "rirekiTourokuOperatorId")
				.columnMapping("tbl2.delete_date", "deleteDate")
				.columnMapping("tbl2.create_user", "createUser")
				.columnMapping("tbl2.last_update_user", "lastUpdateUser")
				.columnMapping("tbl2.create_dt", "createDT")
				.columnMapping("tbl2.last_update_dt", "lastUpdateDT")
				.create();

		// クエリーを生成する。
		Query<KokyakuTaiouRirekiInfo> query = Ebean.find(KokyakuTaiouRirekiInfo.class);
		query.setRawSql(rawSql);

		return query;
	}
	
	/**
	 * 対応履歴情報登録処理
	 * <p>
	 * ＯＲマッパーを使用せず、指定した顧客情報・対応履歴を対応履歴テーブルに登録する。<br>
	 * 対応履歴テーブルに同じ顧客対応ＩＤが存在する場合、Exceptionエラーとする。<br>
	 * （Model.saveメソッドと同様。）
	 * </p>
	 * @param taiouRireki 顧客情報・対応履歴
	 * @return 登録件数
	 * @author 大平　司
	 */
	public static int insertTaiouRireki(KokyakuTaiouRirekiInfo taiouRireki) {

		// 対応履歴登録クエリを実行する。
		int modifiedCount = getInsertQueryTaiouRireki(taiouRireki).execute();

		return modifiedCount;
	}
	
	/**
	 * 対応履歴情報更新処理
	 * <p>
	 * ＯＲマッパーを使用せず、指定した顧客情報・対応履歴を対応履歴テーブルに上書きする。<br>
	 * 対応履歴テーブルに同じ顧客対応ＩＤおよび、最終更新日時が、更新前の最終更新日時と<br>
	 * 同じレコードが存在しない場合、戻り値として、0 を返す。<br>
	 * </p>
	 * @param taiouRireki 顧客情報・対応履歴
	 * @param lastUpdateDT 更新前の最終更新日時
	 * @return 更新件数
	 * @author 大平　司
	 */
	public static int updateTaiouRireki(KokyakuTaiouRirekiInfo taiouRireki, Timestamp lastUpdateDT) {

		// 対応履歴更新クエリを実行する。
		int modifiedCount = getUpdateQueryTaiouRireki(taiouRireki, lastUpdateDT).execute();

		return modifiedCount;
	}
	
	/**
	 * 対応履歴登録クエリ取得処理
	 * <p>
	 * ＯＲマッパーを使用せず、対応履歴を登録するクエリを取得する。
	 * </p>
	 * @param taiouRireki 登録する顧客情報・対応履歴
	 * @return 対応履歴を登録するクエリ
	 * @author 大平　司
	 */
	private static SqlUpdate getInsertQueryTaiouRireki(KokyakuTaiouRirekiInfo taiouRireki) {

		StringBuilder sql = new StringBuilder();
		sql.append("INSERT ");
		sql.append("INTO taiou_rireki ");
		sql.append("(");
		sql.append("taiou_id, ");
		sql.append("kokyaku_id, ");
		sql.append("renrakusha_name, ");
		sql.append("tel_no, ");
		sql.append("taiou_nichiji, ");
		sql.append("taiou_operator_id, ");
		sql.append("kanren_client_taiou_id, ");
		sql.append("toiawase_naiyou, ");
		sql.append("toiawase_syubetsu, ");
		sql.append("taiou_naiyou, ");
		sql.append("taiou_kekka, ");
		sql.append("tsuuchi_naiyou, ");
		sql.append("rireki_touroku_nengetsubi, ");
		sql.append("rireki_touroku_operator_id, ");
		sql.append("delete_date, ");
		sql.append("create_user, ");
		sql.append("last_update_user, ");
		sql.append("create_dt, ");
		sql.append("last_update_dt ");
		sql.append(") ");
		sql.append("VALUES ");
		sql.append("(");
		sql.append(":taiouId, ");
		sql.append(":kokyakuId, ");
		sql.append(":renrakushaName, ");
		sql.append(":telNo, ");
		sql.append(":taiouNichiji, ");
		sql.append(":taiouOperatorId, ");
		sql.append(":kanrenClientTaiouId, ");
		sql.append(":toiawaseNaiyou, ");
		sql.append(":toiawaseSyubetsu, ");
		sql.append(":taiouNaiyou, ");
		sql.append(":taiouKekka, ");
		sql.append(":tsuuchiNaiyou, ");
		sql.append(":rirekiTourokuNengetsubi, ");
		sql.append(":rirekiTourokuOperatorId, ");
		sql.append(":deleteDate, ");
		sql.append(":createUser, ");
		sql.append(":lastUpdateUser, ");
		sql.append(":createDT, ");
		sql.append(":lastUpdateDT");
		sql.append(")");

		SqlUpdate update = Ebean.createSqlUpdate(sql.toString());
		update.setParameter("taiouId", taiouRireki.taiouId);
		update.setParameter("kokyakuId", taiouRireki.kokyakuId);
		update.setParameter("renrakushaName", taiouRireki.renrakushaName);
		update.setParameter("telNo", taiouRireki.telNo);
		update.setParameter("taiouNichiji", taiouRireki.taiouNichiji);
		update.setParameter("taiouOperatorId", taiouRireki.taiouOperatorId);
		update.setParameter("kanrenClientTaiouId", taiouRireki.kanrenClientTaioId);
		update.setParameter("toiawaseNaiyou", taiouRireki.toiawaseNaiyou);
		update.setParameter("toiawaseSyubetsu", taiouRireki.toiawaseSyubetsu);
		update.setParameter("taiouNaiyou", taiouRireki.taiouNaiyou);
		update.setParameter("taiouKekka", taiouRireki.taiouKekka);
		update.setParameter("tsuuchiNaiyou", taiouRireki.tsuuchiNaiyou);
		update.setParameter("rirekiTourokuNengetsubi", taiouRireki.rirekiTourokuNengetsubi);
		update.setParameter("rirekiTourokuOperatorId", taiouRireki.rirekiTourokuOperatorId);
		update.setParameter("deleteDate", taiouRireki.deleteDate);
		update.setParameter("createUser", taiouRireki.createUser);
		update.setParameter("lastUpdateUser", taiouRireki.lastUpdateUser);
		update.setParameter("createDT", taiouRireki.createDT);
		update.setParameter("lastUpdateDT", taiouRireki.lastUpdateDT);

		return update;
	}
	
	/**
	 * 対応履歴更新クエリ取得処理
	 * <p>
	 * ＯＲマッパーを使用せず、対応履歴を更新するクエリを取得する。
	 * 更新条件は、顧客対応ＩＤと最終更新年月日が合致するレコードとする。
	 * </p>
	 * @param taiouRireki 更新する顧客情報・対応履歴
	 * @param lastUpdateDT 更新前の最終更新日時
	 * @return 対応履歴を更新するクエリ
	 * @author 大平　司
	 */
	private static SqlUpdate getUpdateQueryTaiouRireki(KokyakuTaiouRirekiInfo taiouRireki, Timestamp lastUpdateDT) {

		StringBuilder sql = new StringBuilder();
		sql.append("UPDATE ");
		sql.append("taiou_rireki ");
		sql.append("SET ");
		sql.append("taiou_id = :taiouId, ");
		sql.append("kokyaku_id = :kokyakuId, ");
		sql.append("renrakusha_name = :renrakushaName, ");
		sql.append("tel_no = :telNo, ");
		sql.append("taiou_nichiji = :taiouNichiji, ");
		sql.append("taiou_operator_id = :taiouOperatorId, ");
		sql.append("kanren_client_taiou_id = :kanrenClientTaiouId, ");
		sql.append("toiawase_naiyou = :toiawaseNaiyou, ");
		sql.append("toiawase_syubetsu = :toiawaseSyubetsu, ");
		sql.append("taiou_naiyou = :toiawaseNaiyou, ");
		sql.append("taiou_kekka = :taiouKekka, ");
		sql.append("tsuuchi_naiyou = :tsuuchiNaiyou, ");
		sql.append("rireki_touroku_nengetsubi = :rirekiTourokuNengetsubi, ");
		sql.append("rireki_touroku_operator_id = :rirekiTourokuOperatorId, ");
		sql.append("delete_date = :deleteDate, ");
		sql.append("create_user = :createUser, ");
		sql.append("last_update_user = :lastUpdateUser, ");
		sql.append("create_dt = :createDT, ");
		sql.append("last_update_dt = :lastUpdateDT ");
		sql.append("WHERE ");
		sql.append("(");
		sql.append("taiou_id = :whereTaiouId AND ");
		sql.append("last_update_dt = :whereLastUpdateDT");
		sql.append(")");

		SqlUpdate update = Ebean.createSqlUpdate(sql.toString());
		update.setParameter("taiouId", taiouRireki.taiouId);
		update.setParameter("kokyakuId", taiouRireki.kokyakuId);
		update.setParameter("renrakushaName", taiouRireki.renrakushaName);
		update.setParameter("telNo", taiouRireki.telNo);
		update.setParameter("taiouNichiji", taiouRireki.taiouNichiji);
		update.setParameter("taiouOperatorId", taiouRireki.taiouOperatorId);
		update.setParameter("kanrenClientTaiouId", taiouRireki.kanrenClientTaioId);
		update.setParameter("toiawaseNaiyou", taiouRireki.toiawaseNaiyou);
		update.setParameter("toiawaseSyubetsu", taiouRireki.toiawaseSyubetsu);
		update.setParameter("taiouNaiyou", taiouRireki.taiouNaiyou);
		update.setParameter("taiouKekka", taiouRireki.taiouKekka);
		update.setParameter("tsuuchiNaiyou", taiouRireki.tsuuchiNaiyou);
		update.setParameter("rirekiTourokuNengetsubi", taiouRireki.rirekiTourokuNengetsubi);
		update.setParameter("rirekiTourokuOperatorId", taiouRireki.rirekiTourokuOperatorId);
		update.setParameter("deleteDate", taiouRireki.deleteDate);
		update.setParameter("createUser", taiouRireki.createUser);
		update.setParameter("lastUpdateUser", taiouRireki.lastUpdateUser);
		update.setParameter("createDT", taiouRireki.createDT.toString());
		update.setParameter("lastUpdateDT", taiouRireki.lastUpdateDT.toString());
		update.setParameter("whereTaiouId", taiouRireki.taiouId);
		update.setParameter("whereLastUpdateDT", lastUpdateDT);

		return update;
	}

}
