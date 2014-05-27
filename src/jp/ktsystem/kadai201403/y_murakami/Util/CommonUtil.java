package jp.ktsystem.kadai201403.y_murakami.Util;

/**
 * ����Utill�N���X
 *
 * @author y_murakami
 *
 */
public class CommonUtil {

	/**
	 * �L�[�ƃo�����[����Json�`���������Ԃ�
	 *
	 * @param key
	 *            �L�[������
	 * @param value
	 *            �l������
	 * @param isStr
	 *            �����񂩂ǂ���
	 * @return "key":"value" or "key":value
	 */
	public static String createJsonStr(String key, String value, boolean isStr) {

		if (isStr) {
			return String.format("\"%s\":\"%s\"", key, value);
		} else {
			return String.format("\"%s\":%s", key, value);
		}

	}

	/**
	 *�@�ǂݔ�΂��̑Ώە����񂩂ǂ����`�F�b�N����
	 * @param ch ����
	 * @return
	 */
	public static boolean isSkipCode(int ch) {

		int tab = 0x09; // �����^�u
		int ret = 0x0A; // ���s
		int cr = 0x0D; // �L�����b�W���^�[��
		int s = 0x20; // �X�y�[�X

		if (tab == ch || ret == ch || cr == ch || s == ch) {
			return true;
		}

		return false;

	}



}
