package jp.ktsystem.kadai201403.y_murakami;

import java.util.Comparator;

import jp.ktsystem.kadai201403.y_murakami.common.SystemConstant;
import jp.ktsystem.kadai201403.y_murakami.util.CommonUtil;

/**
 * �ｿｽo�ｿｽﾍデ�ｿｽ[�ｿｽ^�ｿｽi�ｿｽ[�ｿｽ�ｿｽ�ｿｽf�ｿｽ�ｿｽ
 *
 * @author y_murakami
 *
 */
public class WorkTime {

	/**
	 * �ｿｽﾎ厄ｿｽ�ｿｽ�ｿｽ
	 */
	private String date;
	/**
	 * workTime �ｿｽﾎ厄ｿｽ�ｿｽ�ｿｽ�ｿｽ�ｿｽ
	 */
	private String workTime;
	/**
	 * �ｿｽ�ｿｽ�ｿｽﾎ厄ｿｽ�ｿｽ�ｿｽ�ｿｽ�ｿｽ
	 */
	private String total;

	public String getDate() {
		return this.date;
	}

	public String getWorkTime() {
		return this.workTime;
	}

	/**
	 * �ｿｽ�ｿｽ�ｿｽﾎ厄ｿｽ�ｿｽ�ｿｽ�ｿｽﾔゑｿｽﾝ定す�ｿｽ�ｿｽ
	 */
	public void setTotal(String total) {
		this.total = total;
	}

	/**
	 * �ｿｽR�ｿｽ�ｿｽ�ｿｽX�ｿｽg�ｿｽ�ｿｽ�ｿｽN�ｿｽ^
	 *
	 * @param date
	 *            �ｿｽﾎ厄ｿｽ�ｿｽ�ｿｽ
	 * @param workTime
	 *            �ｿｽﾎ厄ｿｽ�ｿｽ�ｿｽ�ｿｽ�ｿｽ
	 * @param total
	 *            �ｿｽ�ｿｽ�ｿｽﾎ厄ｿｽ�ｿｽ�ｿｽ�ｿｽ�ｿｽ
	 */
	public WorkTime(String date, String workTime, String total) {

		this.date = date;
		this.workTime = workTime;
		this.total = total;

	}

	/**
	 * �ｿｽR�ｿｽ�ｿｽ�ｿｽX�ｿｽg�ｿｽ�ｿｽ�ｿｽN�ｿｽ^ total�ｿｽﾈゑｿｽ�ｿｽ@Lv�ｿｽQ�ｿｽp
	 *
	 * @param date
	 *            �ｿｽﾎ厄ｿｽ�ｿｽ�ｿｽ
	 * @param workTime
	 *            �ｿｽﾎ厄ｿｽ�ｿｽ�ｿｽ�ｿｽ�ｿｽ
	 */
	public WorkTime(String date, String workTime) {

		this.date = date;
		this.workTime = workTime;

	}

	/**
	 * �ｿｽv�ｿｽ�ｿｽ�ｿｽp�ｿｽe�ｿｽB�ｿｽ�ｿｽ�ｿｽ�ｿｽo�ｿｽﾍ用�ｿｽ�ｿｽJson�ｿｽ`�ｿｽ�ｿｽ�ｿｽﾌ包ｿｽ�ｿｽ�ｿｽ
	 * �ｿｽ�ｿｽ�ｿｽﾔゑｿｽ
	 *
	 * @return
	 */
	public String createJsonStr() {

		StringBuilder sb = new StringBuilder("{");
		// �ｿｽ�ｿｽ�ｿｽt
		sb.append(
				CommonUtil.createJsonStr(SystemConstant.KEY_DATE, this.date,
						true)).append(",");
		// �ｿｽﾎ厄ｿｽ�ｿｽ�ｿｽ�ｿｽ�ｿｽ
		sb.append(
				CommonUtil.createJsonStr(SystemConstant.KEY_WORK_TIME,
						this.workTime, false)).append(",");
		// �ｿｽ�ｿｽ�ｿｽﾎ厄ｿｽ�ｿｽ�ｿｽ�ｿｽ�ｿｽ
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
