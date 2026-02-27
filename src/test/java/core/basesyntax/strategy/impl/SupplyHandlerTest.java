package core.basesyntax.strategy.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;

import core.basesyntax.db.Storage;
import core.basesyntax.strategy.OperationHandler;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

class SupplyHandlerTest {
    private static OperationHandler operationHandler;

    @BeforeAll
    static void beforeAll() {
        operationHandler = new SupplyHandler();
    }

    @AfterEach
    void tearDown() {
        Storage.clear();
    }

    @Test
    void handle_newFruit_ok() {
        operationHandler.handle("banana", 30);
        assertEquals(30, Storage.getQuantity("banana"),
                "Storage should contain 30 banana after call method handle");
    }

    @Test
    void handle_existFruit_ok() {
        Storage.putQuantity("apple", 50);

        operationHandler.handle("apple", 50);
        assertEquals(100, Storage.getQuantity("apple"),
                "Storage should sum quantities for existing fruit");
    }
}
