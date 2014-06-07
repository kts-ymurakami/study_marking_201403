package jp.ktsystem.kadai201403.y_murakami.Test;

import static org.junit.Assert.*;

import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

import jp.ktsystem.kadai201403.y_murakami.Kadai;
import jp.ktsystem.kadai201403.y_murakami.Common.ErrorCode;
import jp.ktsystem.kadai201403.y_murakami.Common.KadaiException;

import org.junit.Test;

public class TestClass {

	private static final String INPUT_DIR_PATH = "testInput\\";// 入力ディレクトリパス
	private static final String OUTPUT_DIR_PATH = "testOutput\\";// 出力ディレクトリパス
	private static final String EXPECTED_DIR_PATH = "testExpected\\";// 期待結果ディレクトリパス

	private InputStream createExpectedStream(String fileName)
			throws IOException {
		return new BufferedInputStream(new FileInputStream(EXPECTED_DIR_PATH
				+ fileName));
	}

	private InputStream createResultStream(String fileName) throws IOException {
		return new BufferedInputStream(new FileInputStream(OUTPUT_DIR_PATH
				+ fileName));
	}

	private InputStream createLv2ResultStream(String fileName) throws IOException {
		return new BufferedInputStream(new FileInputStream("validLv2Out\\"
				+ fileName));
	}


	/**
	 * 正常系テストクラス
	 *
	 * @param inputPath
	 * @param outputPath
	 * @param expectResultFilePath
	 */
	private void executeValidTest(String inputPath, String outputPath,
			String expectResultFilePath, boolean isLv1) {

		InputStream inputStrm = null;
		InputStream outputStrm = null;

		try {
			if (isLv1) {
				Kadai.parseWorkTimeData(INPUT_DIR_PATH + inputPath,
						OUTPUT_DIR_PATH + outputPath);
			} else {
				Kadai.parseWorkTimeDataLv2(INPUT_DIR_PATH + inputPath,
						OUTPUT_DIR_PATH + outputPath);
			}
			inputStrm = createResultStream(outputPath);
			outputStrm = createExpectedStream(expectResultFilePath);
			compareFile(inputStrm, outputStrm);
		} catch (KadaiException ex) {
			fail("予期せぬエラー。エラーコード：" + ex.getErrorCode());
		} catch (Exception ex) {
			fail("予期せぬエラー。エラーメッセージ：" + ex.getMessage());
		}
	}

	/**
	 * 異常系テストクラス
	 *
	 * @param inputPath
	 * @param outPutFilePath
	 * @param expectedErrCode
	 */
	/*private void executeInValidTest(String inputPath, String outPutFilePath,
			int expectedErrCode, boolean isLv1) {
		try {
			if (isLv1) {
				Kadai.parseWorkTimeData(INPUT_DIR_PATH + inputPath,
						OUTPUT_DIR_PATH + outPutFilePath);
			} else {
				Kadai.parseWorkTimeDataLv2(INPUT_DIR_PATH + inputPath,
						OUTPUT_DIR_PATH + outPutFilePath);
			}
			fail("予期せぬエラー");// エラーコードがthrowされるはずなのでここはfail
		} catch (KadaiException ex) {
			assertEquals(expectedErrCode, ex.getErrorCode());
		} catch (Exception ex) {
			fail("予期せぬエラー。エラーメッセージ：" + ex.getMessage());
		}
	}*/



	/**
	 * ２つのファイルを比較する
	 *
	 * @param inputStrm
	 * @param outputStrm
	 * @throws IOException
	 */
	private void compareFile(InputStream inputStrm, InputStream outputStrm)
			throws IOException {
		int input = -1;
		do {
			input = inputStrm.read();
			int output = outputStrm.read();
			assertEquals(input, output);
		} while (-1 < input);
	}

	@Test
	public void testCase01() {
		executeValidTest("validCase01.txt", "validCase01.txt",
				"validCase01.txt", true);
	}

	@Test
	public void testCase02() {
		executeValidTest("validCase02.txt", "validCase02.txt",
				"validCase02.txt", true);
	}

	@Test
	public void testCase03() {
		executeValidTest("validCase03.txt", "validCase03.txt",
				"validCase03.txt", true);
	}

	@Test
	public void testCase04() {
		executeValidTest("validCase04.txt", "validCase04.txt",
				"validCase04.txt", true);
	}

	@Test
	public void testCase05() {
		executeValidTest("validCase05.txt", "validCase05.txt",
				"validCase05.txt", true);
	}

	@Test
	public void testCase06() {
		try {
			Kadai.parseWorkTimeData(null, null);
			fail();
		} catch (KadaiException ex) {
			assertEquals(ErrorCode.NULL_INPUT_FILE_PATH.getErrorCode(),
					ex.getErrorCode());
		}
	}

