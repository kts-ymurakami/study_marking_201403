package jp.ktsystem.kadai201403.y_murakami;

import jp.ktsystem.kadai201403.y_murakami.common.ErrorCode;
import jp.ktsystem.kadai201403.y_murakami.common.KadaiException;
import jp.ktsystem.kadai201403.y_murakami.util.ErrorUtil;

/**
 * �ｿｽﾛ托ｿｽN�ｿｽ�ｿｽ�ｿｽX
 *
 * @author y_murakami
 */
public class Kadai {

	/**
	 * Lv1 �ｿｽ�ｿｽ�ｿｽt�ｿｽA�ｿｽJ�ｿｽn�ｿｽ�ｿｽ�ｿｽﾔ、�ｿｽI�ｿｽ�ｿｽ�ｿｽ�ｿｽ�ｿｽﾔゑｿｽJson�ｿｽ`�ｿｽ�ｿｽ�ｿｽﾅ記�ｿｽq�ｿｽ�ｿｽ�ｿｽ黷ｽ�ｿｽt�ｿｽ@�ｿｽC�ｿｽ�ｿｽ�ｿｽ�ｿｽﾇみ搾ｿｽ�ｿｽﾝ、 �ｿｽﾎ厄ｿｽ�ｿｽ�ｿｽ�ｿｽﾔゑｿｽ�ｿｽt�ｿｽ@�ｿｽC�ｿｽ�ｿｽ�ｿｽﾉ出�ｿｽﾍゑｿｽ�ｿｽ�ｿｽ
	 *
	 * @param anInputPath
	 *            �ｿｽ�ｿｽ�ｿｽﾍフ�ｿｽ@�ｿｽC�ｿｽ�ｿｽ�ｿｽp�ｿｽX
	 * @param anOutputPath
	 *            �ｿｽo�ｿｽﾍフ�ｿｽ@�ｿｽC�ｿｽ�ｿｽ�ｿｽp�ｿｽX
	 * @throws KadaiException
	 */
	public static void parseWorkTimeData(String anInputPath, String anOutputPath)
			throws KadaiException {

		// �ｿｽﾎ厄ｿｽ�ｿｽ�ｿｽ�ｿｽﾔフ�ｿｽ@�ｿｽC�ｿｽ�ｿｽ�ｿｽﾇみ擾ｿｽ�ｿｽ�ｿｽ�ｿｽ�ｿｽ�ｿｽ�ｿｽI�ｿｽu�ｿｽW�ｿｽF�ｿｽN�ｿｽg�ｿｽﾌ撰ｿｽ�ｿｽ�ｿｽ
		WorkTimeFileIOControl workTimeFile = new WorkTimeFileIOControl(
				anInputPath, anOutputPath);

		// �ｿｽt�ｿｽ@�ｿｽC�ｿｽ�ｿｽ�ｿｽﾇみ搾ｿｽ�ｿｽ�ｿｽ
		try {
			workTimeFile.readInputFile();
		} catch (KadaiException ex) {
			int eCode = ex.getErrorCode();
			// �ｿｽo�ｿｽﾍフ�ｿｽ@�ｿｽC�ｿｽ�ｿｽ�ｿｽﾌ作成�ｿｽ�ｿｽ�ｿｽK�ｿｽv�ｿｽﾈゑｿｽ�ｿｽG�ｿｽ�ｿｽ�ｿｽ[�ｿｽR�ｿｽ[�ｿｽh�ｿｽﾌ場合
			if (ErrorCode.NULL_INPUT_FILE_PATH.getErrorCode() == eCode
					|| ErrorCode.NOT_EXIST_INPUT_FILE.getErrorCode() == eCode
					|| ErrorCode.FAILE_READ_INPUT_FILE.getErrorCode() == eCode) {
				throw ex;
			}
			// �ｿｽﾇみ搾ｿｽ�ｿｽﾟてゑｿｽ�ｿｽ髟ｪ�ｿｽ�ｿｽ�ｿｽ�ｿｽ�ｿｽ�ｿｽ�ｿｽ�ｿｽ�ｿｽo�ｿｽ�ｿｽ(�ｿｽﾇみ搾ｿｽ�ｿｽﾝ抵ｿｽ�ｿｽﾌエ�ｿｽ�ｿｽ�ｿｽ[�ｿｽﾌ場合�ｿｽﾍエ�ｿｽ�ｿｽ�ｿｽ[�ｿｽR�ｿｽ[�ｿｽh�ｿｽo�ｿｽ�ｿｽ)
			workTimeFile.writeOutputFile(eCode);
			return;// �ｿｽ�ｿｽ�ｿｽ�ｿｽn�ｿｽﾆゑｿｽ�ｿｽﾄ終�ｿｽ�ｿｽ
		}

		// �ｿｽ�ｿｽ�ｿｽﾍフ�ｿｽ@�ｿｽC�ｿｽ�ｿｽ�ｿｽﾇみ搾ｿｽ�ｿｽﾝゑｿｽ�ｿｽS�ｿｽﾄ擾ｿｽ閧ｭ�ｿｽ�ｿｽ�ｿｽ�ｿｽ�ｿｽ�ｿｽ�ｿｽ鼾�ｿｽ�ｿｽ�ｿｽ�ｿｽ�ｿｽo�ｿｽ�ｿｽ
		workTimeFile.writeOutputFile(-1);

	}

