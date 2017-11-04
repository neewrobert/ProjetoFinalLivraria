package br.com.casadocodigo.livrariacasadocodigo.Helper;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;

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

    private final EditText campoIsbn;
    private final EditText campoTitulo;
    private final EditText campoSubTitulo;
    private final EditText campoEdicao;
    private final EditText campoAutor;
    private final EditText campoQtdPaginas;
    private final EditText campoAno;
    private final EditText campoEditora;
    private final Spinner campoCategoria;
    private final ImageView campoFoto;
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
        livro.setIsbn(Long.parseLong(campoIsbn.getText().toString()));
        livro.setTitulo(campoTitulo.getText().toString());
        livro.setSubTitulo(campoSubTitulo.getText().toString());
        livro.setEdicao(campoEdicao.getText().toString());
        livro.setAutor(campoAutor.getText().toString());
        livro.setQtdPaginas(Long.parseLong(campoQtdPaginas.getText().toString()));
        livro.setAno(Long.parseLong(campoAno.getText().toString()));
        livro.setEditora(campoEditora.getText().toString());
        livro.setFoto((String) campoFoto.getTag());
        livro.setIdCategoria(campoCategoria.getSelectedItemId());

        return livro;

    }

    public void associarImagem(String caminhoFoto) throws fotoApagadaException {

        if(new File(caminhoFoto).exists()){
            if (caminhoFoto != null && !caminhoFoto.equals("")){
                Bitmap bitmap = BitmapFactory.decodeFile(caminhoFoto);
                Bitmap bitmapReduzido = Bitmap.createScaledBitmap(bitmap, 350, 300, true );
                campoFoto.setImageBitmap(bitmapReduzido);
                campoFoto.setScaleType(ImageView.ScaleType.FIT_XY);
                campoFoto.setTag(caminhoFoto);
            }
        } else {
            throw new fotoApagadaException("A foto foi movida ou apagada");
        }
    }
}
