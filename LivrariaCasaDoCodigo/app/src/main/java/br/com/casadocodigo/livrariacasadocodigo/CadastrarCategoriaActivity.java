package br.com.casadocodigo.livrariacasadocodigo;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import br.com.casadocodigo.livrariacasadocodigo.Dao.CategoriaDao;
import br.com.casadocodigo.livrariacasadocodigo.Entities.Categoria;

public class CadastrarCategoriaActivity extends AppCompatActivity {

    EditText campoNome;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadastrar_categoria);

        campoNome = (EditText) findViewById(R.id.cadCat_nome);

        Toolbar barraTarefas = (Toolbar) findViewById(R.id.cadCat_barraTarefas);

        barraTarefas.setLogo(R.drawable.ic_icone_bar);
        setSupportActionBar(barraTarefas);
        barraTarefas.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent voltarHome = new Intent(CadastrarCategoriaActivity.this, HomeActivity.class);
                startActivity(voltarHome);
            }
        });


        Button btnVoltar = (Button) findViewById(R.id.cadCat_btnVoltar);

        btnVoltar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });


        Button btnConfirmar = (Button) findViewById(R.id.cadCat_btnConfirma);
        btnConfirmar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CategoriaDao dao = new CategoriaDao(CadastrarCategoriaActivity.this);
                Categoria categoria = new Categoria();
                categoria.setNome(campoNome.getText().toString());
                dao.insere(categoria);
                onBackPressed();
            }
        });
    }
}
