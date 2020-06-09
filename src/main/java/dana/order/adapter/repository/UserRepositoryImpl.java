package dana.order.adapter.repository;

import dana.order.entity.User;
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

    public Boolean doesPhoneNumberCorrect(String idUser, String phoneNumber){
        User user = databaseMapper.getUserById(idUser);
        String newPhone = "+62"+phoneNumber.substring(1);
        if (!user.getPhoneNumber().equals(newPhone)){
            return Boolean.FALSE;
        }
        return Boolean.TRUE;
    }

}
