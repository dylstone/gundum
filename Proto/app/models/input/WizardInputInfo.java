package models.input;

import play.data.validation.Constraints.MaxLength;
import play.data.validation.Constraints.MinLength;
import play.data.validation.Constraints.Pattern;
import play.data.validation.Constraints.Required;

/**
 * ウィザード入力情報クラス
 * <p>
 * ウィザード入力情報を格納するクラス
 * </p>
 * @author 大平　司
 * @version 0.4　2014/04/09　プロト開発STEP4版
 */
public class WizardInputInfo {

	/** 利用コース */
	//@Required
	public String inputRiyouCourse;

	/** 利用コース（名称） */
	public String inputRiyouCourseName;

	/** 郵便番号１ */
	@Required
	@MaxLength(3)
	@MinLength(3)
	@Pattern("[0-9]+")
	public String inputZipNo1;

	/** 郵便番号２ */
	@Required
	@MaxLength(4)
	@MinLength(4)
	@Pattern("[0-9]+")
	public String inputZipNo2;

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

	/** 気付・様方 */
	@MaxLength(20)
	public String inputKidukeSama;

	/** 電話番号 */
	@Required
	@MaxLength(16)
	@Pattern("([+][0-9]{1,3}([ .-])?)?([(][0-9]{1,6}[)])?([0-9 .-]{1,32})(([A-Za-z :]{1,11})?[0-9]{1,4}?)")
	public String inputTelNo;

	/** 開通工事日 */
	//@Required
	@MaxLength(8)
	@Pattern("[0-9]+")
	public String inputKaitsuuKoujibi;

	/** 工事予定日 */
	//@Required
	@MaxLength(8)
	@Pattern("[0-9]+")
	public String inputKoujiYoteibi;

	/** IT-Phone申込 */
	//@Required
	public String inputItPhoneStatus;

}
