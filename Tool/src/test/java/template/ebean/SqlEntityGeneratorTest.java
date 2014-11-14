package template.ebean;

public class SqlEntityGeneratorTest {
	public static void main(String[] args) {

		SqlEntityGenerator eeg = new SqlEntityGenerator();
		eeg.generate("Workbook.xlsx", ".");

	}
}
