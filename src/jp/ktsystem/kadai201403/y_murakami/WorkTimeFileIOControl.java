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

import jp.ktsystem.kadai201403.y_murakami.Common.ErrorCode;
import jp.ktsystem.kadai201403.y_murakami.Common.KadaiException;
import jp.ktsystem.kadai201403.y_murakami.Common.SystemConstant;
import jp.ktsystem.kadai201403.y_murakami.Util.CommonUtil;
import jp.ktsystem.kadai201403.y_murakami.Util.ErrorUtil;

/**
 * 勤務時間のファイル入出力を制御するクラス
 *
 * @author y_murakami
 */
public class WorkTimeFileIOControl {

	/**
	 * 入力チェック用　正規表現Pattern
	 */
	private final Pattern SUBSTR_FILE_PATTERN = Pattern
			.compile(SystemConstant.REGULAR_EXPRESSION_STR_INPUT_FILE);

	/**
	 * 入力チェック用　正規表現Pattern
	 */
	private final Pattern DATA_PATTERN = Pattern
			.compile(SystemConstant.REGULAR_EXPRESSION_DATE);

	/**
	 * 入力ファイルパス
	 */
	private String inputFilePath = null;

	/**
	 * 出力ファイルパス
	 */
	private String outputFilePath = null;

	/**
	 * 出力ファイル書き込み用文字列リスト
	 */
	private ArrayList<String[]> outputList = null;

	/**
	 * 読み込みファイル文字列
	 */
	private StringBuilder readStr = null;

	/**
	 * 読み込みデータのリスト
	 */
	private ArrayList<WorkTime> workTimeList = null;

	/**
	 * 制御コードのエラーがあったかどうか
	 */
	private boolean controlCodeError = false;

	/**
	 * 読み込みデータのリスト（レベル２）
	 */
	private List<WorkTimeMonth> WorkTimeMonthList = null;

	/**
	 * コンストラクタ
	 *
	 * @param inputFilePath
	 *            入力ファイルパス
	 * @param outputFilePath
	 *            出力ファイルパス
	 * @throws KadaiException
	 *             課題例外
	 */
	public WorkTimeFileIOControl(String inputFilePath, String outputFilePath)
			throws KadaiException {

		// 入力ファイルパスがnull、もしくは空文字の時
		if (!ErrorUtil.checkNullOrEmpty(inputFilePath)) {
			throw new KadaiException(ErrorCode.NULL_INPUT_FILE_PATH);
		}

		// 出力ファイルパスがnullもしくは空文字の時
		if (!ErrorUtil.checkNullOrEmpty(outputFilePath)) {
			throw new KadaiException(ErrorCode.NULL_OUTPUT_FILE_PATH);
		}

		// ファイルパスをメンバに設定
		this.inputFilePath = inputFilePath;
		this.outputFilePath = outputFilePath;

	}

