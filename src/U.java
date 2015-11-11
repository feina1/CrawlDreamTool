import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.StringWriter;
import java.io.UnsupportedEncodingException;
import java.io.Writer;
import java.lang.reflect.Field;
import java.net.Authenticator;
import java.net.PasswordAuthentication;
import java.net.URL;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.AbstractCollection;
import java.util.AbstractMap.SimpleEntry;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Hashtable;
import java.util.List;
import java.util.Properties;
import java.util.Random;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.codehaus.jackson.map.ObjectMapper;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

public class U {

	// static String root = U.file2str("c:/data/trash/cdt.txt");
	public static final String t = "\t";
	public static String endl = "\n";
	public static String bcTimeFormat = "yyyyMMddHHmm";
	public static String hendl = "<br/>";
	public static String cdtPath = "c:/data/cdt/";
	public static String javaCodePath = "C:/Users/IBM_ADMIN/Desktop/java/CrawlDreamTool/src/CrawlCode";

	public static void main(String[] args) throws Exception {
		
		System.out.println(getHtmlDoc("http://researcher.watson.ibm.com/researcher/view_group_people.php?&grp=155"));
	}

	private static void testProperties() throws Exception {
		// Properties prop = new Properties();
		// prop.load(new FileInputStream("data/prop.properties"));
		// System.out.println(prop.get("a"));
	}

	private static void solveLocalHtml() throws IOException {
		System.out
				.println(U
						.getHtmlDoc("file:///C:/Users/IBM_ADMIN/Desktop/java/CrawlDreamTool/data/test/feilipu/row1tab1.html"));
	}

	public static void testJsonParse() throws Exception {
		HashMap<String, ArrayList<Integer>> items = new HashMap<String, ArrayList<Integer>>();
		ArrayList<Integer> al = new ArrayList<Integer>();
		al.add(1);
		al.add(0);
		items.put("location", al);
		al = new ArrayList<Integer>();
		al.add(321);
		al.add(12);
		items.put("name", al);
		String s = U.object2jsonStr(items);
		System.out.println(s);
		items = (HashMap<String, ArrayList<Integer>>) U.jsonStr2object(s,
				HashMap.class);
		ArrayList<Integer> ss = items.get("name");
		System.out.println(ss.get(0));
	}

	public static Document getHtmlDoc(String url) throws IOException {
		String s = "file:///";
		if (url.startsWith(s)) { // local
			File in = new File(url.substring(s.length()));
			Document doc = Jsoup.parse(in, "UTF-8", "");
			return doc;
		}
		return Jsoup
				.connect(url)
				.userAgent(
						"Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.31 (KHTML, like Gecko) Chrome/26.0.1410.64 Safari/537.31")
				.timeout(100000).get();
	}

//	public static Document getHtmlDoc2(String url) throws IOException {
//		String s =getHtmlDoc(url).html().replaceAll("\r", "").replaceAll("\n", "");
//		return Jsoup.parse(s);
//	}
	
	public static void executeCommand(String cmd, String logPath)
			throws Exception {
		if (logPath != null)
			new File(logPath).delete();
		String s;
		// using the Runtime exec method:
		Process p = Runtime.getRuntime().exec(cmd);
		BufferedReader stdInput = new BufferedReader(new InputStreamReader(
				p.getInputStream()));
		// read the output from the command
		while ((s = stdInput.readLine()) != null) {
			System.out.println(s);
			if (logPath != null)
				U.appendStringToFile(s, logPath);
		}

		BufferedReader stdError = new BufferedReader(new InputStreamReader(
				p.getErrorStream()));
		while ((s = stdError.readLine()) != null) {
			System.out.println(s);
		}
	}

	// public static Object jsonStr2object(String userDataJSON, Class t)
	// throws Exception {
	//
	// return new ObjectMapper().readValue(userDataJSON, t);
	// }
	//
	// static String object2jsonStr(Object o) throws Exception {
	// ObjectMapper mapper = new ObjectMapper();
	// Writer strWriter = new StringWriter();
	// mapper.writeValue(strWriter, o);
	// return strWriter.toString();
	// }

	public static String delQuote(String s) {
		return s.substring(1, s.length() - 1);
	}

	// 0 is sunday, 1 is monday
	public static int getDayOfTheWeek(Date d) {
		Calendar c = Calendar.getInstance();
		c.setTime(d);
		return c.get(Calendar.DAY_OF_WEEK) - 1;
	}

	// 1 is Jan, 2 is Feb
	public static int getMonthOfTheDay(Date d) {
		Calendar c = Calendar.getInstance();
		c.setTime(d);
		return c.get(Calendar.MONTH) + 1;
	}

	public static Date addNdays(Date d, int i) {
		Calendar c = Calendar.getInstance();
		c.setTime(d);
		c.add(Calendar.DATE, i);
		return c.getTime();
	}

	public static double dateMinusInDay(Date time, Date time2) {
		return (double) ((time.getTime() - time2.getTime()) / 1000 / 3600 / 24);
	}

	public static <T> void hashmap2file(HashMap<String, T> hm, String file)
			throws Exception {
		BufferedWriter w = U.newWriter(file);
		for (String k : hm.keySet()) {
			w.write(k + "\t" + hm.get(k) + "\n");
		}
		w.close();
	}

