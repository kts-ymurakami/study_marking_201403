package test.jp.ktsystem.kadai201403.y_murakami;

import java.util.ArrayList;

import jp.ktsystem.kadai201403.y_murakami.Kadai;
import jp.ktsystem.kadai201403.y_murakami.common.KadaiException;

public class test {

	public static void main(String[] args) {

		ArrayList<String> inputFile = new ArrayList<String>(){{
			add("testInput\\ValidCase14.txt");
			add("testInput\\ValidCase15.txt");
			add("testInput\\ValidCase16.txt");
			add("testInput\\ValidCase17.txt");
			add("testInput\\ValidCase18.txt");
			add("testInput\\ValidCase24.txt");
			add("testInput\\ValidCase25.txt");
			add("testInput\\ValidCase26.txt");
			add("testInput\\validCaseLv2Bom.txt");
			add("testInput\\validCaseLv2NoBom.txt");
		}};

		ArrayList<String> outDirPath = new ArrayList<String>(){{
			add("testOutput\\case14");
			add("testOutput\\case15");
			add("testOutput\\case16");
			add("testOutput\\case17");
			add("testOutput\\case18");
			add("testOutput\\case24");
			add("testOutput\\case25");
			add("testOutput\\case26");
			add("testOutput\\caseBom");
			add("testOutput\\caseNoBom");
		}};

		int i=0;
		for(String file : inputFile){
			try {
				String input = file;
				Kadai.parseWorkTimeDataLv2(input, outDirPath.get(i));
			} catch (KadaiException ex) {
				System.out.println(ex.getErrorCode());
			}catch(Exception ex){
				System.out.println("予期せぬエラー");
			}
			i++;
		}


	}

}
