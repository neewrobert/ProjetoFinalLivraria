package br.com.casadocodigo.livrariacasadocodigo.Helper;

import android.content.Context;
import android.widget.EditText;

import br.com.casadocodigo.livrariacasadocodigo.Dao.UsuarioDao;
import br.com.casadocodigo.livrariacasadocodigo.Entities.Usuario;
import br.com.casadocodigo.livrariacasadocodigo.LoginActivity;
import br.com.casadocodigo.livrariacasadocodigo.R;

/**
 * Created by nrdossantos on 02/11/2017.
 */

public class LoginHelper {

    private final EditText campoEmail;
    private  final EditText campoSenha;
    private Usuario usuario;

    public LoginHelper (LoginActivity activity){

        campoEmail = (EditText) activity.findViewById(R.id.login_txtEmail);
        campoSenha = (EditText) activity.findViewById(R.id.login_txtSenha);


    }

    public boolean login(Context context){

        UsuarioDao usuarioDao = new UsuarioDao(context);

        usuario = usuarioDao.getUsuarioByEmail(campoEmail.getText().toString());

        if(usuario == null){
            return false;

        } else if (usuario.getSenha().equals(campoSenha.getText().toString())){
            return true;

        } else {
            return false;
        }


    }
}
