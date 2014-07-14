package jp.ktsystem.kadai201403.y_murakami;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import jp.ktsystem.kadai201403.y_murakami.common.ErrorCode;
import jp.ktsystem.kadai201403.y_murakami.common.KadaiException;
import jp.ktsystem.kadai201403.y_murakami.common.SystemConstant;
import jp.ktsystem.kadai201403.y_murakami.util.CommonUtil;
import jp.ktsystem.kadai201403.y_murakami.util.ErrorUtil;

/**
 * 勤務時間のファイル入出力を制御するクラス
 * @author y_murakami
 */
public class WorkTimeFileIOControl {

	/**
	 * �ｿｽ�ｿｽ�ｿｽﾍチ�ｿｽF�ｿｽb�ｿｽN�ｿｽp�ｿｽ@�ｿｽ�ｿｽ�ｿｽK�ｿｽ\�ｿｽ�ｿｽPattern
	 */
	private final Pattern SUBSTR_FILE_PATTERN = Pattern
			.compile(SystemConstant.REGULAR_EXPRESSION_STR_INPUT_FILE);

	/**
	 * �ｿｽ�ｿｽ�ｿｽﾍチ�ｿｽF�ｿｽb�ｿｽN�ｿｽp�ｿｽ@�ｿｽ�ｿｽ�ｿｽK�ｿｽ\�ｿｽ�ｿｽPattern
	 */
	private final Pattern DATA_PATTERN = Pattern
			.compile(SystemConstant.REGULAR_EXPRESSION_DATA);

	/**
	 * �ｿｽ�ｿｽ�ｿｽﾍフ�ｿｽ@�ｿｽC�ｿｽ�ｿｽ�ｿｽp�ｿｽX
	 */
	private String inputFilePath = null;

	/**
	 * �ｿｽo�ｿｽﾍフ�ｿｽ@�ｿｽC�ｿｽ�ｿｽ�ｿｽp�ｿｽX
	 */
	private String outputFilePath = null;

	/**
	 * �ｿｽﾇみ搾ｿｽ�ｿｽﾝフ�ｿｽ@�ｿｽC�ｿｽ�ｿｽ�ｿｽ�ｿｽ�ｿｽ�ｿｽ�ｿｽ�ｿｽ
	 */
	private StringBuilder readStr = null;

	/**
	 * �ｿｽﾇみ搾ｿｽ�ｿｽﾝデ�ｿｽ[�ｿｽ^�ｿｽﾌ�ｿｽ�ｿｽX�ｿｽg
	 */
	private ArrayList<WorkTime> workTimeList = null;

	/**
	 * �ｿｽ�ｿｽ�ｿｽ�ｿｽR�ｿｽ[�ｿｽh�ｿｽﾌエ�ｿｽ�ｿｽ�ｿｽ[�ｿｽ�ｿｽ�ｿｽ�ｿｽ�ｿｽ�ｿｽ�ｿｽ�ｿｽ�ｿｽ�ｿｽ�ｿｽﾇゑｿｽ
	 * �ｿｽ�ｿｽ
	 */
	private boolean controlCodeError = false;


	/**
	 * コンストラクタ
	 * @param inputFilePath　入力ファイルパス
	 * @param outputFilePath　出力ファイルパス
	 * @throws KadaiException 課題例外
	 */
	public WorkTimeFileIOControl(String inputFilePath, String outputFilePath)
			throws KadaiException {

		// null空文字チェック
		if (null == inputFilePath || inputFilePath.isEmpty()) {
			throw new KadaiException(ErrorCode.NULL_INPUT_FILE_PATH);
		}

		if (null == outputFilePath || outputFilePath.isEmpty()) {
			throw new KadaiException(ErrorCode.NULL_OUTPUT_FILE_PATH);
		}

		this.inputFilePath = inputFilePath;
		this.outputFilePath = outputFilePath;

	}

