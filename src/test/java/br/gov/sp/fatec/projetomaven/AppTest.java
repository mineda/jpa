package br.gov.sp.fatec.projetomaven;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

/**
 * Unit test for simple App.
 */
public class AppTest
{

    /**
     * Rigourous Test :-)
     */
    @Test
    public void testSoma()
    {
        Calculadora calc = new Calculadora();
        assertTrue(calc.soma() == 2);
    }
}
