package dana.order.usecase.port;

import dana.order.entity.ThirdParty;
import dana.order.entity.Transaction;
import dana.order.entity.User;
import dana.order.entity.Voucher;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Repository;

@Repository
@Mapper
public interface DatabaseMapper {
    final String getValidVoucherById = "SELECT COUNT(*) AS amount FROM vouchers WHERE expired_date > NOW() " +
            "AND is_active = 1 AND id_voucher = #{idVoucher}";
    final String getVoucherById = "SELECT * FROM vouchers WHERE id_voucher = #{idVoucher}";
    final String getUserVoucherInUse = "SELECT COUNT(*) AS amount FROM transactions WHERE id_user = #{idUser} AND id_goods = #{idVoucher} " +
            "AND id_transaction_status IN (1,4)";
    final String insertNewOrder = "INSERT INTO transactions (id_user, amount, transaction_date, is_credit, id_transaction_status, " +
            "id_payment_method, id_service, id_goods) VALUES (#{idUser}, #{amount}, NOW(), 0, 4, 1, 2, #{idVoucher})";
    final String getUserExistById = "SELECT COUNT(*) AS amount FROM users WHERE id_user = #{idUser}";
    final String getLatestUserSuccessfulTransaction = "SELECT * FROM transactions WHERE id_user = #{idUser} AND id_transaction_status = 1 " +
            "ORDER BY updated_at DESC LIMIT 1";
    final String getLatestUserInProgressTransaction = "SELECT * FROM transactions WHERE id_user = #{idUser} AND id_transaction_status = 4 " +
            "ORDER BY updated_at DESC LIMIT 1";
    final String getLatestVoucherSuccessfulTransaction = "SELECT * FROM transactions WHERE id_goods = #{idVoucher} AND id_transaction_status = 1 " +
            "ORDER BY updated_at DESC LIMIT 1";
    final String getUserById = "SELECT * FROM users WHERE id_user = #{idUser}";
    final String fallingATransaction = "UPDATE transactions SET id_transaction_status = 2 WHERE id_transaction = #{idTransaction} AND id_user = #{idUser}";
    final String getTransactionById = "SELECT * FROM transactions WHERE id_transaction = #{idTransaction}";
    final String checkATransactionExpiration = "SELECT COUNT(*) AS amount FROM transactions WHERE id_transaction = #{idTransaction} AND id_transaction_status = 4 " +
            "AND NOW() > DATE_ADD(created_at, INTERVAL 20 MINUTE)";
    final String setFinishATransaction = "UPDATE transactions SET id_transaction_status = 1 WHERE id_transaction = #{idTransaction}";
    final String checkThirdPartyExists = "SELECT COUNT(*) AS amount FROM third_parties WHERE party_code = ${partyCode}";
    final String TOPUP = "INSERT INTO transactions (id_user, amount, transaction_date, is_credit, id_transaction_status, " +
            "id_payment_method, id_service) VALUES (#{idUser}, #{amount}, NOW(), 1, 1, 2, 1)";
    final String addVirtualPayment = "INSERT INTO virtual_payments (id_transaction, virtual_number, id_third_party) VALUES " +
            "(#{idTransaction}, #{virtualNumber}, #{idThirdParty})";
    final String selectThirdPartyByCode = "SELECT * FROM third_parties WHERE party_code = #{partyCode}";

    @Select(selectThirdPartyByCode)
    @Results(value = {
            @Result(property = "idThirdParty", column = "id_third_party"),
            @Result(property = "partyName", column = "party_name"),
            @Result(property = "partyCode", column = "party_code")
    })
    ThirdParty selectThirdPartyByCode(@Param("partyCode") String partyCode);

    @Insert(addVirtualPayment)
    void addVirtualPayment(@Param("idTransaction") Integer idTransaction, @Param("virtualNumber") String virtualNumber,
                           @Param("idThirdParty") Integer idThirdParty);

    @Insert(TOPUP)
    void TOPUP(@Param("idUser") String idUser, @Param("amount") Double amount);

    @Select(checkThirdPartyExists)
    @Results(value = {
            @Result(column = "amount")
    })
    Integer checkThirdPartyExists(@Param("partyCode") String partyCode);

    @Update(setFinishATransaction)
    void setFinishATransaction(@Param("idTransaction") Integer idTransaction);

    @Select(checkATransactionExpiration)
    @Results(value = {
            @Result(column = "amount")
    })
    Integer checkATransactionExpiration(@Param("idTransaction") Integer idTransaction);

    @Select(getTransactionById)
    @Results(value = {
            @Result(property = "idTransaction", column = "id_transaction"),
            @Result(property = "idUser", column = "id_user"),
            @Result(property = "amount", column = "amount"),
            @Result(property = "transactionDate", column = "transaction_date"),
            @Result(property = "isCredit", column = "is_credit"),
            @Result(property = "idTransactionStatus", column = "id_transaction_status"),
            @Result(property = "idPaymentMethod", column = "id_payment_method"),
            @Result(property = "idService", column = "id_service"),
            @Result(property = "idGoods", column = "id_goods"),
            @Result(property = "createdAt", column = "created_at"),
            @Result(property = "updatedAt", column = "updated_at")
    })
    Transaction getTransactionById(@Param("idTransaction") Integer idTransaction);

