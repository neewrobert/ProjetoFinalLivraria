package br.com.casadocodigo.livrariacasadocodigo;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.FileProvider;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Adapter;
import android.widget.ArrayAdapter;
import android.widget.Button;

import java.io.File;
import java.io.IOException;
import android.net.Uri;
import android.widget.Spinner;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import br.com.casadocodigo.livrariacasadocodigo.Dao.CategoriaDao;
import br.com.casadocodigo.livrariacasadocodigo.Dao.LivroDao;
import br.com.casadocodigo.livrariacasadocodigo.Entities.Categoria;
import br.com.casadocodigo.livrariacasadocodigo.Entities.Livro;
import br.com.casadocodigo.livrariacasadocodigo.Exception.fotoApagadaException;
import br.com.casadocodigo.livrariacasadocodigo.Helper.CadastrarLivroHelper;

import static android.media.MediaRecorder.VideoSource.CAMERA;

public class CadastrarLivroActivity extends AppCompatActivity {

    private CadastrarLivroHelper helper;
    private Uri fotoUri;
    private File arquivoFoto;
    private Spinner campoCategoria;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadastrar_livro);

        Toolbar barraTarefas = (Toolbar) findViewById(R.id.cadLivro_barraTarefas);
        barraTarefas.setLogo(R.drawable.ic_icone_bar);
        setSupportActionBar(barraTarefas);

        helper = new CadastrarLivroHelper(this, this);
        campoCategoria = (Spinner) findViewById(R.id.cadLivros_categorias);

        Intent intent = getIntent();
        Livro livro = (Livro) intent.getSerializableExtra("livro");
        if (livro != null){
            try {
                helper.preencheFormulario(livro);
            } catch (fotoApagadaException e){
                Toast.makeText(this, e.getMessage(), Toast.LENGTH_SHORT).show();
            }
        }



        /**
         * Verifica se a aplicação tem permissao de gravar informacoes no celular do usuario
         */
        if(ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            if(ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.WRITE_EXTERNAL_STORAGE)) {

            } else {
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, 1);
            }
        }


        /**
         * Acao responsavel por tirar a foto do livro
         */

        Button btnFoto = (Button) findViewById(R.id.cadLivro_btnFoto);
        btnFoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tirarFoto();
            }
        });


    }

    @Override
    protected void onResume() {
        super.onResume();

        carregaSpinner();
    }

    private void carregaSpinner() {
        CategoriaDao categoriaDao = new CategoriaDao(this);
        List<Categoria> categorias = categoriaDao.getTodasCategorias();

        if (!categorias.isEmpty()){
            ArrayAdapter<Categoria> adapter = new ArrayAdapter<Categoria>(this, android.R.layout.simple_spinner_item, categorias);
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            campoCategoria.setAdapter(adapter);
        } else{
            campoCategoria.setPrompt("Nenhuma categoria cadastrada");
        }
    }

    private void tirarFoto() {
        Intent takePicutureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if(takePicutureIntent.resolveActivity(getPackageManager()) != null) {
            try {
                arquivoFoto = criarArquivo();
            } catch (IOException e){

            }
            if (arquivoFoto != null) {
                Uri photoUri = FileProvider.getUriForFile(getBaseContext(), getBaseContext().getApplicationContext().getPackageName() + ".provider", arquivoFoto);
                takePicutureIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoUri);
                startActivityForResult(takePicutureIntent, CAMERA);
            }

        }
    }

    private File criarArquivo() throws IOException{

        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        File pasta = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES);
        File imagem = new File(pasta.getPath() + File.separator + "FotoLivro_" + timeStamp + ".jpg");
        return  imagem;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        //se a acao de tirar foto ocorreu e a foto foi salva
        if (requestCode == CAMERA && resultCode == RESULT_OK){
            sendBroadcast(new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE, Uri.fromFile(arquivoFoto)));

            try {
                helper.associarImagem(arquivoFoto.getAbsolutePath());
            } catch (fotoApagadaException e){
                Toast.makeText(this, e.getMessage(), Toast.LENGTH_SHORT).show();
            }
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        MenuInflater inflater =  getMenuInflater();
        inflater.inflate(R.menu.menu_cadastrar_livro, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()){

            case R.id.menu_cadastrar_livro_confirma:

                Livro livro = helper.getLivro();
                LivroDao livroDao = new LivroDao(this);

                if(livro.getId() != null ){
                    livroDao.update(livro);
                } else {
                    livroDao.insere(livro);
                }

                livroDao.close();

                Toast.makeText(this, "Livro: " + livro.getTitulo() + "Salvo!", Toast.LENGTH_SHORT).show();
                finish();

                onBackPressed();

                break;

            case R.id.menu_cadLivro_NovaCat:

                Intent intentNovaCat = new Intent(CadastrarLivroActivity.this, CadastrarCategoriaActivity.class);
                startActivity(intentNovaCat);
                break;

        }

        return super.onOptionsItemSelected(item);
    }
}
