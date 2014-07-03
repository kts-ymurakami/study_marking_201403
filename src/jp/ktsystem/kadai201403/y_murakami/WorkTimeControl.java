package jp.ktsystem.kadai201403.y_murakami;

import jp.ktsystem.kadai201403.y_murakami.common.ErrorCode;
import jp.ktsystem.kadai201403.y_murakami.common.KadaiException;
import jp.ktsystem.kadai201403.y_murakami.common.SystemConstant;
import jp.ktsystem.kadai201403.y_murakami.util.ErrorUtil;

/**
 *　勤務時間コントロールクラス
 *
 * @author y_murakami
 *
 */
public class WorkTimeControl {

	private KadaiTime startTime = null;
	private KadaiTime endTime = null;

	/**
	 * コンストラクタ
	 *
	 * @param startTime
	 *            出社時間
	 * @param endTime
	 *            退社時間
	 * @throws KadaiException
	 */
	public WorkTimeControl(KadaiTime startTime, KadaiTime endTime)
			throws KadaiException {

		// 出社時間と退社時間の前後チェック
		if (!ErrorUtil.startTimeBeforeEndTime(startTime, endTime)) {
			throw new KadaiException(ErrorCode.END_BEFOR_START);
		}

		this.startTime = startTime;
		this.endTime = endTime;

		// 出社時間と退社時間の調整
		adjustStartEndTime();

	}

	/**
	 * 勤務時間の文字列を返す
	 *
	 * @return 勤務時間
	 */
	public String returnWorkTime() {

		int workHour = returnIntWorkHour();
		int workMinute = returnIntWorkMinute();
		int workTime = workHour * SystemConstant.MINUTE_OF_ONE_HOUR
				+ workMinute;

		// 休憩時間をひく
		workTime -= calcRestTime();

		return String.valueOf(workTime);

	}

	/**
	 * 勤務時間を分で返す
	 *
	 * @return　勤務時間　分
	 */
	private int returnIntWorkHour() {

		int workHour = 0;

		workHour = this.endTime.getIntHour() - this.startTime.getIntHour();

		if (0 == workHour) {
			return workHour;
		}

		if (this.endTime.getIntMinute() < this.startTime.getIntMinute()) {
			workHour--;
		}

		return workHour;

	}

	/**
	 * 勤務時間　分を返す
	 *
	 * @return　分
	 */
	private int returnIntWorkMinute() {

		int workMinute = 0;

		int startMinute = this.startTime.getIntMinute();
		int endMinute = this.endTime.getIntMinute();

		// 勤務分
		workMinute = endMinute - startMinute;

		if (0 > workMinute) {
			workMinute += SystemConstant.MINUTE_OF_ONE_HOUR;
		}

		return workMinute;

	}

	/**
	 * 休憩時間を計算する
	 *
	 * @return 休憩時間
	 */
	private int calcRestTime() {

		int restTime = 0;

		int startHour = this.startTime.getIntHour();// 出社時
		int endHour = this.endTime.getIntHour();// 退社時
		int endMinute = this.endTime.getIntMinute();// 退社分

		// 休憩時間の計算
		if (SystemConstant.START_REST_HOUR_NOON > startHour) {
			if (SystemConstant.START_HOUR_NOON <= endHour) {
				restTime += SystemConstant.REST_TIME_NOON;
			}
			if ((SystemConstant.REST_HOUR_EVE == endHour && SystemConstant.START_EVE_MINUTE <= endMinute)
					|| SystemConstant.REST_HOUR_EVE < endHour) {
				restTime += SystemConstant.REST_TIME_EVE;
			}
		}
		else if (SystemConstant.REST_HOUR_EVE > startHour) {
			if ((SystemConstant.REST_HOUR_EVE == endHour && SystemConstant.START_EVE_MINUTE <= endMinute)
					|| SystemConstant.REST_HOUR_EVE < endHour) {
				restTime += SystemConstant.REST_TIME_EVE;
			}
		}

		return restTime;

	}

	/**
	 * 出社時間、退社時間調整
	 */
	private void adjustStartEndTime() {

		int startHour = this.startTime.getIntHour();
		int startMinute = this.startTime.getIntMinute();

		// 始業時間より前
		if (SystemConstant.WORK_START_HOUR_MORNING > startHour) {
			this.startTime.setIntHour(SystemConstant.WORK_START_HOUR_MORNING);
			this.startTime.setIntMinute(SystemConstant.MINIMUM_TIME);

			// お昼休み
		} else if (SystemConstant.START_REST_HOUR_NOON == startHour) {
			this.startTime.setIntHour(startHour + 1);
			this.startTime.setIntMinute(SystemConstant.MINIMUM_TIME);

			// 夕方休み
		} else if (SystemConstant.REST_HOUR_EVE == startHour
				&& SystemConstant.START_EVE_MINUTE > startMinute) {
			this.startTime.setIntMinute(SystemConstant.START_EVE_MINUTE);
		}

		int endHour = this.endTime.getIntHour();
		int endMinute = this.endTime.getIntMinute();

		if (SystemConstant.WORK_START_HOUR_MORNING > endHour) {
			this.endTime.setIntHour(SystemConstant.WORK_START_HOUR_MORNING);
			this.endTime.setIntMinute(SystemConstant.MINIMUM_TIME);
		}
		else if (SystemConstant.START_REST_HOUR_NOON == endHour) {
			this.endTime.setIntHour(endHour + 1);
			this.endTime.setIntMinute(SystemConstant.MINIMUM_TIME);
		}
		else if (SystemConstant.REST_HOUR_EVE == endHour
				&& SystemConstant.START_EVE_MINUTE > endMinute) {
			this.endTime.setIntMinute(SystemConstant.START_EVE_MINUTE);
		}

	}

}
