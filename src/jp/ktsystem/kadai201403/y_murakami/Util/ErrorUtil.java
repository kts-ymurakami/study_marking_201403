package jp.ktsystem.kadai201403.y_murakami.Util;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import jp.ktsystem.kadai201403.y_murakami.KadaiTime;
import jp.ktsystem.kadai201403.y_murakami.Common.ErrorCode;
import jp.ktsystem.kadai201403.y_murakami.Common.KadaiException;
import jp.ktsystem.kadai201403.y_murakami.Common.SystemConstant;

/**
 * エラーチェック
 *
 * @author y_murakami
 */
public class ErrorUtil {

	/**
	 * 入力チェック用　正規表現Pattern
	 */
	public static final Pattern INPUT_TIME_PATTERN = Pattern
			.compile(SystemConstant.REGULAR_EXPRESSION_STR);

	/**
	 * 出社時間入力チェック用　正規表現Pattern
	 */
	public static final Pattern INPUT_TIME_START_PATTERN = Pattern
			.compile(SystemConstant.REGULAR_EXPRESSION_STR_START_TIME);

	/**
	 * 退社時間入力チェック用　正規表現Pattern
	 */
	public static final Pattern INPUT_TIME_END_PATTERN = Pattern
			.compile(SystemConstant.REGULAR_EXPRESSION_STR_END_TIME);

	/**
	 * 正規表現を用いて入力時刻文字列が仕様通りかチェックする
	 *
	 * @param strTime
	 * @return true/false
	 * @throws KadaiException
	 */
	public static boolean checkStrTime(String startTime, String endTime)
			throws KadaiException {

		// null or 空文字　チェク
		if (!checkNullOrEmpty(startTime) || !checkNullOrEmpty(endTime)) {
			throw new KadaiException(ErrorCode.NULL_OR_EMPTY);
		}

		// 入力文字列不正文字チェック
		if (!checkMatches(INPUT_TIME_PATTERN, startTime)
				|| !checkMatches(INPUT_TIME_PATTERN, endTime)) {
			throw new KadaiException(ErrorCode.ILLEGAL_INPUT_TIME);
		}

		// 出社時刻チェック 退社時刻チェック
		if (!checkMatches(INPUT_TIME_START_PATTERN, startTime)
				|| !checkMatches(INPUT_TIME_END_PATTERN, endTime)) {
			throw new KadaiException(ErrorCode.ILLEGAL_START_OR_END_TIME);
		}

		return true;
	}

	/**
	 * 出社時刻が退社時刻より前かどうか
	 *
	 * @return
	 * @throws KadaiException
	 *             　エラーコード3
	 */
	public static boolean endTimeBeforeStartTime(KadaiTime startTime,
			KadaiTime endTime) throws KadaiException {

		int startHour = startTime.getIntHour();// 出社時
		int startMinute = startTime.getIntMinute();// 出社分
		int endHour = endTime.getIntHour();// 退社時
		int endMinute = endTime.getIntMinute();// 退社分

		// 2014/1/25 追加　出社時刻、退社時刻ともに始業時刻前の時は同様のエラーコードを出力するため。
		if (SystemConstant.WORK_START_HOUR_MORNING > startHour
				&& SystemConstant.WORK_START_HOUR_MORNING > endHour) {
			return false;
		}

		if (startHour > endHour) {
			return false;
		} else if (startHour == endHour && startMinute > endMinute) {
			return false;
		} else {
			return true;
		}
	}

	/**
	 * nullチェック
	 *
	 * @param checkStr
	 * @return
	 */
	public static boolean checkNull(String checkStr) {

		return !(null == checkStr);
	}

	/**
	 * 入力日付のチェック
	 *
	 * @return
	 */
	public static boolean checkDate(String inputDate) {

		if (null == inputDate) {
			return false;
		}

		DateFormat df = new SimpleDateFormat(SystemConstant.INPUT_DATA_FORMAT);
		df.setLenient(false);// 厳密にチェック

		try {
			df.parse(inputDate);
		} catch (ParseException ex) {
			return false;
		}

		return true;
	}

	/**
	 * null or Empty チェック
	 *
	 * @param checkStr
	 * @return true/false
	 */
	public static boolean checkNullOrEmpty(String checkStr) {

		// nullだったらfalse
		if (!checkNull(checkStr)) {
			return false;
		}
		// 空だったらfalse

		return !checkStr.isEmpty();

	}

	/**
	 * 文字が制御文字（改行、空白、タブを除く）かどうかを判断する
	 *
	 * @param ch
	 *            チェック文字
	 * @return true/false
	 */
	public static boolean isControlCode(char ch) {

		// 制御コード文字かどうか
		if (0x1f >= ch && 0x00 <= ch) {
			int tab = 0x09; // 水平タブ
			int newline = 0x0A; // 改行
			int space = 0x20; // 空白
			int cr = 0x0D; // キャリッジリターン

			if (tab == ch || newline == ch || space == ch || cr == ch) {
				return false;
			}

			return true;
		}
		return false;
	}

	/**
	 * パターンと文字列からMatchesの結果を返す
	 *
	 * @param ptr
	 *            入力パターン
	 * @param checkStr
	 *            チェック文字列
	 * @return true/false
	 */
	private static boolean checkMatches(Pattern pattern, String checkStr) {

		// Matcherを入力文字列から生成
		Matcher inputMatcher = pattern.matcher(checkStr);

		return inputMatcher.matches();

	}

}
