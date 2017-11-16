package br.com.casadocodigo.livrariacasadocodigo;

import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import br.com.casadocodigo.livrariacasadocodigo.Adapter.LivroAdmAdapter;
import br.com.casadocodigo.livrariacasadocodigo.Dao.CategoriaDao;
import br.com.casadocodigo.livrariacasadocodigo.Dao.LivroDao;
import br.com.casadocodigo.livrariacasadocodigo.Entities.Categoria;
import br.com.casadocodigo.livrariacasadocodigo.Entities.Livro;

public class AdministracaoActivity extends AppCompatActivity {

    private ListView listaLivros;
    private EditText campoBusca;
    private ImageButton btnBuscar;
    private List<Livro> todosLivros;
    private List<Livro> livrosBuscados;
    private List<Categoria> categorias;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_administracao);

        Toolbar barraTarefas = (Toolbar) findViewById(R.id.adm_barraTarefas);
        barraTarefas.setLogo(R.drawable.ic_icone_bar);
        setSupportActionBar(barraTarefas);

        listaLivros = (ListView) findViewById(R.id.adm_listView);
        carregaLista();

        campoBusca =  (EditText) findViewById(R.id.adm_txtPesquisa);
        btnBuscar = (ImageButton) findViewById(R.id.adm_btnPesquisar);

        ImageButton btnNovo = (ImageButton) findViewById(R.id.adm_btnNovo);
        btnNovo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent novoLivroIntent = new Intent(AdministracaoActivity.this, CadastrarLivroActivity.class);
                startActivity(novoLivroIntent);
            }
        });



        btnBuscar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                LivroDao livroDao = new LivroDao(AdministracaoActivity.this);
                CategoriaDao categoriaDao = new CategoriaDao(AdministracaoActivity.this);


                String valorBusca;
                if (campoBusca.getText() != null && !campoBusca.getText().toString().trim().equals("")){
                    valorBusca = campoBusca.getText().toString().toLowerCase();

                    todosLivros = livroDao.getTodosLivros();
                    categorias = categoriaDao.getTodasCategorias();
                    livrosBuscados = new ArrayList<>();
                    if(todosLivros.isEmpty()){
                        carregaListaVazia();
                    } else {

                        for (Livro livro : todosLivros) {

                            if (livro.getTitulo().toLowerCase().contains(valorBusca) || livro.getAutor().toLowerCase().contains(valorBusca)){
                                livrosBuscados.add(livro);
                            }
                            for (Categoria categoria : categorias){
                                if(categoria.getNome().toLowerCase().contains(valorBusca)){
                                    if (categoria.getId().equals(livro.getIdCategoria())){
                                        if(!livrosBuscados.contains(livro)){
                                            livrosBuscados.add(livro);
                                        }

                                    }
                                }

                            }
                        }

                        if (livrosBuscados.isEmpty()){
                            carregaListaVazia();
                            Toast.makeText(AdministracaoActivity.this, "Nenhum Livro encontrado", Toast.LENGTH_SHORT).show();
                        }else {
                            carregaListaBuscados(livrosBuscados);
                        }

                    }


                }else {
                    Toast.makeText(AdministracaoActivity.this, "Campo de busca em branco", Toast.LENGTH_SHORT).show();
                }


            }
        });




        ImageButton btnHome = (ImageButton) findViewById(R.id.adm_btnHome);
        btnHome.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Intent voltarHomeIntent = new Intent(AdministracaoActivity.this, HomeActivity.class);
                startActivity(voltarHomeIntent);
                finish();
            }
        });


    }


    public void carregaLista() {
        LivroDao dao = new LivroDao(this);
        List<Livro> livros = dao.getTodosLivros();
        LivroAdmAdapter adapter = new LivroAdmAdapter(this, livros);
        listaLivros.setAdapter(adapter);
    }

    private void carregaListaBuscados(List<Livro> livrosBuscados) {
        LivroAdmAdapter adapter = new LivroAdmAdapter(this, livrosBuscados);
        listaLivros.setAdapter(adapter);
    }

    private void carregaListaVazia() {
        List<Livro> livros = new ArrayList<>();
        LivroAdmAdapter adapter = new LivroAdmAdapter(this, livros);
        listaLivros.setAdapter(adapter);
    }


    @Override
    protected void onResume() {
        carregaLista();
        super.onResume();
    }
}
