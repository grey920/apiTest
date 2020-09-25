package VillageWeather;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

public class VillageWeatherParsing {

	public static void main(String[] args) {

		try {
			String nx = "60"; //경도
			String ny = "126"; //위도
			String baseDate = "20200925";  // 조회하고 싶은 날짜
			String baseTime = "0500"; // 조회하고 싶은 시간대
			String serviceKey = "MRFX%2BpT7TIoeSUyOu69lbpjnqJGwY9Bjfuxgmwytmfv7vEFw054uBjRE6NUJtP3H5gMfgja%2Bc1yDZlqYrQuFJA%3D%3D";
			String urlStr = "http://apis.data.go.kr/1360000/VilageFcstInfoService/getVilageFcst?serviceKey="+serviceKey+"&pageNo=1&numOfRows=50&dataType=JSON&base_date=20200925&base_time=0500&nx="+nx+"&ny="+ny;
			URL url = new URL(urlStr); // 위 urlStr을 이용해서 URL 객체를 만든다
			
			String line="";
			String result = "";
			
			// 날씨 정보를 받아온다
			BufferedReader bf = new BufferedReader(new InputStreamReader(url.openStream()));
			
			// 버퍼에 있는 정보를 하나의 문자열로 변환
			while((line = bf.readLine()) != null) {
				result = result.concat(line);
				//System.out.println(result);
			}
			// JsonParser를 만들어 읽어온 문자열 데이터를 객체화한다
			JSONParser parser = new JSONParser();
			JSONObject obj = (JSONObject) parser.parse(result);
			
			// Top레벨 단계인 response 키를 가지고 데이터를 파싱
			JSONObject parse_response = (JSONObject) obj.get("response");
			// response로부터 body를 가져온다
			JSONObject parse_body = (JSONObject) parse_response.get("body");
			// body로부터 items를 가져온다
			JSONObject parse_items = (JSONObject)parse_body.get("items");
			// items로부터 itemlist를 받아온다. itemlist는 [로 시작하므로 JSONArray로 담는다
			JSONArray parse_item = (JSONArray) parse_items.get("item");
			
			String category;
			JSONObject weather;
			
			// 필요한 데이터만 가져온다
			for(int i = 0; i < parse_item.size(); i++) {
				weather = (JSONObject) parse_item.get(i);
				//String base_Date = (String)weather.get("baseDate"); 
				//String fcst_Time = (String)weather.get("fcstDate");
				double fcst_Value = (Long.valueOf((String) weather.get("fcstValue"))).doubleValue();
				//String nX = (String)weather.get("nx"); 
				//String nY = (String)weather.get("ny"); 
				category = (String)weather.get("category"); 
				//String base_Time = (String)weather.get("baseTime"); 
				//String fcscDate = (String)weather.get("fcscDate");

				// 출력하기
				System.out.print("배열의 "+i+"번째 요소");
				System.out.print("  category : "+ category);
				System.out.print("  fcst_Value : "+ fcst_Value);
				System.out.println();
				
			}// for
			
			bf.close();
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
	}

}
