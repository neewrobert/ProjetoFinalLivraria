package br.com.casadocodigo.livrariacasadocodigo;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;

public class HomeActivity extends AppCompatActivity {

    Button btnCatalogo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        Toolbar barraTarefas = (Toolbar) findViewById(R.id.home_barraTarefas);
        barraTarefas.setLogo(R.drawable.ic_icone_bar);
        setSupportActionBar(barraTarefas);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent vaiCadastroLivro = new Intent(HomeActivity.this, CadastrarLivroActivity.class);
                startActivity(vaiCadastroLivro);
            }
        });

        btnCatalogo = (Button) findViewById(R.id.adm_btnCatalogo);
        btnCatalogo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intentVaiCatalogo = new Intent(HomeActivity.this, CatalogoActivity.class);
                startActivity(intentVaiCatalogo);
            }
        });


        Button btnAdm = (Button) findViewById(R.id.adm_btnAlterarSenhadmDados);
        btnAdm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(HomeActivity.this, AdministracaoActivity.class);
                startActivity(intent);
            }
        });

        Button btnSenha = (Button) findViewById(R.id.adm_btnAlterarSenha);
        btnSenha.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(HomeActivity.this, MudarSenhaActivity.class);
                startActivity(intent);
            }
        });

        Button btnSobre = (Button) findViewById(R.id.adm_btnSobreEquipe);
        btnSobre.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intentSobre = new Intent(HomeActivity.this, SobreActivity.class);
                startActivity(intentSobre);
            }
        });

        Button btnSair = (Button) findViewById(R.id.adm_btnSair);
        btnSair.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(HomeActivity.this, LoginActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK |    Intent.FLAG_ACTIVITY_CLEAR_TASK);
                intent.putExtra("SAIR", true);
                startActivity(intent);
                finish();
            }
        });



    }

}
