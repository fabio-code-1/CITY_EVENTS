package Model;

import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;
import java.io.BufferedReader;
import java.io.Console;
import java.io.FileReader;

public class Model_User {
  private String nome;
  private String senha;

  public Model_User(Scanner scanner) {
    System.out.print("Digite o nome do usuario: ");
    this.nome = scanner.nextLine();

    Console console = System.console();
    if (console == null) {
      System.out.print("Digite a senha (visível): ");
      this.senha = scanner.nextLine();
    } else {
      char[] senhaChars = console.readPassword("Digite a senha: "); // Mais seguro
      this.senha = new String(senhaChars); // Converte o array de volta para String
    }

  }

  public void salvarEmArquivo() {
    try {
      // 1. Verifica se o usuário já existe
      BufferedReader reader = new BufferedReader(new FileReader("DB/usuarios.data"));
      String linha;
      boolean existe = false;

      /// Verifica se o nome já existe no arquivo
      while ((linha = reader.readLine()) != null) {
        String[] partes = linha.split("\\|"); // separa nome e senha
        if (partes[0].equals(nome) && partes[1].equals(senha)) { // compara o nome e senha digitado
          existe = true;
          break; // já achou, pode parar
        }
      }

      reader.close();

      if (existe) {
        System.out.println("Erro: Usuario '" + nome + "' ja existe!\n");
        return; // não salva
      }

      // 2. Salva se não existir
      FileWriter writer = new FileWriter("DB/usuarios.data", true); // true = append
      writer.write(nome + "|" + senha + "\n");
      writer.close();

      System.out.println("Usuario salvo com sucesso!\n");

    } catch (IOException e) {
      System.out.println("Erro ao acessar o arquivo: " + e.getMessage());
    }
  }

  public static boolean autenticar(String nome, String senha) {
    try {
      BufferedReader reader = new BufferedReader(new FileReader("DB/usuarios.data"));
      String linha;
      while ((linha = reader.readLine()) != null) {
        String[] partes = linha.split("\\|");
        if (partes[0].equals(nome) && partes[1].equals(senha)) {
          reader.close();
          return true; // login válido
        }
      }
      reader.close();
    } catch (IOException e) {
      System.out.println("Erro ao acessar o arquivo: " + e.getMessage());
    }
    return false; // se não encontrou
  }

}
