package models;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public final class PayMethodTypes {
	private PayMethodTypes() {
	}

	public static List<String> list() {
		List<String> all = new ArrayList<String>();
		all.add("口座自振");
		all.add("郵便振替");
		all.add("クレジット(カード引落)");
		all.add("TOKAI(自振)");
		all.add("CVS入金(月次CVS)");
		all.add("振込");
		all.add("法人請求／A4請求(振込)");
		all.add("現金");
		all.add("ペイジー(Pay-easy)");
		all.add("郵便局払込");
		all.add("KDDI請求");
		all.add("未請求");
		return all;
	}

	public static Map<Integer, String> map() {
		Map<Integer, String> all = new HashMap<Integer, String>();
		all.put(0, "口座自振");
		all.put(1, "郵便振替");
		all.put(2, "クレジット(カード引落)");
		all.put(3, "TOKAI(自振)");
		all.put(4, "CVS入金(月次CVS)");
		all.put(5, "振込");
		all.put(6, "法人請求／A4請求(振込)");
		all.put(7, "現金");
		all.put(8, "ペイジー(Pay-easy)");
		all.put(9, "郵便局払込");
		all.put(10, "KDDI請求");
		all.put(11, "未請求");
		return all;
	}
}
