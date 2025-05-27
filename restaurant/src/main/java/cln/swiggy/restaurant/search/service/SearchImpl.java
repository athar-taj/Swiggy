package cln.swiggy.restaurant.search.service;

import cln.swiggy.restaurant.model.response.CommonResponse;
import cln.swiggy.restaurant.service.CategoryService;
import cln.swiggy.restaurant.service.MenuService;
import cln.swiggy.restaurant.service.RestaurantService;
import co.elastic.clients.elasticsearch.ElasticsearchClient;
import co.elastic.clients.elasticsearch.core.SearchResponse;
import co.elastic.clients.elasticsearch.core.search.Hit;
import co.elastic.clients.json.JsonData;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class SearchImpl {

    @Autowired ElasticsearchClient elasticsearchClient;
    @Autowired CategoryService categoryService;
    @Autowired RestaurantService restaurantService;
    @Autowired MenuService menuService;

    public ResponseEntity<CommonResponse> searchElastic(String keyword) throws IOException {
        SearchResponse<JsonData> response = elasticsearchClient.search(s -> s
                        .index("restaurants")
                        .query(q -> q
                                .queryString(qs -> qs
                                        .query(keyword)
                                )
                        ),
                JsonData.class
        );

        List<Map<String, Object>> results = new ArrayList<>();

        for (Hit<JsonData> hit : response.hits().hits()) {
            JsonNode jsonNode = hit.source().to(JsonNode.class);
            Map<String, Object> map = new ObjectMapper().convertValue(jsonNode, Map.class);
            Map<String, Object> source = new HashMap<>();
            System.out.println(map.get("label") + " " + map.get("elementId"));

            if(map.get("label").equals("Category")){
                source.put("elementId", map.get("elementId"));
                source.put("label", map.get("label"));

                ResponseEntity<CommonResponse> responseEntity = categoryService.getCategoryById(Long.valueOf(map.get("elementId").toString()));
                source.put("data",responseEntity.getBody().getData());

            }else if(map.get("label").equals("Restaurant")){
                source.put("elementId", map.get("elementId"));
                source.put("label", map.get("label"));

                ResponseEntity<CommonResponse> responseEntity = restaurantService.getRestaurantById(Long.valueOf(map.get("elementId").toString()));
                source.put("data",responseEntity.getBody().getData());
            }else if(map.get("label").equals("Dish")){

                source.put("elementId", map.get("elementId"));
                source.put("label", map.get("label"));

                ResponseEntity<CommonResponse> responseEntity = menuService.getMenuItem(Long.valueOf(map.get("elementId").toString()));
                source.put("data",responseEntity.getBody().getData());
            }
            else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new CommonResponse(404, "Not Found !!", null));
            }
            results.add(source);
        }

        return ResponseEntity.ok(new CommonResponse(200, "Data Fetched !!", results));
    }
}
