package fly.kasane.banana.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import fly.kasane.banana.entity.User;
import org.apache.ibatis.annotations.Param;

import java.util.List;


public interface UserMapper extends BaseMapper<User> {

    List<User> list(IPage<User> page, @Param("entity")User entity);
}
