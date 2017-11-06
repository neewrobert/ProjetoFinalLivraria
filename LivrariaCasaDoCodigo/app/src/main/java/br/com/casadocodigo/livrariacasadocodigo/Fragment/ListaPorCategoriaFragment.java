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
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

import br.com.casadocodigo.livrariacasadocodigo.Adapter.LivrosAdapter;
import br.com.casadocodigo.livrariacasadocodigo.CadastrarLivroActivity;
import br.com.casadocodigo.livrariacasadocodigo.Dao.CategoriaDao;
import br.com.casadocodigo.livrariacasadocodigo.Dao.LivroDao;
import br.com.casadocodigo.livrariacasadocodigo.Entities.Categoria;
import br.com.casadocodigo.livrariacasadocodigo.Entities.Livro;
import br.com.casadocodigo.livrariacasadocodigo.R;

/**
 * Created by nrdossantos on 04/11/2017.
 */

public class ListaPorCategoriaFragment extends android.app.Fragment implements AdapterView.OnItemSelectedListener {

    private ListView listaLivros;
    View viewListaPorCategoria;
    Spinner campoCategoria;
    List<Livro> livros;


    @Nullable
    @Override
    public View onCreateView(final LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        viewListaPorCategoria = inflater.inflate(R.layout.lista_por_categoria_layout, container, false);
        listaLivros = viewListaPorCategoria.findViewById(R.id.lista_categoria_listaLivros);
        campoCategoria = viewListaPorCategoria.findViewById(R.id.busca_categoria_spinner);


        registerForContextMenu(listaLivros);

        carregaLista();
        carregaSpinner();
        campoCategoria.setOnItemSelectedListener(this);



        return viewListaPorCategoria;
    }

    public void carregaLista() {
        LivroDao dao = new LivroDao(viewListaPorCategoria.getContext());
        livros = dao.getTodosLivros();
        LivrosAdapter adapter = new LivrosAdapter(viewListaPorCategoria.getContext(), livros);
        listaLivros.setAdapter(adapter);


    }

    private List<Categoria> carregaSpinner() {
        CategoriaDao categoriaDao = new CategoriaDao(viewListaPorCategoria.getContext());
        List<Categoria> categorias = categoriaDao.getTodasCategorias();

        if (!categorias.isEmpty()) {
            ArrayAdapter<Categoria> adapter = new ArrayAdapter<Categoria>(viewListaPorCategoria.getContext(), android.R.layout.simple_spinner_item, categorias);
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            campoCategoria.setAdapter(adapter);

        } else {
            campoCategoria.setPrompt("Nenhuma categoria cadastrada");
        }

        return categorias;
    }

    @Override
    public void onResume() {
        super.onResume();
        Categoria categoria = (Categoria) campoCategoria.getSelectedItem();

        carregaListaPorCategoria(categoria.getId());
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

        Categoria categoria = (Categoria) campoCategoria.getSelectedItem();

        carregaListaPorCategoria(categoria.getId());



    }

    private void carregaListaPorCategoria(Long id) {
        LivroDao dao = new LivroDao(viewListaPorCategoria.getContext());
        livros = dao.getLivrosPorCategoria(id);
        LivrosAdapter adapter = new LivrosAdapter(viewListaPorCategoria.getContext(), livros);
        listaLivros.setAdapter(adapter);
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }

    private int posicaoArray(List<Categoria> categorias, int idCategoria) {
        int posicao = 0;
        CategoriaDao categoriaDao = new CategoriaDao(viewListaPorCategoria.getContext());
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

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {

        AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) menuInfo;
        final Livro livro = (Livro) listaLivros.getItemAtPosition(info.position);

        MenuItem itemExcluir = menu.add("Excluir");
        itemExcluir.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                final LivroDao dao = new LivroDao(viewListaPorCategoria.getContext());

                new AlertDialog.Builder(viewListaPorCategoria.getContext())
                        .setTitle("Deletar Livro")
                        .setMessage("Voce deseja deletar o livro: " + livro.getTitulo() + " ?")
                        .setPositiveButton("Sim", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {

                                dao.delete(livro);
                                dao.close();
                                Toast.makeText(viewListaPorCategoria.getContext(), "Livro " + livro.getTitulo() + " Excluido", Toast.LENGTH_SHORT).show();
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
