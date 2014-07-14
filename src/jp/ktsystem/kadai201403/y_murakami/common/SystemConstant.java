package jp.ktsystem.kadai201403.y_murakami.common;

import java.util.regex.Pattern;

/**
 * 定数
 * @author y_murakami
 */
public class SystemConstant {

	/**
	 * 開始時
	 */
	public static final int WORK_START_HOUR_MORNING = 9;

	/**
	 * 休憩開始時
	 */
	public static final int START_REST_HOUR_NOON = 12;

	/**
	 * お昼始業
	 */
	public static final int START_HOUR_NOON = 13;

	/**
	 * 夕休
	 */
	public static final int REST_HOUR_EVE = 18;

	/**
	 * 夕始業分
	 */
	public static final int START_EVE_MINUTE = 30;

	/**
	 * 昼休憩時間
	 */
	public static final int REST_TIME_NOON = 60;

	/**
	 * 夕休憩時間
	 */
	public static final int REST_TIME_EVE = 30;

	/**
	 *　1時間
	 */
	public static final int MINUTE_OF_ONE_HOUR = 60;

	/**
	 * 最少分
	 */
	public static final int MINIMUM_TIME = 0;

	/**
	 * 時間のパターン
	 */
	public static final Pattern INPUT_TIME_PATTERN = Pattern
			.compile("^[0-9][0-9][0-9][0-9]$");

	/**
	 * 出社時間　パターン
	 */
	public static final Pattern INPUT_TIME_START_PATTERN = Pattern
			.compile("^([0-1][0-9]|[2][0-3])[0-5][0-9]$");

	/**
	 * 退社時間　パターン
	 */
	public static final Pattern INPUT_TIME_END_PATTERN = Pattern
			.compile("^([0-2][0-9]|[3][0-2])[0-5][0-9]$");

	/**
	 * ｛｝で囲まれたもの
	 */
	public static final String REGULAR_EXPRESSION_STR_INPUT_FILE = "\\{[\\s\\S]*?\\}";

	/**
	 * ”：”
	 */
	public static final String REGULAR_EXPRESSION_DATA = "[\\s]*\"([a-zA-z]*)\"[\\s]*:[\\s]*\"(.*?)\"";


	/**
	 * ""で囲まれたもの
	 */
	public static final String REGULAR_EXPRESSION_DOUBLE_QUOTATION = "\"(.*?)\"";


	/**
	 * 入力列数
	 */
	public static final int INPUT_FILE_COLUMN_NUM = 3;

	/**
	 * 出力列
	 */
	public static final int OUTPUT_FILE_COLUMN_NUM = 3;

	/**
	 * 日付フォーマット
	 */
	public static final String INPUT_DATA_FORMAT = "yyyyMMdd";

	/**
	 * 日付　キー
	 */
	public static final String KEY_DATE = "date";

	/**
	 * 出社時間　キー
	 */
	public static final String KEY_START = "start";

	/**
	 * 退社時間　キー
	 */
	public static final String KEY_END = "end";

	/**
	 * 勤務時間　キー
	 */
	public static final String KEY_WORK_TIME = "workTime";

	/**
	 * 総時間　キー
	 */
	public static final String KEY_TOTAL = "total";

	/**
	 * ファイルパスを作成するためのフォーマット
	 */
	public static final String CREATE_FILE_PATH_FORMAT = "%s/%s.txt";

}
