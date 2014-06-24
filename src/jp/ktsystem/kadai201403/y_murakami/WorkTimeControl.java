package jp.ktsystem.kadai201403.y_murakami;

import jp.ktsystem.kadai201403.y_murakami.common.ErrorCode;
import jp.ktsystem.kadai201403.y_murakami.common.KadaiException;
import jp.ktsystem.kadai201403.y_murakami.common.SystemConstant;
import jp.ktsystem.kadai201403.y_murakami.util.ErrorUtil;

/**
 * �ｿｽﾎ厄ｿｽ�ｿｽ�ｿｽ�ｿｽﾔ撰ｿｽ�ｿｽ�ｿｽN�ｿｽ�ｿｽ�ｿｽX
 *
 * @author y_murakami
 *
 */
public class WorkTimeControl {

	private KadaiTime startTime = null;
	private KadaiTime endTime = null;

	/**
	 * �ｿｽR�ｿｽ�ｿｽ�ｿｽX�ｿｽg�ｿｽ�ｿｽ�ｿｽN�ｿｽ^ �ｿｽo�ｿｽﾐ趣ｿｽ�ｿｽ�ｿｽ�ｿｽﾆ退社趣ｿｽ�ｿｽ�ｿｽ�ｿｽ�ｿｽ�ｿｽ�ｿｽ�ｿｽ�ｿｽ�ｿｽo�ｿｽﾉ設定す�ｿｽ�ｿｽ
	 *
	 * @param startTime
	 *            �ｿｽo�ｿｽﾐ趣ｿｽ�ｿｽﾔコ�ｿｽ�ｿｽ�ｿｽg�ｿｽ�ｿｽ�ｿｽ[�ｿｽ�ｿｽ
	 * @param endTime
	 *            �ｿｽﾞ社趣ｿｽ�ｿｽﾔコ�ｿｽ�ｿｽ�ｿｽg�ｿｽ�ｿｽ�ｿｽ[�ｿｽ�ｿｽ
	 * @throws KadaiException
	 */
	public WorkTimeControl(KadaiTime startTime, KadaiTime endTime)
			throws KadaiException {

		// �ｿｽG�ｿｽ�ｿｽ�ｿｽ[�ｿｽ`�ｿｽF�ｿｽb�ｿｽN �ｿｽﾞ社趣ｿｽ�ｿｽﾔゑｿｽ�ｿｽo�ｿｽﾐ趣ｿｽ�ｿｽﾔゑｿｽ�ｿｽO�ｿｽ�ｿｽ�ｿｽﾇゑｿｽ�ｿｽ�ｿｽ
		if (!ErrorUtil.startTimeBeforeEndTime(startTime, endTime)) {
			throw new KadaiException(ErrorCode.END_BEFOR_START);
		}

		// �ｿｽ�ｿｽ�ｿｽ�ｿｽ�ｿｽo�ｿｽﾉ設抵ｿｽ
		this.startTime = startTime;
		this.endTime = endTime;

		// �ｿｽo�ｿｽﾐ趣ｿｽ�ｿｽ�ｿｽ�ｿｽﾆ退社趣ｿｽ�ｿｽ�ｿｽ�ｿｽﾌ抵ｿｽ�ｿｽ�ｿｽ�ｿｽ�ｿｽ�ｿｽs�ｿｽ�ｿｽ
		adjustStartEndTime();

	}

	/**
	 * �ｿｽﾎ厄ｿｽ�ｿｽ�ｿｽ�ｿｽﾔを文趣ｿｽ�ｿｽ�ｿｽﾅ返ゑｿｽ�ｿｽ@�ｿｽ�ｿｽ�ｿｽ�ｿｽ�ｿｽ�ｿｽﾔゑｿｽ
	 *
	 * @return 勤務時間を返す
	 */
	public String returnWorkTime() {

		// �ｿｽﾎ厄ｿｽ�ｿｽ�ｿｽ�ｿｽﾔ　�ｿｽ�ｿｽ
		int workHour = returnIntWorkHour();
		// �ｿｽﾎ厄ｿｽ�ｿｽ�ｿｽ�ｿｽﾔ　�ｿｽ�ｿｽ
		int workMinute = returnIntWorkMinute();
		// �ｿｽﾎ厄ｿｽ�ｿｽ�ｿｽ�ｿｽ�ｿｽ�ｿｽ�ｿｽ�ｿｽﾔ　�ｿｽ�ｿｽ
		int workTime = workHour * SystemConstant.MINUTE_OF_ONE_HOUR
				+ workMinute;

		// �ｿｽx�ｿｽe�ｿｽ�ｿｽ�ｿｽﾔゑｿｽ�ｿｽﾐゑｿｽ
		workTime -= calcRestTime();

		return String.valueOf(workTime);

	}

