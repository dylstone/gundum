package web.api;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.InetAddress;
import java.net.URL;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import play.mvc.Http;

public class RequestWrapper {
	public static void main(String[] args) {
		RequestWrapper rw = new RequestWrapper();
		rw.sentRequest("Submit data");
	}

	public void sentRequest(String command) {
		if (command.equals("Submit data")) {
			try {
				HttpURLConnection con = null;

				// URLの作成
				URL url = new URL("http://127.0.0.1:9000/");

				// 接続用HttpURLConnectionオブジェクト作成
				con = (HttpURLConnection) url.openConnection();
				// リクエストメソッドの設定
				con.setRequestMethod("GET");
				// リダイレクトを自動で許可しない設定
				con.setInstanceFollowRedirects(false);
				// ヘッダーの設定(複数設定可能)
				con.setRequestProperty("Accept-Language", "jp");

				// 接続
				con.connect();
				System.out.println(con.getURL());
				
				Map<String, List<String>> headers = con.getHeaderFields();
				Iterator<String> headerIt = headers.keySet().iterator();
				String header = null;
				while (headerIt.hasNext()) {
					String headerKey = (String) headerIt.next();
					header += headerKey + "：" + headers.get(headerKey) + "\r\n";
				}
				System.out.println(header);

				// 本文の取得
				BufferedReader buffredReader = new BufferedReader(
						new InputStreamReader(con.getInputStream()));
				String inputLine;
				while ((inputLine = buffredReader.readLine()) != null)
					System.out.println(inputLine);
				buffredReader.close();
			} catch (IOException t) {
				t.printStackTrace();
				System.out.println(t.getStackTrace());
				// openConnection() failed
				// ...
			}
			Http.Context.current().request().host();
			Http.Request httpRequest = Http.Context.current().request();
			String host=Http.Context.current().request().host();
			
//			InetAddress.getLocalHost();
		}
	}
}
