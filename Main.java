import java.util.Scanner;
import Model.Model_User;

public class Main {

  private static boolean menu() {
    int opcaoMenu = -1;
    Scanner scanner = new Scanner(System.in); // cria o leitor

    do {
      System.out.println("===== MENU PRINCIPAL =====");

      System.out.printf("%-5s | %-30s%n", "Opção", "Descrição");
      System.out.println("------+--------------------------------");

      System.out.printf("%-5d | %-30s%n", 1, "Cadastrar usuário");
      System.out.printf("%-5d | %-30s%n", 2, "Cadastrar evento");
      System.out.printf("%-5d | %-30s%n", 3, "Listar eventos disponíveis");
      System.out.printf("%-5d | %-30s%n", 4, "Confirmar participação");
      System.out.printf("%-5d | %-30s%n", 5, "Cancelar participação");
      System.out.printf("%-5d | %-30s%n", 6, "Ver eventos confirmados");
      System.out.printf("%-5d | %-30s%n", 7, "Ver eventos em andamento");
      System.out.printf("%-5d | %-30s%n", 8, "Ver eventos passados");
      System.out.printf("%-5d | %-30s%n", 9, "Sair");

      System.out.print("Digite o número da opção: "); // print sem quebra de linha

      // Verifica se a entrada é um número inteiro
      if (scanner.hasNextInt()) {
        opcaoMenu = scanner.nextInt();
        System.out.printf("Escolheu [%d]%n\n\n\n", opcaoMenu);
        scanner.nextLine(); // limpa o \n do buffer

        switch (opcaoMenu) {
          case 1:
            System.out.println("===== LOGIN =====");
            break;

          case 2:
            System.out.println("===== CADASTRO DE USUARIO =====");
            Model_User usuario = new Model_User(scanner); // passa o scanner como parâmetro
            usuario.salvarEmArquivo();
            break;

          case 3:
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

    scanner.close(); // fechar o leitor
    return true;
  }

  public static void main(String[] args) {
    System.out.println("\n\n\n"); // quebra de linhas [\n]
    System.out.println("Bem vindo ao CityEvents");

    if (menu() == false) {
      System.out.println("Erro: incializacao menu");
    }

    System.out.println("\n\n\n");
  }
}