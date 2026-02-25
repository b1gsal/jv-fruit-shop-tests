package core.basesyntax.service.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.db.Storage;
import core.basesyntax.model.FruitTransaction;
import core.basesyntax.service.ShopService;
import core.basesyntax.strategy.OperationHandler;
import core.basesyntax.strategy.OperationStrategy;
import core.basesyntax.strategy.impl.BalanceHandler;
import core.basesyntax.strategy.impl.OperationStrategyImpl;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class ShopServiceImplTest {
    private OperationStrategy strategy;
    private ShopService shopService;
    
    @BeforeEach
    void setUp() {
        Map<FruitTransaction.Operation, OperationHandler> handlerMap = new HashMap<>();
        handlerMap.put(FruitTransaction.Operation.BALANCE, new BalanceHandler());
        strategy = new OperationStrategyImpl(handlerMap);
        shopService = new ShopServiceImpl(strategy);
    }

    @AfterEach
    void tearDown() {
        Storage.clear();
    }

    @Test
    void process_validTransaction_ok() {
        List<FruitTransaction> transactions = List.of(
                new FruitTransaction(FruitTransaction.Operation.BALANCE, "apple", 50),
                new FruitTransaction(FruitTransaction.Operation.BALANCE, "apple", 20)
        );
        shopService.process(transactions);
        assertEquals(70, Storage.getQuantity("apple"),
                "Shop service should correctly process transactions and update storage");
    }

    @Test
    void process_nullTransactionList_notOk() {
        assertThrows(RuntimeException.class, () -> {
            shopService.process(null);
        }, "Method should throw RunTimeException if transactions list is null");
    }

    @Test
    void process_emptyTransactionsList_notOk() {
        List<FruitTransaction> emptyList = new ArrayList<>();
        assertThrows(RuntimeException.class, () -> {
            shopService.process(emptyList);
        }, "Method should throw RunTimeException if transactions list is empty");
    }

    @Test
    void process_nullStrategy_notOk() {
        assertThrows(RuntimeException.class, () -> {
            shopService = new ShopServiceImpl(null);
        }, "Class should throw RunTimeException if operation strategy is null");
    }
}