	/**
	 * �ｿｽﾎ厄ｿｽ�ｿｽ�ｿｽ�ｿｽﾔの「�ｿｽ�ｿｽ�ｿｽv�ｿｽ�ｿｽ�ｿｽ�ｿｽ�ｿｽ�ｿｽﾔゑｿｽ
	 *
	 * @return�ｿｽ@�ｿｽﾎ厄ｿｽ�ｿｽ�ｿｽ�ｿｽﾔ　�ｿｽ�ｿｽ
	 */
	private int returnIntWorkHour() {

		// �ｿｽﾎ厄ｿｽ�ｿｽ�ｿｽ�ｿｽﾔ　�ｿｽ�ｿｽ
		int workHour = 0;

		if (null != this.startTime && null != this.endTime) {
			// �ｿｽo�ｿｽﾐ趣ｿｽ�ｿｽﾆ退社趣ｿｽ�ｿｽﾌ搾ｿｽ�ｿｽ�ｿｽ�ｿｽ�ｿｽ�ｿｽﾆゑｿｽ
			workHour = this.endTime.getIntHour() - this.startTime.getIntHour();

			// �ｿｽ�ｿｽ�ｿｽ�ｿｽ�ｿｽﾌ場合�ｿｽ�ｿｽ0�ｿｽ�ｿｽﾔゑｿｽ
			if (0 == workHour) {
				return workHour;
			}

			// �ｿｽo�ｿｽﾐ包ｿｽ�ｿｽﾌ包ｿｽ�ｿｽ�ｿｽ�ｿｽﾞ社包ｿｽ�ｿｽ�ｿｽ闖ｬ�ｿｽ�ｿｽ�ｿｽ�ｿｽ�ｿｽ鼾�ｿｽ�ｿｽ1�ｿｽf�ｿｽN�ｿｽ�ｿｽ�ｿｽ�ｿｽ�ｿｽ�ｿｽ�ｿｽg�ｿｽ�ｿｽ�ｿｽ�ｿｽ
			if (this.endTime.getIntMinute() < this.startTime.getIntMinute()) {
				workHour--;
			}

			return workHour;

		} else {
			return workHour;
		}

	}

	/**
	 * �ｿｽﾎ厄ｿｽ�ｿｽ�ｿｽ�ｿｽﾔの「�ｿｽ�ｿｽ�ｿｽv�ｿｽ�ｿｽ�ｿｽ�ｿｽ�ｿｽ�ｿｽﾔゑｿｽ
	 *
	 * @return�ｿｽ@�ｿｽﾎ厄ｿｽ�ｿｽ�ｿｽ�ｿｽﾔ　�ｿｽ�ｿｽ
	 */
	private int returnIntWorkMinute() {

		// �ｿｽﾎ厄ｿｽ�ｿｽ�ｿｽ�ｿｽﾔ　�ｿｽ�ｿｽ
		int workMinute = 0;

		if (null != this.startTime && null != this.endTime) {

			int startMinute = this.startTime.getIntMinute();
			int endMinute = this.endTime.getIntMinute();

			// �ｿｽo�ｿｽﾐと退社の搾ｿｽ�ｿｽ�ｿｽ�ｿｽ�ｿｽ�ｿｽﾆゑｿｽ
			workMinute = endMinute - startMinute;

			// �ｿｽ�ｿｽ�ｿｽ�ｿｽ�ｿｽ�ｿｽ�ｿｽ�ｿｽ�ｿｽﾌ撰ｿｽ�ｿｽﾌ趣ｿｽ�ｿｽ�ｿｽ60�ｿｽ�ｿｽ�ｿｽ�ｿｽ�ｿｽ�ｿｽ�ｿｽﾄ勤厄ｿｽ�ｿｽ�ｿｽ�ｿｽ�ｿｽ�ｿｽ�ｿｽ�ｿｽﾟゑｿｽ
			if (0 > workMinute) {
				workMinute += SystemConstant.MINUTE_OF_ONE_HOUR;
			}

			return workMinute;

		} else {
			return workMinute;
		}

	}

