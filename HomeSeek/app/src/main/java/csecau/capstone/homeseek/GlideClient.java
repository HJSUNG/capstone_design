package csecau.capstone.homeseek;

import android.content.Context;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.util.Log;
import android.widget.ImageView;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FirebaseStorage;
import com.squareup.picasso.Picasso;

    public class GlideClient {
        final static FirebaseStorage storage = FirebaseStorage.getInstance();
    static String imageURL;

    public  static  void downloadimg(final Context c, String url, final ImageView img)
    {
        if (url!=null && url.length()>0)
        {
            storage.getReferenceFromUrl(url).getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                @Override
                public void onSuccess(Uri uri) {
                    Picasso.with(c).load(uri).into(img);
                    imageURL = uri.toString();

                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    Log.d("glide", "실패");

                }
            });
            Picasso.with(c).load(imageURL).resize(3,3).into(img);
            Picasso.with(c).load(url).resize(3,3).into(img);
            Log.d("aqua", "성공");
        }else
        {
            Picasso.with(c).load(R.drawable.plusicon).into(img);
        }
    }
}