import java.util.Scanner;

import Model.Model_Eventos;
import Model.Model_User;

public class Login {

  public static boolean menu_login(String nomeUsuario, String senhaUsuario, Scanner scanner) {

    int opcaoMenu = -1;

    // menu pós-login com saudacao
    System.out.println("===== Bem-vindo, " + nomeUsuario + " =====");

    do {
      System.out.printf("%-5s | %-30s%n", "Opcao", "Descricao");
      System.out.println("------+--------------------------------");

      System.out.printf("%-5d | %-30s%n", 1, "Cadastrar evento");
      System.out.printf("%-5d | %-30s%n", 2, "Listar eventos disponiveis");
      System.out.printf("%-5d | %-30s%n", 3, "Confirmar participacao");
      System.out.printf("%-5d | %-30s%n", 4, "Cancelar participacao");
      System.out.printf("%-5d | %-30s%n", 5, "Lista partipacoes confirmadas");
      System.out.printf("%-5d | %-30s%n", 8, "Excluir conta");
      System.out.printf("%-5d | %-30s%n", 9, "Logout");

      System.out.print("Digite o numero da opcao: "); // print sem quebra de linha

      // Verifica se a entrada é um numero inteiro
      if (scanner.hasNextInt()) {
        opcaoMenu = scanner.nextInt();
        System.out.printf("Escolheu [%d]%n\n\n\n", opcaoMenu);
        scanner.nextLine(); // limpa o \n do buffer

        switch (opcaoMenu) {
          case 1:
            System.out.println("===== CADASTRAR EVENTO =====");
            Model_Eventos evento = new Model_Eventos(scanner, nomeUsuario); // passa o scanner como parâmetro
            evento.salvarEmArquivo();
            break;

          case 2:
            System.out.println("===== LISTAR EVENTOS =====");
            Model_Eventos.listarEventos();
            break;

          case 3:
            System.out.println("===== CONFIRMAR PRESENCA NO EVENTO =====");
            Model_Eventos.confirmarPresenca(scanner, nomeUsuario);
            break;

          case 4:
            System.out.println("===== CANCELAR PRESENCA NO EVENTO =====");
            Model_Eventos.cancelarPresenca(scanner, nomeUsuario);
            break;

          case 5:
            System.out.println("===== LISTAR PARTICIPACOES CONFIRMADAS  =====");
            Model_Eventos.listarEventosDoUsuario(nomeUsuario);
            break;

          case 8:
            System.out.print("Tem certeza que deseja excluir sua conta? (s/n): ");
            String confirmacao = scanner.nextLine();

            if (confirmacao.equalsIgnoreCase("s")) {
              if (Model_User.excluirConta(nomeUsuario, senhaUsuario)) {
                System.out.println("Conta excluida com sucesso!");
                opcaoMenu = 9; // força logout
              } else {
                System.out.println("Erro: nao foi possivel excluir sua conta.");
              }
            } else {
              System.out.println("Operacao cancelada.");
            }
            break;

          case 9:
            System.out.println("Saindo do programa...");
            break;

          default:
            System.out.println("Erro: opcao invalida!");
            opcaoMenu = -1; // força repetir o loop
        }

      } else {
        System.out.println("Entrada invalida! Digite apenas numeros.");
        scanner.nextLine(); // limpa entrada invalida
      }

    } while (opcaoMenu != 9);

    return true;
  }

}
