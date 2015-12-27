package main;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import controler.ThreadWorker;

public class Main {
	public static void main(String [] args)
	{
		String[] bijbelboeken =		{"Genesis","Exodus","Leviticus","Numeri","Deuteronomium","Jozua","Rechters","Ruth","1-Samuel","2-Samuel","1-Koningen","2-Koningen","1-Kronieken","2-Kronieken","Ezra","Nehemia","Ester","Job","Psalmen","Spreuken","Prediker","Hooglied","Jesaja","Jeremia","Klaagliederen","Ezechiël","Daniël","Hosea","Joël","Amos","Obadja","Jona","Micha","Nahum","Habakuk","Sefanja","Haggai","Zacharia","Maleachi","Matteüs","Marcus","Lucas","Johannes","Handelingen","Romeinen","1-Korintiërs","2-Korintiërs","Galaten","Efeziërs","Filippenzen","Kolossenzen","1-Tessalonicenzen","2-Tessalonicenzen","1-Timoteüs","2-Timoteüs","Titus","Filemon","Hebreeërs","Jakobus","1-Petrus","2-Petrus","1-Johannes","2-Johannes","3-Johannes","Judas","Openbaring"};
		String[] hoofdstuknummers = {"50","40","27","36","34","24","21","4","31","24","22","25","29","36","10","13","10","42","150","31","12","8","66","52","5","48","12","14","4","9","1","4","7","3","3","3","2","14","3","28","16","24","21","28","16","16","13","6","6","4","4","5","3","6","4","3","1","13","5","5","3","5","1","1","1","22"};
		Integer count = 0;
		// tellen hoeveel hoofdstukken er zijn in de bijbel.
		for (int i = 0; i < hoofdstuknummers.length; i++) {
			count +=Integer.parseInt(hoofdstuknummers[i]);
		}
		String standaardUrl = "https://bijbel.eo.nl/bijbel/";
		String[] url = new String[count];
		// lijst met bijbel boeken maken
		count =0;
		if (bijbelboeken.length==hoofdstuknummers.length){
			for (int i = 0; i < bijbelboeken.length; i++) {
				// eerst kleine letters en dubbele punten weghalen
				bijbelboeken[i] = bijbelboeken[i].toLowerCase();
				for (int j = 0; j < Integer.parseInt(hoofdstuknummers[i]); j++) {
					url[count] =standaardUrl+bijbelboeken[i]+"/"+(j+1);
					count++;
				}
			}
		}
		ExecutorService executor = Executors.newFixedThreadPool(3);
		for (int i = 0; i < url.length; i++) {
			Runnable t = new ThreadWorker(url[i]);
			executor.execute(t);
		}
		executor.shutdown();
        while (!executor.isTerminated()) {}
        System.out.println("Finished all threads");

	}
}
