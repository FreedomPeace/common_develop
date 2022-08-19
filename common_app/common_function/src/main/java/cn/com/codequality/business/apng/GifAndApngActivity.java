package cn.com.codequality.business.apng;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.content.res.AppCompatResources;

import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.widget.ImageView;

import com.github.penfeizhou.animation.apng.APNGDrawable;
import com.github.penfeizhou.animation.loader.ResourceStreamLoader;

import cn.com.codequality.R;
import pl.droidsonroids.gif.GifImageView;

public class GifAndApngActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_apng);
        GifImageView gifImageView = findViewById(R.id.star);
        gifImageView.setImageResource(R.drawable.star);

        // Load form Resource
        ResourceStreamLoader resourceLoader = new ResourceStreamLoader(this, R.raw.share_beauty);

        // Create APNG Drawable
        APNGDrawable apngDrawable = new APNGDrawable(resourceLoader);
         ImageView imageView =    findViewById(R.id.share);
         imageView.setImageDrawable(apngDrawable);
    }
}