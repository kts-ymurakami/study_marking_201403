package jp.ktsystem.kadai201403.y_murakami.common;

/**
 * �ۑ�pException
 * @author y_murakami
 */
public class KadaiException extends Exception {

	/**
	 * �V���A���C�YVerID
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * errorCode
	 */
	private ErrorCode errorCode;

	/**
	 * �R���X�g���N�^�@�G���[�R�[�h�������o�ɐݒ肷��
	 * @param errorCode
	 */
	public KadaiException(ErrorCode errorCode) {
		this.errorCode = errorCode;
	}

	/**
	 * errorCode��Ԃ�
	 * @return errorCode
	 */
	public int getErrorCode() {
		return this.errorCode.getErrorCode();
	}

}
