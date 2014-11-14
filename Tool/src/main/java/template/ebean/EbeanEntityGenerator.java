package template.ebean;

import java.io.File;
import java.io.IOException;

import org.apache.commons.io.FileUtils;

import template.ebean.action.ERDReader;
import template.ebean.action.EntityTemplate;
import template.ebean.model.TableEntity;
import template.ebean.model.WorkbookEntity;

public class EbeanEntityGenerator {
	public static void generate(String fileName, String outputDirectory) {

		WorkbookEntity workbookEntity = new ERDReader().read(fileName);
		for (TableEntity tableEntity : workbookEntity.tableEntityList) {
			EntityTemplate template = new EntityTemplate();
			String text = template.generate(tableEntity);
			try {
				FileUtils.writeStringToFile(new File(outputDirectory + "\\"
						+ tableEntity.entityName + ".java"), text);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

	}
}
