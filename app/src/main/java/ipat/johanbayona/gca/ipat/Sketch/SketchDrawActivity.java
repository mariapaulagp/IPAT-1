package ipat.johanbayona.gca.ipat.Sketch;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.PointF;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.ScaleGestureDetector;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

import ipat.johanbayona.gca.ipat.Adapters.SketchMenuSideAdapter;
import ipat.johanbayona.gca.ipat.Compass.CompassActivity;
import ipat.johanbayona.gca.ipat.DataIpat;
import ipat.johanbayona.gca.ipat.DataModels.Evidence;
import ipat.johanbayona.gca.ipat.DataModels.MeasureEvi;
import ipat.johanbayona.gca.ipat.DataModels.ObjetPosition;
import ipat.johanbayona.gca.ipat.File.FileStorage;
import ipat.johanbayona.gca.ipat.Gesture.MoveGestureDetector;
import ipat.johanbayona.gca.ipat.Gesture.RotateGestureDetector;
import ipat.johanbayona.gca.ipat.MyConvert;
import ipat.johanbayona.gca.ipat.R;

public class SketchDrawActivity extends Fragment implements View.OnTouchListener, MeasureTool.OnHeadlineSelectedListener {
    public ArrayList<Integer> prgmImages = new ArrayList<Integer>();
    public ArrayList<String> prgmNameList = new ArrayList<String>();
    View view;
    Toast toast;
    TextView txtIndMenuCapa;
    boolean stateVisualCoordinate = true;
    int lastload = -1, lastselected = -1;
    //region - Var Touch y manipulation                                                             // Variables para mantener informacion:
    float limitZoomIn = 4f;                                                                         // Limite de acercar el selectedbackground en 4,0
    float limitZoomOut = 1f;                                                                        // Limite de alejar el selectedbackground en 1,0
    ArrayList<ObjetPosition> Undolist = new ArrayList<>();
    ArrayList<ObjetPosition> Redolist = new ArrayList<>();
    CompassActivity compassFragment;
    //private ArrayList<MeasureTag> arrMeasureTag = new ArrayList<MeasureTag>();                           // Arreglo para los objetos de la Capa de Coordenadas
    //region - Touch y manipulaciones
    PointF startP = new PointF(0, 0);
    PointF endP = new PointF(0, 0);
    Boolean bandera;
    int margenSelection = 20;
    //endregion var capas
    //region - var Capas y Objetos
    private RelativeLayout pnl_SketchDraw, pnl_ContentCapas, pnl_CapaVias, pnl_CapaSenales,         // Paneles de Capas
            pnl_CapaObjetos, pnl_CapaVehiculos, pnl_CapaDibujo, pnl_CapaCoordenadas,
            pnl_ViasTagMeasure, pnl_SenalesTagMeasure, pnl_ObjectTagMeasure, pnl_VehicleTagMeasure;
    private MeasureTool pnl_CapaMedidas;
    private ArrayList<ImageView> arrCapaVias = new ArrayList<ImageView>();                                  // Arreglo para los objetos de la Capa de Vias
    private ArrayList<ImageView> arrCapaSenales = new ArrayList<ImageView>();                               // Arreglo para los objetos de la Capa de Señales
    private ArrayList<ImageView> arrCapaObjetos = new ArrayList<ImageView>();                               // Arreglo para los objetos de la Capa de Objetos
    private ArrayList<ImageView> arrCapaVehiculos = new ArrayList<ImageView>();                             // Arreglo para los objetos de la Capa de Vehiculos
    //endregion  fin xml
    private ArrayList<ImageView> arrCapaDibujos = new ArrayList<ImageView>();                               // Arreglo para los objetos de la Capa de Dibujo
    private ArrayList<ImageView> arrCapaCoordenadas = new ArrayList<ImageView>();                           // Arreglo para los objetos de la Capa de Coordenadas
    private int capaSelected = -1;
    private boolean flagStartSelection = false;                                                     // Bandera para detectar si na manipulacion corresponde a seleccion de objeto o accion sobre todo el plano
    private ImageView imgViewSelected = null;                                                               // Valor de ImageView para la imagen actualmente seleccionada
    private int imgIndexSelected = -1;                                                              // Valor del index de la iamgen seleccionada sobre el arreglo de la capa correspondiente
    //region - var xaml para el manejo del diseño
    private RelativeLayout btnVias, btnSenales, btnObjetos, btnVehículos, btnDibujo;
    private ImageView btnMessure, btnUndo, btnRedo, btnOkSketch, btnNorte;                                    // Botones del menu de la pantalla de edicion
    private ImageView btnEditDelete, btnEditLock, btnOkEdit, btnEditRotLeft, btnEditRotRigth,
            btnEditLeft, btnEditUp, btnEditDown,
            btnEditRigth, btnEditLessZoom, btnEditMoreZoom, btnEditFlipHor,
            btnEditFlipVert, btnEditUpCapa, btnEditDownCapa,
            btnDeleteMausure;
    private ImageView btnPrevReutrn, btnPrevExitSketch, btnCaompassOk, btnCompassReutrn;
    //endregion
    private LinearLayout fondoSelectedVias, fondoSelectedSenales, fondoSelectedObjetos, fondoSelectedVehiculos, fondoSelectedDibujos;                                                             // Panel que contiene la img selectedbackground del menu de capas
    private RelativeLayout pnlThird, pnlSecond, pnlSecondUp, pnlSecondDown, pnlFirst, pnlFirstUp, pnlFirstDown, pnlCompass, pnlCompassDown;
    //region - Var Menu Lateral
    private DrawerLayout menuLateral;
    private GridView secondList, thirdList;
    //endregion
    private String[] thirdStringList;
    private RelativeLayout pnlMenuSecond, pnlMenuThird;
    private TextView txtSecondTitleList, txtThirdTitleList, textSelected;
    private ImageView imgMenuRow;
    private ArrayList<ImageView> menuImage = new ArrayList<ImageView>();                                    // Arrego de imagenes del menu
    //region - Var Animation
    private Animation ani_LateralMenu_In, ani_LateralMenuArrow_In, ani_LateralMenuArrow_Out, ani_CapaTitle, ani_CapaBlueRoundSelected;
    private Animation ani_CapaMenu_Selected, ani_CapaMenu_Deselected;
    private Animation ani_In_PanelUp, ani_In_PanelDown, ani_Out_PanelUp, ani_Out_PanelDown;
    private Animation ani_BtnSelected, ani_IndcMenuLateral;
    private float mScaleStart = -1f;
    private float mScaleAll = 1.0f;

    //
    //
    //// Gabriel 26-01-17 ////////
    private float mScaleFactor = 1.0f;                                                                // Valor Escala
    private float mRotationDegrees = 0.f;                                                           // Valor Rotacion

    //////////////////////////
    // Objeto de la clase desplazamiento
    //endregion var touch
    private float mFocusX = 0.f;                                                                    // Valor Desplazamiento en X
    private float mFocusY = 0.f;                                                                    // Desplazamiento en Y
    // Objetos de las clases que administran la informacion de:
    private ScaleGestureDetector mScaleDetector;                                                    // Objeto de la clase Scala
    private RotateGestureDetector mRotateDetector;                                                  // Objeto de la clase Rotacion
    private MoveGestureDetector mMoveDetector;

