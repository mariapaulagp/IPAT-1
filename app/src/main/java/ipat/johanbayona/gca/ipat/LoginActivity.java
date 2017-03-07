package ipat.johanbayona.gca.ipat;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

//import butterknife.ButterKnife;

public class LoginActivity extends Activity {
    Button btningresar;
    TextView txtUsuario, txtContrasena;
    Animation animación, animación1, animación3, animación4, animación5;
    LinearLayout logo, contentDown;
    RelativeLayout contentUP;
    Button btnGCA;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        //ButterKnife.bind(this);

        // Set portrait orientation
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);

        txtUsuario = (TextView) findViewById(R.id.usuario);
        txtContrasena = (TextView) findViewById(R.id.contrasena);
        btningresar = (Button) findViewById(R.id.ingresar);
        btningresar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                login();
            }
        });

        logo = (LinearLayout) findViewById(R.id.logo);
        contentUP = (RelativeLayout) findViewById(R.id.contentUp);
        contentDown= (LinearLayout) findViewById(R.id.contentDown);
        btnGCA = (Button) findViewById(R.id.BtnLogoGCA);

        animación = AnimationUtils.loadAnimation (LoginActivity.this, R.anim.opcityin);
        animación.setStartOffset(800);
        animación1 = AnimationUtils.loadAnimation (LoginActivity.this, R.anim.ani_login_torigt);
        animación1.setStartOffset(800);
        animación3 = AnimationUtils.loadAnimation (LoginActivity.this, R.anim.ani_home_toup);
        animación3.setStartOffset(850);
        animación4 = AnimationUtils.loadAnimation (LoginActivity.this, R.anim.ani_home_toup);
        animación4.setStartOffset(900);
        animación5 = AnimationUtils.loadAnimation (LoginActivity.this, R.anim.ani_home_toup);
        animación5.setStartOffset(950);

        contentUP.startAnimation(animación);
        logo.startAnimation(animación1);
        btnGCA.startAnimation(animación1);
        txtUsuario.startAnimation(animación3);
        txtContrasena.startAnimation(animación4);
        btningresar.startAnimation(animación5);

        // Que no saque el teclado
        this.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);
        btnGCA.requestFocus();
    }

    public void login() {
        if (!validate()) {
            onLoginFailed();
            return;
        }
        btningresar.setEnabled(false);

        final ProgressDialog progressDialog = new ProgressDialog(LoginActivity.this, ProgressDialog.STYLE_SPINNER);
        progressDialog.setIndeterminate(true);
        progressDialog.setMessage("  Validando autenticación, porfavor espere un momento ...");
        progressDialog.show();

        new android.os.Handler().postDelayed(new Runnable() {
            public void run() {
                onLoginSuccess();                   // On complete call either onLoginSuccess or onLoginFailed
                progressDialog.dismiss();           // onLoginFailed();
            }
        }, 1000);
    }

//    @Override
//    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
//        if (requestCode == REQUEST_SIGNUP) {
//            if (resultCode == RESULT_OK) {
//                // TODO: Implement successful signup logic here
//                // By default we just finish the Activity and log them in automatically
//            }
//        }
//    }

    @Override
    public void onBackPressed() {
        // Disable going back to the MainActivity
        moveTaskToBack(true);
    }

    public void onLoginSuccess() {
        btningresar.setEnabled(true);
        Intent intent = new Intent(this, HomeActivity.class);
        startActivity(intent);
        overridePendingTransition(R.anim.ani_activitypull_in_right, R.anim.ani_activitypush_out_left);
        this.finish();
        //finish();
    }

    public void onLoginFailed() {
        Toast.makeText(getBaseContext(), "Login Fallido", Toast.LENGTH_LONG).show();
        btningresar.setEnabled(true);
    }

    public boolean validate() {
        boolean valid = true;

        String user = txtUsuario.getText().toString();
        String password = txtContrasena.getText().toString();

        if (user.isEmpty()) {
            txtUsuario.setError("Ingrese un usuario Valido");
            txtUsuario.requestFocus();
            return false;
        } else if (password.isEmpty() || password.length() < 8 || password.length() > 20) {
            txtContrasena.setError("Ingrese una contraseña valida");
            txtContrasena.requestFocus();
            return false;
        } else if(user.equals("123456")){
            if(password.equals("technologies")) {
                txtContrasena.setError(null);
                return true;
            }else{
                txtContrasena.setError("Ingrese una contraseña valida");
                txtContrasena.requestFocus();
                return false;
            }
        }else{
            txtUsuario.setError("Ingrese un usuario Valido");
            txtUsuario.requestFocus();
            return false;
        }
    }

//    @Override
//    public void onClick(View v) {
//        switch (v.getId())
//        {
//            case R.id.ingresar:
//                String usuario = "" + txtUsuario.getText();
//                String contrasena = "" + txtContrasena.getText();
//                if(!usuario.equals("")){
//                    if(!contrasena.equals("")){
//                        if(usuario.equals("0") && contrasena.equals("0"))
//                        {
//                            Intent home = new Intent(getApplicationContext(), HomeActivity.class);
//                            startActivity(home);
//                        } else {
//                            Toast.makeText(getApplicationContext(), "Usuario o Contraseña incorrecta, Intentelo nuevamente", Toast.LENGTH_SHORT).show();
//                        }
//                    }else Toast.makeText(this, "Contraseña vacio, Intentelo nuevamente", Toast.LENGTH_SHORT).show();
//                }else Toast.makeText(this, "Usuario vacio, Intentelo nuevamente", Toast.LENGTH_SHORT).show();
//                break;
//        }
//    }
}
