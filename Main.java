import java.io.File;
import java.io.IOException;
import java.util.Scanner;
import Model.Model_User;

public class Main {

  private static boolean menu() {
    int opcaoMenu = -1;
    Scanner scanner = new Scanner(System.in); // cria o leitor

    try {
      File usuario = new File("DB/usuarios.data");
      File events = new File("DB/events.data");
      if (!events.exists()) {
        // cria o arquivo vazio se não existir
        events.createNewFile();
      }

      if (!usuario.exists()) {
        usuario.createNewFile();
      }
    } catch (IOException e) {
      System.out.println("Erro ao criar o arquivo: " + e.getMessage());
      return false; // encerra o menu se der erro
    }

    do {
      System.out.println("===== MENU PRINCIPAL =====");

      System.out.printf("%-5s | %-30s%n", "Opção", "Descrição");
      System.out.println("------+--------------------------------");

      System.out.printf("%-5d | %-30s%n", 1, "Login");
      System.out.printf("%-5d | %-30s%n", 2, "Cadastrar usuário");
      System.out.printf("%-5d | %-30s%n", 3, "Sair");

      System.out.print("Digite o número da opção: "); // print sem quebra de linha

      // Verifica se a entrada é um número inteiro
      if (scanner.hasNextInt()) {
        opcaoMenu = scanner.nextInt();
        System.out.printf("Escolheu [%d]%n\n\n\n", opcaoMenu);
        scanner.nextLine(); // limpa o \n do buffer

        switch (opcaoMenu) {
          case 1:
            System.out.print("Digite o nome de usuário: ");
            String nome = scanner.nextLine();

            System.out.print("Digite a senha: ");
            String senha = scanner.nextLine();

            if (Model_User.autenticar(nome, senha)) {
              System.out.println("Login realizado com sucesso!\n");
              Login.menu_login(nome, senha, scanner); // chama menu após login
            } else {
              System.out.println("Nome ou senha inválidos!\n");
            }
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

    } while (opcaoMenu != 3);

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