import com.blog.mail.service.impl.SpringMail;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration({"/Spring/applicationContext.xml"})
public class MailTest {
    @Autowired
    SpringMail springMail;
    @Test
    public void sendMail(){
        /*ApplicationContext applicationContext=new ClassPathXmlApplicationContext("classpath:Spring/applicationContext.xml");
        SpringMail springMail=applicationContext.getBean("springMail");
        */springMail.sendMail();
    }
}
