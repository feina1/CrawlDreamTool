import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.codehaus.jackson.map.ObjectMapper;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class CrawlCode {

	public static void main(String[] args) throws Exception {
		String url = "http://www.orient-fund.com/products/hunhe/400001/index.html	";
		String rule = "{'name':['6','2','0','1','0','1'],'date':['6','2','0','1','0','2','0','0','0'],'status':['6','2','0','1','0','2','0','1','1']}";
		
		
		Document htmlDoc = getHtmlDoc(url);
		HashMap<String, ArrayList<String>> hm = crawlPageAccordingToRule(
				htmlDoc, rule);
		printHashMap(hm);
	}

	public static HashMap<String, ArrayList<String>> crawlPageAccordingToRule(
			Document document, String ruleStr) throws Exception {
		ruleStr = ruleStr.replaceAll("'", "\"");
		System.out.println(ruleStr);
		@SuppressWarnings("unchecked")
		HashMap<String, ArrayList<String>> rules = (HashMap<String, ArrayList<String>>) jsonStr2object(
				ruleStr, HashMap.class);
		HashMap<String, ArrayList<String>> res = new HashMap<String, ArrayList<String>>();
		for (String name : rules.keySet()) {

			ArrayList<String> path = rules.get(name);
			ArrayList<String> values = new ArrayList<String>();

			int branchedIndex = -1;
			for (int i = 0; i < path.size(); i++)
				if (path.get(i).equals("*")) {
					branchedIndex = i;
					break;
				}

			if (branchedIndex == -1) // single
				values.add(transferJsonTag(getValue(document, path)));
			else { // multi
				Element father = getElement(document,
						path.subList(0, branchedIndex));
				int childNum = father.children().size();
				for (int i = 0; i < childNum; i++) {
					path.set(branchedIndex, i + "");
					values.add(transferJsonTag(getValue(document, path)));

				}
			}
			res.put(name, values);
		}
		return res;
	}

	public static String transferJsonTag(String value) {
		return value.replaceAll(",", "\uff0c").replaceAll("\\[", "<")
				.replaceAll("\\]", ">");
	}

	public static Element getElement(Document doc, List<String> path)
			throws Exception {
		Element target;
		String p0 = path.get(0);
		if (p0.startsWith("#"))
			target = doc.getElementById(p0.substring(1));
		else
			target = getBody(doc).child(Integer.parseInt(p0));
		for (int i = 1; i < path.size(); i++)
			target = target.child(Integer.parseInt(path.get(i)));
		return target;
	}

	public static String getValue(Document doc, ArrayList<String> path) {
		try {
			Element target = getElement(doc, path);
			String res = target.text();
			String urlString = target.attr("href");
			if (urlString != null && urlString.length() != 0)
				res += "[" + urlString + "]";
			return res;
		} catch (Exception e) {
			System.err.println("*** error in parsing " + arraylist2line(path));
			return "";
		}
	}

	public static void printHashMap(HashMap m) {
		for (Object o : m.keySet()) {
			String os = "";
			if (null != m.get(o))
				os = m.get(o).toString();
			System.out.println(o.toString() + "," + os);
		}
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

	public static <T> String arraylist2line(ArrayList<T> a) {
		String res = a.get(0).toString();
		for (int i = 1; i < a.size(); i++)
			res += "," + a.get(i).toString();
		return res;
	}

	public static Element getBody(Document doc) throws IOException {
		Elements body = doc.getElementsByTag("body");
		if (body.size() == 0)
			body = doc.getElementsByTag("BODY");
		return body.get(0);
	}

	public static Object jsonStr2object(String userDataJSON, Class t)
			throws Exception {
		return new ObjectMapper().readValue(userDataJSON, t);
	}
}
