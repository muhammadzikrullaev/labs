package mzik.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import mzik.dao.Dao;
import mzik.entity.Ticket;
import org.springframework.stereotype.Service;


@Service
@RequiredArgsConstructor
@Slf4j
public class ConversationServiceImpl implements ConversationService{

    private final MessageService messageService;
    private final IOService ioService;
    private final Dao<Ticket> ticketDao;
    private boolean exit = false;

    public void doConversation(){
        log.info("greeting user...");
        localizePrintln("conversation.hello");

        log.info("start getting command");
        while (!exit) {
            String command = getCommand();

            log.info("commad got: {}", command);

            switch (command) {
                case "find-all" -> findAll();
                case "find-by-id" -> findById();
                case "exit" -> exit = true;
            }
        }

        localizePrintln("conversation.exit");
        log.info("exit from conversation");
    }

    private void findById() {
        log.info("start finding by id");
        localizePrint("conversation.get-id");
        var ticketId = ioService.nextInt();
        log.info("got id from user: {}", ticketId);

        localizePrintln("conversation.output");

        ticketDao.findById(ticketId).ifPresentOrElse(
                ioService::println,
                () -> {
                    localizePrint("conversation.no-such-ticket");
                }
        );
        log.info("end of finding by id");

    }

    private void findAll() {
        log.info("start finding all");
        var tickets = ticketDao.findAll();
        localizePrintln("conversation.output");
        tickets.forEach(ioService::println);
        log.info("end of finding all");
    }

    public String getCommand() {
        log.info("getting command");
        localizePrint("conversation.enter-command");
        return ioService.nextLine().trim();
    }

    public void localizePrint(String code) {
        ioService.print(messageService.localize(code));
    }

    public void localizePrintln(String code){
        ioService.println(messageService.localize(code));
    }

}
