package Utilities.ActionUtilities;

import java.security.SecureRandom;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Random;

public class ActionUtilities {
	
	public int randomNumber(int min, int max) {
		Random rand = new Random();
		int randomNum = rand.nextInt((max - min) + 1) + min;
		return randomNum;
	}

	public String randomNumberInString(int min, int max) {
		Random rand = new Random();
		int randomNum = rand.nextInt((max - min) + 1) + min;
		String randNum = Integer.toString(randomNum);
		return randNum;
	}

	public String randomNumberInString(int length) {
		String AB = "0123456789";
		SecureRandom rnd = new SecureRandom();
		StringBuilder sb = new StringBuilder(length);
		for (int i = 0; i < length; i++)
			sb.append(AB.charAt(rnd.nextInt(AB.length())));
		return sb.toString();
	}

	public int getRndNumber(int length) {
		Random random = new Random();
		int randomNumber = 0;
		boolean loop = true;
		while (loop) {
			randomNumber = random.nextInt();
			if (Integer.toString(randomNumber).length() == length && !Integer.toString(randomNumber).startsWith("-")) {
				loop = false;
			}
		}
		return randomNumber;
	}

	public String randomString(int length) {
		String AB = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz";
		SecureRandom rnd = new SecureRandom();
		StringBuilder sb = new StringBuilder(length);
		for (int i = 0; i < length; i++)
			sb.append(AB.charAt(rnd.nextInt(AB.length())));
		return sb.toString();
	}

	public String randomStringWithNo(int length) {
		String AB = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz1234567890";
		SecureRandom rnd = new SecureRandom();
		StringBuilder sb = new StringBuilder(length);
		for (int i = 0; i < length; i++)
			sb.append(AB.charAt(rnd.nextInt(AB.length())));
		return sb.toString();
	}

	public boolean isListAll(Iterable<?> list, String contains) {
		for (Object obj : list) {
			if (!contains.equalsIgnoreCase((String) obj))
				return false;
		}
		return true;
	}

	public boolean isListAll(Iterable<?> list, boolean contains) {
		for (Object obj : list) {
			if ((boolean) obj != contains) {
				for (Object oj : list) {
					System.out.println("====" + String.valueOf((boolean) oj));
				}
				return false;
			}
		}
		return true;
	}

	public static boolean checkNextChar(int i, char[] array) {
		try {
			char temp = array[(i + 1)];
			return (temp == '\'') || (temp == '"');
		} catch (Exception e) {
			return false;
		}
	}

	public String getTodayDate(String format) {
		SimpleDateFormat DtFormat = new SimpleDateFormat(format);
		Date date = new Date();
		String dateAndTime = DtFormat.format(date).toString();
		return dateAndTime;
	}
	
	public static String convertXPathContains(String text) {
		if ((!(text.contains("'"))) && (!(text.contains("\"")))) {
			return "'" + text + "'";
		} else {
			char[] array = text.toCharArray();
			List<String> listHoldString = new ArrayList<>();
			StringBuilder holdString = new StringBuilder();
			for (int i = 0; i < array.length; i++) {
				if (array[i] == '"') {
					listHoldString.add(String.valueOf(array[i]));
				} else if (array[i] == '\'') {
					listHoldString.add(String.valueOf(array[i]));
				} else {
					if (checkNextChar(i, array)) {
						holdString.append(array[i]);
						listHoldString.add(holdString.toString());
						holdString = new StringBuilder();
					} else {
						holdString.append(array[i]);
						if (i == (array.length - 1)) {
							listHoldString.add(holdString.toString());
							holdString = new StringBuilder();
						}
					}
				}
			}
			for (int i = 0; i < listHoldString.size(); i++) {
				if (listHoldString.get(i).contains("'")) {
					if (i == (listHoldString.size() - 1)) {
						holdString.append("\"'\"");
					} else {
						holdString.append("\"'\",");
					}

				} else {
					if (i == (listHoldString.size() - 1)) {
						holdString.append("'").append(listHoldString.get(i)).append("'");
					} else {
						holdString.append("'").append(listHoldString.get(i)).append("',");
					}
				}
			}
			holdString = new StringBuilder("concat(" + holdString + ")");
			return holdString.toString();
		}
	}



}
