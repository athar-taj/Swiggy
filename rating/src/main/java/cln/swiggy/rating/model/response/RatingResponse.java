package cln.swiggy.rating.model.response;

import lombok.Data;

@Data
public class RatingResponse {

    private String userName;
    private String name;
    private int rating;
    private String comment;

}
