package controler;

import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;

public class IO {
	public void makeFile(String nameFile,String tekst) throws FileNotFoundException, UnsupportedEncodingException{
		String[] naamBestand = nameFile.split("/");
		PrintWriter writer = new PrintWriter("bijbel/"+naamBestand[naamBestand.length-2]+" "+naamBestand[naamBestand.length-1]+".txt");
		writer.print(tekst);
		writer.close();
		
	}
}