	/**
	 * Lv2 �ｿｽ�ｿｽ�ｿｽA�ｿｽ�ｿｽ�ｿｽt�ｿｽA�ｿｽJ�ｿｽn�ｿｽ�ｿｽ�ｿｽﾔ、�ｿｽI�ｿｽ�ｿｽ�ｿｽ�ｿｽ�ｿｽﾔゑｿｽJson�ｿｽ`�ｿｽ�ｿｽ�ｿｽﾅ記�ｿｽq�ｿｽ�ｿｽ�ｿｽ黷ｽ�ｿｽt�ｿｽ@�ｿｽC�ｿｽ�ｿｽ�ｿｽ�ｿｽﾇみ搾ｿｽ�ｿｽﾝ、 �ｿｽﾎ厄ｿｽ�ｿｽ�ｿｽ�ｿｽﾔゑｿｽ�ｿｽt�ｿｽ@�ｿｽC�ｿｽ�ｿｽ�ｿｽﾉ出�ｿｽﾍゑｿｽ�ｿｽ�ｿｽ
	 * @param anInputPath
	 *            �ｿｽ�ｿｽ�ｿｽﾍフ�ｿｽ@�ｿｽC�ｿｽ�ｿｽ�ｿｽp�ｿｽX
	 * @param anOutputPath
	 *            �ｿｽo�ｿｽﾍデ�ｿｽB�ｿｽ�ｿｽ�ｿｽN�ｿｽg�ｿｽ�ｿｽ�ｿｽp�ｿｽX
	 * @throws KadaiException
	 */
	public static void parseWorkTimeDataLv2(String anInputPath,
			String anOutputPath) throws KadaiException {

		// �ｿｽﾎ厄ｿｽ�ｿｽ�ｿｽ�ｿｽﾔフ�ｿｽ@�ｿｽC�ｿｽ�ｿｽ�ｿｽﾇみ擾ｿｽ�ｿｽ�ｿｽ�ｿｽ�ｿｽ�ｿｽ�ｿｽI�ｿｽu�ｿｽW�ｿｽF�ｿｽN�ｿｽg�ｿｽﾌ撰ｿｽ�ｿｽ�ｿｽ
		WorkTimeFileIOControlLv2 workTimeFile = new WorkTimeFileIOControlLv2(
				anInputPath, anOutputPath);

		boolean controlErrFlag = false;
		// �ｿｽt�ｿｽ@�ｿｽC�ｿｽ�ｿｽ�ｿｽﾇみ搾ｿｽ�ｿｽ�ｿｽ
		try {
			// �ｿｽt�ｿｽ@�ｿｽC�ｿｽ�ｿｽ�ｿｽﾌ読み搾ｿｽ�ｿｽ�ｿｽ
			workTimeFile.readInputFileLv2();
		} catch (KadaiException ex) {
			// �ｿｽo�ｿｽﾍフ�ｿｽ@�ｿｽC�ｿｽ�ｿｽ�ｿｽﾌ作成�ｿｽ�ｿｽ�ｿｽK�ｿｽv�ｿｽﾈゑｿｽ�ｿｽG�ｿｽ�ｿｽ�ｿｽ[�ｿｽR�ｿｽ[�ｿｽh�ｿｽﾌ場合
			if (ErrorCode.NULL_INPUT_FILE_PATH.getErrorCode() == ex.getErrorCode()
					|| ErrorCode.NOT_EXIST_INPUT_FILE.getErrorCode() == ex.getErrorCode()
					|| ErrorCode.FAILE_READ_INPUT_FILE.getErrorCode() == ex.getErrorCode()) {
				throw ex;
			}
			// �ｿｽ�ｿｽ�ｿｽ苺ｶ�ｿｽ�ｿｽ�ｿｽ�ｿｽ�ｿｽ�ｿｽ�ｿｽ�ｿｽ�ｿｽ�ｿｽ�ｿｽﾄゑｿｽ�ｿｽ�ｿｽ�ｿｽ鼾�
			if(ErrorCode.IS_CTRL_CODE.getErrorCode() == ex.getErrorCode()){
				controlErrFlag = true;
			}
		}
		// �ｿｽ�ｿｽ�ｿｽ�ｿｽ�ｿｽo�ｿｽ�ｿｽ
		workTimeFile.writeOutPutFileLv2(anOutputPath);

		if(controlErrFlag){
			throw new KadaiException(ErrorCode.IS_CTRL_CODE);
		}
	}

