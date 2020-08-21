package br.gov.sp.fatec.projetomaven;

public class Calculadora {
    
    private Integer n1, n2;
    
    public Calculadora (Integer n1, Integer n2) {
        this.n1 = n1;
        this.n2 = n2;
    }
    
    public Calculadora() {
        this(1, 1);
    }
    
    public Integer soma() {
        return n1 + n2;
    }

}
