package template.ebean.model;

public class ColumnEntity {
	/** No. */
	public int columnId;

	/** 項目名 */
	public String columnLogicName;

	/** 物理名 */
	public String columnName;

	public String propertyName;

	/** 型 */
	public String type;

	/** 桁 */
	public String length;

	/** NULL不可 */
	public boolean nullable;

	/** PK */
	public int primaryKey;

	/** IDX1 */
	public int idx1;

	/** IDX2 */
	public int idx2;

	/** IDX3 */
	public int idx3;

	/** IDX4 */
	public int idx4;

	/** IDX5 */
	public int idx5;

	/** 初期値 */
	public String initValue;

	/** FK */
	public String foreignKey;

	/** 設定値の説明 */
	public String desc;

	/** 備考 */
	public String remark;

	public boolean updatable;

	public boolean isCreateDt;

	public boolean isLastUpdateDt;

	@Override
	public String toString() {
		return "ColumnEntity [columnId=" + columnId + ", columnLogicName="
				+ columnLogicName + ", columnName=" + columnName
				+ ", propertyName=" + propertyName + ", type=" + type
				+ ", length=" + length + ", nullable=" + nullable
				+ ", primaryKey=" + primaryKey + ", idx1=" + idx1 + ", idx2="
				+ idx2 + ", idx3=" + idx3 + ", idx4=" + idx4 + ", idx5=" + idx5
				+ ", initValue=" + initValue + ", foreignKey=" + foreignKey
				+ ", desc=" + desc + ", remark=" + remark + ", updatable="
				+ updatable + ", isCreateDt=" + isCreateDt
				+ ", isLastUpdateDt=" + isLastUpdateDt + "]";
	}
}
