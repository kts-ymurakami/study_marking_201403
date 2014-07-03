package jp.ktsystem.kadai201403.y_murakami.util;

import java.io.BufferedReader;
import java.io.IOException;

/**
 * Utilクラス
 *
 * @author y_murakami
 *
 */
public class CommonUtil {

	/**
	 * Jsonを作成する
	 *
	 * @param key
	 *            キー
	 * @param value
	 *            値
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
	 *　許可された制御コードかどうか
	 * @param ch チェック文字
	 * @return
	 */
	public static boolean isSkipCode(int ch) {

		int tab = 0x09; // タブ
		int ret = 0x0A; // 解消
		int cr = 0x0D; // キャリッジリターン
		int s = 0x20; // スペース

		if (tab == ch || ret == ch || cr == ch || s == ch) {
			return true;
		}

		return false;

	}

	/**
	 * BOMチェック
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
