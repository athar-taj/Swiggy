package cln.swiggy.user.model.response;

import cln.swiggy.user.model.User;
import cln.swiggy.user.model.enums.AddressType;
import lombok.Data;

@Data
public class AddressResponse {
    private User user;
    private AddressType addressType;
    private String address;
    private String city;
    private String state;
    private String pincode;
}
