package Model;

import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;
import java.io.Console;

public class Model_User {
  private String nome;
  private String senha;

  public Model_User(Scanner scanner) {
    System.out.print("Digite o nome do usuário: ");
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
      FileWriter writer = new FileWriter("DB/usuarios.data", true); // true = append
      writer.write(nome + "|" + senha + "\n");
      writer.close();
      System.out.println("Usuario salvo com sucesso!\n");
    } catch (IOException e) {
      System.out.println("Erro ao salvar usuario: " + e.getMessage());
    }
  }

}
