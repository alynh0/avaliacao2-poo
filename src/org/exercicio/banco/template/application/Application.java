package org.exercicio.banco.template.application;

import java.math.BigDecimal;
import java.util.Scanner;

import org.exercicio.banco.template.model.Cliente;
import org.exercicio.banco.template.model.ContaCorrente;
import org.exercicio.banco.template.model.ContaInterface;
import org.exercicio.banco.template.model.ContaPoupanca;
import org.exercicio.banco.template.persistence.PersistenciaEmArquivo;

public class Application {

	public static void main(String[] args) {

		Scanner scanner = new Scanner(System.in);

		while (true) {
			System.out.println("Bem-vindo ao banco! O que você gostaria de fazer?");
			System.out.println("1. Cadastrar novo cliente");
			System.out.println("2. Selecionar cliente existente");
			System.out.println("3. Listar clientes");
			System.out.println("4. Deletar cliente");
			System.out.println("5. Sair");

			int opcao = scanner.nextInt();
			scanner.nextLine();

			switch (opcao) {
				case 1:
					System.out.println("Digite o nome do cliente:");
					String nome = scanner.nextLine();
					System.out.println("Digite o CPF do cliente:");
					String cpf = scanner.nextLine();
					Cliente cliente = new Cliente(cpf, nome);
					PersistenciaEmArquivo.getInstance().salvarCliente(cliente);
					break;

				case 2:
					System.out.println("Digite o CPF do cliente:");
					cpf = scanner.next();

					cliente = PersistenciaEmArquivo.getInstance().localizarClientePorCPF(cpf);

					if (cliente == null) {
						System.out.println("Cliente não encontrado");
						break;
					}

					System.out.println("Cliente selecionado: " + cliente.getNome());
					System.out.println("\nO que você gostaria de fazer?\n");
					System.out.println("1. Criar nova conta");
					System.out.println("2. Ver informações das contas");
					System.out.println("3. Realizar Deposito");
					System.out.println("4. Realizar Saque");
					System.out.println("5. Realizar Transferencia");
					System.out.println("6. Deletar conta");
					System.out.println("7. Emitir extrato do mês");
					System.out.println("8. Emitir balanço das contas");

					opcao = scanner.nextInt();
					scanner.nextLine();

					switch (opcao) {
					case 1:
						System.out.println("Qual tipo de conta deseja criar?");

						System.out.println("1. Conta Corrente");
						System.out.println("2. Conta Poupança");

						opcao = scanner.nextInt();
						scanner.nextLine();

						switch (opcao) {
							case 1:
								ContaInterface contaCorrente = new ContaCorrente();
								cliente.adicionarConta(contaCorrente);
								System.out.println("Conta criada com sucesso!");
							break;
							case 2:
								ContaInterface contaPoupanca = new ContaPoupanca();
								cliente.adicionarConta(contaPoupanca);
								System.out.println("Conta criada com sucesso!");
							default:
								System.out.println("Opção inválida");
								break;
						}

						PersistenciaEmArquivo.getInstance().atualizarClienteCadastro(cliente);
						
						break;

					case 2:
						if (cliente.getContas().size() == 0) {
							System.err.println("Nao ha contas associada a este cliente.");
						} else {
							for (ContaInterface c : cliente.getContas()) {
								System.out.println(c);
							}
						}
						break;
					case 3:
						if (cliente.getContas().size() == 0) {
							System.err.println("Nao ha contas associada a este cliente.");
						} else {
							for (ContaInterface c : cliente.getContas()) {
								System.out.println(c);
							}
						}
						System.out.println("Em qual conta deseja realizar o deposito?");
						int opcaoContaDeposito = 0;
						double quantia = 0.0;
						opcaoContaDeposito = scanner.nextInt();
						System.out.println("Insira o valor da quantia a ser depositada: ");
						quantia = scanner.nextDouble();
						ContaInterface temp = cliente.localizarContaNumero(opcaoContaDeposito);
						if (temp != null) {
							temp.depositar(new BigDecimal(quantia));
							PersistenciaEmArquivo.getInstance().atualizarClienteCadastro(cliente);
						}
						break;
					case 4: // realizar saque
						if (cliente.getContas().size() == 0) {
							System.err.println("Nao ha contas associada a este cliente.");
						} else {
							for (ContaInterface c : cliente.getContas()) {
								System.out.println(c);
							}
						}
						System.out.println("Em qual conta deseja realizar o saque?");
						int opcaoContaSaque = 0;
						double quantiaSaque = 0.0;
						opcaoContaSaque = scanner.nextInt();
						System.out.println("Insira o valor da quantia a ser sacada: ");
						quantiaSaque = scanner.nextDouble();
						ContaInterface tempSaque = cliente.localizarContaNumero(opcaoContaSaque);
						if (tempSaque != null) {
							tempSaque.sacar(new BigDecimal(quantiaSaque));
							PersistenciaEmArquivo.getInstance().atualizarClienteCadastro(cliente);
						}
						break;
					case 5: // realizar transferencia
						if (cliente.getContas().size() == 0) {
							System.err.println("Nao ha contas associada a este cliente.");
						} else {
							for (ContaInterface c : cliente.getContas()) {
								System.out.println(c);
							}
						}
						System.out.println("De que conta deseja realizar a transferencia?");
						int opcaoContaTransferencia = 0;
						double quantiaTransferencia = 0.0;
						opcaoContaTransferencia = scanner.nextInt();
						System.out.println("Insira o valor da quantia a ser transferida: ");
						quantiaTransferencia = scanner.nextDouble();
						ContaInterface tempTransferencia = cliente.localizarContaNumero(opcaoContaTransferencia);
						if (tempTransferencia != null) {
							System.out.println("Digite o CPF do cliente para qual deseja transferir:");
							String opcaoClienteTransferencia;
							opcaoClienteTransferencia = scanner.next();
							Cliente clienteTransferencia = PersistenciaEmArquivo.getInstance().localizarClientePorCPF(opcaoClienteTransferencia);
							System.out.println("Insira o numero da conta para qual deseja transferir: ");
							int opcaoContaTransferencia2 = 0;
							opcaoContaTransferencia2 = scanner.nextInt();
							ContaInterface tempTransferencia2 = clienteTransferencia.localizarContaNumero(opcaoContaTransferencia2);
							if (tempTransferencia2 != null) {
								tempTransferencia.transferir(tempTransferencia2, new BigDecimal(quantiaTransferencia));
								PersistenciaEmArquivo.getInstance().atualizarClienteCadastro(cliente);
								PersistenciaEmArquivo.getInstance().atualizarClienteCadastro(clienteTransferencia);
							}
						}
						break;
					case 6: // deletar conta
						if (cliente.getContas().size() == 0) {
							System.err.println("Nao ha contas associada a este cliente.");
						} else {
							for (ContaInterface c : cliente.getContas()) {
								System.out.println(c);
							}
						}
						System.out.println("Em qual conta deseja realizar o saque?");
						int opcaoContaDeletar = 0;
						opcaoContaDeletar = scanner.nextInt();
						ContaInterface tempDeletar = cliente.localizarContaNumero(opcaoContaDeletar);
						if (tempDeletar != null) {
							cliente.removerConta(tempDeletar);
							PersistenciaEmArquivo.getInstance().atualizarClienteCadastro(cliente);
						}
						break;
					case 7: // emitir extrato do mes
						if (cliente.getContas().size() == 0) {
							System.err.println("Nao ha contas associada a este cliente.");
						} else {
							for (ContaInterface c : cliente.getContas()) {
								System.out.println(c);
							}
						}
						System.out.println("Em qual conta deseja emitir o extrato?");
						int opcaoContaExtrato = 0;
						opcaoContaExtrato = scanner.nextInt();
						ContaInterface tempExtrato = cliente.localizarContaNumero(opcaoContaExtrato);
						if (tempExtrato != null) {
							System.out.println("Insira o mes do extrato: ");
							int mesExtrato = 0;
							mesExtrato = scanner.nextInt();
							System.out.println("Insira o ano do extrato: ");
							int anoExtrato = 0;
							anoExtrato = scanner.nextInt();
							tempExtrato.imprimirExtrato(mesExtrato, anoExtrato);
						}
						break;
					case 8: // emitir balanco das contas
						if (cliente.getContas().size() == 0) {
							System.err.println("Nao ha contas associada a este cliente.");
						} else {
							for (ContaInterface c : cliente.getContas()) {
								System.out.println(c);
							}
						}
						cliente.balancoEntreContas();
						break;

					default:
						System.out.println("Opção inválida");
						break;
					}
					break;
			case 3:
				System.out.println("Clientes cadastrados:");
				PersistenciaEmArquivo.getInstance().listarClientes();
				break;

			case 4:
				System.out.println("Digite o CPF do cliente:");
				cpf = scanner.next();

				cliente = PersistenciaEmArquivo.getInstance().localizarClientePorCPF(cpf);

				if (cliente == null) {
					System.out.println("Cliente não encontrado");
					break;
				}

				System.out.println("Cliente selecionado: " + cliente.getNome());
				System.out.println("Deseja realmente deletar este cliente? (S/N)");
				String confirmacao = scanner.next();

				if (confirmacao.equalsIgnoreCase("S")) {
					PersistenciaEmArquivo.getInstance().deletarCliente(cliente);
					System.out.println("Cliente deletado com sucesso!");
				}
				break;
			case 5:
				System.out.println("Até logo!");
				System.exit(0);
				break;

			default:
				System.out.println("Opção inválida");
				break;
			}
		}
	}
}
