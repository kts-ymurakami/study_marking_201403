package jp.ktsystem.kadai201403.y_murakami;

import jp.ktsystem.kadai201403.y_murakami.Common.ErrorCode;
import jp.ktsystem.kadai201403.y_murakami.Common.KadaiException;

/**
 * 時間を扱うクラス
 * @author y_murakami
 */
public class KadaiTime {

	/**********Member**********/

	/**
	 * 時間　時　整数型
	 */
	private int intHour = 0;
	/**
	 * 時間　分　文字列
	 */
	private int intMinute = 0;

	/**********Constructor**********/

	/**
	 * コンストラクタ　HHmmの文字列を受け取ってメンバに値を設定する
	 * @param strTime
	 * @throws KadaiException
	 */
	public KadaiTime(String strTime) throws KadaiException {

		String strHour = strTime.substring(0, 2);// HHを取り出す
		String strMinute = strTime.substring(2, 4);// mmを取り出す

		try {
			this.intHour = Integer.parseInt(strHour);
			this.intMinute = Integer.parseInt(strMinute);
		} catch (NumberFormatException ex) {
			throw new KadaiException(ErrorCode.ILLEGAL_INPUT_TIME);
		}

	}

	/**********Getter**********/

	/**
	 * 時間整数型取得
	 */
	public int getIntHour() {
		return intHour;
	}

	/**
	 * 分整数型取得
	 * @return
	 */
	public int getIntMinute() {
		return intMinute;
	}

	/**********Setter**********/

	/**
	 * 時間整数型設定
	 */
	public void setIntHour(int value) {
		this.intHour = value;
	}

	/**
	 * 分整数型設定
	 */
	public void setIntMinute(int value) {
		this.intMinute = value;
	}

}
