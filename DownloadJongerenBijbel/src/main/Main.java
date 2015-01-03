package main;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.UnsupportedEncodingException;

import controler.IO;
import controler.Web;

public class Main {
	public static void main(String [] args)
	{
		Web web = new Web();
		IO io = new IO();
		String[] bijbelboeken =		{"Genesis","Exodus","Leviticus","Numeri","Deuteronomium","Jozua","Rechters","Ruth","1-Samuel","2-Samuel","1-Koningen","2-Koningen","1-Kronieken","2-Kronieken","Ezra","Nehemia","Ester","Job","Psalmen","Spreuken","Prediker","Hooglied","Jesaja","Jeremia","Klaagliederen","Ezechi�l","Dani�l","Hosea","Jo�l","Amos","Obadja","Jona","Micha","Nahum","Habakuk","Sefanja","Haggai","Zacharia","Maleachi","Matte�s","Marcus","Lucas","Johannes","Handelingen","Romeinen","1-Korinti�rs","2-Korinti�rs","Galaten","Efezi�rs","Filippenzen","Kolossenzen","1-Tessalonicenzen","2-Tessalonicenzen","1-Timote�s","2-Timote�s","Titus","Filemon","Hebree�rs","Jakobus","1-Petrus","2-Petrus","1-Johannes","2-Johannes","3-Johannes","Judas","Openbaring"};
		String[] hoofdstuknummers ={"50","40","27","36","34","24","21","4","31","24","22","25","29","36","10","13","10","42","150","31","12","8","66","52","5","48","12","14","4","9","1","4","7","3","3","3","2","14","3","28","16","24","21","28","16","16","13","6","6","4","4","5","3","6","4","3","1","13","5","5","3","5","1","1","1","22"};
		Integer count = 0;
		// tellen hoeveel hoofdstukken er zijn in de bijbel.
		for (int i = 0; i < hoofdstuknummers.length; i++) {
			count +=Integer.parseInt(hoofdstuknummers[i]);
		}
		String standaardUrl = "http://www.jongerenbijbel.nl/bijbel/lezen/";
		String[] url = new String[count];
		// lijst met bijbel boeken maken
		count =0;
		if (bijbelboeken.length==hoofdstuknummers.length){
			for (int i = 0; i < bijbelboeken.length; i++) {
				// eerst kleine letters en dubbele punten weghalen
				bijbelboeken[i] = bijbelboeken[i].toLowerCase();
				bijbelboeken[i] = bijbelboeken[i].replaceAll("�", "e");
				bijbelboeken[i] = bijbelboeken[i].replaceAll("�", "u");
				for (int j = 0; j < Integer.parseInt(hoofdstuknummers[i]); j++) {
					url[count] =standaardUrl+bijbelboeken[i]+"/"+(j+1);
					count++;
				}
			}
		}
		//String[] url = {"http://www.jongerenbijbel.nl/bijbel/lezen/genesis/1"};
		for (int i = 0; i < url.length; i++) {
			String bijbelTekst = "";
			try {
				bijbelTekst=web.accesSite(url[i]);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			if (bijbelTekst!=null){
				try {
					io.makeFile(url[i], bijbelTekst);
					System.out.println("bestand gemaakt");
				} catch (FileNotFoundException | UnsupportedEncodingException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			else{
				try {
					io.makeFile(url[i]+"error", bijbelTekst);
				} catch (FileNotFoundException | UnsupportedEncodingException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				System.out.println("Error!!");
				System.out.println(url[i]);
			}
		}
	}
}
