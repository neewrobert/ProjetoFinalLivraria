package br.com.casadocodigo.livrariacasadocodigo.Adapter;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

import java.io.File;
import java.util.List;

import br.com.casadocodigo.livrariacasadocodigo.Dao.LivroDao;
import br.com.casadocodigo.livrariacasadocodigo.Entities.Livro;
import br.com.casadocodigo.livrariacasadocodigo.R;

/**
 * Created by nrdossantos on 04/11/2017.
 */

public class LivrosAdapter extends BaseAdapter {

    private final List<Livro> livros;
    private final Context context;

    public LivrosAdapter(Context context, List<Livro> livros) {
        this.livros = livros;
        this.context = context;
    }


    @Override
    public int getCount() {
        return livros.size();
    }

    @Override
    public Object getItem(int position) {
        return livros.get(position);
    }

    @Override
    public long getItemId(int position) {
        return livros.get(position).getId();
    }

    @Override
    public View getView(int position, final View convertView, ViewGroup parent) {

        final Livro livro = livros.get(position);

        LayoutInflater inflater = LayoutInflater.from(context);

        final View view = inflater.inflate(R.layout.listview_catalogo, parent, false);


        TextView campoTitulo = (TextView) view.findViewById(R.id.listview_titulo);
        campoTitulo.setText(livro.getTitulo());

        TextView campoAutor = (TextView) view.findViewById(R.id.listview_autor);
        campoAutor.setText(livro.getAutor());

        ImageView campoFoto = (ImageView) view.findViewById(R.id.listview_foto);
        String caminhoFoto = livro.getFoto();

//        Button btnExcluir =  view.findViewById(R.id.listview_btnExcluir);
//        btnExcluir.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                final LivroDao dao = new LivroDao(context);
//
//                new AlertDialog.Builder(context)
//                        .setTitle("Deletar Livro")
//                        .setMessage("Voce deseja deletar o livro: " + livro.getTitulo() + " ?")
//                        .setPositiveButton("Sim", new DialogInterface.OnClickListener() {
//                            @Override
//                            public void onClick(DialogInterface dialog, int which) {
//
//                                dao.delete(livro);
//                                dao.close();
//                                Toast.makeText(context, "Livro " + livro.getTitulo() + " Excluido", Toast.LENGTH_SHORT).show();
//                            }
//                        }).setNegativeButton("Nao", null).show();
//
//            }
//        });

        if (caminhoFoto != null && new File(caminhoFoto).exists()) {
            Bitmap bitmap = BitmapFactory.decodeFile(caminhoFoto);
            Bitmap bitmapReduzido = Bitmap.createScaledBitmap(bitmap, 350, 300, true);
            campoFoto.setImageBitmap(bitmapReduzido);
            campoFoto.setScaleType(ImageView.ScaleType.FIT_XY);
        }


        return view;
    }
}
