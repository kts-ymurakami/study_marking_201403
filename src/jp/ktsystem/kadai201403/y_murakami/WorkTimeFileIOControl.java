package jp.ktsystem.kadai201403.y_murakami;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import jp.ktsystem.kadai201403.y_murakami.Common.ErrorCode;
import jp.ktsystem.kadai201403.y_murakami.Common.KadaiException;
import jp.ktsystem.kadai201403.y_murakami.Common.SystemConstant;
import jp.ktsystem.kadai201403.y_murakami.Util.CommonUtil;
import jp.ktsystem.kadai201403.y_murakami.Util.ErrorUtil;

/**
 * �Ζ����Ԃ̃t�@�C�����o�͂𐧌䂷��N���X
 *
 * @author y_murakami
 */
public class WorkTimeFileIOControl {

	/**
	 * ���̓`�F�b�N�p�@���K�\��Pattern
	 */
	private final Pattern SUBSTR_FILE_PATTERN = Pattern
			.compile(SystemConstant.REGULAR_EXPRESSION_STR_INPUT_FILE);

	/**
	 * ���̓`�F�b�N�p�@���K�\��Pattern
	 */
	private final Pattern DATA_PATTERN = Pattern
			.compile(SystemConstant.REGULAR_EXPRESSION_DATE);

	/**
	 * ���̓t�@�C���p�X
	 */
	private String inputFilePath = null;

	/**
	 * �o�̓t�@�C���p�X
	 */
	private String outputFilePath = null;

	/**
	 * �o�̓t�@�C���������ݗp�����񃊃X�g
	 */
	private ArrayList<String[]> outputList = null;

	/**
	 * �ǂݍ��݃t�@�C��������
	 */
	private StringBuilder readStr = null;

	/**
	 * �ǂݍ��݃f�[�^�̃��X�g
	 */
	private ArrayList<WorkTime> workTimeList = null;

	/**
	 * ����R�[�h�̃G���[�����������ǂ���
	 */
	private boolean controlCodeError = false;

	/**
	 * �ǂݍ��݃f�[�^�̃��X�g�i���x���Q�j
	 */
	private List<WorkTimeMonth> WorkTimeMonthList = null;

	/**
	 * �R���X�g���N�^
	 *
	 * @param inputFilePath
	 *            ���̓t�@�C���p�X
	 * @param outputFilePath
	 *            �o�̓t�@�C���p�X
	 * @throws KadaiException
	 *             �ۑ��O
	 */
	public WorkTimeFileIOControl(String inputFilePath, String outputFilePath)
			throws KadaiException {

		// ���̓t�@�C���p�X��null�A�������͋󕶎��̎�
		if (!ErrorUtil.checkNullOrEmpty(inputFilePath)) {
			throw new KadaiException(ErrorCode.NULL_INPUT_FILE_PATH);
		}

		// �o�̓t�@�C���p�X��null�������͋󕶎��̎�
		if (!ErrorUtil.checkNullOrEmpty(outputFilePath)) {
			throw new KadaiException(ErrorCode.NULL_OUTPUT_FILE_PATH);
		}

		// �t�@�C���p�X�������o�ɐݒ�
		this.inputFilePath = inputFilePath;
		this.outputFilePath = outputFilePath;

	}

