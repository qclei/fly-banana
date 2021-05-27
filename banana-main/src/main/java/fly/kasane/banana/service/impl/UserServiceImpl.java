package fly.kasane.banana.service.impl;



import com.baomidou.mybatisplus.core.metadata.IPage;
import fly.kasane.banana.base.BaseServiceImpl;
import fly.kasane.banana.entity.User;
import fly.kasane.banana.mapper.UserMapper;
import fly.kasane.banana.service.UserService;
import org.springframework.stereotype.Service;


@Service
public class UserServiceImpl extends BaseServiceImpl<UserMapper, User> implements UserService {

    @Override
    public IPage<User> list(IPage<User> page, User user) {
        return page.setRecords(baseMapper.list(page, user));
    }

}
