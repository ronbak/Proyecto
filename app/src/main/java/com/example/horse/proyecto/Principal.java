package com.example.horse.proyecto;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.StrictMode;
import android.provider.MediaStore;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Toast;

import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class Principal extends AppCompatActivity {
    private SectionsPagerAdapter mSectionsPagerAdapter;

    private ViewPager mViewPager;
    private EditText inputPelicula;
    public static FloatingActionButton fab;
    Usuario usuario;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_principal);

        //Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        //setSupportActionBar(toolbar);
        // Create the adapter that will return a fragment for each of the three
        // primary sections of the activity.
        mSectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager(), this, this);

        // Set up the ViewPager with the sections adapter.
        mViewPager = (ViewPager) findViewById(R.id.container);
        mViewPager.setAdapter(mSectionsPagerAdapter);

        TabLayout tabLayout = (TabLayout) findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(mViewPager);
        /*findViewById(R.id.fab).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Principal.this, Mapa.class));
            }
        });*/

        usuario = (Usuario)getIntent().getExtras().getSerializable("parametro");

        /*fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                REST();
                //onLaunchCamera();
            }
        });*/


    }//-------------------------------------------------------------------FIN ONCREATE

    @Override
    public void onBackPressed() {
        //Toast.makeText(getApplicationContext(), "Te atrape", Toast.LENGTH_LONG).show();
        //super.onBackPressed(); //habilite esto si desea que se devuelva con el boton back
        //Button MiBoton = (Button) findViewById(R.id.btnCancelar);
        //MiBoton.performClick();

    }

    //------------------- SERVICIO REST ----------------------------



    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_principal, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