	/**
	 * ファイル読み込みを行い、出力文字列リストを作成する
	 * @throws KadaiException
	 */
	public void readInputFile() throws KadaiException {

		// 入力ファイル読み込み用変数
		BufferedReader bfRead = null;

		try {
			bfRead = new BufferedReader(new InputStreamReader(
					new FileInputStream(this.inputFilePath), "UTF-8"));

			int readChar;
			this.readStr = new StringBuilder();
			boolean bomCheck = true;

			// ファイル読み込み
			while (-1 != (readChar = bfRead.read())) {

				// BOMチェック
				if (bomCheck && 65533 == readChar) {
					if (!CommonUtil.checkBom(bfRead)) {
						this.controlCodeError = true;
						break;
					}
					readChar = bfRead.read();
				}
				bomCheck = false;

				// 制御コード
				if (ErrorUtil.isControlCode((char) readChar)) {
					this.controlCodeError = true;
					break;
				}
				this.readStr.append((char) readChar);
			}

			// 対象データを読み込み
			Matcher inputMatcher = this.SUBSTR_FILE_PATTERN
					.matcher(this.readStr.toString());
			ArrayList<String> dataList = new ArrayList<String>();
			while (inputMatcher.find()) {
				dataList.add(inputMatcher.group());
			}

			// workTimeList
			this.workTimeList = new ArrayList<WorkTime>();
			int sumWorkTime = 0;// 合計時間
			SimpleDateFormat df = new SimpleDateFormat(SystemConstant.INPUT_DATA_FORMAT);

			// 対象キーから値を取り出し
			for (String data : dataList) {

				String date = null;
				String start = null;
				String end = null;

				Matcher dataMatcher = this.DATA_PATTERN.matcher(data);

				int count = 0;// find回数
				while (dataMatcher.find()) {
					count++;
					String key = dataMatcher.group(1);
					String value = dataMatcher.group(2);
					if (SystemConstant.KEY_DATE.equals(key)) {
						date = value;
						if (!ErrorUtil.checkDate(date,df)) {
							throw new KadaiException(
									ErrorCode.ILLEGAL_INPUT_TIME);
						}
					} else if (SystemConstant.KEY_START.equals(key)) {
						start = value;
					} else if (SystemConstant.KEY_END.equals(key)) {
						end = value;
					} else {
						throw new KadaiException(ErrorCode.ILLEGAL_INPUT_TIME);
					}
				}

				// find回数チェック
				if (3 != count) {
					throw new KadaiException(ErrorCode.INPUT_FILE_ERROR);
				}

				// 勤務時間
				String workTime = Kadai.calcWorkTime(start, end);
				sumWorkTime += Integer.parseInt(workTime);

				WorkTime workTimeModel = new WorkTime(date, workTime,
						String.valueOf(sumWorkTime));
				this.workTimeList.add(workTimeModel);
			}

		} catch (FileNotFoundException ex) {
			File f = new File(this.inputFilePath);
			if (f.exists()) {
				throw new KadaiException(ErrorCode.FAILE_READ_INPUT_FILE);
			}
			throw new KadaiException(ErrorCode.NOT_EXIST_INPUT_FILE);
		} catch (IOException ex) {
			throw new KadaiException(ErrorCode.FAILE_READ_INPUT_FILE);
		} finally {
			if (null != bfRead) {
				try {
					bfRead.close();
				} catch (IOException ex) {
					throw new KadaiException(ErrorCode.FAILE_READ_INPUT_FILE);
				}
			}
		}

		// 制御コードエラーの時
		if (this.controlCodeError) {
			throw new KadaiException(ErrorCode.IS_CTRL_CODE);
		}
	}


	/**
	 * ファイル書き出し
	 *
	 * @param errorCode　エラーコード　出力用　
	 * @throws KadaiException　
	 */
	public void writeOutputFile(int errorCode) throws KadaiException {

		if (null == this.workTimeList) {
			return;
		}

		BufferedWriter bfWrite = null;

		try {
			ArrayList<String> outList = new ArrayList<String>();
			outList.add("[");
			for (int i = 0; i < this.workTimeList.size(); ++i) {
				StringBuilder line = new StringBuilder(this.workTimeList.get(i)
						.createJsonStr());
				if (this.workTimeList.size() - 1 != i) {
					line.append(",");
				}
				outList.add(line.toString());
			}
			outList.add("]");

			bfWrite = new BufferedWriter(new FileWriter(this.outputFilePath));

			for (String line : outList) {
				bfWrite.write(line);
				bfWrite.newLine();
			}

			// 必要なエラーコードの書き出し
			if (-1 != errorCode
					&& errorCode != ErrorCode.NULL_INPUT_FILE_PATH
							.getErrorCode()
					&& errorCode != ErrorCode.NOT_EXIST_INPUT_FILE
							.getErrorCode() && errorCode != ErrorCode.FAILE_READ_INPUT_FILE.getErrorCode()) {
				bfWrite.write(String.valueOf(errorCode));
				bfWrite.newLine();
			}
		} catch (Exception ex) {
			throw new KadaiException(ErrorCode.FAILE_FILE_OUT);
		} finally {
			if (null != bfWrite) {
				try {
					bfWrite.close();
				} catch (IOException ex) {
					throw new KadaiException(ErrorCode.FAILE_FILE_OUT);
				}
			}
		}
	}

}
