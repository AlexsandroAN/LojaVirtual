package br.com.alex.lojavirtual.entidade;

/**
 * Created by 39091 on 06/07/2016.
 */
public enum Profissao {

    ARQUITETO("Arquiteto de Software"),
    PEDREIRO("Pedreiro"),
    ANALISTA("Analista de Sistemas"),
    PROFESSOR("Professor"),
    DESENVOLVEDOR("desenvolvedor Android"),
    ZELADOR("zelador"),
    ADVOGADO("Advogado");

    private Profissao(String descricao) {
        this.descrisao = descricao;
    }

    private String descrisao;

    public String getDescrisao() {
        return descrisao;
    }

    public static Profissao getProfissao(int pos) {
        for (Profissao p : Profissao.values()) {
            if (p.ordinal() == pos) {
                return p;
            }
        }
        return null;
    }
}
