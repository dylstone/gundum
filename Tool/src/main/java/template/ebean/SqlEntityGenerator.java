package template.ebean;

import java.io.File;
import java.io.IOException;

import org.apache.commons.io.FileUtils;

import template.ebean.action.ERDReader;
import template.ebean.action.SqlEntityTemplate;
import template.ebean.model.TableEntity;
import template.ebean.model.WorkbookEntity;

public class SqlEntityGenerator {
	public static void generate(String fileName, String outputDirectory) {

		WorkbookEntity workbookEntity = new ERDReader().read(fileName);
		for (TableEntity tableEntity : workbookEntity.tableEntityList) {
			SqlEntityTemplate template = new SqlEntityTemplate();
			String text = template.generate(tableEntity);
			System.out.println(text);
//			try {
//				FileUtils.writeStringToFile(new File(outputDirectory + "\\"
//						+ tableEntity.tableName + ".java"), text);
//			} catch (IOException e) {
//				e.printStackTrace();
//			}
		}

	}
}
