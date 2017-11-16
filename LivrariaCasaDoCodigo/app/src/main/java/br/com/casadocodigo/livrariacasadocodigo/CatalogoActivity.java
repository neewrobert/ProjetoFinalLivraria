package br.com.casadocodigo.livrariacasadocodigo;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import java.util.List;

import br.com.casadocodigo.livrariacasadocodigo.Adapter.LivrosAdapter;
import br.com.casadocodigo.livrariacasadocodigo.Dao.LivroDao;
import br.com.casadocodigo.livrariacasadocodigo.Entities.Livro;
import br.com.casadocodigo.livrariacasadocodigo.Fragment.BuscarLivroFragment;
import br.com.casadocodigo.livrariacasadocodigo.Fragment.ListaPorCategoriaFragment;
import br.com.casadocodigo.livrariacasadocodigo.Fragment.ListaTodosFragment;

public class CatalogoActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    private ListView listaLivros;
    private NavigationView navigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_catalogo);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setLogo(R.drawable.ic_icone_bar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent vaiCadastroLivro = new Intent(CatalogoActivity.this, CadastrarLivroActivity.class);
                startActivity(vaiCadastroLivro);
            }
        });

        //listaLivros = (ListView) findViewById(R.id.cat_ListaLivros);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        android.app.FragmentManager fragmentManager = getFragmentManager();
        fragmentManager.beginTransaction().replace(R.id.content_frame,  new ListaTodosFragment()).commit();

    }

    @Override
    protected void onResume() {
        super.onResume();

    }

    private void carregaListaTodos() {
        LivroDao livroDao = new LivroDao(this);

        List<Livro> livros = livroDao.getTodosLivros();
        livroDao.close();

        LivrosAdapter adapter = new LivrosAdapter(this, livros);
        listaLivros.setAdapter(adapter);

    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    
    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        android.app.FragmentManager fragmentManager = getFragmentManager();

        if(id == R.id.nav_listarTodos) {
            fragmentManager.beginTransaction().replace(R.id.content_frame,  new ListaTodosFragment()).commit();

        } else if (id == R.id.nav_listarCategoria ){
            fragmentManager.beginTransaction().replace(R.id.content_frame, new ListaPorCategoriaFragment()).commit();
        } else if (id == R.id.nav_pesquisar){
            fragmentManager.beginTransaction().replace(R.id.content_frame, new BuscarLivroFragment()).commit();
        }





        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
