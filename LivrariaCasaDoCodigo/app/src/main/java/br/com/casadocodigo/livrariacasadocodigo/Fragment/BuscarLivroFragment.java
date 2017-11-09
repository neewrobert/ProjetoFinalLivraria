package br.com.casadocodigo.livrariacasadocodigo.Fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import br.com.casadocodigo.livrariacasadocodigo.Adapter.LivrosAdapter;
import br.com.casadocodigo.livrariacasadocodigo.Dao.LivroDao;
import br.com.casadocodigo.livrariacasadocodigo.Entities.Livro;
import br.com.casadocodigo.livrariacasadocodigo.R;

/**
 * Created by Newton on 07/11/2017.
 */

public class BuscarLivroFragment extends android.app.Fragment {

    View viewBuscaLivros;
    ListView listaLivros;
    EditText campoBusca;
    ImageButton btnBuscar;
    List<Livro> livrosBuscados;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {

        viewBuscaLivros = inflater.inflate(R.layout.buscar_livros_layout, container, false);
        listaLivros = viewBuscaLivros.findViewById(R.id.buscar_listaAlunos);
        carregaLista();

        campoBusca =  (EditText) viewBuscaLivros.findViewById(R.id.buscar_campoBusca);
        btnBuscar = (ImageButton) viewBuscaLivros.findViewById(R.id.buscar_btnBusca);


        btnBuscar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                LivroDao livroDao = new LivroDao(viewBuscaLivros.getContext());

                String valorBusca;
                if (campoBusca.getText() != null && !campoBusca.getText().toString().equals("")){
                    valorBusca = campoBusca.getText().toString();

                    livrosBuscados = livroDao.pesquisarAutorTitulo(valorBusca);

                    if(livrosBuscados.isEmpty()){
                        carregaListaVazia();
                        Toast.makeText(viewBuscaLivros.getContext(), "Nao encontrado", Toast.LENGTH_SHORT).show();
                    } else {
                        carregaListaBuscados(livrosBuscados);
                    }


                }


            }
        });



        return viewBuscaLivros;


    }

    private void carregaListaBuscados(List<Livro> livrosBuscados) {
        LivrosAdapter adapter = new LivrosAdapter(viewBuscaLivros.getContext(), livrosBuscados);
        listaLivros.setAdapter(adapter);
    }

    private void carregaListaVazia() {
        List<Livro> livros = new ArrayList<>();
        LivrosAdapter adapter = new LivrosAdapter(viewBuscaLivros.getContext(), livros);
        listaLivros.setAdapter(adapter);
    }

    public void carregaLista() {
        LivroDao dao = new LivroDao(viewBuscaLivros.getContext());
        List<Livro> livros = dao.getTodosLivros();
        LivrosAdapter adapter = new LivrosAdapter(viewBuscaLivros.getContext(), livros);
        listaLivros.setAdapter(adapter);


    }
}
