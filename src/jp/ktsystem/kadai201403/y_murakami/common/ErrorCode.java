package jp.ktsystem.kadai201403.y_murakami.common;

/**
 * �G���[�R�[�h
 * @author y_murakami
 */
public enum ErrorCode {

	NULL_OR_EMPTY(1), //　入力ファイルがnull
	ILLEGAL_INPUT_TIME(2), //　不正な入力文字列
	ILLEGAL_START_OR_END_TIME(3), // 勤務時間文字列不正
	END_BEFOR_START(4), // 	退社のほうが前
	INPUT_FILE_ERROR(5), // 入力ファイルが不正
	NULL_INPUT_FILE_PATH(6), // 入力パスがnull
	NOT_EXIST_INPUT_FILE(7), // 入力ファイルが存在しない
	FAILE_READ_INPUT_FILE(8), // 読み込み失敗
	NULL_OUTPUT_FILE_PATH(9), // 出力パスがnull
	FAILE_FILE_OUT(10),// 出力失敗
	IS_CTRL_CODE(11);// 制御コードを含む

	private int errorCode;

	private ErrorCode(int errorCode) {
		this.errorCode = errorCode;
	}

	public int getErrorCode() {
		return errorCode;
	}

}
