package jp.ktsystem.kadai201403.y_murakami;

import jp.ktsystem.kadai201403.y_murakami.Common.SystemConstant;
import jp.ktsystem.kadai201403.y_murakami.Util.CommonUtil;

/**
 * oอf[^i[f
 *
 * @author y_murakami
 *
 */
public class WorkTime {

	/**
	 * ฮฑ๚
	 */
	private String date;
	/**
	 * workTime ฮฑิ
	 */
	private String workTime;
	/**
	 * ฮฑิ
	 */
	private String total;

	/**
	 * RXgN^
	 *
	 * @param date
	 *            @ฮฑ๚
	 * @param workTime
	 *            @ฮฑิ
	 * @param total
	 *            @ฮฑิ
	 */
	public WorkTime(String date, String workTime, String total) {

		this.date = date;
		this.workTime = workTime;
		this.total = total;

	}

	/**
	 * vpeBฉ็oอpฬJson`ฎฬถ๑๐ิท
	 *
	 * @return
	 */
	public String createJsonStr() {

		StringBuilder sb = new StringBuilder("{");
		// ๚t
		sb.append(
				CommonUtil.createJsonStr(SystemConstant.KEY_DATE, this.date,
						true)).append(",");
		// ฮฑิ
		sb.append(
				CommonUtil.createJsonStr(SystemConstant.KEY_WORK_TIME,
						this.workTime, false)).append(",");
		// ฮฑิ
		sb.append(CommonUtil.createJsonStr(SystemConstant.KEY_TOTAL,
				this.total, false)).append("}");

		return sb.toString();
	}
}
