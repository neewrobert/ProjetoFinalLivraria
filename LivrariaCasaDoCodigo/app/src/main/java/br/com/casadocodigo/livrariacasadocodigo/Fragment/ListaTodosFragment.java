package br.com.casadocodigo.livrariacasadocodigo.Fragment;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import java.util.List;

import br.com.casadocodigo.livrariacasadocodigo.Adapter.LivrosAdapter;
import br.com.casadocodigo.livrariacasadocodigo.Dao.LivroDao;
import br.com.casadocodigo.livrariacasadocodigo.Entities.Livro;
import br.com.casadocodigo.livrariacasadocodigo.R;

/**
 * Created by nrdossantos on 04/11/2017.
 */

public class ListaTodosFragment extends android.app.Fragment {

   private View  viewListaTodos;

   private ListView listView;

    @Nullable
    @Override
    public View onCreateView(final LayoutInflater inflater, @Nullable ViewGroup container, @Nullable final Bundle savedInstanceState) {

        viewListaTodos = inflater.inflate(R.layout.lista_todos_layout, container, false);
        listView = viewListaTodos.findViewById(R.id.lista_todos_listaLivros);

        carregaLista();


        return viewListaTodos;

    }

    public void carregaLista() {
        LivroDao dao = new LivroDao(viewListaTodos.getContext());
        List<Livro> livros = dao.getTodosLivros();
        LivrosAdapter adapter = new LivrosAdapter(viewListaTodos.getContext(), livros);
        listView.setAdapter(adapter);


    }

    @Override
    public void onResume() {
        super.onResume();
        carregaLista();
    }


    public View getViewListaTodos() {
        return viewListaTodos;
    }


    public ListView getListView() {
        return listView;
    }


}
