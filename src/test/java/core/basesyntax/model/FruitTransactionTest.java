package core.basesyntax.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;

class FruitTransactionTest {
    @Test
    void constructor_validData_ok() {
        FruitTransaction fruitTransaction = new FruitTransaction(
                FruitTransaction.Operation.RETURN,
                "apple",
                100);
        assertEquals(FruitTransaction.Operation.RETURN, fruitTransaction.getOperation());
        assertEquals("apple", fruitTransaction.getFruit());
        assertEquals(100, fruitTransaction.getQuantity());
    }

    @Test
    void constructor_nullOperation_notOk() {
        assertThrows(RuntimeException.class, () -> {
            new FruitTransaction(null, "apple", 100);
        }, "Method should throw RuntimeException if operation is null");
    }

    @Test
    void constructor_nullFruit_notOk() {
        assertThrows(RuntimeException.class, () -> {
            new FruitTransaction(FruitTransaction.Operation.RETURN, null, 100);
        }, "Method should throw RuntimeException if operation is null");
    }

    @Test
    void constructor_emptyLine_notOk() {
        assertThrows(RuntimeException.class, () -> {
            new FruitTransaction(FruitTransaction.Operation.RETURN, "", 100);
        }, "Method should throw RuntimeException if operation is null");
    }

    @Test
    void constructor_negativeQuantity_notOk() {
        assertThrows(RuntimeException.class, () -> {
            new FruitTransaction(FruitTransaction.Operation.RETURN, "apple", -20);
        }, "Method should throw RuntimeException if operation is null");
    }

    @Test
    void fromCode_validCodes_ok() {
        assertEquals(FruitTransaction.Operation.BALANCE, FruitTransaction.Operation.fromCode("b"));
        assertEquals(FruitTransaction.Operation.SUPPLY, FruitTransaction.Operation.fromCode("s"));
        assertEquals(FruitTransaction.Operation.PURCHASE, FruitTransaction.Operation.fromCode("p"));
        assertEquals(FruitTransaction.Operation.RETURN, FruitTransaction.Operation.fromCode("r"));

        assertEquals(FruitTransaction.Operation.BALANCE, FruitTransaction.Operation.fromCode(" b"));
    }

    @Test
    void fromCode_invalidCode_notOk() {
        assertThrows(RuntimeException.class, () -> {
            FruitTransaction.Operation.fromCode("o");
        }, "Method should throw RuntimeException for unknown code");
    }

    @Test
    void fromCode_nullCode_notOk() {
        assertThrows(RuntimeException.class, () -> {
            FruitTransaction.Operation.fromCode(null);
        }, "Method should throw RuntimeException for code null");
    }

    @Test
    void fromCode_emptyCode_notOk() {
        assertThrows(RuntimeException.class, () -> {
            FruitTransaction.Operation.fromCode(" ");
        }, "Method should throw RuntimeException for code null");
    }
}
