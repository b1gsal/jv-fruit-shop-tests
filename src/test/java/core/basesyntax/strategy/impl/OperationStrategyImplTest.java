package core.basesyntax.strategy.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.model.FruitTransaction;
import core.basesyntax.strategy.OperationHandler;
import core.basesyntax.strategy.OperationStrategy;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class OperationStrategyImplTest {
    private OperationStrategy operationStrategy;
    private Map<FruitTransaction.Operation, OperationHandler> operationsMap;

    @BeforeEach
    void setUp() {
        operationsMap = new HashMap<>();
        operationsMap.put(FruitTransaction.Operation.BALANCE, new BalanceHandler());
        operationsMap.put(FruitTransaction.Operation.SUPPLY, new SupplyHandler());
        operationsMap.put(FruitTransaction.Operation.RETURN, new ReturnHandler());
        operationsMap.put(FruitTransaction.Operation.PURCHASE, new PurchaseHandler());
        operationStrategy = new OperationStrategyImpl(operationsMap);
    }

    @Test
    void getHandler_getBalanceHandler_ok() {
        OperationHandler handler = operationStrategy.getHandler(FruitTransaction.Operation.BALANCE);
        assertEquals(BalanceHandler.class, handler.getClass(),
                "Method should return balance handler");
    }

    @Test
    void getHandler_getPurchaseHandler_ok() {
        OperationHandler handler = operationStrategy
                .getHandler(FruitTransaction.Operation.PURCHASE);
        assertEquals(PurchaseHandler.class, handler.getClass(),
                "Method should return purchase handler");
    }

    @Test
    void getHandler_getReturnHandler_ok() {
        OperationHandler handler = operationStrategy.getHandler(FruitTransaction.Operation.RETURN);
        assertEquals(ReturnHandler.class, handler.getClass(),
                "Method should return Return handler");
    }

    @Test
    void getHandler_getSupplyHandler_ok() {
        OperationHandler handler = operationStrategy.getHandler(FruitTransaction.Operation.SUPPLY);
        assertEquals(SupplyHandler.class, handler.getClass(),
                "Method should return supply handler");
    }

    @Test
    void getHandler_nullValue_notOk() {
        assertThrows(RuntimeException.class, () -> {
            operationStrategy.getHandler(null);
        }, "Method should throw RuntimeException if operation is null");
    }

    @Test
    void constructor_nullMap_notOk() {
        Map<FruitTransaction.Operation, OperationHandler> map = null;
        assertThrows(RuntimeException.class, () -> {
            operationStrategy = new OperationStrategyImpl(map);
        }, "Class should throw RuntimeException if map is null");
    }

    @Test
    void constructor_emptyMap_notOk() {
        Map<FruitTransaction.Operation, OperationHandler> map = Collections.emptyMap();
        assertThrows(RuntimeException.class, () -> {
            operationStrategy = new OperationStrategyImpl(map);
        }, "Class should throw RuntimeException if map is empty");
    }

    @Test
    void getHandler_notExistHandlerInMap_notOk() {
        Map<FruitTransaction.Operation, OperationHandler> incompleteMap = new HashMap<>();
        incompleteMap.put(FruitTransaction.Operation.BALANCE, new BalanceHandler());
        operationStrategy = new OperationStrategyImpl(incompleteMap);

        assertThrows(RuntimeException.class, () -> {
            operationStrategy.getHandler(FruitTransaction.Operation.PURCHASE);
        }, "Method should throw RuntimeException if handler is not found in the map");
    }
}
