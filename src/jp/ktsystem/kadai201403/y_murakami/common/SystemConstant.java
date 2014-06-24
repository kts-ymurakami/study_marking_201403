package jp.ktsystem.kadai201403.y_murakami.common;

/**
 * �ｿｽ關費ｿｽN�ｿｽ�ｿｽ�ｿｽX
 * @author y_murakami
 */
public class SystemConstant {

	/**
	 * �ｿｽn�ｿｽﾆ趣ｿｽ�ｿｽ�ｿｽ
	 */
	public static final int WORK_START_HOUR_MORNING = 9;

	/**
	 * �ｿｽ�ｿｽ�ｿｽ�ｿｽ�ｿｽx�ｿｽe�ｿｽJ�ｿｽn�ｿｽ�ｿｽ
	 */
	public static final int START_REST_HOUR_NOON = 12;

	/**
	 * �ｿｽ�ｿｽ�ｿｽ�ｿｽ�ｿｽx�ｿｽe�ｿｽ�ｿｽ�ｿｽI�ｿｽ�ｿｽ�ｿｽ�ｿｽ
	 */
	public static final int START_HOUR_NOON = 13;

	/**
	 * �ｿｽ[�ｿｽ�ｿｽ�ｿｽx�ｿｽe�ｿｽJ�ｿｽn�ｿｽ�ｿｽ
	 */
	public static final int REST_HOUR_EVE = 18;

	/**
	 * �ｿｽ[�ｿｽn�ｿｽﾆ趣ｿｽ�ｿｽ@�ｿｽ�ｿｽ
	 */
	public static final int START_EVE_MINUTE = 30;

	/**
	 * �ｿｽ�ｿｽ�ｿｽ�ｿｽ�ｿｽx�ｿｽe�ｿｽ�ｿｽ�ｿｽ�ｿｽ
	 */
	public static final int REST_TIME_NOON = 60;

	/**
	 * �ｿｽ[�ｿｽ�ｿｽ�ｿｽx�ｿｽe�ｿｽ�ｿｽ�ｿｽ�ｿｽ
	 */
	public static final int REST_TIME_EVE = 30;

	/**
	 * 1�ｿｽ�ｿｽ�ｿｽﾔの包ｿｽ
	 */
	public static final int MINUTE_OF_ONE_HOUR = 60;

	/**
	 * �ｿｽﾅ擾ｿｽ�ｿｽ�ｿｽ�ｿｽ�ｿｽ
	 */
	public static final int MINIMUM_TIME = 0;

	/**
	 * �ｿｽ�ｿｽ�ｿｽﾍ包ｿｽ�ｿｽ�ｿｽ�ｿｽ�ｿｽ`�ｿｽF�ｿｽb�ｿｽN�ｿｽ@�ｿｽ�ｿｽ�ｿｽK�ｿｽ\�ｿｽ�ｿｽ�ｿｽ�ｿｽ�ｿｽ�ｿｽ�ｿｽ�ｿｽ@�ｿｽ�ｿｽ�ｿｽ�ｿｽ�ｿｽA�ｿｽ�ｿｽ�ｿｽp�ｿｽS�ｿｽp�ｿｽA�ｿｽs�ｿｽ�ｿｽ�ｿｽ�ｿｽ�ｿｽ�ｿｽ�ｿｽ�ｿｽ�ｿｽr�ｿｽ�ｿｽ�ｿｽ�ｿｽ�ｿｽ�ｿｽp
	 */
	public static final String REGULAR_EXPRESSION_STR = "^[0-9][0-9][0-9][0-9]$";

	/**
	 * �ｿｽo�ｿｽﾐ趣ｿｽ�ｿｽﾔチ�ｿｽF�ｿｽb�ｿｽN�ｿｽ@�ｿｽ�ｿｽ�ｿｽK�ｿｽ\�ｿｽ�ｿｽ�ｿｽ�ｿｽ�ｿｽ�ｿｽ�ｿｽ�ｿｽ@HH 00~23(�ｿｽo�ｿｽﾐゑｿｽ�ｿｽ[�ｿｽ驤ｵ�ｿｽ�ｿｽ�ｿｽﾉなるこ�ｿｽﾆはなゑｿｽ�ｿｽ�ｿｽ�ｿｽ�ｿｽ) mm 00~59(1�ｿｽ�ｿｽ�ｿｽﾔの包ｿｽ)
	 */
	public static final String REGULAR_EXPRESSION_STR_START_TIME = "^([0-1][0-9]|[2][0-3])[0-5][0-9]$";

	/**
	 * �ｿｽﾞ社趣ｿｽ�ｿｽﾔチ�ｿｽF�ｿｽb�ｿｽN�ｿｽ@�ｿｽ�ｿｽ�ｿｽK�ｿｽ\�ｿｽ�ｿｽ�ｿｽ�ｿｽ�ｿｽ�ｿｽ�ｿｽ�ｿｽ@HH 00~32 mm 00~59(1�ｿｽ�ｿｽ�ｿｽﾔの包ｿｽ)
	 */
	public static final String REGULAR_EXPRESSION_STR_END_TIME = "^([0-2][0-9]|[3][0-2])[0-5][0-9]$";