	@Test
	public void testCase07() {
		try {
			Kadai.parseWorkTimeData("NotExtistFile", "test");
			fail();
		} catch (KadaiException ex) {
			assertEquals(ErrorCode.NOT_EXIST_INPUT_FILE.getErrorCode(),
					ex.getErrorCode());
		}
	}

	@Test
	public void testCase08() {
		try {
			Kadai.parseWorkTimeData(INPUT_DIR_PATH + "CanNotRead.txt", "test");
			fail();
		} catch (KadaiException ex) {
			assertEquals(ErrorCode.FAILE_READ_INPUT_FILE.getErrorCode(),
					ex.getErrorCode());
		}
	}

	@Test
	public void testCase09() {
		try {
			Kadai.parseWorkTimeData(INPUT_DIR_PATH + "validCase01.txt", null);
			fail();
		} catch (KadaiException ex) {
			assertEquals(ErrorCode.NULL_OUTPUT_FILE_PATH.getErrorCode(),
					ex.getErrorCode());
		}
	}

	@Test
	public void testCase10() {
		try {
			Kadai.parseWorkTimeData(INPUT_DIR_PATH + "validCase13.txt",
					OUTPUT_DIR_PATH);
			fail();
		} catch (KadaiException ex) {
			assertEquals(ErrorCode.FAILE_FILE_OUT.getErrorCode(),
					ex.getErrorCode());
		}
	}

	@Test
	public void testCase11() {
		executeValidTest("validCase11.txt", "validCase11.txt",
				"validCase11.txt", true);
	}

	@Test
	public void testCase12() {
		executeValidTest("validCase12.txt", "validCase12.txt",
				"validCase12.txt", true);
	}

	@Test
	public void testCase13() {
		executeValidTest("validCase13.txt", "validCase13.txt",
				"validCase13.txt", true);
	}

	@Test
	public void testCaseBom() {
		executeValidTest("validCaseBom.txt", "validCaseBom.txt",
				"validCaseBom.txt", true);
	}

	@Test
	public void testCaseNoBom() {
		executeValidTest("validCaseNoBom.txt", "validCaseNoBom.txt",
				"validCaseNoBom.txt", true);
	}

	@Test
	public void testCase19() {
		try {
			Kadai.parseWorkTimeDataLv2(null, null);
			fail();
		} catch (KadaiException ex) {
			assertEquals(ErrorCode.NULL_INPUT_FILE_PATH.getErrorCode(),
					ex.getErrorCode());
		}
	}

	@Test
	public void testCase20() {
		try {
			Kadai.parseWorkTimeDataLv2("NotExtistFile", OUTPUT_DIR_PATH);
			fail();
		} catch (KadaiException ex) {
			assertEquals(ErrorCode.NOT_EXIST_INPUT_FILE.getErrorCode(),
					ex.getErrorCode());
		}
	}

	@Test
	public void testCase21() {
		try {
			Kadai.parseWorkTimeDataLv2(INPUT_DIR_PATH + "CanNotRead.txt", OUTPUT_DIR_PATH);
			fail();
		} catch (KadaiException ex) {
			assertEquals(ErrorCode.FAILE_READ_INPUT_FILE.getErrorCode(),
					ex.getErrorCode());
		}
	}

	@Test
	public void testCase22() {
		try {
			Kadai.parseWorkTimeDataLv2(INPUT_DIR_PATH + "validCase26.txt", null);
			fail();
		} catch (KadaiException ex) {
			assertEquals(ErrorCode.NULL_OUTPUT_FILE_PATH.getErrorCode(),
					ex.getErrorCode());
		}
	}

	@Test
	public void testCase23() {
		try {
			Kadai.parseWorkTimeDataLv2(INPUT_DIR_PATH + "validCase26.txt",
					"readOnly");
			fail();
		} catch (KadaiException ex) {
			assertEquals(ErrorCode.FAILE_FILE_OUT.getErrorCode(),
					ex.getErrorCode());
		}
	}

	@Test
	public void testCase26() {
		try {
			Kadai.parseWorkTimeDataLv2(INPUT_DIR_PATH + "validCase26.txt","validLv2Out");
			compareFile(createExpectedStream("201401.txt"), createLv2ResultStream("201401.txt"));
			compareFile(createExpectedStream("201402.txt"), createLv2ResultStream("201402.txt"));
			compareFile(createExpectedStream("201403.txt"), createLv2ResultStream("201403.txt"));
			compareFile(createExpectedStream("201404.txt"), createLv2ResultStream("201404.txt"));
			compareFile(createExpectedStream("201405.txt"), createLv2ResultStream("201405.txt"));
		} catch (Exception ex) {
			fail();
		}
	}

}
