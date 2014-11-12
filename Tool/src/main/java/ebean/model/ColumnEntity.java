package ebean.model;

public class ColumnEntity {
	/** No. */
	private int columnId;

	/** 項目名 */
	public String columnLogicName;

	/** 物理名 */
	public String columnName;

	/** 型 */
	public String type;

	/** 桁 */
	public int length;

	/** NULL不可 */
	public boolean nullable;

	/** PK */
	public boolean isPrimaryKey;

	/** IDX1 */
	public boolean isIdx1;

	/** IDX2 */
	public boolean isIdx2;

	/** IDX3 */
	public boolean isIdx3;

	/** IDX4 */
	public boolean isIdx4;

	/** IDX5 */
	public boolean isIdx5;

	/** 初期値 */
	public String initValue;

	/** FK */
	public String foreignKey;

	/** 設定値の説明 */
	public String desc;

	/** 備考 */
	public String remark;

	public boolean updatable;

}
