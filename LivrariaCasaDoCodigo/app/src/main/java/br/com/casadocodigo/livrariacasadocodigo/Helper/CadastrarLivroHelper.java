package br.com.casadocodigo.livrariacasadocodigo.Helper;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import java.io.File;
import java.util.List;

import br.com.casadocodigo.livrariacasadocodigo.CadastrarLivroActivity;
import br.com.casadocodigo.livrariacasadocodigo.Dao.CategoriaDao;
import br.com.casadocodigo.livrariacasadocodigo.Entities.Categoria;
import br.com.casadocodigo.livrariacasadocodigo.Entities.Livro;
import br.com.casadocodigo.livrariacasadocodigo.Exception.fotoApagadaException;
import br.com.casadocodigo.livrariacasadocodigo.R;

/**
 * Created by nrdossantos on 03/11/2017.
 */

public class CadastrarLivroHelper {

    private EditText campoIsbn;
    private EditText campoTitulo;
    private EditText campoSubTitulo;
    private EditText campoEdicao;
    private EditText campoAutor;
    private EditText campoQtdPaginas;
    private EditText campoAno;
    private EditText campoEditora;
    private Spinner campoCategoria;
    private ImageView campoFoto;
    private Livro livro;


    private List<Categoria> categorias;
    private Context context;


    public CadastrarLivroHelper(CadastrarLivroActivity activity, Context context) {

        campoIsbn = (EditText) activity.findViewById(R.id.cadLivro_isbn);
        campoTitulo = (EditText) activity.findViewById(R.id.cadLivro_titulo);
        campoSubTitulo = (EditText) activity.findViewById(R.id.cadLivro_SubTitulo);
        campoEdicao = (EditText) activity.findViewById(R.id.cadLivro_edicao);
        campoAutor = (EditText) activity.findViewById(R.id.cadLivro_autor);
        campoQtdPaginas = (EditText) activity.findViewById(R.id.cadLivro_qtdPaginas);
        campoAno = (EditText) activity.findViewById(R.id.cadLivro_ano);
        campoEditora = (EditText) activity.findViewById(R.id.cadLivro_editora);
        campoCategoria = (Spinner) activity.findViewById(R.id.cadLivros_categorias);
        campoFoto = (ImageView) activity.findViewById(R.id.cadLivro_foto);

        this.context = context;
        livro = new Livro();

    }

    public Livro getLivro() {


        String isbn = campoIsbn.getText().toString();
        livro.setIsbn(isbn.equals("") ? 0 : Long.parseLong(isbn));
        livro.setTitulo(campoTitulo.getText().toString());
        livro.setSubTitulo(campoSubTitulo.getText().toString());
        livro.setEdicao(campoEdicao.getText().toString());
        livro.setAutor(campoAutor.getText().toString());
        String qtdPaginas = campoQtdPaginas.getText().toString();
        livro.setQtdPaginas(qtdPaginas.equals("") ? 0 : Long.parseLong(qtdPaginas));
        String ano = campoAno.getText().toString();
        livro.setAno(ano.equals("") ? 0 : Long.parseLong(ano));
        livro.setEditora(campoEditora.getText().toString());
        livro.setFoto((String) campoFoto.getTag());
        Categoria categoria = (Categoria) campoCategoria.getSelectedItem();
        if(categoria == null){
            Toast.makeText(context, "Voce deve cadastrar uma categoria", Toast.LENGTH_SHORT).show();
        } else {
            livro.setIdCategoria(categoria.getId());
        }


        return livro;

    }


    public void associarImagem(String caminhoFoto) throws fotoApagadaException {

        if (caminhoFoto != null && !caminhoFoto.equals("")) {
            if (new File(caminhoFoto).exists()) {
                if (caminhoFoto != null && !caminhoFoto.equals("")) {
                    Bitmap bitmap = BitmapFactory.decodeFile(caminhoFoto);
                    Bitmap bitmapReduzido = Bitmap.createScaledBitmap(bitmap, 450, 300, true);
                    campoFoto.setImageBitmap(bitmapReduzido);
                    campoFoto.setTag(caminhoFoto);
                }
            } else {
                throw new fotoApagadaException("A foto foi movida ou apagada");
            }
        } else {
            throw new fotoApagadaException("Sem foto cadastrada");
        }
    }

    public void preencheFormulario(Livro livro) throws fotoApagadaException {

        this.livro = livro;
        String isbn = (livro.getIsbn() != null ? livro.getIsbn().toString() : "0");
        campoIsbn.setText(isbn);
        campoIsbn.setEnabled(false);
        String titulo = livro.getTitulo() != null ? livro.getTitulo() : "Titulo";
        campoTitulo.setText(titulo);
        String subTitulo = livro.getSubTitulo() != null ? livro.getSubTitulo() : "SubTitulo";
        campoSubTitulo.setText(subTitulo);
        String edicao = livro.getEdicao() != null ? livro.getEdicao() : "edicao";
        campoEdicao.setText(edicao);
        String autor = livro.getAutor() != null ? livro.getAutor() : "autor";
        campoAutor.setText(autor);
        String qtdPaginas = livro.getQtdPaginas() != null ? livro.getQtdPaginas().toString() : "0";
        campoQtdPaginas.setText(qtdPaginas);
        String ano = livro.getAno() != null ? livro.getAno().toString() : "0";
        campoAno.setText(livro.getAno().toString());
        String editora = livro.getEditora() != null ? livro.getEditora().toString() : "Editora";
        campoEditora.setText(editora);
        try{
            associarImagem(livro.getFoto());
        } catch (fotoApagadaException e){
            throw e;
        }
        if (livro.getIdCategoria() != null){

            int idCategoria = Integer.parseInt(livro.getIdCategoria().toString());
            List<Categoria> categorias = carregaSpinner();
            campoCategoria.setSelection(posicaoArray(categorias, idCategoria));

        } else {
            carregaSpinner();
        }



    }

    private int posicaoArray(List<Categoria> categorias, int idCategoria) {
        int posicao = 0;
        CategoriaDao categoriaDao = new CategoriaDao(context);
        Categoria categoria = categoriaDao.getCategoriaById(idCategoria);


        if (categoria != null) {
            for (int i = 0; i <= categorias.size() - 1; i++){

                if (categorias.get(i).equals(categoria)){
                    posicao = i;
                    break;
                } else {
                    posicao = 0;
                }
            }
        } else {
            posicao = 0;
        }

        return posicao;
    }

    private List<Categoria> carregaSpinner() {
        CategoriaDao categoriaDao = new CategoriaDao(context);
        List<Categoria> categorias = categoriaDao.getTodasCategorias();

        if (!categorias.isEmpty()){
            ArrayAdapter<Categoria> adapter = new ArrayAdapter<Categoria>(context, android.R.layout.simple_spinner_item, categorias);
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            campoCategoria.setAdapter(adapter);

        } else{
            campoCategoria.setPrompt("Nenhuma categoria cadastrada");
        }

        return categorias;
    }

}
