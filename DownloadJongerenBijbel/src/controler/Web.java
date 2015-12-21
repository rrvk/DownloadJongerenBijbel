package controler;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.List;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Attributes;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.nodes.Node;
import org.jsoup.select.Elements;

public class Web {
	/**
	 * Deze methode zorgt er voor dat de bijbel tekst uit pagina wordt gehaald, waarvan de link is meegegeven
	 * Structuur bijbel.eo.nl //19-12-2015
	 * Wanneer null terug gegeven dan is er geen geldige iets gevonden
	 */
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
    			return cleanHTML(getHtml(div));
    		}
        }
        return null;
	}
	/**
	 * Deze methode de clean html
	 * @param div
	 */
	private String getHtml(Element div){
		String hoofdstuk ="";
		Elements pAll = div.getElementsByTag("p");
		for(Element p : pAll){
			Elements spans = p.select("span");
			String versN = "0";
        	String vers = "";
	        for (Element span : spans) {
	        	Attributes attrSpann = span.attributes();
				// eerst de versnummers krijgen
	        	String classValue = attrSpann.get("class");
	    		if (classValue.equals("verse")){
	    			versN = span.childNode(0).toString();
	    		}
	    		// gaan kijken voor de bijbel tekst
	    		String dataReactidValue = attrSpann.get("data-reactid");
	    		if (dataReactidValue!=""){
	    			List<Node> childNode = span.childNodes();
	    			for (Node n :childNode){
	    				if (n.getClass().toString().equals("class org.jsoup.nodes.TextNode")){
	    					vers += n.toString();
	    				}
	    				else if (n.getClass().toString().equals("class org.jsoup.nodes.Element")){
	    					Attributes attSp = n.attributes();
	    					// eerst de versnummers krijgen
	    					String classN = attSp.get("class");
	    					if (classN.equals("devine-name")){
	    						vers+=n.childNode(0).toString();
	    					}
	    					else if (classN.equals("poetry")){
	    						List<Node> childNode2 = n.childNodes();
	    		    			for (Node n2 :childNode2){
	    		    				if (n2.getClass().toString().equals("class org.jsoup.nodes.TextNode")){
	    		    					vers += n2.toString();
	    		    				}
	    		    			}
	    					}
	    					else if (classN.equals("sela")){
	    						System.out.println("a");
	    					}
							else if (classN.equals("foreignText")){
								List<Node> childNode2 = n.childNodes();
								for (Node n2 :childNode2){
	    		    				if (n2.getClass().toString().equals("class org.jsoup.nodes.TextNode")){
	    		    					vers += n2.toString();
	    		    				}
	    		    			}
	    					}
	    				}
	    			}
	    		}
	    		if (versN!="0" && vers!=""){
	    			hoofdstuk+=versN+". "+vers+" ";
	    			versN="0";
	    			vers="";
	    		}
	        }
		}
		return hoofdstuk;
	}
	
	private String cleanHTML(String html){
		String cleanHTML =html;
		cleanHTML.replace("&nbsp;", " ");
		
		return cleanHTML;
	}
}
