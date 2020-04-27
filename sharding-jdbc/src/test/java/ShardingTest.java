import com.sharding.ShardingDemo;
import com.sharding.mapper.ShardingMapper;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.math.BigDecimal;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = {ShardingDemo.class})
public class ShardingTest {
    
    @Autowired
    ShardingMapper shardingMapper;


    @Test
    public void test(){
        for (int i = 1; i <= 10; i++) {
            shardingMapper.add(new BigDecimal(i*6), Long.valueOf(i + ""),"haha" + i);
        }
    }
}
