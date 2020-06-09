package dana.order.usecase.port;

public interface UserRepository {
    Boolean doesUserExist(String idUser);
}