	/**
	 * �t�@�C���ǂݍ��݂��s���A�o�͕����񃊃X�g���쐬����
	 *
	 * @throws KadaiException
	 */
	public void readInputFile() throws KadaiException {

		// ���̓t�@�C���ǂݍ��ݗp�ϐ�
		BufferedReader bfRead = null;

		try {
			// �t�@�C���I�[�v��
			bfRead = new BufferedReader(new InputStreamReader(
					new FileInputStream(this.inputFilePath)));

			int readChar;// �ǂݍ��ݕ���
			this.readStr = new StringBuilder();// �ǂݍ��ݕ�����

			// ���̓t�@�C���ǂݍ��݁@�i����R�[�h�G���[���o���炻���őł��؂�j
			while (-1 != (readChar = bfRead.read())) {
				// ���䕶���G���[�`�F�b�N
				if (ErrorUtil.isControlCode((char) readChar)) {
					this.controlCodeError = true;
					break;
				}
				this.readStr.append((char) readChar);
			}

			// Matcher����͕����񂩂琶�� �u{�v�ł͂��܂��āu�p�v�ŏI��镔�����擾
			Matcher inputMatcher = this.SUBSTR_FILE_PATTERN
					.matcher(this.readStr.toString());
			ArrayList<String> dataList = new ArrayList<String>();
			while (inputMatcher.find()) {
				dataList.add(inputMatcher.group());
			}

			// workTimeList�𐶐�
			this.workTimeList = new ArrayList<WorkTime>();
			int sumWorkTime = 0;// ���Ζ�����

			// �擾�f�[�^����Adate,start,end�𒊏o����
			for (String data : dataList) {

				String date = null;
				String start = null;
				String end = null;

				Matcher dataMatcher = this.DATA_PATTERN.matcher(data);

				int count = 0;// find��
				while (dataMatcher.find()) {
					count++;
					String key = dataMatcher.group(1);
					String value = dataMatcher.group(2);
					// key�ɂ��ꍇ����
					if (SystemConstant.KEY_DATE.equals(key)) {
						date = value;
						if (!ErrorUtil.checkDate(date)) {
							throw new KadaiException(
									ErrorCode.ILLEGAL_INPUT_TIME);
						}
					} else if (SystemConstant.KEY_START.equals(key)) {
						start = value;
					} else if (SystemConstant.KEY_END.equals(key)) {
						end = value;
					} else {
						throw new KadaiException(ErrorCode.ILLEGAL_INPUT_TIME);
					}
				}

				// ���������L�[�̐���3�ȊO�Ȃ�G���[
				if (3 != count) {
					throw new KadaiException(ErrorCode.INPUT_FILE_ERROR);
				}

				// �Ζ����ԁ@�G���[������ꍇ�͂����ł�KadaiException���X���[�����
				String workTime = Kadai.calcWorkTime(start, end);
				// ���Ζ�����
				sumWorkTime += Integer.parseInt(workTime);

				// workTime�I�u�W�F�N�g�𐶐����ďo�͗p�Ƀ��X�g�֒ǉ�
				WorkTime workTimeModel = new WorkTime(date, workTime,
						String.valueOf(sumWorkTime));
				this.workTimeList.add(workTimeModel);
			}

		} catch (FileNotFoundException ex) {
			// ���̓t�@�C�������݂��Ă��鎞
			File f = new File(this.inputFilePath);
			if (f.exists()) {
				throw new KadaiException(ErrorCode.FAILE_READ_INPUT_FILE);
			}
			throw new KadaiException(ErrorCode.NOT_EXIST_INPUT_FILE);
		} catch (KadaiException ex) {
			throw ex; // ���݂��Ȃ��L�[�A����R�[�h
		} catch (Exception ex) {
			// �t�@�C���ǂݍ��݂Ɏ��s������
			throw new KadaiException(ErrorCode.FAILE_READ_INPUT_FILE);
		} finally {
			// �t�@�C���N���[�Y
			if (null != bfRead) {
				try {
					bfRead.close();
				} catch (IOException ex) {
					throw new KadaiException(ErrorCode.FAILE_READ_INPUT_FILE);
				}
			}
		}

		// ����R�[�h�ł̃G���[���������ꍇ
		if (this.controlCodeError) {
			throw new KadaiException(ErrorCode.IS_CTRL_CODE);
		}

	}

	/**
	 * �t�@�C���ǂݍ��݂��s���A�o�͕����񃊃X�g���쐬����@TODO
	 *
	 * @throws KadaiException
	 */
	public void readInputFileLv2() throws KadaiException {

		BufferedReader bfRead = null;
		// ���f�[�^�̕ێ�
		List<WorkTime> workTimeMonth = new ArrayList<WorkTime>();

		// ���̓t�@�C���̓ǂݍ���
		try {
			// �t�@�C���I�[�v��
			bfRead = new BufferedReader(new InputStreamReader(
					new FileInputStream(this.inputFilePath)));

			StringBuilder sb = new StringBuilder();// �L�[or�o�����[
			String month = null;// ��

			// ����Json�R���g���[������
			int jsonControlChar = '{';

			// �ǂݍ���
			int ch = scanInputChar(bfRead, false);
			boolean skipFlag = false;

			while (-1 != ch) {
				// Json���䕶���������ꍇ
				if (ch != jsonControlChar) {
					if (':' == jsonControlChar || ']' == jsonControlChar) {
						// �ǉ�
						sb.append((char) ch);
						if (ch == '"') {
							// "�ȍ~�͕�����ǂݍ���
							skipFlag = !skipFlag;
						}
						ch = scanInputChar(bfRead, skipFlag);
						continue;// �ǂݍ���Ŏ��̕�����
					} else {
						throw new KadaiException(ErrorCode.ILLEGAL_INPUT_TIME);
					}
				}

				// Json���䕶���ɂ�镪��
				if ('{' == jsonControlChar) {
					jsonControlChar = ':';
				} else if (':' == jsonControlChar) {
					// �����擾
					month = sb.toString();// TODO
					sb = new StringBuilder();// �@������
					jsonControlChar = '[';
				} else if ('[' == jsonControlChar) {
					jsonControlChar = ']';
				} else if (']' == jsonControlChar) {

					WorkTimeMonth monthData = new WorkTimeMonth(month);

					String date = null;
					String workTime = null;
					String total = null;

					WorkTime dateData = new WorkTime(date, workTime, total);
					monthData.addDate(dateData);
					this.WorkTimeMonthList.add(monthData);

					System.out.println(sb.toString());

					sb = new StringBuilder();

					// ���̃f�[�^��ǂ�
					ch = scanInputChar(bfRead, false);
					if (',' == ch) {
						// ���f�[�^������ꍇ
						jsonControlChar = ':';
					} else if ('}' == ch) {
						// 1���R�[�h�ǂݍ��ݏI��
						jsonControlChar = '{';
					} else {
						throw new KadaiException(ErrorCode.ILLEGAL_INPUT_TIME);
					}
				} else {
					throw new KadaiException(ErrorCode.ILLEGAL_INPUT_TIME);
				}

				// ���̕�����ǂݍ���
				ch = scanInputChar(bfRead, false);
			}
		} catch (FileNotFoundException ex) {
			// ���̓t�@�C�������݂��Ă��鎞
			File f = new File(this.inputFilePath);
			if (f.exists()) {
				throw new KadaiException(ErrorCode.FAILE_READ_INPUT_FILE);
			}
			throw new KadaiException(ErrorCode.NOT_EXIST_INPUT_FILE);
		} catch (KadaiException ex) {
			throw ex; // ���݂��Ȃ��L�[�A����R�[�h
		} catch (Exception ex) {
			// �t�@�C���ǂݍ��݂Ɏ��s������
			throw new KadaiException(ErrorCode.FAILE_READ_INPUT_FILE);
		} finally {
			// �t�@�C���N���[�Y
			if (null != bfRead) {
				try {
					bfRead.close();
				} catch (IOException ex) {
					throw new KadaiException(ErrorCode.FAILE_READ_INPUT_FILE);
				}
			}
		}
	}

