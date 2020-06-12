package dana.order.adapter.repository;

import dana.order.entity.ThirdParty;
import dana.order.entity.Transaction;
import dana.order.entity.User;
import dana.order.entity.Voucher;
import dana.order.usecase.port.DatabaseMapper;
import dana.order.usecase.port.TransactionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TransactionRepositoryImpl implements TransactionRepository {

    @Autowired
    DatabaseMapper databaseMapper;

    public Boolean validateBalanceConsistency(String idUser){

        Transaction transaction = databaseMapper.getLatestUserSuccessfulTransaction(idUser);
        User user = databaseMapper.getUserById(idUser);

        if (transaction != null && transaction.getUpdatedAt().after(user.getUpdatedAt())){
            return Boolean.FALSE;
        }

        return Boolean.TRUE;
    }

    public Boolean validateVoucherConsistency(Integer idVoucher){

        Transaction transaction = databaseMapper.getLatestVoucherSuccessfulTransaction(idVoucher);
        Voucher voucher = databaseMapper.getVoucherById(idVoucher);

        if (transaction != null && transaction.getUpdatedAt().after(voucher.getUpdatedAt())){
            return Boolean.FALSE;
        }
        return Boolean.TRUE;
    }

    public Boolean checkATransactionExpiration(Integer idTransaction){

        if (databaseMapper.checkATransactionExpiration(idTransaction) > 0){
            return Boolean.FALSE;
        }
        return Boolean.TRUE;
    }

    public void setFinishATransaction(Integer idTransaction){
        databaseMapper.setFinishATransaction(idTransaction);
    }

    public Boolean checkTOPUPThirdParty(String partyCode){
        if (databaseMapper.checkThirdPartyExists(partyCode) == 0){
            return Boolean.FALSE;
        }
        return Boolean.TRUE;
    }

    public void TOPUPBalance(String idUser, Double amount, String virtualNumber, String partyCode){
        databaseMapper.TOPUP(idUser, amount);
        Transaction transaction = databaseMapper.getLatestUserSuccessfulTransaction(idUser);
        ThirdParty thirdParty = databaseMapper.selectThirdPartyByCode(partyCode);
        databaseMapper.addVirtualPayment(transaction.getIdTransaction(), virtualNumber, thirdParty.getIdThirdParty());
    }

    public String getPartyCode(String virtualNumber){
        return virtualNumber.substring(0,4);
    }

    public String getPhoneNumberFromVA(String virtualNumber){
        return (""+virtualNumber).substring(4);
    }
}
