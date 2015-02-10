/**
 * 
 */
package utils;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import models.entities.NewServiceMaster;

/**
 * サービス名称変換クラス
 * <p>
 * 変換を取り扱う
 * </p>
 * @author 那須智貴
 * @version 0.1　2014/04/21　新規作成
 */
public final class ServiceUtil {
    private ServiceUtil() {
    }

    /**
     * サービス名称変換
     * <p>
     * サービス名称変換を行う。（コード⇒文字列）
     * </p>
     * @param strServiceCode サービスコード
     * @param lstServiceMaster サービスマスタ
     * @return 変換後の日付文字列
     * @author 那須智貴
     * @version 0.1　2014/03/17　新規作成
     */
    public static String convCodeToName(String strServiceCode, List<NewServiceMaster> lstServiceMaster) {

        String strServiceName = "";

        try {
            Iterator<NewServiceMaster> it = lstServiceMaster.iterator();
            while (it.hasNext()) {
                NewServiceMaster row = it.next();
                if (row.serviceCd.equals(strServiceCode))
                {
                    strServiceName = row.serviceMeishou;
                    break;
                }
            }
            return strServiceName;
        } catch (Exception e) {
            return strServiceName;
        }
    }
    
    /**
     * サービス分類フィルタリング処理
     * <p>
     * フィルタリングを行う。
     * </p>
     * @param strServiceBunruiCd サービス分類コード
     * @param lstServiceMaster サービスマスタ
     * @return サービス分類コードで絞ったマスタ
     * @author 那須智貴
     * @version 0.1　2014/08/11　新規作成
     */
    public static List<NewServiceMaster> fillterByServiceBunruiCd(String strServiceBunruiCd, List<NewServiceMaster> lstServiceMaster) {
        
        // インスタンス生成
        List<NewServiceMaster> result = new ArrayList<NewServiceMaster>();
        
        try {
            Iterator<NewServiceMaster> it = lstServiceMaster.iterator();
            while (it.hasNext()) {
                NewServiceMaster row = it.next();
                // 使用者が指定したサービス分類コードのみを返却
                if (row.serviceBunrui.serviceBunruiCd.equals(strServiceBunruiCd))
                {   
                    result.add(row);
                }
            }
            return result;
        } catch (Exception e) {
            return result;
        }
    }

}
