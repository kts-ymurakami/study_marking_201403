package jp.ktsystem.kadai201403.y_murakami.Common;

/**
 * エラーコード
 * @author y_murakami
 */
public enum ErrorCode {

	//エラーコード
	NULL_OR_EMPTY(1), //1：入力文字列がnull、または空文字
	ILLEGAL_INPUT_TIME(2), //2：入力文字列が不正
	ILLEGAL_START_OR_END_TIME(3), // 	3：入力文字列が出社時刻、退社時刻として不正
	END_BEFOR_START(4), // 	4：退社時刻が出社時刻以前
	INPUT_FILE_ERROR(5), // 5 : 不正ファイル
	NULL_INPUT_FILE_PATH(6), // 6：入力ファイルのパスがnull
	NOT_EXIST_INPUT_FILE(7), // 7：入力ファイルが存在しない
	FAILE_READ_INPUT_FILE(8), // 8：入力ファイルの読み込みに失敗した
	NULL_OUTPUT_FILE_PATH(9), // 9：出力ファイルのパスがnull
	FAILE_FILE_OUT(10),// 10：ファイルの出力に失敗した
	IS_CTRL_CODE(11);// 11：ファイル内に制御文字が存在する

	private int errorCode;

	private ErrorCode(int errorCode) {
		this.errorCode = errorCode;
	}

	public int gerErrorCode() {
		return errorCode;
	}

}
