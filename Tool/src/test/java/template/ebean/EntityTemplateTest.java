package template.ebean;

import java.util.ArrayList;
import java.util.List;

import ebean.model.ColumnEntity;
import ebean.model.ImportManager;
import ebean.model.TableEntity;

public class EntityTemplateTest {
	public static void main(String[] args) {
		ImportManager importManager = new ImportManager();
		importManager.hasTimestamp = true;
		importManager.hasList = true;
		importManager.hasCascadeType = true;
		importManager.hasColumn = true;
		importManager.hasEntity = true;
		importManager.hasId = true;
		importManager.hasJoinColumn = true;
		importManager.hasOneToMany = true;
		importManager.hasOneToOne = true;
		importManager.hasVersion = true;
		importManager.hasCreatedTimestamp = true;
		importManager.hasUpdatedTimestamp = true;

		TableEntity tableEntity = new TableEntity();
		tableEntity.setImportManager(importManager);
		tableEntity.tableLogicName = "申込情報ＤＢ部品";
		tableEntity.usage = "申込情報のＯＲマッパー";
		tableEntity.writer = "大平　司";
		tableEntity.version = "0.4　2014/04/09　プロト開発STEP4版";
		tableEntity.tableName = "ApplyInfo";

		List<ColumnEntity> columnEntityList = new ArrayList<ColumnEntity>();
		ColumnEntity columnEntity = new ColumnEntity();
		columnEntity.columnLogicName = "申込ＩＤ";
		columnEntity.isPrimaryKey = true;
		columnEntity.nullable = false;
		columnEntity.type = "char";
		columnEntity.length = 10;
		columnEntity.columnName = "applyId";
		columnEntityList.add(columnEntity);

		tableEntity.setColumnEntityList(columnEntityList);

		EntityTemplate template = new EntityTemplate();
		String text = template.generate(tableEntity);
		System.out.println(text);

	}
}
