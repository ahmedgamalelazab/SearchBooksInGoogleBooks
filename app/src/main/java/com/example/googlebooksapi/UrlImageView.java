package com.example.googlebooksapi;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.AsyncTask;
import android.util.AttributeSet;
import android.util.Log;
import android.widget.ImageView;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;

public class UrlImageView extends ImageView {

    private AsyncTask<URL, Void, Bitmap> currentLoadingTask;//end of AsyncTaskLoader;

    /*
     * track loading task to cancel it
     */
    private Object loadingMonitor = new Object();

    public UrlImageView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    public UrlImageView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public UrlImageView(Context context) {
        super(context);
    }

    @Override
    public void setImageBitmap(Bitmap bm) {
        cancelLoading();
        super.setImageBitmap(bm);
    }

    @Override
    public void setImageDrawable(Drawable drawable) {
        cancelLoading();
        super.setImageDrawable(drawable);
    }

    @Override
    public void setImageResource(int resId) {
        cancelLoading();
        super.setImageResource(resId);
    }

    @Override
    public void setImageURI(Uri uri) {
        cancelLoading();
        super.setImageURI(uri);
    }

    public void setImageURL(URL url) {
        synchronized (loadingMonitor) {
            cancelLoading();
            this.currentLoadingTask = new UrlLoadingTask(this).execute(url);
        }
    }

    public void cancelLoading() {
        synchronized (loadingMonitor) {
            if (this.currentLoadingTask != null) {
                this.currentLoadingTask.cancel(true);
                this.currentLoadingTask = null;
            }
        }
    }

    private static class UrlLoadingTask extends AsyncTask<URL, Void, Bitmap> {
        private final ImageView updateView;
        private boolean isCancelled = false;
        private InputStream URLInputStream;

        private UrlLoadingTask(ImageView updateView) {
            this.updateView = updateView;
        }

        @Override
        protected Bitmap doInBackground(URL... params) {
            try {
                URLConnection URlConnection = params[0].openConnection();
                URlConnection.setUseCaches(true);
                this.URLInputStream = URlConnection.getInputStream();
                return BitmapFactory.decodeStream(URLInputStream);
            } catch (IOException e) {
                Log.e(UrlImageView.class.getSimpleName(), "cannot load data from this url", e);
                return null;
            } finally {
                if (this.URLInputStream != null) {
                    try {
                        this.URLInputStream.close();
                    } catch (IOException e) {
                        //swallow
                    } finally {
                        this.URLInputStream = null;
                    }
                }
            }
        }

        @Override
        protected void onPostExecute(Bitmap result) {
            if (!this.isCancelled) {
                this.updateView.setImageBitmap(result);
            }
        }

        @Override
        protected void onCancelled() {
            this.isCancelled = true;
            try {
                if (this.URLInputStream != null) {
                    try {
                        this.URLInputStream.close();
                    } catch (IOException e) {
                        //swallow

                    } finally {
                        this.URLInputStream = null;
                    }
                }
            } finally {
                super.onCancelled();
            }
        }
    }
}
