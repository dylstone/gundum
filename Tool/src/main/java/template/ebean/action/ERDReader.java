package template.ebean.action;

import java.io.FileInputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;

import template.ebean.model.ColumnEntity;
import template.ebean.model.ImportManager;
import template.ebean.model.TableEntity;
import template.ebean.model.WorkbookEntity;
import utils.PersistenceConvention;
import utils.PersistenceConventionImpl;

public class ERDReader {
	public WorkbookEntity read(String fileName) {
		WorkbookEntity wbe = new WorkbookEntity();
		try {
			InputStream inp = new FileInputStream(fileName);
			Workbook wb = WorkbookFactory.create(inp);
			for (int i = 0; i < wb.getNumberOfSheets(); i++) {
				Sheet sheet = wb.getSheetAt(i);
				if (!wbe.excludeSheetNameList.contains(sheet.getSheetName())) {
					TableEntity tableEntity = this.parseSheet(sheet);
					wbe.tableEntityList.add(tableEntity);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return wbe;
	}

	public TableEntity parseSheet(Sheet sheet) {
		TableEntity tableEntity = new TableEntity();
		List<ColumnEntity> columnEntityList = new ArrayList<ColumnEntity>();
		tableEntity.setColumnEntityList(columnEntityList);
		// [1] parsing Excel sheet to get the table info
		try {
			int rowNo = 0;
			while (sheet.getRow(rowNo) != null) {
				Row row = sheet.getRow(rowNo);
				for (int columnNo = 0; columnNo < 18; columnNo++) {
					Cell cell = row.getCell(columnNo);
					try {
						// System.out.println(rowNo + ":" + columnNo + " "
						// + cell.getStringCellValue());
					} catch (Exception e) {
						// System.out.println(rowNo + ":" + columnNo + " "
						// + (cell == null ? "null" : cell.getCellType()));
					}
				}

				switch (rowNo) {
				case 0:// Header key
						// テーブル論理設計書
					String t0 = row.getCell(0).getStringCellValue();
					if (!t0.equals("テーブル論理設計書")) {
						System.err.println("error: " + "テーブル論理設計書");
					}

					// サブシステム名
					String t2 = row.getCell(2).getStringCellValue();
					if (!t2.equals("サブシステム名")) {
						System.err.println("error: " + "サブシステム名");
					}

					// テーブル名
					String t3 = row.getCell(3).getStringCellValue();
					if (!t3.equals("テーブル名")) {
						System.err.println("error: " + "テーブル名");
					}

					// 物理名
					String t8 = row.getCell(8).getStringCellValue();
					if (!t8.equals("物理名")) {
						System.err.println("error: " + "物理名");
					}

					// 用途
					String t12 = row.getCell(12).getStringCellValue();
					if (!t12.equals("用途")) {
						System.err.println("error: " + "用途");
					}

					// 作成日
					String t15 = row.getCell(15).getStringCellValue();
					if (!t15.equals("作成日")) {
						System.err.println("error: " + "作成日");
					}

					// 更新日
					String t16 = row.getCell(16).getStringCellValue();
					if (!t16.equals("更新日")) {
						System.err.println("error: " + "更新日");
					}

					// 設計者
					String t17 = row.getCell(17).getStringCellValue();
					if (!t17.equals("設計者")) {
						System.err.println("error: " + "設計者");
					}
					break;
				case 1:// Header value

					// サブシステム名
					String t_1_2_value = row.getCell(2).getStringCellValue();

					// テーブル名
					String t_1_3_value = row.getCell(3).getStringCellValue();

					// 物理名
					String t_1_8_value = row.getCell(8).getStringCellValue();

					// 用途
					String t_1_12_value = row.getCell(12).getStringCellValue();

					// 作成日
					Date t_1_15_value = row.getCell(15).getDateCellValue();

					// 更新日
					Date t_1_16_value = row.getCell(16).getDateCellValue();

					// 設計者
					String t_1_17_value = row.getCell(17).getStringCellValue();

					/** サブシステム名 */
					tableEntity.subSystemName = t_1_2_value;
					/** テーブル名 */
					tableEntity.tableLogicName = t_1_3_value;
					/** 物理名 */
					tableEntity.tableName = t_1_8_value;
					/** 用途 */
					tableEntity.usage = t_1_12_value;
					/** 作成日 */
					tableEntity.createdDate = t_1_15_value.toLocaleString();
					/** 更新日 */
					tableEntity.updatedDate = t_1_16_value.toGMTString();
					/** 設計者 */
					tableEntity.writer = t_1_17_value;

					break;
				case 2: // space line
					break;
				case 3:// Table Column Names
						// No.
					String t_3_0 = row.getCell(0).getStringCellValue();
					if (!t_3_0.equals("No.")) {
						System.err.println("error: " + "No.");
					}
					// 項目名
					String t_3_1 = row.getCell(1).getStringCellValue();
					if (!t_3_1.equals("項目名")) {
						System.err.println("error: " + "項目名");
					}
					// 物理名
					String t_3_2 = row.getCell(2).getStringCellValue();
					if (!t_3_2.equals("物理名")) {
						System.err.println("error: " + "物理名");
					}
					// 型
					String t_3_3 = row.getCell(3).getStringCellValue();
					if (!t_3_3.equals("型")) {
						System.err.println("error: " + "型");
					}
					// 桁
					String t_3_4 = row.getCell(4).getStringCellValue();
					if (!t_3_4.equals("桁")) {
						System.err.println("error: " + "桁");
					}
					// NULL不可
					String t_3_5 = row.getCell(5).getStringCellValue();
					if (!t_3_5.equals("NULL不可")) {
						System.err.println("error: " + "NULL不可");
					}
					// PK
					String t_3_6 = row.getCell(6).getStringCellValue();
					if (!t_3_6.equals("PK")) {
						System.err.println("error: " + "PK");
					}
					// IDX1
					String t_3_7 = row.getCell(7).getStringCellValue();
					if (!t_3_7.equals("IDX1")) {
						System.err.println("error: " + "IDX1");
					}
					// IDX2
					String t_3_8 = row.getCell(8).getStringCellValue();
					if (!t_3_8.equals("IDX2")) {
						System.err.println("error: " + "IDX2");
					}
					// IDX3
					String t_3_9 = row.getCell(9).getStringCellValue();
					if (!t_3_9.equals("IDX3")) {
						System.err.println("error: " + "IDX3");
					}
					// IDX4
					String t_3_10 = row.getCell(10).getStringCellValue();
					if (!t_3_10.equals("IDX4")) {
						System.err.println("error: " + "IDX4");
					}
					// IDX5
					String t_3_11 = row.getCell(11).getStringCellValue();
					if (!t_3_11.equals("IDX5")) {
						System.err.println("error: " + "IDX5");
					}
					// 初期値
					String t_3_12 = row.getCell(12).getStringCellValue();
					if (!t_3_12.equals("初期値")) {
						System.err.println("error: " + "初期値");
					}
					// FK
					String t_3_13 = row.getCell(13).getStringCellValue();
					if (!t_3_13.equals("FK")) {
						System.err.println("error: " + "FK");
					}
					// 設定値の説明
					String t_3_14 = row.getCell(14).getStringCellValue();
					if (!t_3_14.equals("設定値の説明")) {
						System.err.println("error: " + "設定値の説明");
					}
					// 備考
					String t_3_15 = row.getCell(15).getStringCellValue();
					if (!t_3_15.equals("備考")) {
						System.err.println("error: " + "備考");
					}
					break;
				default:
					try {
						// No.
						int t_n_0 = (int) row.getCell(0).getNumericCellValue();
						if (t_n_0 == 0) {
							break;
						}

						// 項目名
						String t_n_1 = row.getCell(1).getStringCellValue();
						// 物理名
						String t_n_2 = row.getCell(2).getStringCellValue();
						// 型
						String t_n_3 = row.getCell(3).getStringCellValue();
						// 桁
						String t_n_4 = row.getCell(4).getCellType() == Cell.CELL_TYPE_STRING ? row
								.getCell(4).getStringCellValue() : ""
								+ (int) row.getCell(4).getNumericCellValue();
						// NULL不可
						String t_n_5 = row.getCell(5).getStringCellValue();
						// PK
						int t_n_6 = (int) row.getCell(6).getNumericCellValue();
						// IDX1
						int t_n_7 = (int) row.getCell(7).getNumericCellValue();
						// IDX2
						int t_n_8 = (int) row.getCell(8).getNumericCellValue();
						// IDX3
						int t_n_9 = (int) row.getCell(9).getNumericCellValue();
						// IDX4
						int t_n_10 = (int) row.getCell(10)
								.getNumericCellValue();
						// IDX5
						// int t_n_11 = (int) row.getCell(11)
						// .getNumericCellValue();
						// 初期値
						String t_n_12 = row.getCell(12).getStringCellValue();
						// FK
						String t_n_13 = row.getCell(13).getStringCellValue();
						// 設定値の説明
						String t_n_14 = row.getCell(14).getStringCellValue();
						// 備考
						String t_n_15 = row.getCell(15).getStringCellValue();

						ColumnEntity columnEntity = new ColumnEntity();

						/** No. */
						columnEntity.columnId = t_n_0;

						/** 項目名 */
						columnEntity.columnLogicName = t_n_1;
						/** 物理名 */
						columnEntity.columnName = t_n_2;

						/** 型 */
						columnEntity.type = t_n_3.toLowerCase();

						/** 桁 */
						columnEntity.length = t_n_4;

						/** NULL不可 */
						columnEntity.nullable = t_n_5.equals("○") ? false
								: true;

						/** PK */
						columnEntity.primaryKey = t_n_6;

						/** IDX1 */
						columnEntity.idx1 = t_n_7;

						/** IDX2 */
						columnEntity.idx2 = t_n_8;

						/** IDX3 */
						columnEntity.idx3 = t_n_9;

						/** IDX4 */
						columnEntity.idx4 = t_n_10;

						/** IDX5 */
						// columnEntity.idx5 = t_n_11;

						/** 初期値 */
						columnEntity.initValue = t_n_12;

						/** FK */
						columnEntity.foreignKey = t_n_13;

						/** 設定値の説明 */
						columnEntity.desc = t_n_14;

						/** 備考 */
						columnEntity.remark = t_n_15;

						columnEntityList.add(columnEntity);

					} catch (Exception e) {
						e.printStackTrace();
						System.exit(0);
					}
					break;
				}
				rowNo++;
			}

		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
		/**
		 * [2]processing column properties
		 */
		PersistenceConvention pc = new PersistenceConventionImpl();

		// (a) table name
		tableEntity.entityName = pc
				.fromTableNameToEntityName(tableEntity.tableName);
		tableEntity.version = "0.1 初期版";

		for (ColumnEntity entity : columnEntityList) {
			// (b) column name
			entity.propertyName = pc
					.fromColumnNameToPropertyName(entity.columnName);
			// (c) @CreatedTimestamp
			if (entity.propertyName.equals("createDt")) {
				entity.isCreateDt = true;
			} else {
				entity.isCreateDt = false;
			}
			// (d) @Version @Version
			if (entity.propertyName.equals("lastUpdateDt")) {
				entity.isLastUpdateDt = true;
			} else {
				entity.isLastUpdateDt = false;
			}
			// (e) updatable
			if (entity.propertyName.equals("createDt")
					|| entity.columnName.equals("createUser")) {
				entity.updatable = false;
			} else {
				entity.updatable = true;
			}
			// foreign key
			// entity.

		}

		/**
		 * [3] import manager!
		 */
		ImportManager im = new ImportManager();
		tableEntity.setImportManager(im);
		for (ColumnEntity entity : columnEntityList) {
			if (entity.primaryKey > 0) {
				im.hasId = true;
			}
			if (entity.foreignKey != null
					&& !entity.foreignKey.trim().equals("")) {
				im.hasJoinColumn = true;
				im.hasCascadeType = true;
				// TODO:joinColumn
				switch (0) {
				case 0:
					im.hasOneToOne = true;
					im.hasOneToMany = false;
					im.hasManyToOne = false;
					break;
				case 1:
					im.hasOneToOne = false;
					im.hasOneToMany = true;
					im.hasManyToOne = false;
					break;
				case 2:
					im.hasOneToOne = false;
					im.hasOneToMany = false;
					im.hasManyToOne = true;
					break;
				default:
					System.err.println("err");
					return null;
				}
			}

		}
		// System.out.println(tableEntity);
		return tableEntity;
	}
}
