package common.business;

import javax.persistence.OptimisticLockException;

import models.entities.IdInfo;

/**
 * ＩＤ管理
 * <p>
 * 各テーブルで使用するＩＤを管理するクラス
 * </p>
 * @author 大平　司
 * @version 0.1　2014/04/10　初版
 */
public final class IdKanri {
    private IdKanri() {
    }

    /**
     * ＩＤ取得処理
     * <p>
     * 指定したＩＤコードに該当する使用可能なＩＤを取得する。<br>
     * </p>
     * @param idCd ＩＤコード
     * @return ＩＤコードに該当する使用可能なＩＤ（ＩＤ生成失敗時は、空文字を返す。）
     * @author 大平　司
     */
    public static String getId(String idCd) {

        String retId = "";
        IdInfo idInfo;

        while (true) {
            try {
                // ＩＤ情報から、該当レコードを取得する。
                idInfo = IdInfo.find.byId(idCd);

                if (idInfo == null) {
                    // レコードが存在しない場合、ＩＤを空文字として、処理を終了する。
                    retId = "";
                    break;
                }

                // 取得したレコードのＩＤ番号をインクリメントする。
                idInfo.idNo = idInfo.idNo + 1;

                // レコードを更新する。
                idInfo.update();

            } catch (OptimisticLockException e) {
                // 楽観排他エラーの場合、レコード取得からやり直す。
                e.printStackTrace();
                continue;
            } catch (Exception e) {
                // 楽観排他エラー以外のエラーの場合、ＩＤを空文字として、処理を終了する。
                e.printStackTrace();
                retId = "";
                break;
            }

            // レコードの更新ができた場合、ＩＤを生成して、処理を終了する。
            String idNoForm = "%1$0" + idInfo.idNoDigit.toString() + "d";
            retId = idInfo.idTypeString.trim() + String.format("%07d", idInfo.idNo);
            break;
        }

        return retId;
    }

}
