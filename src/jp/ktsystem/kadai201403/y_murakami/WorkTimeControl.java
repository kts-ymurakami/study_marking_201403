package jp.ktsystem.kadai201403.y_murakami;

import jp.ktsystem.kadai201403.y_murakami.common.ErrorCode;
import jp.ktsystem.kadai201403.y_murakami.common.KadaiException;
import jp.ktsystem.kadai201403.y_murakami.common.SystemConstant;
import jp.ktsystem.kadai201403.y_murakami.util.ErrorUtil;

/**
 * �Ζ����Ԑ���N���X
 *
 * @author y_murakami
 *
 */
public class WorkTimeControl {

	private KadaiTime startTime = null;
	private KadaiTime endTime = null;

	/**
	 * �R���X�g���N�^ �o�Ў����ƑގЎ����������o�ɐݒ肷��
	 *
	 * @param startTime
	 *            �o�Ў��ԃR���g���[��
	 * @param endTime
	 *            �ގЎ��ԃR���g���[��
	 * @throws KadaiException
	 */
	public WorkTimeControl(KadaiTime startTime, KadaiTime endTime)
			throws KadaiException {

		// �G���[�`�F�b�N �ގЎ��Ԃ��o�Ў��Ԃ��O���ǂ���
		if (!ErrorUtil.endTimeBeforeStartTime(startTime, endTime)) {
			throw new KadaiException(ErrorCode.END_BEFOR_START);
		}

		// �����o�ɐݒ�
		this.startTime = startTime;
		this.endTime = endTime;

		// �o�Ў����ƑގЎ����̒������s��
		adjustStartEndTime();

	}

	/**
	 * �Ζ����Ԃ𕶎���ŕԂ��@������Ԃ�
	 *
	 * @return �Ζ����ԕ�����@HHmm
	 */
	public String returnWorkTime() {

		// �Ζ����ԁ@��
		int workHour = returnIntWorkHour();
		// �Ζ����ԁ@��
		int workMinute = returnIntWorkMinute();
		// �Ζ��������ԁ@��
		int workTime = workHour * SystemConstant.MINUTE_OF_ONE_HOUR
				+ workMinute;

		// �x�e���Ԃ��Ђ�
		workTime -= calcRestTime();

		return String.valueOf(workTime);

	}

	/**
	 * �Ζ����Ԃ́u���v������Ԃ�
	 *
	 * @return�@�Ζ����ԁ@��
	 */
	private int returnIntWorkHour() {

		// �Ζ����ԁ@��
		int workHour = 0;

		if (null != this.startTime && null != this.endTime) {
			// �o�Ў��ƑގЎ��̍������Ƃ�
			workHour = this.endTime.getIntHour() - this.startTime.getIntHour();

			// �����̏ꍇ��0��Ԃ�
			if (0 == workHour) {
				return workHour;
			}

			// �o�Е��̕����ގЕ���菬�����ꍇ��1�f�N�������g����
			if (this.endTime.getIntMinute() < this.startTime.getIntMinute()) {
				workHour--;
			}

			return workHour;

		} else {
			return workHour;
		}

	}

	/**
	 * �Ζ����Ԃ́u���v������Ԃ�
	 *
	 * @return�@�Ζ����ԁ@��
	 */
	private int returnIntWorkMinute() {

		// �Ζ����ԁ@��
		int workMinute = 0;

		if (null != this.startTime && null != this.endTime) {

			int startMinute = this.startTime.getIntMinute();
			int endMinute = this.endTime.getIntMinute();

			// �o�ЂƑގЂ̍������Ƃ�
			workMinute = endMinute - startMinute;

			// ���������̐��̎���60�������ċΖ��������߂�
			if (0 > workMinute) {
				workMinute += SystemConstant.MINUTE_OF_ONE_HOUR;
			}

			return workMinute;

		} else {
			return workMinute;
		}

	}