	/**
	 * �ｿｽ�ｿｽ�ｿｽﾍフ�ｿｽ@�ｿｽC�ｿｽ�ｿｽ�ｿｽ�ｿｽ�ｿｽ�ｿｽA�ｿｽﾎ象デ�ｿｽ[�ｿｽ^�ｿｽﾌ趣ｿｽ�ｿｽo�ｿｽ�ｿｽ�ｿｽp�ｿｽ@�ｿｽ�ｿｽ�ｿｽK�ｿｽ\�ｿｽ�ｿｽ�ｿｽ�ｿｽ�ｿｽ�ｿｽ�ｿｽ�ｿｽ@�ｿｽo�ｿｽp�ｿｽﾅゑｿｽ�ｿｽ�ｿｽ�ｿｽ�ｿｽ黷ｽ�ｿｽ�ｿｽ�ｿｽﾌゑｿｽ�ｿｽﾗゑｿｽ
	 */
	public static final String REGULAR_EXPRESSION_STR_INPUT_FILE = "\\{[\\s\\S]*?\\}";

	/**
	 * �ｿｽﾎ象デ�ｿｽ[�ｿｽ^�ｿｽﾌ趣ｿｽ�ｿｽo�ｿｽ�ｿｽ�ｿｽp�ｿｽ@�ｿｽ�ｿｽ�ｿｽK�ｿｽ\�ｿｽ�ｿｽ�ｿｽ�ｿｽ�ｿｽ�ｿｽ�ｿｽ�ｿｽ
	 */
	public static final String REGULAR_EXPRESSION_DATA = "[\\s]*\"([a-zA-z]*)\"[\\s]*:[\\s]*\"(.*?)\"";


	/**
	 * ""�ｿｽ�ｿｽ�ｿｽﾌデ�ｿｽ[�ｿｽ^�ｿｽ�ｿｽ�ｿｽ�ｿｽ�ｿｽo�ｿｽ�ｿｽ�ｿｽ@�ｿｽ�ｿｽ�ｿｽK�ｿｽ\�ｿｽ�ｿｽ�ｿｽ�ｿｽ�ｿｽ�ｿｽ�ｿｽ�ｿｽ
	 */
	public static final String REGULAR_EXPRESSION_DOUBLE_QUOTATION = "\"(.*?)\"";


	/**
	 * �ｿｽ�ｿｽ�ｿｽﾍフ�ｿｽ@�ｿｽC�ｿｽ�ｿｽ�ｿｽﾌ搾ｿｽ�ｿｽﾚ撰ｿｽ�ｿｽ@�ｿｽ�ｿｽ�ｿｽt�ｿｽ@�ｿｽo�ｿｽﾐ趣ｿｽ�ｿｽ�ｿｽ�ｿｽ@�ｿｽﾞ社趣ｿｽ�ｿｽ�ｿｽ�ｿｽ@
	 */
	public static final int INPUT_FILE_COLUMN_NUM = 3;

	/**
	 * �ｿｽo�ｿｽﾍフ�ｿｽ@�ｿｽC�ｿｽ�ｿｽ�ｿｽﾌ搾ｿｽ�ｿｽﾚ撰ｿｽ�ｿｽ@�ｿｽ�ｿｽ�ｿｽt�ｿｽ@�ｿｽﾎ厄ｿｽ�ｿｽ�ｿｽ�ｿｽﾔ　�ｿｽ�ｿｽ�ｿｽﾎ厄ｿｽ�ｿｽ�ｿｽ�ｿｽ�ｿｽ
	 */
	public static final int OUTPUT_FILE_COLUMN_NUM = 3;

	/**
	 * �ｿｽ�ｿｽ�ｿｽﾍフ�ｿｽ@�ｿｽC�ｿｽ�ｿｽ�ｿｽ�ｿｽ�ｿｽt�ｿｽ�ｿｽ�ｿｽ�ｿｽ�ｿｽ�ｿｽt�ｿｽH�ｿｽ[�ｿｽ}�ｿｽb�ｿｽg
	 */
	public static final String INPUT_DATA_FORMAT = "yyyyMMdd";

	/**
	 * �ｿｽL�ｿｽ[�ｿｽ�ｿｽ�ｿｽ�ｿｽ�ｿｽ�ｿｽ date
	 */
	public static final String KEY_DATE = "date";

	/**
	 * �ｿｽL�ｿｽ[�ｿｽ�ｿｽ�ｿｽ�ｿｽ�ｿｽ�ｿｽ start
	 */
	public static final String KEY_START = "start";

	/**
	 * �ｿｽL�ｿｽ[�ｿｽ�ｿｽ�ｿｽ�ｿｽ�ｿｽ�ｿｽ end
	 */
	public static final String KEY_END = "end";

	/**
	 * �ｿｽL�ｿｽ[�ｿｽ�ｿｽ�ｿｽ�ｿｽ�ｿｽ�ｿｽ workTime
	 */
	public static final String KEY_WORK_TIME = "workTime";

	/**
	 * �ｿｽL�ｿｽ[�ｿｽ�ｿｽ�ｿｽ�ｿｽ�ｿｽ�ｿｽ total
	 */
	public static final String KEY_TOTAL = "total";

	/**
	 * ファイルパスを作成するためのフォーマット
	 */
	public static final String CREATE_FILE_PATH_FORMAT = "%s/%s.txt";

}