	/**
	 * �ǂݍ��ݓ��e�̏����o��
	 *
	 * @param errorCode
	 *            �ǂݍ��ݎ��ɃG���[���������ꍇ�̃G���[�R�[�h(�K�v�Ȃ��ꍇ��-1��ݒ肵�Ă�������)
	 * @throws KadaiException
	 */
	public void writeOutputFile(int errorCode) throws KadaiException {

		if (null == this.workTimeList) {
			return;
		}

		BufferedWriter bfWrite = null;// �o�̓t�@�C������p�ϐ�

		try {
			// �t�@�C�������݂��邩�ǂ���
			if (checkExistFile(this.outputFilePath)) {
				// ���݂��Ă���ꍇ�͒ǋL���[�h�ŃI�[�v��
				bfWrite = new BufferedWriter(new FileWriter(
						this.outputFilePath, true));
			} else {
				// ���݂��Ȃ��ꍇ�͐V�K�쐬
				bfWrite = new BufferedWriter(
						new FileWriter(this.outputFilePath));
			}

			// �o�͗p�����񃊃X�g�̍쐬
			ArrayList<String> outList = new ArrayList<String>();
			outList.add("[");
			for (int i = 0; i < this.workTimeList.size(); ++i) {
				StringBuilder line = new StringBuilder(this.workTimeList.get(i)
						.createJsonStr());
				if (this.workTimeList.size() - 1 != i) {
					line.append(",");
				}
				outList.add(line.toString());
			}
			outList.add("]");

			// �o��
			for (String line : outList) {
				// ��������
				bfWrite.write(line);
				bfWrite.newLine();
			}

			// �ǂݍ��ݎ��G���[�R�[�h�̏�������(6,7,8�͏o�̓t�@�C�����̂����݂��Ȃ����ߋL�q���Ȃ�)
			if (-1 != errorCode && errorCode != 6 && errorCode != 7
					&& errorCode != 8) {
				bfWrite.write(String.valueOf(errorCode));
				bfWrite.newLine();
			}

		} catch (Exception ex) {
			// �o�͎��s
			throw new KadaiException(ErrorCode.FAILE_FILE_OUT);
		} finally {
			// �t�@�C���N���[�Y
			if (null != bfWrite) {
				try {
					bfWrite.close();
				} catch (IOException ex) {
					throw new KadaiException(ErrorCode.FAILE_FILE_OUT);
				}
			}
		}
	}


	/**
	 * ���̕�����ǂ�
	 * @param bfRead�@�t�@�C�����[�_�[
	 * @param skipFlag�@�s�v������ǂݔ�΂����ǂ����@
	 * @return
	 * @throws KadaiException
	 * @throws IOException
	 */
	private int scanInputChar(BufferedReader bfRead, boolean skipFlag)
			throws KadaiException, IOException {

		int ch = bfRead.read(); // �ǂݍ���
		if (skipFlag) {
			// ����R�[�h�`�F�b�N
			if (ErrorUtil.isControlCode((char) ch)) {
				throw new KadaiException(ErrorCode.ILLEGAL_INPUT_TIME);
			}
			return ch;
		} else {
			// �󔒂�ǂݔ�΂�
			while (-1 != ch) {
				// �󔒃R�[�h�`�F�b�N
				if (!CommonUtil.isSkipCode(ch)) {
					// ����R�[�h�`�F�b�N
					if (ErrorUtil.isControlCode((char) ch)) {
						throw new KadaiException(ErrorCode.ILLEGAL_INPUT_TIME);
					}
					return ch;
				}
				ch = bfRead.read();
			}
			return ch;
		}
	}

	/**
	 * �t�@�C�������݂��Ă��邩�ǂ���
	 *
	 * @param filePath
	 * @return true/false
	 */
	private boolean checkExistFile(String filePath) {

		File f = new File(filePath);

		if (f.exists()) {
			return true;
		} else {
			return false;
		}
	}

}
