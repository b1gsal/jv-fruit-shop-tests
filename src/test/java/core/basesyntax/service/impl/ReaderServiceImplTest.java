package core.basesyntax.service.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import core.basesyntax.service.ReaderService;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class ReaderServiceImplTest {
    private static final String PATH_TO_READ_FILE = "src/test/resources/ReportToReadTest.csv";
    private static final String PATH_TO_EMPTY_FILE = "src/test/resources/EmptyFileTest.csv";

    private ReaderService readerService;

    @BeforeEach
    void setUp() {
        readerService = new ReaderServiceImpl();
    }

    @Test
    void read_validFile_ok() {
        List<String> actualResult = readerService.read(PATH_TO_READ_FILE);
        List<String> result = List.of(
                "type,fruit,quantity",
                "b,banana,20",
                "b,apple,100",
                "s,banana,100"
        );
        assertEquals(result, actualResult, "Method should return list with data from file");
    }

    @Test
    void read_invalidPathToFile_notOk() {
        assertThrows(RuntimeException.class, () -> {
            readerService.read("kmskdn");
        }, "Method should throw RunTimeException if invalid path");
    }

    @Test
    void read_nullPath_notOk() {
        assertThrows(RuntimeException.class, () -> {
            readerService.read(null);
        }, "Method should throw RunTimeException if path is null");
    }

    @Test
    void read_emptyFile_ok() {
        List<String> actualResult = readerService.read(PATH_TO_EMPTY_FILE);
        assertTrue(actualResult.isEmpty());
    }
}
