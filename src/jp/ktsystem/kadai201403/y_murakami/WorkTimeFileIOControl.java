package jp.ktsystem.kadai201403.y_murakami;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import jp.ktsystem.kadai201403.y_murakami.common.ErrorCode;
import jp.ktsystem.kadai201403.y_murakami.common.KadaiException;
import jp.ktsystem.kadai201403.y_murakami.common.SystemConstant;
import jp.ktsystem.kadai201403.y_murakami.util.CommonUtil;
import jp.ktsystem.kadai201403.y_murakami.util.ErrorUtil;

/**
 * �ｿｽﾎ厄ｿｽ�ｿｽ�ｿｽ�ｿｽﾔのフ�ｿｽ@�ｿｽC�ｿｽ�ｿｽ�ｿｽ�ｿｽ�ｿｽo�ｿｽﾍを制御す�ｿｽ�ｿｽN�ｿｽ�ｿｽ�ｿｽX
 *
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
	 * �ｿｽ�ｿｽ�ｿｽ�ｿｽR�ｿｽ[�ｿｽh�ｿｽﾌエ�ｿｽ�ｿｽ�ｿｽ[�ｿｽ�ｿｽ�ｿｽ�ｿｽ�ｿｽ�ｿｽ�ｿｽ�ｿｽ�ｿｽ�ｿｽ�ｿｽﾇゑｿｽ�ｿｽ�ｿｽ
	 */
	private boolean controlCodeError = false;

	/**
	 * �ｿｽﾇみ搾ｿｽ�ｿｽﾝデ�ｿｽ[�ｿｽ^�ｿｽﾌ�ｿｽ�ｿｽX�ｿｽg�ｿｽi�ｿｽ�ｿｽ�ｿｽx�ｿｽ�ｿｽ�ｿｽQ�ｿｽj
	 */
	private List<WorkTimeMonth> WorkTimeMonthList = new ArrayList<WorkTimeMonth>();

	/**
	 * �ｿｽR�ｿｽ�ｿｽ�ｿｽX�ｿｽg�ｿｽ�ｿｽ�ｿｽN�ｿｽ^
	 *
	 * @param inputFilePath
	 *            �ｿｽ�ｿｽ�ｿｽﾍフ�ｿｽ@�ｿｽC�ｿｽ�ｿｽ�ｿｽp�ｿｽX
	 * @param outputFilePath
	 *            �ｿｽo�ｿｽﾍフ�ｿｽ@�ｿｽC�ｿｽ�ｿｽ�ｿｽp�ｿｽX
	 * @throws KadaiException
	 *             �ｿｽﾛ托ｿｽ�ｿｽO
	 */
	public WorkTimeFileIOControl(String inputFilePath, String outputFilePath)
			throws KadaiException {

		// �ｿｽ�ｿｽ�ｿｽﾍフ�ｿｽ@�ｿｽC�ｿｽ�ｿｽ�ｿｽp�ｿｽX�ｿｽ�ｿｽnull�ｿｽA�ｿｽ�ｿｽ�ｿｽ�ｿｽ�ｿｽ�ｿｽ�ｿｽﾍ空文趣ｿｽ�ｿｽﾌ趣ｿｽ
		if (null == inputFilePath || inputFilePath.isEmpty()) {
			throw new KadaiException(ErrorCode.NULL_INPUT_FILE_PATH);
		}

		// �ｿｽo�ｿｽﾍフ�ｿｽ@�ｿｽC�ｿｽ�ｿｽ�ｿｽp�ｿｽX�ｿｽ�ｿｽnull�ｿｽ�ｿｽ�ｿｽ�ｿｽ�ｿｽ�ｿｽ�ｿｽﾍ空文趣ｿｽ�ｿｽﾌ趣ｿｽ
		if (null == outputFilePath || outputFilePath.isEmpty()) {
			throw new KadaiException(ErrorCode.NULL_OUTPUT_FILE_PATH);
		}

		// �ｿｽt�ｿｽ@�ｿｽC�ｿｽ�ｿｽ�ｿｽp�ｿｽX�ｿｽ�ｿｽ�ｿｽ�ｿｽ�ｿｽ�ｿｽ�ｿｽo�ｿｽﾉ設抵ｿｽ
		this.inputFilePath = inputFilePath;
		this.outputFilePath = outputFilePath;

	}

	/**
	 * �ｿｽt�ｿｽ@�ｿｽC�ｿｽ�ｿｽ�ｿｽﾇみ搾ｿｽ�ｿｽﾝゑｿｽ�ｿｽs�ｿｽ�ｿｽ�ｿｽA�ｿｽo�ｿｽﾍ包ｿｽ�ｿｽ�ｿｽ�ｿｽ潟X�ｿｽg�ｿｽ�ｿｽ�ｿｽ�成�ｿｽ�ｿｽ�ｿｽ�ｿｽ
	 *
	 * @throws KadaiException
	 */
	public void readInputFile() throws KadaiException {

		// �ｿｽ�ｿｽ�ｿｽﾍフ�ｿｽ@�ｿｽC�ｿｽ�ｿｽ�ｿｽﾇみ搾ｿｽ�ｿｽﾝ用�ｿｽﾏ撰ｿｽ
		BufferedReader bfRead = null;

		try {
			// �ｿｽt�ｿｽ@�ｿｽC�ｿｽ�ｿｽ�ｿｽI�ｿｽ[�ｿｽv�ｿｽ�ｿｽ
			bfRead = new BufferedReader(new InputStreamReader(
					new FileInputStream(this.inputFilePath),"UTF-8"));

			int readChar;// �ｿｽﾇみ搾ｿｽ�ｿｽﾝ包ｿｽ�ｿｽ�ｿｽ
			this.readStr = new StringBuilder();// �ｿｽﾇみ搾ｿｽ�ｿｽﾝ包ｿｽ�ｿｽ�ｿｽ�ｿｽ�ｿｽ
			boolean bomCheck = true;

			// �ｿｽ�ｿｽ�ｿｽﾍフ�ｿｽ@�ｿｽC�ｿｽ�ｿｽ�ｿｽﾇみ搾ｿｽ�ｿｽﾝ　�ｿｽi�ｿｽ�ｿｽ�ｿｽ�ｿｽR�ｿｽ[�ｿｽh�ｿｽG�ｿｽ�ｿｽ�ｿｽ[�ｿｽ�ｿｽ�ｿｽo�ｿｽ�ｿｽ�ｿｽ轤ｻ�ｿｽ�ｿｽ�ｿｽﾅ打ゑｿｽ�ｿｽﾘゑｿｽj
			while (-1 != (readChar = bfRead.read())) {

				// BOM�ｿｽﾇみ費ｿｽﾎゑｿｽ
				if (bomCheck && 65533 == readChar) {
					if (!CommonUtil.checkBom(bfRead)) {
						this.controlCodeError = true;
						break;
					}
					readChar = bfRead.read();
				}
				bomCheck = false;

				// �ｿｽ�ｿｽ�ｿｽ苺ｶ�ｿｽ�ｿｽ�ｿｽG�ｿｽ�ｿｽ�ｿｽ[�ｿｽ`�ｿｽF�ｿｽb�ｿｽN
				if (ErrorUtil.isControlCode((char) readChar)) {
					this.controlCodeError = true;
					break;
				}
				this.readStr.append((char) readChar);
			}

			// Matcher�ｿｽ�ｿｽ�ｿｽ�ｿｽﾍ包ｿｽ�ｿｽ�ｿｽ�ｿｽｩら生�ｿｽ�ｿｽ �ｿｽu{�ｿｽv�ｿｽﾅはゑｿｽ�ｿｽﾜゑｿｽ�ｿｽﾄ「�ｿｽp�ｿｽv�ｿｽﾅ終�ｿｽ�ｿｽ髟費ｿｽ�ｿｽ�ｿｽ�ｿｽ�ｿｽ謫ｾ
			Matcher inputMatcher = this.SUBSTR_FILE_PATTERN
					.matcher(this.readStr.toString());
			ArrayList<String> dataList = new ArrayList<String>();
			while (inputMatcher.find()) {
				dataList.add(inputMatcher.group());
			}

			// workTimeList�ｿｽｶ撰ｿｽ
			this.workTimeList = new ArrayList<WorkTime>();
			int sumWorkTime = 0;// �ｿｽ�ｿｽ�ｿｽﾎ厄ｿｽ�ｿｽ�ｿｽ�ｿｽ�ｿｽ

			// �ｿｽ謫ｾ�ｿｽf�ｿｽ[�ｿｽ^�ｿｽ�ｿｽ�ｿｽ�ｿｽAdate,start,end�ｿｽ鰹o�ｿｽ�ｿｽ�ｿｽ�ｿｽ
			for (String data : dataList) {

				String date = null;
				String start = null;
				String end = null;

				Matcher dataMatcher = this.DATA_PATTERN.matcher(data);

				int count = 0;// find�ｿｽ�ｿｽ
				while (dataMatcher.find()) {
					count++;
					String key = dataMatcher.group(1);
					String value = dataMatcher.group(2);
					// key�ｿｽﾉゑｿｽ�ｿｽ鼾�ｿｽ�ｿｽ�ｿｽ�ｿｽ
					if (SystemConstant.KEY_DATE.equals(key)) {
						date = value;
						if (!ErrorUtil.checkDate(date)) {
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

				// �ｿｽ�ｿｽ�ｿｽﾂゑｿｽ�ｿｽ�ｿｽ�ｿｽ�ｿｽ�ｿｽL�ｿｽ[�ｿｽﾌ撰ｿｽ�ｿｽ�ｿｽ3�ｿｽﾈ外�ｿｽﾈゑｿｽG�ｿｽ�ｿｽ�ｿｽ[
				if (3 != count) {
					throw new KadaiException(ErrorCode.INPUT_FILE_ERROR);
				}

				// �ｿｽﾎ厄ｿｽ�ｿｽ�ｿｽ�ｿｽﾔ　�ｿｽG�ｿｽ�ｿｽ�ｿｽ[�ｿｽ�ｿｽ�ｿｽ�ｿｽ�ｿｽ�ｿｽ鼾�ｿｽﾍゑｿｽ�ｿｽ�ｿｽ�ｿｽﾅゑｿｽKadaiException�ｿｽ�ｿｽ�ｿｽX�ｿｽ�ｿｽ�ｿｽ[�ｿｽ�ｿｽ�ｿｽ�ｿｽ�ｿｽ
				String workTime = Kadai.calcWorkTime(start, end);
				// �ｿｽ�ｿｽ�ｿｽﾎ厄ｿｽ�ｿｽ�ｿｽ�ｿｽ�ｿｽ
				sumWorkTime += Integer.parseInt(workTime);

				// workTime�ｿｽI�ｿｽu�ｿｽW�ｿｽF�ｿｽN�ｿｽg�ｿｽｶ撰ｿｽ�ｿｽ�ｿｽ�ｿｽﾄ出�ｿｽﾍ用�ｿｽﾉ�ｿｽ�ｿｽX�ｿｽg�ｿｽﾖ追会ｿｽ
				WorkTime workTimeModel = new WorkTime(date, workTime,
						String.valueOf(sumWorkTime));
				this.workTimeList.add(workTimeModel);
			}

		} catch (FileNotFoundException ex) {
			// �ｿｽ�ｿｽ�ｿｽﾍフ�ｿｽ@�ｿｽC�ｿｽ�ｿｽ�ｿｽ�ｿｽ�ｿｽ�ｿｽ�ｿｽﾝゑｿｽ�ｿｽﾄゑｿｽ�ｿｽ骼�
			File f = new File(this.inputFilePath);
			if (f.exists()) {
				throw new KadaiException(ErrorCode.FAILE_READ_INPUT_FILE);
			}
			throw new KadaiException(ErrorCode.NOT_EXIST_INPUT_FILE);
		} catch (IOException ex) {
			// �ｿｽt�ｿｽ@�ｿｽC�ｿｽ�ｿｽ�ｿｽﾇみ搾ｿｽ�ｿｽﾝに趣ｿｽ�ｿｽs�ｿｽ�ｿｽ�ｿｽ�ｿｽ�ｿｽ�ｿｽ
			throw new KadaiException(ErrorCode.FAILE_READ_INPUT_FILE);
		} finally {
			// �ｿｽt�ｿｽ@�ｿｽC�ｿｽ�ｿｽ�ｿｽN�ｿｽ�ｿｽ�ｿｽ[�ｿｽY
			if (null != bfRead) {
				try {
					bfRead.close();
				} catch (IOException ex) {
					throw new KadaiException(ErrorCode.FAILE_READ_INPUT_FILE);
				}
			}
		}

		// �ｿｽ�ｿｽ�ｿｽ�ｿｽR�ｿｽ[�ｿｽh�ｿｽﾅのエ�ｿｽ�ｿｽ�ｿｽ[�ｿｽ�ｿｽ�ｿｽ�ｿｽ�ｿｽ�ｿｽ�ｿｽ�ｿｽ�ｿｽ鼾�
		if (this.controlCodeError) {
			throw new KadaiException(ErrorCode.IS_CTRL_CODE);
		}
	}

	/**
	 * �ｿｽt�ｿｽ@�ｿｽC�ｿｽ�ｿｽ�ｿｽﾇみ搾ｿｽ�ｿｽﾝゑｿｽ�ｿｽs�ｿｽ�ｿｽ�ｿｽA�ｿｽo�ｿｽﾍ包ｿｽ�ｿｽ�ｿｽ�ｿｽ潟X�ｿｽg�ｿｽ�ｿｽ�ｿｽ�成�ｿｽ�ｿｽ�ｿｽ�ｿｽ@
	 *
	 * @throws KadaiException
	 */
	public void readInputFileLv2() throws KadaiException {

		BufferedReader bfRead = null;
		String month = null;// �ｿｽ�ｿｽ

		// �ｿｽ�ｿｽ�ｿｽﾍフ�ｿｽ@�ｿｽC�ｿｽ�ｿｽ�ｿｽﾌ読み搾ｿｽ�ｿｽ�ｿｽ
		try {
			// �ｿｽt�ｿｽ@�ｿｽC�ｿｽ�ｿｽ�ｿｽI�ｿｽ[�ｿｽv�ｿｽ�ｿｽ
			bfRead = new BufferedReader(new InputStreamReader(
					new FileInputStream(this.inputFilePath),"UTF-8"));

			StringBuilder sb = new StringBuilder();// �ｿｽL�ｿｽ[or�ｿｽo�ｿｽ�ｿｽ�ｿｽ�ｿｽ�ｿｽ[

			// �ｿｽ�ｿｽ�ｿｽ�ｿｽJson�ｿｽR�ｿｽ�ｿｽ�ｿｽg�ｿｽ�ｿｽ�ｿｽ[�ｿｽ�ｿｽ�ｿｽ�ｿｽ�ｿｽ�ｿｽ
			int jsonControlChar = '{';

			// �ｿｽﾇみ搾ｿｽ�ｿｽ�ｿｽ
			int readChar = scanInputChar(bfRead, false, true);
			boolean skipFlag = false;
			while (-1 != readChar) {
				// Json�ｿｽ�ｿｽ�ｿｽ苺ｶ�ｿｽ�ｿｽ�ｿｽ�ｿｽ�ｿｽ�ｿｽ�ｿｽ�ｿｽ�ｿｽ鼾�
				if (readChar != jsonControlChar) {
					if (':' == jsonControlChar || ']' == jsonControlChar) {
						// �ｿｽﾇ会ｿｽ
						sb.append((char) readChar);
						if (readChar == '"') {
							// "�ｿｽﾈ降�ｿｽﾍ包ｿｽ�ｿｽ�ｿｽ�ｿｽ�ｿｽﾇみ搾ｿｽ�ｿｽ�ｿｽ
							skipFlag = !skipFlag;
						}
						readChar = scanInputChar(bfRead, skipFlag,false);
						continue;// �ｿｽﾇみ搾ｿｽ�ｿｽ�ｿｽﾅ趣ｿｽ�ｿｽﾌ包ｿｽ�ｿｽ�ｿｽ�ｿｽ�ｿｽ
					} else {
						throw new KadaiException(ErrorCode.ILLEGAL_INPUT_TIME);
					}
				}

				// Json�ｿｽ�ｿｽ�ｿｽ苺ｶ�ｿｽ�ｿｽ�ｿｽﾉゑｿｽ髟ｪ�ｿｽ�ｿｽ
				if ('{' == jsonControlChar) {
					jsonControlChar = ':';
				} else if (':' == jsonControlChar) {
					// �ｿｽ�ｿｽ�ｿｽ�ｿｽ�ｿｽ謫ｾ
					month = sb.toString().replace('"', ' ').trim();
					sb = new StringBuilder();// �ｿｽ@�ｿｽ�ｿｽ�ｿｽ�ｿｽ�ｿｽ�ｿｽ
					jsonControlChar = '[';
				} else if ('[' == jsonControlChar) {
					jsonControlChar = ']';
				} else if (']' == jsonControlChar) {

					// �ｿｽ黷ｩ�ｿｽ�ｿｽ�ｿｽ�ｿｽ�ｿｽﾌデ�ｿｽ[�ｿｽ^�ｿｽ�ｿｽ�ｿｽ�ｿｽ�ｿｽ�ｿｽ�ｿｽ�ｿｽ�ｿｽ�ｿｽ
					WorkTimeMonth monthData = new WorkTimeMonth(month,
							sb.toString());
					monthData.scanInputData();// �ｿｽf�ｿｽ[�ｿｽ^�ｿｽﾌ読み搾ｿｽ�ｿｽ�ｿｽ

					// �ｿｽﾇみ搾ｿｽ�ｿｽﾝ済みデ�ｿｽ[�ｿｽ^�ｿｽﾉ追会ｿｽ
					this.WorkTimeMonthList.add(monthData);

					sb = new StringBuilder();

					// �ｿｽ�ｿｽ�ｿｽﾌデ�ｿｽ[�ｿｽ^�ｿｽ�ｿｽﾇゑｿｽ
					readChar = scanInputChar(bfRead, false,false);
					if (',' == readChar) {
						// �ｿｽ�ｿｽ�ｿｽf�ｿｽ[�ｿｽ^�ｿｽ�ｿｽ�ｿｽ�ｿｽ�ｿｽ�ｿｽ鼾�
						jsonControlChar = ':';
					} else if ('}' == readChar) {
						// 1�ｿｽ�ｿｽ�ｿｽR�ｿｽ[�ｿｽh�ｿｽﾇみ搾ｿｽ�ｿｽﾝ終�ｿｽ�ｿｽ
						jsonControlChar = '{';
					} else {
						throw new KadaiException(ErrorCode.ILLEGAL_INPUT_TIME);
					}
				} else {
					throw new KadaiException(ErrorCode.ILLEGAL_INPUT_TIME);
				}

				// �ｿｽ�ｿｽ�ｿｽﾌ包ｿｽ�ｿｽ�ｿｽ�ｿｽ�ｿｽﾇみ搾ｿｽ�ｿｽ�ｿｽ
				readChar = scanInputChar(bfRead, false, false);
			}
		} catch (FileNotFoundException ex) {
			// �ｿｽ�ｿｽ�ｿｽﾍフ�ｿｽ@�ｿｽC�ｿｽ�ｿｽ�ｿｽ�ｿｽ�ｿｽ�ｿｽ�ｿｽﾝゑｿｽ�ｿｽﾄゑｿｽ�ｿｽ骼�
			File f = new File(this.inputFilePath);
			if (f.exists()) {
				throw new KadaiException(ErrorCode.FAILE_READ_INPUT_FILE);
			}
			throw new KadaiException(ErrorCode.NOT_EXIST_INPUT_FILE);
		} catch (IOException ex) {
			// �ｿｽt�ｿｽ@�ｿｽC�ｿｽ�ｿｽ�ｿｽﾇみ搾ｿｽ�ｿｽﾝに趣ｿｽ�ｿｽs�ｿｽ�ｿｽ�ｿｽ�ｿｽ�ｿｽ�ｿｽ
			throw new KadaiException(ErrorCode.FAILE_READ_INPUT_FILE);
		} finally {
			// �ｿｽt�ｿｽ@�ｿｽC�ｿｽ�ｿｽ�ｿｽN�ｿｽ�ｿｽ�ｿｽ[�ｿｽY
			if (null != bfRead) {
				try {
					bfRead.close();
				} catch (IOException ex) {
					throw new KadaiException(ErrorCode.FAILE_READ_INPUT_FILE);
				}
			}
		}
	}

	/**
	 * �ｿｽﾇみ搾ｿｽ�ｿｽﾝ難ｿｽ�ｿｽe�ｿｽﾌ擾ｿｽ�ｿｽ�ｿｽ�ｿｽo�ｿｽ�ｿｽ
	 *
	 * @param errorCode
	 *            �ｿｽﾇみ搾ｿｽ�ｿｽﾝ趣ｿｽ�ｿｽﾉエ�ｿｽ�ｿｽ�ｿｽ[�ｿｽ�ｿｽ�ｿｽ�ｿｽ�ｿｽ�ｿｽ�ｿｽ�ｿｽ�ｿｽ鼾�ｿｽﾌエ�ｿｽ�ｿｽ�ｿｽ[�ｿｽR�ｿｽ[�ｿｽh(�ｿｽK�ｿｽv�ｿｽﾈゑｿｽ�ｿｽ鼾�ｿｽ�ｿｽ-1�ｿｽ�ｿｽﾝ定し�ｿｽﾄゑｿｽ�ｿｽ�ｿｽ�ｿｽ�ｿｽ�ｿｽ�ｿｽ)
	 * @throws KadaiException
	 */
	public void writeOutputFile(int errorCode) throws KadaiException {

		if (null == this.workTimeList) {
			return;
		}

		BufferedWriter bfWrite = null;// �ｿｽo�ｿｽﾍフ�ｿｽ@�ｿｽC�ｿｽ�ｿｽ�ｿｽ�ｿｽ�ｿｽ�ｿｽp�ｿｽﾏ撰ｿｽ

		try {
			bfWrite = new BufferedWriter(new FileWriter(this.outputFilePath));
			// �ｿｽo�ｿｽﾍ用�ｿｽ�ｿｽ�ｿｽ�ｿｽ�ｿｽ潟X�ｿｽg�ｿｽﾌ作成
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

			// �ｿｽo�ｿｽ�ｿｽ
			for (String line : outList) {
				// �ｿｽ�ｿｽ�ｿｽ�ｿｽ�ｿｽ�ｿｽ�ｿｽ�ｿｽ
				bfWrite.write(line);
				bfWrite.newLine();
			}

			// �ｿｽﾇみ搾ｿｽ�ｿｽﾝ趣ｿｽ�ｿｽG�ｿｽ�ｿｽ�ｿｽ[�ｿｽR�ｿｽ[�ｿｽh�ｿｽﾌ擾ｿｽ�ｿｽ�ｿｽ�ｿｽ�ｿｽ�ｿｽ�ｿｽ(6,7,8�ｿｽﾍ出�ｿｽﾍフ�ｿｽ@�ｿｽC�ｿｽ�ｿｽ�ｿｽ�ｿｽ�ｿｽﾌゑｿｽ�ｿｽ�ｿｽ�ｿｽﾝゑｿｽ�ｿｽﾈゑｿｽ�ｿｽ�ｿｽ�ｿｽﾟ記�ｿｽq�ｿｽ�ｿｽ�ｿｽﾈゑｿｽ)
			if (-1 != errorCode && errorCode != 6 && errorCode != 7
					&& errorCode != 8) {
				bfWrite.write(String.valueOf(errorCode));
				bfWrite.newLine();
			}
		} catch (Exception ex) {
			// �ｿｽo�ｿｽﾍ趣ｿｽ�ｿｽs
			throw new KadaiException(ErrorCode.FAILE_FILE_OUT);
		} finally {
			// �ｿｽt�ｿｽ@�ｿｽC�ｿｽ�ｿｽ�ｿｽN�ｿｽ�ｿｽ�ｿｽ[�ｿｽY
			if (null != bfWrite) {
				try {
					bfWrite.close();
				} catch (IOException ex) {
					throw new KadaiException(ErrorCode.FAILE_FILE_OUT);
				}
			}
		}
	}

	/**
	 * Lv2�ｿｽp�ｿｽt�ｿｽ@�ｿｽC�ｿｽ�ｿｽ�ｿｽ�ｿｽ�ｿｽ�ｿｽ�ｿｽo�ｿｽ�ｿｽ
	 *
	 * @throws KadaiException
	 */
	public void writeOutPutFileLv2(String dirPath) throws KadaiException {

		for (WorkTimeMonth workTimeMonth : this.WorkTimeMonthList) {

			BufferedWriter bfWrite = null;
			String month = workTimeMonth.getMonth();
			String fullPath = String.format("%s//%s.txt", dirPath, month);// �ｿｽ�ｿｽ�ｿｽ�ｿｽ�ｿｽ�ｿｽ�ｿｽﾝパ�ｿｽX
			int errorCode = workTimeMonth.getErrorCode();// errorCode�ｿｽﾌ取得

			try {

				// �ｿｽf�ｿｽB�ｿｽ�ｿｽ�ｿｽN�ｿｽg�ｿｽ�ｿｽ�ｿｽ�ｿｽ�ｿｽ�ｿｽ�ｿｽﾝゑｿｽ�ｿｽﾈゑｿｽ�ｿｽ鼾�ｿｽﾍ作成�ｿｽ�ｿｽ�ｿｽ�ｿｽ
				File dir = new File(dirPath);
				if (!dir.exists()) {
					dir.mkdirs();
				}

				bfWrite = new BufferedWriter(new FileWriter(fullPath));

				// �ｿｽo�ｿｽﾍ用�ｿｽ�ｿｽ�ｿｽ�ｿｽ�ｿｽ潟X�ｿｽg�ｿｽﾌ作成
				ArrayList<String> outList = new ArrayList<String>();
				List<WorkTime> workTimeList = workTimeMonth.getWorkTimeList();

				// �ｿｽ�ｿｽ�ｿｽt�ｿｽ�ｿｽ�ｿｽﾅソ�ｿｽ[�ｿｽg
				for (int i = 0; i < workTimeList.size() - 1; ++i) {
					WorkTime wt1 = workTimeList.get(i);
					WorkTime wt2 = workTimeList.get(i + 1);
					if (0 < wt1.getDate().compareTo(wt2.getDate())) {
						WorkTime wtTemp = wt1;
						workTimeList.set(i, wt2);
						workTimeList.set(i + 1, wtTemp);
					}
				}

				// �ｿｽo�ｿｽﾍ包ｿｽ�ｿｽ�ｿｽ�ｿｽ�ｿｽﾌ作成
				int total = 0;
				outList.add("[");
				for (int i = 0; i < workTimeList.size(); ++i) {

					// total�ｿｽ�ｿｽﾝ定す�ｿｽ�ｿｽ
					WorkTime wt = workTimeList.get(i);
					int workTime = Integer.parseInt(wt.getWorkTime());
					wt.setTotal(String.valueOf(total += workTime));

					// �ｿｽ�ｿｽ�ｿｽ�ｿｽ�ｿｽ�ｿｽ�ｿｽ�ｿｽJson�ｿｽ�ｿｽ�ｿｽ�ｿｽ�ｿｽ�ｿｽﾌ作成
					StringBuilder line = new StringBuilder(workTimeList.get(i)
							.createJsonStr());
					if (workTimeList.size() - 1 != i) {
						line.append(",");
					}
					outList.add(line.toString());
				}
				outList.add("]");

				// �ｿｽo�ｿｽ�ｿｽ
				for (String line : outList) {
					// �ｿｽ�ｿｽ�ｿｽ�ｿｽ�ｿｽ�ｿｽ�ｿｽ�ｿｽ
					bfWrite.write(line);
					bfWrite.newLine();
				}

				// �ｿｽﾇみ搾ｿｽ�ｿｽﾝ趣ｿｽ�ｿｽG�ｿｽ�ｿｽ�ｿｽ[�ｿｽR�ｿｽ[�ｿｽh�ｿｽﾌ擾ｿｽ�ｿｽ�ｿｽ�ｿｽ�ｿｽ�ｿｽ�ｿｽ(6,7,8�ｿｽﾍ出�ｿｽﾍフ�ｿｽ@�ｿｽC�ｿｽ�ｿｽ�ｿｽ�ｿｽ�ｿｽﾌゑｿｽ�ｿｽ�ｿｽ�ｿｽﾝゑｿｽ�ｿｽﾈゑｿｽ�ｿｽ�ｿｽ�ｿｽﾟ記�ｿｽq�ｿｽ�ｿｽ�ｿｽﾈゑｿｽ)
				if (-1 != errorCode && errorCode != 6 && errorCode != 7
						&& errorCode != 8) {
					bfWrite.write(String.valueOf(errorCode));
					bfWrite.newLine();
				}

			} catch (Exception ex) {
				// �ｿｽo�ｿｽﾍ趣ｿｽ�ｿｽs
				throw new KadaiException(ErrorCode.FAILE_FILE_OUT);
			} finally {
				// �ｿｽt�ｿｽ@�ｿｽC�ｿｽ�ｿｽ�ｿｽN�ｿｽ�ｿｽ�ｿｽ[�ｿｽY
				if (null != bfWrite) {
					try {
						bfWrite.close();
					} catch (IOException ex) {
						throw new KadaiException(ErrorCode.FAILE_FILE_OUT);
					}
				}
			}
			// error�ｿｽ�ｿｽ�ｿｽ�ｿｽ�ｿｽﾝゑｿｽ�ｿｽ�ｿｽ�ｿｽ�ｿｽI�ｿｽ�ｿｽ
			if (0 < errorCode) {
				break;
			}
		}
	}

	/**
	 * �ｿｽ�ｿｽ�ｿｽﾌ包ｿｽ�ｿｽ�ｿｽ�ｿｽ�ｿｽﾇゑｿｽ
	 *
	 * @param bfRead
	 *            �ｿｽt�ｿｽ@�ｿｽC�ｿｽ�ｿｽ�ｿｽ�ｿｽ�ｿｽ[�ｿｽ_�ｿｽ[
	 * @param skipFlag
	 *            �ｿｽs�ｿｽv�ｿｽ�ｿｽ�ｿｽ�ｿｽ�ｿｽ�ｿｽﾇみ費ｿｽﾎゑｿｽ�ｿｽ�ｿｽ�ｿｽﾇゑｿｽ�ｿｽ�ｿｽ�ｿｽ@
	 * @return
	 * @throws KadaiException
	 * @throws IOException
	 */
	private int scanInputChar(BufferedReader bfRead, boolean skipFlag,
			boolean bomCheck) throws KadaiException, IOException {

		int readChar = bfRead.read(); // �ｿｽﾇみ搾ｿｽ�ｿｽ�ｿｽ
		if (skipFlag) {
			// �ｿｽ�ｿｽ�ｿｽ�ｿｽR�ｿｽ[�ｿｽh�ｿｽ`�ｿｽF�ｿｽb�ｿｽN
			if (ErrorUtil.isControlCode((char) readChar)) {
				throw new KadaiException(ErrorCode.ILLEGAL_INPUT_TIME);
			}
			return readChar;
		} else {
			// �ｿｽ窒�ｿｽﾇみ費ｿｽﾎゑｿｽ
			while (-1 != readChar) {
				// �ｿｽ�ｿｽ�ｿｽ�ｿｽﾌゑｿｽBOM�ｿｽ`�ｿｽF�ｿｽb�ｿｽN
				if (bomCheck && 65533 == readChar) {
					if (!CommonUtil.checkBom(bfRead)) {
						throw new KadaiException(
								ErrorCode.IS_CTRL_CODE);
					}
					readChar = bfRead.read();
				}
				// �ｿｽ茶R�ｿｽ[�ｿｽh�ｿｽ`�ｿｽF�ｿｽb�ｿｽN
				if (!CommonUtil.isSkipCode(readChar)) {
					// �ｿｽ�ｿｽ�ｿｽ�ｿｽR�ｿｽ[�ｿｽh�ｿｽ`�ｿｽF�ｿｽb�ｿｽN
					if (ErrorUtil.isControlCode((char) readChar)) {
						throw new KadaiException(ErrorCode.IS_CTRL_CODE);
					}
					return readChar;
				}
				readChar = bfRead.read();
			}
			return readChar;
		}
	}
}
