package jp.ktsystem.kadai201403.y_murakami.Util;

import java.io.BufferedReader;
import java.io.IOException;

/**
 * 共通Utillクラス
 *
 * @author y_murakami
 *
 */
public class CommonUtil {

	/**
	 * キーとバリューからJson形式文字列を返す
	 *
	 * @param key
	 *            キー文字列
	 * @param value
	 *            値文字列
	 * @param isStr
	 *            文字列かどうか
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
	 *　読み飛ばしの対象文字列かどうかチェックする
	 * @param ch 文字
	 * @return
	 */
	public static boolean isSkipCode(int ch) {

		int tab = 0x09; // 水平タブ
		int ret = 0x0A; // 改行
		int cr = 0x0D; // キャリッジリターン
		int s = 0x20; // スペース

		if (tab == ch || ret == ch || cr == ch || s == ch) {
			return true;
		}

		return false;

	}

	/**
	 * BOMの読み飛ばし
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
