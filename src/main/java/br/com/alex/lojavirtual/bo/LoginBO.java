package br.com.alex.lojavirtual.bo;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;

import br.com.alex.lojavirtual.repository.LoginRepository;
import br.com.alex.lojavirtual.validation.LoginValidation;

/**
 * Created by 39091 on 04/07/2016.
 */
public class LoginBO {

    private LoginRepository loginRepository;

    public LoginBO(Activity activity) {
        loginRepository = new LoginRepository(activity);
        // loginRepository.popularBD();

      //  loginRepository.listarLogins(activity);

    }

    public boolean validarCamposLogin(LoginValidation validation) {

        boolean resultado = true;

        if (validation.getUsuario() == null || "".equals(validation.getUsuario())) {
            validation.getEdtUsuario().setError("Campo obrigatorio");
            // Util.showMsgToast(LoginActivity.this, "Campo usuário é obrigatório!");
            resultado = false;
        }
        if (validation.getSenha() == null || "".equals(validation.getSenha())) {
            validation.getEdtSenha().setError("Campo obrigatorio");
            //  Util.showMsgToast(LoginActivity.this, "Campo senha é obrigatório!");
            resultado = false;
        }
        // else if (senha.length() < 6) {
        //    edtSenha.setError("Campo deve ter no mínimo 6 caracteres!");
        //    resultado = false;
        // }

        if (resultado) {

            loginRepository.addLogin(validation.getUsuario(), validation.getSenha());


           // if (!validation.getUsuario().equals("admin") || !validation.getSenha().equals("admin")) {

             //   Util.showMsgToast(validation.getActivity(), "Login/Senha inválidos!");

                //  Util.showMsgAlertOK(LoginActivity.this, "Erro", "teste", TipoMsg.ERROR);
              //  resultado = false;
           // } else {
                SharedPreferences.Editor editor = validation.getActivity().getSharedPreferences("pref", Context.MODE_PRIVATE).edit();
                editor.putString("usuario", validation.getUsuario());
                editor.putString("senha", validation.getSenha());
                editor.commit();
           // }
        }
        return resultado;
    }

    public void deslogar() {

    }
}
