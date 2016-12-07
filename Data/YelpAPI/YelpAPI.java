import java.io.FileWriter;
import java.io.IOException;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.scribe.builder.ServiceBuilder;
import org.scribe.model.OAuthRequest;
import org.scribe.model.Response;
import org.scribe.model.Token;
import org.scribe.model.Verb;
import org.scribe.oauth.OAuthService;


/**
 * Adapted from YelpAPI v2.0, collect businuess information from
 * yelp and write out a json file
 */
public class YelpAPI {

  private static int offset = 0;
  private static JSONArray allBusinesses = new JSONArray();
  
  // the query address
  private static String query = "https://api.yelp.com/v2/search/?location=19104&limit=20"
  										+ "&radius_filter=1600&category_filter=restaurants&offset=";
  /*
   * KEYs are excluded
   */
  private static final String CONSUMER_KEY = "";
  private static final String CONSUMER_SECRET = "";
  private static final String TOKEN = "";
  private static final String TOKEN_SECRET = "";

  OAuthService service;
  Token accessToken;

  /**
   * Setup the Yelp API OAuth credentials.
   *
   * @param consumerKey Consumer key
   * @param consumerSecret Consumer secret
   * @param token Token
   * @param tokenSecret Token secret
   */
  public YelpAPI(String consumerKey, String consumerSecret, String token, String tokenSecret) {
    this.service =
        new ServiceBuilder().provider(TwoStepOAuth.class).apiKey(consumerKey)
            .apiSecret(consumerSecret).build();
    this.accessToken = new Token(token, tokenSecret);
  }

  /**
   * Creates and sends a request to the Search API by term and location.
   */
  public String searchForBusinessesByLocation() {
    OAuthRequest request = new OAuthRequest(Verb.GET, query + offset);
    return sendRequestAndGetResponse(request);
  }

  /**
   * Sends an {@link OAuthRequest} and returns the {@link Response} body.
   *
   * @param request {@link OAuthRequest} corresponding to the API request
   * @return <tt>String</tt> body of API response
   */
  private String sendRequestAndGetResponse(OAuthRequest request) {
    System.out.println("Querying " + request.getCompleteUrl() + " ...");
    this.service.signRequest(this.accessToken, request);
    Response response = request.send();
    return response.getBody();
  }

  /**
   * Queries the Search API based on the command line arguments and takes the first result to query
   * the Business API.
   *
   * @param yelpApi <tt>YelpAPI</tt> service instance
   * @throws IOException 
   * @throws ParseException 
   */
  private static void queryAPI(YelpAPI yelpApi) throws IOException, ParseException {
    String searchResponseJSON =
        yelpApi.searchForBusinessesByLocation();
    
    JSONParser parser = new JSONParser();
    JSONObject response = null;
    try {
      response = (JSONObject) parser.parse(searchResponseJSON);
    } catch (ParseException pe) {
      System.out.println("Error: could not parse JSON response:");
      System.out.println(searchResponseJSON);
      System.exit(1);
    }
    //System.out.println(response);
    JSONArray businesses = (JSONArray) response.get("businesses");
    allBusinesses.addAll(businesses);
    System.out.println(allBusinesses.size());
  }

  /**
   * Main entry for sample Yelp API requests.
   * @throws IOException 
   * @throws ParseException 
   */
  public static void main(String[] args) throws IOException, ParseException {
    YelpAPI yelpApi = new YelpAPI(CONSUMER_KEY, CONSUMER_SECRET, TOKEN, TOKEN_SECRET);
    while(offset < 236){
    	queryAPI(yelpApi);
    	offset = offset + 20;
    }
    System.out.println(allBusinesses.size());
    
    FileWriter file = new FileWriter("test.json");
    
    for (int i = 0; i < allBusinesses.size(); i++){
    	file.write(allBusinesses.get(i).toString());
    	file.write("\n");
    }
    file.close();
    
  }
}
