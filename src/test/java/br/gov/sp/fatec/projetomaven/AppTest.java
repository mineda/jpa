package br.gov.sp.fatec.projetomaven;

import org.junit.Test;
import static org.junit.Assert.*;

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
