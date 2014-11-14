package template.ebean.action;

import java.util.List;
import template.ebean.model.TableEntity;
import template.ebean.model.ColumnEntity;
import utils.StringUtil;

public class SqlEntityTemplate {
	protected static String nl;

	public static synchronized SqlEntityTemplate create(String lineSeparator) {
		nl = lineSeparator;
		SqlEntityTemplate result = new SqlEntityTemplate();
		nl = null;
		return result;
	}

	public final String NL = nl == null ? (System.getProperties()
			.getProperty("line.separator")) : nl;
	protected final String TEXT_1 = "StringBuilder sql = new StringBuilder();"
			+ NL + "\t\tsql.append(\"UPDATE \");" + NL + "\t\tsql.append(\"";
	protected final String TEXT_2 = " \");" + NL + "\t\tsql.append(\"SET \");";
	protected final String TEXT_3 = "\t\t" + NL + "\t\tsql.append(\"";
	protected final String TEXT_4 = " = :";
	protected final String TEXT_5 = ", \");";
	protected final String TEXT_6 = NL + "\t\tsql.append(\"WHERE \");" + NL
			+ "\t\tsql.append(\"(\");";
	protected final String TEXT_7 = NL + "\t\tsql.append(\"";
	protected final String TEXT_8 = " = :where";
	protected final String TEXT_9 = " AND \");";
	protected final String TEXT_10 = NL
			+ "\t\tsql.append(\"last_update_dt = :whereLastUpdateDT\");" + NL
			+ "\t\tsql.append(\")\");" + NL + "" + NL
			+ "\t\tSqlUpdate update = Ebean.createSqlUpdate(sql.toString());";
	protected final String TEXT_11 = NL + "\t\tupdate.setParameter(\"";
	protected final String TEXT_12 = "\", ";
	protected final String TEXT_13 = ".";
	protected final String TEXT_14 = ");";
	protected final String TEXT_15 = NL + "\t\tupdate.setParameter(\"";
	protected final String TEXT_16 = "\", ";
	protected final String TEXT_17 = ".";
	protected final String TEXT_18 = ");";
	protected final String TEXT_19 = NL
			+ "\t\tupdate.setParameter(\"whereLastUpdateDT\", lastUpdateDT);";
	protected final String TEXT_20 = NL;

	public String generate(Object argument) {
		final StringBuffer stringBuffer = new StringBuffer();
		TableEntity tableEntity = (TableEntity) argument;
		stringBuffer.append(TEXT_1);
		stringBuffer.append(tableEntity.tableName);
		stringBuffer.append(TEXT_2);
		List<ColumnEntity> columnEntityList = tableEntity.getColumnEntityList();
		for (ColumnEntity columnEntity : columnEntityList) {
			stringBuffer.append(TEXT_3);
			stringBuffer.append(columnEntity.columnName);
			stringBuffer.append(TEXT_4);
			stringBuffer.append(columnEntity.propertyName);
			stringBuffer.append(TEXT_5);
		}
		stringBuffer.append(TEXT_6);
		for (ColumnEntity columnEntity : columnEntityList) {
			if (columnEntity.primaryKey > 0) {
				stringBuffer.append(TEXT_7);
				stringBuffer.append(columnEntity.columnName);
				stringBuffer.append(TEXT_8);
				stringBuffer.append(StringUtil
						.capitalize(columnEntity.propertyName));
				stringBuffer.append(TEXT_9);
			}
		}
		stringBuffer.append(TEXT_10);
		for (ColumnEntity columnEntity : columnEntityList) {
			stringBuffer.append(TEXT_11);
			stringBuffer.append(columnEntity.columnName);
			stringBuffer.append(TEXT_12);
			stringBuffer
					.append(StringUtil.decapitalize(tableEntity.entityName));
			stringBuffer.append(TEXT_13);
			stringBuffer.append(columnEntity.propertyName);
			stringBuffer.append(TEXT_14);
		}
		for (ColumnEntity columnEntity : columnEntityList) {
			if (columnEntity.primaryKey > 0) {
				stringBuffer.append(TEXT_15);
				stringBuffer.append(columnEntity.columnName);
				stringBuffer.append(TEXT_16);
				stringBuffer.append(StringUtil
						.decapitalize(tableEntity.entityName));
				stringBuffer.append(TEXT_17);
				stringBuffer.append(columnEntity.propertyName);
				stringBuffer.append(TEXT_18);
			}
		}
		stringBuffer.append(TEXT_19);
		stringBuffer.append(TEXT_20);
		return stringBuffer.toString();
	}
}
