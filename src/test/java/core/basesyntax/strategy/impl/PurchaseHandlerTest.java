package core.basesyntax.strategy.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.db.Storage;
import core.basesyntax.strategy.OperationHandler;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class PurchaseHandlerTest {
    private OperationHandler operationHandler;

    @BeforeEach
    void setUp() {
        operationHandler = new PurchaseHandler();
    }

    @AfterEach
    void tearDown() {
        Storage.clear();
    }

    @Test
    void handle_enoughFruit_ok() {
        Storage.putQuantity("apple", 100);
        operationHandler.handle("apple", 99);
        assertEquals(1, Storage.getQuantity("apple"),
                "Storage should contain exactly 1 apple");
    }

    @Test
    void handle_notEnoughQuantity_notOk() {
        Storage.putQuantity("apple", 10);
        assertThrows(RuntimeException.class, () -> {
            operationHandler.handle("apple", 15);
        }, "Method should throw RuntimeException if trying to buy more fruits than available");
    }

    @Test
    void handle_notExistFruit_notOk() {
        assertThrows(RuntimeException.class, () -> {
            operationHandler.handle("apple", 15);
        }, "Method should throw RuntimeException if fruit not exist in storage");
    }

    @Test
    void handle_nullFruit_notOk() {
        Storage.putQuantity("apple", 10);
        assertThrows(RuntimeException.class, () -> {
            operationHandler.handle(null, 10);
        }, "Method should throw RuntimeException if fruit name is null");
    }
}