    @Update(fallingATransaction)
    void fallingATransaction(@Param("idUser") String idUser, @Param("idTransaction") Integer idTransaction);

    @Select(getUserById)
    @Results(value = {
            @Result(property = "idUser", column = "id_user"),
            @Result(property = "balance", column = "balance"),
            @Result(property = "phoneNumber", column = "phone_number"),
            @Result(property = "createdAt", column = "created_at"),
            @Result(property = "updatedAt", column = "updated_at")
    })
    User getUserById(@Param("idUser") String idUser);

    @Select(getLatestUserSuccessfulTransaction)
    @Results(value = {
            @Result(property = "idTransaction", column = "id_transaction"),
            @Result(property = "idUser", column = "id_user"),
            @Result(property = "amount", column = "amount"),
            @Result(property = "transactionDate", column = "transaction_date"),
            @Result(property = "isCredit", column = "is_credit"),
            @Result(property = "idTransactionStatus", column = "id_transaction_status"),
            @Result(property = "idPaymentMethod", column = "id_payment_method"),
            @Result(property = "idService", column = "id_service"),
            @Result(property = "idGoods", column = "id_goods"),
            @Result(property = "createdAt", column = "created_at"),
            @Result(property = "updatedAt", column = "updated_at")
    })
    Transaction getLatestUserSuccessfulTransaction(@Param("idUser") String idUser);

    @Select(getLatestUserInProgressTransaction)
    @Results(value = {
            @Result(property = "idTransaction", column = "id_transaction"),
            @Result(property = "idUser", column = "id_user"),
            @Result(property = "amount", column = "amount"),
            @Result(property = "transactionDate", column = "transaction_date"),
            @Result(property = "isCredit", column = "is_credit"),
            @Result(property = "idTransactionStatus", column = "id_transaction_status"),
            @Result(property = "idPaymentMethod", column = "id_payment_method"),
            @Result(property = "idService", column = "id_service"),
            @Result(property = "idGoods", column = "id_goods"),
            @Result(property = "createdAt", column = "created_at"),
            @Result(property = "updatedAt", column = "updated_at")
    })
    Transaction getLatestUserInProgressTransaction(@Param("idUser") String idUser);

    @Select(getLatestVoucherSuccessfulTransaction)
    @Results(value = {
            @Result(property = "idTransaction", column = "id_transaction"),
            @Result(property = "idUser", column = "id_user"),
            @Result(property = "amount", column = "amount"),
            @Result(property = "transactionDate", column = "transaction_date"),
            @Result(property = "isCredit", column = "is_credit"),
            @Result(property = "idTransactionStatus", column = "id_transaction_status"),
            @Result(property = "idPaymentMethod", column = "id_payment_method"),
            @Result(property = "idService", column = "id_service"),
            @Result(property = "idGoods", column = "id_goods"),
            @Result(property = "createdAt", column = "created_at"),
            @Result(property = "updatedAt", column = "updated_at")
    })
    Transaction getLatestVoucherSuccessfulTransaction(@Param("idVoucher") Integer idVoucher);

    @Select(getValidVoucherById)
    @Results(value = {
            @Result(column = "amount")
    })
    Integer getValidVoucherById(@Param("idVoucher") Integer idVoucher);

    @Select(getVoucherById)
    @Results(value = {
            @Result(property = "idVoucher", column = "id_voucher"),
            @Result(property = "idMerchant", column = "id_merchant"),
            @Result(property = "voucherName", column = "voucher_name"),
            @Result(property = "voucherPrice", column = "voucher_price"),
            @Result(property = "maxDiscountPrice", column = "max_discount_price"),
            @Result(property = "discount", column = "discount"),
            @Result(property = "voucherQuantity", column = "voucher_quantity"),
            @Result(property = "is_active", column = "isActive"),
            @Result(property = "expiredDate", column = "expired_date"),
            @Result(property = "createdAt", column = "created_at"),
            @Result(property = "updatedAt", column = "updated_at")
    })
    Voucher getVoucherById(@Param("idVoucher") Integer idVoucher);

    @Select(getUserVoucherInUse)
    @Results(value = {
            @Result(column = "amount")
    })
    Integer getUserVoucherInUse(@Param("idUser") String idUser, @Param("idVoucher") Integer idVoucher);

    @Insert(insertNewOrder)
    void insertNewOrder(@Param("idUser") String idUser, @Param("amount") Double amount, @Param("idVoucher") Integer idVoucher);

    @Select(getUserExistById)
    @Results(value = {
            @Result(column = "amount")
    })
    Integer getUserExistById(@Param("idUser") String idUser);
}
