package br.com.casadocodigo.livrariacasadocodigo.Adapter;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
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

import java.io.File;
import java.util.List;

import br.com.casadocodigo.livrariacasadocodigo.AdministracaoActivity;
import br.com.casadocodigo.livrariacasadocodigo.CadastrarActivity;
import br.com.casadocodigo.livrariacasadocodigo.CadastrarLivroActivity;
import br.com.casadocodigo.livrariacasadocodigo.Dao.LivroDao;
import br.com.casadocodigo.livrariacasadocodigo.Entities.Livro;
import br.com.casadocodigo.livrariacasadocodigo.R;

/**
 * Created by Newton on 15/11/2017.
 */

public class LivroAdmAdapter extends BaseAdapter{

    private final List<Livro> livros;
    private final Context context;
    private Livro livro;

    public LivroAdmAdapter(Context context, List<Livro> livros) {
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
    public View getView(final int position, final View convertView, ViewGroup parent) {

        livro = livros.get(position);

        LayoutInflater inflater = LayoutInflater.from(context);

        final View view = inflater.inflate(R.layout.listview_adm, parent, false);


        TextView campoTitulo = (TextView) view.findViewById(R.id.listview_titulo);
        campoTitulo.setText(livro.getTitulo());

        ImageView campoFoto = (ImageView) view.findViewById(R.id.listview_foto);
        String caminhoFoto = livro.getFoto();


        if (caminhoFoto != null && new File(caminhoFoto).exists()) {
            Bitmap bitmap = BitmapFactory.decodeFile(caminhoFoto);
            Bitmap bitmapReduzido = Bitmap.createScaledBitmap(bitmap, 350, 300, true);
            campoFoto.setImageBitmap(bitmapReduzido);
            campoFoto.setScaleType(ImageView.ScaleType.FIT_XY);
        }


        Button btnExcluir = (Button) view.findViewById(R.id.listview_btnExcluir);
        btnExcluir.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final LivroDao dao = new LivroDao(view.getContext());
                livro = livros.get(position);
                new AlertDialog.Builder(view.getContext())
                        .setTitle("Deletar Livro")
                        .setMessage("Voce deseja deletar o livro: " + livro.getTitulo() + " ?")
                        .setPositiveButton("Sim", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {

                                dao.delete(livro);
                                dao.close();
                                Toast.makeText(view.getContext(), "Livro " + livro.getTitulo() + " Excluido", Toast.LENGTH_SHORT).show();
                                Intent intent = new Intent(view.getContext(), AdministracaoActivity.class);
                                view.getContext().startActivity(intent);
                            }

                        }).setNegativeButton("Nao", null).show();
            }
        });

        Button btnEditar = (Button) view.findViewById(R.id.listview_btnEditar);
        btnEditar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                livro = livros.get(position);
                Intent intentVaiProCadastro = new Intent(view.getContext(), CadastrarLivroActivity.class);
                intentVaiProCadastro.putExtra("livro", livro);
                view.getContext().startActivity(intentVaiProCadastro);
            }
        });



        return view;
    }
}