	/**
	 * �ｿｽo�ｿｽﾐ趣ｿｽ�ｿｽﾔと退社趣ｿｽ�ｿｽﾔゑｿｽ�ｿｽ�ｿｽA�ｿｽﾎ厄ｿｽ�ｿｽ�ｿｽ�ｿｽﾔゑｿｽﾔゑｿｽ�ｿｽ�ｿｽ�ｿｽ\�ｿｽb�ｿｽh
	 *
	 * @param aStartTime
	 *            �ｿｽo�ｿｽﾐ趣ｿｽ�ｿｽﾔ　HHmm
	 * @param anEndTime
	 *            �ｿｽﾞ社趣ｿｽ�ｿｽ�ｿｽ HHmm
	 * @return �ｿｽﾎ厄ｿｽ�ｿｽ�ｿｽ�ｿｽ�ｿｽ
	 * @throws KadaiException
	 */
	public static String calcWorkTime(String aStartTime, String anEndTime)
			throws KadaiException {

		// �ｿｽ�ｿｽ�ｿｽﾍエ�ｿｽ�ｿｽ�ｿｽ[�ｿｽ`�ｿｽF�ｿｽb�ｿｽN
		ErrorUtil.checkStrTime(aStartTime, anEndTime);

		// �ｿｽ�ｿｽ�ｿｽﾔク�ｿｽ�ｿｽ�ｿｽX�ｿｽﾌ撰ｿｽ�ｿｽ�ｿｽ
		KadaiTime startTime = new KadaiTime(aStartTime);
		KadaiTime endTime = new KadaiTime(anEndTime);

		// �ｿｽﾎ厄ｿｽ�ｿｽ�ｿｽ�ｿｽﾔ撰ｿｽ�ｿｽ�ｿｽN�ｿｽ�ｿｽ�ｿｽX�ｿｽﾌ撰ｿｽ�ｿｽ�ｿｽ
		WorkTimeControl workTimeCtrl = new WorkTimeControl(startTime, endTime);

		// �ｿｽﾎ厄ｿｽ�ｿｽ�ｿｽ�ｿｽﾔゑｿｽ�ｿｽ謫ｾ
		return workTimeCtrl.returnWorkTime();

	}

}
