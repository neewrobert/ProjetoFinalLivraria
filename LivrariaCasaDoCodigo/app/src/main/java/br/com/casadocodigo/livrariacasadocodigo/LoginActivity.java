package br.com.casadocodigo.livrariacasadocodigo;

import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import br.com.casadocodigo.livrariacasadocodigo.Helper.LoginHelper;

public class LoginActivity extends AppCompatActivity {

    ImageButton homePage;
    Button entrar;
    Button cadastrar;
    EditText campoEmail;
    EditText campoSenha;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        /*
            Ao clicar na imagem de cabeçalho, o usuraio é redirecionado para o site da
            casa do Código
         */
        homePage = (ImageButton) findViewById(R.id.login_imagemTop);
        homePage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intentSite = new Intent(Intent.ACTION_VIEW);
                intentSite.setData(Uri.parse("http://www.casadocodigo.com.br"));
                startActivity(intentSite);
            }
        });

        cadastrar = (Button) findViewById(R.id.login_btnCadastrar);
        cadastrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intentVaiParaCadastro = new Intent(LoginActivity.this, CadastrarActivity.class);
                startActivity(intentVaiParaCadastro);
            }
        });

        /**
         * Regra de negocio para o Login
         */
        entrar = (Button) findViewById(R.id.loging_btnEntrar);
        entrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                campoEmail = (EditText) findViewById(R.id.login_txtEmail);
                campoSenha = (EditText) findViewById(R.id.login_txtSenha);
                if(campoEmail.getText().toString() == null || campoEmail.getText().toString().equals("")){
                    Toast toast = Toast.makeText(LoginActivity.this, "Email obrigatorio", Toast.LENGTH_SHORT );
                    toast.show();
                    return;
                }

                if(campoSenha.getText().toString() == null || campoSenha.getText().toString().equals("")){
                    Toast toast = Toast.makeText(LoginActivity.this, "Senha obrigatoria", Toast.LENGTH_SHORT );
                    toast.show();
                    return;
                }

                LoginHelper helper = new LoginHelper(LoginActivity.this);
                if (helper.login(LoginActivity.this)){
                    Toast toast = Toast.makeText(LoginActivity.this, "Logado", Toast.LENGTH_SHORT );
                    toast.show();

                    Intent intent = new Intent(LoginActivity.this, HomeActivity.class);
                    startActivity(intent);
                } else {
                    Toast toast = Toast.makeText(LoginActivity.this, "Usuario nao cadastrado ou senha inválida", Toast.LENGTH_SHORT );
                    toast.show();
                }

            }
        });


        TextView btnEsqueciSenha = (TextView) findViewById(R.id.login_linkEsqueci);
        btnEsqueciSenha.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LoginActivity.this, MudarSenhaActivity.class);
                startActivity(intent);
            }
        });

        Button btnSair =  (Button) findViewById(R.id.longin_btnSair);
        btnSair.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LoginActivity.this, LoginActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK |    Intent.FLAG_ACTIVITY_CLEAR_TASK);
                intent.putExtra("SAIR", true);
                startActivity(intent);
                finish();
            }
        });


    }

    @Override
    protected void onResume() {

        if(getIntent().getBooleanExtra("SAIR", false)){
            finish();
        }
        super.onResume();
    }
}
