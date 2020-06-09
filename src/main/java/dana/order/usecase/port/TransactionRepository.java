package dana.order.usecase.port;

public interface TransactionRepository {
    Boolean validateBalanceConsistency(String idUser);
    Boolean validateVoucherConsistency(Integer idVoucher);
    Boolean checkATransactionExpiration(Integer idTransaction);
    void setFinishATransaction(Integer idTransaction);
    void broadcastATransaction(Integer idTransaction);
    Integer getPartyCode(Integer virtualNumber);
    Boolean checkTOPUPThirdParty(Integer partyCode);
    void TOPUPBalance(String idUser, Double amount, Integer virtualNumber, Integer partyCode);
}
