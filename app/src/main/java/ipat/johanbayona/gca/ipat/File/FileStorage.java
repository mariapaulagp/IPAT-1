package ipat.johanbayona.gca.ipat.File;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Environment;
import android.text.Layout;
import android.widget.RelativeLayout;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ResourceBundle;

/**
 * Created by JohanBayona on 16/11/2016.
 */

public class FileStorage {

    public static class myFileStorage {
        static String fileCroquisImageName = "NumerodeIpat.png";
        static String fileCroquisTxtName = "IPATS.txt";
        static String folderIpat = "IPATGCA";
        static File directory;
        static File root;

        public static boolean readNameIpat() {
            try {
                root = new File(Environment.getExternalStorageDirectory(), folderIpat);
                BufferedReader br = new BufferedReader(new FileReader(new File(root, fileCroquisTxtName)));
                String line;
                line = br.readLine();
                line.replace(" ", "");
                fileCroquisImageName = line + ".png";
                return true;
            } catch (IOException e) {
                e.printStackTrace();
            }
            return false;
        }

        public static void createIpatFolder(Context context, String ipatName){
            directory = Environment.getExternalStoragePublicDirectory(folderIpat);
            folderIpat = ipatName;
        }

        public static void deleteIpatFolder(Context context){
            //directory = context.getDir("" + folderIpat, Context.MODE_PRIVATE);
            if(directory.exists()) directory.delete();
        }

        public static void saveImageSketch(Context context, RelativeLayout myControl){
            myControl.setDrawingCacheEnabled(true);
            Bitmap bitmap = myControl.getDrawingCache();
            saveImageFile(context, bitmap);
        }

        public static Bitmap getImageSketch(Context context){
            return getImageFile(context);
        }

//////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

        private static void saveImageFile(Context context, Bitmap bitmap){
            FileOutputStream out;
            try {
                if(!root.exists()) root.mkdirs();
                FileOutputStream fOut = new FileOutputStream(new File(root, fileCroquisImageName));

                bitmap.compress(Bitmap.CompressFormat.PNG, 90, fOut);
                fOut.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        private static Bitmap getImageFile(Context context){
            try{
                FileInputStream fis = context.openFileInput(root.getAbsolutePath() + "//" + fileCroquisImageName);
                Bitmap b = BitmapFactory.decodeStream(fis);
                fis.close();
                return b;
            }
            catch(Exception e){
            }
            return null;
        }

        private static String getNameIpat(Context context, String name, String extension){
           return "Nombre123";
        }
    }
}
