package jp.ktsystem.kadai201403.y_murakami;

import jp.ktsystem.kadai201403.y_murakami.common.ErrorCode;
import jp.ktsystem.kadai201403.y_murakami.common.KadaiException;
import jp.ktsystem.kadai201403.y_murakami.util.ErrorUtil;

/**
 * �ۑ�N���X
 *
 * @author y_murakami
 */
public class Kadai {

	/**
	 * Lv1 ���t�A�J�n���ԁA�I�����Ԃ�Json�`���ŋL�q���ꂽ�t�@�C����ǂݍ��݁A �Ζ����Ԃ��t�@�C���ɏo�͂���
	 *
	 * @param anInputPath
	 *            ���̓t�@�C���p�X
	 * @param anOutputPath
	 *            �o�̓t�@�C���p�X
	 * @throws KadaiException
	 */
	public static void parseWorkTimeData(String anInputPath, String anOutputPath)
			throws KadaiException {

		// �Ζ����ԃt�@�C���ǂݏ�������I�u�W�F�N�g�̐���
		WorkTimeFileIOControl workTimeFile = new WorkTimeFileIOControl(
				anInputPath, anOutputPath);

		// �t�@�C���ǂݍ���
		try {
			workTimeFile.readInputFile();
		} catch (KadaiException ex) {
			int eCode = ex.getErrorCode();
			// �o�̓t�@�C���̍쐬���K�v�Ȃ��G���[�R�[�h�̏ꍇ
			if (ErrorCode.NULL_INPUT_FILE_PATH.getErrorCode() == eCode
					|| ErrorCode.NOT_EXIST_INPUT_FILE.getErrorCode() == eCode
					|| ErrorCode.FAILE_READ_INPUT_FILE.getErrorCode() == eCode) {
				throw ex;
			}
			// �ǂݍ��߂Ă��镪���������o��(�ǂݍ��ݒ��̃G���[�̏ꍇ�̓G���[�R�[�h�o��)
			workTimeFile.writeOutputFile(eCode);
			return;// ����n�Ƃ��ďI��
		}

		// ���̓t�@�C���ǂݍ��݂��S�ď�肭�������ꍇ�����o��
		workTimeFile.writeOutputFile(-1);

	}

	/**
	 * Lv2 ���A���t�A�J�n���ԁA�I�����Ԃ�Json�`���ŋL�q���ꂽ�t�@�C����ǂݍ��݁A �Ζ����Ԃ��t�@�C���ɏo�͂���
	 * @param anInputPath
	 *            ���̓t�@�C���p�X
	 * @param anOutputPath
	 *            �o�̓f�B���N�g���p�X
	 * @throws KadaiException
	 */
	public static void parseWorkTimeDataLv2(String anInputPath,
			String anOutputPath) throws KadaiException {

		// �Ζ����ԃt�@�C���ǂݏ�������I�u�W�F�N�g�̐���
		WorkTimeFileIOControl workTimeFile = new WorkTimeFileIOControl(
				anInputPath, anOutputPath);

		boolean controlErrFlag = false;
		// �t�@�C���ǂݍ���
		try {
			// �t�@�C���̓ǂݍ���
			workTimeFile.readInputFileLv2();
		} catch (KadaiException ex) {
			// �o�̓t�@�C���̍쐬���K�v�Ȃ��G���[�R�[�h�̏ꍇ
			if (ErrorCode.NULL_INPUT_FILE_PATH.getErrorCode() == ex.getErrorCode()
					|| ErrorCode.NOT_EXIST_INPUT_FILE.getErrorCode() == ex.getErrorCode()
					|| ErrorCode.FAILE_READ_INPUT_FILE.getErrorCode() == ex.getErrorCode()) {
				throw ex;
			}
			// ���䕶�����������Ă����ꍇ
			if(ErrorCode.IS_CTRL_CODE.getErrorCode() == ex.getErrorCode()){
				controlErrFlag = true;
			}
		}
		// �����o��
		workTimeFile.writeOutPutFileLv2(anOutputPath);

		if(controlErrFlag){
			throw new KadaiException(ErrorCode.IS_CTRL_CODE);
		}
	}

	/**
	 * �o�Ў��ԂƑގЎ��Ԃ���A�Ζ����Ԃ�Ԃ����\�b�h
	 *
	 * @param aStartTime
	 *            �o�Ў��ԁ@HHmm
	 * @param anEndTime
	 *            �ގЎ��� HHmm
	 * @return �Ζ�����
	 * @throws KadaiException
	 */
	public static String calcWorkTime(String aStartTime, String anEndTime)
			throws KadaiException {

		// ���̓G���[�`�F�b�N
		ErrorUtil.checkStrTime(aStartTime, anEndTime);

		// ���ԃN���X�̐���
		KadaiTime startTime = new KadaiTime(aStartTime);
		KadaiTime endTime = new KadaiTime(anEndTime);

		// �Ζ����Ԑ���N���X�̐���
		WorkTimeControl workTimeCtrl = new WorkTimeControl(startTime, endTime);

		// �Ζ����Ԃ��擾
		return workTimeCtrl.returnWorkTime();

	}

}
