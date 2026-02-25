package core.basesyntax.service.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.service.WriterService;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class FileWriterServiceImplTest {
    private static final String INCORRECT_PATH = "src/some/not/existing/folder/FinalReport.csv";
    private static final String FILE_PATH_TO_WRITE = "src/test/resources/FinalReport.csv";
    private static final String text = "fruit,quantity" + System.lineSeparator()
            + "banana,20" + System.lineSeparator()
            + "apple,100" + System.lineSeparator()
            + "banana,100";

    private WriterService writerService;

    @BeforeEach
    void setUp() {
        writerService = new FileWriterServiceImpl();
    }

    @AfterEach
    void tearDown() throws IOException {
        Files.deleteIfExists(Path.of(FILE_PATH_TO_WRITE));
    }

    @Test
    void write_validData_ok() throws IOException {

        writerService.write(text, FILE_PATH_TO_WRITE);
        String actualResult = Files.readString(Path.of(FILE_PATH_TO_WRITE));
        assertEquals(text, actualResult, "Written text should match the read text");
    }

    @Test
    void write_nullPath_notOk() {
        assertThrows(RuntimeException.class, () -> {
            writerService.write(text, null);
        }, "Method should throw RunTimeException if path is null");
    }

    @Test
    void write_incorrectPath_notOK() {
        assertThrows(RuntimeException.class, () -> {
            writerService.write(text, INCORRECT_PATH);
        }, "Method should throw RuntimeException if path is incorrect");
    }

    @Test
    void write_nullText_notOk() {
        assertThrows(RuntimeException.class, () -> {
            writerService.write(null, FILE_PATH_TO_WRITE);
        }, "Method should throw RuntimeException if input text is null");
    }

    @Test
    void write_emptyString_notOk() {
        assertThrows(RuntimeException.class, () -> {
            writerService.write("", FILE_PATH_TO_WRITE);
        }, "Method should throw RuntimeException if input text is null");
    }
}
