import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;


public class Lambda {

    public static void main(String[] args) {
        
        Biblioteca biblioteca = new Biblioteca();
        biblioteca.adicionarLivro(new Livro("Dom Quixote", "Miguel de Cervantes", 79.99));
        biblioteca.adicionarLivro(new Livro("Razão e Sensibilidade", "Jane Austen", 69.99));
        biblioteca.adicionarLivro(new Livro("1984", "George Orwell", 49.99));
        biblioteca.adicionarLivro(new Livro("Persuasão", "Jane Austen", 49.99));
        biblioteca.adicionarLivro(new Livro("O Pequeno Príncipe", "Antoine de Saint-Exupéry", 39.99));
        biblioteca.adicionarLivro(new Livro("A Guerra dos Tronos", "George R.R. Martin", 119.99));
        biblioteca.adicionarLivro(new Livro("Orgulho e Preconceito", "Jane Austen", 59.99));
        biblioteca.adicionarLivro(new Livro("O Hobbit", "J.R.R. Tolkien", 89.99));
        biblioteca.adicionarLivro(new Livro("A Dança dos Dragões", "George R.R. Martin", 159.99));
        biblioteca.adicionarLivro(new Livro("Emma", "Jane Austen", 79.99));

        System.out.println("Livros de Jane Austen:");
        biblioteca.filtrarPorAutor("Jane Austen").forEach(System.out::println);

        System.out.println("\nTodos os livros ordenados por preço:");
        biblioteca.ordenarPorPreco().forEach(System.out::println);

        System.out.printf("\nPreço médio dos livros: R$ %.2f%n", biblioteca.calcularPrecoMedio());

        biblioteca.encontrarLivroMaisCaro().ifPresent(livro ->
                System.out.println("\nLivro mais caro: " + livro));

        biblioteca.encontrarLivroMaisBarato().ifPresent(livro ->
                System.out.println("Livro mais barato: " + livro));
    }
}

// Classe Biblioteca
class Biblioteca {
    private List<Livro> livros;

    public Biblioteca() {
        this.livros = new ArrayList<>();
    }

    public void adicionarLivro(Livro livro) {
        livros.add(livro);
    }

    public List<Livro> filtrarPorAutor(String autor) {
        return livros.stream()
                .filter(livro -> autor.equalsIgnoreCase(livro.getAutor()))
                .collect(Collectors.toList());
    }

    public List<Livro> ordenarPorPreco() {
        return livros.stream()
                .sorted(Comparator.comparing(Livro::getPreco))
                .collect(Collectors.toList());
    }

    public double calcularPrecoMedio() {
        return livros.stream()
                .mapToDouble(Livro::getPreco)
                .average()
                .orElse(0.0);
    }

    public Optional<Livro> encontrarLivroMaisCaro() {
        return livros.stream()
                .max(Comparator.comparing(Livro::getPreco));
    }

    public Optional<Livro> encontrarLivroMaisBarato() {
        return livros.stream()
                .min(Comparator.comparing(Livro::getPreco));
    }
}

// Classe Livro
class Livro {
    private String titulo;
    private String autor;
    private double preco;

    public Livro(String titulo, String autor, double preco) {
        this.titulo = titulo;
        this.autor = autor;
        this.preco = preco;
    }

    public String getTitulo() {
        return titulo;
    }

    public String getAutor() {
        return autor;
    }

    public double getPreco() {
        return preco;
    }

    @Override
    public String toString() {
        return "Livro: " +
                "título='" + titulo + '\'' +
                ", autor='" + autor + '\'' +
                ", preço=" + preco +
                ' ';
    }
}
