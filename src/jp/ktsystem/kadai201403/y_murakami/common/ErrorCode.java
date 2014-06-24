package jp.ktsystem.kadai201403.y_murakami.common;

/**
 * �G���[�R�[�h
 * @author y_murakami
 */
public enum ErrorCode {

	//�G���[�R�[�h
	NULL_OR_EMPTY(1), //1�F���͕�����null�A�܂��͋󕶎�
	ILLEGAL_INPUT_TIME(2), //2�F���͕����񂪕s��
	ILLEGAL_START_OR_END_TIME(3), // 	3�F���͕����񂪏o�Ў����A�ގЎ����Ƃ��ĕs��
	END_BEFOR_START(4), // 	4�F�ގЎ������o�Ў����ȑO
	INPUT_FILE_ERROR(5), // 5 : �s���t�@�C��
	NULL_INPUT_FILE_PATH(6), // 6�F���̓t�@�C���̃p�X��null
	NOT_EXIST_INPUT_FILE(7), // 7�F���̓t�@�C�������݂��Ȃ�
	FAILE_READ_INPUT_FILE(8), // 8�F���̓t�@�C���̓ǂݍ��݂Ɏ��s����
	NULL_OUTPUT_FILE_PATH(9), // 9�F�o�̓t�@�C���̃p�X��null
	FAILE_FILE_OUT(10),// 10�F�t�@�C���̏o�͂Ɏ��s����
	IS_CTRL_CODE(11);// 11�F�t�@�C�����ɐ��䕶�������݂���

	private int errorCode;

	private ErrorCode(int errorCode) {
		this.errorCode = errorCode;
	}

	public int getErrorCode() {
		return errorCode;
	}

}
