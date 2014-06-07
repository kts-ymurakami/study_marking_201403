package jp.ktsystem.kadai201403.y_murakami;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import jp.ktsystem.kadai201403.y_murakami.Common.ErrorCode;
import jp.ktsystem.kadai201403.y_murakami.Common.KadaiException;
import jp.ktsystem.kadai201403.y_murakami.Common.SystemConstant;

/**
 * 月の勤務時間を格納するモデル
 *
 * @author y_murakami
 *
 */
public class WorkTimeMonth {

	/**
	 * ""内部取り出しパタン
	 */
	private final Pattern DOUBLE_QUOTE_PATTERN = Pattern
			.compile(SystemConstant.REGULAR_EXPRESSION_DOUBLE_QUOTATION);

	/**
	 * 月
	 */
	private String month;

	/**
	 * 月のデータ
	 */
	private String inputData;

	/**
	 * 日のモデル　WorkTime
	 */
	private List<WorkTime> workTimeList = new ArrayList<WorkTime>() {};

	/**
	 * エラーコード
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
	 * コンストラクタ
	 *
	 * @param month
	 *            日付
	 */
	public WorkTimeMonth(String month, String inputData) {
		this.month = month;
		this.inputData = inputData;
	}

	/**
	 * 読み込みデータメンバからKadaiクラスのparseWorkTimeDataを使用するための
	 * ファイルを作成する。
	 * @throws KadaiException
	 */
	public void scanInputData() throws KadaiException{
		Matcher dataMatcher = this.DOUBLE_QUOTE_PATTERN.matcher(this.inputData);

		int count = 0;// find回数
		String date = "";
		String start = "";
		String end = "";

		// カラム数不正チェック
		int check = 0;
		while(dataMatcher.find()){
			check++;
		}
		if(0 != check % 5){
			this.errorCode = ErrorCode.INPUT_FILE_ERROR.getErrorCode();
		}

		// 初期化
		dataMatcher = this.DOUBLE_QUOTE_PATTERN.matcher(this.inputData);

		while (dataMatcher.find()) {
			String str = dataMatcher.group(1);//""の中身
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

			// SWITCH文の処理でエラーコードが設定された場合
			if(0 < this.errorCode){
				break;
			}

			if(count != 0 && 4 == subIndex){
				String workTime = "";
				try {
					workTime = Kadai.calcWorkTime(start, end);
				} catch (KadaiException ex) {
					// errorCodeを設定して終了
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
	 * 日のデータを追加
	 *
	 * @param workTime
	 */
	public void addDate(WorkTime workTime) {
		if (null != workTime) {
			workTimeList.add(workTime);
		}
	}

}