    public static SketchDrawActivity newInstance(String text) {
        SketchDrawActivity f = new SketchDrawActivity();
        Bundle b = new Bundle();
        b.putString("msg", text);

        f.setArguments(b);
        return f;
    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.activity_sketchdraw, container, false);
        view.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
//                if (keyCode == event.KEYCODE_BACK) {
//                    if(selectedDoubleTap) {
//                        selectedDoubleTap = false;
//                        deselctedObject();
//                        arrViewportVias.get(imgSelected).setBackgroundResource(0);
//                    }
//                    else
                exitSketch();
//                }
                return false;
            }
        });

        FragmentManager FM = getFragmentManager();
        FragmentTransaction FT = FM.beginTransaction();
        compassFragment = new CompassActivity();

        FT.add(R.id.PnlCompassDown, compassFragment);
        FT.commit();

        //region - Menu principal
        textSelected = (TextView) view.findViewById(R.id.TextSelected);
        txtIndMenuCapa = (TextView) view.findViewById(R.id.TxtIndMenuCapa);

        fondoSelectedVias = (LinearLayout) view.findViewById(R.id.FondoselectedVias);
        fondoSelectedSenales = (LinearLayout) view.findViewById(R.id.FondoselectedSeñales);
        fondoSelectedObjetos = (LinearLayout) view.findViewById(R.id.FondoselectedObjetos);
        fondoSelectedVehiculos = (LinearLayout) view.findViewById(R.id.FondoselectedVehiculos);
        fondoSelectedDibujos = (LinearLayout) view.findViewById(R.id.FondoselectedDibujos);
        btnVias = (RelativeLayout) view.findViewById(R.id.BtnVias);
        btnVias.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                SelectedFirstMenuDown(0);
            }
        });
        btnSenales = (RelativeLayout) view.findViewById(R.id.BtnSenales);
        btnSenales.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                SelectedFirstMenuDown(1);
            }
        });
        btnObjetos = (RelativeLayout) view.findViewById(R.id.BtnObjetos);
        btnObjetos.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                SelectedFirstMenuDown(2);
            }
        });
        btnVehículos = (RelativeLayout) view.findViewById(R.id.BtnVehiculos);
        btnVehículos.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                SelectedFirstMenuDown(3);
            }
        });
        btnDibujo = (RelativeLayout) view.findViewById(R.id.BtnDibujos);
        btnDibujo.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                SelectedFirstMenuDown(4);
            }
        });

        btnDeleteMausure = (ImageView) view.findViewById(R.id.BtnDeleteMausure);
        btnDeleteMausure.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                pnl_CapaMedidas.deleteMeasure();
            }
        });

        btnMessure = (ImageView) view.findViewById(R.id.BtnMessure);
        btnMessure.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {

                SelectedFirstMenuUp(0);
            }
        });
        btnUndo = (ImageView) view.findViewById(R.id.BtnUndo);
        btnUndo.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {

                SelectedFirstMenuUp(1);

                //Gabriel//
                Retroceder();
            }
        });
        btnRedo = (ImageView) view.findViewById(R.id.BtnRedo);
        btnRedo.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {

                SelectedFirstMenuUp(2);
                Adelantar();
            }
        });
        btnNorte = (ImageView) view.findViewById(R.id.BtnNorte);
        btnNorte.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                if (!bandera) {
                    btnNorte.startAnimation(ani_BtnSelected);
                    openCompass();
                }
                //SelectedFirstMenuUp(3);
            }
        });
        btnOkSketch = (ImageView) view.findViewById(R.id.BtnOkSketch);
        btnOkSketch.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                SelectedFirstMenuUp(4);
            }
        });

        pnlThird = (RelativeLayout) view.findViewById(R.id.PnlThird);
        pnlSecond = (RelativeLayout) view.findViewById(R.id.PnlSecond);
        pnlSecondUp = (RelativeLayout) view.findViewById(R.id.PnlSecondUp);
        pnlSecondDown = (RelativeLayout) view.findViewById(R.id.PnlSecondDown);
        pnlFirst = (RelativeLayout) view.findViewById(R.id.PnlFirst);
        pnlFirstUp = (RelativeLayout) view.findViewById(R.id.PnlFirstUp);
        pnlFirstDown = (RelativeLayout) view.findViewById(R.id.PnlFirstDown);
        pnlCompass = (RelativeLayout) view.findViewById(R.id.PnlCompass);
        pnlCompassDown = (RelativeLayout) view.findViewById(R.id.PnlCompassDown);
        //endregion fin menu proncipal

        btnPrevReutrn = (ImageView) view.findViewById(R.id.BtnPevReutrn);
        btnPrevReutrn.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                SelectedThirdMenuUp(0);
            }
        });
        btnPrevExitSketch = (ImageView) view.findViewById(R.id.BtnPrevExit);
        btnPrevExitSketch.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                SelectedThirdMenuUp(1);
            }
        });

        btnCompassReutrn = (ImageView) view.findViewById(R.id.BtnCompassReutrn);
        btnCompassReutrn.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                SelectedCompassMenu(0);
            }
        });
        btnCaompassOk = (ImageView) view.findViewById(R.id.BtnCaompassOk);
        btnCaompassOk.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                SelectedCompassMenu(1);
            }
        });

        //region - Menu secundario edit
        btnEditDelete = (ImageView) view.findViewById(R.id.BtnEditDelete);
        btnEditDelete.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                SelectedSecondMenuUp(0);
            }
        });
        btnEditLock = (ImageView) view.findViewById(R.id.BtnEditLock);
        btnEditLock.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                SelectedSecondMenuUp(1);
            }
        });
        btnOkEdit = (ImageView) view.findViewById(R.id.BtnOkEdit);
        btnOkEdit.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                SelectedSecondMenuUp(2);
            }
        });

        btnEditRotLeft = (ImageView) view.findViewById(R.id.BtnEditRotLeft);
        btnEditRotLeft.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                SelectedSecondMenuDown(0);
            }
        });
        btnEditRotRigth = (ImageView) view.findViewById(R.id.BtnEditRotRigth);
        btnEditRotRigth.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                SelectedSecondMenuDown(1);
            }
        });
        btnEditLeft = (ImageView) view.findViewById(R.id.BtnEditLeft);
        btnEditLeft.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                SelectedSecondMenuDown(2);
            }
        });
        btnEditUp = (ImageView) view.findViewById(R.id.BtnEditUp);
        btnEditUp.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                SelectedSecondMenuDown(3);
            }
        });
        btnEditDown = (ImageView) view.findViewById(R.id.BtnEditDown);
        btnEditDown.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                SelectedSecondMenuDown(4);
            }
        });
        btnEditRigth = (ImageView) view.findViewById(R.id.BtnEditRigth);
        btnEditRigth.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                SelectedSecondMenuDown(5);
            }
        });
        btnEditLessZoom = (ImageView) view.findViewById(R.id.BtnEditLessZoom);
        btnEditLessZoom.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                SelectedSecondMenuDown(6);
            }
        });
        btnEditMoreZoom = (ImageView) view.findViewById(R.id.BtnEditMoreZoom);
        btnEditMoreZoom.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                SelectedSecondMenuDown(7);
            }
        });
        btnEditFlipHor = (ImageView) view.findViewById(R.id.BtnEditFlipHor);
        btnEditFlipHor.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                SelectedSecondMenuDown(8);
            }
        });
        btnEditFlipVert = (ImageView) view.findViewById(R.id.BtnEditFlipVert);
        btnEditFlipVert.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                SelectedSecondMenuDown(9);
            }
        });
        btnEditUpCapa = (ImageView) view.findViewById(R.id.BtnEditUpCapa);
        btnEditUpCapa.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                SelectedSecondMenuDown(10);
            }
        });
        btnEditDownCapa = (ImageView) view.findViewById(R.id.BtnEditDownCapa);
        btnEditDownCapa.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                SelectedSecondMenuDown(11);
            }
        });
        //endregion

        //region - Menu Lateral
        txtSecondTitleList = (TextView) view.findViewById(R.id.TxtSecondTitleList);
        txtThirdTitleList = (TextView) view.findViewById(R.id.TxtThirdTitleList);

        imgMenuRow = (ImageView) view.findViewById(R.id.ImgMenuRow);

        pnlMenuSecond = (RelativeLayout) view.findViewById(R.id.PnlMenu_Second);
        pnlMenuThird = (RelativeLayout) view.findViewById(R.id.PnlMenu_Third);
        //pnlMenuSecond.setVisibility(View.GONE);
        pnlMenuThird.setVisibility(View.GONE);
        imgMenuRow.setVisibility(View.GONE);

        //firstList = (GridView) findViewById(R.id.first_list);
        secondList = (GridView) view.findViewById(R.id.Second_list);
        thirdList = (GridView) view.findViewById(R.id.Third_list);
        //thirdList.setChoiceMode(AbsListView.CHOICE_MODE_MULTIPLE);

        menuLateral = (DrawerLayout) view.findViewById(R.id.Menu_Content);
        menuLateral.addDrawerListener(new DrawerLayout.DrawerListener() {
            @Override
            public void onDrawerStateChanged(int arg0) {                                                    // cuando inicia el evento
                // TODO Auto-generated method stub
                if (pnlFirst.getVisibility() == View.VISIBLE
                        && pnlSecond.getVisibility() == View.GONE
                        && pnlThird.getVisibility() == View.GONE
                        && pnlCompass.getVisibility() == View.GONE) {
                    if (capaSelected == -1) {
                        textSelected.startAnimation(ani_CapaTitle);
                        textSelected.startAnimation(ani_CapaTitle);
                        menuLateral.closeDrawers();
                    } else if (lastload != capaSelected) loadMennu();
                    clearbackground2();
                } else menuLateral.closeDrawers();
            }

            @Override
            public void onDrawerSlide(View arg0, float arg1) {
                // TODO Auto-generated method stub
            }

            @Override
            public void onDrawerOpened(View arg0) {
                //clearSelectedmark();
            }

            @Override
            public void onDrawerClosed(View arg0) {
                pnlMenuThird.setVisibility(View.GONE);
                imgMenuRow.setVisibility(View.GONE);
            }
        });
        //endregion Inicializacion

        //region - Animation
        ani_LateralMenu_In = AnimationUtils.loadAnimation(view.getContext(), R.anim.ani_lateralmenu_in);                // creo y cargo las animaciones para menu 3
        ani_LateralMenuArrow_In = AnimationUtils.loadAnimation(view.getContext(), R.anim.ani_lateralmenu_arrow_in);               // creo y cargo las animaciones para menu 3
        ani_LateralMenuArrow_Out = AnimationUtils.loadAnimation(view.getContext(), R.anim.ani_lateralmenu_arrow_out);
        ani_CapaTitle = AnimationUtils.loadAnimation(view.getContext(), R.anim.ani_capaselectedtitle);
        ani_CapaBlueRoundSelected = AnimationUtils.loadAnimation(view.getContext(), R.anim.ani_capablueselected);
        ani_CapaMenu_Selected = AnimationUtils.loadAnimation(view.getContext(), R.anim.ani_capaselectedbtn);
        ani_CapaMenu_Deselected = AnimationUtils.loadAnimation(view.getContext(), R.anim.ani_capadeselectedbtn);
        ani_In_PanelUp = AnimationUtils.loadAnimation(view.getContext(), R.anim.ani_menu_toup_in);
        ani_In_PanelDown = AnimationUtils.loadAnimation(view.getContext(), R.anim.ani_menu_todown_in);
        ani_Out_PanelUp = AnimationUtils.loadAnimation(view.getContext(), R.anim.ani_menu_toup_out);
        ani_Out_PanelDown = AnimationUtils.loadAnimation(view.getContext(), R.anim.ani_menu_todown_out);

        ani_BtnSelected = AnimationUtils.loadAnimation(view.getContext(), R.anim.ani_btnselected);

        ani_IndcMenuLateral = AnimationUtils.loadAnimation(view.getContext(), R.anim.ani_indicmenulateral);
        //endregion animation

        //region - Inicialization Capas, Touch y manipulation
        pnl_SketchDraw = (RelativeLayout) view.findViewById(R.id.Pnl_SketchDraw);                        // Paneles de Capas
        pnl_ContentCapas = (RelativeLayout) view.findViewById(R.id.Pnl_ContentCapas);
        pnl_CapaVias = (RelativeLayout) view.findViewById(R.id.Pnl_CapaVias);
        pnl_CapaSenales = (RelativeLayout) view.findViewById(R.id.Pnl_CapaSenales);
        pnl_CapaObjetos = (RelativeLayout) view.findViewById(R.id.Pnl_CapaObjetos);
        pnl_CapaVehiculos = (RelativeLayout) view.findViewById(R.id.Pnl_CapaVehiculos);
        pnl_CapaDibujo = (RelativeLayout) view.findViewById(R.id.Pnl_CapaDibujo);
        pnl_CapaCoordenadas = (RelativeLayout) view.findViewById(R.id.Pnl_CapaCoordenadas);

        pnl_ViasTagMeasure = (RelativeLayout) view.findViewById(R.id.Pnl_ViasTagMeasure);
        pnl_SenalesTagMeasure = (RelativeLayout) view.findViewById(R.id.Pnl_SenalesTagMeasure);
        pnl_ObjectTagMeasure = (RelativeLayout) view.findViewById(R.id.Pnl_ObjectTagMeasure);
        pnl_VehicleTagMeasure = (RelativeLayout) view.findViewById(R.id.Pnl_VehicleTagMeasure);

        pnl_CapaMedidas = (MeasureTool) view.findViewById(R.id.Pnl_CapaMedidas);
        pnl_CapaMedidas.setCallback(this);
        pnl_SketchDraw.setOnTouchListener(this);                                                    // Subscribo el panel del principal del selectedbackground que contiene todas las capa para registrar la manipulacion
        // Inicializo los Objetos de
        mScaleDetector = new ScaleGestureDetector(getContext(), new ScaleListener());                       // Objeto Classe Scala
        mRotateDetector = new RotateGestureDetector(getContext(), new RotateListener());                    // Objeto Classe Rotacion
        mMoveDetector = new MoveGestureDetector(getContext(), new MoveListener());                          // Objeto Classe Desplazamiento
        //endregion

        btnDeleteMausure.setVisibility(View.GONE);
        view.setLayerType(View.LAYER_TYPE_HARDWARE, null);
        return view;

    }

    @Override
    public boolean onTouch(View motionView, MotionEvent motionEvent) {
        bandera = true;
        try {
            mScaleDetector.onTouchEvent(motionEvent);                                                   // Se envia el evento para calculor la Scala
            mRotateDetector.onTouchEvent(motionEvent);                                                  // Se envia el evento para calculor la Rotacion
            mMoveDetector.onTouchEvent(motionEvent);                                                    // Se envia el evento para calculor el desplazamiento en X & Y

            switch (motionEvent.getActionMasked()) {                                                    // Obtenemos del Motion Event el tipo de evento
                case MotionEvent.ACTION_DOWN:                                                           // Para cuando genera el primer contacto con la pantalla
                    startP.x = motionEvent.getX();                                              // actualizo las variables con las coordenadas de inicio
                    startP.y = motionEvent.getY();
                    if (motionView instanceof ImageView) {                                              // Valido si el dedo esta sobre un objeto de imagen, si o es porque esta sobre el lienzo del selectedbackground
                        flagStartSelection = true;                                                      // Bandera de inicio de manipulacion para en el Up determinar si se quiere seleccionar un objeto o generar un movimiento del selectedbackground
                        imgViewSelected = (ImageView) motionView;                                       // Capturo la imagen sobre la que se esta generando la accion
                        editOnTouch4Capa(false);                                                        // Le quito el evento de manipulacion a todas las imagenes de la capa actual
                        pnl_SketchDraw.dispatchTouchEvent(motionEvent);                                 // Obligo a generar el mimo evento que se genero en la img ahora sobre el selectedbackground
                        return false;                                                                   //  Me salgo pra permitir reiniciar obteniendo la informacion dl evento ahora desde el selectedbackground
                    }
                    break;
                case MotionEvent.ACTION_MOVE:                                                           // Para desplazamiento en X & Y
                    endP.x = motionEvent.getX();                                              // actualizo las variables con las coordenadas de inicio
                    endP.y = motionEvent.getY();
                    if (isLineDraw(new PointF(startP.x, startP.y), new PointF(endP.x, endP.y))) {
                        flagStartSelection = false;                                                         // limpio la bandera para indicar que no es seleccion si no movimiento
                        if (imgIndexSelected > -1 && pnlSecond.getVisibility() == View.VISIBLE) {          // Valido si el movimiento se esta generando dentro de edicion de objeto
                            moveObjectSelected();                                                           // si es asi aplico accion sobre el objeto actualmente seleccionado
                        } else if (imgIndexSelected < 0 && (pnlFirst.getVisibility() == View.VISIBLE
                                || pnlThird.getVisibility() == View.VISIBLE)) {                             // Valido si el movimiento se esta generando dentro de la pantalla principal
                            moveAll();                                                                      // Si es asi aplico accion sobre el selectedbackground
                        }
                    }
                    break;
                case MotionEvent.ACTION_UP:                                                             // Para cuando se quita el ultimo dedo de la pantalla
                    if (flagStartSelection) {                                                            // Valido si la bandera sigue en true la accion fue de seleccion
                        flagStartSelection = false;                                                     // Limpio la bandera
                        imgIndexSelected = getIndexOfImageSelected();                                   // obtengo el index de la imagen seleccionada
                        imgViewSelected.setBackgroundResource(R.drawable.selectedbackground);           // Marco con el borde la imagen seleccionada
                        selctedObject();                                                               // Acomodo el entorno para la edicion, ocultando el panel principal y mostrando el panel de edicion
                    } else {                                                                            // Cuando la accion es de manupulacion de todo o el selectedbackground
                        if (imgIndexSelected < 0 && pnlFirst.getVisibility() == View.VISIBLE) {     // Valido si el movimiento se esta generando dentro de la pantalla principal
                            imgViewSelected = null;                                                     // Limpio la variable de imagenManipulada
                            editOnTouch4Capa(true);                                                     // Subscribo las imagenes de la capa actual al onTouchListener(this)
                        }
                    }
                    break;
                case (MotionEvent.ACTION_CANCEL):
                    return true;
                case (MotionEvent.ACTION_OUTSIDE):
                    return true;
                default:
                    return view.onTouchEvent(motionEvent);
            }

            mScaleFactor = 1;                                                                           // Refresco las variables
            mRotationDegrees = 0.f;
            mFocusX = 0.f;
            mFocusY = 0.f;
        } catch (IllegalArgumentException ex) {
            Toast.makeText(getContext(), "Error en el app, controlado.", Toast.LENGTH_LONG).show();
            return false;
        }

        return true;
    }

    private boolean isLineDraw(PointF startPoint, PointF EndPoint) {                 // Metodo retorna true si la intencion es pintar y false si la intencion es seleccionar
        if ((Math.sqrt(Math.pow((EndPoint.x - startPoint.x), 2) + Math.pow((EndPoint.y - startPoint.y), 2))) > margenSelection) // valido la distancia entre el punto oinicial y final y si es mayor al margen digo que la intencion es pintar
            return true;    // si la intencion es pintar retorno true
        return false;       // si la intencion es seleccionar retorno false
    }

    private void moveAll()                                                                          // Aplica manipulacion al contenedor de las capas para manipularlo como un todo (Solo escala y desplazamiento)
    {
        try {
            int ancho = pnl_ContentCapas.getMeasuredWidth() / 2;
            int alto = pnl_ContentCapas.getMeasuredHeight() / 2;
            if ((pnl_ContentCapas.getTranslationX() + mFocusX) <= ((mScaleAll * ancho) - ancho)) {                   // Valido que x no se pase del lado Izquirdo
                if ((pnl_ContentCapas.getTranslationX() + mFocusX) >= -((mScaleAll * ancho) - ancho)) {              // Valido que x no se pase del lado derecho
                    pnl_ContentCapas.setTranslationX(pnl_ContentCapas.getTranslationX() + mFocusX);                // Actualiza desplazamiento en X registrado en el Contenedo
                } else
                    pnl_ContentCapas.setTranslationX(-((mScaleAll * ancho) - ancho));                  // si se paso lo mantengo en el limite
            } else
                pnl_ContentCapas.setTranslationX((mScaleAll * ancho) - ancho);                         // si se paso lo mantengo en el limite
            if ((pnl_ContentCapas.getTranslationY() + mFocusY) <= ((mScaleAll * alto) - alto)) {                    // Valido que y no se pase de arriba
                if ((pnl_ContentCapas.getTranslationY() + mFocusY) >= -((mScaleAll * alto) - alto)) {                // Valido que y no se pase de abajo
                    pnl_ContentCapas.setTranslationY(pnl_ContentCapas.getTranslationY() + mFocusY);                // Actualiza desplazamiento en Y registrado en el Contenedor
                } else
                    pnl_ContentCapas.setTranslationY(-((mScaleAll * alto) - alto));                    // si se paso lo mantengo en el limite
            } else
                pnl_ContentCapas.setTranslationY((mScaleAll * alto) - alto);                          // si se paso lo mantengo en el limite
            ScaleAllLast();
        } catch (Exception ex) {
        }
    }

    private void ScaleAllLast() {
        if ((pnl_ContentCapas.getScaleX() * mScaleFactor) >= limitZoomOut) {                          // valido si Escala es mayor que 1
            if ((pnl_ContentCapas.getScaleX() * mScaleFactor) <= limitZoomIn) {                       // a ala vez es menor que 6
                pnl_ContentCapas.setScaleX(pnl_ContentCapas.getScaleX() * mScaleFactor);            // Actualiza Escala en X registrado en el Contenedor
                pnl_ContentCapas.setScaleY(pnl_ContentCapas.getScaleY() * mScaleFactor);            // Actualiza Escala en Y registrado en el Contenedor
                mScaleAll = pnl_ContentCapas.getScaleX();                                          // Actualizo la variable de escala
            } else {                                                                                 // Si es mayor que 6
                pnl_ContentCapas.setScaleX(limitZoomIn);                                            // la mantengo en 6
                pnl_ContentCapas.setScaleY(limitZoomIn);
                mScaleAll = pnl_ContentCapas.getScaleX();                                           // Actualizo la variable de escala
            }
        } else {                                                                                     // si es menor que 1
            pnl_ContentCapas.setScaleX(limitZoomOut);                                               // la mantengo en 1
            pnl_ContentCapas.setScaleY(limitZoomOut);
            mScaleAll = pnl_ContentCapas.getScaleX();                                               // Actualizo la variable de escala
        }
    }

    private void ScaleAllNew() {
        if ((pnl_ContentCapas.getScaleX() * mScaleFactor) >= limitZoomOut) {
            if ((pnl_ContentCapas.getScaleX() * mScaleFactor) <= limitZoomIn) {
                for (ImageView img : arrCapaVehiculos) {
                    img.setScaleX(img.getScaleX() * mScaleFactor);
                    img.setScaleY(img.getScaleY() * mScaleFactor);
                }
                mScaleAll = pnl_ContentCapas.getScaleX();
            } else {
                for (ImageView img : arrCapaVehiculos) {
                    img.setScaleX(limitZoomIn);
                    img.setScaleY(limitZoomIn);
                }
                mScaleAll = pnl_ContentCapas.getScaleX();
            }
        } else {
            for (ImageView img : arrCapaVehiculos) {
                img.setScaleX(limitZoomOut);
                img.setScaleY(limitZoomOut);
            }
            mScaleAll = pnl_ContentCapas.getScaleX();
        }
    }

    private void moveObjectSelected() {
        try {
            imgViewSelected.setTranslationX(imgViewSelected.getTranslationX() + mFocusX / mScaleAll);     // Actualiza desplazamiento en X registrado en el objeto seleccionado
            imgViewSelected.setTranslationY(imgViewSelected.getTranslationY() + mFocusY / mScaleAll);     // Actualiza desplazamiento en Y registrado en el objeto seleccionado
            imgViewSelected.setRotation(imgViewSelected.getRotation() - mRotationDegrees);              // Actualiza Rotacion registrado en el objeto seleccionado
            //imgViewSelected.setScaleX(imgViewSelected.getScaleX() * mScaleFactor);                      // Actualiza Escala en X registrado en el objeto seleccionado
            //imgViewSelected.setScaleY(imgViewSelected.getScaleY() * mScaleFactor);                      // Actualiza Escala en Y registrado en el objeto seleccionado
        } catch (Exception ex) {
        }
    }

    private int getIndexOfImageSelected()                                                           // Obtengo el index del objeto actualmente seleccionado en base al arreglo de la capa actual
    {
        switch (capaSelected) {                                                                     // Valido capa actual
            case 0:
                return arrCapaVias.indexOf(imgViewSelected);                                    // Obtengo el indel del objeto seleccionado con respecto al arreglo de imagenes
            case 1:
                return arrCapaSenales.indexOf(imgViewSelected);
            case 2:
                return arrCapaObjetos.indexOf(imgViewSelected);
            case 3:
                return arrCapaVehiculos.indexOf(imgViewSelected);
            case 4:
                return arrCapaDibujos.indexOf(imgViewSelected);
        }
        return -1;                                                                                  // Si no existe devuelve -1
    }

    private void previewAndSave() {
        if (pnlThird.getVisibility() == View.GONE) {
            editOnTouch4Capa(false);
            pnl_CapaVias.setAlpha(1f);
            pnl_ViasTagMeasure.setAlpha(1f);
            pnl_CapaSenales.setAlpha(1f);
            pnl_SenalesTagMeasure.setAlpha(1f);
            pnl_CapaObjetos.setAlpha(1f);
            pnl_ObjectTagMeasure.setAlpha(1f);
            pnl_CapaVehiculos.setAlpha(1f);
            pnl_VehicleTagMeasure.setAlpha(1f);
            pnl_CapaDibujo.setAlpha(1f);
            ani_Out_PanelUp.setAnimationListener(new Animation.AnimationListener() {                    // a la ultima animacion le creo evento para saber cuando termino y poner desaparece los botones
                public void onAnimationStart(Animation anim) {
                }


                public void onAnimationRepeat(Animation anim) {
                }

                ;

                public void onAnimationEnd(Animation anim)                                                // evento de terminacion de la animacion
                {
                    pnlFirst.setVisibility(View.GONE);                                   // desaparece el control donde estan todos los botones
                    ani_Out_PanelUp.setAnimationListener(null);
                }

                ;
            });
            pnlFirstUp.startAnimation(ani_Out_PanelUp);
            pnlFirstDown.startAnimation(ani_Out_PanelDown);
            btnNorte.setVisibility(View.GONE);

            ani_In_PanelUp.setStartOffset(100);
            pnlThird.startAnimation(ani_In_PanelUp);
            pnlThird.setVisibility(View.VISIBLE);
        }
    }

    private void returnOfPreview() {
        if (pnlFirst.getVisibility() == View.GONE) {
            editOnTouch4Capa(true);                                                                 // habilito el evento de manipulacion a las imagenes de la capa seleccionada
            pnl_CapaVias.setAlpha(0.3f);
            pnl_ViasTagMeasure.setAlpha(0.3f);
            pnl_CapaSenales.setAlpha(0.3f);
            pnl_SenalesTagMeasure.setAlpha(0.3f);
            pnl_CapaObjetos.setAlpha(0.3f);
            pnl_ObjectTagMeasure.setAlpha(0.3f);
            pnl_CapaVehiculos.setAlpha(0.3f);
            pnl_VehicleTagMeasure.setAlpha(0.3f);
            switch (capaSelected) {                                                                 // opacidad en  1 a la capa selecccionada
                case 0:
                    pnl_CapaVias.setAlpha(1f);
                    pnl_ViasTagMeasure.setAlpha(1f);
                    break;
                case 1:
                    pnl_CapaSenales.setAlpha(1f);
                    pnl_SenalesTagMeasure.setAlpha(1f);
                    break;
                case 2:
                    pnl_CapaObjetos.setAlpha(1f);
                    pnl_ObjectTagMeasure.setAlpha(1f);
                    break;
                case 3:
                    pnl_CapaVehiculos.setAlpha(1f);
                    pnl_VehicleTagMeasure.setAlpha(1f);
                    break;
                case 4:
                    pnl_CapaDibujo.setAlpha(1f);
                    break;
            }
            ani_Out_PanelUp.setAnimationListener(new Animation.AnimationListener() {                    // a la ultima animacion le creo evento para saber cuando termino y poner desaparece los botones
                public void onAnimationStart(Animation anim) {
                }

                ;

                public void onAnimationRepeat(Animation anim) {
                }

                ;

                public void onAnimationEnd(Animation anim)                                                // evento de terminacion de la animacion
                {
                    //pnlThird.setVisibility(View.VISIBLE);
                    pnlThird.setVisibility(View.GONE);                                   // desaparece el control donde estan todos los botones
                    ani_Out_PanelUp.setAnimationListener(null);
                }

                ;
            });
            pnlThird.startAnimation(ani_Out_PanelUp);
            btnNorte.setVisibility(View.VISIBLE);

            ani_In_PanelUp.setStartOffset(100);
            ani_In_PanelDown.setStartOffset(100);
            pnlFirstUp.startAnimation(ani_In_PanelUp);
            pnlFirstDown.startAnimation(ani_In_PanelDown);
            pnlFirst.setVisibility(View.VISIBLE);
        }
    }

    private void openCompass() {
        if (pnlCompass.getVisibility() == View.GONE) {
            editOnTouch4Capa(false);
            if (pnl_CapaCoordenadas.getVisibility() == view.VISIBLE)
                pnl_CapaCoordenadas.setVisibility(View.GONE);

            compassFragment.startCompass();
            //FT.add(R.id.PnlCompassDown, compassFragment);
            //FT.commit();

            //pnlCompassDown.addView(editor);

            ani_Out_PanelUp.setAnimationListener(new Animation.AnimationListener() {                        // a la ultima animacion le creo evento para saber cuando termino y poner desaparece los botones
                public void onAnimationStart(Animation anim) {
                }

                ;

                public void onAnimationRepeat(Animation anim) {
                }

                ;

                public void onAnimationEnd(Animation anim)                                                  // evento de terminacion de la animacion
                {
                    pnlFirst.setVisibility(View.GONE);                                                      // desaparece el control donde estan todos los botones
                    ani_Out_PanelUp.setAnimationListener(null);
                }

                ;
            });
            pnlFirstUp.startAnimation(ani_Out_PanelUp);
            btnNorte.setVisibility(View.GONE);

            ani_In_PanelUp.setStartOffset(100);
            pnlCompass.startAnimation(ani_In_PanelUp);
            pnlCompass.setVisibility(View.VISIBLE);
        }
    }

    private void returnOfCompass() {
        if (pnlFirst.getVisibility() == View.GONE) {
            editOnTouch4Capa(true);
            if (stateVisualCoordinate == true) pnl_CapaCoordenadas.setVisibility(View.VISIBLE);

            compassFragment.stopCompass();
            //FT.remove(compassFragment);

            ani_Out_PanelUp.setAnimationListener(new Animation.AnimationListener() {                    // a la ultima animacion le creo evento para saber cuando termino y poner desaparece los botones
                public void onAnimationStart(Animation anim) {
                }

                ;

                public void onAnimationRepeat(Animation anim) {
                }


                public void onAnimationEnd(Animation anim)                                                // evento de terminacion de la animacion
                {
                    //pnlThird.setVisibility(View.VISIBLE);
                    pnlCompass.setVisibility(View.GONE);                                   // desaparece el control donde estan todos los botones
                    ani_Out_PanelUp.setAnimationListener(null);
                }

                ;
            });
            pnlCompass.startAnimation(ani_Out_PanelUp);
            btnNorte.setVisibility(View.VISIBLE);

            ani_In_PanelUp.setStartOffset(100);
            pnlFirstUp.startAnimation(ani_In_PanelUp);
            pnlFirst.setVisibility(View.VISIBLE);
        }
    }

    private void saveCompass() {
        btnNorte.setRotation(compassFragment.currentDegree);
        returnOfCompass();
    }

    private void selctedObject() {
        if (pnlSecond.getVisibility() == View.GONE) {
            //if(pnl_CapaCoordenadas.getVisibility() == view.VISIBLE) pnl_CapaCoordenadas.setVisibility(View.GONE);

            ani_Out_PanelDown.setAnimationListener(new Animation.AnimationListener() {                    // a la ultima animacion le creo evento para saber cuando termino y poner desaparece los botones
                public void onAnimationStart(Animation anim) {
                }

                ;

                public void onAnimationRepeat(Animation anim) {
                }

                ;

                public void onAnimationEnd(Animation anim)                                                // evento de terminacion de la animacion
                {
                    pnlFirst.setVisibility(View.GONE);                                   // desaparece el control donde estan todos los botones
                    ani_Out_PanelDown.setAnimationListener(null);
                }

                ;
            });
            pnlFirstUp.startAnimation(ani_Out_PanelUp);
            pnlFirstDown.startAnimation(ani_Out_PanelDown);
            btnNorte.setVisibility(View.GONE);

            ani_In_PanelUp.setStartOffset(100);
            ani_In_PanelDown.setStartOffset(100);
            pnlSecondUp.startAnimation(ani_In_PanelUp);
            pnlSecondDown.startAnimation(ani_In_PanelDown);
            pnlSecond.setVisibility(View.VISIBLE);
        }
    }

    private void deselctedObject() {

        if (pnlFirst.getVisibility() == View.GONE) {
            bandera = false;
            if (stateVisualCoordinate == true) pnl_CapaCoordenadas.setVisibility(View.VISIBLE);
            ani_Out_PanelDown.setAnimationListener(new Animation.AnimationListener() {                    // a la ultima animacion le creo evento para saber cuando termino y poner desaparece los botones
                public void onAnimationStart(Animation anim) {
                }

                ;

                public void onAnimationRepeat(Animation anim) {
                }

                ;

                public void onAnimationEnd(Animation anim)                                                // evento de terminacion de la animacion
                {
                    pnlSecond.setVisibility(View.GONE);                                   // desaparece el control donde estan todos los botones
                    ani_Out_PanelDown.setAnimationListener(null);
                }

                ;
            });
            pnlSecondUp.startAnimation(ani_Out_PanelUp);
            pnlSecondDown.startAnimation(ani_Out_PanelDown);
            btnNorte.setVisibility(View.VISIBLE);

            ani_In_PanelUp.setStartOffset(100);
            ani_In_PanelDown.setStartOffset(100);
            pnlFirstUp.startAnimation(ani_In_PanelUp);
            pnlFirstDown.startAnimation(ani_In_PanelDown);
            pnlFirst.setVisibility(View.VISIBLE);
        }
    }

    private void editOnTouch4Capa(boolean action) {                                                 // Accion para quitar o poner evento de gestos a todos los objetos del arreglo de la capa actualmente seleccionada
        try {
            switch (capaSelected) {
                case 0:
                    for (int i = 0; i < arrCapaVias.size(); i++) {                                  // recorro el arreglo de objetos de la capa de Vias
                        if (action)
                            arrCapaVias.get(i).setOnTouchListener(this);                    // true quita el evento de gestos
                        else
                            arrCapaVias.get(i).setOnTouchListener(null);                           // false pone evento de gestos
                    }
                    break;
                case 1:
                    for (int i = 0; i < arrCapaSenales.size(); i++) {                               // recorro el arreglo de objetos de la capa de Señales
                        if (action)
                            arrCapaSenales.get(i).setOnTouchListener(this);                 // true quita el evento de gestos
                        else
                            arrCapaSenales.get(i).setOnTouchListener(null);                        // false pone evento de gestos
                    }
                    break;

                case 2:
                    for (int i = 0; i < arrCapaObjetos.size(); i++) {                               // recorro el arreglo de objetos de la capa de Objetos
                        if (action)
                            arrCapaObjetos.get(i).setOnTouchListener(this);                 // true quita el evento de gestos
                        else
                            arrCapaObjetos.get(i).setOnTouchListener(null);                        // false pone evento de gestos
                    }
                    break;
                case 3:
                    for (int i = 0; i < arrCapaVehiculos.size(); i++) {                             // recorro el arreglo de objetos de la capa de Vehículos
                        if (action)
                            arrCapaVehiculos.get(i).setOnTouchListener(this);               // true quita el evento de gestos
                        else
                            arrCapaVehiculos.get(i).setOnTouchListener(null);                      // false pone evento de gestos
                    }
                    break;
                case 4:
                    for (int i = 0; i < arrCapaDibujos.size(); i++) {                               // recorro el arreglo de objetos de la capa de Dibujos
                        if (action)
                            arrCapaDibujos.get(i).setOnTouchListener(this);                 // true quita el evento de gestos
                        else
                            arrCapaDibujos.get(i).setOnTouchListener(null);                        // false pone evento de gestos
                    }
                    break;
            }
        } catch (Exception ex) {
        }
        return;
    }

    //region - Menu Lateral
    private void loadMennu() {
        if (capaSelected == 0) //VIAS
        {
            lastload = 0;
            secondList.setAdapter(new SketchMenuSideAdapter((Activity) view.getContext(), getResources().getStringArray(R.array.Vías), getResources().obtainTypedArray(R.array.viasIcon), 2));
            txtSecondTitleList.setText("Vías");
            secondList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> arg20, View arg21, int arg22, long arg23) {
                    clearbackground2();
                    arg21.setBackgroundResource(R.drawable.fondoselected);
                    ((TextView) arg21.findViewById(R.id.textoItem)).setTextColor(Color.parseColor("#8fb5e3"));
                    if (arg22 == 0) {
                        final int menuSecondSelected = arg22;
                        txtThirdTitleList.setText("4 - Curvas");
                        thirdList.setAdapter(new SketchMenuSideAdapter((Activity) view.getContext(), getResources().getStringArray(R.array.viaCurva), getResources().obtainTypedArray(R.array.viaCurvaIcon), 3));
                        thirdList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                            @Override
                            public void onItemClick(AdapterView<?> argTrh20, View argTrh21, int arg22, long arg2Trh3) {
                                if (arg22 == 0) {
                                    addNewImage(menuSecondSelected, "1carril", null, null);
                                }
                                if (arg22 == 1) {
                                    addNewImage(menuSecondSelected, "2carriles", null, null);
                                }
                                if (arg22 == 2) {
                                    addNewImage(menuSecondSelected, "3carriles", null, null);
                                }
                                if (arg22 == 3) {
                                    addNewImage(menuSecondSelected, "add1", null, null);
                                }
                                if (arg22 == 4) {
                                    addNewImage(menuSecondSelected, "adds", null, null);
                                }
                                if (arg22 == 5) {
                                    addNewImage(menuSecondSelected, "carril1", null, null);
                                }
                                if (arg22 == 6) {
                                    addNewImage(menuSecondSelected, "carril2", null, null);
                                }
                                if (arg22 == 7) {
                                    addNewImage(menuSecondSelected, "carril3", null, null);
                                }
                                if (arg22 == 8) {
                                    addNewImage(menuSecondSelected, "carril4", null, null);
                                }
                                if (arg22 == 9) {
                                    addNewImage(menuSecondSelected, "carril5", null, null);
                                }
                                if (arg22 == 10) {
                                    addNewImage(menuSecondSelected, "carril6", null, null);
                                }


                            }
                        });
                    }
                    if (arg22 == 1) {
                        final int menuSecondSelected = arg22;
                        txtThirdTitleList.setText("4 - Tramo de Vía");
                        thirdList.setAdapter(new SketchMenuSideAdapter((Activity) view.getContext(), getResources().getStringArray(R.array.tramoVia), getResources().obtainTypedArray(R.array.tramoViaIcon), 3));
                        thirdList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                            @Override
                            public void onItemClick(AdapterView<?> argTrh20, View argTrh21, int arg22, long arg2Trh3) {
                                if (arg22 == 0) {
                                    addNewImage(menuSecondSelected, "1carril", null, null);
                                }
                                if (arg22 == 1) {
                                    addNewImage(menuSecondSelected, "2carriles", null, null);
                                }
                                if (arg22 == 2) {
                                    addNewImage(menuSecondSelected, "3carriles", null, null);
                                }
                                if (arg22 == 3) {
                                    addNewImage(menuSecondSelected, "4carriles", null, null);
                                }
                                if (arg22 == 4) {
                                    addNewImage(menuSecondSelected, "5carriles", null, null);
                                }
                                if (arg22 == 5) {
                                    addNewImage(menuSecondSelected, "6carriles", null, null);
                                }
                                if (arg22 == 6) {
                                    addNewImage(menuSecondSelected, "berma", null, null);
                                }
                                if (arg22 == 7) {
                                    addNewImage(menuSecondSelected, "add1", null, null);
                                }
                                if (arg22 == 8) {
                                    addNewImage(menuSecondSelected, "add2", null, null);
                                }


                            }
                        });
                    }
                    if (arg22 == 2) {
                        final int menuSecondSelected = arg22;
                        txtThirdTitleList.setText("4 - Intersección");
                        thirdList.setAdapter(new SketchMenuSideAdapter((Activity) view.getContext(), getResources().getStringArray(R.array.viaInterseccion), getResources().obtainTypedArray(R.array.viaInterseccionIcon), 3));
                        thirdList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                            @Override
                            public void onItemClick(AdapterView<?> argTrh20, View argTrh21, int arg22, long arg2Trh3) {
                                if (arg22 == 0) {
                                    addNewImage(menuSecondSelected, "2x2_", null, null);
                                }
                                if (arg22 == 1) {
                                    addNewImage(menuSecondSelected, "2x4_", null, null);
                                }
                                if (arg22 == 2) {
                                    addNewImage(menuSecondSelected, "4x4_", null, null);
                                }
                                if (arg22 == 3) {
                                    addNewImage(menuSecondSelected, "4X4_1", null, null);
                                }
                                if (arg22 == 4) {
                                    addNewImage(menuSecondSelected, "3X3", null, null);
                                }

                                if (arg22 == 5) {
                                    addNewImage(menuSecondSelected, "complemento1", null, null);
                                }
                                if (arg22 == 6) {
                                    addNewImage(menuSecondSelected, "complemento2", null, null);
                                }
                                if (arg22 == 7) {
                                    addNewImage(menuSecondSelected, "complemento3", null, null);
                                }


                            }
                        });
                    }
                    if (arg22 == 3) {
                        final int menuSecondSelected = arg22;
                        txtThirdTitleList.setText("Antibloqueo");
                        thirdList.setAdapter(new SketchMenuSideAdapter((Activity) view.getContext(), getResources().getStringArray(R.array.viaAntibloqueo), getResources().obtainTypedArray(R.array.viaAntibloqueoIcon), 3));
                        thirdList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                            @Override
                            public void onItemClick(AdapterView<?> argTrh20, View argTrh21, int arg22, long arg2Trh3) {
                                if (arg22 == 0) {
                                    addNewImage(menuSecondSelected, "2x2", null, null);
                                }
                                if (arg22 == 1) {
                                    addNewImage(menuSecondSelected, "2x4", null, null);
                                }
                                if (arg22 == 2) {
                                    addNewImage(menuSecondSelected, "4x4", null, null);
                                }
                                if (arg22 == 3) {
                                    addNewImage(menuSecondSelected, "antibloqueo1", null, null);
                                }
                                if (arg22 == 4) {
                                    addNewImage(menuSecondSelected, "antibloqueo2", null, null);
                                }
                                if (arg22 == 5) {
                                    addNewImage(menuSecondSelected, "antibloqueo3", null, null);
                                }
                                if (arg22 == 6) {
                                    addNewImage(menuSecondSelected, "antibloqueo4", null, null);
                                }
                                if (arg22 == 7) {
                                    addNewImage(menuSecondSelected, "antibloqueo5", null, null);
                                }


                            }
                        });
                    }
                    if (arg22 == 4) {
                        final int menuSecondSelected = arg22;
                        txtThirdTitleList.setText("Lote o Prédio");
                        thirdList.setAdapter(new SketchMenuSideAdapter((Activity) view.getContext(), getResources().getStringArray(R.array.lotePredio), getResources().obtainTypedArray(R.array.lotePredioIcon), 3));
                        thirdList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                            @Override
                            public void onItemClick(AdapterView<?> argTrh20, View argTrh21, int arg22, long arg2Trh3) {
                                if (arg22 == 0) {
                                    addNewImage(menuSecondSelected, "Parqueadero", null, null);
                                }
                                if (arg22 == 1) {
                                    addNewImage(menuSecondSelected, "Asfalto", null, null);
                                }
                                if (arg22 == 2) {
                                    addNewImage(menuSecondSelected, "Arena", null, null);
                                }
                                if (arg22 == 3) {
                                    addNewImage(menuSecondSelected, "puertapredio", null, null);
                                }
                                if (arg22 == 4) {
                                    addNewImage(menuSecondSelected, "paredpredio", null, null);
                                }


                            }
                        });


                    }

                    if (arg22 == 5) {
                        final int menuSecondSelected = arg22;
                        txtThirdTitleList.setText("Paso a Nivel");
                        thirdList.setAdapter(new SketchMenuSideAdapter((Activity) view.getContext(), getResources().getStringArray(R.array.pasoNivel), getResources().obtainTypedArray(R.array.pasoNivelIcon), 3));
                        thirdList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                            @Override
                            public void onItemClick(AdapterView<?> argTrh20, View argTrh21, int arg22, long arg2Trh3) {
                                if (arg22 == 0) {
                                    addNewImage(menuSecondSelected, "Paso1", null, null);
                                }
                                if (arg22 == 1) {
                                    addNewImage(menuSecondSelected, "Paso2", null, null);
                                }
                                if (arg22 == 2) {
                                    addNewImage(menuSecondSelected, "Paso2_2", null, null);
                                }
                                if (arg22 == 3) {
                                    addNewImage(menuSecondSelected, "Riel", null, null);
                                }
                                if (arg22 == 4) {
                                    addNewImage(menuSecondSelected, "RielY", null, null);
                                }
                                if (arg22 == 5) {
                                    addNewImage(menuSecondSelected, "Riel1", null, null);
                                }
                                if (arg22 == 6) {
                                    addNewImage(menuSecondSelected, "Riel2", null, null);
                                }
                                if (arg22 == 7) {
                                    addNewImage(menuSecondSelected, "Riel3", null, null);
                                }
                                if (arg22 == 8) {
                                    addNewImage(menuSecondSelected, "Riel4", null, null);
                                }
                                if (arg22 == 9) {
                                    addNewImage(menuSecondSelected, "Riel5", null, null);
                                }
                                if (arg22 == 10) {
                                    addNewImage(menuSecondSelected, "Riel6", null, null);
                                }
                                if (arg22 == 11) {
                                    addNewImage(menuSecondSelected, "Riel7", null, null);
                                }


                            }
                        });


                    }
                    if (arg22 == 6) {
                        final int menuSecondSelected = arg22;
                        txtThirdTitleList.setText("Ciclo Ruta");
                        thirdList.setAdapter(new SketchMenuSideAdapter((Activity) view.getContext(), getResources().getStringArray(R.array.cicloRuta), getResources().obtainTypedArray(R.array.cicloRutaIcon), 3));

                        thirdList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                            @Override
                            public void onItemClick(AdapterView<?> argTrh20, View argTrh21, int arg22, long arg2Trh3) {
                                if (arg22 == 0) {
                                    addNewImage(menuSecondSelected, "ciclovia1", null, null);
                                }
                                if (arg22 == 1) {
                                    addNewImage(menuSecondSelected, "ciclovia2", null, null);
                                }
                                if (arg22 == 2) {
                                    addNewImage(menuSecondSelected, "ciclocurva1", null, null);
                                }
                                if (arg22 == 3) {
                                    addNewImage(menuSecondSelected, "ciclocurva2", null, null);
                                }
                                if (arg22 == 4) {
                                    addNewImage(menuSecondSelected, "ciclos1", null, null);
                                }
                                if (arg22 == 5) {
                                    addNewImage(menuSecondSelected, "ciclos2", null, null);
                                }
                                if (arg22 == 6) {
                                    addNewImage(menuSecondSelected, "ciclocurvac1", null, null);
                                }
                                if (arg22 == 7) {
                                    addNewImage(menuSecondSelected, "ciclocurvac2", null, null);
                                }
                                if (arg22 == 8) {
                                    addNewImage(menuSecondSelected, "cicloviay1", null, null);
                                }
                                if (arg22 == 9) {
                                    addNewImage(menuSecondSelected, "cicloviay2", null, null);
                                }
                                if (arg22 == 10) {
                                    addNewImage(menuSecondSelected, "cicloviaa", null, null);
                                }
                                if (arg22 == 11) {
                                    addNewImage(menuSecondSelected, "cicloviaa2", null, null);
                                }


                            }
                        });


                    }


                    if (arg22 == 7) {
                        final int menuSecondSelected = arg22;
                        txtThirdTitleList.setText("Peatonal");
                        thirdList.setAdapter(new SketchMenuSideAdapter((Activity) view.getContext(), getResources().getStringArray(R.array.anden), getResources().obtainTypedArray(R.array.andenIcon), 3));
                        thirdList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                            @Override
                            public void onItemClick(AdapterView<?> argTrh20, View argTrh21, int arg22, long arg2Trh3) {

                                if (arg22 == 0) {
                                    addNewImage(menuSecondSelected, "andenxl", null, null);
                                }
                                if (arg22 == 1) {
                                    addNewImage(menuSecondSelected, "andenxl2", null, null);
                                }
                                if (arg22 == 2) {
                                    addNewImage(menuSecondSelected, "curva", null, null);
                                }
                                if (arg22 == 3) {
                                    addNewImage(menuSecondSelected, "curva2", null, null);
                                }
                                if (arg22 == 4) {
                                    addNewImage(menuSecondSelected, "andenS", null, null);
                                }
                                if (arg22 == 5) {
                                    addNewImage(menuSecondSelected, "andenS2", null, null);
                                }

                                if (arg22 == 6) {
                                    addNewImage(menuSecondSelected, "asfalto", null, null);
                                }
                                if (arg22 == 7) {
                                    addNewImage(menuSecondSelected, "adoquin", null, null);
                                }
                                if (arg22 == 8) {
                                    addNewImage(menuSecondSelected, "piedra", null, null);
                                }
                                if (arg22 == 9) {
                                    addNewImage(menuSecondSelected, "anden", null, null);
                                }

                                if (arg22 == 10) {
                                    addNewImage(menuSecondSelected, "Rampa1", null, null);
                                }

                                if (arg22 == 11) {
                                    addNewImage(menuSecondSelected, "Rampa2", null, null);
                                }

                                if (arg22 == 12) {
                                    addNewImage(menuSecondSelected, "Rampa3", null, null);
                                }



                            }
                        });


                    }
                    if (arg22 == 8) {
                        final int menuSecondSelected = arg22;
                        txtThirdTitleList.setText("Paso elevado/Puente");
                        thirdList.setAdapter(new SketchMenuSideAdapter((Activity) view.getContext(), getResources().getStringArray(R.array.puente), getResources().obtainTypedArray(R.array.puenteIcon), 3));
                        thirdList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                            @Override
                            public void onItemClick(AdapterView<?> argTrh20, View argTrh21, int arg22, long arg2Trh3) {
                                if (arg22 == 0) {
                                    addNewImage(menuSecondSelected, "puente1", null, null);
                                }
                                if (arg22 == 1) {
                                    addNewImage(menuSecondSelected, "puente2", null, null);
                                }
                                if (arg22 == 2) {
                                    addNewImage(menuSecondSelected, "puente3", null, null);
                                }
                                if (arg22 == 3) {
                                    addNewImage(menuSecondSelected, "puente4", null, null);
                                }
                                if (arg22 == 4) {
                                    addNewImage(menuSecondSelected, "puente5", null, null);
                                }
                                if (arg22 == 5) {
                                    addNewImage(menuSecondSelected, "puente6", null, null);
                                }

                            }
                        });


                    }


                    if (arg22 == 9) {
                        final int menuSecondSelected = arg22;
                        txtThirdTitleList.setText("Tunel");
                        thirdList.setAdapter(new SketchMenuSideAdapter((Activity) view.getContext(), getResources().getStringArray(R.array.tunel), getResources().obtainTypedArray(R.array.tunelIcon), 3));
                        thirdList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                            @Override
                            public void onItemClick(AdapterView<?> argTrh20, View argTrh21, int arg22, long arg2Trh3) {
                                if (arg22 == 0) {
                                    addNewImage(menuSecondSelected, "tunel1", null, null);
                                }
                                if (arg22 == 1) {
                                    addNewImage(menuSecondSelected, "tunel2", null, null);
                                }
                                if (arg22 == 2) {
                                    addNewImage(menuSecondSelected, "tunel3", null, null);
                                }
                                if (arg22 == 3) {
                                    addNewImage(menuSecondSelected, "tunel4", null, null);
                                }
                                if (arg22 == 4) {
                                    addNewImage(menuSecondSelected, "tunel5", null, null);
                                }
                                if (arg22 == 5) {
                                    addNewImage(menuSecondSelected, "tunel6", null, null);
                                }


                            }
                        });


                    }

                    if (arg22 == 10) {
                        final int menuSecondSelected = arg22;
                        txtThirdTitleList.setText("Paso Inferior");
                        thirdList.setAdapter(new SketchMenuSideAdapter((Activity) view.getContext(), getResources().getStringArray(R.array.pasoInferior), getResources().obtainTypedArray(R.array.pasoInferiorIcon), 3));
                        thirdList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                            @Override
                            public void onItemClick(AdapterView<?> argTrh20, View argTrh21, int arg22, long arg2Trh3) {
                                if (arg22 == 0) {
                                    addNewImage(menuSecondSelected, "inferior1", null, null);
                                }
                                if (arg22 == 1) {
                                    addNewImage(menuSecondSelected, "inferior2", null, null);
                                }
                                if (arg22 == 2) {
                                    addNewImage(menuSecondSelected, "inferior3", null, null);
                                }

                                if (arg22 == 3) {
                                    addNewImage(menuSecondSelected, "desague1", null, null);
                                }
                                if (arg22 ==4) {
                                    addNewImage(menuSecondSelected, "desague2", null, null);
                                }
                                if (arg22 == 5) {
                                    addNewImage(menuSecondSelected, "desague3", null, null);
                                }


                            }
                        });


                    }

                    if (arg22 == 11) {
                        final int menuSecondSelected = arg22;
                        txtThirdTitleList.setText("Separador");
                        thirdList.setAdapter(new SketchMenuSideAdapter((Activity) view.getContext(), getResources().getStringArray(R.array.Separador), getResources().obtainTypedArray(R.array.SeparadorIcon), 3));
                        thirdList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                            @Override
                            public void onItemClick(AdapterView<?> argTrh20, View argTrh21, int arg22, long arg2Trh3) {
                                if (arg22 == 0) {
                                    addNewImage(menuSecondSelected, "Separador1", null, null);
                                }
                                if (arg22 == 1) {
                                    addNewImage(menuSecondSelected, "Separador2", null, null);
                                }
                                if (arg22 == 2) {
                                    addNewImage(menuSecondSelected, "Separador3", null, null);
                                }
                                if (arg22 == 3) {
                                    addNewImage(menuSecondSelected, "Separador4", null, null);
                                }
                                if (arg22 == 4) {
                                    addNewImage(menuSecondSelected, "Separador5", null, null);
                                }
                                if (arg22 == 5) {
                                    addNewImage(menuSecondSelected, "Separador6", null, null);
                                }
                                if (arg22 == 6) {
                                    addNewImage(menuSecondSelected, "Separador7", null, null);
                                }
                                if (arg22 == 7) {
                                    addNewImage(menuSecondSelected, "Separador8", null, null);
                                }
                                if (arg22 == 8) {
                                    addNewImage(menuSecondSelected, "Separador9", null, null);
                                }
                                if (arg22 == 9) {
                                    addNewImage(menuSecondSelected, "Separador10", null, null);
                                }
                                if (arg22 == 10) {
                                    addNewImage(menuSecondSelected, "Separador11", null, null);
                                }
                                if (arg22 == 11) {
                                    addNewImage(menuSecondSelected, "Separador12", null, null);
                                }
                                if (arg22 == 12) {
                                    addNewImage(menuSecondSelected, "Separador13", null, null);
                                }
                                if (arg22 == 13) {
                                    addNewImage(menuSecondSelected, "Separador14", null, null);
                                }
                                if (arg22 == 14) {
                                    addNewImage(menuSecondSelected, "Separador15", null, null);
                                }
                                if (arg22 == 15) {
                                    addNewImage(menuSecondSelected, "Separador16", null, null);
                                }
                                if (arg22 == 16) {
                                    addNewImage(menuSecondSelected, "Separador17", null, null);
                                }
                                if (arg22 == 17) {
                                    addNewImage(menuSecondSelected, "Separador18", null, null);
                                }
                                if (arg22 == 18) {
                                    addNewImage(menuSecondSelected, "Separador19", null, null);
                                }
                                if (arg22 == 19) {
                                    addNewImage(menuSecondSelected, "Separador20", null, null);
                                }
                                if (arg22 == 20) {
                                    addNewImage(menuSecondSelected, "Separador21", null, null);
                                }
                                if (arg22 == 21) {
                                    addNewImage(menuSecondSelected, "Separador22", null, null);
                                }

                            }
                        });
                    }


                    imgMenuRow.setVisibility(View.VISIBLE);
                    pnlMenuThird.setVisibility(View.VISIBLE);
                    pnlMenuThird.startAnimation(ani_LateralMenu_In);
                    imgMenuRow.startAnimation(ani_LateralMenuArrow_In);
                }
            });
        }
        if (capaSelected == 1) //SEñALES
        {
            lastload = 1;
            secondList.setAdapter(new SketchMenuSideAdapter((Activity) view.getContext(), getResources().getStringArray(R.array.Señales), getResources().obtainTypedArray(R.array.senalesIcon), 2));
            txtSecondTitleList.setText("Señales");
            secondList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> arg30, View arg31, int arg32, long arg33) {
                    clearbackground2();
                    arg31.setBackgroundResource(R.drawable.fondoselected);
                    ((TextView) arg31.findViewById(R.id.textoItem)).setTextColor(Color.parseColor("#8fb5e3"));
                    if (arg32 == 0) {
                        final int menuSecondSelected = arg32;
                        txtThirdTitleList.setText("4 - Agentes y Semaforo");
                        thirdList.setAdapter(new SketchMenuSideAdapter((Activity) view.getContext(), getResources().getStringArray(R.array.agentesSemaforo), getResources().obtainTypedArray(R.array.agentSemIcon), 3));
                        thirdList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                            @Override
                            public void onItemClick(AdapterView<?> arg30, View arg31, int arg32, long arg33) {
                                if (arg32 == 0) {
                                    addNewImage(menuSecondSelected, "Agente", null, null);
                                }
                                if (arg32 == 1) {
                                    addNewImage(menuSecondSelected, "Agente2", null, null);
                                }
                                if (arg32 == 2) {
                                    addNewImage(menuSecondSelected, "Rojo", null, null);
                                }
                                if (arg32 == 3) {
                                    addNewImage(menuSecondSelected, "Amarillo", null, null);
                                }
                                if (arg32 == 4) {
                                    addNewImage(menuSecondSelected, "Verde", null, null);
                                }
                                if (arg32 == 5) {
                                    addNewImage(menuSecondSelected, "Peaton_rojo", null, null);
                                }
                                if (arg32 == 6) {
                                    addNewImage(menuSecondSelected, "Peaton_verde", null, null);
                                }
                                if (arg32 == 7) {
                                    addNewImage(menuSecondSelected, "Icono1", null, null);
                                }
                                if (arg32 == 8) {
                                    addNewImage(menuSecondSelected, "Icono2", null, null);
                                }
                                if (arg32 == 9) {
                                    addNewImage(menuSecondSelected, "Icono3", null, null);
                                }
                                if (arg32 == 10) {
                                    addNewImage(menuSecondSelected, "Icono4", null, null);
                                }
                                if (arg32 == 11) {
                                    addNewImage(menuSecondSelected, "Icono5", null, null);
                                }
                                if (arg32 == 12) {
                                    addNewImage(menuSecondSelected, "Icono6", null, null);
                                }
                            }
                        });
                    }
                    if (arg32 == 1) {
                        final int menuSecondSelected = arg32;
                        txtThirdTitleList.setText("Preventivas");
                        thirdList.setAdapter(new SketchMenuSideAdapter((Activity) view.getContext(), getResources().getStringArray(R.array.preventivas), getResources().obtainTypedArray(R.array.preventivasIcon), 3));
                        thirdList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                            @Override
                            public void onItemClick(AdapterView<?> arg30, View arg31, int arg32, long arg33) {
                                if (arg32 == 0) {
                                    addNewImage(menuSecondSelected, "DesmCarril", null, null);
                                }
                                if (arg32 == 1) {
                                    addNewImage(menuSecondSelected, "ArregloVia", null, null);
                                }
                                if (arg32 == 2) {
                                    addNewImage(menuSecondSelected, "Rotonda", null, null);
                                }
                                if (arg32 == 3) {
                                    addNewImage(menuSecondSelected, "DobleSentido", null, null);
                                }
                                if (arg32 == 4) {
                                    addNewImage(menuSecondSelected, "Cntracurva", null, null);
                                }
                                if (arg32 == 5) {
                                    addNewImage(menuSecondSelected, "Resalto", null, null);
                                }
                                if (arg32 == 6) {
                                    addNewImage(menuSecondSelected, "ViaT", null, null);
                                }
                                if (arg32 == 7) {
                                    addNewImage(menuSecondSelected, "CurvaDerecha", null, null);
                                }
                                if (arg32 == 8) {
                                    addNewImage(menuSecondSelected, "ViaTren", null, null);
                                }
                                //____________________________________________

                                if (arg32 == 9) {
                                    addNewImage(menuSecondSelected, "Preventiva1", null, null);
                                }
                                if (arg32 == 10) {
                                    addNewImage(menuSecondSelected, "Preventiva2", null, null);
                                }
                                if (arg32 == 11) {
                                    addNewImage(menuSecondSelected, "Preventiva3", null, null);
                                }
                                if (arg32 == 12) {
                                    addNewImage(menuSecondSelected, "Preventiva5", null, null);
                                }

                                if (arg32 == 13) {
                                    addNewImage(menuSecondSelected, "Preventiva6", null, null);
                                }
                                if (arg32 == 14) {
                                    addNewImage(menuSecondSelected, "Preventiva7", null, null);
                                }
                                if (arg32 == 15) {
                                    addNewImage(menuSecondSelected, "Preventiva8", null, null);
                                }
                                if (arg32 == 16) {
                                    addNewImage(menuSecondSelected, "Preventiva9", null, null);
                                }
                                if (arg32 == 17) {
                                    addNewImage(menuSecondSelected, "Preventiva10", null, null);
                                }

                                if (arg32 == 18) {
                                    addNewImage(menuSecondSelected, "Preventiva11", null, null);
                                }
                                if (arg32 == 19) {
                                    addNewImage(menuSecondSelected, "Preventiva12", null, null);
                                }
                                if (arg32 ==20) {
                                    addNewImage(menuSecondSelected, "Preventiva13", null, null);
                                }
                                if (arg32 == 21) {
                                    addNewImage(menuSecondSelected, "Preventiva14", null, null);
                                }
                                if (arg32 == 22) {
                                    addNewImage(menuSecondSelected, "Preventiva15", null, null);//58
                                }
                                if (arg32 == 23) {
                                    addNewImage(menuSecondSelected, "Preventiva16", null, null);
                                }
                                if (arg32 == 24) {
                                    addNewImage(menuSecondSelected, "Preventiva17", null, null);
                                }
                                if (arg32 == 25) {
                                    addNewImage(menuSecondSelected, "Preventiva18", null, null);
                                }
                                if (arg32 == 26) {
                                    addNewImage(menuSecondSelected, "Preventiva19", null, null);
                                }
                                if (arg32 == 27) {
                                    addNewImage(menuSecondSelected, "Preventiva20", null, null);
                                }

                                if (arg32 == 28) {
                                    addNewImage(menuSecondSelected, "Preventiva21", null, null);
                                }
                                if (arg32 == 29) {
                                    addNewImage(menuSecondSelected, "Preventiva22", null, null);
                                }
                                if (arg32 == 30) {
                                    addNewImage(menuSecondSelected, "Preventiva23", null, null);
                                }
                                if (arg32 == 31) {
                                    addNewImage(menuSecondSelected, "Preventiva24", null, null);
                                }
                                if (arg32 == 32) {
                                    addNewImage(menuSecondSelected, "Preventiva25", null, null);//58
                                }
                                if (arg32 == 33) {
                                    addNewImage(menuSecondSelected, "Preventiva26", null, null);
                                }
                                if (arg32 == 34) {
                                    addNewImage(menuSecondSelected, "Preventiva27", null, null);
                                }
                                if (arg32 == 35) {
                                    addNewImage(menuSecondSelected, "Preventiva28", null, null);
                                }

                                if (arg32 == 36) {
                                    addNewImage(menuSecondSelected, "Preventiva30", null, null);
                                }
                                if (arg32 == 37) {
                                    addNewImage(menuSecondSelected, "Preventiva31", null, null);
                                }
                                if (arg32 == 38) {
                                    addNewImage(menuSecondSelected, "Preventiva32", null, null);
                                }
                                if (arg32 == 39) {
                                    addNewImage(menuSecondSelected, "Preventiva33", null, null);
                                }
                                if (arg32 == 40) {
                                    addNewImage(menuSecondSelected, "Preventiva34", null, null);
                                }
                                if (arg32 == 41) {
                                    addNewImage(menuSecondSelected, "Preventiva35", null, null);//58
                                }
                                if (arg32 == 42) {
                                    addNewImage(menuSecondSelected, "Preventiva36", null, null);
                                }
                                if (arg32 == 43) {
                                    addNewImage(menuSecondSelected, "Preventiva37", null, null);
                                }
                                if (arg32 == 44) {
                                    addNewImage(menuSecondSelected, "Preventiva38", null, null);
                                }
                                if (arg32 == 45) {
                                    addNewImage(menuSecondSelected, "Preventiva39", null, null);
                                }
                                if (arg32 == 46) {
                                    addNewImage(menuSecondSelected, "Preventiva40", null, null);
                                }
                                if (arg32 == 47) {
                                    addNewImage(menuSecondSelected, "Preventiva41", null, null);
                                }
                                if (arg32 == 48) {
                                    addNewImage(menuSecondSelected, "Preventiva42", null, null);
                                }
                                if (arg32 == 49) {
                                    addNewImage(menuSecondSelected, "Preventiva43", null, null);
                                }
                                if (arg32 == 50) {
                                    addNewImage(menuSecondSelected, "Preventiva44", null, null);
                                }
                                if (arg32 == 51) {
                                    addNewImage(menuSecondSelected, "Preventiva45", null, null);//58
                                }
                                if (arg32 == 52) {
                                    addNewImage(menuSecondSelected, "Preventiva46", null, null);
                                }
                                if (arg32 == 53) {
                                    addNewImage(menuSecondSelected, "Preventiva47", null, null);
                                }
                                if (arg32 == 54) {
                                    addNewImage(menuSecondSelected, "Preventiva48", null, null);
                                }
                                if (arg32 == 55) {
                                    addNewImage(menuSecondSelected, "Preventiva49", null, null);
                                }


                            }
                        });
                    }
                    if (arg32 == 2) {
                        final int menuSecondSelected = arg32;
                        txtThirdTitleList.setText("Reglamentarias");
                        thirdList.setAdapter(new SketchMenuSideAdapter((Activity) view.getContext(), getResources().getStringArray(R.array.reglamentarias), getResources().obtainTypedArray(R.array.reglamentaIcon), 3));
                        thirdList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                            @Override
                            public void onItemClick(AdapterView<?> arg30, View arg31, int arg32, long arg33) {
                                if (arg32 == 0) {
                                    addNewImage(menuSecondSelected, "Pare", null, null);
                                }
                                if (arg32 == 1) {
                                    addNewImage(menuSecondSelected, "NoPase", null, null);
                                }
                                if (arg32 == 2) {
                                    addNewImage(menuSecondSelected, "Cedaelpaso", null, null);
                                }
                                if (arg32 == 3) {
                                    addNewImage(menuSecondSelected, "CuceDerecha", null, null);
                                }
                                if (arg32 == 4) {
                                    addNewImage(menuSecondSelected, "SigaDerecho", null, null);
                                }
                                if (arg32 == 5) {
                                    addNewImage(menuSecondSelected, "ProhibGirar", null, null);
                                }
                                if (arg32 == 6) {
                                    addNewImage(menuSecondSelected, "NoEstacionar", null, null);
                                }
                                if (arg32 == 7) {
                                    addNewImage(menuSecondSelected, "NoCambioCarril", null, null);
                                }
                                if (arg32 == 8) {
                                    addNewImage(menuSecondSelected, "ViDobleSentido", null, null);
                                }
                                /*______________________________________________________________________*/

                                if (arg32 == 9) {
                                    addNewImage(menuSecondSelected, "Reglamentaria1", null, null);
                                }
                                if (arg32 == 10) {
                                    addNewImage(menuSecondSelected, "Reglamentaria2", null, null);
                                }
                                if (arg32 == 11) {
                                    addNewImage(menuSecondSelected, "Reglamentaria3", null, null);
                                }
                                if (arg32 == 12) {
                                    addNewImage(menuSecondSelected, "Reglamentaria4", null, null);
                                }
                                if (arg32 == 13) {
                                    addNewImage(menuSecondSelected, "Reglamentaria5", null, null);
                                }
                                if (arg32 == 14) {
                                    addNewImage(menuSecondSelected, "Reglamentaria6", null, null);
                                }
                                if (arg32 == 15) {
                                    addNewImage(menuSecondSelected, "Reglamentaria7", null, null);
                                }
                                if (arg32 == 16) {
                                    addNewImage(menuSecondSelected, "Reglamentaria8", null, null);
                                }
                                if (arg32 == 17) {
                                    addNewImage(menuSecondSelected, "Reglamentaria9", null, null);
                                }
                                if (arg32 == 18) {
                                    addNewImage(menuSecondSelected, "Reglamentaria10", null, null);
                                }

                                if (arg32 == 19) {
                                    addNewImage(menuSecondSelected, "Reglamentaria11", null, null);
                                }
                                if (arg32 == 20) {
                                    addNewImage(menuSecondSelected, "Reglamentaria12", null, null);
                                }
                                if (arg32 == 21) {
                                    addNewImage(menuSecondSelected, "Reglamentaria13", null, null);
                                }
                                if (arg32 == 22) {
                                    addNewImage(menuSecondSelected, "Reglamentaria14", null, null);
                                }
                                if (arg32 == 23) {
                                    addNewImage(menuSecondSelected, "Reglamentaria15", null, null);
                                }
                                if (arg32 == 24) {
                                    addNewImage(menuSecondSelected, "Reglamentaria16", null, null);
                                }
                                if (arg32 == 25) {
                                    addNewImage(menuSecondSelected, "Reglamentaria17", null, null);
                                }
                                if (arg32 == 26) {
                                    addNewImage(menuSecondSelected, "Reglamentaria18", null, null);
                                }
                                if (arg32 == 27) {
                                    addNewImage(menuSecondSelected, "Reglamentaria19", null, null);
                                }
                                if (arg32 ==28) {
                                    addNewImage(menuSecondSelected, "Reglamentaria20", null, null);
                                }
                                if (arg32 == 29) {
                                    addNewImage(menuSecondSelected, "Reglamentaria21", null, null);
                                }
                                if (arg32 == 30) {
                                    addNewImage(menuSecondSelected, "Reglamentaria22", null, null);
                                }
                                if (arg32 == 31) {
                                    addNewImage(menuSecondSelected, "Reglamentaria23", null, null);
                                }
                                if (arg32 == 32) {
                                    addNewImage(menuSecondSelected, "Reglamentaria24", null, null);
                                }
                                if (arg32 == 33) {
                                    addNewImage(menuSecondSelected, "Reglamentaria25", null, null);
                                }
                                if (arg32 == 34) {
                                    addNewImage(menuSecondSelected, "Reglamentaria26", null, null);
                                }
                                if (arg32 == 35) {
                                    addNewImage(menuSecondSelected, "Reglamentaria27", null, null);
                                }
                                if (arg32 == 36) {
                                    addNewImage(menuSecondSelected, "Reglamentaria28", null, null);
                                }
                                if (arg32 == 37) {
                                    addNewImage(menuSecondSelected, "Reglamentaria29", null, null);
                                }
                                if (arg32 == 38) {
                                    addNewImage(menuSecondSelected, "Reglamentaria30", null, null);
                                }
                                if (arg32 == 39) {
                                    addNewImage(menuSecondSelected, "Reglamentaria31", null, null);
                                }
                                if (arg32 == 40) {
                                    addNewImage(menuSecondSelected, "Reglamentaria32", null, null);
                                }
                                if (arg32 == 41) {
                                    addNewImage(menuSecondSelected, "Reglamentaria33", null, null);
                                }
                                if (arg32 == 42) {
                                    addNewImage(menuSecondSelected, "Reglamentaria34", null, null);
                                }
                                if (arg32 == 43) {
                                    addNewImage(menuSecondSelected, "Reglamentaria35", null, null);
                                }
                                if (arg32 == 44) {
                                    addNewImage(menuSecondSelected, "Reglamentaria36", null, null);
                                }
                                if (arg32 == 45) {
                                    addNewImage(menuSecondSelected, "Reglamentaria37", null, null);
                                }
                                if (arg32 == 46) {
                                    addNewImage(menuSecondSelected, "Reglamentaria38", null, null);
                                }
                                if (arg32 == 47) {
                                    addNewImage(menuSecondSelected, "Reglamentaria39", null, null);
                                }

                            }
                        });
                    }
                    if (arg32 == 3) {
                        final int menuSecondSelected = arg32;
                        txtThirdTitleList.setText("Informativas");
                        thirdList.setAdapter(new SketchMenuSideAdapter((Activity) view.getContext(), getResources().getStringArray(R.array.informativa), getResources().obtainTypedArray(R.array.informativaIcon), 3));
                        thirdList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                            @Override
                            public void onItemClick(AdapterView<?> arg30, View arg31, int arg32, long arg33) {
                                if (arg32 == 0) {
                                    addNewImage(menuSecondSelected, "Restaurante", null, null);
                                }
                                if (arg32 == 1) {
                                    addNewImage(menuSecondSelected, "Castillo", null, null);
                                }
                                if (arg32 == 2) {
                                    addNewImage(menuSecondSelected, "Gasolinera", null, null);
                                }
                                if (arg32 == 3) {
                                    addNewImage(menuSecondSelected, "Parqueaderos", null, null);
                                }
                                if (arg32 == 4) {
                                    addNewImage(menuSecondSelected, "CrusRoja", null, null);
                                }
                                if (arg32 == 5) {
                                    addNewImage(menuSecondSelected, "Taxis", null, null);//63
                                }
                                if (arg32 == 6) {
                                    addNewImage(menuSecondSelected, "varios", null, null);
                                }
                                if (arg32 == 7) {
                                    addNewImage(menuSecondSelected, "Milla", null, null);
                                }
                                if (arg32 == 8) {
                                    addNewImage(menuSecondSelected, "Informativa1", null, null);
                                }
                                if (arg32 == 9) {
                                    addNewImage(menuSecondSelected, "Informativa2", null, null);
                                }
                                if (arg32 == 10) {
                                    addNewImage(menuSecondSelected, "Informativa3", null, null);
                                }
                                if (arg32 == 11) {
                                    addNewImage(menuSecondSelected, "Informativa4", null, null);
                                }
                                if (arg32 == 12) {
                                    addNewImage(menuSecondSelected, "Informativa5", null, null);
                                }
                                if (arg32 == 13) {
                                    addNewImage(menuSecondSelected, "Informativa6", null, null);
                                }
                                if (arg32 == 14) {
                                    addNewImage(menuSecondSelected, "Informativa7", null, null);
                                }
                                if (arg32 == 15) {
                                    addNewImage(menuSecondSelected, "Informativa8", null, null);//63
                                }
                                if (arg32 == 16) {
                                    addNewImage(menuSecondSelected, "Informativa9", null, null);
                                }
                                if (arg32 == 17) {
                                    addNewImage(menuSecondSelected, "Informativa10", null, null);
                                }
                                if (arg32 == 18) {
                                    addNewImage(menuSecondSelected, "Informativa11", null, null);
                                }
                                if (arg32 == 19) {
                                    addNewImage(menuSecondSelected, "Informativa12", null, null);
                                }
                                if (arg32 == 20) {
                                    addNewImage(menuSecondSelected, "Informativa13", null, null);
                                }
                                if (arg32 == 21) {
                                    addNewImage(menuSecondSelected, "Informativa14", null, null);
                                }
                                if (arg32 == 22) {
                                    addNewImage(menuSecondSelected, "Informativa15", null, null);
                                }
                                if (arg32 == 23) {
                                    addNewImage(menuSecondSelected, "Informativa16", null, null);
                                }
                                if (arg32 == 24) {
                                    addNewImage(menuSecondSelected, "Informativa17", null, null);
                                }
                                if (arg32 == 25) {
                                    addNewImage(menuSecondSelected, "Informativa18", null, null);//63
                                }
                                if (arg32 == 26) {
                                    addNewImage(menuSecondSelected, "Informativa19", null, null);
                                }
                                if (arg32 == 27) {
                                    addNewImage(menuSecondSelected, "Informativa20", null, null);
                                }
                                if (arg32 == 28) {
                                    addNewImage(menuSecondSelected, "Informativa21", null, null);
                                }
                                if (arg32 == 29) {
                                    addNewImage(menuSecondSelected, "Informativa22", null, null);
                                }
                                if (arg32 == 30) {
                                    addNewImage(menuSecondSelected, "Informativa23", null, null);
                                }
                                if (arg32 == 31) {
                                    addNewImage(menuSecondSelected, "Informativa24", null, null);
                                }
                                if (arg32 == 32) {
                                    addNewImage(menuSecondSelected, "Informativa25", null, null);
                                }
                                if (arg32 == 33) {
                                    addNewImage(menuSecondSelected, "Informativa26", null, null);
                                }
                                if (arg32 == 34) {
                                    addNewImage(menuSecondSelected, "Informativa27", null, null);
                                }
                                if (arg32 == 35) {
                                    addNewImage(menuSecondSelected, "Informativa28", null, null);//63
                                }
                                if (arg32 == 36) {
                                    addNewImage(menuSecondSelected, "Informativa29", null, null);
                                }
                                if (arg32 == 37) {
                                    addNewImage(menuSecondSelected, "Informativa30", null, null);
                                }
                                if (arg32 == 38) {
                                    addNewImage(menuSecondSelected, "Informativa31", null, null);
                                }
                                if (arg32 == 39) {
                                    addNewImage(menuSecondSelected, "Informativa32", null, null);
                                }
                                if (arg32 == 40) {
                                    addNewImage(menuSecondSelected, "Informativa33", null, null);
                                }
                                if (arg32 == 41) {
                                    addNewImage(menuSecondSelected, "Informativa34", null, null);
                                }
                                if (arg32 == 42) {
                                    addNewImage(menuSecondSelected, "Informativa35", null, null);
                                }
                                if (arg32 == 43) {
                                    addNewImage(menuSecondSelected, "Informativa36", null, null);
                                }
                                if (arg32 == 44) {
                                    addNewImage(menuSecondSelected, "Informativa37", null, null);
                                }
                                if (arg32 ==45) {
                                    addNewImage(menuSecondSelected, "Informativa38", null, null);//63
                                }
                                if (arg32 == 46) {
                                    addNewImage(menuSecondSelected, "Informativa39", null, null);
                                }
                                if (arg32 == 47) {
                                    addNewImage(menuSecondSelected, "Informativa40", null, null);
                                }
                                if (arg32 == 48) {
                                    addNewImage(menuSecondSelected, "Informativa41", null, null);
                                }
                                if (arg32 == 49) {
                                    addNewImage(menuSecondSelected, "Informativa42", null, null);
                                }
                                if (arg32 == 50) {
                                    addNewImage(menuSecondSelected, "Informativa43", null, null);
                                }
                                if (arg32 == 51) {
                                    addNewImage(menuSecondSelected, "Informativa44", null, null);
                                }
                                if (arg32 == 52) {
                                    addNewImage(menuSecondSelected, "Informativa45", null, null);
                                }
                                if (arg32 == 53) {
                                    addNewImage(menuSecondSelected, "Informativa46", null, null);
                                }
                                if (arg32 == 54) {
                                    addNewImage(menuSecondSelected, "Informativa47", null, null);
                                }
                                if (arg32 == 55) {
                                    addNewImage(menuSecondSelected, "Informativa48", null, null);
                                }
                                if (arg32 == 56) {
                                    addNewImage(menuSecondSelected, "Informativa49", null, null);
                                }
                                if (arg32 == 57) {
                                    addNewImage(menuSecondSelected, "Informativa50", null, null);
                                }
                                if (arg32 == 58) {
                                    addNewImage(menuSecondSelected, "Informativa51", null, null);
                                }
                                if (arg32 == 59) {
                                    addNewImage(menuSecondSelected, "Informativa52", null, null);
                                }
                                if (arg32 == 60) {
                                    addNewImage(menuSecondSelected, "Informativa53", null, null);
                                }
                                if (arg32 == 61) {
                                    addNewImage(menuSecondSelected, "Informativa54", null, null);
                                }
                                if (arg32 == 62) {
                                    addNewImage(menuSecondSelected, "Informativa55", null, null);
                                }
                                if (arg32 == 63) {
                                    addNewImage(menuSecondSelected, "Informativa56", null, null);
                                }

                            }
                        });
                    }
                    if (arg32 == 4) {
                        final int menuSecondSelected = arg32;
                        txtThirdTitleList.setText("Temporales");
                        thirdList.setAdapter(new SketchMenuSideAdapter((Activity) view.getContext(), getResources().getStringArray(R.array.temporales), getResources().obtainTypedArray(R.array.temporalesIcon), 3));
                        thirdList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                            @Override
                            public void onItemClick(AdapterView<?> arg30, View arg31, int arg32, long arg33) {
                                if (arg32 == 0) {
                                    addNewImage(menuSecondSelected, "Desvio", null, null);
                                }
                                if (arg32 == 1) {
                                    addNewImage(menuSecondSelected, "Cinta", null, null);
                                }
                                if (arg32 == 2) {
                                    addNewImage(menuSecondSelected, "Pin", null, null);
                                }
                                if (arg32 == 3) {
                                    addNewImage(menuSecondSelected, "Temporal1", null, null);
                                }
                                if (arg32 == 4) {
                                    addNewImage(menuSecondSelected, "Temporal2", null, null);
                                }
                                if (arg32 == 5) {
                                    addNewImage(menuSecondSelected, "Temporal3", null, null);
                                }
                                if (arg32 == 6) {
                                    addNewImage(menuSecondSelected, "Temporal4", null, null);
                                }
                                if (arg32 == 7) {
                                    addNewImage(menuSecondSelected, "Temporal5", null, null);
                                }
                                if (arg32 == 8) {
                                    addNewImage(menuSecondSelected, "Temporal6", null, null);
                                }
                                if (arg32 == 9) {
                                    addNewImage(menuSecondSelected, "Temporal7", null, null);
                                }
                            }
                        });
                    }
                    if (arg32 == 5) {
                        final int menuSecondSelected = arg32;
                        txtThirdTitleList.setText("De piso");
                        thirdList.setAdapter(new SketchMenuSideAdapter((Activity) view.getContext(), getResources().getStringArray(R.array.dePiso), getResources().obtainTypedArray(R.array.dePisoIcon), 3));
                        thirdList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                            @Override
                            public void onItemClick(AdapterView<?> arg30, View arg31, int arg32, long arg33) {
                                if (arg32 == 0) {
                                    addNewImage(menuSecondSelected, "Sebra", null, null);
                                }
                                if (arg32 == 1) {
                                    addNewImage(menuSecondSelected, "Sebra2", null, null);
                                }
                                if (arg32 == 2) {
                                    addNewImage(menuSecondSelected, "Sebra3", null, null);
                                }
                                if (arg32 == 3) {
                                    addNewImage(menuSecondSelected, "Sebra_", null, null);
                                }
                                if (arg32 == 4) {
                                    addNewImage(menuSecondSelected, "Sebra_2", null, null);
                                }
                                if (arg32 == 5) {
                                    addNewImage(menuSecondSelected, "Sebra_3", null, null);
                                }
                                if (arg32 == 6) {
                                    addNewImage(menuSecondSelected, "Pare", null, null);
                                }
                                if (arg32 == 7) {
                                    addNewImage(menuSecondSelected, "ProhibidoParquear", null, null);
                                }
                                if (arg32 == 8) {
                                    addNewImage(menuSecondSelected, "Bifurcacion", null, null);
                                }
                                if (arg32 == 9) {
                                    addNewImage(menuSecondSelected, "SoloBuses", null, null);
                                }
                                if (arg32 == 10) {
                                    addNewImage(menuSecondSelected, "SalidaDerecha", null, null);
                                }
                                if (arg32 == 11) {
                                    addNewImage(menuSecondSelected, "Bicicletas", null, null);
                                }
                                if (arg32 == 12) {
                                    addNewImage(menuSecondSelected, "Escolar", null, null);
                                }
                                if (arg32 == 13) {
                                    addNewImage(menuSecondSelected, "Interseccion", null, null);
                                }
                                if (arg32 == 14) {
                                    addNewImage(menuSecondSelected, "Piso1", null, null);
                                }
                                if (arg32 == 15) {
                                    addNewImage(menuSecondSelected, "Piso2", null, null);
                                }
                                if (arg32 == 16) {
                                    addNewImage(menuSecondSelected, "Piso3", null, null);
                                }
                                if (arg32 == 17) {
                                    addNewImage(menuSecondSelected, "Piso4", null, null);
                                }
                                if (arg32 == 18) {
                                    addNewImage(menuSecondSelected, "Piso5", null, null);
                                }
                                if (arg32 == 19) {
                                    addNewImage(menuSecondSelected, "Piso6", null, null);
                                }
                                if (arg32 == 20) {
                                    addNewImage(menuSecondSelected, "Piso7", null, null);
                                }
                                if (arg32 == 21) {
                                    addNewImage(menuSecondSelected, "Piso8", null, null);
                                }
                                if (arg32 == 22) {
                                    addNewImage(menuSecondSelected, "Piso9", null, null);
                                }
                                if (arg32 == 23) {
                                    addNewImage(menuSecondSelected, "Piso10", null, null);
                                }
                                if (arg32 == 24) {
                                    addNewImage(menuSecondSelected, "Piso11", null, null);
                                }
                                if (arg32 == 25) {
                                    addNewImage(menuSecondSelected, "Piso12", null, null);
                                }
                                if (arg32 == 26) {
                                    addNewImage(menuSecondSelected, "Piso13", null, null);
                                }
                                if (arg32 == 27) {
                                    addNewImage(menuSecondSelected, "Piso14", null, null);
                                }
                                if (arg32 == 28) {
                                    addNewImage(menuSecondSelected, "Piso15", null, null);
                                }
                                if (arg32 == 29) {
                                    addNewImage(menuSecondSelected, "Piso16", null, null);
                                }
                                if (arg32 == 30) {
                                    addNewImage(menuSecondSelected, "Piso17", null, null);
                                }
                                if (arg32 == 31) {
                                    addNewImage(menuSecondSelected, "Piso18", null, null);
                                }
                                if (arg32 == 32) {
                                    addNewImage(menuSecondSelected, "Piso19", null, null);
                                }
                                if (arg32 == 33) {
                                    addNewImage(menuSecondSelected, "Piso20", null, null);
                                }
                                if (arg32 == 34) {
                                    addNewImage(menuSecondSelected, "Piso21", null, null);
                                }
                                if (arg32 == 35) {
                                    addNewImage(menuSecondSelected, "Piso22", null, null);
                                }
                                if (arg32 == 36) {
                                    addNewImage(menuSecondSelected, "Piso23", null, null);
                                }
                                if (arg32 == 37) {
                                    addNewImage(menuSecondSelected, "Piso24", null, null);
                                }
                                if (arg32 == 38) {
                                    addNewImage(menuSecondSelected, "Piso25", null, null);
                                }

                            }
                        });
                    }
                    if (arg32 == 6) {
                        final int menuSecondSelected = arg32;
                        txtThirdTitleList.setText("Líneas Blancas/Amarillas");

                        thirdList.setAdapter(new SketchMenuSideAdapter((Activity) view.getContext(), getResources().getStringArray(R.array.lineas), getResources().obtainTypedArray(R.array.lineasIcon), 3));
                        thirdList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                            @Override
                            public void onItemClick(AdapterView<?> arg30, View arg31, int arg32, long arg33) {
                                if (arg32 == 0) {
                                    addNewImage(menuSecondSelected, "Lineas1", null, null);
                                }
                                if (arg32 == 1) {
                                    addNewImage(menuSecondSelected, "Lineas2", null, null);
                                }
                                if (arg32 == 2) {
                                    addNewImage(menuSecondSelected, "Lineas3", null, null);
                                }
                                if (arg32 == 3) {
                                    addNewImage(menuSecondSelected, "Lineas4", null, null);
                                }
                                if (arg32 == 4) {
                                    addNewImage(menuSecondSelected, "Lineas5", null, null);
                                }

                                if (arg32 == 5) {
                                    addNewImage(menuSecondSelected, "Lineas6", null, null);
                                }
                                if (arg32 ==6) {
                                    addNewImage(menuSecondSelected, "Lineas7", null, null);
                                }
                                if (arg32 ==7) {
                                    addNewImage(menuSecondSelected, "Lineas8", null, null);
                                }
                                if (arg32 ==8) {
                                    addNewImage(menuSecondSelected, "Lineas9", null, null);
                                }
                                if (arg32 ==9) {
                                    addNewImage(menuSecondSelected, "Lineas10", null, null);
                                }
                                if (arg32 ==10) {
                                    addNewImage(menuSecondSelected, "Lineas11", null, null);
                                }
                                if (arg32 ==11) {
                                    addNewImage(menuSecondSelected, "Lineas12", null, null);
                                }
                                if (arg32 ==12) {
                                    addNewImage(menuSecondSelected, "Lineas13", null, null);
                                }

                                if (arg32 ==13) {
                                    addNewImage(menuSecondSelected, "Lineas14", null, null);
                                }
                                if (arg32 ==14) {
                                    addNewImage(menuSecondSelected, "Lineas15", null, null);
                                }
                                if (arg32 ==15) {
                                    addNewImage(menuSecondSelected, "Lineas16", null, null);
                                }
                                if (arg32 ==16) {
                                    addNewImage(menuSecondSelected, "Lineas17", null, null);
                                }

                            }
                        });


                    }

                    if (arg32 ==7) {
                        final int menuSecondSelected = arg32;
                        txtThirdTitleList.setText("Reductores");
                        thirdList.setAdapter(new SketchMenuSideAdapter((Activity) view.getContext(), getResources().getStringArray(R.array.reductores), getResources().obtainTypedArray(R.array.reductoresIcon), 3));
                        thirdList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                            @Override
                            public void onItemClick(AdapterView<?> arg30, View arg31, int arg32, long arg33) {
                                if (arg32 == 0) {
                                    addNewImage(menuSecondSelected, "Resaltos", null, null);
                                }
                                if (arg32 == 1) {
                                    addNewImage(menuSecondSelected, "Tachas", null, null);
                                }
                                if (arg32 == 2) {
                                    addNewImage(menuSecondSelected, "Tabular", null, null);
                                }
                                if (arg32 == 3) {
                                    addNewImage(menuSecondSelected, "Barreras", null, null);
                                }
                                if (arg32 == 4) {
                                    addNewImage(menuSecondSelected, "Conos", null, null);
                                }
                                if (arg32 == 5) {
                                    addNewImage(menuSecondSelected, "Sonoras", null, null);
                                }
                                if (arg32 == 6) {
                                    addNewImage(menuSecondSelected, "Movil", null, null);
                                }
                                if (arg32 == 7) {
                                    addNewImage(menuSecondSelected, "Tachones", null, null);
                                }
                                if (arg32 == 8) {
                                    addNewImage(menuSecondSelected, "Boyas", null, null);
                                }
                                if (arg32 == 9) {
                                    addNewImage(menuSecondSelected, "Bordillos", null, null);
                                }
                                if (arg32 == 10) {
                                    addNewImage(menuSecondSelected, "Sonorizador", null, null);
                                }
                                if (arg32 == 11) {
                                    addNewImage(menuSecondSelected, "Esoperol", null, null);
                                }

                            }
                        });

                    }
                    imgMenuRow.setVisibility(View.VISIBLE);
                    pnlMenuThird.setVisibility(View.VISIBLE);
                    pnlMenuThird.startAnimation(ani_LateralMenu_In);
                    imgMenuRow.startAnimation(ani_LateralMenuArrow_In);
                }
            });
        }
        if (capaSelected == 2) //OBJETOS
        {
            lastload = 2;
            secondList.setAdapter(new SketchMenuSideAdapter((Activity) view.getContext(), getResources().getStringArray(R.array.Objetos), getResources().obtainTypedArray(R.array.objetosIcon), 2));
            txtSecondTitleList.setText("Objetos");
            secondList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> arg40, View arg41, int arg42, long arg43) {
                    clearbackground2();
                    arg41.setBackgroundResource(R.drawable.fondoselected);
                    ((TextView) arg41.findViewById(R.id.textoItem)).setTextColor(Color.parseColor("#8fb5e3"));
                    if (arg42 == 0) {
                        final int menuSecondSelected = arg42;
                        txtThirdTitleList.setText("Objetos Fijo");
                        thirdList.setAdapter(new SketchMenuSideAdapter((Activity) view.getContext(), getResources().getStringArray(R.array.objetosFijo), getResources().obtainTypedArray(R.array.objetosFijoIcon), 3));
                        thirdList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                            @Override
                            public void onItemClick(AdapterView<?> arg40, View arg41, int arg42, long arg43) {
                                if (arg42 == 0) {
                                    addNewImage(menuSecondSelected, "Muro", null, null);
                                }
                                if (arg42 == 1) {
                                    addNewImage(menuSecondSelected, "Poste", null, null);
                                }
                                if (arg42 == 2) {
                                    addNewImage(menuSecondSelected, "Arbol1", null, null);
                                }
                                if (arg42 == 3) {
                                    addNewImage(menuSecondSelected, "Arbol2", null, null);
                                }
                                if (arg42 == 4) {
                                    addNewImage(menuSecondSelected, "Arbol3", null, null);
                                }
                                if (arg42 == 5) {
                                    addNewImage(menuSecondSelected, "Arbol4", null, null);
                                }
                                if (arg42 == 6) {
                                    addNewImage(menuSecondSelected, "Arbusto", null, null);
                                }

                                if (arg42 == 7) {
                                    addNewImage(menuSecondSelected, "Alcantarilla", null, null);
                                }
                                if (arg42 == 8) {
                                    addNewImage(menuSecondSelected, "Baranda", null, null);
                                }
                                if (arg42 == 9) {
                                    addNewImage(menuSecondSelected, "Semaforo", null, null);
                                }
                                if (arg42 == 10) {
                                    addNewImage(menuSecondSelected, "Inmueble", null, null);
                                }
                                if (arg42 == 11) {
                                    addNewImage(menuSecondSelected, "Hidrante", null, null);
                                }
                                if (arg42 == 12) {
                                    addNewImage(menuSecondSelected, "VallaSenal", null, null);
                                }
                                if (arg42 == 13) {
                                    addNewImage(menuSecondSelected, "Caseta", null, null);
                                }
                                if (arg42 == 14) {
                                    addNewImage(menuSecondSelected, "Valla", null, null);
                                }
                                if (arg42 == 15) {
                                    addNewImage(menuSecondSelected, "Lampara", null, null);
                                }
                                if (arg42 == 16) {
                                    addNewImage(menuSecondSelected, "EstacionFerrea", null, null);
                                }
                                if (arg42 == 17) {
                                    addNewImage(menuSecondSelected, "EstacionTransm", null, null);
                                }
                            }
                        });
                    }

                    if (arg42 == 1) {
                        final int menuSecondSelected = arg42;
                        txtThirdTitleList.setText("Estado de la Vía");
                        thirdList.setAdapter(new SketchMenuSideAdapter((Activity) view.getContext(), getResources().getStringArray(R.array.estadoVia), getResources().obtainTypedArray(R.array.estadoViaIcon), 3));
                        thirdList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                            @Override
                            public void onItemClick(AdapterView<?> arg40, View arg41, int arg42, long arg43) {
                                if (arg42 == 0) {
                                    addNewImage(menuSecondSelected, "hueco", null, null);
                                }
                                if (arg42 == 1) {
                                    addNewImage(menuSecondSelected, "derrumbe", null, null);
                                }
                                if (arg42 == 2) {
                                    addNewImage(menuSecondSelected, "reparacion", null, null);
                                }
                                if (arg42 == 3) {
                                    addNewImage(menuSecondSelected, "hundimiento", null, null);
                                }
                                if (arg42 == 4) {
                                    addNewImage(menuSecondSelected, "inundacion", null, null);
                                }
                                if (arg42 == 5) {
                                    addNewImage(menuSecondSelected, "parcha", null, null);
                                }
                                if (arg42 == 6) {
                                    addNewImage(menuSecondSelected, "rizada", null, null);
                                }
                                if (arg42 == 7) {
                                    addNewImage(menuSecondSelected, "fisura", null, null);
                                }


                            }
                        });
                    }

                    if (arg42 == 2) {
                        final int menuSecondSelected = arg42;
                        txtThirdTitleList.setText("Condiciones Climáticas");
                        thirdList.setAdapter(new SketchMenuSideAdapter((Activity) view.getContext(), getResources().getStringArray(R.array.condClima), getResources().obtainTypedArray(R.array.condClimaIcon), 3));
                        thirdList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                            @Override
                            public void onItemClick(AdapterView<?> arg40, View arg41, int arg42, long arg43) {
                                if (arg42 == 0) {
                                    addNewImage(menuSecondSelected, "clima1", null, null);
                                }
                                if (arg42 == 1) {
                                    addNewImage(menuSecondSelected, "clima2", null, null);
                                }
                                if (arg42 == 2) {
                                    addNewImage(menuSecondSelected, "clima3", null, null);
                                }
                                if (arg42 == 3) {
                                    addNewImage(menuSecondSelected, "clima4", null, null);
                                }
                                if (arg42 == 4) {
                                    addNewImage(menuSecondSelected, "clima5", null, null);
                                }
                                if (arg42 == 5) {
                                    addNewImage(menuSecondSelected, "clima6", null, null);
                                }
                                if (arg42 == 6) {
                                    addNewImage(menuSecondSelected, "clima7", null, null);
                                }
                                if (arg42 == 7) {
                                    addNewImage(menuSecondSelected, "clima8", null, null);
                                }
                                if (arg42 == 8) {
                                    addNewImage(menuSecondSelected, "clima9", null, null);
                                }
                                if (arg42 == 9) {
                                    addNewImage(menuSecondSelected, "clima10", null, null);
                                }


                            }
                        });
                    }
                    if (arg42 == 3) {
                        final int menuSecondSelected = arg42;
                        txtThirdTitleList.setText("Condición Vía");
                        thirdList.setAdapter(new SketchMenuSideAdapter((Activity) view.getContext(), getResources().getStringArray(R.array.condVia), getResources().obtainTypedArray(R.array.condViaIcon), 3));
                        thirdList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                            @Override
                            public void onItemClick(AdapterView<?> arg40, View arg41, int arg42, long arg43) {
                                if (arg42 == 0) {
                                    addNewImage(menuSecondSelected, "aceite", null, null);
                                }
                                if (arg42 == 1) {
                                    addNewImage(menuSecondSelected, "humeda", null, null);
                                }
                                if (arg42 == 2) {
                                    addNewImage(menuSecondSelected, "lodo", null, null);
                                }


                            }
                        });

                    }
                    if (arg42 == 4) {
                        final int menuSecondSelected = arg42;
                        txtThirdTitleList.setText("Objetos Varios");
                        thirdList.setAdapter(new SketchMenuSideAdapter((Activity) view.getContext(), getResources().getStringArray(R.array.objetosVarios), getResources().obtainTypedArray(R.array.objetosVariosIcon), 3));
                        thirdList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                            @Override
                            public void onItemClick(AdapterView<?> arg40, View arg41, int arg42, long arg43) {
                                if (arg42 == 0) {
                                    addNewImage(menuSecondSelected, "material1", null, null);
                                }
                                if (arg42 == 1) {
                                    addNewImage(menuSecondSelected, "material2", null, null);
                                }
                                if (arg42 == 2) {
                                    addNewImage(menuSecondSelected, "paraguas", null, null);
                                }
                                if (arg42 == 3) {
                                    addNewImage(menuSecondSelected, "zapato", null, null);
                                }
                                if (arg42 == 4) {
                                    addNewImage(menuSecondSelected, "maletin", null, null);
                                }
                                if (arg42 == 5) {
                                    addNewImage(menuSecondSelected, "placa", null, null);
                                }
                                if (arg42 == 6) {
                                    addNewImage(menuSecondSelected, "semoviente", null, null);
                                }
                                if (arg42 == 7) {
                                    addNewImage(menuSecondSelected, "celular", null, null);
                                }
                                if (arg42 == 8) {
                                    addNewImage(menuSecondSelected, "arma1", null, null);
                                }
                                if (arg42 == 9) {
                                    addNewImage(menuSecondSelected, "arma2", null, null);
                                }


                            }
                        });

                    }

                    imgMenuRow.setVisibility(View.VISIBLE);
                    pnlMenuThird.setVisibility(View.VISIBLE);
                    pnlMenuThird.startAnimation(ani_LateralMenu_In);
                    imgMenuRow.startAnimation(ani_LateralMenuArrow_In);
                }
            });
        }
        if (capaSelected == 3) //VEHICULOS
        {
            lastload = 3;
            secondList.setAdapter(new SketchMenuSideAdapter((Activity) view.getContext(), getResources().getStringArray(R.array.vehiculos), getResources().obtainTypedArray(R.array.vehiculosIcon), 2));
            txtSecondTitleList.setText("Vehículos");
            secondList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> arg50, View arg51, int arg52, long arg53) {
                    clearbackground2();
                    arg51.setBackgroundResource(R.drawable.fondoselected);
                    ((TextView) arg51.findViewById(R.id.textoItem)).setTextColor(Color.parseColor("#8fb5e3"));
                    if (arg52 == 0) {
                        final int menuSecondSelected = arg52;
                        txtThirdTitleList.setText("Automóviles");
                        thirdList.setAdapter(new SketchMenuSideAdapter((Activity) view.getContext(), getResources().getStringArray(R.array.automoviles), getResources().obtainTypedArray(R.array.automovilesIcon), 3));
                        thirdList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                            @Override
                            public void onItemClick(AdapterView<?> arg50, View arg51, int arg52, long arg53) {
                                if (arg52 == 0) {
                                    addNewImage(menuSecondSelected, "Sedan", null, null);
                                }
                                if (arg52 == 1) {
                                    addNewImage(menuSecondSelected, "Golf", null, null);
                                }
                                if (arg52 == 2) {
                                    addNewImage(menuSecondSelected, "Taxi_zapatico", null, null);
                                }
                                if (arg52 == 3) {
                                    addNewImage(menuSecondSelected, "Taxi_Sedan", null, null);
                                }
                                if (arg52 == 4) {
                                    addNewImage(menuSecondSelected, "Cupe", null, null);
                                }
                                if (arg52 == 5) {
                                    addNewImage(menuSecondSelected, "Camioneta1", null, null);
                                }
                                if (arg52 == 6) {
                                    addNewImage(menuSecondSelected, "Camioneta2", null, null);
                                }
                                if (arg52 == 7) {
                                    addNewImage(menuSecondSelected, "Convertible", null, null);
                                }
                                if (arg52 == 8) {
                                    addNewImage(menuSecondSelected, "Limosina", null, null);
                                }
                                if (arg52 == 9) {
                                    addNewImage(menuSecondSelected, "Minivan", null, null);
                                }
                                if (arg52 == 10) {
                                    addNewImage(menuSecondSelected, "Van", null, null);
                                }
                                if (arg52 == 11) {
                                    addNewImage(menuSecondSelected, "Van_escolar", null, null);
                                }
                                if (arg52 == 12) {
                                    addNewImage(menuSecondSelected, "Campero", null, null);
                                }
                                if (arg52 == 13) {
                                    addNewImage(menuSecondSelected, "Pickup1", null, null);
                                }
                                if (arg52 == 14) {
                                    addNewImage(menuSecondSelected, "Pickup2", null, null);
                                }

                            }
                        });
                    }
                    if (arg52 == 1) {
                        final int menuSecondSelected = arg52;
                        txtThirdTitleList.setText("Motos");
                        thirdList.setAdapter(new SketchMenuSideAdapter((Activity) view.getContext(), getResources().getStringArray(R.array.motos), getResources().obtainTypedArray(R.array.motosIcon), 3));
                        thirdList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                            @Override
                            public void onItemClick(AdapterView<?> arg50, View arg51, int arg52, long arg53) {
                                if (arg52 == 0) {
                                    addNewImage(menuSecondSelected, "Moto", null, null);
                                }
                                if (arg52 == 1) {
                                    addNewImage(menuSecondSelected, "Moto2", null, null);
                                }
                                if (arg52 == 2) {
                                    addNewImage(menuSecondSelected, "QuatriMoto", null, null);
                                }
                                if (arg52 == 3) {
                                    addNewImage(menuSecondSelected, "Mototriciclo", null, null);
                                }
                                if (arg52 == 4) {
                                    addNewImage(menuSecondSelected, "MotoAltoCilindraje", null, null);
                                }
                                if (arg52 == 5) {
                                    addNewImage(menuSecondSelected, "Motocarro", null, null);
                                }
                                if (arg52 == 6) {
                                    addNewImage(menuSecondSelected, "MotoTaxi", null, null);
                                }


                            }
                        });
                    }
                    if (arg52 == 2) {
                        final int menuSecondSelected = arg52;
                        txtThirdTitleList.setText("Buses");
                        thirdList.setAdapter(new SketchMenuSideAdapter((Activity) view.getContext(), getResources().getStringArray(R.array.buses), getResources().obtainTypedArray(R.array.busesIcon), 3));
                        thirdList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                            @Override
                            public void onItemClick(AdapterView<?> arg50, View arg51, int arg52, long arg53) {

                               /* if (arg52 == 0)

                                {
                                    addNewImage(menuSecondSelected, "Bus", null, null);
                                }*/

                                if (arg52 == 0)

                                {
                                    addNewImage(menuSecondSelected, "Buseta", null, null);
                                }

                               /* if (arg52 == 2)

                                {
                                    addNewImage(menuSecondSelected, "BusetaEscolar", null, null);
                                }*/

                               /* if (arg52 == 3)

                                {
                                    addNewImage(menuSecondSelected, "BusEscolar", null, null);
                                }

                                if (arg52 == 4)

                                {
                                    addNewImage(menuSecondSelected, "Microbus", null, null);
                                }

                                if (arg52 == 5)

                                {
                                    addNewImage(menuSecondSelected, "Colectivo", null, null);
                                }*/

                                if (arg52 == 1)

                                {
                                    addNewImage(menuSecondSelected, "Articulado", null, null);
                                }

                                if (arg52 == 2)

                                {
                                    addNewImage(menuSecondSelected, "Biarticulado", null, null);
                                }

                                if (arg52 == 3)

                                {
                                    addNewImage(menuSecondSelected, "Sitp", null, null);
                                }

                                if (arg52 == 4)

                                {
                                    addNewImage(menuSecondSelected, "Alimentadores", null, null);
                                }
                            }

                        });

                    }
                    if (arg52 == 3) {
                        final int menuSecondSelected = arg52;
                        txtThirdTitleList.setText("Camiones");
                        thirdList.setAdapter(new SketchMenuSideAdapter((Activity) view.getContext(), getResources().getStringArray(R.array.camiones), getResources().obtainTypedArray(R.array.camionesIcon), 3));
                        thirdList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                            @Override
                            public void onItemClick(AdapterView<?> arg50, View arg51, int arg52, long arg53) {
                                if (arg52 == 0) {
                                    addNewImage(menuSecondSelected, "Camion", null, null);
                                }
                                if (arg52 == 1) {
                                    addNewImage(menuSecondSelected, "Tanque", null, null);
                                }
                                if (arg52 == 2) {
                                    addNewImage(menuSecondSelected, "Estacas", null, null);
                                }

                                if (arg52 == 3) {
                                    addNewImage(menuSecondSelected, "Ninera", null, null);
                                }

                                if (arg52 == 4) {
                                    addNewImage(menuSecondSelected, "Dobletroque", null, null);
                                }

                                if (arg52 == 5) {
                                    addNewImage(menuSecondSelected, "Tractocamion", null, null);
                                }
                                if (arg52 == 6) {
                                    addNewImage(menuSecondSelected, "Remolque", null, null);
                                }
                                if (arg52 == 7) {
                                    addNewImage(menuSecondSelected, "Mezcladora", null, null);
                                }

                                if (arg52 == 8) {
                                    addNewImage(menuSecondSelected, "Cabezote", null, null);
                                }
                                if (arg52 == 9) {
                                    addNewImage(menuSecondSelected, "Trailer1", null, null);
                                }
                                if (arg52 == 10) {
                                    addNewImage(menuSecondSelected, "Trailer2", null, null);
                                }
                                if (arg52 == 11) {
                                    addNewImage(menuSecondSelected, "Trailer3", null, null);
                                }
                            }
                        });
                    }
                    if (arg52 == 4) {
                        final int menuSecondSelected = arg52;
                        txtThirdTitleList.setText("Bicicletas");
                        thirdList.setAdapter(new SketchMenuSideAdapter((Activity) view.getContext(), getResources().getStringArray(R.array.bicicletas), getResources().obtainTypedArray(R.array.bicicletasIcon), 3));
                        thirdList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                            @Override

                            public void onItemClick(AdapterView<?> arg50, View arg51, int arg52, long arg53) {
                                if (arg52 == 0) {
                                    addNewImage(menuSecondSelected, "Bicicletas", null, null);
                                }
                                if (arg52 == 1) {
                                    addNewImage(menuSecondSelected, "BiciTaxi", null, null);
                                }
                                if (arg52 == 2) {
                                    addNewImage(menuSecondSelected, "Bicimoto", null, null);
                                }
                                if (arg52 == 3) {
                                    addNewImage(menuSecondSelected, "Triciclo", null, null);
                                }

                            }
                        });
                    }
                    if (arg52 == 5) {
                        final int menuSecondSelected = arg52;
                        txtThirdTitleList.setText("Maquinaria");
                        thirdList.setAdapter(new SketchMenuSideAdapter((Activity) view.getContext(), getResources().getStringArray(R.array.maquinarias), getResources().obtainTypedArray(R.array.maquinariasIcon), 3));
                        thirdList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                            @Override
                            public void onItemClick(AdapterView<?> arg50, View arg51, int arg52, long arg53) {

                                if (arg52 == 0)

                                {
                                    addNewImage(menuSecondSelected, "Aplanadora", null, null);
                                }

                                if (arg52 == 1)

                                {
                                    addNewImage(menuSecondSelected, "Minicargador", null, null);
                                }

                                if (arg52 == 2)

                                {
                                    addNewImage(menuSecondSelected, "Retroexcavadora", null, null);
                                }

                                if (arg52 == 3)

                                {
                                    addNewImage(menuSecondSelected, "Excavadora", null, null);
                                }

                                if (arg52 == 4)

                                {
                                    addNewImage(menuSecondSelected, "Grua", null, null);
                                }

                                if (arg52 == 5)

                                {
                                    addNewImage(menuSecondSelected, "Camion", null, null);
                                }

                                if (arg52 == 6)

                                {
                                    addNewImage(menuSecondSelected, "Montacarga", null, null);
                                }


                            }

                        });


                    }

                    if (arg52 == 6) {
                        final int menuSecondSelected = arg52;
                        txtThirdTitleList.setText("Tracción Animal");
                        thirdList.setAdapter(new SketchMenuSideAdapter((Activity) view.getContext(), getResources().getStringArray(R.array.traccionAnimal), getResources().obtainTypedArray(R.array.traccionAnimalIcon), 3));
                        thirdList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                            @Override
                            public void onItemClick(AdapterView<?> arg50, View arg51, int arg52, long arg53) {
                                if (arg52 == 0) {
                                    addNewImage(menuSecondSelected, "Animal", null, null);
                                }
                                if (arg52 == 1) {
                                    addNewImage(menuSecondSelected, "Remolque", null, null);
                                }
                                if (arg52 == 2) {
                                    addNewImage(menuSecondSelected, "Zorillo", null, null);
                                }
                            }
                        });


                    }
                    imgMenuRow.setVisibility(View.VISIBLE);
                    pnlMenuThird.setVisibility(View.VISIBLE);
                    pnlMenuThird.startAnimation(ani_LateralMenu_In);
                    imgMenuRow.startAnimation(ani_LateralMenuArrow_In);
                }
            });
        }
        if (capaSelected == 4) //Dibujo
        {
            lastload = 4;
            secondList.setAdapter(new SketchMenuSideAdapter((Activity) view.getContext(), getResources().getStringArray(R.array.dibujo), getResources().obtainTypedArray(R.array.dibujosIcon), 2));
            txtSecondTitleList.setText("Dibujo");
            secondList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> arg60, View arg61, int arg62, long arg63) {
                    clearbackground2();
                    arg61.setBackgroundResource(R.drawable.fondoselected);
                    ((TextView) arg61.findViewById(R.id.textoItem)).setTextColor(Color.parseColor("#8fb5e3"));
                    /*if(arg62 == 0)
                    {
                        txtThirdTitleList.setText("Puntos PR PA");
                        thirdList.setAdapter(new SketchMenuSideAdapter((Activity) view.getContext(), getResources().getStringArray(R.array.dibujo), getResources().obtainTypedArray(R.array.principalIcon), 3));
                        thirdList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                            @Override
                            public void onItemClick(AdapterView<?> arg60, View arg61, int arg62, long arg63) {
                                if(arg62 == 0)
                                {
                                    addNewImage("PR");
                                }
                            }
                        });
                    }*/
                    if (arg62 == 0) {
                        txtThirdTitleList.setText("Medidas");
                        thirdList.setAdapter(new SketchMenuSideAdapter((Activity) view.getContext(), getResources().getStringArray(R.array.medidas), getResources().obtainTypedArray(R.array.medidasIcon), 3));
                    }
                    if (arg62 == 1) {
                        txtThirdTitleList.setText("Huellas");
                        thirdList.setAdapter(new SketchMenuSideAdapter((Activity) view.getContext(), getResources().getStringArray(R.array.huellas), getResources().obtainTypedArray(R.array.huellasIcon), 3));
                    }
                    if (arg62 == 2) {
                        txtThirdTitleList.setText("Dibujo Libre");
                        thirdList.setAdapter(new SketchMenuSideAdapter((Activity) view.getContext(), getResources().getStringArray(R.array.dibujoLibre), getResources().obtainTypedArray(R.array.dibujoLibreIcon), 3));
                    }
                    imgMenuRow.setVisibility(View.VISIBLE);
                    pnlMenuThird.setVisibility(View.VISIBLE);
                    pnlMenuThird.startAnimation(ani_LateralMenu_In);
                    imgMenuRow.startAnimation(ani_LateralMenuArrow_In);
                }
            });
        }
        pnlMenuThird.setVisibility(View.GONE);
        imgMenuRow.setVisibility(View.GONE);
    }

    private void clearbackground2() {
        try {
            for (int i = 0; i < secondList.getChildCount(); i++) {
                secondList.getChildAt(i).setBackground(null);
                //((ImageView) secondList.getChildAt(i).findViewById(R.id.ImgItem)).setImageDrawable(getResources().obtainTypedArray(R.array.principalIcon).getDrawable(i));
                ((TextView) secondList.getChildAt(i).findViewById(R.id.textoItem)).setTextColor(Color.parseColor("#dadde0"));
            }
        } catch (Exception ex) {
        }
    }

    //region - Acciones de los botones
    private void SelectedFirstMenuUp(int selectedIndex) {
        switch (selectedIndex) {
            case 0:
                btnMessure.startAnimation(ani_BtnSelected);
                if (pnl_CapaCoordenadas.getVisibility() == view.VISIBLE) {
                    pnl_CapaCoordenadas.setVisibility(view.GONE);
                    stateVisualCoordinate = false;
                    btnMessure.setAlpha(0.6f);
                } else {
                    pnl_CapaCoordenadas.setVisibility(view.VISIBLE);
                    stateVisualCoordinate = true;
                    btnMessure.setAlpha(1f);
                }
                break;
            case 1:
                btnUndo.startAnimation(ani_BtnSelected);
                break;
            case 2:
                btnRedo.startAnimation(ani_BtnSelected);
                break;
            case 3:
                break;
            case 4:
                btnOkSketch.startAnimation(ani_BtnSelected);
                previewAndSave();
                break;
        }
    }
    //endregion fin touch y manipulaciones

    private void SelectedFirstMenuDown(int selectedIndex) {
        if (lastselected != selectedIndex) {
            if (capaSelected == -1) {
                deselctedObject();
            } else {                                                                                      // Si al menos una capa SI esta seleccionada accion de deseleccion de capa Animacion y cambio del alpha
                switch (capaSelected) {
                    case 0:
                        btnVias.startAnimation(ani_CapaMenu_Deselected);
                        pnl_CapaVias.setAlpha(0.3f);
                        pnl_ViasTagMeasure.setAlpha(0.3f);
                        break;
                    case 1:
                        btnSenales.startAnimation(ani_CapaMenu_Deselected);
                        pnl_CapaSenales.setAlpha(0.3f);
                        pnl_SenalesTagMeasure.setAlpha(0.3f);
                        break;
                    case 2:
                        btnObjetos.startAnimation(ani_CapaMenu_Deselected);
                        pnl_CapaObjetos.setAlpha(0.3f);
                        pnl_ObjectTagMeasure.setAlpha(0.3f);
                        break;
                    case 3:
                        btnVehículos.startAnimation(ani_CapaMenu_Deselected);
                        pnl_CapaVehiculos.setAlpha(0.5f);
                        break;
                    case 4:
                        btnDibujo.startAnimation(ani_CapaMenu_Deselected);
                        pnl_CapaDibujo.setAlpha(0.3f);
                        pnl_VehicleTagMeasure.setAlpha(0.3f);
                        break;
                    default:
                        capaSelected = -1;
                        break;
                }
                editOnTouch4Capa(false);                                                                // se le quito los eventos de manipulacion a los objetos de la anterior capa que estaba seleccionada
            }
            capaSelected = selectedIndex;
            editOnTouch4Capa(true);

            switch (lastselected) { // deseleccion Btn
                case 0:
                    fondoSelectedVias.setVisibility(View.GONE);
                    break;
                case 1:
                    fondoSelectedSenales.setVisibility(View.GONE);
                    break;
                case 2:
                    fondoSelectedObjetos.setVisibility(View.GONE);
                    break;
                case 3:
                    fondoSelectedVehiculos.setVisibility(View.GONE);
                    break;
                case 4:
                    fondoSelectedDibujos.setVisibility(View.GONE);
                    pnl_CapaMedidas.setFlagActive(false);
                    break;
            }

            switch (capaSelected) { // Seleccion Btn
                case 0:
                    lastselected = capaSelected;
                    fondoSelectedVias.setVisibility(View.VISIBLE);
//                    fondoselected.setX(0);
//                    fondoselected.setAlpha(1);
                    textSelected.setText("Capa de Vías");
                    btnVias.startAnimation(ani_BtnSelected);
                    pnl_CapaVias.setAlpha(1f);
                    pnl_ViasTagMeasure.setAlpha(1f);
                    txtIndMenuCapa.setText("<   Menu\n     Vías");
                    break;
                case 1:
                    lastselected = capaSelected;
                    fondoSelectedSenales.setVisibility(View.VISIBLE);
//                    fondoselected.setX(82);
//                    fondoselected.setAlpha(1);
                    textSelected.setText("Capa de Señales");
                    btnSenales.startAnimation(ani_BtnSelected);
                    pnl_CapaSenales.setAlpha(1f);
                    pnl_SenalesTagMeasure.setAlpha(1f);
                    txtIndMenuCapa.setText("<   Menu\n     Señales");
                    break;
                case 2:
                    lastselected = capaSelected;
                    fondoSelectedObjetos.setVisibility(View.VISIBLE);
//                    fondoselected.setX(164);
//                    fondoselected.setAlpha(1);
                    textSelected.setText("Capa de Objetos");
                    btnObjetos.startAnimation(ani_BtnSelected);
                    pnl_CapaObjetos.setAlpha(1f);
                    pnl_ObjectTagMeasure.setAlpha(1f);
                    txtIndMenuCapa.setText("<   Menu\n     Objetos");
                    break;
                case 3:
                    lastselected = capaSelected;
                    fondoSelectedVehiculos.setVisibility(View.VISIBLE);
//                    fondoselected.setX(246);
//                    fondoselected.setAlpha(1);
                    textSelected.setText("Capa de Vehículos");
                    btnVehículos.startAnimation(ani_BtnSelected);
                    pnl_CapaVehiculos.setAlpha(1f);
                    pnl_VehicleTagMeasure.setAlpha(1f);
                    txtIndMenuCapa.setText("<   Menu\n     Vehículos");
                    break;
                case 4:
                    lastselected = capaSelected;
                    fondoSelectedDibujos.setVisibility(View.VISIBLE);
//                    fondoselected.setX(328);
//                    fondoselected.setAlpha(1);
                    textSelected.setText("Capa de Dibujo");
                    btnDibujo.startAnimation(ani_BtnSelected);
                    pnl_CapaDibujo.setAlpha(1f);
                    pnl_CapaMedidas.setFlagActive(true);
                    txtIndMenuCapa.setText("<   Menu\n     Dibujos");
                    break;
                default:
                    capaSelected = -1;
                    break;
            }
            textSelected.startAnimation(ani_CapaTitle);
            //fondoselected.startAnimation(ani_CapaBlueRoundSelected);

            txtIndMenuCapa.setVisibility(View.VISIBLE);
            ani_IndcMenuLateral.setAnimationListener(new Animation.AnimationListener() {                    // a la ultima animacion le creo evento para saber cuando termino y poner desaparece los botones
                public void onAnimationStart(Animation anim) {
                }

                ;

                public void onAnimationRepeat(Animation anim) {
                }

                ;

                public void onAnimationEnd(Animation anim)                                                // evento de terminacion de la animacion
                {
                    txtIndMenuCapa.setVisibility(View.GONE);                                   // desaparece el control donde estan todos los botones
                    ani_IndcMenuLateral.setAnimationListener(null);
                }

                ;
            });
            txtIndMenuCapa.startAnimation(ani_IndcMenuLateral);
        }
    }

    private void SelectedThirdMenuUp(int selectedIndex) {
        switch (selectedIndex) {
            case 0:
                btnPrevReutrn.startAnimation(ani_BtnSelected);
                returnOfPreview();
                break;
            case 1:
                btnPrevExitSketch.startAnimation(ani_BtnSelected);
                saveSketch();
                break;
            case 2:
                break;
        }
    }
    //endregion fin menu lateral

    private void SelectedCompassMenu(int selectedIndex) {
        switch (selectedIndex) {
            case 0:
                btnPrevReutrn.startAnimation(ani_BtnSelected);
                returnOfCompass();
                break;
            case 1:
                btnPrevExitSketch.startAnimation(ani_BtnSelected);
                saveCompass();
                break;
            case 2:
                break;
        }
    }

    private void SelectedSecondMenuUp(int selectedIndex) {
        switch (selectedIndex) {
            case 0:
                btnEditDelete.startAnimation(ani_BtnSelected);
                AlertDialog deleteAlert = new AlertDialog.Builder(getContext()).create();                   // Pregunto si esta seguro de Eliminar la imagen
                deleteAlert.setTitle("Eliminar");
                deleteAlert.setMessage("Seguro que decea eliminar el objeto seleccionado?");
                //deleteAlert.setIcon(R.drawable.delete);
                deleteAlert.setButton("Ok", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {                  // Si confirma la eliminacion
                        imgViewSelected.setBackgroundResource(0);                                   // Quito el bacgraund de seleccion del objeto a eliminar
                        switch (capaSelected)                                                        // Valido la capa actual
                        {
                            case 0:
                                pnl_CapaVias.removeView(arrCapaVias.get(imgIndexSelected));         // Elimino el ImageView del RrelativeLayout correspondiente a la capa de la imagen
                                arrCapaVias.remove(imgIndexSelected);                               // Elimino el objeto del arreglo de Objetos correspondiente a la capa de la imagen
                                break;
                            case 1:
                                pnl_CapaSenales.removeView(arrCapaSenales.get(imgIndexSelected));
                                arrCapaSenales.remove(imgIndexSelected);
                                break;
                            case 2:
                                pnl_CapaObjetos.removeView(arrCapaObjetos.get(imgIndexSelected));
                                arrCapaObjetos.remove(imgIndexSelected);
                                break;
                            case 3:
                                pnl_CapaVehiculos.removeView(arrCapaVehiculos.get(imgIndexSelected));
                                arrCapaVehiculos.remove(imgIndexSelected);
                                break;
                            case 4:
                                pnl_CapaDibujo.removeView(arrCapaDibujos.get(imgIndexSelected));
                                arrCapaDibujos.remove(imgIndexSelected);
                                break;
                        }
                        imgViewSelected = null;                                                     // se limpia la variable de ImageView de la imagen seleccionada
                        imgIndexSelected = -1;                                                      // se limpia la variable del index del objeto dela imagen seleccionada

                        editOnTouch4Capa(true);                                                     // Subscribo al evento de manipulacion a los objetos de la capa actualmente seleccionada
                        deselctedObject();                                                         // Acomodo el entorno de la pantalla principal, ocultando el panel de Edicion y mostrando el panel Principal
                    }
                });
                deleteAlert.setButton2("Cancelar", new DialogInterface.OnClickListener() {          // Cancela la eliminacion
                    @Override
                    public void onClick(DialogInterface dialog, int which) {           // vacio no hay que hacer nada solo regresar al la pantalla en que estaba

                    }
                });
                deleteAlert.show();                                                                 // Muestra el dialogo
                break;
            case 1:
                btnEditLock.startAnimation(ani_BtnSelected);
                break;
            case 2:

                CreateUndoRed(false);
                //if(mScaleStart == -1f && imgViewSelected.getScaleX() != 1)                          // Valido si se cambio la escala por primera vez
                //    mScaleStart = imgViewSelected.getScaleX();                                      // actualizo la variable para escalar por defecto a todas las imagenes al agragarce al lienzo
                imgViewSelected.setBackgroundResource(0);                                           // Quito el borde de seleccion a la imagen
                imgViewSelected = null;                                                             // Limpio el ImageView de la imagen que estaba seleccionada
                imgIndexSelected = -1;                                                              // Limpio el Index de la Imagen que estaba seleccionada

                editOnTouch4Capa(true);                                                             // Subscribo al evento de manipulacion a los objetos de la capa actualmente seleccionada
                deselctedObject();                                                                 // Acomodo el entorno de la pantalla principal, ocultando el panel de Edicion y mostrando el panel Principal
                break;
        }
    }

    private void SelectedSecondMenuDown(int selectedIndex) {
        switch (selectedIndex) {
            case 0:
                btnEditRotLeft.startAnimation(ani_BtnSelected);
                imgViewSelected.setRotation(imgViewSelected.getRotation() - 1);
                break;
            case 1:
                btnEditRotRigth.startAnimation(ani_BtnSelected);
                imgViewSelected.setRotation(imgViewSelected.getRotation() + 1);
                break;
            case 2:
                btnEditLeft.startAnimation(ani_BtnSelected);
                imgViewSelected.setTranslationX(imgViewSelected.getTranslationX() - 1);
                break;
            case 3:
                btnEditUp.startAnimation(ani_BtnSelected);
                imgViewSelected.setTranslationY(imgViewSelected.getTranslationY() - 1);
                break;
            case 4:
                btnEditDown.startAnimation(ani_BtnSelected);
                imgViewSelected.setTranslationY(imgViewSelected.getTranslationY() + 1);
                break;
            case 5:
                btnEditRigth.startAnimation(ani_BtnSelected);
                imgViewSelected.setTranslationX(imgViewSelected.getTranslationX() + 1);
                break;
            case 6: //menos
                btnEditLessZoom.startAnimation(ani_BtnSelected);
                imgViewSelected.setScaleX(imgViewSelected.getScaleX() * 0.991f);
                imgViewSelected.setScaleY(imgViewSelected.getScaleY() * 0.991f);
                break;
            case 7: // mas
                btnEditMoreZoom.startAnimation(ani_BtnSelected);
                imgViewSelected.setScaleX(imgViewSelected.getScaleX() * 1.009f);
                imgViewSelected.setScaleY(imgViewSelected.getScaleY() * 1.009f);
                break;
            case 8:
                btnEditFlipHor.startAnimation(ani_BtnSelected);
                imgViewSelected.setScaleY(imgViewSelected.getScaleY() * -1);
                break;
            case 9:
                btnEditFlipVert.startAnimation(ani_BtnSelected);
                imgViewSelected.setScaleX(imgViewSelected.getScaleX() * -1);
                break;
            case 10:
                btnEditUpCapa.startAnimation(ani_BtnSelected);
                break;
            case 11:
                btnEditDownCapa.startAnimation(ani_BtnSelected);
                break;
        }
    }

    private void addNewImage(int submenu, String nameImageToAdd, final PointF startPoint, final PointF EndPoint) {
        try {
            if (!nameImageToAdd.equals("")) {
                ImageView newImage = new ImageView(getContext());                                                   // se crea un ImagewView para contener la imagen que se desea agregar
                newImage.setScaleType(ImageView.ScaleType.CENTER);                                          // se configura el tipo de escalado de dicha imagen con repecto al ImageView
                newImage.setAdjustViewBounds(true);
                newImage.setDrawingCacheEnabled(true);
                newImage.setOnTouchListener(this);                                                          // se Subscribe la imagen al evento de manipulacion

                switch (capaSelected)                                                                       // Valido la capa seleccionada actualmente
                {
                    case 0:
                        newImage = getImageVias(submenu, nameImageToAdd);                    // Cargo la imagen correspondiente desde drawable
                        arrCapaVias.add(newImage);                                                          // Adiciono la imagen al arreglo de objetos correspondiente
                        imgIndexSelected = arrCapaVias.size() - 1;                                            // Actualizo la variable del index de donde quedo almacenada la imagen con respecto al arreglo
                        pnl_CapaVias.addView(arrCapaVias.get(imgIndexSelected));                            // Agrego el ImageView al RelativeLayoud correspondiente a la capa seleccionada
                        imgViewSelected = arrCapaVias.get(imgIndexSelected);                                // Actualizo la variable con el ImageView del objeto que se esta agregando
                        break;
                    case 1:
                        newImage = getImageSenales(submenu, nameImageToAdd);
                        arrCapaSenales.add(newImage);
                        imgIndexSelected = arrCapaSenales.size() - 1;
                        pnl_CapaSenales.addView(arrCapaSenales.get(imgIndexSelected));
                        imgViewSelected = arrCapaSenales.get(imgIndexSelected);
                        break;
                    case 2:
                        newImage = getImageObject(submenu, nameImageToAdd);
                        arrCapaObjetos.add(newImage);
                        imgIndexSelected = arrCapaObjetos.size() - 1;
                        pnl_CapaObjetos.addView(arrCapaObjetos.get(imgIndexSelected));
                        imgViewSelected = arrCapaObjetos.get(imgIndexSelected);
                        break;
                    case 3:
                        newImage = getImageVehiculos(submenu, nameImageToAdd);
                        arrCapaVehiculos.add(newImage);
                        imgIndexSelected = arrCapaVehiculos.size() - 1;
                        pnl_CapaVehiculos.addView(arrCapaVehiculos.get(imgIndexSelected));
                        imgViewSelected = arrCapaVehiculos.get(imgIndexSelected);
                        break;
                    case 4:
                        newImage = getImageDibujos(submenu, nameImageToAdd);
                        arrCapaDibujos.add(newImage);
                        imgIndexSelected = arrCapaDibujos.size() - 1;
                        pnl_CapaDibujo.addView(arrCapaDibujos.get(imgIndexSelected));
                        imgViewSelected = arrCapaDibujos.get(imgIndexSelected);
                        break;
                }

                imgViewSelected.getLayoutParams().width = (int) MyConvert.scaleValue(newImage.getDrawable().getIntrinsicWidth());                                         // le asigno el tamaño que trae la imagen en el ancho
                imgViewSelected.getLayoutParams().height = (int) MyConvert.scaleValue(newImage.getDrawable().getIntrinsicHeight());                                           // le asigno el tamaño que trae la imagen en el alto
                imgViewSelected.setBackgroundResource(R.drawable.selectedbackground);
                imgViewSelected.setBackgroundResource(0);
//                imgViewSelected.setScaleX(1 / (MyConvert.getScale() / 122));                                       // Ajusto a la nueva escala
//                imgViewSelected.setScaleY(1 / (MyConvert.getScale() / 122));

                if (startPoint == null) {
                    imgViewSelected.setTranslationX(((pnl_ContentCapas.getMeasuredWidth() / 2) -                  // Centro Coordenadas en X iniciales dodnde debe salir la imagen
                            (imgViewSelected.getLayoutParams().width / 2)) -                                     // Le quito el centro de la imagen
                            (pnl_ContentCapas.getTranslationX() / mScaleAll));                                               // Recalculapara poner siempre en el centro sin importar la escala ni el desplazamiento
                    imgViewSelected.setTranslationY(((pnl_ContentCapas.getMeasuredHeight() / 2) -                 // Centro Coordenadas en X iniciales dodnde debe salir la imagen
                            (imgViewSelected.getLayoutParams().height / 2)) -                                    // Le quito el centro de la imagen
                            (pnl_ContentCapas.getTranslationY() / mScaleAll));                                               // Recalculapara poner siempre en el centro sin importar la escala ni el desplazamiento

                    imgViewSelected.setBackgroundResource(R.drawable.selectedbackground);                       // se le agrega la iamgen de fondo de seleccion de objeto
                    editOnTouch4Capa(false);                                                                    // se le quita los eventos de manipulacion a los objetos de la capa seleccionada
                    selctedObject();
                    menuLateral.closeDrawer(GravityCompat.START);                                                       // animo y cierro el menulateral
                } else {
                    imgViewSelected.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
                        @Override
                        public void onGlobalLayout() {
                            PointF imageLocation = MyConvert.MtsToPixs(MyTrigonometry.getTwoCenterPoint(startPoint, startPoint, imgViewSelected.getHeight() / 2));

                            imgViewSelected.setTranslationX((imageLocation.x + MyConvert.getOffset().x));                                                                    // Recalculapara poner siempre en el centro sin importar la escala ni el desplazamiento
                            imgViewSelected.setTranslationY(MyConvert.getScreenResolution().y - ((imageLocation.y) + MyConvert.getOffset().y));

                            imgViewSelected.setPivotX(0);
                            imgViewSelected.setPivotY(imgViewSelected.getHeight());
                            double angle = MyTrigonometry.angleTwoVectors(startPoint, EndPoint);
                            imgViewSelected.setRotation((int) angle);

                            editOnTouch4Capa(true);                                                     // Subscribo al evento de manipulacion a los objetos de la capa actualmente seleccionada
                            deselctedObject();
                            if (Build.VERSION.SDK_INT < Build.VERSION_CODES.JELLY_BEAN) {
                                imgViewSelected.getViewTreeObserver().removeGlobalOnLayoutListener(this);
                            } else {
                                imgViewSelected.getViewTreeObserver().removeOnGlobalLayoutListener(this);
                            }
                        }
                    });
                }
            }
            CreateUndoRed(true);
        } catch (Exception ex) {
        }
    }

    private ImageView getImageVias(int submenu, String nameImageToAdd) {
        ImageView newImage = new ImageView(getContext());
        switch (submenu) {
            case 0: // Curvas
                switch (nameImageToAdd) {
                    case "1carril":
                        newImage.setImageResource(R.drawable.ml_vias_curva_90_1); //lateral_vias_curvas_90_1);                                    // Cargo la imagen en el ImageView
                        break;
                    case "2carriles":
                        newImage.setImageResource(R.drawable.ml_vias_curva_90_2);//lateral_vias_curvas_90_2);                                // Cargo la imagen en el ImageView
                        break;
                    case "3carriles":
                        newImage.setImageResource(R.drawable.ml_vias_curva_90_3);                                       // Cargo la imagen en el ImageView
                        break;
                    case "add1":
                        newImage.setImageResource(R.drawable.ml_vias_curva_90_1_union);                                       // Cargo la imagen en el ImageView
                        break;
                    case "adds":
                        newImage.setImageResource(R.drawable.ml_vias_curva_90_2_union);                                       // Cargo la imagen en el ImageView
                        break;

                    //________________________________________
                    case "carril1":
                        newImage.setImageResource(R.drawable.ml_vias_curva_cotra_1); //lateral_vias_curvas_90_1);                                    // Cargo la imagen en el ImageView
                        break;
                    case "carril2":
                        newImage.setImageResource(R.drawable.ml_vias_curva_contra_2); //lateral_vias_curvas_90_1);                                    // Cargo la imagen en el ImageView
                        break;
                    case "carril3":
                        newImage.setImageResource(R.drawable.ml_vias_curva_contra_3); //lateral_vias_curvas_90_1);                                    // Cargo la imagen en el ImageView
                        break;
                    case "carril4":
                        newImage.setImageResource(R.drawable.ml_vias_curva_contra_4); //lateral_vias_curvas_90_1);                                    // Cargo la imagen en el ImageView
                        break;
                    case "carril5":
                        newImage.setImageResource(R.drawable.ml_vias_curva_contra_5); //lateral_vias_curvas_90_1);                                    // Cargo la imagen en el ImageView
                        break;
                    case "carril6":
                        newImage.setImageResource(R.drawable.ml_vias_curva_cotra_6); //lateral_vias_curvas_90_1);                                    // Cargo la imagen en el ImageView
                        break;

                }
                break;
            case 1: // TramoVia
                switch (nameImageToAdd) {
                    case "1carril":
                        newImage.setImageResource(R.drawable.ml_vias_recta_1);                                    // Cargo la imagen en el ImageView
                        break;
                    case "2carriles":
                        newImage.setImageResource(R.drawable.ml_vias_recta_2);                                // Cargo la imagen en el ImageView
                        break;
                    case "3carriles":
                        newImage.setImageResource(R.drawable.ml_vias_recta_3union);                                       // Cargo la imagen en el ImageView
                        break;
                    case "4carriles":
                        newImage.setImageResource(R.drawable.ml_vias_recta_4);                                       // Cargo la imagen en el ImageView
                        break;
                    case "5carriles":
                        newImage.setImageResource(R.drawable.ml_vias_recta_5);                                       // Cargo la imagen en el ImageView
                        break;
                    case "6carriles":
                        newImage.setImageResource(R.drawable.ml_vias_recta_6);                                       // Cargo la imagen en el ImageView
                        break;
                    case "berma":
                        newImage.setImageResource(R.drawable.ml_vias_recta_berma);                                       // Cargo la imagen en el ImageView
                        break;
                    case "add1":
                        newImage.setImageResource(R.drawable.ml_vias_recta_1union);                                       // Cargo la imagen en el ImageView
                        break;
                    case "add2":
                        newImage.setImageResource(R.drawable.ml_vias_recta_2union);                                       // Cargo la imagen en el ImageView
                        break;


                }
                break;


            case 2: // Interseccion
                switch (nameImageToAdd) {
                    case "2x2_":
                        newImage.setImageResource(R.drawable.ml_vias_int_2x2_);                                    // Cargo la imagen en el ImageView
                        break;
                    case "2x4_":
                        newImage.setImageResource(R.drawable.ml_vias_int_4x2_);                                // Cargo la imagen en el ImageView
                        break;
                    case "4x4_":
                        newImage.setImageResource(R.drawable.ml_vias_int_4x409_);                                       // Cargo la imagen en el ImageView
                        break;
                    case "4X4_1":
                        newImage.setImageResource(R.drawable.ml_vias_int_4x412_);                                       // Cargo la imagen en el ImageView
                        break;
                    case "3X3":
                        newImage.setImageResource(R.drawable.ml_vias_int_3x3_);                                       // Cargo la imagen en el ImageView
                        break;
                    case "complemento1":
                        newImage.setImageResource(R.drawable.ml_vias_int_2de90);                                       // Cargo la imagen en el ImageView
                        break;
                    case "complemento2":
                        newImage.setImageResource(R.drawable.ml_vias_int_45);                                       // Cargo la imagen en el ImageView
                        break;
                    case "complemento3":
                        newImage.setImageResource(R.drawable.ml_vias_int_90);                                       // Cargo la imagen en el ImageView
                        break;


                }
                break;


            case 3: // Antibloqueo
                switch (nameImageToAdd) {
                    case "2x2":
                        newImage.setImageResource(R.drawable.ml_vias_int_2x2);                                    // Cargo la imagen en el ImageView
                        break;
                    case "2x4":
                        newImage.setImageResource(R.drawable.ml_vias_int_4x2);                                // Cargo la imagen en el ImageView
                        break;
                    case "4x4":
                        newImage.setImageResource(R.drawable.ml_vias_int_4x4);                                       // Cargo la imagen en el ImageView
                        break;
                    case "antibloqueo1":
                        newImage.setImageResource(R.drawable.ml_vias_int_antibloqueo1x1);                                       // Cargo la imagen en el ImageView
                        break;
                    case "antibloqueo2":
                        newImage.setImageResource(R.drawable.ml_vias_int_antibloqueo2x1);                                       // Cargo la imagen en el ImageView
                        break;
                    case "antibloqueo3":
                        newImage.setImageResource(R.drawable.ml_vias_int_antibloqueo2x1recto);                                       // Cargo la imagen en el ImageView
                        break;
                    case "antibloqueo4":
                        newImage.setImageResource(R.drawable.ml_vias_int_antibloqueo2x1lado);                                       // Cargo la imagen en el ImageView
                        break;
                    case "antibloqueo5":
                        newImage.setImageResource(R.drawable.ml_vias_int_antibloqueo2x1recto);                                       // Cargo la imagen en el ImageView
                        break;


                }
                break;

            case 4: // Lote/Predio
                switch (nameImageToAdd) {
                    case "Parqueadero":
                        newImage.setImageResource(R.drawable.ml_vias_predio_parqueo6);                                    // Cargo la imagen en el ImageView
                        break;
                    case "Asfalto":
                        newImage.setImageResource(R.drawable.ml_vias_predio_cemento);                                // Cargo la imagen en el ImageView
                        break;
                    case "Arena":
                        newImage.setImageResource(R.drawable.ml_vias_predio_arena);
                        break;
                    case "puertapredio":
                        newImage.setImageResource(R.drawable.ml_ob_fijos_muropuerta);
                        break;
                    case "paredpredio":
                        newImage.setImageResource(R.drawable.ml_ob_fijos_icon_muro);
                        break;

                }

            case 5: // Paso /Nivel
                switch (nameImageToAdd) {
                    case "Paso1":
                        newImage.setImageResource(R.drawable.ml_vias_tren_icon_pasonivel);                                    // Cargo la imagen en el ImageView
                        break;
                    case "Paso2":
                        newImage.setImageResource(R.drawable.ml_vias_tren_pasonivel2);                                // Cargo la imagen en el ImageView
                        break;
                    case "Paso2_2":
                        newImage.setImageResource(R.drawable.ml_vias_tren_pasonivel2barda);                                // Cargo la imagen en el ImageView
                        break;
                    case "Riel":
                        newImage.setImageResource(R.drawable.ml_vias_tren_1);                                // Cargo la imagen en el ImageView
                        break;
                    case "RielY":
                        newImage.setImageResource(R.drawable.ml_vias_tren_y);                                // Cargo la imagen en el ImageView
                        break;
                    case "Riel1":
                        newImage.setImageResource(R.drawable.ml_vias_tren_con_tren);                                // Cargo la imagen en el ImageView
                        break;
                    case "Riel2":
                        newImage.setImageResource(R.drawable.ml_vias_tren_barda_arriba);                                // Cargo la imagen en el ImageView
                        break;
                    case "Riel3":
                        newImage.setImageResource(R.drawable.ml_vias_tren_barda);                                // Cargo la imagen en el ImageView
                        break;
                    case "Riel4":
                        newImage.setImageResource(R.drawable.ml_vias_tren_puente);                                // Cargo la imagen en el ImageView
                        break;
                    case "Riel5":
                        newImage.setImageResource(R.drawable.ml_vias_tren_curva);                                // Cargo la imagen en el ImageView
                        break;
                    case "Riel6":
                        newImage.setImageResource(R.drawable.ml_vias_tren_c);                                // Cargo la imagen en el ImageView
                        break;
                    case "Riel7":
                        newImage.setImageResource(R.drawable.ml_vias_tren_fin);                                // Cargo la imagen en el ImageView
                        break;


                }


            case 6: // ciclo via

                switch (nameImageToAdd) {
                    case "ciclovia1":
                        newImage.setImageResource(R.drawable.ml_vias_cicloruta_13);                                    // Cargo la imagen en el ImageView
                        break;
                    case "ciclovia2":
                        newImage.setImageResource(R.drawable.ml_vias_cicloruta_14);                                // Cargo la imagen en el ImageView
                        break;
                    case "ciclocurva1":
                        newImage.setImageResource(R.drawable.ml_vias_cicloruta_15);                                    // Cargo la imagen en el ImageView
                        break;
                    case "ciclocurva2":
                        newImage.setImageResource(R.drawable.ml_vias_cicloruta_16);                                // Cargo la imagen en el ImageView
                        break;
                    case "ciclos1":
                        newImage.setImageResource(R.drawable.ml_vias_cicloruta_17);                                    // Cargo la imagen en el ImageView
                        break;
                    case "ciclos2":
                        newImage.setImageResource(R.drawable.ml_vias_cicloruta_18);                                // Cargo la imagen en el ImageView
                        break;
                    case "ciclocurvac1":
                        newImage.setImageResource(R.drawable.ml_vias_cicloruta_19);                                    // Cargo la imagen en el ImageView
                        break;
                    case "ciclocurvac2":
                        newImage.setImageResource(R.drawable.ml_vias_cicloruta_20);                                // Cargo la imagen en el ImageView
                        break;
                    case "cicloviay1":
                        newImage.setImageResource(R.drawable.ml_vias_cicloruta_21);                                    // Cargo la imagen en el ImageView
                        break;
                    case "cicloviay2":
                        newImage.setImageResource(R.drawable.ml_vias_cicloruta_22);                                // Cargo la imagen en el ImageView
                        break;
                    case "cicloviaa":
                        newImage.setImageResource(R.drawable.ml_vias_cicloruta_23);                                    // Cargo la imagen en el ImageView
                        break;
                    case "cicloviaa2":
                        newImage.setImageResource(R.drawable.ml_vias_cicloruta_24);                                // Cargo la imagen en el ImageView
                        break;

                }

/*if (arg22 == 0) {
                                    addNewImage(menuSecondSelected, "andenxl", null, null);
                                }
                                if (arg22 == 1) {
                                    addNewImage(menuSecondSelected, "andenxl2", null, null);
                                }
                                if (arg22 == 2) {
                                    addNewImage(menuSecondSelected, "curva", null, null);
                                }
                                if (arg22 == 3) {
                                    addNewImage(menuSecondSelected, "curva2", null, null);
                                }
                                if (arg22 == 4) {
                                    addNewImage(menuSecondSelected, "andenS", null, null);
                                }
                                if (arg22 == 5) {
                                    addNewImage(menuSecondSelected, "andenS2", null, null);
                                }


                                }*/
            case 7: // anden
                switch (nameImageToAdd) {
                    case "andenxl":
                        newImage.setImageResource(R.drawable.ml_vias_peatonal_anden);                                    // Cargo la imagen en el ImageView
                        break;
                    case "andenxl2":
                        newImage.setImageResource(R.drawable.ml_vias_peatonal_r_anden);                                // Cargo la imagen en el ImageView
                        break;
                    case "curva":
                        newImage.setImageResource(R.drawable.ml_vias_peatonal_c_gris);                                    // Cargo la imagen en el ImageView
                        break;
                    case "curva2":
                        newImage.setImageResource(R.drawable.ml_vias_peatonal_c_rojo);                                // Cargo la imagen en el ImageView
                        break;
                    case "andenS":
                        newImage.setImageResource(R.drawable.ml_vias_peatonal_s_gris);                                // Cargo la imagen en el ImageView
                        break;

                    case "andenS2":
                        newImage.setImageResource(R.drawable.ml_vias_peatonal_s_r);                                    // Cargo la imagen en el ImageView
                        break;

                    case "asfalto":
                        newImage.setImageResource(R.drawable.ml_vias_peatonal_icon_textura);                                    // Cargo la imagen en el ImageView
                        break;
                    case "adoquin":
                        newImage.setImageResource(R.drawable.ml_vias_peatonal_icon_r_textura);                                // Cargo la imagen en el ImageView
                        break;
                    case "piedra":
                        newImage.setImageResource(R.drawable.ml_vias_predio_icon_piedras);                                // Cargo la imagen en el ImageView
                        break;

                    case "anden":
                        newImage.setImageResource(R.drawable.ml_vias_peatonal_esquina_grande);                                    // Cargo la imagen en el ImageView
                        break;

                    case "Rampa1":
                        newImage.setImageResource(R.drawable.ml_vias_peatonal_rampa_carro);                                    // Cargo la imagen en el ImageView
                        break;
                    case "Rampa2":
                        newImage.setImageResource(R.drawable.ml_vias_peatonal_rampa_2carros);                                    // Cargo la imagen en el ImageView
                        break;
                    case "Rampa3":
                        newImage.setImageResource(R.drawable.ml_vias_peatonal_rampa_peatonal);                                    // Cargo la imagen en el ImageView
                        break;





                }

            case 8: // paso elevado
                switch (nameImageToAdd) {
                    case "puente1":
                        newImage.setImageResource(R.drawable.ml_vias_puente_1);                                    // Cargo la imagen en el ImageView
                        break;
                    case "puente2":
                        newImage.setImageResource(R.drawable.ml_vias_puente_2);                                // Cargo la imagen en el ImageView
                        break;
                    case "puente3":
                        newImage.setImageResource(R.drawable.ml_vias_puente_3);                           // Cargo la imagen en el ImageView
                        break;
                    case "puente4":
                        newImage.setImageResource(R.drawable.ml_vias_puente_4);                                    // Cargo la imagen en el ImageView
                        break;
                    case "puente5":
                        newImage.setImageResource(R.drawable.ml_vias_puente_lateral2);                                // Cargo la imagen en el ImageView
                        break;
                    case "puente6":
                        newImage.setImageResource(R.drawable.ml_vias_puente_lateral1);                           // Cargo la imagen en el ImageView
                        break;

                }
            case 9: // tunel
                switch (nameImageToAdd) {
                    case "tunel1":
                        newImage.setImageResource(R.drawable.ml_vias_tunel_entrada2);                                    // Cargo la imagen en el ImageView
                        break;
                    case "tunel2":
                        newImage.setImageResource(R.drawable.ml_vias_tunel_entrada4);                                // Cargo la imagen en el ImageView
                        break;
                    case "tunel3":
                        newImage.setImageResource(R.drawable.ml_vias_tunel_entrada2);                                    // Cargo la imagen en el ImageView
                        break;
                    case "tunel4":
                        newImage.setImageResource(R.drawable.ml_vias_tunel_entrada4);                                // Cargo la imagen en el ImageView
                        break;
                    case "tunel5":
                        newImage.setImageResource(R.drawable.ml_vias_tunel_completo2);                                // Cargo la imagen en el ImageView
                        break;
                    case "tunel6":
                        newImage.setImageResource(R.drawable.ml_vias_tunel_completo4);                                // Cargo la imagen en el ImageView
                        break;


                }

            case 10: // paso inferior
                switch (nameImageToAdd) {
                    case "inferior1":
                        newImage.setImageResource(R.drawable.ml_vias_paso_inferior_1);                                    // Cargo la imagen en el ImageView
                        break;
                    case "inferior2":
                        newImage.setImageResource(R.drawable.ml_vias_paso_inferior_2);                                // Cargo la imagen en el ImageView
                        break;
                    case "inferior3":
                        newImage.setImageResource(R.drawable.ml_vias_paso_inferior_4);                                // Cargo la imagen en el ImageView
                        break;
                    case "desague1":
                        newImage.setImageResource(R.drawable.ml_vias_cano_sombra);                                    // Cargo la imagen en el ImageView
                        break;
                    case "desague2":
                        newImage.setImageResource(R.drawable.ml_vias_cano_derecho);                                // Cargo la imagen en el ImageView
                        break;
                    case "desague3":
                        newImage.setImageResource(R.drawable.ml_vias_cano_izquierdo);                                // Cargo la imagen en el ImageView
                        break;

                }
            case 11: // paso inferior
                switch (nameImageToAdd) {
                    case "Separador1":
                        newImage.setImageResource(R.drawable.ml_vias_separador_2carriles);                                    // Cargo la imagen en el ImageView
                        break;
                    case "Separador2":
                        newImage.setImageResource(R.drawable.ml_vias_separador_2carriles_gris);                                // Cargo la imagen en el ImageView
                        break;
                    case "Separador3":
                        newImage.setImageResource(R.drawable.ml_vias_separador_angosto_gris);                                // Cargo la imagen en el ImageView
                        break;
                    case "Separador4":
                        newImage.setImageResource(R.drawable.ml_vias_separador_angosto_union);                                    // Cargo la imagen en el ImageView
                        break;
                    case "Separador5":
                        newImage.setImageResource(R.drawable.ml_vias_separador_cambio_carril);                                // Cargo la imagen en el ImageView
                        break;
                    case "Separador6":
                        newImage.setImageResource(R.drawable.ml_vias_separador_cambio_carril_gris_18);                                // Cargo la imagen en el ImageView
                        break;
                    case "Separador7":
                        newImage.setImageResource(R.drawable.ml_vias_separador_cambio_carril_gris_22);                                // Cargo la imagen en el ImageView
                        break;
                    case "Separador8":
                        newImage.setImageResource(R.drawable.ml_vias_separador_cambio_carril_union);                                // Cargo la imagen en el ImageView
                        break;
                    case "Separador9":
                        newImage.setImageResource(R.drawable.ml_vias_separador_cambio_carril_union_gris);                                // Cargo la imagen en el ImageView
                        break;
                    case "Separador10":
                        newImage.setImageResource(R.drawable.ml_vias_separador_rectangulo);                                // Cargo la imagen en el ImageView
                        break;
                    case "Separador11":
                        newImage.setImageResource(R.drawable.ml_vias_separador_rectangulo_gris);                                // Cargo la imagen en el ImageView
                        break;
                    case "Separador12":
                        newImage.setImageResource(R.drawable.ml_vias_separador_recuctotor_carril);                                // Cargo la imagen en el ImageView
                        break;
                    case "Separador13":
                        newImage.setImageResource(R.drawable.ml_vias_separador_redondeado_union);                                // Cargo la imagen en el ImageView
                        break;
                    case "Separador14":
                        newImage.setImageResource(R.drawable.ml_vias_separador_redondo);                                // Cargo la imagen en el ImageView
                        break;
                    case "Separador15":
                        newImage.setImageResource(R.drawable.ml_vias_separador_redondo_gris);                                // Cargo la imagen en el ImageView
                        break;
                    case "Separador16":
                        newImage.setImageResource(R.drawable.ml_vias_separador_redondo_union_gris);                                // Cargo la imagen en el ImageView
                        break;
                    case "Separador17":
                        newImage.setImageResource(R.drawable.ml_vias_separador_unionrectangulo);                                // Cargo la imagen en el ImageView
                        break;
                    case "Separador18":
                        newImage.setImageResource(R.drawable.ml_vias_separador_unionrectangulo_union_gris);                                // Cargo la imagen en el ImageView
                        break;
                    case "Separador19":
                        newImage.setImageResource(R.drawable.ml_vias_separador_y);                                // Cargo la imagen en el ImageView
                        break;
                    case "Separador20":
                        newImage.setImageResource(R.drawable.ml_vias_separador_y_gris);                                // Cargo la imagen en el ImageView
                        break;
                    case "Separador21":
                        newImage.setImageResource(R.drawable.ml_vias_separador_y_lado);                                // Cargo la imagen en el ImageView
                        break;
                    case "Separador22":
                        newImage.setImageResource(R.drawable.ml_vias_separador_y_lado_gris);                                // Cargo la imagen en el ImageView
                        break;

                }





        }
        return newImage;
    }

