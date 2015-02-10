package common;

import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.xml.DomDriver;

/**
 * XML→JAVA変換クラス.
 * <p>
 * XMLからJAVAオブジェクトへの変換を行う共通部品
 * </p>
 * @author 甲斐正之
 * @version 0.1　2014/07/25　新規作成
 */
public class ChangeApplyInfoFromXml {

	public static ApplyInfoXml changeFromXml(String strXml) {

		XStream xs = new XStream(new DomDriver());

		ApplyInfoXml info = (ApplyInfoXml) xs.fromXML(strXml);

		return info;
	}

}
