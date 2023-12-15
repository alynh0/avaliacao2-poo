package org.exercicio.banco.template.model;

import java.io.Serializable;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import org.exercicio.banco.template.model.enumerator.TipoTransacao;

public class ContaCorrente implements ContaInterface, Serializable {

    /**
     * 
     */
    private static final long serialVersionUID = 1L;

    private Integer numeroConta;
	private BigDecimal saldo;
	private LocalDateTime dataAbertura;
	private boolean status;
	private List<RegistroTransacao> transacoes;

    public ContaCorrente() {
        this.numeroConta = new Random().nextInt(999999999);
		this.saldo = BigDecimal.ZERO;
		saldo.setScale(4, RoundingMode.HALF_UP);
		this.dataAbertura = LocalDateTime.now();
		this.status = true;
		transacoes = new ArrayList<>();
    }
    
    public Integer getNumeroConta() {
        return numeroConta;
    }

    public void setNumeroConta(Integer numeroConta) {
        this.numeroConta = numeroConta;
    }

    public LocalDateTime getDataAbertura() {
        return dataAbertura;
    }

    public void setDataAbertura(LocalDateTime dataAbertura) {
        this.dataAbertura = dataAbertura;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public List<RegistroTransacao> getTransacoes() {
        return transacoes;
    }

    public void setTransacoes(List<RegistroTransacao> transacoes) {
        this.transacoes = transacoes;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((numeroConta == null) ? 0 : numeroConta.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        ContaCorrente other = (ContaCorrente) obj;
        if (numeroConta == null) {
            if (other.numeroConta != null)
                return false;
        } else if (!numeroConta.equals(other.numeroConta))
            return false;
        return true;
    }

    @Override
    public String toString() {
        return "ContaCorrente [numeroConta=" + numeroConta + ", saldo=" + saldo + ", dataAbertura=" + dataAbertura
                + ", status=" + status + ", transacoes=" + transacoes + "]";
    }

    @Override
    public void depositar(BigDecimal quantia) {
        if (status) {
			if (quantia.compareTo(BigDecimal.ZERO) > 0) {
				this.saldo = this.saldo.add(quantia);
				transacoes.add(new RegistroTransacao(quantia, TipoTransacao.CREDITO, LocalDateTime.now()));
				System.out.println("Deposito realizado com sucesso.");
			} else {
				System.err.println("Valor invalido para deposito.");

			}
		} else {
			System.err.println("Operação não permitida. Conta desativada.");

		}
    }

    @Override
    public void sacar(BigDecimal quantia) {
        if (status) {
			if (quantia.compareTo(BigDecimal.ZERO) > 0) {
				if (this.saldo.compareTo(quantia) > 0) {
					this.saldo = this.saldo.subtract(quantia);
					transacoes.add(new RegistroTransacao(quantia, TipoTransacao.DEBITO, LocalDateTime.now()));
					System.out.println("Saque realizado com sucesso!");
				} else {
					System.err.println("Saldo insuficiente.");
				}
			} else {
				System.err.println("Valor invalido para saque.");
			}
		} else {
			System.err.println("Operação não permitida. Conta desativada.");
		}
    }

    @Override
    public void transferir(ContaInterface c, BigDecimal quantia) {
        if (status && c.isStatus()) {
			if (quantia.compareTo(BigDecimal.ZERO) < 0) {
				System.err.println("Valor invalido para transferencia.");
			} else if (quantia.compareTo(saldo) <= 0) {
				setSaldo(saldo.subtract(quantia));
				c.setSaldo(c.getSaldo().add(quantia));
				ContaInterface.transacoes.add(new RegistroTransacao(quantia, TipoTransacao.TRANSACAO_CREDITO, LocalDateTime.now()));
				transacoes.add(new RegistroTransacao(quantia, TipoTransacao.TRANSACAO_DEBITO, LocalDateTime.now()));
			} else
				System.err.println("Saldo insuficiente para realizar a transferencia.");
		} else {
			System.err.println("Operacao nao pode ser realizada entre contas desativadas.");
		}
    }

    @Override
    public void imprimirExtrato(int month, int year) {
        for (RegistroTransacao t: transacoes) {
			if (t.getData().getMonthValue() == month && t.getData().getYear() == year) {
				System.out.println(t);
			} else
				System.out.println("Não há transações para o mês e ano informados.");
		}
    }

    @Override
    public void imprimirSaldo() {
		System.out.println("Saldo atual: " + saldo);
    }

    @Override
    public boolean isStatus() {
        return status;
    }

    @Override
    public BigDecimal getSaldo() {
        return saldo;
    }

    @Override
    public void setSaldo(BigDecimal quantia) {
        this.saldo = quantia;
    }

}