	// yyyy-MM-dd_HH:mm:ss_SSS
	public static String date2str(Date d, String format) {
		return new SimpleDateFormat(format).format(d);
	}

	public static Date str2date(String s, String format) throws ParseException {
		if (s == null || s.length() == 0)
			return null;
		return new SimpleDateFormat(format).parse(s);
	}

	public static <T> void add(HashMap<T, Double> hm, T key, double val) {
		double res = 0;
		if (null != hm.get(key))
			res = hm.get(key);
		res += val;
		hm.put(key, res);
	}

	public static <T> int get(HashMap<T, Integer> m, T s) {
		int i = 0;
		if (null != m.get(s))
			i = m.get(s);
		return i;
	}

	public static HashMap<String, Integer> file2hashmap(String hashMapFile)
			throws Exception {
		HashMap<String, Integer> res = new HashMap<String, Integer>();

		int ii = 0;
		BufferedReader r = U.newReader(hashMapFile);
		while (true) {
			String l = r.readLine();
			if (l == null)
				break;

			ii++;
			if (ii % 100000 == 0)
				System.out.println(ii);
			String[] sa = l.split("\t");
			res.put(sa[0], Integer.parseInt(sa[1]));
		}
		r.close();
		return res;
	}

	public static HashMap<String, Double> file2hashmapDouble(String hashMapFile)
			throws Exception {
		HashMap<String, Double> res = new HashMap<String, Double>();

		int ii = 0;
		BufferedReader r = U.newReader(hashMapFile);
		while (true) {
			String l = r.readLine();
			if (l == null)
				break;

			ii++;
			if (ii % 100000 == 0)
				System.out.println(ii);
			String[] sa = l.split("\t");
			res.put(sa[0], Double.parseDouble(sa[1]));
		}
		r.close();
		return res;
	}

	public static void Assert(boolean b) {
		if (!b) {
			throw new NullPointerException();
		}

	}

	public static BufferedReader newUtf8Reader(String in)
			throws UnsupportedEncodingException, FileNotFoundException {
		return new BufferedReader(new InputStreamReader(
				new FileInputStream(in), "UTF-8"));
	}

	public static BufferedWriter newUtf8writer(String string) throws Exception {
		return new BufferedWriter(new OutputStreamWriter(new FileOutputStream(
				string), "UTF-8"));
	}

	public static int getFileLineNum(String f) throws IOException {
		BufferedReader r = U.newReader(f);
		int res = 0;
		while (true) {
			String l = r.readLine();
			if (null == l)
				break;
			res++;
		}
		r.close();
		return res;
	}

	public static String TrimGood(String s) {
		if (null == s)
			return null;
		s = s.replace("\n", "");
		s = s.replace("\r", "");
		s = trimBeginAndEnd(s);
		return s;
	}

	public static boolean isEmpty(String s) {
		if (null == s || s.trim().equals(""))
			return true;
		return false;
	}

	public static <E> void collection2file(AbstractCollection<E> c, String fpath)
			throws Exception {
		BufferedWriter w = newWriter(fpath);
		for (E s : c)
			w.write(s.toString() + '\n');
		w.close();
	}

	public static void closeW(Writer w) throws IOException {
		w.flush();
		w.close();
	}

	public static String getHTMLSrc(String url, String charSet)
			throws IOException {

		URL u = new URL(url);
		InputStream in = u.openStream();
		BufferedReader is = new BufferedReader(new InputStreamReader(in,
				charSet));
		String c = "";
		StringBuffer sb = new StringBuffer();
		while ((c = is.readLine()) != null) {
			sb.append(c).append("\n");// 读入数据
		}
		in.close();
		return U.TrimGood(new String(sb.toString()));
	}

	public static String getHTMLSource(String url) throws IOException {
		return U.getHTMLSrc(url, "utf-8");
	}

	public static int getIndex(String[] sa, String s) {
		for (int i = 0; i < sa.length; i++) {
			if (sa[i].equals(s))
				return i;
		}
		return -1;
	}

	public static void setProxy() {
		System.getProperties().put("http.proxyHost", "162.105.146.215");
		System.getProperties().put("http.proxyPort", "3128");
		Authenticator.setDefault(new MyAuthenticator());
	}

	static class MyAuthenticator extends Authenticator {
		public PasswordAuthentication getPasswordAuthentication() {
			return new PasswordAuthentication("jjyao", new char[] { 'p', 's',
					'd', 'j', 'j', 'y' });
		}
	}

	public static double minOr0(double[] da) {
		double res = 0;
		for (int i = 0; i < da.length; i++) {
			if (da[i] < res)
				res = da[i];
		}
		return res;
	}

	static List<String> getMatchedStringByRegex(String src, String regEx) {
		List<String> matchList = new ArrayList<String>();

		Pattern regex = Pattern.compile(regEx);
		Matcher regexMatcher = regex.matcher(src);
		while (regexMatcher.find()) {
			matchList.add(regexMatcher.group());
		}
		return matchList;
	}

