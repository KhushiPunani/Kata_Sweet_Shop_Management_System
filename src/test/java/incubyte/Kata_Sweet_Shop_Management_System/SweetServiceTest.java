package incubyte.Kata_Sweet_Shop_Management_System;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class SweetServiceTest {

    @Test
    public void testAddSweet() {
        SweetService service = new SweetService();

        Sweet sweet = new Sweet(101, "Ladoo", "Indian", 25.0, 2);
        service.addSweet(sweet);

        assertTrue(service.getSweets().contains(sweet), "Sweet should be added to the list");
    }
}
