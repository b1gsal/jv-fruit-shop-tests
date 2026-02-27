package core.basesyntax.service.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import core.basesyntax.model.FruitTransaction;
import core.basesyntax.service.DataConverterService;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

class DataConverterServiceImplTest {
    public static final String HEADER = "type,fruit,quantity";
    private static DataConverterService dataConverterService;

    @BeforeAll
    static void beforeAll() {
        dataConverterService = new DataConverterServiceImpl();
    }

    @Test
    void convert_validData_ok() {
        List<String> inputData = new ArrayList<>();
        inputData.add(HEADER);
        inputData.add("b,apple,100");
        inputData.add("p,banana,34");

        List<FruitTransaction> actualResult = dataConverterService.convert(inputData);

        assertEquals(2, actualResult.size(), "List should contains exactly 2 transactions");

        assertEquals(FruitTransaction.Operation.BALANCE, actualResult.get(0).getOperation());
        assertEquals("apple", actualResult.get(0).getFruit());
        assertEquals(100, actualResult.get(0).getQuantity());

        assertEquals(FruitTransaction.Operation.PURCHASE, actualResult.get(1).getOperation());
        assertEquals("banana", actualResult.get(1).getFruit());
        assertEquals(34, actualResult.get(1).getQuantity());
    }

    @Test
    void convert_nullData_notOk() {
        assertThrows(RuntimeException.class, () -> {
            dataConverterService.convert(null);
        }, "Method should throw RunTimeException if input data is null");
    }

    @Test
    void convert_nullLine_notOk() {
        List<String> inputData = new ArrayList<>();
        inputData.add(HEADER);
        inputData.add(null);
        assertThrows(RuntimeException.class, () -> {
            dataConverterService.convert(inputData);
        }, "Method should throw RunTimeException if line data is empty");
    }

    @Test
    void convert_invalidLineFormat_notOk() {
        List<String> inputData = new ArrayList<>();
        inputData.add(HEADER);
        inputData.add("b.apple.100");

        assertThrows(RuntimeException.class, () -> {
            dataConverterService.convert(inputData);
        }, "Method should throw RunTimeException for invalid line Format");
    }

    @Test
    void convert_negativeQuantity_notOk() {
        List<String> inputData = new ArrayList<>();
        inputData.add(HEADER);
        inputData.add("b,apple,-12");

        assertThrows(RuntimeException.class, () -> {
            dataConverterService.convert(inputData);
        }, "Method should throw RunTimeException if quantity is negative");
    }

    @Test
    void convert_invalidNumber_notOk() {
        List<String> inputData = new ArrayList<>();
        inputData.add(HEADER);
        inputData.add("b,apple,1j3");

        assertThrows(RuntimeException.class, () -> {
            dataConverterService.convert(inputData);
        }, "Method should throw RunTimeException if quantity isn't number");
    }

    @Test
    void convert_invalidOperation_notOk() {
        List<String> inputData = new ArrayList<>();
        inputData.add(HEADER);
        inputData.add("beer,apple,13");

        assertThrows(RuntimeException.class, () -> {
            dataConverterService.convert(inputData);
        }, "Method should throw RunTimeException if get unknown operation");
    }

    @Test
    void convert_emptyLine_notOk() {
        List<String> inputData = new ArrayList<>();
        inputData.add(HEADER);
        inputData.add("");

        assertThrows(RuntimeException.class, () -> {
            dataConverterService.convert(inputData);
        }, "Method should throw RunTimeException if line is empty");
    }

    @Test
    void convert_onlyHeader_ok() {
        List<String> inputData = new ArrayList<>();
        inputData.add(HEADER);

        List<FruitTransaction> actualResult = dataConverterService.convert(inputData);
        assertNotNull(actualResult);
        assertTrue(actualResult.isEmpty(), "List should be empty if is only header");
    }
}
