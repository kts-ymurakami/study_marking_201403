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

import jp.ktsystem.kadai201403.y_murakami.common.ErrorCode;
import jp.ktsystem.kadai201403.y_murakami.common.KadaiException;
import jp.ktsystem.kadai201403.y_murakami.common.SystemConstant;
import jp.ktsystem.kadai201403.y_murakami.util.CommonUtil;
import jp.ktsystem.kadai201403.y_murakami.util.ErrorUtil;

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
			.compile(SystemConstant.REGULAR_EXPRESSION_DATA);

	/**
	 * ���̓t�@�C���p�X
	 */
	private String inputFilePath = null;

	/**
	 * �o�̓t�@�C���p�X
	 */
	private String outputFilePath = null;

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
	private List<WorkTimeMonth> WorkTimeMonthList = new ArrayList<WorkTimeMonth>();

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
			boolean bomCheck = true;

			// ���̓t�@�C���ǂݍ��݁@�i����R�[�h�G���[���o���炻���őł��؂�j
			while (-1 != (readChar = bfRead.read())) {

				// BOM�ǂݔ�΂�
				if (bomCheck && 65533 == readChar) {
					if (!CommonUtil.checkBom(bfRead)) {
						this.controlCodeError = true;
						break;
					}
					readChar = bfRead.read();
				}
				bomCheck = false;

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
	 * �t�@�C���ǂݍ��݂��s���A�o�͕����񃊃X�g���쐬����@
	 *
	 * @throws KadaiException
	 */
	public void readInputFileLv2() throws KadaiException {

		BufferedReader bfRead = null;
		String month = null;// ��

		// ���̓t�@�C���̓ǂݍ���
		try {
			// �t�@�C���I�[�v��
			bfRead = new BufferedReader(new InputStreamReader(
					new FileInputStream(this.inputFilePath)));

			StringBuilder sb = new StringBuilder();// �L�[or�o�����[

			// ����Json�R���g���[������
			int jsonControlChar = '{';

			// �ǂݍ���
			int readChar = scanInputChar(bfRead, false, true);
			boolean skipFlag = false;
			while (-1 != readChar) {
				// Json���䕶���������ꍇ
				if (readChar != jsonControlChar) {
					if (':' == jsonControlChar || ']' == jsonControlChar) {
						// �ǉ�
						sb.append((char) readChar);
						if (readChar == '"') {
							// "�ȍ~�͕�����ǂݍ���
							skipFlag = !skipFlag;
						}
						readChar = scanInputChar(bfRead, skipFlag,false);
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
					month = sb.toString().replace('"', ' ').trim();
					sb = new StringBuilder();// �@������
					jsonControlChar = '[';
				} else if ('[' == jsonControlChar) {
					jsonControlChar = ']';
				} else if (']' == jsonControlChar) {

					// �ꂩ�����̃f�[�^����������
					WorkTimeMonth monthData = new WorkTimeMonth(month,
							sb.toString());
					monthData.scanInputData();// �f�[�^�̓ǂݍ���

					// �ǂݍ��ݍς݃f�[�^�ɒǉ�
					this.WorkTimeMonthList.add(monthData);

					sb = new StringBuilder();

					// ���̃f�[�^��ǂ�
					readChar = scanInputChar(bfRead, false,false);
					if (',' == readChar) {
						// ���f�[�^������ꍇ
						jsonControlChar = ':';
					} else if ('}' == readChar) {
						// 1���R�[�h�ǂݍ��ݏI��
						jsonControlChar = '{';
					} else {
						throw new KadaiException(ErrorCode.ILLEGAL_INPUT_TIME);
					}
				} else {
					throw new KadaiException(ErrorCode.ILLEGAL_INPUT_TIME);
				}

				// ���̕�����ǂݍ���
				readChar = scanInputChar(bfRead, false, false);
			}
		} catch (FileNotFoundException ex) {
			// ���̓t�@�C�������݂��Ă��鎞
			File f = new File(this.inputFilePath);
			if (f.exists()) {
				throw new KadaiException(ErrorCode.FAILE_READ_INPUT_FILE);
			}
			throw new KadaiException(ErrorCode.NOT_EXIST_INPUT_FILE);
		} catch (KadaiException ex) {
			throw ex;// ����R�[�h
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
			bfWrite = new BufferedWriter(new FileWriter(this.outputFilePath));
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
	 * Lv2�p�t�@�C�������o��
	 *
	 * @throws KadaiException
	 */
	public void writeOutPutFileLv2(String dirPath) throws KadaiException {

		for (WorkTimeMonth workTimeMonth : this.WorkTimeMonthList) {

			BufferedWriter bfWrite = null;
			String month = workTimeMonth.getMonth();
			String fullPath = String.format("%s//%s.txt", dirPath, month);// �������݃p�X
			int errorCode = workTimeMonth.getErrorCode();// errorCode�̎擾

			try {

				// �f�B���N�g�������݂��Ȃ��ꍇ�͍쐬����
				File dir = new File(dirPath);
				if (!dir.exists()) {
					dir.mkdirs();
				}

				bfWrite = new BufferedWriter(new FileWriter(fullPath));

				// �o�͗p�����񃊃X�g�̍쐬
				ArrayList<String> outList = new ArrayList<String>();
				List<WorkTime> workTimeList = workTimeMonth.getWorkTimeList();

				// ���t���Ń\�[�g
				for (int i = 0; i < workTimeList.size() - 1; ++i) {
					WorkTime wt1 = workTimeList.get(i);
					WorkTime wt2 = workTimeList.get(i + 1);
					if (0 < wt1.getDate().compareTo(wt2.getDate())) {
						WorkTime wtTemp = wt1;
						workTimeList.set(i, wt2);
						workTimeList.set(i + 1, wtTemp);
					}
				}

				// �o�͕�����̍쐬
				int total = 0;
				outList.add("[");
				for (int i = 0; i < workTimeList.size(); ++i) {

					// total��ݒ肷��
					WorkTime wt = workTimeList.get(i);
					int workTime = Integer.parseInt(wt.getWorkTime());
					wt.setTotal(String.valueOf(total += workTime));

					// ��������Json������̍쐬
					StringBuilder line = new StringBuilder(workTimeList.get(i)
							.createJsonStr());
					if (workTimeList.size() - 1 != i) {
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
			// error�����݂�����I��
			if (0 < errorCode) {
				break;
			}
		}
	}

	/**
	 * ���̕�����ǂ�
	 *
	 * @param bfRead
	 *            �t�@�C�����[�_�[
	 * @param skipFlag
	 *            �s�v������ǂݔ�΂����ǂ����@
	 * @return
	 * @throws KadaiException
	 * @throws IOException
	 */
	private int scanInputChar(BufferedReader bfRead, boolean skipFlag,
			boolean bomCheck) throws KadaiException, IOException {

		int readChar = bfRead.read(); // �ǂݍ���
		if (skipFlag) {
			// ����R�[�h�`�F�b�N
			if (ErrorUtil.isControlCode((char) readChar)) {
				throw new KadaiException(ErrorCode.ILLEGAL_INPUT_TIME);
			}
			return readChar;
		} else {
			// �󔒂�ǂݔ�΂�
			while (-1 != readChar) {
				// ����̂�BOM�`�F�b�N
				if (bomCheck && 65533 == readChar) {
					if (!CommonUtil.checkBom(bfRead)) {
						throw new KadaiException(
								ErrorCode.IS_CTRL_CODE);
					}
					readChar = bfRead.read();
				}
				// �󔒃R�[�h�`�F�b�N
				if (!CommonUtil.isSkipCode(readChar)) {
					// ����R�[�h�`�F�b�N
					if (ErrorUtil.isControlCode((char) readChar)) {
						throw new KadaiException(ErrorCode.IS_CTRL_CODE);
					}
					return readChar;
				}
				readChar = bfRead.read();
			}
			return readChar;
		}
	}
}
