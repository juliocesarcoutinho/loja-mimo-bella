package br.com.mimobella;

import br.com.mimobella.util.ValidaCPF;
import br.com.mimobella.util.ValidadorCnpj;

public class TestCnpjCpf {

    public static void main(String[] args) {
        boolean isCnpj = ValidadorCnpj.isCNPJ("36.081.440/0001-68");

        System.out.println("CNPJ VALIDO = " + isCnpj);

        boolean isCpf = ValidaCPF.isCPF("367.703.578-36");

        System.out.println("Cpf Valido = " + isCpf);

    }
}
