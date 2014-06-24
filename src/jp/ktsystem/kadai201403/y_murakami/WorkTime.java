package jp.ktsystem.kadai201403.y_murakami;

import jp.ktsystem.kadai201403.y_murakami.common.SystemConstant;
import jp.ktsystem.kadai201403.y_murakami.util.CommonUtil;

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

	public String getDate() {
		return this.date;
	}

	public String getWorkTime() {
		return this.workTime;
	}

	/**
	 * ���Ζ����Ԃ�ݒ肷��
	 */
	public void setTotal(String total) {
		this.total = total;
	}

	/**
	 * �R���X�g���N�^
	 *
	 * @param date
	 *            �Ζ���
	 * @param workTime
	 *            �Ζ�����
	 * @param total
	 *            ���Ζ�����
	 */
	public WorkTime(String date, String workTime, String total) {

		this.date = date;
		this.workTime = workTime;
		this.total = total;

	}

	/**
	 * �R���X�g���N�^ total�Ȃ��@Lv�Q�p
	 *
	 * @param date
	 *            �Ζ���
	 * @param workTime
	 *            �Ζ�����
	 */
	public WorkTime(String date, String workTime) {

		this.date = date;
		this.workTime = workTime;

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
		sb.append(
				CommonUtil.createJsonStr(SystemConstant.KEY_TOTAL, this.total,
						false)).append("}");

		return sb.toString();
	}
}
