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
			.compile(SystemConstant.REGULAR_EXPRESSION_DATA);

	/**
	 * 入力ファイルパス
	 */
	private String inputFilePath = null;

	/**
	 * 出力ファイルパス
	 */
	private String outputFilePath = null;

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
	private List<WorkTimeMonth> WorkTimeMonthList = new ArrayList<WorkTimeMonth>();

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
			boolean bomCheck = true;

			// 入力ファイル読み込み　（制御コードエラーが出たらそこで打ち切り）
			while (-1 != (readChar = bfRead.read())) {

				// BOM読み飛ばし
				if (bomCheck && 65533 == readChar) {
					if (!CommonUtil.checkBom(bfRead)) {
						this.controlCodeError = true;
						break;
					}
					readChar = bfRead.read();
				}
				bomCheck = false;

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
	 * ファイル読み込みを行い、出力文字列リストを作成する　
	 *
	 * @throws KadaiException
	 */
	public void readInputFileLv2() throws KadaiException {

		BufferedReader bfRead = null;
		String month = null;// 月

		// 入力ファイルの読み込み
		try {
			// ファイルオープン
			bfRead = new BufferedReader(new InputStreamReader(
					new FileInputStream(this.inputFilePath)));

			StringBuilder sb = new StringBuilder();// キーorバリュー

			// 次のJsonコントロール文字
			int jsonControlChar = '{';

			// 読み込み
			int readChar = scanInputChar(bfRead, false, true);
			boolean skipFlag = false;
			while (-1 != readChar) {
				// Json制御文字が来た場合
				if (readChar != jsonControlChar) {
					if (':' == jsonControlChar || ']' == jsonControlChar) {
						// 追加
						sb.append((char) readChar);
						if (readChar == '"') {
							// "以降は文字列読み込み
							skipFlag = !skipFlag;
						}
						readChar = scanInputChar(bfRead, skipFlag,false);
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
					month = sb.toString().replace('"', ' ').trim();
					sb = new StringBuilder();// 　初期化
					jsonControlChar = '[';
				} else if ('[' == jsonControlChar) {
					jsonControlChar = ']';
				} else if (']' == jsonControlChar) {

					// 一か月分のデータを処理する
					WorkTimeMonth monthData = new WorkTimeMonth(month,
							sb.toString());
					monthData.scanInputData();// データの読み込み

					// 読み込み済みデータに追加
					this.WorkTimeMonthList.add(monthData);

					sb = new StringBuilder();

					// 次のデータを読む
					readChar = scanInputChar(bfRead, false,false);
					if (',' == readChar) {
						// 次データがある場合
						jsonControlChar = ':';
					} else if ('}' == readChar) {
						// 1レコード読み込み終了
						jsonControlChar = '{';
					} else {
						throw new KadaiException(ErrorCode.ILLEGAL_INPUT_TIME);
					}
				} else {
					throw new KadaiException(ErrorCode.ILLEGAL_INPUT_TIME);
				}

				// 次の文字を読み込み
				readChar = scanInputChar(bfRead, false, false);
			}
		} catch (FileNotFoundException ex) {
			// 入力ファイルが存在している時
			File f = new File(this.inputFilePath);
			if (f.exists()) {
				throw new KadaiException(ErrorCode.FAILE_READ_INPUT_FILE);
			}
			throw new KadaiException(ErrorCode.NOT_EXIST_INPUT_FILE);
		} catch (KadaiException ex) {
			throw ex;// 制御コード
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
			bfWrite = new BufferedWriter(new FileWriter(this.outputFilePath));
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
	 * Lv2用ファイル書き出し
	 *
	 * @throws KadaiException
	 */
	public void writeOutPutFileLv2(String dirPath) throws KadaiException {

		for (WorkTimeMonth workTimeMonth : this.WorkTimeMonthList) {

			BufferedWriter bfWrite = null;
			String month = workTimeMonth.getMonth();
			String fullPath = String.format("%s//%s.txt", dirPath, month);// 書き込みパス
			int errorCode = workTimeMonth.getErrorCode();// errorCodeの取得

			try {

				// ディレクトリが存在しない場合は作成する
				File dir = new File(dirPath);
				if (!dir.exists()) {
					dir.mkdirs();
				}

				bfWrite = new BufferedWriter(new FileWriter(fullPath));

				// 出力用文字列リストの作成
				ArrayList<String> outList = new ArrayList<String>();
				List<WorkTime> workTimeList = workTimeMonth.getWorkTimeList();

				// 日付順でソート
				for (int i = 0; i < workTimeList.size() - 1; ++i) {
					WorkTime wt1 = workTimeList.get(i);
					WorkTime wt2 = workTimeList.get(i + 1);
					if (0 < wt1.getDate().compareTo(wt2.getDate())) {
						WorkTime wtTemp = wt1;
						workTimeList.set(i, wt2);
						workTimeList.set(i + 1, wtTemp);
					}
				}

				// 出力文字列の作成
				int total = 0;
				outList.add("[");
				for (int i = 0; i < workTimeList.size(); ++i) {

					// totalを設定する
					WorkTime wt = workTimeList.get(i);
					int workTime = Integer.parseInt(wt.getWorkTime());
					wt.setTotal(String.valueOf(total += workTime));

					// 書き込みJson文字列の作成
					StringBuilder line = new StringBuilder(workTimeList.get(i)
							.createJsonStr());
					if (workTimeList.size() - 1 != i) {
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
			// errorが存在したら終了
			if (0 < errorCode) {
				break;
			}
		}
	}

	/**
	 * 次の文字を読む
	 *
	 * @param bfRead
	 *            ファイルリーダー
	 * @param skipFlag
	 *            不要文字を読み飛ばすかどうか　
	 * @return
	 * @throws KadaiException
	 * @throws IOException
	 */
	private int scanInputChar(BufferedReader bfRead, boolean skipFlag,
			boolean bomCheck) throws KadaiException, IOException {

		int readChar = bfRead.read(); // 読み込み
		if (skipFlag) {
			// 制御コードチェック
			if (ErrorUtil.isControlCode((char) readChar)) {
				throw new KadaiException(ErrorCode.ILLEGAL_INPUT_TIME);
			}
			return readChar;
		} else {
			// 空白を読み飛ばす
			while (-1 != readChar) {
				// 初回のみBOMチェック
				if (bomCheck && 65533 == readChar) {
					if (!CommonUtil.checkBom(bfRead)) {
						throw new KadaiException(
								ErrorCode.IS_CTRL_CODE);
					}
					readChar = bfRead.read();
				}
				// 空白コードチェック
				if (!CommonUtil.isSkipCode(readChar)) {
					// 制御コードチェック
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
