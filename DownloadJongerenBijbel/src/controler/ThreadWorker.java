package controler;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.UnsupportedEncodingException;

public class ThreadWorker implements Runnable{
	String url;
	public ThreadWorker(String url){
		this.url=url;
	}

	@Override
	public void run() {
		Web web = new Web();
		IO io = new IO();
		String bijbelTekst = "";
		try {
			bijbelTekst=web.accesSite(url);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		if (bijbelTekst!=null){
			try {
				io.makeFile(url, bijbelTekst);
			} catch (FileNotFoundException | UnsupportedEncodingException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		else{
			try {
				io.makeFile(url+"error", bijbelTekst);
			} catch (FileNotFoundException | UnsupportedEncodingException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			System.out.println("Error!!");
			System.out.println(url);
		}
		
	}

}
