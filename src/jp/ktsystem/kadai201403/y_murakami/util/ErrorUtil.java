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
 * �G���[�`�F�b�N
 *
 * @author y_murakami
 */
public class ErrorUtil {

	/**
	 * ���̓`�F�b�N�p�@���K�\��Pattern
	 */
	public static final Pattern INPUT_TIME_PATTERN = Pattern
			.compile("^[0-9][0-9][0-9][0-9]$");

	/**
	 * �o�Ў��ԓ��̓`�F�b�N�p�@���K�\��Pattern
	 */
	public static final Pattern INPUT_TIME_START_PATTERN = Pattern
			.compile("^([0-1][0-9]|[2][0-3])[0-5][0-9]$");

	/**
	 * �ގЎ��ԓ��̓`�F�b�N�p�@���K�\��Pattern
	 */
	public static final Pattern INPUT_TIME_END_PATTERN = Pattern
			.compile("^([0-2][0-9]|[3][0-2])[0-5][0-9]$");

	/**
	 * ���K�\����p���ē��͎��������񂪎d�l�ʂ肩�`�F�b�N����
	 *
	 * @param strTime
	 * @return true/false
	 * @throws KadaiException
	 */
	public static boolean checkStrTime(String startTime, String endTime)
			throws KadaiException {

		// null or �󕶎��@�`�F�N
		if (!checkNullOrEmpty(startTime) || !checkNullOrEmpty(endTime)) {
			throw new KadaiException(ErrorCode.NULL_OR_EMPTY);
		}

		// ���͕�����s�������`�F�b�N
		if (!checkMatches(INPUT_TIME_PATTERN, startTime)
				|| !checkMatches(INPUT_TIME_PATTERN, endTime)) {
			throw new KadaiException(ErrorCode.ILLEGAL_INPUT_TIME);
		}

		// �o�Ў����`�F�b�N �ގЎ����`�F�b�N
		if (!checkMatches(INPUT_TIME_START_PATTERN, startTime)
				|| !checkMatches(INPUT_TIME_END_PATTERN, endTime)) {
			throw new KadaiException(ErrorCode.ILLEGAL_START_OR_END_TIME);
		}

		return true;
	}

	/**
	 * �o�Ў������ގЎ������O���ǂ���
	 *
	 * @return
	 * @throws KadaiException
	 *             �@�G���[�R�[�h3
	 */
	public static boolean endTimeBeforeStartTime(KadaiTime startTime,
			KadaiTime endTime) throws KadaiException {

		int startHour = startTime.getIntHour();// �o�Ў�
		int startMinute = startTime.getIntMinute();// �o�Е�
		int endHour = endTime.getIntHour();// �ގЎ�
		int endMinute = endTime.getIntMinute();// �ގЕ�

		// 2014/1/25 �ǉ��@�o�Ў����A�ގЎ����Ƃ��Ɏn�Ǝ����O�̎��͓��l�̃G���[�R�[�h���o�͂��邽�߁B
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
	 * null�`�F�b�N
	 *
	 * @param checkStr
	 * @return
	 */
	public static boolean checkNull(String checkStr) {

		return !(null == checkStr);
	}

	/**
	 * ���͓��t�̃`�F�b�N
	 *
	 * @return
	 */
	public static boolean checkDate(String inputDate) {

		if (null == inputDate) {
			return false;
		}

		DateFormat df = new SimpleDateFormat(SystemConstant.INPUT_DATA_FORMAT);
		df.setLenient(false);// �����Ƀ`�F�b�N

		try {
			df.parse(inputDate);
		} catch (ParseException ex) {
			return false;
		}

		return true;
	}

	/**
	 * null or Empty �`�F�b�N
	 *
	 * @param checkStr
	 * @return true/false
	 */
	public static boolean checkNullOrEmpty(String checkStr) {

		// null��������false
		if (!checkNull(checkStr)) {
			return false;
		}
		// �󂾂�����false

		return !checkStr.isEmpty();

	}

	/**
	 * ���������䕶���i���s�A�󔒁A�^�u�������j���ǂ����𔻒f����
	 *
	 * @param ch
	 *            �`�F�b�N����
	 * @return true/false
	 */
	public static boolean isControlCode(char ch) {

		// ����R�[�h�������ǂ���
		if (0x1f >= ch && 0x00 <= ch) {
			int tab = 0x09; // �����^�u
			int newline = 0x0A; // ���s
			int space = 0x20; // ��
			int cr = 0x0D; // �L�����b�W���^�[��

			if (tab == ch || newline == ch || space == ch || cr == ch) {
				return false;
			}

			return true;
		}
		return false;
	}

	/**
	 * null�Ƌ󕶎����`�F�b�N����
	 * @param str
	 * @return
	 */
	public static boolean isNullorEmpty(String str){
		return null == str || "".equals(str);
	}

	/**
	 * �p�^�[���ƕ����񂩂�Matches�̌��ʂ�Ԃ�
	 *
	 * @param ptr
	 *            ���̓p�^�[��
	 * @param checkStr
	 *            �`�F�b�N������
	 * @return true/false
	 */
	private static boolean checkMatches(Pattern pattern, String checkStr) {

		// Matcher����͕����񂩂琶��
		Matcher inputMatcher = pattern.matcher(checkStr);

		return inputMatcher.matches();

	}

}
