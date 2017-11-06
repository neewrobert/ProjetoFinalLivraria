package br.com.casadocodigo.livrariacasadocodigo.Fragment;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import java.util.List;

import br.com.casadocodigo.livrariacasadocodigo.Adapter.LivrosAdapter;
import br.com.casadocodigo.livrariacasadocodigo.CadastrarLivroActivity;
import br.com.casadocodigo.livrariacasadocodigo.Dao.LivroDao;
import br.com.casadocodigo.livrariacasadocodigo.Entities.Livro;
import br.com.casadocodigo.livrariacasadocodigo.R;

/**
 * Created by nrdossantos on 04/11/2017.
 */

public class ListaTodosFragment extends android.app.Fragment {

   private View  viewListaTodos;
   private ListView listaLivros;

    @Nullable
    @Override
    public View onCreateView(final LayoutInflater inflater, @Nullable ViewGroup container, @Nullable final Bundle savedInstanceState) {

        viewListaTodos = inflater.inflate(R.layout.lista_todos_layout, container, false);
        listaLivros = viewListaTodos.findViewById(R.id.lista_todos_listaLivros);

        carregaLista();
        registerForContextMenu(listaLivros);


        return viewListaTodos;

    }

    public void carregaLista() {
        LivroDao dao = new LivroDao(viewListaTodos.getContext());
        List<Livro> livros = dao.getTodosLivros();
        LivrosAdapter adapter = new LivrosAdapter(viewListaTodos.getContext(), livros);
        listaLivros.setAdapter(adapter);


    }

    @Override
    public void onResume() {
        super.onResume();
        carregaLista();
    }


    public View getViewListaTodos() {
        return viewListaTodos;
    }


    public ListView getListaLivros() {
        return listaLivros;
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {

        AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) menuInfo;
        final Livro livro = (Livro) listaLivros.getItemAtPosition(info.position);

        MenuItem itemExcluir = menu.add("Excluir");
        itemExcluir.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                final LivroDao dao = new LivroDao(viewListaTodos.getContext());

                new AlertDialog.Builder(viewListaTodos.getContext())
                        .setTitle("Deletar Livro")
                        .setMessage("Voce deseja deletar o livro: " + livro.getTitulo() + " ?")
                        .setPositiveButton("Sim", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {

                                dao.delete(livro);
                                dao.close();
                                Toast.makeText(viewListaTodos.getContext(), "Livro " + livro.getTitulo() + " Excluido", Toast.LENGTH_SHORT).show();
                                carregaLista();
                            }

                        }).setNegativeButton("Nao", null).show();

                return false;
            }
        });

        MenuItem itemEditar = menu.add("Editar");
        itemEditar.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {

                Intent intentVaiProCadastro = new Intent(getActivity(), CadastrarLivroActivity.class);
                intentVaiProCadastro.putExtra("livro", livro);
                startActivity(intentVaiProCadastro);
                return false;
            }
        });

    }
}
