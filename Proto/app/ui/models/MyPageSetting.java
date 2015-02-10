package ui.models;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

import play.data.validation.Constraints.Required;
import play.db.ebean.Model;

import com.avaje.ebean.annotation.UpdatedTimestamp;

/***
 * サブフレーム1用モデル.
 * 
 * @author CTC
 * 
 */
@Entity
@Table(uniqueConstraints = { @UniqueConstraint(columnNames = { "user_id",
        "kinou_id", "setting_id", "subframe_id" }) })
public class MyPageSetting extends Model {

    /** id */
    @Id
    public Integer id;

    /** ユーザＩＤ */
    @Required
    public String userId;

    /** 機能ＩＤ */
    @Required
    public String kinouId;

    /** 設定ＩＤ */
    @Required
    public String settingId;

    /** サブフレームID */
    @Required
    public String subframeId;

    /** 横位置 */
    public Integer x;

    /** 縦位置 */
    public Integer y;

    /** 幅 */
    public Integer width;

    /** 高さ */
    public Integer height;

    /** 前後位置 */
    public Integer zIndex;

    /** 表示状態 */
    public String visiblity;

    /** 更新年月日 */
    @UpdatedTimestamp
    public Date updateAt;

    /**
     * ファインダ
     */
    public static Finder<Integer, MyPageSetting> find = new Finder<Integer, MyPageSetting>(
            Integer.class, MyPageSetting.class);

}