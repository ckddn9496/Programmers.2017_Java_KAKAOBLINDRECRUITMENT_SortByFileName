import java.util.Arrays;
import java.util.Comparator;
import java.util.LinkedList;
import java.util.List;

public class Main {

	public static void main(String[] args) {
//		String[] files = { "img12.png", "img10.png", "img02.png", "img1.png", "IMG01.GIF", "img2.JPG" };
//		// return [img1.png, IMG01.GIF, img02.png, img2.JPG, img10.png, img12.png]

		String[] files = {"F-5 Freedom Fighter", "B-50 Superfortress", "A-10 Thunderbolt II", "F-14 Tomcat"};
		// return [A-10 Thunderbolt II, B-50 Superfortress, F-5 Freedom Fighter, F-14 Tomcat]

		System.out.println(Arrays.toString(new Solution().solution(files)));
	}

}

class Solution {

	/**
	 * 1) class Filename : filename, HEAD, NUMBER, ORDER(들어온 순서) 2) List<Filename>
	 * init 3) Comparator<Filename> compByFilename
	 */

	class Filename {
		private String filename;
		private String head;
		private int number;

		public Filename(String filename) {
			this.filename = filename;
			setHeadAndNumber();
		}

		private void setHeadAndNumber() {
			int numberStartIdx = 0;
			for (int i = 0; i < this.filename.length(); i++) {
				if (Character.isDigit(this.filename.charAt(i))) {
					numberStartIdx = i;
					break;
				}
			}
			this.head = this.filename.substring(0, numberStartIdx);

			String numberStr = "";
			for (int i = numberStartIdx; i < this.filename.length(); i++) {
				if (Character.isDigit(this.filename.charAt(i)) && numberStr.length() < 5) {
					numberStr += this.filename.charAt(i);
				} else {
					break;
				}
			}
			
			this.number = Integer.parseInt(numberStr);
		}

		public String getFilename() {
			return filename;
		}

		public String getHead() {
			return head;
		}

		public int getNumber() {
			return number;
		}


		public String toString() {
			return "filename: " + this.filename + "\nhead: " + this.head + "\tnumber: " + this.number + "\n";
		}
	}

	private Comparator<Filename> compByFilename = new Comparator<Filename>() {

		@Override
		public int compare(Filename f1, Filename f2) {
			return f1.getHead().equalsIgnoreCase(f2.getHead()) ? f1.getNumber() - f2.getNumber() : f1.getHead().compareToIgnoreCase(f2.getHead());
		}
	};

	public String[] solution(String[] files) {
		String[] answer = new String[files.length];
		List<Filename> list = new LinkedList<>();

		for (int idx = 0; idx < files.length; idx++)
			list.add(new Filename(files[idx]));

//		System.out.println(list);
		list.sort(compByFilename);
//		System.out.println(list);

		int idx = 0;
		for (Filename filename : list) {
			answer[idx++] = filename.getFilename();
		}

		return answer;
	}
}