	public static boolean isIn(int[] ia, int content) {
		for (int i = 0; i < ia.length; i++) {
			if (content == ia[i])
				return true;
		}
		return false;
	}

	public static boolean isIn(String[] ia, String content) {
		for (int i = 0; i < ia.length; i++) {
			if (content.equals(ia[i]))
				return true;
		}
		return false;
	}

	public static BufferedReader getReader(String file)
			throws FileNotFoundException {
		return new BufferedReader(new FileReader(file));
	}

	static boolean isDigit(String value) {
		try {
			Double.parseDouble(value);
			return true;
		} catch (NumberFormatException e) {
			return false;
		}
	}

	static boolean isInt(String value) {
		try {
			Integer.parseInt(value);
			return true;
		} catch (NumberFormatException e) {
			return false;
		}
	}

	public static double truncation(double d) {
		long _d = (long) (d * 100);
		double res = ((double) _d) / 100;
		if (d > 100 && Math.abs(d - res) > 0.1 * Math.abs(d)) {
			// System.err.println("U.truncation error!");
			return Double.NaN;
		}
		return res;
	}

	public static BufferedWriter newWriter(String file) throws Exception {
		return U.newUtf8writer(file);
	}

	public static <T> void add(HashMap<T, Integer> m, T s) {
		int i = 0;
		if (null != m.get(s))
			i = m.get(s);
		m.put(s, i + 1);
	}

	int find(int[] a, int t) {
		if (a == null || a.length == 0)
			return -1;
		int begin = 0, end = a.length - 1;
		while (begin <= end) {
			int middle = (begin + end) / 2;
			if (a[middle] == t)
				return middle;
			if (a[middle] < t)
				begin = middle + 1;
			else
				end = middle - 1;
		}
		return -1;
	}

	public static void add(Hashtable<String, Integer> m, String s) {
		int i = 0;
		if (null != m.get(s))
			i = m.get(s);
		m.put(s, i + 1);
	}

	public static double getExpectation(ArrayList<Double> v) {
		double result = 0;
		for (int i = 0; i < v.size(); i++) {
			result += v.get(i);
		}
		return result / v.size();
	}

	public static double getSD(ArrayList<Double> v) {
		if (v.size() == 1)
			return Double.NaN;
		double e = getExpectation(v);
		double result = 0;
		for (int i = 0; i < v.size(); i++) {
			result += (v.get(i) - e) * (v.get(i) - e);
		}
		return Math.sqrt(result / (v.size() - 1));
	}

	public static double relatedCoefficient(ArrayList<Double> v,
			ArrayList<Double> v2) {

		double res = 0;
		double e = getExpectation(v);
		double e2 = getExpectation(v2);
		for (int i = 0; i < v.size(); i++) {
			res += (v.get(i) - e) * (v2.get(i) - e2);
		}
		return res / getSD(v) / getSD(v2) / v.size();
	}

	public static int tryGet(HashMap<String, Integer> hm, String s) {
		if (null != hm.get(s))
			return hm.get(s);
		return 0;
	}

	public static int hashtableTryGet(Hashtable<String, Integer> hm, String s) {
		if (null != hm.get(s))
			return hm.get(s);
		return 0;
	}

	public static Object getProperty(Object owner, String fieldName)
			throws Exception {
		Class ownerClass = owner.getClass();

		Field field = ownerClass.getField(fieldName);

		Object property = field.get(owner);

		return property;
	}

