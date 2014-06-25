package jp.ktsystem.kadai201403.y_murakami.util;

import java.io.BufferedReader;
import java.io.IOException;

/**
 * �ｿｽ�ｿｽ�ｿｽ�ｿｽUtill�ｿｽN�ｿｽ�ｿｽ�ｿｽX
 *
 * @author y_murakami
 *
 */
public class CommonUtil {

	/**
	 * �ｿｽL�ｿｽ[�ｿｽﾆバ�ｿｽ�ｿｽ�ｿｽ�ｿｽ�ｿｽ[�ｿｽ�ｿｽ�ｿｽ�ｿｽJson�ｿｽ`�ｿｽ�ｿｽ�ｿｽ�ｿｽ�ｿｽ�ｿｽ�ｿｽ�ｿｽ�ｿｽﾔゑｿｽ
	 *
	 * @param key
	 *            �ｿｽL�ｿｽ[�ｿｽ�ｿｽ�ｿｽ�ｿｽ�ｿｽ�ｿｽ
	 * @param value
	 *            �ｿｽl�ｿｽ�ｿｽ�ｿｽ�ｿｽ�ｿｽ�ｿｽ
	 * @param isStr
	 *            �ｿｽ�ｿｽ�ｿｽ�ｿｽ�ｿｽｩどゑｿｽ�ｿｽ�ｿｽ
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
	 *�ｿｽ@�ｿｽﾇみ費ｿｽﾎゑｿｽ�ｿｽﾌ対象包ｿｽ�ｿｽ�ｿｽ�ｿｽｩどゑｿｽ�ｿｽ�ｿｽ�ｿｽ`�ｿｽF�ｿｽb�ｿｽN�ｿｽ�ｿｽ�ｿｽ�ｿｽ
	 * @param ch �ｿｽ�ｿｽ�ｿｽ�ｿｽ
	 * @return
	 */
	public static boolean isSkipCode(int ch) {

		int tab = 0x09; // �ｿｽ�ｿｽ�ｿｽ�ｿｽ�ｿｽ^�ｿｽu
		int ret = 0x0A; // �ｿｽ�ｿｽ�ｿｽs
		int cr = 0x0D; // �ｿｽL�ｿｽ�ｿｽ�ｿｽ�ｿｽ�ｿｽb�ｿｽW�ｿｽ�ｿｽ�ｿｽ^�ｿｽ[�ｿｽ�ｿｽ
		int s = 0x20; // �ｿｽX�ｿｽy�ｿｽ[�ｿｽX

		if (tab == ch || ret == ch || cr == ch || s == ch) {
			return true;
		}

		return false;

	}

	/**
	 * BOM�ｿｽﾌ読み費ｿｽﾎゑｿｽ
	 * @param bfRead
	 * @return
	 * @throws IOException
	 */
	public static boolean checkBom(BufferedReader bfRead) throws IOException{
		int b2 = 0;
		int b3 = 0;
		if (-1 != (b2 = (byte) bfRead.read())
				&& ((byte) 65403 == b2)) {
			if (-1 != (b3 = (byte) bfRead.read())
					&& ((byte) 65407 == b3)) {
				return true;
			}
		}
		return false;
	}


}
