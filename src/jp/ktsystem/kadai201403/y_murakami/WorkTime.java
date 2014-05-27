package jp.ktsystem.kadai201403.y_murakami;

import jp.ktsystem.kadai201403.y_murakami.Common.SystemConstant;
import jp.ktsystem.kadai201403.y_murakami.Util.CommonUtil;

/**
 * �o�̓f�[�^�i�[���f��
 *
 * @author y_murakami
 *
 */
public class WorkTime {

	/**
	 * �Ζ���
	 */
	private String date;
	/**
	 * workTime �Ζ�����
	 */
	private String workTime;
	/**
	 * ���Ζ�����
	 */
	private String total;

	/**
	 * �R���X�g���N�^
	 *
	 * @param date
	 *            �@�Ζ���
	 * @param workTime
	 *            �@�Ζ�����
	 * @param total
	 *            �@���Ζ�����
	 */
	public WorkTime(String date, String workTime, String total) {

		this.date = date;
		this.workTime = workTime;
		this.total = total;

	}

	/**
	 * �v���p�e�B����o�͗p��Json�`���̕������Ԃ�
	 *
	 * @return
	 */
	public String createJsonStr() {

		StringBuilder sb = new StringBuilder("{");
		// ���t
		sb.append(
				CommonUtil.createJsonStr(SystemConstant.KEY_DATE, this.date,
						true)).append(",");
		// �Ζ�����
		sb.append(
				CommonUtil.createJsonStr(SystemConstant.KEY_WORK_TIME,
						this.workTime, false)).append(",");
		// ���Ζ�����
		sb.append(CommonUtil.createJsonStr(SystemConstant.KEY_TOTAL,
				this.total, false)).append("}");

		return sb.toString();
	}
}
