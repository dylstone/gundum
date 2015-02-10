package models.sqlrows;

import javax.persistence.Entity;

import play.db.ebean.Model;

import com.avaje.ebean.Ebean;
import com.avaje.ebean.Query;
import com.avaje.ebean.RawSql;
import com.avaje.ebean.RawSqlBuilder;
import com.avaje.ebean.annotation.Sql;

/**
 * 申込受付情報取得ＤＢ部品
 * <p>
 * 申込承認画面で表示する項目を取得するクラス
 * </p>
 * @author 甲斐
 * @version 0.1　2014/07/17　初版
 */
@Entity
@Sql
public class ApplyUketsukeInfo extends Model {
	
    /** 項番 */
    public String seq;
    
    /** 申込受付キー */
    public String queueId;
    
	/** 申込ＩＤ */
    public String applyId;
    
    /** 顧客ＩＤ */
    public String kokyakuId;
    
    /** 契約ＩＤ */
    public String keiyakuId;
    
	/** 顧客氏名（姓） */
    public String lastName;

    /** 顧客氏名（名） */
    public String firstName;
    
    /** 電話番号 */
    public String telNo1;
    
    /** サービスＩＤ */
   	public String serviceId;
    
    /** アクティビティＩＤ */
    public String activityId;
    
    /** アクティビティ名称 */
    public String activityName;

	/**
	 * 申込受付情報抽出クエリ取得処理
	 * <p>
	 * ＯＲマッパーを使用せず、申込受付キーに紐づく申込履歴情報
	 * を抽出するクエリを取得する。
	 * </p>
	 * @return 申込受付キーに紐づく申込履歴情報を抽出するクエリ
	 * @author 甲斐
	 */
	public static Query<ApplyUketsukeInfo> find() {
		
		// SELECT文を生成する。
		StringBuilder sql = new StringBuilder();
		sql.append("SELECT ");
		sql.append("ROWNUM AS seq, ");
		sql.append("tbl1.apply_id, ");
		sql.append("tbl1.queue_id, ");
		sql.append("tbl1.kokyaku_id, ");
		sql.append("tbl1.keiyaku_id, ");
		sql.append("tbl2.last_name, ");
		sql.append("tbl2.first_name, ");
		sql.append("tbl2.tel_no1, ");
		sql.append("tbl1.service_id, ");
		sql.append("tbl1.activity_id, ");
		sql.append("tbl3.activity_name ");
		sql.append("FROM apply_uketsuke_key tbl1 ");
		sql.append("INNER JOIN apply_kokyaku_info tbl2 ");
		sql.append("ON (tbl1.kokyaku_id = tbl2.kokyaku_id) ");
		sql.append("AND (tbl1.apply_id = tbl2.apply_id) ");
		sql.append("INNER JOIN activity_code_master tbl3 ");
		sql.append("ON (tbl1.activity_id = tbl3.activity_id) ");
		sql.append("ORDER BY tbl1.keiyaku_id, tbl1.queue_id");

		// フィールドとテーブルのカラムを結び付ける。
		RawSql rawSql = 
				RawSqlBuilder.parse(sql.toString())
				.columnMapping("tbl1.apply_id", "applyId")
				.columnMapping("tbl1.queue_id", "queueId")
				.columnMapping("tbl1.kokyaku_id", "kokyakuId")
				.columnMapping("tbl1.keiyaku_id", "keiyakuId")
				.columnMapping("tbl2.last_name", "lastName")
				.columnMapping("tbl2.first_name", "firstName")
				.columnMapping("tbl2.tel_no1", "telNo1")
				.columnMapping("tbl1.service_id", "serviceId")
				.columnMapping("tbl1.activity_id", "activityId")
				.columnMapping("tbl3.activity_name", "activityName")
				.create();

		// クエリーを生成する。
		Query<ApplyUketsukeInfo> query = Ebean.find(ApplyUketsukeInfo.class);
		query.setRawSql(rawSql);

		return query;
	}
	
}
