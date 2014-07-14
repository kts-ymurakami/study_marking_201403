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
import java.util.Collections;
import java.util.List;

import jp.ktsystem.kadai201403.y_murakami.common.ErrorCode;
import jp.ktsystem.kadai201403.y_murakami.common.KadaiException;
import jp.ktsystem.kadai201403.y_murakami.common.SystemConstant;
import jp.ktsystem.kadai201403.y_murakami.util.CommonUtil;
import jp.ktsystem.kadai201403.y_murakami.util.ErrorUtil;

/**
 * Lv2用ファイルコントロールクラス
 *
 * @author y_murakami
 */
public class WorkTimeFileIOControlLv2 {

	/**
	 * 入力ディレクトリパス
	 */
	private String inputFilePath = null;

	/**
	 * 月のリスト
	 */
	private List<WorkTimeMonth> WorkTimeMonthList = new ArrayList<WorkTimeMonth>();

	/**
	 * コンストラクタ
	 *
	 * @param inputFilePath
	 *            入力ディレクトリパス
	 * @param outputFilePath
	 *            出力ディレクトリパス
	 * @throws KadaiException
	 *             課題Exception
	 */
	public WorkTimeFileIOControlLv2(String inputFilePath, String outputFilePath)
			throws KadaiException {

		if (null == inputFilePath || inputFilePath.isEmpty()) {
			throw new KadaiException(ErrorCode.NULL_INPUT_FILE_PATH);
		}

		if (null == outputFilePath || outputFilePath.isEmpty()) {
			throw new KadaiException(ErrorCode.NULL_OUTPUT_FILE_PATH);
		}

		this.inputFilePath = inputFilePath;

	}

	/**
	 * ファイル読み込み
	 *
	 * @throws KadaiException
	 */
	public void readInputFileLv2() throws KadaiException {

		BufferedReader bfRead = null;
		String month = null;

		try {
			bfRead = new BufferedReader(new InputStreamReader(
					new FileInputStream(this.inputFilePath), "UTF-8"));

			StringBuilder sb = new StringBuilder();

			int jsonControlChar = '{';

			int readChar = scanInputChar(bfRead, false, true);
			boolean skipFlag = false;
			while (-1 != readChar) {
				if (readChar != jsonControlChar) {
					if (':' == jsonControlChar || ']' == jsonControlChar) {
						sb.append((char) readChar);
						if (readChar == '"') {
							skipFlag = !skipFlag;
						}
						readChar = scanInputChar(bfRead, skipFlag, false);
						continue;
					} else {
						throw new KadaiException(ErrorCode.ILLEGAL_INPUT_TIME);
					}
				}

				// Json制御文字
				if ('{' == jsonControlChar) {
					jsonControlChar = ':';
				} else if (':' == jsonControlChar) {
					month = sb.toString().replace('"', ' ').trim();
					sb = new StringBuilder();
					jsonControlChar = '[';
				} else if ('[' == jsonControlChar) {
					jsonControlChar = ']';
				} else if (']' == jsonControlChar) {

					WorkTimeMonth monthData = new WorkTimeMonth(month,
							sb.toString());
					monthData.scanInputData();

					// 月データを表示
					this.WorkTimeMonthList.add(monthData);

					sb = new StringBuilder();

					readChar = scanInputChar(bfRead, false, false);
					if (',' == readChar) {
						jsonControlChar = ':';
					} else if ('}' == readChar) {
						jsonControlChar = '{';
					} else {
						throw new KadaiException(ErrorCode.ILLEGAL_INPUT_TIME);
					}
				} else {
					throw new KadaiException(ErrorCode.ILLEGAL_INPUT_TIME);
				}

				readChar = scanInputChar(bfRead, false, false);
			}
		} catch (FileNotFoundException ex) {
			// エラーコードのための分岐
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
	}

	/**
	 * Lv2用　ファイル　出力
	 *
	 * @param dirPath
	 *            出力ディレクトリパス
	 * @throws KadaiException
	 */
	public void writeOutPutFileLv2(String dirPath) throws KadaiException {

		for (WorkTimeMonth workTimeMonth : this.WorkTimeMonthList) {

			BufferedWriter bfWrite = null;
			String month = workTimeMonth.getMonth();
			String fullPath = String.format(SystemConstant.CREATE_FILE_PATH_FORMAT, dirPath, month);// �ｿｽ�ｿｽ�ｿｽ�ｿｽ�ｿｽ�ｿｽ�ｿｽﾝパ�ｿｽX
			int errorCode = workTimeMonth.getErrorCode();// エラーコード

			try {

				// 出力ディレクトリ作成
				File dir = new File(dirPath);
				if (!dir.exists()) {
					dir.mkdirs();
				}

				bfWrite = new BufferedWriter(new FileWriter(fullPath));

				ArrayList<String> outList = new ArrayList<String>();
				List<WorkTime> workTimeList = workTimeMonth.getWorkTimeList();
				Collections.sort(workTimeList,new WorkTimeComparator());

				int total = 0;
				outList.add("[");
				for (int i = 0; i < workTimeList.size(); ++i) {

					WorkTime wt = workTimeList.get(i);
					int workTime = Integer.parseInt(wt.getWorkTime());
					wt.setTotal(String.valueOf(total += workTime));

					StringBuilder line = new StringBuilder(workTimeList.get(i)
							.createJsonStr());
					if (workTimeList.size() - 1 != i) {
						line.append(",");
					}
					outList.add(line.toString());
				}
				outList.add("]");

				for (String line : outList) {
					bfWrite.write(line);
					bfWrite.newLine();
				}

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

			// エラーがある場合はそこで終了
			if (0 < errorCode) {
				break;
			}
		}
	}

	/**
	 * 文字の読み込み
	 *
	 * @param bfRead
	 *            リーダー
	 * @param skipFlag
	 *            空白、許容制御文字を読み飛ばすかどうあ
	 * @param bomCheck
	 *            Bomのチェックを行うかどうか
	 * @return
	 * @throws KadaiException
	 * @throws IOException
	 */
	private int scanInputChar(BufferedReader bfRead, boolean skipFlag,
			boolean bomCheck) throws KadaiException, IOException {

		int readChar = bfRead.read();
		if (skipFlag) {
			if (ErrorUtil.isControlCode((char) readChar)) {
				throw new KadaiException(ErrorCode.ILLEGAL_INPUT_TIME);
			}
			return readChar;
		} else {
			while (-1 != readChar) {
				if (bomCheck && 65533 == readChar) {
					if (!CommonUtil.checkBom(bfRead)) {
						throw new KadaiException(ErrorCode.IS_CTRL_CODE);
					}
					readChar = bfRead.read();
				}
				if (!CommonUtil.isSkipCode(readChar)) {
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
