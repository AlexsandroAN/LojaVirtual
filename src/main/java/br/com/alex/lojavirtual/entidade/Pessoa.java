package br.com.alex.lojavirtual.entidade;

import java.io.Serializable;
import java.util.Date;

/**
 * Created by 39091 on 11/07/2016.
 */
public class Pessoa implements Serializable {

    private int id;

    private String nome;

    private String endereco;

    private String cpfCnpj;

    private TipoPessoa tipoPessoa;

    private Sexo sexo;

    private Profissao profissao;

    private Date dtNasc;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getEndereco() {
        return endereco;
    }

    public void setEndereco(String endereco) {
        this.endereco = endereco;
    }

    public String getCpfCnpj() {
        return cpfCnpj;
    }

    public void setCpfCnpj(String cpfCnpj) {
        this.cpfCnpj = cpfCnpj;
    }

    public TipoPessoa getTipoPessoa() {
        return tipoPessoa;
    }

    public void setTipoPessoa(TipoPessoa tipoPessoa) {
        this.tipoPessoa = tipoPessoa;
    }

    public Sexo getSexo() {
        return sexo;
    }

    public void setSexo(Sexo sexo) {
        this.sexo = sexo;
    }

    public Profissao getProfissao() {
        return profissao;
    }

    public void setProfissao(Profissao profissao) {
        this.profissao = profissao;
    }

    public Date getDtNasc() {
        return dtNasc;
    }

    public void setDtNasc(Date dtNasc) {
        this.dtNasc = dtNasc;
    }

    @Override
    public String toString() {
        return "Pessoa{" +
                "id=" + id +
                ", nome='" + nome + '\'' +
                ", endereco='" + endereco + '\'' +
                ", cpfCnpj='" + cpfCnpj + '\'' +
                ", tipoPessoa=" + tipoPessoa +
                ", sexo=" + sexo +
                ", profissao=" + profissao +
                ", dtNasc=" + dtNasc +
                '}';
    }
}


