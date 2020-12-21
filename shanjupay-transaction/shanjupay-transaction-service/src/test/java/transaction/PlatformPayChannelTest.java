package transaction;

import com.shanjupay.transaction.TransactionBootstrap;
import com.shanjupay.transaction.api.PayChannelService;
import com.shanjupay.transaction.mapper.PlatformPayChannelMapper;
import lombok.extern.slf4j.Slf4j;
import org.apache.dubbo.config.annotation.Reference;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * @author mercurius
 * @version 1.0
 * @date 2020/11/27 17:31
 */
@SpringBootTest
@ContextConfiguration(classes = TransactionBootstrap.class)
@RunWith(SpringJUnit4ClassRunner.class)
@Slf4j
public class PlatformPayChannelTest {

    @Reference
    PayChannelService payChannelService;


    @Test
    public void selectPayChannelByPlatformChannelTest(){
        payChannelService.queryPayChannelByPlatformChannel("shanju_c2b");
    }
}
