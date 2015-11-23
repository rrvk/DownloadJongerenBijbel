package controler;

import java.awt.im.InputContext;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Attributes;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class Web {
	/**
	 * Deze methode zorgt er voor dat de bijbel tekst uit pagina wordt gehaald, waarvan de link is meegegeven
	 * Structuur jongerenbijbel.nl //29-12-2014
	 * Wanneer null terug gegeven dan is er geen geldige iets gevonden
	 */
	@SuppressWarnings("unused")
	public String accesSite(String url) throws IOException{
		URL oracle = new URL(url);
        BufferedReader in = new BufferedReader(
        new InputStreamReader(oracle.openStream()));
        String inputLine;
        String html="";
        while ((inputLine = in.readLine()) != null){
        	html += inputLine;
        }
        in.close();
        Document document = Jsoup.parse(html);
        Elements divs = document.select("div");
        for (Element div : divs) {
        	// get the class name
        	Attributes attrClass = div.attributes();
        	String classValue = attrClass.get("class");
    		if (classValue.equals("bible")){
    			System.out.println(div.ownText());
    		}
        }
        return null;
	}
	
	/**
	 * Deze methode haald de bijbel tekst clean uit de aangeleverde html
	 * Werkte op 29-12-2014
	 * @param html
	 * @return De tekst
	 */
	private String cleanHTML(String html){
		html = html.replaceAll("<div id=\"tx_jongerenbijbel_footnotes\" style=\"display: none;\"", "");
		html = html.replaceAll("<div id=\"main\">", "");
		html = html.replaceAll("<div class=\"poetry\">", "");
		html = html.replaceAll("<div class=\"q\">", "");
		html = html.replaceAll("<div class=\"sp\">", "");
		html = html.replaceAll("<div class=\"qe\">", "");
		html = html.replaceAll("<div class=\"qc\">", "\r\n");
		html = html.replaceAll("<br />", "");
		html = html.replaceAll("</div>", "\r\n");
		html = html.replaceAll("<h2>", "");
		html = html.replaceAll("</h2>", "\r\n");
		html = html.replaceAll("<h3>", "");
		html = html.replaceAll("</h3>", "\r\n");
		html = html.replaceAll("<p>", "\r\n");
		html = html.replaceAll("</p>", "");
		html = html.replaceAll("<em>", "");
		html = html.replaceAll("</em>", ". ");
		//System.out.println(html);
		//System.out.println("__________________________");
		// de span met display none kan weg
		html = removeSpanDisplayNone(html);
		html = removeSpan(html);
		html = removeImg(html);
		html = removeLink(html);
		return html;
	}
	
	private String removeLink(String html){
		String[] htmlTemp = html.split("<a");
		while (htmlTemp.length>1){
			String[] htmlTemp2 = htmlTemp[1].split("</a>");
			// 	dit zou de rest moeten zijn
			html = htmlTemp[0]+htmlTemp2[1];
			for (int i = 2; i < htmlTemp2.length; i++) {
				html+= "</a>"+htmlTemp2[i];
			}
			for (int i = 2; i < htmlTemp.length; i++) {
				html+= "<a"+htmlTemp[i];
			}
			htmlTemp = html.split("<a");
		}
		return html;
	}
	
	private String removeSpan(String html){
		String[] htmlTemp = html.split("<span");
		while (htmlTemp.length>1){
			String[] htmlTemp2 = htmlTemp[1].split(">");
			// 	dit zou de rest moeten zijn
			html = htmlTemp[0]+htmlTemp2[1];
			for (int i = 2; i < htmlTemp2.length; i++) {
				html+= ">"+htmlTemp2[i];
			}
			for (int i = 2; i < htmlTemp.length; i++) {
				html+= "<span"+htmlTemp[i];
			}
			htmlTemp = html.split("<span");
		}
		html = html.replaceAll("</span>", "");
		html = html.replaceAll("</span", "");
		return html;
	}
	
	private String removeImg(String html){
		String[] htmlTemp = html.split("<img");
		while (htmlTemp.length>1){
			String[] htmlTemp2 = htmlTemp[1].split("/>");
			// 	dit zou de rest moeten zijn
			html = htmlTemp[0]+htmlTemp2[1];
			for (int i = 2; i < htmlTemp2.length; i++) {
				html+= "/>"+htmlTemp2[i];
			}
			for (int i = 2; i < htmlTemp.length; i++) {
				html+= "<img"+htmlTemp[i];
			}
			htmlTemp = html.split("<img");
		}
		return html;
	}
	
	private String removeSpanDisplayNone(String html){
		String[] htmlTemp = html.split("<span style=\"display: none;\">");
		while (htmlTemp.length>1){
			String[] htmlTemp2 = htmlTemp[1].split("</span>");
			// 	dit zou de rest moeten zijn
			html = htmlTemp[0]+htmlTemp2[1];
			for (int i = 2; i < htmlTemp2.length; i++) {
				html+= "</span>"+htmlTemp2[i];
			}
			for (int i = 2; i < htmlTemp.length; i++) {
				html+= "<span style=\"display: none;\">"+htmlTemp[i];
			}
			htmlTemp = html.split("<span style=\"display: none;\">");
		}
		return html;
	}
}
