package jp.ktsystem.kadai201403.y_murakami;
import java.util.ArrayList;
import java.util.List;

/**
 * ���̋Ζ����Ԃ��i�[���郂�f��
 *
 * @author y_murakami
 *
 */
public class WorkTimeMonth {

	/**
	 * ��
	 */
	private String month;
	/**
	 * ���̃��f���@WorkTime
	 */
	private List<WorkTime> workTimeList = new ArrayList<WorkTime>() {
	};

	/**
	 * �R���X�g���N�^
	 *
	 * @param month
	 *            ���t
	 */
	public WorkTimeMonth(String month) {
		this.month = month;
	}

	/**
	 * ���̃f�[�^��ǉ�
	 *
	 * @param workTime
	 */
	public void addDate(WorkTime workTime) {
		if (null != workTime) {
			workTimeList.add(workTime);
		}
	}

}
