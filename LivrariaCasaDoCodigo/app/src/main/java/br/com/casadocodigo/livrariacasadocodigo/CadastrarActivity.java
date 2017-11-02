package br.com.casadocodigo.livrariacasadocodigo;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import br.com.casadocodigo.livrariacasadocodigo.Dao.UsuarioDao;
import br.com.casadocodigo.livrariacasadocodigo.Entities.Usuario;
import br.com.casadocodigo.livrariacasadocodigo.Helper.CadastrarHelper;

public class CadastrarActivity extends AppCompatActivity {

    private CadastrarHelper helper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadastrar);

        helper = new CadastrarHelper(this);

        Toolbar barraTarefas = (Toolbar) findViewById(R.id.login_barraTarefas);

        barraTarefas.setLogo(R.drawable.ic_icone_bar);
        setSupportActionBar(barraTarefas);
        barraTarefas.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent voltarHome = new Intent(CadastrarActivity.this, LoginActivity.class);
                startActivity(voltarHome);
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        MenuInflater inflater =  getMenuInflater();
        inflater.inflate(R.menu.menu_cadastrar, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()){
            case R.id.menu_cadastrar_confirma:

                Usuario usuario = helper.getUsuario();
                UsuarioDao dao = new UsuarioDao(this);
                dao.insere(usuario);
                dao.close();

                Toast.makeText(CadastrarActivity.this, "Usuario " + usuario.getNome() + " Cadastrado", Toast.LENGTH_SHORT).show();

                finish();
                break;

        }

        return super.onOptionsItemSelected(item);
    }
}