///////////////////// De aca quite lo de addNewMeasureInDraw

    private ImageView getImageSenales(int submenu, String nameImageToAdd) {
        ImageView newImage = new ImageView(getContext());
        switch (submenu) {
            case 0: // Señales Agentes y semaforos
                switch (nameImageToAdd) {
                    case "Agente":
                        newImage.setImageResource(R.drawable.ml_sn_agente_semaforo_agente_lat);                                    // Cargo la imagen en el ImageView
                        break;
                    case "Agente2":
                        newImage.setImageResource(R.drawable.ml_sn_agente_semaforo_agente_sup);                                    // Cargo la imagen en el ImageView
                        break;
                    case "Rojo":
                        newImage.setImageResource(R.drawable.lateral_senales_rojo);                                // Cargo la imagen en el ImageView
                        break;
                    case "Amarillo":
                        newImage.setImageResource(R.drawable.lateral_senales_amarillo);                                       // Cargo la imagen en el ImageView
                        break;
                    case "Verde":
                        newImage.setImageResource(R.drawable.lateral_senales_verde);                                       // Cargo la imagen en el ImageView
                        break;
                    case "Peaton_rojo":
                        newImage.setImageResource(R.drawable.ml_sn_agente_semaforo_peatonal_rojo);                                       // Cargo la imagen en el ImageView
                        break;
                    case "Peaton_verde":
                        newImage.setImageResource(R.drawable.ml_sn_agente_semaforo_peatonal_verde);                                       // Cargo la imagen en el ImageView
                        break;
                    case "Icono1":
                        newImage.setImageResource(R.drawable.ml_sn_agente_semaforo_simb_peaton2);                                       // Cargo la imagen en el ImageView
                        break;
                    case "Icono2":
                        newImage.setImageResource(R.drawable.ml_sn_agente_semaforo_simb_vehi2);                                       // Cargo la imagen en el ImageView
                        break;
                    case "Icono3":
                        newImage.setImageResource(R.drawable.ml_sn_agente_semaforo_simb_ambos);                                       // Cargo la imagen en el ImageView
                        break;
                    case "Icono4":
                        newImage.setImageResource(R.drawable.ml_sn_agente_semaforo_simb_3vehi_1peat);                                       // Cargo la imagen en el ImageView
                        break;
                    case "Icono5":
                        newImage.setImageResource(R.drawable.ml_sn_agente_semaforo_simb_2vehi_1peat);                                       // Cargo la imagen en el ImageView
                        break;
                    case "Icono6":
                        newImage.setImageResource(R.drawable.ml_sn_agente_semaforo_simb_2vehi);                                       // Cargo la imagen en el ImageView
                        break;
                }
                break;
            case 1: // Señales Preventivas
                switch (nameImageToAdd) {
                    case "DesmCarril":
                        newImage.setImageResource(R.drawable.lateral_senales_p1);                                    // Cargo la imagen en el ImageView
                        break;
                    case "ArregloVia":
                        newImage.setImageResource(R.drawable.lateral_senales_p3);                                // Cargo la imagen en el ImageView
                        break;
                    case "Rotonda":
                        newImage.setImageResource(R.drawable.lateral_senales_p5);                                       // Cargo la imagen en el ImageView
                        break;
                    case "DobleSentido":
                        newImage.setImageResource(R.drawable.lateral_senales_p6);                                       // Cargo la imagen en el ImageView
                        break;
                    case "Cntracurva":
                        newImage.setImageResource(R.drawable.lateral_senales_p7);                                       // Cargo la imagen en el ImageView
                        break;
                    case "Resalto":
                        newImage.setImageResource(R.drawable.lateral_senales_p8);                                    // Cargo la imagen en el ImageView
                        break;
                    case "ViaT":
                        newImage.setImageResource(R.drawable.lateral_senales_p9);                                // Cargo la imagen en el ImageView
                        break;
                    case "CurvaDerecha":
                        newImage.setImageResource(R.drawable.lateral_senales_p10);                                       // Cargo la imagen en el ImageView
                        break;
                    case "ViaTren":
                        newImage.setImageResource(R.drawable.lateral_senales_p2);                                       // Cargo la imagen en el ImageView
                        break;
                    //__________________________
                    case "Preventiva1":
                        newImage.setImageResource(R.drawable.ml_sn_prev_altura_libre);                                    // Cargo la imagen en el ImageView
                        break;
                    case "Preventiva2":
                        newImage.setImageResource(R.drawable.ml_sn_prev_ancho_libre);                                // Cargo la imagen en el ImageView
                        break;
                    case "Preventiva3":
                        newImage.setImageResource(R.drawable.ml_sn_prev_animales);                                       // Cargo la imagen en el ImageView
                        break;

                    case "Preventiva5":
                        newImage.setImageResource(R.drawable.ml_sn_prev_barrera);                                       // Cargo la imagen en el ImageView
                        break;
                    case "Preventiva6":
                        newImage.setImageResource(R.drawable.ml_sn_prev_bifur_der);                                    // Cargo la imagen en el ImageView
                        break;
                    case "Preventiva7":
                        newImage.setImageResource(R.drawable.ml_sn_prev_bifur_izq);                                // Cargo la imagen en el ImageView
                        break;
                    case "Preventiva8":
                        newImage.setImageResource(R.drawable.ml_sn_prev_bufrucacion_t);                                       // Cargo la imagen en el ImageView
                        break;
                    case "Preventiva9":
                        newImage.setImageResource(R.drawable.ml_sn_prev_cemaforo);                                       // Cargo la imagen en el ImageView
                        break;
                    case "Preventiva10":
                        newImage.setImageResource(R.drawable.ml_sn_prev_choque);                                       // Cargo la imagen en el ImageView
                        break;


                    case "Preventiva11":
                        newImage.setImageResource(R.drawable.ml_sn_prev_clclistas);                                    // Cargo la imagen en el ImageView
                        break;
                    case "Preventiva12":
                        newImage.setImageResource(R.drawable.ml_sn_prev_curva_peligrosa_iz);                                // Cargo la imagen en el ImageView
                        break;
                    case "Preventiva13":
                        newImage.setImageResource(R.drawable.ml_sn_prev_curva_pron);                                       // Cargo la imagen en el ImageView
                        break;
                    case "Preventiva14":
                        newImage.setImageResource(R.drawable.ml_sn_prev_curva_pron_der);                                       // Cargo la imagen en el ImageView
                        break;
                    case "Preventiva15":
                        newImage.setImageResource(R.drawable.ml_sn_prev_curvaycontra);                                       // Cargo la imagen en el ImageView
                        break;
                    case "Preventiva16":
                        newImage.setImageResource(R.drawable.ml_sn_prev_deportiva);                                    // Cargo la imagen en el ImageView
                        break;
                    case "Preventiva17":
                        newImage.setImageResource(R.drawable.ml_sn_prev_depresion);                                // Cargo la imagen en el ImageView
                        break;
                    case "Preventiva18":
                        newImage.setImageResource(R.drawable.ml_sn_prev_derrumbes);                                       // Cargo la imagen en el ImageView
                        break;
                    case "Preventiva19":
                        newImage.setImageResource(R.drawable.ml_sn_prev_descenso_peli);                                       // Cargo la imagen en el ImageView
                        break;
                    case "Preventiva20":
                        newImage.setImageResource(R.drawable.ml_sn_prev_deslizante);                                       // Cargo la imagen en el ImageView
                        break;


                    case "Preventiva21":
                        newImage.setImageResource(R.drawable.ml_sn_prev_direccin);

                        break;
                    case "Preventiva22":
                        newImage.setImageResource(R.drawable.ml_sn_prev_ensancha_asi2);                                // Cargo la imagen en el ImageView
                        break;
                    case "Preventiva23":
                        newImage.setImageResource(R.drawable.ml_sn_prev_ensancha_asim);                                       // Cargo la imagen en el ImageView
                        break;
                    case "Preventiva24":
                        newImage.setImageResource(R.drawable.ml_sn_prev_ensanchamiento);                                       // Cargo la imagen en el ImageView
                        break;
                    case "Preventiva25":
                        newImage.setImageResource(R.drawable.ml_sn_prev_fin_pavimento);                                       // Cargo la imagen en el ImageView
                        break;
                    case "Preventiva26":
                        newImage.setImageResource(R.drawable.ml_sn_prev_incorporacion);                                    // Cargo la imagen en el ImageView
                        break;
                    case "Preventiva27":
                        newImage.setImageResource(R.drawable.ml_sn_prev_incorporacion_der);                                // Cargo la imagen en el ImageView
                        break;
                    case "Preventiva28":
                        newImage.setImageResource(R.drawable.ml_sn_prev_interseccion);                                       // Cargo la imagen en el ImageView
                        break;
                    /*case "Preventiva29":
                        newImage.setImageResource(R.drawable.ml_sn_prev_maquinaria);                                       // Cargo la imagen en el ImageView
                        break;*/
                    case "Preventiva30":
                        newImage.setImageResource(R.drawable.ml_sn_prev_maquinaria_agricola);                                       // Cargo la imagen en el ImageView
                        break;

                    case "Preventiva31":
                        newImage.setImageResource(R.drawable.ml_sn_prev_paso_nivel);
                        break;
                    case "Preventiva32":
                        newImage.setImageResource(R.drawable.ml_sn_prev_pasonivel_tren);                                // Cargo la imagen en el ImageView
                        break;
                    case "Preventiva33":
                        newImage.setImageResource(R.drawable.ml_sn_prev_peatones);                                       // Cargo la imagen en el ImageView
                        break;
                    case "Preventiva34":
                        newImage.setImageResource(R.drawable.ml_sn_prev_peso_max);                                       // Cargo la imagen en el ImageView
                        break;
                    case "Preventiva35":
                        newImage.setImageResource(R.drawable.ml_sn_prev_prev_ceda_paso);                                       // Cargo la imagen en el ImageView
                        break;
                    case "Preventiva36":
                        newImage.setImageResource(R.drawable.ml_sn_prev_puente);                                    // Cargo la imagen en el ImageView
                        break;
                    case "Preventiva37":
                        newImage.setImageResource(R.drawable.ml_sn_prev_reduccion);                                // Cargo la imagen en el ImageView
                        break;
                    case "Preventiva38":
                        newImage.setImageResource(R.drawable.ml_sn_prev_reduccion_asi_2);                                       // Cargo la imagen en el ImageView
                        break;
                    case "Preventiva39":
                        newImage.setImageResource(R.drawable.ml_sn_prev_reduccion_asime);                                       // Cargo la imagen en el ImageView
                        break;
                    case "Preventiva40":
                        newImage.setImageResource(R.drawable.ml_sn_prev_separador);                                       // Cargo la imagen en el ImageView
                        break;



                    case "Preventiva41":
                        newImage.setImageResource(R.drawable.ml_sn_prev_separador2sentidos);                                    // Cargo la imagen en el ImageView
                        break;
                    case "Preventiva42":
                        newImage.setImageResource(R.drawable.ml_sn_prev_sup_rizada);                                // Cargo la imagen en el ImageView
                        break;
                    case "Preventiva43":
                        newImage.setImageResource(R.drawable.ml_sn_prev_terminacion_separador);                                       // Cargo la imagen en el ImageView
                        break;
                    case "Preventiva44":
                        newImage.setImageResource(R.drawable.ml_sn_prev_treminacion_separador2sentidos);                                       // Cargo la imagen en el ImageView
                        break;
                    case "Preventiva45":
                        newImage.setImageResource(R.drawable.ml_sn_prev_tres_carriles);                                       // Cargo la imagen en el ImageView
                        break;
                    case "Preventiva46":
                        newImage.setImageResource(R.drawable.ml_sn_prev_tunel);                                    // Cargo la imagen en el ImageView
                        break;
                    case "Preventiva47":
                        newImage.setImageResource(R.drawable.ml_sn_prev_via_lat_izq);                                // Cargo la imagen en el ImageView
                        break;
                    case "Preventiva48":
                        newImage.setImageResource(R.drawable.ml_sn_prev_y);                                       // Cargo la imagen en el ImageView
                        break;
                    case "Preventiva49":
                        newImage.setImageResource(R.drawable.ml_sn_prev_zona_escolar);                                       // Cargo la imagen en el ImageView
                        break;


                }
                break;
            case 2: // Señales Reglamentarias
                switch (nameImageToAdd) {
                    case "Pare":
                        newImage.setImageResource(R.drawable.lateral_senales_r6);                                    // Cargo la imagen en el ImageView
                        break;
                    case "NoPase":
                        newImage.setImageResource(R.drawable.lateral_senales_r1);                                // Cargo la imagen en el ImageView
                        break;
                    case "Cedaelpaso":
                        newImage.setImageResource(R.drawable.lateral_senales_r2);                                       // Cargo la imagen en el ImageView
                        break;
                    case "CuceDerecha":
                        newImage.setImageResource(R.drawable.lateral_senales_r3);                                       // Cargo la imagen en el ImageView
                        break;
                    case "SigaDerecho":
                        newImage.setImageResource(R.drawable.lateral_senales_r4);                                       // Cargo la imagen en el ImageView
                        break;
                    case "ProhibGirar":
                        newImage.setImageResource(R.drawable.lateral_senales_r5);                                    // Cargo la imagen en el ImageView
                        break;
                    case "NoEstacionar":
                        newImage.setImageResource(R.drawable.lateral_senales_r8);                                // Cargo la imagen en el ImageView
                        break;
                    case "NoCambioCarril":
                        newImage.setImageResource(R.drawable.lateral_senales_r9);                                       // Cargo la imagen en el ImageView
                        break;
                    case "ViDobleSentido":
                        newImage.setImageResource(R.drawable.lateral_senales_r7);                                       // Cargo la imagen en el ImageView
                        break;
                    /*___________________________________________________________*/
                    case "Reglamentaria1":
                        newImage.setImageResource(R.drawable.reglametaria46);                                       // Cargo la imagen en el ImageView
                        break;
                    case "Reglamentaria2":
                        newImage.setImageResource(R.drawable.asitencia);                                       // Cargo la imagen en el ImageView
                        break;
                    case "Reglamentaria3":
                        newImage.setImageResource(R.drawable.peaton);                                       // Cargo la imagen en el ImageView
                        break;
                    case "Reglamentaria4":
                        newImage.setImageResource(R.drawable.sinttulo_2);                                       // Cargo la imagen en el ImageView
                        break;
                    case "Reglamentaria5":
                        newImage.setImageResource(R.drawable.reglamentaria);                                       // Cargo la imagen en el ImageView
                        break;
                    case "Reglamentaria6":
                        newImage.setImageResource(R.drawable.reglamentaria4);                                       // Cargo la imagen en el ImageView
                        break;
                    case "Reglamentaria7":
                        newImage.setImageResource(R.drawable.reglamentaria5);                                       // Cargo la imagen en el ImageView
                        break;
                    case "Reglamentaria8":
                        newImage.setImageResource(R.drawable.reglamentaria6);                                       // Cargo la imagen en el ImageView
                        break;
                    case "Reglamentaria9":
                        newImage.setImageResource(R.drawable.reglamentaria7);                                       // Cargo la imagen en el ImageView
                        break;
                    case "Reglamentaria10":
                        newImage.setImageResource(R.drawable.reglamentaria8);                                       // Cargo la imagen en el ImageView
                        break;


                    case "Reglamentaria11":
                        newImage.setImageResource(R.drawable.reglamentaria9);                                       // Cargo la imagen en el ImageView
                        break;
                    case "Reglamentaria12":
                        newImage.setImageResource(R.drawable.reglamentaria10);                                       // Cargo la imagen en el ImageView
                        break;
                    case "Reglamentaria13":
                        newImage.setImageResource(R.drawable.reglamentaria11);                                       // Cargo la imagen en el ImageView
                        break;
                    case "Reglamentaria14":
                        newImage.setImageResource(R.drawable.reglamentaria12);                                       // Cargo la imagen en el ImageView
                        break;
                    case "Reglamentaria15":
                        newImage.setImageResource(R.drawable.reglamentaria13);                                       // Cargo la imagen en el ImageView
                        break;
                    case "Reglamentaria16":
                        newImage.setImageResource(R.drawable.reglamentaria14);                                       // Cargo la imagen en el ImageView
                        break;
                    case "Reglamentaria17":
                        newImage.setImageResource(R.drawable.reglamentaria15);                                       // Cargo la imagen en el ImageView
                        break;
                    case "Reglamentaria18":
                        newImage.setImageResource(R.drawable.reglamentaria16);                                       // Cargo la imagen en el ImageView
                        break;
                    case "Reglamentaria19":
                        newImage.setImageResource(R.drawable.reglamentaria17);                                       // Cargo la imagen en el ImageView
                        break;
                    case "Reglamentaria20":
                        newImage.setImageResource(R.drawable.reglamentaria18);                                       // Cargo la imagen en el ImageView
                        break;


                    case "Reglamentaria21":
                        newImage.setImageResource(R.drawable.reglametaria19);                                       // Cargo la imagen en el ImageView
                        break;
                    case "Reglamentaria22":
                        newImage.setImageResource(R.drawable.reglamentaria20);                                       // Cargo la imagen en el ImageView
                        break;
                    case "Reglamentaria23":
                        newImage.setImageResource(R.drawable.reglametaria21);                                       // Cargo la imagen en el ImageView
                        break;
                    case "Reglamentaria24":
                        newImage.setImageResource(R.drawable.reglametaria22);                                       // Cargo la imagen en el ImageView
                        break;
                    case "Reglamentaria25":
                        newImage.setImageResource(R.drawable.reglametaria24);                                       // Cargo la imagen en el ImageView
                        break;
                    case "Reglamentaria26":
                        newImage.setImageResource(R.drawable.reglametaria25);                                       // Cargo la imagen en el ImageView
                        break;
                    case "Reglamentaria27":
                        newImage.setImageResource(R.drawable.reglametaria27);                                       // Cargo la imagen en el ImageView
                        break;
                    case "Reglamentaria28":
                        newImage.setImageResource(R.drawable.reglametaria28);                                       // Cargo la imagen en el ImageView
                        break;
                    case "Reglamentaria29":
                        newImage.setImageResource(R.drawable.reglametaria30);                                       // Cargo la imagen en el ImageView
                        break;
                    case "Reglamentaria30":
                        newImage.setImageResource(R.drawable.reglametaria31);                                       // Cargo la imagen en el ImageView
                        break;



                    case "Reglamentaria31":
                        newImage.setImageResource(R.drawable.reglametaria33);                                       // Cargo la imagen en el ImageView
                        break;
                    case "Reglamentaria32":
                        newImage.setImageResource(R.drawable.reglametaria34);                                       // Cargo la imagen en el ImageView
                        break;
                    case "Reglamentaria33":
                        newImage.setImageResource(R.drawable.reglametaria36);                                       // Cargo la imagen en el ImageView
                        break;
                    case "Reglamentaria34":
                        newImage.setImageResource(R.drawable.reglametaria37);                                       // Cargo la imagen en el ImageView
                        break;
                    case "Reglamentaria35":
                        newImage.setImageResource(R.drawable.reglametaria38);                                       // Cargo la imagen en el ImageView
                        break;
                    case "Reglamentaria36":
                        newImage.setImageResource(R.drawable.reglametaria41);                                       // Cargo la imagen en el ImageView
                        break;
                    case "Reglamentaria37":
                        newImage.setImageResource(R.drawable.reglametaria43);                                       // Cargo la imagen en el ImageView
                        break;
                    case "Reglamentaria38":
                        newImage.setImageResource(R.drawable.reglametaria44);                                       // Cargo la imagen en el ImageView
                        break;
                    case "Reglamentaria39":
                        newImage.setImageResource(R.drawable.reglametaria45);                                       // Cargo la imagen en el ImageView
                        break;




                }
                break;
            case 3: // Señales Informativas
                switch (nameImageToAdd) {
                    case "Restaurante":
                        newImage.setImageResource(R.drawable.lateral_senales_i1);                                    // Cargo la imagen en el ImageView
                        break;
                    case "Castillo":
                        newImage.setImageResource(R.drawable.lateral_senales_i3);                                // Cargo la imagen en el ImageView
                        break;
                    case "Gasolinera":
                        newImage.setImageResource(R.drawable.lateral_senales_i4);                                       // Cargo la imagen en el ImageView
                        break;
                    case "Parqueaderos":
                        newImage.setImageResource(R.drawable.lateral_senales_i5);                                       // Cargo la imagen en el ImageView
                        break;
                    case "CrusRoja":
                        newImage.setImageResource(R.drawable.lateral_senales_i6);                                       // Cargo la imagen en el ImageView
                        break;
                    case "Taxis":
                        newImage.setImageResource(R.drawable.lateral_senales_i7);                                    // Cargo la imagen en el ImageView
                        break;
                    case "varios":
                        newImage.setImageResource(R.drawable.lateral_senales_i9);                                // Cargo la imagen en el ImageView
                        break;
                    case "Milla":
                        newImage.setImageResource(R.drawable.lateral_senales_i10);                                       // Cargo la imagen en el ImageView
                        break;
                    /*_____________________________________________________________*/
                 //Informativa53
                    case "Informativa1":
                        newImage.setImageResource(R.drawable.ml_sn_info_aeropuerto);                                       // Cargo la imagen en el ImageView
                        break;
                    case "Informativa2":
                        newImage.setImageResource(R.drawable.ml_sn_info_alquiler);                                       // Cargo la imagen en el ImageView
                        break;
                    case "Informativa3":
                        newImage.setImageResource(R.drawable.ml_sn_info_arqueologia);                                       // Cargo la imagen en el ImageView
                        break;
                    case "Informativa4":
                        newImage.setImageResource(R.drawable.ml_sn_info_artesanias);                                       // Cargo la imagen en el ImageView
                        break;
                    case "Informativa5":
                        newImage.setImageResource(R.drawable.ml_sn_info_atractivo_natural);                                       // Cargo la imagen en el ImageView
                        break;
                    case "Informativa6":
                        newImage.setImageResource(R.drawable.ml_sn_info_banco);                                       // Cargo la imagen en el ImageView
                        break;
                    case "Informativa7":
                        newImage.setImageResource(R.drawable.ml_sn_info_camping);                                       // Cargo la imagen en el ImageView
                        break;
                    case "Informativa8":
                        newImage.setImageResource(R.drawable.ml_sn_info_cascada);                                       // Cargo la imagen en el ImageView
                        break;
                    case "Informativa9":
                        newImage.setImageResource(R.drawable.ml_sn_info_confirmativa_destino);                                       // Cargo la imagen en el ImageView
                        break;
                    case "Informativa10":
                        newImage.setImageResource(R.drawable.ml_sn_info_croquis);                                       // Cargo la imagen en el ImageView
                        break;


                    case "Informativa11":
                        newImage.setImageResource(R.drawable.ml_sn_info_cruce_peatonal);                                       // Cargo la imagen en el ImageView
                        break;
                    case "Informativa12":
                        newImage.setImageResource(R.drawable.ml_sn_info_departamental);                                       // Cargo la imagen en el ImageView
                        break;
                    case "Informativa13":
                        newImage.setImageResource(R.drawable.ml_sn_info_descripcion);                                       // Cargo la imagen en el ImageView
                        break;
                    case "Informativa14":
                        newImage.setImageResource(R.drawable.ml_sn_info_desicion_destino);                                       // Cargo la imagen en el ImageView
                        break;
                    case "Informativa15":
                        newImage.setImageResource(R.drawable.ml_sn_info_discapacitados);                                       // Cargo la imagen en el ImageView
                        break;
                    case "Informativa16":
                        newImage.setImageResource(R.drawable.ml_sn_info_estacio_servicio);                                       // Cargo la imagen en el ImageView
                        break;
                    case "Informativa17":
                        newImage.setImageResource(R.drawable.ml_sn_info_estacionamiento_taxi);                                       // Cargo la imagen en el ImageView
                        break;
                    case "Informativa18":
                        newImage.setImageResource(R.drawable.ml_sn_info_estructura);                                       // Cargo la imagen en el ImageView
                        break;
                    case "Informativa19":
                        newImage.setImageResource(R.drawable.ml_sn_info_geografica);                                       // Cargo la imagen en el ImageView
                        break;
                    case "Informativa20":
                        newImage.setImageResource(R.drawable.ml_sn_info_hospedaje);                                       // Cargo la imagen en el ImageView
                        break;


                    case "Informativa21":
                        newImage.setImageResource(R.drawable.ml_sn_info_iglesia);                                       // Cargo la imagen en el ImageView
                        break;
                    case "Informativa22":
                        newImage.setImageResource(R.drawable.ml_sn_info_info);                                       // Cargo la imagen en el ImageView
                        break;
                    case "Informativa23":
                        newImage.setImageResource(R.drawable.ml_sn_info_info_destino);                                       // Cargo la imagen en el ImageView
                        break;
                    case "Informativa24":
                        newImage.setImageResource(R.drawable.ml_sn_info_lago);                                       // Cargo la imagen en el ImageView
                        break;
                    case "Informativa25":
                        newImage.setImageResource(R.drawable.ml_sn_info_marginal_selva);                                       // Cargo la imagen en el ImageView
                        break;
                    case "Informativa26":
                        newImage.setImageResource(R.drawable.ml_sn_info_militar);                                       // Cargo la imagen en el ImageView
                        break;
                    case "Informativa27":
                        newImage.setImageResource(R.drawable.ml_sn_info_mirador);                                       // Cargo la imagen en el ImageView
                        break;
                    case "Informativa28":
                        newImage.setImageResource(R.drawable.ml_sn_info_montallantas);                                       // Cargo la imagen en el ImageView
                        break;
                    case "Informativa29":
                        newImage.setImageResource(R.drawable.ml_sn_info_monumento);                                       // Cargo la imagen en el ImageView
                        break;
                    case "Informativa30":
                        newImage.setImageResource(R.drawable.ml_sn_info_muelle);                                       // Cargo la imagen en el ImageView
                        break;


                    case "Informativa31":
                        newImage.setImageResource(R.drawable.ml_sn_info_museo);                                       // Cargo la imagen en el ImageView
                        break;
                    case "Informativa32":
                        newImage.setImageResource(R.drawable.ml_sn_info_nevado);                                       // Cargo la imagen en el ImageView
                        break;
                    case "Informativa33":
                        newImage.setImageResource(R.drawable.ml_sn_info_nomenclatura);                                       // Cargo la imagen en el ImageView
                        break;
                    case "Informativa34":
                        newImage.setImageResource(R.drawable.ml_sn_info_panamericana);                                       // Cargo la imagen en el ImageView
                        break;
                    case "Informativa35":
                        newImage.setImageResource(R.drawable.ml_sn_info_parqueo_buses);                                       // Cargo la imagen en el ImageView
                        break;
                    case "Informativa36":
                        newImage.setImageResource(R.drawable.ml_sn_info_pesca);                                       // Cargo la imagen en el ImageView
                        break;
                    case "Informativa37":
                        newImage.setImageResource(R.drawable.ml_sn_info_playa);                                       // Cargo la imagen en el ImageView
                        break;
                    case "Informativa38":
                        newImage.setImageResource(R.drawable.lateral_senales_i10);                                       // Cargo la imagen en el ImageView
                        break;
                    case "Informativa39":
                        newImage.setImageResource(R.drawable.ml_sn_info_postereferencia);                                       // Cargo la imagen en el ImageView
                        break;

                    case "Informativa40":
                        newImage.setImageResource(R.drawable.ml_sn_info_poste_doble);                                       // Cargo la imagen en el ImageView
                        break;



                    case "Informativa41":
                        newImage.setImageResource(R.drawable.ml_sn_info_poste_sencillo);                                       // Cargo la imagen en el ImageView
                        break;
                    case "Informativa42":
                        newImage.setImageResource(R.drawable.ml_sn_info_recreativo);                                       // Cargo la imagen en el ImageView
                        break;
                    case "Informativa43":
                        newImage.setImageResource(R.drawable.ml_sn_info_restaurante);                                       // Cargo la imagen en el ImageView
                        break;
                    case "Informativa44":
                        newImage.setImageResource(R.drawable.ml_sn_info_ruta_nacional);                                       // Cargo la imagen en el ImageView
                        break;
                    case "Informativa45":
                        newImage.setImageResource(R.drawable.ml_sn_info_seguridad_vial);                                       // Cargo la imagen en el ImageView
                        break;
                    case "Informativa46":
                        newImage.setImageResource(R.drawable.ml_sn_info_servicio_sanitario);                                       // Cargo la imagen en el ImageView
                        break;
                    case "Informativa47":
                        newImage.setImageResource(R.drawable.ml_sn_info_sitio_parqueo);                                       // Cargo la imagen en el ImageView
                        break;
                    case "Informativa48":
                        newImage.setImageResource(R.drawable.ml_sn_info_taller);                                       // Cargo la imagen en el ImageView
                        break;
                    case "Informativa49":
                        newImage.setImageResource(R.drawable.ml_sn_info_telefono);                                       // Cargo la imagen en el ImageView
                        break;
                    case "Informativa50":
                        newImage.setImageResource(R.drawable.ml_sn_info_termal);                                       // Cargo la imagen en el ImageView
                        break;





                    case "Informativa51":
                        newImage.setImageResource(R.drawable.ml_sn_info_transbordador);                                       // Cargo la imagen en el ImageView
                        break;
                    case "Informativa52":
                        newImage.setImageResource(R.drawable.ml_sn_info_tren);                                       // Cargo la imagen en el ImageView
                        break;
                    case "Informativa53":
                        newImage.setImageResource(R.drawable.ml_sn_info_via_ciclas);                                       // Cargo la imagen en el ImageView
                        break;
                    case "Informativa54":
                        newImage.setImageResource(R.drawable.ml_sn_info_volcan);                                       // Cargo la imagen en el ImageView
                        break;
                    case "Informativa55":
                        newImage.setImageResource(R.drawable.ml_sn_info_zologico);                                       // Cargo la imagen en el ImageView
                        break;
                    case "Informativa56":
                        newImage.setImageResource(R.drawable.ml_sn_info_zona_especial);                                       // Cargo la imagen en el ImageView
                        break;


                }
                break;
            case 4: // Señales Temporales
                switch (nameImageToAdd) {
                    case "Desvio":
                        newImage.setImageResource(R.drawable.lateral_senales_barricada);                                    // Cargo la imagen en el ImageView
                        break;
                    case "Cinta":
                        newImage.setImageResource(R.drawable.lateral_senales_tubularcinta);                                    // Cargo la imagen en el ImageView
                        break;
                    case "Pin":
                        newImage.setImageResource(R.drawable.lateral_senales_tubular);                                    // Cargo la imagen en el ImageView
                        break;

                    case "Temporal1":
                        newImage.setImageResource(R.drawable.ml_sn_info_carril_cerrado);                                    // Cargo la imagen en el ImageView
                        break;
                    case "Temporal2":
                        newImage.setImageResource(R.drawable.ml_sn_info_desvio);                                    // Cargo la imagen en el ImageView
                        break;
                    case "Temporal3":
                        newImage.setImageResource(R.drawable.ml_sn_info_fin_obra);                                    // Cargo la imagen en el ImageView
                        break;
                    case "Temporal4":
                        newImage.setImageResource(R.drawable.ml_sn_info_inicio_obra);                                    // Cargo la imagen en el ImageView
                        break;
                    case "Temporal5":
                        newImage.setImageResource(R.drawable.ml_sn_info_obra_via);                                    // Cargo la imagen en el ImageView
                        break;
                    case "Temporal6":
                        newImage.setImageResource(R.drawable.ml_sn_prev_banderero);                                    // Cargo la imagen en el ImageView
                        break;
                    case "Temporal7":
                        newImage.setImageResource(R.drawable.ml_sn_prev_maquinaria);                                    // Cargo la imagen en el ImageView
                        break;
                }
                break;
            case 5: // Señales de Piso
                switch (nameImageToAdd) {
                    case "Sebra":
                        newImage.setImageResource(R.drawable.ml_sn_piso_cebrae);                                    // Cargo la imagen en el ImageView
                        break;
                    case "Sebra2":
                        newImage.setImageResource(R.drawable.ml_sn_piso_cebra2carrilese);                                    // Cargo la imagen en el ImageView
                        break;
                    case "Sebra3":
                        newImage.setImageResource(R.drawable.ml_sn_piso_cebra3carrilese);                                    // Cargo la imagen en el ImageView
                        break;
                    case "Sebra_":
                        newImage.setImageResource(R.drawable.ml_sn_piso_cebra_linea_paree);                                    // Cargo la imagen en el ImageView
                        break;
                    case "Sebra_2":
                        newImage.setImageResource(R.drawable.ml_sn_piso_cebra_linea_pare2carrilese);                                    // Cargo la imagen en el ImageView
                        break;
                    case "Sebra_3":
                        newImage.setImageResource(R.drawable.ml_sn_piso_cebra_linea_pare3carrilese);                                    // Cargo la imagen en el ImageView
                        break;
                    case "Pare":
                        newImage.setImageResource(R.drawable.lateral_senales_h1);                                    // Cargo la imagen en el ImageView
                        break;
                    case "ProhibidoParquear":
                        newImage.setImageResource(R.drawable.lateral_senales_h2);                                // Cargo la imagen en el ImageView
                        break;
                    case "Bifurcacion":
                        newImage.setImageResource(R.drawable.lateral_senales_h3);                                       // Cargo la imagen en el ImageView
                        break;
                    case "SoloBuses":
                        newImage.setImageResource(R.drawable.lateral_senales_h4);                                       // Cargo la imagen en el ImageView
                        break;
                    case "SalidaDerecha":
                        newImage.setImageResource(R.drawable.lateral_senales_h5);                                       // Cargo la imagen en el ImageView
                        break;
                    case "Bicicletas":
                        newImage.setImageResource(R.drawable.lateral_senales_h6);                                    // Cargo la imagen en el ImageView
                        break;
                    case "Escolar":
                        newImage.setImageResource(R.drawable.lateral_senales_h7);                                // Cargo la imagen en el ImageView
                        break;
                    case "Interseccion":
                        newImage.setImageResource(R.drawable.lateral_senales_h8);                                       // Cargo la imagen en el ImageView
                        break;
                    case "Piso1":
                        newImage.setImageResource(R.drawable.ml_sn_piso_escolar);                                       // Cargo la imagen en el ImageView
                        break;
                    case "Piso2":
                        newImage.setImageResource(R.drawable.ml_sn_piso_militar);                                       // Cargo la imagen en el ImageView
                        break;
                    case "Piso3":
                        newImage.setImageResource(R.drawable.ml_sn_piso_pare_bus);                                       // Cargo la imagen en el ImageView
                        break;
                    case "Piso4":
                        newImage.setImageResource(R.drawable.ml_sn_piso_ninos_via);                                       // Cargo la imagen en el ImageView
                        break;
                    case "Piso5":
                        newImage.setImageResource(R.drawable.ml_sn_piso_pare);                                       // Cargo la imagen en el ImageView
                        break;
                    case "Piso6":
                        newImage.setImageResource(R.drawable.ml_sn_piso_no_parqueo);                                       // Cargo la imagen en el ImageView
                        break;
                    case "Piso7":
                        newImage.setImageResource(R.drawable.ml_sn_piso_terminacion_carril);                                       // Cargo la imagen en el ImageView
                        break;
                    case "Piso8":
                        newImage.setImageResource(R.drawable.ml_sn_piso_seda_paso);                                       // Cargo la imagen en el ImageView
                        break;
                    case "Piso9":
                        newImage.setImageResource(R.drawable.ml_sn_piso_paso_nivel);                                       // Cargo la imagen en el ImageView
                        break;
                    case "Piso10":
                        newImage.setImageResource(R.drawable.ml_sn_piso_flecha_2lados);                                       // Cargo la imagen en el ImageView
                        break;
                    case "Piso11":
                        newImage.setImageResource(R.drawable.ml_sn_piso_flecha_3lados);                                       // Cargo la imagen en el ImageView
                        break;
                    case "Piso12":
                        newImage.setImageResource(R.drawable.ml_sn_piso_flecha_recta);                                       // Cargo la imagen en el ImageView
                        break;
                    case "Piso13":
                        newImage.setImageResource(R.drawable.ml_sn_piso_flechas_2carriles);                                       // Cargo la imagen en el ImageView
                        break;
                    case "Piso14":
                        newImage.setImageResource(R.drawable.ml_sn_piso_hospital);                                       // Cargo la imagen en el ImageView
                        break;
                    case "Piso15":
                        newImage.setImageResource(R.drawable.ml_sn_piso_icon_accesibilidad);                                       // Cargo la imagen en el ImageView
                        break;
                    /*________________________*/
                    case "Piso16":
                        newImage.setImageResource(R.drawable.ml_sn_piso_linea_cruce);                                       // Cargo la imagen en el ImageView
                        break;
                    case "Piso17":
                        newImage.setImageResource(R.drawable.ml_sn_piso_linea_cruce2carriles);                                       // Cargo la imagen en el ImageView
                        break;
                    case "Piso18":
                        newImage.setImageResource(R.drawable.ml_sn_piso_linea_cruce3carriles);                                       // Cargo la imagen en el ImageView
                        break;
                    case "Piso19":
                        newImage.setImageResource(R.drawable.ml_sn_piso_linea_pare);                                       // Cargo la imagen en el ImageView
                        break;
                    case "Piso20":
                        newImage.setImageResource(R.drawable.ml_sn_piso_linea_pare2carriles);                                       // Cargo la imagen en el ImageView
                        break;
                    case "Piso21":
                        newImage.setImageResource(R.drawable.ml_sn_piso_linea_pare3carriles);                                       // Cargo la imagen en el ImageView
                        break;
                    case "Piso22":
                        newImage.setImageResource(R.drawable.ml_sn_piso_paso_peatonal_doble);                                       // Cargo la imagen en el ImageView
                        break;
                    case "Piso23":
                        newImage.setImageResource(R.drawable.ml_sn_piso_paso_peatonal_2carriles);                                       // Cargo la imagen en el ImageView
                        break;
                    case "Piso24":
                        newImage.setImageResource(R.drawable.ml_sn_piso_paso_peatonal3carriles);                                       // Cargo la imagen en el ImageView
                        break;
                    case "Piso25":
                        newImage.setImageResource(R.drawable.ml_sn_piso_max_vel);                                       // Cargo la imagen en el ImageView
                        break;

                }
                break;


            case 6: // Lineas
                switch (nameImageToAdd) {
                    case "Lineas1":
                        newImage.setImageResource(R.drawable.ml_sn_lineas_reductor_velocidad);                                    // Cargo la imagen en el ImageView
                        break;
                    case "Lineas2":
                        newImage.setImageResource(R.drawable.ml_sn_lineas_blanca_continua);                                    // Cargo la imagen en el ImageView
                        break;
                    case "Lineas3":
                        newImage.setImageResource(R.drawable.ml_sn_lineas_blanca_discontinua);                                    // Cargo la imagen en el ImageView
                        break;
                    case "Lineas4":
                        newImage.setImageResource(R.drawable.ml_sn_lineas_doble_sentido);                                    // Cargo la imagen en el ImageView
                        break;
                    case "Lineas5":
                        newImage.setImageResource(R.drawable.ml_sn_lineas_delineador_canalizacion_salida);                                    // Cargo la imagen en el ImageView
                        break;
                    case "Lineas6":
                        newImage.setImageResource(R.drawable.ml_sn_lineas_delineador_canalizacion_entrada);                                    // Cargo la imagen en el ImageView
                        break;
                    case "Lineas7":
                        newImage.setImageResource(R.drawable.ml_sn_lineas_berma);                                    // Cargo la imagen en el ImageView
                        break;
                    case "Lineas8":
                        newImage.setImageResource(R.drawable.ml_sn_lineas_blanca_continua);                                    // Cargo la imagen en el ImageView
                        break;
                    case "Lineas9":
                        newImage.setImageResource(R.drawable.ml_sn_lineas_amarilla_discon);                                    // Cargo la imagen en el ImageView
                        break;
                    case "Lineas10":
                        newImage.setImageResource(R.drawable.ml_sn_lineas_amarilla_1y1);                                    // Cargo la imagen en el ImageView
                        break;
                    case "Lineas11":
                        newImage.setImageResource(R.drawable.ml_sn_lineas_amarilla_2continuas);                                    // Cargo la imagen en el ImageView
                        break;
                    case "Lineas12":
                        newImage.setImageResource(R.drawable.ml_sn_lineas_amarilla2discon);                                    // Cargo la imagen en el ImageView
                        break;
                    case "Lineas13":
                        newImage.setImageResource(R.drawable.ml_sn_piso_interseccion);                                    // Cargo la imagen en el ImageView
                        break;

                    case "Lineas14":
                        newImage.setImageResource(R.drawable.ml_sn_lineas_c_amarilla_conti);                                    // Cargo la imagen en el ImageView
                        break;
                    case "Lineas15":
                        newImage.setImageResource(R.drawable.ml_sn_lineas_c_amarilla_discon);                                    // Cargo la imagen en el ImageView
                        break;
                    case "Lineas16":
                        newImage.setImageResource(R.drawable.ml_sn_lineas_s_amarilla_continua_27);                                    // Cargo la imagen en el ImageView
                        break;
                    case "Lineas17":
                        newImage.setImageResource(R.drawable.ml_sn_lineas_s_amarilla_disconti);                                    // Cargo la imagen en el ImageView
                        break;
                }




            case 7: // Reductores
                switch (nameImageToAdd) {
                    case "Resaltos":
                        newImage.setImageResource(R.drawable.ml_sn_reductores_resalto);                                    // Cargo la imagen en el ImageView
                        break;
                    case "Tachas":
                        newImage.setImageResource(R.drawable.ml_sn_reductores_tacha_amarilla);                                    // Cargo la imagen en el ImageView
                        break;
                    case "Tabular":
                        newImage.setImageResource(R.drawable.ml_sn_temporales_delineador_sup);                                    // Cargo la imagen en el ImageView
                        break;
                    case "Barreras":
                        newImage.setImageResource(R.drawable.ml_sn_temporales_barandas_sup);                                    // Cargo la imagen en el ImageView
                        break;
                    case "Conos":
                        newImage.setImageResource(R.drawable.ml_sn_temporales_cono_sup);                                    // Cargo la imagen en el ImageView
                        break;
                    case "Sonoras":
                        newImage.setImageResource(R.drawable.ml_sn_reductores_banda_sonora);                                    // Cargo la imagen en el ImageView
                        break;
                    case "Movil":
                        newImage.setImageResource(R.drawable.ml_sn_reductores_resalto_portatil);                                    // Cargo la imagen en el ImageView
                        break;
                    case "Tachones":
                        newImage.setImageResource(R.drawable.ml_sn_reductores_tachon);                                    // Cargo la imagen en el ImageView
                        break;
                    case "Boyas":
                        newImage.setImageResource(R.drawable.ml_sn_reductores_boya);                                    // Cargo la imagen en el ImageView
                        break;
                    case "Bordillos":
                        newImage.setImageResource(R.drawable.ml_sn_reductores_bordillo);                                    // Cargo la imagen en el ImageView
                        break;
                    case "Sonorizador":
                        newImage.setImageResource(R.drawable.ml_sn_reductores_sonorizador);                                    // Cargo la imagen en el ImageView
                        break;
                    case "Esoperol":
                        newImage.setImageResource(R.drawable.ml_sn_reductores_estoperol);                                    // Cargo la imagen en el ImageView
                        break;
                }


        }
        return newImage;
    }

    private ImageView getImageObject(int submenu, String nameImageToAdd) {
        ImageView newImage = new ImageView(getContext());
        switch (submenu) {
            case 0: // objetos fijos
                switch (nameImageToAdd) {
                    case "Muro":
                        newImage.setImageResource(R.drawable.ml_ob_fijos_icon_muro);
                        break;
                    case "Poste":
                        newImage.setImageResource(R.drawable.ml_ob_fijos_poste_luz_sup);
                        break;
                    case "Arbol1":
                        newImage.setImageResource(R.drawable.ml_ob_fijos_arbol_sup);                                       // Cargo la imagen en el ImageView
                        break;
                    case "Arbol2":
                        newImage.setImageResource(R.drawable.ml_ob_fijos_arbolsinhojas_sup);                                       // Cargo la imagen en el ImageView
                        break;
                    case "Arbol3":
                        newImage.setImageResource(R.drawable.ml_ob_fijos_arbolcaido_lat);                                       // Cargo la imagen en el ImageView
                        break;
                    case "Arbol4":
                        newImage.setImageResource(R.drawable.ml_ob_fijos_arbolcaido_sup);                                       // Cargo la imagen en el ImageView
                        break;
                    case "Arbusto":
                        newImage.setImageResource(R.drawable.ml_ob_fijos_arbusto);                                       // Cargo la imagen en el ImageView
                        break;
                    case "Alcantarilla":
                        newImage.setImageResource(R.drawable.ml_ob_fijos_alcantarilla);                                       // Cargo la imagen en el ImageView
                        break;
                    case "Baranda":
                        newImage.setImageResource(R.drawable.ml_ob_fijos_baranta_sup);
                        break;
                    case "Semaforo":
                        newImage.setImageResource(R.drawable.ml_ob_fijos_semaforo_sencillo_lat);
                        break;
                    case "Inmueble":
                        newImage.setImageResource(R.drawable.ml_ob_fijos_inmueble_sup);
                        break;
                    case "Hidrante":
                        newImage.setImageResource(R.drawable.ml_ob_fijos_hidrante_sup);
                        break;
                    case "VallaSenal":
                        newImage.setImageResource(R.drawable.ml_ob_fijos_cerca_malla_lat);
                        break;
                    case "Caseta":
                        newImage.setImageResource(R.drawable.ml_ob_fijos_caseta_sup);
                        break;
                    case "Valla":
                        newImage.setImageResource(R.drawable.ml_ob_fijos_cerca_malla_lat);
                        break;
                    case "Lampara":
                        newImage.setImageResource(R.drawable.ml_ob_fijos_poste_luminaria2_superior);
                        break;
                    case "EstacionFerrea":
                        newImage.setImageResource(R.drawable.ml_ob_fijos_estacion_ferrea_xl);
                        break;
                    case "EstacionTransm":
                        newImage.setImageResource(R.drawable.ml_ob_fijos_estacion_transmi_sup_xl);
                        break;

                }
                break;


            case 1: // estado via
                switch (nameImageToAdd) {
                    case "hueco":
                        newImage.setImageResource(R.drawable.ml_objetos_estado_via_hueco);
                        break;
                    case "derrumbe":
                        newImage.setImageResource(R.drawable.ml_objetos_estado_via_derrumbe);
                        break;
                    case "reparacion":
                        newImage.setImageResource(R.drawable.ml_objetos_estado_via_reparacion);                                       // Cargo la imagen en el ImageView
                        break;
                    case "hundimiento":
                        newImage.setImageResource(R.drawable.ml_objetos_estado_via_hueco_redondo);
                        break;
                    case "inundacion":
                        newImage.setImageResource(R.drawable.ml_objetos_estado_via_inundacion);
                        break;
                    case "parcha":
                        newImage.setImageResource(R.drawable.ml_objetos_estado_via_reparacion);
                        break;
                    case "rizada":
                        newImage.setImageResource(R.drawable.ml_objetos_estado_via_agrietamiento);
                        break;
                    case "fisura":
                        newImage.setImageResource(R.drawable.ml_objetos_estado_via_arrastre);
                        break;

                }
                break;

            case 2: // condiciones climaticas
                switch (nameImageToAdd) {
                    case "clima1":
                        newImage.setImageResource(R.drawable.ml_ob_clima_granizo_dia);
                        break;
                    case "clima2":
                        newImage.setImageResource(R.drawable.ml_ob_clima_granizo_noche);
                        break;
                    case "clima3":
                        newImage.setImageResource(R.drawable.ml_ob_clima_viento_granizo_dia);                                       // Cargo la imagen en el ImageView
                        break;
                    case "clima4":
                        newImage.setImageResource(R.drawable.ml_ob_clima_viento_granizo_noche);
                        break;
                    case "clima5":
                        newImage.setImageResource(R.drawable.ml_ob_clima_lluvia_noche);
                        break;
                    case "clima6":
                        newImage.setImageResource(R.drawable.ml_ob_clima_lluvia_dia);
                        break;
                    case "clima7":
                        newImage.setImageResource(R.drawable.ml_ob_clima_muy_nublado_dia);
                        break;
                    case "clima8":
                        newImage.setImageResource(R.drawable.ml_ob_clima_muy_nublado_noche);
                        break;
                    case "clima9":
                        newImage.setImageResource(R.drawable.ml_ob_clima_vientos_dia);
                        break;
                    case "clima10":
                        newImage.setImageResource(R.drawable.ml_ob_clima_vientos_noche);
                        break;

                }
                break;

            case 3: // condiciones via
                switch (nameImageToAdd) {
                    case "aceite":
                        newImage.setImageResource(R.drawable.ml_objetos_estado_via_gotas);
                        break;
                    case "humeda":
                        newImage.setImageResource(R.drawable.ml_objetos_estado_via_charco_largo);
                        break;
                    case "lodo":
                        newImage.setImageResource(R.drawable.ml_objetos_estado_via_arenapiedras);                                       // Cargo la imagen en el ImageView
                        break;
                }
                break;
            case 4: // condiciones via
                switch (nameImageToAdd) {
                    case "material1":
                        newImage.setImageResource(R.drawable.partespequenas_persona2copia2);
                        break;
                    case "material2":
                        newImage.setImageResource(R.drawable.partespequenas_parte_vehiculocopia2);
                        break;
                    case "paraguas":
                        newImage.setImageResource(R.drawable.partespeq_sealcopia2);                                       // Cargo la imagen en el ImageView
                        break;
                    case "zapato":
                        newImage.setImageResource(R.drawable.partespequenas_naturalezacopia2);
                        break;
                    case "maletin":
                        newImage.setImageResource(R.drawable.partespeque_armacopia2);
                        break;
                    case "placa":
                        newImage.setImageResource(R.drawable.partespequenas_personacopia2);
                        break;
                    case "semoviente":
                        newImage.setImageResource(R.drawable.partespequenas_naturalezacopia);                                       // Cargo la imagen en el ImageView
                        break;
                    case "celular":
                        newImage.setImageResource(R.drawable.partespequenas_animalcopia2);
                        break;

                }
                break;


        }
        return newImage;
    }

    private ImageView getImageVehiculos(int submenu, String nameImageToAdd) {
        ImageView newImage = new ImageView(getContext());
        switch (submenu) {
            case 0: // Automoviles
                switch (nameImageToAdd) {

                    case "Sedan":
                        newImage.setImageResource(R.drawable.ml_vehiculos_automoviles_sedan);                                       // Cargo la imagen en el ImageView
                        break;
                    case "Golf":
                        newImage.setImageResource(R.drawable.ml_vehiculos_automoviles_golf);                                       // Cargo la imagen en el ImageView
                        break;
                    case "Taxi_zapatico":
                        newImage.setImageResource(R.drawable.ml_vehiculos_automoviles_taxi_peque_sup);                                       // Cargo la imagen en el ImageView
                        break;
                    case "Taxi_Sedan":
                        newImage.setImageResource(R.drawable.ml_vehiculos_automoviles_taxi_sedan_sup);                                       // Cargo la imagen en el ImageView
                        break;
                    case "Cupe":
                        newImage.setImageResource(R.drawable.ml_vehiculos_automoviles_cupe);                                       // Cargo la imagen en el ImageView
                        break;
                    case "Camioneta1":
                        newImage.setImageResource(R.drawable.ml_vehiculos_automoviles_camioneta2);                                       // Cargo la imagen en el ImageView
                        break;
                    case "Camioneta2":
                        newImage.setImageResource(R.drawable.ml_vehiculos_automoviles_camioneta);                                       // Cargo la imagen en el ImageView
                        break;
                    case "Convertible":
                        newImage.setImageResource(R.drawable.ml_vehiculos_automoviles_convert_sup);                                       // Cargo la imagen en el ImageView
                        break;
                    case "Limosina":
                        newImage.setImageResource(R.drawable.ml_vehiculos_automoviles_limo_sup);                                       // Cargo la imagen en el ImageView
                        break;
                    case "Minivan":
                        newImage.setImageResource(R.drawable.ml_vehiculos_automoviles_minivan_sup);                                       // Cargo la imagen en el ImageView
                        break;
                    case "Van":
                        newImage.setImageResource(R.drawable.ml_vehiculos_automoviles_van_sup);                                       // Cargo la imagen en el ImageView
                        break;
                    case "Van_escolar":
                        newImage.setImageResource(R.drawable.ml_vehiculos_automoviles_minivan_ventanas_sup);                                       // Cargo la imagen en el ImageView
                        break;
                    case "Campero":
                        newImage.setImageResource(R.drawable.ml_vehiculos_automoviles_jeep);                                       // Cargo la imagen en el ImageView
                        break;
                    case "Pickup1":
                        newImage.setImageResource(R.drawable.ml_vehiculos_automoviles_pickup_2_sup);                                       // Cargo la imagen en el ImageView
                        break;
                    case "Pickup2":
                        newImage.setImageResource(R.drawable.ml_vehiculos_automoviles_pickup_sup);                                       // Cargo la imagen en el ImageView
                        break;

                }
                break;
            case 1: // Motos
                switch (nameImageToAdd) {
                    case "Moto":
                        newImage.setImageResource(R.drawable.ml_vehiculos_motos_moto_lat);                                       // Cargo la imagen en el ImageView
                        break;
                    case "Moto2":
                        newImage.setImageResource(R.drawable.ml_vehiculos_motos_moto_peque_lat);                                       // Cargo la imagen en el ImageView
                        break;
                    case "QuatriMoto":
                        newImage.setImageResource(R.drawable.ml_vehiculos_motos_quatrimoto_sup);                                       // Cargo la imagen en el ImageView
                        break;
                    case "Mototriciclo":
                        newImage.setImageResource(R.drawable.ml_vehiculos_motos_tricimoto_sup);                                       // Cargo la imagen en el ImageView
                        break;
                    case "MotoAltoCilindraje":
                        newImage.setImageResource(R.drawable.ml_vehiculos_motos_chopper_lat);                                       // Cargo la imagen en el ImageView
                        break;
                    case "Motocarro":
                        newImage.setImageResource(R.drawable.ml_vehiculos_motos_motocarro_sup);                                       // Cargo la imagen en el ImageView
                        break;
                    case "MotoTaxi":
                        newImage.setImageResource(R.drawable.ml_vehiculos_motos_motocross_lat);                                       // Cargo la imagen en el ImageView
                        break;
                }
                break;

            case 2: // Buses
                switch (nameImageToAdd) {
                    case "Bus":
                        break;
                    case "Buseta":
                        newImage.setImageResource(R.drawable.ml_vehiculos_bus_bus_sup);                                       // Cargo la imagen en el ImageView
                        break;
                    case "BusetaEscolar":
                        break;
                    case "BusEscolar":
                        break;
                    case "Microbus":
                        break;
                    case "Colectivo":
                        break;
                    case "Articulado":
                        newImage.setImageResource(R.drawable.ml_vehiculos_bus_transmi_sup);                                       // Cargo la imagen en el ImageView
                        break;
                    case "Biarticulado":
                        newImage.setImageResource(R.drawable.ml_vehiculos_bus_biarticulado_sup);                                       // Cargo la imagen en el ImageView
                        break;
                    case "Sitp":
                        newImage.setImageResource(R.drawable.ml_vehiculos_bus_sitp_sup);                                       // Cargo la imagen en el ImageView
                        break;
                    case "Alimentadores":
                        newImage.setImageResource(R.drawable.ml_vehiculos_bus_alimentador_sup);                                       // Cargo la imagen en el ImageView
                        break;
                }


                break;
            case 3: // Camiones
                switch (nameImageToAdd) {
                    case "Camion":
                        newImage.setImageResource(R.drawable.lateral_vehiculos_camion_up);                                       // Cargo la imagen en el ImageView
                        break;
                    case "Tanque":
                        newImage.setImageResource(R.drawable.ml_vehiculos_camiones_13);                                    // Cargo la imagen en el ImageView
                        break;
                    case "Estacas":
                        newImage.setImageResource(R.drawable.ml_vehiculos_camiones_estacas_completo_sup);                                       // Cargo la imagen en el ImageView
                        break;
                    case "Camabaja":
                        // newImage.setImageResource(R.drawable.ml_vehiculos_camiones_11);                                       // Cargo la imagen en el ImageView
                        break;
                    case "Ninera":
                        newImage.setImageResource(R.drawable.ml_vehiculos_camiones_ninera_sup);                                       // Cargo la imagen en el ImageView
                        break;
                   /* case "Tractomula":
                        newImage.setImageResource(R.drawable.ml_vehiculos_camiones_11);                                       // Cargo la imagen en el ImageView
                        break;*/
                    case "Dobletroque":
                        newImage.setImageResource(R.drawable.ml_vehiculos_camiones_04);                                       // Cargo la imagen en el ImageView
                        break;
                    case "Tractocamion":
                        newImage.setImageResource(R.drawable.ml_vehiculos_camiones_11);                                       // Cargo la imagen en el ImageView
                        break;
                    case "Remolque":
                        newImage.setImageResource(R.drawable.ml_vehiculos_camiones_estacas_sup);                                       // Cargo la imagen en el ImageView
                        break;
                    case "Mezcladora":
                        newImage.setImageResource(R.drawable.ml_vehiculos_camiones_15);                                       // Cargo la imagen en el ImageView
                        break;

                    case "Cabezote":
                        newImage.setImageResource(R.drawable.ml_vehiculos_camiones_cabezote_grande_sup);                                       // Cargo la imagen en el ImageView
                        break;
                    case "Trailer1":
                        newImage.setImageResource(R.drawable.ml_vehiculos_camiones_inferior);                                       // Cargo la imagen en el ImageView
                        break;
                    case "Trailer2":
                        newImage.setImageResource(R.drawable.ml_vehiculos_camiones_remolque_sup);                                       // Cargo la imagen en el ImageView
                        break;
                    case "Trailer3":
                        newImage.setImageResource(R.drawable.ml_vehiculos_camiones_remolque_peque_sup);                                       // Cargo la imagen en el ImageView
                        break;


                }
                break;
            case 4: // Bicicletas
                switch (nameImageToAdd) {

                    case "Bicicletas":
                        newImage.setImageResource(R.drawable.lateral_vehiculos_bicicleta_side);                                       // Cargo la imagen en el ImageView
                        break;
                    case "BiciTaxi":
                        newImage.setImageResource(R.drawable.ml_vehiculos_bici_bicitaxi_sup);                                       // Cargo la imagen en el ImageView
                        break;
                    case "Bicimoto":
                        newImage.setImageResource(R.drawable.ml_vehiculos_bici_bicimotor);                                       // Cargo la imagen en el ImageView
                        break;
                    case "Triciclo":
                        newImage.setImageResource(R.drawable.ml_vehiculos_bici_triciclo_sup);                                       // Cargo la imagen en el ImageView
                        break;
                }
                break;


            case 5: // Maquinaria
                switch (nameImageToAdd) {

                    case "Aplanadora":
                        newImage.setImageResource(R.drawable.ml_vehiculos_maquinaria_aplanadora_sup);                                       // Cargo la imagen en el ImageView
                        break;
                    case "Minicargador":
                        newImage.setImageResource(R.drawable.ml_vehiculos_maquinaria_cargador_sup);                                       // Cargo la imagen en el ImageView
                        break;
                    case "Retroexcavadora":
                        newImage.setImageResource(R.drawable.ml_vehiculos_maquinaria_retroexcabadora_sup);                                       // Cargo la imagen en el ImageView
                        break;
                    case "Excavadora":
                        newImage.setImageResource(R.drawable.ml_vehiculos_maquinaria_excabadora_sup);                                       // Cargo la imagen en el ImageView
                        break;
                    case "Grua":
                        newImage.setImageResource(R.drawable.ml_vehiculos_maquinaria_grua_sup);                                       // Cargo la imagen en el ImageView
                        break;
                    case "Camion":
                        newImage.setImageResource(R.drawable.ml_vehiculos_maquinaria_camion_descarga_sup);                                       // Cargo la imagen en el ImageView
                        break;

                    case "Montacarga":
                        newImage.setImageResource(R.drawable.ml_vehiculos_maquinaria_montacarga_sup);                                       // Cargo la imagen en el ImageView
                        break;

                }


            case 6: // Traccion
                switch (nameImageToAdd) {

                    case "Animal":
                        newImage.setImageResource(R.drawable.ml_vehiculos_traccion_animal_sup);                                       // Cargo la imagen en el ImageView
                        break;
                    case "Remolque":
                        newImage.setImageResource(R.drawable.ml_vehiculos_traccion_persona_sup);                                       // Cargo la imagen en el ImageView
                        break;
                    case "Zorillo":
                        newImage.setImageResource(R.drawable.ml_vehiculos_traccion_remolque_sup);                                       // Cargo la imagen en el ImageView
                        break;
                }
                break;


        }
        return newImage;
    }

