package models.entities;

import javax.persistence.Column;
import javax.persistence.Embeddable;

/**
 * 画面項目制御情報用複合主キークラス
 * <p>
 * 画面項目制御情報の複合主キーを形成する。
 * </p>
 * @author 大平　司
 * @version 0.3　2014/03/14　STEP3対応版
 */
@Embeddable
public class GamenItemControlInfoPK {

	/** サブフレームＩＤ */
	@Column(nullable = false, columnDefinition = "char(8)")
    public String subFrameId;

	/** 画面部品ＩＤ */
	@Column(nullable = false, columnDefinition = "char(8)")
	public String gamenPartId;

	/** 画面項目ＩＤ */
	@Column(nullable = false, columnDefinition = "char(8)")
	public String gamenItemId;

	
	/**
	 * コンストラクタ
	 * <p>
	 * コンストラクタ
	 * </p>
	 * @author 大平　司
	 */
    public GamenItemControlInfoPK() {}

	/**
	 * コンストラクタ（複合主キー格納）
	 * <p>
	 * クラスが保持する複合主キーに値を格納するコンストラクタ。
	 * </p>
	 * @param subFrameId サブフレームＩＤ
	 * @param gamenPartId 画面部品ＩＤ
	 * @param gamenItemId 画面項目ＩＤ
	 * @author 大平　司
	 */
    public GamenItemControlInfoPK(String subFrameId, String gamenPartId, String gamenItemId) {
        this.subFrameId = subFrameId;
        this.gamenPartId = gamenPartId;
        this.gamenItemId = gamenItemId;
    }

    /**
	 * 複合主キー一致確認処理
	 * <p>
	 * 引数の複合主キーとクラスが保持する複合主キーが一致するか確認する。
	 * </p>
	 * @param other 複合主キーのオブジェクト
	 * @return 確認結果（true:一致、false：不一致）
	 * @author 大平　司
	 */
    public boolean equals(Object other) {
    	if (other instanceof GamenItemControlInfoPK) {
    		final GamenItemControlInfoPK otherGamenItemControlInfoPK = 
            	(GamenItemControlInfoPK)other;
            final boolean areEqual = 
            	(
            	otherGamenItemControlInfoPK.subFrameId.equals(this.subFrameId) && 
            	otherGamenItemControlInfoPK.gamenPartId.equals(this.gamenPartId) && 
            	otherGamenItemControlInfoPK.gamenItemId.equals(this.gamenItemId)
            	);
            
            return areEqual;
        }

    	return false;
    }

    /**
	 * ハッシュコード取得処理
	 * <p>
	 * クラスが保持する複合主キーで生成したハッシュコードを取得する。
	 * </p>
	 * @return ハッシュコード
	 * @author 大平　司
	 */
    public int hashCode() {
        return (
        		this.subFrameId.hashCode() + 
        		this.gamenPartId.hashCode() + 
        		this.gamenItemId.hashCode()
        		);
    }

}