	/**
	 * �ｿｽx�ｿｽe�ｿｽ�ｿｽ�ｿｽﾔゑｿｽ�ｿｽv�ｿｽZ�ｿｽ�ｿｽ�ｿｽ�ｿｽ
	 *
	 * @return �ｿｽx�ｿｽe�ｿｽ�ｿｽ�ｿｽ�ｿｽ
	 */
	private int calcRestTime() {

		int restTime = 0;// �ｿｽx�ｿｽe�ｿｽ�ｿｽ�ｿｽ�ｿｽ

		if (null != this.startTime && null != this.endTime) {
			int startHour = this.startTime.getIntHour();// �ｿｽo�ｿｽﾐ趣ｿｽ
			int endHour = this.endTime.getIntHour();// �ｿｽﾞ社趣ｿｽ
			int endMinute = this.endTime.getIntMinute();// �ｿｽﾞ社包ｿｽ

			// �ｿｽ�ｿｽ�ｿｽ�ｿｽ�ｿｽx�ｿｽﾝゑｿｽ�ｿｽﾈ前�ｿｽﾉ出�ｿｽﾐゑｿｽ�ｿｽﾄゑｿｽ�ｿｽ�ｿｽ鼾�
			if (SystemConstant.START_REST_HOUR_NOON > startHour) {
				// �ｿｽﾞ社趣ｿｽ�ｿｽ�ｿｽ�ｿｽ�ｿｽ�ｿｽ�ｿｽ�ｿｽ�ｿｽ�ｿｽx�ｿｽe�ｿｽ�ｿｽ�ｿｽ�ｿｽﾌ趣ｿｽ
				if (SystemConstant.START_HOUR_NOON <= endHour) {
					// �ｿｽx�ｿｽe�ｿｽ�ｿｽ�ｿｽﾔにゑｿｽ�ｿｽ�ｿｽ�ｿｽx�ｿｽe�ｿｽ�ｿｽ�ｿｽﾔを足ゑｿｽ
					restTime += SystemConstant.REST_TIME_NOON;
				}
				// �ｿｽﾞ社趣ｿｽ�ｿｽ�ｿｽ�ｿｽ�ｿｽ�ｿｽ[�ｿｽ�ｿｽ�ｿｽx�ｿｽe�ｿｽ�ｿｽ�ｿｽ�ｿｽﾌ趣ｿｽ
				if ((SystemConstant.REST_HOUR_EVE == endHour && SystemConstant.START_EVE_MINUTE <= endMinute)
						|| SystemConstant.REST_HOUR_EVE < endHour) {
					// �ｿｽx�ｿｽe�ｿｽ�ｿｽ�ｿｽﾔに夕�ｿｽ�ｿｽ�ｿｽx�ｿｽe�ｿｽ�ｿｽ�ｿｽﾔを足ゑｿｽ
					restTime += SystemConstant.REST_TIME_EVE;
				}
			}

			else if (SystemConstant.REST_HOUR_EVE > startHour) {
				// �ｿｽo�ｿｽﾐ趣ｿｽ�ｿｽ�ｿｽ�ｿｽ�ｿｽ�ｿｽ�ｿｽ�ｿｽ�ｿｽ�ｿｽx�ｿｽﾝゑｿｽ�ｿｽ�ｿｽA�ｿｽ[�ｿｽ�ｿｽ�ｿｽx�ｿｽe�ｿｽ�ｿｽ�ｿｽO
				// �ｿｽﾞ社趣ｿｽ�ｿｽ�ｿｽ�ｿｽ�ｿｽ�ｿｽ[�ｿｽ�ｿｽ�ｿｽx�ｿｽe�ｿｽ�ｿｽ�ｿｽ�ｿｽﾌ趣ｿｽ
				if ((SystemConstant.REST_HOUR_EVE == endHour && SystemConstant.START_EVE_MINUTE <= endMinute)
						|| SystemConstant.REST_HOUR_EVE < endHour) {
					// �ｿｽx�ｿｽe�ｿｽ�ｿｽ�ｿｽﾔに夕�ｿｽ�ｿｽ�ｿｽx�ｿｽe�ｿｽ�ｿｽ�ｿｽﾔを足ゑｿｽ
					restTime += SystemConstant.REST_TIME_EVE;
				}
			}
		}

		return restTime;

	}

