package models;

import java.util.ArrayList;
import java.util.List;

public final class BrandTypes {
	private BrandTypes() {
	}

	public static List<String> list() {
		List<String> all = new ArrayList<String>();
		all.add("TCOM");
		all.add("TNC");
		return all;
	}
}