	/**
	 * ファイル読み込みを行い、出力文字列リストを作成する
	 *
	 * @throws KadaiException
	 */
	public void readInputFile() throws KadaiException {

		// 入力ファイル読み込み用変数
		BufferedReader bfRead = null;

		try {
			// ファイルオープン
			bfRead = new BufferedReader(new InputStreamReader(
					new FileInputStream(this.inputFilePath)));

			int readChar;// 読み込み文字
			this.readStr = new StringBuilder();// 読み込み文字列

			// 入力ファイル読み込み　（制御コードエラーが出たらそこで打ち切り）
			while (-1 != (readChar = bfRead.read())) {
				// 制御文字エラーチェック
				if (ErrorUtil.isControlCode((char) readChar)) {
					this.controlCodeError = true;
					break;
				}
				this.readStr.append((char) readChar);
			}

			// Matcherを入力文字列から生成 「{」ではじまって「｝」で終わる部分を取得
			Matcher inputMatcher = this.SUBSTR_FILE_PATTERN
					.matcher(this.readStr.toString());
			ArrayList<String> dataList = new ArrayList<String>();
			while (inputMatcher.find()) {
				dataList.add(inputMatcher.group());
			}

			// workTimeListを生成
			this.workTimeList = new ArrayList<WorkTime>();
			int sumWorkTime = 0;// 総勤務時間

			// 取得データから、date,start,endを抽出する
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
					// keyによる場合分け
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

				// 見つかったキーの数が3以外ならエラー
				if (3 != count) {
					throw new KadaiException(ErrorCode.INPUT_FILE_ERROR);
				}

				// 勤務時間　エラーがある場合はここでもKadaiExceptionがスローされる
				String workTime = Kadai.calcWorkTime(start, end);
				// 総勤務時間
				sumWorkTime += Integer.parseInt(workTime);

				// workTimeオブジェクトを生成して出力用にリストへ追加
				WorkTime workTimeModel = new WorkTime(date, workTime,
						String.valueOf(sumWorkTime));
				this.workTimeList.add(workTimeModel);
			}

		} catch (FileNotFoundException ex) {
			// 入力ファイルが存在している時
			File f = new File(this.inputFilePath);
			if (f.exists()) {
				throw new KadaiException(ErrorCode.FAILE_READ_INPUT_FILE);
			}
			throw new KadaiException(ErrorCode.NOT_EXIST_INPUT_FILE);
		} catch (KadaiException ex) {
			throw ex; // 存在しないキー、制御コード
		} catch (Exception ex) {
			// ファイル読み込みに失敗した時
			throw new KadaiException(ErrorCode.FAILE_READ_INPUT_FILE);
		} finally {
			// ファイルクローズ
			if (null != bfRead) {
				try {
					bfRead.close();
				} catch (IOException ex) {
					throw new KadaiException(ErrorCode.FAILE_READ_INPUT_FILE);
				}
			}
		}

