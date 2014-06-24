package jp.ktsystem.kadai201403.y_murakami.util;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import jp.ktsystem.kadai201403.y_murakami.KadaiTime;
import jp.ktsystem.kadai201403.y_murakami.common.ErrorCode;
import jp.ktsystem.kadai201403.y_murakami.common.KadaiException;
import jp.ktsystem.kadai201403.y_murakami.common.SystemConstant;

/**
 * �ｿｽG�ｿｽ�ｿｽ�ｿｽ[�ｿｽ`�ｿｽF�ｿｽb�ｿｽN
 *
 * @author y_murakami
 */
public class ErrorUtil {

	/**
	 * �ｿｽ�ｿｽ�ｿｽﾍチ�ｿｽF�ｿｽb�ｿｽN�ｿｽp�ｿｽ@�ｿｽ�ｿｽ�ｿｽK�ｿｽ\�ｿｽ�ｿｽPattern
	 */
	public static final Pattern INPUT_TIME_PATTERN = Pattern
			.compile("^[0-9][0-9][0-9][0-9]$");

	/**
	 * �ｿｽo�ｿｽﾐ趣ｿｽ�ｿｽﾔ難ｿｽ�ｿｽﾍチ�ｿｽF�ｿｽb�ｿｽN�ｿｽp�ｿｽ@�ｿｽ�ｿｽ�ｿｽK�ｿｽ\�ｿｽ�ｿｽPattern
	 */
	public static final Pattern INPUT_TIME_START_PATTERN = Pattern
			.compile("^([0-1][0-9]|[2][0-3])[0-5][0-9]$");

	/**
	 * �ｿｽﾞ社趣ｿｽ�ｿｽﾔ難ｿｽ�ｿｽﾍチ�ｿｽF�ｿｽb�ｿｽN�ｿｽp�ｿｽ@�ｿｽ�ｿｽ�ｿｽK�ｿｽ\�ｿｽ�ｿｽPattern
	 */
	public static final Pattern INPUT_TIME_END_PATTERN = Pattern
			.compile("^([0-2][0-9]|[3][0-2])[0-5][0-9]$");

	private static DateFormat df = new SimpleDateFormat(SystemConstant.INPUT_DATA_FORMAT);


	/**
	 * 出勤時間と退勤時間のエラーチェック
	 * @param startTime
	 * @param endTime
	 * @return
	 * @throws KadaiException
	 */
	public static boolean checkStrTime(String startTime, String endTime)
			throws KadaiException {

		// null or �ｿｽｶ趣ｿｽ�ｿｽ@�ｿｽ`�ｿｽF�ｿｽN
		if ((null == (startTime) || startTime.isEmpty()) || (null == (endTime) || endTime.isEmpty())) {
			throw new KadaiException(ErrorCode.NULL_OR_EMPTY);
		}

		// �ｿｽ�ｿｽ�ｿｽﾍ包ｿｽ�ｿｽ�ｿｽ�ｿｽ�ｿｽs�ｿｽ�ｿｽ�ｿｽ�ｿｽ�ｿｽ�ｿｽ�ｿｽ`�ｿｽF�ｿｽb�ｿｽN
		if (!checkMatches(INPUT_TIME_PATTERN, startTime)
				|| !checkMatches(INPUT_TIME_PATTERN, endTime)) {
			throw new KadaiException(ErrorCode.ILLEGAL_INPUT_TIME);
		}

		// �ｿｽo�ｿｽﾐ趣ｿｽ�ｿｽ�ｿｽ�ｿｽ`�ｿｽF�ｿｽb�ｿｽN �ｿｽﾞ社趣ｿｽ�ｿｽ�ｿｽ�ｿｽ`�ｿｽF�ｿｽb�ｿｽN
		if (!checkMatches(INPUT_TIME_START_PATTERN, startTime)
				|| !checkMatches(INPUT_TIME_END_PATTERN, endTime)) {
			throw new KadaiException(ErrorCode.ILLEGAL_START_OR_END_TIME);
		}

		return true;
	}

