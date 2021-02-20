package hw2_unit_testing.UnitTests;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.Random;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import hw2_unit_testing.statistic_block.ICorrelation;
import hw2_unit_testing.statistic_block.ITakeData;
import hw2_unit_testing.statistic_block.statistic_block;

class SafeValueTest {
	public static Random myRandom = new Random();

	public statistic_block statisticBlockTest;
	public statistic_block statisticBlockRegular;

	public ITakeData iTakeDataTest;

	public ICorrelation iCorrelationTest;

	@BeforeEach
	void setUp() throws Exception {
		statisticBlockRegular = new statistic_block();

		// create iTakeDataTest interface
		iTakeDataTest = new ITakeData() {
			@Override
			public double[] takeData(String ID, int year) {
				if (ID.equals("CompanyX")) {
					double[] d = new double[20];
					for (int i = 0; i < d.length; i++) {
						d[i] = 4;
					}
					return d;
				}
				return null; // if the id is not CompanyX return null
			}
		};

		// create iCorrelationTest interface
		iCorrelationTest = new ICorrelation() {
			@Override
			public double correlation(double[] d1, double[] d2) {
				return myRandom.nextDouble();
			}
		};

		// set the statistic block test with the function we created
		statisticBlockTest = new statistic_block(iTakeDataTest, statisticBlockRegular.iCorrelation);

	}

	@Test
	void RegularTest() {

		double expectedResult = 4.040872617272977; // The regular result

		statisticBlockRegular.connectToDB(); // initial connection to DB.

		double actualResult = statisticBlockRegular.safeValue("CompanyX", 2020);

		statisticBlockRegular.closeConnectionFromDB(); // close connection to DB.

		assertEquals(actualResult, expectedResult);
	}

	@Test
	void AboveThresholdTest() {
		iCorrelationTest = new ICorrelation() {
			@Override
			public double correlation(double[] d1, double[] d2) {
				return statistic_block.Threshold + 0.05;
			}
		};

		double expectedResult = 4.0;
		double res = statisticBlockTest.safeValue("CompanyX", 2020);

		assertEquals(expectedResult, res);

	}

	@Test
	void EqualThresholdTest() {
		iCorrelationTest = new ICorrelation() {
			@Override
			public double correlation(double[] d1, double[] d2) {
				return statistic_block.Threshold;
			}
		};

		double expectedResult = 4.0;
		double res = statisticBlockTest.safeValue("CompanyX", 2020);

		assertEquals(expectedResult, res);

	}

	@Test
	void BelowThresholdTest() {
		iCorrelationTest = new ICorrelation() {
			@Override
			public double correlation(double[] d1, double[] d2) {
				return statistic_block.Threshold - 0.05;
			}
		};

		double expectedResult = 4.0;
		double res = statisticBlockTest.safeValue("CompanyX", 2020);

		assertEquals(expectedResult, res);

	}

	@Test
	void EmptyIDTest() {
		double expectedResult = -1;
		double actualResult = statisticBlockTest.safeValue("", 2020);

		assertEquals(expectedResult, actualResult);
	}

	@Test
	void NegativeYearTest() {
		double expectedResult = -2;
		double actualResult = statisticBlockTest.safeValue("CompanyX", -1500);

		assertEquals(expectedResult, actualResult);
	}

	@Test
	void InvalidIDTest() {
		double expectedResult = -3;
		double actualResult = statisticBlockTest.safeValue("CompanyY", 2019);

		assertEquals(expectedResult, actualResult);
	}

}
