package jp.ktsystem.kadai201403.y_murakami;

import java.util.ArrayList;

import jp.ktsystem.kadai201403.y_murakami.Common.KadaiException;

public class test {

	public static void main(String[] args) {

		/*
		ArrayList<String> inputFile = new ArrayList<String>(){{
			add("testFileInput\\case6.txt");
			add("testFileInput\\case7.txt");
			add("testFileInput\\case8.txt");
			add("testFileInput\\case9.txt");
			add("testFileInput\\case10.txt");
			add("testFileInput\\case11.txt");
		}};
		String outFilePath = "testFileOut//";

		int i=0;
		for(String file : inputFile){
			try {
				String input = file;
				String output = outFilePath + i + ".txt";
				Kadai.parseWorkTimeData(input, output);
			} catch (KadaiException ex) {
				System.out.println(ex.getErrorCode());
			}catch(Exception ex){
				System.out.println("êßå‰Ç≈Ç´ÇƒÇ¢Ç»Ç¢");
			}
			++i;
		}
		*/
		ArrayList<String> inputFile = new ArrayList<String>(){{
			add("testFileInput\\test2.txt");
		}};
		String outFilePath = "testFileOut//";

		int i=0;
		for(String file : inputFile){
			try {
				String input = file;
				String output = outFilePath + i + ".txt";
				Kadai.parseWorkTimeDataLv2(input, output);
			} catch (KadaiException ex) {
				System.out.println(ex.getErrorCode());
			}catch(Exception ex){
				System.out.println("êßå‰Ç≈Ç´ÇƒÇ¢Ç»Ç¢");
			}
			++i;
		}


	}

}