		// 制御コードでのエラーがあった場合
		if (this.controlCodeError) {
			throw new KadaiException(ErrorCode.IS_CTRL_CODE);
		}

	}

	/**
	 * ファイル読み込みを行い、出力文字列リストを作成する　TODO
	 *
	 * @throws KadaiException
	 */
	public void readInputFileLv2() throws KadaiException {

		BufferedReader bfRead = null;
		// 月データの保持
		List<WorkTime> workTimeMonth = new ArrayList<WorkTime>();

		// 入力ファイルの読み込み
		try {
			// ファイルオープン
			bfRead = new BufferedReader(new InputStreamReader(
					new FileInputStream(this.inputFilePath)));

			StringBuilder sb = new StringBuilder();// キーorバリュー
			String month = null;// 月

			// 次のJsonコントロール文字
			int jsonControlChar = '{';

			// 読み込み
			int ch = scanInputChar(bfRead, false);
			boolean skipFlag = false;

			while (-1 != ch) {
				// Json制御文字が来た場合
				if (ch != jsonControlChar) {
					if (':' == jsonControlChar || ']' == jsonControlChar) {
						// 追加
						sb.append((char) ch);
						if (ch == '"') {
							// "以降は文字列読み込み
							skipFlag = !skipFlag;
						}
						ch = scanInputChar(bfRead, skipFlag);
						continue;// 読み込んで次の文字へ
					} else {
						throw new KadaiException(ErrorCode.ILLEGAL_INPUT_TIME);
					}
				}

				// Json制御文字による分岐
				if ('{' == jsonControlChar) {
					jsonControlChar = ':';
				} else if (':' == jsonControlChar) {
					// 月を取得
					month = sb.toString();// TODO
					sb = new StringBuilder();// 　初期化
					jsonControlChar = '[';
				} else if ('[' == jsonControlChar) {
					jsonControlChar = ']';
				} else if (']' == jsonControlChar) {

					WorkTimeMonth monthData = new WorkTimeMonth(month);

					String date = null;
					String workTime = null;
					String total = null;

					WorkTime dateData = new WorkTime(date, workTime, total);
					monthData.addDate(dateData);
					this.WorkTimeMonthList.add(monthData);

					System.out.println(sb.toString());

					sb = new StringBuilder();

					// 次のデータを読む
					ch = scanInputChar(bfRead, false);
					if (',' == ch) {
						// 次データがある場合
						jsonControlChar = ':';
					} else if ('}' == ch) {
						// 1レコード読み込み終了
						jsonControlChar = '{';
					} else {
						throw new KadaiException(ErrorCode.ILLEGAL_INPUT_TIME);
					}
				} else {
					throw new KadaiException(ErrorCode.ILLEGAL_INPUT_TIME);
				}

				// 次の文字を読み込み
				ch = scanInputChar(bfRead, false);
			}
		} catch (FileNotFoundException ex) {
			// 入力ファイルが存在している時
			File f = new File(this.inputFilePath);
			if (f.exists()) {
				throw new KadaiException(ErrorCode.FAILE_READ_INPUT_FILE);
			}
			throw new KadaiException(ErrorCode.NOT_EXIST_INPUT_FILE);
		} catch (KadaiException ex) {
			throw ex; // 存在しないキー、制御コード
		} catch (Exception ex) {
			// ファイル読み込みに失敗した時
			throw new KadaiException(ErrorCode.FAILE_READ_INPUT_FILE);
		} finally {
			// ファイルクローズ
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
	 * 読み込み内容の書き出し
	 *
	 * @param errorCode
	 *            読み込み時にエラーがあった場合のエラーコード(必要ない場合は-1を設定してください)
	 * @throws KadaiException
	 */
	public void writeOutputFile(int errorCode) throws KadaiException {

		if (null == this.workTimeList) {
			return;
		}

		BufferedWriter bfWrite = null;// 出力ファイル制御用変数

		try {
			// ファイルが存在するかどうか
			if (checkExistFile(this.outputFilePath)) {
				// 存在している場合は追記モードでオープン
				bfWrite = new BufferedWriter(new FileWriter(
						this.outputFilePath, true));
			} else {
				// 存在しない場合は新規作成
				bfWrite = new BufferedWriter(
						new FileWriter(this.outputFilePath));
			}

			// 出力用文字列リストの作成
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

			// 出力
			for (String line : outList) {
				// 書き込み
				bfWrite.write(line);
				bfWrite.newLine();
			}

			// 読み込み時エラーコードの書き込み(6,7,8は出力ファイル自体が存在しないため記述しない)
			if (-1 != errorCode && errorCode != 6 && errorCode != 7
					&& errorCode != 8) {
				bfWrite.write(String.valueOf(errorCode));
				bfWrite.newLine();
			}

		} catch (Exception ex) {
			// 出力失敗
			throw new KadaiException(ErrorCode.FAILE_FILE_OUT);
		} finally {
			// ファイルクローズ
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
	 * 次の文字を読む
	 * @param bfRead　ファイルリーダー
	 * @param skipFlag　不要文字を読み飛ばすかどうか　
	 * @return
	 * @throws KadaiException
	 * @throws IOException
	 */
	private int scanInputChar(BufferedReader bfRead, boolean skipFlag)
			throws KadaiException, IOException {

		int ch = bfRead.read(); // 読み込み
		if (skipFlag) {
			// 制御コードチェック
			if (ErrorUtil.isControlCode((char) ch)) {
				throw new KadaiException(ErrorCode.ILLEGAL_INPUT_TIME);
			}
			return ch;
		} else {
			// 空白を読み飛ばす
			while (-1 != ch) {
				// 空白コードチェック
				if (!CommonUtil.isSkipCode(ch)) {
					// 制御コードチェック
					if (ErrorUtil.isControlCode((char) ch)) {
						throw new KadaiException(ErrorCode.ILLEGAL_INPUT_TIME);
					}
					return ch;
				}
				ch = bfRead.read();
			}
			return ch;
		}
	}

	/**
	 * ファイルが存在しているかどうか
	 *
	 * @param filePath
	 * @return true/false
	 */
	private boolean checkExistFile(String filePath) {

		File f = new File(filePath);

		if (f.exists()) {
			return true;
		} else {
			return false;
		}
	}

}
