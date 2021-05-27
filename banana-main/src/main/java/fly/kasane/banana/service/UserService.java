package fly.kasane.banana.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import fly.kasane.banana.base.BaseService;
import fly.kasane.banana.entity.User;

public interface UserService extends BaseService<User> {

    IPage<User> list(IPage<User> page,  User user);
}
