package jp.ktsystem.kadai201403.y_murakami;

import java.util.Comparator;

import jp.ktsystem.kadai201403.y_murakami.common.SystemConstant;
import jp.ktsystem.kadai201403.y_murakami.util.CommonUtil;

/**
 * 勤務時間クラス
 *
 * @author y_murakami
 *
 */
public class WorkTime {

	/**
	 * 日付
	 */
	private String date;
	/**
	 * workTime 勤務時間
	 */
	private String workTime;
	/**
	 * 総勤務時間
	 */
	private String total;

	public String getDate() {
		return this.date;
	}

	public String getWorkTime() {
		return this.workTime;
	}

	public void setTotal(String total) {
		this.total = total;
	}

	/**
	 * コンストラクタ
	 *
	 * @param date
	 *            日付
	 * @param workTime
	 *            勤務時間
	 * @param total
	 *            総勤務時間
	 */
	public WorkTime(String date, String workTime, String total) {

		this.date = date;
		this.workTime = workTime;
		this.total = total;

	}

	/**
	 * コンストラクタ
	 *
	 * @param date
	 *            日付
	 * @param workTime
	 *            勤務時間
	 */
	public WorkTime(String date, String workTime) {

		this.date = date;
		this.workTime = workTime;

	}

	/**
	 * 出力用Json文字列作成
	 *
	 * @return
	 */
	public String createJsonStr() {

		// 日付、時間、合計時間を出力
		StringBuilder sb = new StringBuilder("{");
		sb.append(
				CommonUtil.createJsonStr(SystemConstant.KEY_DATE, this.date,
						true)).append(",");
		sb.append(
				CommonUtil.createJsonStr(SystemConstant.KEY_WORK_TIME,
						this.workTime, false)).append(",");
		sb.append(
				CommonUtil.createJsonStr(SystemConstant.KEY_TOTAL, this.total,
						false)).append("}");
		return sb.toString();
	}
}

/**
 * WorkTimeソート用Comparatorクラス
 *
 */
class WorktTimeComparator implements Comparator<WorkTime> {

	public int compare(WorkTime arg0, WorkTime arg1) {
		String str1 = arg0.getDate();
		String str2 = arg1.getDate();

		int comp = str1.compareTo(str2);
		if (0 < comp) {
			return 1;
		} else if (0 == comp) {
			return 0;
		} else {
			return -1;
		}

	}

}
