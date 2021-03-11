package net.ujacha.urlshortening.util;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;


@SpringBootTest
class Base62SupportTest {

    private long offset = 0;

    @Test
    void encode() {

        String code_0 = Base62Support.encode(0L, offset);
        assertEquals("0", code_0);

        String code_1 = Base62Support.encode(1L, offset);
        assertEquals("1", code_1);

        String code_10 = Base62Support.encode(10L, offset);
        assertEquals("A", code_10);

        String code_61 = Base62Support.encode(61L, offset);
        assertEquals("z", code_61);

        String code_62 = Base62Support.encode(62L, offset);
        assertEquals("10", code_62);

        String code_91574294826053 = Base62Support.encode(91574294826053L, offset);
        assertEquals("Q0DRQksv", code_91574294826053);

    }

    @Test
    void decode() {

        long value_0 = Base62Support.decode("0", offset);
        assertEquals(0L, value_0);

        long value_1 = Base62Support.decode("1", offset);
        assertEquals(1L, value_1);

        long value_A = Base62Support.decode("A", offset);
        assertEquals(10L, value_A);


        long value_z = Base62Support.decode("z", offset);
        assertEquals(61L, value_z);

        long value_10 = Base62Support.decode("10", offset);
        assertEquals(62L, value_10);

        long value_Q0DRQksv = Base62Support.decode("Q0DRQksv", offset);
        assertEquals(91574294826053L, value_Q0DRQksv);

    }

    @Test
    public void testMinMax(){
        String minCode = "AAAAAA";
        String maxCode = "zzzzzzzz";

        long minValue = Base62Support.decode(minCode, 0);
        long maxValue = Base62Support.decode(maxCode, 0);

        System.out.println("minValue = " + minValue);
        System.out.println("maxValue = " + maxValue);

        System.out.printf("size = %,d\n", (maxValue - minValue));


    }
}