package jp.ktsystem.kadai201403.y_murakami;

import jp.ktsystem.kadai201403.y_murakami.common.ErrorCode;
import jp.ktsystem.kadai201403.y_murakami.common.KadaiException;

/**
 * ���Ԃ������N���X
 * @author y_murakami
 */
public class KadaiTime {

	/**********Member**********/

	/**
	 * ���ԁ@���@�����^
	 */
	private int intHour = 0;
	/**
	 * ���ԁ@���@������
	 */
	private int intMinute = 0;

	/**********Constructor**********/

	/**
	 * �R���X�g���N�^�@HHmm�̕�������󂯎���ă����o�ɒl��ݒ肷��
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

	/**
	 * ���Ԑ����^�擾
	 */
	public int getIntHour() {
		return intHour;
	}

	/**
	 * �������^�擾
	 * @return
	 */
	public int getIntMinute() {
		return intMinute;
	}

	/**********Setter**********/

	/**
	 * ���Ԑ����^�ݒ�
	 */
	public void setIntHour(int value) {
		this.intHour = value;
	}

	/**
	 * �������^�ݒ�
	 */
	public void setIntMinute(int value) {
		this.intMinute = value;
	}

}
