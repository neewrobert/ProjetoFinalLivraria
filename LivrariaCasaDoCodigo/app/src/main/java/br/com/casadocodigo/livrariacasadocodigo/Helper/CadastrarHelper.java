package br.com.casadocodigo.livrariacasadocodigo.Helper;

import android.widget.EditText;

import br.com.casadocodigo.livrariacasadocodigo.CadastrarActivity;
import br.com.casadocodigo.livrariacasadocodigo.Entities.Usuario;
import br.com.casadocodigo.livrariacasadocodigo.R;

/**
 * Created by nrdossantos on 02/11/2017.
 */

public class CadastrarHelper {

    //fields
    private EditText campoNome;
    private EditText campoEmail;
    private EditText campoSenha;
    private EditText campoSenhaConfirma;

    public CadastrarHelper(CadastrarActivity activity){

        campoNome = (EditText) activity.findViewById(R.id.cadastrar_txtNome);
        campoEmail = (EditText) activity.findViewById(R.id.cadastrar_txtEmail);
        campoSenha = (EditText) activity.findViewById(R.id.cadastrar_senha);
        campoSenhaConfirma = (EditText) activity.findViewById(R.id.cadastrar_senhaConfir);
    }

    public Usuario getUsuario(){

        Usuario usuario = new Usuario();

        usuario.setNome(campoNome.getText().toString());
        usuario.setEmail(campoEmail.getText().toString());
        usuario.setSenha(campoSenha.getText().toString());

     return usuario;
    }







}
