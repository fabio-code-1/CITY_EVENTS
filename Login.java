import java.util.Scanner;

import Model.Model_Eventos;
import Model.Model_User;

public class Login {

  public static boolean menu_login(String nomeUsuario, String senhaUsuario, Scanner scanner) {

    int opcaoMenu = -1;

    // menu pós-login com saudação
    System.out.println("===== Bem-vindo, " + nomeUsuario + " =====");

    do {
      System.out.printf("%-5s | %-30s%n", "Opção", "Descrição");
      System.out.println("------+--------------------------------");

      System.out.printf("%-5d | %-30s%n", 1, "Cadastrar evento");
      System.out.printf("%-5d | %-30s%n", 2, "Listar eventos disponíveis");
      System.out.printf("%-5d | %-30s%n", 3, "Confirmar participação");
      System.out.printf("%-5d | %-30s%n", 4, "Cancelar participação");
      System.out.printf("%-5d | %-30s%n", 5, "Ver eventos confirmados");
      System.out.printf("%-5d | %-30s%n", 6, "Ver eventos em andamento");
      System.out.printf("%-5d | %-30s%n", 7, "Ver eventos passados");
      System.out.printf("%-5d | %-30s%n", 8, "Excluir conta");
      System.out.printf("%-5d | %-30s%n", 9, "Logout");

      System.out.print("Digite o número da opção: "); // print sem quebra de linha

      // Verifica se a entrada é um número inteiro
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
            break;

          case 8:
            System.out.print("Tem certeza que deseja excluir sua conta? (s/n): ");
            String confirmacao = scanner.nextLine();

            if (confirmacao.equalsIgnoreCase("s")) {
              if (Model_User.excluirConta(nomeUsuario, senhaUsuario)) {
                System.out.println("Conta excluída com sucesso!");
                opcaoMenu = 9; // força logout
              } else {
                System.out.println("Erro: não foi possível excluir sua conta.");
              }
            } else {
              System.out.println("Operação cancelada.");
            }
            break;

          case 9:
            System.out.println("Saindo do programa...");
            break;

          default:
            System.out.println("Erro: opção inválida!");
            opcaoMenu = -1; // força repetir o loop
        }

      } else {
        System.out.println("Entrada inválida! Digite apenas números.");
        scanner.nextLine(); // limpa entrada inválida
      }

    } while (opcaoMenu != 9);

    return true;
  }

}
