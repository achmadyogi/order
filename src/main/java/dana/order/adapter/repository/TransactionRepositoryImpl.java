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

    public Boolean checkTOPUPThirdParty(Integer partyCode){
        if (databaseMapper.checkThirdPartyExists(partyCode) == 0){
            return Boolean.FALSE;
        }
        return Boolean.TRUE;
    }

    public void TOPUPBalance(String idUser, Double amount, Integer virtualNumber, Integer partyCode){
        databaseMapper.TOPUP(idUser, amount);
        Transaction transaction = databaseMapper.getLatestUserSuccessfulTransaction(idUser);
        ThirdParty thirdParty = databaseMapper.selectThirdPartyByCode(partyCode);
        databaseMapper.addVirtualPayment(transaction.getIdTransaction(), virtualNumber, thirdParty.getIdThirdParty());
    }

    public Integer getPartyCode(Integer virtualNumber){
        return Integer.valueOf((""+virtualNumber).charAt(0)+(""+virtualNumber).charAt(1)+(""+virtualNumber).charAt(2)+(""+virtualNumber).charAt(3));
    }

    public void broadcastATransaction(Integer idTransaction){

    }
}
