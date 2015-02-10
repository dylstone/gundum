package models.entities;

import java.lang.reflect.Field;

import javax.persistence.MappedSuperclass;

import play.db.ebean.Model;

/**
 * エンティティベースクラス
 * <p>
 * エンティティクラスが継承するスーパークラス
 * </p>
 * @author 大平　司
 * @version 0.5　2014/07/11　プロト開発STEP5版
 */
@MappedSuperclass
public class BaseEntity extends Model {

    /**
     * ファインダ取得
     * @param <I> 主キークラス型
     * @param <T> レコードクラス型
     * @param i 主キークラス型
     * @param t レコードクラス型
     * @return ファインダ
     */
    public static <I, T> Model.Finder<I, T> getFinder(Class<I> i, Class<T> t) {
        return new Model.Finder<I, T>(i, t);
    }

    /**
     * フィールド値取得処理
     * <p>
     * 指定したオブジェクトから指定したフィールドの値を取得する。
     * </p>
     * @param obj オブジェクト
     * @param strFldName フィールド名
     * @return フィールド値のオブジェクト
     * @author 大平　司
     */
    public static Object getFieldValue(Object obj, String strFldName) {

        Object ret = null;

        if (obj != null) {

            try {

                Field fldName = obj.getClass().getDeclaredField(strFldName);

                if (fldName != null) {
                    ret = fldName.get(obj);
                }

            } catch (SecurityException e) {
                // TODO 自動生成された catch ブロック
                e.printStackTrace();
                return null;
            } catch (NoSuchFieldException e) {
                // TODO 自動生成された catch ブロック
                e.printStackTrace();
                return null;
            } catch (IllegalArgumentException e) {
                // TODO 自動生成された catch ブロック
                e.printStackTrace();
                return null;
            } catch (IllegalAccessException e) {
                // TODO 自動生成された catch ブロック
                e.printStackTrace();
                return null;
            }
        }

        return ret;
    }

}
