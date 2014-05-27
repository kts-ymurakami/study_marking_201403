package jp.ktsystem.kadai201403.y_murakami.Common;

/**
 * 課題用Exception
 * @author y_murakami
 */
public class KadaiException extends Exception {

	/**
	 * シリアライズVerID
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * errorCode
	 */
	private ErrorCode errorCode;

	/**
	 * コンストラクタ　エラーコードをメンバに設定する
	 * @param errorCode
	 */
	public KadaiException(ErrorCode errorCode) {
		this.errorCode = errorCode;
	}

	/**
	 * errorCodeを返す
	 * @return errorCode
	 */
	public int getErrorCode() {
		return this.errorCode.gerErrorCode();
	}

}
