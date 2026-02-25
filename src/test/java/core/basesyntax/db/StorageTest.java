package core.basesyntax.db;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.Map;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;

class StorageTest {
    @AfterEach
    void tearDown() {
        Storage.clear();
    }

    @Test
    void putQuantity_newFruit_ok() {
        Storage.putQuantity("apple", 20);
        assertEquals(20, Storage.getQuantity("apple"), "Method should new fruit in storage");
    }

    @Test
    void mergeQuantity_newFruit_ok() {
        Storage.mergeQuantity("banana", 20);
        assertEquals(20, Storage.getQuantity("banana"), "Method should new fruit in storage");
    }

    @Test
    void mergeQuantity_existFruit_ok() {
        Storage.mergeQuantity("banana", 20);
        Storage.mergeQuantity("banana", 20);
        assertEquals(40, Storage.getQuantity("banana"), "Method should sum quantity in storage");
    }

    @Test
    void getQuantity_notExistFruit_ok() {
        assertNull(Storage.getQuantity("banana"), "Method should return null if fruit not exist");
    }

    @Test
    void getAll_returnCorrectMap_ok() {
        Storage.putQuantity("banana", 20);
        Storage.putQuantity("apple", 20);
        Map<String, Integer> actualResult = Storage.getAll();
        assertEquals(20, actualResult.get("apple"));
        assertEquals(20, actualResult.get("banana"));
    }

    @Test
    void getAll_modifyMap_notOk() {
        Storage.putQuantity("banana", 333);
        Map<String, Integer> actualResult = Storage.getAll();
        assertThrows(UnsupportedOperationException.class, () -> {
            actualResult.put("banana", 20);
        }, "Method should return unmodifiable map");
    }
}
