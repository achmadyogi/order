package dana.order.usecase.port;

public interface VoucherRepository {
    Boolean validateExpiration(Integer idVoucher);
    Boolean validateQuantity(Integer idVoucher);
    void insertNewOrder(String idUser, Integer idVoucher);
    void broadcastNewOrder(String idUser, Integer idVoucher);
}
