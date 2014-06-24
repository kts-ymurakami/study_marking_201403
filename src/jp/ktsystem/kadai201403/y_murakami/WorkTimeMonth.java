package jp.ktsystem.kadai201403.y_murakami;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import jp.ktsystem.kadai201403.y_murakami.common.ErrorCode;
import jp.ktsystem.kadai201403.y_murakami.common.KadaiException;
import jp.ktsystem.kadai201403.y_murakami.common.SystemConstant;

/**
 * ���̋Ζ����Ԃ��i�[���郂�f��
 *
 * @author y_murakami
 *
 */
public class WorkTimeMonth {

	/**
	 * ""�������o���p�^��
	 */
	private final Pattern DOUBLE_QUOTE_PATTERN = Pattern
			.compile(SystemConstant.REGULAR_EXPRESSION_DOUBLE_QUOTATION);

	/**
	 * ��
	 */
	private String month;

	/**
	 * ���̃f�[�^
	 */
	private String inputData;

	/**
	 * ���̃��f���@WorkTime
	 */
	private List<WorkTime> workTimeList = new ArrayList<WorkTime>() {};

	/**
	 * �G���[�R�[�h
	 */
	private int errorCode = -1;


	public String getMonth(){
		return this.month;
	}

	public List<WorkTime> getWorkTimeList(){
		return this.workTimeList;
	}

	public int getErrorCode(){
		return this.errorCode;
	}

	/**
	 * �R���X�g���N�^
	 *
	 * @param month
	 *            ���t
	 */
	public WorkTimeMonth(String month, String inputData) {
		this.month = month;
		this.inputData = inputData;
	}

	/**
	 * �ǂݍ��݃f�[�^�����o����Kadai�N���X��parseWorkTimeData���g�p���邽�߂�
	 * �t�@�C�����쐬����B
	 * @throws KadaiException
	 */
	public void scanInputData() throws KadaiException{
		Matcher dataMatcher = this.DOUBLE_QUOTE_PATTERN.matcher(this.inputData);

		int count = 0;// find��
		String date = "";
		String start = "";
		String end = "";

		// �J�������s���`�F�b�N
		int check = 0;
		while(dataMatcher.find()){
			check++;
		}
		if(0 != check % 5){
			this.errorCode = ErrorCode.INPUT_FILE_ERROR.getErrorCode();
		}

		// ������
		dataMatcher = this.DOUBLE_QUOTE_PATTERN.matcher(this.inputData);

		while (dataMatcher.find()) {
			String str = dataMatcher.group(1);//""�̒��g
			int subIndex = count % 5;

			switch(subIndex){
			case 0:
				date = str;
				break;
			case 1:
				if(!SystemConstant.KEY_START.equals(str)){
				this.errorCode = ErrorCode.ILLEGAL_INPUT_TIME.getErrorCode();
				}
				break;
			case 2:
				start = str;
				break;
			case 3:
				if(!SystemConstant.KEY_END.equals(str)){
					this.errorCode = ErrorCode.ILLEGAL_INPUT_TIME.getErrorCode();
				}
				break;
			case 4:
				end = str;
				break;
			default:
				this.errorCode = ErrorCode.ILLEGAL_INPUT_TIME.getErrorCode();
				break;
			}

			// SWITCH���̏����ŃG���[�R�[�h���ݒ肳�ꂽ�ꍇ
			if(0 < this.errorCode){
				break;
			}

			if(count != 0 && 4 == subIndex){
				String workTime = "";
				try {
					workTime = Kadai.calcWorkTime(start, end);
				} catch (KadaiException ex) {
					// errorCode��ݒ肵�ďI��
					this.errorCode = ex.getErrorCode();
					break;
				}
				WorkTime workTimeDate = new WorkTime(date, workTime);
				this.workTimeList.add(workTimeDate);
			}

			count++;
		}
	}


	/**
	 * ���̃f�[�^��ǉ�
	 *
	 * @param workTime
	 */
	public void addDate(WorkTime workTime) {
		if (null != workTime) {
			workTimeList.add(workTime);
		}
	}

}
