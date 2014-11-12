package ebean.model;

import java.util.List;

/**
 * テーブル論理設計書
 * 
 * @author 丁玉磊
 * 
 */
public class TableEntity {
	/** サブシステム名 */
	public String subSystemName;
	/** テーブル名 */
	public String tableLogicName;
	/** 物理名 */
	public String tableName;
	/** 用途 */
	public String usage;
	/** 作成日 */
	public String createdDate;
	/** 更新日 */
	public String updatedDate;
	/** 設計者 */
	public String writer;
	/***/
	public String version;

	private List<ColumnEntity> columnEntityList;

	private ImportManager importManager;

	public ImportManager getImportManager() {
		return importManager;
	}

	public void setImportManager(ImportManager importManager) {
		this.importManager = importManager;
	}

	public List<ColumnEntity> getColumnEntityList() {
		return columnEntityList;
	}

	public void setColumnEntityList(List<ColumnEntity> columnEntityList) {
		this.columnEntityList = columnEntityList;
	}

}