/*    static final int REQUEST_IMAGE_CAPTURE = 1;

    private void dispatchTakePictureIntent() {
        Intent imageCaptureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        startActivityForResult(imageCaptureIntent, REQUEST_IMAGE_CAPTURE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK) {
            Bundle extras = data.getExtras();
            Bitmap imageBitmap = (Bitmap) extras.get("data");
           // mImageView.setImageBitmap(imageBitmap);
        }
    }*/


    public final String APP_TAG = "MyCustomApp";
    public final static int CAPTURE_IMAGE_ACTIVITY_REQUEST_CODE = 1034;
    public String photoFileName = "photo.jpg";
    public static boolean tomarFoto = true;

   /* public void onLaunchCamera() {
        // create Intent to take a picture and return control to the calling application
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        intent.putExtra(MediaStore.EXTRA_OUTPUT, getPhotoFileUri(photoFileName)); // set the image file name

        // If you call startActivityForResult() using an intent that no app can handle, your app will crash.
        // So as long as the result is not null, it's safe to use the intent.
        if (intent.resolveActivity(getPackageManager()) != null) {
            // Start the image capture intent to take photo
            startActivityForResult(intent, CAPTURE_IMAGE_ACTIVITY_REQUEST_CODE);
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == CAPTURE_IMAGE_ACTIVITY_REQUEST_CODE) {
            if (resultCode == RESULT_OK) {
                Uri takenPhotoUri = getPhotoFileUri(photoFileName);
                // by this point we have the camera photo on disk
                Bitmap takenImage = BitmapFactory.decodeFile(takenPhotoUri.getPath());
                // Load the taken image into a preview
               // ImageView ivPreview = (ImageView) findViewById(R.id.ivPreview);
               // ivPreview.setImageBitmap(takenImage);
            } else { // Result was a failure
                Toast.makeText(this, "Picture wasn't taken!", Toast.LENGTH_SHORT).show();
            }
        }
    }*/

    static final int REQUEST_IMAGE_CAPTURE = 1;

    private void dispatchTakePictureIntent() {
        Intent imageCaptureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        startActivityForResult(imageCaptureIntent, REQUEST_IMAGE_CAPTURE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == RESULT_OK) {
            if(tomarFoto == false && data != null){
                Uri imageUri = data.getData();
                try {
                    Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), imageUri);
                    ((ImageView) findViewById(R.id.imageView)).setImageBitmap(bitmap);
                }
                catch (Exception e){

                }
            }
            if(tomarFoto == true) {
                Bundle extras = data.getExtras();
                Bitmap imageBitmap = (Bitmap) extras.get("data");
                ((ImageView) findViewById(R.id.imageView)).setImageBitmap(imageBitmap);
            }
        }
    }

    public static String bitmapToBase64(Bitmap bitmap) {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, byteArrayOutputStream);
        byte[] byteArray = byteArrayOutputStream .toByteArray();
        return Base64.encodeToString(byteArray, Base64.DEFAULT);
    }

    public static Bitmap base64ToBitmap(String b64) {
        byte[] imageAsBytes = Base64.decode(b64.getBytes(), Base64.DEFAULT);
        return BitmapFactory.decodeByteArray(imageAsBytes, 0, imageAsBytes.length);
    }

    // Returns the Uri for a photo stored on disk given the fileName
   /* public Uri getPhotoFileUri(String fileName) {
        // Only continue if the SD Card is mounted
        if (isExternalStorageAvailable()) {
            // Get safe storage directory for photos
            // Use `getExternalFilesDir` on Context to access package-specific directories.
            // This way, we don't need to request external read/write runtime permissions.
            File mediaStorageDir = new File(
                    getExternalFilesDir(Environment.DIRECTORY_PICTURES), APP_TAG);

            // Create the storage directory if it does not exist
            if (!mediaStorageDir.exists() && !mediaStorageDir.mkdirs()){
                Log.d(APP_TAG, "failed to create directory");
            }

            // Return the file target for the photo based on filename
            return Uri.fromFile(new File(mediaStorageDir.getPath() + File.separator + fileName));
        }
        return null;
    }*/

    // Returns true if external storage for photos is available
    private boolean isExternalStorageAvailable() {
        String state = Environment.getExternalStorageState();
        return state.equals(Environment.MEDIA_MOUNTED);
    }

    /**
     * A placeholder fragment containing a simple view.
     */
    public static class PlaceholderFragment extends Fragment {
        /**
         * The fragment argument representing the section number for this
         * fragment.
         */
        private static String ARG_SECTION_NUMBER = "section_number";
        static Activity p;
        static Principal a;
        static boolean primera = true;
        private List<Reporte> reportes = new ArrayList<Reporte>();
        static final int REQUEST_IMAGE_CAPTURE = 1;

        /**
         * Returns a new instance of this fragment for the given section
         * number.
         */
        public static PlaceholderFragment newInstance(int sectionNumber, Activity pr, Principal pp) {
            PlaceholderFragment fragment = new PlaceholderFragment();
            p = pr;
            a = pp;

           Bundle args = new Bundle();
            args.putInt(ARG_SECTION_NUMBER, sectionNumber);
           fragment.setArguments(args);
            return fragment;
        }

        public PlaceholderFragment() {
/*            reportes.add(new Reporte("Seguridad", "Robo", "San Jose", "12/12/12"));
            reportes.add(new Reporte("Luz", "Poste caido", "Alajuela", "12/12/12"));
            reportes.add(new Reporte("Agua", "Tuberia en mal estado", "Heredia", "12/12/12"));*/
        }

        public void cargaReportes(){
            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
            StrictMode.setThreadPolicy(policy);
            String URL = "http://empere12-001-site1.btempurl.com/WebServiceApiRouter.svc/api/reportes";
            try{
                String result = "";
                HttpClient httpclient = new DefaultHttpClient();
                HttpResponse response = httpclient.execute(new HttpGet(URL));
                BufferedReader reader = new BufferedReader(new InputStreamReader(response.getEntity().getContent(),"UTF-8"));
                result=reader.readLine();


                JSONObject obj = new JSONObject(result);
                JSONArray proveedores = obj.getJSONArray("lista");


                for (int i=0;i<proveedores.length();i++){
                    JSONObject json = proveedores.getJSONObject(i);
                    String fecha = json.getString("fecha");
                    String tipo = json.getString("tipo");
                    String descripcion = json.getString("descripcion");
                    String direccion = json.getString("direccion");

                    if(tipo.equals("1")){
                        tipo = "Agua";
                    }
                    else if(tipo.equals("2")){
                        tipo = "Luz";
                    }
                    else{
                        tipo = "Seguridad";
                    }


                    reportes.add(new Reporte(tipo, descripcion, direccion, fecha));
                    if(i == 10)
                        break;

                }

            }catch(JSONException e){
                e.printStackTrace();
            }catch(ClientProtocolException e){
                e.printStackTrace();
            }catch (IOException e){
                e.printStackTrace();
            }
        };
        //-----------------------------------------------------------------------


        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {

            View rootView = inflater.inflate(R.layout.fragment_principal, container, false);

            if(getArguments().getInt(ARG_SECTION_NUMBER) == 1){
                rootView = inflater.inflate(R.layout.inicio, container, false);

                //HACER LO QUE TENGA QUE VER CON INICIO

                cargaReportes();


                rootView.findViewById(R.id.fab).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        startActivity(new Intent(getContext(), Mapa.class));
                    }
                });



                    ArrayAdapter<Reporte> adapter = new AdaptadorReporte(p, reportes);
                    ListView list = (ListView) rootView.findViewById(R.id.listaReportes);
                    list.setAdapter(adapter);


                        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                            @Override
                            public void onItemClick(AdapterView<?> parent, View viewClicked,
                                                    int position, long id) {
                                //Car clickedCar = myCars.get(position);
                                String message = "Elegiste item No. " + (1 + position);
                                Toast.makeText(p, message,
                                        Toast.LENGTH_SHORT).show();
                            }
                        });




                //fab.setVisibility(View.VISIBLE);
            }

            if(getArguments().getInt(ARG_SECTION_NUMBER) == 2){


                rootView = inflater.inflate(R.layout.reporte, container, false);

                Spinner s1;
                final String[] presidents = {
                        "Seguridad",
                        "Luz",
                        "Agua"};

                s1 = (Spinner) rootView.findViewById(R.id.spinner);
                ArrayAdapter<String> adapter = new ArrayAdapter<String>(rootView.getContext(), android.R.layout.simple_spinner_item, presidents);

                s1.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> parent, View view,
                                               int position, long id) {
                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> parent) {
                    }
                });
                s1.setAdapter(adapter);

                ImageButton im = (ImageButton) rootView.findViewById(R.id.imageButton);


                im.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        //Toast.makeText(getContext(), "Tomar foto o seleccionar una", Toast.LENGTH_LONG).show();

                        // a.onLaunchCamera();

                        // Intent imageCaptureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                        //   startActivityForResult(imageCaptureIntent, REQUEST_IMAGE_CAPTURE);


                        //

                        //Intent i = new Intent(Intent.ACTION_PICK,android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                        // startActivityForResult(i, RESULT_LOAD_IMAGE);

                        //   Intent galleryIntent = new Intent(Intent.ACTION_GET_CONTENT);
                        //   galleryIntent.setType("image/*");
                        //    startActivityForResult(galleryIntent, 2);

                        AlertDialog.Builder builder1 = new AlertDialog.Builder(getContext());
                        builder1.setMessage("Elija una opción");
                        builder1.setCancelable(true);
                        builder1.setPositiveButton("Tomar una fotografía",
                                new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int id) {

                                        Intent imageCaptureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                                        startActivityForResult(imageCaptureIntent, REQUEST_IMAGE_CAPTURE);
                                        tomarFoto = true;

                                    }
                                });
                        builder1.setNegativeButton("Cargar una fotografía",
                                new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int id) {

                                        Intent galleryIntent = new Intent(Intent.ACTION_GET_CONTENT);
                                        galleryIntent.setType("image/*");
                                        startActivityForResult(galleryIntent, 2);
                                        tomarFoto = false;

                                    }
                                });
                        AlertDialog alert11 = builder1.create();
                        alert11.show();


                    }
                });


                ImageButton im2 = (ImageButton) rootView.findViewById(R.id.ubicacion);


                im2.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intento = new Intent(getContext(), IngresarLocalizacion.class);
                        startActivity(intento);
                    }
                });


            }





            if(getArguments().getInt(ARG_SECTION_NUMBER) == 3){
                rootView = inflater.inflate(R.layout.cuenta, container, false);

                //HACER  LO QUE TENGA QUE VER CON CUENTA

                //a.dispatchTakePictureIntent()
                rootView.findViewById(R.id.fuera).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        a.finish();
                        startActivity(new Intent(a, InicioSession.class));
                    }
                });

                rootView.findViewById(R.id.ajustes).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        startActivity(new Intent(a, ModificarUsuario.class));
                    }
                });

            }


            return rootView;
        }
    }


    /**
     * A {@link FragmentPagerAdapter} that returns a fragment corresponding to
     * one of the sections/tabs/pages.
     */
    public class SectionsPagerAdapter extends FragmentPagerAdapter {

        Activity p;
        Principal pp;

        public SectionsPagerAdapter(FragmentManager fm, Activity p, Principal pp) {
            super(fm);
            this.p = p;
            this.pp = pp;
        }

        @Override
        public Fragment getItem(int position) {
            // getItem is called to instantiate the fragment for the given page.
            // Return a PlaceholderFragment (defined as a static inner class below).
            //return PlaceholderFragment.newInstance(position + 1);

            return PlaceholderFragment.newInstance(position + 1, p, pp);

        }

        @Override
        public int getCount() {
            // Show 3 total pages.
            return 3;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            Locale l = Locale.getDefault();
            switch (position) {
                case 0:
                    return getString(R.string.title_section1).toUpperCase(l);
                case 1:
                    return getString(R.string.title_section2).toUpperCase(l);
                case 2:
                    return getString(R.string.title_section3).toUpperCase(l);
            }
            return null;
        }
    }
}
