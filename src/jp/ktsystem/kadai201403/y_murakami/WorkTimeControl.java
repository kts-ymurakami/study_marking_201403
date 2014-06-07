package jp.ktsystem.kadai201403.y_murakami;

import jp.ktsystem.kadai201403.y_murakami.Common.ErrorCode;
import jp.ktsystem.kadai201403.y_murakami.Common.KadaiException;
import jp.ktsystem.kadai201403.y_murakami.Common.SystemConstant;
import jp.ktsystem.kadai201403.y_murakami.Util.ErrorUtil;

/**
 * 勤務時間制御クラス
 *
 * @author y_murakami
 *
 */
public class WorkTimeControl {

	private KadaiTime startTime = null;
	private KadaiTime endTime = null;

	/**
	 * コンストラクタ 出社時刻と退社時刻をメンバに設定する
	 *
	 * @param startTime
	 *            出社時間コントロール
	 * @param endTime
	 *            退社時間コントロール
	 * @throws KadaiException
	 */
	public WorkTimeControl(KadaiTime startTime, KadaiTime endTime)
			throws KadaiException {

		// エラーチェック 退社時間が出社時間より前かどうか
		if (!ErrorUtil.endTimeBeforeStartTime(startTime, endTime)) {
			throw new KadaiException(ErrorCode.END_BEFOR_START);
		}

		// メンバに設定
		this.startTime = startTime;
		this.endTime = endTime;

		// 出社時刻と退社時刻の調整を行う
		adjustStartEndTime();

	}

	/**
	 * 勤務時間を文字列で返す　総分を返す
	 *
	 * @return 勤務時間文字列　HHmm
	 */
	public String returnWorkTime() {

		// 勤務時間　時
		int workHour = returnIntWorkHour();
		// 勤務時間　分
		int workMinute = returnIntWorkMinute();
		// 勤務総合時間　分
		int workTime = workHour * SystemConstant.MINUTE_OF_ONE_HOUR
				+ workMinute;

		// 休憩時間をひく
		workTime -= calcRestTime();

		return String.valueOf(workTime);

	}

	/**
	 * 勤務時間の「時」部分を返す
	 *
	 * @return　勤務時間　時
	 */
	private int returnIntWorkHour() {

		// 勤務時間　時
		int workHour = 0;

		if (null != this.startTime && null != this.endTime) {
			// 出社時と退社時の差分をとる
			workHour = this.endTime.getIntHour() - this.startTime.getIntHour();

			// 同時の場合は0を返す
			if (0 == workHour) {
				return workHour;
			}

			// 出社分の方が退社分より小さい場合は1デクリメントする
			if (this.endTime.getIntMinute() < this.startTime.getIntMinute()) {
				workHour--;
			}

			return workHour;

		} else {
			return workHour;
		}

	}

	/**
	 * 勤務時間の「分」部分を返す
	 *
	 * @return　勤務時間　分
	 */
	private int returnIntWorkMinute() {

		// 勤務時間　分
		int workMinute = 0;

		if (null != this.startTime && null != this.endTime) {

			int startMinute = this.startTime.getIntMinute();
			int endMinute = this.endTime.getIntMinute();

			// 出社と退社の差分をとる
			workMinute = endMinute - startMinute;

			// 差分が負の数の時は60分加えて勤務分を求める
			if (0 > workMinute) {
				workMinute += SystemConstant.MINUTE_OF_ONE_HOUR;
			}

			return workMinute;

		} else {
			return workMinute;
		}

	}

	/**
	 * 休憩時間を計算する
	 *
	 * @return 休憩時間
	 */
	private int calcRestTime() {

		int restTime = 0;// 休憩時間

		if (null != this.startTime && null != this.endTime) {
			int startHour = this.startTime.getIntHour();// 出社時
			int endHour = this.endTime.getIntHour();// 退社時
			int endMinute = this.endTime.getIntMinute();// 退社分

			// お昼休みより以前に出社している場合
			if (SystemConstant.START_REST_HOUR_NOON > startHour) {
				// 退社時刻がお昼休憩より後の時
				if (SystemConstant.START_HOUR_NOON <= endHour) {
					// 休憩時間にお昼休憩時間を足す
					restTime += SystemConstant.REST_TIME_NOON;
				}
				// 退社時刻が夕方休憩より後の時
				if ((SystemConstant.REST_HOUR_EVE == endHour && SystemConstant.START_EVE_MINUTE <= endMinute)
						|| SystemConstant.REST_HOUR_EVE < endHour) {
					// 休憩時間に夕方休憩時間を足す
					restTime += SystemConstant.REST_TIME_EVE;
				}
			}

			else if (SystemConstant.REST_HOUR_EVE > startHour) {
				// 出社時刻がお昼休みより後、夕方休憩より前
				// 退社時刻が夕方休憩より後の時
				if ((SystemConstant.REST_HOUR_EVE == endHour && SystemConstant.START_EVE_MINUTE <= endMinute)
						|| SystemConstant.REST_HOUR_EVE < endHour) {
					// 休憩時間に夕方休憩時間を足す
					restTime += SystemConstant.REST_TIME_EVE;
				}
			}
		}

		return restTime;

	}

	/**
	 * 出社時刻と退社時刻の調整を行う
	 */
	private void adjustStartEndTime() {

		if (null != this.startTime && null != this.endTime) {

			/********** 出社時刻調整 **********/
			int startHour = this.startTime.getIntHour();// 出社時　時
			int startMinute = this.startTime.getIntMinute();// 出社時　分

			// 出社時刻が始業時間より前の時
			if (SystemConstant.WORK_START_HOUR_MORNING > startHour) {
				// 出社時刻を始業時間に調整
				this.startTime
						.setIntHour(SystemConstant.WORK_START_HOUR_MORNING);
				this.startTime.setIntMinute(SystemConstant.MINIMUM_TIME);

				// 出社時間がお昼休憩時間中の時
			} else if (SystemConstant.START_REST_HOUR_NOON == startHour) {
				// 出社時間をお昼休憩後に調整
				this.startTime.setIntHour(startHour + 1);
				this.startTime.setIntMinute(SystemConstant.MINIMUM_TIME);

				// 出社時刻が夕方休憩時間中の時
			} else if (SystemConstant.REST_HOUR_EVE == startHour
					&& SystemConstant.START_EVE_MINUTE > startMinute) {
				// 出社時刻を夕方休憩後に調整
				this.startTime.setIntMinute(SystemConstant.START_EVE_MINUTE);
			}

			/********** 退社時刻調整 **********/
			int endHour = this.endTime.getIntHour();// 退社　時
			int endMinute = this.endTime.getIntMinute();// 退社　分

			if (SystemConstant.WORK_START_HOUR_MORNING > endHour) {
				this.endTime.setIntHour(SystemConstant.WORK_START_HOUR_MORNING);
				this.endTime.setIntMinute(SystemConstant.MINIMUM_TIME);
			}
			// お昼休憩時間中に退社した時
			else if (SystemConstant.START_REST_HOUR_NOON == endHour) {
				// お昼始業時間に調整
				this.endTime.setIntHour(endHour + 1);
				this.endTime.setIntMinute(SystemConstant.MINIMUM_TIME);
			}
			// 夕方休憩時間中に退社した時
			else if (SystemConstant.REST_HOUR_EVE == endHour
					&& SystemConstant.START_EVE_MINUTE > endMinute) {
				// 夕方始業時間に調整
				this.endTime.setIntMinute(SystemConstant.START_EVE_MINUTE);
			}

		}

	}

}
