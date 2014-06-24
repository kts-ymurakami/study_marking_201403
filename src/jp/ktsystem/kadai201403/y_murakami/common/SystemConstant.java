package jp.ktsystem.kadai201403.y_murakami.common;

/**
 * �萔�N���X
 * @author y_murakami
 */
public class SystemConstant {

	/**
	 * �n�Ǝ���
	 */
	public static final int WORK_START_HOUR_MORNING = 9;

	/**
	 * �����x�e�J�n��
	 */
	public static final int START_REST_HOUR_NOON = 12;

	/**
	 * �����x�e���I����
	 */
	public static final int START_HOUR_NOON = 13;

	/**
	 * �[���x�e�J�n��
	 */
	public static final int REST_HOUR_EVE = 18;

	/**
	 * �[�n�Ǝ��@��
	 */
	public static final int START_EVE_MINUTE = 30;

	/**
	 * �����x�e����
	 */
	public static final int REST_TIME_NOON = 60;

	/**
	 * �[���x�e����
	 */
	public static final int REST_TIME_EVE = 30;

	/**
	 * 1���Ԃ̕�
	 */
	public static final int MINUTE_OF_ONE_HOUR = 60;

	/**
	 * �ŏ�����
	 */
	public static final int MINIMUM_TIME = 0;

	/**
	 * ���͕�����`�F�b�N�@���K�\��������@�����A���p�S�p�A�s���������r������p
	 */
	public static final String REGULAR_EXPRESSION_STR = "^[0-9][0-9][0-9][0-9]$";

	/**
	 * �o�Ў��ԃ`�F�b�N�@���K�\��������@HH 00~23(�o�Ђ��[�鈵���ɂȂ邱�Ƃ͂Ȃ�����) mm 00~59(1���Ԃ̕�)
	 */
	public static final String REGULAR_EXPRESSION_STR_START_TIME = "^([0-1][0-9]|[2][0-3])[0-5][0-9]$";

	/**
	 * �ގЎ��ԃ`�F�b�N�@���K�\��������@HH 00~32 mm 00~59(1���Ԃ̕�)
	 */
	public static final String REGULAR_EXPRESSION_STR_END_TIME = "^([0-2][0-9]|[3][0-2])[0-5][0-9]$";

	/**
	 * ���̓t�@�C������A�Ώۃf�[�^�̎��o���p�@���K�\��������@�o�p�ł�����ꂽ���̂��ׂ�
	 */
	public static final String REGULAR_EXPRESSION_STR_INPUT_FILE = "\\{[\\s\\S]*?\\}";

	/**
	 * �Ώۃf�[�^�̎��o���p�@���K�\��������
	 */
	public static final String REGULAR_EXPRESSION_DATA = "[\\s]*\"([a-zA-z]*)\"[\\s]*:[\\s]*\"(.*?)\"";


	/**
	 * ""���̃f�[�^�����o���@���K�\��������
	 */
	public static final String REGULAR_EXPRESSION_DOUBLE_QUOTATION = "\"(.*?)\"";


	/**
	 * ���̓t�@�C���̍��ڐ��@���t�@�o�Ў����@�ގЎ����@
	 */
	public static final int INPUT_FILE_COLUMN_NUM = 3;

	/**
	 * �o�̓t�@�C���̍��ڐ��@���t�@�Ζ����ԁ@���Ζ�����
	 */
	public static final int OUTPUT_FILE_COLUMN_NUM = 3;

	/**
	 * ���̓t�@�C�����t������t�H�[�}�b�g
	 */
	public static final String INPUT_DATA_FORMAT = "yyyyMMdd";

	/**
	 * �L�[������ date
	 */
	public static final String KEY_DATE = "date";

	/**
	 * �L�[������ start
	 */
	public static final String KEY_START = "start";

	/**
	 * �L�[������ end
	 */
	public static final String KEY_END = "end";

	/**
	 * �L�[������ workTime
	 */
	public static final String KEY_WORK_TIME = "workTime";

	/**
	 * �L�[������ total
	 */
	public static final String KEY_TOTAL = "total";

}
