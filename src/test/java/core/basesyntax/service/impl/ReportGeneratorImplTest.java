package core.basesyntax.service.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;

import core.basesyntax.service.ReportGenerator;
import java.util.HashMap;
import java.util.Map;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class ReportGeneratorImplTest {
    private Map<String, Integer> storage;
    private ReportGenerator reportGenerator;

    @BeforeEach
    void setUp() {
        reportGenerator = new ReportGeneratorImpl();
        storage = new HashMap<>();
    }

    @Test
    void getReport_validData_ok() {
        storage.put("Apple", 100);
        storage.put("Pineapple", 50);
        storage.put("Chery", 200);

        String actualResult = reportGenerator.getReport(storage);
        String result = "fruit,quantity" + System.lineSeparator()
                + "Apple,100" + System.lineSeparator()
                + "Chery,200" + System.lineSeparator()
                + "Pineapple,50";
        assertEquals(result, actualResult,
                "Method should return correctly formatted report string");
    }

    @Test
    void getReport_nullInputData_ok() {
        String actualResult = reportGenerator.getReport(storage);
        String result = "fruit,quantity";
        assertEquals(result, actualResult, "Method should return string with header");
    }
}
