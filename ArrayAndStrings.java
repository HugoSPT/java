import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;


public class ArrayAndStrings {

	public static void main(String[] args){
		ArrayList<Interval> list = new ArrayList<Interval>(4);
		list.add(new Interval(1,5));
		list.add(new Interval(10,15));
		list.add(new Interval(20,25));
		list.add(new Interval(12,27));

		unionDisjoint(list);

		for(int i = 0; i < list.size(); i++)
			System.out.println(list.get(i).getMin() + " " + list.get(i).getMax() + " ");
	}


	public static void unionDisjoint(ArrayList<Interval> list){
		int n = list.size() - 1;
		//ORDERING THE ARRAY USING HEAPSORT
		buildHeap(list);
		for(int i = n ; i > 0; i--){
			Collections.swap(list, 0, i);
			n = n - 1;
			maxParents(list, n, 0);
		}

		//CROSS THE ARRAY TO GATHER DISJOINT INTERVALS
		Interval newRange;
		boolean found;
		int j;
		for(int i = 0; i < list.size() && list.get(i) != null; i++){
			found = true;
			j = i + 1;
			while(found && j < list.size()){
				if(list.get(j).getMin() < list.get(i).getMax()){
					if(list.get(j).getMax() < list.get(i).getMax()){
						newRange = new Interval(list.get(i).getMin(), list.get(i).getMax());
					} else {
						newRange = new Interval(list.get(i).getMin(), list.get(j).getMax());
					}
					list.remove(j);
					list.remove(i);
					list.add(i, newRange);
				} else{
					found = false;
					break;
				}

			}
		}
	}

	private static void buildHeap(ArrayList<Interval> list){
		int n = list.size() - 1;
		for(int i = list.size()/2; i >= 0; i--){
			maxParents(list, n, i);

		}
	}

	private static void maxParents(ArrayList<Interval> list, int n, int i){
		int left = 2*i + 1;
		int right = 2 * i + 2;
		int largest = i;
		if(left <= n && list.get(left).getMin() > list.get(largest).getMin())
			largest = left;
		if(right <= n && list.get(right).getMin() > list.get(largest).getMin())
			largest = right;
		if(largest != i){
			Collections.swap(list, i, largest);
			maxParents(list, n, largest);
		}

	}
	public static String findNonRepeatedUnicode16(String str){
		HashMap<Character, Integer> counter = new HashMap<Character, Integer>();
		for(int i = 0; i < str.length(); i++){
			if(!counter.containsKey(str.charAt(i))){
				counter.put(str.charAt(i), 1);
			} else
				counter.put(str.charAt(i), counter.get(str.charAt(i) + 1));
		}

		for(int i = 0; i < str.length(); i++){
			if(counter.get(str.charAt(i)) == 1)
				return Character.toString(str.charAt(i));
		}
		return null;
	}

	public static String findNonRepeatedUnicode32(String str){
		HashMap<Integer, Object> counter = new HashMap<Integer,Object>();
		Object seenOnce = new Object(), seenMultiple = new Object();
		Object seen;
		int i;
		for(i = 0; i < str.length();){
			final int chr = str.codePointAt(i);
			i += Character.charCount(chr);
			seen = counter.get(chr);
			if(seen == null){
				counter.put(chr, seenOnce);
			} else{
				if(seen == seenOnce){
					counter.put(chr, seenMultiple);
				}
			}
		}

		for(i = 0; i < str.length();){
			final int chr = str.codePointAt(i);
			i += Character.charCount(chr);
			if(counter.get(chr) == seenOnce)
				return new String(Character.toChars(chr));
		}
		return null;

	}

	public static String remove(String str, String remove){
		char[] strArray = str.toCharArray();
		char[] removeArray = remove.toCharArray();

		int src, dst = 0;

		boolean[] flags = new boolean[128];

		for(src = 0; src < removeArray.length; ++src){
			flags[removeArray[src]] = true;
		}

		for(src = 0; src < strArray.length; ++src){
			if(!flags[strArray[src]]){
				strArray[dst++] = strArray[src];
			}
		}
		return new String(strArray, 0, dst);
	}

	public static String reverseWords(String str){
		char[] strArray = str.toCharArray();
		char[] reverse = new char[str.length()];
		int dst = reverse.length-1;
		for(int i = 0; i < str.length(); i++){
			reverse[dst] = strArray[i];
			dst--;
		}

		int start, end = 0;
		while(end < reverse.length){
			if(reverse[end] != ' '){
				start = end;
				while(end < reverse.length && reverse[end] != ' '){
					end++;
				}
				end--;
				switchString(reverse, start, end);
			}
			end++;
		}
		return new String(reverse, 0, reverse.length);
	}

	private static void switchString(char[] str, int start, int end){
		char temp;
		while(end > start){
			temp = str[end];
			str[end] = str[start];
			str[start] = temp;
			start++; end--;
		}
	}

	public static ArrayList<Interval> disjointRanges(ArrayList<Interval> list){
		ArrayList<Interval> disjointAux = new ArrayList<Interval>();
		Interval aux = null;// = new Interval(list.get(0).getMin(), list.get(0).getMax());
		for(int i = 0; i < list.size(); i++){
			for(int j = i + 1; j < list.size(); j++){
				if(list.get(i).getMax() > list.get(j).getMin())
					aux = new Interval(list.get(i).getMin(), list.get(j).getMax());
			}
			if(aux != null)
				disjointAux.add(aux);
		}
		return disjointAux;
	}
	
	public static int stringToInteger(String str){
		int result = Integer.parseInt(str);
		return result;
	}
	
	public static void permutation(String prefix, String str) {
	    int n = str.length();
	    if (n == 0) System.out.println(prefix);
	    else {
	        for (int i = 0; i < n; i++)
	            permutation(prefix + str.charAt(i), str.substring(0, i) + str.substring(i+1, n));
	    }
	}

	static class Interval{
		int min;
		int max;
		public Interval(int min, int max){
			this.min = min;
			this.max = max;
		}

		public int getMin(){
			return this.min;
		}

		public int getMax(){
			return this.max;
		}

		public void setMin(int min){
			this.min = min;
		}

		public void setMax(int max){
			this.max = max;
		}
	}
}
