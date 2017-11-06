package br.com.casadocodigo.livrariacasadocodigo.Helper;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import java.io.File;
import java.util.Collections;
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

    private  EditText campoIsbn;
    private  EditText campoTitulo;
    private  EditText campoSubTitulo;
    private  EditText campoEdicao;
    private  EditText campoAutor;
    private  EditText campoQtdPaginas;
    private  EditText campoAno;
    private  EditText campoEditora;
    private  Spinner campoCategoria;
    private  ImageView campoFoto;
    private Livro livro;


    private List<Categoria> categorias;


    public CadastrarLivroHelper(CadastrarLivroActivity activity) {

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
        livro.setIdCategoria(campoCategoria.getSelectedItemId());

        return livro;

    }


    public void associarImagem(String caminhoFoto) throws fotoApagadaException {

        if(new File(caminhoFoto).exists()){
            if (caminhoFoto != null && !caminhoFoto.equals("")){
                Bitmap bitmap = BitmapFactory.decodeFile(caminhoFoto);
                Bitmap bitmapReduzido = Bitmap.createScaledBitmap(bitmap, 450, 300, true );
                campoFoto.setImageBitmap(bitmapReduzido);
                campoFoto.setScaleType(ImageView.ScaleType.FIT_XY);
                campoFoto.setTag(caminhoFoto);
            }
        } else {
            throw new fotoApagadaException("A foto foi movida ou apagada");
        }
    }
}
