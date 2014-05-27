package jp.ktsystem.kadai201403.y_murakami;

import jp.ktsystem.kadai201403.y_murakami.Common.ErrorCode;
import jp.ktsystem.kadai201403.y_murakami.Common.KadaiException;
import jp.ktsystem.kadai201403.y_murakami.Util.ErrorUtil;

/**
 * 課題クラス
 *
 * @author y_murakami
 */
public class Kadai {

	/**
	 * Lv1 日付、開始時間、終了時間がJson形式で記述されたファイルを読み込み、 勤務時間をファイルに出力する
	 *
	 * @param anInputPath
	 *            入力ファイルパス
	 * @param anOutputPath
	 *            出力ファイルパス
	 * @throws KadaiException
	 */
	public static void parseWorkTimeData(String anInputPath, String anOutputPath)
			throws KadaiException {

		// 勤務時間ファイル読み書き制御オブジェクトの生成
		WorkTimeFileIOControl workTimeFile = new WorkTimeFileIOControl(
				anInputPath, anOutputPath);

		// ファイル読み込み
		try {
			workTimeFile.readInputFile();
		} catch (KadaiException ex) {
			int eCode = ex.getErrorCode();
			// 出力ファイルの作成が必要ないエラーコードの場合
			if (ErrorCode.NULL_INPUT_FILE_PATH.gerErrorCode() == eCode
					|| ErrorCode.NOT_EXIST_INPUT_FILE.gerErrorCode() == eCode
					|| ErrorCode.FAILE_READ_INPUT_FILE.gerErrorCode() == eCode) {
				throw ex;
			}
			// 読み込めている分だけ書き出し(読み込み中のエラーの場合はエラーコード出力)
			workTimeFile.writeOutputFile(eCode);
			return;// 正常系として終了
		}

		// 入力ファイル読み込みが全て上手くいった場合書き出し
		workTimeFile.writeOutputFile(-1);

	}

	/**
	 * Lv2 月、日付、開始時間、終了時間がJson形式で記述されたファイルを読み込み、 勤務時間をファイルに出力する
	 * TODO
	 * @param anInputPath
	 *            入力ファイルパス
	 * @param anOutputPath
	 *            出力ディレクトリパス
	 * @throws KadaiException
	 */
	public static void parseWorkTimeDataLv2(String anInputPath,
			String anOutputPath) throws KadaiException {

		// 勤務時間ファイル読み書き制御オブジェクトの生成
		WorkTimeFileIOControl workTimeFile = new WorkTimeFileIOControl(
				anInputPath, anOutputPath);

		// ファイル読み込み
		try {
			workTimeFile.readInputFileLv2();
		} catch (KadaiException ex) {
			int eCode = ex.getErrorCode();
			// 出力ファイルの作成が必要ないエラーコードの場合
			if (ErrorCode.NULL_INPUT_FILE_PATH.gerErrorCode() == eCode
					|| ErrorCode.NOT_EXIST_INPUT_FILE.gerErrorCode() == eCode
					|| ErrorCode.FAILE_READ_INPUT_FILE.gerErrorCode() == eCode) {
				throw ex;
			}
			// 読み込めている分だけ書き出し(読み込み中のエラーの場合はエラーコード出力)
			workTimeFile.writeOutputFile(eCode);
			return;// 正常系として終了
		}

		// 入力ファイル読み込みが全て上手くいった場合書き出し
		workTimeFile.writeOutputFile(-1);

	}

	/**
	 * 出社時間と退社時間から、勤務時間を返すメソッド
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

		// 入力エラーチェック
		ErrorUtil.checkStrTime(aStartTime, anEndTime);

		// 時間クラスの生成
		KadaiTime startTime = new KadaiTime(aStartTime);
		KadaiTime endTime = new KadaiTime(anEndTime);

		// 勤務時間制御クラスの生成
		WorkTimeControl workTimeCtrl = new WorkTimeControl(startTime, endTime);

		// 勤務時間を取得
		return workTimeCtrl.returnWorkTime();

	}

}
