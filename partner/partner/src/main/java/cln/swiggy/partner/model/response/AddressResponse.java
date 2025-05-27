package cln.swiggy.partner.model.response;

import cln.swiggy.partner.model.Address;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AddressResponse {
        private String outlet;
        private String address;
        private String city;
        private String state;
        private String pincode;

        public static AddressResponse convertToResponse(Address address) {
                AddressResponse response = new AddressResponse();
                response.outlet = address.getOutlet();
                response.address = address.getAddress();
                response.city = address.getCity();
                response.state = address.getState();
                response.pincode = address.getPincode();
                return response;
        }
}