	public static BufferedReader newReader(String file) {
		try {
			return newUtf8Reader(file);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
			System.err.println("fail to read file " + file);
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

	public static String Array2String(Object[] a) {
		String res = "[";
		for (int i = 0; i < a.length; i++) {
			res += " " + a[i];
		}
		return res + " ]";
	}

	public static String Array2String(int[] a) {
		String res = "[";
		for (int i = 0; i < a.length; i++) {
			res += " " + a[i];
		}
		return res + " ]";
	}

	public static String Array2String(double[] a) {
		String res = "[";
		for (int i = 0; i < a.length; i++) {
			res += " " + a[i];
		}
		return res + " ]";
	}

	static int[] intSas2A(String s) {
		String[] sa = s.split(" ");
		int[] res = new int[sa.length - 2];
		for (int i = 1; i < sa.length - 1; i++) {
			res[i - 1] = Integer.parseInt(sa[i]);
		}
		return res;
	}

	static double[] doubleSas2A(String s) {
		String[] sa = s.split(" ");
		double[] res = new double[sa.length - 2];
		for (int i = 1; i < sa.length - 1; i++) {
			res[i - 1] = Double.parseDouble(sa[i]);
		}
		return res;
	}

	public static String trimBeginAndEnd(String s) {
		if (null == s || s.equals(""))
			return s;
		int i = 0;
		for (; i < s.length() && s.charAt(i) == ' '; i++)
			;
		if (i == s.length())
			return "";
		return s.substring(i).trim();
	}

	public static HashSet<String> loadHashSet(String f) throws IOException {
		BufferedReader r = U.newReader(f);
		HashSet<String> res = new HashSet<String>();
		while (true) {
			String l = r.readLine();
			if (null == l)
				break;
			res.add(l);
		}
		r.close();
		return res;
	}

	public static int[] arrayAdd(int[] a, int i) {
		int[] res = new int[a.length + 1];
		for (int j = 0; j < a.length; j++) {
			res[j] = a[j];
		}
		res[res.length - 1] = i;
		return res;
	}

	public static double[] arrayAdd(double[] a, double i) {
		double[] res = new double[a.length + 1];
		for (int j = 0; j < a.length; j++) {
			res[j] = a[j];
		}
		res[res.length - 1] = i;
		return res;
	}

	public static int getMaxIndex(int[] a) {
		int max = -Integer.MAX_VALUE;
		int index = -1;
		for (int i = 0; i < a.length; i++) {
			if (a[i] > max) {
				max = a[i];
				index = i;
			}

		}
		return index;
	}

	// ascending order
	public static int[] sort(int[] a) {
		if (a == null)
			return null;
		// int[] res = new int[a.length];
		//
		// for (int i = res.length - 1; i >= 0; i--) {
		// int j = U.getMaxIndex(a);
		// res[i] = a[j];
		// a[j] = -Integer.MAX_VALUE;
		// if(i%10000==0)
		// System.out.println(i);
		// }
		Arrays.sort(a);
		return a;
	}

	public static ArrayList<String> setMinus(Set<String> set1, Set<String> _set2) {
		ArrayList<String> res = new ArrayList<String>();
		for (String s1 : set1) {
			if (!_set2.contains(s1))
				res.add(s1);
		}
		return res;
	}

	@SuppressWarnings("unchecked")
	public static void setAdd(Set featureSet, Set featureSet2) {
		for (Object o : featureSet2)
			featureSet.add(o);

	}

	public static HashSet<String> ArrayList2Set(ArrayList<String> v) {
		HashSet<String> set = new HashSet<String>();
		for (String f : v)
			set.add(f);
		return set;
	}

	public static HashSet<String> ArrayList2HashSet(ArrayList<String> v) {
		HashSet<String> res = new HashSet<String>();
		for (String f : v)
			res.add(f);
		return res;
	}

	public static String[] set2Sa(Set<String> labels) {
		String[] res = new String[labels.size()];
		int i = 0;
		for (String o : labels) {
			res[i] = o;
			i++;
		}
		return res;
	}

	public static boolean StrIsEmpty(String s) {
		if (null == s || s.trim().equals(""))
			return true;
		return false;
	}

	public static String file2str(String file) {
		String res = "";
		BufferedReader br = U.newReader(file);
		try {
			while (true) {
				String l = br.readLine();
				if (null == l)
					break;
				res += l + "\n";
			}

			br.close();
		} catch (IOException e) {
			System.err.println("file to read file: " + file);
			e.printStackTrace();
		}
		if (res.length() > 0)
			res = res.substring(0, res.length() - 1);
		return res;
	}

	public static void str2file(String str, String file) throws Exception {
		// FileWriter bw = new FileWriter(file, append);
		BufferedWriter bw = U.newUtf8writer(file);
		try {
			bw.write(str);
			bw.close();
		} catch (IOException e) {
			System.err.println("file to write file: " + file);
			e.printStackTrace();
		}
	}

	public static void appendStringToFile(String s, String fileName)
			throws IOException {
		BufferedWriter w = new BufferedWriter(new FileWriter(fileName, true));
		w.write(s + U.endl);
		U.closeW(w);

	}

	public static String removeUnnecessaryBlank(String s) {
		return s.replaceAll(" +", " ");
	}

	public static boolean deleteDir(String f) {
		File dir = new File(f);
		if (dir.isDirectory()) {
			String[] children = dir.list();
			for (int i = 0; i < children.length; i++) {
				boolean success = deleteDir(new File(dir, children[i])
						.getAbsolutePath());
				if (!success) {
					System.err
							.println("something is wrong in deleting the dir");
					return false;
				}
			}
		}
		return dir.delete();
	}

	// k integers from 1~(n-1)
	public static HashSet<Integer> getRandomK_Numbers(int n, int k) {
		Assert(n >= k);
		HashSet<Integer> res = new HashSet<Integer>();
		if (k == 0)
			return res;
		while (true) {
			int j = getRandomInt(n);
			if (!res.contains(j))
				res.add(j);
			if (res.size() == k)
				return res;
		}
	}

	// 0~(n-1)
	public static int getRandomInt(int n) {
		int i = new Random().nextInt(n);
		return i;
	}

	// (0,1) uniform distribution
	public static double getRandomNum() {
		return Math.random();
	}

	public static ArrayList<String> file2Arraylist(String f) throws IOException {
		ArrayList<String> v = new ArrayList<String>();
		BufferedReader r = U.newReader(f);
		while (true) {
			String l = r.readLine();
			if (null == l)
				break;
			v.add(TrimGood(l));
		}
		r.close();
		return v;
	}

	// only bat in your java project dir, you can cd to other dir to excute
	// other bat
	public static void executeCommandLine(String order) throws IOException,
			InterruptedException {
		Process p = Runtime.getRuntime().exec(order);
		p.waitFor();
	}

	public static HashSet<String> file2hashSet(String file) throws Exception {
		HashSet<String> res = new HashSet<String>();
		List<String> v = U.file2Arraylist(file);
		for (String ss : v)
			if (null != ss) {
				ss = U.TrimGood(ss);
				if (!ss.equals(""))
					res.add(ss);
			}
		return res;
	}

	public static <T> void hashSet2file(HashSet<T> hs, String f)
			throws Exception {
		BufferedWriter w = U.newWriter(f);
		for (T s : hs)
			w.write(s + "\r\n");
		U.closeW(w);
	}

	public static ArrayList<String> hashSet2ArrayList(HashSet<String> set) {
		ArrayList<String> res = new ArrayList<String>();
		for (String s : set)
			res.add(s);
		return res;
	}

	public static String toUtf8String(String s) {
		StringBuffer sb = new StringBuffer();
		for (int i = 0; i < s.length(); i++) {
			char c = s.charAt(i);
			if (c >= 0 && c <= 255) {
				sb.append(c);
			} else {
				byte[] b;
				try {
					b = String.valueOf(c).getBytes("utf-8");
				} catch (Exception ex) {
					System.out.println(ex);
					b = new byte[0];
				}
				for (int j = 0; j < b.length; j++) {
					int k = b[j];
					if (k < 0)
						k += 256;
					sb.append("%" + Integer.toHexString(k).toUpperCase());
				}
			}
		}
		return sb.toString();
	}

	public static String unescape(String s) {
		StringBuffer sbuf = new StringBuffer();
		int l = s.length();
		int ch = -1;
		int b, sumb = 0;
		for (int i = 0, more = -1; i < l; i++) {
			/* Get next byte b from URL segment s */
			switch (ch = s.charAt(i)) {
			case '%':
				ch = s.charAt(++i);
				int hb = (Character.isDigit((char) ch) ? ch - '0'
						: 10 + Character.toLowerCase((char) ch) - 'a') & 0xF;
				ch = s.charAt(++i);
				int lb = (Character.isDigit((char) ch) ? ch - '0'
						: 10 + Character.toLowerCase((char) ch) - 'a') & 0xF;
				b = (hb << 4) | lb;
				break;
			case '+':
				b = ' ';
				break;
			default:
				b = ch;
			}
			/* Decode byte b as UTF-8, sumb collects incomplete chars */
			if ((b & 0xc0) == 0x80) { // 10xxxxxx (continuation byte)
				sumb = (sumb << 6) | (b & 0x3f); // Add 6 bits to sumb
				if (--more == 0)
					sbuf.append((char) sumb); // Add char to sbuf
			} else if ((b & 0x80) == 0x00) { // 0xxxxxxx (yields 7 bits)
				sbuf.append((char) b); // Store in sbuf
			} else if ((b & 0xe0) == 0xc0) { // 110xxxxx (yields 5 bits)
				sumb = b & 0x1f;
				more = 1; // Expect 1 more byte
			} else if ((b & 0xf0) == 0xe0) { // 1110xxxx (yields 4 bits)
				sumb = b & 0x0f;
				more = 2; // Expect 2 more bytes
			} else if ((b & 0xf8) == 0xf0) { // 11110xxx (yields 3 bits)
				sumb = b & 0x07;
				more = 3; // Expect 3 more bytes
			} else if ((b & 0xfc) == 0xf8) { // 111110xx (yields 2 bits)
				sumb = b & 0x03;
				more = 4; // Expect 4 more bytes
			} else /* if ((b & 0xfe) == 0xfc) */{ // 1111110x (yields 1 bit)
				sumb = b & 0x01;
				more = 5; // Expect 5 more bytes
			}
			/* We don't test if the UTF-8 encoding is well-formed */
		}
		return sbuf.toString();
	}

	public static ArrayList<String> getFileNameFromDir(String dir) {
		if (dir.charAt(dir.length() - 1) == '\\'
				|| dir.charAt(dir.length() - 1) == '/')
			dir = dir.substring(0, dir.length() - 1);
		File d = new File(dir);
		if (!d.exists())
			return null;
		ArrayList<String> res = new ArrayList<String>();
		File[] fs = d.listFiles();
		for (File f : fs)
			res.add(f.getName());
		return res;
	}

	public static String preprocessStrForHttptransmition(String q) {
		q = q.replaceAll("%", "%25");
		q = q.replaceAll("&", "%26");
		q = q.replaceAll("#", "%23");
		q = q.replaceAll(" ", "%20");
		q = q.replaceAll("=", "%3D");
		q = U.toUtf8String(q);
		q = q.replaceAll(" ", "%20");
		q = q.replaceAll("\t", "%09");
		return q;
	}

	public static String getPropertyValue(String filePath, String key) {
		Properties props = new Properties();
		try {
			InputStream in = new BufferedInputStream(new FileInputStream(
					filePath));
			props.load(in);
			String value = props.getProperty(key);
			in.close();
			return value;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	@SuppressWarnings("unchecked")
	public static void ArrayList2file(ArrayList res, String file)
			throws Exception {
		BufferedWriter w = U.newWriter(file);
		for (Object s : res)
			w.write(s.toString() + "\n");
		w.close();
	}

	public static ArrayList<SimpleEntry<String, Integer>> Hashmap2ArrayList(
			HashMap<String, Integer> m) {
		U.Assert(null != m);
		ArrayList<SimpleEntry<String, Integer>> list = new ArrayList<SimpleEntry<String, Integer>>();
		for (String k : m.keySet()) {
			list.add(new SimpleEntry<String, Integer>(k, m.get(k)));
		}
		return list;
	}

	public static class Triple {
		public Object first;
		public Object second;
		public Object third;

		public Triple(Object f, Object s, Object t) {
			first = f;
			second = s;
			third = t;
		}
	}

	public static class KeyValuePair {
		public Object key;
		public double value;

		public KeyValuePair(Object k, double v) {
			key = k;
			value = v;
		}
	}

	public static <T> ArrayList<T> sort(ArrayList<T> list,
			ArrayList<Double> value) {

		ArrayList<KeyValuePair> l = new ArrayList<U.KeyValuePair>();
		for (int i = 0; i < list.size(); i++)
			l.add(new KeyValuePair(list.get(i), value.get(i)));
		sort(l);

		ArrayList<T> res = new ArrayList<T>();
		for (KeyValuePair p : l)
			res.add((T) p.key);
		return res;
	}

	public static void sort(ArrayList<KeyValuePair> list) {
		Comparator comp = new Comparator() {

			public final int compare(Object o1, Object o2) {
				double first = ((KeyValuePair) o1).value;
				double second = ((KeyValuePair) o2).value;
				double diff = first - second;
				if (diff > 0)
					return 1;
				if (diff < 0)
					return -1;
				else
					return 0;
			}

		};
		Collections.sort(list, comp);
	}

	public static ArrayList<String> findAllMatchUsingRegex(String s,
			String pattern) {
		Pattern regex = Pattern.compile(pattern);
		Matcher regexMatcher = regex.matcher(s);
		ArrayList<String> res = new ArrayList<String>();
		while (regexMatcher.find()) {
			res.add(regexMatcher.group());
		}
		return res;
	}

	@SuppressWarnings("unchecked")
	public static void printCollection(AbstractCollection c) {
		for (Object o : c)
			System.out.println(o.toString());
	}

	public static void printHashMap(HashMap m) {
		for (Object o : m.keySet()) {
			String os = "";
			if (null != m.get(o))
				os = m.get(o).toString();
			System.out.println(o.toString() + t + os);
		}
	}

	public static HashMap<String, Object> getObjectInfo(Object object)
			throws Exception {
		HashMap<String, Object> res = new HashMap<String, Object>();
		Field[] fields = object.getClass().getDeclaredFields();
		String type;
		for (int i = 0, len = fields.length; i < len; i++) {
			boolean accessFlag = fields[i].isAccessible();
			fields[i].setAccessible(true);
			type = fields[i].getType().getName();
			// not basic type
			if (type.equals("java.util.HashMap")) {
				res.putAll((HashMap) fields[i].get(object));
			} else if (!type.contains("double") && !type.contains("int")
					&& !type.contains("String") && !type.contains("Date")) {
				HashMap<String, Object> tmp = getObjectInfo(fields[i]
						.get(object));
				res.putAll(tmp);
			} else {
				res.put(fields[i].getName(), fields[i].get(object));
				fields[i].setAccessible(accessFlag);
			}
		}
		return res;
	}

	public static ArrayList<String> getObjectTitle(Object f) throws Exception {
		Field[] fields = f.getClass().getDeclaredFields();
		ArrayList<String> res = new ArrayList<String>();
		for (int i = 0, len = fields.length; i < len; i++) {
			String varName = fields[i].getName();

			boolean accessFlag = fields[i].isAccessible();
			fields[i].setAccessible(true);
			Object o = fields[i].get(f);
			res.add(varName);
			fields[i].setAccessible(accessFlag);
		}
		return res;
	}

	public class TS {
		Date begin;
		Date end;
	}

	public static String getStrContent(String s, String begin, String end) {

		int i = s.indexOf(begin) + begin.length();
		if (i == -1)
			return null;
		int e = s.indexOf(end, i);
		if (e == -1)
			return null;
		return s.substring(i, e);
	}

	public static ArrayList<Object> getObjectProperties(Object f)
			throws Exception {
		Field[] fields = f.getClass().getDeclaredFields();
		ArrayList<Object> res = new ArrayList<Object>();
		for (int i = 0, len = fields.length; i < len; i++) {
			if (fields[i].isAccessible())
				continue;
			Object o = fields[i].get(f);
			res.add(o);
		}
		return res;
	}

	public static void mergeFile(String p) throws IOException {
		File[] fa = new File(p).listFiles();
		FileWriter w = new FileWriter(p + "//merge.txt");
		for (File f : fa) {
			System.out.println("merging file: " + f.getAbsolutePath());
			BufferedReader r = newReader(f.getAbsolutePath());
			while (true) {
				String l = r.readLine();
				if (l == null)
					break;
				w.write(l + U.endl);
			}
			r.close();
		}
		w.close();

	}

	public static long dateMinus_MS(Date ct, Date t) {

		return ct.getTime() - t.getTime();
	}

	// java have month number -1, but not here.
	public static Date newDate(int i, int j) {
		Calendar cc = Calendar.getInstance();
		cc.set(i, j - 1, 1, 0, 0, 0);
		Date d = cc.getTime();
		return d;
	}

	// java have month number -1, but not here.
	public static Date newDate(int i, int j, int k) {
		Calendar cc = Calendar.getInstance();
		cc.set(i, j - 1, k, 0, 0, 0);
		Date d = cc.getTime();
		return d;
	}

	// java have month number -1, but not here.
	public static Date newDate(int i, int j, int k, int l, int m) {
		Calendar cc = Calendar.getInstance();
		cc.set(i, j - 1, k, l, m, 0);
		Date d = cc.getTime();
		return d;
	}

	// type: Calendar.YEAR, Calendar.MONTH...
	public static int get(Date d, int type) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(d);
		return cal.get(type);
	}

	public static HashSet<Integer> file2InthashSet(String f) throws IOException {
		HashSet<Integer> res = new HashSet<Integer>();
		List<String> v = U.file2Arraylist(f);
		for (String ss : v)
			if (null != ss && ss.trim().length() != 0) {
				try {
					res.add(Integer.parseInt(ss));
				} catch (Exception e) {

				}
			}
		return res;
	}

	public static double sum(double[] b) {
		double res = 0;
		for (double i : b)
			res += i;
		return res;
	}

	public static int sum(int[] b) {
		int res = 0;
		for (int i : b)
			res += i;
		return res;
	}

	public static ArrayList<Integer> getHashMapFrequency(
			HashMap<String, Integer> m, int maxResLength) {
		int[] res = new int[maxResLength];
		for (String k : m.keySet())
			res[m.get(k)]++;
		int end = res.length - 1;
		for (; end >= 0 && res[end] == 0; end--)
			;
		ArrayList<Integer> res1 = new ArrayList<Integer>();
		for (int i = 0; i <= end; i++)
			res1.add(res[i]);
		return res1;
	}

	public static Date nd(int i, int j, int k) {
		return newDate(i, j, k);
	}

	public static Date dateAddDays(Date basicDate, int days) {

		Calendar c = Calendar.getInstance();
		c.setTime(basicDate);
		c.add(Calendar.DAY_OF_YEAR, days);
		return c.getTime();
	}

	public static Date dateAddMonth(Date d, int i) {
		Calendar c = Calendar.getInstance();
		c.setTime(d);
		int year = c.get(Calendar.YEAR);
		int month = c.get(Calendar.MONTH);
		month = dateGetMonth(d) + i;
		c.set(Calendar.YEAR, year + (dateGetMonth(d) + i) / 12);
		c.set(Calendar.MONTH, month % 12);
		return c.getTime();
	}

	public static int dateGetMonth(Date d) {
		Calendar c = Calendar.getInstance();
		c.setTime(d);
		return c.get(Calendar.MONTH);
	}

	public static void printArray(int[] a) {
		for (int t : a)
			System.out.println(t);
	}

	public static HashMap<String, String> file2HashmapStr(String string)
			throws Exception {
		HashMap<String, String> res = new HashMap<String, String>();

		int ii = 0;
		BufferedReader r = U.newReader(string);
		while (true) {
			String l = r.readLine();
			if (l == null)
				break;

			ii++;
			if (ii % 100000 == 0)
				System.out.println(ii);
			String[] sa = l.split("\t");
			res.put(sa[0], sa[1]);
		}
		r.close();
		return res;
	}

	// result: 0:k; 1:Math.sqrt(rss/n)
	public static double[] linearRegression(double[] y) {
		int n = y.length;
		double[] x = new double[n];
		double sumx = 0.0, sumy = 0.0, sumx2 = 0.0;

		for (int i = 0; i < n; i++) {
			x[i] = i;
			sumx += x[i];
			sumx2 += x[i] * x[i];
			sumy += y[i];
		}

		double xbar = sumx / n;
		double ybar = sumy / n;

		// second pass: compute summary statistics
		double xxbar = 0.0, yybar = 0.0, xybar = 0.0;
		for (int i = 0; i < n; i++) {
			xxbar += (x[i] - xbar) * (x[i] - xbar);
			yybar += (y[i] - ybar) * (y[i] - ybar);
			xybar += (x[i] - xbar) * (y[i] - ybar);
		}
		double beta1 = xybar / xxbar;
		double beta0 = ybar - beta1 * xbar;

		// print results
		// System.out.println("y   = " + beta1 + " * x + " + beta0);

		// analyze results
		int df = n - 2;
		double rss = 0.0; // residual sum of squares
		double ssr = 0.0; // regression sum of squares
		for (int i = 0; i < n; i++) {
			double fit = beta1 * x[i] + beta0;
			rss += (fit - y[i]) * (fit - y[i]);
			ssr += (fit - ybar) * (fit - ybar);
		}
		double R2 = ssr / yybar;
		double svar = rss / df;
		double svar1 = svar / xxbar;
		double svar0 = svar / n + xbar * xbar * svar1;
		// System.out.println("R^2                 = " + R2);
		// System.out.println("std error of beta_1 = " + Math.sqrt(svar1));
		// System.out.println("std error of beta_0 = " + Math.sqrt(svar0));
		svar0 = svar * sumx2 / (n * xxbar);
		// System.out.println("std error of beta_0 = " + Math.sqrt(svar0));

		// System.out.println("SSTO = " + yybar);
		// System.out.println("RSS  = " + rss);
		// System.out.println("SSR  = " + ssr);
		// System.out.println(Math.sqrt(rss/n));
		return new double[] { beta1, Math.sqrt(rss / n - 1) };
	}

	public static double[] arrayList2array(ArrayList<Double> al) {
		if (al == null)
			return null;
		double[] res = new double[al.size()];
		for (int i = 0; i < al.size(); i++)
			res[i] = al.get(i);
		return res;
	}

	public static boolean isNan(double d) {
		return new Double(d).isNaN();
	}

	public static void fileStringReplace(String path, String s, String ssub)
			throws Exception {
		File f = new File(path);
		BufferedReader r = U.newReader(path);
		FileWriter w = new FileWriter(f.getParent() + "/_" + f.getName());
		while (true) {
			String l = r.readLine();
			if (l == null)
				break;
			l = l.replace(s, ssub);
			w.write(l + U.endl);
		}
		w.close();
		r.close();
	}

	public static boolean dateIsBetweenDate(Date d, Date small, Date big) {
		if (before(d, small) || equal(d, big) || after(d, big))
			return false;
		return true;
	}

	public static String removeLastChar(String s) {
		return s.substring(0, s.length() - 1);
	}

	public static String truncation(double d, int leaveN) {
		String p = "#0.";
		for (int i = 0; i < leaveN; i++)
			p += "0";
		return new java.text.DecimalFormat(p).format(d);
	}

	public static String improvement(double base, double max) {
		return U.truncation((max - base) / base, 2);
	}

	public static boolean before(Date d1, Date d2) {
		if (Long.parseLong(U.date2str(d1, "yyMMddHHmmss")) < Long.parseLong(U
				.date2str(d2, "yyMMddHHmmss")))
			return true;
		return false;
	}

	public static boolean equal(Date d1, Date d2) {
		if (Long.parseLong(U.date2str(d1, "yyMMddHHmmss")) == Long.parseLong(U
				.date2str(d2, "yyMMddHHmmss")))
			return true;
		return false;
	}

	public static boolean after(Date d1, Date d2) {
		if (Long.parseLong(U.date2str(d1, "yyMMddHHmmss")) > Long.parseLong(U
				.date2str(d2, "yyMMddHHmmss")))
			return true;
		return false;
	}

	public static List<Double> file2DoubleArrayList(String f)
			throws IOException {
		ArrayList<Double> v = new ArrayList<Double>();
		BufferedReader r = U.newReader(f);
		while (true) {
			String l = r.readLine();
			if (null == l)
				break;
			v.add(Double.parseDouble(TrimGood(l)));
		}
		r.close();
		return v;
	}

	public static double max(List<Double> limits) {
		double max = -Double.MAX_VALUE;
		for (double d : limits)
			if (d > max)
				max = d;
		return max;
	}

	public static <T> ArrayList<T> sample(ArrayList<T> d, double e) {
		ArrayList<T> res = new ArrayList<T>();
		for (T t : d) {
			if (trueInProb(e)) {
				res.add(t);
			}
		}
		return res;
	}

	private static boolean trueInProb(double e) {
		if (e < 0 || e > 1) {
			System.err.println("trueInProb error!");
			System.exit(-1);
		}
		double d = U.getRandomNum();
		if (d < e)
			return true;
		return false;
	}

	public static void writeln(FileWriter w, String string) throws IOException {
		w.write(string + "\n");
	}

	public static boolean equal_near(double l, double loss) {
		if (Math.abs(l - loss) < 0.0000000001)
			return true;
		return false;
	}

	public static void makeDirectory(String dir) {
		U.deleteDir(dir);
		new File(dir).mkdir();
	}

	// make sure that the constructor exists
	public static Object jsonStr2object(String userDataJSON, Class t)
			throws Exception {
		return new ObjectMapper().readValue(userDataJSON, t);
	}

	public static String object2jsonStr(Object o) throws Exception {
		ObjectMapper mapper = new ObjectMapper();
		Writer strWriter = new StringWriter();
		mapper.writeValue(strWriter, o);
		return strWriter.toString();
	}

	public static ArrayList<String[]> previewNLine(String file, int n)
			throws IOException {
		BufferedReader r = U.newReader(file);
		ArrayList<String[]> res = new ArrayList<String[]>();
		while (r.ready()) {
			res.add(r.readLine().split(","));
			n--;
			if (n == 0)
				break;
		}
		return res;
	}

	public static <T> String arraylist2line(ArrayList<T> a) {
		String res = a.get(0).toString();
		for (int i = 1; i < a.size(); i++)
			res += "," + a.get(i).toString();
		return res;
	}

	public static void saveToJava(String rule) throws Exception {
		rule=rule.replaceAll("\"", "\\\"");
		String code=U.file2str(U.getPropertyValue("properties.txt", "root")+"CrawlCode.java");
		System.out.println(code);
		U.str2file(code, "c:/a.txt");
	}
}