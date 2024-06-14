package in.amit.service;

import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.databind.ObjectMapper;

import in.amit.binding.Quote;
import in.amit.properties.AppProps;

@Service
public class DashboardServiceImpl implements DashboardService {
	
	@Autowired
	private AppProps appProps;

	Quote[] quotes = null;
	
	@Override
	public String getQuote() {
		if (quotes == null) {
			String apiUrl = appProps.getMessages().get("quoteEndpointUrl");
			RestTemplate rt = new RestTemplate();
			ResponseEntity<String> forEntity = rt.getForEntity(apiUrl, String.class);
			String body = forEntity.getBody();

			ObjectMapper mapper = new ObjectMapper();
			try {
				quotes = mapper.readValue(body, Quote[].class);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		Random random = new Random();
		int randomIndex = random.nextInt(quotes.length - 1);
		Quote randomQuote = quotes[randomIndex];
		return randomQuote.getText();
	}

}
