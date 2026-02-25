package core.basesyntax.strategy.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;

import core.basesyntax.db.Storage;
import core.basesyntax.strategy.OperationHandler;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class BalanceHandlerTest {
    private OperationHandler operationHandler;

    @BeforeEach
    void setUp() {
        operationHandler = new BalanceHandler();
    }

    @AfterEach
    void tearDown() {
        Storage.clear();
    }

    @Test
    void handle_newFruit_ok() {
        operationHandler.handle("apple", 10);
        assertEquals(10, Storage.getQuantity("apple"),
                "Storage should contain 10 apples after balance operation");
    }

    @Test
    void handle_existFruit_ok() {
        Storage.putQuantity("apple", 50);
        operationHandler.handle("apple", 50);
        assertEquals(100, Storage.getQuantity("apple"),
                "Storage should sum quantities for existing fruit");
    }
}
