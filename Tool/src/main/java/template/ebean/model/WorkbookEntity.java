package template.ebean.model;

import java.util.ArrayList;
import java.util.List;

public class WorkbookEntity {
	public List<TableEntity> tableEntityList;
	public String version;
	public List<String> excludeSheetNameList = new ArrayList<String>();

	public WorkbookEntity() {
		tableEntityList = new ArrayList<TableEntity>();
		excludeSheetNameList.add("バージョン管理");
		excludeSheetNameList.add("テーブル一覧");
		excludeSheetNameList.add("sample");
	}
}
