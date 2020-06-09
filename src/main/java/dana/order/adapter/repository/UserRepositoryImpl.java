package dana.order.adapter.repository;

import dana.order.usecase.port.DatabaseMapper;
import dana.order.usecase.port.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserRepositoryImpl implements UserRepository {

    @Autowired
    DatabaseMapper databaseMapper;

    public Boolean doesUserExist(String idUser){
        if (databaseMapper.getUserExistById(idUser) < 1){
            return Boolean.FALSE;
        }
        return Boolean.TRUE;
    }

}