///////////////////// De aca quite lo de addNewMeasureInDraw

    private ImageView getImageDibujos(int submenu, String nameImageToAdd) {
        ImageView newImage = new ImageView(getContext());
        switch (submenu) {
            case 0: // PR y PA
                switch (nameImageToAdd) {
                    case "PR":
                        newImage.setImageResource(R.drawable.uno);                                       // Cargo la imagen en el ImageView
                        break;
                }
                break;
        }
        return newImage;
    }

    private void saveSketch() {
        AlertDialog deleteAlert = new AlertDialog.Builder(view.getContext()).create();
        deleteAlert.setTitle("Salir del Sketch");
        deleteAlert.setMessage("\nSalir y Guardar los cambios?\n\n");
        //deleteAlert.setIcon(R.drawable.delete);
        deleteAlert.setButton("Salir y Guardar", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                saveImage();
                getActivity().finish();
                Toast.makeText(getActivity(), "Guardando Sketch", Toast.LENGTH_SHORT).show();
            }
        });
        deleteAlert.setButton2("Salir SIN Guardar", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                getActivity().finish();
            }
        });
        deleteAlert.setButton3("Regresar", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });
        deleteAlert.show();
    }

    private void exitSketch() {
        try {
            AlertDialog deleteAlert = new AlertDialog.Builder(view.getContext()).create();
            deleteAlert.setTitle("Salir");
            deleteAlert.setMessage("Desea salir del sketch, se perderan los cambios?");
            //deleteAlert.setIcon(R.drawable.delete);
            deleteAlert.setButton("Ok", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    getActivity().finish();
                }
            });
            deleteAlert.setButton2("Cancelar", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {

                }
            });
            deleteAlert.show();
        } catch (Exception ex) {
        }
    }

    private void saveImage() {
        FileStorage.myFileStorage.saveImageSketch(getContext(), pnl_ContentCapas);
        Intent homeIntent = new Intent(Intent.ACTION_MAIN);
        homeIntent.addCategory(Intent.CATEGORY_HOME);
        homeIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(homeIntent);
    }

    //region evento de add measure in map
    public void onMeasureInDrawActiom(int position, boolean delete) { // Rec Index measureItemArray de la medida a la que se le modifico
        if (delete) {
            deleteMeasureInDraw();
        } else {
            int rlyIdContentTags;
            switch (DataIpat.measureEviArray.get(position).getEvidence().geteCategory()) { // Obtendo el relative contenedor que corresponde
                case VEHICULO:
                    rlyIdContentTags = pnl_VehicleTagMeasure.getId();
                    break;
                case SEÑAL:
                    rlyIdContentTags = pnl_SenalesTagMeasure.getId();
                    break;
                case VICTIMA:
                case HUELLA:
                case OBJETO:
                    rlyIdContentTags = pnl_ObjectTagMeasure.getId();
                    break;
                default:
                    return;
            }

            for (int index = 0; index < ((ViewGroup) ((RelativeLayout) view.findViewById(rlyIdContentTags))).getChildCount(); ++index) {
                TextView nextChild = (TextView) ((RelativeLayout) view.findViewById(rlyIdContentTags)).getChildAt(index);
                if (DataIpat.measureEviArray.get(position).getmTag().getText().equals(nextChild.getText())) {  // Valido si ese tag ya esta puesto
                    updateMeasureInDraw(position, rlyIdContentTags); // Si existo actualizo
                    return;
                }
            }
            addMeasureInDraw(position, rlyIdContentTags); // No existe Agrego
        }
    }

    // Agrego Tag Medida
    private void addMeasureInDraw(final int position, final int rlyIdContentTags) {
        Toast.makeText(getContext(), "Add Measure Tag", Toast.LENGTH_SHORT).show();
        if (DataIpat.measureEviArray.get(position).getmTag().getParent() != null) {
            ((RelativeLayout) view.findViewById(rlyIdContentTags)).removeView(DataIpat.measureEviArray.get(position).getmTag());
        }
        ((RelativeLayout) view.findViewById(rlyIdContentTags)).addView(DataIpat.measureEviArray.get(position).getmTag()); // Agego el measuTag al rlyContenedor

        MeasureEvi measureTag = DataIpat.measureEviArray.get(position);
        int suma = measureTag.getmIndex() + 1;
        try {
            measureTag.getmTag().setText(suma);
        } catch (Exception ex) {
        }
        DataIpat.measureEviArray.get(position).getmTag().getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {      // Cundo estoy seguro de que se agrego
                MeasureEvi measureTag = DataIpat.measureEviArray.get(position);                                      // Acrualizo la posicion
                PointF newMeasurePixs = MyConvert.MtsToPixs(measureTag.getmCoordenate());
                measureTag.getmTag().setTranslationX((newMeasurePixs.x - 25 / 2) + MyConvert.getOffset().x);                                                                    // Recalculapara poner siempre en el centro sin importar la escala ni el desplazamiento
                measureTag.getmTag().setTranslationY(MyConvert.getScreenResolution().y - ((newMeasurePixs.y + 25 / 2) + MyConvert.getOffset().y));

                //Valido si estan completos para agregar TagEvidence
                addIfTagMeasureComplete(position, rlyIdContentTags);

                if (Build.VERSION.SDK_INT < Build.VERSION_CODES.JELLY_BEAN) {
                    measureTag.getmTag().getViewTreeObserver().removeGlobalOnLayoutListener(this);
                } else {
                    measureTag.getmTag().getViewTreeObserver().removeOnGlobalLayoutListener(this);
                }
            }
        });
    }
    //endregion fin acciones de los botones

    // Actualizo Tag Medida
    private void updateMeasureInDraw(int position, int rlyIdContentTags) {
        Toast.makeText(getContext(), "Update Measure Tag", Toast.LENGTH_SHORT).show();
        MeasureEvi measureTag = DataIpat.measureEviArray.get(position);
        PointF updateMeasurePixs = MyConvert.MtsToPixs(measureTag.getmCoordenate());
        measureTag.getmTag().setTranslationX((updateMeasurePixs.x - 25 / 2) + MyConvert.getOffset().x);                                                                    // Recalculapara poner siempre en el centro sin importar la escala ni el desplazamiento
        measureTag.getmTag().setTranslationY(MyConvert.getScreenResolution().y - ((updateMeasurePixs.y + 25 / 2) + MyConvert.getOffset().y));

        updateIfTagMeasureComplete(position, rlyIdContentTags);
    }

    // Borro Tag Medida
    private void deleteMeasureInDraw() {
        Toast.makeText(getContext(), "Delete Measure Tag", Toast.LENGTH_SHORT).show();
    }

    // Agrego Tag Evidencia
    private void addEvidenceInDraw(int position, int rlyIdContentTags, final PointF startPoint, final PointF endPoint) {
        Toast.makeText(getContext(), "Add Evidence Tag", Toast.LENGTH_SHORT).show();

        final Evidence myEvidence = addNewImageFromEvidence(position, startPoint, endPoint);
        ((RelativeLayout) view.findViewById(rlyIdContentTags)).addView(myEvidence.geteTag());

        myEvidence.geteTag().getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {

                if (endPoint == null) {
                    PointF newEvidenceTagPixs = MyConvert.MtsToPixs(startPoint);
                    myEvidence.geteTag().setTranslationX((newEvidenceTagPixs.x) + MyConvert.getOffset().x - myEvidence.geteTag().getWidth() / 2);                                                                    // Recalculapara poner siempre en el centro sin importar la escala ni el desplazamiento
                    myEvidence.geteTag().setTranslationY(MyConvert.getScreenResolution().y - ((newEvidenceTagPixs.y + myEvidence.geteTag().getHeight() / 2) + MyConvert.getOffset().y));
                } else {
                    PointF newEvidenceTagPixs = MyConvert.MtsToPixs(MyTrigonometry.getTwoCenterPoint(startPoint, endPoint, MyConvert.PixsToMts(myEvidence.geteImage().getHeight()) / 2));
                    myEvidence.geteTag().setTranslationX((newEvidenceTagPixs.x) + MyConvert.getOffset().x - myEvidence.geteTag().getWidth() / 2);                                                                    // Recalculapara poner siempre en el centro sin importar la escala ni el desplazamiento
                    myEvidence.geteTag().setTranslationY(MyConvert.getScreenResolution().y - ((newEvidenceTagPixs.y + myEvidence.geteTag().getHeight() / 2) + MyConvert.getOffset().y));
                }
                if (Build.VERSION.SDK_INT < Build.VERSION_CODES.JELLY_BEAN) {
                    myEvidence.geteTag().getViewTreeObserver().removeGlobalOnLayoutListener(this);
                } else {
                    myEvidence.geteTag().getViewTreeObserver().removeOnGlobalLayoutListener(this);
                }
            }
        });
    }

    // Actualizo Tag Evidencia
    private void updateEvidenceInDraw(int position, int rlyIdContentTags, final PointF startPoint, final PointF endPoint) {
        Toast.makeText(getContext(), "Update Evidence Tag", Toast.LENGTH_SHORT).show();
        Evidence myEvidence = updateNewImageFromEvidence(position, startPoint, endPoint);

        if (endPoint == null) {
            PointF newEvidenceTagPixs = MyConvert.MtsToPixs(startPoint);
            myEvidence.geteTag().setTranslationX((newEvidenceTagPixs.x) + MyConvert.getOffset().x - myEvidence.geteTag().getWidth() / 2);                                                                    // Recalculapara poner siempre en el centro sin importar la escala ni el desplazamiento
            myEvidence.geteTag().setTranslationY(MyConvert.getScreenResolution().y - ((newEvidenceTagPixs.y + myEvidence.geteTag().getHeight() / 2) + MyConvert.getOffset().y));
        } else {
            PointF newEvidenceTagPixs = MyConvert.MtsToPixs(MyTrigonometry.getTwoCenterPoint(startPoint, endPoint, MyConvert.PixsToMts(myEvidence.geteImage().getHeight()) / 2));
            myEvidence.geteTag().setTranslationX((newEvidenceTagPixs.x) + MyConvert.getOffset().x - myEvidence.geteTag().getWidth() / 2);                                                                    // Recalculapara poner siempre en el centro sin importar la escala ni el desplazamiento
            myEvidence.geteTag().setTranslationY(MyConvert.getScreenResolution().y - ((newEvidenceTagPixs.y + myEvidence.geteTag().getHeight() / 2) + MyConvert.getOffset().y));
        }
    }

    // Borro Tag Evidencia
    private void deleteEvidenceInDraw() {
        Toast.makeText(getContext(), "Delete Evidence Tag", Toast.LENGTH_LONG).show();
    }

    private void updateIfTagMeasureComplete(int position, int rlyIdContentTags) { // si debo pintar o Actulizar Tag de Evidencia
        MeasureEvi secondMeasure = DataIpat.measureEviArray.get(position);
        // Miro si solo tiene 1 debo pintar si tiene mas miro si ya hay puesto en el mapa
        MeasureEvi firstMeasure = null;
        if (secondMeasure.getEvidence().getmIndexArray().size() != 1) {
            int countMeausure = 0;
            for (MeasureEvi measure : DataIpat.measureEviArray) {  //
                if (secondMeasure.getEvidence().geteCategory() == measure.getEvidence().geteCategory() && secondMeasure.geteIndex() == measure.geteIndex() &&
                        measure.getmCoordenate().x != 0 && measure.getmCoordenate().y != 0) {
                    countMeausure++;
                    if (measure.getmCoordenate().x != secondMeasure.getmCoordenate().x || measure.getmCoordenate().y != secondMeasure.getmCoordenate().y)
                        firstMeasure = measure;
                    if (secondMeasure.getEvidence().getmIndexArray().size() == countMeausure)
                        updateEvidenceInDraw(position, rlyIdContentTags, firstMeasure.getmCoordenate(), secondMeasure.getmCoordenate());
                }
            }
        } else if (secondMeasure.getEvidence().getmIndexArray().size() == 1)
            updateEvidenceInDraw(position, rlyIdContentTags, null, secondMeasure.getmCoordenate());

    }

    private void addIfTagMeasureComplete(int position, int rlyIdContentTags) { // si debo pintar o Actulizar Tag de Evidencia
        MeasureEvi myMeasure = DataIpat.measureEviArray.get(position);
        // Miro si solo tiene 1 debo pintar si tiene mas miro si ya hay puesto en el mapa
        PointF firstPoint = null;
        if (myMeasure.getEvidence().getmIndexArray().size() != 1) {
            int countMeausure = 0;
            for (MeasureEvi measure : DataIpat.measureEviArray) {  //
                if (myMeasure.getEvidence().geteCategory() == measure.getEvidence().geteCategory() && myMeasure.geteIndex() == measure.geteIndex() &&
                        measure.getmCoordenate().x != 0 && measure.getmCoordenate().y != 0) {
                    countMeausure++;
                    if (measure.getmCoordenate().x != myMeasure.getmCoordenate().x || measure.getmCoordenate().y != myMeasure.getmCoordenate().y)
                        firstPoint = measure.getmCoordenate();
                    if (myMeasure.getEvidence().getmIndexArray().size() == countMeausure)
                        addEvidenceInDraw(position, rlyIdContentTags, firstPoint, myMeasure.getmCoordenate());
                }
            }
        } else if (myMeasure.getEvidence().getmIndexArray().size() == 1)
            addEvidenceInDraw(position, rlyIdContentTags, null, myMeasure.getmCoordenate());

    }

    private int isTagEvidenceExist(int position, int rlyIdContentTags) {
        for (int index = 0; index < ((ViewGroup) ((RelativeLayout) view.findViewById(rlyIdContentTags))).getChildCount(); ++index) {
            TextView nextChild = (TextView) ((RelativeLayout) view.findViewById(rlyIdContentTags)).getChildAt(index);
            if (DataIpat.measureEviArray.get(position).getEvidence().geteTag().getId() == nextChild.getId()) {  // Valido si ese tag ya esta puesto
                return nextChild.getId();
            }
        }
        return -1;
    }

    //////////////////////////////////////////////////////////

    private Evidence addNewImageFromEvidence(final int position, final PointF startPoint, final PointF endPoint) {
        Toast.makeText(getContext(), "Add Image", Toast.LENGTH_LONG).show();

        final Evidence myEvidence = DataIpat.measureEviArray.get(position).getEvidence();
        try {
            myEvidence.geteImage().setOnTouchListener(this);

            switch (DataIpat.measureEviArray.get(position).getEvidence().geteCategory()) {
                case SEÑAL:
                    arrCapaSenales.add(myEvidence.geteImage());
                    imgIndexSelected = arrCapaSenales.size() - 1;
                    pnl_CapaSenales.addView(arrCapaSenales.get(imgIndexSelected));
                    imgViewSelected = arrCapaSenales.get(imgIndexSelected);
                    break;
                case OBJETO:
                case HUELLA:
                    arrCapaObjetos.add(myEvidence.geteImage());
                    imgIndexSelected = arrCapaObjetos.size() - 1;
                    pnl_CapaObjetos.addView(arrCapaObjetos.get(imgIndexSelected));
                    imgViewSelected = arrCapaObjetos.get(imgIndexSelected);
                    break;
                case VICTIMA:
                    arrCapaObjetos.add(myEvidence.geteImage());
                    imgIndexSelected = arrCapaObjetos.size() - 1;
                    pnl_CapaObjetos.addView(arrCapaObjetos.get(imgIndexSelected));
                    imgViewSelected = arrCapaObjetos.get(imgIndexSelected);
                    break;
                case VEHICULO:
                    arrCapaVehiculos.add(myEvidence.geteImage());
                    imgIndexSelected = arrCapaVehiculos.size() - 1;
                    pnl_CapaVehiculos.addView(arrCapaVehiculos.get(imgIndexSelected));
                    imgViewSelected = arrCapaVehiculos.get(imgIndexSelected);
                    break;
            }

            imgViewSelected.getLayoutParams().width = (int) MyConvert.scaleValue(myEvidence.geteImage().getDrawable().getIntrinsicWidth());                                         // le asigno el tamaño que trae la imagen en el ancho
            imgViewSelected.getLayoutParams().height = (int) MyConvert.scaleValue(myEvidence.geteImage().getDrawable().getIntrinsicHeight());                                           // le asigno el tamaño que trae la imagen en el alto
            imgViewSelected.setBackgroundResource(R.drawable.selectedbackground);
            imgViewSelected.setBackgroundResource(0);
//                imgViewSelected.setScaleX(1 / (MyConvert.getScale() / 122));                                       // Ajusto a la nueva escala
//                imgViewSelected.setScaleY(1 / (MyConvert.getScale() / 122));

            if (startPoint == null) {
                imgViewSelected.setTranslationX(((pnl_ContentCapas.getMeasuredWidth() / 2) -                  // Centro Coordenadas en X iniciales dodnde debe salir la imagen
                        (imgViewSelected.getLayoutParams().width / 2)) -                                     // Le quito el centro de la imagen
                        (pnl_ContentCapas.getTranslationX() / mScaleAll));                                               // Recalculapara poner siempre en el centro sin importar la escala ni el desplazamiento
                imgViewSelected.setTranslationY(((pnl_ContentCapas.getMeasuredHeight() / 2) -                 // Centro Coordenadas en X iniciales dodnde debe salir la imagen
                        (imgViewSelected.getLayoutParams().height / 2)) -                                    // Le quito el centro de la imagen
                        (pnl_ContentCapas.getTranslationY() / mScaleAll));                                               // Recalculapara poner siempre en el centro sin importar la escala ni el desplazamiento

                imgViewSelected.setBackgroundResource(R.drawable.selectedbackground);                       // se le agrega la iamgen de fondo de seleccion de objeto
                editOnTouch4Capa(false);                                                                    // se le quita los eventos de manipulacion a los objetos de la capa seleccionada
                selctedObject();
                menuLateral.closeDrawer(GravityCompat.START);                                                       // animo y cierro el menulateral
            } else {
                imgViewSelected.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
                    @Override
                    public void onGlobalLayout() {

                        imgViewSelected.setPivotX(0);
                        imgViewSelected.setPivotY(0);
                        double angle = MyTrigonometry.angleTwoVectors(startPoint, endPoint);
                        PointF imageLocation = null;
                        /*if (myEvidence.geteCategory() == DataIpat.EvidenceCategory.VICTIMA) {
                            imgViewSelected.setRotation((int) angle);
                            imageLocation = MyConvert.MtsToPixs(MyTrigonometry.getCornerrPoint(startPoint, endPoint, 0));
                        } else {*/
                        imgViewSelected.setRotation((int) angle);
                        imageLocation = MyConvert.MtsToPixs(MyTrigonometry.getCornerrPoint(startPoint, endPoint, MyConvert.PixsToMts(myEvidence.geteImage().getHeight())));
                        /*}*/


                        imgViewSelected.setTranslationX((imageLocation.x + MyConvert.getOffset().x));                                                                    // Recalculapara poner siempre en el centro sin importar la escala ni el desplazamiento
                        imgViewSelected.setTranslationY(MyConvert.getScreenResolution().y - ((imageLocation.y) + MyConvert.getOffset().y));

                        editOnTouch4Capa(true);                                                     // Subscribo al evento de manipulacion a los objetos de la capa actualmente seleccionada
                        deselctedObject();
                        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.JELLY_BEAN) {
                            imgViewSelected.getViewTreeObserver().removeGlobalOnLayoutListener(this);
                        } else {
                            imgViewSelected.getViewTreeObserver().removeOnGlobalLayoutListener(this);
                        }
                    }
                });
            }
            CreateUndoRed(true);
        } catch (Exception ex) {
        }
        return myEvidence;
    }

    private Evidence updateNewImageFromEvidence(final int position, final PointF startPoint, final PointF endPoint) {
        Toast.makeText(getContext(), "Update Image", Toast.LENGTH_LONG).show();

        final Evidence myEvidence = DataIpat.measureEviArray.get(position).getEvidence();
        try {
            switch (DataIpat.measureEviArray.get(position).getEvidence().geteCategory()) {
                case SEÑAL:
                    imgViewSelected = arrCapaSenales.get(imgIndexSelected);
                    break;
                case OBJETO:
                case HUELLA:
                    imgViewSelected = arrCapaObjetos.get(imgIndexSelected);
                    break;
                case VICTIMA:
                    imgViewSelected = arrCapaObjetos.get(imgIndexSelected);
                    break;
                case VEHICULO:
                    imgViewSelected = arrCapaVehiculos.get(imgIndexSelected);
                    break;
            }

            double angle = MyTrigonometry.angleTwoVectors(startPoint, endPoint);
            PointF imageLocation = null;
            if (myEvidence.geteCategory() == DataIpat.EvidenceCategory.VICTIMA) {
                imgViewSelected.setRotation((int) angle - 90);
                imageLocation = MyConvert.MtsToPixs(MyTrigonometry.getCornerrPoint(startPoint, endPoint, 0));
            } else {
                imgViewSelected.setRotation((int) angle);
                imageLocation = MyConvert.MtsToPixs(MyTrigonometry.getCornerrPoint(startPoint, endPoint, MyConvert.PixsToMts(myEvidence.geteImage().getHeight())));
            }

            imgViewSelected.setTranslationX((imageLocation.x + MyConvert.getOffset().x));                                                                    // Recalculapara poner siempre en el centro sin importar la escala ni el desplazamiento
            imgViewSelected.setTranslationY(MyConvert.getScreenResolution().y - ((imageLocation.y) + MyConvert.getOffset().y));

        } catch (Exception ex) {
        }
        return myEvidence;
    }

    @Override
    public void onSelectedMeasure(boolean state) {
        if (state) {
            if (pnlSecond.getVisibility() == View.GONE) {
                //if(pnl_CapaCoordenadas.getVisibility() == view.VISIBLE) pnl_CapaCoordenadas.setVisibility(View.GONE);

                ani_Out_PanelDown.setAnimationListener(new Animation.AnimationListener() {                    // a la ultima animacion le creo evento para saber cuando termino y poner desaparece los botones
                    public void onAnimationStart(Animation anim) {
                    }

                    ;

                    public void onAnimationRepeat(Animation anim) {
                    }

                    ;

                    public void onAnimationEnd(Animation anim)                                                // evento de terminacion de la animacion
                    {
                        pnlFirst.setVisibility(View.GONE);                                   // desaparece el control donde estan todos los botones
                        ani_Out_PanelDown.setAnimationListener(null);
                    }

                    ;
                });
                pnlFirstUp.startAnimation(ani_Out_PanelUp);
                pnlFirstDown.startAnimation(ani_Out_PanelDown);
                btnNorte.setVisibility(View.GONE);

                btnDeleteMausure.setVisibility(View.VISIBLE);
                //            ani_In_PanelUp.setStartOffset(100);
                //            ani_In_PanelDown.setStartOffset(100);
                //            pnlSecondUp.startAnimation(ani_In_PanelUp);
                //            pnlSecondDown.startAnimation(ani_In_PanelDown);
                //            pnlSecond.setVisibility(View.VISIBLE);
            }
        } else {
            if (pnlFirst.getVisibility() == View.GONE) {
                if (stateVisualCoordinate == true) pnl_CapaCoordenadas.setVisibility(View.VISIBLE);
//                ani_Out_PanelDown.setAnimationListener(new Animation.AnimationListener() {                    // a la ultima animacion le creo evento para saber cuando termino y poner desaparece los botones
//                    public void onAnimationStart(Animation anim) {};
//                    public void onAnimationRepeat(Animation anim) {};
//                    public void onAnimationEnd(Animation anim)                                                // evento de terminacion de la animacion
//                    {
//                        pnlSecond.setVisibility(View.GONE);                                   // desaparece el control donde estan todos los botones
//                        ani_Out_PanelDown.setAnimationListener(null);
//                    };
//                });
//                pnlSecondUp.startAnimation(ani_Out_PanelUp);
//                pnlSecondDown.startAnimation(ani_Out_PanelDown);
//                btnNorte.setVisibility(View.VISIBLE);

                btnDeleteMausure.setVisibility(View.GONE);

                ani_In_PanelUp.setStartOffset(100);
                ani_In_PanelDown.setStartOffset(100);
                pnlFirstUp.startAnimation(ani_In_PanelUp);
                pnlFirstDown.startAnimation(ani_In_PanelDown);
                pnlFirst.setVisibility(View.VISIBLE);
            }
        }

    }

    /////////////////////////////////////////

    private void Updateview(ObjetPosition newposition, Boolean isUndo) {
        if (newposition.getRemove()) {
            if (isUndo) {
                //undo == remove
                switch (newposition.getType()) {
                    case VEHICULO:
                        pnl_CapaVehiculos.removeView(arrCapaVehiculos.get(newposition.getIndex()));
                        arrCapaVehiculos.remove(newposition.getIndex());
                        break;
                    case OBJETO:
                        pnl_CapaObjetos.removeView(arrCapaObjetos.get(newposition.getIndex()));
                        arrCapaObjetos.remove(newposition.getIndex());
                        break;
                    case SEÑAL:
                        pnl_CapaSenales.removeView(arrCapaSenales.get(newposition.getIndex()));
                        arrCapaSenales.remove(newposition.getIndex());
                        break;
                    case VIA:
                        pnl_CapaVias.removeView(arrCapaVias.get(newposition.getIndex()));
                        arrCapaVias.remove(newposition.getIndex());
                        break;
                }
            } else {
                // redo == create
                switch (newposition.getType()) {
                    case VEHICULO:
                        arrCapaVehiculos.add(newposition.getImageCreate());
                        pnl_CapaVehiculos.addView(arrCapaVehiculos.get(arrCapaVehiculos.size() - 1));
                        break;
                    case OBJETO:
                        arrCapaObjetos.add(newposition.getImageCreate());
                        pnl_CapaObjetos.addView(arrCapaObjetos.get(arrCapaObjetos.size() - 1));

                        break;
                    case SEÑAL:
                        arrCapaSenales.add(newposition.getImageCreate());
                        pnl_CapaSenales.addView(arrCapaSenales.get(arrCapaSenales.size() - 1));

                        break;
                    case VIA:
                        arrCapaVias.add(newposition.getImageCreate());
                        pnl_CapaVias.addView(arrCapaVias.get(arrCapaVias.size() - 1));

                        break;
                }
            }
        } else {
            int id = newposition.getIndex();
            switch (newposition.getType()) {
                case VEHICULO:
                    //MyImage = arrCapaVehiculos.get(newposition.getIndex());
                    try {

                        arrCapaVehiculos.get(newposition.getIndex()).setScaleX(newposition.getScale());
                        arrCapaVehiculos.get(newposition.getIndex()).setScaleY(newposition.getScale());
                        arrCapaVehiculos.get(newposition.getIndex()).setRotation(newposition.getRotation());
                        arrCapaVehiculos.get(newposition.getIndex()).setTranslationX(newposition.getPosition().x);
                        arrCapaVehiculos.get(newposition.getIndex()).setTranslationY(newposition.getPosition().y);
                    } catch (Exception ex) {

                    }

                    break;
                case OBJETO:
                    //MyImage = arrCapaObjetos.get(newposition.getIndex());
                    arrCapaObjetos.get(newposition.getIndex()).setScaleX(newposition.getScale());
                    arrCapaObjetos.get(newposition.getIndex()).setScaleY(newposition.getScale());
                    arrCapaObjetos.get(newposition.getIndex()).setRotation(newposition.getRotation());
                    arrCapaObjetos.get(newposition.getIndex()).setTranslationX(newposition.getPosition().x);
                    arrCapaObjetos.get(newposition.getIndex()).setTranslationY(newposition.getPosition().y);
                    break;
                case SEÑAL:
                    //MyImage = arrCapaSenales.get(newposition.getIndex());
                    arrCapaSenales.get(newposition.getIndex()).setScaleX(newposition.getScale());
                    arrCapaSenales.get(newposition.getIndex()).setScaleY(newposition.getScale());
                    arrCapaSenales.get(newposition.getIndex()).setRotation(newposition.getRotation());
                    arrCapaSenales.get(newposition.getIndex()).setTranslationX(newposition.getPosition().x);
                    arrCapaSenales.get(newposition.getIndex()).setTranslationY(newposition.getPosition().y);
                    break;
                case VIA:
                    //MyImage = arrCapaVias.get(newposition.getIndex());
                    arrCapaVias.get(newposition.getIndex()).setScaleX(newposition.getScale());
                    arrCapaVias.get(newposition.getIndex()).setScaleY(newposition.getScale());
                    arrCapaVias.get(newposition.getIndex()).setRotation(newposition.getRotation());
                    arrCapaVias.get(newposition.getIndex()).setTranslationX(newposition.getPosition().x);
                    arrCapaVias.get(newposition.getIndex()).setTranslationY(newposition.getPosition().y);
                    break;
            }
        }
    }

    private void CreateUndoRed(Boolean isImgCreate) {
        if (Undolist != null) {
            if (Undolist.size() >= 0 && Undolist.size() < 11) {
                int index;
                switch (capaSelected) {
                    case 0:
                        //CapaVias
                        index = -1;
                        for (ImageView nyImage : arrCapaVias) {
                            index++;
                            if (nyImage.getTag() == imgViewSelected.getTag()) break;
                        }
                        LoadObject(DataIpat.EvidenceCategory.VIA, index, isImgCreate);
                        break;
                    case 1:
                        //CapaSenales
                        index = -1;
                        for (ImageView nyImage : arrCapaSenales) {
                            index++;
                            if (nyImage.getId() == imgViewSelected.getId()) break;
                        }
                        LoadObject(DataIpat.EvidenceCategory.SEÑAL, index, isImgCreate);
                        break;
                    case 2:
                        //CapaObjetos
                        index = -1;
                        for (ImageView nyImage : arrCapaObjetos) {
                            index++;
                            if (nyImage.getTag() == imgViewSelected.getTag()) break;
                        }
                        LoadObject(DataIpat.EvidenceCategory.OBJETO, index, isImgCreate);
                        break;
                    case 3:
                        //CapaVehiculos
                        index = -1;
                        for (ImageView nyImage : arrCapaVehiculos) {
                            index++;
                            if (nyImage.getId() == imgViewSelected.getId()) break;
                        }
                        LoadObject(DataIpat.EvidenceCategory.VEHICULO, index, isImgCreate);
                        break;
                }
            }
        }
    }
    //endregion

    private void LoadObject(DataIpat.EvidenceCategory type, int index, Boolean isImgCreate) {
        ObjetPosition newObject = new ObjetPosition(

                imgViewSelected.getScaleX(),
                imgViewSelected.getRotation(),
                new PointF(imgViewSelected.getTranslationX(), imgViewSelected.getTranslationY()),
                0
        );
        newObject.setType(type);
        newObject.setIndex(index);
        newObject.setRemove(isImgCreate);
        if (isImgCreate) newObject.setImageCreate(imgViewSelected);
        Undolist.add(newObject);
    }


    /////// Gabriel 26-01-17 ////////

    public void Retroceder() {
        if (Undolist.size() > 0 && Undolist.size() < 10) {
            //
            ObjetPosition captureitem = Undolist.get(Undolist.size() - 1);
            Undolist.remove(Undolist.size() - 1);
            Redolist.add(captureitem);
            Updateview(captureitem, true);
        }
    }

    public void Adelantar() {
        if (Redolist.size() > 0 && Redolist.size() < 10) {

            //Undolist.add(Redolist.remove(Redolist.size() - 1));

            ObjetPosition captureitem = Redolist.get(Redolist.size() - 1);
            Redolist.remove(Redolist.size() - 1);
            Undolist.add(captureitem);
            Updateview(captureitem, false);
        }
    }

    private class ScaleListener extends ScaleGestureDetector.SimpleOnScaleGestureListener                   // evento de escala
    {
        @Override
        public boolean onScale(ScaleGestureDetector detector) {
            mScaleFactor = detector.getScaleFactor();                                                       // actualizo variable de scala
            return true;
        }
    }

    private class RotateListener extends RotateGestureDetector.SimpleOnRotateGestureListener                // evento de rotacion
    {
        @Override
        public boolean onRotate(RotateGestureDetector detector) {
            mRotationDegrees = detector.getRotationDegreesDelta();                                          // actualizo variable de Rotacion
            return true;
        }
    }

    private class MoveListener extends MoveGestureDetector.SimpleOnMoveGestureListener                      // Evento de translacion
    {
        @Override
        public boolean onMove(MoveGestureDetector detector) {
            PointF d = detector.getFocusDelta();
            mFocusX = d.x;                                                                                    // actualizo variable de tgraslacion
            mFocusY = d.y;
            return true;
        }
    }


    /////// - Fin Gabriel 26-01-17 -////////////


}