package net.ujacha.urlshortening.util;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import static org.junit.jupiter.api.Assertions.*;


@SpringBootTest
@ActiveProfiles("test")
class Base62SupportTest {

    private String codec = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz";
    private long offset = 0;

    @Test
    void encode() {

        String code_0 = Base62Support.encode(codec, offset, 0L);
        assertEquals("0", code_0);

        String code_1 = Base62Support.encode(codec, offset, 1L);
        assertEquals("1", code_1);

        String code_10 = Base62Support.encode(codec, offset, 10L);
        assertEquals("A", code_10);

        String code_61 = Base62Support.encode(codec, offset, 61L);
        assertEquals("z", code_61);

        String code_62 = Base62Support.encode(codec, offset, 62L);
        assertEquals("10", code_62);

        String code_91574294826053 = Base62Support.encode(codec, offset, 91574294826053L);
        assertEquals("Q0DRQksv", code_91574294826053);

    }

    @Test
    void decode() {

        long value_0 = Base62Support.decode(codec, offset,"0");
        assertEquals(0L, value_0);

        long value_1 = Base62Support.decode(codec, offset,"1");
        assertEquals(1L, value_1);

        long value_A = Base62Support.decode(codec, offset,"A");
        assertEquals(10L, value_A);


        long value_z = Base62Support.decode(codec, offset,"z");
        assertEquals(61L, value_z);

        long value_10 = Base62Support.decode(codec, offset,"10");
        assertEquals(62L, value_10);

        long value_Q0DRQksv = Base62Support.decode(codec, offset,"Q0DRQksv");
        assertEquals(91574294826053L, value_Q0DRQksv);

    }

    @Test
    public void testOffset(){
        long offset = 10000000000L;
        String code = Base62Support.encode(codec,  offset, 0);

        System.out.println("code = " + code);

    }

    @Test
    public void testMinMax(){
        long offset = 10000000000L;

        String minCode = "Aukyoa";
        String maxCode = "zzzzzzzz";

        long minValue = Base62Support.decode(codec, offset, minCode);
        long maxValue = Base62Support.decode(codec, offset, maxCode);

        System.out.println("minValue = " + minValue);
        System.out.println("maxValue = " + maxValue);

        System.out.printf("size = %,d\n", (maxValue - minValue));


    }
    

}