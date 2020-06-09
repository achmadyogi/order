package dana.order.adapter.repository;

import dana.order.entity.Voucher;
import dana.order.usecase.port.DatabaseMapper;
import dana.order.usecase.port.VoucherRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class VoucherRepositoryImpl implements VoucherRepository {

    @Autowired
    DatabaseMapper databaseMapper;

    public Boolean validateExpiration(Integer idVoucher){
        if (databaseMapper.getValidVoucherById(idVoucher) < 1){
            return Boolean.FALSE;
        }
        return Boolean.TRUE;
    }

    public Boolean validateQuantity(Integer idVoucher){
        Voucher voucher = databaseMapper.getVoucherById(idVoucher);
        if (voucher.getVoucherQuantity() < 1){
            return Boolean.FALSE;
        }
        return Boolean.TRUE;
    }

    public void insertNewOrder(String idUser, Integer idVoucher){
        Voucher voucher = databaseMapper.getVoucherById(idVoucher);
        databaseMapper.insertNewOrder(idUser, voucher.getVoucherPrice(), idVoucher);
    }

    public void broadcastNewOrder(String idUser, Integer idVoucher){

    }
}
