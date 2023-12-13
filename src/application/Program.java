package application;

import entities.Sale;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;

public class Program {
    public static void main(String[] args) {
        Locale.setDefault(Locale.US);
        Scanner sc = new Scanner(System.in);

        System.out.print("Entre o caminho do arquivo: ");
        String path = sc.next();
        System.out.println(path);

        try (BufferedReader br = new BufferedReader(new FileReader(path))) {
            List<Sale> sales = new ArrayList<>();

            String line = br.readLine();
            while (line != null) {
                String[] fields = line.split(",");
                sales.add(new Sale(Integer.parseInt(fields[0]),
                        Integer.parseInt(fields[1]), fields[2],
                        Integer.parseInt(fields[3]),
                        Double.parseDouble(fields[4])));
                line = br.readLine();
            }

            Comparator<Sale> comp = (s1, s2) -> s1.averagePrice().compareTo(s2.averagePrice());
            List<Sale> s1 = sales.stream().filter(s -> s.getYear() == 2016).sorted(comp.reversed()).limit(5L).toList();

            System.out.println("\nCinco primeiras vendas de 2016 de maior preço médio");
            s1.forEach(System.out::println);

            double sum = sales.stream()
                    .filter(s -> s.getSeller().equals("Logan"))
                    .filter(s -> s.getMonth() == 1 || s.getMonth() == 7)
                    .map(s -> s.getTotal())
                    .reduce(0.0, (x, y) -> x + y);

            System.out.println("\nValor total vendido pelo vendedor Logan nos meses 1 e 7 = " + sum);

        }
        catch (IOException e) {
            System.out.println("Erro: " + e.getMessage());
        }
        sc.close();

    }
}
