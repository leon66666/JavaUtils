package zhongqiu.javautils.test;

import zhongqiu.javautils.http.HttpRequest;

public class HttpTest {
	public static void main(String[] args) {
		// demo:代理访问
		// String url = "http://api.adf.ly/api.php";
		// String para =
		// "key=youkeyid&youuid=uid&advert_type=int&domain=adf.ly&url=http://somewebsite.com";
		//
		// String sr = HttpRequest.sendPost(url, para, true);
		// System.out.println(sr);

		String getUrl = "http://msg1.niuguwang.com/api/bbslikeme.ashx?index=1&size=20&usertoken=yFqkAGaWTyMpqgqe4B5iApR2WqXDWpLLvS-VLxhzDsvMRTwgpnlQTTrSOe0b1OEe";
		String repense = HttpRequest.sendPost("http://msg1.niuguwang.com/api/bbslikeme.ashx",
				"index=1&size=20&usertoken=yFqkAGaWTyMpqgqe4B5iApR2WqXDWpLLvS-VLxhzDsvMRTwgpnlQTTrSOe0b1OEe",false);
		System.out.println(repense);
	}
}
