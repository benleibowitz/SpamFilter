package test;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.AutowireCapableBeanFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import data.Word;
import email.Email;
import email.ProbabilityCalculator;

public class test {
    private ProbabilityCalculator probCalc;
    
    @Autowired
    public void setProbabilityCalculator(ProbabilityCalculator probCalc) {
        this.probCalc = probCalc;
    }
    
    public static void main(String[] args) {
        try {
            loadConfigProperties("resources/application.properties");
        } catch (IOException e) {
            e.printStackTrace();
        }
        
        test t = new test();
        
        ApplicationContext context = new ClassPathXmlApplicationContext(
                "resources/emailbeans.xml");
        context.getAutowireCapableBeanFactory().autowireBeanProperties(t,
                AutowireCapableBeanFactory.AUTOWIRE_BY_TYPE, true);

        Email email = new Email("NewSexAlert", "want to be my new bangbuddy",
                "foo");
        System.out.println(t.probCalc.isSpam(email));

        ((ClassPathXmlApplicationContext) context).close();
    }

    public static void loadConfigProperties(String propertiesFileURL) throws IOException {
        Properties systemProperties = System.getProperties();
        Properties configProperties = new Properties();
        
        InputStream iStream = test.class.getClassLoader().getResourceAsStream(propertiesFileURL);
        configProperties.load(iStream);
        
        for(Object key : configProperties.keySet()) {
            systemProperties.put(key, configProperties.get(key));
        }
        
    }
}
