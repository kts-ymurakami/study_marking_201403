package jp.ktsystem.kadai201403.y_murakami;

import jp.ktsystem.kadai201403.y_murakami.Common.SystemConstant;
import jp.ktsystem.kadai201403.y_murakami.Util.CommonUtil;

/**
 * oÍf[^i[f
 *
 * @author y_murakami
 *
 */
public class WorkTime {

	/**
	 * Î±ú
	 */
	private String date;
	/**
	 * workTime Î±Ô
	 */
	private String workTime;
	/**
	 * Î±Ô
	 */
	private String total;

	public String getDate() {
		return this.date;
	}

	public String getWorkTime() {
		return this.workTime;
	}

	/**
	 * Î±ÔðÝè·é
	 */
	public void setTotal(String total) {
		this.total = total;
	}

	/**
	 * RXgN^
	 *
	 * @param date
	 *            Î±ú
	 * @param workTime
	 *            Î±Ô
	 * @param total
	 *            Î±Ô
	 */
	public WorkTime(String date, String workTime, String total) {

		this.date = date;
		this.workTime = workTime;
		this.total = total;

	}

	/**
	 * RXgN^ totalÈµ@LvQp
	 *
	 * @param date
	 *            Î±ú
	 * @param workTime
	 *            Î±Ô
	 */
	public WorkTime(String date, String workTime) {

		this.date = date;
		this.workTime = workTime;

	}

	/**
	 * vpeB©çoÍpÌJson`®Ì¶ñðÔ·
	 *
	 * @return
	 */
	public String createJsonStr() {

		StringBuilder sb = new StringBuilder("{");
		// út
		sb.append(
				CommonUtil.createJsonStr(SystemConstant.KEY_DATE, this.date,
						true)).append(",");
		// Î±Ô
		sb.append(
				CommonUtil.createJsonStr(SystemConstant.KEY_WORK_TIME,
						this.workTime, false)).append(",");
		// Î±Ô
		sb.append(
				CommonUtil.createJsonStr(SystemConstant.KEY_TOTAL, this.total,
						false)).append("}");

		return sb.toString();
	}
}
