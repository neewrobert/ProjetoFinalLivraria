package br.com.casadocodigo.livrariacasadocodigo;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;
import java.util.List;

import br.com.casadocodigo.livrariacasadocodigo.Dao.CategoriaDao;
import br.com.casadocodigo.livrariacasadocodigo.Entities.Categoria;
import br.com.casadocodigo.livrariacasadocodigo.Entities.Livro;
import br.com.casadocodigo.livrariacasadocodigo.Exception.FotoApagadaException;

public class DetalheActivity extends AppCompatActivity {

    private Livro livro;
    private ImageView campoFoto;
    private TextView campoTitulo;
    private TextView campoSubTitulo;
    private TextView campoAutor;
    private TextView campoIsbn;
    private TextView campoEdicao;
    private TextView campoQtdPaginas;
    private TextView campoAno;
    private TextView campoEditora;
    private TextView campoCategoria;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detalhe);

        Toolbar barraTarefas = (Toolbar) findViewById(R.id.detalhe_barraTarefas);
        barraTarefas.setLogo(R.drawable.ic_icone_bar);
        setSupportActionBar(barraTarefas);

        campoFoto = (ImageView) findViewById(R.id.detalhe_foto);
        campoTitulo = (TextView) findViewById(R.id.detalhe_txtTitulo);
        campoSubTitulo = (TextView) findViewById(R.id.detalhe_txtSubTitulo);
        campoAutor = (TextView) findViewById(R.id.detalhe_txtAutor);
        campoIsbn = (TextView) findViewById(R.id.detalhe_txtIsbn);
        campoEdicao = (TextView) findViewById(R.id.detalhe_txtEdicao);
        campoQtdPaginas = (TextView) findViewById(R.id.detalhe_txtPaginas);
        campoAno = (TextView) findViewById(R.id.detalhe_txtAno);
        campoEditora = (TextView) findViewById(R.id.detalhe_txtEditora);
        campoCategoria = (TextView) findViewById(R.id.detalhe_txtCategoria);

        Intent intent = getIntent();
        Livro livro = (Livro) intent.getSerializableExtra("livro");
        if (livro != null){
            try {
                preencheFormulario(livro);
            } catch (FotoApagadaException e){
                Toast.makeText(this, e.getMessage(), Toast.LENGTH_SHORT).show();
            }
        }


        ImageButton btnVoltar = (ImageButton) findViewById(R.id.detalhe_btnVoltar);
        btnVoltar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
                onBackPressed();
            }
        });


    }

    public void associarImagem(String caminhoFoto) throws FotoApagadaException {

        if (caminhoFoto != null && !caminhoFoto.equals("")) {
            if (new File(caminhoFoto).exists()) {
                if (caminhoFoto != null && !caminhoFoto.equals("")) {
                    Bitmap bitmap = BitmapFactory.decodeFile(caminhoFoto);
                    Bitmap bitmapReduzido = Bitmap.createScaledBitmap(bitmap, 115, 145, true);
                    campoFoto.setImageBitmap(bitmapReduzido);
                    campoFoto.setTag(caminhoFoto);
                }
            } else {
                throw new FotoApagadaException("A foto foi movida ou apagada");
            }
        } else {
            throw new FotoApagadaException("Sem foto cadastrada");
        }
    }

    public void preencheFormulario(Livro livro) throws FotoApagadaException {

        this.livro = livro;
        String isbn = (livro.getIsbn() != null ? livro.getIsbn().toString() : "");
        campoIsbn.setText(isbn);
        campoIsbn.setEnabled(false);
        String titulo = livro.getTitulo() != null ? livro.getTitulo() : "";
        campoTitulo.setText(titulo);
        String subTitulo = livro.getSubTitulo() != null ? livro.getSubTitulo() : "";
        campoSubTitulo.setText(subTitulo);
        String edicao = livro.getEdicao() != null ? livro.getEdicao() : "";
        campoEdicao.setText(edicao);
        String autor = livro.getAutor() != null ? livro.getAutor() : "";
        campoAutor.setText(autor);
        String qtdPaginas = livro.getQtdPaginas() != null ? livro.getQtdPaginas().toString() : "";
        campoQtdPaginas.setText(qtdPaginas);
        String ano = livro.getAno() != null ? livro.getAno().toString() : "";
        campoAno.setText(livro.getAno().toString());
        String editora = livro.getEditora() != null ? livro.getEditora().toString() : "";
        campoEditora.setText(editora);

        CategoriaDao categoriaDao = new CategoriaDao(this);
        List<Categoria> categorias = categoriaDao.getTodasCategorias();

        f: for (Categoria categoria : categorias) {

            if (categoria.getId().equals(livro.getIdCategoria())) {
                campoCategoria.setText(categoria.getNome());
                break f;
            }

        }


        try {
            associarImagem(livro.getFoto());
        } catch (FotoApagadaException e) {
            throw e;
        }
    }
}
