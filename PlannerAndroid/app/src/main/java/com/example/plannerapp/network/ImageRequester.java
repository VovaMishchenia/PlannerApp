package com.example.plannerapp.network;

import android.content.Context;
import android.graphics.Bitmap;
import android.util.DisplayMetrics;
import android.util.LruCache;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.NetworkImageView;
import com.android.volley.toolbox.Volley;
import com.example.plannerapp.application.HomeApplication;

public class ImageRequester {

    //статичне поле для реалізації ін'єкції залежності
    private static ImageRequester instance = null;
    //Константа, яка містить інформацію про програму
    private final Context context;
    //Константа для роботи з потоками(запити)
    private final RequestQueue requestQueue;
    //Константа для завантаження та кешування зображень із віддалених URL
    private final ImageLoader imageLoader;
    // максимальній розмір зображення
    private final int maxByteSize;

    //Конструктор
    private ImageRequester() {


        // отримуємо інформацію про програму(контекст)
        context = HomeApplication.getAppContext();
        //створюємо екземпляр робочого пулу та запитів на основі нашого контексту
        this.requestQueue = Volley.newRequestQueue(context);
        //початок роботи черги
        this.requestQueue.start();
        //підраховуємо максимальний розмір зображення
        this.maxByteSize = calculateMaxByteSize();
        // створюємо кешувальник зображення через констуктор,
        //який приймає створений раніше пул запитів та інтерфейс адаптера кешу(ImageCache)
        this.imageLoader =
                new ImageLoader(
                        requestQueue,
                        //створюємо інтерфейс адаптера кешу
                        new ImageLoader.ImageCache() {
                            //створюємо константу для роботи з кеш-пам'ятю,
                            //яка містить в собі ключ - посилання на фото та саме зображення у форматі bitmap(кожен піксель - байт)
                            private final LruCache<String, Bitmap> lruCache =
                                    new LruCache<String, Bitmap>(maxByteSize) {
                                //Реалізація методу для отримання розміру зображення
                                        @Override
                                        protected int sizeOf(String url, Bitmap bitmap) {
                                            return bitmap.getByteCount();
                                        }
                                    };

                            //Реалізація методу для отримання зображення через ключ-посилання
                            @Override
                            public synchronized Bitmap getBitmap(String url) {
                                return lruCache.get(url);
                            }

                            //Реалізація методу для встановлення зображення через ключ-посилання та саме зображення(байти)
                            @Override
                            public synchronized void putBitmap(String url, Bitmap bitmap) {
                                lruCache.put(url, bitmap);
                            }
                        });
    }

    // реалізація ін'єкції залежності
    public static ImageRequester getInstance() {
        if (instance == null) {
            instance = new ImageRequester();
        }
        return instance;
    }


    //метод для встановлення зображення для view-елемента на фронтенді
    public void setImageFromUrl(NetworkImageView networkImageView, String url) {
        networkImageView.setImageUrl(url, imageLoader);
    }

    //метод для підрахунку максимального розміру зображення
    private int calculateMaxByteSize() {
        //структура, яка описує головні дані про дисплей
        //створюємо через отримання інформації через контекст
        DisplayMetrics displayMetrics = context.getResources().getDisplayMetrics();
        //знаходимо розмір зображення через кількість пікселів, що множиться на розмір одного пікселя у байтах(4)
        final int screenBytes = displayMetrics.widthPixels * displayMetrics.heightPixels * 4;
        return screenBytes * 3;
    }
}