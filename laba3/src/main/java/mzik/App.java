package mzik;

import lombok.extern.slf4j.Slf4j;
import mzik.service.ConversationService;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.ComponentScan;

@Slf4j
@ComponentScan
public class App {


//    TODO this is laba1

    public static void main(String[] args) {
        log.info("Program started");
        var context = new AnnotationConfigApplicationContext(App.class);
        var conversationService = context.getBean(ConversationService.class);
        conversationService.doConversation();
        log.info("Program finished");
    }
}
