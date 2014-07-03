package jp.ktsystem.kadai201403.y_murakami;

import jp.ktsystem.kadai201403.y_murakami.common.ErrorCode;
import jp.ktsystem.kadai201403.y_murakami.common.KadaiException;

/**
 * 課題クラス
 * @author y_murakami
 */
public class KadaiTime {

	/**********Member**********/

	/**
	 * 時間
	 */
	private int intHour = 0;
	/**
	 * 分
	 */
	private int intMinute = 0;

	/**********Constructor**********/

	/**
	 * コンストラクタ
	 * @param strTime
	 * @throws KadaiException
	 */
	public KadaiTime(String strTime) throws KadaiException {

		String strHour = strTime.substring(0, 2);// HH�����o��
		String strMinute = strTime.substring(2, 4);// mm�����o��

		try {
			this.intHour = Integer.parseInt(strHour);
			this.intMinute = Integer.parseInt(strMinute);
		} catch (NumberFormatException ex) {
			throw new KadaiException(ErrorCode.ILLEGAL_INPUT_TIME);
		}

	}

	/**********Getter**********/

	public int getIntHour() {
		return intHour;
	}

	public int getIntMinute() {
		return intMinute;
	}

	/**********Setter**********/

	public void setIntHour(int value) {
		this.intHour = value;
	}

	public void setIntMinute(int value) {
		this.intMinute = value;
	}

}
