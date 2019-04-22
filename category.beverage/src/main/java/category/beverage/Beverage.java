package category.beverage;

import java.lang.reflect.Type;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Component;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.reflect.TypeToken;

import api.ICategoryProvider;
import category.entity.Category;
import category.entity.Product;
import exceptions.CoreException;
import client.IHttpClient;

@Component
public class Beverage implements ICategoryProvider{
	private final String id="1";
	private final String name="Beverages";
    private IHttpClient httpClient;
    
    public Beverage(@Qualifier("apacheHttpClient") IHttpClient httpClient) {
		super();
		this.httpClient = httpClient;
	}


	public Category getCategory() {
		Category beverage=new Category(id, name, "Soft drinks, coffees, teas, beers, and ales");
		return beverage;
	}

	public List<Product> getProducts() {
		String url="https://services.odata.org/Northwind/Northwind.svc/Products";
        String jsonResponse="";
        Map<String, String> queryParams=new HashMap<String, String>();
        queryParams.put("$format", "json");
        queryParams.put("$filter", "CategoryID eq 1");
        try {
        	Type typeToken = new TypeToken<List<Product>>() { }.getType();
            jsonResponse = httpClient.request(url, HttpMethod.GET,
            		 Collections.<String, String>emptyMap(),queryParams,null);
            JsonObject response =new Gson().fromJson(jsonResponse, JsonObject.class);
            jsonResponse = response.get("value").toString();
            return new Gson().fromJson(jsonResponse, typeToken);
        } catch (CoreException e) {
            e.printStackTrace();
        }
		return null;
	}


	public String getName() {
		return name;
	}

	
	
}