	/**
	 * �ｿｽo�ｿｽﾐ趣ｿｽ�ｿｽ�ｿｽ�ｿｽﾆ退社趣ｿｽ�ｿｽ�ｿｽ�ｿｽﾌ抵ｿｽ�ｿｽ�ｿｽ�ｿｽ�ｿｽ�ｿｽs�ｿｽ�ｿｽ
	 */
	private void adjustStartEndTime() {

		if (null != this.startTime && null != this.endTime) {

			/********** �ｿｽo�ｿｽﾐ趣ｿｽ�ｿｽ�ｿｽ�ｿｽ�ｿｽ�ｿｽ�ｿｽ **********/
			int startHour = this.startTime.getIntHour();// �ｿｽo�ｿｽﾐ趣ｿｽ�ｿｽ@�ｿｽ�ｿｽ
			int startMinute = this.startTime.getIntMinute();// �ｿｽo�ｿｽﾐ趣ｿｽ�ｿｽ@�ｿｽ�ｿｽ

			// �ｿｽo�ｿｽﾐ趣ｿｽ�ｿｽ�ｿｽ�ｿｽ�ｿｽ�ｿｽn�ｿｽﾆ趣ｿｽ�ｿｽﾔゑｿｽ�ｿｽO�ｿｽﾌ趣ｿｽ
			if (SystemConstant.WORK_START_HOUR_MORNING > startHour) {
				// �ｿｽo�ｿｽﾐ趣ｿｽ�ｿｽ�ｿｽ�ｿｽ�ｿｽ�ｿｽn�ｿｽﾆ趣ｿｽ�ｿｽﾔに抵ｿｽ�ｿｽ�ｿｽ
				this.startTime
						.setIntHour(SystemConstant.WORK_START_HOUR_MORNING);
				this.startTime.setIntMinute(SystemConstant.MINIMUM_TIME);

				// �ｿｽo�ｿｽﾐ趣ｿｽ�ｿｽﾔゑｿｽ�ｿｽ�ｿｽ�ｿｽ�ｿｽ�ｿｽx�ｿｽe�ｿｽ�ｿｽ�ｿｽﾔ抵ｿｽ�ｿｽﾌ趣ｿｽ
			} else if (SystemConstant.START_REST_HOUR_NOON == startHour) {
				// �ｿｽo�ｿｽﾐ趣ｿｽ�ｿｽﾔゑｿｽ�ｿｽ�ｿｽ�ｿｽ�ｿｽ�ｿｽx�ｿｽe�ｿｽ�ｿｽﾉ抵ｿｽ�ｿｽ�ｿｽ
				this.startTime.setIntHour(startHour + 1);
				this.startTime.setIntMinute(SystemConstant.MINIMUM_TIME);

				// �ｿｽo�ｿｽﾐ趣ｿｽ�ｿｽ�ｿｽ�ｿｽ�ｿｽ�ｿｽ[�ｿｽ�ｿｽ�ｿｽx�ｿｽe�ｿｽ�ｿｽ�ｿｽﾔ抵ｿｽ�ｿｽﾌ趣ｿｽ
			} else if (SystemConstant.REST_HOUR_EVE == startHour
					&& SystemConstant.START_EVE_MINUTE > startMinute) {
				// �ｿｽo�ｿｽﾐ趣ｿｽ�ｿｽ�ｿｽ�ｿｽ�ｿｽ[�ｿｽ�ｿｽ�ｿｽx�ｿｽe�ｿｽ�ｿｽﾉ抵ｿｽ�ｿｽ�ｿｽ
				this.startTime.setIntMinute(SystemConstant.START_EVE_MINUTE);
			}

			/********** �ｿｽﾞ社趣ｿｽ�ｿｽ�ｿｽ�ｿｽ�ｿｽ�ｿｽ�ｿｽ **********/
			int endHour = this.endTime.getIntHour();// �ｿｽﾞ社　�ｿｽ�ｿｽ
			int endMinute = this.endTime.getIntMinute();// �ｿｽﾞ社　�ｿｽ�ｿｽ

			if (SystemConstant.WORK_START_HOUR_MORNING > endHour) {
				this.endTime.setIntHour(SystemConstant.WORK_START_HOUR_MORNING);
				this.endTime.setIntMinute(SystemConstant.MINIMUM_TIME);
			}
			// �ｿｽ�ｿｽ�ｿｽ�ｿｽ�ｿｽx�ｿｽe�ｿｽ�ｿｽ�ｿｽﾔ抵ｿｽ�ｿｽﾉ退社ゑｿｽ�ｿｽ�ｿｽ�ｿｽ�ｿｽ
			else if (SystemConstant.START_REST_HOUR_NOON == endHour) {
				// �ｿｽ�ｿｽ�ｿｽ�ｿｽ�ｿｽn�ｿｽﾆ趣ｿｽ�ｿｽﾔに抵ｿｽ�ｿｽ�ｿｽ
				this.endTime.setIntHour(endHour + 1);
				this.endTime.setIntMinute(SystemConstant.MINIMUM_TIME);
			}
			// �ｿｽ[�ｿｽ�ｿｽ�ｿｽx�ｿｽe�ｿｽ�ｿｽ�ｿｽﾔ抵ｿｽ�ｿｽﾉ退社ゑｿｽ�ｿｽ�ｿｽ�ｿｽ�ｿｽ
			else if (SystemConstant.REST_HOUR_EVE == endHour
					&& SystemConstant.START_EVE_MINUTE > endMinute) {
				// �ｿｽ[�ｿｽ�ｿｽ�ｿｽn�ｿｽﾆ趣ｿｽ�ｿｽﾔに抵ｿｽ�ｿｽ�ｿｽ
				this.endTime.setIntMinute(SystemConstant.START_EVE_MINUTE);
			}

		}

	}

}
