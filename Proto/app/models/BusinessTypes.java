package models;

import java.util.ArrayList;
import java.util.List;

public final class BusinessTypes {
	private BusinessTypes() {
	}

	public static List<String> list() {
		List<String> all = new ArrayList<String>();
		all.add("ISP");
		all.add("その他");
		return all;
	}
}
