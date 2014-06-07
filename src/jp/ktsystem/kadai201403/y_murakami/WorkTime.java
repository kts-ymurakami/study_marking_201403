package jp.ktsystem.kadai201403.y_murakami;

import jp.ktsystem.kadai201403.y_murakami.Common.SystemConstant;
import jp.ktsystem.kadai201403.y_murakami.Util.CommonUtil;

/**
 * 出力データ格納モデル
 *
 * @author y_murakami
 *
 */
public class WorkTime {

	/**
	 * 勤務日
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

	/**
	 * 総勤務時間を設定する
	 */
	public void setTotal(String total) {
		this.total = total;
	}

	/**
	 * コンストラクタ
	 *
	 * @param date
	 *            勤務日
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
	 * コンストラクタ totalなし　Lv２用
	 *
	 * @param date
	 *            勤務日
	 * @param workTime
	 *            勤務時間
	 */
	public WorkTime(String date, String workTime) {

		this.date = date;
		this.workTime = workTime;

	}

	/**
	 * プロパティから出力用のJson形式の文字列を返す
	 *
	 * @return
	 */
	public String createJsonStr() {

		StringBuilder sb = new StringBuilder("{");
		// 日付
		sb.append(
				CommonUtil.createJsonStr(SystemConstant.KEY_DATE, this.date,
						true)).append(",");
		// 勤務時間
		sb.append(
				CommonUtil.createJsonStr(SystemConstant.KEY_WORK_TIME,
						this.workTime, false)).append(",");
		// 総勤務時間
		sb.append(
				CommonUtil.createJsonStr(SystemConstant.KEY_TOTAL, this.total,
						false)).append("}");

		return sb.toString();
	}
}
