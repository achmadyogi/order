package dana.order.usecase.port;

public interface UserRepository {
    Boolean doesUserExist(String idUser);
    Boolean doesPhoneNumberCorrect(String idUser, String phoneNumber);
}