	/**
	 * �ｿｽo�ｿｽﾐ趣ｿｽ�ｿｽ�ｿｽ�ｿｽ�ｿｽ�ｿｽﾞ社趣ｿｽ�ｿｽ�ｿｽ�ｿｽ�ｿｽ�ｿｽO�ｿｽ�ｿｽ�ｿｽﾇゑｿｽ�ｿｽ�ｿｽ
	 *
	 * @return
	 * @throws KadaiException
	 *             �ｿｽ@�ｿｽG�ｿｽ�ｿｽ�ｿｽ[�ｿｽR�ｿｽ[�ｿｽh3
	 */
	public static boolean startTimeBeforeEndTime(KadaiTime startTime,
			KadaiTime endTime) {

		int startHour = startTime.getIntHour();// �ｿｽo�ｿｽﾐ趣ｿｽ
		int startMinute = startTime.getIntMinute();// �ｿｽo�ｿｽﾐ包ｿｽ
		int endHour = endTime.getIntHour();// �ｿｽﾞ社趣ｿｽ
		int endMinute = endTime.getIntMinute();// �ｿｽﾞ社包ｿｽ

		// 2014/1/25 �ｿｽﾇ会ｿｽ�ｿｽ@�ｿｽo�ｿｽﾐ趣ｿｽ�ｿｽ�ｿｽ�ｿｽA�ｿｽﾞ社趣ｿｽ�ｿｽ�ｿｽ�ｿｽﾆゑｿｽ�ｿｽﾉ始�ｿｽﾆ趣ｿｽ�ｿｽ�ｿｽ�ｿｽO�ｿｽﾌ趣ｿｽ�ｿｽﾍ難ｿｽ�ｿｽl�ｿｽﾌエ�ｿｽ�ｿｽ�ｿｽ[�ｿｽR�ｿｽ[�ｿｽh�ｿｽ�ｿｽ�ｿｽo�ｿｽﾍゑｿｽ�ｿｽ驍ｽ�ｿｽﾟ。
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
	 * 日付かどうかチェックする
	 * @param inputDate　チェック対象文字列
	 * @return
	 */
	public static boolean checkDate(String inputDate) {

		if (null == inputDate) {
			return false;
		}
		df.setLenient(false);// �ｿｽ�ｿｽ�ｿｽ�ｿｽ�ｿｽﾉチ�ｿｽF�ｿｽb�ｿｽN

		try {
			df.parse(inputDate);
		} catch (ParseException ex) {
			return false;
		}

		return true;
	}

	/**
	 * �ｿｽ�ｿｽ�ｿｽ�ｿｽ�ｿｽ�ｿｽ�ｿｽ�ｿｽ�ｿｽ苺ｶ�ｿｽ�ｿｽ�ｿｽi�ｿｽ�ｿｽ�ｿｽs�ｿｽA�ｿｽ秩A�ｿｽ^�ｿｽu�ｿｽ�ｿｽ�ｿｽ�ｿｽ�ｿｽ�ｿｽ�ｿｽj�ｿｽ�ｿｽ�ｿｽﾇゑｿｽ�ｿｽ�ｿｽ�ｿｽｻ断�ｿｽ�ｿｽ�ｿｽ�ｿｽ
	 *
	 * @param ch
	 *            �ｿｽ`�ｿｽF�ｿｽb�ｿｽN�ｿｽ�ｿｽ�ｿｽ�ｿｽ
	 * @return true/false
	 */
	public static boolean isControlCode(char ch) {

		// �ｿｽ�ｿｽ�ｿｽ�ｿｽR�ｿｽ[�ｿｽh�ｿｽ�ｿｽ�ｿｽ�ｿｽ�ｿｽ�ｿｽ�ｿｽﾇゑｿｽ�ｿｽ�ｿｽ
		if (0x1f >= ch && 0x00 <= ch) {
			int tab = 0x09; // �ｿｽ�ｿｽ�ｿｽ�ｿｽ�ｿｽ^�ｿｽu
			int newline = 0x0A; // �ｿｽ�ｿｽ�ｿｽs
			int space = 0x20; // �ｿｽ�ｿｽ
			int cr = 0x0D; // �ｿｽL�ｿｽ�ｿｽ�ｿｽ�ｿｽ�ｿｽb�ｿｽW�ｿｽ�ｿｽ�ｿｽ^�ｿｽ[�ｿｽ�ｿｽ

			if (tab == ch || newline == ch || space == ch || cr == ch) {
				return false;
			}

			return true;
		}
		return false;
	}

	/**
	 * パターンに文字列がマッチするかどうか
	 * @param pattern　パターン
	 * @param checkStr　チェック文字列
	 * @return
	 */
	private static boolean checkMatches(Pattern pattern, String checkStr) {

		// Matcher�ｿｽ�ｿｽ�ｿｽ�ｿｽﾍ包ｿｽ�ｿｽ�ｿｽ�ｿｽｩら生�ｿｽ�ｿｽ
		Matcher inputMatcher = pattern.matcher(checkStr);

		return inputMatcher.matches();

	}

}
