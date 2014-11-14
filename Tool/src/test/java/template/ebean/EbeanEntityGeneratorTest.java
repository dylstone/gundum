package template.ebean;

public class EbeanEntityGeneratorTest {
	public static void main(String[] args) {

		EbeanEntityGenerator eeg = new EbeanEntityGenerator();
		eeg.generate("Workbook.xlsx", ".");

	}
}
