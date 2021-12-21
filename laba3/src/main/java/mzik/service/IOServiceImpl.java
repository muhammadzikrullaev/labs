package mzik.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.util.Scanner;

@Service
@Slf4j
public class IOServiceImpl implements IOService {
    private final Scanner scanner;
    private final PrintWriter writer;

    public IOServiceImpl(InputStream is, OutputStream os) {
        scanner = new Scanner(is);
        writer = new PrintWriter(os);
    }

    public void print(Object o) {
        log.info("print: " + o);
        writer.print(o);
        writer.flush();
    }

    public void println(Object o) {
        log.info("println: " + o);
        writer.println(o);
        writer.flush();
    }

    public String nextLine() {
        return scanner.nextLine();
    }

    public int nextInt(){
        return scanner.nextInt();
    }
}
