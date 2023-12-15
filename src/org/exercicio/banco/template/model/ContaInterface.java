package org.exercicio.banco.template.model;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public interface ContaInterface {

    void depositar(BigDecimal quantia);
    void sacar(BigDecimal quantia);
    void transferir(ContaInterface contaDestino, BigDecimal quantia);
    void imprimirExtrato(int mes, int ano);
    void imprimirSaldo();
    boolean isStatus();
    BigDecimal getSaldo();
    void setSaldo(BigDecimal quantia);
    List<RegistroTransacao> transacoes = new ArrayList<>();
    Integer getNumeroConta();

}
