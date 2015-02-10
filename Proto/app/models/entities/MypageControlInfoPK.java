package models.entities;

import javax.persistence.Column;
import javax.persistence.Embeddable;

/**
 * マイページ制御情報用複合主キークラス
 * <p>
 * マイページ制御情報の複合主キーを形成する。
 * </p>
 * @author 大平　司
 * @version 0.3　2014/03/14　STEP3対応版
 */
@Embeddable
public class MypageControlInfoPK {

	/** オペレータＩＤ */
    @Column(nullable = true, columnDefinition ="varchar2(10)")
	public String operatorId;

	/** 機能 */
    @Column(nullable = true, columnDefinition ="char(8)")
	public String function;

	/** パターン */
    @Column(nullable = true, columnDefinition ="char(4)")
	public String pattern;

	/** サブフレームＩＤ */
    @Column(nullable = true, columnDefinition ="char(8)")
	public String subFrameId;

    
	/**
	 * コンストラクタ
	 * <p>
	 * コンストラクタ
	 * </p>
	 * @author 大平　司
	 */
    public MypageControlInfoPK() {}

	/**
	 * コンストラクタ（複合主キー格納）
	 * <p>
	 * クラスが保持する複合主キーに値を格納するコンストラクタ。
	 * </p>
	 * @param operatorId オペレータＩＤ
	 * @param function 機能
	 * @param pattern パターン
	 * @param subFrameId サブフレームＩＤ
	 * @author 大平　司
	 */
    public MypageControlInfoPK(String operatorId, String function, String pattern, String subFrameId) {
        this.operatorId = operatorId;
        this.function = function;
        this.pattern = pattern;
        this.subFrameId = subFrameId;
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
        if (other instanceof MypageControlInfoPK) {
            final MypageControlInfoPK otherMypageControlInfoPK = 
            		(MypageControlInfoPK)other;
            final boolean areEqual = 
            		(
            		otherMypageControlInfoPK.operatorId.equals(this.operatorId) && 
           			otherMypageControlInfoPK.function.equals(this.function) && 
           			otherMypageControlInfoPK.pattern.equals(this.pattern) && 
           			otherMypageControlInfoPK.subFrameId.equals(this.subFrameId)
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
        		this.operatorId.hashCode() + 
        		this.function.hashCode() + 
        		this.pattern.hashCode() + 
        		this.subFrameId.hashCode()
        		);
    }

}
