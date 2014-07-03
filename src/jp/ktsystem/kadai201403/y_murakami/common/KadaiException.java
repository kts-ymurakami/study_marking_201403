package jp.ktsystem.kadai201403.y_murakami.common;

/**
 * 課題Exception
 * @author y_murakami
 */
public class KadaiException extends Exception {

	private static final long serialVersionUID = 1L;

	/**
	 * errorCode
	 */
	private ErrorCode errorCode;

	/**
	 * コンストラクタ
	 * @param errorCode　エラーコード
	 */
	public KadaiException(ErrorCode errorCode) {
		this.errorCode = errorCode;
	}

	/**
	 * errorCodeを返す
	 * @return errorCode
	 */
	public int getErrorCode() {
		return this.errorCode.getErrorCode();
	}

}
