package cln.swiggy.notification.model.request;

public interface UserRequest {
    String getName();
    String getPhoneNumber();
    String getEmail();
    Double getLatitude();
    Double getLongitude();
}