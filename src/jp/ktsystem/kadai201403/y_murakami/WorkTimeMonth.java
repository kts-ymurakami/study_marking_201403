package jp.ktsystem.kadai201403.y_murakami;
import java.util.ArrayList;
import java.util.List;

/**
 * 月の勤務時間を格納するモデル
 *
 * @author y_murakami
 *
 */
public class WorkTimeMonth {

	/**
	 * 月
	 */
	private String month;
	/**
	 * 日のモデル　WorkTime
	 */
	private List<WorkTime> workTimeList = new ArrayList<WorkTime>() {
	};

	/**
	 * コンストラクタ
	 *
	 * @param month
	 *            日付
	 */
	public WorkTimeMonth(String month) {
		this.month = month;
	}

	/**
	 * 日のデータを追加
	 *
	 * @param workTime
	 */
	public void addDate(WorkTime workTime) {
		if (null != workTime) {
			workTimeList.add(workTime);
		}
	}

}
