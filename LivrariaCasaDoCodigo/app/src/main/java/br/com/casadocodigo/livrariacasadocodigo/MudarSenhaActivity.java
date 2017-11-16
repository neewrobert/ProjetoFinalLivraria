package br.com.casadocodigo.livrariacasadocodigo;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import br.com.casadocodigo.livrariacasadocodigo.Dao.UsuarioDao;
import br.com.casadocodigo.livrariacasadocodigo.Entities.Usuario;

public class MudarSenhaActivity extends AppCompatActivity {

    private ImageButton btnConfirmar;
    private EditText campoEmail;
    private EditText campoSenha;
    private EditText campoConfSenha;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mudar_senha);

        Toolbar barraTarefas = (Toolbar) findViewById(R.id.esqueci_barraTarefas);

        campoEmail = (EditText) findViewById(R.id.esqueci_email);
        campoSenha = (EditText) findViewById(R.id.esqueci_senha);
        campoConfSenha = (EditText) findViewById(R.id.esqueci_confSenha);

        btnConfirmar = (ImageButton) findViewById(R.id.esqueci_btnConfirma);

        btnConfirmar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Usuario usuario;
                UsuarioDao dao = new UsuarioDao(MudarSenhaActivity.this);
                if(camposValidos()){
                    if (senhasMatch()){
                        String email = campoEmail.getText().toString().trim();
                        usuario = dao.getUsuarioByEmail(email);

                        if(usuario != null) {

                            usuario.setSenha(campoSenha.getText().toString().trim());
                            dao.update(usuario);
                            Toast.makeText(MudarSenhaActivity.this, "Senha Alterada com Sucesso", Toast.LENGTH_SHORT).show();
                            dao.close();
                            Intent login = new Intent(MudarSenhaActivity.this, LoginActivity.class);
                            startActivity(login);
                        } else {
                            Toast.makeText(MudarSenhaActivity.this, "Usuario Nao encontardo", Toast.LENGTH_SHORT).show();
                        }

                    } else {
                        Toast.makeText(MudarSenhaActivity.this, "As Senhas nao conferem", Toast.LENGTH_SHORT).show();
                    }
                }

            }
        });

        barraTarefas.setLogo(R.drawable.ic_icone_bar);
        setSupportActionBar(barraTarefas);
        barraTarefas.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent voltarHome = new Intent(MudarSenhaActivity.this, LoginActivity.class);
                startActivity(voltarHome);
            }
        });

    }

    private boolean camposValidos() {

        if (campoEmail.getText().toString().trim().equals("")){
            Toast.makeText(this, "E-mail Obrigatorio", Toast.LENGTH_LONG).show();
            return false;
        }
        if (campoSenha.getText().toString().trim().equals("")){
            Toast.makeText(this, "Senha Obrigatoria", Toast.LENGTH_LONG).show();
            return false;
        }
        if (campoConfSenha.getText().toString().trim().equals("")){
            Toast.makeText(this, "Campo confirmar Senha obrigatorio", Toast.LENGTH_LONG).show();
            return false;
        }

        return true;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        MenuInflater inflater =  getMenuInflater();
        inflater.inflate(R.menu.menu_esqueci_senha, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()){
            case R.id.menu_esqueci_novo:

                Intent novoUsuario = new Intent(MudarSenhaActivity.this, CadastrarActivity.class);
                startActivity(novoUsuario);


                break;
        }
        return super.onOptionsItemSelected(item);
    }

    private boolean senhasMatch() {
        String senha =  campoSenha.getText().toString().trim();
        String confSenha = campoConfSenha.getText().toString().trim();

        return senha.equals(confSenha);


    }
}
