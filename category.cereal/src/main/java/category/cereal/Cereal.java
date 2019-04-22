package category.cereal;

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
import client.IHttpClient;
import category.entity.Category;
import category.entity.Product;
import exceptions.CoreException;

@Component
public class Cereal implements ICategoryProvider{
    private final String id="3";
    private final String name="Grains/Cereals";
    private IHttpClient httpClient;
    
    public Cereal(@Qualifier("springHttpClient") IHttpClient httpClient) {
		super();
		this.httpClient = httpClient;
	}
    
	public Category getCategory() {
		Category beverage=new Category(id, name, "Breads, crackers, pasta, and cereal");
		return beverage;
	}

	public List<Product> getProducts() {
		String url=String.format("https://services.odata.org/Northwind/Northwind.svc/Products?$format=json&$filter=CategoryID eq %s", id);
        String jsonResponse="";
        Map<String, String> queryParams=new HashMap<String, String>();
        queryParams.put("$format", "json");
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

	@Override
	public String getName() {
		return name;
	}

}