	/**
	 * �x�e���Ԃ��v�Z����
	 *
	 * @return �x�e����
	 */
	private int calcRestTime() {

		int restTime = 0;// �x�e����

		if (null != this.startTime && null != this.endTime) {
			int startHour = this.startTime.getIntHour();// �o�Ў�
			int endHour = this.endTime.getIntHour();// �ގЎ�
			int endMinute = this.endTime.getIntMinute();// �ގЕ�

			// �����x�݂��ȑO�ɏo�Ђ��Ă���ꍇ
			if (SystemConstant.START_REST_HOUR_NOON > startHour) {
				// �ގЎ����������x�e����̎�
				if (SystemConstant.START_HOUR_NOON <= endHour) {
					// �x�e���Ԃɂ����x�e���Ԃ𑫂�
					restTime += SystemConstant.REST_TIME_NOON;
				}
				// �ގЎ������[���x�e����̎�
				if ((SystemConstant.REST_HOUR_EVE == endHour && SystemConstant.START_EVE_MINUTE <= endMinute)
						|| SystemConstant.REST_HOUR_EVE < endHour) {
					// �x�e���Ԃɗ[���x�e���Ԃ𑫂�
					restTime += SystemConstant.REST_TIME_EVE;
				}
			}

			else if (SystemConstant.REST_HOUR_EVE > startHour) {
				// �o�Ў����������x�݂���A�[���x�e���O
				// �ގЎ������[���x�e����̎�
				if ((SystemConstant.REST_HOUR_EVE == endHour && SystemConstant.START_EVE_MINUTE <= endMinute)
						|| SystemConstant.REST_HOUR_EVE < endHour) {
					// �x�e���Ԃɗ[���x�e���Ԃ𑫂�
					restTime += SystemConstant.REST_TIME_EVE;
				}
			}
		}

		return restTime;

	}

	/**
	 * �o�Ў����ƑގЎ����̒������s��
	 */
	private void adjustStartEndTime() {

		if (null != this.startTime && null != this.endTime) {

			/********** �o�Ў������� **********/
			int startHour = this.startTime.getIntHour();// �o�Ў��@��
			int startMinute = this.startTime.getIntMinute();// �o�Ў��@��

			// �o�Ў������n�Ǝ��Ԃ��O�̎�
			if (SystemConstant.WORK_START_HOUR_MORNING > startHour) {
				// �o�Ў������n�Ǝ��Ԃɒ���
				this.startTime
						.setIntHour(SystemConstant.WORK_START_HOUR_MORNING);
				this.startTime.setIntMinute(SystemConstant.MINIMUM_TIME);

				// �o�Ў��Ԃ������x�e���Ԓ��̎�
			} else if (SystemConstant.START_REST_HOUR_NOON == startHour) {
				// �o�Ў��Ԃ������x�e��ɒ���
				this.startTime.setIntHour(startHour + 1);
				this.startTime.setIntMinute(SystemConstant.MINIMUM_TIME);

				// �o�Ў������[���x�e���Ԓ��̎�
			} else if (SystemConstant.REST_HOUR_EVE == startHour
					&& SystemConstant.START_EVE_MINUTE > startMinute) {
				// �o�Ў�����[���x�e��ɒ���
				this.startTime.setIntMinute(SystemConstant.START_EVE_MINUTE);
			}

			/********** �ގЎ������� **********/
			int endHour = this.endTime.getIntHour();// �ގЁ@��
			int endMinute = this.endTime.getIntMinute();// �ގЁ@��

			if (SystemConstant.WORK_START_HOUR_MORNING > endHour) {
				this.endTime.setIntHour(SystemConstant.WORK_START_HOUR_MORNING);
				this.endTime.setIntMinute(SystemConstant.MINIMUM_TIME);
			}
			// �����x�e���Ԓ��ɑގЂ�����
			else if (SystemConstant.START_REST_HOUR_NOON == endHour) {
				// �����n�Ǝ��Ԃɒ���
				this.endTime.setIntHour(endHour + 1);
				this.endTime.setIntMinute(SystemConstant.MINIMUM_TIME);
			}
			// �[���x�e���Ԓ��ɑގЂ�����
			else if (SystemConstant.REST_HOUR_EVE == endHour
					&& SystemConstant.START_EVE_MINUTE > endMinute) {
				// �[���n�Ǝ��Ԃɒ���
				this.endTime.setIntMinute(SystemConstant.START_EVE_MINUTE);
			}

		}

	}

}
