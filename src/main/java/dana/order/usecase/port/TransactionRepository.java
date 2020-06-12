package dana.order.usecase.port;

public interface TransactionRepository {
    Boolean validateBalanceConsistency(String idUser);
    Boolean validateVoucherConsistency(Integer idVoucher);
    Boolean checkATransactionExpiration(Integer idTransaction);
    void setFinishATransaction(Integer idTransaction);
    String getPartyCode(String virtualNumber);
    Boolean checkTOPUPThirdParty(String partyCode);
    void TOPUPBalance(String idUser, Double amount, String virtualNumber, String partyCode);
    String getPhoneNumberFromVA(String virtualNumber);
}
