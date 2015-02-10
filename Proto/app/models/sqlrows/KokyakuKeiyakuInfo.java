package models.sqlrows;

import javax.persistence.Entity;

import play.db.ebean.Model;

import com.avaje.ebean.Ebean;
import com.avaje.ebean.Query;
import com.avaje.ebean.RawSql;
import com.avaje.ebean.RawSqlBuilder;
import com.avaje.ebean.annotation.Sql;

/**
 * 顧客契約情報取得ＤＢ部品
 * <p>
 * 契約IDに紐づく顧客/契約情報を取得するクラス
 * </p>
 * @author 那須智貴
 * @version 0.3　2014/04/07　初版
 */
@Entity
@Sql
public class KokyakuKeiyakuInfo extends Model {
	
	/** 項番 */
    public String seq;
    
	/** 顧客ＩＤ */
    public String kokyakuId;
    
    /** 契約ＩＤ */
	public String keiyakuId;
	
	/** ユーザＩＤ */
    public String kokyakuUserId;
    
	/** 顧客氏名（姓） */
    public String lastName;

    /** 顧客氏名（名） */
    public String firstName;

	/** 電話番号 */
    public String telNo1;

	/**
	 * 顧客情報・契約情報抽出クエリ取得処理
	 * <p>
	 * ＯＲマッパーを使用せず、契約ＩＤに紐づく顧客情報と契約情報
	 * を抽出するクエリを取得する。
	 * </p>
	 * @return 契約ＩＤに紐づく顧客情報・契約情報を抽出するクエリ
	 * @author 大平　司
	 */
	public static Query<KokyakuKeiyakuInfo> find() {
		
		// SELECT文を生成する。
		StringBuilder sql = new StringBuilder();
		sql.append("SELECT ");
		sql.append("ROWNUM AS seq, ");
		sql.append("tbl1.kokyaku_id, ");
		sql.append("tbl1.kokyaku_user_id, ");
		sql.append("tbl1.last_name, ");
		sql.append("tbl1.first_name, ");
		sql.append("tbl1.tel_no1, ");
		sql.append("tbl2.keiyaku_id ");
		sql.append("FROM kokyaku_info tbl1 ");
		sql.append("INNER JOIN keiyaku_info tbl2 ");
		sql.append("ON (tbl1.kokyaku_id = tbl2.kokyaku_id)");

		// フィールドとテーブルのカラムを結び付ける。
		RawSql rawSql = 
				RawSqlBuilder.parse(sql.toString())
				.columnMapping("tbl1.kokyaku_id", "kokyakuId")
				.columnMapping("tbl1.kokyaku_user_id", "kokyakuUserId")
				.columnMapping("tbl1.last_name", "lastName")
				.columnMapping("tbl1.first_name", "firstName")
				.columnMapping("tbl1.tel_no1", "telNo1")
				.columnMapping("tbl2.keiyaku_id", "keiyakuId")
				.create();

		// クエリーを生成する。
		Query<KokyakuKeiyakuInfo> query = Ebean.find(KokyakuKeiyakuInfo.class);
		query.setRawSql(rawSql);

		return query;
	}
	
}
