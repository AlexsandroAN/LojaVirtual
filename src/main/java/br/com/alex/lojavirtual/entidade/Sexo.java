package br.com.alex.lojavirtual.entidade;

/**
 * Created by 39091 on 08/07/2016.
 */
public enum Sexo {
    FEMININO, MASCULINO;

    public static Sexo getSexo(int pos) {
        for (Sexo sexo : Sexo.values()) {
            if (sexo.ordinal() == pos) {
                return sexo;
            }
        }
        return null;
    }

}
