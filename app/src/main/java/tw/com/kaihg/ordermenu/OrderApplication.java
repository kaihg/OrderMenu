package tw.com.kaihg.ordermenu;

import android.app.Application;

import com.squareup.picasso.OkHttpDownloader;
import com.squareup.picasso.Picasso;

import tw.com.kaihg.ordermenu.manager.OrderManager;

/**
 * Created by huangkaihg on 2016/3/14.
 */
public class OrderApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        OrderManager.getInstance().init(this);
        initPicasso();
    }

    private void initPicasso() {
        Picasso.Builder builder = new Picasso.Builder(this);
        builder.downloader(new OkHttpDownloader(this, 1024 * 1024 * 20));
        Picasso built = builder.build();
        built.setIndicatorsEnabled(true);
        built.setLoggingEnabled(true);
        Picasso.setSingletonInstance(built);
    }
}
