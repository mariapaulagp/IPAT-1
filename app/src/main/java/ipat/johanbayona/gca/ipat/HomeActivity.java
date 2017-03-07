package ipat.johanbayona.gca.ipat;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.LinearLayout;

public class HomeActivity extends Activity implements View.OnClickListener {
    Button btnCloseSesion, btnUptdateRange, btnUpdateTable, btnOpenIpat, btnNewIpat;
    Animation animación, animación1, animación2, animación3, animación4, animación5;
    LinearLayout contentInfo, contentMenu;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        btnNewIpat = (Button) findViewById(R.id.btnNewIpat);
        btnNewIpat.setOnClickListener(this);
        btnOpenIpat = (Button) findViewById(R.id.btnOpenIpat);
        btnOpenIpat.setOnClickListener(this);
        btnUpdateTable = (Button) findViewById(R.id.btnUpdateTable);
        btnUpdateTable.setOnClickListener(this);
        btnUptdateRange = (Button) findViewById(R.id.btnUptdateRange);
        btnUptdateRange.setOnClickListener(this);
        btnCloseSesion = (Button) findViewById(R.id.btnCloseSesion);
        btnCloseSesion.setOnClickListener(this);

        contentInfo = (LinearLayout) findViewById(R.id.contentInfo);


        // Set portrait orientation
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
        // Que no saque el teclado
        this.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);

        animación = AnimationUtils.loadAnimation (HomeActivity.this, R.anim.ani_home_todown);
        animación.setStartOffset(400);
        animación1 = AnimationUtils.loadAnimation (HomeActivity.this, R.anim.ani_home_toup);
        animación1.setStartOffset(200);
        animación2 = AnimationUtils.loadAnimation (HomeActivity.this, R.anim.ani_home_toup);
        animación2.setStartOffset(250);
        animación3 = AnimationUtils.loadAnimation (HomeActivity.this, R.anim.ani_home_toup);
        animación3.setStartOffset(300);
        animación4 = AnimationUtils.loadAnimation (HomeActivity.this, R.anim.ani_home_toup);
        animación4.setStartOffset(350);
        animación5 = AnimationUtils.loadAnimation (HomeActivity.this, R.anim.ani_home_toup);
        animación5.setStartOffset(400);


        contentInfo.startAnimation(animación);
        btnNewIpat.startAnimation(animación1);
        btnOpenIpat.startAnimation(animación2);
        btnUpdateTable.startAnimation(animación3);
        btnUptdateRange.startAnimation(animación4);
        btnCloseSesion.startAnimation(animación5);
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btnNewIpat:
                Intent Activity_1 = new Intent(this, NewIpatActivity.class);
                //i.putExtra("direccion", et1.getText().toString());
                startActivity(Activity_1);
                overridePendingTransition(R.anim.ani_activitypull_in_right, R.anim.ani_activitypush_out_left);
                break;
            case R.id.btnOpenIpat:
                break;
            case R.id.btnUpdateTable:
                break;
            case R.id.btnUptdateRange:
                break;
            case R.id.btnCloseSesion:
                alertCloseHome();
                break;
        }
    }


    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == event.KEYCODE_BACK) {
            alertCloseHome();
        }
        return super.onKeyDown(keyCode, event);
    }

    private void alertCloseHome()
    {
        AlertDialog deleteAlert = new AlertDialog.Builder(this).create();
        deleteAlert.setTitle("Salir");
        deleteAlert.setMessage("Desea cerrar la seción?");
        //deleteAlert.setIcon(R.drawable.delete);
        deleteAlert.setButton("Ok", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Intent i = new Intent(HomeActivity.this, LoginActivity.class);
                i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(i);
                overridePendingTransition(R.anim.ani_activitypull_in_left, R.anim.ani_activitypush_out_right);
            }
        });
        deleteAlert.setButton2("Cancelar", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });
        deleteAlert.show();
    }
}
