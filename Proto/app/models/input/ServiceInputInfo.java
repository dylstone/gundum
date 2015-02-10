package models.input;

import play.data.validation.Constraints.MaxLength;
import play.data.validation.Constraints.Required;

/**
 * ウィザード入力情報クラス
 * <p>
 * ウィザード入力情報を格納するクラス
 * </p>
 * @author 大平　司
 * @version 0.4　2014/04/09　プロト開発STEP4版
 */
public class ServiceInputInfo {

	/** 都道府県 */
	@Required
	@MaxLength(8)
	public String inputTodouhuken;

	/** 市区郡 */
	@Required
	@MaxLength(100)
	public String inputSiKuGun;

	/** 町村/大字 */
	@Required
	@MaxLength(100)
	public String inputTyousonOaza;

	/** 字/番地/号 */
	@Required
	@MaxLength(100)
	public String inputAzaBanchiGou;

	/** アパートマンション */
	@MaxLength(50)
	public String inputApartment;

	/** 部屋番号 */
	@MaxLength(30)
	public String inputApartmentRoomNo;
	
	/** サービスコード */
    public String inputServiceCd;
}
