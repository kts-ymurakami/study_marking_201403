package jp.ktsystem.kadai201403.y_murakami;

import jp.ktsystem.kadai201403.y_murakami.common.ErrorCode;
import jp.ktsystem.kadai201403.y_murakami.common.KadaiException;
import jp.ktsystem.kadai201403.y_murakami.util.ErrorUtil;

/**
 * 課題クラス
 *
 * @author y_murakami
 */
public class Kadai {

	/**
	 * Lv1 勤怠時間の記述されたファイルを読み取り、勤務時間を出力する
	 *
	 * @param anInputPath
	 *            入力ファイルパス
	 * @param anOutputPath
	 *            出力ファイルパス
	 * @throws KadaiException
	 */
	public static void parseWorkTimeData(String anInputPath, String anOutputPath)
			throws KadaiException {

		// ファイルコントロールオブジェクトを生成
		WorkTimeFileIOControl workTimeFile = new WorkTimeFileIOControl(
				anInputPath, anOutputPath);

		// ファイル読み込みと出力
		try {
			workTimeFile.readInputFile();
		} catch (KadaiException ex) {
			int eCode = ex.getErrorCode();
			// エラーコードの出力の必要がない場合
			if (ErrorCode.NULL_INPUT_FILE_PATH.getErrorCode() == eCode
					|| ErrorCode.NOT_EXIST_INPUT_FILE.getErrorCode() == eCode
					|| ErrorCode.FAILE_READ_INPUT_FILE.getErrorCode() == eCode) {
				throw ex;
			}
			// ファイル書き出し
			workTimeFile.writeOutputFile(eCode);
			return;// 正常終了
		}

		// ファイル書き出し
		workTimeFile.writeOutputFile(-1);

	}

	/**
	 * Lv2　月ごとの勤怠データを読み込んで出力する
	 * @param anInputPath
	 *            入力ファイルパス
	 * @param anOutputPath
	 *            出力ディレクトリパス
	 * @throws KadaiException
	 */
	public static void parseWorkTimeDataLv2(String anInputPath,
			String anOutputPath) throws KadaiException {

		// コントロールオブジェクトの生成
		WorkTimeFileIOControlLv2 workTimeFile = new WorkTimeFileIOControlLv2(
				anInputPath, anOutputPath);

		boolean controlErrFlag = false;
		// 処理
		try {
			// ファイル読み込み
			workTimeFile.readInputFileLv2();
		} catch (KadaiException ex) {
			// エラー出力の必要がない場合はスロー
			if (ErrorCode.NULL_INPUT_FILE_PATH.getErrorCode() == ex.getErrorCode()
					|| ErrorCode.NOT_EXIST_INPUT_FILE.getErrorCode() == ex.getErrorCode()
					|| ErrorCode.FAILE_READ_INPUT_FILE.getErrorCode() == ex.getErrorCode()) {
				throw ex;
			}
			// 制御文字の場合は設定
			if(ErrorCode.IS_CTRL_CODE.getErrorCode() == ex.getErrorCode()){
				controlErrFlag = true;
			}
		}
		// 書き出し
		workTimeFile.writeOutPutFileLv2(anOutputPath);

		if(controlErrFlag){
			throw new KadaiException(ErrorCode.IS_CTRL_CODE);
		}
	}

	/**
	 * 出社時間と退社時間から退社時間を勤務時間を返す
	 *
	 * @param aStartTime
	 *            出社時間　HHmm
	 * @param anEndTime
	 *            退社時間 HHmm
	 * @return 勤務時間
	 * @throws KadaiException
	 */
	public static String calcWorkTime(String aStartTime, String anEndTime)
			throws KadaiException {

		// エラーチェック
		ErrorUtil.checkStrTime(aStartTime, anEndTime);

		KadaiTime startTime = new KadaiTime(aStartTime);
		KadaiTime endTime = new KadaiTime(anEndTime);

		// 計算用オブジェクトを生成
		WorkTimeControl workTimeCtrl = new WorkTimeControl(startTime, endTime);

		// 結果を返す
		return workTimeCtrl.returnWorkTime();

	}

}
