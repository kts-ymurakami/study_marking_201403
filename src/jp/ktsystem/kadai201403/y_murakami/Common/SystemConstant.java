package jp.ktsystem.kadai201403.y_murakami.Common;

/**
 * 定数クラス
 * @author y_murakami
 */
public class SystemConstant {

	/**
	 * 始業時刻
	 */
	public static final int WORK_START_HOUR_MORNING = 9;

	/**
	 * お昼休憩開始時
	 */
	public static final int START_REST_HOUR_NOON = 12;

	/**
	 * お昼休憩時終了時
	 */
	public static final int START_HOUR_NOON = 13;

	/**
	 * 夕方休憩開始時
	 */
	public static final int REST_HOUR_EVE = 18;

	/**
	 * 夕始業時　分
	 */
	public static final int START_EVE_MINUTE = 30;

	/**
	 * お昼休憩時間
	 */
	public static final int REST_TIME_NOON = 60;

	/**
	 * 夕方休憩時間
	 */
	public static final int REST_TIME_EVE = 30;

	/**
	 * 1時間の分
	 */
	public static final int MINUTE_OF_ONE_HOUR = 60;

	/**
	 * 最小時間
	 */
	public static final int MINIMUM_TIME = 0;

	/**
	 * 入力文字列チェック　正規表現文字列　桁数、半角全角、不正文字列を排除する用
	 */
	public static final String REGULAR_EXPRESSION_STR = "^[0-9][0-9][0-9][0-9]$";

	/**
	 * 出社時間チェック　正規表現文字列　HH 00~23(出社が深夜扱いになることはないため) mm 00~59(1時間の分)
	 */
	public static final String REGULAR_EXPRESSION_STR_START_TIME = "^([0-1][0-9]|[2][0-3])[0-5][0-9]$";

	/**
	 * 退社時間チェック　正規表現文字列　HH 00~32 mm 00~59(1時間の分)
	 */
	public static final String REGULAR_EXPRESSION_STR_END_TIME = "^([0-2][0-9]|[3][0-2])[0-5][0-9]$";

	/**
	 * 入力ファイルから、対象データの取り出し用　正規表現文字列　｛｝でくくられたものすべて
	 */
	public static final String REGULAR_EXPRESSION_STR_INPUT_FILE = "\\{[\\s\\S]*?\\}";

	/**
	 * 対象データの取り出し用　正規表現文字列
	 */
	public static final String REGULAR_EXPRESSION_DATA = "[\\s]*\"([a-zA-z]*)\"[\\s]*:[\\s]*\"(.*?)\"";


	/**
	 * ""内のデータを取り出す　正規表現文字列
	 */
	public static final String REGULAR_EXPRESSION_DOUBLE_QUOTATION = "\"(.*?)\"";


	/**
	 * 入力ファイルの項目数　日付　出社時刻　退社時刻　
	 */
	public static final int INPUT_FILE_COLUMN_NUM = 3;

	/**
	 * 出力ファイルの項目数　日付　勤務時間　総勤務時間
	 */
	public static final int OUTPUT_FILE_COLUMN_NUM = 3;

	/**
	 * 入力ファイル日付文字列フォーマット
	 */
	public static final String INPUT_DATA_FORMAT = "yyyyMMdd";

	/**
	 * キー文字列 date
	 */
	public static final String KEY_DATE = "date";

	/**
	 * キー文字列 start
	 */
	public static final String KEY_START = "start";

	/**
	 * キー文字列 end
	 */
	public static final String KEY_END = "end";

	/**
	 * キー文字列 workTime
	 */
	public static final String KEY_WORK_TIME = "workTime";

	/**
	 * キー文字列 total
	 */
	public static final String KEY_TOTAL = "total";

}
